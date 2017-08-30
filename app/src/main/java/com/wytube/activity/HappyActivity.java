package com.wytube.activity;

import android.content.Context;
import android.content.Intent;
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
import com.wytube.adaper.HappyAdapter;
import com.wytube.beans.HappyBean;
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
 * 喜事列表
 */
@KActivity(R.layout.activity_happy)
public class HappyActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshAndMoreLoadLayout.OnLoadMoreListener {
    private LinearLayout selectLayout;
    @KBind(R.id.ll_reviewed)
    private LinearLayout ll_reviewed;
    @KBind(R.id.ll_oked)
    private LinearLayout ll_oked;
    @KBind(R.id.happy_list)
    private ListView happy_list;
    @KBind(R.id.shaxin)
    private RelativeLayout mshaxin;
    @KBind(R.id.img_404)
    private ImageView mimg_404;
    @KBind(R.id.img_200)
    private ImageView mimg_200;
    @KBind(R.id.swipe_container)
    private SwipeRefreshAndMoreLoadLayout mSwipe_container;
    private HappyAdapter apapter;
    List<HappyBean.DataBean> passData = new ArrayList<>();
    List<HappyBean.DataBean> list;
    private Context context;
    int type = 0;
    int page=1,ISok=0;

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            passData.clear();
            loadData(page,15);
            AppValue.fish=-1;
        }
    }

    @KListener(R.id.shaxin)
    private void shaxinOnClick() {
        passData.clear();/*清空之前的数据*/
        loadData(page,15);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        context = this;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        mSwipe_container.setOnRefreshListener(this);
        mSwipe_container.setOnLoadMoreListener(this);
        mSwipe_container.setColorSchemeResources(R.color.colorAccent,
                R.color.app_color_pass_color,R.color.red);
        loadData(page,15);
        selectLayout = ll_reviewed;
    }


    /*
    * 喜事列表数据
    * */
    private void loadData(int page,int rows) {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.HAPPY, "stateId"+"&page="+ page +"&rows="+rows, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            HappyBean bean = Json.toObject(json, HappyBean.class);
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
            AppValue.xsBeans = bean.getData();
            apapter = new HappyAdapter(this, passData);
            happy_list.setAdapter(apapter);
            apapter.setOnIteOnItemClickListener(bean1 -> {
                Intent intent = new Intent(HappyActivity.this, ApplyDetailActivity.class);
                intent.putExtra("data", bean1);
                context.startActivity(intent);//传递参数判断是审阅还是通过
            });
            if (ISok==0){
                apapter=new HappyAdapter(this, passData);
                happy_list.setAdapter(apapter);
            }else {
                mSwipe_container.setLoading(false);
            }
            if(page==1){
                passData.clear();
            }
            for (HappyBean.DataBean repairBean : AppValue.xsBeans) {
                if(type==0){
                    if (repairBean.getStateId() == 0) {
                        passData.add(repairBean);
                    }
                }else {
                    if(repairBean.getStateId()==1||repairBean.getStateId()==2){
                        passData.add(repairBean);
                    }
                }
                if (passData.size()==0){
                    mshaxin.setVisibility(View.VISIBLE);
                }else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            apapter.setBeans(passData);
            apapter.notifyDataSetChanged();
            ISok++;
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

    @KListener(R.id.ll_reviewed)
    private void ll_reviewedOnClick() {
        clearStyle(selectLayout);
        selectLayout = ll_reviewed;
        ((TextView) ll_reviewed.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        ll_reviewed.getChildAt(1).setVisibility(View.VISIBLE);
        type = 0;
        passData.clear();
        if (AppValue.xsBeans!=null) {
            for (HappyBean.DataBean happyBean : AppValue.xsBeans) {
                if (happyBean.getStateId() == 0) {
                    passData.add(happyBean);
                }
                if (passData.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            apapter.setData(passData);
            apapter.notifyDataSetChanged();

        }
    }

    @KListener(R.id.ll_oked)
    private void ll_okedOnClick() {
        clearStyle(selectLayout);
        type = 1;
        passData.clear();
        selectLayout = ll_oked;
        ((TextView) ll_oked.getChildAt(0)).setTextColor(getResources().getColor(R.color.app_main_color_green));
        ll_oked.getChildAt(1).setVisibility(View.VISIBLE);
        if (AppValue.xsBeans!=null) {
            for (HappyBean.DataBean happyBean : AppValue.xsBeans) {
                if (happyBean.getStateId() == 1 || happyBean.getStateId() == 2) {
                    passData.add(happyBean);
                }
                if (passData.size() == 0) {
                    mshaxin.setVisibility(View.VISIBLE);
                } else {
                    mshaxin.setVisibility(View.GONE);
                }
            }
            apapter.setData(passData);
            apapter.notifyDataSetChanged();
        }
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
        if (AppValue.xsBeans.size()<=0||AppValue.xsBeans==null){
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
