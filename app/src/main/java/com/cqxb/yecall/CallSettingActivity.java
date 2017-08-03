package com.cqxb.yecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

public class CallSettingActivity extends BaseTitleActivity implements
		OnClickListener {

	private CheckBox outCallCheck,wifiCheck;//录音开关、拨号声音
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		setContentView(R.layout.activity_call_setting);
		setTitle("拨打设置");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.outCall).setOnClickListener(this);
		findViewById(R.id.numberSetting).setOnClickListener(this);
		
		
		
		outCallCheck=(CheckBox)findViewById(R.id.outCallCheck);
		wifiCheck=(CheckBox)findViewById(R.id.wifiCheck);
		String outCall = SettingInfo.getParams(PreferenceBean.OUTCALLCHECK, "");
		String wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
		if("".equals(outCall)){
			outCallCheck.setChecked(getResources().getBoolean(R.bool.g3g4_result));
			SettingInfo.setParams(PreferenceBean.OUTCALLCHECK, ""+getResources().getBoolean(R.bool.g3g4_result));
		}else {
			outCallCheck.setChecked(Boolean.parseBoolean(outCall));
		}
		
		if("".equals(wifi)){
			wifiCheck.setChecked(getResources().getBoolean(R.bool.wifi_result));
			SettingInfo.setParams(PreferenceBean.WIFICHECK, ""+getResources().getBoolean(R.bool.wifi_result));
		}else {
			wifiCheck.setChecked(Boolean.parseBoolean(wifi));
		}
		
		outCallCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingInfo.setParams(PreferenceBean.OUTCALLCHECK, ""+isChecked);
			}
		});
		
		wifiCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingInfo.setParams(PreferenceBean.WIFICHECK, ""+isChecked);
				
//				Context context = getApplicationContext();
//				AudioManager audioManager =  (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
//		        audioManager.setMode(AudioManager.STREAM_SYSTEM);
//		        if(isChecked){
//		            if(!audioManager.isSpeakerphoneOn()) {
//		                audioManager.setSpeakerphoneOn(true);
//		           }
//		        }else{
//		        	if(audioManager.isSpeakerphoneOn()) {
//		        		audioManager.setSpeakerphoneOn(false);
//		        	}
//		        }
			}
		});
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.outCall){
//			T.show(getApplicationContext(), "去电", 0);
		}else if(v.getId()==R.id.numberSetting){
			startActivity(new Intent(CallSettingActivity.this,SettingNativeActivity.class));
//			DialogNativeCade dialog=new DialogNativeCade(CallSettingActivity.this,  "确认", "取消");
//			dialog.show();
		}
	}

}
