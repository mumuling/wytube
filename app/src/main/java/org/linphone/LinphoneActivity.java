package org.linphone;

/*
 LinphoneActivity.java
 Copyright (C) 2012  Belledonne Communications, Grenoble, France

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.until.Config;
import com.cqxb.yecall.MainActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.wytube.activity.OrderActivity;

import org.linphone.LinphoneManager.AddressType;
import org.linphone.LinphonePreferences.AccountBuilder;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnMessageReceivedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnRegistrationStateChangedListener;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.CallDirection;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneAddress.TransportType;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallLog;
import org.linphone.core.LinphoneCallLog.CallStatus;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.core.PayloadType;
import org.linphone.mediastream.Log;
import org.linphone.ui.AddressText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.ACTION_MAIN;

/**
 * @author Sylvain Berfini
 */

public class LinphoneActivity extends FragmentActivity implements
        ContactPicked, LinphoneOnCallStateChangedListener,
        LinphoneOnMessageReceivedListener,
        LinphoneOnRegistrationStateChangedListener {
    public static final String PREF_FIRST_LAUNCH = "pref_first_launch";
    private static final int SETTINGS_ACTIVITY = 123;
    private static final int FIRST_LOGIN_ACTIVITY = 101;
    private static final int REMOTE_PROVISIONING_LOGIN_ACTIVITY = 102;
    private static final int CALL_ACTIVITY = 19;

    private static LinphoneActivity instance;

    private StatusFragment statusFragment;
    private FragmentsAvailable currentFragment, nextFragment;
    private Fragment dialerFragment;
    private SavedState dialerSavedState;
    private boolean preferLinphoneContacts = false, isAnimationDisabled = false, isContactPresenceDisabled = true;
    private Handler mHandler = new Handler();
    private List<Contact> contactList, sipContactList;
    private Cursor contactCursor, sipContactCursor;
    private OrientationEventListener mOrientationHelper;
    private SharedPreferences mPref;
    private ProgressDialog progressDlg;
    private String loginFlag = "";


    static final boolean isInstanciated() {
        return instance != null;
    }

    public static final LinphoneActivity instance() {
        if (instance != null)
            return instance;
        throw new RuntimeException("LinphoneActivity not instantiated yet");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loginFlag = SettingInfo.getParams(PreferenceBean.LOGINFLAG, "");
        if (loginFlag.equals("true")) {
            if (isTablet() && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (!isTablet() && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            if (!LinphoneManager.isInstanciated()) {
                Log.e("No service running: avoid crash by starting the launcher", this.getClass().getName());
                // super.onCreate called earlier
                finish();
                startActivity(getIntent().setClass(this, MainActivity.class));
                return;
            }
            mPrefs = LinphonePreferences.instance();

            SettingInfo.init(getApplicationContext());
            mPref = PreferenceManager.getDefaultSharedPreferences(this);

//			mPrefs.sendDtmfsAsRfc2833(false);
//			mPrefs.sendDTMFsAsSipInfo(true);

            String checkLogin = SettingInfo.getParams(PreferenceBean.CHECKLOGIN, "");
            if ("".equals(checkLogin)) {
                progressDlg = ProgressDialog.show(LinphoneActivity.this.getParent(), null, "初始化话机中。。。");
                progressDlg.setCanceledOnTouchOutside(true);
                progressDlg.show();
                deleteOldAccount();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        timer.schedule(new timetask(), 500);
                    }
                }).start();
            }
        }

        instance = this;
        currentFragment = nextFragment = FragmentsAvailable.DIALER;
        if (savedInstanceState == null) {
            if (findViewById(R.id.fragmentContainer) != null) {
                dialerFragment = new DialerFragment();
                dialerFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, dialerFragment, currentFragment.toString()).commitAllowingStateLoss();
                selectMenu(FragmentsAvailable.DIALER);
            }
        }

