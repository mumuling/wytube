package com.cqxb.yecall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.wytube.activity.OrderActivity;

import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import static android.content.Intent.ACTION_MAIN;

public class LoginActivity extends Activity{
	
	private Smack smack;
	private LinearLayout formlogin_layout1, liner2,liner3;
	private EditText account,password;
	private String TAG="LoginActivity";
	private LinearLayout loginPage,checkLoginPage,loginComplete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		smack=Smack.getInstance();
		smack.initContext(getApplicationContext());
		formlogin_layout1=(LinearLayout)findViewById(R.id.formlogin_layout1);
		liner2=(LinearLayout)findViewById(R.id.liner2);
		liner3=(LinearLayout)findViewById(R.id.liner3);
		account=(EditText)findViewById(R.id.ediphonenum);
		account.setText("zd@yuneasy.cn");
		password=(EditText)findViewById(R.id.edipwd);
		password.setText("123");
		//页面
		loginPage=(LinearLayout)findViewById(R.id.loginPage);
		
		
		
		YETApplication.instanceContext(getApplicationContext());
		SettingInfo.init(getApplicationContext());
		if(smack.isAuthenticated()){
			startActivity(new Intent(LoginActivity.this,OrderActivity.class));
			this.finish();
		}
	}
	
	public void checkLogin(View view){
		//
		formlogin_layout1.setVisibility(View.VISIBLE);
		liner2.setVisibility(View.GONE);
		liner3.setVisibility(View.GONE);
		smack.getConnection();
		if(smack==null)
		{
			handler.sendEmptyMessage(0);//
			return;
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean login = smack.login(account.getText().toString(), password.getText().toString(), "android");
				if(login){
					handler.sendEmptyMessage(1);
					SettingInfo.setParams(PreferenceBean.USERACCOUNT, account.getText().toString());
					SettingInfo.setParams(PreferenceBean.USERPWD, password.getText().toString());
//					SettingInfo.setParams(PreferenceBean.USERLINPHONEACCOUNT, "zhangding");
//					SettingInfo.setParams(PreferenceBean.USERLINPHONEPWD, "123456");
//					SettingInfo.setParams(PreferenceBean.USERLINPHONEACCOUNT, "825");
//					SettingInfo.setParams(PreferenceBean.USERLINPHONEPWD, "1234");
//					SettingInfo.setParams(PreferenceBean.CLIENT, "android");
				}else{
					handler.sendEmptyMessage(2);
				}
			}
		}).start();
	}
	
	public void registUser(View view){
		Intent intent=new Intent(this,RegistUserActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				T.showShort(getApplicationContext(), "登陆失败！");
				Log.i(TAG, "smack init faild");
				formlogin_layout1.setVisibility(View.GONE);
				liner2.setVisibility(View.VISIBLE);
				liner3.setVisibility(View.VISIBLE);
				break;
			case 1:	
				T.showShort(getApplicationContext(), "登陆成功！");
				formlogin_layout1.setVisibility(View.VISIBLE);
				liner2.setVisibility(View.GONE);
				liner3.setVisibility(View.GONE);
				Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
				break;
			case 2:	
				T.showShort(getApplicationContext(), "登陆失败！");
				Log.i(TAG, "xmpp init faild");
				formlogin_layout1.setVisibility(View.GONE);
				liner2.setVisibility(View.VISIBLE);
				liner3.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	};
	
	
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
					exit();
					YETApplication.getinstant().exit();
					dialog.dismiss(); 
					finish();
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
