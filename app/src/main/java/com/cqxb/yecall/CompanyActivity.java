package com.cqxb.yecall;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.CompanyBean;
import com.cqxb.yecall.bean.EmployeeBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CompanyActivity extends BaseTitleActivity implements OnClickListener{

	private ProgressDialog dialog;
	private TextView compName,address,balance,audit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_info);
		setTitle("公司信息");
		findViewById(R.id.companyName).setOnClickListener(this);
		findViewById(R.id.companyAddress).setOnClickListener(this);
		findViewById(R.id.remain).setOnClickListener(this);
		findViewById(R.id.realName).setOnClickListener(this);
		compName=((TextView)findViewById(R.id.compName));
		address=((TextView)findViewById(R.id.address));
		balance=((TextView)findViewById(R.id.balance));
		audit=((TextView)findViewById(R.id.audit));
		dialog=ProgressDialog.show(CompanyActivity.this, "", "正在加载，请稍候...");
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		new NetAction().getCompanyInfo(SettingInfo.getAccount(), new OnResponseListener() {
			
			@Override
			public void onResponse(String statusCode, CommReply reply) {
				if(CommReply.SUCCESS.equals(statusCode)){
					CompanyBean parseObject = JSONObject.parseObject(reply.getBody(), CompanyBean.class);
					compName.setText(parseObject.getCompany_name());
					address.setText(parseObject.getAddress());
					balance.setText(parseObject.getBalance());
					audit.setText(("0".equals(parseObject.getAduit())?"未认证":"已认证"));
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
		if(v.getId()==R.id.companyName){
			T.show(getApplicationContext(), ""+compName.getText().toString(), 0);
		}else if(v.getId()==R.id.companyAddress){
			T.show(getApplicationContext(), ""+address.getText().toString(), 0);
		}else if(v.getId()==R.id.remain){
			T.show(getApplicationContext(), ""+balance.getText().toString(), 0);
		}else if(v.getId()==R.id.realName){
			T.show(getApplicationContext(), ""+audit.getText().toString(), 0);
		}
	}
	
}
