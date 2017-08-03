package com.wytube.net;

/**
 * 创建时间: 2017/4/22.
 * 类 描 述:
 */
public class NetParmet {
    /*HTTP协议*/
    private static final String HTTP = "http://";
    /*正式服务器*/
    private static final String APP_SERVER = "app.123667.com/community";
    private static final String APP_SER= "app.123667.com";
    private static final String WEB_SERVER = "web.123667.com";
    /*软件版本*/

    private static final String SOFT_WYSION = "/managerApi";
    private static final String SOFT_VERSION = "/api";
    private static final String DOOR_DIR = "/door";
    /*目录*/
    private static final String DIR_NAME = "";
    private static final String SERVER_DIR = "/yecall";
    private static final String SERVER_VERSION = "/20150101";
    private static final String PARK = "/park";
    /*停车场IP*/
    public static String PARK_IP = "192.168.0.5";
    /*停车场端口*/
    public static String PARK_PROT = "80";

    private static final String DRL= "/shop";
    /*云服务ip  pro1.123667.com*/
    public static final String YURL = "115.28.40.125";

    /*----------------------------------------------新接口----------------------------------------------------*/

    /*-----------------------------------------POST请求类型接口------------------------------------------------*/
    /*用户登录*/
    public static final String USR_LOGIN = HTTP + APP_SERVER + DIR_NAME +SOFT_WYSION + "/login";
    /*物业费用*/
    public static final String USR_WYFY = HTTP + APP_SERVER + DIR_NAME +SOFT_WYSION + "/billInfo";
    /*物业通知*/
    public static final String PROP_MSG = HTTP + APP_SERVER + DIR_NAME + SOFT_WYSION + "/pushInfo";
    /*发送通知*/
    public static final String PROP_WYFS = HTTP + APP_SERVER + DIR_NAME + SOFT_WYSION + "/pushInfo/add";
    /*删除通知*/
    public static final String PROP_WYDL = HTTP + APP_SERVER + DIR_NAME + SOFT_WYSION + "/pushInfo/delete";
    /*修改通知*/
    public static final String PROP_WYSC = HTTP + APP_SERVER + DIR_NAME + SOFT_WYSION + "/pushInfo/edit";
    /*家政服务列表*/
    public static final String QUERY_REPAIR_LIST = HTTP + APP_SERVER + DIR_NAME + SOFT_WYSION + "/repairWork";



    /*业主管理接口*/
    public static final String USR_ZHUCE= HTTP + APP_SERVER + DIR_NAME +SOFT_WYSION+ "/owner";
    /*获取验证码*/
    public static final String USR_YZM= HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/authcode";
    /*忘记密码*/
    public static final String USR_WJMM= HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/resetpass";
    /*获取用户所有小区信息*/
    public static final String GET_USER_LOCATION = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/cells";
    /*为用户绑定小区*/
    public static final String BIND_USER_DATA = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/changecell";
    /*可借用物品列表*/
    public static final String BORRO_LIST = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/borrow/queryBorrowShopList";
    /*检查版本更新*/
    public static final String CHECK_VERSION = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/version";
    /*物业未缴费总金额*/
    public static final String USR_NOT_MONEY = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/bill/findTotalMoney";
    /*物业账单列表*/
    public static final String USR_WY_LIST = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/bill/queryBillInfoList";

