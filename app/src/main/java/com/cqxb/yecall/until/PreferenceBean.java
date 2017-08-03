package com.cqxb.yecall.until;

public class PreferenceBean {
	public static String IS_FIRST_CREATE_DATA_SQL="firstCreate";//第一次创建数据库
	public static String USERACCOUNT="userAccount";//web账号
	public static String USERPWD="userPwd";//web密码
	public static String LOGINFLAG="loginFlag";//是否登录
	public static String USERNAME="userphone";//用户手机号


	public static String USERLINPHONEACCOUNT="userLinphoneAccount";//linphone 账号
	public static String USERLINPHONEPWD="userLinphonePwd";//linphone 密码
	public static String USERLINPHONEIP="userLinphoneIp";//linphone ip
	public static String USERLINPHONEPORT="userLinphonePort";//linphone port
	public static String USERLINPHONEDOMAIN="userLinphoneDomain";//linphone domain
	public static String USERLINPHONELOGIN="userLinphoneLogin";//sip 登陆状态
	public static String USERLINPHONEREGISTOK="userLinponeRegistOk";//linphone注册状态
	
	
	public static String USERIMACCOUNT="userImAccount";//im账号
	public static String USERIMPWD="userImPwd";//im密码
	public static String USERIMIP="userImIp";//im ip
	public static String USERIMPORT="userImPort";//im port
	public static String USERIMDOMAIN="userImDomain";//im domain
	public static String IMLOGIN="imLogin";//im登陆状态
	
	public static String CLIENT="client";
	public static String CURRCHATID;//当前聊天的对象  这里是用来作为下拉刷新的标记id
	public static String CHECKLOGIN="checkLogin";//判断是否登陆过
	public static String ORG_SELF="org_self";//下发的组织结构以及自己的app信息
	public static String ORG="org";//下发的组织结构
	public static String SELF="self";//自己的app信息
	public static String CALLEDREFRESH="calledRefresh";//打完电话后刷新通话记录
	
	
	public static String CALLNAME="callName";//当前拨号的名字
	public static String CALLPHONE="callPhone";//当前拨号话的号码
	public static String CALLPOSITION="callPosition";//当前号码归属地
	public static String CALLSTATUS="callStatus";//来电状态
	
	public static String COMPANYALLEMP="companyAllEmp";//所有员工;
	public static String COMPANYNAME="companyName";//企业名称;
	
	public static String PLAYVOICE="playVoice";//播放声音
	public static String PLAYVIBRATE="playVibrate";//震动
	
	public static String ADDCONTACTSUCCESSBACK="addContactSuccessBack";//拨号界面添加联系人成功后 回到原界面的号码
	
	public static String G3G4CHECK="g3g4Check";//3G4G状态下
	public static String WIFICHECK="wifiCheck";//wifi状态下
	public static String OUTCALLCHECK="outCallCheck";//去电显号
	
	public static String CALLPROYX="callProyx";//座机前缀
	
//	public static String CALLTIME="callTime";//座通话时间
	public static String BACKTOAPPNUME="backToAppMune";//返回手机主界面
	
	public static String CALLBACKNAME="callBackName";//回拨模式下的名字
	public static String CALLBACKSTART="callBackStart";//当前是回拨的电话
	public static String CALLBACKSELF="callBackSelf";//当前是回拨的电话(自己打出的)
	
	public static String PHONEORFREECOMPANY="phoneOrCompany";//区分拨号是平台号码还是企业号
	public static String PHONEORFREECOMPANYNUMBER="phoneOrCompanyNumber";//记住拨打平台号码时点击的手机号
	
	public static String APPVERSIONS="appVersions";
	
	public static String PHONEADDZERO="phoneAddZero";//是否加0拨号
	
	public static String ADVERTISEMENT="advertisement";//缓存的图片地址
	public static String ADVERTISEMENT_2="advertisement_2";//缓存的图片地址
	public static String ADVERTISEMENT_3="advertisement_3";//缓存的图片地址
	public static String ADVERTISEMENTCOUNT="advertisementCount";//缓存图片的数目
	public static String ADVERTISEMENTCOUNT_2="advertisementCount_2";//缓存图片的数目
	public static String ADVERTISEMENTCOUNT_3="advertisementCount_3";//缓存图片的数目
	
	public static String RECORDPATH="recordPath";//录音文件地址
	public static String PHOTOPATH="photoPath";//截屏文件地址
	
	//呼入设置
	public static String CALLINDND="callInDND";
	public static String CALLINALLTRANSFER="callInAllTransfer";
	public static String CALLINCUSTOM="callInDND";
	public static String CALLINALLTRANSFERNUMBER="callInAllTransferNumber";
	public static String CALLINGPRSTRANSFER="callInGprsTransfer";
	public static String CALLINNOPERSONTRANSFER="callInNoPersonTransfer";
	public static String CALLINBUSYTRANSFER="callInBusyTransfer";
	public static String CALLINUNCONNECTTRANSFER="callInUnconnectTransfer";	
}
