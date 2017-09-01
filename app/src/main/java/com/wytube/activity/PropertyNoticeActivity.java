package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.PropListAdapters;
import com.wytube.beans.PropMsgBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业通知
 */
@KActivity(R.layout.activity_property_notice)
public class PropertyNoticeActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener{
    @KBind(R.id.msg_list)
    private ListView mMsgList;
    @KBind(R.id.repair_now)
    private LinearLayout mRepair_now;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    private List<PropMsgBean.DataBean> list;
    PropListAdapters adapter;
    int page=1,ISok=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        page=1;
        initData(page,5);
    }

    private void iniview() {
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent);
        initData(page,5);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            page=1;
            initData(page,5);
            AppValue.fish=-1;
        }
    }

    /**
     * 请求数据
     */
    private void initData(int page,int rows) {
        Client.sendPost(NetParmet.PROP_MSG, "page="+ page +"&rows="+rows, new Handler(msg -> {
            String json = msg.getData().getString("post");
            PropMsgBean bean = Json.toObject(json, PropMsgBean.class);
            if (bean == null) {
//                Utils.showNetErrorDialog(PropertyNoticeActivity.this);
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                mRepair_now.setVisibility(View.GONE);
                ToastUtils.showToast(this,"服务器异常!请稍后再试!");
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(PropertyNoticeActivity.this, bean.getMessage());
                return false;
            }
            AppValue.propMsgs = bean.getData();
            if (ISok == 0) {
                /*第一次加载的数据*/
                list = bean.getData();
                adapter = new PropListAdapters(this, list);
                mMsgList.setAdapter(adapter);
                mshaxin.setVisibility(View.VISIBLE);
            } else {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
                mSwipe_container.setLoading(false);/*结束更多加载*/
            }
            ISok++;
            if (list.size()==0){
                mshaxin.setVisibility(View.VISIBLE);
            }else {
                mshaxin.setVisibility(View.GONE);
            }
            return false;
        }));
    }

    /*发布通知*/
    @KListener(R.id.repair_now)
    private void repair_nowOnClick() {
        startActivity(new Intent(this, ReleaseNoticeActivity.class));
    }

    /*下拉刷新*/
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData(page,5);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    /*上拉更多*/
    @Override
    public void onLoadMore() {
        if (AppValue.propMsgs.size()==0){
            new Handler().postDelayed(() -> {
                ToastUtils.showToast(this, "没有更多数据");
                mSwipe_container.setLoading(false);
            }, 2000);
        }else {
            mRepair_now.setVisibility(View.GONE);
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                initData(page,5);
                mRepair_now.setVisibility(View.VISIBLE);
            }, 2000);
        }
    }




}