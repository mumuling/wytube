package com.cqxb.yecall;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;


import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.DelayInformation;
import org.jivesoftware.smackx.packet.VCard;

import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.ChatAdapter;
import com.cqxb.yecall.adapter.FaceAdapter;
import com.cqxb.yecall.adapter.FacePageAdeapter;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.bean.XmlBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.CirclePageIndicator;
import com.cqxb.yecall.until.PreferenceBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.PreferenceUtils;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.SoundMeter;
import com.cqxb.yecall.until.SystemUtils;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.until.TimeRender;
import com.cqxb.ui.RefreshableView;
import com.cqxb.ui.XListView;
import com.cqxb.ui.RefreshableView.PullToRefreshListener;
import com.cqxb.ui.XListView.IXListViewListener;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ChatActivity extends BaseTitleActivity implements OnClickListener,OnTouchListener,IXListViewListener{
	
	private List<String> mFaceMapKeys;// 表情对应的字符串数组
	private ViewPager mFaceViewPager;// 表情选择ViewPager
	private LinearLayout mFaceRoot,inputArea;// 表情父容器
	private ImageView showImg;// 显示图片
	private InputMethodManager mInputMethodManager;
	private boolean mIsFaceShow = false;// 是否显示表情
	private WindowManager.LayoutParams mWindowNanagerParams;
	private EditText message;
	private int mCurrentPage;
	private XListView listview;
	private String TAG="ChatActivity";
	private DBHelper dbHelper;
	private String jid="";
	private String nickName=""; 
	private ArrayList<SingleChatEntity> arrayList;
	private ChatAdapter adapter;
	private Button buttonOK; 
	private ImageButton mVoiceSwitchBtn;
	private TextView inputVoice;
	private int currFrom=0;
	private int countData=0;
	String account="";
	
//	private RefreshableView refreshableView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		account=SettingInfo.getParams(PreferenceBean.USERIMACCOUNT, "")+"@"+SettingInfo.getParams(PreferenceBean.USERIMDOMAIN, "");
		jid=getIntent().getStringExtra("jid");
		PreferenceUtils.setPrefString(getApplicationContext(), PreferenceBean.CURRCHATID, jid);
		setContentView(R.layout.chat_window);
		nickName = getIntent().getStringExtra("nickName");
		setTitle("与"+nickName+"聊天中");
		setCustomLeftBtn(R.drawable.fh5, new LeftClickListener());
		setCustomRightBtn(R.drawable.sz6, new RightClickListener());
		dbHelper=new DBHelper(ChatActivity.this.getApplicationContext());
		dbHelper.open();
		
		imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto); 
		buttonOK = (Button) findViewById(R.id.buttonImg); 
		mVoiceSwitchBtn = (ImageButton) findViewById(R.id.voice_switch_btn);
		mVoiceSwitchBtn.setOnClickListener(this);
		inputVoice = (TextView)findViewById(R.id.inputVoice);
		inputArea = (LinearLayout)findViewById(R.id.inputArea);
		inputVoice.setOnTouchListener(new MyOnTouchListener());
		
		buttonOK.setOnClickListener(this); 
		arrayList=new ArrayList<SingleChatEntity>();
		try {
			initQQImg();
			initUI();
		} catch (Exception e) {
			Log.e(TAG, e.getLocalizedMessage()+"");
		}
		IntentFilter filter=new IntentFilter(Smack.action);
		registerReceiver(broadcastReceiver, filter);
		((Button)findViewById(R.id.formclient_btsend)).setOnClickListener(this);
		
		updateInfoStatus();
		
		// 语音输入相关
		volume = (ImageView) this.findViewById(R.id.volume);
		img1 = (ImageView) this.findViewById(R.id.img1);
		sc_img1 = (ImageView) this.findViewById(R.id.sc_img1);
		rcChat_popup = (LinearLayout)findViewById(R.id.rcChat_popup);
		del_re = (LinearLayout) this.findViewById(R.id.del_re);
		voice_rcd_hint_rcding = (LinearLayout) this.findViewById(R.id.voice_rcd_hint_rcding);
		voice_rcd_hint_loading = (LinearLayout) this.findViewById(R.id.voice_rcd_hint_loading);
		voice_rcd_hint_tooshort = (LinearLayout) this.findViewById(R.id.voice_rcd_hint_tooshort);
		mSensor = new SoundMeter();
		
		
