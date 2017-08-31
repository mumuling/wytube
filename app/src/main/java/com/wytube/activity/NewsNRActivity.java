package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.NewsAdapters;
import com.wytube.beans.NewsNrBean;
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
 * 创建时间: 2017/8/8.
 * 类 描 述: 新闻资讯内容列表
 */

@KActivity(R.layout.item_news_list)

// 这个类里面放刷新的逻辑
public class NewsNRActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {
    @KBind(R.id.listview_zx)
    private ListView mlistview_zx;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    NewsAdapters adapter;
    int page = 1, ISok = 0;

    Intent intent;
    private List<NewsNrBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        iniview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            iniview();
            AppValue.fish = -1;
        }
    }

    private void iniview() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");

        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent,
                R.color.app_color_pass_color, R.color.red);
        loadData(page, 10);
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        loadData(page, 10);
    }


    /**
     * 加载分类列表
     */
    private void loadData(int page, int rows) {
        Client.sendPost(NetParmet.GET_ALL_INFO_TYPES, "type=" + intent.getStringExtra("tyId") + "&page=" + page + "&rows=" + rows, new Handler(msg -> {
            String json = msg.getData().getString("post");
            NewsNrBean bean = Json.toObject(json, NewsNrBean.class);
            if (bean == null) {
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                ToastUtils.showToast(this, "服务器异常!请稍后再试!");
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }

            AppValue.typeBean = bean.getData();
            if (ISok == 0) {
                list = bean.getData();
                adapter = new NewsAdapters(this, list);
                mlistview_zx.setAdapter(adapter);
            } else {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
                mSwipe_container.setLoading(false);
            }
            ISok++;
           if (list.size() == 0) {
                mshaxin.setVisibility(View.VISIBLE);
            } else {
                mshaxin.setVisibility(View.GONE);
            }
            return false;
        }));
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData(page, 10);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        if (AppValue.typeBean.size() <= 0 || AppValue.typeBean == null) {
            ToastUtils.showToast(this, "没有更多数据");
        } else {
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                loadData(page, 10);
            }, 2000);
        }
    }

}
