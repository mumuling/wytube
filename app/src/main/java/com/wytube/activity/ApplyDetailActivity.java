package com.wytube.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseOK;
import com.wytube.beans.HappyBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

@KActivity(R.layout.activity_appy_detail)
public class ApplyDetailActivity extends BaseActivity {

    @KBind(R.id.tv_wait)
    private TextView tv_wait;
    @KBind(R.id.tv_cross)
    private TextView tv_cross;
    @KBind(R.id.tv_reason)
    private TextView tv_reason;
    @KBind(R.id.tv_name_number)
    private TextView tv_name_number;
    @KBind(R.id.iv_telPhone)
    private ImageView iv_telPhone;
    @KBind(R.id.tv_address)
    private TextView tv_address;
    @KBind(R.id.tv_delete)
    private TextView tv_delete;
    @KBind(R.id.rlayout_agree)
    private RelativeLayout rlayout_agree;
    private HappyBean.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.title_text).setOnClickListener(v -> {
            finish();
        });
        loadData();
    }

    private void loadData() {
        dataBean = (HappyBean.DataBean) getIntent().getSerializableExtra("data");
        if (dataBean.getStateId() == 0) {
            tv_wait.setTextColor(getResources().getColor(R.color.blue));
            tv_cross.setTextColor(getResources().getColor(R.color.text_default));
        } else {
            tv_wait.setTextColor(getResources().getColor(R.color.text_default));
            tv_cross.setTextColor(getResources().getColor(R.color.blue));
        }
        tv_reason.setText(dataBean.getContent());
        tv_name_number.setText(dataBean.getRegUserName() + "(" + dataBean.getRegUserPhone() + ")");
        tv_address.setText(dataBean.getCellName() + dataBean.getBuildingName() + dataBean.getUnitName() + dataBean.getNumberName());
    }
    @KListener(R.id.iv_telPhone)
    private void iv_telPhoneOnClick(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ dataBean.getRegUserPhone()));
        startActivity(intent);
    }

    @KListener(R.id.tv_delete)
    private void deleteOnClick() {
        loadsDle(AppValue.XSID);
    }
    //删除喜事列表
    private void loadsDle(String typeId) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_JY_DELET, "typeId=" + typeId, new Handler(msg -> {
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
            }
            return false;
        }));
    }

    @KListener(R.id.rlayout_agree)
    private void agreeOnClick() {

    }

}
