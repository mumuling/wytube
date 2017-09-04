package com.cqxb.yecall;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.umeng.analytics.MobclickAgent;

import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import java.util.List;

import static android.content.Intent.ACTION_MAIN;

/**
 * 主页面
 * */
public class MainTabActivity extends ActivityGroup {
    private TabHost tabHost;
    private int flag = 1;
    private String TAG = "MainTabActivity";
    private View previousView;
    private View currentView;
    private int currentTab;
    private static final int ANIMATION_TIME = 350;
    public ImageView img; //图像
    public LinearLayout imgGroup; // 图像容器
    public static MainTabActivity instance; // 实例


    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainTab"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainTab"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 处理用户信息数据
        SettingInfo.init(getApplicationContext());
        instance = this;
        setContentView(R.layout.activity_tab_mains);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.text_name).setOnClickListener(v -> {finish();});
        findViewById(R.id.menu_text).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.dir/person");
            intent.setType("vnd.android.cursor.dir/contact");
            intent.setType("vnd.android.cursor.dir/raw_contact");
            String inputNumber = LinphoneActivity.instance()
                    .getInputNumber();
            intent.putExtra("phone", inputNumber);
            startActivity(intent);
        });
//        findViewById(R.id.kf_but).setOnClickListener(v -> {startActivity(new Intent(this, ServiceCenterActivity.class));});/*服务中心*/

        img = (ImageView) findViewById(R.id.main_show_img);
        imgGroup = (LinearLayout) findViewById(R.id.main_img_group);
        getCallLogs();
        tabHost = (TabHost) findViewById(R.id.myTabHost);
        // 初始化
        YETApplication.getinstant().addActivity(this);
        addTab();
        // 初始化存储
        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "loginOk");
        registerReceiver(broadcastReceiver, new IntentFilter(Smack.action));
        Log.e("", "MainTabActivity  oncreate");
    }

    /**
     * 添加选项卡
     */
    private void addTab() {
        tabHost.setup(this.getLocalActivityManager());// 不写这句话会报错
        // 拨号
        Intent layout2intent = new Intent();
        // layout2intent.setClass(this, DialingActivity.class);
        layout2intent.setClass(this, LinphoneActivity.class);
        TabHost.TabSpec mDialerTabSpec3 = tabHost.newTabSpec("dialer3");
        mDialerTabSpec3.setIndicator("呼叫");
//                .setIndicator(getTabView("呼叫", R.drawable.tab_dialer_up));
        mDialerTabSpec3.setContent(layout2intent);
        // mDialerTabSpec3.setContent(R.id.view3);
        tabHost.addTab(mDialerTabSpec3);

        // 联系人
        Intent layout1intent = new Intent();
        // layout1intent.setClass(this, FriendGroupShow.class);
        layout1intent.setClass(this, NewsletterActivity.class);
        Bundle bundle = new Bundle();
        layout1intent.putExtras(bundle);
        TabHost.TabSpec mDialerTabSpec2 = tabHost.newTabSpec("dialer2");
        mDialerTabSpec2.setIndicator("联系人");
//                .setIndicator(getTabView("联系人", R.drawable.tab_contacts));
        mDialerTabSpec2.setContent(layout1intent);
        // mDialerTabSpec2.setContent(R.id.view2);
        tabHost.addTab(mDialerTabSpec2);

        // 创建tab
        tabHost.setCurrentTab(0);
        TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.main_def_text_color));// 设置Tab栏字体的颜色
        }

        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            {
                previousView = tabHost.getCurrentView();
            }

            @Override
            public void onTabChanged(String tabId) {
                /*获取当前显示的视图*/
                currentView = tabHost.getCurrentView();
                /*如果当前选卡大于当前视图*/
                if (tabHost.getCurrentTab() > currentTab) {
                    previousView.setAnimation(outToLeftAnimation());
                    currentView.setAnimation(inFromRightAnimation());
                } else {
                    previousView.setAnimation(outToRightAnimation());
                    currentView.setAnimation(inFromLeftAnimation());
                }
                previousView = currentView;
                currentTab = tabHost.getCurrentTab();

                if (!"dialer3".equals(tabId)) {
                    flag = 0;
                    findViewById(R.id.call_img).setVisibility(View.GONE);
                    LinphoneActivity.instance().clearAddress();
                }
            }
        });
    }
    public void setDialerPanVisibility(int visibility) {
        findViewById(R.id.call_img).setVisibility(visibility);
    }

    /**
     * 调用拨打电话功能
     *
     * @param view
     */
    public void callPhone(View view) {
        Log.i("TabMain", "TabMain 拨号");
        Activity currentActivity = getCurrentActivity();
        if (currentActivity instanceof LinphoneActivity) {
            ((LinphoneActivity) currentActivity).callOut();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
        super.onDestroy();
        if (LinphoneManager.isInstanciated()) {
            LinphoneManager.getLcIfManagerNotDestroyedOrNull().setPresenceInfo(
                    0, "", OnlineStatus.Offline);
        }
        stopService(new Intent(ACTION_MAIN).setClass(this,
                LinphoneService.class));
        finish();
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("backMune".equals(intent.getStringExtra("backMune"))) {
                Intent in = new Intent(ACTION_MAIN);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                in.addCategory(Intent.CATEGORY_HOME);
                startActivity(in);
            }
        }
    };

    /**
     * 获取通话记录
     */
    public void getCallLogs() {
        System.out.println("插入成功:::::::::::" + SettingInfo.getAccount());
        try {
            // 通话记录
            if (BaseUntil.stringNoNull(SettingInfo.getAccount()).trim()
                    .equals("")) {
                return;
            }
            List<Contacts> clList = YETApplication.getinstant().getThjl();
            clList.clear();
            if (clList.size() <= 0) {
                ContactBase cb = new ContactBase(getApplicationContext());
                List<Contacts> allcontact = cb.getPhoneCallLists();
                YETApplication.getinstant().setThjl(allcontact);
            }
        } catch (Exception e) {
            Log.e("",
                    "loginAppActivity  line 380 ===>>>  "
                            + e.getLocalizedMessage());
        }

    }


    /**
     * 自定义动画, 此动画是从左向右进入当前窗口的动画
     *
     * @return 返回一个Animation对象
     */
    private Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(inFromRight);
    }

    /**
     * 自定义动画,此动画是从做往右退出的动画
     *
     * @return 返回一个Animation对象
     */
    private Animation outToRightAnimation() {
        Animation outToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outToRight);
    }

    /**
     * 自定义动画,此动画是从左进入当前窗口
     *
     * @return 返回一个Animation对象
     */
    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(inFromLeft);
    }

    /**
     * 自定义动画,此动画是从当前窗口想左移出
     *
     * @return 返回一个Animation对象
     */
    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
        return setProperties(outtoLeft);
    }

    /**
     * 设置动画的播放时间
     *
     * @param animation 动画
     * @return 返回设置了时间的动画对象
     */
    private Animation setProperties(Animation animation) {
        animation.setDuration(ANIMATION_TIME);
        /*设置动画先加速后减速*/
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        return animation;
    }

}
