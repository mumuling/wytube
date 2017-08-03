package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;


import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.FriendAdapter;
import com.cqxb.yecall.bean.ContactEntity;
import com.cqxb.yecall.bean.UserBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.listener.GestureListener;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.ClearEditText;
import com.cqxb.yecall.until.PinyinComparator;
import com.cqxb.yecall.until.SideBar;
import com.cqxb.yecall.until.T;
import com.cqxb.until.PinyinUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class FriendActivity extends Fragment implements OnClickListener{
	private static final String TAG = "FriendActivity";
	private String hello;// = "hello android";
	private String defaultHello = "default value";
	private DBHelper dbHelper;
	private ListView listView;
	private SideBar sideBar;
	private TextView dialog;
	private FriendAdapter adapter;
	private TextView fgtv3;
	private ClearEditText mClearEditText;
	private int i=-1;
	private ProgressDialog progressDialog;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<UserBean> sourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	static FriendActivity newInstance(String s) {
		FriendActivity newFragment = new FriendActivity();
		Bundle bundle = new Bundle();
		bundle.putString("hello", s);
		newFragment.setArguments(bundle);
		// bundle还可以在每个标签里传送数据
		return newFragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "FriendActivity-----onCreateView");
//		Bundle args = getArguments();
//		hello = args != null ? args.getString("hello") : defaultHello;
		
		
		View view = inflater.inflate(R.layout.activity_friend, container, false);
		sourceDateList=new ArrayList<UserBean>();
		listView=(ListView)view.findViewById(R.id.country_lvcountry);
		sideBar = (SideBar)view.findViewById(R.id.sidrbar);
		dialog = (TextView)view.findViewById(R.id.dialog);
		fgtv3=(TextView)view.findViewById(R.id.fgtv3);
		mClearEditText = (ClearEditText)view.findViewById(R.id.filter_edit);
		dbHelper=new DBHelper(getActivity());
		progressDialog= ProgressDialog.show(FriendActivity.this.getActivity(), null, "加载中...");
		progressDialog.setCanceledOnTouchOutside(true);
		initUI();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				sourceDateList.clear();
				boolean initData = initData();
				if (initData) {
					updateUI();
					handler.sendEmptyMessage(3);
				}
			}
		}).start();
		
		IntentFilter filter=new IntentFilter(Smack.action);
		getActivity().registerReceiver(broadcastReceiver, filter);
		((LinearLayout)view.findViewById(R.id.toFriendReqList)).setOnClickListener(this);
		((LinearLayout)view.findViewById(R.id.toGroupReq)).setOnClickListener(this);
		
		view.findViewById(R.id.fgrl2).setOnTouchListener(new MyGestureListener(getActivity().getApplicationContext()));
		view.findViewById(R.id.text_notuse).requestFocus();
		
		return view;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.toFriendReqList){
			T.show(getActivity().getApplicationContext(), "该功能还未开放，敬请期待！", 0);
		}else if(v.getId()==R.id.toGroupReq){
			T.show(getActivity().getApplicationContext(), "该功能还未开放，敬请期待！", 0);
		}
	}
	
	public boolean initData(){
		dbHelper.open();
		Cursor data = dbHelper.getData("select * from "+ContactEntity.TABLE, null);
		if(dbHelper==null){
			Log.i(TAG, TAG+"   dbHelper is null");
			return false;
		}
		if(data==null){
			Log.i(TAG, TAG+"   data is null");
			return false;
		}
		for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
			if(data.getString(data.getColumnIndex(ContactEntity.FRIENDID))!=null||!TextUtils.isEmpty(data.getString(data.getColumnIndex(ContactEntity.FRIENDID)))){
				System.out.println(TAG+" :"+" name - "+data.getString(data.getColumnIndex(ContactEntity.NICKNAME)));
				System.out.println(TAG+" :"+" friendId - "+data.getString(data.getColumnIndex(ContactEntity.FRIENDID)));
				System.out.println(TAG+" :"+" img - "+data.getString(data.getColumnIndex(ContactEntity.VISIBILITYIMG)));
				UserBean bean=new UserBean();
				bean.setUserID(data.getString(data.getColumnIndex(ContactEntity.FRIENDID)));
				bean.setNickName(data.getString(data.getColumnIndex(ContactEntity.NICKNAME)));
				bean.setVisibility(data.getString(data.getColumnIndex(ContactEntity.VISIBILITY)));
				bean.setUserAvatarUrl(null);
				bean.setStatusImg(data.getString(data.getColumnIndex(ContactEntity.VISIBILITYIMG)));
				bean.setGroups(data.getString(data.getColumnIndex(ContactEntity.GROUP)));
				sourceDateList.add(bean);
			}
		}
		dbHelper.close();
		return true;
	}
	
	public void initUI(){
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		
		sideBar.setTextView(dialog);
		
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

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//				 T.show(getActivity().getApplicationContext(),((UserBean)adapter.getItem(position)).getUserID(),0);
				// 进入聊天窗口
				 UserBean userBean = sourceDateList.get(position);
				 Intent intent = new Intent(getActivity(),ChatActivity.class);
				 intent.putExtra("jid", userBean.getUserID());
				 intent.putExtra("nickName", userBean.getNickName());
				 startActivity(intent);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
				i=position;
//				 T.show(getActivity().getApplicationContext(),"长按："+((UserBean)adapter.getItem(position)).getNickName(),0);
				final UserBean user=(UserBean)adapter.getItem(position);
				new AlertDialog.Builder(getActivity())
					.setMessage("是否确认删除好友-"+ ((UserBean) adapter.getItem(position)).getNickName() + "？")
					.setTitle("删除提示")
					.setPositiveButton("确认",new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									/*
									 *  方法一：
										setCanceledOnTouchOutside(false);调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
										方法二：
										setCanceleable(false);调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
									 * */
									progressDialog= ProgressDialog.show(getActivity(), null, "请稍候...");
									progressDialog.setCanceledOnTouchOutside(true);
									progressDialog.show();
									try {
										boolean removeUser = Smack.getInstance().removeUser(user.getUserID());
										if(removeUser){
											handler.sendEmptyMessage(0);//操作成功
										}else {
											handler.sendEmptyMessage(1);//操作失败
										}
									} catch (Exception e) {
										handler.sendEmptyMessage(2);//操作失败
									}
									
								
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									dialog.dismiss();
								}
							}).show();
				 
				 return true;
			}
		});

		// 有数据
		if (sourceDateList.size() > 0) {
			// 数据
			sourceDateList = filledData(sourceDateList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
		} else {
			UserBean user = new UserBean();
			user.setNickName("无");
			sourceDateList.add(user);
			sourceDateList = filledData(sourceDateList);
			listView.setVisibility(View.GONE);
			fgtv3.setVisibility(View.VISIBLE);
		}

		// 根据a-z进行排序源数据
		Collections.sort(sourceDateList, pinyinComparator);
		adapter = new FriendAdapter(getActivity().getApplicationContext(), sourceDateList);
		listView.setAdapter(adapter);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {

			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
	}
	
	
	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	public List<UserBean> filledData(List<UserBean> date) {
		List<UserBean> mSortList = new ArrayList<UserBean>();

		for (int i = 0; i < date.size(); i++) {
			if(date.get(i).getNickName()!=null||"".equals(date.get(i).getNickName())){
				UserBean sortModel = new UserBean();
				sortModel.setNickName(date.get(i).getNickName());
				sortModel.setUserAccount(date.get(i).getUserAccount());
				sortModel.setVisibility(date.get(i).getVisibility());
				sortModel.setStatusImg(date.get(i).getStatusImg());
				sortModel.setUserID(date.get(i).getUserID());
				// 汉字转换成拼音
				String pinyin = PinyinUtils.getPingYin(date.get(i).getNickName());
				if(pinyin.length()<=0){
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
		}
		return mSortList;

	}
	
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<UserBean> filterDateList = new ArrayList<UserBean>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = sourceDateList;
		} else {
			filterDateList.clear();
			for (UserBean sortModel : sourceDateList) {
				String name = sortModel.getNickName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if("presenceChanged".equals(intent.getStringExtra("presence"))||"delete".equals(intent.getStringExtra("newInfoFlag"))){
				Log.i(TAG, "广播接收："+intent.getStringExtra("presence")+" "+intent.getStringExtra("newInfoFlag"));
				if(sourceDateList!=null){
					sourceDateList.clear();
				}
				initData();
				updateUI();
			}
		}
	};
	
	public void updateUI(){
		// 有数据
		if (sourceDateList.size() > 0) {
			// 数据
			sourceDateList = filledData(sourceDateList);
			listView.setVisibility(View.VISIBLE);
			fgtv3.setVisibility(View.GONE);
		} else {
			UserBean user = new UserBean();
			user.setNickName("无");
			sourceDateList.add(user);
			sourceDateList = filledData(sourceDateList);
			listView.setVisibility(View.GONE);
			fgtv3.setVisibility(View.VISIBLE);
		}
		// 根据a-z进行排序源数据
		Collections.sort(sourceDateList, pinyinComparator);
		adapter.updateListView(sourceDateList);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	/** 
     * 继承GestureListener，重写left和right方法 
     */  
    private class MyGestureListener extends GestureListener {  
        public MyGestureListener(Context context) {  
            super(context);  
        }  
 
        @Override  
        public boolean left() {  
            Log.e("test", "向左滑");  
            return super.left();  
        }  
  
        @Override  
        public boolean right() {  
            Log.e("test", "向右滑");  
            return super.right();  
        }  
    } 
    
    private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				Presence presence = new Presence(Type.unsubscribe);
				presence.setTo(sourceDateList.get(i).getUserID());
				//<presence id="2wj9c-26" to="zd@yuneasy.cn" type="unsubscribe"></presence>
				System.out.println("删除好友："+presence.toXML());
				Smack.conn.sendPacket(presence);
				dbHelper.open();
				boolean deleteData = dbHelper.deleteData(ContactEntity.TABLE, ContactEntity.FRIENDID + "=?", new String[] {sourceDateList.get(i).getUserID()});
				dbHelper.close();
				if(deleteData){
					T.show(getActivity(), "操作成功！", 0);
					progressDialog.dismiss();
					Intent intent=new Intent(Smack.action);
					intent.putExtra("presence", "presenceChanged");
					getActivity().sendBroadcast(intent);
				}else {
					T.show(getActivity(), "操作失败！", 0);
					progressDialog.dismiss();
					Intent intent=new Intent(Smack.action);
					intent.putExtra("presence", "presenceChanged");
					getActivity().sendBroadcast(intent);
				}
			}else if(msg.what==1){
				T.show(getActivity(), "操作失败！",0);
				progressDialog.dismiss();
			}else if(msg.what==2){
				T.show(getActivity(), "服务器异常！",0);
				progressDialog.dismiss();
			}else if(msg.what==3){
				progressDialog.dismiss();
			}

			
		}
    	
    };
    
}
