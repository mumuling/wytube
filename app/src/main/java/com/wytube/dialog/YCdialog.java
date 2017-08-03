package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cqxb.yecall.R;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/22.
 * 类 描 述:
 */

public class YCdialog {
    private Context mContext;
    private Dialog mDialog;
    View viewDialog;
    public static YCdialog ycdialog;

    public YCdialog(Context context)
    {
        mContext = context;
    }

    public Dialog loadDialog(String text)
    {
        mDialog = new Dialog(mContext, R.style.dialog1);
        LayoutInflater in = LayoutInflater.from(mContext);
        viewDialog = in.inflate(R.layout.activity_okdialog, null);
        TextView tips = (TextView) viewDialog.findViewById(R.id.tips);
        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        viewDialog.findViewById(R.id.ok_but).setOnClickListener(v -> {mDialog.dismiss();});
        if (text != null) {
            tips.setText(text);
        }
        return mDialog;
    }

    public void removeDialog()
    {
        mDialog.dismiss();
    }
}
