package com.wytube.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.RepairAdapters;
import com.wytube.beans.RepairBean;
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
 * 家政服务
 * */
@KActivity(R.layout.activity_repair)
public class RepairActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
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
    private LinearLayout selectLayout;
    private List<RepairBean.DataBean> tempBeans = new ArrayList<>();
    private RepairAdapters adapter;
    private int statetype = 0; /*每次点击改变值 默认为0*/
    int page=1,ISok=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent,
                R.color.app_color_pass_color,R.color.red);
        loadData(page,15);
        selectLayout = mNotProcess;/*标头栏*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            tempBeans.clear();/*清空之前的数据*/
            loadData(page,15);
            AppValue.fish = -1;
        }
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        tempBeans.clear();/*清空之前的数据*/
        loadData(page,15);
    }

    @KListener(R.id.not_process)
    private void not_processOnClick() {
        clearStyle(selectLayout);
        selectLayout = mNotProcess;
        ((TextView) mNotProcess.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        mNotProcess.getChildAt(1).setVisibility(View.VISIBLE);
        statetype = 0;
        tempBeans.clear();
        if (AppValue.repairBeans!=null) {
            for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                if (repairBean.getStateId() == 0) {
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
        statetype = 1;
        tempBeans.clear();
        if (AppValue.repairBeans!=null) {
            for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                if (repairBean.getStateId() == 1) {
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
        statetype = 2;
        tempBeans.clear();
        if (AppValue.repairBeans!=null){
            for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                if (repairBean.getStateId() == 2) {
                    tempBeans.add(repairBean);
                }
                if (tempBeans.size()==0){
                    mshaxin.setVisibility(View.VISIBLE);
                }else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 请求数据
     */
    private void loadData(int page,int rows) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.QUERY_REPAIR_LIST, "page="+ page +"&rows="+rows, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            RepairBean bean = Json.toObject(json, RepairBean.class);
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
            AppValue.repairBeans = bean.getData();
            adapter = new RepairAdapters(this,AppValue.repairBeans);
            mRepairList.setAdapter(this.adapter);
<<<<<<< Updated upstream
            if (AppValue.repairBeans.size()!=0) {
                for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                    if (repairBean.getStateId() == statetype) {
                        tempBeans.add(repairBean);
                    }
                    if (tempBeans.size() == 0) {
                        mshaxin.setVisibility(View.VISIBLE);
                    } else {
                        mshaxin.setVisibility(View.GONE);
                    }
=======
            if (ISok==0){
                adapter=new RepairAdapters(this,AppValue.repairBeans);
                mRepairList.setAdapter(adapter);
            }else {
                mSwipe_container.setLoading(false);
            }
            if(page==1){
                tempBeans.clear();
            }
            for (RepairBean.DataBean repairBean : AppValue.repairBeans) {
                if (repairBean.getStateId() == statetype) {
                    tempBeans.add(repairBean);
>>>>>>> Stashed changes
                }
            }else {
                if (tempBeans.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            adapter.setBeans(tempBeans);
            adapter.notifyDataSetChanged();
            return false;
        }));
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

    /*下拉刷新*/
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                loadData(page,15);
                mSwipe_container.setRefreshing(false);
            }
        }, 3000);
    }

    /*上拉更多*/
    @Override
    public void onLoadMore() {
        if (AppValue.repairBeans.size()<=0||AppValue.repairBeans==null){
            ToastUtils.showToast(this,"没有更多数据");
        }else {
            mSwipe_container.setLoadingContext("正在加载");
            new Handler().postDelayed(() -> {
                page++;
                loadData(page, 15);
            }, 2000);
        }
    }

}
