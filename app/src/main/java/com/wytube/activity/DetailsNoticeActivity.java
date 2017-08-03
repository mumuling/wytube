package com.wytube.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/3.
 * 类 描 述: 通知详情
 */
@KActivity(R.layout.activity_detaiks)
public class DetailsNoticeActivity extends Activity {

    @KBind(R.id.edit_title)
    private EditText medit_title;
    @KBind(R.id.content_text)
    private EditText mcontent_text;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        intent = getIntent();
        medit_title.setText(intent.getStringExtra("title"));
        mcontent_text.setText(intent.getStringExtra("content"));
    }


    /**
     * 删除通知
     * pushId	通知推送Id
     */
    private void initDelete() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.PROP_WYDL, "pushId="+intent.getStringExtra("pushId"), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseWyOK bean = Json.toObject(json, BaseWyOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.isSuccess()){/*true成功*/
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }

    /**
     * 修改通知
     * pushId	通知推送Id
     * title	标题
     * content	通知内容
     */
    private void initup() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.PROP_WYSC, "pushId="+intent.getStringExtra("pushId")+"&title="+medit_title.getText() + "&content="+mcontent_text.getText(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseWyOK bean = Json.toObject(json, BaseWyOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.isSuccess()){/*true成功*/
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }

    /*删除*/
    @KListener(R.id.linear_delete)
    private void linear_deleteOnClick() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {initDelete();});
        builder.setNegativeButton("取消", (dialog, which) -> {});
        builder.create().show();
    }

    /*修改*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定修改?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {initup();});
        builder.setNegativeButton("取消", (dialog, which) -> {});
        builder.create().show();
    }
}
