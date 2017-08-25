package com.cqxb.yecall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.param.AuthCodeRequest;
import com.android.action.param.CommReply;
import com.cqxb.yecall.bean.AuthCodeBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.T;
import com.wytube.net.Client;
import com.wytube.net.NetParmet;
import com.wytube.utlis.Utils;

import static com.cqxb.yecall.R.id.getCode;

public class RegistUserVoiceActivity extends BaseTitleActivity implements OnClickListener {

    private LinearLayout layout1;
    private EditText editText1,edi_phone, editText3, editText4, yanzhengma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_zc);
        setTitleVisiable(View.GONE);
        // 初始化参数
        editText1 = (EditText) findViewById(R.id.editTextPhone);
        edi_phone = (EditText) findViewById(R.id.edi_phone);
        editText3 = (EditText) findViewById(R.id.editTextPwd);
        editText4 = (EditText) findViewById(R.id.editTextpwd2);

        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        layout1 = (LinearLayout) findViewById(R.id.formRegist_layout);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void regsitOk() {

        if (!isMobileNO(edi_phone.getText().toString())) {
            T.show(getApplicationContext(), "请输入正确的手机号", 1);
            return;
        }

        if (editText1.getText().toString().length() <= 0) {
            T.show(getApplicationContext(), "请输入账号", 1);
            return;
        }
        if (editText1.getText().toString().length() != 11) {
            T.show(getApplicationContext(), "手机号为11位，请检查你的输入！", 0);
            return;
        }
        if (editText3.getText().toString().length() <= 0) {
            T.show(getApplicationContext(), "请输入密码", 1);
            return;
        }
        if (editText3.getText().toString().length() < 6) {
            T.show(getApplicationContext(), "密码至少6位", 1);
            return;
        }
        if (!editText3.getText().toString()
                .equals(editText4.getText().toString())) {
            T.show(getApplicationContext(), "两次输入的密码不一致", 1);
            return;
        }
        logzhuce(editText1.getText().toString(),editText3.getText().toString());
    }

    /**
     * 注册接口
     */
    private void logzhuce(String phone,String pass) {
        Utils.showLoad(this);
        String keyValue = "phone=" + phone+ "&pass="+pass;
        Client.sendPost(NetParmet.USR_ZHUCE, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            Log.i("-----注册-----", json);
//            Basezhece bean = Json.toObject(json, Basezhece.class);
//            if (bean == null) {
//                Utils.showNetErrorDialog(this);
//                return false;
//            }
//            if (!bean.isSuccess()) {
//                Utils.showOkDialog(this, "该账号已被注册使用,请重新输入手机号! ");
//                return false;
//            }
            editText1.setText("");
            editText3.setText("");
            editText4.setText("");
            Intent intent = new Intent();
            intent.putExtra("phone",editText1.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
//            Toast.makeText(this, "注册成功!", Toast.LENGTH_SHORT).show();
            return false;
        }));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getCode) {
            if (editText1.getText().toString().length() <= 0) {
                T.show(getApplicationContext(), "请输入账号", 1);
                return;
            }
            if (editText1.getText().toString().length() < 11) {
                T.show(getApplicationContext(), "手机号最小长度为11个字符！", 0);
                return;
            }
            if (editText1.getText().toString().length() > 11) {
                T.show(getApplicationContext(), "手机号最大长度为11个字符！", 0);
                return;
            }

            T.show(RegistUserVoiceActivity.this, "请稍候", 0);
            AuthCodeRequest authCode = new AuthCodeRequest();
            authCode.setAccount(editText1.getText().toString());
            authCode.setVersion(YETApplication.appClass);
            authCode.setType("0");
            new NetAction().authCode(authCode, jsonObject -> {
                if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                    AuthCodeBean bean = JSONObject.parseObject(jsonObject, AuthCodeBean.class);
                    if (CommReply.SUCCESS.equals(bean.getStatuscode())) {
                        yanzhengma.setText("");
                    } else {
                        T.show(getApplicationContext(), bean.getReason(), 0);
                        yanzhengma.setText("");
                    }
                } else {
                    T.show(getApplicationContext(), getString(R.string.service_error), 0);
                    yanzhengma.setText("");
                }
            }).execm();
        }
    }


    //密码的查看与隐藏
    private void passwordStatus(CheckBox checkBox, EditText eText) {

        if (checkBox.isChecked()) {
            eText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            eText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        CharSequence charSequence = eText.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

}
