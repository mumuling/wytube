package com.cqxb.yecall;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.AuthCodeRequest;
import com.android.action.param.CommReply;
import com.android.action.param.ResetPwdRequest;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.AuthCodeBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgetPwdActivity extends BaseTitleActivity implements OnClickListener{

	private LinearLayout layout1, layout2;
	private EditText editText1, editText3, editText4,yanzhengma;
	private Button getCode;
	private LinearLayout yy;
	private TextView tt;
	private CheckBox psdStatus1, psdStatus2;
	private LinearLayout llHasAccount;//当获取得到有密码的时候把输入帐号一栏隐藏
	String stringExtra="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		stringExtra = BaseUntil.stringNoNull(getIntent().getStringExtra("loginOk"));
		setContentView(R.layout.forget_pwd);
		setTitle("忘记密码");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 初始化参数
		editText1 = (EditText) findViewById(R.id.editTextPhone);
		editText3 = (EditText) findViewById(R.id.editTextPwd);
		editText4 = (EditText) findViewById(R.id.editTextpwd2);
		yanzhengma= (EditText) findViewById(R.id.yanzhengma);
		
		llHasAccount = (LinearLayout) findViewById(R.id.ll_has_account);
		
		psdStatus1 = (CheckBox) findViewById(R.id.login_psd_status1);
		psdStatus2 = (CheckBox) findViewById(R.id.login_psd_status2);
		
		psdStatus1.setOnClickListener(this);
		psdStatus2.setOnClickListener(this);

		layout1 = (LinearLayout) findViewById(R.id.formRegist_layout);
		layout2 = (LinearLayout) findViewById(R.id.formRegist_layout1);
		yy = (LinearLayout) findViewById(R.id.yy);
		tt = (TextView) findViewById(R.id.tt);
		
		getCode=(Button)findViewById(R.id.getCode);
		getCode.setOnClickListener(this);
		
		if(!"".equals(stringExtra)){
			setTitle("修改密码");
			editText1.setText(SettingInfo.getAccount());
			yy.setVisibility(View.GONE);
			tt.setVisibility(View.GONE);
			editText1.setVisibility(View.GONE);
			
			extraViewGone();//隐藏多余控件
			
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	

	public void regsitOk(View view) {
		if (editText1.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入账号", 1);
			return;
		}
		if (editText1.getText().toString().length() < 11) {
			T.show(getApplicationContext(), "手机号最小长度为11个字符！", 0);
			return;
		}
		if (editText3.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入密码", 1);
			return;
		}
		if (editText3.getText().toString().length() < 6) {
			T.show(getApplicationContext(), "密码至少6位", 1);
			return;
		}
		if (!editText3.getText().toString()
				.equals(editText4.getText().toString())) {
			T.show(getApplicationContext(), "两次输入的密码不一致", 1);
			return;
		}
		
		if(!"".equals(stringExtra)){
			updatePwd();
		}else {
			if (yanzhengma.getText().toString().length()<=0) {
				T.show(getApplicationContext(), "请输入验证码", 1);
				return;
			}
			
			resetPwd();
		}
	}

	public void updatePwd(){
		ResetPwdRequest reset=new ResetPwdRequest();
		reset.setPassword(editText3.getText().toString());
		handler.sendEmptyMessage(1);
		new NetAction().updatePwd(reset, new OnMyResponseListener() {
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if (CommReply.SUCCESS.equals(parseObject
							.getString("statuscode"))) {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
						SettingInfo.setAccount(editText1.getText().toString());
						SettingInfo.setPassword(editText3.getText().toString());
						finish();
					} else {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
					}
				} else {
					T.show(getApplicationContext(), getString(R.string.service_error), 1);
					handler.sendEmptyMessage(2);
				}
			}
		}).execm();
	}
	
	public void resetPwd(){
		ResetPwdRequest reset=new ResetPwdRequest();
		reset.setAccount(editText1.getText().toString());
		reset.setPassword(editText3.getText().toString());
		reset.setVersion(YETApplication.appClass);
		reset.setAuthcode(yanzhengma.getText().toString());
		handler.sendEmptyMessage(1);
		new NetAction().resetPwd(reset, new OnMyResponseListener() {
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if (CommReply.SUCCESS.equals(parseObject
							.getString("statuscode"))) {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
						SettingInfo.setAccount(editText1.getText().toString());
						SettingInfo.setPassword(editText3.getText().toString());
						finish();
					} else {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
					}
				} else {
					T.show(getApplicationContext(), getString(R.string.service_error), 1);
					handler.sendEmptyMessage(2);
				}
			}
		}).execm();
	}
	
	// 接受消息控制 显示不同的页面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
			} else if (msg.what == 2) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
			}
		};
	};
	
	private int time=30;
	
	//处理倒计时
	private Runnable runnable = new Runnable( ) {

		public void run ( ) {
			getCode.setText(time+"");
			getCode.setClickable(false);
			handler.postDelayed(this,1000);//postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
			
			
			if(time<=0){
				time=31;//第二次  这里比外面多1是因为按照从上往下的顺序    
				getCode.setClickable(true);
				getCode.setText("获取语音验证码");
				handler.removeCallbacks(runnable);
			}
			time--;//会在这里在减去1
		}

	};

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.getCode){
			
			if (editText1.getText().toString().length() <= 0) {
				T.show(getApplicationContext(), "请输入账号", 1);
				return;
			}
			
			if (editText1.getText().toString().length() < 11) {
				T.show(getApplicationContext(), "手机号最小长度为11个字符！", 0);
				return;
			}
			
			getCode.setText(""+time);
			getCode.setClickable(false);
			handler.postDelayed(runnable, 0);
			
			T.show(ForgetPwdActivity.this, "请稍候",0);
			AuthCodeRequest authCode=new AuthCodeRequest();
			authCode.setAccount(editText1.getText().toString());
			authCode.setVersion(YETApplication.appClass);
			authCode.setType("1");
			new NetAction().authCode(authCode, new OnMyResponseListener() {
				
				@Override
				public void onResponse(String jsonObject) {
					if(!"".equals(BaseUntil.stringNoNull(jsonObject))){
						AuthCodeBean bean=JSONObject.parseObject(jsonObject.toString(), AuthCodeBean.class);
						if(CommReply.SUCCESS.equals(bean.getStatuscode())){
							yanzhengma.setText("");
						}else {
							T.show(getApplicationContext(), bean.getReason(), 0);
							yanzhengma.setText("");
						}
					}else {
						T.show(getApplicationContext(), getString(R.string.service_error), 0);
						yanzhengma.setText("");
					}
				}
			}).execm();
		} else if (v.getId() == R.id.login_psd_status1) {
			passwordStatus(psdStatus1, editText3);
		} else if (v.getId() == R.id.login_psd_status2) {
			passwordStatus(psdStatus2, editText4);
		}
	}
	
	 //密码的查看与隐藏
	private void passwordStatus(CheckBox checkBox,EditText eText){
		
		if (checkBox.isChecked()) {
			eText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		} else {
			eText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
       CharSequence charSequence = eText.getText();
       if (charSequence instanceof Spannable) {
           Spannable spanText = (Spannable) charSequence;
           Selection.setSelection(spanText, charSequence.length());
       }
	}
	
	/**
	 * 隐藏额外的控件
	 */
	private void extraViewGone() {
		llHasAccount.setVisibility(View.GONE);
		findViewById(R.id.mima_extra_view1).setVisibility(View.GONE);
		findViewById(R.id.mima_extra_view2).setVisibility(View.GONE);
		findViewById(R.id.mima_extra_view3).setVisibility(View.GONE);
		findViewById(R.id.mima_extra_view4).setVisibility(View.GONE);
		findViewById(R.id.login_psd_status1).setVisibility(View.GONE);
		findViewById(R.id.login_psd_status2).setVisibility(View.GONE);
	}
}
