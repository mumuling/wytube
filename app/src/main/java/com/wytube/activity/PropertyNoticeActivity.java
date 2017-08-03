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
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.PropListAdapters;
import com.wytube.beans.PropMsgBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业通知
 */
@KActivity(R.layout.activity_property_notice)
public class PropertyNoticeActivity extends Activity {

    @KBind(R.id.msg_list)
    private ListView mMsgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            initData();
            AppValue.fish=1;
        }
    }

    /**
     * 请求数据
     */
    private void initData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.PROP_MSG, "", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            PropMsgBean bean = Json.toObject(json, PropMsgBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(PropertyNoticeActivity.this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(PropertyNoticeActivity.this, bean.getMessage());
                return false;
            }
            AppValue.propMsgs = bean.getData();
            PropListAdapters adapter = new PropListAdapters(this, AppValue.propMsgs);
            mMsgList.setAdapter(adapter);
            return false;
        }));
    }

    /*发布通知*/
    @KListener(R.id.repair_now)
    private void repair_nowOnClick() {
        startActivity(new Intent(this, ReleaseNoticeActivity.class));
    }
}