package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.until.ACache;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PhoneCallAuthUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.wytube.activity.OrderActivity;
import com.wytube.beans.BaseLogin;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.ShareOption;
import com.wytube.utlis.Utils;

import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import java.util.List;

import static android.content.Intent.ACTION_MAIN;

public class LoginAppActivity extends BaseTitleActivity implements OnClickListener {
    private LinearLayout formlogin_layout1, loginPage;
    private EditText ediphonenum, edipwd;
    private String TAG = "LoginActivity";
    //    private Button registUser;
    private CardView loginButton;
    private TextView forgetPwd;
    private ProgressDialog dialog;
    private CheckBox psdStatus;
    private RelativeLayout rela_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String loginFlag = SettingInfo.getParams(PreferenceBean.LOGINFLAG, "");
        if (!"".equals(loginFlag)) {
            handler.sendEmptyMessage(0);
        }

        setContentView(R.layout.activity_applogins);
//        setTitle("登录");
        /*隐藏标题*/
        setTitleVisiable(View.GONE);
        //标题返回箭头
//        setCustomLeftBtn(R.drawable.fanhui, v -> finish());
        //密码的显示状态
        psdStatus = (CheckBox) findViewById(R.id.login_psd_status);
        //页面
        formlogin_layout1 = (LinearLayout) findViewById(R.id.formlogin_layout1);
        loginPage = (LinearLayout) findViewById(R.id.loginPage);
        //文本框
        ediphonenum = (EditText) findViewById(R.id.ediphonenum);
        edipwd = (EditText) findViewById(R.id.edipwd);
        rela_title = (RelativeLayout) findViewById(R.id.rela_title);
        //按钮
        loginButton = (CardView) findViewById(R.id.loginButton);
//        registUser = (Button) findViewById(R.id.registUser);
        //忘记密码
        forgetPwd = (TextView) findViewById(R.id.forgetPwd);
        if (AppValue.btl == 1){
            rela_title.setVisibility(View.VISIBLE);
            AppValue.btl = -1;
        }
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        //设置点击事件
        loginButton.setOnClickListener(this);
//        registUser.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        psdStatus.setOnClickListener(this);
        //赋值
        ediphonenum.setText(SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""));
        edipwd.setText(SettingInfo.getParams(PreferenceBean.USERPWD, ""));
        //初始化context
        YETApplication.instanceContext(getApplicationContext());
        //初始化存储
        SettingInfo.init(getApplicationContext());
        //初始化来电状态
        new PhoneCallAuthUtil().callState();
        Log.i("", "LOGINFLAG=[" + BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.LOGINFLAG, "")) + "]");
        if (!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEIP, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEPORT, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEACCOUNT, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEPWD, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERPWD, ""))) &&
                !"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.LOGINFLAG, "")))
                ) {
            startActivity(new Intent(LoginAppActivity.this, OrderActivity.class));
        }
