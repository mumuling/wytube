package com.cqxb.yecall;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.T;
import com.cqxb.ui.TglButton;
import com.cqxb.ui.TglButton.OnChangedListener;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class CallOutSettingActivity extends BaseTitleActivity{

	private TglButton button;
	private ToggleButton toggleBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callout_setting);
		setTitle("呼出设置");
		button=(TglButton)findViewById(R.id.toggleButton);
		toggleBtn=(ToggleButton)findViewById(R.id.toggleBtn);
		button.setOnChangedListener(new OnChangedListener() {
			
			@Override
			public void onChange(TglButton button, long check) {
				T.show(getApplicationContext(), (check==0?"不允许直播：因为是收到设置的":"可以主人允许你开启"), 0);
			}
		});
		
		toggleBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				T.show(getApplicationContext(), (isChecked?"选中了":"没选中"), 0);
			}
		});
	}

}