//		refreshableView = (RefreshableView)findViewById(R.id.refreshable_view);
//		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
//			@Override
//			public void onRefresh() {
//				mHandler.sendEmptyMessageDelayed(1, 2000);
//			}
//		}, 0);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);
	}
	
	//更改消息状态（未读改已读）
	public void updateInfoStatus(){
		boolean execSql = dbHelper.execSql("update "+SingleChatEntity.TABLE+" set isRead = 'YES' where friendId = ? and "+SingleChatEntity.USERID+" = ? ", new String[]{jid,account});
		Log.i(TAG, "更新已读消息状态："+execSql);
	}
	

	//标题左边按钮
	private class LeftClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			try {
				dbHelper.close();
				finish();
				closeAndRefreshInfomationAcyivity();
				//取消正在播放的语音
				adapter.cancelVoice();
			} catch (Exception e) {
				Log.w(TAG, e.getLocalizedMessage());
			}
			
		}
		
	}
	
	
	//标题右边按钮
	private class RightClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			T.show(getApplicationContext(), "该功能还未开放，敬请期待!", 0);
		}
		
	}
	
	int pictureCount=0;
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.formclient_btsend){
			saveChatRecord();
			pictureCount=0;
		}else if(v.getId()==R.id.buttonImg){
			if(pictureCount>=1){
				T.show(getApplicationContext(), "只能同时发送一张图片！", 0);
				return;
			}
			doPickPhotoAction();
		}else if(v.getId()==R.id.voice_switch_btn){
			if (!mIsFaceShow) {
				mInputMethodManager.hideSoftInputFromWindow(message.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				mFaceRoot.setVisibility(View.VISIBLE);
				mVoiceSwitchBtn.setImageResource(R.drawable.aio_keyboard);
				mIsFaceShow = true;
				
				inputVoice.setVisibility(View.VISIBLE);
				inputArea.setVisibility(View.GONE);
			} else {
//				mFaceRoot.setVisibility(View.GONE);
				mInputMethodManager.showSoftInput(message, 0);
				mVoiceSwitchBtn.setImageResource(R.drawable.chatting_setmode_voice_btn);
				mIsFaceShow = false;
				
				inputVoice.setVisibility(View.GONE);
				inputArea.setVisibility(View.VISIBLE);
			}
		}
		
	}
	
	
	//保存聊天记录
	public void saveChatRecord(){
		if(TextUtils.isEmpty(message.getText().toString())){
			T.show(getApplicationContext(), "请输入文字！", 0);
			return;
		}
		String size = message.getText().toString();
		float bytes2kb = BaseUntil.bytes2kb(size.getBytes().length);
		if(bytes2kb>1.0){
			T.show(getApplicationContext(), "内容过大！", 0);
			return;
		}
//		String content = "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
//		content += "<body style=\"\">";
//		content += "<bodyitem itemtype=\"image\" sha1=\"\" width=\"\" height=\"\">     </bodyitem>";
//		content += "<bodyitem itemtype=\"face\" sha1=\"\">         </bodyitem>";
//		content += "<bodyitem itemtype=\"vibration\" >         </bodyitem>";
//		content += "<bodyitem itemtype=\"text\" >"
//				+ message.getText().toString() + "</bodyitem>";
//		content += "<bodyitem itemtype=\"voice\" sha1=\"\">          </bodyitem>";
//		content += "</body></html>";
		
		System.out.println("聊天时间间隔：11111");
		String content = getRecord(message.getText().toString());
		System.out.println("聊天时间间隔：11111");
		
		
		if(!Smack.getInstance().isAuthenticated()){
			T.show(getApplicationContext(), "未连接到服务器，请检查网络！", 0);
			return;
		}
		
		System.out.println("聊天时间间隔：22222");
		long ts = System.currentTimeMillis();
		final Message newMessage = new Message(jid, Message.Type.chat);
		newMessage.setBody(content);
		DelayInformation delay = new DelayInformation(new Date(ts));
        newMessage.addExtension(delay);
        newMessage.addExtension(new DelayInfo(delay));
		Smack.conn.sendPacket(newMessage);
		System.out.println("聊天时间间隔：22222");
		
		//存放本地数据
		android.os.Message msg=new android.os.Message();
		msg.what=3;
		msg.obj=content;
		mHandler.sendMessage(msg);
		
		System.out.println("聊天时间间隔：55555");
		
		//刷新ui
		mHandler.sendEmptyMessage(2);
		//显示底部
		listview.setSelection(adapter.getCount()-1);
		
		System.out.println("聊天时间间隔：55555");
	}
	
	//将图片和文字混合的字符串整理出格式
	private String getRecord(String chat){
		String content = "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		content += "<body style=\"\">";
//		content += "<bodyitem itemtype=\"face\" sha1=\"\">         </bodyitem>";
//		content += "<bodyitem itemtype=\"vibration\" >         </bodyitem>";
		
//		content += "<bodyitem itemtype=\"voice\" sha1=\"\">          </bodyitem>";

		//qwe <_#_<_>_<_>_<>_# 1
		//图片#_<>_<_>_<_>_#_>dfgedfdfdfdf  <_#_<_>_<_>_<>_# 2
		//图片#_<>_<_>_<_>_#_>23232323 3
		if(chat.indexOf("<_#_<_>_<_>_<>_#")!=-1){
			String[] split = chat.split("<_#_<_>_<_>_<>_#");
			for (int i = 0; i < split.length; i++) {
				if(split[i].indexOf("#_<>_<_>_<_>_#_>")!=-1){
					String[] split2 = split[i].split("#_<>_<_>_<_>_#_>");
					Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(split2[0]);
					String bitmaptoString = BaseUntil.bitmaptoString(loacalBitmap, 100);
					String sha1 ="";
					try {
						sha1=BaseUntil.getSHA1(split2[0]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(split2.length==2){
						content += "<bodyitem itemtype=\"image\" sha1=\""+sha1+"\" width=\"\" height=\"\">"+bitmaptoString+"</bodyitem>";
						content += "<bodyitem itemtype=\"text\" >"+ split2[1] + "</bodyitem>";
					}else {
						content += "<bodyitem itemtype=\"image\" sha1=\""+sha1+"\" width=\"\" height=\"\">"+bitmaptoString+"</bodyitem>";
					}
				}else {
					content += "<bodyitem itemtype=\"text\" >"+ split[i] + "</bodyitem>";
				}
			}
		}else if(chat.indexOf("voice")!=-1)
		{
			content += chat;
		}else {
			content += "<bodyitem itemtype=\"text\" >"+ chat + "</bodyitem>";
		}
		content += "</body></html>";
		
//		System.out.println("呈现的数据 saveChatRecord:"+content);
		return content;
	}
	
	
	public void hiddenInut(){
		mInputMethodManager.hideSoftInputFromWindow(
				message.getWindowToken(), 0);
	}
	
	//获取聊天界面记录
	public void getChatRecord(int from,int limit){
		System.out.println("ChatMsg : "+jid);
		
		Cursor count = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.FRIENDID+" =? and "+SingleChatEntity.USERID+" = ? ", new String[]{jid,account});
		countData=count.getCount();
		if(countData>0){
			currFrom+=limit;
			if(currFrom>=count.getCount()){
				currFrom=count.getCount();
			}
		}
		
		System.out.println("数据报表：   "+limit+"  "+currFrom+"    ");
		
		
		
		Cursor data = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.FRIENDID+" =? and "+SingleChatEntity.USERID +" = ? order by _id desc limit "+currFrom+" offset "+from, new String[]{jid,account});
//		Cursor data = dbHelper.getData("select * from "+SingleChatEntity.TABLE+" where "+SingleChatEntity.FRIENDID+" =? limit 30 offset 0", new String[]{jid});
		if(data==null)
			return;
		for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
			SingleChatEntity object=new SingleChatEntity();
			if("".equals(data.getString(data.getColumnIndex(SingleChatEntity.NICKNAME)))){
				object.setNickName(nickName);
			}else {
				object.setNickName(data.getString(data.getColumnIndex(SingleChatEntity.NICKNAME)));
			}
			object.setFriendId(data.getString(data.getColumnIndex(SingleChatEntity.FRIENDID)));
			object.setContext(BaseUntil.getMsgDistr(data.getString(data.getColumnIndex(SingleChatEntity.CONTEXT))));
			object.setIsRead(data.getString(data.getColumnIndex(SingleChatEntity.ISREAD)));
			object.setMsgDate(data.getString(data.getColumnIndex(SingleChatEntity.MSGDATE)));
			object.setUserId(data.getString(data.getColumnIndex(SingleChatEntity.USERID)));
			object.setWho(data.getString(data.getColumnIndex(SingleChatEntity.WHO)));
			System.out.println("saveChatRecord:"+object.getContext());
			arrayList.add(object);
		}
		//将倒序的数据在倒序  就相当于正了
		Collections.reverse(arrayList);
	}
	
	//初始化界面
	public void initUI(){
		getChatRecord(0,20);
		adapter=new ChatAdapter(getApplicationContext(), arrayList);
		listview.setAdapter(adapter);
		//显示底部
		listview.setSelection(adapter.getCount()-1);
	}
	
	//刷新UI
	public void refreshUI(){
		arrayList.clear();
		getChatRecord(0,20);
		adapter.updateListView(arrayList);
		updateInfoStatus();
	}
	
	
	//初始化QQ标签
	public void initQQImg(){
		listview=(XListView) findViewById(R.id.cl_cl);
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
						ImageSpan imageSpan = new ImageSpan(ChatActivity.this,newBitmap);
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
			//取消正在播放的语音
			adapter.cancelVoice();
			closeAndRefreshInfomationAcyivity();
			this.finish();
		}
		return false;
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if("newInfo".equals(intent.getStringExtra("newInfoFlag"))){//有新消息
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
	 
	 
	 private void doPickPhotoAction(){
		 Context context = ChatActivity.this; 
		 final Context dialogContext = new ContextThemeWrapper(context,android.R.style.Theme_Light); 
		 String cancel = "返回"; 
		 String[] choices=new String[2]; 
		 choices[0] = "拍照"; //拍照
//		 choices[1] = getString(R.string.pick_photo); //从相册中选择
		 choices[1] = "从相册中选择"; //从相册中选择
		 final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,android.R.layout.simple_list_item_1, choices); 
		 final AlertDialog.Builder builder = new AlertDialog.Builder(dialogContext); 
		 builder.setTitle("选择图片"); 
		 builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss(); 
				switch (which)
				{
					case 0: 
					{
						String status = Environment.getExternalStorageState(); 
						if (status.equals(Environment.MEDIA_MOUNTED)){
							//判断是否有SD卡 
								doTakePhoto();// 用户点击了从照相机获取 
						}else {
							T.show(getApplicationContext(), "没有SD卡", 0);
						}
					}
					break;
					
					case 1: 
						doPickPhotoFromGallery();// 从相册中去获取 
					break;
				}
			}
			 
		 });
		 
		 builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss(); 
			}
		 });
		 builder.create().show(); 
		 imageViewPhoto.setVisibility(View.VISIBLE);
	 }
	 
	 
	 /* 用来标识请求照相功能的activity */ 
	 private static final int CAMERA_WITH_DATA = 3023; 
	 /* 用来标识请求gallery的activity */
	 private static final int PHOTO_PICKED_WITH_DATA = 3021; 
	 
	 ImageView imageViewPhoto; 
	/**
	 * 
	 * 拍照获取图片
	 * 
	 * 
	 */

	protected void doTakePhoto()
	{
		pictureCount++;
		try
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			// 直接使用，没有缩小
			startActivityForResult(intent, CAMERA_WITH_DATA);
		}
		catch (ActivityNotFoundException e)
		{
			T.show(ChatActivity.this, "没找到资源", 0);
		} catch (Exception e) {
			T.show(ChatActivity.this, "资源错误", 0);
		}
	}
	
	
	// 请求Gallery程序

	protected void doPickPhotoFromGallery()

	{
		pictureCount++;
		try
		{
			final Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		}
		catch (ActivityNotFoundException e)
		{
			T.show(ChatActivity.this, "没找到资源", 0);
		} catch (Exception e) {
			T.show(ChatActivity.this, "资源错误", 0);
		}
	}

	
	// // 封装请求Gallery的intent

	public static Intent getPhotoPickIntent()
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		return intent;
	}

	
	// 因为调用了Camera和Gally所以要判断他们各自的返回情况,他们启动时是这样的startActivityForResult 
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	  {
		  if (resultCode != RESULT_OK)
			  return ;
		switch (requestCode) {
			case PHOTO_PICKED_WITH_DATA:
				Uri uri = data.getData();
				String selectedImagePath = getPath(uri);
				
				Log.e(TAG, "imgPath:" + selectedImagePath);
				
				Bitmap comp = BaseUntil.comp(BaseUntil.getLoacalBitmap(selectedImagePath), 480, 800, 256);
				String saveBit = BaseUntil.saveBitmap(comp, System.currentTimeMillis()+".png",100);
				
				Bitmap newBit = Bitmap.createBitmap(comp, 0, 0, 60, 60);
				
				ImageSpan imageSpans = new ImageSpan(ChatActivity.this,newBit);

				String emojiStri = "<_#_<_>_<_>_<>_#"+saveBit+"#_<>_<_>_<_>_#_>";
				SpannableString spannableStr = new SpannableString(emojiStri);
//				System.out.println("saveChatRecord:"+emojiStr.indexOf("<_#_<_>_<_>_<>_#")+"   "+emojiStr.indexOf("#_<>_<_>_<_>_#_>"));
				spannableStr.setSpan(imageSpans,emojiStri.indexOf("<_#_<_>_<_>_<>_#"),emojiStri.indexOf("#_<>_<_>_<_>_#_>")+16,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				message.append(spannableStr);
				
				break;
			case CAMERA_WITH_DATA://有时候直接返回的路径是空 会被封装到bundle
				Uri uri1 = data.getData();
				if(uri1 == null){  
					 Bundle bundle = data.getExtras(); 
					 if (bundle != null) {
						 Bitmap  photo = (Bitmap) bundle.get("data"); //get bitmap  256  114
						 
//						 System.out.println("height:"+photo.getHeight()+"  width:"+photo.getWidth());
						 String saveBitmap = BaseUntil.saveBitmap(photo, System.currentTimeMillis()+".png",100);
////						 Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(saveBitmap);
////						 Drawable loacalBitmap = Drawable.createFromPath(saveBitmap);
////						 if(loacalBitmap!=null)imageViewPhoto.setImageDrawable(loacalBitmap);
//						 if(photo!=null)imageViewPhoto.setImageBitmap(photo);
//						 else Toast.makeText(getApplicationContext(), "图片资源不存在", Toast.LENGTH_LONG).show();
						 
						 int rawHeigh = photo.getHeight();//256
						 int rawWidth = photo.getWidth();//114
						 int newHeight = 60;
						 int newWidth = 60;
						 // 计算缩放因子
						 float heightScale = ((float) newHeight) / rawHeigh;
						 float widthScale = ((float) newWidth) / rawWidth;
						 // 新建立矩阵
						 Matrix matrix = new Matrix();
						 matrix.postScale(widthScale, heightScale);
						 // 设置图片的旋转角度
						 // matrix.postRotate(-30);
						 // 设置图片的倾斜
						 // matrix.postSkew(0.1f, 0.1f);
						 // 将图片大小压缩
						 // 压缩后图片的宽和高以及kB大小均会变化
//						 Bitmap newBitmap = Bitmap.createBitmap(photo, 0, 0,newWidth, newHeight, matrix, true);
						 Bitmap newBitmap = Bitmap.createBitmap(photo, 0, 0, 60, 60);
//						 Bitmap newBitmap = Bitmap.createBitmap(photo);
						 ImageSpan imageSpan = new ImageSpan(ChatActivity.this,newBitmap);

						 
						 String emojiStr = "<_#_<_>_<_>_<>_#"+saveBitmap+"#_<>_<_>_<_>_#_>";
						 SpannableString spannableString = new SpannableString(emojiStr);
//						 System.out.println("saveChatRecord:"+emojiStr.indexOf("<_#_<_>_<_>_<>_#")+"   "+emojiStr.indexOf("#_<>_<_>_<_>_#_>"));
						 spannableString.setSpan(imageSpan,emojiStr.indexOf("<_#_<_>_<_>_<>_#"),emojiStr.indexOf("#_<>_<_>_<_>_#_>")+16,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						 message.append(spannableString);
					 }else {        
	                   Toast.makeText(getApplicationContext(), "图片资源不存在", Toast.LENGTH_LONG).show();           
	                 return;        
	                 }    
				}else {
					Log.e(TAG, "imgPath:" + uri1.toString());
					String selectedImagePath1 = getPath(uri1);
					Log.e(TAG, "imgPath:" + selectedImagePath1);
					imageViewPhoto.setImageURI(uri1);
				}
				break;
			default:
				break;
		}
	  }
	  
	  
	public String getPath(Uri uri)
	{
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	LinearLayout rcChat_popup, del_re, voice_rcd_hint_rcding, voice_rcd_hint_loading, voice_rcd_hint_tooshort;
	SoundMeter mSensor;
	ImageView img1, sc_img1, volume;
	class MyOnTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
				inputVoice.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
				rcChat_popup.setVisibility(View.VISIBLE);
				voice_rcd_hint_loading.setVisibility(View.VISIBLE);
				voice_rcd_hint_rcding.setVisibility(View.GONE);
				voice_rcd_hint_tooshort.setVisibility(View.GONE);
				mHandler.postDelayed(new Runnable() {
					public void run() {
						if (!isShosrt) {
							voice_rcd_hint_loading.setVisibility(View.GONE);
							voice_rcd_hint_rcding.setVisibility(View.VISIBLE);
						}
					}
				}, 300);
				img1.setVisibility(View.VISIBLE);
				del_re.setVisibility(View.GONE);
				startVoiceT = SystemClock.currentThreadTimeMillis();
				voiceName = startVoiceT + ".amr";
				start(voiceName);
				flag = 2;
				if(touch){
					mHandler.post(run);
					System.out.println("录音时间开始...........");
					touch=false;
				}
			} else if (arg1.getAction() == MotionEvent.ACTION_UP) {
				touch=true;
				inputVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
				voice_rcd_hint_rcding.setVisibility(View.GONE);
				stop();
				endVoiceT = SystemClock.currentThreadTimeMillis();
				flag = 1;
				int time = (int) ((endVoiceT - startVoiceT) / 1000);
				rcChat_popup.setVisibility(View.GONE);
				mHandler.removeCallbacks(run);
			}
			return true;
		}
		
	}
	boolean touch=true;
	int recordTime=0;
	Runnable run=new Runnable() {
		public void run() {
			recordTime++;
			System.out.println("录音时间："+recordTime);
			mHandler.postDelayed(run, 1000);
			if(recordTime>=59){
				System.out.println("录音时间结束");
				mHandler.removeCallbacks(run);
				recordTime=0;
				
				
				inputVoice.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
				voice_rcd_hint_rcding.setVisibility(View.GONE);
				stop();
				endVoiceT = SystemClock.currentThreadTimeMillis();
				flag = 1;
				int time = (int) ((endVoiceT - startVoiceT) / 1000);
				rcChat_popup.setVisibility(View.GONE);
			}
		}
	};
	
	private int flag = 1;
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
//				refreshableView.finishRefreshing();
			}else if(msg.what==2){
				message.setText("");
				refreshUI();
			}else if(msg.what==3){
				final String content=msg.obj.toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						System.out.println("聊天时间间隔：33333");
						List<XmlBean> xml = BaseUntil.getMsg(content);
						String result = BaseUntil.getListString(xml);
						
//						VCard vcard = new BaseUntil().getVcard(SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""));
						String userName = nickName;
//						if(vcard==null){
//							userName=SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");
//						}else {
//							userName=vcard.getNickName();
//						}
						System.out.println("聊天时间间隔：33333");
						
						
						System.out.println("聊天时间间隔：44444");
						System.out.println(" 呈现的数据  result： "+result);
						ContentValues values = new ContentValues();
				        values.put(SingleChatEntity.NICKNAME, userName);
				        values.put(SingleChatEntity.FRIENDID, jid);
				        values.put(SingleChatEntity.CONTEXT, result);
				        values.put(SingleChatEntity.ISREAD, SingleChatEntity.IS_READ);
				        values.put(SingleChatEntity.MSGDATE, TimeRender.longToDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
				        values.put(SingleChatEntity.USERID, SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""));
				        values.put(SingleChatEntity.WHO, SingleChatEntity.OUT);
						
						dbHelper.insertData(SingleChatEntity.TABLE, null, values);
						
						System.out.println("聊天时间间隔：44444");
						
						mHandler.sendEmptyMessage(2);
					}
				}).start();
			}
		}
		
	};
	private boolean isShosrt = false;
	private long startVoiceT, endVoiceT;
	private String voiceName;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v.getId()==R.id.cl_cl){
			mInputMethodManager.hideSoftInputFromWindow(message.getWindowToken(), 0);
//			mVoiceSwitchBtn.setImageResource(R.drawable.chatting_setmode_voice_btn);
//			mFaceRoot.setVisibility(View.GONE);
			mIsFaceShow = false;
		}else if(v.getId()==R.id.infomation){
			mInputMethodManager.showSoftInput(message, 0);
//			mVoiceSwitchBtn.setImageResource(R.drawable.chatting_setmode_voice_btn);
//			mFaceRoot.setVisibility(View.GONE);
			mIsFaceShow = false;
		}
		
		return false;
	}
	
	private static final int POLL_INTERVAL = 300;
	private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	private Runnable mPollTask = new Runnable() {
		public void run() {
			double amp = mSensor.getAmplitude();
			updateDisplay(amp);
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);

		}
	};
	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
		volume.setImageResource(R.drawable.amp1);
		
		try {
			 
			SystemUtils.createFolder(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path));
			String audioStr =BaseUntil.encodeBase64File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+voiceName);
			//voiceName  既是文件名也是 sha1 的值 我在生成amr文件的时候就已经处理掉了
			String sha1 = BaseUntil.getSHA1(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+voiceName);
			File file=new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+voiceName);
			if(file.exists()){
				file.renameTo(new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+sha1+".amr"));
			}
			
			String content = "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
			content += "<body style=\"\">";
