package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.BiilAdapter;
import com.wytube.beans.BaseOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.BiilBeaan;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 物业缴费
 * */
@KActivity(R.layout.activity_wyjfs)
public class PropertyPayActivity extends BaseActivity {
//    private ArrayList<PinnedSectionBean> real_data;
    List<BiilBeaan.DataBean> real_data = new ArrayList<>();
    private LinearLayout selectLayout;

    @KBind(R.id.all_zd)
    private LinearLayout mAllZd;
    @KBind(R.id.wj_zd)
    private LinearLayout mWjZd;
    @KBind(R.id.zd_list)
    private ListView mListView;
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
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;

    private BiilAdapter mAdapter;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            if (mAdapter!=null){
                mAdapter.flage = !mAdapter.flage;
                if (!mAdapter.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        loadList();
        selectLayout = mAllZd;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            real_data.clear();
            loadList();
        }
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        real_data.clear();/*清空之前的数据*/
        loadList();
    }

    /*全选*/
    @KListener(R.id.linear_qx)
    private void linear_qxOnClick() {
        if (mAdapter.flages) {
            for (int i = 0; i < real_data.size(); i++) {
                /*全选*/
                if (AppValue.WYjfId != null && !AppValue.WYjfId.equals(""))
                {
                    AppValue.WYjfId += ",";
                }
                AppValue.WYjfId += real_data.get(i).getBillId();
                real_data.get(i).isCheck = true;
            }
            mAdapter.flages=!mAdapter.flages;
            mtext_qx.setText("全不选");
            mAdapter.notifyDataSetChanged();
        }else {
            AppValue.WYjfId="";
            real_data.clear();
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
        String[] wWYjfId = AppValue.WYjfId.split(",");
        for (int i = 0; i < wWYjfId.length; i++) {
            if (AppValue.WYjfId != null && !AppValue.WYjfId.equals(""))
            {
                AppValue.WYjfId="";
                AppValue.WYjfId += "";
            }
            AppValue.WYjfId += real_data.get(i).getBillId();
            loaddelete(AppValue.WYjfId);
        }
    }


    /**
     * billId	是	String	物业费Id
     * stateId
     */
    boolean ISok = false;
    private void loaddelete(String billId) {
        String Kvs = "billId="+billId;
        Client.sendPost(NetParmet.USR_WYFY_DELE, Kvs, new Handler(msg -> {
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
                ToastUtils.showToast(this,"删除成功");
                mAdapter.flage = !mAdapter.flage;
                if (!mAdapter.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
                AppValue.WYjfId="";
                real_data.clear();
                if (!ISok){
                    loadList();
                    mAdapter.flage = false;
                    ISok=true;
                }
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
//                Utils.showNetErrorDialog(this);
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                ToastUtils.showToast(this,"服务器异常!请稍后再试!");
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(PropertyPayActivity.this, bean.getMessage());
                return false;
            }
            if (AppValue.fish==1){
                /*删除时先清除上一次数据*/
                AppValue.wyreal.clear();
                AppValue.fish=-1;
            }
            AppValue.wyreal = bean.getData();
            mAdapter = new BiilAdapter(real_data, this);
            mListView.setAdapter(mAdapter);
            for (BiilBeaan.DataBean repairBean : AppValue.wyreal) {
                if (repairBean.getStateId() == type) {
                    real_data.add(repairBean);
                }
                if (real_data.size()==0){
                    mshaxin.setVisibility(View.VISIBLE);
                }else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            mAdapter.setData(real_data);
            mAdapter.notifyDataSetChanged();
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

    /*未缴费*/
    @KListener(R.id.all_zd)
    private void all_zdOnClick() {
        clearStyle(selectLayout);
        selectLayout = mAllZd;
        ((TextView) mAllZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mAllZd.getChildAt(1).setVisibility(View.VISIBLE);
        type = 0;
        real_data.clear();
        if (AppValue.wyreal!=null) {
            for (BiilBeaan.DataBean BiilBean : AppValue.wyreal) {
                if (BiilBean.getStateId() == 0) {
                    real_data.add(BiilBean);
                }
                if (real_data.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            mAdapter.setData(real_data);
            mAdapter.notifyDataSetChanged();
        }
    }


    /*已缴费*/
    @KListener(R.id.wj_zd)
    private void wj_zdOnClick() {
        clearStyle(selectLayout);
        selectLayout = mWjZd;
        type = 1;
        ((TextView) mWjZd.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mWjZd.getChildAt(1).setVisibility(View.VISIBLE);
        real_data.clear();
        if (AppValue.wyreal!=null) {
            for (BiilBeaan.DataBean BiilBean : AppValue.wyreal) {
                if (BiilBean.getStateId() == 1) {
                    real_data.add(BiilBean);
                }
                if (real_data.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            mAdapter.setData(real_data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.sreatch_layout)
    private void sreatch_layoutOnClick() {
        mSreatchLayout.setVisibility(View.GONE);
        mKeyWords.setVisibility(View.VISIBLE);
    }
}
