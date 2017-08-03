package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.wytube.utlis.AppValue;

import java.util.Objects;


public class VersionActivity {
    private Context mContext;
    private Dialog mDialog;
    View viewDialog;
    public static VersionActivity Version;

    public VersionActivity(Context context)
    {
        mContext = context;
    }

    public Dialog loadDialog()
    {
        mDialog = new Dialog(mContext, R.style.dialog1);
        LayoutInflater in = LayoutInflater.from(mContext);
        viewDialog = in.inflate(R.layout.activity_version, null);
        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        viewDialog.findViewById(R.id.cancel_but).setOnClickListener(v -> {
            /*截取前两个字符串*/
            String one =  String.valueOf(AppValue.localVersion);
            String two = String.valueOf(AppValue.serverVersion);
            one = one.substring(0,2);
            two = two.substring(0,2);
            if (Objects.equals(one, two)){
            /*小版本*/
                mDialog.dismiss();
            }else {
            /*大版本*/
                showToast(mContext,"改动较大建议升级");
            }

        });
//        viewDialog.findViewById(R.id.ok_but).setOnClickListener(v -> {
//            Intent intent = new Intent();
//            intent.setData(Uri.parse(AppValue.versionUrl));
//            intent.setAction(Intent.ACTION_VIEW);
//            mContext.startActivity(intent);
//            mDialog.dismiss();});
        return mDialog;
    }

    public void removeDialog()
    {
        mDialog.dismiss();
    }


    private static Toast toast;
    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
