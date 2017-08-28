package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.TradeWPAdapters;
import com.wytube.beans.BaseOK;
import com.wytube.beans.BaseWPjy;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_wp_trade)
public class BorroActivity extends Activity {

    @KBind(R.id.news_list)
    private GridView mNewsList;
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

    private TradeWPAdapters lifeAdapater;
    private List<BaseWPjy.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            if (lifeAdapater!=null){
                lifeAdapater.flage = !lifeAdapater.flage;
                if (!lifeAdapater.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                lifeAdapater.notifyDataSetChanged();
            }
        });
        iniview();
    }


    private void iniview() {
        loadDate();
    }

    @KListener(R.id.img_200)
    private void img_200OnClick() {
        startActivity(new Intent(this,TraWPfbInfoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            loadDate();
            AppValue.fish = -1;
        }
    }

    /*全选*/
    @KListener(R.id.linear_qx)
    private void linear_qxOnClick() {
        if (lifeAdapater.flages) {
            for (int i = 0; i < list.size(); i++) {
                /*全选*/
                if (AppValue.WPJYxxId != null && !AppValue.WPJYxxId.equals(""))
                {
                    AppValue.WPJYxxId += ",";
                }
                AppValue.WPJYxxId += list.get(i).getGoodsId();
                list.get(i).isCheck = true;
            }
            lifeAdapater.flages=!lifeAdapater.flages;
            mtext_qx.setText("全不选");
            lifeAdapater.notifyDataSetChanged();
        }else {
            AppValue.WPJYxxId="";
            for (int i = 0; i < list.size(); i++) {
                list.get(i).isCheck = false;
            }
            lifeAdapater.flages=!lifeAdapater.flages;
            lifeAdapater.notifyDataSetChanged();
            mtext_qx.setText("全选");
        }
    }

    /*删除*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        String[] wPJYxxId = AppValue.WPJYxxId.split(",");
        for (int i = 0; i < wPJYxxId.length; i++) {
            if (AppValue.WPJYxxId != null && !AppValue.WPJYxxId.equals(""))
            {
                AppValue.WPJYxxId="";
                AppValue.WPJYxxId += "";
            }
            AppValue.WPJYxxId += list.get(i).getGoodsId();
            loadsDle(AppValue.WPJYxxId);
        }
    }


    /**
     * 删除物品借用
     */
    private void loadsDle(String goodsId) {
        Client.sendPost(NetParmet.USR_WPJY_DLETE, "goodsId=" + goodsId, new Handler(msg -> {
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
                ToastUtils.showToast(this,"删除成功!");
                lifeAdapater.flage = !lifeAdapater.flage;
                if (!lifeAdapater.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                lifeAdapater.notifyDataSetChanged();
                AppValue.WPJYxxId = "";
                loadDate();
                lifeAdapater.flage = false;
            }
            return false;
        }));
    }



    /**
     * 物品借用列表
     */
    private void loadDate() {
        Client.sendPost(NetParmet.USR_WPJY_LB, "", new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseWPjy bean = Json.toObject(json, BaseWPjy.class);
            if (bean == null) {
//                Utils.showNetErrorDialog(this);
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
            list = bean.getData();
            lifeAdapater = new TradeWPAdapters(this,list);
            mNewsList.setAdapter(lifeAdapater);
            if (list.size()==0){
                mshaxin.setVisibility(View.VISIBLE);
            }else {
                mshaxin.setVisibility(View.GONE);
            }
            return false;
        }));
    }
}
