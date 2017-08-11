package com.wytube.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BaseOK;
import com.wytube.beans.NewsNrBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

@KActivity(R.layout.activity_news_info)
public class NewsInfoActivity extends BaseActivity {

    @KBind(R.id.title_text)
    private TextView mTitleText;
    @KBind(R.id.news_image)
    private ImageView mNewsImage;
    @KBind(R.id.news_context)
    private WebView mNewsContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_texts).setOnClickListener(v -> {finish();});
    }

    /**
     * 初始化数据
     */
    private void initView() {
        if (AppValue.infoBean == null) {
            return;
        }
        NewsNrBean.DataBean bean = AppValue.infoBean;
        mNewsContext.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mNewsContext.loadData(bean.getContent(), "text/html;charset=UTF-8", null);
        Utils.loadImage(mNewsImage, bean.getPic());
        mTitleText.setText(bean.getTitle());
    }

    @Override
    protected void onDestroy(){
        mNewsContext.destroy();
        super.onDestroy();
    }

    /*删除资讯*/
    @KListener(R.id.linear_delete)
    private void linear_deleteOnClick() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {loadData();});
        builder.setNegativeButton("取消", (dialog, which) -> {});
        builder.create().show();
    }


    /*修改资讯*/
    @KListener(R.id.linear_modify)
    private void linear_modifyOnClick() {
        AppValue.NewSxg = 1;
        startActivity(new Intent(this, NewFBzxActivity.class));
        this.finish();
    }

    /**
     * 删除
     */
    private void loadData() {
        Client.sendPost(NetParmet.GET_ALL_DELETE, "ids="+ AppValue.infoBean.getInfoId(), new Handler(msg -> {
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }else {
                Toast.makeText(this, "删除成功!", Toast.LENGTH_SHORT).show();
                AppValue.fish=1;
                this.finish();
            }
            return false;
        }));
    }
}
