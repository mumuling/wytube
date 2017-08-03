package com.cqxb.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.linphone.LinphoneActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.base.Contact;
import com.cqxb.base.PhoneContacts;
import com.cqxb.yecall.BaseTitleActivity;
import com.cqxb.yecall.NewsletterActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.search.SearchHelper;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.SideBar;
import com.cqxb.ui.SideNav;
import com.cqxb.ui.SideNav.OnTouchingLetterChangedListener;
import com.cqxb.until.ContactSearch;
import com.cqxb.until.PinyinUtils;

public class SMSContactActivity extends BaseTitleActivity implements
		OnClickListener, OnTouchingLetterChangedListener {

	private ListView lv_contactmanager;
	private List<Contact> allContacts=new ArrayList<Contact>();
	private List<Contact> searchContacts=new ArrayList<Contact>();
	private SideNav sideNav;
	private EditText et_sms_search;

	private List<String> smsList = new ArrayList<String>();
	private Map<Integer, Boolean> checkbosMap = new HashMap<Integer, Boolean>();

	private ListViewAdapter lvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_contact);
		setTitle("联系人");
		findViewById(R.id.bt_sms_cancel).setOnClickListener(this);
		findViewById(R.id.bt_sms_ture).setOnClickListener(this);
		findViewById(R.id.iv_sms_delete).setOnClickListener(this);
		findViewById(R.id.iv_sms_delete).setOnLongClickListener(
				new OnLongClickListener() {

					@Override
					public boolean onLongClick(View v) {
						((EditText) findViewById(R.id.et_sms_search))
								.setText("");
						return false;
					}
				});
		((EditText) findViewById(R.id.et_sms_search))
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						allContacts = PhoneContacts.getInstance(
								SMSContactActivity.this).getContacts(s);
						notifyDataChanged();

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					}

					@Override
					public void afterTextChanged(Editable arg0) {
					}
				});
		sideNav = (SideNav) findViewById(R.id.sidenav);
		sideNav.setTextView((TextView) findViewById(R.id.tv_sms_dialog));
		sideNav.setOnTouchingLetterChangedListener(this);

		// 给搜索框添加事件 改变联系人列表
		et_sms_search = (EditText) findViewById(R.id.et_sms_search);
		et_sms_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
//				if ("".equals(search)) {
//					handler.post(run);
//				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		lv_contactmanager = (ListView) findViewById(R.id.lv_sms_contact);
		lv_contactmanager.setTextFilterEnabled(true);

		allContacts = PhoneContacts.getInstance(this).getContacts();
		lvAdapter = new ListViewAdapter();
		lv_contactmanager.setAdapter(lvAdapter);
		lv_contactmanager.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});

	}
	
	private static int lastest = 0;
	public void searchList(final String s) {
		Log.e("", "00000");
//		progressDialog=ProgressDialog.show(NewsletterActivity.this, "", "");
//		progressDialog.setCancelable(true);
//		progressDialog.setCanceledOnTouchOutside(true);
		// 用户可能正在快速输入电话号码，之前在搜索的联系人列表已经过时了。
		final int i = ++lastest;
		// 开一个线程来进行快速搜索
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (TextUtils.isEmpty(s)) {
					// 如果输入的字符串为空，则显示所有联系人
					searchContacts = allContacts;
				} else {
					// searhContacts指向搜索返回的list
					searchContacts = ContactSearch.search(s, allContacts);
				}
				//final List<Contact> filledData = filledData(searchContacts);
				
				if (i == lastest) { // 避免线程同步问题
					SMSContactActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (i == lastest) { // 避免线程同步问题
//								contactList.clear();
//								contactList.addAll(searchContacts);
							//	refreshList(filledData);
								
							}
						}
					});
				}
			}
		}).start();
	}

	
	
