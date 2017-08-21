package com.wytube.activity;

import android.content.Context;
import android.content.Intent;
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
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 喜事列表
 */
@KActivity(R.layout.activity_happy)
public class HappyActivity extends BaseActivity {
    private LinearLayout selectLayout;
    @KBind(R.id.ll_reviewed)
    private LinearLayout ll_reviewed;
    @KBind(R.id.ll_oked)
    private LinearLayout ll_oked;
    @KBind(R.id.happy_list)
    private ListView happy_list;
    @KBind(R.id.tv_nodata)
    private TextView tv_nodata;
    private HappyAdapter apapter;
    List<HappyBean.DataBean> passData = new ArrayList<>();
    private Context context;
    int type = 0;


    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            passData.clear();
            loadData();
            AppValue.fish=-1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        context = this;
        loadData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        happy_list.setEmptyView(tv_nodata);
        selectLayout = ll_reviewed;
    }


    /*
    * 喜事列表数据
    * */
    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY, "stateId", new Handler(msg -> {
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
            AppValue.xsBeans = bean.getData();
            apapter = new HappyAdapter(this, passData);
            happy_list.setAdapter(apapter);

            apapter.setOnIteOnItemClickListener(bean1 -> {
                Intent intent = new Intent(HappyActivity.this, ApplyDetailActivity.class);
                intent.putExtra("data", bean1);
                context.startActivity(intent);//传递参数判断是审阅还是通过
            });

            for (HappyBean.DataBean repairBean : AppValue.xsBeans) {
                if (repairBean.getStateId() == type) {
                    passData.add(repairBean);
                }
            }
            apapter.setBeans(passData);
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
        type = 0;
        passData.clear();
        for (HappyBean.DataBean happyBean : AppValue.xsBeans) {
            if (happyBean.getStateId() == 0) {
                passData.add(happyBean);
            }
        }
        apapter.setData(passData);
        apapter.notifyDataSetChanged();
    }

    @KListener(R.id.ll_oked)
    private void ll_okedOnClick() {
        clearStyle(selectLayout);
        type = 1;
        passData.clear();
        selectLayout = ll_oked;
        ((TextView) ll_oked.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        ll_oked.getChildAt(1).setVisibility(View.VISIBLE);
        for (HappyBean.DataBean happyBean : AppValue.xsBeans) {
            if (happyBean.getStateId() == 1|| happyBean.getStateId() == 2) {
                passData.add(happyBean);
            }
        }
        apapter.setData(passData);
        apapter.notifyDataSetChanged();

    }

}
