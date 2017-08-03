package com.cqxb.yecall;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SystemChargeActivity extends BaseTitleActivity implements
		OnClickListener {

	private EditText ediphonenum,edipwd;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		setContentView(R.layout.activity_system_charge);
		setTitle("充值卡充值");
		
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.charge).setOnClickListener(this);
		
		ediphonenum=(EditText)findViewById(R.id.ediphonenum);
		edipwd=(EditText)findViewById(R.id.edipwd);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.charge){
			if("".equals(ediphonenum.getText().toString())||"".equals(edipwd.getText().toString())){
				T.show(getApplicationContext(), "请输入卡号卡密！", 0);
				return;
			}
			dialog=ProgressDialog.show(SystemChargeActivity.this, "", "请稍候。。");
			dialog.show();
			new NetAction().systemRecharge(SettingInfo.getAccount(), ediphonenum.getText().toString(), edipwd.getText().toString(), new OnMyResponseListener() {
				@Override
				public void onResponse(String jsonObject) {
					if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
						JSONObject parseObject = JSONObject.parseObject(jsonObject);
						if(CommReply.SUCCESS.equals(parseObject.getString("statuscode"))){
							T.show(getApplicationContext(), ""+parseObject.getString("reason"), 0);
							handler.sendEmptyMessage(0);
						}else {
							T.show(getApplicationContext(), ""+parseObject.getString("reason"), 0);
							handler.sendEmptyMessage(0);
						}
					}else {
						T.show(getApplicationContext(), getString(R.string.service_error), 0);
						handler.sendEmptyMessage(0);
					}
				}
			}).execm();
			
//			T.show(getApplicationContext(), "充值卡", 0);
		}
	}
	
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==0){
				if(dialog!=null)dialog.dismiss();
			}
		}
		
	};

}
