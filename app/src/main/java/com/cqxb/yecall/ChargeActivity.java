package com.cqxb.yecall;

import com.cqxb.dialog.DialogAllAlipayCharge;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.cqxb.yecall.zhifubao.Result;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ChargeActivity extends BaseTitleActivity implements OnClickListener{
	private static final int RQF_PAY = 1;

	private static final int RQF_LOGIN = 2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge);
		setTitle("话费充值");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.systemCharge).setOnClickListener(this);
		findViewById(R.id.zhifubao).setOnClickListener(this);
	}
	private DialogAllAlipayCharge dialog;
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.systemCharge){
			startActivity(new Intent(ChargeActivity.this, SystemChargeActivity.class));
		}else if(v.getId()==R.id.zhifubao){
			startActivity(new Intent(ChargeActivity.this, ZhiFuBaoActivity.class));
//			dialog=new DialogAllAlipayCharge(ChargeActivity.this, handler,"app手机充值", "支付宝充值", SettingInfo.getAccount(), "确认", "取消", "0.01", "1003", "请输入手机号码");
//			dialog.show();
		}
		
	}

	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Result result = new Result((String) msg.obj);
				switch (msg.what) {
					case RQF_PAY:
						//111111111111111result.resultStatus==操作成功(9000)
						Log.i("1111111111111111111111", "111111111111111result.resultStatus=="+result.resultStatusReplace);
						//Toast.makeText(CallerIdentificationActivity.this, result.resultStatusReplace+",已为您开通此服务。",
								//Toast.LENGTH_SHORT).show();
						Toast.makeText(ChargeActivity.this, result.resultStatusReplace+"开通金额="+result.total_fee,
								Toast.LENGTH_LONG).show();
						if(result.resultStatusReplace.indexOf("9000")!=-1){
							if(dialog!=null)dialog.dismiss();
						}
						break;
					case RQF_LOGIN: {
						Log.i("1111111111111111111111", "11111111111111133333333333333333"+result.resultStatusReplace);

						Toast.makeText(ChargeActivity.this, result.getResult(),
								Toast.LENGTH_SHORT).show();

					}
						break;
				}
		}
	};
}