//		new NotificationUtil().setNotification(0, LoginAppActivity.class, R.drawable.tb144_144, getString(R.string.app_name),"未注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //compalete完成后获取得到的组织架构和配置自己的im以及sip
                    if (AppValue.onec==1 ){
                        Intent intent = new Intent(LoginAppActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (AppValue.HhGq==1){
                        Intent intent = new Intent(LoginAppActivity.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        AppValue.HhGq=-1;
                    }else {
                        finish();
                    }

                    //formlogin_layout1.setVisibility(View.GONE);
                    break;
                case 1:
                    loginPage.setVisibility(View.VISIBLE);
                    formlogin_layout1.setVisibility(View.GONE);
                    break;
                case 999999:
                    try {
                        if (dialog != null) dialog.dismiss();
                        exit();
                        YETApplication.getinstant().exit();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            try {
//                dialog = ProgressDialog.show(LoginAppActivity.this, "", "正在退出...");
//                dialog.show();
//                handler.sendEmptyMessage(999999);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return true;
    }

    public void exit() {
        refreshStatus(OnlineStatus.Offline);
        finish();
        stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
    }

    private void refreshStatus(OnlineStatus status) {
        if (LinphoneManager.isInstanciated()) {
            LinphoneManager.getLcIfManagerNotDestroyedOrNull().setPresenceInfo(0, "", status);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.loginButton) {
                if ("".equals(ediphonenum.getText().toString())) {
                    T.show(getApplicationContext(), "请输入手机号！", 0);
                    return;
                }
                if ("".equals(edipwd.getText().toString())) {
                    T.show(getApplicationContext(), "请输入密码！", 0);
                    return;
                }
                if (edipwd.getText().length() < 6) {
                    T.show(getApplicationContext(), "密码不小于6位！", 0);
                    return;
                }
                loginPage.setVisibility(View.GONE);
                formlogin_layout1.setVisibility(View.VISIBLE);
                SettingInfo.setParams(PreferenceBean.USERACCOUNT, ediphonenum.getText().toString());
                SettingInfo.setParams(PreferenceBean.USERPWD, edipwd.getText().toString());
                SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
                /**智慧之家登录*/
                userLogin(ediphonenum.getText().toString(), edipwd.getText().toString());
            }
//            else if (v.getId() == R.id.registUser) {
//                //跳转到激活页面
//                Intent intent = new Intent(this, RegistUserVoiceActivity.class);
//                startActivity(intent);
//            }
            else if (v.getId() == R.id.forgetPwd) {
                //跳转到修改密码页面
                Intent forget = new Intent(this, ForgetPwdActivity.class);
                startActivity(forget);
            } else if (v.getId() == R.id.login_psd_status) {
                //密码明文显示与暗文显示切换
                passwordStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "异常：" + e.getLocalizedMessage());
        }
    }

    /**
     * 用户登录处理方法
     * @param usr 用户名
     * @param pwd 用户密码
     */
    private void userLogin(final String usr, final String pwd) {
        String keyValue = "account=" + usr + "&password=" + pwd;
        Client.sendPost(NetParmet.USR_LOGIN, keyValue, new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseLogin bean = Json.toObject(json, BaseLogin.class);
            if (bean == null) {
                Toast.makeText(this, "服务器异常!请稍后再试!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
            } else {
                AppValue.TextName = bean.getData().getUserDTO().getUserName();
                ACache mCache = ACache.get(LoginAppActivity.this);
                mCache.put("token", AppValue.token, 60*60*24*6);
                ShareOption.writerString("LOGIN_STATE", usr + ":" + pwd, LoginAppActivity.this);
            }
//            dataBean = bean.getData();
            if (bean.getCode()==200) {
                saveData(bean);
                SettingInfo.setParams(PreferenceBean.LOGINFLAG, "true");
                //sip 登录信息
                SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, "pro1.123667.com");
                SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, "5070");
                SettingInfo.setParams(PreferenceBean.USERNAME,bean.getData().getSip());
                SettingInfo.setLinphoneAccount(bean.getData().getSip());
                SettingInfo.setLinphonePassword(bean.getData().getSipPass());
                getCallLogs();
                handler.sendEmptyMessage(0);
            }else {
//                T.show(getApplicationContext(), "" + dataBean.getReason(), 0);
                handler.sendEmptyMessage(1);
            }
            AppValue.online = true;
            return false;
        }));
    }

    /* 保存数据
    * @param bean 服务器返回的JSON数据反序列化的对象
    */
    private void saveData(BaseLogin bean) {
        if (bean!=null){
            AppValue.token = bean.getData().getToken();
//            AppValue.sipName = bean.getData().getSipaccount();
//            AppValue.sipPass = bean.getData().getSippassword();
//            AppValue.sipAddr = bean.getData().getIpaddr();
//            AppValue.sipProt = bean.getData().getPort();
            AppValue.sipName = bean.getData().getSip();
            AppValue.sipPass = bean.getData().getSipPass();
            AppValue.sipAddr = "pro1.123667.com";
            AppValue.sipProt = "5070";
            AppValue.YhPass = edipwd.getText().toString();
        }
    }


    /**
     * 密码的查看与隐藏
     */
    private void passwordStatus() {
        if (psdStatus.isChecked()) {
            edipwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            edipwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        CharSequence charSequence = edipwd.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 获取通话记录
     */
    public void getCallLogs() {
        System.out.println("插入成功:::::::::::" + SettingInfo.getAccount());
        try {
            // 通话记录
            List<Contacts> clList = YETApplication.getinstant().getThjl();
            clList.clear();
            if (clList.size() <= 0) {
                ContactBase cb = new ContactBase(getApplicationContext());
                List<Contacts> allcontact = cb.getPhoneCallLists();
                YETApplication.getinstant().setThjl(allcontact);
            }
        } catch (Exception e) {
            Log.e("", "loginAppActivity  line 380 ===>>>  " + e.getLocalizedMessage());
        }

    }
}
