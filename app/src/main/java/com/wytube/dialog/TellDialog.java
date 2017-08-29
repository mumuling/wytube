package com.wytube.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;

import static com.wytube.utlis.SipCore.mCalling;


public class TellDialog {
    private LinearLayout ll_mjtell;
    private LinearLayout ll_dhtell;
    private TextView tv_diss;
    private Dialog dialog1;

    public static void showTell(Activity mActivity, String phoneNumber) {
        new TellDialog(mActivity, phoneNumber);
    }

    public TellDialog(Activity mActivity, String phoneNumber) {
        tellDialog(mActivity, phoneNumber);
    }


    public void tellDialog(Activity mActivity, String phoneNumber) {
        dialog1 = tldialog(mActivity, phoneNumber);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.telldialog, null);
        ll_dhtell = (LinearLayout) view.findViewById(R.id.ll_dhtell);
        ll_mjtell = (LinearLayout) view.findViewById(R.id.ll_mjtell);
        RelativeLayout rlrelatype = (RelativeLayout) view.findViewById(R.id.rlrelatype);
        tv_diss = (TextView) view.findViewById(R.id.tv_diss);
        tv_diss.setOnClickListener(v -> dialog1.dismiss());
        rlrelatype.setOnClickListener(v -> dialog1.dismiss());
        dialog1.getWindow().setContentView(view);
        ll_dhtell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
            }
        });
        ll_mjtell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AppValue.MJphone = phoneNumber;
//                mActivity.startActivity(new Intent(mActivity, MainTabActivity.class));
                mCalling(phoneNumber);
            }
        });

    }

    private Dialog tldialog(Activity mActivity, String phoneNumber) {
        Rect bloco = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(bloco);
        Dialog dialog = new Dialog(mActivity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setDimAmount(0.3f);
        DisplayMetrics dm2 = mActivity.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = bloco.width(); //(int) (dm2.widthPixels); // 设置宽度
        lp.height = bloco.height(); //(int) (dm2.heightPixels-titleBarHeight); // 设置高度
        dialog.getWindow().setAttributes(lp);
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(bloco);
        return dialog;
    }

}
