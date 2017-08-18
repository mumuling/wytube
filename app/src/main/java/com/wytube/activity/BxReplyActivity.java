package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/14.
 * 类 描 述: 报修回复
 */
@KActivity(R.layout.activity_add_action)
public class BxReplyActivity extends Activity {
    @KBind(R.id.edi_content)
    private EditText medi_content;
    @KBind(R.id.text_content)
    private TextView mtext_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /*回复*/
    @KListener(R.id.tx_but)
    private void tx_butOnClick() {
        loadhf();
    }

    private void loadhf() {
        String keyValue = "suitWorkId="+AppValue.lbBeansitem.getSuitWorkId()+"&replyContent="+medi_content.getText();
        Client.sendPost(NetParmet.USR_BXTS_HF, keyValue, new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseOK beans = Json.toObject(json, BaseOK.class);
            if (beans == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!beans.isSuccess()) {
                Utils.showOkDialog(this, beans.getMessage());
                return false;
            }else {
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }

}
