package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.OwnerAdapter;
import com.wytube.beans.BaseWyOK;
import com.wytube.beans.OwnerBean;
import com.wytube.dialog.TellDialog;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;


@KActivity(R.layout.item_owner_info)
public class OwnerItemActivity extends BaseActivity {

    @KBind(R.id.owneradress)
    private EditText owneradress;
    @KBind(R.id.ownerName)
    private EditText ownerName;
    @KBind(R.id.ownerPhone)
    private EditText ownerPhone;
    @KBind(R.id.ownerType)
    private TextView ownerType;
    @KBind(R.id.ll_tellowner)
    private LinearLayout ll_tellowner;
    @KBind(R.id.tv_date_time)
    private TextView tv_date_time;
    @KBind(R.id.tv_edit)
    private TextView tv_edit;
    Intent intent;
    private OwnerBean.DataBean mDatabean;
    private OwnerAdapter adapter;
    private TextWatcher watcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        owneradress.setOnClickListener(view -> owneradress.setCursorVisible(true));
        ownerName.setOnClickListener(view -> ownerName.setCursorVisible(true));
        ownerPhone.setOnClickListener(view -> ownerPhone.setCursorVisible(true));
        ownerType.setOnClickListener(view -> ownerType.setCursorVisible(true));
        findViewById(R.id.back_but).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.title_text).setOnClickListener(v -> {
            finish();
        });

        Ownershow();

        //监听文字变化
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_edit.setVisibility(View.VISIBLE);
            }
        };
        ownerName.addTextChangedListener(watcher);
        owneradress.addTextChangedListener(watcher);
        ownerType.addTextChangedListener(watcher);
        ownerPhone.addTextChangedListener(watcher);
    }

    private void Ownershow() {
        mDatabean = (OwnerBean.DataBean) getIntent().getSerializableExtra("databean");
        owneradress.setText(mDatabean.getBuildingName() + mDatabean.getUnitName() + mDatabean.getRoomNum());
        ownerName.setText(mDatabean.getOwnerName());
        ownerPhone.setText(mDatabean.getOwnerPhone());
        if (mDatabean.getOwnerType() == 0) {
            ownerType.setText("业主");
        } else if (mDatabean.getOwnerType() == 1) {
            ownerType.setText("租客");
        } else if ((mDatabean.getOwnerType() == 2)) {
            ownerType.setText("亲属");
        } else {
            ownerType.setText("其他");
        }
        tv_date_time.setText(mDatabean.getCreateDate());
        ll_tellowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TellDialog.showTell(OwnerItemActivity.this,ownerPhone.getText().toString());
            }
        });
    }

    /**
     * 修改业主
     * buildingId	是	String	楼宇ID
     * unitId	    是	String	单元ID
     * roomId	    是	String	房间ID
     * roomNum	    是	String	房间号
     * ownerType	是	String	业主类型
     * ownerName	是	String	姓名
     * ownerPhone	是	String	电话
     * ownerId	    是	String	业主ID
     */
    @KListener(R.id.tv_edit)
    private void tv_editOnClick() {
        Utils.showLoad(this);
        String name = ownerName.getText().toString();
        String phone = ownerPhone.getText().toString();
        String keyValue = "buildingId=" + mDatabean.getBuildingId() +
                "&unitId=" + mDatabean.getUnitId() +
                "&roomNum=" + mDatabean.getRoomNum() +
                "&roomId=" + mDatabean.getRoomId() +
                "&ownerType=" + mDatabean.getOwnerType() +
                "&ownerName=" + name +
                "&ownerPhone=" + phone +
                "&ownerId=" + mDatabean.getOwnerId();
        Client.sendPost(NetParmet.OWNER_UPDATE, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseWyOK bean = Json.toObject(json, BaseWyOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.isSuccess()) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


   /* *//**
     * 删除业主
     * ownerId	是	String	业主ID
     *//*
     *//*删除*//*
    @KListener(R.id.iv_delete)
    private void iv_deleteOnClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {
            initDelete();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.create().show();
    }

    *//**
     * 删除业主
     *//*
    private void initDelete() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.OWNER_DELETE,"ids=" +mDatabean.getOwnerId() , new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            OwnerDel bean = Json.toObject(json, OwnerDel.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (bean.isSuccess()) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }*/

}