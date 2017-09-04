package org.linphone;

/*
 DialerFragment.java

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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.dialog.DialogPublic;
import com.cqxb.ui.WelcomeDialog;
import com.cqxb.yecall.LoginAppActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.RegistUserVoiceActivity;
import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.adapter.DialerViewAdapter;
import com.cqxb.yecall.adapter.DialingAdapter;
import com.cqxb.yecall.bean.AdvertisementBean;
import com.cqxb.yecall.bean.NoticeBean;
import com.cqxb.yecall.t9search.fragment.BaseFragment;
import com.cqxb.yecall.t9search.helper.ContactsHelper;
import com.cqxb.yecall.t9search.helper.ContactsHelper.OnContactsLoad;
import com.cqxb.yecall.t9search.helper.ContactsIndexHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.t9search.view.ContactsOperationView;
import com.cqxb.yecall.t9search.view.T9TelephoneDialpadView;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.NetUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

import org.linphone.LinphoneManager.AddressType;
import org.linphone.core.LinphoneCore;
import org.linphone.mediastream.Log;
import org.linphone.ui.AddressAware;
import org.linphone.ui.AddressText;
import org.linphone.ui.CallButton;
import org.linphone.ui.EraseButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Sylvain Berfini
 */
public class DialerFragment extends BaseFragment implements OnContactsLoad,
        OnClickListener {
    private static DialerFragment instance;
    private static boolean isCallTransferOngoing = false;

    private AddressText mAddress;
    private CallButton mCall;
    private ImageView mAddContact;
    private OnClickListener addContactListener, cancelListener,
            transferListener, titleListener;
    private boolean shouldEmptyAddressField = true;
    private List<Contacts> cList;// 通话记录 通过url查询
    private List<Contacts> cList2 = new ArrayList<>();// 搜索后的通话记录
    private DialingAdapter adapter;
    private TextView textView_title, textView_title2;
    private static View view;
    private ListView listView;
    private FrameLayout advImage;
    //	private LinearLayout btnCall, btnHistory;// 拨号、通话记录
//	private TextView tvCall, tvHistory;
    private CheckBox btnPan;// 控制拨号键盘
    private RelativeLayout hiddenOr ;
    private Boolean key = true;// 地址有数字时键盘只是退回；
//	private View vCall, vHistory;

    private String number, wifi, g3g4, name;

    private ViewPager viewpager;

    private DialerViewAdapter viewAdapter;

    private LinearLayout viewGroup;

    private ImageView dot, dots[];

    private Runnable runnable;

    private int autoChangeTime = 3000;

    private SoundPool sp;// 声明一个SoundPool
    private HashMap<Integer, Integer> soundPoolMap;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    boolean plays = false, loaded = false;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private static final String TAG = "T9SearchFragment";
    private T9TelephoneDialpadView mT9TelephoneDialpadView;
    private ContactsOperationView mContactsOperationView;

    private Button mDialpadOperationBtn;
    private boolean mFirstRefreshView = true;


    @Override
    protected void initData() {
        ContactsHelper.getInstance().setOnContactsLoad(this);
        setFirstRefreshView(true);
    }

    public void setFirstRefreshView(boolean firstRefreshView) {
        mFirstRefreshView = firstRefreshView;
    }

    @Override
    public void onContactsLoadSuccess() {
        ContactsHelper.getInstance().parseT9InputSearchContacts(null);
        mContactsOperationView.contactsLoadSuccess();

        ContactsIndexHelper.getInstance().praseContacts(
                ContactsHelper.getInstance().getBaseContacts());
    }

    @Override
    public void onContactsLoadFailed() {
        mContactsOperationView.contactsLoadFailed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void initListener() {
    }

    public void justLogin(final android.content.Context content) {

        WelcomeDialog.Builder builder = new WelcomeDialog.Builder(content);
        builder.setMessage("尊敬的用户,您尚未登录，您可以");
        builder.setTitle("智慧之家物业版提示");
        builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(content,
                        RegistUserVoiceActivity.class);
                startActivity(intent);

            }
        });

        builder.setNegativeButton("登录",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (LinphoneService.instance() != null)
                            LinphoneService.instance().deleteOldAccount();
                        SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
                        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
                        SettingInfo.setParams(
                                PreferenceBean.USERLINPHONEREGISTOK, "");
                        SettingInfo
                                .setParams(PreferenceBean.USERLINPHONEIP, "");
                        SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT,
                                "");
                        SettingInfo.setLinphoneAccount("");
                        SettingInfo.setLinphonePassword("");
                        // SettingInfo.setAccount("");
                        SettingInfo.setPassword("");
                        Intent intent = new Intent(content,
                                LoginAppActivity.class);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // 没有登录转入登录界面
                        startActivity(intent);

                    }
                });

        builder.create().show();

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        Log.e("","====================DialerFragment create=====================");
        instance = this;
        view = inflater.inflate(R.layout.dialer, container, false);

        btnPan = (CheckBox) view.findViewById(R.id.hiddenOrShowNumpad);
        hiddenOr = (RelativeLayout) view.findViewById(R.id.hiddenOr);
        btnPan.setOnClickListener(this);
        hiddenOr.setOnClickListener(this);

        // --------------------------------------------------------------------------------

        mT9TelephoneDialpadView = (T9TelephoneDialpadView) view
                .findViewById(R.id.t9_telephone_dialpad_layout);
        mContactsOperationView = (ContactsOperationView) view
                .findViewById(R.id.contacts_operation_layout);
        boolean startLoad = ContactsHelper.getInstance().startLoadContacts();
        if (true == startLoad) {
            mContactsOperationView.contactsLoading();
        }