//			content += "<bodyitem itemtype=\"image\" sha1=\"\" width=\"\" height=\"\">     </bodyitem>";
//			content += "<bodyitem itemtype=\"face\" sha1=\"\">         </bodyitem>";
//			content += "<bodyitem itemtype=\"vibration\" >         </bodyitem>";
//			content += "<bodyitem itemtype=\"text\" >"
//					+ message.getText().toString() + "</bodyitem>";
			content += "<bodyitem itemtype=\"voice\" sha1=\""+sha1+"\">"+audioStr+"</bodyitem>";
			content += "</body></html>";
			
			if(Smack.conn!=null){
				message.setText("<bodyitem itemtype=\"voice\" sha1=\""+sha1+"\">"+audioStr+"</bodyitem>");
				saveChatRecord();
			}else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void start(String name) {
		mSensor.start(name);
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}
	
	
	private void updateDisplay(double signalEMA) {
		
		switch ((int) signalEMA) {
		case 0:
		case 1:
			volume.setImageResource(R.drawable.amp1);
			break;
		case 2:
		case 3:
			volume.setImageResource(R.drawable.amp2);
			
			break;
		case 4:
		case 5:
			volume.setImageResource(R.drawable.amp3);
			break;
		case 6:
		case 7:
			volume.setImageResource(R.drawable.amp4);
			break;
		case 8:
		case 9:
			volume.setImageResource(R.drawable.amp5);
			break;
		case 10:
		case 11:
			volume.setImageResource(R.drawable.amp6);
			break;
		default:
			volume.setImageResource(R.drawable.amp7);
			break;
		}
	}

	//下拉刷新
	@Override
	public void onRefresh() {
		System.out.println("数据报表开始下拉");
		arrayList.clear();
		getChatRecord(0,20);
		adapter.updateListView(arrayList);
		updateInfoStatus();
		// TODO Auto-generated method stub
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
				System.out.println("数据报表结束下拉");
			}
		}, 150);
	}

	//加载更多
	@Override
	public void onLoadMore() {
//		// TODO Auto-generated method stub
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				arrayList.clear();
//				getChatRecord(0,20);
//				adapter.updateListView(arrayList);
//				updateInfoStatus();
//				onLoad();
//			}
//		}, 2000);
	}
	
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
		if(currFrom==countData){
			listview.setSelection(0);
		}else {
			listview.setSelection(20);
		}
		
//		listview.smoothScrollToPosition(20);
		System.out.println("数据报表............"+listview.getSelectedItemPosition());
	}
}
