package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseOK;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.BiilBeaan;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/17.
 * 类 描 述:
 */
@KActivity(R.layout.activity_wyxq)
public class PropertyXQActivity extends Activity {

    @KBind(R.id.text_timer)
    private TextView mtext_timer;
    @KBind(R.id.text_xqname)
    private TextView mtext_xqname;
    @KBind(R.id.text_zt)
    private TextView mtext_zt;
    @KBind(R.id.text_money)
    private TextView mtext_money;
    @KBind(R.id.text_xq)
    private TextView mtext_xq;
    @KBind(R.id.text_peic)
    private TextView mtext_peic;
    @KBind(R.id.text_jf_types)
    private TextView mtext_jf_types;
    @KBind(R.id.text_num)
    private TextView mtext_num;
    @KBind(R.id.text_jf_type)
    private TextView mtext_jf_type;
    @KBind(R.id.jf_time)
    private RelativeLayout mjf_time;
    @KBind(R.id.publish_but)
    private CardView mpublish_but;

    BiilBeaan.DataBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        iniview();
    }

    /*已完成缴费*/
    @KListener(R.id.publish_but)
    private void publish_butOnClick(){
        loadxg();
    }

    /**
     * billId	是	String	物业费Id
     * stateId
     */
    private void loadxg() {
        Utils.showLoad(this);
        String Kvs = "billId="+bean.getBillId() +"&stateId=1";
        Client.sendPost(NetParmet.USR_WYFY_JF, Kvs, new Handler(msg -> {
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
                Toast.makeText(this, "缴费成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }



    private void iniview() {
        if (AppValue.wyfBeans != null) {
            bean = AppValue.wyfBeans;
        }
        mtext_timer.setText(bean.getMonth());
        mtext_xqname.setText(bean.getBillName());
        if (bean.getStateId().equals("0")){
            mtext_zt.setText("未缴");
            mjf_time.setVisibility(View.GONE);
        }else {
            mjf_time.setVisibility(View.VISIBLE);
            mtext_money.setTextColor(getResources().getColor(R.color.app_call_option_text_color));
            mtext_zt.setText("已缴费");
            mtext_jf_types.setText("24:00");
            mpublish_but.setVisibility(View.GONE);
        }
        mtext_money.setText(bean.getTotalMoney());
        mtext_xq.setText(bean.getBuildingName()+bean.getUnitName()+bean.getNumberName());
        mtext_peic.setText(bean.getPrice());
        mtext_num.setText(bean.getNum());
        mtext_jf_type.setText(bean.getRemark());
    }

}
