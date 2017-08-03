package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.LoginAppActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.utlis.AppValue;

import org.linphone.LinphoneService;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/28.
 * 类 描 述:
 */

@KActivity(R.layout.my_menu)
public class MyActivity extends FragmentActivity {
    @KBind(R.id.relative_xg)
    private RelativeLayout mRelativeXg;
    @KBind(R.id.relative_mj)
    private RelativeLayout mRelativeMj;
    @KBind(R.id.relative_qh)
    private RelativeLayout mRelativeQh;
    @KBind(R.id.text_name)
    private TextView mtext_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    private void iniview() {
        mtext_name.setText(AppValue.TextName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniview();
    }

    /*修改密码*/
    @KListener(R.id.relative_xg)
    private void relative_xgOnClick() {

    }

    /*门禁账号*/
    @KListener(R.id.relative_mj)
    private void relative_mjOnClick() {

    }

    /*切换账号*/
    @KListener(R.id.relative_qh)
    private void relative_qhOnClick() {
        AppValue.btl = 1;
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
        startActivity(new Intent(this, LoginAppActivity.class));
    }

    private long exitTime = 0;// 退出程序时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MyActivity.this, "再按一次结束程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
//                System.exit(0);
                sendBroadcast(new Intent(Smack.action).putExtra("backMune",
                        "backMune"));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
