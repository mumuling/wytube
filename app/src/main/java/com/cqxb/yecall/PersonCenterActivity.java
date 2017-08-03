package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import org.linphone.DialerFragment;
import org.linphone.LinphoneActivity;

public class PersonCenterActivity extends BaseTitleActivity implements
        OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        setTitle("个人中心");
        findViewById(R.id.balance).setOnClickListener(this);
        findViewById(R.id.reCharge).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.balance) {
            if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
                DialerFragment.instance().justLogin(PersonCenterActivity.this);
            } else {
                startActivity(new Intent(PersonCenterActivity.this, BalanceActivity.class));
            }
        } else if (v.getId() == R.id.reCharge) {
            if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
                DialerFragment.instance().justLogin(PersonCenterActivity.this);
            } else {
                startActivity(new Intent(PersonCenterActivity.this, ChargeActivity.class));
            }
        } else if (v.getId() == R.id.aboutus) {
            startActivity(new Intent(PersonCenterActivity.this, ProtocolActivity.class));
        }
    }


    private ProgressDialog progressDlg;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendBroadcast(new Intent(Smack.action).putExtra("backMune", "backMune"));
        }
//		LinphoneUtils.onKeyBackGoHome(this, keyCode, event);
        return super.onKeyDown(keyCode, event);
    }


    /**
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     * if (keyCode == KeyEvent.KEYCODE_BACK) {
     * // 创建退出对话框
     * AlertDialog.Builder isExit = new AlertDialog.Builder(this);
     * // 设置对话框标题
     * isExit.setTitle("系统提示");
     * // 设置对话框消息
     * isExit.setMessage("确定要退出吗");
     * // 添加选择按钮并注册监听
     * isExit.setPositiveButton("确认", new DialogInterface.OnClickListener() {
     *
     * @Override public void onClick(DialogInterface dialog, int which) {
     * progressDlg=ProgressDialog.show(PersonCenterActivity.this, null, "正在退出。。。");
     * dialog.dismiss();
     * handler.sendEmptyMessage(999999);
     * }
     * });
     * isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
     * @Override public void onClick(DialogInterface dialog, int which) {
     * dialog.dismiss();
     * }
     * });
     * // 显示对话框
     * isExit.show();
     * }
     * return super.onKeyDown(keyCode, event);
     * }
     **/
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 999999) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressDlg.dismiss();
                        LinphoneActivity.instance().exit();
                        finish();
                        YETApplication.getinstant().exit();
                        SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
                        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
                    }
                }).start();

            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
    }


}
