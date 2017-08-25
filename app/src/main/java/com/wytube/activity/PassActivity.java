package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import com.cqxb.until.ACache;
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
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/18.
 * 类 描 述: 修改密码
 */
@KActivity(R.layout.wordxg)
public class PassActivity extends Activity{
    @KBind(R.id.edi_password)
    private EditText medi_password;
    @KBind(R.id.edi_password_to)
    private EditText medi_password_to;
    private String password,passwordto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /*重置密码*/
    @KListener(R.id.select_but)
    private void select_butOnClick() {
        password = medi_password.getText().toString();
        passwordto = medi_password_to.getText().toString();
        if (password.length()<= 0) {
            Utils.showOkDialog(this, "请输入重置密码!");
            return;
        }
        if (passwordto.length()<= 0) {
            Utils.showOkDialog(this, "请输入确认密码!");
            return;
        }
        if (!password.equals(passwordto)) {
            T.show(getApplicationContext(), "两次输入的密码不一致", 1);
            return;
        }
        postData();
    }

    private void postData() {
        Utils.showLoad(this);
        String keyValue = "oldpass="+ AppValue.YhPass+"&newpass="+password;
        Client.sendPost(NetParmet.USR_BXTS_CZ, keyValue, new Handler(msg -> {
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
                AppValue.YhPass = password;
                ACache mCache = ACache.get(PassActivity.this);
                mCache.put("password", AppValue.YhPass, 60*60*24*6);
                ToastUtils.showToast(this,"修改成功!");
                finish();
            }
            return false;
        }));
    }

}
