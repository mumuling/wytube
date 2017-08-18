package com.wytube.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.OwnerAdapter;
import com.wytube.beans.BaseWyOK;
import com.wytube.beans.OwnerBean;
import com.wytube.beans.OwnerDel;
import com.wytube.dialog.TellDialog;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import org.linphone.DialerFragment;
import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.ui.AddressText;

/**
 * Created by LIN on 2017/8/6.
 */
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
    @KBind(R.id.iv_delete)
    private ImageView iv_delete;
    Intent intent;
    private OwnerBean.DataBean mDatabean;
    private OwnerAdapter adapter;
    private TextWatcher watcher;
    private String number = "";

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
                TellDialog tellDialog = new TellDialog(OwnerItemActivity.this,ownerPhone.getText().toString());

                tellDialog.show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ownerPhone.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
    /**
     * 调用免费拨打
     * @param name 电话号码
     */
    public void mCalling(String name) {
        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            DialerFragment.instance().justLogin(this);
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            // LinphoneActivity.instance().startIncallActivity(null);
            SettingInfo.setParams(PreferenceBean.CALLNAME, name);
            SettingInfo.setParams(PreferenceBean.CALLPHONE, number);
            if (number.length() <= 11) {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
            } else {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(OwnerItemActivity.this,
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    startActivity(intent);
                }
            }).start();

            LinphoneManager.AddressType address = new AddressText(this, null);
            address.setDisplayedName(name);
            address.setText(number);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
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
            if (bean.isSuccess()) {//*true成功*//*
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                AppValue.fish = 1;
                this.finish();
            }
            return false;
        }));
    }


    /**
     * 删除业主
     * ownerId	是	String	业主ID
     */
     /*删除*/
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

    /**
     * 删除业主
     */
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
    }

}