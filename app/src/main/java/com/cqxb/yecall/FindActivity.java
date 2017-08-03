package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.ui.PinnedHeaderListView;
import com.cqxb.ui.PullRefreshableView;
import com.cqxb.ui.PullRefreshableView.PullToRefreshListener;
import com.cqxb.yecall.adapter.FindAdapter;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.bean.EmployeeBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.ClearEditText;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

public class FindActivity extends BaseActivity {
	private PinnedHeaderListView mListView;
	private FindAdapter mAdapter;
	private List<Contacts> list= new ArrayList<>();
	private List<Contacts> result= new ArrayList<>();
	// 首字母集
	private List<String> mSections= new ArrayList<>();
	// 根据首字母存放数据
	private Map<String, List<String>> mMap;
	// 首字母位置集
	private List<Integer> mPositions;
	// 首字母对应的位置
	private Map<String, Integer> mIndexer;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private ClearEditText edit;
	private TextView close;
	private List<EmployeeBean> parseArray= new ArrayList<>();
	private PullRefreshableView refresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		String allEmployee = SettingInfo.getParams(PreferenceBean.COMPANYALLEMP, "");
		parseArray= JSONArray.parseArray(allEmployee,EmployeeBean.class);
		setContentView(R.layout.activity_find);
		list = new ArrayList<>();
		edit = (ClearEditText) findViewById(R.id.edit);
		close = (TextView) findViewById(R.id.close);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		//初始化数据
		list=YETApplication.getinstant().getCltList();
		//初始化企业数据
		formatAllEmp();
		
		
		initData(list);
		initView();
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.i("Contacts", "Contacts onTextChanged" + s + " " + start
						+ " " + before + " " + count);
				filterData(s.toString());
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
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		refresh=(PullRefreshableView)findViewById(R.id.findRefresh);
		refresh.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				 new NetAction().getAllEmployee(SettingInfo.getAccount(), new OnResponseListener() {
						
						@Override
						public void onResponse(String statusCode, CommReply reply) {
							if(CommReply.SUCCESS.equals(statusCode)){
								final String body=reply.getBody();
								new Thread(new Runnable() {
									@Override
									public void run() {
										parseArray.clear();
										list.clear();
										list=new ContactBase(getApplicationContext()).getAllcontact();
										YETApplication.getinstant().setCltList(list);
										parseArray= JSONArray.parseArray(body,EmployeeBean.class);
										handler.sendEmptyMessage(1);
									}
								}).start();
							}else {
								handler.sendEmptyMessage(2);
							}
						}
				 }).exec();
				 
//				 handler.sendEmptyMessageDelayed(1, 3000);
			}
		}, R.id.findRefresh);
		result=list;
	}
	
	//处理企业数据
	public void formatAllEmp(){
		if(parseArray==null)
			return;
		for (int i = 0; i < parseArray.size(); i++) {
			Contacts ca=new Contacts();
			ca.setContactName(parseArray.get(i).getName());
			ca.setContactType(1);
			String jsonString = JSON.toJSONString(parseArray.get(i).getObjects());
			ca.setContext(jsonString);
			ca.setNumber(parseArray.get(i).getPhone());
			list.add(ca);
		}
	}

	// 处理数据
	public void initData(List<Contacts> filterDateList) {
		mSections = new ArrayList<>();
		mMap = new HashMap<>();
		mPositions = new ArrayList<>();
		mIndexer = new HashMap<>();

		for (int i = 0; i < filterDateList.size(); i++) {
//			System.out.println("name：" + filterDateList.get(i).getContactName()+"===="+filterDateList.get(i).getContactType());
			int flag = filterDateList.get(i).getContactType();
			if (flag == 0) {
				String log = "手机联系人";
				if (mSections.contains(log)) {
					mMap.get(log).add(filterDateList.get(i).getContactName());
				} else {
					mSections.add(log);
					List<String> l = new ArrayList<>();
					l.add(filterDateList.get(i).getContactName());
					mMap.put(log, l);
				}
			} else if (flag == 1) {
				String log = "企业联系人";
				if (mSections.contains(log)) {
					mMap.get(log).add(filterDateList.get(i).getContactName());
				} else {
					mSections.add(log);
					List<String> l = new ArrayList<>();
					l.add(filterDateList.get(i).getContactName());
					mMap.put(log, l);
				}
			}
//			else if (flag == 2) {
//				String log = "来自SIM卡2的你";
//				if (mSections.contains(log)) {
//					mMap.get(log).add(filterDateList.get(i).getContactName());
//				} else {
//					mSections.add(log);
//					List<String> l = new ArrayList<String>();
//					l.add(filterDateList.get(i).getContactName());
//					mMap.put(log, l);
//				}
//			}
		}

		
		
		int position = 0;
		for (int i = 0; i < mSections.size(); i++) {
			mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
			mPositions.add(position);// 首字母在listview中位置，存入list中
			position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
		}
		// 根据a-z进行排序
//		Collections.sort(mSections);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = (PinnedHeaderListView) findViewById(R.id.friends_display);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Contacts contactBean = result.get(arg2);
				int contactType = contactBean.getContactType();
				if(0==contactType){//本机号码
					Intent intent=new Intent(FindActivity.this,CaContactActivity.class);
					intent.putExtra("name", contactBean.getContactName());
					intent.putExtra("callPhone", contactBean.getNumber());
					startActivity(intent);
				}else if(1==contactType){//企业号码
					//"orgInfo", employeeBean.getName()+"="+JSON.toJSONString(employeeBean.getObjects())
					if("".equals(contactBean.getContext())){
						T.show(getApplicationContext(), "该用户还未激活", 0);
						return;
					}
					Intent intent=new Intent(FindActivity.this,OrgContactActivity.class);
					intent.putExtra("orgInfo", contactBean.getContactName()+"="+contactBean.getContext());
					startActivity(intent);
				}
				//本机号码跳转到。CaContactActivity
				
				//公司号码跳转到。OrgContactActivity
				arg1.setBackgroundColor(Color.TRANSPARENT);
			}
		});
		mAdapter = new FindAdapter(this, list, mSections, mPositions);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(mAdapter);
		mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.find_head, mListView, false));
	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<Contacts> filterDateList = new ArrayList<Contacts>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = list;
		} else {
			filterDateList.clear();
			for (Contacts sortModel : list) {
				String name = sortModel.getContactName();
				String number = sortModel.getNumber();
				if (number.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(number).startsWith(
								filterStr.toString())
						|| name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		initData(filterDateList);
		// 根据a-z进行排序
		Collections.sort(mSections);
		mAdapter.updateListView(filterDateList, mSections, mPositions);
		result=filterDateList;
	}

	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				formatAllEmp();
				initData(list);
				result=list;
				mAdapter.updateListView(list, mSections, mPositions);
				refresh.finishRefreshing();
				break;
			case 2:
				T.show(getApplicationContext(), "更新失败！", 0);
				refresh.finishRefreshing();
				break;
			default:
				break;
			}
		}
		
	};
}
