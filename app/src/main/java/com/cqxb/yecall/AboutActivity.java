package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AboutActivity extends BaseTitleActivity implements OnClickListener {

    private ProgressDialog dialog;
    private String path = "";
    private String version = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuneasy_about);
        setTitle("关于云翌电话");
        setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.checkUpdate).setOnClickListener(this);
        findViewById(R.id.allow).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checkUpdate) {
//			T.show(getApplicationContext(), "检查更新", 0);
            dialog = ProgressDialog.show(AboutActivity.this, "", "请稍候..");
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
        } else if (v.getId() == R.id.allow) {
            startActivity(new Intent(AboutActivity.this, ProtocolActivity.class));
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
                    if (format(vs) >= format(version)) {
                        final DialogPublic dlg = new DialogPublic(AboutActivity.this, "版本升级", "您当前的版本已是最新版本!", true);
                        dlg.show();
                        dlg.setOKBtn("确定", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dlg.dismiss();
                            }
                        });
                    } else {
                        final DialogPublic dlg = new DialogPublic(AboutActivity.this, "版本升级", version, true);
                        dlg.show();
                        dlg.setOKBtn("下载", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SettingInfo.setParams(PreferenceBean.APPVERSIONS, version);
                                // TODO Auto-generated method stub
                                Uri uri = Uri.parse(path);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);

                                dlg.dismiss();
                            }
                        });
                    }
                    break;
                case 1:
                    if (dialog != null) dialog.dismiss();
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
