package org.linphone;

/*
 InCallActivity.java
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.until.Config;
import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.DialerViewAdapter;
import com.cqxb.yecall.bean.AdvertisementBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import org.linphone.LinphoneSimpleListener.LinphoneOnCallEncryptionChangedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallParams;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.mediastream.Log;
import org.linphone.mediastream.video.capture.hwconf.AndroidCameraConfiguration;
import org.linphone.ui.AvatarWithShadow;
import org.linphone.ui.Numpad;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Sylvain Berfini
 */
public class InCallActivity extends FragmentActivity implements
        LinphoneOnCallStateChangedListener,
        LinphoneOnCallEncryptionChangedListener, OnClickListener {
    private final static int SECONDS_BEFORE_HIDING_CONTROLS     = 3000;
    private final static int SECONDS_BEFORE_DENYING_CALL_UPDATE = 30000;

    private static InCallActivity instance;

    private LinearLayout speaker, micro, tv_incall_dial, screemshots, video;
    private ImageView iconSpeaker, iconMicro, iconVideo;
    private TextView tvSpeaker, tvMicro, tvVideo;


    private Handler mHandler         = new Handler();
    private Handler mControlsHandler = new Handler();
    private Runnable  mControls;
    private ImageView switchCamera;
    private TextView  pause, hangUp, dialer, options, addCall, transfer,
            conference;
    private TextView audioRoute, routeSpeaker, routeReceiver, routeBluetooth,
            hideDialder2;
    private LinearLayout routeLayout, ll_incall_btContain,
            ll_incall_btdoor_contain;
    private ProgressBar       videoProgress;
    private StatusFragment    status;
    private AudioCallFragment audioCallFragment;
    private VideoCallFragment videoCallFragment;
    private boolean           isSpeakerEnabled = false, isMicMuted = false,
            isTransferAllowed, isAnimationDisabled;
    private ViewGroup      mControlsLayout;
    private RelativeLayout mControlsLayout1;
    private Numpad         numpad;
    private int            cameraNumber;
    private Animation      slideOutLeftToRight, slideInRightToLeft,
            slideInBottomToTop, slideInTopToBottom, slideOutBottomToTop,
            slideOutTopToBottom;
    private CountDownTimer         timer;
    private AcceptCallUpdateDialog callUpdateDialog;
    private boolean isVideoCallPaused = false;

    private TableLayout    callsList;
    private LayoutInflater inflater;
    private ViewGroup      container;
    private boolean isConferenceRunning = false;
    private boolean showCallListInVideo = false;

    private TextView callText, jy, tjth, lxr, ysq, tv_incall_talkName,
            tv_incall_talkNumber;
    private RelativeLayout hangUpCall, oprete, hideDialder, dialder;

    private FrameLayout advImage;

    private ViewPager viewpager;

    private DialerViewAdapter viewAdapter;

    private LinearLayout viewGroup;

    private ImageView dot, dots[];

    private Runnable runnable;

    private int autoChangeTime = 3000;

    private boolean isRecord = true;

    private boolean isAdvert = true;

    private boolean isHidden = false;

    public static InCallActivity instance() {
        return instance;
    }

    public static boolean isInstanciated() {
        return instance != null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        // 获取传过来的号码信息
        SettingInfo.init(getApplicationContext());
        String CALLNAME = SettingInfo.getParams(PreferenceBean.CALLNAME, "")
                .trim();
        if (CALLNAME.equals(""))
            CALLNAME = "陌生号码";
        String CALLPHONE = SettingInfo.getParams(PreferenceBean.CALLPHONE, "");
        CALLPHONE = CALLPHONE.replaceAll("sip:", "");
        if (CALLPHONE.indexOf("@") > 0) {
            CALLPHONE = CALLPHONE.substring(0, CALLPHONE.indexOf("@"));
        }
        String CALLPOSITION = SettingInfo.getParams(
                PreferenceBean.CALLPOSITION, "");
        Log.e("", "::::::::::::::::::::incall  " + CALLNAME + "  " + CALLPHONE
                + "   " + CALLPOSITION);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.incall);

        iconSpeaker = (ImageView) findViewById(R.id.iconSpeaker);
        iconMicro = (ImageView) findViewById(R.id.iconMicro);
        iconVideo = (ImageView) findViewById(R.id.iconVideo);

        tvSpeaker = (TextView) findViewById(R.id.tvSpeaker);
        tvMicro = (TextView) findViewById(R.id.tvMicro);
        tvVideo = (TextView) findViewById(R.id.tvVideo);

        tv_incall_talkName = ((TextView) findViewById(R.id.tv_incall_talkName));
        tv_incall_talkName.setText(CALLNAME);
        tv_incall_talkNumber = ((TextView) findViewById(R.id.tv_incall_talkNumber));
        tv_incall_talkNumber.setText(CALLPHONE);

        isRecord = false;
        isAdvert = false;
        /*
         * TextView calledNumber=((TextView)findViewById(R.id.calledNumber));
		 * calledNumber.setText(CALLPHONE); TextView
		 * callName=((TextView)findViewById(R.id.callName));
		 * callName.setText(CALLNAME); TextView
		 * callPosition=((TextView)findViewById(R.id.callPosition));
		 * callPosition.setText(CALLPOSITION);
		 * 
		 * dialder=(RelativeLayout)findViewById(R.id.dialder);
		 * hideDialder=(RelativeLayout)findViewById(R.id.hideDialder);
		 * hideDialder.setOnClickListener(this);
		 * oprete=(RelativeLayout)findViewById(R.id.oprete);
		 */

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setMode(AudioManager.STREAM_SYSTEM);
        if (audioManager.isSpeakerphoneOn()) {
            audioManager.setSpeakerphoneOn(false);
        }

        isTransferAllowed = getApplicationContext().getResources().getBoolean(
                R.bool.allow_transfers);
        showCallListInVideo = getApplicationContext().getResources()
                .getBoolean(R.bool.show_current_calls_above_video);
        isSpeakerEnabled = LinphoneManager.getLcIfManagerNotDestroyedOrNull()
                .isSpeakerEnabled();

        isAnimationDisabled = getApplicationContext().getResources()
                .getBoolean(R.bool.disable_animations)
                || !LinphonePreferences.instance().areAnimationsEnabled();
        cameraNumber = AndroidCameraConfiguration.retrieveCameras().length;

        if (findViewById(R.id.fragmentContainer) != null) {
            initUI();

            if (LinphoneManager.getLc().getCallsNb() > 0) {
                LinphoneCall call = LinphoneManager.getLc().getCalls()[0];

                if (LinphoneUtils.isCallEstablished(call)) {
                    enableAndRefreshInCallActions();
                }
            }

            if (savedInstanceState != null) {
                // Fragment already created, no need to create it again (else it
                // will generate a memory leak with duplicated fragments)
                isSpeakerEnabled = savedInstanceState.getBoolean("Speaker");
                isMicMuted = savedInstanceState.getBoolean("Mic");
                isVideoCallPaused = savedInstanceState
                        .getBoolean("VideoCallPaused");
                refreshInCallActions();
                return;
            }

            Fragment callFragment;
            if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
                callFragment = new VideoCallFragment();
                videoCallFragment = (VideoCallFragment) callFragment;
                isSpeakerEnabled = true;

                if (cameraNumber > 1) {
                    switchCamera.setVisibility(View.INVISIBLE);
                }
            } else {
                callFragment = new AudioCallFragment();
                audioCallFragment = (AudioCallFragment) callFragment;
                switchCamera.setVisibility(View.INVISIBLE);
            }
            callFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, callFragment)
                    .commitAllowingStateLoss();

        }
        // IntentFilter filter=new IntentFilter(Smack.action);
        // registerReceiver(receiver, filter);
        advImage = (FrameLayout) findViewById(R.id.imageViewAd);
        advertType = "2";
        getImage();

        android.util.Log.w("bug", "videoAdHidden = " + Config.videoAdHidden);
        if (Config.videoAdHidden) {
            advImage.setVisibility(View.GONE);
            Config.videoAdHidden = false;
        }

    }

    private boolean isVideoEnabled(LinphoneCall call) {
        if (call != null) {
            return call.getCurrentParamsCopy().getVideoEnabled();
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("Speaker", LinphoneManager.getLc()
                .isSpeakerEnabled());
        outState.putBoolean("Mic", LinphoneManager.getLc().isMicMuted());
        outState.putBoolean("VideoCallPaused", isVideoCallPaused);

        super.onSaveInstanceState(outState);
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    private void initUI() {
        inflater = LayoutInflater.from(this);
        container = (ViewGroup) findViewById(R.id.topLayout);
        callsList = (TableLayout) findViewById(R.id.calls);
        if (!showCallListInVideo) {
            callsList.setVisibility(View.GONE);

        }

		/*
         * callText = (TextView) findViewById(R.id.callText);
		 * hangUpCall=(RelativeLayout)findViewById(R.id.hangUpCall);
		 * hangUpCall.setOnClickListener(this);
		 * 
		 * jy=(TextView)findViewById(R.id.jy); jy.setOnClickListener(this);
		 * tjth=(TextView)findViewById(R.id.tjth);
		 * tjth.setOnClickListener(this); lxr=(TextView)findViewById(R.id.lxr);
		 * lxr.setOnClickListener(this); ysq=(TextView)findViewById(R.id.ysq);
		 * ysq.setOnClickListener(this); bhp=(TextView)findViewById(R.id.bhp);
		 * bhp.setOnClickListener(this);
		 */

        // 拨号键盘
        tv_incall_dial = (LinearLayout) findViewById(R.id.tv_incall_dial);
        tv_incall_dial.setOnClickListener(this);
        dialder = (RelativeLayout) findViewById(R.id.dialder);
        hideDialder = (RelativeLayout) findViewById(R.id.hideDialder);
        hideDialder2 = (TextView) findViewById(R.id.hideDialder2);
        hideDialder2.setOnClickListener(this);
        ll_incall_btContain = (LinearLayout) findViewById(R.id.ll_incall_btContain);
        ll_incall_btdoor_contain = (LinearLayout) findViewById(R.id.ll_incall_btdoor_contain);
        ll_incall_btdoor_contain.setOnClickListener(this);
        callText = (TextView) findViewById(R.id.callText);

        video = (LinearLayout) findViewById(R.id.video);
        video.setOnClickListener(this);
        video.setEnabled(false);
        micro = (LinearLayout) findViewById(R.id.micro);
        micro.setOnClickListener(this);
        // micro.setEnabled(false);
        speaker = (LinearLayout) findViewById(R.id.speaker);
        speaker.setOnClickListener(this);
        if (isTablet()) {
            speaker.setEnabled(false);
        }
        // speaker.setEnabled(false);
        addCall = (TextView) findViewById(R.id.addCall);
        addCall.setOnClickListener(this);
        addCall.setEnabled(false);
        transfer = (TextView) findViewById(R.id.transfer);
        transfer.setOnClickListener(this);
        transfer.setEnabled(false);
        options = (TextView) findViewById(R.id.options);
        options.setOnClickListener(this);
        options.setEnabled(false);
        pause = (TextView) findViewById(R.id.pause);
        pause.setOnClickListener(this);
        pause.setEnabled(false);
        hangUp = (TextView) findViewById(R.id.hangUp);
        hangUp.setOnClickListener(this);
        conference = (TextView) findViewById(R.id.conference);
        conference.setOnClickListener(this);
        dialer = (TextView) findViewById(R.id.dialer);
        dialer.setOnClickListener(this);
        dialer.setEnabled(false);
        screemshots = (LinearLayout) findViewById(R.id.tv_incall_screemshots);
        screemshots.setOnClickListener(this);
        screemshots.setEnabled(false);
        numpad = (Numpad) findViewById(R.id.numpad);
        videoProgress = (ProgressBar) findViewById(R.id.videoInProgress);
        videoProgress.setVisibility(View.GONE);

        try {
            routeLayout = (LinearLayout) findViewById(R.id.routesLayout);
            audioRoute = (TextView) findViewById(R.id.audioRoute);
            audioRoute.setOnClickListener(this);
            routeSpeaker = (TextView) findViewById(R.id.routeSpeaker);
            routeSpeaker.setOnClickListener(this);
            routeReceiver = (TextView) findViewById(R.id.routeReceiver);
            routeReceiver.setOnClickListener(this);
            routeBluetooth = (TextView) findViewById(R.id.routeBluetooth);
            routeBluetooth.setOnClickListener(this);
        } catch (NullPointerException npe) {
            Log.e("Bluetooth: Audio routes menu disabled on tablets for now (1)");
        }

        switchCamera = (ImageView) findViewById(R.id.switchCamera);
        switchCamera.setVisibility(View.GONE);
        // switchCamera.setOnClickListener(this);

        mControlsLayout = (ViewGroup) findViewById(R.id.menu);
        mControlsLayout1 = (RelativeLayout) findViewById(R.id.rl_incall_header);
        if (!isTransferAllowed) {
            addCall.setBackgroundResource(R.drawable.options_add_call);
        }

        if (!isAnimationDisabled) {
            slideInRightToLeft = AnimationUtils.loadAnimation(this,
                    R.anim.slide_in_right_to_left);
            slideOutLeftToRight = AnimationUtils.loadAnimation(this,
                    R.anim.slide_out_left_to_right);
            slideInBottomToTop = AnimationUtils.loadAnimation(this,
                    R.anim.slide_in_bottom_to_top);
            slideInTopToBottom = AnimationUtils.loadAnimation(this,
                    R.anim.slide_in_top_to_bottom);
            slideOutBottomToTop = AnimationUtils.loadAnimation(this,
                    R.anim.slide_out_bottom_to_top);
            slideOutTopToBottom = AnimationUtils.loadAnimation(this,
                    R.anim.slide_out_top_to_bottom);
        }

        if (BluetoothManager.getInstance().isBluetoothHeadsetAvailable()) {
            try {
                if (routeLayout != null)
                    routeLayout.setVisibility(View.VISIBLE);
                audioRoute.setVisibility(View.VISIBLE);
                speaker.setVisibility(View.GONE);
            } catch (NullPointerException npe) {
                Log.e("Bluetooth: Audio routes menu disabled on tablets for now (2)");
            }
        } else {
            try {
                if (routeLayout != null)
                    routeLayout.setVisibility(View.GONE);
                audioRoute.setVisibility(View.GONE);
                speaker.setVisibility(View.VISIBLE);
            } catch (NullPointerException npe) {
                Log.e("Bluetooth: Audio routes menu disabled on tablets for now (3)");
            }
        }

        LinphoneManager.getInstance().changeStatusToOnThePhone();
    }

    private void refreshInCallActions() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * 开始录音
                 */
                if (!isRecord) {
                    String recordFlag = SettingInfo.getParams(
                            PreferenceBean.OUTCALLCHECK, "");
                    if (recordFlag.equals("true")
                            && LinphoneManager.getLc().getCurrentCall() != null) {
                        LinphoneManager.getLc().getCurrentCall()
                                .startRecording();
                    } else {
                        SettingInfo.setParams(PreferenceBean.RECORDPATH, "");
                    }
                    isRecord = true;
                }

                if (!LinphonePreferences.instance().isVideoEnabled()) {
                    video.setEnabled(false);
                } else {
                    if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
                        iconVideo.setImageResource(R.drawable.video_off);

                        tvVideo.setTextColor(getResources().getColor(R.color.white));
                        video.setBackgroundColor(getResources().getColor(R.color.mBlue));

                    } else {
                        iconVideo.setImageResource(R.drawable.video_on);

                        tvVideo.setTextColor(getResources().getColor(R.color.mBlue));
                        video.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }

                try {
                    if (isSpeakerEnabled) {
                        iconSpeaker.setImageResource(R.drawable.speaker_off);

                        tvSpeaker.setTextColor(getResources().getColor(R.color.white));
                        speaker.setBackgroundColor(getResources().getColor(R.color.mBlue));

                        routeSpeaker.setBackgroundResource(R.drawable.route_speaker_on);
                        routeReceiver.setBackgroundResource(R.drawable.route_receiver_off);
                        routeBluetooth.setBackgroundResource(R.drawable.route_bluetooth_off);
                    } else {
                        iconSpeaker.setImageResource(R.drawable.speaker_on);

                        tvSpeaker.setTextColor(getResources().getColor(R.color.mBlue));
                        speaker.setBackgroundColor(getResources().getColor(R.color.white));

                        routeSpeaker.setBackgroundResource(R.drawable.route_speaker_off);
                        if (BluetoothManager.getInstance()
                                .isUsingBluetoothAudioRoute()) {
                            routeReceiver
                                    .setBackgroundResource(R.drawable.route_receiver_off);
                            routeBluetooth
                                    .setBackgroundResource(R.drawable.route_bluetooth_on);
                        } else {
                            routeReceiver
                                    .setBackgroundResource(R.drawable.route_receiver_on);
                            routeBluetooth
                                    .setBackgroundResource(R.drawable.route_bluetooth_off);
                        }
                    }
                } catch (NullPointerException npe) {
                    Log.e("Bluetooth: Audio routes menu disabled on tablets for now (4)");
                }

                if (isMicMuted) {
                    iconMicro.setImageResource(R.drawable.micro_off);

                    tvMicro.setTextColor(getResources().getColor(R.color.white));
                    micro.setBackgroundColor(getResources().getColor(R.color.mBlue));
                } else {
                    iconMicro.setImageResource(R.drawable.micro_on);

                    tvMicro.setTextColor(getResources().getColor(R.color.mBlue));
                    micro.setBackgroundColor(getResources().getColor(R.color.white));
                }

                if (LinphoneManager.getLc().getCallsNb() > 1) {
                    conference.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.GONE);
                } else {
                    conference.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);

                    List<LinphoneCall> pausedCalls = LinphoneUtils
                            .getCallsInState(LinphoneManager.getLc(),
                                    Arrays.asList(State.Paused));
                    if (pausedCalls.size() == 1) {
                        pause.setBackgroundResource(R.drawable.pause_on);
                    } else {
                        pause.setBackgroundResource(R.drawable.pause_off);
                    }
                }
            }
        });
    }

    private void enableAndRefreshInCallActions() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                addCall.setEnabled(LinphoneManager.getLc().getCallsNb() < LinphoneManager
                        .getLc().getMaxCalls());
                transfer.setEnabled(getResources().getBoolean(
                        R.bool.allow_transfers));
                options.setEnabled(!getResources().getBoolean(
                        R.bool.disable_options_in_call)
                        && (addCall.isEnabled() || transfer.isEnabled()));

                video.setEnabled(true);
                micro.setEnabled(true);
                if (!isTablet()) {
                    speaker.setEnabled(true);
                }
                transfer.setEnabled(true);
                pause.setEnabled(true);
                dialer.setEnabled(true);
                conference.setEnabled(true);
                screemshots.setEnabled(true);

                refreshInCallActions();
            }
        });
    }

    public void updateStatusFragment(StatusFragment statusFragment) {
        status = statusFragment;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
            displayVideoCallControlsIfHidden();
        }

        if (id == R.id.video) {// 开启视频
            enabledOrDisabledVideo(isVideoEnabled(LinphoneManager.getLc().getCurrentCall()));
        } else if (id == R.id.micro) {//开启声音
            toggleMicro();
        } else if (id == R.id.speaker) {//扬声器
            toggleSpeaker();
        } else if (id == R.id.addCall) {
            goBackToDialer();
        } else if (id == R.id.pause) {
            pauseOrResumeCall();
        } else if (id == R.id.hangUp) {
            hangUp();
        }
        // else if (id == R.id.hangUpCall) {
        // try {
        // hangUp();
        // } catch (Exception e) {
        // Log.e("","hangUpCall:"+e.getLocalizedMessage());
        // }
        // }
        else if (id == R.id.dialer) {
            hideOrDisplayNumpad();
        } else if (id == R.id.conference) {
            enterConference();
            // } else if (id == R.id.switchCamera) {
            // if (videoCallFragment != null) {
            // videoCallFragment.switchCamera();
            // }
        } else if (id == R.id.transfer) {
            goBackToDialerAndDisplayTransferButton();
        } else if (id == R.id.options) {
            hideOrDisplayCallOptions();
        } else if (id == R.id.audioRoute) {
            hideOrDisplayAudioRoutes();
        } else if (id == R.id.routeBluetooth) {
            if (BluetoothManager.getInstance().routeAudioToBluetooth()) {
                isSpeakerEnabled = false;
                routeBluetooth
                        .setBackgroundResource(R.drawable.route_bluetooth_on);
                routeReceiver
                        .setBackgroundResource(R.drawable.route_receiver_off);
                routeSpeaker
                        .setBackgroundResource(R.drawable.route_speaker_off);
                hideOrDisplayAudioRoutes();
            }
        } else if (id == R.id.routeReceiver) {
            LinphoneManager.getInstance().routeAudioToReceiver();
            isSpeakerEnabled = false;
            routeBluetooth
                    .setBackgroundResource(R.drawable.route_bluetooth_off);
            routeReceiver.setBackgroundResource(R.drawable.route_receiver_on);
            routeSpeaker.setBackgroundResource(R.drawable.route_speaker_off);
            hideOrDisplayAudioRoutes();
        } else if (id == R.id.routeSpeaker) {
            LinphoneManager.getInstance().routeAudioToSpeaker();
            isSpeakerEnabled = true;
            routeBluetooth
                    .setBackgroundResource(R.drawable.route_bluetooth_off);
            routeReceiver.setBackgroundResource(R.drawable.route_receiver_off);
            routeSpeaker.setBackgroundResource(R.drawable.route_speaker_on);
            hideOrDisplayAudioRoutes();
        } else if (id == R.id.callStatus) {
            LinphoneCall call = (LinphoneCall) v.getTag();
            pauseOrResumeCall(call);
        } else if (id == R.id.conferenceStatus) {
            pauseOrResumeConference();
        } else if (id == R.id.tv_incall_dial) {
            dialder.setVisibility(View.VISIBLE);
            ll_incall_btContain.setVisibility(View.GONE);
            ll_incall_btdoor_contain.setVisibility(View.GONE);
            hangUp.setVisibility(View.GONE);
            hideDialder.setVisibility(View.VISIBLE);
            advImage.setVisibility(View.GONE);
            if (viewpager != null)
                viewpager.setVisibility(View.GONE);
        } else if (id == R.id.hideDialder2) {
            dialder.setVisibility(View.GONE);
            hideDialder.setVisibility(View.GONE);
            ll_incall_btContain.setVisibility(View.VISIBLE);
            ll_incall_btdoor_contain.setVisibility(View.VISIBLE);
            hangUp.setVisibility(View.VISIBLE);
            if (viewpager != null)
                viewpager.setVisibility(View.VISIBLE);
            if (!isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
                advImage.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.tv_incall_screemshots || id == R.id.switchCamera) {// 截屏

            if (advImage.getVisibility() != View.VISIBLE) {

                if (LinphoneManager.getLc().getCurrentCall() != null) {
                    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

                    String photoPath = "/sdcard/tedi/software/files/"
                            + LinphoneManager.getLc().getAuthInfosList()[0]
                            .getUsername() + "_" + df.format(new Date())
                            + ".png";
                    File    file  = new File(photoPath);
                    File    dir   = new File(file.getParent());
                    boolean isDir = dir.exists();
                    if (!isDir) {
                        dir.mkdirs();
                    }
                    LinphoneManager.getLc().getCurrentCall().takeSnapshot(photoPath);
                    // String callTimestamp =
                    // String.valueOf(LinphoneManager.getLc().getCurrentCall().getCallLog().getTimestamp());
                    String actPhotoPath = SettingInfo.getParams(
                            PreferenceBean.PHOTOPATH, "").trim();
                    if (actPhotoPath.equals("")) {
                        SettingInfo.setParams(PreferenceBean.PHOTOPATH, photoPath);
                    } else {
                        SettingInfo.setParams(PreferenceBean.PHOTOPATH,
                                actPhotoPath + "," + photoPath);
                    }

                    Toast.makeText(this, "截图成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请在视频通话中截图!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.ll_incall_btdoor_contain) {// 开门
            android.util.Log.i("-------","点击开门");
            if (LinphoneManager.getLc().isIncall()) {
                String doorPwd = SettingInfo.getParams(
                        PreferenceBean.CALLPROYX, "").trim();
                if (!doorPwd.equals("")) {
                    char[] dtmfChar = doorPwd.toCharArray();
                    for (char dtmf : dtmfChar) {
                        LinphoneManager.getLc().sendDtmf(dtmf);
                    }
                    LinphoneManager.getLc().sendDtmf('#');
                } else {
                    LinphoneManager.getLc().sendDtmf('#');
                }
            }
        }
    }

    private void enabledOrDisabledVideo(final boolean isVideoEnabled) {
        final LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
        if (call == null) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isVideoEnabled) {
                    video.setEnabled(true);
                    LinphoneCallParams params = call.getCurrentParamsCopy();
                    params.setVideoEnabled(false);
                    LinphoneManager.getLc().updateCall(call, params);
                } else {
                    video.setEnabled(false);
                    videoProgress.setVisibility(View.VISIBLE);
                    if (!call.getRemoteParams().isLowBandwidthEnabled()) {
                        LinphoneManager.getInstance().addVideo();
                    } else {
                        displayCustomToast(
                                getString(R.string.error_low_bandwidth),
                                Toast.LENGTH_LONG);
                    }
                }
            }
        });

    }

    public void displayCustomToast(final String message, final int duration) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast,
                        (ViewGroup) findViewById(R.id.toastRoot));

                TextView toastText = (TextView) layout
                        .findViewById(R.id.toastMessage);
                toastText.setText(message);

                final Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    private void switchVideo(final boolean displayVideo) {
        final LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
        if (call == null) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // Check if the call is not terminated
                if (call.getState() == State.CallEnd
                        || call.getState() == State.CallReleased)
                    return;

                if (!displayVideo) {
                    showAudioView();
                } else {
                    if (!call.getRemoteParams().isLowBandwidthEnabled()) {
                        LinphoneManager.getInstance().addVideo();
                        if (videoCallFragment == null
                                || !videoCallFragment.isVisible())
                            showVideoView();
                    } else {
                        displayCustomToast(
                                getString(R.string.error_low_bandwidth),
                                Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }

    private void showAudioView() {
        iconVideo.setImageResource(R.drawable.video_on);

        tvVideo.setTextColor(getResources().getColor(R.color.mBlue));
        video.setBackgroundColor(getResources().getColor(R.color.white));

        advImage.setVisibility(View.VISIBLE);
        LinphoneManager.startProximitySensorForActivity(InCallActivity.this);
        replaceFragmentVideoByAudio();
        setCallControlsVisibleAndRemoveCallbacks();
    }

    private void showVideoView() {
        if (!BluetoothManager.getInstance().isBluetoothHeadsetAvailable()) {
            Log.w("Bluetooth not available, using speaker");
            LinphoneManager.getInstance().routeAudioToSpeaker();
            isSpeakerEnabled = true;
            iconSpeaker.setImageResource(R.drawable.speaker_off);

            tvSpeaker.setTextColor(getResources().getColor(R.color.white));
            speaker.setBackgroundColor(getResources().getColor(R.color.mBlue));
        }
        advImage.setVisibility(View.GONE);
        iconVideo.setImageResource(R.drawable.video_off);

        tvVideo.setTextColor(getResources().getColor(R.color.white));
        video.setBackgroundColor(getResources().getColor(R.color.mBlue));
        video.setEnabled(true);
        videoProgress.setVisibility(View.INVISIBLE);

        LinphoneManager.stopProximitySensorForActivity(InCallActivity.this);
        replaceFragmentAudioByVideo();
        displayVideoCallControlsIfHidden();
    }

    private void replaceFragmentVideoByAudio() {
        audioCallFragment = new AudioCallFragment();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, audioCallFragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }

    private void replaceFragmentAudioByVideo() {
        // Hiding controls to let displayVideoCallControlsIfHidden add them plus
        // the callback
        mControlsLayout.setVisibility(View.GONE);
        mControlsLayout1.setVisibility(View.GONE);
        switchCamera.setVisibility(View.INVISIBLE);

        videoCallFragment = new VideoCallFragment();

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, videoCallFragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
        }
    }

    private void toggleMicro() {
        LinphoneCore lc = LinphoneManager.getLc();
        isMicMuted = !isMicMuted;
        lc.muteMic(isMicMuted);
        if (isMicMuted) {
            iconMicro.setImageResource(R.drawable.micro_off);

            tvMicro.setTextColor(getResources().getColor(R.color.white));
            micro.setBackgroundColor(getResources().getColor(R.color.mBlue));
            // jy.setBackgroundResource(R.drawable.jydj);
        } else {
            iconMicro.setImageResource(R.drawable.micro_on);

            tvMicro.setTextColor(getResources().getColor(R.color.mBlue));
            micro.setBackgroundColor(getResources().getColor(R.color.white));
            // jy.setBackgroundResource(R.drawable.jywdj);
        }
    }

    private void toggleSpeaker() {
        isSpeakerEnabled = !isSpeakerEnabled;
        if (isSpeakerEnabled) {
            LinphoneManager.getInstance().routeAudioToSpeaker();
            iconSpeaker.setImageResource(R.drawable.speaker_off);

            tvSpeaker.setTextColor(getResources().getColor(R.color.white));
            speaker.setBackgroundColor(getResources().getColor(R.color.mBlue));

            LinphoneManager.getLc().enableSpeaker(isSpeakerEnabled);
            // ysq.setBackgroundResource(R.drawable.ysqdj);
        } else {
            Log.d("Toggle speaker off, routing back to earpiece");
            LinphoneManager.getInstance().routeAudioToReceiver();
            iconSpeaker.setImageResource(R.drawable.speaker_on);

            tvSpeaker.setTextColor(getResources().getColor(R.color.mBlue));
            speaker.setBackgroundColor(getResources().getColor(R.color.white));
            // ysq.setBackgroundResource(R.drawable.ysqwdj);
        }
    }

    private void pauseOrResumeCall() {
        LinphoneCore lc   = LinphoneManager.getLc();
        LinphoneCall call = lc.getCurrentCall();
        pauseOrResumeCall(call);
    }

    public void pauseOrResumeCall(LinphoneCall call) {
        LinphoneCore lc = LinphoneManager.getLc();
        if (call != null && LinphoneUtils.isCallRunning(call)) {
            if (call.isInConference()) {
                lc.removeFromConference(call);
                if (lc.getConferenceSize() <= 1) {
                    lc.leaveConference();
                }
            } else {
                lc.pauseCall(call);
                if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
                    isVideoCallPaused = true;
                    showAudioView();
                }
                pause.setBackgroundResource(R.drawable.pause_on);
            }
        } else {
            List<LinphoneCall> pausedCalls = LinphoneUtils.getCallsInState(lc,
                    Arrays.asList(State.Paused));
            if (pausedCalls.size() == 1) {
                LinphoneCall callToResume = pausedCalls.get(0);
                if ((call != null && callToResume.equals(call)) || call == null) {
                    lc.resumeCall(callToResume);
                    if (isVideoCallPaused) {
                        isVideoCallPaused = false;
                        showVideoView();
                    }
                    pause.setBackgroundResource(R.drawable.pause_off);
                }
            } else if (call != null) {
                lc.resumeCall(call);
                if (isVideoCallPaused) {
                    isVideoCallPaused = false;
                    showVideoView();
                }
                pause.setBackgroundResource(R.drawable.pause_off);
            }
        }
    }

    private void hangUp() {
        LinphoneCore lc          = LinphoneManager.getLc();
        LinphoneCall currentCall = lc.getCurrentCall();

        if (currentCall != null) {
            lc.terminateCall(currentCall);
        } else if (lc.isInConference()) {
            lc.terminateConference();
        } else {
            lc.terminateAllCalls();
        }

    }

    private void enterConference() {
        LinphoneManager.getLc().addAllToConference();
    }

    public void pauseOrResumeConference() {
        LinphoneCore lc = LinphoneManager.getLc();
        if (lc.isInConference()) {
            lc.leaveConference();
        } else {
            lc.enterConference();
        }
    }

    /**
     * 底部按钮显示的控制
     */
    public void displayVideoCallControlsIfHidden() {
        if (mControlsLayout != null) {

            if (mControlsLayout.getVisibility() != View.VISIBLE) {
                if (isAnimationDisabled) {

                    mControlsLayout.setVisibility(View.VISIBLE);
                    mControlsLayout1.setVisibility(View.VISIBLE);

                    isHidden = true;

                    callsList.setVisibility(showCallListInVideo ? View.VISIBLE
                            : View.GONE);
                    if (cameraNumber > 1) {
                        switchCamera.setVisibility(View.GONE);
                    }
                } else {

                    Animation animation = slideInBottomToTop;
                    animation.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            mControlsLayout.setVisibility(View.VISIBLE);
                            mControlsLayout1.setVisibility(View.VISIBLE);
                            callsList
                                    .setVisibility(showCallListInVideo ? View.VISIBLE
                                            : View.GONE);
                            if (cameraNumber > 1) {
                                switchCamera.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            animation.setAnimationListener(null);
                        }
                    });
                    mControlsLayout.startAnimation(animation);
                    mControlsLayout1.startAnimation(animation);
                    if (cameraNumber > 1) {
                        switchCamera.startAnimation(slideInTopToBottom);
                    }
                }
            }

            resetControlsHidingCallBack();

        }
    }

    public void resetControlsHidingCallBack() {
        if (mControlsHandler != null && mControls != null) {
            mControlsHandler.removeCallbacks(mControls);
        }
        mControls = null;

        if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())
                && mControlsHandler != null) {
            mControlsHandler.postDelayed(mControls = new Runnable() {
                public void run() {
                    hideNumpad();

                    if (isAnimationDisabled) {

                        if (VideoCallFragment.hidden) {
                            mControlsLayout.setVisibility(View.GONE);
                            mControlsLayout1.setVisibility(View.GONE);
                            VideoCallFragment.hidden = false;

                            if (isHidden) {
                                mControlsLayout.setVisibility(View.VISIBLE);
                                mControlsLayout1.setVisibility(View.VISIBLE);
                                isHidden = false;
                            }
                        }

                        transfer.setVisibility(View.INVISIBLE);
                        addCall.setVisibility(View.INVISIBLE);
                        // mControlsLayout.setVisibility(View.GONE);
                        // mControlsLayout1.setVisibility(View.GONE);
                        callsList.setVisibility(View.GONE);
                        switchCamera.setVisibility(View.INVISIBLE);
//						numpad.setVisibility(View.GONE);
                        options.setBackgroundResource(R.drawable.options);
                    } else {
                        Animation animation = slideOutTopToBottom;
                        animation.setAnimationListener(new AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                video.setEnabled(false); // HACK: Used to avoid
                                // controls from
                                // being hided if
                                // video is switched
                                // while controls
                                // are hiding
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                video.setEnabled(true); // HACK: Used to avoid
                                // controls from being
                                // hided if video is
                                // switched while
                                // controls are hiding
                                transfer.setVisibility(View.INVISIBLE);
                                addCall.setVisibility(View.INVISIBLE);
                                mControlsLayout.setVisibility(View.GONE);
                                mControlsLayout1.setVisibility(View.GONE);
                                callsList.setVisibility(View.GONE);
                                switchCamera.setVisibility(View.INVISIBLE);
                                numpad.setVisibility(View.GONE);
                                options.setBackgroundResource(R.drawable.options);

                                animation.setAnimationListener(null);
                            }
                        });
                        mControlsLayout.startAnimation(animation);
                        mControlsLayout1.startAnimation(animation);
                        if (cameraNumber > 1) {
                            switchCamera.startAnimation(slideOutBottomToTop);
                        }
                    }
                }
            }, 100);
        }
    }

    public void setCallControlsVisibleAndRemoveCallbacks() {
        if (mControlsHandler != null && mControls != null) {
            mControlsHandler.removeCallbacks(mControls);
        }
        mControls = null;

        mControlsLayout.setVisibility(View.VISIBLE);
        mControlsLayout1.setVisibility(View.VISIBLE);
        callsList.setVisibility(View.VISIBLE);
        switchCamera.setVisibility(View.INVISIBLE);
    }

    private void hideNumpad() {
        if (numpad == null || numpad.getVisibility() != View.VISIBLE) {
            return;
        }

        dialer.setBackgroundResource(R.drawable.dialer_alt);
        if (isAnimationDisabled) {
            numpad.setVisibility(View.GONE);
        } else {
            Animation animation = slideOutTopToBottom;
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    numpad.setVisibility(View.GONE);
                    animation.setAnimationListener(null);
                }
            });
            numpad.startAnimation(animation);
        }
    }

    private void hideOrDisplayNumpad() {
        if (numpad == null) {
            return;
        }

        if (numpad.getVisibility() == View.VISIBLE) {
            hideNumpad();
        } else {
            dialer.setBackgroundResource(R.drawable.dialer_alt_back);
            if (isAnimationDisabled) {
                numpad.setVisibility(View.VISIBLE);
            } else {
                Animation animation = slideInBottomToTop;
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        numpad.setVisibility(View.VISIBLE);
                        animation.setAnimationListener(null);
                    }
                });
                numpad.startAnimation(animation);
            }
        }
    }

    private void hideAnimatedPortraitCallOptions() {
        Animation animation = slideOutLeftToRight;
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isTransferAllowed) {
                    transfer.setVisibility(View.INVISIBLE);
                }
                addCall.setVisibility(View.INVISIBLE);
                animation.setAnimationListener(null);
            }
        });
        if (isTransferAllowed) {
            transfer.startAnimation(animation);
        }
        addCall.startAnimation(animation);
    }

    private void hideAnimatedLandscapeCallOptions() {
        Animation animation = slideOutTopToBottom;
        if (isTransferAllowed) {
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    transfer.setAnimation(null);
                    transfer.setVisibility(View.INVISIBLE);
                    animation = AnimationUtils
                            .loadAnimation(InCallActivity.this,
                                    R.anim.slide_out_top_to_bottom); // Reload
                    // animation
                    // to
                    // prevent
                    // transfer
                    // button
                    // to
                    // blink
                    animation.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            addCall.setVisibility(View.INVISIBLE);
                        }
                    });
                    addCall.startAnimation(animation);
                }
            });
            transfer.startAnimation(animation);
        } else {
            animation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    addCall.setVisibility(View.INVISIBLE);
                }
            });
            addCall.startAnimation(animation);
        }
    }

    private void showAnimatedPortraitCallOptions() {
        Animation animation = slideInRightToLeft;
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options.setBackgroundResource(R.drawable.options_alt);
                if (isTransferAllowed) {
                    transfer.setVisibility(View.VISIBLE);
                }
                addCall.setVisibility(View.VISIBLE);
                animation.setAnimationListener(null);
            }
        });
        if (isTransferAllowed) {
            transfer.startAnimation(animation);
        }
        addCall.startAnimation(animation);
    }

    private void showAnimatedLandscapeCallOptions() {
        Animation animation = slideInBottomToTop;
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addCall.setAnimation(null);
                options.setBackgroundResource(R.drawable.options_alt);
                addCall.setVisibility(View.VISIBLE);
                if (isTransferAllowed) {
                    animation.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            transfer.setVisibility(View.VISIBLE);
                        }
                    });
                    transfer.startAnimation(animation);
                }
            }
        });
        addCall.startAnimation(animation);
    }

    private void hideOrDisplayAudioRoutes() {
        if (routeSpeaker.getVisibility() == View.VISIBLE) {
            routeSpeaker.setVisibility(View.INVISIBLE);
            routeBluetooth.setVisibility(View.INVISIBLE);
            routeReceiver.setVisibility(View.INVISIBLE);
            audioRoute.setSelected(false);
        } else {
            routeSpeaker.setVisibility(View.VISIBLE);
            routeBluetooth.setVisibility(View.VISIBLE);
            routeReceiver.setVisibility(View.VISIBLE);
            audioRoute.setSelected(true);
        }
    }

    private void hideOrDisplayCallOptions() {
        boolean isOrientationLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (addCall.getVisibility() == View.VISIBLE) {
            options.setBackgroundResource(R.drawable.options);
            if (isAnimationDisabled) {
                if (isTransferAllowed) {
                    transfer.setVisibility(View.INVISIBLE);
                }
                addCall.setVisibility(View.INVISIBLE);
            } else {
                if (isOrientationLandscape) {
                    hideAnimatedLandscapeCallOptions();
                } else {
                    hideAnimatedPortraitCallOptions();
                }
            }
            options.setSelected(false);
        } else {
            if (isAnimationDisabled) {
                if (isTransferAllowed) {
                    transfer.setVisibility(View.VISIBLE);
                }
                addCall.setVisibility(View.VISIBLE);
                options.setBackgroundResource(R.drawable.options_alt);
            } else {
                if (isOrientationLandscape) {
                    showAnimatedLandscapeCallOptions();
                } else {
                    showAnimatedPortraitCallOptions();
                }
            }
            options.setSelected(true);
            transfer.setEnabled(LinphoneManager.getLc().getCurrentCall() != null);
        }
    }

    public void goBackToDialer() {
        Intent intent = new Intent();
        intent.putExtra("Transfer", false);
        setResult(Activity.RESULT_FIRST_USER, intent);
        finish();
    }

    private void goBackToDialerAndDisplayTransferButton() {
        Intent intent = new Intent();
        intent.putExtra("Transfer", true);
        setResult(Activity.RESULT_FIRST_USER, intent);
        finish();
    }

    private void acceptCallUpdate(boolean accept) {
        if (timer != null) {
            timer.cancel();
        }

        if (callUpdateDialog != null) {
            callUpdateDialog.dismissAllowingStateLoss();
        }

        LinphoneCall call = LinphoneManager.getLc().getCurrentCall();
        if (call == null) {
            return;
        }

        LinphoneCallParams params = call.getCurrentParamsCopy();
        if (accept) {
            params.setVideoEnabled(true);
            LinphoneManager.getLc().enableVideo(true, true);
        }

        try {
            LinphoneManager.getLc().acceptCallUpdate(call, params);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    public void startIncomingCallActivity() {
        Intent intent = new Intent(this, IncomingCallActivity.class);
        intent.putExtra("intent", this.getClass().toString() + "\n 1496");
        startActivity(intent);
    }

    @Override
    public void onCallStateChanged(final LinphoneCall call, State state,
                                   String message) {
        if (LinphoneManager.getLc().getCallsNb() == 0) {
            finish();
            return;
        }

        if (!LinphonePreferences.instance().isVideoEnabled()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    video.setEnabled(true);
                }
            });

        }

        //接听
        if (state == State.IncomingReceived) {
            startIncomingCallActivity();
            return;
        }

        //视频
        if (state == State.Paused || state == State.PausedByRemote
                || state == State.Pausing) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    video.setEnabled(false);
                    if (isVideoEnabled(call)) {
                        showAudioView();
                    }
                }
            });
        }

        //声音
        if (state == State.Resuming) {
            if (LinphonePreferences.instance().isVideoEnabled()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        status.refreshStatusItems(call, isVideoEnabled(call));
                        if (call.getCurrentParamsCopy().getVideoEnabled()) {
                            showVideoView();
                        }
                    }
                });
            }
        }

        if (state == State.StreamsRunning) {
            switchVideo(isVideoEnabled(call));

            LinphoneManager.getLc().enableSpeaker(isSpeakerEnabled);

            isMicMuted = LinphoneManager.getLc().isMicMuted();
            enableAndRefreshInCallActions();

            if (status != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        videoProgress.setVisibility(View.GONE);
                        status.refreshStatusItems(call, isVideoEnabled(call));
                    }
                });
            }
        }

        refreshInCallActions();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                refreshCallList(getResources());
            }
        });

        if (state == State.CallUpdatedByRemote) {
            // If the correspondent proposes video while audio call
            boolean videoEnabled = LinphonePreferences.instance()
                    .isVideoEnabled();
            if (!videoEnabled) {
                acceptCallUpdate(false);
                return;
            }

            boolean remoteVideo = call.getRemoteParams().getVideoEnabled();
            boolean localVideo  = call.getCurrentParamsCopy().getVideoEnabled();
            boolean autoAcceptCameraPolicy = LinphonePreferences.instance()
                    .shouldAutomaticallyAcceptVideoRequests();
            if (remoteVideo && !localVideo && !autoAcceptCameraPolicy
                    && !LinphoneManager.getLc().isInConference()) {
                mHandler.post(new Runnable() {
                    public void run() {
                        // showAcceptCallUpdateDialog();
                        // 自动接受视频
                        acceptCallUpdate(true);

                        // timer = new CountDownTimer(
                        // SECONDS_BEFORE_DENYING_CALL_UPDATE, 1000) {
                        // public void onTick(long millisUntilFinished) {
                        // }
                        //
                        // public void onFinish() {
                        // acceptCallUpdate(false);
                        // }
                        // }.start();
                    }
                });
            }
            // else if (remoteVideo && !LinphoneManager.getLc().isInConference()
            // && autoAcceptCameraPolicy) {
            // mHandler.post(new Runnable() {
            // @Override
            // public void run() {
            // acceptCallUpdate(true);
            // }
            // });
            // }
        }

        mHandler.post(new Runnable() {
            public void run() {
                transfer.setEnabled(LinphoneManager.getLc().getCurrentCall() != null);
            }
        });
    }

    private void showAcceptCallUpdateDialog() {
        FragmentManager fm = getSupportFragmentManager();
        callUpdateDialog = new AcceptCallUpdateDialog();
        callUpdateDialog.show(fm, "Accept Call Update Dialog");
    }

    @Override
    public void onCallEncryptionChanged(final LinphoneCall call,
                                        boolean encrypted, String authenticationToken) {
        if (status != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    status.refreshStatusItems(call, call.getCurrentParamsCopy()
                            .getVideoEnabled());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        instance = this;
        if (isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
            displayVideoCallControlsIfHidden();
        } else {
            LinphoneManager.startProximitySensorForActivity(this);
            setCallControlsVisibleAndRemoveCallbacks();
        }

        super.onResume();

        LinphoneManager.addListener(this);

        refreshCallList(getResources());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // SettingInfo.setParams(PreferenceBean.BACKTOAPPNUME, "已返回");
        if (mControlsHandler != null && mControls != null) {
            mControlsHandler.removeCallbacks(mControls);
        }
        mControls = null;

        if (!isVideoEnabled(LinphoneManager.getLc().getCurrentCall())) {
            LinphoneManager.stopProximitySensorForActivity(this);
        }

        LinphoneManager.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        LinphoneManager.getInstance().changeStatusToOnline();

        if (mControlsHandler != null && mControls != null) {
            mControlsHandler.removeCallbacks(mControls);
        }
        mControls = null;
        mControlsHandler = null;
        mHandler = null;
        // unregisterReceiver(receiver);
        unbindDrawables(findViewById(R.id.topLayout));
        instance = null;
        super.onDestroy();
        System.gc();
        // SettingInfo.setParams(PreferenceBean.BACKTOAPPNUME, "");

    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ImageView) {
            view.setOnClickListener(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (LinphoneUtils.onKeyVolumeAdjust(keyCode))
            return true;
        if (LinphoneUtils.onKeyBackGoHome(this, keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    public void bindAudioFragment(AudioCallFragment fragment) {
        audioCallFragment = fragment;
    }

    public void bindVideoFragment(VideoCallFragment fragment) {
        videoCallFragment = fragment;
    }

    @SuppressLint("ValidFragment")
    class AcceptCallUpdateDialog extends DialogFragment {

        public AcceptCallUpdateDialog() {
            // Empty constructor required for DialogFragment
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.accept_call_update_dialog,
                    container);

            getDialog().setTitle(R.string.call_update_title);

            Button yes = (Button) view.findViewById(R.id.yes);
            yes.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Call Update Accepted");
                    acceptCallUpdate(true);
                }
            });

            Button no = (Button) view.findViewById(R.id.no);
            no.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Call Update Denied");
                    acceptCallUpdate(false);
                }
            });

            return view;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);

            callUpdateDialog = new AcceptCallUpdateDialog();
            callUpdateDialog.show(getSupportFragmentManager(),
                    "Accept Call Update Dialog");
        }
    }

    private void displayConferenceHeader() {
        LinearLayout conferenceHeader = (LinearLayout) inflater.inflate(
                R.layout.conference_header, container, false);

        ImageView conferenceState = (ImageView) conferenceHeader
                .findViewById(R.id.conferenceStatus);
        conferenceState.setOnClickListener(this);
        if (LinphoneManager.getLc().isInConference()) {
            conferenceState.setImageResource(R.drawable.playaudio);
        } else {
            conferenceState.setImageResource(R.drawable.pause);
        }

        callsList.addView(conferenceHeader);
    }

    private void displayCall(Resources resources, LinphoneCall call, int index) {
        String          sipUri = call.getRemoteAddress().asStringUriOnly();
        LinphoneAddress lAddress;
        try {
            lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(
                    sipUri);
        } catch (LinphoneCoreException e) {
            Log.e("Incall activity cannot parse remote address", e);
            lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(
                    "uknown", "unknown", "unkonown");
        }

        // Control Row
        LinearLayout callView = (LinearLayout) inflater.inflate(
                R.layout.active_call_control_row, container, false);
        callView.setId(index + 1);
        setContactName(callView, lAddress, sipUri, resources);
        displayCallStatusIconAndReturnCallPaused(callView, call);
        setRowBackground(callView, index);
        registerCallDurationTimer(callView, call);
        callsList.addView(callView);

        // Image Row
        LinearLayout imageView = (LinearLayout) inflater.inflate(
                R.layout.active_call_image_row, container, false);
        Uri pictureUri = LinphoneUtils
                .findUriPictureOfContactAndSetDisplayName(lAddress, imageView
                        .getContext().getContentResolver());
        displayOrHideContactPicture(imageView, pictureUri, false);
        callsList.addView(imageView);

        callView.setTag(imageView);
        callView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    View imageView = (View) v.getTag();
                    if (imageView.getVisibility() == View.VISIBLE)
                        imageView.setVisibility(View.GONE);
                    else
                        imageView.setVisibility(View.VISIBLE);
                    callsList.invalidate();
                }
            }
        });
    }

    private void setContactName(LinearLayout callView,
                                LinphoneAddress lAddress, String sipUri, Resources resources) {
        TextView contact = (TextView) callView
                .findViewById(R.id.contactNameOrNumber);

        LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress,
                callView.getContext().getContentResolver());
        String displayName = lAddress.getDisplayName();

        if (displayName == null) {
            if (resources.getBoolean(R.bool.only_display_username_if_unknown)
                    && LinphoneUtils.isSipAddress(sipUri)) {
                contact.setText(LinphoneUtils.getUsernameFromAddress(sipUri));
            } else {
                contact.setText(sipUri);
            }
        } else {
            contact.setText(displayName);
        }
    }

    private boolean displayCallStatusIconAndReturnCallPaused(
            LinearLayout callView, LinphoneCall call) {
        boolean isCallPaused, isInConference;
        ImageView callState = (ImageView) callView
                .findViewById(R.id.callStatus);
        callState.setTag(call);
        callState.setOnClickListener(this);

        if (call.getState() == State.Paused
                || call.getState() == State.PausedByRemote
                || call.getState() == State.Pausing) {
            callState.setImageResource(R.drawable.pause);
            isCallPaused = true;
            isInConference = false;
        } else if (call.getState() == State.OutgoingInit
                || call.getState() == State.OutgoingProgress
                || call.getState() == State.OutgoingRinging) {
            callState.setImageResource(R.drawable.call_state_ringing_default);
            isCallPaused = false;
            isInConference = false;
        } else {
            if (isConferenceRunning && call.isInConference()) {
                callState.setImageResource(R.drawable.remove);
                isInConference = true;
            } else {
                callState.setImageResource(R.drawable.playaudio);
                isInConference = false;
            }
            isCallPaused = false;
        }

        return isCallPaused || isInConference;
    }

    private void displayOrHideContactPicture(LinearLayout callView,
                                             Uri pictureUri, boolean hide) {
        AvatarWithShadow contactPicture = (AvatarWithShadow) callView
                .findViewById(R.id.contactPicture);
        if (pictureUri != null) {
            LinphoneUtils.setImagePictureFromUri(callView.getContext(),
                    contactPicture.getView(), Uri.parse(pictureUri.toString()),
                    R.drawable.unknown_small);
        }
        callView.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    private void setRowBackground(LinearLayout callView, int index) {
        int backgroundResource;
        if (index == 0) {
            // backgroundResource = active ?
            // R.drawable.cell_call_first_highlight :
            // R.drawable.cell_call_first;
            backgroundResource = R.drawable.cell_call_first;
        } else {
            // backgroundResource = active ? R.drawable.cell_call_highlight :
            // R.drawable.cell_call;
            backgroundResource = R.drawable.cell_call;
        }
        callView.setBackgroundResource(backgroundResource);
    }

    private void registerCallDurationTimer(View v, LinphoneCall call) {
        int callDuration = call.getDuration();
        if (callDuration == 0 && call.getState() != State.StreamsRunning) {
            return;
        }

        if (!isAdvert) {
            if (LinphoneManager.getLc().getCurrentCall() != null) {
                advertType = "3";
                getImage();
            }
            isAdvert = true;
        }

        Chronometer timer = (Chronometer) findViewById(R.id.callTimed);
        if (timer == null) {
            throw new IllegalArgumentException("no callee_duration view found");
        }
        callText.setText("通话中...");

        timer.setVisibility(View.VISIBLE);
        timer.setBase(SystemClock.elapsedRealtime() - 1000 * callDuration);
        timer.start();

        if (Config.experienceCall) {// 必须从点击体验这里点进才自动开启视频
            Config.experienceCall = false;
            try {
                Thread.sleep(5000);
                enabledOrDisabledVideo(isVideoEnabled(LinphoneManager.getLc().getCurrentCall()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshCallList(Resources resources) {
        if (callsList == null) {
            return;
        }

        callsList.removeAllViews();
        int index = 0;

        if (LinphoneManager.getLc().getCallsNb() == 0) {
            goBackToDialer();
            return;
        }

        isConferenceRunning = LinphoneManager.getLc().getConferenceSize() > 1;
        if (isConferenceRunning) {
            displayConferenceHeader();
            index++;
        }
        for (LinphoneCall call : LinphoneManager.getLc().getCalls()) {
            displayCall(resources, call, index);
            index++;
        }

        if (LinphoneManager.getLc().getCurrentCall() == null) {
            showAudioView();
            video.setEnabled(false);
        }

        callsList.invalidate();
    }

    // private BroadcastReceiver receiver=new BroadcastReceiver() {
    //
    // @Override
    // public void onReceive(Context context, Intent intent) {
    // if("calledEnd".equals(intent.getStringExtra("calledEnd"))){
    // // saveRecord();
    // }
    // }
    // };
    private List<String> deleteList;
    private String advertType = "2";

    /**
     * 获取轮询图片
     */
    public void getImage() {
        new NetAction().getAdvertiseImg(new OnMyResponseListener() {
            @Override
            public void onResponse(String jsonObject) {
                if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                    AdvertisementBean bean = JSONObject.parseObject(
                            jsonObject.toString(), AdvertisementBean.class);
                    if (CommReply.SUCCESS.equals(bean.getStatuscode())) {
                        String[]     advInfo = bean.getAdvInfo();
                        List<String> list    = new ArrayList<String>();
                        List<String> oldList = new ArrayList<String>();
                        int params = Integer.parseInt(SettingInfo.getParams(
                                PreferenceBean.ADVERTISEMENTCOUNT + "_"
                                        + advertType, "0").toString());
                        int count = 0;
                        // 取得所有服务端的图片路径
                        list.addAll(Arrays.asList(advInfo));
                        // 取得本机旧的缓存图片路径
                        for (int i = 0; i < params; i++) {
                            oldList.add(SettingInfo.getParams(
                                    PreferenceBean.ADVERTISEMENT + "_"
                                            + advertType + i, ""));
                        }
                        // 比较两个集合 取交集
                        // list.retainAll(oldList);
                        // 得到变动的path路径
                        oldList.removeAll(list);
                        // 判断是否被删除过，删除了就更新此界面
                        boolean delete = false;
                        if (deleteList == null)
                            deleteList = new ArrayList<String>();
                        deleteList.addAll(oldList);
                        if (deleteList.size() > 0) {
                            delete = true;
                        }

                        // 加载新的图片
                        list = new ArrayList<String>();
                        for (int i = 0; i < advInfo.length; i++) {
                            list.add(advInfo[i]);
                            count++;
                            SettingInfo.setParams(PreferenceBean.ADVERTISEMENT
                                    + "_" + advertType + i, advInfo[i]);
                        }
                        SettingInfo.setParams(PreferenceBean.ADVERTISEMENTCOUNT
                                + "_" + advertType, "" + count);
                        // 当第一次进入，或者删除图片，或者第一次初始化就刷新界面
                        if (params <= 0 || delete || viewAdapter == null) {
                            deleteList = new ArrayList<String>();
                            initViewPager(list, deleteList);
                            delete = false;
                        }
                    } else {
                        // T.show(getActivity(), ""+bean.getReason(),0);
                        requestImgError();
                    }
                } else {
                    requestImgError();
                }
            }
        }, advertType).execm();
    }

    // 请求失败后本地图片处理
    public void requestImgError() {
        // T.show(getActivity(), ""+getString(R.string.service_error),0);
        int params = Integer.parseInt(SettingInfo.getParams(
                PreferenceBean.ADVERTISEMENTCOUNT, "0").toString());
        if (params <= 0) {
            if (viewAdapter != null) {
                if (viewAdapter.getCount() <= 0) {
                    List<String> list = new ArrayList<String>();
                    list.add("默认图片:" + R.drawable.bhp1);
                    initViewPager(list, deleteList);
                }
            } else {
                List<String> list = new ArrayList<String>();
                list.add("默认图片:" + R.drawable.bhp1);
                initViewPager(list, deleteList);
            }
        } else {
            if (viewAdapter != null) {
                if (viewAdapter.getCount() <= 0) {
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < params; i++) {
                        list.add(SettingInfo.getParams(
                                PreferenceBean.ADVERTISEMENT + i, ""));
                    }
                    initViewPager(list, deleteList);
                }
            } else {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < params; i++) {
                    list.add(SettingInfo.getParams(PreferenceBean.ADVERTISEMENT
                            + i, ""));
                }
                initViewPager(list, deleteList);
            }
        }

    }

    private void initViewPager(List<String> list, List<String> delete) {
        // if(viewAdapter==null){
        // viewAdapter = new DialerViewAdapter(InCallActivity.this);
        // }
        viewAdapter = new DialerViewAdapter(InCallActivity.this);
        viewAdapter.change(list, deleteList);
        // if(viewpager==null){
        // try{
        // viewpager = (ViewPager) findViewById(R.id.viewpager);
        // viewpager.setAdapter(viewAdapter);
        // viewpager.setOnPageChangeListener(myOnPageChangeListener);
        // }catch(Exception e){
        // Log.e("",e.getMessage());
        // }
        // }

        try {
            viewpager = (ViewPager) findViewById(R.id.viewpager);
            viewpager.setAdapter(viewAdapter);
            viewpager.setOnPageChangeListener(myOnPageChangeListener);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }

        initDot();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (viewpager != null) {
                    int next = viewpager.getCurrentItem() + 1;
                    if (next >= viewAdapter.getCount()) {
                        next = 0;
                    }
                    viewHandler.sendEmptyMessage(next);
                }
            }
        };
        viewHandler.removeCallbacks(runnable);
        viewHandler.postDelayed(runnable, autoChangeTime);
    }

    private List<Integer> getList() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.bhp);
        list.add(R.drawable.bhp1);
        list.add(R.drawable.bhp);
        list.add(R.drawable.bhp1);
        return list;
    }

    // 初始化dot视图
    private void initDot() {
        viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        if (viewGroup == null)
            return;
        viewGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                40, 40);
        layoutParams.setMargins(4, 3, 4, 3);
        Log.e("", "dot长度：" + viewAdapter.getCount());
        dots = new ImageView[viewAdapter.getCount()];
        try {
            for (int i = 0; i < viewAdapter.getCount(); i++) {
                dot = new ImageView(InCallActivity.this);

                dot.setLayoutParams(layoutParams);
                dots[i] = dot;
                dots[i].setTag(i);
                dots[i].setOnClickListener(onClick);

                if (i == 0) {
                    dots[i].setBackgroundResource(R.drawable.dotc);
                } else {
                    dots[i].setBackgroundResource(R.drawable.dotn);
                }

                viewGroup.addView(dots[i]);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    OnPageChangeListener myOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            setCurDot(arg0);
            viewHandler.removeCallbacks(runnable);
            viewHandler.postDelayed(runnable, autoChangeTime);
        }

    };

    // 实现dot点击响应功能
    OnClickListener onClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            setCurView(position);
        }

    };

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position > viewAdapter.getCount()) {
            return;
        }
        viewpager.setCurrentItem(position);
    }

    /**
     * 选中当前引导小点
     */
    private void setCurDot(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (position == i) {
                dots[i].setBackgroundResource(R.drawable.dotc);
            } else {
                dots[i].setBackgroundResource(R.drawable.dotn);
            }
        }
    }

    /**
     * 每隔固定时间切换广告栏图片
     */
    @SuppressLint("HandlerLeak")
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurView(msg.what);
        }

    };
}
