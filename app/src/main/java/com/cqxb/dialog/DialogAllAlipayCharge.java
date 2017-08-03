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
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.zhifubao.BaseAllAlipay;
public class DialogAllAlipayCharge extends Dialog {

	private EditText editTextPassword;
	private Handler handler;
	private Activity activity;
	private String title;
	private String phone;
	private String bottonOk;
	private String bottonCancel;
	private String money;
	private String number;
	private String hint;
	private String name;
	private EditText editTextCardNo;
	
	/**
	 * 
	 * @param context 
	 * @param act  Activity
	 * @param title  dialog标题
	 * @param name  商品名称
	 * @param phone  充值的手机号 ===>> 可不填
	 * @param bottonOk  确认按钮文本
	 * @param bottonCancel  取消按钮文本
	 * @param money 套餐价格  ===>> 可不填
	 * @param number 套餐编号 ===>>  可不填
	 * @param hint 输入框提示  ===>> 可不填
	 */
	public DialogAllAlipayCharge(Activity act,Handler handler,String name,String title,String phone,String bottonOk,String bottonCancel,String money,String number,String hint) {
		super(act, R.style.AppDialog);
		activity=act;
		this.title=title;
		this.phone=phone;
		this.bottonOk=bottonOk;
		this.bottonCancel=bottonCancel;
		this.handler=handler;
		this.money=money;
		this.number=number;
		this.hint=hint;
		this.name=name;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_charge);
		SettingInfo.init(getContext());
		editTextCardNo=(EditText) findViewById(R.id.editTextCardNo);//输入手机账号
		editTextCardNo.setVisibility(View.VISIBLE);
		editTextCardNo.setHint("请输入充值号码");
		editTextCardNo.setText(SettingInfo.getAccount());
		
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);//输入金额
		editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
		editTextPassword.setVisibility(View.VISIBLE);
		editTextPassword.setHint("请输入充值金额");
		
		((TextView) findViewById(R.id.editTextParValue)).setVisibility(View.GONE);//下拉框显示值
		((Spinner) findViewById(R.id.Spinner01)).setVisibility(View.GONE);//下拉框
		
		TextView textViewCaption=(TextView) findViewById(R.id.textViewCaption);
		textViewCaption.setText(title);
		
		Button buttonCancel=(Button)findViewById(R.id.buttonCancel);
		Button buttonOK=(Button)findViewById(R.id.buttonOK);
		
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogAllAlipayCharge.this.dismiss();
			}
		});
		
		buttonCancel.setText(bottonCancel);
		
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(editTextCardNo.getText().toString())){
					T.show(activity, "请输入充值号码", 0);
					return;
				}
				if(TextUtils.isEmpty(editTextPassword.getText().toString())){
					T.show(activity, "请输入充值金额", 0);
					return;
				}
//				if(!BaseUntil.isDecimal(editTextPassword.getText().toString())){
//					T.show(activity, "请输入正确的金额", 0);
//					return;
//				}
				
				//T.show(getApplicationContext(), "五元一个月", 0);
				BaseAllAlipay baseAlipay=new BaseAllAlipay(activity, handler,name, null, "notify_caller.jsp", editTextCardNo.getText().toString(), editTextPassword.getText().toString());
//				BaseAlipay baseAlipay = new BaseAlipay(activity, handler, null, "notify_caller.jsp", editTextPassword.getText().toString(), money,number);
				baseAlipay.pay();
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
