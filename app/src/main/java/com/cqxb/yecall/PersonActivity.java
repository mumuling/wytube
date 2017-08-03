package com.cqxb.yecall;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.EmployeeBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonActivity extends BaseTitleActivity implements
		OnClickListener {

	private ProgressDialog dialog;
	private TextView nickName,sex,account,phone,address,self;
	private ImageView head,erweima;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		setTitle("个人信息");
		findViewById(R.id.personHead).setOnClickListener(this);
		findViewById(R.id.personNick).setOnClickListener(this);
		findViewById(R.id.personSex).setOnClickListener(this);
		findViewById(R.id.personAccount).setOnClickListener(this);
		findViewById(R.id.personPhone).setOnClickListener(this);
		findViewById(R.id.personErweima).setOnClickListener(this);
		findViewById(R.id.personAddress).setOnClickListener(this);
		findViewById(R.id.personSelf).setOnClickListener(this);
		
		nickName=((TextView)findViewById(R.id.nickName));
		sex=((TextView)findViewById(R.id.sex));
		account=((TextView)findViewById(R.id.account));
		phone=((TextView)findViewById(R.id.phone));
		address=((TextView)findViewById(R.id.address));
		self=((TextView)findViewById(R.id.self));
		
		head=((ImageView)findViewById(R.id.head));
		erweima=((ImageView)findViewById(R.id.erweima));
		
		dialog=ProgressDialog.show(PersonActivity.this, "", "正在加载，请稍候...");
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		new NetAction().getSelfInfo(SettingInfo.getAccount(), new OnResponseListener() {
			
			@Override
			public void onResponse(String statusCode, CommReply reply) {
				if(CommReply.SUCCESS.equals(statusCode)){
					EmployeeBean parseObject = JSONObject.parseObject(reply.getBody(), EmployeeBean.class);
					nickName.setText(parseObject.getDisplay_name());
					sex.setText(("0".equals(parseObject.getSex())?"男":"女"));
					account.setText(SettingInfo.getAccount());
					phone.setText(parseObject.getMobile());
					address.setText("");
					self.setText("");
					dialog.dismiss();
				}else {
					T.show(getApplicationContext(), "获取信息失败！", 0);
					dialog.dismiss();
				}
			}
		}).exec();
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.personHead){
//			T.show(getApplicationContext(), "", 0);
		}else if(v.getId()==R.id.personNick){
//			T.show(getApplicationContext(), "", 0);
		}else if(v.getId()==R.id.personSex){
//			T.show(getApplicationContext(), "保密", 0);
		}else if(v.getId()==R.id.personAccount){
//			T.show(getApplicationContext(), "账号", 0);
		}else if(v.getId()==R.id.personPhone){
//			T.show(getApplicationContext(), "手机号", 0);
		}else if(v.getId()==R.id.personErweima){
//			T.show(getApplicationContext(), "二维码名片", 0);
		}else if(v.getId()==R.id.personAddress){
			T.show(getApplicationContext(), ""+address.getText().toString(), 0);
		}else if(v.getId()==R.id.personSelf){
			T.show(getApplicationContext(), ""+self.getText().toString(), 0);
		}
	}

}
