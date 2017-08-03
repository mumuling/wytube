package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.ParkBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_location_list)
public class LocationListActivity extends Activity {

    private ImageView backBut;
    private TextView titleText, menuText;
    @KBind(R.id.key_words)
    private EditText mKeyWords;
    @KBind(R.id.sreatch_layout)
    private LinearLayout mSreatchLayout;
    @KBind(R.id.park_list)
    private LinearLayout mParkList;
    private LinearLayout selectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        loadAllPark();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    /**
     * 获取所有停车场信息
     */
    private void loadAllPark() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.GET_ALL_PARKS, "rows=10000", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            ParkBean bean = Json.toObject(json, ParkBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            initList(bean.getData().getParks());
            return false;
        }));
    }

    /**
     * 初始化列表
     */
    private void initList(List<ParkBean.DataBean.ParksBean> beans) {
        mParkList.removeAllViews();
        for (ParkBean.DataBean.ParksBean bean : beans) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_park_list_layout, null);
            ((TextView) view.findViewById(R.id.local_text)).setText(bean.getName());
            if (AppValue.parkName != null && AppValue.parkName.length() > 0) {
                if (bean.getName().equals(AppValue.parkName)) {
                    view.findViewById(R.id.tips_text).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.now_select).setVisibility(View.VISIBLE);
                    selectView = (LinearLayout) view;
                }
            }
            view.findViewById(R.id.park_item).setOnClickListener(v -> {
                if (selectView != null) {
                    selectView.findViewById(R.id.tips_text).setVisibility(View.VISIBLE);
                    selectView.findViewById(R.id.now_select).setVisibility(View.INVISIBLE);
                }
                view.findViewById(R.id.tips_text).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.now_select).setVisibility(View.VISIBLE);
                NetParmet.PARK_IP = bean.getIp();
                NetParmet.PARK_PROT = bean.getPort();
                AppValue.parkId = bean.getParkId();
                AppValue.parkName = bean.getName();
                selectView = (LinearLayout) view;
                LocationListActivity.this.finish();
            });
            mParkList.addView(view);
        }
    }

    @KListener(R.id.sreatch_layout)
    private void sreatch_layoutOnClick() {
        mSreatchLayout.setVisibility(View.GONE);
        mKeyWords.setVisibility(View.VISIBLE);
    }
}
