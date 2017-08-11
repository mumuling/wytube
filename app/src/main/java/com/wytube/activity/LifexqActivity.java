package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.LifexqAdapater;
import com.wytube.beans.BaseLeftxq;
import com.wytube.beans.BaseOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


/**
 * 加载生活服务管理详情
 */

@KActivity(R.layout.left_xq_list)
public class LifexqActivity extends Activity {
    @KBind(R.id.back_but)
    private ImageView backBut;
    @KBind(R.id.title_text)
    private TextView titleText;
    @KBind(R.id.rlss)
    private RelativeLayout rlss;
    @KBind(R.id.listv)
    private ListView listv;
    @KBind(R.id.menu_text)
    private TextView rmenu_text;
    @KBind(R.id.linear_sc_qx)
    private LinearLayout mlinear_sc_qx;
    @KBind(R.id.text_qx)
    private TextView mtext_qx;

    private String title, ShopId;
    private List<BaseLeftxq.DataBean> list;
    LifexqAdapater lifexqAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        ShopId = intent.getStringExtra("ShopId");
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            lifexqAdapater.flage = !lifexqAdapater.flage;
            if (!lifexqAdapater.flage) {
                rmenu_text.setText("取消");
                mlinear_sc_qx.setVisibility(View.VISIBLE);
            } else {
                rmenu_text.setText("选择");
                mlinear_sc_qx.setVisibility(View.GONE);
            }
            lifexqAdapater.notifyDataSetChanged();
        });
        titleText.setText(title);
        loads(ShopId);
    }


    /*全选*/
    @KListener(R.id.linear_qx)
    private void linear_qxOnClick() {
        if (lifexqAdapater.flages) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).isCheck = true;
            }
            lifexqAdapater.flages=!lifexqAdapater.flages;
            mtext_qx.setText("全不选");
            lifexqAdapater.notifyDataSetChanged();
        }else {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).isCheck = false;
            }
            lifexqAdapater.flages=!lifexqAdapater.flages;
            lifexqAdapater.notifyDataSetChanged();
            mtext_qx.setText("全选");
        }
    }

    /*删除*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        loadsDle(AppValue.LifeId);
    }


    /**
     * 删除生活服务
     */
    private void loadsDle(String shopId) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SHFWXQ_DL, "ids=" + shopId, new Handler(msg -> {
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
            }else {
                Toast.makeText(this, "删除成功!", Toast.LENGTH_SHORT).show();
                lifexqAdapater.flage = !lifexqAdapater.flage;
                if (!lifexqAdapater.flage) {
                    rmenu_text.setText("取消");
                    mlinear_sc_qx.setVisibility(View.VISIBLE);
                } else {
                    rmenu_text.setText("选择");
                    mlinear_sc_qx.setVisibility(View.GONE);
                }
                lifexqAdapater.notifyDataSetChanged();
                loads(ShopId);
            }
            return false;
        }));
    }

    /**
     * 生活服务  获取商品信息
     */
    private void loads(String shopTypeId) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SHFWXQ, "shopTypeId=" + shopTypeId, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseLeftxq bean = Json.toObject(json, BaseLeftxq.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            if (bean.getData().size()==0){
                Utils.showOkDialog(this, "暂无数据!");
            }
            lifexqAdapater = new LifexqAdapater(this,list);
            listv.setAdapter(lifexqAdapater);
            return false;
        }));
    }
}
