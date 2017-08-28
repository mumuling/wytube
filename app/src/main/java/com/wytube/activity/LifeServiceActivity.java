package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.LifeAdapater;
import com.wytube.beans.BaseSHfw;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.Utils;

import java.util.List;



@KActivity(R.layout.activity_life_list)
public class LifeServiceActivity extends BaseActivity {
    private List<BaseSHfw.DataBean> list;

    @KBind(R.id.ListV)
    private GridView mListV;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    @KBind(R.id.text_fb)
    private TextView mtext_fb;

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


    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
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
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                mtext_fb.setVisibility(View.GONE);
                ToastUtils.showToast(this,"服务器异常!请稍后再试!");
                return false;
            }
            mtext_fb.setVisibility(View.VISIBLE);
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            LifeAdapater lifeAdapater = new LifeAdapater(this,list);
            mListV.setAdapter(lifeAdapater);
            if (list.size()==0){
                mshaxin.setVisibility(View.VISIBLE);
            }else {
                mshaxin.setVisibility(View.GONE);
            }
            return false;
        }));
    }
}
