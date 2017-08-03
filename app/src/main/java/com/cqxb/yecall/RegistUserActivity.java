package com.cqxb.yecall;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;
import org.linphone.mediastream.Log;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.android.action.param.RegisteredRequest;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.T;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegistUserActivity extends BaseActivity {

	private LinearLayout layout1, layout2;
	private EditText editText1, editText2, editText3, editText4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_user);

		// 初始化参数
		editText1 = (EditText) findViewById(R.id.editTextPhone);
		editText2 = (EditText) findViewById(R.id.company_yu);
		editText3 = (EditText) findViewById(R.id.editTextPwd);
		editText4 = (EditText) findViewById(R.id.editTextpwd2);

		layout1 = (LinearLayout) findViewById(R.id.formRegist_layout);
		layout2 = (LinearLayout) findViewById(R.id.formRegist_layout1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void regsitOk(View view) {
		System.out.println("注册开始！！！");
		if (editText1.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入账号", 1);
			return;
		}
		if (editText3.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入密码", 1);
			return;
		}
		if (editText4.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请重复密码", 1);
			return;
		}
		if (editText3.getText().toString().length() < 6) {
			T.show(getApplicationContext(), "密码至少6位", 1);
			return;
		}
		if (!editText3.getText().toString()
				.equals(editText4.getText().toString())) {
			T.show(getApplicationContext(), "两次输入的密码不一致", 1);
			return;
		}

		RegisteredRequest regist = new RegisteredRequest();
		regist.setAccount(editText1.getText().toString());
		regist.setPassword(editText3.getText().toString());
		regist.setVersion(YETApplication.appClass);
		regist.setAuthcode("7758");
		handler.sendEmptyMessage(1);
		new NetAction().setRegistered(regist, new OnMyResponseListener() {
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if (CommReply.SUCCESS.equals(parseObject
							.getString("statuscode"))) {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
						finish();
					} else {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
					}
				} else {
					T.show(getApplicationContext(), getString(R.string.service_error), 1);
					handler.sendEmptyMessage(2);
				}
			}
		}).execm();

	}

	// 接受消息控制 显示不同的页面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
			} else if (msg.what == 2) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
			}
		};
	};
}
