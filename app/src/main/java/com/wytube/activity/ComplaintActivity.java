package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.TSRepairAdapters;
import com.wytube.beans.BaseLbrepair;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.shared.Ftime.SwipeRefreshAndMoreLoadLayout;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/14.
 * 类 描 述: 报修主页
 */

@KActivity(R.layout.activity_bx_repair)
public class ComplaintActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {

    @KBind(R.id.not_process)
    private LinearLayout mNotProcess;
    @KBind(R.id.process_ing)
    private LinearLayout mProcessIng;
    @KBind(R.id.processed)
    private LinearLayout mProcessed;
    @KBind(R.id.repair_list)
    private ListView mRepairList;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    private List<BaseLbrepair.DataBean> list;
    private LinearLayout selectLayout;
    private List<BaseLbrepair.DataBean> tempBeans = new ArrayList<>();
    private TSRepairAdapters adapter;
    private int SuitStateIdtype = 0;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    int page = 1, ISok = 0;

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
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent,
                R.color.app_color_pass_color, R.color.red);
        loadData(page, 10);
        selectLayout = mNotProcess;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            tempBeans.clear();
            loadData(page, 10);
            AppValue.fish = -1;
        }
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        tempBeans.clear();/*清空之前的数据*/
        loadData(page, 10);
    }


    @KListener(R.id.not_process)
    private void not_processOnClick() {
        clearStyle(selectLayout);
        selectLayout = mNotProcess;
        ((TextView) mNotProcess.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mNotProcess.getChildAt(1).setVisibility(View.VISIBLE);
        SuitStateIdtype = 0;
        tempBeans.clear();
        if (AppValue.lbBeans != null) {
            for (BaseLbrepair.DataBean repairBean : AppValue.lbBeans) {
                if (repairBean.getSuitStateId() == 0) {
                    tempBeans.add(repairBean);
                }
                if (tempBeans.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.process_ing)
    private void process_ingOnClick() {
        clearStyle(selectLayout);
        selectLayout = mProcessIng;
        ((TextView) mProcessIng.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mProcessIng.getChildAt(1).setVisibility(View.VISIBLE);
        SuitStateIdtype = 1;
        tempBeans.clear();
        if (AppValue.lbBeans != null) {
            for (BaseLbrepair.DataBean repairBean : AppValue.lbBeans) {
                if (repairBean.getSuitStateId() == 1) {
                    tempBeans.add(repairBean);
                }
                if (tempBeans.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
        }
    }

    @KListener(R.id.processed)
    private void processedOnClick() {
        clearStyle(selectLayout);
        selectLayout = mProcessed;
        ((TextView) mProcessed.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mProcessed.getChildAt(1).setVisibility(View.VISIBLE);
        SuitStateIdtype = 2;
        tempBeans.clear();
        if (AppValue.lbBeans != null) {
            for (BaseLbrepair.DataBean repairBean : AppValue.lbBeans) {
                if (repairBean.getSuitStateId() == 2) {
                    tempBeans.add(repairBean);
                }
                if (tempBeans.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 清除点击样式
     *
     * @param layout 布局视图
     */
    private void clearStyle(LinearLayout layout) {
        ((TextView) layout.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_def_text_color));
        layout.getChildAt(1).setVisibility(View.INVISIBLE);
    }

    /**
     * 请求数据
     */
    private void loadData(int page, int rows) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_BXTS_LB, "page=" + page + "&rows=" + rows, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseLbrepair bean = Json.toObject(json, BaseLbrepair.class);
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
            AppValue.lbBeans = bean.getData();
            adapter = new TSRepairAdapters(this, tempBeans);
            mRepairList.setAdapter(this.adapter);
<<<<<<< Updated upstream
            if (AppValue.lbBeans.size()!=0) {
                for (BaseLbrepair.DataBean repairBean : AppValue.lbBeans) {
                    if (repairBean.getSuitStateId() == SuitStateIdtype) {
                        tempBeans.add(repairBean);
                    }
                    if (tempBeans.size() == 0) {
                        mshaxin.setVisibility(View.VISIBLE);
                    } else {
                        mshaxin.setVisibility(View.GONE);
                    }
                }
            }else {
                if (tempBeans.size() == 0) {
=======
            if (ISok == 0) {
                adapter = new TSRepairAdapters(this, tempBeans);
                mRepairList.setAdapter(this.adapter);
            } else {
                mSwipe_container.setLoading(false);
            }
            if (page == 1) {
                tempBeans.clear();
            }
            for (BaseLbrepair.DataBean repairBean : AppValue.lbBeans) {
                if (repairBean.getSuitStateId() == SuitStateIdtype) {
                    tempBeans.add(repairBean);
                }
                if (tempBeans.size() <= 0) {
>>>>>>> Stashed changes
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
            ISok++;
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
        if (AppValue.lbBeans.size() <= 0 || AppValue.lbBeans == null) {
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
