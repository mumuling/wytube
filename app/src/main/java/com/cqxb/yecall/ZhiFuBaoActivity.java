package com.cqxb.yecall;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.zhifubao.BaseAllAlipay;
import com.cqxb.yecall.zhifubao.Result;

public class ZhiFuBaoActivity extends BaseTitleActivity implements OnClickListener {

    private EditText editTextCardNo, editTextPassword;
    private static final int RQF_PAY = 1;

    private static final int RQF_LOGIN = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhifubao);
        setTitle("支付宝充值");
        setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.charge).setOnClickListener(this);
        editTextCardNo = (EditText) findViewById(R.id.ediphonenum);//输入手机账号
        editTextCardNo.setVisibility(View.VISIBLE);
        editTextCardNo.setHint("请输入充值号码");
        editTextCardNo.setText(SettingInfo.getAccount());

        editTextPassword = (EditText) findViewById(R.id.edipwd);//输入金额
        editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextPassword.setVisibility(View.VISIBLE);
        editTextPassword.setHint("请输入充值金额");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.charge) {
            if (TextUtils.isEmpty(editTextCardNo.getText().toString())) {
                T.show(ZhiFuBaoActivity.this, "请输入充值号码", 0);
                return;
            }
            if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
                T.show(ZhiFuBaoActivity.this, "请输入充值金额", 0);
                return;
            }

            //T.show(getApplicationContext(), "五元一个月", 0);
            BaseAllAlipay baseAlipay = new BaseAllAlipay(ZhiFuBaoActivity.this, handler, "app手机充值", null, "/jsp/notify_url.jsp", editTextCardNo.getText().toString(), editTextPassword.getText().toString());
//			BaseAlipay baseAlipay = new BaseAlipay(activity, handler, null, "notify_caller.jsp", editTextPassword.getText().toString(), money,number);
            baseAlipay.pay();
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Result result = new Result((String) msg.obj);
            switch (msg.what) {
                case RQF_PAY:
                    //111111111111111result.resultStatus==操作成功(9000)
                    Log.i("1111111111111111111111", "111111111111111result.resultStatus==" + result.resultStatusReplace);
                    //Toast.makeText(CallerIdentificationActivity.this, result.resultStatusReplace+",已为您开通此服务。",
                    //Toast.LENGTH_SHORT).show();
//						Toast.makeText(ZhiFuBaoActivity.this, result.resultStatusReplace+"开通金额="+result.total_fee, Toast.LENGTH_LONG).show();
                    if (result.resultStatusReplace.indexOf("9000") != -1) {
                        finish();
                    }
                    break;
                case RQF_LOGIN: {
                    Log.i("1111111111111111111111", "11111111111111133333333333333333" + result.resultStatusReplace);
//						Toast.makeText(ZhiFuBaoActivity.this, result.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    };
}
