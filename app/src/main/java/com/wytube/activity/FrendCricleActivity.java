package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.DynamicAdapters;
import com.wytube.beans.DynamicBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
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


    public static boolean isReload = false;
    private DynamicAdapters adapter;
    private List<DynamicBean.DataBean.TracksBean> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        loadData();
        AppValue.TrackId="";
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            if (adapter!=null){
                adapter.flage = !adapter.flage;
                if (adapter.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        loadData();
    }

    /**
     * 初始化列表
     *
     * @param beans 数据对象集合
     */
    private void initList(List<DynamicBean.DataBean.TracksBean> beans) {
        this.adapter = new DynamicAdapters(this,beans);
        mDynamicList.setAdapter(this.adapter);
        list = beans;
        if (list.size()==0){
            mshaxin.setVisibility(View.VISIBLE);
        }else {
            mshaxin.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isReload) {
            isReload = false;
            loadData();
        }
    }

    /*全选*/
    @KListener(R.id.linear_qx)
    private void linear_qxOnClick() {
        if (adapter.flages) {
            for (int i = 0; i < list.size(); i++) {
                /*全选*/
                if (AppValue.TrackId != null && !AppValue.TrackId.equals(""))
                {
                    AppValue.TrackId += ",";
                }
                AppValue.TrackId += list.get(i).getTrackId();
                list.get(i).isCheck = true;
            }
            adapter.flages=!adapter.flages;
            mtext_qx.setText("全不选");
            adapter.notifyDataSetChanged();
        } else {
            AppValue.TrackId="";
            for (int i = 0; i < list.size(); i++) {
                list.get(i).isCheck = false;
            }
            adapter.flages=!adapter.flages;
            adapter.notifyDataSetChanged();
            mtext_qx.setText("全选");
        }
    }

    /*删除*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        loadDataPB();
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
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                ToastUtils.showToast(this,"服务器异常!请稍后再试!");
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
     * 加载数据
     * ids	  	朋友圈Id
     * type	    类型 1动态 0 回复内容
     * status   状态 0禁用 1正常
     */
    private void loadDataPB() {
        Utils.showLoad(this);
        if(AppValue.TrackId != null && !AppValue.TrackId.equals("")) {
            String idsVal = "";
            String[] idsArray = AppValue.TrackId.split(",");
            for (int i = 0; i< idsArray.length;i++)
            {
                if(!idsVal.equals(""))
                {
                    idsVal += "&";
                }
                idsVal += "ids=" + idsArray[i]+"&type="+ list.get(i).getType()+"&status="+list.get(i).getStatus();
            }
            Client.sendPost(NetParmet.GET_DYNAPB, idsVal, new Handler(msg -> {
                Utils.exitLoad();
                String json = msg.getData().getString("post");
                return false;
            }));
        }
    }


}
