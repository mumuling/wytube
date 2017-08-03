package com.cqxb.yecall;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SettingNativeActivity extends BaseTitleActivity implements OnClickListener{

	private EditText ediphonenum;
	String diss="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		diss=BaseUntil.stringNoNull(getIntent().getStringExtra("diss"));
		setContentView(R.layout.activity_setnative);
		setTitle("开门密码设置");
		SettingInfo.init(SettingNativeActivity.this);
		ediphonenum=(EditText) findViewById(R.id.ediphonenum);//输入手机账号
		ediphonenum.setVisibility(View.VISIBLE);
		
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		String params = SettingInfo.getParams(PreferenceBean.CALLPROYX, "");
		if(!"".equals(params)){
			ediphonenum.setText(""+params);
		}
		findViewById(R.id.charge).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.charge){
//			if("".equals(ediphonenum.getText().toString())){
//				T.show(SettingNativeActivity.this, "请设置开门密码！", 0);
//				return;
//			}
			SettingInfo.setParams(PreferenceBean.CALLPROYX, ediphonenum.getText().toString());
			if("".equals(diss)){
				
			}else {
				finish();
			}
			T.show(SettingNativeActivity.this, "设置成功！", 0);
		}
	}
	
}
