package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.ui.AddressText;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.ui.RefreshableView;
import com.cqxb.ui.RefreshableView.PullToRefreshListener;
import com.cqxb.until.PinyinUtils;
import com.cqxb.yecall.adapter.CommunityAdapter;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.NetUtil;
import com.cqxb.yecall.until.PinyinComparatorCommunity;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.SideBar;
import com.cqxb.yecall.until.T;

public class UpdateNewsletterActivity extends BaseTitleActivity {
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparatorCommunity pinyinComparator;
	private EditText mClearEditText;
	//
	private List<Contacts> cList=new ArrayList<Contacts>();
	private List<Contacts> cList2=new ArrayList<Contacts>();
	private ListView listView;
	private CommunityAdapter adapter;
	public static final String action = "jason.broadcast.action";
	private SideBar sideBar;
	private TextView dialog;
	private TextView fgtv3;
	private ProgressDialog progressDialog;
	private RefreshableView refreshableView;
	private String number,wifi,g3g4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community);
		setTitle("联系人");
		setCustomRightBtn(R.drawable.jia, new OnClickListener() {
			@Override
			public void onClick(View v) {
				T.show(getApplicationContext(), "点击",0);
			}
		});
		listView = (ListView) findViewById(R.id.cl_cl);
		fgtv3 = (TextView) findViewById(R.id.fgtv3);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		refreshableView = (RefreshableView)findViewById(R.id.refreshable_view);
		refreshableView.hideHead();
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						//把加载的数据存放在另外一个list 在handler刷新的时候在赋值刷新  则清空当前的
						if(cList.size()<=1){
							if("暂无联系人".equals(cList.get(0).getContext()))
							cList.clear();
						}
						cList=new ContactBase(getApplicationContext()).getAllcontact();
						YETApplication.getinstant().setCltList(cList);
						handler.sendEmptyMessage(1);
					}
				}).start();
			}
		}, R.id.cl_cl);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparatorCommunity();
		progressDialog=ProgressDialog.show(UpdateNewsletterActivity.this, null, "加载中。。。");
		progressDialog.show();
		initContactList();
		initUI();
		new Thread(new Runnable() {
			@Override
			public void run() {
				cList.clear();
				cList = YETApplication.getinstant().getCltList();
				Log.i("联系人", "联系人:" + cList.size());
				handler.sendEmptyMessage(000000);
			}
		}).start();

		
		//获取所有员工到时候可以先预加载
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				new NetAction().getAllEmployee(SettingInfo.getAccount(), new OnResponseListener() {
//					
//					@Override
//					public void onResponse(String statusCode, CommReply reply) {
//						if(CommReply.SUCCESS.equals(statusCode)){
//							SettingInfo.setParams(PreferenceBean.COMPANYALLEMP, reply.getBody());
//						}
//					}
//				}).exec();
//				
//			}
//		}).start();
		
		wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
		g3g4 = SettingInfo.getParams(PreferenceBean.G3G4CHECK, "");
	}
	
	

	@Override
	protected void onStart() {
		super.onStart();
		wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
		g3g4 = SettingInfo.getParams(PreferenceBean.G3G4CHECK, "");
	}



	public void initUI() {

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					listView.setSelection(position);
				}
			}
		});

		// 给搜索框添加事件 改变联系人列表
		mClearEditText = (EditText) findViewById(R.id.cl_cet);
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.i("Contacts", "Contacts onTextChanged" + s + " " + start
						+ " " + before + " " + count);
				final String result=s.toString();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						Message msg=new Message();
						msg.obj=result;
						msg.what=111111;
						handler.sendMessage(msg);
					}
				}).start();
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.i("Contacts", "Contacts beforeTextChanged" + s + " "
						+ start + " " + after + " " + count);
			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("Contacts", "Contacts afterTextChanged" + s.toString());
			}
		});
		
		findViewById(R.id.imageViewDel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String s = ((EditText) findViewById(R.id.cl_cet)).getText().toString();
				if (s.length() > 0){
					((EditText) findViewById(R.id.cl_cet)).setText(s.substring(0, s.length() - 1));
				}else{
//					onReStartUI();
				}
			}
		});
		findViewById(R.id.imageViewDel).setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				((EditText) findViewById(R.id.cl_cet)).setText("");
				return false;
			}
		});
	}
	// 初始化联系人
	public void initContactList() {

		// 有数据
		if (cList.size() > 0) {
			// 数据
			cList = filledData(cList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
			listView.setClickable(true);
		} else {
			//添加一个为无的list信息以防无法下拉刷新
			Contacts user = new Contacts();
			user.setContactName("无");
			user.setNumber("-123456789");
			cList.add(user);
			cList = filledData(cList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
			listView.setClickable(false);
		}

		// 根据a-z进行排序
		Collections.sort(cList, pinyinComparator);

		adapter = new CommunityAdapter(getApplicationContext(), cList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Contacts contactBean = cList2.get(arg2);
				number=contactBean.getNumber();
				// 隐藏键盘
				((InputMethodManager) getApplicationContext().getSystemService(
						getApplicationContext().INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				int networkState = NetUtil.getNetworkState(getApplicationContext());
				if(networkState==0){//无连接
					T.show(getApplicationContext(), "请检查网络连接！", 0);
					return;
				}else if(networkState==1){//wifi
					if("".equals(wifi)){
//						boolean wifiCheck = getResources().getBoolean(R.bool.wifi_result);
//						if(wifiCheck){//回拨
//							callBack(contactBean.getContactName());
//						}else {//直播
//							calling(contactBean.getContactName());
//						}
						calling(contactBean.getContactName());
					}else {
//						if(Boolean.parseBoolean(wifi)){//回拨
//							callBack(contactBean.getContactName());
//						}else {//直播
//							calling(contactBean.getContactName());
//						}
						calling(contactBean.getContactName());
					}
				}else if(networkState==2){//手机
					if("".equals(g3g4)){
//						boolean g3g4Check = getResources().getBoolean(R.bool.g3g4_result);
//						if(g3g4Check){//回拨
//							callBack(contactBean.getContactName());
//						}else {//直播
//							calling(contactBean.getContactName());
//						}
						calling(contactBean.getContactName());
					}else {
//						if(Boolean.parseBoolean(g3g4)){//回拨
//							callBack(contactBean.getContactName());
//						}else {//直播
//							calling(contactBean.getContactName());
//						}
						calling(contactBean.getContactName());
					}
				}
				

////				T.show(getActivity(), contactBean.getContactName() + "的手机号为："
////						+ contactBean.getNumber(), 0);
//				Intent intent = new Intent(getApplicationContext(), CaContactActivity.class);
//				intent.putExtra("name", contactBean.getContactName());
//				intent.putExtra("callPhone", contactBean.getNumber());
//				startActivity(intent);
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏键盘
				((InputMethodManager) getApplicationContext().getSystemService(
						getApplicationContext().INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				return false;
			}
		});

	}

	public boolean updateUI(){
		cList2.clear();
		// 有数据
		if (cList.size() > 0) {
			// 数据
			cList = filledData(cList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
			listView.setClickable(true);
		} else {
			//添加一个为无的list信息以防无法下拉刷新
			Contacts user = new Contacts();
			user.setContactName("无");
			user.setNumber("-123456789");
			cList.add(user);
			cList = filledData(cList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
			listView.setClickable(false);
		}
		// 根据a-z进行排序源数据
		Collections.sort(cList, pinyinComparator);
		adapter.updateListView(cList);
		cList2.addAll(cList);
		return true;
	}
	
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		System.out.println("filterData 开始");
		cList2.clear();
		List<Contacts> filterDateList = new ArrayList<Contacts>();
		Pattern p=Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(filterStr); 
		if(m.matches()){
			filterDateList.clear();
			for (Contacts sortModel : cList) {
				String name = sortModel.getContactName();
				String en = sortModel.getSortLetters();
				if (en.startsWith(filterStr.toUpperCase().toString())) {
					filterDateList.add(sortModel);
				}
			}
		}else if (TextUtils.isEmpty(filterStr)) {
			filterDateList = cList;
		} else {
			filterDateList.clear();
			for (Contacts sortModel : cList) {
				String name = sortModel.getContactName();
				String number = sortModel.getNumber();
//				if (number.indexOf(filterStr.toString()) != -1
//						|| characterParser.getSelling(number).startsWith(
//								filterStr.toString())
//						|| name.indexOf(filterStr.toString()) != -1
//						|| characterParser.getSelling(name).startsWith(
//								filterStr.toString())) {
//					filterDateList.add(sortModel);
//				}
				
				if (number.indexOf(filterStr.toString()) != -1
						|| name.indexOf(filterStr.toString()) != -1
						) {
					filterDateList.add(sortModel);
				}
			}
		}
		cList2.addAll(filterDateList);
		// 根据a-z进行排序
//		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		System.out.println("filterData 结束");
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	public List<Contacts> filledData(List<Contacts> date) {
		List<Contacts> mSortList = new ArrayList<Contacts>();
		for (int i = 0; i < date.size(); i++) {
			Contacts sortModel = new Contacts();
			sortModel.setContactID(date.get(i).getContactID());
			sortModel.setContactName(date.get(i).getContactName());
			sortModel.setContactPhoto(date.get(i).getContactPhoto());
			sortModel.setContactType(date.get(i).getContactType());
			sortModel.setNumber(date.get(i).getNumber());
			sortModel.setPhotoID(date.get(i).getPhotoID());

			// 汉字转换成拼音
			String pinyin = PinyinUtils
					.getPingYin(date.get(i).getContactName());
			if (pinyin.length() <= 0) {
				continue;
			}
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;

	}
	private ProgressDialog progressDlg;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				boolean updateUI = updateUI();
				if(updateUI){
					refreshableView.finishRefreshing();
				}
				mClearEditText.setText(""+mClearEditText.getText().toString());
			}else if (msg.what==000000) {
				boolean updateUI = updateUI();
				if(updateUI){
					progressDialog.dismiss();
				}
			}else if (msg.what==111111) {
				filterData(msg.obj.toString());
			}else if(msg.what==999999){
				progressDlg=ProgressDialog.show(UpdateNewsletterActivity.this, null, "正在退出。。。");
				new Thread(new Runnable() {
					@Override
					public void run() {
						progressDlg.dismiss();
						LinphoneActivity.instance().exit();
						boolean exit = YETApplication.getinstant().exit();
						if(exit){
							finish();
						}
					}
				}).start();
				
			}
			
			
		}
		
		
	};
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框 
			AlertDialog.Builder isExit = new AlertDialog.Builder(this); 
			// 设置对话框标题  
			isExit.setTitle("系统提示");
			// 设置对话框消息  
		    isExit.setMessage("确定要退出吗");
		    // 添加选择按钮并注册监听  
		    isExit.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss(); 
					handler.sendEmptyMessage(999999);
				}
			});
		    isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 dialog.dismiss(); 
				}
			});
		    // 显示对话框
		    isExit.show();  
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void calling(String name){
		SettingInfo.setParams(PreferenceBean.CALLNAME, name);
		SettingInfo.setParams(PreferenceBean.CALLPHONE, number);
		if(number.length()<=11){
			SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
		}else {
			SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
		}
		LinphoneActivity.instance().startIncallActivity(null);
		AddressType address = new AddressText(UpdateNewsletterActivity.this, null);
		address.setDisplayedName(name);
		address.setText(number);
		LinphoneManager.getInstance().newOutgoingCall(address);
		
	}
	
	public void callBack(String name){
		new NetAction().callBack(SettingInfo.getAccount(), number.replaceAll(" ", ""), new OnMyResponseListener() {
			
			@Override
			public void onResponse(String json) {
				if (!"".equals(BaseUntil.stringNoNull(json))) {
					JSONObject parseObject = JSONObject.parseObject(json);
					if(CommReply.SUCCESS.equals(parseObject.getString("statuscode"))){
						T.show(getApplicationContext(), ""+parseObject.getString("reason"), 0);
					}else {
						T.show(getApplicationContext(), ""+parseObject.getString("reason"), 0);
					}
				} else {
					T.show(getApplicationContext(), getString(R.string.service_error), 0);
				}
			}
		}).execm();
	}
}