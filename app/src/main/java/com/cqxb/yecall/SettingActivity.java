package com.cqxb.yecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cqxb.yecall.until.T;

public class SettingActivity extends BaseTitleActivity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setTitle("设置");
		findViewById(R.id.newInfo).setOnClickListener(this);
		findViewById(R.id.callout).setOnClickListener(this);
		findViewById(R.id.callin).setOnClickListener(this);
		findViewById(R.id.callmove).setOnClickListener(this);
		findViewById(R.id.nativeNumber).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.newInfo){
			T.show(getApplicationContext(), "新消息提醒", 0);
			startActivity(new Intent(SettingActivity.this,NewInfoNoticeActivity.class));
		}else if(v.getId()==R.id.callout){
			T.show(getApplicationContext(), "呼出设置", 0);
			startActivity(new Intent(SettingActivity.this,CallOutSettingActivity.class));
		}else if(v.getId()==R.id.callin){
			T.show(getApplicationContext(), "呼入设置", 0);
//			startActivity(new Intent(SettingActivity.this,CallOutSettingActivity.class));
		}else if(v.getId()==R.id.callmove){
			T.show(getApplicationContext(), "来电转接", 0);
//			startActivity(new Intent(SettingActivity.this,CallOutSettingActivity.class));
		}else if(v.getId()==R.id.nativeNumber){
			T.show(getApplicationContext(), "本地区号设置", 0);
//			startActivity(new Intent(SettingActivity.this,CallOutSettingActivity.class));
		}
		
	}
}
