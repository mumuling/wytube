package com.cqxb.yecall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;

import com.cqxb.until.ACache;
import com.cqxb.yecall.bean.CallLogBean;
import com.cqxb.yecall.bean.ContactEntity;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.GPSThread;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.umeng.analytics.MobclickAgent;
import com.wytube.activity.OrderActivity;
import com.wytube.beans.BaseLogin;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.ShareOption;
import com.wytube.utlis.Utils;

import org.linphone.LinphoneService;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.mediastream.Log;
import org.linphone.tutorials.TutorialLauncherActivity;

import java.io.File;
import java.util.List;

import static android.content.Intent.ACTION_MAIN;

/**
 * 欢迎界面
 */
public class MainActivity extends Activity {
    //线程消息传递
    private Handler           mHandler;
    //检测服务启动线程
    private ServiceWaitThread mThread;
    //版本号
    private TextView          version;

    /**
     * 切回软件
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Main"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    /**
     * 切出软件
     */
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Main");// （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Used to change for the lifetime of the app the name used to tag the logs
        new Log(getResources().getString(R.string.app_name), !getResources().getBoolean(R.bool.disable_every_log));
        setContentView(R.layout.activity_main);
        version = (TextView) findViewById(R.id.app_version);
        version.setText(YETApplication.appVersion);
        // Hack to avoid to draw twice LinphoneActivity on tablets
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        LinphoneCoreFactory.instance().setDebugMode(true, "TEDI_SIP");

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                new DBHelper(getApplicationContext());
                if (LinphoneService.isReady()) {
                    //sql数据库
                    initSql();
                } else {
                    startService(new Intent(ACTION_MAIN).setClass(MainActivity.this, LinphoneService.class));
                    mThread = new ServiceWaitThread();
                    mThread.start();
                }
            }
        }, 1000);

        new GPSThread(this).start();
    }

    /**
     * 初始化数据脚本
     */
    private void initSql() {
        //图片储存路径
        File f = new File(Environment.getExternalStorageDirectory() + YETApplication.getContext().getString(R.string.chat_pathimg));
        Log.i(this.getClass(), " 路径:" + f.getAbsolutePath());
        //检测路径是否存在
        if (!f.exists()) {
            //创建目录
            f.mkdir();
        }

        YETApplication.instanceContext(getApplicationContext());
        SettingInfo.init(getApplicationContext());
        String back = SettingInfo.getParams(PreferenceBean.IS_FIRST_CREATE_DATA_SQL, "第一次登陆");
        Log.i(this.getClass(), " initSql " + back + "!!!!!!!!!!!!!!!!!!!!!!!!");
        //判断是否是第一次登陆
        if ("第一次登陆".equals(back)) {
            SettingInfo.setParams(PreferenceBean.IS_FIRST_CREATE_DATA_SQL, "创建系统表");
            //创建数据库操作类实例
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            //打开数据库
            dbHelper.open();
            // 好友消息列表
            String singleChat = "DROP TABLE IF EXISTS " + SingleChatEntity.TABLE;
            // 聊天记录
            String groupChat = "DROP TABLE IF EXISTS " + GroupChatEntity.TABLE;
            // 联系人列表
            String information = "DROP TABLE IF EXISTS " + InformationList.TABLE;
            // 群聊
            String contact = "DROP TABLE IF EXISTS " + ContactEntity.TABLE;
            // 通话记录
            String callLog = "DROP TABLE IF EXISTS " + CallLogBean.TABLE;

            dbHelper.execSql(singleChat, null);
            dbHelper.execSql(groupChat, null);
            dbHelper.execSql(information, null);
            dbHelper.execSql(contact, null);
            dbHelper.execSql(callLog, null);

            // 单人聊天
            String singleChatCreate = "CREATE TABLE  IF NOT EXISTS " + SingleChatEntity.TABLE
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SingleChatEntity.USERID + " text , " +
                    SingleChatEntity.FRIENDID + " text  , " +
                    SingleChatEntity.CONTEXT + " text," +
                    SingleChatEntity.WHO + " text," +
                    SingleChatEntity.ISREAD + " text, " +
                    SingleChatEntity.NICKNAME + " text," +
                    SingleChatEntity.MSGDATE + " text)";
            // 群聊
            String groupChatCreate = "CREATE TABLE  IF NOT EXISTS " + GroupChatEntity.TABLE
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GroupChatEntity.ROOMID + " text , " +
                    GroupChatEntity.FRIENDID + " text  , " +
                    GroupChatEntity.CONTEXT + " text," +
                    GroupChatEntity.WHO + " text," +
                    GroupChatEntity.ISREAD + " text, " +
                    GroupChatEntity.NICKNAME + " text," +
                    GroupChatEntity.GID + " text," +
                    GroupChatEntity.MSGDATE + " text)";
            // 消息列表
            String informationCreate = "CREATE TABLE  IF NOT EXISTS " + InformationList.TABLE
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InformationList.GID + " text , " +
                    InformationList.FRIENDID + " text , " +
                    InformationList.ROOMID + " text , " +
                    InformationList.NICKNAME + " text, " +
                    InformationList.CONTEXT + " text," +
                    InformationList.MSGDATE + " text," +
                    InformationList.FRIENDIMG + " text," +
                    InformationList.OBJECT + " text," +
                    InformationList.FLAG + " text)";
            //near "group": syntax error (code 1): , while compiling:
            //CREATE TABLE  IF NOT EXISTS contactList( _id INTEGER PRIMARY KEY AUTOINCREMENT,
            //friendId text,nickName text,friendImg text,visibility
            //text,descr text,group text,visibilityImg text,status text)
            // 联系人
            String contactCreate = "CREATE TABLE  IF NOT EXISTS " + ContactEntity.TABLE
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ContactEntity.FRIENDID + " text," +
                    ContactEntity.NICKNAME + " text," +
                    ContactEntity.FRIENDIMG + " text," +
                    ContactEntity.VISIBILITY + " text," +
                    ContactEntity.DESCR + " text," +
                    ContactEntity.GROUP + " text," +
                    ContactEntity.VISIBILITYIMG + " text," +
                    ContactEntity.STATUS + " text)";

            // 通话记录
            String callLogs = "CREATE TABLE  IF NOT EXISTS " + CallLogBean.TABLE
                    + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CallLogBean.NAME + " text," +
                    CallLogBean.NUMBER + " text," +
                    CallLogBean.TYPE + " text," +
                    CallLogBean.CALLTIME + " text," +
                    CallLogBean.BELONG + " text," +
                    CallLogBean.NEWS + " text," +
                    CallLogBean.STARTCALL + " text," +
                    CallLogBean.RECORDPATH + " text," +
                    CallLogBean.PHOTOPATH + " text)";
            dbHelper.execSql(singleChatCreate, null);
            dbHelper.execSql(groupChatCreate, null);
            dbHelper.execSql(informationCreate, null);
            dbHelper.execSql(contactCreate, null);
            dbHelper.execSql(callLogs, null);
            Log.i(this.getClass(), " 数据插入成功!");
            dbHelper.close();
        }
        //获取手机通讯录
        initCallRecord();
    }

    /**
     * 获取手机通讯录
     */
    public void initCallRecord() {
        // 联系人
        List<Contacts> cltList = YETApplication.getinstant().getCltList();
        ContactBase    cb      = new ContactBase(getApplicationContext());
        // cb.testAddContact(getApplicationContext(), "在仄仄仄仄仄仄仄仄仄仄仄仄",
        // "1231231231231");
        // ContactBase.insertCallLog(getApplicationContext(), "zzzzzzzzzz",
        // "123456465465", 2, 30, System.currentTimeMillis());
        if (cltList.size() <= 0) {

            List<Contacts> allcontact = cb.getAllcontact();
            YETApplication.getinstant().setCltList(allcontact);
        }
//		// 通话记录
//		List<CallBean> clList = YETApplication.getinstant().getClList();
//		if (clList.size() <= 0) {
//			List<CallBean> allcontact = cb.getPhoneCallList();
//			YETApplication.getinstant().setClList(allcontact);
//		}
        onServiceReady();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//	private Handler handler=new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 1:
//				startActivity(new Intent(MainActivity.this,LoginActivity.class));
//				finish();
//				break;
//
//			default:
//				break;
//			}
//		}
//
//	};

    /**
     * 检查服务启动的线程
     */
    private class ServiceWaitThread extends Thread {
        public void run() {
            while (!LinphoneService.isReady()) {
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException("waiting thread sleep() has been interrupted");
                }
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
//					onServiceReady();
                    //初始化数据库
                    initSql();
                }
            });
            mThread = null;
        }
    }

    /**
     * 跳转Activity
     */
    protected void onServiceReady() {
        final Class<? extends Activity> classToStart;
        if (getResources().getBoolean(R.bool.show_tutorials_instead_of_app)) {
            classToStart = TutorialLauncherActivity.class;
        } else {
            classToStart = OrderActivity.class;
        }
        LinphoneService.instance().setActivityToLaunchOnIncomingReceived(classToStart);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.firstRun(MainActivity.this)) {
                    startActivity(new Intent(MainActivity.this, WelcomActivity.class));
                    finish();
                } else {
                    /*自动登录*/
                    ACache mCache = ACache.get(MainActivity.this);
                    String value = mCache.getAsString("token");
                    String password = mCache.getAsString("password");
                    if (value!=null){
                        String login_state = Utils.clearString(ShareOption.readerString("LOGIN_STATE", MainActivity.this));
                        if (login_state != null && login_state.length() > 0) {
                            String usr = login_state.split(":")[0];
                            String pwd = login_state.split(":")[1];
                            String keyValue;
                            if (password!=null){
                                keyValue = "account=" + usr + "&password=" + password;
                                AppValue.YhPass = password;
                            }else {
                                keyValue = "account=" + usr + "&password=" + pwd;
                                AppValue.YhPass = pwd;
                            }
                            Client.sendPost(NetParmet.USR_LOGIN, keyValue, new Handler(msg -> {
                                String json = msg.getData().getString("post");
                                BaseLogin bean = Json.toObject(json, BaseLogin.class);
                                if (bean == null) {
                                    startActivity(new Intent().setClass(MainActivity.this, classToStart).setData(getIntent().getData()));
                                    return false;
                                }
                                if (!bean.isSuccess()) {
                                    Utils.showOkDialog(MainActivity.this, bean.getMessage());
                                    startActivity(new Intent().setClass(MainActivity.this, classToStart).setData(getIntent().getData()));
                                    return false;
                                }
                                saveData(bean);
                                AppValue.online = true;
                                AppValue.TextName = bean.getData().getUserDTO().getUserName();
                                startActivity(new Intent().setClass(MainActivity.this, classToStart).setData(getIntent().getData()));
                                finish();
                                return false;
                            }));
                        }
                    }else {
                        SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
                        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
                        if (LinphoneService.instance() != null) LinphoneService.instance().deleteOldAccount();
                        SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
                        SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, "");
                        SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, "");
                        SettingInfo.setLinphoneAccount("");
                        SettingInfo.setLinphonePassword("");
                        SettingInfo.setPassword("");
                        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
                        startActivity(new Intent(MainActivity.this, LoginAppActivity.class));
                        finish();
                    }
                }
            }
        }, 1000);
    }

    /* 保存数据
    * @param bean 服务器返回的JSON数据反序列化的对象
    */
    private void saveData(BaseLogin bean) {
        if (bean!=null){
            AppValue.token = bean.getData().getToken();
            AppValue.sipName = "18680808185";
            AppValue.sipPass = "123456";
            AppValue.sipAddr = "pro1.123667.com";
            AppValue.sipProt = "5070";
        }
    }


    /**
     * 检查是否第一次运行该APP
     *
     * @param context APP参数
     * @return 返回true则表示是第一次运行, 返回false则表示不是第一次运行
     */
    public static boolean firstRun(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("share", Context.MODE_PRIVATE);

        boolean isFirstRun = sharedPreferences.getBoolean("FIRST_RUN", true);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isFirstRun) {

            editor.putBoolean("FIRST_RUN", false);

            editor.apply();

            return true;
        } else {

            return false;
        }
    }

}
