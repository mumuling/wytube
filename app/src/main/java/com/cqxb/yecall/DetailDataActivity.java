package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.NetUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import org.linphone.DialerFragment;
import org.linphone.InCallActivity;
import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.ui.AddressText;

public class DetailDataActivity extends BaseActivity implements OnClickListener {
	private String name = "";
	private String number = "", freeNumber, online;
	private TextView phone, sipAccount, accountName;
	private ProgressDialog dialog;
	private String wifi, g3g4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String stringExtra = getIntent().getStringExtra("detail");
		if (!"".equals(stringExtra)) {
			String[] split = stringExtra.split(",");
			name = (null == split[0] ? split[1] : split[0]);
			number = split[1].replaceAll(" ", "");
			if (number.indexOf("@") != -1) {
				number = number.split("@")[0];
				if (number.indexOf(":") != -1) {
					number = number.split(":")[1];
				}
			}
		}
		setContentView(R.layout.activity_detail_data);
		accountName = (TextView) findViewById(R.id.accountName);
		accountName.setText(name);
		phone = (TextView) findViewById(R.id.phone);
		phone.setText(number);
		sipAccount = (TextView) findViewById(R.id.sipAccount);
		sipAccount.setText("");
		findViewById(R.id.calling).setOnClickListener(this);
		findViewById(R.id.backCall).setOnClickListener(this);
		findViewById(R.id.freeCall).setOnClickListener(this);
		findViewById(R.id.back_but).setOnClickListener(new OnClickListener() {public void onClick(View v) {finish();}});
		findViewById(R.id.title_text).setOnClickListener(new OnClickListener() {public void onClick(View v) {finish();}});
		dialog = ProgressDialog.show(DetailDataActivity.this, "", "请稍候。。");
		dialog.show();
		new NetAction().checkaccount(number, new OnMyResponseListener() {
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if (CommReply.SUCCESS.equals(parseObject
							.getString("statuscode"))) {
						freeNumber = parseObject.getString("number");
						online = parseObject.getString("online");
						handler.sendEmptyMessage(0);
					} else {
						T.show(getApplicationContext(),
								"" + parseObject.getString("reason"), 0);
						handler.sendEmptyMessage(1);
					}
				} else {
					T.show(getApplicationContext(),
							getString(R.string.service_error), 0);
					handler.sendEmptyMessage(1);
				}
			}
		}).execm();

		// 处理 防止null
		if ("".equals(phone.getText().toString())
				|| "null".equals(phone.getText().toString())) {
			phone.setText("");
			handler.sendEmptyMessage(2);
		}
		IntentFilter filter = new IntentFilter(Smack.action);
		registerReceiver(broadcastReceiver, filter);

		wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
		g3g4 = SettingInfo.getParams(PreferenceBean.G3G4CHECK, "");
		
		
		Log.w("number", "number="+number);
		Log.w("number", "name="+name);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (LinphoneActivity.instance() != null)
			LinphoneActivity.instance().changeCall();
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				sipAccount.setText(freeNumber);
				// findViewById(R.id.freeAccountLayout).setVisibility(("".equals(freeNumber))?View.GONE:View.VISIBLE);

				phone.setVisibility(("".equals(freeNumber)) ? View.GONE
						: View.VISIBLE);
				findViewById(R.id.sipStation).setVisibility(
						("".equals(freeNumber)) ? View.GONE : View.VISIBLE);// 平台号
																			// 三个字隐藏
				sipAccount.setText(number);// 用户电话
				sipAccount.setTextColor(Color.parseColor("#54ABEE"));

				if ("0".equals(online)) {// 离线
					findViewById(R.id.status).setBackgroundResource(
							R.drawable.lixian);
					// findViewById(R.id.backCall).setBackgroundResource(R.drawable.callling);
					findViewById(R.id.backCall).setClickable(true);
				} else {
					findViewById(R.id.status).setBackgroundResource(
							R.drawable.zaixian);
					// findViewById(R.id.backCall).setBackgroundResource(R.drawable.callling);
					findViewById(R.id.backCall).setClickable(true);
				}
				if (dialog != null)
					dialog.dismiss();
				break;
			case 1:
				// findViewById(R.id.freeAccountLayout).setVisibility(View.GONE);

				phone.setVisibility(View.GONE);
				findViewById(R.id.sipStation).setVisibility(View.GONE);// 平台号
																		// 三个字隐藏
				sipAccount.setText(number);// 用户电话

				findViewById(R.id.status).setBackgroundResource(
						R.drawable.lixian);
				// findViewById(R.id.backCall).setBackgroundColor(Color.GRAY);
				// findViewById(R.id.backCall).setClickable(false);
				if (dialog != null)
					dialog.dismiss();
				break;
			case 2:
				// findViewById(R.id.freeAccountLayout).setVisibility(View.GONE);

				phone.setVisibility(View.GONE);
				findViewById(R.id.sipStation).setVisibility(View.GONE);// 平台号
																		// 三个字隐藏
				sipAccount.setText(number);// 用户电话

				findViewById(R.id.status).setBackgroundResource(
						R.drawable.lixian);
				findViewById(R.id.backCall).setBackgroundColor(Color.GRAY);
				findViewById(R.id.backCall).setClickable(false);
				findViewById(R.id.calling).setClickable(false);
				findViewById(R.id.calling).setBackgroundColor(Color.GRAY);
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.calling) {
			// 直播+0
			String params = SettingInfo.getParams(PreferenceBean.CALLPROYX, "");
			// if(number.length()<10){
			// if("".equals(params)){
			// DialogNativeCade dialog=new
			// DialogNativeCade(DetailDataActivity.this, "确认", "取消");
			// dialog.show();
			// return;
			// }
			// if (number.length() > params.length()) {
			// String subZone = number.substring(0, params.length());
			// if (params.compareTo(subZone) != 0) {
			// // 电话号码中已经带了区号
			// number = params + number;
			// }
			// } else {
			// number = params + number;
			// }
			// }
			int networkState = NetUtil.getNetworkState(getApplicationContext());
			if (networkState == 0) {// 无连接
				T.show(getApplicationContext(), "请检查网络连接！", 0);
				return;
			} else {
				// 处理号码前面加0
				SettingInfo.setParams(PreferenceBean.PHONEADDZERO, "addZero");
				calling();
			}
			name = "";
		} else if (v.getId() == R.id.backCall) {
			SettingInfo.setParams(PreferenceBean.PHONEADDZERO, "addZero");
			callBack();
			name = "";
		}else if (v.getId() == R.id.freeCall) {
			mCalling(number);
		}
	}

	public void calling() {
		if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
			DialerFragment.instance().justLogin(this);
		} else {
			SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
			SettingInfo.setParams(PreferenceBean.CALLNAME, name);
			String prefix = "0";
			if (number.indexOf("00") == 0 || number.indexOf("013") == 0
					|| number.indexOf("014") == 0 || number.indexOf("015") == 0
					|| number.indexOf("016") == 0 || number.indexOf("017") == 0
					|| number.indexOf("018") == 0) {
				prefix = "";
			}
			SettingInfo.setParams(PreferenceBean.CALLPHONE, prefix + number);

			if (number.length() <= 11) {
				SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
			} else {
				SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
			}
			// LinphoneActivity.instance().startIncallActivity(null);
			new Thread(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(DetailDataActivity.this,
							InCallActivity.class);
					intent.putExtra("VideoEnabled", false);
					startActivity(intent);
				}
			}).start();
			AddressType address = new AddressText(DetailDataActivity.this, null);
			address.setDisplayedName(name);
			address.setText(prefix + number);
			LinphoneManager.getInstance().newOutgoingCall(address);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	public void callBack() {
		new NetAction().callBack(SettingInfo.getAccount(), number,
				new OnMyResponseListener() {

					@Override
					public void onResponse(String json) {
						if (!"".equals(BaseUntil.stringNoNull(json))) {
							JSONObject parseObject = JSONObject
									.parseObject(json);
							if (CommReply.SUCCESS.equals(parseObject
									.getString("statuscode"))) {
								T.show(getApplicationContext(), ""
										+ parseObject.getString("reason"), 1);
								SettingInfo.setParams(
										PreferenceBean.CALLBACKSTART,
										"callBackStart");
								SettingInfo.setParams(
										PreferenceBean.CALLBACKSELF,
										"callBackSelf");
								SettingInfo.setParams(
										PreferenceBean.CALLBACKNAME, name);
								// T.show(getApplicationContext(),
								// String.format(getString(R.string.call_back),
								// number), 1);
							} else {
								T.show(getApplicationContext(), ""
										+ parseObject.getString("reason"), 0);
							}
						} else {
							T.show(getApplicationContext(),
									getString(R.string.service_error), 0);
						}
					}
				}).execm();
	}

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if ("hangUp".equals(intent.getStringExtra("hangUp"))) {
				T.show(DetailDataActivity.this, ""
						+ getString(R.string.call_declined), 0);
			}
		}
	};

	/**
	 * 调用免费拨打
	 * @param name 电话号码
	 */
	public void mCalling(String name) {
		if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
			DialerFragment.instance().justLogin(this);
		} else {
			SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
			// LinphoneActivity.instance().startIncallActivity(null);
			SettingInfo.setParams(PreferenceBean.CALLNAME, name);
			SettingInfo.setParams(PreferenceBean.CALLPHONE, number);
			if (number.length() <= 11) {
				SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
			} else {
				SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(DetailDataActivity.this,
							InCallActivity.class);
					intent.putExtra("VideoEnabled", false);
					startActivity(intent);
				}
			}).start();

			AddressType address = new AddressText(this, null);
			address.setDisplayedName(name);
			address.setText(number);
			LinphoneManager.getInstance().newOutgoingCall(address);
		}
	}
}
