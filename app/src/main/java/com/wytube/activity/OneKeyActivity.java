package com.wytube.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.until.ShakeListener;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.YeCallListAdapters;
import com.wytube.beans.BeasOpen;
import com.wytube.beans.YeCallBeans;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.utlis.SipCore;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/2.
 * 类 描 述: 一键开门
 */

@KActivity(R.layout.activity_onekey)
public class OneKeyActivity extends Activity {

    @KBind(R.id.text_name)
    private TextView mtext_name;
    @KBind(R.id.yecall_listview_item)
    private ListView itemListView;

    private ShakeListener mShaker;
    private PopupWindow popupWindow = new PopupWindow();
    private boolean isOnclick = false;//是否点击
    private int positionNum = 0;//点击数据
    private List<YeCallBeans.DataBean> lists;
    private BeasOpen bean;
    private String phone;// 用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    private void iniview() {
        mtext_name.setText("一键开门");
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        mtext_name.setOnClickListener(v -> {finish();});
        phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        yyyOpenDoor();//摇一摇开门
        initYeCall();//一键开门
    }
    /**
     * 摇一摇开门
     */
    private void yyyOpenDoor() {
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(() -> {
            if (isOnclick) {
                String phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
                if (lists.get(positionNum).getDoorType()==1111){
                    loadopen(phone,lists.get(positionNum).getDoorId());
                    popupWindow.dismiss();
                } else if (lists.get(positionNum).getDoorType()==3333){
                    /*发送消息内容*/
                    SipCore.sendMessage(lists.get(positionNum));
                    Toast.makeText(OneKeyActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 获取远程开门数据
     */
    private void loadopen(String phone,String door) {
        Client.sendPost(NetParam.USR_OPEN, "door="+door+"&phone="+phone, new Handler(msg -> {
            String json = msg.getData().getString("post");
            bean = Json.toObject(json, BeasOpen.class);
            assert bean != null;
            if (bean.getCode()==200){
                Toast.makeText(OneKeyActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(OneKeyActivity.this, "开门失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }));
    }

    /**
     * 初始化门禁相关
     */
    private void initYeCall() {
        Utils.showLoad(this);
        loadAllPark(phone,"1111");
    }

    /**
     * 获取远程开门数据
     */
    private void loadAllPark(String phone,String type) {
        Client.sendPost(NetParam.USR_DRIVERS, "phone="+phone +"&type="+type, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            YeCallBeans bean = Json.toObject(json, YeCallBeans.class);
            lists = bean.getData();
            if (!lists.equals("[]")){
                YeCallListAdapters yeCallAdapter = new YeCallListAdapters(OneKeyActivity.this, lists);
                itemListView.setAdapter(yeCallAdapter);
                itemListView.setVisibility(View.VISIBLE);
            }else {
                itemListView.setVisibility(View.GONE);
            }
            itemListView.setOnItemClickListener((parent, view, position, id) -> {
                isOnclick = true;
                positionNum = position;
                String community = lists.get(positionNum).getCommunityName();
                String equipment = lists.get(positionNum).getDoorName();
                showPopupWindowCount(community, equipment);
            });
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            return false;
        }));
    }

    /**
     * 自定义pop
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
        popupWindow.showAtLocation(mtext_name, Gravity.CENTER_VERTICAL, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOnclick = false;
            }
        });
    }

}
