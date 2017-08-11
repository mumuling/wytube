package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.LifeAdapater;
import com.wytube.beans.BaseSHfw;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.Utils;

import java.util.List;



@KActivity(R.layout.activity_life_list)
public class LifeServiceActivity extends BaseActivity {
    private List<BaseSHfw.DataBean> list;

    @KBind(R.id.ListV)
    private GridView mListV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.text_fb).setOnClickListener(v -> {
            startActivity(new Intent(this, LifeFbActivity.class));
        });
        loads();
    }


    /**
     * 生活服务  获取商铺类型
     */
    private void loads() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SHFW, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseSHfw bean = Json.toObject(json, BaseSHfw.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            LifeAdapater lifeAdapater = new LifeAdapater(this,list);
            mListV.setAdapter(lifeAdapater);
            return false;
        }));
    }
}
