package com.cqxb.yecall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.EmployeeBean;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreActivity extends BaseTitleActivity implements OnClickListener{

	private LinearLayout personInfo,companyInfo,setting,questionBack,gprsSearch,about;
	private TextView userName,userAccount,companyName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		String params = SettingInfo.getParams(PreferenceBean.SELF, "");
		EmployeeBean emp = JSON.parseObject(params, EmployeeBean.class);
		setContentView(R.layout.activity_more);
		setTitle("更多");
		personInfo=(LinearLayout)findViewById(R.id.personInfo);
		personInfo.setOnClickListener(this);
		
		companyInfo=(LinearLayout)findViewById(R.id.companyInfo);
		companyInfo.setOnClickListener(this);
		
		setting=(LinearLayout)findViewById(R.id.setting);
		setting.setOnClickListener(this);
		
		questionBack=(LinearLayout)findViewById(R.id.questionBack);
		questionBack.setOnClickListener(this);
		
		gprsSearch=(LinearLayout)findViewById(R.id.gprsSearch);
		gprsSearch.setOnClickListener(this);
		
		about=(LinearLayout)findViewById(R.id.about);
		about.setOnClickListener(this);
		
		userName=(TextView)findViewById(R.id.userName);
		userAccount=(TextView)findViewById(R.id.userAccount);
		companyName=(TextView)findViewById(R.id.companyName);
		userName.setText(emp.getName());
		userAccount.setText("账号："+SettingInfo.getAccount());
		companyName.setText(SettingInfo.getParams(PreferenceBean.COMPANYNAME, ""));
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.personInfo){
			//个人信息
			Intent person=new Intent(MoreActivity.this,PersonActivity.class);
			startActivity(person);
		}else if(v.getId()==R.id.companyInfo){
			//公司信息
			Intent company=new Intent(MoreActivity.this,CompanyActivity.class);
			startActivity(company);
		}else if(v.getId()==R.id.setting){
			//设置
			Intent setting=new Intent(MoreActivity.this,SettingActivity.class);
			startActivity(setting);
		}else if(v.getId()==R.id.questionBack){
			//问题反馈
			T.show(getApplicationContext(), "问题反馈", 0);
		}else if(v.getId()==R.id.gprsSearch){
			//设置
			T.show(getApplicationContext(), "设置", 0);
		}else if(v.getId()==R.id.about){
			//关于云翌通
			T.show(getApplicationContext(), "关于云翌通", 0);
		}
	}
	
	private ProgressDialog progressDlg;
	
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
					progressDlg=ProgressDialog.show(MoreActivity.this, null, "正在退出。。。");
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
	
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==123654){
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
	
}
