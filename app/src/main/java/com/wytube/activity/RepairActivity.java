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
import com.wytube.adaper.RepairAdapters;
import com.wytube.beans.RepairBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 家政服务
 * */
@KActivity(R.layout.activity_repair)
public class RepairActivity extends BaseActivity {

    @KBind(R.id.not_process)
    private LinearLayout mNotProcess;
    @KBind(R.id.process_ing)
    private LinearLayout mProcessIng;
    @KBind(R.id.processed)
    private LinearLayout mProcessed;
    @KBind(R.id.repair_list)
    private ListView mRepairList;
    private LinearLayout selectLayout;
    private List<RepairBean.DataBean> tempBeans = new ArrayList<>();
    private RepairAdapters adapter;
    private int statetype = 0; /*每次点击改变值 默认为0*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        loadData();
        selectLayout = mNotProcess;/*标头栏*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            tempBeans.clear();/*清空之前的数据*/
            loadData();
            AppValue.fish = -1;
        }
    }


    @KListener(R.id.not_process)
    private void not_processOnClick() {
        clearStyle(selectLayout);
        selectLayout = mNotProcess;
        ((TextView) mNotProcess.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mNotProcess.getChildAt(1).setVisibility(View.VISIBLE);
        statetype = 0;
        tempBeans.clear();
        for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
            if (repairBean.getStateId() == 0) {
                tempBeans.add(repairBean);
            }
        }
        adapter.setBeans(tempBeans);
        adapter.notifyDataSetChanged();
    }

    @KListener(R.id.process_ing)
    private void process_ingOnClick() {
        clearStyle(selectLayout);
        selectLayout = mProcessIng;
        ((TextView) mProcessIng.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mProcessIng.getChildAt(1).setVisibility(View.VISIBLE);
        statetype = 1;
        tempBeans.clear();
        for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
            if (repairBean.getStateId() == 1) {
                tempBeans.add(repairBean);
            }
        }
        adapter.setBeans(tempBeans);
        adapter.notifyDataSetChanged();
    }

    @KListener(R.id.processed)
    private void processedOnClick() {
        clearStyle(selectLayout);
        selectLayout = mProcessed;
        ((TextView) mProcessed.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mProcessed.getChildAt(1).setVisibility(View.VISIBLE);
        statetype = 2;
        tempBeans.clear();
        for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
            if (repairBean.getStateId() == 2) {
                tempBeans.add(repairBean);
            }
        }
        adapter.setBeans(tempBeans);
        adapter.notifyDataSetChanged();
    }

    /**
     * 请求数据
     */
    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.QUERY_REPAIR_LIST, "rows=20", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            RepairBean bean = Json.toObject(json, RepairBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            AppValue.repairBeans = bean.getData();
            adapter = new RepairAdapters(this,AppValue.repairBeans);
            mRepairList.setAdapter(this.adapter);


            for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                if (repairBean.getStateId() == statetype) {
                    tempBeans.add(repairBean);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
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



}