//        textView_title = (TextView) view.findViewById(R.id.textView_title);
//        textView_title.setText("拨号");
        textView_title2 = (TextView) view.findViewById(R.id.textView_title2);
        String loginFlag = SettingInfo.getParams(PreferenceBean.LOGINFLAG, "");
        titleListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                justLogin(getActivity());
            }
        };
        if (loginFlag.equals("true")) {
            textView_title2.setText("(已登录)");
        } else {
            textView_title2.setText("(请登录)");
            textView_title2.setOnClickListener(titleListener);
        }

        // SettingInfo.setParams(PreferenceBean.LOGINFLAG, "true");
        mAddress = (AddressText) view.findViewById(R.id.Address);
        mAddress.setDialerFragment(this);
        initCallRecord();
        EraseButton erase = (EraseButton) view.findViewById(R.id.Erase);
        erase.setAddressWidget(mAddress);

        mCall = (CallButton) view.findViewById(R.id.Call);
        mCall.setAddressWidget(mAddress);
        if (LinphoneActivity.isInstanciated()
                && LinphoneManager.getLc().getCallsNb() > 0) {
            if (isCallTransferOngoing) {
                mCall.setImageResource(R.drawable.transfer_call);
            } else {
                mCall.setImageResource(R.drawable.add_call);
            }
        } else {
            mCall.setImageResource(R.drawable.call);
        }

        audioManager = (AudioManager) this.getActivity().getSystemService(this.getActivity().AUDIO_SERVICE);
        audioManager.setMode(AudioManager.STREAM_SYSTEM);

        actVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_SYSTEM);
        maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        volume = actVolume / maxVolume;
        sp = new SoundPool(5, AudioManager.STREAM_SYSTEM, 100);
        sp.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1, sp.load(this.getActivity(), R.raw.k_1, 1));
        soundPoolMap.put(2, sp.load(this.getActivity(), R.raw.k_2, 1));
        soundPoolMap.put(3, sp.load(this.getActivity(), R.raw.k_3, 1));
        soundPoolMap.put(4, sp.load(this.getActivity(), R.raw.k_4, 1));
        soundPoolMap.put(5, sp.load(this.getActivity(), R.raw.k_5, 1));
        soundPoolMap.put(6, sp.load(this.getActivity(), R.raw.k_6, 1));
        soundPoolMap.put(7, sp.load(this.getActivity(), R.raw.k_7, 1));
        soundPoolMap.put(8, sp.load(this.getActivity(), R.raw.k_8, 1));
        soundPoolMap.put(9, sp.load(this.getActivity(), R.raw.k_9, 1));
        soundPoolMap.put(10, sp.load(this.getActivity(), R.raw.k_p, 1));
        soundPoolMap.put(11, sp.load(this.getActivity(), R.raw.k_0, 1));
        soundPoolMap.put(12, sp.load(this.getActivity(), R.raw.k_s, 1));

        AddressAware numpad = (AddressAware) view.findViewById(R.id.Dialer);
        if (numpad != null) {
            numpad.setAddressWidget(mAddress);
            numpad.setTouchToneParam(sp, soundPoolMap, actVolume);
        }

        mAddContact = (ImageView) view.findViewById(R.id.addContact);

        addContactListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneActivity.instance().displayContactsForEdition(
                        mAddress.getText().toString());
            }
        };
        cancelListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneActivity.instance()
                        .resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            }
        };
        transferListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                LinphoneCore lc = LinphoneManager.getLc();
                if (lc.getCurrentCall() == null) {
                    return;
                }
                lc.transferCall(lc.getCurrentCall(), mAddress.getText()
                        .toString());
                isCallTransferOngoing = false;
                LinphoneActivity.instance()
                        .resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
            }
        };

        mAddContact.setEnabled(!(LinphoneActivity.isInstanciated() && LinphoneManager
                .getLc().getCallsNb() > 0));
        resetLayout(isCallTransferOngoing);

        if (getArguments() != null) {
            shouldEmptyAddressField = false;
            String number = getArguments().getString("SipUri");
            String displayName = getArguments().getString("DisplayName");
            String photo = getArguments().getString("PhotoUri");
            mAddress.setText(number);
            if (displayName != null) {
                mAddress.setDisplayedName(displayName);
            }
            if (photo != null) {
                mAddress.setPictureUri(Uri.parse(photo));
            }
        }
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mAddress.setOnClickListener(this);
//        /*门禁电话*/
//        if (!AppValue.MJphone.equals("")){
//            mAddress.setText(AppValue.MJphone);
//        }

        // 监听号码输入框的文字改变
        mAddress.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // Log.e("CallPhone", "CallPhone :" + s);
                if (s.length() > 0) {
                    key = false;
                    mAddress.setBackgroundResource(R.drawable.address_bg_has_tv);// 改变背景

                    cList = cList2;
                    // ((MainTabActivity)
                    // getActivity().getParent()).setDialerPanVisibility(View.VISIBLE);//
                    // 底部拨号键盘的隐藏
                    ((RelativeLayout) view.findViewById(R.id.cpkrl1)).setVisibility(View.VISIBLE);

                    // ((RelativeLayout)
                    // view.findViewById(R.id.LayoutTitle)).setVisibility(View.GONE);//这一句用于控制拨打电话栏的标题
                    changeImg();
                } else {
                    key = true;

                    mAddress.setBackgroundResource(R.drawable.address_bg_no_tv);// 改变背景


                    cList = YETApplication.getinstant().getThjl();
                    // ((MainTabActivity)
                    // getActivity().getParent()).setDialerPanVisibility(View.GONE);
                    // ((RelativeLayout)
                    // view.findViewById(R.id.cpkrl1)).setVisibility(View.GONE);
//                    view.findViewById(R.id.LayoutTitle).setVisibility(View.VISIBLE);

                    changeImg();
                }

                handler.post(run);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
        IntentFilter filter = new IntentFilter(Smack.action);
        getActivity().registerReceiver(receiver, filter);
        advImage = (FrameLayout) view.findViewById(R.id.imageViewAd);
        advImage.setVisibility(View.GONE);
        changeImg();
        wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
        g3g4 = SettingInfo.getParams(PreferenceBean.G3G4CHECK, "");
        getImage();
