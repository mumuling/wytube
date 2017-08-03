package com.wytube.activity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cqxb.until.Config;
import com.cqxb.yecall.InformationActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.umeng.analytics.MobclickAgent;

import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphonePreferences;
import org.linphone.LinphoneService;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.OnlineStatus;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.ACTION_MAIN;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/16.
 * 类 描 述: 主页面
 */
public class OrderActivity extends ActivityGroup {
	public ImageView img; //图像
	public LinearLayout imgGroup; // 图像容器
	public static OrderActivity instance; // 实例
	private View previousView;
	private View currentView;
	private int currentTab;
	private TabHost tabHost;
	private int flag = 1;
	private static final int ANIMATION_TIME = 350;
	private static OrderActivity activity;
	private SharedPreferences mPref;
	private static final int CALL_ACTIVITY = 19;

	/**
	 * 获取MainActivity的实例
	 *
	 * @return
	 */
	public static OrderActivity instance() {
		return activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingInfo.init(getApplicationContext());
		instance = this;
		setContentView(R.layout.dial_menu);
		img = (ImageView) findViewById(R.id.main_show_img);
		imgGroup = (LinearLayout) findViewById(R.id.main_img_group);
		tabHost = (TabHost) findViewById(R.id.myTabHost);
		// 初始化
		YETApplication.getinstant().addActivity(this);
			addTab();
			// 美化tabHost背景
			for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
				tabHost.getTabWidget().getChildAt(i)
						.setBackgroundColor(Color.parseColor("#dddddd"));
			}
		registerReceiver(broadcastReceiver, new IntentFilter(Smack.action));


