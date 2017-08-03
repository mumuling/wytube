package com.cqxb.yecall;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.until.ACache;
import com.cqxb.until.AsyncHttpClientManager;
import com.cqxb.until.CheckUtils;
import com.cqxb.until.Config;
import com.cqxb.until.ShakeListener;
import com.cqxb.yecall.adapter.YeCallListAdapter;
import com.cqxb.yecall.adapter.YeCallListPasswordAdapter;
import com.cqxb.yecall.bean.YeCallBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.linphone.DialerFragment;
import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.LinphoneService;
import org.linphone.ui.AddressText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationActivity extends BaseTitleActivity implements View.OnClickListener {

    private ListView itemListView;
    private YeCallListAdapter yeCallAdapter;// 门禁列表适配器
    private YeCallListPasswordAdapter passwordAdapter;// 密码开门适配器
    private String user;// 用户名
    //	private List<String> clientUrList;// 门对应的服务器地址
    private JSONArray mJsonArray;
    private int successKey = 0;// 开门是否成功的标识
    private View line1, line2, line3;// 文字对应的标识线
    private LinearLayout llBtn1, llBtn2, llBtn3;// 文字对应点击事件按钮
    private TextView tv1, tv2, tv3;// 文字
    private ProgressDialog progressDlg;
    private ImageView imgRetry;// 点击图片重试
    private ImageView imgNoEquipment;// 没有设备
    private FrameLayout flNotLogin;// 没有登录
    private Button btnLogin;// 登录按钮
    private List<YeCallBean> list = new ArrayList<>();
    private EditText ediphonenum;
    private Button btnConfirm;
    private String diss = "";
    private Boolean listKey = true;// 网络或请求失败的时候判断刷新哪一个list

    private ShakeListener mShaker;
    private PopupWindow popupWindow = new PopupWindow();
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    private boolean isOnclick = false;//是否点击
    private int positionNum = 0;//点击数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        diss = BaseUntil.stringNoNull(getIntent().getStringExtra("diss"));

        setContentView(R.layout.activity_application);

        findViewById(R.id.experience_oppen_door).setOnClickListener(this);
        findViewById(R.id.experience_oppen_door1).setOnClickListener(this);

        setTitle("门禁");
        setZxing();//扫一扫;
        yyyOpenDoor();//摇一摇开门
        SettingInfo.init(this);

        initView();// 初始化控件

        initListener();// 注册监听

        String params = SettingInfo.getParams(PreferenceBean.CALLPROYX, "");
        if (!"".equals(params)) {
            ediphonenum.setText("" + params);
        }

        user = SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");

        if (user.equals("")) {
            flNotLogin.setVisibility(View.VISIBLE);
        } else {
            flNotLogin.setVisibility(View.GONE);

            if (CheckUtils.checkNet(this)) {// 检查手机网络
                initYeCall();
            } else {
                itemListView.setVisibility(View.GONE);
                imgRetry.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 摇一摇开门
     */
    private void yyyOpenDoor() {
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                if (isOnclick) {
                    successKey = 0;
                    JSONObject obj;
                    try {
                        obj = mJsonArray.getJSONObject(positionNum);
                        String doorId = obj.getString("doorId");
                        String openDoorUrl = obj.getString("destinationIp");

//                            String url = "http://"+ openDoorUrl+ "/door/api/opendoor?phone="+ user +"&doorId="+ doorId;
                        String url = NetParam.USR_OPEN;
//                        String url = "http://" + openDoorUrl + "/door/api/opendoor";

                        RequestParams requestParams = new RequestParams();
                        requestParams.put("phone", user);
                        requestParams.put("door", doorId);

                        for (int i = 0; i < 3; i++) {
                            AsyncHttpClientManager.post(url, requestParams, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    successKey++;
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                }

                                @Override
                                public void onFinish() {
                                    if (successKey > 0) {
                                        popupWindow.dismiss();
                                        Toast.makeText(ApplicationActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
                                        successKey = -10;
                                    }
                                    super.onFinish();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        mShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mShaker.pause();
        super.onPause();
    }

    private void setZxing() {
        findViewById(R.id.title_custom_bg).setVisibility(View.VISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.title_custom_id);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.sys));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), CustomZxingActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Zxing(result);
                    //Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 扫一扫开门
     *
     * @param string
     */
    private void Zxing(String string) {
        try {
//		String doorId = obj.getString("doorId");
//		String openDoorUrl = obj.getString("destinationIp");

//      String url = "http://"+ openDoorUrl+ "/door/api/opendoor?phone="+ user +"&doorId="+ doorId;
            String url = NetParam.USR_CODE;

            RequestParams requestParams = new RequestParams();
            requestParams.put("phone", user);
            requestParams.put("seria", string.split("//")[1].split("\\.")[0]);

            for (int i = 0; i < 3; i++) {
                AsyncHttpClientManager.post(url, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        successKey++;
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }

                    @Override
                    public void onFinish() {
                        if (successKey > 0) {
                            Toast.makeText(ApplicationActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
                            successKey = -10;
                        }
                        super.onFinish();
                    }
                });
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        line1 = findViewById(R.id.yuancheng_line);
        line2 = findViewById(R.id.mima_line);
        line3 = findViewById(R.id.tiyan_line);

        tv1 = (TextView) findViewById(R.id.tv_yuancheng);
        tv2 = (TextView) findViewById(R.id.tv_mima);
        tv3 = (TextView) findViewById(R.id.tv_tiyan);

        llBtn1 = (LinearLayout) findViewById(R.id.ll_yuancheng_btn);
        llBtn2 = (LinearLayout) findViewById(R.id.ll_mima_btn);
        llBtn3 = (LinearLayout) findViewById(R.id.ll_tiyan_btn);

        imgRetry = (ImageView) findViewById(R.id.no_net_retry);
        imgNoEquipment = (ImageView) findViewById(R.id.no_equipment);

        itemListView = (ListView) findViewById(R.id.yecall_listview_item);

        flNotLogin = (FrameLayout) findViewById(R.id.fl_not_have_user);

        btnLogin = (Button) findViewById(R.id.btn_login);

        ediphonenum = (EditText) findViewById(R.id.ediphonenum);

        btnConfirm = (Button) findViewById(R.id.yecall_confirm_btn);

    }

    /**
     * 注册监听
     */
    private void initListener() {
        llBtn1.setOnClickListener(this);
        llBtn2.setOnClickListener(this);
        llBtn3.setOnClickListener(this);

        btnLogin.setOnClickListener(this);

        imgRetry.setOnClickListener(this);

        btnConfirm.setOnClickListener(this);

        ediphonenum.setOnClickListener(this);
    }

    /**
     * 初始化门禁相关
     */
    private void initYeCall() {
        progressDlg = ProgressDialog.show(ApplicationActivity.this, null, "请等待...", true, true);
        progressDlg.show();

        list.clear();

//		String url = "http://115.28.3.3:80/door/api/mydoors";
//        String url = "http://115.28.2.168:80/door/api/doors";

        RequestParams params = new RequestParams();
        params.put("phone", user);
        params.put("type", "1111");

//        AsyncHttpClientManager.post(NetParam.USR_DRIVERS, params, new JsonHttpResponseHandler() {
        AsyncHttpClientManager.post(NetParam.USR_DRIVERS, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {
                    try {
                        String code = response.getString("code");//获取状态码
                        if (code.equals("200")) { //状态码是000000说明请求成功

//                            String doors = response.getJSONObject("data").getJSONArray("doors").toString();// 获取设备
                            String doors = response.getJSONArray("data").toString();// 获取设备
                            if (!doors.equals("[]")) { // 设备不为空才添加设备
                                mJsonArray = response.getJSONArray("data");
//								clientUrList = new ArrayList<>();

                                for (int i = 0; i < mJsonArray.length(); i++) {

                                    JSONObject obj = mJsonArray.getJSONObject(i);

//									clientUrList.add(obj.getString("doorId"));

                                    YeCallBean bean = new YeCallBean(obj.getString("communityName")
                                            , obj.getString("doorName")
                                            , obj.getString("doorId"));
                                    list.add(bean);
                                }

                                yeCallAdapter = new YeCallListAdapter(ApplicationActivity.this, list);

                                itemListView.setAdapter(yeCallAdapter);

                                itemListView.setVisibility(View.VISIBLE);
                                imgRetry.setVisibility(View.GONE);

                            } else {// 呈现没有设备图片
                                itemListView.setVisibility(View.GONE);
                                imgRetry.setVisibility(View.GONE);
                                imgNoEquipment.setVisibility(View.VISIBLE);
                            }
                        } else {// 呈现没有设备图片
                            itemListView.setVisibility(View.GONE);
                            imgRetry.setVisibility(View.GONE);
                            imgNoEquipment.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(ApplicationActivity.this, "请检查您的网络！点击重新加载", Toast.LENGTH_SHORT).show();
                itemListView.setVisibility(View.GONE);
                imgRetry.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDlg.dismiss();
            }
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isOnclick = true;
                positionNum = position;
                JSONObject obj = null;
                try {
                    obj = mJsonArray.getJSONObject(position);
                    String community = obj.getString("communityName");
                    String equipment = obj.getString("doorName");
                    showPopupWindowCount(community, equipment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 物品数量
     *
     * @param
     */
    private void showPopupWindowCount(String community, String equipment) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.yyy, null);
        TextView tvName = (TextView) contentView.findViewById(R.id.yyy_tv_name);
        TextView tvAddress = (TextView) contentView.findViewById(R.id.yyy_tv_address);
        ImageView ivDel = (ImageView) contentView.findViewById(R.id.yyy_iv_del);
        tvName.setText(community + "");
        tvAddress.setText(equipment + "");
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setContentView(contentView);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);


        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        //popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xdd000000);
        popupWindow.setBackgroundDrawable(dw);
        RelativeLayout fl = (RelativeLayout) findViewById(R.id.LayoutTitle);

        popupWindow.showAtLocation(fl, Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOnclick = false;

            }
        });
    }

    /**
     * 初始化密码开门
     */
    private void initPasswordYeCall() {
        progressDlg = ProgressDialog.show(ApplicationActivity.this, null,
                "请等待...", true, true);
        progressDlg.show();

        list.clear();
//        String url = "http://115.28.2.168:80/door/api/doors";
//        String url = "http://115.28.3.3:80/tdmj/api/myequipments";

        RequestParams requestParams = new RequestParams();
        requestParams.put("phone", user);
        requestParams.put("type", "2222");

        AsyncHttpClientManager.post(NetParam.USR_DRIVERS, requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {
                    try {
                        String code = response.getString("code");//获取状态码
                        if (code.equals("200")) { //状态码是000000说明请求成功

//                            String doors = response.getJSONObject("data").getJSONArray("doors").toString();// 获取设备
                            String doors = response.getJSONArray("data").toString();// 获取设备
                            if (!doors.equals("[]")) { // 设备不为空才添加设备
                                mJsonArray = response.getJSONArray("data");
//								clientUrList = new ArrayList<>();

                                for (int i = 0; i < mJsonArray.length(); i++) {

                                    JSONObject obj = mJsonArray.getJSONObject(i);

//									clientUrList.add(obj.getString("doorId"));

                                    YeCallBean bean = new YeCallBean(obj.getString("communityName")
                                            , obj.getString("doorName")
                                            , obj.getString("doorId"));
                                    list.add(0, bean);
                                }

                                isAnotherDay();

                                passwordAdapter = new YeCallListPasswordAdapter(ApplicationActivity.this, list);

                                itemListView.setAdapter(passwordAdapter);

                                itemListView.setVisibility(View.VISIBLE);
                                imgRetry.setVisibility(View.GONE);
                            } else {// 呈现没有设备图片
                                itemListView.setVisibility(View.GONE);
                                imgRetry.setVisibility(View.GONE);
                                imgNoEquipment.setVisibility(View.VISIBLE);

                            }

                        } else {// 呈现没有设备图片
                            itemListView.setVisibility(View.GONE);
                            imgRetry.setVisibility(View.GONE);
                            imgNoEquipment.setVisibility(View.VISIBLE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(ApplicationActivity.this, "请检查您的网络！点击重新加载",
                        Toast.LENGTH_SHORT).show();

                itemListView.setVisibility(View.GONE);
                imgRetry.setVisibility(View.VISIBLE);
                Log.e("YECALL", "网络请求失败！");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDlg.dismiss();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendBroadcast(new Intent(Smack.action).putExtra("backMune",
                    "backMune"));
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yuancheng_btn:// 远程开门
                imgNoEquipment.setVisibility(View.GONE);
                if (checkPhoneNet()) {

                    listKey = true;

                    tv1.setTextColor(Color.parseColor("#54ABEE"));
                    line1.setBackgroundColor(Color.parseColor("#54ABEE"));
                    tv2.setTextColor(Color.parseColor("#A1A1A1"));
                    line2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tv3.setTextColor(Color.parseColor("#A1A1A1"));
                    line3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    findViewById(R.id.yecall_ll_password_setting).setVisibility(View.GONE);

                    initYeCall();// 门禁相关
                }
                break;
            case R.id.ll_mima_btn:// 密码开门
                imgNoEquipment.setVisibility(View.GONE);

                if (checkPhoneNet()) {
                    listKey = false;

                    tv2.setTextColor(Color.parseColor("#54ABEE"));
                    line2.setBackgroundColor(Color.parseColor("#54ABEE"));
                    tv1.setTextColor(Color.parseColor("#A1A1A1"));
                    line1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tv3.setTextColor(Color.parseColor("#A1A1A1"));
                    line3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    findViewById(R.id.yecall_ll_password_setting).setVisibility(View.GONE);

                    initPasswordYeCall();// 密码开门
                }
                break;
            case R.id.ll_tiyan_btn:// 可视门铃
                imgNoEquipment.setVisibility(View.GONE);

                tv3.setTextColor(Color.parseColor("#54ABEE"));
                line3.setBackgroundColor(Color.parseColor("#54ABEE"));
                tv2.setTextColor(Color.parseColor("#A1A1A1"));
                line2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv1.setTextColor(Color.parseColor("#A1A1A1"));
                line1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                findViewById(R.id.yecall_ll_password_setting).setVisibility(View.VISIBLE);

                imgRetry.setVisibility(View.GONE);
                itemListView.setVisibility(View.GONE);

                break;
            case R.id.no_net_retry:// 无网络点击重新加载
                if (checkPhoneNet()) {
                    if (listKey) {
                        initYeCall();
                    } else {
                        initPasswordYeCall();
                    }
                }
                break;
            case R.id.btn_login:// 未登录时登录
                jumpLogin();
                break;
            case R.id.yecall_confirm_btn:// 设置开门密码
                SettingInfo.setParams(PreferenceBean.CALLPROYX, ediphonenum
                        .getText().toString());
                ediphonenum.setCursorVisible(false);
                T.show(this, "设置成功！", 0);
                break;
            case R.id.ediphonenum:// 显示光标
                ediphonenum.setCursorVisible(true);
                break;
            case R.id.experience_oppen_door:
                Config.experienceCall = true;
                mCalling("2300102");
                break;
            case R.id.experience_oppen_door1:
                Config.experienceCall = true;
                mCalling("2300103");
                break;
        }

    }

    /**
     * 不弹dialog，直接跳入登录界面
     */
    private void jumpLogin() {
        if (LinphoneService.instance() != null)
            LinphoneService.instance().deleteOldAccount();
        SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
        SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
        SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
        SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, "");
        SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, "");
        SettingInfo.setLinphoneAccount("");
        SettingInfo.setLinphonePassword("");
        // SettingInfo.setAccount("");
        SettingInfo.setPassword("");
        Intent intent = new Intent(this, LoginAppActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 没有登录转入登录界面
        startActivity(intent);
    }

    /**
     * 判断是否是同一天，用于密码开门中是否开启密码动画
     */
    private void isAnotherDay() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String str = format.format(date);// 获取的当前时间
        String day = SettingInfo.getParams(SettingInfo.KEY_DATE, "default");// APP储存的时间
        if (!day.equals(str)) {
            SettingInfo.setParams(SettingInfo.KEY_DATE, str);
            for (int i = 0; i < list.size(); i++) {

//				Log.w("bug", "循环也走进来了！！！");

                ACache aCache = ACache.get(ApplicationActivity.this);
                aCache.put("@password" + user + i, "");
            }
        }
    }

    /**
     * 检查是否有手机网络
     */
    private boolean checkPhoneNet() {
        if (CheckUtils.checkNet(this)) {// 检查手机网络
            return true;
        } else {
            Toast.makeText(this, "请检查手机网络", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 调用免费拨打
     *
     * @param name 电话号码
     */
    public void mCalling(String name) {
        String number = "2300102";

        if (SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")) {
            DialerFragment.instance().justLogin(this);
        } else {
            SettingInfo.setParams(PreferenceBean.CALLSTATUS, "拨号");
            // LinphoneActivity.instance().startIncallActivity(null);
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
                    Intent intent = new Intent(ApplicationActivity.this,
                            InCallActivity.class);
                    intent.putExtra("VideoEnabled", false);
                    startActivity(intent);
                }
            }).start();

            AddressType address = new AddressText(this, null);
            address.setDisplayedName(name);
            address.setText(number);
//            LinphoneManager.getLc().enableVideo(true, true);
//            LinphoneManager.getLc().setVideoPolicy(true, true);
            LinphoneManager.getInstance().newOutgoingCall(address);
        }
    }

}
