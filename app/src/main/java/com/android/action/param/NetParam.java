package com.android.action.param;

import android.util.Base64;
import android.util.Log;

import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.until.SettingInfo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络参数
 *
 * @author
 * @version 1.0
 */

public class NetParam {

    // 协议类型
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    // 交易服务器地址和端口

    /* 测试 */
    private static final String TEST_TR_PROTOCOL = HTTPS;
    private static final String TEST_HOST_TR = "192.168.1.5";
    private static final Integer TEST_PORT_TR = 8008;

    /* 正式 */
    private static final String OFFICIAL_TR_PROTOCOL = HTTPS;
    private static final String OFFICIAL_HOST_TR = "tran.winwho.com";
    private static final Integer OFFICIAL_PORT_TR = 8008;

    // 数据服务器地址和端口
    /* 测试 */
    private static final String TEST_BI_PROTOCOL = HTTPS;
    private static final String TEST_HOST_BI = "192.168.1.5";
    private static final Integer TEST_PORT_BI = 9009;
    /* 正式 */
    private static final String OFFICIAL_BI_PROTOCOL = HTTPS;
    private static final String OFFICIAL_HOST_BI = "tran.winwho.com";
    private static final Integer OFFICIAL_PORT_BI = 9009;

    //支付宝
//	public static final String SERVER = "http://10.1.20.41:8080";
    public static final String SERVER = "http://web.123667.com";
//    public static final String SERVER_CE = "http://115.28.2.168";
    public static final String SERVER_CE = "http://web.123667.com";

    public static final String SERVER_NAME = "/yecall";

//	public static final String SERVER = "http://10.1.20.40:8080/uas";

    public static final String CALLSERVER = "http://web.123667.com/ebs";


    //public static final String SERVER = "http://114.215.140.56:8081/uecall";
    //public static final String SERVER = "http://101.95.103.46:8081/uecall";//yxxxxxx
    // private static String SERVER = "http://192.168.44.1/uecall";
    private static final String SOFT_VERSION = "/20150101";

    private static final String CALL_SOFT_VERSION = "/20140301";
    // //////////////////////////////////////////////////////////////////////////
    // + 交易请求网址（开始）
    // 初始化数据和交易服务器网址
    public static String URL_AD_CALL = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/voiceservice.json";
    public static String URL_LOGIN_AUTHENTICATION = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/authentication.json";


    //验证是否存在员工
    public static String URL_LOGIN_CHECK = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/checkLogin.json";
    //激活用户
    public static String URL_ACTIVATE_USER = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/activateAccount.json";

    //验证存在后初始化密码
    public static String URL_LOGIN_CHECK_PWD = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/updatePwd.json";

    //获取语音验证码
    public static String URL_VOICE_CODE = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/getVerCode.json";
    //获取组织结构
    public static String URL_GET_ORG = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/getOrg.json";
    //获取所有员工
    public static String URL_GET_ALL_EMPLOYEE = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/getAllEmployee.json";
    //获取个人信息
    public static String URL_GET_EMP_INFO = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/getSelfInfo.json";
    //获取企业信息
    public static String URL_GET_COM_INFO = SERVER + SERVER_NAME + SOFT_VERSION + "/accounts/calls/getCompanyInfo.json";
    //回拨
    public static String CALL_BACK = CALLSERVER + CALL_SOFT_VERSION + "/accounts/infs/callback.json";

    /************************************************************************************/

    //登录
    public static String URL_LOGIN = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/login.json";

    //注册
    public static String URL_REGIST = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/register.json";

    //回呼
    public static String URL_CALL_BACK = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/callback.json";

    //检测平台用户
    public static String URL_CHECK_ACCOUNT = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/checkaccount.json";

    //检测最新版本
    public static String URL_CHECK_VERSION = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/checkversion.json";

    //查询余额接口
    public static String URL_BALANCE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/getbalance.json";

    //系统卡充值
    public static String URL_SYSTEM_CHARGE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/rechargebycard.json";

    //广告图片
    public static String URL_ADVERTISE_MENT = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/advertisement.json";

    //提交反馈
    public static String URL_FEEDBACK = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/feedback.json";

    //语音验证码
    public static String URL_AUTH_CODE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/authcode.json";

    //重置密码
    public static String URL_RESET_PWD = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/resetpwd.json";

    //公告
    public static String URL_NOTICE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/notice.json";

