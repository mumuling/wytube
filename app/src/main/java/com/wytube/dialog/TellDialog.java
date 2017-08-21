package com.wytube.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;


public class TellDialog extends Dialog {
    private LinearLayout ll_mjtell;
    private LinearLayout ll_dhtell;
    private TextView tv_diss;
    private String mPhoneNumber;
    private Activity mActivity;
    private String number = "";

    public TellDialog(@NonNull Context context, String mPhoneNumber) {
        super(context);
        initView();
        mActivity = (Activity) context;
        this.mPhoneNumber = mPhoneNumber;
    }

    public TellDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected TellDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        setContentView(R.layout.telldialog);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
        ll_mjtell = (LinearLayout) findViewById(R.id.ll_mjtell);
        ll_dhtell = (LinearLayout) findViewById(R.id.ll_dhtell);
        tv_diss = (TextView) findViewById(R.id.tv_diss);
        tv_diss.setOnClickListener(v -> dismiss());

        //门禁
        ll_mjtell.setOnClickListener(v -> {

        });
        //电话
        ll_dhtell.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);
        });
    }


}
