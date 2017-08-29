package com.wytube.utlis;


import android.content.Intent;

import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.wytube.beans.YeCallBeans;
import com.wytube.net.NetParmet;

import org.linphone.DialerFragment;
import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.ui.AddressText;

import static com.cqxb.yecall.YETApplication.getContext;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/22.
 * 类 描 述:
 */

public class SipCore {
    /**
     * 向Sip服务器注册Sip账户
     *
     * @param sipUsr     sip用户名
     * @param sipPwd     sip密码
     * @param sipAddress sip代理服务器地址
     * @param sipProt    sip服务器端口
     */
    public static void registeredSipAccount(String sipUsr, String sipPwd, String sipAddress, String sipProt) {
        try {
            LinphoneCore core = SipCore.getCore();
            //代理服务器格式  sip:用户名@115.28.40.125:5070
            String identity = "sip:" + sipUsr + "@" + sipAddress + ":" + sipProt;//代理服务器地址
            String proxy = "sip:" + sipAddress + ":" + sipProt;
            //通过解析用户提供的地址（以字符串形式）来构造LinphoneAddress对象。
            LinphoneAddress identityAddr = LinphoneCoreFactory.instance().createLinphoneAddress(identity);
            LinphoneAddress proxyAddr = LinphoneCoreFactory.instance().createLinphoneAddress(proxy);
            //设置地址中的传输
            proxyAddr.setTransport(LinphoneAddress.TransportType.LinphoneTransportTcp);
            //身份，代理，路由，状态
            LinphoneProxyConfig config = core.createProxyConfig(identityAddr.asString(), proxyAddr.asStringUriOnly(), null, true);
            //设置注册过期时间
            config.setExpires(150);
            //指示AVPF/SAVPF是否必须用于使用此代理配置的呼叫。
            config.enableAvpf(false);
            //设置使用AVPF/SAVPF时常规RTCP报告之间的间隔。
            config.setAvpfRRInterval(0);
            //指示是否必须使用此代理配置的呼叫使用质量报告。
            config.enableQualityReporting(false);
            //设置收集器SIP URI以在使用质量报告时收集报告。
            config.setQualityReportingCollector(null);
            //在使用质量报告时，设置通话期间质量间隔报告之间的间隔。
            config.setQualityReportingInterval(0);
            //启用注册
            config.enableRegister(true);
            //用户名，字符串用户标识，密码，字符串ha1，字符串领域，字符串域
            LinphoneAuthInfo info = LinphoneCoreFactory.instance().createAuthInfo(sipUsr, null, sipPwd, null, null, sipAddress + ":" + sipProt);
            //添加代理配置
            core.addProxyConfig(config);
            //添加注册信息
            core.addAuthInfo(info);
            //设置默认代理
            core.setDefaultProxyConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取LinphoneCore对象
     *
     * @return 返回LinphoneCore对象
     */
    private static LinphoneCore getCore() {
        return LinphoneManager.getLc();
    }



    /*Linphone的消息发送地址*/
    private static String identy;

    /**
     * 设置Linphone消息发送地址
     *
     * @param sipUsr  sip账户
     * @param sipAddr sip地址
     * @param sipProt sip端口
     */
    public static void setIdenty(String sipUsr, String sipAddr, String sipProt) {
        SipCore.identy = "sip:" + sipUsr + "@" + sipAddr + ":" + sipProt;
    }

    /**
     * 发送文本消息
     * @param bean 消息内容
     */
    public static void sendMessage(YeCallBeans.DataBean bean) {
        LinphoneChatRoom room;
        try {
            setIdenty(bean.getSip().toString(), NetParmet.YURL,"5070");
            room = LinphoneManager.getLc().getOrCreateChatRoom(identy);
            room.sendMessage(bean.getSerial().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用免费拨打
     * @param name 电话号码
     */
    public static void mCalling(String name) {
        String number = "";

        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            DialerFragment.instance().justLogin(getContext());
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            // LinphoneActivity.instance().startIncallActivity(null);
            SettingInfo.setParams(PreferenceBean.CALLNAME, name);
            SettingInfo.setParams(PreferenceBean.CALLPHONE, name);
            if (name.length() <= 11) {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
            } else {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getContext(),
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    /*不写这句会报错*/
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            }).start();

            LinphoneManager.AddressType address = new AddressText(getContext(), null);
            address.setDisplayedName(name);
            address.setText(name);
//            LinphoneManager.getLc().enableVideo(true, true);
//            LinphoneManager.getLc().setVideoPolicy(true, true);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
    }
}
