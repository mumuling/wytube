package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.adaper.OwnerAdapter;
import com.wytube.beans.OwnerBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 业主管理
 */
@KActivity(R.layout.activity_owner)
public class OwnerActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {
    @KBind(R.id.yzgl_list)
    private ListView yzgl_list;
    @KBind(R.id.tv_edit)
    private ImageView tv_edit;
    private OwnerAdapter adapter;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    private List<OwnerBean.DataBean> list;

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
        findViewById(R.id.tv_edit).setOnClickListener(view -> startActivity(
                new Intent(OwnerActivity.this,AddOwnerActivity.class)));
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadData(page,20);
        yzgl_list.setOnItemClickListener((adapterView, view, i, l) -> {
            OwnerBean.DataBean bean =(OwnerBean.DataBean )yzgl_list.getItemAtPosition(i);
            Intent intent = new Intent(OwnerActivity.this,OwnerItemActivity.class);
            intent.putExtra("databean",bean);
            startActivity(intent);
        });

    }


    /**
     * 请求数据
     */
    private void loadData(int page,int rows) {
        Client.sendPost(NetParmet.OWNER,"page="+ page +"&rows="+rows+"&sort=createDate",new Handler(msg -> {
            String json = msg.getData().getString("post");
            OwnerBean bean = Json.toObject(json, OwnerBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            AppValue.ownerBeans = bean.getData();
            if(ISok==0){
                list=bean.getData();
                adapter = new OwnerAdapter(this,list);
                yzgl_list.setAdapter(adapter);
            }else {
                if(page==1){
                    list.clear();
                }
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
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
                loadData(page,20);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    /*上拉更多*/
    @Override
    public void onLoadMore() {
        if (AppValue.ownerBeans.size()==0){
            ToastUtils.showToast(this,"没有更多数据");
        }else {
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                loadData(page, 20);
            }, 2000);
        }
    }


}
