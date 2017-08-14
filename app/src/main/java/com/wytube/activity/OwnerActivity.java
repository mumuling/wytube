package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.OwnerAdapter;
import com.wytube.beans.OwnerBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 业主管理
 */
@KActivity(R.layout.activity_owner)
public class OwnerActivity extends BaseActivity {
    @KBind(R.id.yzgl_list)
    private ListView yzgl_list;
    @KBind(R.id.tv_edit)
    private ImageView tv_edit;
    private OwnerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.tv_edit).setOnClickListener(view -> startActivity(
                new Intent(OwnerActivity.this,AddOwnerActivity.class)));
        yzgl_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OwnerBean.DataBean bean =(OwnerBean.DataBean )yzgl_list.getItemAtPosition(i);
                Intent intent = new Intent(OwnerActivity.this,OwnerItemActivity.class);
                intent.putExtra("databean",bean);
               startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 请求数据
     */
    private void loadData() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.OWNER,"20",new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            OwnerBean bean = Json.toObject(json, OwnerBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            AppValue.ownerBeans = bean.getData();
            adapter = new OwnerAdapter(this,AppValue.ownerBeans);
            yzgl_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return false;
        }));
    }

}
