package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.DynamicAdapters;
import com.wytube.beans.DynamicBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_frend_cricle)
public class FrendCricleActivity extends Activity  {

    @KBind(R.id.dynamic_list)
    private ListView mDynamicList;
    @KBind(R.id.rlss)
    private RelativeLayout rlsss;
    @KBind(R.id.relatid)
    private RelativeLayout relatids;
    @KBind(R.id.borrow_but)
    private CardView rborrow_but;


    public static boolean isReload = false;
    private DynamicAdapters adapter;
    private List<DynamicBean.DataBean.TracksBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        loadData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isReload) {
            isReload = false;
            loadData();
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.GET_DYNAMIC, "rows=15", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            DynamicBean bean = Json.toObject(json, DynamicBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            initList(bean.getData().getTracks());
            return false;
        }));
    }

    /**
     * 初始化列表
     *
     * @param beans 数据对象集合
     */
    private void initList(List<DynamicBean.DataBean.TracksBean> beans) {
//        if (this.adapter != null) {
//            adapter.setBeans(beans);
//            adapter.notifyDataSetChanged();
//            return;
//        }
        this.adapter = new DynamicAdapters(this,beans);
        mDynamicList.setAdapter(this.adapter);
        list = beans;
    }

}
