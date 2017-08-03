package com.cqxb.yecall;

import static android.content.Intent.ACTION_MAIN;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
import org.linphone.core.OnlineStatus;

import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.AddMuneAdapter;
import com.cqxb.yecall.adapter.MyFragmentPagerAdapter;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.listener.MyParticipantStatusListener;
import com.cqxb.yecall.listener.MyUserStatusListener;
import com.cqxb.yecall.listener.PackgeListener;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.until.TimeRender;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainFragmentActivity extends FragmentActivity{
	private ViewPager mPager;
	private String TAG="FragmentActivity";
	private TextView text1, text2; 
	private ImageView image;
	private ArrayList<Fragment> fragmentList;
	
	private int currIndex;//当前页卡编号 
	private int bmpW;//横线图片宽度  
	private int offset;//图片移动的偏移量  
	private ProgressDialog progressDlg;
	private PopupWindow popupWindow;
	private ImageView imageView,search;
	private Fragment[] mFragments;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransation;
	private int mPosX;
	private int mPosY;
	private int mCurrentPosX;
	private int mCurrentPosY;
	private DBHelper dbHelper;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);
		initTextView();
//		initImage();
//		initViewPager();
		initFragment();
		imageView=(ImageView)findViewById(R.id.showPopUp);
		search=(ImageView)findViewById(R.id.search);
		
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopUp(v);
			}
		});
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainFragmentActivity.this,FindActivity.class));
			}
		});
		
		dbHelper=new DBHelper(getApplicationContext());
	}
	
	
	private void initFragment(){
		mFragments = new Fragment[2];
		fragmentManager = getSupportFragmentManager();
		mFragments[1] = fragmentManager.findFragmentById(R.id.main_friend);
		mFragments[0] = fragmentManager.findFragmentById(R.id.main_organize);

		fragmentTransation = fragmentManager.beginTransaction()
				.hide(mFragments[0]).hide(mFragments[1]);
		fragmentTransation.show(mFragments[1]).commit();
		
	}
	
	
	
	/**
	 * 初始化签名
	 */
	 public void initTextView(){  
		 text1 = (TextView)findViewById(R.id.tv_guid1);
		 text2 = (TextView)findViewById(R.id.tv_guid2);
		 text1.setOnClickListener(new TextListener(0));  
		 text2.setOnClickListener(new TextListener(1));  
	 }
	
	 
	 public class TextListener implements OnClickListener{
		private int index=0; 
		
		public TextListener(int i) { 
			index=i;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub\
			/*
			mPager.setCurrentItem(index);
			*/
			if(index==0){
				text1.setBackgroundResource(R.drawable.group_list_selector);
				text2.setBackgroundResource(R.drawable.group_huise);
				search.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.GONE);
			}else{
				text2.setBackgroundResource(R.drawable.group_list_selector);
				text1.setBackgroundResource(R.drawable.group_huise);
				imageView.setVisibility(View.VISIBLE);
				search.setVisibility(View.GONE);
			}
			
			
			fragmentTransation = fragmentManager.beginTransaction()
					.hide(mFragments[0]).hide(mFragments[1]);
			if(v.getId()==R.id.tv_guid1){
				fragmentTransation.show(mFragments[0]).commit();
			}else if(v.getId()==R.id.tv_guid2){
				fragmentTransation.show(mFragments[1]).commit();
			}
			
		}
		 
	 }
	
	 /**
	  * 初始化图片的位移像素 
	  */
	 public void initImage(){  
		 image = (ImageView)findViewById(R.id.cursor);
		 bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.amp1).getWidth();
		 DisplayMetrics dm = new DisplayMetrics(); 
		 getWindowManager().getDefaultDisplay().getMetrics(dm);
		 int screenW = dm.widthPixels;
		 offset =(screenW/4-bmpW)/2; 
		//imgageview设置平移，使下划线平移到初始位置（平移一个offset）
		 Matrix matrix = new Matrix();
		 matrix.postTranslate(offset, 0);
		 image.setImageMatrix(matrix);
	 }
	 
	 
	 public void initViewPager(){  
		 mPager = (ViewPager)findViewById(R.id.viewpagerLayout);
		 fragmentList = new ArrayList<Fragment>();
		 Fragment btFragment= new OrganizeActivity(); 
		 Fragment secondFragment = FriendActivity.newInstance("");
		 fragmentList.add(btFragment); 
		 fragmentList.add(secondFragment);  
		//给ViewPager设置适配器
		 mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
		 mPager.setCurrentItem(1);//设置当前显示标签页为第一页 
		 mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
	 }
	 
	 public class MyOnPageChangeListener implements OnPageChangeListener{
		 private int one = offset *2 +bmpW;//两个相邻页面的偏移量  
		 
		 
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画  
			currIndex = arg0;
			animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
			animation.setDuration(200);//动画持续时间0.2秒  
			image.startAnimation(animation);//是用ImageView来显示动画的  
			int i = currIndex + 1;  
//			T.show(MainFragmentActivity.this, "您选择了第"+i+"个页卡", 0);  
			if(currIndex==0){
				text1.setBackgroundResource(R.drawable.group_list_selector);
				text2.setBackgroundResource(R.drawable.group_huise);
			}else{
				text2.setBackgroundResource(R.drawable.group_list_selector);
				text1.setBackgroundResource(R.drawable.group_huise);
			}
		}  
		 
	 }
	 
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
	
	
	
	// 下拉菜单
	public void showPopUp(View v) {
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundResource(R.drawable.itembg);
		layout.setGravity(Gravity.CENTER);
//		layout.setBackgroundColor(Color.TRANSPARENT);
		View inflate = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.friend_request_list, null);
		inflate.setBackgroundResource(R.drawable.itembg);
		// 生成动态数组，加入数据
		ListView listView = (ListView) inflate.findViewById(R.id.frqlist);
		final List<String> mune = new ArrayList<String>();
		mune.add("添加好友");
		mune.add("创建群组");
		mune.add("加入群组");

		AddMuneAdapter adapter = new AddMuneAdapter(getApplicationContext(),
				mune);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final String string = mune.get(arg2);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Looper.prepare();
						if ("添加好友".equals(string)) {
							handler.sendEmptyMessage(1);
						} else if ("创建群组".equals(string)) {
							handler.sendEmptyMessage(2);
						} else if ("加入群组".equals(string)) {
							handler.sendEmptyMessage(3);
						}
						Looper.loop();
					}
				}).start();
				popupWindow.dismiss();
			}
		});

		layout.addView(inflate);
		popupWindow = new PopupWindow(layout, 400, 420);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		int[] location = new int[2];
		v.getLocationOnScreen(location);
		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],
		// location[1]-popupWindow.getHeight());//上面
		popupWindow.showAsDropDown(v);// 下面
		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
		// location[0]-popupWindow.getWidth(), location[1]); //左边
		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
		// location[0]+v.getWidth(), location[1]); //右边
	}
	
	
	
	
	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			LinearLayout layout=new LinearLayout(getApplicationContext());
			layout.setBackgroundColor(R.drawable.input_normal);
			layout.setOrientation(1);
			
			final int i=msg.what;
			final EditText editText = new EditText(MainFragmentActivity.this);//gid
			editText.setBackgroundResource(R.drawable.input_bg);
			
			final EditText editText2 = new EditText(MainFragmentActivity.this);//pwd or reault
			editText2.setBackgroundResource(R.drawable.input_bg);
			
			layout.addView(editText);
			
			String title="";
			if (msg.what == 1) {
				title="添加好友";
				editText.setHint("请输入好友ID");
				editText2.setHint("备注名称");
				layout.addView(editText2);
			} else if (msg.what == 2) {
				title="请输入要创建的房间号";
				editText.setHint("请输入房间号码");
				editText2.setHint("请输入房间密码(不填默认无密码)");
				layout.addView(editText2);
			} else if (msg.what == 3) {
				title="请输入要加入的房间号";
				editText.setHint("请输入房间号码");
				editText2.setHint("请输入房间密码(不填默认无密码)");
				layout.addView(editText2);
			}else if (msg.what == 4) {
				T.show(getApplicationContext(), "操作成功！", Toast.LENGTH_SHORT);
				progressDlg.dismiss();
			}else if (msg.what == 5) {
				T.show(getApplicationContext(), "操作失败！", Toast.LENGTH_SHORT);
				progressDlg.dismiss();
			}else if(msg.what==123654){
				progressDlg.dismiss();
			}else if(msg.what==999999){
				progressDlg=ProgressDialog.show(MainFragmentActivity.this, null, "正在退出。。。");
				new Thread(new Runnable() {
					@Override
					public void run() {
						boolean exit = YETApplication.getinstant().exit();
						if(exit){
							progressDlg.dismiss();
							finish();
						}
					}
				}).start();
				
			}
			
			
			if(TextUtils.isEmpty(title))
				return;
			final Builder builder = new AlertDialog.Builder(MainFragmentActivity.this);
			builder
			.setTitle(title)
			.setIcon(R.drawable.trans)
			.setView(layout)
			.setPositiveButton("添加", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					if(TextUtils.isEmpty(editText.getText().toString()))
						return;
					builder.create().dismiss();
					progressDlg = ProgressDialog.show(MainFragmentActivity.this, null, "正在发送请求");
					progressDlg.setCanceledOnTouchOutside(true);//设置点击进度对话框外的区域对话框消失 
					progressDlg.setCancelable(true);//设置进度条是否可以按退回键取消
					InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					mInputMethodManager.hideSoftInputFromWindow(
							editText.getWindowToken(), 0);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Looper.prepare();
							if (i == 1) {
								boolean addUser = Smack.addUser(editText.getText().toString(), editText2.getText().toString());
								if(addUser){
									handler.sendEmptyMessage(4);
								}else {
									handler.sendEmptyMessage(5);
								}
							} else if (i == 2) {
//								T.show(getApplicationContext(), "创建房间", 0);
								dbHelper.open();
//								handler.sendEmptyMessage(4);
								MultiUserChat createRoom = Smack.getInstance().createRoom(SettingInfo.getAccount(), editText.getText().toString(), editText2.getText().toString());
								
//								MultiUserChat createRoom = Smack.getInstance().joinMultiUserChat(SettingInfo.getAccount(), editText.getText().toString(), editText2.getText().toString());
								if(createRoom!=null){
									createRoom.addMessageListener(PackgeListener.getInstance());
									createRoom.addParticipantStatusListener(new MyParticipantStatusListener());
									createRoom.addUserStatusListener(new MyUserStatusListener());
									
									String roomName=editText.getText().toString();
									if(roomName.indexOf("@")==-1){
										roomName=roomName+ "@muc." + Smack.conn.getServiceName();
									}
									
									//刚房间监听添加到集合
									if(!YETApplication.getinstant().getRoomListener().containsKey(roomName)){
										YETApplication.getinstant().getRoomListener().put(roomName, createRoom);
									}
									
									System.out.println("房间名称："+roomName);
									
									
									ContentValues initialValues=new ContentValues();
									initialValues.put(InformationList.FRIENDID, SettingInfo.getAccount());
									initialValues.put(InformationList.NICKNAME, SettingInfo.getAccount());
									initialValues.put(InformationList.CONTEXT, "");
									initialValues.put(InformationList.MSGDATE, TimeRender.getDate("yyyy-MM-dd  hh:mm:ss"));
									initialValues.put(InformationList.FLAG, "4");
									initialValues.put(InformationList.ROOMID, roomName);
									//
									boolean insertData = dbHelper.insertData(InformationList.TABLE, null, initialValues);
									if(insertData){
										dbHelper.close();
										handler.sendEmptyMessage(4);
										Intent intent=new Intent(Smack.action);
										intent.putExtra("newInfoFlag", "newChatRoom");  //群组
										getApplicationContext().sendBroadcast(intent);
									}else {
										dbHelper.close();
										handler.sendEmptyMessage(5);
									}
								}else {
									dbHelper.close();
									handler.sendEmptyMessage(5);
								}
								dbHelper.close();
								
							} else if (i == 3) {
								dbHelper.open();
//								T.show(getApplicationContext(), "加入房间", 0);
								MultiUserChat joinMultiUserChat = Smack.getInstance().joinMultiUserChat(SettingInfo.getAccount(), editText.getText().toString(), editText2.getText().toString());
								if(joinMultiUserChat!=null){
									joinMultiUserChat.addMessageListener(PackgeListener.getInstance());
									joinMultiUserChat.addParticipantStatusListener(new MyParticipantStatusListener());
									joinMultiUserChat.addUserStatusListener(new MyUserStatusListener());
									
									String roomName=editText.getText().toString();
									if(roomName.indexOf("@")==-1){
										roomName=roomName+ "@muc." + Smack.conn.getServiceName();
									}
									
									//刚房间监听添加到集合
									if(!YETApplication.getinstant().getRoomListener().containsKey(roomName)){
										YETApplication.getinstant().getRoomListener().put(roomName, joinMultiUserChat);
									}
									
									System.out.println("房间名称："+roomName);
									
									
									ContentValues initialValues=new ContentValues();
									initialValues.put(InformationList.FRIENDID, SettingInfo.getAccount());
									initialValues.put(InformationList.NICKNAME, SettingInfo.getAccount());
									initialValues.put(InformationList.CONTEXT, "");
									initialValues.put(InformationList.MSGDATE, TimeRender.getDate("yyyy-MM-dd  hh:mm:ss"));
									initialValues.put(InformationList.FLAG, "4");
									initialValues.put(InformationList.ROOMID, roomName);
									dbHelper.deleteData(InformationList.TABLE, InformationList.ROOMID+" = ? ", new String[]{roomName});
									dbHelper.deleteData(GroupChatEntity.TABLE, GroupChatEntity.ROOMID+" = ? ", new String[]{roomName});
									//
									boolean insertData = dbHelper.insertData(InformationList.TABLE, null, initialValues);
									if(insertData){
										dbHelper.close();
										handler.sendEmptyMessage(4);
										Intent intent=new Intent(Smack.action);
										intent.putExtra("newInfoFlag", "newChatRoom");  //群组
										getApplicationContext().sendBroadcast(intent);
									}else {
										dbHelper.close();
										handler.sendEmptyMessage(5);
									}
								}else {
									dbHelper.close();
									handler.sendEmptyMessage(5);
								}
								dbHelper.close();
							}else if(i==4){
								
							}else if(i==5){//加入房间
								
							}
							Looper.loop();
						}
					}).start();
				}
			})
			.setNegativeButton("关闭",  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
			builder.show();
			super.handleMessage(msg);
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
	
	
	
	public void exit() {
		refreshStatus(OnlineStatus.Offline);
		finish();
		stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
	}
	
	private void refreshStatus(OnlineStatus status) {
		if (LinphoneManager.isInstanciated()) {
			LinphoneManager.getLcIfManagerNotDestroyedOrNull().setPresenceInfo(0, "", status);
		}
	}
}
