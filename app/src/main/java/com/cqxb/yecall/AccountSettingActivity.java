package com.cqxb.yecall;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.dialog.DialogPublic;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import org.linphone.DialerFragment;
import org.linphone.LinphoneService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountSettingActivity extends BaseTitleActivity implements
        OnClickListener {

    private String path    = "";
    private String version = "";
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingInfo.init(getApplicationContext());
        setContentView(R.layout.activity_account_setting);
        setTitle("账户设置");


        findViewById(R.id.updatePwd).setOnClickListener(this);
        findViewById(R.id.callSetting).setOnClickListener(this);
        findViewById(R.id.callinSetting).setOnClickListener(this);
        findViewById(R.id.checkUpdate).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);
        findViewById(R.id.aboutus).setOnClickListener(this);
        findViewById(R.id.zzfw_zx).setOnClickListener(this);
    }

    public void openDoor(View v) {
        startActivity(new Intent(this, OpenDoorSettings.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.updatePwd) {
            if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
                DialerFragment.instance().justLogin(AccountSettingActivity.this);
            } else {
                startActivity(new Intent(AccountSettingActivity.this, ForgetPwdActivity.class).putExtra("loginOk", "loginOk"));
            }
        } else if (v.getId() == R.id.callSetting) {
            if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
                DialerFragment.instance().justLogin(AccountSettingActivity.this);
            } else {
                startActivity(new Intent(AccountSettingActivity.this, CallSettingActivity.class));
            }
        } else if (v.getId() == R.id.callinSetting) {
            if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
                DialerFragment.instance().justLogin(AccountSettingActivity.this);
            } else {
                startActivity(new Intent(AccountSettingActivity.this, CallinSettingActivity.class));
            }
        } else if (v.getId() == R.id.checkUpdate) {

            dialog = ProgressDialog.show(AccountSettingActivity.this, null, "请稍候..", true, true);
            dialog.show();
            new NetAction().checkVersion(SettingInfo.getParams(PreferenceBean.APPVERSIONS, getString(R.string.app_version)), getString(R.string.app_class), new OnMyResponseListener() {
                @Override
                public void onResponse(String jsonObject) {
                    if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                        JSONObject parseObject = JSONObject.parseObject(jsonObject);
                        if (CommReply.SUCCESS.equals(parseObject.getString("statuscode"))) {
                            handler.sendEmptyMessage(0);
                            path = parseObject.getString("url");
                            version = parseObject.getString("newversion");
                        } else {
                            T.show(getApplicationContext(), "" + parseObject.getString("reason"), 0);
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        T.show(getApplicationContext(), getString(R.string.service_error), 0);
                        handler.sendEmptyMessage(1);
                    }
                }
            }).execm();
        } else if (v.getId() == R.id.exit) {
            SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
            SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
            if (LinphoneService.instance() != null) LinphoneService.instance().deleteOldAccount();
            dialog = ProgressDialog.show(AccountSettingActivity.this, null, "正在退出。。。");
            dialog.dismiss();
            handler.sendEmptyMessage(2);
        } else if (v.getId() == R.id.aboutus) {
            startActivity(new Intent(AccountSettingActivity.this, ProtocolActivity.class));
        } else if (v.getId() == R.id.zzfw_zx) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://web.123667.com/eus/")));
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (dialog != null) dialog.dismiss();
                    String vs = SettingInfo.getParams(PreferenceBean.APPVERSIONS, getString(R.string.app_version));

                    Log.w("version", "服务器：" + version);
                    Log.w("version", "本地：" + vs);

                    if (format(vs) >= format(version)) {
                        final DialogPublic dlg = new DialogPublic(AccountSettingActivity.this, "版本升级", "您当前的版本已是最新版本!", true);
                        dlg.show();
                        dlg.setOKBtn("确定", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dlg.dismiss();
                            }
                        });
                    } else {
                        final DialogPublic dlg = new DialogPublic(AccountSettingActivity.this, "版本升级", version, true);
                        dlg.show();
                        dlg.setOKBtn("下载", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SettingInfo.setParams(PreferenceBean.APPVERSIONS, version);
                                Uri    uri    = Uri.parse("http://web.123667.com/yecall/appfile/yecall_v1.0.apk");
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.setData(uri);
                                startActivity(intent);
                                dlg.dismiss();
                            }
                        });
                    }
                    break;
                case 1:
                    if (dialog != null) dialog.dismiss();
                    break;
                case 2:
                    if (dialog != null) dialog.dismiss();
//				LinphoneActivity.instance().exit();
//				finish();//也可以直接返回 这条不能要！
//				YETApplication.getinstant().exit();
                    SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
                    SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, "");
                    SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, "");
                    SettingInfo.setLinphoneAccount("");
                    SettingInfo.setLinphonePassword("");
                    SettingInfo.setPassword("");
                    SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");

                    startActivity(new Intent(AccountSettingActivity.this, LoginAppActivity.class));
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 去除字符串中的符号
     *
     * @param str 字符串
     * @return 返回格式化的字符串
     */
    public static int format(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("").replaceAll("V", "").replaceAll("\\.", "");
        }
        return Integer.parseInt(dest);
    }
}
