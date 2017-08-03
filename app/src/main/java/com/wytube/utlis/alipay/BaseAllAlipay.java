package com.wytube.utlis.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.wytube.utlis.AppValue;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseAllAlipay {
    private Activity activity;
    private Handler mHandler;
    private String orderInfo;
    public String TAG = "alipay-sdk";
    private static final int RQF_PAY = 1;

    private static final int RQF_LOGIN = 2;
    private String backurl = "";
    private String number = "";
    private String mMoney = "";
    private String productName = "";

    /**
     * @param act     activity 对象
     * @param handler Handler
     * @param name    商品名称
     * @param order   订单信息(订单号,默认null)
     * @param url     返回的url路劲
     * @param phone   充值的手机号
     * @param money   充值的金额
     */
    public BaseAllAlipay(Activity act, Handler handler, String name, String order, String url, String phone, String money) {
        activity = act;
        mHandler = handler;
        orderInfo = order;
        backurl = url;
        number = phone;
        mMoney = money;
        productName = name;
    }


    public void pay() {
        //支付
        zhifubao();
    }

    public void zhifubao() {
        try {
            String info = getZhiFuBaoInfo();

            String sign = Rsa.sign(info, Keys.PRIVATE);
            sign = URLEncoder.encode(sign);
            info += "&sign=\"" + sign + "\"&" + getSignType();
            Log.i("ExternalPartner", "start pay");
            final String orderInfo = info;
            new Thread() {
                public void run() {
                    AliPay alipay = new AliPay(activity, mHandler);
                    String result = alipay.pay(orderInfo);
                    Message msg = new Message();
                    msg.what = RQF_PAY;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            }.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(activity, "remote_call_failed",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private String getZhiFuBaoInfo() {
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        sb.append("partner=\"");
        sb.append(Keys.DEFAULT_PARTNER);
        sb.append("\"&out_trade_no=\"");
        if (this.orderInfo == null) {
            sb.append(getOutTradeNo());
        } else {
            sb.append(this.orderInfo);
        }
        sb.append("\"&subject=\"");
        sb.append(productName);
        sb.append("\"&body=\"");

        sb.append("account:" + AppValue.sipName + ",version:AAA,rechargeaccount:" + number + ",apptype:Android");
        sb.append("\"&total_fee=\"");
        sb.append(mMoney);
        sb.append("\"&notify_url=\"");

        // 网址需要做URL编码
        sb.append(URLEncoder.encode(AppValue.notifyUrl));
        sb.append("\"&service=\"mobile.securitypay.pay");
        sb.append("\"&_input_charset=\"UTF-8");
        sb.append("\"&return_url=\"");
        sb.append(URLEncoder.encode("http://m.alipay.com"));
        sb.append("\"&payment_type=\"1");
        sb.append("\"&seller_id=\"");
        sb.append(Keys.DEFAULT_SELLER);

        // 如果show_url值为空，可不传
        sb.append("\"&it_b_pay=\"1m");
        sb.append("\"");

        return new String(sb);
    }

    private String getOutTradeNo() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);
        java.util.Random r = new java.util.Random();
        key += r.nextInt();
        key = key.substring(0, 15);
        Log.d(TAG, "outTradeNo: " + key);
        return key;
    }

    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}
