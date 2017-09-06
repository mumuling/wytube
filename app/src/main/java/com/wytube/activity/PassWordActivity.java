package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.action.param.NetParam;
import com.cqxb.until.ACache;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.google.gson.reflect.TypeToken;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.YeCallListPasswordAdapter;
import com.wytube.adaper.YeCallListPasswordAdapters;
import com.wytube.beans.BaseMmkm;
import com.wytube.beans.modle.Remotelys;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.threads.GsonUtil;
import com.wytube.utlis.Utils;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wytube.beans.modle.Remotelys.PERSONSs;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/2.
 * 类 描 述: 密码开门
 */

@KActivity(R.layout.activity_onekey)
public class PassWordActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{

    @KBind(R.id.text_name)
    private TextView mtext_name;
    @KBind(R.id.yecall_listview_item)
    private ListView itemListView;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;

    private String phone;// 用户名
    private List<BaseMmkm.DataBean> list;
    ACache mCache = null;
    List<Remotelys> remotelys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    private void iniview() {
        mtext_name.setText("密码开门");
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        mtext_name.setOnClickListener(v -> {finish();});
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent);

        phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        mCache = ACache.get(this);
        JSONArray results = mCache.getAsJSONArray(PERSONSs);
        if (results!=null){
            /*缓存数据*/
            Type mType = new TypeToken<List<Remotelys>>(){}.getType();
            remotelys = GsonUtil.getGson().fromJson(results.toString(), mType);
            MMlist = getData();
            YeCallListPasswordAdapter passwordAdapter = new YeCallListPasswordAdapter(PassWordActivity.this, MMlist);
            itemListView.setAdapter(passwordAdapter);
            if (remotelys.size()==0){
                mshaxin.setVisibility(View.VISIBLE);
            }else {
                mshaxin.setVisibility(View.GONE);
            }
        }else {
            loadAllMm(phone);
        }
    }

    List<Map<String, Object>> MMlist;
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < remotelys.size(); i++) {
            Map<String, Object> map= new HashMap<>();
            map.put("name", remotelys.get(i).name);
            map.put("content", remotelys.get(i).content);
            map.put("doorType", remotelys.get(i).doorType);
            map.put("doorId", remotelys.get(i).doorId);
            map.put("sip", remotelys.get(i).sip);
            map.put("serial", remotelys.get(i).serial);
            list.add(map);
        }
        return list;
    }


    /**
     * 初始化密码开门
     */
    private void loadAllMm(String phone) {
        Utils.showLoad(this);
        Client.sendPost(NetParam.USR_DRIVERS, "phone="+phone +"&type=2222", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseMmkm bean = Json.toObject(json, BaseMmkm.class);
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
            list = bean.getData();
            /*缓存*/
            ACache mCache = ACache.get(this);
            remotelys = new ArrayList<Remotelys>();
            for (int i = 0; i < list.size() ; i++) {
                remotelys.add(new Remotelys(list.get(i).getCommunityName()!=null?list.get(i).getCommunityName():"null",
                        list.get(i).getDoorName()!=null?list.get(i).getDoorName():"null",
                        list.get(i).getDoorType()!=null?list.get(i).getDoorType():"null",
                        list.get(i).getDoorId()!=null?list.get(i).getDoorId():"null",
                        list.get(i).getSip()!=null?list.get(i).getSip():"null",
                        list.get(i).getSerial()!=null?list.get(i).getSerial():"null"));
            }
            String personArray = GsonUtil.getGson().toJson(remotelys);
            mCache.put(PERSONSs, personArray, 60*60*24*14);

            if (!list.equals("[]")){
                isAnotherDay();
                YeCallListPasswordAdapters passwordAdapter = new YeCallListPasswordAdapters(PassWordActivity.this, list);
                itemListView.setAdapter(passwordAdapter);
                itemListView.setVisibility(View.VISIBLE);
                if (list.size()==0){
                    mshaxin.setVisibility(View.VISIBLE);
                }else {
                    mshaxin.setVisibility(View.GONE);
                }
            }else {
                itemListView.setVisibility(View.GONE);
            }
            return false;
        }));
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
                ACache aCache = ACache.get(PassWordActivity.this);
                aCache.put("@password" + phone + i, "");
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadAllMm(phone);
                mSwipe_container.setRefreshing(false);
            }
        }, 2000);
    }
}


