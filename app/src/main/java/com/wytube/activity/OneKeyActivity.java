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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.until.ACache;
import com.cqxb.until.ShakeListener;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.google.gson.reflect.TypeToken;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.YeCallListAdapter;
import com.wytube.adaper.YeCallListAdapters;
import com.wytube.beans.BeasOpen;
import com.wytube.beans.modle.Remotely;
import com.wytube.beans.YeCallBeans;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.shared.ToastUtils;
import com.wytube.threads.GsonUtil;
import com.wytube.utlis.SipCore;
import com.wytube.utlis.Utils;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wytube.beans.modle.Remotely.PERSONS;

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
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;


    private ShakeListener mShaker;
    private PopupWindow popupWindow = new PopupWindow();
    private boolean isOnclick = false;//是否点击
    private int positionNum = 0;//点击数据
    private List<YeCallBeans.DataBean> lists;
    private BeasOpen bean;
    private String phone;// 用户名
    ACache mCache = null;
    List<Remotely> remotely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();

    }

    private void iniview() {
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        mtext_name.setOnClickListener(v -> {finish();});
        mtext_name.setText("一键开门");
        phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        mCache = ACache.get(this);
//        mCache.clear();
        JSONArray result = mCache.getAsJSONArray(PERSONS);
        if (result!=null){
            /*缓存数据*/
            Type mType = new TypeToken<List<Remotely>>(){}.getType();
            remotely = GsonUtil.getGson().fromJson(result.toString(), mType);
            YJlist = getData();
            YeCallListAdapter adapters= new YeCallListAdapter(this,YJlist);
            itemListView.setAdapter(adapters);
            itemListView.setOnItemClickListener((parent, view, position, id) -> {
                isOnclick = true;
                positionNum=position;
                showPopupWindowCount(remotely.get(position).name, remotely.get(position).content);
                yyyOpenDoor();//摇一摇开门
            });
        }else {
            loadAllPark(phone);
        }
    }

    List<Map<String, Object>> YJlist;
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < remotely.size(); i++) {
            Map<String, Object> map= new HashMap<>();
            map.put("name", remotely.get(i).name);
            map.put("content", remotely.get(i).content);
            map.put("doorType", remotely.get(i).doorType);
            map.put("doorId", remotely.get(i).doorId);
            map.put("sip", remotely.get(i).sip);
            map.put("serial", remotely.get(i).serial);
            list.add(map);
        }
        return list;
    }

    /**
     * 本地摇一摇开门
     */
    private void yyyOpenDoor() {
        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mShaker = new ShakeListener(this);
        mShaker.setOnShakeListener(() -> {
            if (isOnclick) {
                String phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
                if (Integer.parseInt(remotely.get(positionNum).doorType)==1111){
                    loadopen(phone,remotely.get(positionNum).doorId);
                    popupWindow.dismiss();
                } else if (Integer.parseInt(remotely.get(positionNum).doorType)==3333){
                    /*发送消息内容*/
                    SipCore.BsendMessage(remotely.get(positionNum).sip,remotely.get(positionNum).serial);
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
            if (bean == null) {
                ToastUtils.showToast(this,"网络异常!");
                return false;
            }
            if (bean.getCode()==200){
                Toast.makeText(OneKeyActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(OneKeyActivity.this, "开门失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }));
    }

    /**
     * 获取远程开门数据
     */
    private void loadAllPark(String phone) {
        Utils.showLoad(this);
        Client.sendPost(NetParam.USR_DRIVERS, "phone="+phone +"&type=1111", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            YeCallBeans bean = Json.toObject(json, YeCallBeans.class);
            if (bean == null) {
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                ToastUtils.showToast(this,"服务器异常!请稍后再试!");
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            lists = bean.getData();
            /*缓存*/
            ACache mCache = ACache.get(this);
            remotely = new ArrayList<Remotely>();
            for (int i = 0; i < lists.size() ; i++) {
                remotely.add(new Remotely(lists.get(i).getCommunityName()!=null?lists.get(i).getCommunityName():"null",
                        lists.get(i).getDoorName()!=null?lists.get(i).getDoorName():"null",
                        lists.get(i).getDoorType()!=null?lists.get(i).getDoorType():"null",
                        lists.get(i).getDoorId()!=null?lists.get(i).getDoorId():"null",
                        lists.get(i).getSip()!=null?lists.get(i).getSip():"null",
                        lists.get(i).getSerial()!=null?lists.get(i).getSerial():"null"));
            }
            String personArray = GsonUtil.getGson().toJson(remotely);
            mCache.put(PERSONS, personArray, 60*60*24*14);

            if (!lists.equals("[]")){
                YeCallListAdapters yeCallAdapter = new YeCallListAdapters(OneKeyActivity.this, lists);
                itemListView.setAdapter(yeCallAdapter);
                itemListView.setVisibility(View.VISIBLE);
                if (lists.size()==0){
                    mshaxin.setVisibility(View.VISIBLE);
                }else {
                    mshaxin.setVisibility(View.GONE);
                }
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
