package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.HappyAdapter;
import com.wytube.beans.BaseOK;
import com.wytube.beans.BasesOK;
import com.wytube.beans.HappyBean;
import com.wytube.dialog.TellDialog;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import static com.cqxb.yecall.R.drawable.jyxs_ztz2;

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
    @KBind(R.id.text_wx_name)
    private TextView tv_name_number;
    @KBind(R.id.tv_address)
    private TextView tv_address;
    @KBind(R.id.tv_delete)
    private TextView tv_delete;
    @KBind(R.id.tv_agree)
    private TextView tv_agree;
    private HappyBean.DataBean dataBean;
    @KBind(R.id.iv_dsl)
    private ImageView mIvDsl;
    @KBind(R.id.iv_ytg)
    private ImageView mIvYtg;
    @KBind(R.id.rl_del)
    private TextView rl_del;
    @KBind(R.id.tv_nook)
    private TextView tv_nook;
    private HappyAdapter adapter;

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
            mIvDsl.setImageResource(jyxs_ztz2);
            mIvYtg.setImageResource(R.drawable.yd_sele);
            ll_foot.setVisibility(View.VISIBLE);
            rl_del.setVisibility(View.GONE);

        } else {
            tv_wait.setTextColor(getResources().getColor(R.color.text_default));
            tv_cross.setTextColor(getResources().getColor(R.color.blue));
            mIvDsl.setImageResource(R.drawable.yd_sele);
            mIvYtg.setImageResource(jyxs_ztz2);
            ll_foot.setVisibility(View.GONE);
            rl_del.setVisibility(View.VISIBLE);
        }
        tv_reason.setText(dataBean.getContent());
        tv_name_number.setText(dataBean.getRegUserName() + "(" + dataBean.getRegUserPhone() + ")");
        tv_address.setText(dataBean.getCellName() + dataBean.getBuildingName() + dataBean.getUnitName() + dataBean.getNumberName());
    }

    @KListener(R.id.call_but_bx)
    private void call_but_bxOnClick() {
        TellDialog.showTell(ApplyDetailActivity.this,dataBean.getRegUserPhone());
    }

    /*
    * 喜事删除
    * */
    @KListener(R.id.rl_del)
    private void rl_delOnclick() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY_DELETE, "celebrationId=" + dataBean.getCelebrationId(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BasesOK bean = Json.toObject(json, BasesOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMsg());
            }else {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


    //删除喜事列表
    @KListener(R.id.tv_delete)
    private void deleteOnClick() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY_DELETE, "celebrationId=" + dataBean.getCelebrationId(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


    /*
    * 同意喜事
    * */
    int stateId = 1;

    @KListener(R.id.tv_agree)
    private void tv_agreeOnClick() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY_EDIT, "celebrationId=" + dataBean.getCelebrationId() + "&stateId=" + stateId, new Handler(msg -> {
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
            } else {
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


    /*
    * 拒绝驳回喜事
    * */
    int stateId2 = 2;

    @KListener(R.id.tv_nook)
    private void tv_nookClick() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY_EDIT, "celebrationId=" + dataBean.getCelebrationId() + "&stateId=" + stateId2, new Handler(msg -> {
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
            } else {
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }

}
