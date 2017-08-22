package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.VisitorAdapters;
import com.wytube.beans.VisitorListBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_visitor_info)
public class VisitorInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener{

    @KBind(R.id.visitor_info_list)
    private ListView mVisitorInfoList;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;

    private VisitorAdapters adapter;
    private List<VisitorListBean.DataBean> list;
    int page=1,ISok=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    private void iniview() {
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadData(page,10);
    }

    private void loadData(int page,int rows) {
        Client.sendPost(NetParmet.USR_FK_LB, "page="+ page +"&rows="+rows , new Handler(msg -> {
            String json = msg.getData().getString("post");
            VisitorListBean bean = Json.toObject(json, VisitorListBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(VisitorInfoActivity.this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(VisitorInfoActivity.this, bean.getMessage());
                return false;
            }
            AppValue.VisitorMsgs = bean.getData();
            if (ISok == 0) {
                list = bean.getData();
                adapter = new VisitorAdapters(this, list);
                mVisitorInfoList.setAdapter(adapter);
            } else {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
                /*结束更多加载*/
                mSwipe_container.setLoading(false);
            }
            ISok++;
            return false;
        }));
    }

    /*下拉刷新*/
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData(page,10);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    /*上拉更多*/
    @Override
    public void onLoadMore() {
        if (AppValue.VisitorMsgs.size()==0){
            ToastUtils.showToast(this,"没有更多数据");
        }else {
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                loadData(page, 10);
            }, 2000);
        }
    }
}
