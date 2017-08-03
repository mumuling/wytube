package com.wytube.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseTCOK;
import com.wytube.beans.InitBean;
import com.wytube.beans.SelectBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.MD5;
import com.wytube.utlis.Utils;

import static com.wytube.net.Json.toObject;
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
        /*从月卡续期成功返回刷新*/
        if (AppValue.fish == 1){
            selectData(AppValue.carNum);
            AppValue.fish = -1;
        }
    }

    /**
     * 初始化视图数据
     */
    private void initView() {
        mLocationText.setText("选择停车场");
        Intent intent = getIntent();
        mCarNum.setText(intent.getStringExtra("chepai"));
    }

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
        initLink(this.carNum);
    }

    /**
     * 初始化连接
     */
    private void initLink(String carNum) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.INIT_LOGIN, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            InitBean bean = toObject(json, InitBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.getStatetype() != 1) {
                Utils.showOkDialog(this, bean.getTipmsg());
                return false;
            }
            initBean = bean;
            selectData(carNum);
            AppValue.skey = bean.getUserLoginInfo().getTemp_key();
            return false;
        }));
    }

    /**
     * 查询数据
     *
     * @param carNum 车牌号
     */
    @SuppressLint("SetTextI18n")
    private void selectData(String carNum) {
        Utils.showLoad(this);
        String time = Utils.toDate(System.currentTimeMillis()).replace(" ", "");
        String keyValue = "trans_date=" + time +
                "&car_no=" + carNum +
                "&skey=" + initBean.getUserLoginInfo().getTemp_key() +
                "&jftype=1" +
                "&parkno=" + parkId +
                "&dvalidate=" + MD5.getParamMD5(time, carNum, "1", initBean.getUserLoginInfo().getTemp_key(), parkId);
        Client.sendPost(NetParmet.SELECT_STOP_MONEY, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            SelectBean bean = toObject(json, SelectBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            orderNum = bean.getOrderFormNo();
            AppValue.Money = bean.getUserTransInfo().getSfmoney() / 100 + "";
            String str = bean.getUserTransInfo().getU_cardtype();

            if (str!=null && !str.equals("")){
                str = str.substring(0,2); // or  str=str.Remove(i,str.Length-i);
                if (str.equals("临时")) {
                    mMoneyText.setText(bean.getUserTransInfo().getSfmoney() / 100 + "");
//                    mMoneyText.setText("0.01");
                    mGoStopTime.setText(bean.getUserTransInfo().getCometime());
                    mStopAllTime.setText(bean.getUserTransInfo().getStoptimes() + "分钟");
                    mTempStop.setVisibility(View.VISIBLE);
                } else if(str.equals("月卡")){
                    mMothDay.setText(bean.getUserTransInfo().getStoptimes());
                    mMonthStop.setVisibility(View.VISIBLE);
                }
            }else {
                Utils.showOkDialog(this, bean.getTipmsg());
            }
            return false;
        }));
    }

    /*已完成收费*/
    @KListener(R.id.ali_pay)
    private void ali_payOnClick() {
        callBack();
    }

    /*月卡延期*/
    @KListener(R.id.to_pay)
    private void to_payOnClick() {
        startActivity(new Intent(this, MonthMoneyActivity.class));
    }

    /*选择停车场*/
    @KListener(R.id.select_stop_location)
    private void select_stop_locationOnClick() {
        startActivity(new Intent(this, LocationListActivity.class));
    }



//    /**
//     * 获取订单号
//     * carNum	//车牌号
//     * parkId	//车场号
//     */
//    private void getOrderNum() {
//        String keyValue = "carNum=" + AppValue.carNum +"&parkId=" + parkId +"&payType="+"ALIPAY" +"&isno="+ AppValue.orderNum;
//        Client.sendPost(NetParmet.CREATE_ORDER_NUM, keyValue, new Handler(msg -> {
//            Utils.exitLoad();
//            String json = msg.getData().getString("post");
//            OrderBean bean = Json.toObject(json, OrderBean.class);
//            if (bean == null) {
//                Utils.showNetErrorDialog(this);
//                return false;
//            }
//            if (!bean.isSuccess()) {
//                Utils.showOkDialog(this, bean.getMessage());
//                return false;
//            }
//            this.orderNums = bean.getData();
//            getalipay();
//            return false;
//        }));
//    }



//    /**
//     * 验签
//     * orderNum     ,系统生成的订单号
//     * total_fee    ,支付金额
//     * isno         ,IIS服务器订单号
//     * subject      ,标题
//     */
//    private void getalipay() {
//        Client.sendPost(NetParmet.CREATE_ORDER_ALIPAY, "orderNum="+orderNums  +"&total_fee="+AppValue.Money
//                        +"&isno="+ orderNum  +"&subject="+"停车缴费", new Handler(msg -> {
//            Utils.exitLoad();
//            String json = msg.getData().getString("post");
//            OrderBean bean = Json.toObject(json, OrderBean.class);
//            if (bean == null) {
//                Utils.showNetErrorDialog(this);
//                return false;
//            }
//            if (!bean.isSuccess()) {
//                Utils.showOkDialog(this, bean.getMessage());
//                return false;
//            }
//            pay(bean.getData());
//            return false;
//        }));
//    }
//
//    /**
//     * 支付方法
//     */
//    private void pay(String orderNum) {
//        Runnable payRunnable = () -> {
//            PayTask alipay = new PayTask(SelectCarsActivity.this);
//            Map<String, String> result = alipay.payV2(orderNum, true);
//            Message msg = new Message();
//            msg.what =SDK_PAY_FLAG;
//            msg.obj = result;
//            mHandler.sendMessage(msg);
//        };
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//
//    private static final int SDK_PAY_FLAG = 1;
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        sure(orderNums);
//                        callBack();
////                        Toast.makeText(SelectCarsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(SelectCarsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };
//
//    /**
//     * 验证是否支付成功
//     * order     订单号
//     */
//    private void sure(String orderNum) {
//        Client.sendGet(NetParmet.PAY_SURE, "order=" + orderNum, new Handler(msg -> {
//            String json = msg.getData().getString("get");
//            SureBean bean = Json.toObject(json, SureBean.class);
//            if (bean == null) {
//                Utils.showNetErrorDialog(this);
//                return false;
//            }
//            if (!bean.isSuccess()) {
//                Utils.showOkDialog(this, bean.getMessage());
//                return false;
//            }
//            if (bean.getMessage().equals("OK")) {
//                isSure = true;
//                mTempStop.setVisibility(View.GONE);
//                Utils.showOkDialog(SelectCarsActivity.this, "缴费成功!");
//            }
//            return false;
//        }));
//    }
//
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
                selectData(AppValue.carNum);
                Utils.showOkDialog(SelectCarsActivity.this, "缴费成功!");
                return false;
            }
            return false;
        }));
    }
}
