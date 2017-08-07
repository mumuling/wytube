package com.wytube.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.cqxb.yecall.until.T;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.PropDialogAdapters;
import com.wytube.beans.BaseOK;
import com.wytube.beans.BaseZPry;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/4.
 * 类 描 述: 指派人员
 */
@KActivity(R.layout.activity_repair_zpry)
public class RepairZPActivity extends Activity {
    @KBind(R.id.repair_type)
    private TextView mrepair_type;
    @KBind(R.id.content_text)
    private TextView mcontent_text;

    private LayoutInflater inflater;
    private List<BaseZPry.DataBean> list;
    private Dialog dialog1;
    private ListView canshu_list;
    private BaseZPry bean;
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
    }

    /*选择指派人员*/
    @KListener(R.id.repair_type)
    private void repair_typeOnClick() {
        dalogcanshu();
    }

    /*确定*/
    @KListener(R.id.add_money_but)
    private void add_money_butOnClick() {
        if ("请选择".equals(mrepair_type.getText().toString())) {
            T.show(getApplicationContext(), "请选择指派人员！", 0);
            return;
        }
        if ("".equals(mcontent_text.getText().toString())) {
            T.show(getApplicationContext(), "请输入备注信息！", 0);
            return;
        }
        loadzpADDry(intent.getStringExtra("repairWorkId"),"1");
    }

    public Dialog dalog() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        inflater = LayoutInflater.from(RepairZPActivity.this);
        Dialog dialog = new Dialog(RepairZPActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setDimAmount(0.3f);
        DisplayMetrics dm2 = RepairZPActivity.this.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = frame.width(); //(int) (dm2.widthPixels); // 设置宽度
        lp.height = frame.height(); //(int) (dm2.heightPixels-titleBarHeight); // 设置高度
        dialog.getWindow().setAttributes(lp);
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return dialog;
    }

    /**
     * 选择指派人员
     */
    String repairmanId;
    public void dalogcanshu() {
        dialog1 = dalog();
        View view = inflater.inflate(R.layout.activity_repair_dilogtxt, null);
        canshu_list = (ListView) view.findViewById(R.id.canshu_list);
        RelativeLayout rela_djxs = (RelativeLayout) view.findViewById(R.id.rela_djxs);
        dialog1.getWindow().setContentView(view);
        rela_djxs.setOnClickListener(v -> dialog1.dismiss());
        loadzpry();
        canshu_list.setOnItemClickListener((parent, view1, position, id) -> {
            mrepair_type.setText(bean.getData().get(position).getRepairmanName());
            repairmanId = bean.getData().get(position).getRepairmanId();
            dialog1.dismiss();
        });
    }


    /**
     * 指派人员列表
     */
    private void loadzpry() {
        Client.sendPost(NetParmet.ADD_JZXQ_ZPRYLB, "", new Handler(msg -> {
            String json = msg.getData().getString("post");
            bean = Json.toObject(json, BaseZPry.class);
            list = bean.getData();
            PropDialogAdapters adapter = new PropDialogAdapters(this, list);
            canshu_list.setAdapter(adapter);
            return false;
        }));
    }

    /**
     * 指派人员确定
     * repairWorkId	是	String	服务ID
     * stateId	是	String	状态ID
     * repairmanId	是	String	指派人员ID
     * runContent	是	String	备注
     */
    private void loadzpADDry(String repairWorkId,String stateId) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.ADD_JZXQ_ADDZPRYQD, "repairWorkId="+repairWorkId+"&stateId="+stateId+"&repairmanId="+repairmanId+
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
