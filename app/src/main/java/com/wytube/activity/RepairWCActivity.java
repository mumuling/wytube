package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.T;
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
 * 创建时间: 2017/8/7.
 * 类 描 述: 维修完成
 */
@KActivity(R.layout.activity_repair_wxwc)
public class RepairWCActivity extends Activity{
    @KBind(R.id.repair_type)
    private TextView mrepair_type;
    @KBind(R.id.content_text)
    private TextView mcontent_text;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        intent = getIntent();
        mrepair_type.setText(intent.getStringExtra("cost"));
    }

    /*发布*/
    @KListener(R.id.add_money_but)
    private void add_money_butOnClick() {
        if ("".equals(mrepair_type.getText().toString())) {
            T.show(getApplicationContext(), "请输入金额！", 0);
            return;
        }
        if ("".equals(mcontent_text.getText().toString())) {
            T.show(getApplicationContext(), "请输入备注信息！", 0);
            return;
        }
        loadzpADDwc(intent.getStringExtra("repairWorkId"),"2");
    }

    /**
     * 完成维修确定
     * repairWorkId	是	String	服务ID
     * stateId	是	String	状态ID
     * price	是	String	金额
     * runContent	是	String	备注
     */
    private void loadzpADDwc(String repairWorkId,String stateId) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.ADD_JZXQ_ADDZPRYQD, "repairWorkId="+repairWorkId+"&stateId="+stateId+"&price="+mrepair_type.getText()+
                "&runContent="+mcontent_text.getText(), new Handler(msg -> {
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
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }
}
