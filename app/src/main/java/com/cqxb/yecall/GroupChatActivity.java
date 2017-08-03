package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.http.cookie.SM;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;
import org.jivesoftware.smackx.packet.VCard;

import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.AddMuneAdapter;
import com.cqxb.yecall.adapter.ChatAdapter;
import com.cqxb.yecall.adapter.FaceAdapter;
import com.cqxb.yecall.adapter.FacePageAdeapter;
import com.cqxb.yecall.adapter.GroupAdapter;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.XmlBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.listener.MultiUserChatListener;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.CirclePageIndicator;
import com.cqxb.yecall.until.PreferenceBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.until.TimeRender;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class GroupChatActivity extends BaseTitleActivity implements OnClickListener{
	
	private List<String> mFaceMapKeys;// 表情对应的字符串数组
	private ViewPager mFaceViewPager;// 表情选择ViewPager
	private LinearLayout mFaceRoot;// 表情父容器
	private ImageView showImg;// 显示图片
	private InputMethodManager mInputMethodManager;
	private boolean mIsFaceShow = false;// 是否显示表情
	private WindowManager.LayoutParams mWindowNanagerParams;
	private EditText message;
	private int mCurrentPage;
	private ListView listview;
	private String TAG="ChatActivity";
	private DBHelper dbHelper;
	private String roomid="";
	private String nickName=""; 
	private ArrayList<GroupChatEntity> arrayList;
	private GroupAdapter adapter;
	private String friendId="";
	private MultiUserChat muc;
	private PopupWindow popupWindow;
	private ProgressDialog progressDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_window);
		nickName = getIntent().getStringExtra("nickName");
		roomid=getIntent().getStringExtra("jid");
		friendId=getIntent().getStringExtra("friendId");
		setTitle("与"+roomid+"聊天中");
		setCustomLeftBtn(R.drawable.fh5, new LeftClickListener());
		setCustomRightBtn(R.drawable.sz6, new RightClickListener());
		dbHelper=new DBHelper(GroupChatActivity.this.getApplicationContext());
		dbHelper.open();
		
		arrayList=new ArrayList<GroupChatEntity>();
		try {
			initQQImg();
			initUI();
			
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage());
		}
		IntentFilter filter=new IntentFilter(Smack.action);
		registerReceiver(broadcastReceiver, filter);
		((Button)findViewById(R.id.formclient_btsend)).setOnClickListener(this);
		updateInfoStatus();
	}

	//更改消息状态（未读改已读）
	public void updateInfoStatus(){
		boolean execSql = dbHelper.execSql("update groupChat set isRead = 'YES' where roomId = ? ", new String[]{roomid});
		Log.i(TAG, "更新已读消息状态："+execSql);
	}
	
	
	//标题左边按钮
	private class LeftClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			try {
				dbHelper.close();
				closeAndRefreshInfomationAcyivity();
				finish();
			} catch (Exception e) {
				Log.w(TAG, e.getLocalizedMessage());
			}
			
		}
		
	}
	
	
	//标题右边按钮
	private class RightClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
//			T.show(getApplicationContext(), "该功能还未开放，敬请期待!", 0);
			showPopUpWindow(v);
		}
		
	}
	
	public void showPopUpWindow(View v){
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundResource(R.color.gray);
		layout.setGravity(Gravity.CENTER);
		// layout.setBackgroundColor(Color.TRANSPARENT);
		View inflate = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.friend_request_list, null);
		inflate.setBackgroundResource(R.color.gray);
		// 生成动态数组，加入数据
		ListView listView = (ListView) inflate.findViewById(R.id.frqlist);
		final List<String> mune = new ArrayList<String>();
		mune.add("邀请好友");
//		mune.add("创建群组");
//		mune.add("加入群组");

		AddMuneAdapter adapter = new AddMuneAdapter(getApplicationContext(),
				mune);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final String string = mune.get(arg2);
					if ("邀请好友".equals(string)) {
						handler.sendEmptyMessage(1);
					} 