//			int rotation = getWindowManager().getDefaultDisplay().getRotation();
//			switch (rotation) {
//			case Surface.ROTATION_0:
//				rotation = 0;
//				break;
//			case Surface.ROTATION_90:
//				rotation = 90;
//				break;
//			case Surface.ROTATION_180:
//				rotation = 180;
//				break;
//			case Surface.ROTATION_270:
//				rotation = 270;
//				break;
//			}
//
//			LinphoneManager.getLc().setDeviceRotation(rotation);
//			mAlwaysChangingPhoneAngle = rotation;
        if (loginFlag.equals("true")) {
            updateAnimationsState();

            IntentFilter filter = new IntentFilter(Smack.action);
            registerReceiver(broadcastReceiver, filter);
            setVoiceCode();
        }
    }


    /**
     * 设置电话编码
     * 代码来自 class SettingsFragment
     * 函数：initAudioSettings
     */
    public void setVoiceCode() {
        try {
            LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
            for (final PayloadType pt : lc.getAudioCodecs()) {
                //===>>G729  8000  annexb=no  null  false
                //Log.e(Log.TAG, "===>>"+pt.getMime());
//				Log.e("", "===>>"+pt.getMime()+"  "+pt.getRate()+"  "+pt.getRecvFmtp()+"  "+pt.getSendFmtp()+"  "+lc.isPayloadTypeEnabled(pt));
                //LinphoneManager.getLcIfManagerNotDestroyedOrNull().enablePayloadType(pt, true);
                if ("PCMU".equals(pt.getMime()) || "PCMA".equals(pt.getMime()) || "G729".equals(pt.getMime()) || "iLBC".equals(pt.getMime())) {
                    LinphoneManager.getLcIfManagerNotDestroyedOrNull().enablePayloadType(pt, true);
                } else {
                    LinphoneManager.getLcIfManagerNotDestroyedOrNull().enablePayloadType(pt, false);
                }
            }
        } catch (Exception e) {
            Log.e("line 214", "LinManage==>>开启语音编码失败:" + e.getLocalizedMessage());
        }
    }

    public void deleteOldAccount() {
        // Get already configured extra accounts
        // 添加用户前先删除老账户
        int nbAccounts = mPrefs.getAccountCount();
        for (int i = 0; i < nbAccounts; i++) {
            final int accountId = i;
//			Log.e("", i + "----删掉的账户:" + mPrefs.getAccountUsername(accountId)
//					+ "@" + mPrefs.getAccountDomain(accountId));
            try {
                mPrefs.deleteAccount(accountId);
            } catch (Exception e) {
                Log.e("", "" + e.getLocalizedMessage());
            }
            // For each, add menus to configure it
        }
        if (LinphoneManager.isInstanciated()) {
            LinphoneManager.getLc().refreshRegisters();
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
                if (progressDlg != null) progressDlg.cancel();
            }
        }

    }


    private void logIn(String username, String password, String domain, boolean sendEcCalibrationResult) {
        saveCreatedAccount(username, password, domain);
         /*隐藏软键盘*/
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

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
        if (progressDlg != null) progressDlg.dismiss();
        LinphoneService.instance().setActivityToLaunchOnIncomingReceived(OrderActivity.class);
//		finish();
    }

    private LinphonePreferences mPrefs;
    private boolean accountCreated = false;

    public void saveCreatedAccount(String username, String password, String domain) {
        if (accountCreated)
            return;

        boolean isMainAccountLinphoneDotOrg = domain.equals(getString(R.string.default_domain));
        boolean useLinphoneDotOrgCustomPorts = getResources().getBoolean(R.bool.use_linphone_server_ports);
        AccountBuilder builder = new AccountBuilder(LinphoneManager.getLc())
                .setUsername(username)
                .setDomain(domain)
                .setPassword(password);

        if (isMainAccountLinphoneDotOrg && useLinphoneDotOrgCustomPorts) {
            if (getResources().getBoolean(R.bool.disable_all_security_features_for_markets)) {
                android.util.Log.e(this.getClass().toString(), domain + ":5228");
                builder.setProxy(domain + ":5228")
                        .setTransport(TransportType.LinphoneTransportTcp);
            } else {
                android.util.Log.e(this.getClass().toString(), domain + ":5228");
                builder.setProxy(domain + ":5223")
                        .setTransport(TransportType.LinphoneTransportTls);
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

        if (getResources().getBoolean(R.bool.enable_push_id)) {
            String regId = mPrefs.getPushNotificationRegistrationID();
            String appId = getString(R.string.push_sender_id);
            if (regId != null && mPrefs.isPushNotificationEnabled()) {
                String contactInfos = "app-id=" + appId + ";pn-type=google;pn-tok=" + regId;
                builder.setContactParameters(contactInfos);
            }
        }

        try {
            builder.saveNewAccount();
            accountCreated = true;
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void hideStatusBar() {
        if (isTablet()) {
            return;
        }

        findViewById(R.id.fragmentContainer).setPadding(0, 0, 0, 0);
    }

    private void showStatusBar() {
        if (isTablet()) {
            return;
        }

        if (statusFragment != null && !statusFragment.isVisible()) {
            // Hack to ensure statusFragment is visible after coming back to
            // dialer from chat
            statusFragment.getView().setVisibility(View.VISIBLE);
        }
        findViewById(R.id.fragmentContainer).setPadding(0, LinphoneUtils.pixelsToDpi(getResources(), 40), 0, 0);
    }

    private void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras) {
        changeCurrentFragment(newFragmentType, extras, false);
    }

    @SuppressWarnings("incomplete-switch")
    private void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras, boolean withoutAnimation) {
        if (newFragmentType == currentFragment && newFragmentType != FragmentsAvailable.CHAT) {
            return;
        }
        nextFragment = newFragmentType;

        if (currentFragment == FragmentsAvailable.DIALER) {
            try {
                dialerSavedState = getSupportFragmentManager().saveFragmentInstanceState(dialerFragment);
            } catch (Exception e) {
            }
        }

        Fragment newFragment = null;

        switch (newFragmentType) {
            case DIALER:
                newFragment = new DialerFragment();
                if (extras == null) {
                    newFragment.setInitialSavedState(dialerSavedState);
                }
                dialerFragment = newFragment;
                break;
        }

        if (newFragment != null) {
            newFragment.setArguments(extras);
            if (isTablet()) {
                changeFragmentForTablets(newFragment, newFragmentType, withoutAnimation);
            } else {
                changeFragment(newFragment, newFragmentType, withoutAnimation);
            }
        }
    }

    private void updateAnimationsState() {
        isAnimationDisabled = getResources().getBoolean(R.bool.disable_animations) || !LinphonePreferences.instance().areAnimationsEnabled();
        isContactPresenceDisabled = !getResources().getBoolean(R.bool.enable_linphone_friends);
    }

    public boolean isAnimationDisabled() {
        return isAnimationDisabled;
    }

    public boolean isContactPresenceDisabled() {
        return isContactPresenceDisabled;
    }

    private void changeFragment(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
        if (statusFragment != null) {
            statusFragment.closeStatusBar();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
            if (newFragmentType.isRightOf(currentFragment)) {
                transaction.setCustomAnimations(R.anim.slide_in_right_to_left,
                        R.anim.slide_out_right_to_left,
                        R.anim.slide_in_left_to_right,
                        R.anim.slide_out_left_to_right);
            } else {
                transaction.setCustomAnimations(R.anim.slide_in_left_to_right,
                        R.anim.slide_out_left_to_right,
                        R.anim.slide_in_right_to_left,
                        R.anim.slide_out_right_to_left);
            }
        }
        try {
            getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (java.lang.IllegalStateException e) {
        }
        transaction.addToBackStack(newFragmentType.toString());
        transaction.replace(R.id.fragmentContainer, newFragment, newFragmentType.toString());
        transaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
        currentFragment = newFragmentType;
    }

    private void changeFragmentForTablets(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
//		if (getResources().getBoolean(R.bool.show_statusbar_only_on_dialer)) {
//			if (newFragmentType == FragmentsAvailable.DIALER) {
//				showStatusBar();
//			} else {
//				hideStatusBar();
//			}
//		}
        if (statusFragment != null) {
            statusFragment.closeStatusBar();
        }


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (newFragmentType.shouldAddItselfToTheRightOf(currentFragment)) {
        } else {
            if (newFragmentType == FragmentsAvailable.DIALER
                    || newFragmentType == FragmentsAvailable.ABOUT
                    || newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT
                    || newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_SETTINGS
                    || newFragmentType == FragmentsAvailable.SETTINGS
                    || newFragmentType == FragmentsAvailable.ACCOUNT_SETTINGS) {
            } else {
            }

            if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
                if (newFragmentType.isRightOf(currentFragment)) {
                    transaction.setCustomAnimations(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left, R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right);
                } else {
                    transaction.setCustomAnimations(R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right, R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left);
                }
            }

            try {
                getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (java.lang.IllegalStateException e) {

            }

            transaction.addToBackStack(newFragmentType.toString());
            transaction.replace(R.id.fragmentContainer, newFragment);
        }
        transaction.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();

        currentFragment = newFragmentType;
        if (currentFragment == FragmentsAvailable.DIALER) {
        }
    }

    public void displayHistoryDetail(String sipUri, LinphoneCallLog log) {
        LinphoneAddress lAddress;
        try {
            lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
        } catch (LinphoneCoreException e) {
            Log.e("Cannot display history details", e);
            return;
        }
        Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());

        String displayName = lAddress.getDisplayName();
        String pictureUri = uri == null ? null : uri.toString();

        String status;
        if (log.getDirection() == CallDirection.Outgoing) {
            status = "Outgoing";
        } else {
            if (log.getStatus() == CallStatus.Missed) {
                status = "Missed";
            } else {
                status = "Incoming";
            }
        }

        String callTime = secondsToDisplayableString(log.getCallDuration());
        String callDate = String.valueOf(log.getTimestamp());
    }

    @SuppressLint("SimpleDateFormat")
    private String secondsToDisplayableString(int secs) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(0, 0, 0, 0, 0, secs);
        return dateFormat.format(cal.getTime());
    }

    public void displayContacts(boolean chatOnly) {
        if (chatOnly) {
            preferLinphoneContacts = true;
        }

        Bundle extras = new Bundle();
        extras.putBoolean("ChatAddressOnly", chatOnly);
        changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
        preferLinphoneContacts = false;
    }

    public void displayContactsForEdition(String sipAddress) {
        Bundle extras = new Bundle();
        extras.putBoolean("EditOnClick", true);
        extras.putString("SipAddress", sipAddress);
        changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
    }

    public void displayAbout() {
        changeCurrentFragment(FragmentsAvailable.ABOUT, null);
    }

    public void displayChat(String sipUri) {
    }

    @SuppressWarnings("incomplete-switch")
    public void selectMenu(FragmentsAvailable menuToSelect) {
        currentFragment = menuToSelect;
    }

    public void updateDialerFragment(DialerFragment fragment) {
        dialerFragment = fragment;
        // Hack to maintain soft input flags
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void updateStatusFragment(StatusFragment fragment) {
        statusFragment = fragment;

        LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
        if (lc != null && lc.getDefaultProxyConfig() != null) {
            statusFragment.registrationStateChanged(LinphoneManager.getLc().getDefaultProxyConfig().getState());
        }
    }

    public void displaySettings() {
        changeCurrentFragment(FragmentsAvailable.SETTINGS, null);
    }

    public void applyConfigChangesIfNeeded() {
        if (nextFragment != FragmentsAvailable.SETTINGS && nextFragment != FragmentsAvailable.ACCOUNT_SETTINGS) {
            updateAnimationsState();
        }
    }

    public void displayAccountSettings(int accountNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt("Account", accountNumber);
        changeCurrentFragment(FragmentsAvailable.ACCOUNT_SETTINGS, bundle);
    }

    public StatusFragment getStatusFragment() {
        return statusFragment;
    }


    @Override
    public void onMessageReceived(LinphoneAddress from, LinphoneChatMessage message, int id) {

    }


    public void onRegistrationStateChanged(LinphoneProxyConfig proxy, RegistrationState state, String message) {
        LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
        if (statusFragment != null) {
            if (lc != null)
                if (lc.getDefaultProxyConfig() == null)
                    statusFragment.registrationStateChanged(proxy.getState());
                else
                    statusFragment.registrationStateChanged(lc.getDefaultProxyConfig().getState());
            else
                statusFragment.registrationStateChanged(RegistrationState.RegistrationNone);
        }

        if (state.equals(RegistrationState.RegistrationCleared)) {
            if (lc != null) {
                LinphoneAuthInfo authInfo = lc.findAuthInfo(proxy.getIdentity(), proxy.getRealm(), proxy.getDomain());
                if (authInfo != null)
                    lc.removeAuthInfo(authInfo);
            }
        }
    }

    private void displayMissedCalls(final int missedCallsCount) {
    }

    private void displayMissedChats(final int missedChatCount) {
    }

    @Override
    public void onCallStateChanged(LinphoneCall call, State state, String message) {
        if (state == State.IncomingReceived) {
            Intent intent = new Intent(this, IncomingCallActivity.class);
            startActivity(intent);
        } else if (state == State.OutgoingInit) {
            if (call.getCurrentParamsCopy().getVideoEnabled()) {
                startVideoActivity(call);
            } else {
                startIncallActivity(call);
            }
        } else if (state == State.CallEnd || state == State.Error || state == State.CallReleased) {
            // Convert LinphoneCore message for internalization
            if (message != null && message.equals("Call declined.")) {
                displayCustomToast(getString(R.string.error_call_declined), Toast.LENGTH_LONG);
            } else if (message != null && message.equals("Not Found")) {
                displayCustomToast(getString(R.string.error_user_not_found), Toast.LENGTH_LONG);
            } else if (message != null && message.equals("Unsupported media type")) {
                displayCustomToast(getString(R.string.error_incompatible_media), Toast.LENGTH_LONG);
            }
            resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
        }

        int missedCalls = LinphoneManager.getLc().getMissedCallsCount();
        displayMissedCalls(missedCalls);
    }

    public void displayCustomToast(final String message, final int duration) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastRoot));

                TextView toastText = (TextView) layout.findViewById(R.id.toastMessage);
                toastText.setText(message);

                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    @Override
    public void setAddresGoToDialerAndCall(String number, String name, Uri photo) {
//		Bundle extras = new Bundle();
//		extras.putString("SipUri", number);
//		extras.putString("DisplayName", name);
//		extras.putString("Photo", photo == null ? null : photo.toString());
//		changeCurrentFragment(FragmentsAvailable.DIALER, extras);

        AddressType address = new AddressText(this, null);
        address.setDisplayedName(name);
        address.setText(number);
        LinphoneManager.getInstance().newOutgoingCall(address);
    }

    public void setAddressAndGoToDialer(String number) {
        Bundle extras = new Bundle();
        extras.putString("SipUri", number);
        changeCurrentFragment(FragmentsAvailable.DIALER, extras);
    }

    @Override
    public void goToDialer() {
        changeCurrentFragment(FragmentsAvailable.DIALER, null);
    }

    public void startVideoActivity(LinphoneCall currentCall) {
//		android.util.Log.w("bug", "从方法1跳入");
//		android.util.Log.w("bug", "Call = " + currentCall.getState().value());
        Config.videoAdHidden = true;
        Intent intent = new Intent(this, InCallActivity.class);
        intent.putExtra("VideoEnabled", false);
        startOrientationSensor();
        startActivityForResult(intent, CALL_ACTIVITY);
    }
    public void startIncallActivity(LinphoneCall currentCall) {
//		android.util.Log.w("bug", "从方法2跳入");
//		android.util.Log.w("bug", "Call = " + currentCall.getState().value());
        Intent intent = new Intent(this, InCallActivity.class);
        intent.putExtra("VideoEnabled", false);
        startOrientationSensor();
        startActivityForResult(intent, CALL_ACTIVITY);
    }

    /**
     * Register a sensor to track phoneOrientation changes
     */
    private synchronized void startOrientationSensor() {
        if (mOrientationHelper == null) {
            mOrientationHelper = new LocalOrientationEventListener(this);
        }
        mOrientationHelper.enable();
    }

    private int mAlwaysChangingPhoneAngle = -1;
    private AcceptNewFriendDialog acceptNewFriendDialog;

    private class LocalOrientationEventListener extends OrientationEventListener {
        public LocalOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(final int o) {
            if (o == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return;
            }

            int degrees = 270;
            if (o < 45 || o > 315)
                degrees = 0;
            else if (o < 135)
                degrees = 90;
            else if (o < 225)
                degrees = 180;

            if (mAlwaysChangingPhoneAngle == degrees) {
                return;
            }
            mAlwaysChangingPhoneAngle = degrees;

            Log.d("Phone orientation changed to ", degrees);
            int rotation = (360 - degrees) % 360;
            LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
            if (lc != null) {
                lc.setDeviceRotation(rotation);
                LinphoneCall currentCall = lc.getCurrentCall();
                if (currentCall != null && currentCall.cameraEnabled() && currentCall.getCurrentParamsCopy().getVideoEnabled()) {
                    lc.updateCall(currentCall, null);
                }
            }
        }
    }

    public void showPreferenceErrorDialog(String message) {

    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public List<Contact> getSIPContacts() {
        return sipContactList;
    }

    public Cursor getAllContactsCursor() {
        return contactCursor;
    }

    public Cursor getSIPContactsCursor() {
        return sipContactCursor;
    }

    public void setLinphoneContactsPrefered(boolean isPrefered) {
        preferLinphoneContacts = isPrefered;
    }

    public boolean isLinphoneContactsPrefered() {
        return preferLinphoneContacts;
    }

    public void onNewSubscriptionRequestReceived(LinphoneFriend friend,
                                                 String sipUri) {
        if (isContactPresenceDisabled) {
            return;
        }

        sipUri = sipUri.replace("<", "").replace(">", "");
        if (LinphonePreferences.instance().shouldAutomaticallyAcceptFriendsRequests()) {
            Contact contact = findContactWithSipAddress(sipUri);
            if (contact != null) {
                friend.enableSubscribes(true);
                try {
                    LinphoneManager.getLc().addFriend(friend);
                    contact.setFriend(friend);
                } catch (LinphoneCoreException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Contact contact = findContactWithSipAddress(sipUri);
            if (contact != null) {
                FragmentManager fm = getSupportFragmentManager();
                acceptNewFriendDialog = new AcceptNewFriendDialog(contact, sipUri);
                acceptNewFriendDialog.show(fm, "New Friend Request Dialog");
            }
        }
    }

    private Contact findContactWithSipAddress(String sipUri) {
        if (!sipUri.startsWith("sip:")) {
            sipUri = "sip:" + sipUri;
        }

        for (Contact contact : sipContactList) {
            for (String addr : contact.getNumerosOrAddresses()) {
                if (addr.equals(sipUri)) {
                    return contact;
                }
            }
        }
        return null;
    }

    public void onNotifyPresenceReceived(LinphoneFriend friend) {
    }

    public boolean newFriend(Contact contact, String sipUri) {
        LinphoneFriend friend = LinphoneCoreFactory.instance().createLinphoneFriend(sipUri);
        friend.enableSubscribes(true);
        friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
        try {
            LinphoneManager.getLc().addFriend(friend);
            contact.setFriend(friend);
            return true;
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void acceptNewFriend(Contact contact, String sipUri, boolean accepted) {
        acceptNewFriendDialog.dismissAllowingStateLoss();
        if (accepted) {
            newFriend(contact, sipUri);
        }
    }

    public boolean removeFriend(Contact contact, String sipUri) {
        LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
        if (friend != null) {
            friend.enableSubscribes(false);
            LinphoneManager.getLc().removeFriend(friend);
            contact.setFriend(null);
            return true;
        }
        return false;
    }

    private void searchFriendAndAddToContact(Contact contact) {
        if (contact == null || contact.getNumerosOrAddresses() == null) {
            return;
        }

        for (String sipUri : contact.getNumerosOrAddresses()) {
            if (LinphoneUtils.isSipAddress(sipUri)) {
                LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
                if (friend != null) {
                    friend.enableSubscribes(true);
                    friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
                    contact.setFriend(friend);
                    break;
                }
            }
        }
    }

    public void removeContactFromLists(Contact contact) {
        for (Contact c : contactList) {
            if (c != null && c.getID().equals(contact.getID())) {
                contactList.remove(c);
                contactCursor = Compatibility.getContactsCursor(getContentResolver());
                break;
            }
        }

        for (Contact c : sipContactList) {
            if (c != null && c.getID().equals(contact.getID())) {
                sipContactList.remove(c);
                sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());
                break;
            }
        }
    }

    public synchronized void prepareContactsInBackground() {
        if (contactCursor != null) {
            contactCursor.close();
        }
        if (sipContactCursor != null) {
            sipContactCursor.close();
        }

        contactCursor = Compatibility.getContactsCursor(getContentResolver());
        sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());

        Thread sipContactsHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                if (sipContactCursor != null) {
                    for (int i = 0; i < sipContactCursor.getCount(); i++) {
                        Contact contact = Compatibility.getContact(getContentResolver(), sipContactCursor, i);
                        if (contact == null)
                            continue;

                        contact.refresh(getContentResolver());
                        if (!isContactPresenceDisabled) {
                            searchFriendAndAddToContact(contact);
                        }
                        sipContactList.add(contact);
                    }
                }
                if (contactCursor != null) {
                    for (int i = 0; i < contactCursor.getCount(); i++) {
                        Contact contact = Compatibility.getContact(getContentResolver(), contactCursor, i);
                        if (contact == null)
                            continue;

                        for (Contact c : sipContactList) {
                            if (c != null && c.getID().equals(contact.getID())) {
                                contact = c;
                                break;
                            }
                        }
                        contactList.add(contact);
                    }
                }
            }
        });

        contactList = new ArrayList<Contact>();
        sipContactList = new ArrayList<Contact>();

        sipContactsHandler.start();
    }

    private void initInCallMenuLayout(boolean callTransfer) {
        selectMenu(FragmentsAvailable.DIALER);
        if (dialerFragment != null) {
            ((DialerFragment) dialerFragment).resetLayout(callTransfer);
        }
    }

    public void resetClassicMenuLayoutAndGoBackToCallIfStillRunning() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (dialerFragment != null) {
                    ((DialerFragment) dialerFragment).resetLayout(false);
                }

                if (LinphoneManager.isInstanciated() && LinphoneManager.getLc().getCallsNb() > 0) {
                    LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
                    if (call.getState() == LinphoneCall.State.IncomingReceived) {
                        Intent intent = new Intent(LinphoneActivity.this, IncomingCallActivity.class);
                        intent.putExtra("intent", this.getClass().toString() + "\n 1037");
                        startActivity(intent);
                    } else if (call.getCurrentParamsCopy().getVideoEnabled()) {
                        startVideoActivity(call);
                    } else {
                        startIncallActivity(call);
                    }
                }
            }
        });
    }

    public FragmentsAvailable getCurrentFragment() {
        return currentFragment;
    }


    public void addContact(String displayName, String sipUri) {
        if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
            Intent intent = Compatibility.prepareAddContactIntent(displayName, sipUri);
            startActivity(intent);
        } else {
            Bundle extras = new Bundle();
            extras.putSerializable("NewSipAdress", sipUri);
            changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
        }
    }

    public void editContact(Contact contact) {
        if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
            Intent intent = Compatibility.prepareEditContactIntent(Integer.parseInt(contact.getID()));
            startActivity(intent);
        } else {
            Bundle extras = new Bundle();
            extras.putSerializable("Contact", contact);
            changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
        }
    }

    public void editContact(Contact contact, String sipAddress) {
        if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
            Intent intent = Compatibility.prepareEditContactIntentWithSipAddress(Integer.parseInt(contact.getID()), sipAddress);
            startActivity(intent);
        } else {
            Bundle extras = new Bundle();
            extras.putSerializable("Contact", contact);
            extras.putSerializable("NewSipAdress", sipAddress);
            changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
        }
    }

    public void exit() {
        finish();
        stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_FIRST_USER && requestCode == SETTINGS_ACTIVITY) {
            if (data.getExtras().getBoolean("Exit", false)) {
                exit();
            } else {
                FragmentsAvailable newFragment = (FragmentsAvailable) data.getExtras().getSerializable("FragmentToDisplay");
                changeCurrentFragment(newFragment, null, true);
                selectMenu(newFragment);
            }
        } else if (resultCode == Activity.RESULT_FIRST_USER && requestCode == CALL_ACTIVITY) {
            getIntent().putExtra("PreviousActivity", CALL_ACTIVITY);
            boolean callTransfer = data == null ? false : data.getBooleanExtra("Transfer", false);
            if (LinphoneManager.getLc().getCallsNb() > 0) {
                initInCallMenuLayout(callTransfer);
            } else {
                resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onPause() {
        getIntent().putExtra("PreviousActivity", 0);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


        if (loginFlag.equals("true")) {
            if (DialerFragment.instance() != null) {
                if (!"".equals(getInputNumber())) {
                    //添加通话记录完成
                    List<Contacts> allcontact = new ContactBase(getApplicationContext()).getAllcontact();
                    YETApplication.getinstant().setCltList(allcontact);
                    Intent intent = new Intent(Smack.action);
                    intent.putExtra("addContactSuccess", "addContactSuccess");
                    sendBroadcast(intent);
                }
            }

            if (!LinphoneService.isReady()) {
                startService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
            }

            // Remove to avoid duplication of the listeners
            LinphoneManager.removeListener(this);
            LinphoneManager.addListener(this);

            if (!LinphoneManager.isInstanciated()) {
                LinphoneManager.createAndStart(getApplicationContext(), new LinphoneService());
            }

            LinphoneManager.getInstance().changeStatusToOnline();

            if (getIntent().getIntExtra("PreviousActivity", 0) != CALL_ACTIVITY) {
                if (LinphoneManager.getLc().getCalls().length > 0) {
                    LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
                    LinphoneCall.State callState = call.getState();
                    if (callState == State.IncomingReceived) {
                        Intent intent = new Intent(this, IncomingCallActivity.class);
                        intent.putExtra("intent", this.getClass().toString() + "\n 1158");
                        startActivity(intent);
                    } else {

                        if (call.getCurrentParamsCopy().getVideoEnabled()) {
                            startVideoActivity(call);
                        } else {
                            startIncallActivity(call);
                        }
                    }
                }
            }
        }
    }

    public void changeCall() {

        if (DialerFragment.instance() != null) {
            if (!"".equals(getInputNumber())) {
                // 添加通话记录完成
                List<Contacts> allcontact = new ContactBase(
                        getApplicationContext()).getAllcontact();
                YETApplication.getinstant().setCltList(allcontact);
                Intent intent = new Intent(Smack.action);
                intent.putExtra("addContactSuccess", "addContactSuccess");
                sendBroadcast(intent);
            }
        }

        if (!LinphoneService.isReady()) {
            startService(new Intent(ACTION_MAIN).setClass(this,
                    LinphoneService.class));
        }

        // Remove to avoid duplication of the listeners
        LinphoneManager.removeListener(this);
        LinphoneManager.addListener(this);

        // displayMissedCalls(LinphoneManager.getLc().getMissedCallsCount());

        if (!LinphoneManager.isInstanciated()) {
            LinphoneManager.createAndStart(getApplicationContext(),
                    new LinphoneService());
        }
        if (LinphoneManager.getLc().getCalls().length > 0) {

            LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
            LinphoneCall.State callState = call.getState();
            // T.show(getApplicationContext(),
            // "onResumeonResumeonResume:"+callState, 0);
            // T.show(getApplicationContext(), callState.toString(), 0);
            if (callState == State.IncomingReceived) {
                Intent intent = new Intent(Smack.action);
                intent.putExtra("callingIn", "callingIn");
                getApplicationContext().sendBroadcast(intent);

                Intent intent2 = new Intent(this, IncomingCallActivity.class);
                intent2.putExtra("intent", this.getClass().toString() + "\n 1214");
                startActivity(intent2);
            } else if (callState == State.StreamsRunning) {
                // 如果在通话中 通过返回 跳到主界面后 在进入 那么直接跳转到通话界面
                // String params =
                // SettingInfo.getParams(PreferenceBean.CALLTIME, "0");
                // System.out.println("水煎。。。"+params);
                // if(!"".equals(params)){
                // SettingInfo.setParams(PreferenceBean.CALLTIME,
                // ""+(Integer.parseInt(params)));
                // }
                startActivity(new Intent(this, InCallActivity.class));
                // resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            } else if (callState == State.OutgoingProgress
                    || callState == State.OutgoingEarlyMedia
                    || callState == State.OutgoingRinging) {
                // 如果在拨号中 通过返回 跳到主界面后 在进入 那么直接跳转到通话界面
                String params = SettingInfo.getParams(
                        PreferenceBean.BACKTOAPPNUME, "");
                if (!"".equals(params)) {
                    startActivity(new Intent(this, InCallActivity.class));
                    SettingInfo.setParams(PreferenceBean.BACKTOAPPNUME, "");
                }

                // resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            } else if (callState == State.IncomingReceived) {
                // 如果在拨号中 通过返回 跳到主界面后 在进入 那么直接跳转到通话界面
                String params = SettingInfo.getParams(
                        PreferenceBean.BACKTOAPPNUME, "");
                if (!"".equals(params)) {
                    Intent intent = new Intent(this, IncomingCallActivity.class);
                    intent.putExtra("intent", this.getClass().toString() + "\n 1245");
                    startActivity(intent);
                    SettingInfo.setParams(PreferenceBean.BACKTOAPPNUME, "");
                }

                // resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            }
        }

        //refreshStatus(OnlineStatus.Online);

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        LinphoneManager.removeListener(this);

        if (mOrientationHelper != null) {
            mOrientationHelper.disable();
            mOrientationHelper = null;
        }
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        instance = null;
        super.onDestroy();

        unbindDrawables(findViewById(R.id.topLayout));
        System.gc();
        Log.e("", "====================LinphoneActivity finish=====================");
    }

    private void unbindDrawables(View view) {
        if (view != null && view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();
        if (extras != null && extras.getBoolean("GoToChat", false)) {
            LinphoneService.instance().removeMessageNotification();
            String sipUri = extras.getString("ChatContactSipUri");
            displayChat(sipUri);
        } else if (extras != null && extras.getBoolean("Notification", false)) {
            if (LinphoneManager.getLc().getCallsNb() > 0) {
                LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
                if (call.getCurrentParamsCopy().getVideoEnabled()) {
                    startVideoActivity(call);
                } else {
                    startIncallActivity(call);
                }
            }
        } else {
            if (dialerFragment != null) {
                if (extras != null && extras.containsKey("SipUriOrNumber")) {
                    if (getResources().getBoolean(R.bool.automatically_start_intercepted_outgoing_gsm_call)) {
                        ((DialerFragment) dialerFragment).newOutgoingCall(extras.getString("SipUriOrNumber"));
                    } else {
                        ((DialerFragment) dialerFragment).displayTextInAddressBar(extras.getString("SipUriOrNumber"));
                    }
                } else {
                    ((DialerFragment) dialerFragment).newOutgoingCall(intent);
                }
            }
            if (LinphoneManager.getLc().getCalls().length > 0) {
                LinphoneCall calls[] = LinphoneManager.getLc().getCalls();
                if (calls.length > 0) {
                    LinphoneCall call = calls[0];

                    if (call != null && call.getState() != LinphoneCall.State.IncomingReceived) {
                        if (call.getCurrentParamsCopy().getVideoEnabled()) {
                            startVideoActivity(call);
                        } else {
                            startIncallActivity(call);
                        }
                    }
                }

                // If a call is ringing, start incomingcallactivity
                Collection<LinphoneCall.State> incoming = new ArrayList<LinphoneCall.State>();
                incoming.add(LinphoneCall.State.IncomingReceived);
                if (LinphoneUtils.getCallsInState(LinphoneManager.getLc(), incoming).size() > 0) {
                    if (InCallActivity.isInstanciated()) {
                        InCallActivity.instance().startIncomingCallActivity();
                    } else {
                        Intent intent2 = new Intent(this, IncomingCallActivity.class);
                        intent2.putExtra("intent", this.getClass().toString() + "\n 1344");
                        startActivity(intent2);
                    }
                }
            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("rsgiseOk".equals(intent.getStringExtra("regist"))) {
                if (progressDlg != null) progressDlg.dismiss();
                if (timer != null) timer.cancel();
                LinphoneService.instance().setActivityToLaunchOnIncomingReceived(OrderActivity.class);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @SuppressLint("ValidFragment")
    class AcceptNewFriendDialog extends DialogFragment {
        private Contact contact;
        private String sipUri;

        public AcceptNewFriendDialog(Contact c, String a) {
            contact = c;
            sipUri = a;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.new_friend_request_dialog, container);

            getDialog().setTitle(R.string.linphone_friend_new_request_title);

            Button yes = (Button) view.findViewById(R.id.yes);
            yes.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptNewFriend(contact, sipUri, true);
                }
            });

            Button no = (Button) view.findViewById(R.id.no);
            no.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptNewFriend(contact, sipUri, false);
                }
            });

            return view;
        }
    }

    public void callOut() {
        if (currentFragment == FragmentsAvailable.DIALER) {
            ((DialerFragment) dialerFragment).callOut();
        }
    }

    public boolean setDialerNumberPanVisiable() {
        return DialerFragment.instance().setDialerNumberPanVisiable();
    }

    public void setDialerNumberPanVisiable(Boolean show) {
        DialerFragment.instance().setDialerNumberPanVisiable(show);
    }

    public boolean getHideStatus() {
        return DialerFragment.instance().getHideStatus();
    }

    public String getInputNumber() {
        return DialerFragment.instance().getInputNumber();
    }

    public void clearAddress() {
        DialerFragment.instance().clearAddress();
    }
}

interface ContactPicked {
    void setAddresGoToDialerAndCall(String number, String name, Uri photo);

    void goToDialer();
}
