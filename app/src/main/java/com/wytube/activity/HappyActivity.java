package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.HappyAdapter;
import com.wytube.beans.HappyBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIN on 2017/8/12.
 */
@KActivity(R.layout.activity_happy)
public class HappyActivity extends BaseActivity{
    private LinearLayout selectLayout;
    @KBind(R.id.ll_reviewed)
    private LinearLayout ll_reviewed;
    @KBind(R.id.ll_oked)
    private LinearLayout ll_oked;
    @KBind(R.id.happy_list)
    private ListView happy_list;
    private HappyAdapter apapter;
    List<HappyBean.DataBean> passData = new ArrayList<>();
    List<HappyBean.DataBean> unPassData = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        loadData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        selectLayout = ll_reviewed;
    }

    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY, "rows=15", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            HappyBean bean = Json.toObject(json, HappyBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            List<HappyBean.DataBean> data = bean.getData();
            for (HappyBean.DataBean dataBean : data) {
                if (dataBean.getStateId() == 0){
                    unPassData.add(dataBean);
                }else {
                    passData.add(dataBean);
                }
            }
            apapter=new HappyAdapter(this,unPassData);
            happy_list.setAdapter(apapter);
            apapter.notifyDataSetChanged();
            return false;
        }));
    }

    /**
     * 清除点击样式
     *
     * @param layout 布局视图
     */
    private void clearStyle(LinearLayout layout) {
        ((TextView) layout.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        layout.getChildAt(1).setVisibility(View.INVISIBLE);
    }

    @KListener(R.id.ll_reviewed)
    private void ll_reviewedOnClick() {
        clearStyle(selectLayout);
        selectLayout = ll_reviewed;
        ((TextView) ll_reviewed.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        ll_reviewed.getChildAt(1).setVisibility(View.VISIBLE);
        apapter.setData(unPassData);
        apapter.notifyDataSetChanged();
    }

    @KListener(R.id.ll_oked)
    private void ll_okedOnClick() {
        clearStyle(selectLayout);
        selectLayout = ll_oked;
        ((TextView) ll_oked.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        ll_oked.getChildAt(1).setVisibility(View.VISIBLE);
        apapter.setData(passData);
        apapter.notifyDataSetChanged();

    }

}
