package com.cqxb.yecall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cqxb.yecall.adapter.InformationAdapter;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.listener.MultiUserChatListener;
import com.cqxb.yecall.listener.MyParticipantStatusListener;
import com.cqxb.yecall.listener.MyUserStatusListener;
import com.cqxb.yecall.listener.PackgeListener;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Intent.ACTION_MAIN;

public class InformationActivity extends BaseTitleActivity{
	private ListView listView;
	private DBHelper dbHelper;
	private String TAG="InformationActivity";
	private List<InformationList> informationLists;
	private InformationAdapter adapter;
	public int i;//点击的第几个item
	private ProgressDialog progressDlg;
//	private Map<String, InformationList> roomMap;
	
	private Map<String, MultiUserChat> roomListener;
	String account="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		account=SettingInfo.getParams(PreferenceBean.USERIMACCOUNT, "")+"@"+SettingInfo.getParams(PreferenceBean.USERIMDOMAIN, "");
		setContentView(R.layout.activity_information);
		setTitle("消息");
		setCustomRightBtn(R.drawable.heiadd, new RightListener());
		listView=(ListView)findViewById(R.id.formclient_listview);
		informationLists=new ArrayList<InformationList>();
		dbHelper=new DBHelper(getApplicationContext());
		dbHelper.open();
		try {
			initDate();
			initUI();
		} catch (Exception e) {
			Log.w(TAG, TAG+" init is error ==>>"+e.getLocalizedMessage());
		}
		IntentFilter filter=new IntentFilter(Smack.action);
		registerReceiver(broadcastReceiver, filter);
	}

	
	public void initDate(){
		Cursor data = dbHelper.getData("select * from "+InformationList.TABLE+" where "+InformationList.GID+" = ? ", new String[]{account});
		if(data==null) return ;
		for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
			InformationList object=new InformationList();
			object.setFriendId(data.getString(data.getColumnIndex(InformationList.FRIENDID)));
			object.setContext(data.getString(data.getColumnIndex(InformationList.CONTEXT)));
			object.setFlag(data.getString(data.getColumnIndex(InformationList.FLAG)));
			object.setMsgDate(data.getString(data.getColumnIndex(InformationList.MSGDATE)));
			object.setNickName(data.getString(data.getColumnIndex(InformationList.NICKNAME)));
			object.setObject(data.getString(data.getColumnIndex(InformationList.OBJECT)));
			object.setRoomId(data.getString(data.getColumnIndex(InformationList.ROOMID)));
			
			
			if("1".equals(object.getFlag())){//加好友
				Cursor data2 = dbHelper.getData("select * from "+InformationList.TABLE+" where "+InformationList.FRIENDID+" = ? and flag = '1' and "+InformationList.GID+" = ? ", new String[]{object.getFriendId(),account});
				if(data2!=null){
					object.setCount(data2.getCount()+"") ;
				}
			}else if("2".equals(object.getFlag())){//好友聊天
				Cursor data2 = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.FRIENDID+" = ? and "+SingleChatEntity.ISREAD+" = '"+SingleChatEntity.IS_NOT_READ+"' and "+SingleChatEntity.USERID+" = ? ", new String[]{object.getFriendId(),account});
				if(data2!=null){
					object.setCount(data2.getCount()+"") ;
				}
				object.setContext(BaseUntil.getMsgDistr(data.getString(data.getColumnIndex(InformationList.CONTEXT))));
			}else if("3".equals(object.getFlag())){//加群组
				Cursor data2 = dbHelper.getData("select * from "+InformationList.TABLE+" where "+InformationList.ROOMID+" = ? and flag = '3' and "+InformationList.GID+" = ? ", new String[]{object.getRoomId(),account});
				if(data2!=null){
					object.setCount(data2.getCount()+"") ;
				}
			}else if("4".equals(object.getFlag())){//群组聊天
				Cursor data2 = dbHelper.getData("select * from "+GroupChatEntity.TABLE+" where "+GroupChatEntity.ROOMID+" = ? and "+GroupChatEntity.ISREAD+" = '"+GroupChatEntity.IS_NOT_READ+"' and "+GroupChatEntity.GID+" = ? ", new String[]{object.getRoomId(),account});
				if(data2!=null){
					object.setCount(data2.getCount()+"") ;
				}
			}else if("5".equals(object.getFlag())){//加好友
				
			}else if("6".equals(object.getFlag())){//加好友
				
			}
			
			informationLists.add(object);
		}
	}
	
	public void initUI(){
//		if(roomMap!=null){
//			for(Map.Entry<String, InformationList> map: roomMap.entrySet()){
//				informationLists.add(map.getValue());
//			}
//		}
		showImgCount();
		adapter=new InformationAdapter(getApplicationContext(), informationLists);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				i=arg2;
				InformationList informationList = informationLists.get(arg2);
				if("1".equals(informationList.getFlag())){//请求好友
					new Thread(new Runnable() {
						@Override
						public void run() {
							Looper.prepare();
							handler.sendEmptyMessage(1314);
							Looper.loop();
						}
					}).start();
				}else if("2".equals(informationList.getFlag())){//单人聊天
//					T.show(getApplicationContext(), informationList.getFriendId(), 0);
					Intent intent = new Intent(InformationActivity.this,ChatActivity.class);
					intent.putExtra("jid", informationList.getFriendId());
					intent.putExtra("nickName", informationList.getNickName());
					startActivity(intent);
				}if("3".equals(informationList.getFlag())){//群组请求
					new Thread(new Runnable() {
						@Override
						public void run() {
							Looper.prepare();
							handler.sendEmptyMessage(1315);
							Looper.loop();
						}
					}).start();
				}if("4".equals(informationList.getFlag())){//群聊
//					T.show(getApplicationContext(), informationList.getRoomId(), 0);
					Intent intent = new Intent(InformationActivity.this,GroupChatActivity.class);
					intent.putExtra("jid", informationList.getRoomId());
					intent.putExtra("nickName", informationList.getNickName());
					intent.putExtra("friendId", informationList.getFriendId());
					startActivity(intent);
				}
			}
			
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				InformationList informationList = informationLists.get(arg2);
				if("2".equals(informationList.getFlag())){//请求好友
					if(TextUtils.isEmpty(informationList.getNickName())){
						T.show(getApplicationContext(), informationList.getFriendId(), 0);
					}else{
						T.show(getApplicationContext(), informationList.getNickName(), 0);
					}
				}else if("4".equals(informationList.getFlag())){//请求好友
					T.show(getApplicationContext(), informationList.getRoomId(), 0);
				}
				return true;
			}
			
		});
	}
	
	
	public void refreshUI(){
		informationLists.clear();
		initDate();
//		if(roomMap!=null){
//			for(Map.Entry<String, InformationList> map: roomMap.entrySet()){
//				informationLists.add(map.getValue());
//			}
//		}
		System.out.println("resfresh  : "+informationLists.size());
		showImgCount();
		adapter.updateListView(informationLists);
	}
	
	
	
	//显示消息数目
	public void showImgCount(){
		//显示消息数目
		 TabHost tab= (TabHost) getParent().findViewById(R.id.myTabHost);
		 TextView text =(TextView)tab.getTabWidget().getChildAt(0).findViewById(R.id.title);
		 int count = 0;
		 Cursor data1 = dbHelper.getData("select * from "+InformationList.TABLE+" where flag in (1,3) and "+InformationList.GID+" = ? ", new String[]{account});
		 if(data1!=null){
			 count=data1.getCount();
		 }
//		 System.out.println("显示消息树木:"+count);
		 Cursor data2 = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.ISREAD+" = '"+SingleChatEntity.IS_NOT_READ+"' and "+SingleChatEntity.USERID+" = ? ", new String[]{account});
		 if(data2!=null){
			 count+=data2.getCount();
//			 for (data2.moveToFirst(); !data2.isAfterLast(); data2.moveToNext()) {
//				 System.out.print("显示消息树木:"+data2.getString(data2.getColumnIndex(SingleChatEntity.FRIENDID)));
//				 System.out.println("      显示消息树木:"+data2.getString(data2.getColumnIndex(SingleChatEntity.USERID)));
//			 }
		 }