    /*添加访客通行*/
    public static final String VISITOR_ADD = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/pass/add";
    /*获取访客记录*/
    public static final String VISITOR_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/pass/queryPassList";
    /*获取报修类型列表*/
    public static final String REPAIR_TYPE_LIST = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/queryRepairItemList";
    /*添加报修明细*/
    public static final String ADD_REPAIR_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/add";
    /*家政服务详情*/
    public static final String ADD_JZXQ_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/view";
    /*家政星级*/
    public static final String ADD_JZXJ_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/queryRepairSorceList";
    /*家政评价*/
    public static final String ADD_JZPJ_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/comment";
    /*取消报修订单*/
    public static final String ADD_DELETE_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/repairWork/delete";
    /*获取投诉建议类型*/
    public static final String SUIT_TYPE = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/suitWork/querySuitTypeList";
    /*添加投诉建议*/
    public static final String ADD_SUIT = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/suitWork/add";
    /*获取我的投诉记录*/
    public static final String QUERY_MY_RECORD = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/suitWork/querySuitWorkList";
    /*获取我的投诉详情*/
    public static final String QUERY_MY_XQ = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/suitWork/view";
    /*获取便民服务*/
    public static final String GET_SERVER_URL = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/lifetype";
    /*添加喜事预约*/
    public static final String ADD_WELL = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/celebration/add";
    /*获取小区部门信息*/
    public static final String GET_COMUNITY_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/departments";
    /*查询预约记录*/
    public static final String QUERY_WELL_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/celebration/queryCelebrationInfoList";
    /*借用物品*/
    public static final String BORROW_ADD = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/borrow/add";
    /*获取朋友圈所有动态*/
    public static final String GET_DYNAMIC = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track";
    /*发布新动态*/
    public static final String PUBLISH_DYNAMIC = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/create";
    /*点赞或取消点赞*/
    public static final String GOOD_DYNAMIC = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/good";
    /*删除动态*/
    public static final String DELETE_DYNAMIC = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/delete";
    /*朋友圈评论*/
    public static final String GOOD_PDYPL = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/reply";
    /*朋友圈评论回复*/
    public static final String GOOD_PDYPLHF = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/getReply";
    /*朋友圈评论删除*/
    public static final String DELETE_PDYPL = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/track/deletereply";

    /*获取所有咨询信息*/
    public static final String GET_ALL_INFO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/info";
    /*获取所有咨询分类*/
    public static final String GET_ALL_INFO_TYPES = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/info/types";
    /*首页推荐商品*/
    public static final String FIR_PAGE_RECD = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/shop/queryCommodityList";
    /*发布新的交易信息*/
    public static final String TRADING_CREATE = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/trading/create";
    /*修改交易信息*/
    public static final String TRADING_UPJY = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/trading/update";
    /*删除交易信息*/
    public static final String TRADING_DELETE = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/trading/delete";
    /*获取所有交易信息*/
    public static final String GET_ALL_TRADING = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/trading";
    /*我的物品借用记录*/
    public static final String BORROW_INFO_LIST = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/borrow/queryUserBorrowList";
    /*我的红包卡券*/
    public static final String MY_RED_BAO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/shop/giveMyCouponList";
    /*获得得红包*/
    public static final String GET_RED_BAO = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/shop/giveCoupon";
    /*用户注册接口*/
    public static final String USER_REG = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/owner/register";
    /*用户获取所有设备*/
    public static final String USR_DOORS = HTTP + APP_SERVER + DOOR_DIR + SOFT_VERSION + "/doors";
    /*动态门禁密码*/
    public static final String USR_DOOR_KEY = HTTP + APP_SERVER + DOOR_DIR + SOFT_VERSION + "/getkey";
    /*开门请求*/
    public static final String USR_OPEN = HTTP + APP_SERVER + DOOR_DIR + SOFT_VERSION + "/open";
    /*扫码开门*/
    public static final String USR_CODE = HTTP + APP_SERVER + DOOR_DIR + SOFT_VERSION + "/code";
    /*获取活动列表*/
    public static final String USR_SQHD = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/queryActivityList";
    /*获取我参与的活动*/
    public static final String USR_WCY = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/queryMyActivityList";
    /*查看活动详情*/
    public static final String USR_XQ= HTTP + APP_SERVER  + SOFT_VERSION + "/activity/view";
    /*我要加入*/
    public static final String USR_WJR = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/add";
    /*取消加入*/
    public static final String USR_QXJR = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/delete";
    /*点赞*/
    public static final String USR_DZ = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/addHeart";
    /*点赞*/
    public static final String USR_QX = HTTP + APP_SERVER  + SOFT_VERSION + "/activity/removeHeart";
    /*生活服务  获取商铺类型*/
    public static final String USR_SHFW = HTTP + APP_SERVER  + SOFT_VERSION + "/shop/shopTypes";
    /*生活服务  获取商品信息*/
    public static final String USR_SHFWXQ = HTTP + APP_SERVER  + SOFT_VERSION + "/shop";
    /*评论投诉建议*/
    public static final String USR_PLTSJY = HTTP + APP_SERVER  + SOFT_VERSION + "/suitWork/comment";
    /*获取评论时星级列表*/
    public static final String USR_PLXJLB = HTTP + APP_SERVER  + SOFT_VERSION + "/suitWork/querySuitSorceList";
    /*保存用户头像*/
    public static final String USR_BCYHTX = HTTP + APP_SERVER  + SOFT_VERSION + "/owner/headimg";

