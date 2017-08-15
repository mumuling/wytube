package com.wytube.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.wytube.utlis.Utils;

@KActivity(R.layout.activity_appy_detail)
public class ApplyDetailActivity extends BaseActivity {
    @KBind(R.id.ll_foot)
    private LinearLayout ll_foot;
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
    @KBind(R.id.iv_dsl)
    private ImageView mIvDsl;
    @KBind(R.id.iv_ytg)
    private ImageView mIvYtg;
    @KBind(R.id.rl_delete)
    private RelativeLayout rl_delete;


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
            mIvDsl.setImageResource(R.drawable.jyxs_ztz2);
            mIvYtg.setImageResource(R.drawable.yd_sele);

        } else {
            tv_wait.setTextColor(getResources().getColor(R.color.text_default));
            tv_cross.setTextColor(getResources().getColor(R.color.blue));
            mIvDsl.setImageResource(R.drawable.yd_sele);
            mIvYtg.setImageResource(R.drawable.jyxs_ztz2);
            ll_foot.setVisibility(View.INVISIBLE);
            rl_delete.setVisibility(View.VISIBLE);

        }
        tv_reason.setText(dataBean.getContent());
        tv_name_number.setText(dataBean.getRegUserName() + "(" + dataBean.getRegUserPhone() + ")");
        tv_address.setText(dataBean.getCellName() + dataBean.getBuildingName() + dataBean.getUnitName() + dataBean.getNumberName());
    }

    @KListener(R.id.iv_telPhone)
    private void iv_telPhoneOnClick() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + dataBean.getRegUserPhone()));
        startActivity(intent);
    }

    @KListener(R.id.tv_delete)
    private void deleteOnClick() {
        loadsDle();
    }

    //删除喜事列表
    private void loadsDle() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY_DELETE, "typeId=" + dataBean.getCelebrationId(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }));
    }

    @KListener(R.id.rlayout_agree)
    private void agreeOnClick() {

    }

}
