package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.CommunityAdapter;
import com.wytube.beans.BeseHd;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_activelist)
public class ActiveActivity extends BaseActivity {
    private List<BeseHd.DataBean> list;

    @KBind(R.id.list_hd)
    private ListView mListHd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.text_fb).setOnClickListener(v -> {
            startActivity(new Intent(this, ActiveFbActivity.class));
        });
        loadhd("", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            loadhd("","");
            AppValue.fish=-1;
        }
    }

    /**
     * 社区活动
     * regUserId    参与人ID
     * rows         获取条数 默认10
     * page         获取第几页数据 默认1
     */
    private void loadhd(String rows, String page) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SQHD,"&rows=" + rows + "&page=" + page, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BeseHd bean = Json.toObject(json, BeseHd.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            list = bean.getData();
            CommunityAdapter Adapter = new CommunityAdapter(ActiveActivity.this,list);
            mListHd.setAdapter(Adapter);
            return false;
        }));
    }
}
