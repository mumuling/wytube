package com.cqxb.yecall;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.ui.TglButton;
import com.cqxb.ui.TglButton.OnChangedListener;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class NewInfoNoticeActivity extends BaseTitleActivity implements OnClickListener{

	private TglButton playVoice,vibrate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_info_notice);
		setTitle("新消息提醒");
		playVoice=(TglButton)findViewById(R.id.playVoice);
		playVoice.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void onChange(TglButton button, long check) {
				if(check==1){
					SettingInfo.setParams(PreferenceBean.PLAYVOICE, "play");
				}else if(check==0){
					SettingInfo.setParams(PreferenceBean.PLAYVOICE, "");
				}
			}
		});
		vibrate=(TglButton)findViewById(R.id.vibrate);
		vibrate.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void onChange(TglButton button, long check) {
				if(check==1){
					SettingInfo.setParams(PreferenceBean.PLAYVIBRATE, "play");
				}else if(check==0){
					SettingInfo.setParams(PreferenceBean.PLAYVIBRATE, "");
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		
	}

}