    /*---------------------------------------------老接口-----------------------------------------------------*/
   // http://web.123667.com/yecall/20150101/infs/getbalance.json
    public  static  final String NEW_UESR_MONEY_URL=HTTP+APP_SERVER+SOFT_VERSION+"/getbalance";
    /*用户余额查询接口*/
    public static final String GET_USER_MONEY = HTTP + WEB_SERVER + SERVER_DIR + SERVER_VERSION + "/infs/getbalance.json";
    /*呼叫转移接口*/
    public static final String USR_ALL_CALL_REST = HTTP + WEB_SERVER + SERVER_DIR + SERVER_VERSION + "/infs/setdndtransfer.json";
    /*用户呼叫转移设定*/
    public static final String USR_CALL_REST_SET = HTTP + WEB_SERVER + SERVER_DIR + SERVER_VERSION + "/infs/getdndtransfer.json";
    /*用户账户明细查询接口*/
    public static final String USR_MONEY_INFO_SELECT = HTTP + WEB_SERVER + SERVER_DIR + SERVER_VERSION + "/infs/telephonefareinfo.json";
    /*支付宝话费充值回调接口*/
    public static final String NOTIFY_URL = HTTP + WEB_SERVER + SERVER_DIR + "/jsp/notify_url.jsp";

    /*-----------------------------------------回调或HTML类型接口-------------------------------------------------*/

    /*自助服务中心*/
    public static final String SERVER_CENTER = HTTP + WEB_SERVER + "/eus/";
    /*支付回调接口*/
    public static final String ALIPAY_CALLBACK = HTTP + APP_SERVER + DIR_NAME + SOFT_VERSION + "/bill/aliPay";

    /*-----------------------------------------停车场接口-------------------------------------------------*/

    /*获得所有停车场信息*/
    public static final String GET_ALL_PARKS = HTTP + APP_SER + PARK + SOFT_VERSION + "/parks";
    /*获取用户所有车辆信息*/
    public static final String GET_ALL_CARS = HTTP + APP_SER + PARK + SOFT_VERSION + "/cars";
    /*绑定停车场*/
    public static final String BIND_USER_PARK = HTTP + APP_SER + PARK + SOFT_VERSION + "/bind";
    /*创建车牌*/
    public static final String CREATE_NEW_CAR = HTTP + APP_SER + PARK + SOFT_VERSION + "/create";
    /*删除车牌*/
    public static final String CREATE_DELE_CAR = HTTP + APP_SER + PARK + SOFT_VERSION + "/delete";
    /*修改车牌*/
    public static final String CREATE_XG_CAR = HTTP + APP_SER + PARK + SOFT_VERSION + "/update";
    /*获取订单号*/
    public static final String CREATE_ORDER_NUM = HTTP + APP_SER + PARK + "/order/create";
    /*支付成功验证*/
    public static final String PAY_SURE = HTTP + APP_SER + PARK + "/order/sure";
    /*支付宝回调接口*/
    public static final String PARK_ALIPAY_CALLBACK = HTTP + APP_SER + PARK + "/order/callback";
    /*初始化停车场*/
    public static final String INIT_LOGIN = HTTP + PARK_IP + ":" + PARK_PROT + "/Service1.svc/initLogin";
    /*查询停车费用，月卡延期*/
    public static final String SELECT_STOP_MONEY = HTTP + PARK_IP + ":" + PARK_PROT + "/Service1.svc/Accountmoney";
    /*IIS服务器的回调*/
    public static final String PAY_CALL_BACK = HTTP + PARK_IP + ":" + PARK_PROT + "/Service1.svc/TradePayformoney";
    /*月卡续费金额查询*/
    public static final String MONTH_MONEY_SELECT = HTTP + PARK_IP + ":" + PARK_PROT + "/Service1.svc/OrderMonthCardDefer";

}
