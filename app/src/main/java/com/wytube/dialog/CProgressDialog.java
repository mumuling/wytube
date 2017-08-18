package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cqxb.yecall.R;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/22.
 * 类 描 述:
 */

public class CProgressDialog {

    private Context mContext;
    private Dialog mDialog;
    public static CProgressDialog Progress;

    public CProgressDialog(Context context) {
        mContext = context;
    }

    public Dialog loadDialog() {
        mDialog = new Dialog(mContext, R.style.dialog);
        LayoutInflater in = LayoutInflater.from(mContext);
        View viewDialog = in.inflate(R.layout.progress_dialog, null);
        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        /*触摸屏幕其它区域，就会让这个progressDialog消失  消失true反之false*/
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        mDialog.dismiss();
    }
}
