package com.cqxb.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.zhifubao.BaseAllAlipay;
public class DialogNativeCade extends Dialog {

	private EditText editTextPassword;
	private String bottonOk;
	private String bottonCancel;
	private EditText editTextCardNo;
	
	/**
	 * 
	 * @param context 
	 * @param act  Activity
	 * @param bottonOk  确认按钮文本
	 * @param bottonCancel  取消按钮文本
	 */
	public DialogNativeCade(Activity act,String bottonOk,String bottonCancel) {
		super(act, R.style.AppDialog);
		this.bottonOk=bottonOk;
		this.bottonCancel=bottonCancel;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_native);
		SettingInfo.init(getContext());
		editTextCardNo=(EditText) findViewById(R.id.editTextCardNo);//输入手机账号
		editTextCardNo.setVisibility(View.VISIBLE);
		
		String params = SettingInfo.getParams(PreferenceBean.CALLPROYX, "");
		if(!"".equals(params)){
			editTextCardNo.setText(""+params);
		}
		
		Button buttonCancel=(Button)findViewById(R.id.buttonCancel);
		Button buttonOK=(Button)findViewById(R.id.buttonOK);
		
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogNativeCade.this.dismiss();
			}
		});
		
		buttonCancel.setText(bottonCancel);
		
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if("".equals(editTextCardNo.getText().toString())){
					T.show(getContext(), "请设置区号！", 0);
					return;
				}
				SettingInfo.setParams(PreferenceBean.CALLPROYX, editTextCardNo.getText().toString());
				dismiss();
			}
		});
		buttonOK.setText(bottonOk);
	}

	public void setOKBtn(String name, View.OnClickListener listener) {
		((Button) findViewById(R.id.buttonOK)).setText(name);
		((Button) findViewById(R.id.buttonOK)).setOnClickListener(listener);
	}

	public void setCancelBtn(String name, View.OnClickListener listener) {
		((Button) findViewById(R.id.buttonCancel)).setText(name);
		((Button) findViewById(R.id.buttonCancel)).setOnClickListener(listener);
	}
	
	public String getEditText() {
		return editTextPassword.getText().toString();
	}

}
