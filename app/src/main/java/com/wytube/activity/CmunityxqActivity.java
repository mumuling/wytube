package com.wytube.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BeseHd;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import static com.cqxb.yecall.R.id.text_add;


@KActivity(R.layout.activity_active_xq)
public class CmunityxqActivity extends BaseActivity {

    @KBind(R.id.back_but)
    private ImageView mBackBut;
    @KBind(R.id.title_text)
    private TextView mTitleText;
    @KBind(R.id.rlss)
    private RelativeLayout mRlss;
    @KBind(R.id.sss)
    private View mSss;
    @KBind(R.id.text_tiele_content)
    private TextView mTextTieleContent;
    @KBind(R.id.textView)
    private TextView mTextView;
    @KBind(R.id.textView3)
    private TextView mTextView3;
    @KBind(R.id.text_hd)
    private TextView mTextHd;
    @KBind(R.id.text_hdtime)
    private TextView mTextHdtime;
    @KBind(R.id.phone)
    private TextView mPhone;
    @KBind(R.id.text_phone)
    private TextView mTextPhone;
    @KBind(R.id.textView1)
    private TextView mTextView1;
    @KBind(text_add)
    private TextView mTextAdd;
    @KBind(R.id.textView2)
    private TextView mTextView2;
    @KBind(R.id.img_v)
    private ImageView mImgV;
    @KBind(R.id.text_contentxq)
    private TextView mTextContentxq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }


    /*删除*/
    @KListener(R.id.linear_delete)
    private void mLinearDeleteOnClick() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", (dialog, which) -> {loadhdele();});
        builder.setNegativeButton("取消", (dialog, which) -> {});
        builder.create().show();
    }

    /*编辑修改*/
    @KListener(R.id.linear_modify)
    private void mlinear_modifyOnClick() {
        AppValue.ActivSxg = 1;
        startActivity(new Intent(this, ActiveFbActivity.class));
        finish();
    }

    /**
     * 删除社区活动
     * activityId	是	String	活动Id
     */
    private void loadhdele() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.USR_ADD_DELE,"activityId="+AppValue.listBeseHd.getActivityId(), new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BeseHd bean = Json.toObject(json, BeseHd.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }else {
                AppValue.fish = 1;
                finish();
            }
            return false;
        }));
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mTextTieleContent.setText(AppValue.listBeseHd.getAddress() + " : " + AppValue.listBeseHd.getActivityName());
        mTextView.setText(AppValue.listBeseHd.getJoinCount());
        if (AppValue.listBeseHd.getJoinCount()==null){
            mTextView.setText("0");
        }else {
            mTextView.setText(AppValue.listBeseHd.getJoinCount());
        }
        mTextHdtime.setText(AppValue.listBeseHd.getStarttime() + " 至 " + AppValue.listBeseHd.getEndtime());
        mTextPhone.setText(AppValue.listBeseHd.getPhone());
        mTextAdd.setText(AppValue.listBeseHd.getAddress());
        Utils.loadImage(mImgV, AppValue.listBeseHd.getImgUrl());
        if (AppValue.listBeseHd.getContent() == null) {
            mTextContentxq.setText("暂无内容");
        } else {
            mTextContentxq.setText(AppValue.listBeseHd.getContent().toString());
        }
    }
}
