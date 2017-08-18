package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseJylb;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

@KActivity(R.layout.activity_trading_info)
public class TradingInfoActivity extends Activity {

    @KBind(R.id.shop_img)
    private ImageView mShopImg;
    @KBind(R.id.shop_name)
    private TextView mShopName;
    @KBind(R.id.shop_money)
    private TextView mShopMoney;
    @KBind(R.id.phone_num)
    private TextView mPhoneNum;
    @KBind(R.id.content_text)
    private WebView mContentText;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /**
     * 初始化视图
     */
    private void initView() {
        if (AppValue.tradingsBean == null) {
            return;
        }
        BaseJylb.DataBean bean = AppValue.tradingsBean;
        mShopName.setText(bean.getTitle());
        if (bean.getImgList() != null && bean.getImgList().size() > 0) {
            Utils.loadImage(mShopImg, bean.getImgList().get(0).getImgUrl());
        }else {
            mShopImg.setImageResource(R.drawable.jygl_tz);
        }
        mShopMoney.setText(bean.getPrice());
        mPhoneNum.setText(bean.getPhone());
        mContentText.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mContentText.loadData(bean.getDetail(), "text/html;charset=UTF-8", null);
        phone = bean.getPhone();
    }

    @KListener(R.id.call_but)
    private void call_butOnClick() {
        if (phone.length() <= 0) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
