package com.wytube.activity;

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
import com.wytube.beans.BaseOK;
import com.wytube.beans.BiilAdapters;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.BiilBeaan;
import com.wytube.shared.Ftime.PinnedSectionRefreshListView;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 物业缴费
 * */
@KActivity(R.layout.activity_wyjfs)
public class PropertyPayActivity extends BaseActivity implements PinnedSectionRefreshListView.IXListViewListener {
//    private ArrayList<PinnedSectionBean> real_data;
    private List<BiilBeaan.DataBean> real_data;
    private List<BiilBeaan.DataBean> notBean;
    private List<BiilBeaan.DataBean> allBean;
    public static boolean isReload = false;

    @KBind(R.id.all_zd)
    private LinearLayout mAllZd;
    @KBind(R.id.wj_zd)
    private LinearLayout mWjZd;
    @KBind(R.id.zd_list)
    private PinnedSectionRefreshListView mListView;
    @KBind(R.id.key_words)
    private EditText mKeyWords;
    @KBind(R.id.sreatch_layout)
    private LinearLayout mSreatchLayout;
    @KBind(R.id.menu_text)
    private TextView rmenu_text;
    @KBind(R.id.linear_sc_qx)
    private LinearLayout mlinear_sc_qx;
    @KBind(R.id.text_qx)
    private TextView mtext_qx;
    private BiilAdapters mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            mAdapter.flage = !mAdapter.flage;
            if (!mAdapter.flage) {
                rmenu_text.setText("取消");
                mlinear_sc_qx.setVisibility(View.VISIBLE);
            } else {
                rmenu_text.setText("选择");
                mlinear_sc_qx.setVisibility(View.GONE);
            }
            mAdapter.notifyDataSetChanged();
        });
        loadList();
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            loadList();
            AppValue.fish=-1;
        }
    }

    /*全选*/
    @KListener(R.id.linear_qx)
    private void linear_qxOnClick() {
        if (mAdapter.flages) {
            for (int i = 0; i < real_data.size(); i++) {
                real_data.get(i).isCheck = true;
            }
            mAdapter.flages=!mAdapter.flages;
            mtext_qx.setText("全不选");
            mAdapter.notifyDataSetChanged();
        }else {
            for (int i = 0; i < real_data.size(); i++) {
                real_data.get(i).isCheck = false;
            }
            mAdapter.flages=!mAdapter.flages;
            mAdapter.notifyDataSetChanged();
            mtext_qx.setText("全选");
        }
    }


    /*删除*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        loaddelete();
    }


    @Override
    public void onRefresh() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        // 这里也可以用系统时间
        mListView.setRefreshTime("刚刚");
    }


    @Override
    public void onLoadMore() {
        mListView.setLoadHide();
    }


    /**
     * billId	是	String	物业费Id
     * stateId
     */
    private void loaddelete() {
        Utils.showLoad(this);
        String Kvs = "billId="+AppValue.WYjfId;
        Client.sendPost(NetParmet.USR_WYFY_DELE, Kvs, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            } else {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                mAdapter.flage = !mAdapter.flage;
                if (!mAdapter.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                loadList();
            }
            return false;
        }));
    }


    /**
     * 加载账单
     */
    BiilBeaan bean;
    private void loadList() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_WYFY, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            bean = Json.toObject(json, BiilBeaan.class);
            if (bean == null) {
                Utils.showNetErrorDialog(PropertyPayActivity.this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(PropertyPayActivity.this, bean.getMessage());
                return false;
            }
            allBean = bean.getData();
            real_data = bean.getData();
            splitData(bean.getData());
            mAdapter = new BiilAdapters(real_data, this);
            mListView.setAdapter(mAdapter);
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

    /*已缴费*/
    @KListener(R.id.all_zd)
    private void all_zdOnClick() {
        ((TextView) mAllZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mAllZd.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) mWjZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        mWjZd.getChildAt(1).setVisibility(View.INVISIBLE);
        if (allBean != null) {
            mAdapter.setBeen(allBean);
            mAdapter.notifyDataSetChanged();
        }
    }

    /*未缴费*/
    @KListener(R.id.wj_zd)
    private void wj_zdOnClick() {
        ((TextView) mAllZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        mAllZd.getChildAt(1).setVisibility(View.INVISIBLE);
        ((TextView) mWjZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mWjZd.getChildAt(1).setVisibility(View.VISIBLE);
        if (notBean != null) {
            mAdapter.setBeen(notBean);
            mAdapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.sreatch_layout)
    private void sreatch_layoutOnClick() {
        mSreatchLayout.setVisibility(View.GONE);
        mKeyWords.setVisibility(View.VISIBLE);
    }
}
