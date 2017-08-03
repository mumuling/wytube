package com.android.action;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.NetBase.OnResponseListener;
import com.android.action.param.AdvertRequest;
import com.android.action.param.AuthCodeRequest;
import com.android.action.param.CallBkRequest;
import com.android.action.param.CallInParams;
import com.android.action.param.CallInRequest;
import com.android.action.param.CheckAccountRequest;
import com.android.action.param.CommReply;
import com.android.action.param.CommRequest;
import com.android.action.param.FeedBackRequest;
import com.android.action.param.LoginRequest;
import com.android.action.param.NetParam;
import com.android.action.param.RegisteredRequest;
import com.android.action.param.ResetPwdRequest;
import com.android.action.param.SystemRequest;
import com.android.action.param.VersionRequest;
import com.android.volley.Request;

public class NetAction extends NetBase {
    /**
     * 登陆
     *
     * @param listener
     * @return
     */
    public NetAction setLoginParam(String phone, String pwd, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        login.setPwd(pwd);
        setRequest(login);

        // 登录URL
        setUrl(NetParam.URL_LOGIN);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);

        setMethod(Request.Method.POST);

        return this;
    }

    /**
     * 检验手机
     *
     * @param phone
     * @param verCode  验证码
     * @param listener
     * @return
     */
    public NetAction setCheckLogin(String phone, String verCode, OnResponseListener listener) {

        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        login.setVerCode(verCode);
        setRequest(login);

        // 登录URL
        setUrl(NetParam.URL_LOGIN_CHECK);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);

        return this;
    }


    /**
     * 获取语音验证码
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction setVoiceCode(String phone, OnResponseListener listener) {

        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        setRequest(login);

        // 获取语音
        setUrl(NetParam.URL_VOICE_CODE);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);

        return this;
    }


    /**
     * 激活用户
     *
     * @param phone
     * @param pwd
     * @param listener
     * @return
     */
    public NetAction setActivateUser(String phone, String pwd, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        login.setPwd(pwd);
        setRequest(login);

        // 修改密码
        setUrl(NetParam.URL_ACTIVATE_USER);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 修改密码
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction setNewPwd(String phone, String pwd, OnResponseListener listener) {

        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        login.setPwd(pwd);
        setRequest(login);

        // 修改密码
        setUrl(NetParam.URL_LOGIN_CHECK_PWD);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);

        return this;
    }

    /**
     * 拉取组织结构
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction setOrg(String phone, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        setRequest(login);

        // 修改密码
        setUrl(NetParam.URL_GET_ORG);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);


        return this;
    }


    /**
     * 拉取所有员工
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction getAllEmployee(String phone, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        setRequest(login);

        // 修改密码
        setUrl(NetParam.URL_GET_ALL_EMPLOYEE);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);


        return this;
    }


    /**
     * 拉取个人信息
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction getSelfInfo(String phone, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        setRequest(login);

        // 修改密码
        setUrl(NetParam.URL_GET_EMP_INFO);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);


        return this;
    }


    /**
     * 拉取个人信息
     *
     * @param phone
     * @param listener
     * @return
     */
    public NetAction getCompanyInfo(String phone, OnResponseListener listener) {
        LoginRequest login = new LoginRequest();
        login.setPhone(phone);
        setRequest(login);

        setUrl(NetParam.URL_GET_COM_INFO);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);


        return this;
    }


    /******************************新接口********************************************/
    /**
     * 登陆 app
     *
     * @param listener
     * @return
     */
    public NetAction setLogin(OnMyResponseListener listener) {
        CommRequest login = new CommRequest();
        setRequest(login);

        // 登录URL
        setUrl(NetParam.URL_LOGIN);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setMyReplyListener(listener);

        setMethod(Request.Method.POST);

        return this;
    }


    /**
     * 注册
     *
     * @param regist
     * @param listener
     * @return
     */
    public NetAction setRegistered(RegisteredRequest regist, OnMyResponseListener listener) {

        Log.e(getClass().toString(), "注册方法提交的数据" + JSON.toJSON(regist));

        setRequest(regist);
        // 登录URL
        setUrl(NetParam.URL_REGIST);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);

        return this;
    }


    /**
     * 回呼
     *
     * @param caller   主叫
     * @param callee   被叫
     * @param listener
     * @return
     */
    public NetAction callBack(String caller, String callee, OnMyResponseListener listener) {
        CallBkRequest call = new CallBkRequest();
        String prefix = "0";
        if (caller.indexOf("00") == 0 || caller.indexOf("013") == 0 || caller.indexOf("014") == 0 || caller.indexOf("015") == 0
                || caller.indexOf("016") == 0 || caller.indexOf("017") == 0 || caller.indexOf("018") == 0) {
            call.setCaller(caller);
        } else {
            call.setCaller(prefix + caller);
        }

        if (callee.indexOf("00") == 0 || callee.indexOf("013") == 0 || callee.indexOf("014") == 0 || callee.indexOf("015") == 0
                || callee.indexOf("016") == 0 || callee.indexOf("017") == 0 || callee.indexOf("018") == 0) {
            call.setCallee(callee);
        } else {
            call.setCallee(prefix + callee);
        }
        setRequest(call);

        setUrl(NetParam.URL_CALL_BACK);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);

        return this;
    }

    /**
     * 检查账户
     *
     * @param account  手机号
     * @param listener
     * @return
     */
    public NetAction checkaccount(String account, OnMyResponseListener listener) {
        CheckAccountRequest check = new CheckAccountRequest();
        check.setAccount(account);
        setRequest(check);

        setUrl(NetParam.URL_CHECK_ACCOUNT);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }

    /**
     * 检查app版本
     *
     * @param currversion app版本
     * @param listener
     * @return
     */
    public NetAction checkVersion(String currversion, String appType, OnMyResponseListener listener) {
        VersionRequest check = new VersionRequest();
        check.setApptype(appType);
        check.setCurrversion(currversion);
        setRequest(check);

        setUrl(NetParam.URL_CHECK_VERSION);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 查询余额
     *
     * @param listener
     * @return
     */
    public NetAction getBalance(OnMyResponseListener listener) {
        CommRequest check = new CommRequest();
        setRequest(check);

        setUrl(NetParam.URL_BALANCE);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 系统充值卡
     *
     * @param listener
     * @return
     */
    public NetAction systemRecharge(String account, String cardnum, String password, OnMyResponseListener listener) {
        SystemRequest check = new SystemRequest();
        check.setAccount(account);
        check.setCardnum(cardnum);
        check.setPassword(password);
        setRequest(check);

        setUrl(NetParam.URL_SYSTEM_CHARGE);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }

    /**
     * 广告图片
     *
     * @param listener
     * @return
     */
    public NetAction getAdvertiseImg(OnMyResponseListener listener) {
        setRequest(new AdvertRequest());

        setUrl(NetParam.URL_ADVERTISE_MENT);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }

    /**
     * 获取指定类型的广告图片
     *
     * @param listener
     * @param advertType 1-首页广告     2-振铃广告   3-通话广告
     * @return
     */
    public NetAction getAdvertiseImg(OnMyResponseListener listener, String advertType) {
        AdvertRequest advert = new AdvertRequest();
        advert.setAdvertType(advertType);
        setRequest(advert);

        setUrl(NetParam.URL_ADVERTISE_MENT);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 反馈
     *
     * @param listener
     * @return
     */
    public NetAction feedBack(FeedBackRequest feed, OnMyResponseListener listener) {
        setRequest(feed);

        setUrl(NetParam.URL_FEEDBACK);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 语音验证码
     *
     * @param authCode 请求对象
     * @param listener
     * @return
     */
    public NetAction authCode(AuthCodeRequest authCode, OnMyResponseListener listener) {
        setRequest(authCode);

        setUrl(NetParam.URL_AUTH_CODE);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 重置密码
     *
     * @param resetPwd 请求对象
     * @param listener
     * @return
     */
    public NetAction resetPwd(ResetPwdRequest resetPwd, OnMyResponseListener listener) {
        setRequest(resetPwd);

        setUrl(NetParam.URL_RESET_PWD);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 公告消息
     *
     * @param listener
     * @return
     */
    public NetAction notice(OnMyResponseListener listener) {
        setRequest(new CommRequest());

        setUrl(NetParam.URL_NOTICE);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }


    /**
     * 修改密码
     *
     * @param listener
     * @return
     */
    public NetAction updatePwd(ResetPwdRequest re, OnMyResponseListener listener) {
        setRequest(re);

        setUrl(NetParam.URL_UPDATE_PWD);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }

    /**
     * 获取话单信息
     *
     * @param listener
     * @return
     */
    public NetAction getTelephoneInfo(OnMyResponseListener listener) {
        setRequest(new CommRequest());

        setUrl(NetParam.URL_TELEPHONEFARE_INFO);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);

        setMyReplyListener(listener);

        setMethod(Request.Method.POST);
        return this;
    }

    /**
     * 获取呼入信息
     *
     * @param listener
     * @return
     */
    public NetAction getCallInInfo(CallInParams getCallIn,
                                   OnMyResponseListener listener) {
        setUrl(NetParam.GET_CALLIN_MESSAGE);
        setRequest(getCallIn);
        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setMyReplyListener(listener);
        setMethod(Request.Method.POST);

        return this;
    }

    /**
     * 设置呼入信息
     *
     * @param listener
     * @return
     */
    public NetAction setCallInInfo(CallInRequest setCallIn,
                                   OnResponseListener listener) {
        setRequest(setCallIn);
        setUrl(NetParam.SET_CALLIN_MESSAGE);

        // 设置回应的类和listener
        setReplyClass(CommReply.class);
        setReplyListener(listener);
        setMethod(Request.Method.POST);

        return this;
    }
}
