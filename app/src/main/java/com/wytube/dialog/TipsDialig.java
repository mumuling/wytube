package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cqxb.yecall.LoginAppActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import org.linphone.LinphoneService;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/22.
 * 类 描 述:
 */

public class TipsDialig {
    private Context mContext;
    private Dialog mDialog;
    View viewDialog;
    public static TipsDialig tipsDialig;

    public TipsDialig(Context context)
    {
        mContext = context;
    }

    public Dialog loadDialog()
    {
        mDialog = new Dialog(mContext, R.style.dialog1);
        LayoutInflater in = LayoutInflater.from(mContext);
        viewDialog = in.inflate(R.layout.activity_ok_cancel_dialog, null);
        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        viewDialog.findViewById(R.id.cancel_but).setOnClickListener(v -> {mDialog.dismiss();});
        viewDialog.findViewById(R.id.ok_but).setOnClickListener(v -> {
            SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
            SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
            if (LinphoneService.instance() != null) LinphoneService.instance().deleteOldAccount();
            SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
            SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, "");
            SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, "");
            SettingInfo.setLinphoneAccount("");
            SettingInfo.setLinphonePassword("");
            SettingInfo.setPassword("");
            SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
            mContext.startActivity(new Intent(mContext, LoginAppActivity.class));
            mDialog.dismiss();});
        return mDialog;
    }

    public void removeDialog()
    {
        mDialog.dismiss();
    }
}
