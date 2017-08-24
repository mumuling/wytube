package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseWyOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/3.
 * 类 描 述: 发布通知
 */

@KActivity(R.layout.activity_release)
public class ReleaseNoticeActivity extends Activity {

    @KBind(R.id.edit_title)
    private EditText medit_title;
    @KBind(R.id.content_text)
    private EditText mcontent_text;

    String titte,contents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});

    }


    /**
     * 添加通知
     * title	String	标题
     * content	String	通知内容
     */
    private void initData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.PROP_WYFS, "title="+medit_title.getText() + "&content="+mcontent_text.getText(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseWyOK bean = Json.toObject(json, BaseWyOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.isSuccess()){/*true成功*/
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }


    /*发布*/
    @KListener(R.id.borrow_but)
    private void borrow_butOnClick() {
        titte =  medit_title.getText().toString();
        contents = mcontent_text.getText().toString();
        if (titte.length() <= 0) {
            ToastUtils.showToast(this,"请填写发布标题");
            return;
        }
        if (contents.length() <= 0) {
            ToastUtils.showToast(this,"请填写发布内容");
            return;
        }

        initData();
    }
}
