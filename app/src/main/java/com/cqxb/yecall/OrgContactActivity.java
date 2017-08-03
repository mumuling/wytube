package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.List;

import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.ui.AddressText;

import com.alibaba.fastjson.JSON;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.TreeNodeBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrgContactActivity extends BaseTitleActivity implements OnClickListener{
	private List<TreeNodeBean> arrayList=new ArrayList<TreeNodeBean>();
	private TextView phoneNumber,sipAccount;
	private String name;
	private String imAccount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orgcontact);
		SettingInfo.init(getApplicationContext());
		String orgInfo = getIntent().getStringExtra("orgInfo");
		String[] split = orgInfo.split("=");
		name=split[0];
		setTitle(split[0]+"(在线)");
		phoneNumber=(TextView)findViewById(R.id.phoneNumber);
		sipAccount=(TextView)findViewById(R.id.sipAccount);
		
		Log.e("", "===========info :"+split[1]);
		arrayList=JSON.parseArray(split[1], TreeNodeBean.class);
		
		for(TreeNodeBean tree:arrayList){
			if("im".equals(tree.getType())){
				imAccount=tree.getAccount()+"@"+SettingInfo.getParams(PreferenceBean.USERIMDOMAIN, "");
			}else if("sip".equals(tree.getType())){
				if("".equals(BaseUntil.strNotNull(tree.getAccount()))){
					sipAccount.setText("");
					
				}else {
					sipAccount.setText(tree.getAccount());
				}
				
			}else if("phone".equals(tree.getType())){
				
				if("".equals(BaseUntil.strNotNull(tree.getPhone()))){
					phoneNumber.setText("");
				}else {
					phoneNumber.setText(tree.getPhone());
				}
			}
		}
		
		findViewById(R.id.com_call).setOnClickListener(this);
		findViewById(R.id.com_msg).setOnClickListener(this);
		findViewById(R.id.sel_call).setOnClickListener(this);
		findViewById(R.id.sel_msg).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		AddressType address = new AddressText(this, null);
		if(v.getId()==R.id.com_call){
			if("".equals(sipAccount.getText().toString())){
				T.show(getApplicationContext(), "该用户还未激活", 0);
				return;
			}
			//企业号只允许直播
			SettingInfo.setParams(PreferenceBean.CALLNAME, name);
			SettingInfo.setParams(PreferenceBean.CALLPHONE, sipAccount.getText().toString());
			SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
			address.setDisplayedName(name);
			address.setText(sipAccount.getText().toString());
			if (LinphoneManager.getLc().getCallsNb() == 0) {
				LinphoneManager.getInstance().newOutgoingCall(address);
			}
			
		}else if(v.getId()==R.id.com_msg){
			if("".equals(sipAccount.getText().toString())){
				T.show(getApplicationContext(), "该用户还未激活", 0);
				return;
			}
//			T.show(getApplicationContext(), "该功能暂未开通", 0);
			//企业号只允许IM聊天
			Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
			intent.putExtra("jid", imAccount);
			intent.putExtra("nickName", name);
			startActivity(intent);
		}else if(v.getId()==R.id.sel_call){
			if("".equals(phoneNumber.getText().toString())){
				T.show(getApplicationContext(), "该用户还未激活", 0);
				return;
			}
			//直播
			SettingInfo.setParams(PreferenceBean.CALLNAME, name);
			SettingInfo.setParams(PreferenceBean.CALLPHONE, phoneNumber.getText().toString());
			SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
			address.setDisplayedName(name);
			address.setText(phoneNumber.getText().toString());
			if (LinphoneManager.getLc().getCallsNb() == 0) {
				LinphoneManager.getInstance().newOutgoingCall(address);
			}
			//回拨
		}else if(v.getId()==R.id.sel_msg){
			if("".equals(phoneNumber.getText().toString())){
				T.show(getApplicationContext(), "该用户还未激活", 0);
				return;
			}
			T.show(getApplicationContext(), "该功能暂未开通", 0);
			//短信
		}
	}
	
	
	
}