//		 System.out.println("显示消息树木:"+count);
		 Cursor data3 = dbHelper.getData("select * from "+GroupChatEntity.TABLE+" where "+GroupChatEntity.ISREAD+" = '"+GroupChatEntity.IS_NOT_READ+"' and "+GroupChatEntity.GID+" = ? ", new String[]{account});
		 if(data1!=null){
			 count+=data3.getCount();
		 }
//		 System.out.println("显示消息树木:"+count);
		 text.setText("");
		 text.setTextColor(Color.WHITE);
		 
		 if(count>99){
			 text.setText(" 99+");
			 text.setTextColor(Color.WHITE);
		 }else if(count>0){
			 text.setText(" "+count);
			 text.setTextColor(Color.WHITE);
		 }else {
			 text.setText("");
			 text.setTextColor(Color.WHITE);
		 }
		 
	}
	
	
	
	private class RightListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			T.show(getApplicationContext(), "该功能还未开放，敬请期待!", 0);
		}
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
		unregisterReceiver(broadcastReceiver);
	}
	
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		//newInfo - 新消息  newFriend - 新好友  delete - 删除好友请求  newChatRoom - 新聊天室邀请
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println(TAG+" broadcastReceiver :  "+intent.getStringExtra("newInfoFlag"));
			if("newInfo".equals(intent.getStringExtra("newInfoFlag"))||
			   "newFriend".equals(intent.getStringExtra("newInfoFlag"))||
			   "delete".equals(intent.getStringExtra("newInfoFlag"))||
			   "newChatRoom".equals(intent.getStringExtra("newInfoFlag"))||
			   "newGroupInfo".equals(intent.getStringExtra("newInfoFlag"))){
				refreshUI();
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
					progressDlg=ProgressDialog.show(InformationActivity.this, null, "正在退出。。。");
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
	
	
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			System.out.println("handler msg:"+msg.what);
			if(msg.what==1314){//好友请求
				new AlertDialog.Builder(InformationActivity.this)
				.setMessage("同意添加"+informationLists.get(i).getNickName()+"为好友？")
				.setTitle("消息提示")
				.setPositiveButton("同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
							try {
								progressDlg = ProgressDialog.show(InformationActivity.this, null, "请稍候...");
								progressDlg.show();
								Presence presence=new Presence(Presence.Type.subscribed);
								
								presence.setTo(informationLists.get(i).getFriendId());
								Smack.conn.sendPacket(presence);
								boolean addUser = Smack.getInstance().addUser(informationLists.get(i).getFriendId(), informationLists.get(i).getNickName());
								if(addUser){
									//删除好友请求
									dbHelper.deleteData(InformationList.TABLE, InformationList.FRIENDID + " = ? ", new String[]{informationLists.get(i).getFriendId()});
								}else {
									T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
								}
								progressDlg.dismiss();
								refreshUI();
								Intent intent=new Intent(Smack.action);
				                intent.putExtra("presence", "presenceChanged");
				                getApplicationContext().sendBroadcast(intent);
								
							} catch (Exception e) {
								e.printStackTrace();
								T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
								progressDlg.dismiss();
							}
					}
				})
				.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							progressDlg = ProgressDialog.show(InformationActivity.this, null, "请稍候...");
							progressDlg.show();
							Presence presence=new Presence(Presence.Type.unsubscribed);
							presence.setTo(informationLists.get(i).getFriendId());
							Smack.conn.sendPacket(presence);
							
							boolean deleteData = dbHelper.deleteData(InformationList.TABLE, InformationList.FRIENDID + " = ? ", new String[]{informationLists.get(i).getFriendId()});
							if(deleteData){
								refreshUI();
							}else {
								T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
							}
							//显示消息数目
							progressDlg.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
							T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
							progressDlg.dismiss();
						}
					}
				}).show();
			}else if(msg.what==1315){//群组请求
				new AlertDialog.Builder(InformationActivity.this)
				.setMessage("同意接受"+informationLists.get(i).getFriendId()+"的群组邀请  ？")
				.setTitle("消息提示")
				.setPositiveButton("同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
							try {
								final InformationList informationList = informationLists.get(i);
								progressDlg = ProgressDialog.show(InformationActivity.this, null, "请稍候...");
								progressDlg.show();
								
								
								
								MultiUserChatListener muc=MultiUserChatListener.getInistance();
								muc.getInistanceConn(Smack.getInstance().getConnection());
								MultiUserChat joinMultiUserChat = muc.joinMultiUserChat(Smack.getInstance().getConnection().getUser(),informationList.getRoomId(), BaseUntil.stringNoNull(informationList.getObject()) , null);
								if(joinMultiUserChat==null){
									T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
									progressDlg.dismiss();
									return;
								}
								//将同意了的房间加上监听
								joinMultiUserChat.addMessageListener(PackgeListener.getInstance());
								joinMultiUserChat.addParticipantStatusListener(new MyParticipantStatusListener());
								joinMultiUserChat.addUserStatusListener(new MyUserStatusListener());
								//这里将装的密码替换成房间对象
								//同意请求后就把这个请求变成房间
								
//										InformationList informationList = informationLists.get(i);
//										informationList.setFriendId(informationLists.get(i).getRoomId());
//										informationList.setObject(muc);
//										informationList.setFlag("4");
//										roomMap = YETApplication.getinstant().getRoomMap();
//										if(!roomMap.containsKey(informationLists.get(i).getRoomId())){
//											roomMap.put(informationLists.get(i).getRoomId(), informationList);
//										}
								
								roomListener=YETApplication.getinstant().getRoomListener();
										if(!roomListener.containsKey(informationList.getRoomId())){
											roomListener.put(informationList.getRoomId(), joinMultiUserChat);
										}
										ContentValues initialValues=new ContentValues();
										initialValues.put(InformationList.FRIENDID, informationList.getFriendId());
										initialValues.put(InformationList.NICKNAME, informationList.getNickName());
										initialValues.put(InformationList.CONTEXT, informationList.getContext());
										initialValues.put(InformationList.MSGDATE, informationList.getMsgDate());
										initialValues.put(InformationList.FLAG, "4");
										initialValues.put(InformationList.ROOMID, informationList.getRoomId());
										//
										boolean updateData = dbHelper.updateData(InformationList.TABLE, initialValues, InformationList.ROOMID + " = ? ", new String[]{informationList.getRoomId()});
								
								
//								boolean deleteData = dbHelper.deleteData(InformationList.TABLE, InformationList.ROOMID + " = ? and "+InformationList.FLAG+" = ? ", new String[]{informationLists.get(i).getRoomId(),"3"});
//								if(updateData){
									refreshUI();
//								}
								progressDlg.dismiss();
								
								//显示消息数目
							} catch (Exception e) {
								e.printStackTrace();
								T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
								progressDlg.dismiss();
							}
					}
				})
				.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							progressDlg = ProgressDialog.show(InformationActivity.this, null, "请稍候...");
							progressDlg.show();
							//拒绝
							MultiUserChat.decline(Smack.getInstance().getConnection(), informationLists.get(i).getRoomId(), informationLists.get(i).getFriendId(), "");
							//
							boolean deleteData = dbHelper.deleteData(InformationList.TABLE, InformationList.ROOMID + " = ? ", new String[]{informationLists.get(i).getRoomId()});
							if(deleteData){
								refreshUI();
							}else {
								T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
							}
							progressDlg.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
							progressDlg.dismiss();
							T.show(getApplicationContext(), "操作失败，请稍候再试！", 0);
						}
					}
				}).show();  
			}else if(msg.what==123654){
				progressDlg.dismiss();
			}else if(msg.what==999999){
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
		}
	};
	
	 public String getNickName(String name){
    	if(name.indexOf("@")!=-1){
    		return name.substring(0, name.indexOf("@"));
    	}
    	return name;
     }
	
}
