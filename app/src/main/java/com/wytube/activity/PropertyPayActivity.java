package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BiilAdapters;
import com.wytube.beans.BiilBeaan;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 物业缴费
 * */
@KActivity(R.layout.activity_wyjfs)
public class PropertyPayActivity extends BaseActivity {
    private List<BiilBeaan.DataBean> list;

    @KBind(R.id.all_zd)
    private LinearLayout mAllZd;
    @KBind(R.id.wj_zd)
    private LinearLayout mWjZd;
    @KBind(R.id.zd_list)
    private ListView mZdList;
    @KBind(R.id.key_words)
    private EditText mKeyWords;
    @KBind(R.id.sreatch_layout)
    private LinearLayout mSreatchLayout;

    private List<BiilBeaan.DataBean> allBean;
    private List<BiilBeaan.DataBean> notBean;
    private BiilAdapters adapter;

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
        loadList();
    }

    /**
     * 加载账单
     */
    private void loadList() {
        Utils.showLoad(this);
        AppValue.authorization = AppValue.token;
        Client.sendPost(NetParmet.USR_WYFY, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BiilBeaan bean = Json.toObject(json, BiilBeaan.class);
            if (bean == null) {
                Utils.showNetErrorDialog(PropertyPayActivity.this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(PropertyPayActivity.this, bean.getMessage());
                return false;
            }
            allBean = bean.getData();
            splitData(bean.getData());
            list = bean.getData();
            adapter = new BiilAdapters(PropertyPayActivity.this, list);
            mZdList.setAdapter(adapter);
            return false;
        }));
    }

    /**
     * 分离数据
     */
    private void splitData(List<BiilBeaan.DataBean> been) {
        List<BiilBeaan.DataBean> beans = new ArrayList<>();
        for (BiilBeaan.DataBean bean : been) {
            if (bean.getStateId().equals("0")) {
                beans.add(bean);
            }
        }
        notBean = beans;
    }

    @KListener(R.id.all_zd)
    private void all_zdOnClick() {
        ((TextView) mAllZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mAllZd.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) mWjZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        mWjZd.getChildAt(1).setVisibility(View.INVISIBLE);
        if (allBean != null) {
            adapter.setBeen(allBean);
            adapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.wj_zd)
    private void wj_zdOnClick() {
        ((TextView) mAllZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        mAllZd.getChildAt(1).setVisibility(View.INVISIBLE);
        ((TextView) mWjZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mWjZd.getChildAt(1).setVisibility(View.VISIBLE);
        if (notBean != null) {
            adapter.setBeen(notBean);
            adapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.sreatch_layout)
    private void sreatch_layoutOnClick() {
        mSreatchLayout.setVisibility(View.GONE);
        mKeyWords.setVisibility(View.VISIBLE);
    }
}