//        getNotice();
        return view;
    }

    /**
     * 处理按返回键后清空输入框的号码
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mAddress != null)
            mAddress.setText("");
    }

    /**
     * 获取公告
     */
    public void getNotice() {
        Log.e("", "getNotice ");
        new NetAction().notice(new OnMyResponseListener() {
            @Override
            public void onResponse(String jsonObject) {
                Log.e("", "getNotice  ====>>" + jsonObject);
                // TODO Auto-generated method stub
                if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                    NoticeBean bean = JSONObject.parseObject(
                            jsonObject.toString(), NoticeBean.class);
                    if (CommReply.SUCCESS.equals(bean.getStatuscode())) {
                        String messageresultid = bean.getId();
                        String savedPushMessageID = SettingInfo
                                .getPushMessageID();
                        if (!savedPushMessageID.equals(messageresultid)) {
                            final DialogPublic dlg = new DialogPublic(
                                    getActivity().getParent(), bean.getTitle(), bean
                                    .getContent(), true);
                            dlg.show();
                            dlg.setCancelable(false);
                            dlg.setCanceledOnTouchOutside(false);
                            dlg.setOKBtn("确定", new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated
                                    // method
                                    // stub
                                    dlg.dismiss();
                                    handler.sendEmptyMessage(1);
                                }
                            });

                        } else {
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                    SettingInfo.setPushMessageID(bean.getId());
                } else {
                    handler.sendEmptyMessage(1);
                }
            }
        }).execm();
    }

    private String path = "http://web.123667.com/yecall/appfile/yecall_v1.0.apk";
    private String version = "";

    // private ProgressDialog dialog;
    public void checkAppUpdate() {
        Log.e("", "获取更新版本");
        // dialog=ProgressDialog.show(getActivity(), "", "请稍候..");
        // dialog.show();
        new NetAction().checkVersion(
                SettingInfo.getParams(PreferenceBean.APPVERSIONS,
                        getString(R.string.app_version)),
                getString(R.string.app_class), new OnMyResponseListener() {
                    @Override
                    public void onResponse(String jsonObject) {
                        if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
                            JSONObject parseObject = JSONObject
                                    .parseObject(jsonObject);
                            if (CommReply.SUCCESS.equals(parseObject
                                    .getString("statuscode"))) {
                                handler.sendEmptyMessage(2);
                                path = parseObject.getString("url");
                                version = parseObject.getString("newversion");
                            } else {
                                // T.show(getActivity(),
                                // ""+parseObject.getString("reason"), 0);
                                handler.sendEmptyMessage(3);
                            }
                        } else {
                            // T.show(getActivity(),
                            // getString(R.string.service_error), 0);
                            handler.sendEmptyMessage(3);
                        }
                    }
                }).execm();
    }

    private List<String> deleteList;

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
                        String[] advInfo = bean.getAdvInfo();
                        List<String> list = new ArrayList<String>();
                        List<String> oldList = new ArrayList<String>();
                        int params = Integer.parseInt(SettingInfo.getParams(
                                PreferenceBean.ADVERTISEMENTCOUNT, "0")
                                .toString());
                        int count = 0;
                        // 取得所有服务端的图片路径
                        list.addAll(Arrays.asList(advInfo));
                        // 取得本机旧的缓存图片路径
                        for (int i = 0; i < params; i++) {
                            oldList.add(SettingInfo.getParams(
                                    PreferenceBean.ADVERTISEMENT + i, ""));
                        }
                        // 比较两个集合 取交集
                        list.retainAll(oldList);
                        // 得到变动的path路径
                        oldList.removeAll(list);
                        // 判断是否被删除过，删除了就更新此界面
                        boolean delete = false;
                        if (deleteList == null)
                            deleteList = new ArrayList<String>();
                        deleteList.addAll(oldList);
                        if (deleteList.size() >= 0) {
                            delete = true;
                        }

                        // 加载新的图片
                        list = new ArrayList<String>();
                        for (int i = 0; i < advInfo.length; i++) {
                            list.add(advInfo[i]);
                            count++;
                            SettingInfo.setParams(PreferenceBean.ADVERTISEMENT
                                    + i, advInfo[i]);
                        }
                        SettingInfo.setParams(
                                PreferenceBean.ADVERTISEMENTCOUNT, "" + count);
                        // 当第一次进入，或者删除图片，或者第一次初始化就刷新界面
                        if (params <= 0 || delete || viewAdapter == null) {
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
        }).execm();
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

    /**
     * @return null if not ready yet
     */
    public static DialerFragment instance() {
        return instance;
    }

    // 初始化通话记录
    private void initCallRecord() {
        listView = (ListView) view.findViewById(R.id.cpklv1);// 通话记录

        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        cList = new ArrayList<>();
        cList = YETApplication.getinstant().getThjl();
        cList2.addAll(cList);
        cList2.addAll(YETApplication.getinstant().getCltList());
        adapter = new DialingAdapter(getActivity(), cList);
        listView.setAdapter(adapter);

        phoneCallList();

    }

    // 手机通话记录
    private void phoneCallList() {
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				if (mAddress.getText().length() <= 0) {
//					// 通话记录
//					name = cList.get(arg2).getContactName();
//					number = cList.get(arg2).getNumber();
//
//					//对获取到的电话号处理，未接电话可能是sip:12345678900@sip.123667.com格式；
//					String actCallNumber = number;
//					if (actCallNumber.indexOf("sip:") != -1) {
//						actCallNumber = actCallNumber.replaceFirst("sip:", "");
//						if (actCallNumber.indexOf("@") != -1) {
//							actCallNumber = actCallNumber.substring(0,
//									actCallNumber.indexOf("@"));
//						}
//					}
//					number = actCallNumber.trim();
//
//				} else {
//					// 通话记录与通讯录
//					name = cList.get(arg2).getContactName();
//					number = cList.get(arg2).getNumber();
//				}
//
//				chooseCall(name);
//
//			}
//		});

    }

    @Override
    public void onResume() {
        super.onResume();

        if (LinphoneActivity.isInstanciated()) {
            LinphoneActivity.instance().selectMenu(FragmentsAvailable.DIALER);
            LinphoneActivity.instance().updateDialerFragment(this);
            // 会让标题距离40dp
            // LinphoneActivity.instance().showStatusBar();
        }

        if (shouldEmptyAddressField) {
            mAddress.setText("");
        } else {
            shouldEmptyAddressField = true;
        }
        resetLayout(isCallTransferOngoing);

    }


    @Override
    public void onStart() {
        super.onStart();

        wifi = SettingInfo.getParams(PreferenceBean.WIFICHECK, "");
        g3g4 = SettingInfo.getParams(PreferenceBean.G3G4CHECK, "");

        if (wifi.equals("true")) {
            if (!audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(true);
            }
        } else {
            if (audioManager.isSpeakerphoneOn()) {
                audioManager.setSpeakerphoneOn(false);
            }
        }

        String params = SettingInfo.getParams(PreferenceBean.CALLEDREFRESH, "");
        if (!"".equals(BaseUntil.stringNoNull(params))) {
            cList = YETApplication.getinstant().getThjl();
            adapter.updateListView(cList);
        }
        SettingInfo.setParams(PreferenceBean.CALLEDREFRESH, "");

    }

    public void resetLayout(boolean callTransfer) {
        isCallTransferOngoing = callTransfer;
        LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
        if (lc == null) {
            return;
        }

        if (lc.getCallsNb() > 0) {
            if (isCallTransferOngoing) {
                mCall.setImageResource(R.drawable.transfer_call);
                mCall.setExternalClickListener(transferListener);
            } else {
                mCall.setImageResource(R.drawable.add_call);
                mCall.resetClickListener();
            }
            mAddContact.setEnabled(true);
            mAddContact.setImageResource(R.drawable.cancel);
            mAddContact.setOnClickListener(cancelListener);
        } else {
            mCall.setImageResource(R.drawable.call);
            mAddContact.setEnabled(true);
            mAddContact.setImageResource(R.drawable.add_contact);
            mAddContact.setOnClickListener(addContactListener);
            enableDisableAddContact();
        }
    }

    public void enableDisableAddContact() {
        mAddContact.setEnabled(LinphoneManager.getLc().getCallsNb() > 0
                || !mAddress.getText().toString().equals(""));
    }

    public void displayTextInAddressBar(String numberOrSipAddress) {
        shouldEmptyAddressField = false;
        mAddress.setText(numberOrSipAddress);
    }

    public void newOutgoingCall(String numberOrSipAddress) {
        displayTextInAddressBar(numberOrSipAddress);
        LinphoneManager.getInstance().newOutgoingCall(mAddress);
    }

    public void newOutgoingCall(Intent intent) {
        if (intent != null && intent.getData() != null) {
            String scheme = intent.getData().getScheme();
            if (scheme.startsWith("imto")) {
                mAddress.setText("sip:" + intent.getData().getLastPathSegment());
            } else if (scheme.startsWith("call") || scheme.startsWith("sip")) {
                mAddress.setText(intent.getData().getSchemeSpecificPart());
            } else {
                Log.e("Unknown scheme: ", scheme);
                mAddress.setText(intent.getData().getSchemeSpecificPart());
            }

            mAddress.clearDisplayedName();
            intent.setData(null);

            LinphoneManager.getInstance().newOutgoingCall(mAddress);
        }
    }

    int flag = 0;
    String search = "";
    String string = "";
    private Runnable run = new Runnable() {
        public void run() {
            flag++;
            if (flag >= 0) {
                handler.removeCallbacks(run);
            }
            if (search.equals(mAddress.getText().toString())) {
                flag = 0;
                search = "";
                handler.removeCallbacks(run);
                searchList(mAddress.getText().toString(), string);

                string = "";
            } else {
                handler.postDelayed(run, 300);
                search = mAddress.getText().toString();
            }
        }
    };

    private static int lastest = 0;

    // 实现线程同步搜索联系人
    // 线程同步问题可能会导致数据显示错误，比如会显示上一次搜索到的数据
    public void searchList(final String number, final String name) {
        List<Contacts> contactBases = new ArrayList<Contacts>();
        // cList.clear();
        if (number.length() == 0) {
            cList = YETApplication.getinstant().getThjl();
        } else {
            ContactsHelper.getInstance().parseT9InputSearchContacts(number);
            cList = ContactsHelper.getInstance().getSearchContacts();
        }
        adapter.updateListView(cList);
    }

    /**
     * 刷新列表和确认按钮
     **/
    public void refreshList(List<Contacts> filledData) {
        adapter.updateListView(filledData);
        cList = filledData;
    }

    // 打电话
    public void callOut() {
        name = "";
        SettingInfo.setParams(PreferenceBean.CALLNAME, "");
        SettingInfo.setParams(PreferenceBean.CALLPHONE, "");
        SettingInfo.setParams(PreferenceBean.CALLPOSITION, "");
        // 区号
        for (int i = 0; i < cList2.size(); i++) {
            if (cList2.get(i).getNumber().equals(mAddress.getText().toString())) {
                name = cList2.get(i).getContactName();
                break;
            }
        }
        if (mAddress.getText().toString().length() > 11) {
            SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
        } else {
            SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
        }
        number = mAddress.getText().toString();
        // 取决号码显不显示
        SettingInfo.setParams(PreferenceBean.ADDCONTACTSUCCESSBACK, "");
        chooseCall(name);
    }

    /**
     * 设置区号
     *
     * @return
     */
    public String setCallCode() {
        String params = SettingInfo.getParams(PreferenceBean.CALLPROYX, "");
        // if(number.length()<10){
        // if("".equals(params)){
        // DialogNativeCade dialog=new DialogNativeCade(getActivity(), "确认",
        // "取消");
        // dialog.show();
        // return "";
        // }
        // if (number.length() > params.length()) {
        // String subZone = number.substring(0, params.length());
        // if (params.compareTo(subZone) != 0) {
        // // 电话号码中已经带了区号
        // number = params + number;
        // }
        // } else {
        // number = params + number;
        // }
        // }
        return number;
    }

    /*版本更新弹框*/
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 000000:
                    adapter.updateListView((List<Contacts>) msg.obj);
                    break;
                case 1:
                    int networkState = NetUtil.getNetworkState(getActivity());
                    if (networkState == 1)
//                        checkAppUpdate();
                        break;
                case 2:
                    // if(dialog!=null) dialog.dismiss();
                    String vs = SettingInfo.getParams(PreferenceBean.APPVERSIONS,
                            getString(R.string.app_version));

                    android.util.Log.w("bug", "vs = " + vs);
                    android.util.Log.w("bug", "version = " + version);

                    if (!(format(vs) >= format(version))) {// 最新版本不提示升级
                        final DialogPublic dlg = new DialogPublic(getActivity().getParent(),
                                "版本升级", version, true);
                        dlg.show();
                        dlg.setOKBtn("下载", new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SettingInfo.setParams(PreferenceBean.APPVERSIONS, version);
                                Uri uri = Uri.parse("http://web.123667.com/yecall/appfile/yecall_v1.0.apk");
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.setData(uri);
                                startActivity(intent);
                                dlg.dismiss();
                            }
                        });
                    }
                    break;
                case 3:
                    // if(dialog!=null) dialog.dismiss();
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 去除字符串中的符号
     *
     * @param str 字符串
     * @return 返回格式化的字符串
     */
    public static int format(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("").replaceAll("V", "").replaceAll("\\.", "");
        }
        return Integer.parseInt(dest);
    }

    /**
     * 刷新界面
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("missCalled".equals(intent.getStringExtra("missCalled"))
                    || "addContactSuccess".equals(intent
                    .getStringExtra("addContactSuccess"))) {
                cList = YETApplication.getinstant().getThjl();
                cList2.clear();
                cList2.addAll(cList);
                cList2.addAll(YETApplication.getinstant().getCltList());
                adapter.updateListView(cList);
                Log.e("", "通话记录刷新完成");
                String back = SettingInfo.getParams(
                        PreferenceBean.ADDCONTACTSUCCESSBACK, "");
                if (!"".equals(back)) {
                    mAddress.setText(back);
                    SettingInfo.setParams(PreferenceBean.ADDCONTACTSUCCESSBACK,
                            "");
                }
            }
        }
    };

    /**
     * 获取拨号盘的隐藏状态
     *
     * @return
     */
    public boolean getHideStatus() {
        boolean bool = false;
        if (view.findViewById(R.id.layoutDialer).getVisibility() == View.VISIBLE) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * 设置拨号盘的隐藏状态并返回是否隐藏
     *
     * @return
     */
    public boolean setDialerNumberPanVisiable() {
        boolean bool = false;
        if (view.findViewById(R.id.layoutDialer).getVisibility() == View.VISIBLE) {
            view.findViewById(R.id.layoutDialer).setVisibility(View.GONE);
            changeImg();
            bool = true;
        } else {
            view.findViewById(R.id.layoutDialer).setVisibility(View.VISIBLE);
            changeImg();
            bool = false;
        }
        return bool;
    }

    /**
     * 设置拨号盘的隐藏状态
     *
     * @param show
     */
    public void setDialerNumberPanVisiable(Boolean show) {
        if (show) {
            view.findViewById(R.id.layoutDialer).setVisibility(View.VISIBLE);
            changeImg();
        } else {
            view.findViewById(R.id.layoutDialer).setVisibility(View.GONE);
            changeImg();
        }
    }

    /**
     * 设置隐藏
     */
    public void changeImg() {
        if (!"".equals(mAddress.getText().toString())) {
            advImage.setVisibility(View.GONE);
            view.findViewById(R.id.cpklv1).setVisibility(View.VISIBLE);
//            textView_title.setText("拨号");
        } else if (view.findViewById(R.id.layoutDialer).getVisibility() == View.VISIBLE
                && "".equals(mAddress.getText().toString())) {
            advImage.setVisibility(View.VISIBLE);
            view.findViewById(R.id.cpklv1).setVisibility(View.GONE);
//            textView_title.setText("拨号");
        } else {
            advImage.setVisibility(View.VISIBLE);
            view.findViewById(R.id.layoutDialer).setVisibility(View.VISIBLE);
            view.findViewById(R.id.cpklv1).setVisibility(View.GONE);
//            textView_title.setText("拨号");
        }
    }

    public String getInputNumber() {
        if (!"".equals(mAddress.getText().toString())) {
            SettingInfo.setParams(PreferenceBean.ADDCONTACTSUCCESSBACK,
                    mAddress.getText().toString());
            number = mAddress.getText().toString();
            return number;
        }
        return "";
    }

    public void clearAddress() {
        mAddress.setText("");
    }

    // 选择拨打方式
    public void chooseCall(String accountName) {
        String phoneNum = setCallCode();

        if ("".equals(phoneNum)) {
            return;
        }

        number = phoneNum;


        int networkState = NetUtil.getNetworkState(getActivity());
        if (networkState == 0) {// 无连接
            T.show(getActivity(), "请检查网络连接！", 0);
            return;
        } else if (networkState == 1) {// wifi
            if ("".equals(wifi)) {
                calling(accountName);
            } else {
                calling(accountName);
            }
        } else if (networkState == 2) {// 手机
            if ("".equals(g3g4)) {
                calling(accountName);
            } else {
                calling(accountName);
            }
        }
        mAddress.setText("");
        name = "";
    }

    public void calling(String name) {
        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            justLogin(getActivity().getParent());
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            SettingInfo.setParams(PreferenceBean.CALLNAME, name);
            SettingInfo.setParams(PreferenceBean.CALLPHONE, number);
            if (number.length() <= 11) {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "私人号码");
            } else {
                SettingInfo.setParams(PreferenceBean.CALLPOSITION, "企业号");
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getActivity(),
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    startActivity(intent);
                }
            }).start();

            AddressType address = new AddressText(getActivity(), null);
            address.setDisplayedName(name);
            address.setText(number);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
    }

    private void initViewPager(List<String> list, List<String> delete) {
        if (viewAdapter == null) {
            viewAdapter = new DialerViewAdapter(getActivity());
        }
        viewAdapter.change(list, deleteList);
        if (viewpager == null) {
            viewpager = (ViewPager) view.findViewById(R.id.viewpager);
            viewpager.setAdapter(viewAdapter);
            viewpager.setOnPageChangeListener(myOnPageChangeListener);
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


    // 初始化dot视图
    private void initDot() {
        viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        viewGroup.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                40, 40);
        layoutParams.setMargins(4, 3, 4, 3);
        Log.e("", "dot长度：" + viewAdapter.getCount());
        dots = new ImageView[viewAdapter.getCount()];
        for (int i = 0; i < viewAdapter.getCount(); i++) {
            dot = new ImageView(getActivity());

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("",
                "====================DialerFragment finish=====================");
    }

    // 控件监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hiddenOr:// 键盘的隐藏或收起
                ycjp();
                break;
            case R.id.hiddenOrShowNumpad:
                ycjp();
                break;
            case R.id.Address:
                if (mAddress.getText().toString().length() == 0) {
                    if (getHideStatus()) {
                        view.findViewById(R.id.layoutDialer).setVisibility(View.GONE);
                        btnPan.setChecked(false);
                        showContacts();
                    } else {
                        view.findViewById(R.id.layoutDialer).setVisibility(View.VISIBLE);
                        btnPan.setChecked(true);
                        showContacts();
                    }


                    cList = new ArrayList<>();
                    cList = YETApplication.getinstant().getThjl();
                    cList2.addAll(cList);
                    cList2.addAll(YETApplication.getinstant().getCltList());
                    adapter = new DialingAdapter(getActivity(), cList);
                    listView.setAdapter(adapter);
                    phoneCallList();

                } else {
                    tempShowNumpan();
                    Activity currentActivity = getActivity();
                    if (currentActivity instanceof LinphoneActivity) {
                        ((LinphoneActivity) currentActivity).callOut();
                    }
                }
                break;
        }
    }

    public void ycjp(){
        if (getHideStatus()) {
            view.findViewById(R.id.layoutDialer).setVisibility(View.GONE);
            btnPan.setChecked(false);

            cList = new ArrayList<>();
            cList = YETApplication.getinstant().getThjl();
            cList2.addAll(cList);
            cList2.addAll(YETApplication.getinstant().getCltList());
            adapter = new DialingAdapter(getActivity(), cList);
            listView.setAdapter(adapter);
            phoneCallList();

            showContacts();
        } else {
            view.findViewById(R.id.layoutDialer)
                    .setVisibility(View.VISIBLE);
            btnPan.setChecked(true);
            showContacts();
        }
    };


    // 呈现联系人
    private void showContacts() {

        if (key) {

            if (advImage.getVisibility() == View.VISIBLE) {
                advImage.setVisibility(View.GONE);
                view.findViewById(R.id.cpklv1).setVisibility(View.VISIBLE);
            } else {
                advImage.setVisibility(View.VISIBLE);
                view.findViewById(R.id.cpklv1).setVisibility(View.GONE);
            }
        }

    }

    // 临时用于挂断电话返回主页呈现键盘
    // 函数调用位置，InCallActivity->hangUp()
    public static void tempShowNumpan() {
        view.findViewById(R.id.layoutDialer).setVisibility(View.VISIBLE);
    }

}
