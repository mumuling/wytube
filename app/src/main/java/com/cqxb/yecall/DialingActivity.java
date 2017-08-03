package com.cqxb.yecall;




import java.util.ArrayList;
import java.util.List;

import org.linphone.LinphoneActivity;
import org.linphone.ui.AddressText;
import org.linphone.ui.CallButton;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cqxb.yecall.adapter.DialingAdapter;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.T;

public class DialingActivity extends BaseTitleActivity implements OnClickListener{
	private ListView listView;
	private List<Contacts> cList;//通话记录 通过url查询
	private List<Contacts> cList2;//搜索后的通话记录
	private DialingAdapter adapter;
	private AddressText address;
	private CallButton callButton;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_phone_keyboard);
		setTitle("拨号 ");
		
		
		listView=(ListView)findViewById(R.id.cpklv1);
		
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		IntentFilter filter=new IntentFilter(ContactActivity.action);
		registerReceiver(broadcastReceiver, filter);
		phoneCallList();
		
		address=(AddressText)findViewById(R.id.cpket1);
		callButton=(CallButton)findViewById(R.id.callButton);
		
		findViewById(R.id.cpkNum0).setOnClickListener(this);
		findViewById(R.id.cpkNum1).setOnClickListener(this);
		findViewById(R.id.cpkNum2).setOnClickListener(this);
		findViewById(R.id.cpkNum3).setOnClickListener(this);
		findViewById(R.id.cpkNum4).setOnClickListener(this);
		findViewById(R.id.cpkNum5).setOnClickListener(this);
		findViewById(R.id.cpkNum6).setOnClickListener(this);
		findViewById(R.id.cpkNum7).setOnClickListener(this);
		findViewById(R.id.cpkNum8).setOnClickListener(this);
		findViewById(R.id.cpkNum9).setOnClickListener(this);
		findViewById(R.id.cpkNumStar).setOnClickListener(this);
		findViewById(R.id.cpkNumHusa).setOnClickListener(this);
		findViewById(R.id.cpkib2).setOnClickListener(this);
		
		//长按删除 
		findViewById(R.id.cpkib2).setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				address.setText("");
				return false;
			}
		});

		//短按删除
		findViewById(R.id.cpkib2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String s = address.getText().toString();
				if (s.length() > 0)
					address.setText(s.substring(0, s.length() - 1));
			}
		});
		
		
		//监听号码输入框的文字改变
		address.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("CallPhone", "CallPhone :" + s);
				if (s.length() > 0) {
//							((RelativeLayout)findViewById(R.id.call_img)).setVisibility(View.INVISIBLE);
					((MainTabActivity) getParent()).setDialerPanVisibility(View.VISIBLE);
					((RelativeLayout)findViewById(R.id.cpkrl1)).setVisibility(View.VISIBLE);
					setTitleVisiable(View.GONE);
				} else {
//							((RelativeLayout)findViewById(R.id.call_img)).setVisibility(View.GONE);
					((MainTabActivity) getParent()).setDialerPanVisibility(View.GONE);
					((RelativeLayout)findViewById(R.id.cpkrl1)).setVisibility(View.GONE);
					setTitleVisiable(View.VISIBLE);
				}
				
				filterData(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});
		
		callButton = (CallButton) findViewById(R.id.callButton);
		callButton.setAddressWidget(address);
	}
	
	
	//手机通话记录
	public void phoneCallList(){
		cList=new ArrayList<Contacts>();
		cList=YETApplication.getinstant().getThjl();
		adapter=new DialingAdapter(getApplicationContext(), cList);
		listView.setAdapter(adapter);
		adapter.updateListView(cList);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(address.getText().length()<=0){
					address.setText(cList.get(arg2).getNumber());
				}else {
					address.setText(cList2.get(arg2).getNumber());
				}
				callOut();
			}
		});
		
