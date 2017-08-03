package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.action.param.NetParam;
import com.cqxb.until.ACache;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.YeCallListPasswordAdapters;
import com.wytube.beans.BaseMmkm;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.utlis.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/2.
 * 类 描 述: 密码开门
 */

@KActivity(R.layout.activity_onekey)
public class PassWordActivity extends Activity{

    @KBind(R.id.text_name)
    private TextView mtext_name;
    @KBind(R.id.yecall_listview_item)
    private ListView itemListView;

    private String phone;// 用户名
    private List<BaseMmkm.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    private void iniview() {
        mtext_name.setText("密码开门");
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        mtext_name.setOnClickListener(v -> {finish();});
        phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        initPasswordYeCall();/*密码开门*/
    }


    /**
     * 初始化密码开门
     */
    private void initPasswordYeCall() {
        Utils.showLoad(this);
        loadAllMm(phone,"2222");
    }

    /**
     * 初始化密码开门
     */
    private void loadAllMm(String phone,String type) {
        Client.sendPost(NetParam.USR_DRIVERS, "phone="+phone +"&type="+type, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseMmkm bean = Json.toObject(json, BaseMmkm.class);
            list = bean.getData();
            if (!list.equals("[]")){
                isAnotherDay();
                YeCallListPasswordAdapters passwordAdapter = new YeCallListPasswordAdapters(PassWordActivity.this, list);
                itemListView.setAdapter(passwordAdapter);
                itemListView.setVisibility(View.VISIBLE);
            }else {
                itemListView.setVisibility(View.GONE);
            }
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            return false;
        }));
    }

    /**
     * 判断是否是同一天，用于密码开门中是否开启密码动画
     */
    private void isAnotherDay() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String str = format.format(date);// 获取的当前时间
        String day = SettingInfo.getParams(SettingInfo.KEY_DATE, "default");// APP储存的时间
        if (!day.equals(str)) {
            SettingInfo.setParams(SettingInfo.KEY_DATE, str);
            for (int i = 0; i < list.size(); i++) {
//				Log.w("bug", "循环也走进来了！！！");
                ACache aCache = ACache.get(PassWordActivity.this);
                aCache.put("@password" + phone + i, "");
            }
        }
    }
}


