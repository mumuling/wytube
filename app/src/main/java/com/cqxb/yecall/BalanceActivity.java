package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.T;

public class BalanceActivity extends BaseTitleActivity implements OnClickListener{

	private TextView account,time,date,money;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		setTitle("余额查询");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		account=(TextView)findViewById(R.id.account);
		time=(TextView)findViewById(R.id.time);
		date=(TextView)findViewById(R.id.date);
		money=(TextView)findViewById(R.id.money);
		
		findViewById(R.id.detail).setOnClickListener(this);
		findViewById(R.id.charge).setOnClickListener(this);
		
		dialog=ProgressDialog.show(BalanceActivity.this, "", "请稍候。。");
		dialog.show();
		new NetAction().getBalance(new OnMyResponseListener() {
			
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if(CommReply.SUCCESS.equals(parseObject.getString("statuscode"))){
						account.setText(parseObject.getString("account"));
						money.setText(parseObject.getString("amount"));
						time.setText(parseObject.getString("calltime"));
						date.setText(parseObject.getString("expireddate"));
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
	}


	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.detail){
			dialog=ProgressDialog.show(BalanceActivity.this, "", "请稍候。。");
			dialog.show();
			new NetAction().getTelephoneInfo(new OnMyResponseListener() {
				
				@Override
				public void onResponse(String jsonObject) {
					try {

						if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
							Log.e("", "  ====>>> "+jsonObject);
							//{"reason":"成功!","statuscode":"000000","fareInfo":"http:\/\/10.1.20.41:8080\/yecall\/ShowReport.wx?PAGEID=business_searchfare"}
							JSONObject parseObject = JSONObject.parseObject(jsonObject);
							if(CommReply.SUCCESS.equals(parseObject.getString("statuscode"))){
								handler.sendEmptyMessage(0);
								String path = parseObject.get("fareinfoweb").toString();
								Uri uri = Uri.parse(path);
								Intent intent = new Intent(Intent.ACTION_VIEW, uri);
								startActivity(intent);
							}else {
								T.show(getApplicationContext(), ""+parseObject.getString("reason"), 0);
								handler.sendEmptyMessage(0);
							}
						}else {
							T.show(getApplicationContext(), getString(R.string.service_error), 0);
							handler.sendEmptyMessage(0);
						}
					
					} catch (Exception e) {
						Log.e("", "获取话单失败："+e.getLocalizedMessage());
					}
					
				}
			}).execm();
		}else if(v.getId()==R.id.charge){
			startActivity(new Intent(BalanceActivity.this, ChargeActivity.class));
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