//						else if ("创建群组".equals(string)) {
//							handler.sendEmptyMessage(2);
//						} else if ("加入群组".equals(string)) {
//							handler.sendEmptyMessage(3);
//						}
				popupWindow.dismiss();
			}
		});

		layout.addView(inflate);
		popupWindow = new PopupWindow(layout, 400, 160);
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
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.formclient_btsend){
			saveChatRecord();
		}
		
	}
	
	
	//保存聊天记录
	public void saveChatRecord(){
		if(TextUtils.isEmpty(message.getText().toString())){
			T.show(getApplicationContext(), "请输入文字！", 0);
			return;
		}
		String content = "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		content += "<body style=\"\">";
//		content += "<bodyitem itemtype=\"image\" sha1=\"\" width=\"\" height=\"\">     </bodyitem>";
//		content += "<bodyitem itemtype=\"face\" sha1=\"\">         </bodyitem>";
//		content += "<bodyitem itemtype=\"vibration\" >         </bodyitem>";
		content += "<bodyitem itemtype=\"text\" >"
				+ message.getText().toString() + "</bodyitem>";
//		content += "<bodyitem itemtype=\"voice\" sha1=\"\">          </bodyitem>";
		content += "</body></html>";
		if(!Smack.getInstance().isAuthenticated()){
			T.show(getApplicationContext(), "未连接到服务器，请重新加入房间！", 0);
			return;
		}
		
		long ts = System.currentTimeMillis();
		final Message newMessage = new Message(Smack.conn.getUser(), Message.Type.normal);
		newMessage.setTo(roomid);
		newMessage.setFrom(Smack.conn.getUser());
		newMessage.setBody(content);
		newMessage.setType(Type.groupchat);
		System.out.println("  ==================::::   "+newMessage.toXML());
//		DelayInformation delay = new DelayInformation(new Date(ts));
//        newMessage.addExtension(delay);
//        newMessage.addExtension(new DelayInfo(delay));
//        
//		Smack.conn.sendPacket(newMessage);
		try {
			muc.sendMessage(newMessage);
		} catch (XMPPException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			T.show(getApplicationContext(), "您由于网络不稳定,已离开房间,请尝试重新加入！", 0);
			return;
		}
		
		List<XmlBean> xml = BaseUntil.getMsg(content);
		content = BaseUntil.getListString(xml);
		
//		System.out.println("vcard:"+userName);
		
		String userNickName="";
		try {
			VCard vcard = new BaseUntil().getVcard(SettingInfo.getAccount());
			if(vcard!=null){
				userNickName=vcard.getNickName();
			}else {
				if(SettingInfo.getAccount()!=null){
					if(SettingInfo.getAccount().indexOf("@")!=-1){
						userNickName=SettingInfo.getAccount().substring(0,SettingInfo.getAccount().indexOf("@"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			T.show(getApplicationContext(), "您由于网络不稳定,已离开房间,请尝试重新加入！", 0);
			return;
		}
		
		
		ContentValues values = new ContentValues();
		values.put(GroupChatEntity.ROOMID, roomid);
        values.put(GroupChatEntity.NICKNAME, userNickName);
        values.put(GroupChatEntity.FRIENDID, SettingInfo.getAccount());
        values.put(GroupChatEntity.CONTEXT, content);
        values.put(GroupChatEntity.ISREAD, GroupChatEntity.IS_READ);
        values.put(GroupChatEntity.MSGDATE, TimeRender.longToDate(ts, "yyyy-MM-dd HH:mm:ss"));
        values.put(GroupChatEntity.WHO, GroupChatEntity.OUT);
		
		dbHelper.insertData(GroupChatEntity.TABLE, null, values);
		
		message.setText("");
		
		refreshUI();
		
		//显示底部
		listview.setSelection(adapter.getCount()-1);
	}
	
	
	public void hiddenInut(){
		mInputMethodManager.hideSoftInputFromWindow(
				message.getWindowToken(), 0);
	}
	
	//获取聊天界面记录
	public void getChatRecord(){
		System.out.println("ChatMsg : "+roomid);
		Cursor data = dbHelper.getData("select * from "+GroupChatEntity.TABLE+" where "+GroupChatEntity.ROOMID+" =? order by _id desc limit 30 offset 0", new String[]{roomid});
//		Cursor data = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.FRIENDID+" =? limit 30 offset 0", new String[]{jid});
		if(data==null)
			return;
		for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
			GroupChatEntity object=new GroupChatEntity();
			object.setRoomId(data.getString(data.getColumnIndex(GroupChatEntity.ROOMID)));
			object.setNickName(data.getString(data.getColumnIndex(GroupChatEntity.NICKNAME)));
			object.setFriendId(data.getString(data.getColumnIndex(GroupChatEntity.FRIENDID)));
			object.setContext(BaseUntil.getMsgDistr(data.getString(data.getColumnIndex(GroupChatEntity.CONTEXT))));
			object.setIsRead(data.getString(data.getColumnIndex(GroupChatEntity.ISREAD)));
			object.setMsgDate(data.getString(data.getColumnIndex(GroupChatEntity.MSGDATE)));
			object.setWho(data.getString(data.getColumnIndex(GroupChatEntity.WHO)));
			arrayList.add(object);
		}
		//将倒序的数据在倒序  就相当于正了
		Collections.reverse(arrayList);
	}
	
	//初始化界面
	public void initUI(){
		getChatRecord();
		adapter=new GroupAdapter(getApplicationContext(), arrayList);
		listview.setAdapter(adapter);
		muc= YETApplication.getinstant().getRoomListener().get(roomid);//test@muc.yuneasy.cn
		System.out.println("房间名称："+roomid+" "+(muc==null?"空":"不空"));//房间名称：test@muc.yuneasy.cn 空
	}
	
	//刷新UI
	public void refreshUI(){
		arrayList.clear();
		getChatRecord();
		adapter.updateListView(arrayList);
		updateInfoStatus();
	}
	
	
	//初始化QQ标签
	public void initQQImg(){
		listview=(ListView) findViewById(R.id.formclient_listview);
		listview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		Set<String> keySet = YETApplication.getinstant().getFaceMap().keySet();
		System.out.println("mFaceMap size:"+keySet.size());
		mFaceMapKeys = new ArrayList<String>();
		mFaceMapKeys.addAll(keySet);
		mFaceViewPager = (ViewPager) findViewById(R.id.face_pager);
		mFaceRoot = (LinearLayout) findViewById(R.id.face_ll);
		showImg = (ImageView) findViewById(R.id.cw_showImg);
		mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		mWindowNanagerParams = getWindow().getAttributes();
		message = (EditText) findViewById(R.id.infomation);
		
		initFacePage();
		
		message.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (mWindowNanagerParams.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
							|| mIsFaceShow) {
						mFaceRoot.setVisibility(View.GONE);
						mIsFaceShow = false;
						// imm.showSoftInput(msgEt, 0);
						return true;
					}
				}
				return false;
			}
		});
		
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
		unregisterReceiver(broadcastReceiver);
		finish();
	}
	
	
	public void initFacePage() {
		List<View> lv = new ArrayList<View>();
		for (int i = 0; i < YETApplication.NUM_PAGE; ++i)
			lv.add(getGridView(i));
		FacePageAdeapter adapter = new FacePageAdeapter(lv);
		mFaceViewPager.setAdapter(adapter);
		mFaceViewPager.setCurrentItem(mCurrentPage);
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mFaceViewPager);
		adapter.notifyDataSetChanged();
		mFaceRoot.setVisibility(View.GONE);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mCurrentPage = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});

	}

	// 图片的view
	private GridView getGridView(int i) {
		// 九宫格列表
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(this, i));
		gv.setOnTouchListener(forbidenScroll());
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == YETApplication.NUM) {// 删除键的位置
					int selection = message.getSelectionStart();
					message.getText().delete(0, selection);
					// String text = message.getText().toString();
					// if (selection > 0) {
					// String text2 = text.substring(selection - 1);
					// if ("]".equals(text2)) {
					// int start = text.lastIndexOf("[");
					// int end = selection;
					// message.getText().delete(start, end);
					// }
					// message.getText().delete(selection - 1, selection);
					// }
				}
				return false;
			}
		});

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == YETApplication.NUM) {// 删除键的位置
					int selection = message.getSelectionStart();
					String text = message.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							message.getText().delete(start, end);
							return;
						}
						message.getText().delete(selection - 1, selection);
					}
				} else {
					int count = mCurrentPage * YETApplication.NUM + arg2;
					// 注释的部分，在EditText中显示字符串
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// 下面这部分，在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) YETApplication.getinstant().getFaceMap().values().toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 计算缩放因子
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// 设置图片的旋转角度
						// matrix.postRotate(-30);
						// 设置图片的倾斜
						// matrix.postSkew(0.1f, 0.1f);
						// 将图片大小压缩
						// 压缩后图片的宽和高以及kB大小均会变化
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(GroupChatActivity.this,newBitmap);
						String emojiStr = mFaceMapKeys.get(count);
						SpannableString spannableString = new SpannableString(emojiStr);
						spannableString.setSpan(imageSpan,emojiStr.indexOf('['),emojiStr.indexOf(']') + 1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						message.append(spannableString);
					} else {
						String ori = message.getText().toString();
						int index = message.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, mFaceMapKeys.get(count));
						message.setText(stringBuilder.toString());
						message.setSelection(index
								+ mFaceMapKeys.get(count).length());
					}
				}
			}
		});
		return gv;
	}

	// 防止乱pageview乱滚动
	private OnTouchListener forbidenScroll() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		};
	}

	public void showImg(View view) {
		if (!mIsFaceShow) {
			mInputMethodManager.hideSoftInputFromWindow(
					message.getWindowToken(), 0);
			try {
				Thread.sleep(80);// 解决此时会黑一下屏幕的问题
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mFaceRoot.setVisibility(View.VISIBLE);
			showImg.setImageResource(R.drawable.aio_keyboard);
			mIsFaceShow = true;
		} else {
			mFaceRoot.setVisibility(View.GONE);
			mInputMethodManager.showSoftInput(message, 0);
			showImg.setImageResource(R.drawable.qzone_edit_face_drawable);
			mIsFaceShow = false;
		}
	}

	public void inputHide(View view) {
		mFaceRoot.setVisibility(View.GONE);
		mInputMethodManager.showSoftInput(message, 0);
		showImg.setImageResource(R.drawable.qzone_edit_face_drawable);
		mIsFaceShow = false;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			closeAndRefreshInfomationAcyivity();
			this.finish();
		}
		return false;
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if("newGroupInfo".equals(intent.getStringExtra("newInfoFlag"))){//有新消息
				try {
					refreshUI();
				} catch (Exception e) {
					Log.w("ChatActivity", "ChatActivity refresh UI is failed");
				}
				
			}
		}
	};
	
	public void closeAndRefreshInfomationAcyivity(){
		 Intent intent=new Intent(Smack.action);
		 intent.putExtra("newInfoFlag", "newInfo");
		 getApplicationContext().sendBroadcast(intent);
	 }
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);

			LinearLayout layout=new LinearLayout(getApplicationContext());
			layout.setBackgroundColor(R.drawable.input_normal);
			layout.setOrientation(1);
			
			final int i=msg.what;
			final EditText editText = new EditText(GroupChatActivity.this);//gid
			editText.setBackgroundResource(R.drawable.input_bg);
			
			final EditText editText2 = new EditText(GroupChatActivity.this);//pwd or reault
			editText2.setBackgroundResource(R.drawable.input_bg);
			
			layout.addView(editText);
			
			String title="";
			if (msg.what == 1) {
				title="邀请好友";
				editText.setHint("请输入好友ID");
				editText2.setHint("邀请信息");
				layout.addView(editText2);
			}else if(msg.what==000000){
				T.show(getApplicationContext(), "操作成功！", Toast.LENGTH_SHORT);
				progressDlg.dismiss();
			}else if(msg.what==999999){
				T.show(getApplicationContext(), "操作失败！", Toast.LENGTH_SHORT);
				progressDlg.dismiss();
			}
			
			if(TextUtils.isEmpty(title))
				return;
			final Builder builder=new AlertDialog.Builder(GroupChatActivity.this);
			builder
			.setTitle(title)
			.setIcon(R.drawable.trans)
			.setView(layout).setPositiveButton("邀请", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					progressDlg = ProgressDialog.show(GroupChatActivity.this, null, "正在发送请求");
					progressDlg.setCanceledOnTouchOutside(true);//设置点击进度对话框外的区域对话框消失 
					progressDlg.setCancelable(true);//设置进度条是否可以按退回键取消
					InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					mInputMethodManager.hideSoftInputFromWindow(
							editText.getWindowToken(), 0);
					new Thread(new Runnable() {
						@Override
						public void run() {
							Looper.prepare();
							if(i==1){
								String jid=editText.getText().toString();
								if(jid.indexOf("@")==-1){
									jid=jid+"@"+Smack.conn.getServiceName();
								}
								final String fjid=jid;
								if(muc!=null){
									try {
										/**
										 * <message id="JN_165" to="123@muc.yuneasy.cn">
										 * <x xmlns="http://jabber.org/protocol/muc#user">
										 * <invite to="zd@yuneasy.cn">
										 * <reason></reason></invite></x></message>
										 */
										Message m=new Message();
										PacketExtension p=new PacketExtension() {
											
											@Override
											public String toXML() {
												String xml="<x xmlns=\"http://jabber.org/protocol/muc#user\"><invite to=\""+fjid+"\"><reason></reason></invite></x>";
												return xml;
											}
											
											@Override
											public String getNamespace() {
												return null;
											}
											
											@Override
											public String getElementName() {
												return null;
											}
										};
										m.addExtension(p);
										m.setTo(roomid);
										
										System.out.println("sssssssss:"+m.toXML());
										muc.invite(m,jid, editText2.getText().toString());
//										muc.invite(jid, editText2.getText().toString());
										handler.sendEmptyMessage(000000);
									} catch (Exception e) {
										e.printStackTrace();
										handler.sendEmptyMessage(999999);
									}
									
								}else {
									handler.sendEmptyMessage(999999);
								}
							}
						}
					}).start();
					
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.show();
		}
		
	};
}
