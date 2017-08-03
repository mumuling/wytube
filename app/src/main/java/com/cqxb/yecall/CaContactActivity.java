package com.cqxb.yecall;

import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.ui.AddressText;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CaContactActivity extends BaseTitleActivity implements OnClickListener{
	private ImageView sel_call;
	private String name,callPhone;
	private TextView sel_phone,nickName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cacontact);
		name=getIntent().getStringExtra("name");
		callPhone=getIntent().getStringExtra("callPhone");
		setTitle(name);
		sel_call=(ImageView)findViewById(R.id.sel_call);
		sel_call.setOnClickListener(this);
		sel_phone=(TextView)findViewById(R.id.sel_phone);
		sel_phone.setText(callPhone);
		nickName=(TextView)findViewById(R.id.nickName);
		nickName.setText(name);
		
	}

	@Override
	public void onClick(View v) {
		AddressType address = new AddressText(this, null);
		if(v.getId()==R.id.sel_call){
			//企业号只允许直播
			SettingInfo.setParams(PreferenceBean.CALLNAME, name);
			SettingInfo.setParams(PreferenceBean.CALLPHONE, callPhone);
			SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
			address.setDisplayedName(name);
			address.setText(callPhone);
			if (LinphoneManager.getLc().getCallsNb() == 0) {
				LinphoneManager.getInstance().newOutgoingCall(address);
			}
		}
	}

}
