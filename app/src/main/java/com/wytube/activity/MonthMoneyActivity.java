package com.wytube.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.until.AsyncHttpClientManager;
import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wytube.utlis.AppValue.orderNum;


@KActivity(R.layout.activity_month_money)
public class MonthMoneyActivity extends BaseActivity {

    @KBind(R.id.month_num)
    private EditText mMonthNum;
    @KBind(R.id.money_text)
    private TextView mMoneyText;
    @KBind(R.id.temp_stop)
    private LinearLayout mTempStop;
    private boolean isSure = false;
    private String orderNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /**
     * 月卡延期
     * carNum			//车牌号码
     * months			//延期月数
     * parkId			//车场ID
     */
    private void loadData() {
        Utils.showLoad(this);
        RequestParams params = new RequestParams();
        params.put("carNum", AppValue.parkphone);
        params.put("months", mMonthNum.getText().toString());
        params.put("parkId", AppValue.parkId);
        AsyncHttpClientManager.post(NetParmet.PAY_YKYQ, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    try {
                        AppValue.orderNum = response.getString("order");
                        AppValue.Money = Integer.parseInt(response.getString("fee"))+"";
                        mMoneyText.setText(AppValue.Money);
//                        mMoneyText.setText("0.01");
                        mTempStop.setVisibility(View.VISIBLE);
                    } catch (JSONException e){
                        try {
                            Utils.showOkDialog(MonthMoneyActivity.this, response.getString("message"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MonthMoneyActivity.this, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Utils.exitLoad();
                super.onFinish();
            }
        });
    }

    /**
     * 月卡延期确定
     * */
    @KListener(R.id.ok_but)
    private void ok_butOnClick() {
        String month = mMonthNum.getText().toString();
        if (month.length() <= 0) {
            Utils.showOkDialog(this, "请输入续费月数!");
            return;
        }
        loadData();
    }

    @KListener(R.id.ali_pay)
    private void ali_payOnClick() {
        callBack();
    }


    /**
     * strorderno		//IIS服务器订单号
     * sfmoney			//缴费金额
     * paytype			//支付类型			0=现金1=微信 2=支付宝 3=银联 4=余额
     * order			//park服务器订单     (现金支付时,非必须)
     * parkId			//车场ID
     */
    private void callBack() {
        Utils.showLoad(this);
        RequestParams params = new RequestParams();
        params.put("strorderno", orderNum);
        params.put("sfmoney", AppValue.Money);
        params.put("paytype", "2");
        params.put("order", orderNums);
        params.put("parkId",AppValue.parkId);
        AsyncHttpClientManager.post(NetParmet.PAY_SFHD, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    mTempStop.setVisibility(View.GONE);
                    try {
                        Utils.showOkDialog(MonthMoneyActivity.this, response.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Utils.showOkDialog(MonthMoneyActivity.this, response.getString("tipmsg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(MonthMoneyActivity.this, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Utils.exitLoad();
                super.onFinish();
            }
        });
    }
}
