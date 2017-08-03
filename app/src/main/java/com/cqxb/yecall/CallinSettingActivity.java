package com.cqxb.yecall;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.CallInParams;
import com.android.action.param.CallInRequest;
import com.android.action.param.CallInResponse;
import com.android.action.param.CommReply;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;


public class CallinSettingActivity extends BaseTitleActivity {

    private LinearLayout ll_callin_custom_contain;
    private ToggleButton tb_set_callin_allTransfer,
            tb_set_callin_customTransfer;

    private boolean isEnabledDNS = false;
    private boolean isEnabledAllTransfer = false;
    private boolean isEnabledCustomTransfer = false;

    private EditText et_callin_allTransfer, et_callin_gprsTransfer,
            et_callin_noPersonTransfer, et_callin_busyTransfer,
            et_callin_unconnectTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callin_setting);
        setTitle("呼入设置");
        setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tb_set_callin_allTransfer = (ToggleButton) findViewById(R.id.tb_set_callin_allTransfer);
        tb_set_callin_customTransfer = (ToggleButton) findViewById(R.id.tb_set_callin_customTransfer);

        ll_callin_custom_contain = (LinearLayout) findViewById(R.id.ll_callin_custom_contain);

        et_callin_allTransfer = (EditText) findViewById(R.id.et_callin_allTransfer);

        et_callin_gprsTransfer = (EditText) findViewById(R.id.et_callin_gprsTransfer);
        et_callin_noPersonTransfer = (EditText) findViewById(R.id.et_callin_noPersonTransfer);
        et_callin_busyTransfer = (EditText) findViewById(R.id.et_callin_busyTransfer);
        et_callin_unconnectTransfer = (EditText) findViewById(R.id.et_callin_unconnectTransfer);

        CallInParams getCallIn = new CallInParams();
        new NetAction().getCallInInfo(getCallIn, new OnMyResponseListener() {
            @Override
            public void onResponse(String jsonObject) {
                if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                    CallInResponse getCallIn = JSONObject.parseObject(
                            jsonObject.toString(), CallInResponse.class);
                    if (CommReply.SUCCESS.equals(getCallIn.getStatusCode())) {

                        isEnabledDNS = "YES".equals(getCallIn.getDnd()) ? true
                                : false;
                        isEnabledAllTransfer = "YES".equals(getCallIn
                                .getXfersetting()) ? true : false;
                        isEnabledCustomTransfer = "YES".equals(getCallIn
                                .getCustomSetting()) ? true : false;

                        tb_set_callin_allTransfer
                                .setChecked(isEnabledAllTransfer);
                        tb_set_callin_customTransfer
                                .setChecked(isEnabledCustomTransfer);

                        et_callin_allTransfer.setText(getCallIn.getXferTo());

                        et_callin_gprsTransfer.setText(getCallIn.getWlanTo());
                        et_callin_noPersonTransfer.setText(getCallIn
                                .getNoAnswerTo());
                        et_callin_busyTransfer.setText(getCallIn.getBusyTo());
                        et_callin_unconnectTransfer.setText(getCallIn
                                .getNoResponseTo());

                        if (tb_set_callin_customTransfer.isChecked()) {
                            ll_callin_custom_contain
                                    .setVisibility(View.VISIBLE);
                            et_callin_allTransfer.setVisibility(View.GONE);
                        }
                        if (tb_set_callin_allTransfer.isChecked()) {
                            ll_callin_custom_contain.setVisibility(View.GONE);
                            et_callin_allTransfer.setVisibility(View.VISIBLE);
                        }

                        // 设置本地缓存
                        SettingInfo.setParams(PreferenceBean.CALLINDND,
                                isEnabledDNS + "");
                        SettingInfo.setParams(PreferenceBean.CALLINALLTRANSFER,
                                isEnabledAllTransfer + "");
                        SettingInfo.setParams(PreferenceBean.CALLINCUSTOM,
                                isEnabledCustomTransfer + "");

                        SettingInfo.setParams(
                                PreferenceBean.CALLINALLTRANSFERNUMBER,
                                getCallIn.getXferTo());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINGPRSTRANSFER,
                                getCallIn.getWlanTo());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINNOPERSONTRANSFER,
                                getCallIn.getNoAnswerTo());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINBUSYTRANSFER,
                                getCallIn.getBusyTo());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINUNCONNECTTRANSFER,
                                getCallIn.getNoResponseTo());
                    }
                }
            }
        }).execm();

        tb_set_callin_allTransfer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    et_callin_allTransfer.setVisibility(View.VISIBLE);
                    ll_callin_custom_contain.setVisibility(View.GONE);
                    tb_set_callin_customTransfer.setChecked(!isChecked);
                } else {
                    et_callin_allTransfer.setVisibility(View.GONE);
                }
                isEnabledAllTransfer = isChecked;
            }
        });

        tb_set_callin_customTransfer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    ll_callin_custom_contain
                            .setVisibility(View.VISIBLE);
                    et_callin_allTransfer.setVisibility(View.GONE);
                    tb_set_callin_allTransfer.setChecked(!isChecked);
                } else {
                    ll_callin_custom_contain.setVisibility(View.GONE);
                }
                isEnabledCustomTransfer = isChecked;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        boolean isChanged = false;
        CallInRequest setCallIn = new CallInRequest();

        setCallIn.setXfersetting(isEnabledAllTransfer ? "YES" : "NO");
        setCallIn.setCustomSetting(isEnabledCustomTransfer ? "YES" : "NO");
        setCallIn.setDnd(isEnabledDNS ? "YES" : "NO");

        if (isEnabledAllTransfer != Boolean.parseBoolean(SettingInfo.getParams(
                PreferenceBean.CALLINALLTRANSFER, ""))) {
            isChanged = true;
        }
        if (isEnabledCustomTransfer != Boolean.parseBoolean(SettingInfo
                .getParams(PreferenceBean.CALLINCUSTOM, ""))) {
            isChanged = true;
        }
        if (isEnabledAllTransfer
                && !(et_callin_allTransfer.getText().toString().trim())
                .equals(SettingInfo.getParams(
                        PreferenceBean.CALLINALLTRANSFERNUMBER, ""))) {
            setCallIn.setXferTo(et_callin_allTransfer.getText().toString()
                    .trim());
            isChanged = true;
        } else {
            setCallIn.setXferTo(SettingInfo.getParams(
                    PreferenceBean.CALLINALLTRANSFERNUMBER, ""));
        }
        if (isEnabledCustomTransfer
                && (!(et_callin_noPersonTransfer.getText().toString().trim())
                .equals(SettingInfo.getParams(
                        PreferenceBean.CALLINNOPERSONTRANSFER, ""))
                || !(et_callin_unconnectTransfer.getText().toString()
                .trim()).equals(SettingInfo.getParams(
                PreferenceBean.CALLINUNCONNECTTRANSFER, ""))
                || !(et_callin_gprsTransfer.getText().toString().trim())
                .equals(SettingInfo.getParams(
                        PreferenceBean.CALLINGPRSTRANSFER, "")) || !(et_callin_busyTransfer
                .getText().toString().trim()).equals(SettingInfo
                .getParams(PreferenceBean.CALLINBUSYTRANSFER, "")))) {
            setCallIn.setNoAnswerTo(et_callin_noPersonTransfer.getText()
                    .toString().trim());
            setCallIn.setNoResponseTo(et_callin_unconnectTransfer.getText()
                    .toString().trim());
            setCallIn.setWlanTo(et_callin_gprsTransfer.getText().toString()
                    .trim());
            setCallIn.setBusyTo(et_callin_busyTransfer.getText().toString()
                    .trim());
            isChanged = true;
        } else {
            setCallIn.setNoAnswerTo(SettingInfo.getParams(
                    PreferenceBean.CALLINNOPERSONTRANSFER, ""));
            setCallIn.setNoResponseTo(SettingInfo.getParams(
                    PreferenceBean.CALLINUNCONNECTTRANSFER, ""));
            setCallIn.setWlanTo(SettingInfo.getParams(
                    PreferenceBean.CALLINGPRSTRANSFER, ""));
            setCallIn.setBusyTo(SettingInfo.getParams(
                    PreferenceBean.CALLINBUSYTRANSFER, ""));
        }

        if (isChanged) {
            new NetAction().setCallInInfo(setCallIn, new OnResponseListener() {
                @Override
                public void onResponse(String statusCode, CommReply reply) {
                    if (CommReply.SUCCESS.equals(statusCode)) {

                        // 设置本地缓存
                        SettingInfo.setParams(PreferenceBean.CALLINDND,
                                isEnabledDNS + "");
                        SettingInfo.setParams(PreferenceBean.CALLINALLTRANSFER,
                                isEnabledAllTransfer + "");
                        SettingInfo.setParams(PreferenceBean.CALLINCUSTOM,
                                isEnabledCustomTransfer + "");

                        SettingInfo.setParams(
                                PreferenceBean.CALLINALLTRANSFERNUMBER,
                                et_callin_allTransfer.getText().toString()
                                        .trim());

                        SettingInfo.setParams(
                                PreferenceBean.CALLINGPRSTRANSFER,
                                et_callin_gprsTransfer.getText().toString()
                                        .trim());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINNOPERSONTRANSFER,
                                et_callin_noPersonTransfer.getText().toString()
                                        .trim());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINBUSYTRANSFER,
                                et_callin_busyTransfer.getText().toString()
                                        .trim());
                        SettingInfo.setParams(
                                PreferenceBean.CALLINUNCONNECTTRANSFER,
                                et_callin_unconnectTransfer.getText()
                                        .toString().trim());

                        T.show(getApplicationContext(), "修改成功", 0);
                    } else {
                        T.show(getApplicationContext(), "修改失败", 0);
                    }
                }
            }).exec();
        }
    }
}