//	int flag=0;
//	String search="";
//	private Runnable run=new Runnable() {
//		public void run() {
//			flag++;
//			if(flag>=0){
//				handler.removeCallbacks(run);
//			}
//			if(search.equals(et_sms_search.getText().toString())){
//				flag=0;
//				search="";
//				handler.removeCallbacks(run);
//				searchList(et_sms_search.getText().toString());
//			}else {
//				handler.postDelayed(run, 300);
//				search=et_sms_search.getText().toString();
//			}
//		}
//	};
	
//	private ProgressDialog progressDlg;
//	private Handler handler=new Handler(){

//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if(msg.what==1){
//				boolean updateUI = updateUI();
//				if(updateUI){
//					refreshableView.finishRefreshing();
//				}
//				mClearEditText.setText(""+mClearEditText.getText().toString());
//			}else if (msg.what==000000) {
//				boolean updateUI = updateUI();
//				if(updateUI){
//					progressDialog.dismiss();
//				}
//			}else if(msg.what==999999){
//				SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
//				SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
//				progressDlg=ProgressDialog.show(NewsletterActivity.this, null, "正在退出。。。");
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						progressDlg.dismiss();
//						LinphoneActivity.instance().exit();
//						boolean exit = YETApplication.getinstant().exit();
//						if(exit){
//							finish();
//						}
//					}
//				}).start();
//				
//			}
//			
//			
//		}
//		
//		
//	};
	
	protected void notifyDataChanged() {
		// 更新对应listview的adapter
		ListViewAdapter adapter;
		if (lv_contactmanager.getAdapter() instanceof HeaderViewListAdapter) {
			HeaderViewListAdapter listAdapter = (HeaderViewListAdapter) lv_contactmanager
					.getAdapter();
			adapter = (ListViewAdapter) listAdapter.getWrappedAdapter();
		} else {
			adapter = (ListViewAdapter) lv_contactmanager.getAdapter();
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		for (int i = 0; i < allContacts.size(); i++) {
			if (PinyinUtils.converterToFirstSpell(
					allContacts.get(i).getFname().substring(0, 1)).equals(s)) {
				lv_contactmanager.setSelection(i);
				return;
			}
		}

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.bt_sms_cancel){
			smsList.clear();
			checkbosMap.clear();
			lvAdapter.notifyDataSetChanged();
		}else if(v.getId()==R.id.bt_sms_ture){
			if (smsList != null) {
				for (int i = 0; i < smsList.size(); i++) {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(smsList.get(i), null, "优E通短信分享",
							null, null);
				}
				Toast.makeText(this, "推送消息发送成功！", Toast.LENGTH_SHORT).show();
				SMSContactActivity.this.finish();
			} else {
				Toast.makeText(this, "请选择联系人！", Toast.LENGTH_SHORT).show();
			}
		}else if(v.getId()==R.id.iv_sms_delete){
			String s = ((EditText) findViewById(R.id.et_sms_search)).getText()
					.toString();
			if (s.length() > 0) {
				((EditText) findViewById(R.id.et_sms_search)).setText(s
						.substring(0, s.length() - 1));
			}
		}
	}

	private class ListViewAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return allContacts == null ? 0 : allContacts.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_sms,
						null);
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_contactName);
				holder.phone = (TextView) convertView
						.findViewById(R.id.tv_contact_number);
				holder.sms_checkbox = (CheckBox) convertView
						.findViewById(R.id.cb_sms);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(allContacts.get(position).getFname());
			holder.phone.setText(allContacts.get(position).getPhone());

			holder.sms_checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(
								CompoundButton compoundButton, boolean b) {
							if (b) {
								smsList.add(allContacts.get(position)
										.getPhone());
								checkbosMap.put(position, true);
							} else {
								smsList.remove(allContacts.get(position)
										.getPhone());
								checkbosMap.put(position, false);
							}
						}
					});

			if (checkbosMap != null && checkbosMap.get(position) != null
					&& checkbosMap.get(position)) {
				holder.sms_checkbox.setChecked(true);
			} else {
				holder.sms_checkbox.setChecked(false);
			}

			return convertView;
		}
	}

	public final class ViewHolder {
		TextView name;
		TextView phone;
		View call;
		View sms;
		CheckBox sms_checkbox;
	}

}
