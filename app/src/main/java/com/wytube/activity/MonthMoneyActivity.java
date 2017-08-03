package com.wytube.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseTCOK;
import com.wytube.beans.MonthBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.MD5;
import com.wytube.utlis.Utils;

import static com.wytube.utlis.AppValue.orderNum;
import static com.wytube.utlis.AppValue.parkId;


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
//        setBackCallBack(this);
//        setTitleText("月卡续费");
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /**
     * 加载数据
     */
    @SuppressLint("SetTextI18n")
    private void loadData() {
        Utils.showLoad(this);
        String time = Utils.toDate(System.currentTimeMillis()).replace(" ", "");
        String keyValue = "trans_date=" + time +
                "&car_no=" + AppValue.carNum +
                "&months=" + mMonthNum.getText().toString() +
                "&jftype=1" +
                "&skey=" + AppValue.skey +
                "&dvalidate=" + MD5.getParamMD5(time, AppValue.carNum, mMonthNum.getText().toString(), "1", AppValue.skey);
        Client.sendPost(NetParmet.MONTH_MONEY_SELECT, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            MonthBean bean = Json.toObject(json, MonthBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            orderNum = bean.getOrderFormNo();
            AppValue.Money = bean.getSfmoney() / 100 + "";
//            mMoneyText.setText("0.01");
            mMoneyText.setText(bean.getSfmoney() / 100 + "");
            mTempStop.setVisibility(View.VISIBLE);
            return false;
        }));
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
     * 回调支付成功
     */
    private void callBack() {
        String time = Utils.toDate(System.currentTimeMillis()).replace(" ", "");
        String keyValue = "trans_date=" + time +
                "&strorderno=" + orderNum +
                "&sfmoney=" + mMoneyText.getText().toString() +
                "&skey=" + AppValue.skey +
                "&parkno=" + parkId +
                "&dvalidate=" + MD5.getParamMD5(time, orderNum, mMoneyText.getText().toString(), AppValue.skey, parkId);
        Client.sendPost(NetParmet.PAY_CALL_BACK, keyValue, new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseTCOK bean = Json.toObject(json, BaseTCOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.getTipmsg().equals("收费成功")) {
                AppValue.fish = 1;
                Toast.makeText(this, "续费成功", Toast.LENGTH_SHORT).show();
                this.finish();
                return false;
            }
            return false;
        }));
    }
}
