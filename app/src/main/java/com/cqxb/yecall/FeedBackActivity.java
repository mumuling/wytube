package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.android.action.param.FeedBackRequest;
import com.android.action.param.FeedbackReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.AdvertisementBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBackActivity extends BaseTitleActivity{
	private EditText s;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		setTitle("帮助反馈");
		s=(EditText) findViewById(R.id.editTextContent);
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		button=(Button)findViewById(R.id.buttonSend);
		
		// androud2.2没有该方法
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if ((s.getText().toString().length() == 0)) {
					T.show(FeedBackActivity.this, "请填写您宝贵的意见！", 0);
					return;
				} 
				
				button.setClickable(false);
				button.setText("正在提交");
				String content = ((EditText) findViewById(R.id.editTextContent)).getText().toString();
				Log.d("", "suggest content:" + content);
				FeedBackRequest feed=new FeedBackRequest();
				
				feed.setFeedbackTitle(getTitle().toString());
				feed.setFeedbackContent(s.getText().toString());
				new NetAction().feedBack(feed, new OnMyResponseListener() {
					@Override
					public void onResponse(String jsonObject) {
						if(!"".equals(BaseUntil.stringNoNull(jsonObject))){
							JSONObject parseObject = JSONObject.parseObject(jsonObject.toString());
							if(CommReply.SUCCESS.equals(parseObject.get("statuscode"))){
								T.show(FeedBackActivity.this, ""+parseObject.get("reason"),0);
							}else {
								T.show(FeedBackActivity.this, ""+parseObject.get("reason"),0);
							}
						}else {
							T.show(FeedBackActivity.this, ""+getString(R.string.service_error),0);
						}
						button.setClickable(true);
						button.setText("提 交");
					}
				}).execm();
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
