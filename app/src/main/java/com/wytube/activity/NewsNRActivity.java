package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.NewsAdapters;
import com.wytube.beans.NewsNrBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/8.
 * 类 描 述: 新闻资讯内容列表
 */

@KActivity(R.layout.item_news_list)
public class NewsNRActivity extends Activity {
    @KBind(R.id.listview_zx)
    private ListView mlistview_zx;
    Intent intent;
    private List<NewsNrBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            iniview();
            AppValue.fish=-1;
        }
    }

    private void iniview() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        loadData();
    }
//
//
    /**
     * 加载分类列表
     */
    private void loadData() {
        Client.sendPost(NetParmet.GET_ALL_INFO_TYPES, "type="+intent.getStringExtra("tyId"), new Handler(msg -> {
            String json = msg.getData().getString("post");
            NewsNrBean bean = Json.toObject(json, NewsNrBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            NewsAdapters adapter = new NewsAdapters(this,list);
            mlistview_zx.setAdapter(adapter);
            return false;
        }));
    }

}
