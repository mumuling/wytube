package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.ui.RefreshableView;
import com.cqxb.ui.RefreshableView.PullToRefreshListener;
import com.cqxb.until.PinyinUtils;
import com.cqxb.yecall.adapter.CommunityAdapter;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.ClearEditText;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PinyinComparatorCommunity;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.SideBar;

public class CommunityActivity extends Fragment {
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparatorCommunity pinyinComparator;
	private ClearEditText mClearEditText;
	private View view;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_community, null);
		listView = (ListView) view.findViewById(R.id.cl_cl);
		fgtv3 = (TextView) view.findViewById(R.id.fgtv3);
		sideBar = (SideBar) view.findViewById(R.id.sidrbar);
		dialog = (TextView) view.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		refreshableView = (RefreshableView)view.findViewById(R.id.refreshable_view);
		refreshableView.hideHead();
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(cList.size()<=1){
							if("暂无联系人".equals(cList.get(0).getContext()))
							cList.clear();
						}
						cList=new ContactBase(getActivity()).getAllcontact();
						YETApplication.getinstant().setCltList(cList);
						handler.sendEmptyMessage(1);
					}
				}).start();
			}
		}, R.id.cl_cl);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparatorCommunity();
		progressDialog=ProgressDialog.show(getActivity(), null, "加载中。。。");
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

		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new NetAction().getAllEmployee(SettingInfo.getAccount(), new OnResponseListener() {
					
					@Override
					public void onResponse(String statusCode, CommReply reply) {
						if(CommReply.SUCCESS.equals(statusCode)){
							SettingInfo.setParams(PreferenceBean.COMPANYALLEMP, reply.getBody());
						}
					}
				}).exec();
				
			}
		}).start();
		
		return view;
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
		mClearEditText = (ClearEditText) view.findViewById(R.id.cl_cet);
		mClearEditText.addTextChangedListener(new TextWatcher() {

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

		adapter = new CommunityAdapter(getActivity(), cList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 隐藏键盘
				((InputMethodManager) getActivity().getSystemService(
						getActivity().INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getActivity()
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				Contacts contactBean = cList2.get(arg2);
//				T.show(getActivity(), contactBean.getContactName() + "的手机号为："
//						+ contactBean.getNumber(), 0);
				Intent intent = new Intent(getActivity(), CaContactActivity.class);
				intent.putExtra("name", contactBean.getContactName());
				intent.putExtra("callPhone", contactBean.getNumber());
				startActivity(intent);
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏键盘
				((InputMethodManager) getActivity().getSystemService(
						getActivity().INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(getActivity()
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				return false;
			}
		});

	}

	public boolean updateUI(){
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
		return true;
	}
	
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		cList2.clear();
		List<Contacts> filterDateList = new ArrayList<Contacts>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = cList;
		} else {
			filterDateList.clear();
			for (Contacts sortModel : cList) {
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
		cList2.addAll(filterDateList);
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	public List<Contacts> filledData(List<Contacts> date) {
		List<Contacts> mSortList = new ArrayList<Contacts>();
		cList2.clear();
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
		cList2.addAll(mSortList);
		return mSortList;

	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				boolean updateUI = updateUI();
				if(updateUI){
					refreshableView.finishRefreshing();
				}
			}else if (msg.what==000000) {
				boolean updateUI = updateUI();
				if(updateUI){
					progressDialog.dismiss();
				}
			}
			
			
		}
		
		
	};
}