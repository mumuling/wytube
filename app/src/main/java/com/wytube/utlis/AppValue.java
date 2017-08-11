package com.wytube.utlis;

import android.annotation.SuppressLint;

import com.cqxb.yecall.bean.SipAccountBean;
import com.wytube.beans.CarsBean;
import com.wytube.beans.OwnerBean;
import com.wytube.beans.PropMsgBean;
import com.wytube.beans.RepairBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2017/4/19.
 * 类 描 述: APP的静态变量引用
 */
public class AppValue {
    /*可视云登录modle*/
    public static SipAccountBean bean;
//    /*录音文件储存位置*/
//    @SuppressLint("SdCardPath")
//    public final static String audioPath = "/sdcard/tedi/WH/file/recordAudio/";
//    /*截图文件保存位置*/
//    @SuppressLint("SdCardPath")
//    public final static String picturePath = "/sdcard/tedi/WH/file/catPicture/";
    /*用户头像保存路径*/
    @SuppressLint("SdCardPath")
    public final static String userHeadPath = "/sdcard/tedi/WH/file/user/head/photo.png";
    /*用户选择的图片经过压缩后存放的路劲*/
    @SuppressLint("SdCardPath")
    public final static String userTempPath = "/sdcard/tedi/WH/file/user/temp/";
    /*通话设置保存位置*/
    public static final String setPath = "/sdcard/tedi/HW/file/user/settings/";
    /*SIP代理及登录用户名密码等*/
    public static String sipAddr = "";
    public static String sipProt = "";
    public static String sipName = "";
    public static String sipPass = "";
    /*用户的token值*/
//    public static String token = PreferenceBean.CHECKLOGIN;
    public static String token = "";
    /*版本号对比*/
    public static int localVersion = 0;
    public static int serverVersion = 0;
    /*APP的版本号*/
    public static String appVersion = "";
    /*HTTP请求头验证*/
    public static String authorization = "";
    /*回调连接*/
    public static String notifyUrl = "";
    /*用户名称*/
    public static String TextName = "";
//    /*通话参数记录*/
//    public static String callOut = "";
//    public static String callOutNum = "";
//    public static String callIn = "";
//    public static String callInNum = "";
//    /*通话参数配置*/
//    public static boolean useRecordAudio = false;
//    public static boolean useCallOutVideo = false;
//    public static String audios = "";
//    public static String pictures = "";
    /*用户在线状态*/
    public static boolean online = false;
//    /*联系人列表信息*/
//    public static List<UserBean> contacts;
//    /*通话记录对象*/
//    public static UserBean callLogBean;
//    /*通话记录信息列表*/
//    public static List<UserBean> callLog;
//    /*分离后的通话记录*/
//    public static List<UserBean> sqlitCallLog;
//    /*联系人中无数据的索引*/
//    public static List<Integer> saveIndax = new ArrayList<>();
    /*选择列表*/
    public static List<String> items = new ArrayList<>();
//    /*远程开门设备列表*/
//    public static List<DoorBean.DataBean> netDoors = new ArrayList<>();
//    /*密码门径涉笔列表*/
//    public static List<DoorBean.DataBean> passDoors = new ArrayList<>();
    /*物业通知列表*/
    public static List<PropMsgBean.DataBean> propMsgs;
//    /*Web界面的标题文字*/
//    public static String webTitle = "";
//    /*Web界面加载的Urls*/
//    public static String webUrl = "";
//    /*版本连接*/
//    public static String versionUrl = "";
//    /*用户的小区信息*/
//    public static String locationText = "";
//    public static String locationUnitNum = "";
//    public static String locationRoomNum = "";
//

    /*报修记录列表*/
    public static List<RepairBean.DataBean> repairBeans;
////    /*是否退出并重新登录*/
////    public static boolean isRelogin = false;
    /*选择对话框的按钮显示的文本*/
    public static String selectBut = "";
//    /*投诉记录列表*/
//    public static List<ComRecordBean.DataBean> recordBeans;
    /*报修列表的Item*/
    public static RepairBean.DataBean repairInfoBean;

    public static List<OwnerBean.DataBean> ownerBeans;
    public static OwnerBean.DataBean ownerInBean;
//    /*投诉列表的Item*/
//    public static ComRecordBean.DataBean comRecordinfoBean;
//    /*便民服务的Urls*/
//    public static List<ServiceUrlBean.DataBean.LifeTypesBean> serviceBeans;
//    /*喜事预约详情对象*/
//    public static WellBean.DataBean wellBean;
//    /*用户小区ID*/
//    public static String userHouseId = "";
//    /*设置信息对象*/
////    public static SettingsBean setBean;
//    public static CallSetBean callSetBean;
////    /*门禁设备名称*/
////    public static String doorName = "";
////    /*门禁设备区域*/
////    public static String doorLocal = "";
////    /*门禁设备ID*/
////    public static String doorId = "";
//    /*默认显示的门禁设备界面*/
//    public static int doorPage = -1;
    /*刷新*/
    public static int fish = -1;
    /*标题栏*/
    public static int btl = -1;
    /*判断是哪个地方进入*/
    public static int onec = -1;
    /*我的车辆进入添加车牌*/
    public static int chel = -1;
//    /*默认显示的首页界面*/
//    public static int doorPages = -1;
    /*临时图片路径*/
    public static String tempImage = "";
//    /*维修类型数据列表*/
//    public static List<RepairTypeBean.DataBean> typeBeens;
//    /*借用物品对象*/
//    public static BorrowBean.DataBean borrwInfoBean;
//    /*动态类型列表*/
//    public static List<String> dynamicTypeList;
//    /*交易买卖类型*/
//    public static List<String> tradeTypes;
//    /*交易买卖详情*/
//    public static TradeBean.DataBean.TradingsBean tradingsBean;
//    /*交易记录详情*/
//    public static BaseJYJL.DataBean.TradingsBean tradingsJLBean;
//    /*用户积分*/
//    public static String usrJf;
//    /*用户头像*/
//    public static String usrJfs;
    /*车场订单号*/
    public static String orderNum = "";
    /*车场金额*/
    public static String Money = "";
    /*停车场ID*/
    public static String parkId = "";
    /*车主所属车牌信息*/
    public static List<CarsBean.DataBean> carsBeans;
    /*车牌号*/
    public static String carNum;
    /*临时秘钥*/
    public static String skey;
    /*停车场名称*/
    public static String parkName;
//    /*新闻资讯对象*/
//    public static NewsBean.DataBean.InfosBean infoBean;
//    /*活动记录*/
//    public static BeseHd.DataBean listBeseHd;
//    /*星级*/
//    public static String skexj;
//    /*车牌信息*/
//    public static CarsBean.DataBean Carsx;

//    /*点赞*/
//    public static boolean ISok=true;

//    /*购物车保存到本地*/
//    public static final String PERSON = "ONE_PERSON";
    /*楼宇id*/
    public static String LYid="";
    /*单元id*/
    public static String DYid="";



}
