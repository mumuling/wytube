package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.CommunityAdapter;
import com.wytube.beans.BeseHd;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


@KActivity(R.layout.activity_activelist)
public class ActiveActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {
    private List<BeseHd.DataBean> list;

    @KBind(R.id.list_hd)
    private ListView mListHd;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    @KBind(R.id.text_fb)
    private TextView mtext_fb;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    int page = 1, ISok = 0;
    private CommunityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.title_text).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.text_fb).setOnClickListener(v -> {
            startActivity(new Intent(this, ActiveFbActivity.class));
        });
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent,
                R.color.app_color_pass_color, R.color.red);
        loadhd(page, 15);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            loadhd(page, 15);
            AppValue.fish = -1;
        }
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        loadhd(page, 15);
    }

    /**
     * 社区活动
     * regUserId    参与人ID
     * rows         获取条数 默认10
     * page         获取第几页数据 默认1
     */
    private void loadhd(int page, int rows) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_SQHD, "page=" + page + "&rows=" + rows, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BeseHd bean = Json.toObject(json, BeseHd.class);
            if (bean == null) {
                mshaxin.setVisibility(View.VISIBLE);
                mimg_200.setVisibility(View.GONE);
                mimg_404.setVisibility(View.VISIBLE);
                mtext_fb.setVisibility(View.GONE);
                ToastUtils.showToast(this, "服务器异常!请稍后再试!");
                return false;
            }
            mtext_fb.setVisibility(View.VISIBLE);
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }

            AppValue.beseBean = bean.getData();
            if (ISok == 0) {
                list = bean.getData();
                mAdapter = new CommunityAdapter(ActiveActivity.this, list);
                mListHd.setAdapter(mAdapter);
            } else {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(bean.getData());
                mAdapter.notifyDataSetChanged();
                mSwipe_container.setLoading(false);
            }
            ISok++;
            if (list == null || list.size() <= 0) {
                mshaxin.setVisibility(View.VISIBLE);

            } else {
                mshaxin.setVisibility(View.GONE);
            }
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
                loadhd(page, 15);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    /*上拉更多*/
    @Override
    public void onLoadMore() {
        // 这里蹦的
        if (AppValue.beseBean.size() <= 0 || AppValue.beseBean == null) {
            ToastUtils.showToast(this, "没有更多数据");
        } else {
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                loadhd(page, 15);
            }, 2000);
        }
    }
}
