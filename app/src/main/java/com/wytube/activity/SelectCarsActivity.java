package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.wytube.beans.InitBean;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wytube.utlis.AppValue.orderNum;
import static com.wytube.utlis.AppValue.parkId;


/**
 * 查询车牌
 * */
@KActivity(R.layout.activity_select_cars)
public class SelectCarsActivity extends BaseActivity {
    String TAG = "SelectCarsActivity";
    @KBind(R.id.location_text)
    private TextView mLocationText;
    @KBind(R.id.car_num)
    private EditText mCarNum;
    @KBind(R.id.money_text)
    private TextView mMoneyText;
    @KBind(R.id.go_stop_time)
    private TextView mGoStopTime;
    @KBind(R.id.stop_all_time)
    private TextView mStopAllTime;
    @KBind(R.id.temp_stop)
    private LinearLayout mTempStop;
    @KBind(R.id.moth_day)
    private TextView mMothDay;
    @KBind(R.id.month_stop)
    private LinearLayout mMonthStop;
    @KBind(R.id.select_but)
    private CardView mselect_but;
    @KBind(R.id.to_pay)
    private CardView mto_pay;

    private InitBean initBean;
    private String carNum;
    /*判断是否支付成功*/
    private boolean isSure = false;
    private String orderNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitleText("停车缴费");
//        setBackCallBack(this);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.parkName != null && AppValue.parkName.length() > 0) {
            mLocationText.setText(AppValue.parkName);
        }
//        /*从月卡续期成功返回刷新*/
//        if (AppValue.fish == 1){
//            selectData(AppValue.carNum);
//            AppValue.fish = -1;
//        }
    }


    /**
     * 初始化视图数据
     */
    private void initView() {
        mCarNum.addTextChangedListener(textWatcher);
        mLocationText.setText("选择停车场");
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before,int count) {
            if (!mCarNum.getText().toString().equals("")){
                mselect_but.setCardBackgroundColor(getResources().getColor(R.color.app_main_color_green));
                mto_pay.setCardBackgroundColor(getResources().getColor(R.color.app_main_color_green));
            }else {
                mselect_but.setCardBackgroundColor(getResources().getColor(R.color.e8e8e8));
                mto_pay.setCardBackgroundColor(getResources().getColor(R.color.e8e8e8));
            }
        }
    };

    @KListener(R.id.select_but)
    private void select_butOnClick() {
        mMonthStop.setVisibility(View.GONE);
        mTempStop.setVisibility(View.GONE);
        if (parkId.length() <= 0) {
            Utils.showOkDialog(this, "请选择停车场!");
            return;
        }
        if (AppValue.carsBeans == null || AppValue.carsBeans.size() <= 0) {
            Utils.showOkDialog(this, "数据异常!");
            return;
        }
        this.carNum = mCarNum.getText().toString();
        if (this.carNum.length() <= 0) {
            Utils.showOkDialog(this, "请输入需要查询的车牌号!");
            return;
        }
        AppValue.carNum = carNum;
        initTCfy();
    }


    /**
     * 计算停车费用
     * carNum			//车牌号码
     * parkId			//车场ID
     */
    private void initTCfy() {
        Utils.showLoad(this);
        RequestParams params = new RequestParams();
        params.put("carNum", AppValue.carNum);
        params.put("parkId", AppValue.parkId);
        AsyncHttpClientManager.post(NetParmet.PAY_JSFY, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    try {
                        if (response.getString("message").equals("此卡类无需计费")){
                            mTempStop.setVisibility(View.GONE);
                            Utils.showOkDialog(SelectCarsActivity.this, response.getString("message"));
                            return;
                        }else if (response.getString("message").equals("此车尚未入场")){
                            mTempStop.setVisibility(View.GONE);
                            Utils.showOkDialog(SelectCarsActivity.this, response.getString("message"));
                            return;
                        }
                        AppValue.orderNum = response.getString("order");
                        AppValue.Money = Integer.parseInt(response.getString("money")) + "";
                        mMoneyText.setText(Integer.parseInt(response.getString("money")) / 100+"");
//                        mMoneyText.setText("0.01");
                        mGoStopTime.setText(response.getString("startTime"));
                        mStopAllTime.setText(response.getString("stopTime") + "分钟");
                        mTempStop.setVisibility(View.VISIBLE);
                    } catch (JSONException e){
                        try {
                            Utils.showOkDialog(SelectCarsActivity.this, response.getString("message"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(SelectCarsActivity.this, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Utils.exitLoad();
                super.onFinish();
            }
        });
    }

    /*已完成收费*/
    @KListener(R.id.ali_pay)
    private void ali_payOnClick() {
        callBack();
    }

    /*月卡延期*/
    @KListener(R.id.to_pay)
    private void to_payOnClick() {
        if (parkId.length() <= 0) {
            Utils.showOkDialog(this, "请选择停车场!");
            return;
        }
        this.carNum = mCarNum.getText().toString();
        if (this.carNum.length() <= 0) {
            Utils.showOkDialog(this, "请输入续费的车牌号!");
            return;
        }
        AppValue.parkphone= mCarNum.getText().toString();
        startActivity(new Intent(this, MonthMoneyActivity.class));
    }

    /*选择停车场*/
    @KListener(R.id.select_stop_location)
    private void select_stop_locationOnClick() {
        startActivity(new Intent(this, LocationListActivity.class));
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
                        Utils.showOkDialog(SelectCarsActivity.this, response.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Utils.showOkDialog(SelectCarsActivity.this, response.getString("tipmsg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(SelectCarsActivity.this, "请检查您的网络！", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Utils.exitLoad();
                super.onFinish();
            }
        });
    }
}