    //修改密码
    public static String URL_UPDATE_PWD = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/updatepwd.json";

    //话单信息
    public static String URL_TELEPHONEFARE_INFO = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/telephonefareinfo.json";

    // 获取呼入信息
    public static String GET_CALLIN_MESSAGE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/getdndtransfer.json";

    // 设置呼入信息
    public static String SET_CALLIN_MESSAGE = SERVER + SERVER_NAME + SOFT_VERSION + "/infs/setdndtransfer.json";

    /*用户获取所有设备*/
    public static String USR_DRIVERS = SERVER_CE + "/door/api/doors";

    /*用户门禁信息*/
    public static String USR_DOOR_INFO = SERVER_CE + "/door/api/getkey";

    /*开门请求*/
    public static String USR_OPEN = SERVER_CE + "/door/api/open";

    /*扫码开门*/
    public static String USR_CODE = SERVER_CE + "/door/api/code";

    // 请求ID（每次会话，reqId 自增1）
    private static Integer reqId = 1;

    // 网络模块版本
    public static final String VERSION = "0.1.0";

    // HTTP 连接类型
    public static final String HTTP_CONN_KEEP = "Keep-Alive";
    public static final String HTTP_CONN_CLOSE = "Close";

    // HTTP 支持 GZIP 压缩
    public static final String HTTP_GZIP = "gzip";
    public static final String ACCEPT = "application/json";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static String AUTHORIZATION = "";

    // HTTP 请求 User-Agent 字串
    public static final String USER_AGENT = "Winwho-" + VERSION + "/" + "Android-" + android.os.Build.VERSION.RELEASE + "/" + android.os.Build.BRAND + "-" + android.os.Build.MODEL;

    // 请求Key名（所有请求字串格式统一为：Key=Value）
    public static final String REQ_KEY = "param";
    public static final String LOG_KEY = "log";

    // 交易服务器地址和端口
    private static final String TEST_SERVER_TR = TEST_TR_PROTOCOL + TEST_HOST_TR + ":" + TEST_PORT_TR.toString();
    private static final String OFFICIAL_SERVER_TR = OFFICIAL_TR_PROTOCOL + OFFICIAL_HOST_TR + ":" + OFFICIAL_PORT_TR.toString();

    // 数据服务器地址和端口
    private static final String TEST_SERVER_BI = TEST_BI_PROTOCOL + TEST_HOST_BI + ":" + TEST_PORT_BI.toString();
    private static final String OFFICIAL_SERVER_BI = OFFICIAL_BI_PROTOCOL + OFFICIAL_HOST_BI + ":" + OFFICIAL_PORT_BI.toString();

    // SESSION（会话ID：标识一个有效的登录连接，有效期间所有HTTP请求都必须在URL末尾携带该字段）
    private static String session = null;

    // 禁止实例化
    private NetParam() {
    }

    // 服务器时间（服务器返回给客户端，用于日志同步. 时间单位：从1970年1月1日8:00起的时间戳）
    private static Long serverTime = null;

    // 设置服务器时间
    public static void setServerTime(Long time) {
        // Log.v("Renhua", "new serverTime = " + time.toString());
        NetParam.serverTime = time;
        // LogManager.updateServerTime(time);
    }

    // 获得服务器时间
    public static Long getServerTime() {
        return NetParam.serverTime;
    }

    // 获得 HTTP SESSION
    public static String getSessionId() {
        return NetParam.session;
    }

    // 设置HTTP SESSION
    public static void setSessionId(String session) {
        NetParam.session = session;
    }

    // 获得请求 ID（每次交互，reqId 自增1）
    public static Integer getReqId() {
        NetParam.reqId = Integer.valueOf(reqId.intValue() + 1);
        return NetParam.reqId;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String getAuthorization() {
        SettingInfo.init(YETApplication.getContext());
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        // String author = String.format("%s:%s:%s", SettingInfo.getAccount(),
        // SettingInfo.getPassword(), "20140502");
        String author = new String(SettingInfo.getAccount() + ":" + SettingInfo.getPassword() + ":" + YETApplication.appClass);
        Log.v("", "author:" + author);
        String encode = "";
        try {
            encode = Base64.encodeToString(author.getBytes("UTF-8"), Base64.DEFAULT);
            Log.v("", "author base64:" + encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return replaceBlank(encode);
    }
}