//			listView.setOnTouchListener(new OnTouchListener() {
//				
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					LinearLayout linerLayout=(LinearLayout)findViewById(R.id.cpkll1);
//					linerLayout.setVisibility(View.GONE);
//					return false;
//				}
//			});
	}
		
		
	//删除号码
	public void delNumber() {
		String text = address.getText().toString();
		int len = text.length();
		if (len > 0) {
			text = text.substring(0, len - 1);
		}
		address.setText(text);
	}

	//添加号码
	public void addNumber(String number) {
		String text = address.getText().toString();
		address.setText(text + number);
	}
		
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<Contacts> filterDateList = new ArrayList<Contacts>();
		cList2=new ArrayList<Contacts>();
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = cList;
		}else{
			filterDateList.clear();
			for(Contacts sortModel : cList){
				System.out.println("CallPhone "+sortModel.getContactName()+" "+sortModel.getNumber());
				String name = sortModel.getContactName();
				String number=sortModel.getNumber();
				if(number.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(number).startsWith(filterStr.toString())||name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
					cList2.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		//Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	
	//打电话
	public void callOut(){
		if(TextUtils.isEmpty(address.getText().toString())){
			T.show(getApplicationContext(), "请选择号码！", 0);
			return;
		}
		/*
		List<ContactBean> cltList = MyApplication.getinstant().getCltList();
		for (int i = 0; i < cltList.size(); i++) {
			if(cltList.get(i).getNumber().equals(textView.getText().toString())){
				//
			}
		}
		*/
		T.show(getApplicationContext(), "号码："+address.getText().toString(), 0);
		
		findViewById(R.id.callButton).performClick();
		
		
		startActivity(new Intent(DialingActivity.this,LinphoneActivity.class));
		//调用系统方法拨打电话  
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
//					        .parse("tel:" + textView.getText().toString()));  
//					startActivity(dialIntent);
//				}
//			}).start();
	    
//		Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView.getText().toString()));  
//		startActivity(dialIntent);
//		AddressText address=new AddressText(getApplicationContext(), null);
//		address.setText(((EditText) findViewById(R.id.cpket1)).getText());
//		LinphoneManager.getInstance().newOutgoingCall(address);
		
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cpkNum0) {
			addNumber("0");
		} else if (v.getId() == R.id.cpkNum1) {
			addNumber("1");
		} else if (v.getId() == R.id.cpkNum2) {
			addNumber("2");
		} else if (v.getId() == R.id.cpkNum3) {
			addNumber("3");
		} else if (v.getId() == R.id.cpkNum4) {
			addNumber("4");
		} else if (v.getId() == R.id.cpkNum5) {
			addNumber("5");
		} else if (v.getId() == R.id.cpkNum6) {
			addNumber("6");
		} else if (v.getId() == R.id.cpkNum7) {
			addNumber("7");
		} else if (v.getId() == R.id.cpkNum8) {
			addNumber("8");
		} else if (v.getId() == R.id.cpkNum9) {
			addNumber("9");
		} else if (v.getId() == R.id.cpkNumStar) {
			addNumber("*");
		} else if (v.getId() == R.id.cpkNumHusa) {
			addNumber("#");
		} else if (v.getId() == R.id.cpkib2) {
			delNumber();
		} 
	}
	
	//跳转到联系人列表
	public void toContacts(View v){
		System.out.println("跳转到联系人列表");
		Intent intent=new Intent(this,ContactActivity.class);
		startActivity(intent);
	}
	
	
	//监听按键
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
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
					YETApplication.getinstant().exit();
					dialog.dismiss(); 
					//finish();
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
		return false;  
	}

	
	BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if("更新拨号界面号码".equals(intent.getStringExtra("data"))){
				//ToastUtil.makeText(getApplicationContext(), ""+intent.getStringExtra("callPhone"), Toast.LENGTH_SHORT);
				address.setText(intent.getStringExtra("callPhone"));
			}
		}
	};
	
	public void hideDialing(){
		if(findViewById(R.id.cpkll1).getVisibility()==View.VISIBLE){
			findViewById(R.id.cpkll1).setVisibility(View.GONE);
		}else {
			findViewById(R.id.cpkll1).setVisibility(View.VISIBLE);
		}
	}
}
