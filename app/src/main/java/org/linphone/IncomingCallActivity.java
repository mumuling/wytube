/*
IncomingCallActivity.java
Copyright (C) 2011  Belledonne Communications, Grenoble, France

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
package org.linphone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.adapter.DialerViewAdapter;
import com.cqxb.yecall.bean.AdvertisementBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallParams;
import org.linphone.mediastream.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Activity displayed when a call comes in. It should bypass the screen lock
 * mechanism.
 *
 * @author Guillaume Beraudo
 */
public class IncomingCallActivity extends Activity implements
        LinphoneOnCallStateChangedListener, OnClickListener {

    private static IncomingCallActivity instance;
    private        LinphoneCall         mCall;
    TextView tv_incall_talkName, tv_incall_talkNumber;
    LinearLayout fragmentContainer;
    ImageView    iv_callcoming_finishcall, iv_callcoming_answer;
    private FrameLayout       advImage;
    private ViewPager         viewpager;
    private DialerViewAdapter viewAdapter;
    private LinearLayout      viewGroup;
    private ImageView         dot, dots[];
    private Runnable runnable;
    private int autoChangeTime = 3000;
    private PowerManager.WakeLock wakeLock;

    public static IncomingCallActivity instance() {
        return instance;
    }

    public static boolean isInstanciated() {
        return instance != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // setContentView(R.layout.incoming);
        setContentView(R.layout.incomingcall);

        new Thread() {
            @Override
            public void run() {

                boolean isEnd = false;

                while (!isEnd) {

                    if (YETApplication.state == LinphoneCall.State.CallEnd) {

                        isEnd = true;

                        YETApplication.state = LinphoneCall.State.CallReleased;

                        finish();
                    }
                }
            }
        }.start();

        tv_incall_talkName = (TextView) findViewById(R.id.tv_incall_talkName);
        tv_incall_talkNumber = (TextView) findViewById(R.id.tv_incall_talkNumber);
        fragmentContainer = (LinearLayout) findViewById(R.id.fragmentContainer);
        findViewById(R.id.iv_callcoming_finishcall).setOnClickListener(this);
        findViewById(R.id.iv_callcoming_answer).setOnClickListener(this);
        advImage = (FrameLayout) findViewById(R.id.imageViewAd);
        // set this flag so this activity will stay in front of the keyguard
        int flags = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);
        advertType = "2";
        getImage();
        super.onCreate(savedInstanceState);
        instance = this;

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
        LinphoneManager.addListener(this);
        if (LinphoneManager.getLcIfManagerNotDestroyedOrNull() != null) {
            List<LinphoneCall> calls = LinphoneUtils
                    .getLinphoneCalls(LinphoneManager.getLc());
            for (LinphoneCall call : calls) {
                if (State.IncomingReceived == call.getState()) {
                    mCall = call;
                    break;
                }
            }
        }
        if (mCall == null) {
            Log.e("Couldn't find incoming call");
            finish();
            return;
        }
        LinphoneAddress address = mCall.getRemoteAddress();
        // May be greatly sped up using a drawable cache
        Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(
                address, getContentResolver());
        tv_incall_talkName.setText(address.getUserName());
        if (getResources().getBoolean(R.bool.only_display_username_if_unknown)) {
            tv_incall_talkNumber.setText(address.getUserName());
        } else {
            tv_incall_talkNumber.setText(address.asStringUriOnly());
        }
        String name = BaseUntil.stringNoNull(tv_incall_talkNumber.getText()
                .toString());
        List<Contacts> cltList = YETApplication.getinstant().getCltList();
        for (int i = 0; i < cltList.size(); i++) {
            if (cltList.get(i).getNumber().equals(name)) {
                tv_incall_talkName.setText(cltList.get(i).getContactName());
                name = cltList.get(i).getContactName();
                break;
            }
        }
        SettingInfo.setParams(PreferenceBean.CALLNAME, name);
        SettingInfo.setParams(PreferenceBean.CALLPHONE,
                address.asStringUriOnly());
        SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LinphoneManager.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (LinphoneUtils.onKeyBackGoHome(this, keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCallStateChanged(LinphoneCall call, State state, String msg) {
        if (call == mCall && State.CallEnd == state) {
            IncomingCallActivity.this.finish();
        }
        if (state == State.StreamsRunning) {
            // The following should not be needed except some devices need it
            // (e.g. Galaxy S).
            LinphoneManager.getLc().enableSpeaker(LinphoneManager.getLc().isSpeakerEnabled());
        }
    }

    // 拒接
    private void decline() {
        // saveRecord();
        SettingInfo.setParams(PreferenceBean.CALLSTATUS, "呼入未接");
        LinphoneManager.getLc().terminateCall(mCall);
        Intent intent = new Intent(Smack.action);
        intent.putExtra("hangUp", "hangUp");
        getApplicationContext().sendBroadcast(intent);
    }

    // 接听
    private void answer() {
        SettingInfo.setParams(PreferenceBean.CALLSTATUS, "呼入");
        LinphoneCallParams params = LinphoneManager.getLc()
                .createDefaultCallParameters();
        if (mCall != null && mCall.getRemoteParams() != null
                && mCall.getRemoteParams().getVideoEnabled()
                && LinphoneManager.isInstanciated()) {
            params.setVideoEnabled(true);
        } else {
            params.setVideoEnabled(false);
        }

        if (mCall != null && params != null) {
            advertType = "3";
            getImage();
            String recordFlag = SettingInfo.getParams(PreferenceBean.OUTCALLCHECK, "");
            if (recordFlag.equals("true")) {
                DateFormat df         = new SimpleDateFormat("yyyyMMddHHmmss");
                String     recordPath = "/sdcard/tedi/software/files/audio/" + LinphoneManager.getLc().getAuthInfosList()[0].getUsername() + "_" + df.format(new Date()) + ".wav";
                File       file       = new File(recordPath);
                File       dir        = new File(file.getParent());
                boolean    isDir      = dir.exists();
                if (!isDir) {
                    dir.mkdirs();
                }
                params.setRecordFile(recordPath);
                SettingInfo.setParams(PreferenceBean.RECORDPATH, recordPath);
                LinphoneManager.getLc().getCurrentCall().startRecording();
            } else {
                SettingInfo.setParams(PreferenceBean.RECORDPATH, "");
            }

        }

        boolean isLowBandwidthConnection = !LinphoneUtils
                .isHightBandwidthConnection(this);
        if (isLowBandwidthConnection) {
            params.enableLowBandwidth(true);
            Log.d("Low bandwidth enabled in call params");
        }

        if (!LinphoneManager.getInstance().acceptCallWithParams(mCall, params)) {
            // the above method takes care of Samsung Galaxy S
            Toast.makeText(this, R.string.couldnt_accept_call,
                    Toast.LENGTH_LONG).show();
        } else {
            if (!LinphoneActivity.isInstanciated()) {
                return;
            }
            final LinphoneCallParams remoteParams = mCall.getRemoteParams();
            if (remoteParams != null && remoteParams.getVideoEnabled()) {
                LinphoneActivity.instance().startVideoActivity(mCall);
            } else {
                LinphoneActivity.instance().startIncallActivity(mCall);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_callcoming_answer:
                answer();
                IncomingCallActivity.this.finish();
                break;
            case R.id.iv_callcoming_finishcall:
                decline();
                IncomingCallActivity.this.finish();
                break;
        }
    }

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
                                PreferenceBean.ADVERTISEMENTCOUNT + "_" + advertType, "0")
                                .toString());
                        int count = 0;
                        // 取得所有服务端的图片路径
                        list.addAll(Arrays.asList(advInfo));
                        // 取得本机旧的缓存图片路径
                        for (int i = 0; i < params; i++) {
                            oldList.add(SettingInfo.getParams(
                                    PreferenceBean.ADVERTISEMENT + "_" + advertType + i, ""));
                        }
                        // 比较两个集合 取交集
                        //list.retainAll(oldList);
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
                            SettingInfo.setParams(PreferenceBean.ADVERTISEMENT + "_" + advertType
                                    + i, advInfo[i]);
                        }
                        SettingInfo.setParams(
                                PreferenceBean.ADVERTISEMENTCOUNT + "_" + advertType, "" + count);
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
//		if (viewAdapter == null) {
//			viewAdapter = new DialerViewAdapter(IncomingCallActivity.this);
//		}
        viewAdapter = new DialerViewAdapter(IncomingCallActivity.this);
        viewAdapter.change(list, deleteList);
//		if (viewpager == null) {
//			viewpager = (ViewPager) findViewById(R.id.viewpager);
//			viewpager.setAdapter(viewAdapter);
//			viewpager.setOnPageChangeListener(myOnPageChangeListener);
//		}

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
                int next = viewpager.getCurrentItem() + 1;
                if (next >= viewAdapter.getCount()) {
                    next = 0;
                }
                viewHandler.sendEmptyMessage(next);
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
        viewGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                40, 40);
        layoutParams.setMargins(4, 3, 4, 3);
        Log.e("", "dot长度：" + viewAdapter.getCount());
        dots = new ImageView[viewAdapter.getCount()];
        for (int i = 0; i < viewAdapter.getCount(); i++) {
            dot = new ImageView(IncomingCallActivity.this);

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