		mPref = PreferenceManager.getDefaultSharedPreferences(this);
		String checkLogin = SettingInfo.getParams(PreferenceBean.CHECKLOGIN, "");
		if ("".equals(checkLogin)) {
//		progressDlg = ProgressDialog.show(LinphoneActivity.this.getParent(), null, "初始化话机中。。。");
//		progressDlg.setCanceledOnTouchOutside(true);
//		progressDlg.show();
//		deleteOldAccount();
			new Thread(new Runnable() {
				@Override
				public void run() {
					timer.schedule(new timetask(), 500);
				}
			}).start();
		}
	}


	private Timer timer = new Timer();
	class timetask extends TimerTask {
		@Override
		public void run() {
//			Log.e("Linphone", "account:"+SettingInfo.getLinphoneAccount()+"  pwd:"+SettingInfo.getLinphonePassword()+"   ip:"+SettingInfo.getParams(PreferenceBean.USERLINPHONEIP, "")+"  port:"+SettingInfo.getParams(PreferenceBean.USERLINPHONEPORT,""));
			logIn(SettingInfo.getLinphoneAccount(), SettingInfo.getLinphonePassword(), SettingInfo.getParams(PreferenceBean.USERLINPHONEIP, "") + ":" + SettingInfo.getParams(PreferenceBean.USERLINPHONEPORT, ""), false);
			Log.e("Linphone", "Linphone  注册。。。。。。。。");
			LinphoneManager.getInstance().startLin();
			String regist = SettingInfo.getParams(PreferenceBean.USERLINPHONEREGISTOK, "");
			if (!"".equals(regist)) {
				if (timer != null) timer.cancel();
//				if (progressDlg != null) progressDlg.cancel();
			}
		}
	}

	private void logIn(String username, String password, String domain, boolean sendEcCalibrationResult) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null && getCurrentFocus() != null) {
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
		saveCreatedAccount(username, password, domain);
		if (LinphoneManager.getLc().getDefaultProxyConfig() != null) {
			launchEchoCancellerCalibration(sendEcCalibrationResult);
		}
	}

	private void launchEchoCancellerCalibration(boolean sendEcCalibrationResult) {
		boolean needsEchoCalibration = LinphoneManager.getLc().needsEchoCalibration();
		if (needsEchoCalibration && !mPref.getBoolean(getString(R.string.first_launch_suceeded_once_key), false)) {
			success();
		} else {
			success();
		}
	}

	public void success() {
		mPref.edit().putBoolean(getString(R.string.first_launch_suceeded_once_key), true);
		setResult(Activity.RESULT_OK);
		if (timer != null) timer.cancel();
//		if (progressDlg != null) progressDlg.dismiss();
		LinphoneService.instance().setActivityToLaunchOnIncomingReceived(InCallActivity.class);
//		finish();
	}


	private LinphonePreferences mPrefs;
	private boolean accountCreated = false;
	public void saveCreatedAccount(String username, String password, String domain) {
		if (accountCreated)
			return;

		boolean isMainAccountLinphoneDotOrg = domain.equals(getString(R.string.default_domain));
		boolean useLinphoneDotOrgCustomPorts = getResources().getBoolean(R.bool.use_linphone_server_ports);
		LinphonePreferences.AccountBuilder builder = new LinphonePreferences.AccountBuilder(LinphoneManager.getLc())
				.setUsername(username)
				.setDomain(domain)
				.setPassword(password);

		if (isMainAccountLinphoneDotOrg && useLinphoneDotOrgCustomPorts) {
			if (getResources().getBoolean(R.bool.disable_all_security_features_for_markets)) {
				android.util.Log.e(this.getClass().toString(), domain + ":5228");
				builder.setProxy(domain + ":5228")
						.setTransport(LinphoneAddress.TransportType.LinphoneTransportTcp);
			} else {
				android.util.Log.e(this.getClass().toString(), domain + ":5228");
				builder.setProxy(domain + ":5223")
						.setTransport(LinphoneAddress.TransportType.LinphoneTransportTls);
			}

			builder.setExpires("604800")
					.setOutboundProxyEnabled(true)
					.setAvpfEnabled(true)
					.setAvpfRRInterval(3)
					.setQualityReportingCollector("sip:voip-metrics@sip.linphone.org")
					.setQualityReportingEnabled(true)
					.setQualityReportingInterval(180)
					.setRealm("sip.linphone.org");
			mPrefs.setStunServer(getString(R.string.default_stun));
			mPrefs.setIceEnabled(true);
			mPrefs.setPushNotificationEnabled(true);
		} else {
			String forcedProxy = getResources().getString(R.string.setup_forced_proxy);
			if (!TextUtils.isEmpty(forcedProxy)) {
				builder.setProxy(forcedProxy)
						.setOutboundProxyEnabled(true)
						.setAvpfRRInterval(5);
			}
		}

//		if (getResources().getBoolean(R.bool.enable_push_id)) {
//			String regId = mPrefs.getPushNotificationRegistrationID();
//			String appId = getString(R.string.push_sender_id);
//			if (regId != null && mPrefs.isPushNotificationEnabled()) {
//				String contactInfos = "app-id=" + appId + ";pn-type=google;pn-tok=" + regId;
//				builder.setContactParameters(contactInfos);
//			}
//		}

		try {
			builder.saveNewAccount();
			accountCreated = true;
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
	}

	private void addTab() {
		// 如果不是继承TabActivity，则必须在得到tabHost之后，添加标签之前调用tabHost.setup()
		tabHost.setup(this.getLocalActivityManager());// 不写这句话会报错
        /* 去除标签下方的白线 */
		tabHost.setPadding(tabHost.getPaddingLeft(), tabHost.getPaddingTop(),
				tabHost.getPaddingRight(), tabHost.getPaddingBottom() - 5);
		Intent layout0intent = new Intent();
		layout0intent.setClass(this, InformationActivity.class);
		TabHost.TabSpec mDialerTabSpec1 = tabHost.newTabSpec("dialer1");
		mDialerTabSpec1.setIndicator(getTabView(R.drawable.tab_public));
		mDialerTabSpec1.setContent(layout0intent);

		/*小区管理*/
		Intent layout2intent = new Intent();
		layout2intent.setClass(this, IndexActivity.class);
		TabHost.TabSpec mDialerTabSpec3 = tabHost.newTabSpec("dialer3");
		mDialerTabSpec3.setIndicator(getTabView("小区管理", R.drawable.tab_dialer_up));
		mDialerTabSpec3.setContent(layout2intent);
		tabHost.addTab(mDialerTabSpec3);

		/*社区管理*/
		Intent layout1intent = new Intent();
		layout1intent.setClass(this, CommunityActivity.class);
		TabHost.TabSpec mDialerTabSpec2 = tabHost.newTabSpec("dialer2");
		mDialerTabSpec2.setIndicator(getTabView("社区管理", R.drawable.tab_contacts));
		mDialerTabSpec2.setContent(layout1intent);
		tabHost.addTab(mDialerTabSpec2);

		/*我的*/
		Intent layout7intent = new Intent();
		layout7intent.setClass(this, MyActivity.class);
		TabHost.TabSpec mDialerTabSpec7 = tabHost.newTabSpec("dialer7");
		mDialerTabSpec7.setIndicator(getTabView("我的",R.drawable.tab_setting));
		mDialerTabSpec7.setContent(layout7intent);
		tabHost.addTab(mDialerTabSpec7);

		tabHost.setCurrentTab(0);

		// 1D88CE 之前的颜色代码
		initTabHostTvColor(0, "#AFAFAF");
		initTabHostTvColor(1, "#AFAFAF");
		initTabHostTvColor(2, "#AFAFAF");


		((TextView) tabHost.getTabWidget().getChildAt(1)
				.findViewById(R.id.title)).setTextColor(Color
				.parseColor("#AFAFAF"));
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

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

				TextView text = (TextView) tabHost.getTabWidget().getChildAt(0)
						.findViewById(R.id.title);
				TextView text1 = (TextView) tabHost.getTabWidget()
						.getChildAt(1).findViewById(R.id.title);
				TextView text2 = (TextView) tabHost.getTabWidget()
						.getChildAt(2).findViewById(R.id.title);
			}
		});
	}
	public View getTabView(String label, int resId) {
		final LayoutInflater inflater = LayoutInflater.from(OrderActivity.this);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.tab_styles, null).findViewById(R.id.layoutTabStyle);
		((ImageView) (layout.findViewById(R.id.image))).setImageResource(resId);
		((TextView) (layout.findViewById(R.id.title))).setText(label);
		Log.i("agent", "tab style height:" + layout.getHeight());
		return layout;
	}

	public View getTabView(int resId) {
		final LayoutInflater inflater = LayoutInflater.from(OrderActivity.this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(
				R.layout.tab_style, null).findViewById(R.id.layoutTabStyle);
		((ImageView) (layout.findViewById(R.id.image))).setImageResource(resId);
		Log.i("agent", "tab style height:" + layout.getHeight());
		return layout;
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
	/**
	 * 初始化底部导航栏字体颜色
	 *
	 * @param positon 选项卡位置
	 * @param color   颜色代码
	 */
	private void initTabHostTvColor(int positon, String color) {
		((TextView) tabHost.getTabWidget().getChildAt(positon)
				.findViewById(R.id.title))
				.setTextColor(Color.parseColor(color));
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
	public void hiddenImg() {
		img.setVisibility(View.GONE);
		imgGroup.setVisibility(View.GONE);
		Config.imgShow = false;
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
	 * 显示图片
	 *
	 * @param imgUrl
	 */
	public void showImg(String imgUrl) {
		Bitmap bp = null;
		float scaleWidth;
		float scaleHeight;

//        Display display = mContext.getWindowManager().getDefaultDisplay();
//        imageview = (ImageView) findViewById(R.id.iv_image_detail);

		WindowManager wm = (WindowManager) instance.getSystemService(Context.WINDOW_SERVICE);

		bp = BitmapFactory.decodeFile(imgUrl);
		int width = bp.getWidth();
		int height = bp.getHeight();
		int w = wm.getDefaultDisplay().getWidth();
		int h = wm.getDefaultDisplay().getHeight();
		scaleWidth = ((float) w) / width;
		scaleHeight = ((float) h) / height;
		//imageview.setImageBitmap(bp);

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
				bp.getHeight(), matrix, true);
		img.setImageBitmap(newBitmap);

		imgGroup.setVisibility(View.VISIBLE);
		img.setVisibility(View.VISIBLE);

		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				img.setVisibility(View.GONE);
				imgGroup.setVisibility(View.GONE);
			}
		});
	}
}
