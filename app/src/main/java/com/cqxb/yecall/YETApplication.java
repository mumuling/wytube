package com.cqxb.yecall;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.action.param.NetLoadImage;
import com.cqxb.yecall.bean.CallBean;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.PreferenceBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.pgyersdk.crash.PgyCrashManager;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.DisplayUtil;

import org.jivesoftware.smackx.muc.MultiUserChat;
import org.linphone.LinphoneActivity;
import org.linphone.core.LinphoneCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class YETApplication extends Application {
    private static YETApplication myApplication;
    public static String appClass = "AAA";
    public static String appType = "Android";
    public static String appVersion = "";
    public static Context mContext;
    public static final int NUM_PAGE = 6;// 总共有多少页
    public static int NUM = 20;// 每页20个表情,还有最后一个删除button
    private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
    public static String appName = "yet";
    public NetLoadImage mNetLoadImage;
    public static LinphoneCall.State state = LinphoneCall.State.CallReleased;

    public Map<String, Integer> getFaceMap() {
        initFaceMap();
        return mFaceMap;
    }

    public static void instanceContext(Context context) {
        mContext = context;
    }

    static public Context getContext() {
        return mContext;
    }

    /**
     * 必须要用LinkedList 否则就报错了
     */
    private List<Activity> activityList = new LinkedList<Activity>();
    /**
     * 邀请讨论组
     */
    private Map<String, InformationList> roomMap = new HashMap<String, InformationList>();

    /**
     * 邀请讨论组
     */
    private Map<String, MultiUserChat> roomListener = new HashMap<String, MultiUserChat>();

    /**
     * 通话记录
     */
    private List<CallBean> clList = new ArrayList<CallBean>();

    /**
     * 通话记录
     */
    private List<Contacts> thjl = new ArrayList<Contacts>();

    /**
     * 通讯录
     */
    private List<Contacts> cltList = new ArrayList<Contacts>();


    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//		BaseUtil.init(MyApplication.this);
        SettingInfo.init(getApplicationContext());
        mContext = this.getApplicationContext();
        mNetLoadImage = new NetLoadImage(this);
        myApplication = this;
        // 初始化Fresco
        Fresco.initialize(this);
        createFold();
        initFaceMap();
        getAppVersion();
        String appId = "6ada7ca49b07e039dc68a52d8923ba3e";  //蒲公英注册或上传应用获取的AppId
        PgyCrashManager.register(this, appId);
        initMobclickAgent();
        initDisplayOpinion();
    }

    /**
     * 友盟统计
     */
    public void initMobclickAgent() {
        MobclickAgent.UMAnalyticsConfig u = new
                MobclickAgent.UMAnalyticsConfig(getApplicationContext(), "579b09d6e0f55a4080000220", "测试");
        MobclickAgent.startWithConfigure(u);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setSessionContinueMillis(1000);
    }


    public NetLoadImage getNetLoadImage() {
        return mNetLoadImage;
    }

    public void createFold() {
        File file = new File(android.os.Environment.getExternalStorageDirectory()
                .getAbsolutePath() + getApplicationContext().getString(R.string.chat_fold));
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static Context getContextObject() {
        return mContext;
    }

    public static YETApplication getinstant() {
        return myApplication;
    }

    /**
     * 完全退出应用
     */
    public boolean exit() {
        try {
            thjl.clear();
            clList.clear();
            cltList.clear();
            roomMap.clear();
            roomListener.clear();
            DBHelper dbHelper = new DBHelper(mContext);
            dbHelper.open();
            dbHelper.deleteData(InformationList.TABLE, InformationList.FLAG + "=?", new String[]{"4"});
            dbHelper.execSql("delete from " + GroupChatEntity.TABLE, null);
            dbHelper.close();
            Smack.getInstance().removeListener();
            if (Smack.conn != null) {
                Smack.conn.disconnect();
            }
            for (Activity activity : activityList) {
                System.out.println("Exit====>>>" + activity.getLocalClassName());
                activity.finish();
            }
            System.exit(0);
            LinphoneActivity.instance().exit();
            System.gc();
        } catch (Exception e) {
            Log.e("Application", "Application :" + e.getLocalizedMessage());
            for (Activity activity : activityList) {
                System.out.println("Exit====>>>" + activity.getLocalClassName());
                activity.finish();
            }
            System.exit(0);
            System.gc();
        }
        return true;
    }

    /**
     * @param activity 添加到集合中
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void initFaceMap() {
        // TODO Auto-generated method stub
        mFaceMap.put("[呲牙]", R.drawable.f_static_000);
        mFaceMap.put("[调皮]", R.drawable.f_static_001);
        mFaceMap.put("[流汗]", R.drawable.f_static_002);
        mFaceMap.put("[偷笑]", R.drawable.f_static_003);
        mFaceMap.put("[再见]", R.drawable.f_static_004);
        mFaceMap.put("[敲打]", R.drawable.f_static_005);
        mFaceMap.put("[擦汗]", R.drawable.f_static_006);
        mFaceMap.put("[猪头]", R.drawable.f_static_007);
        mFaceMap.put("[玫瑰]", R.drawable.f_static_008);
        mFaceMap.put("[流泪]", R.drawable.f_static_009);
        mFaceMap.put("[大哭]", R.drawable.f_static_010);
        mFaceMap.put("[嘘]", R.drawable.f_static_011);
        mFaceMap.put("[酷]", R.drawable.f_static_012);
        mFaceMap.put("[抓狂]", R.drawable.f_static_013);
        mFaceMap.put("[委屈]", R.drawable.f_static_014);
        mFaceMap.put("[便便]", R.drawable.f_static_015);
        mFaceMap.put("[炸弹]", R.drawable.f_static_016);
        mFaceMap.put("[菜刀]", R.drawable.f_static_017);
        mFaceMap.put("[可爱]", R.drawable.f_static_018);
        mFaceMap.put("[色]", R.drawable.f_static_019);
        mFaceMap.put("[害羞]", R.drawable.f_static_020);

        mFaceMap.put("[得意]", R.drawable.f_static_021);
        mFaceMap.put("[吐]", R.drawable.f_static_022);
        mFaceMap.put("[微笑]", R.drawable.f_static_023);
        mFaceMap.put("[发怒]", R.drawable.f_static_024);
        mFaceMap.put("[尴尬]", R.drawable.f_static_025);
        mFaceMap.put("[惊恐]", R.drawable.f_static_026);
        mFaceMap.put("[冷汗]", R.drawable.f_static_027);
        mFaceMap.put("[爱心]", R.drawable.f_static_028);
        mFaceMap.put("[示爱]", R.drawable.f_static_029);
        mFaceMap.put("[白眼]", R.drawable.f_static_030);
        mFaceMap.put("[傲慢]", R.drawable.f_static_031);
        mFaceMap.put("[难过]", R.drawable.f_static_032);
        mFaceMap.put("[惊讶]", R.drawable.f_static_033);
        mFaceMap.put("[疑问]", R.drawable.f_static_034);
        mFaceMap.put("[睡]", R.drawable.f_static_035);
        mFaceMap.put("[亲亲]", R.drawable.f_static_036);
        mFaceMap.put("[憨笑]", R.drawable.f_static_037);
        mFaceMap.put("[爱情]", R.drawable.f_static_038);
        mFaceMap.put("[衰]", R.drawable.f_static_039);
        mFaceMap.put("[撇嘴]", R.drawable.f_static_040);
        mFaceMap.put("[阴险]", R.drawable.f_static_041);

        mFaceMap.put("[奋斗]", R.drawable.f_static_042);
        mFaceMap.put("[发呆]", R.drawable.f_static_043);
        mFaceMap.put("[右哼哼]", R.drawable.f_static_044);
        mFaceMap.put("[拥抱]", R.drawable.f_static_045);
        mFaceMap.put("[坏笑]", R.drawable.f_static_046);
        mFaceMap.put("[飞吻]", R.drawable.f_static_047);
        mFaceMap.put("[鄙视]", R.drawable.f_static_048);
        mFaceMap.put("[晕]", R.drawable.f_static_049);
        mFaceMap.put("[大兵]", R.drawable.f_static_050);
        mFaceMap.put("[可怜]", R.drawable.f_static_051);
        mFaceMap.put("[强]", R.drawable.f_static_052);
        mFaceMap.put("[弱]", R.drawable.f_static_053);
        mFaceMap.put("[握手]", R.drawable.f_static_054);
        mFaceMap.put("[胜利]", R.drawable.f_static_055);
        mFaceMap.put("[抱拳]", R.drawable.f_static_056);
        mFaceMap.put("[凋谢]", R.drawable.f_static_057);
        mFaceMap.put("[饭]", R.drawable.f_static_058);
        mFaceMap.put("[蛋糕]", R.drawable.f_static_059);
        mFaceMap.put("[西瓜]", R.drawable.f_static_060);
        mFaceMap.put("[啤酒]", R.drawable.f_static_061);
        mFaceMap.put("[飘虫]", R.drawable.f_static_062);

        mFaceMap.put("[勾引]", R.drawable.f_static_063);
        mFaceMap.put("[OK]", R.drawable.f_static_064);
        mFaceMap.put("[爱你]", R.drawable.f_static_065);
        mFaceMap.put("[咖啡]", R.drawable.f_static_066);
        mFaceMap.put("[钱]", R.drawable.f_static_067);
        mFaceMap.put("[月亮]", R.drawable.f_static_068);
        mFaceMap.put("[美女]", R.drawable.f_static_069);
        mFaceMap.put("[刀]", R.drawable.f_static_070);
        mFaceMap.put("[发抖]", R.drawable.f_static_071);
        mFaceMap.put("[差劲]", R.drawable.f_static_072);
        mFaceMap.put("[拳头]", R.drawable.f_static_073);
        mFaceMap.put("[心碎]", R.drawable.f_static_074);
        mFaceMap.put("[太阳]", R.drawable.f_static_075);
        mFaceMap.put("[礼物]", R.drawable.f_static_076);
        mFaceMap.put("[足球]", R.drawable.f_static_077);
        mFaceMap.put("[骷髅]", R.drawable.f_static_078);
        mFaceMap.put("[挥手]", R.drawable.f_static_079);
        mFaceMap.put("[闪电]", R.drawable.f_static_080);
        mFaceMap.put("[饥饿]", R.drawable.f_static_081);
        mFaceMap.put("[困]", R.drawable.f_static_082);
        mFaceMap.put("[咒骂]", R.drawable.f_static_083);

        mFaceMap.put("[折磨]", R.drawable.f_static_084);
        mFaceMap.put("[抠鼻]", R.drawable.f_static_085);
        mFaceMap.put("[鼓掌]", R.drawable.f_static_086);
        mFaceMap.put("[糗大了]", R.drawable.f_static_087);
        mFaceMap.put("[左哼哼]", R.drawable.f_static_088);
        mFaceMap.put("[哈欠]", R.drawable.f_static_089);
        mFaceMap.put("[快哭了]", R.drawable.f_static_090);
        mFaceMap.put("[吓]", R.drawable.f_static_091);
        mFaceMap.put("[篮球]", R.drawable.f_static_092);
        mFaceMap.put("[乒乓球]", R.drawable.f_static_093);
        mFaceMap.put("[NO]", R.drawable.f_static_094);
        mFaceMap.put("[跳跳]", R.drawable.f_static_095);
        mFaceMap.put("[怄火]", R.drawable.f_static_096);
        mFaceMap.put("[转圈]", R.drawable.f_static_097);
        mFaceMap.put("[磕头]", R.drawable.f_static_098);
        mFaceMap.put("[回头]", R.drawable.f_static_099);
        mFaceMap.put("[跳绳]", R.drawable.f_static_100);
        mFaceMap.put("[激动]", R.drawable.f_static_101);
        mFaceMap.put("[街舞]", R.drawable.f_static_102);
        mFaceMap.put("[献吻]", R.drawable.f_static_103);
        mFaceMap.put("[左太极]", R.drawable.f_static_104);

        mFaceMap.put("[右太极]", R.drawable.f_static_105);
        mFaceMap.put("[闭嘴]", R.drawable.f_static_106);
    }

    public Map<String, InformationList> getRoomMap() {
        return roomMap;
    }

    public void setRoomMap(Map<String, InformationList> roomMap) {
        this.roomMap = roomMap;
    }

    public Map<String, MultiUserChat> getRoomListener() {
        return roomListener;
    }

    public void setRoomListener(Map<String, MultiUserChat> roomListener) {
        this.roomListener = roomListener;
    }

    public List<CallBean> getClList() {
        return clList;
    }

    public void setClList(List<CallBean> clList) {
        this.clList = clList;
    }

    public List<Contacts> getCltList() {
        return cltList;
    }

    public void setCltList(List<Contacts> cltList) {
        this.cltList = cltList;
    }


    public List<Contacts> getThjl() {
        return thjl;
    }

    public void setThjl(List<Contacts> thjl) {
        this.thjl = thjl;
    }

    // 在SD卡上获取并创建目录
    public static String getRenhuaSdcardDir() {
        String ret = android.os.Environment.getExternalStorageDirectory()
                .getAbsolutePath() + YETApplication.getContext().getString(R.string.chat_pathimg);
        File file = new File(ret);
        if (!file.exists())
            file.mkdirs();
        return ret;
    }

    //清除所有的sip账号
    public void clearAllSipAccount() {
        Log.e("", "*******************清除所有的sip账号");
        PreferenceBase.init(mContext);
        SharedPreferences prefs = PreferenceBase.getmPreferences();
        int nbAccounts = prefs.getInt(getString(R.string.pref_extra_accounts), 1);
        SharedPreferences.Editor editor = prefs.edit();

        for (int i = 0; i < nbAccounts - 1; i++) {
            editor.putString(getString(R.string.pref_username_key) + getAccountNumber(i), prefs.getString(getString(R.string.pref_username_key) + getAccountNumber(i + 1), null));
            editor.putString(getString(R.string.pref_passwd_key) + getAccountNumber(i), prefs.getString(getString(R.string.pref_passwd_key) + getAccountNumber(i + 1), null));
            editor.putString(getString(R.string.pref_domain_key) + getAccountNumber(i), prefs.getString(getString(R.string.pref_domain_key) + getAccountNumber(i + 1), null));
            editor.putString(getString(R.string.pref_proxy_key) + getAccountNumber(i), prefs.getString(getString(R.string.pref_proxy_key) + getAccountNumber(i + 1), null));
            editor.putBoolean(getString(R.string.pref_enable_outbound_proxy_key) + getAccountNumber(i), prefs.getBoolean(getString(R.string.pref_enable_outbound_proxy_key) + getAccountNumber(i + 1), false));
            editor.putBoolean(getString(R.string.pref_disable_account_key) + getAccountNumber(i), prefs.getBoolean(getString(R.string.pref_disable_account_key) + getAccountNumber(i + 1), false));
        }

//    	if (n != 0) {
        int lastAccount = nbAccounts - 1;
        editor.putString(getString(R.string.pref_username_key) + getAccountNumber(lastAccount), null);
        editor.putString(getString(R.string.pref_passwd_key) + getAccountNumber(lastAccount), null);
        editor.putString(getString(R.string.pref_domain_key) + getAccountNumber(lastAccount), null);
        editor.putString(getString(R.string.pref_proxy_key) + getAccountNumber(lastAccount), null);
        editor.putBoolean(getString(R.string.pref_enable_outbound_proxy_key) + getAccountNumber(lastAccount), false);
        editor.putBoolean(getString(R.string.pref_disable_account_key) + getAccountNumber(lastAccount), false);

        int defaultAccount = prefs.getInt(getString(R.string.pref_default_account_key), 0);
//    		if (defaultAccount > n) {
        editor.putInt(getString(R.string.pref_default_account_key), defaultAccount - 1);
//    		}
        editor.putInt(getString(R.string.pref_extra_accounts), nbAccounts - 1);
//    	} else if (n == 0 && nbAccounts <= 1) {
        editor.putString(getString(R.string.pref_username_key), "");
        editor.putString(getString(R.string.pref_passwd_key), "");
        editor.putString(getString(R.string.pref_domain_key), "");
//    	} else {
        editor.putInt(getString(R.string.pref_extra_accounts), nbAccounts - 1);
//    	}

        editor.commit();

//    	LinphoneActivity.instance().displaySettings();

    }

    private String getAccountNumber(int n) {
        if (n > 0)
            return Integer.toString(n);
        else
            return "";
    }

    //获取app版本号
    public String getAppVersion() {
        PackageManager manager = YETApplication.getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(YETApplication.getContext().getPackageName(), 0);
            Log.e("", "APPVERSION:" + info.versionCode + "   " + info.versionName);
            SettingInfo.setParams(PreferenceBean.APPVERSIONS, info.versionName);
            SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
            appVersion = info.versionName;
        } catch (NameNotFoundException e) {
            SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
            e.printStackTrace();
            appVersion = getString(R.string.app_version);
            return SettingInfo.getParams(PreferenceBean.APPVERSIONS, getString(R.string.app_version));
        }
        return info.versionName;
    }
}
