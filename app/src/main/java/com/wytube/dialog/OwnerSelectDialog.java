package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;

/**
 * Created by LIN on 2017/8/6.
 */

public class OwnerSelectDialog extends Dialog implements View.OnClickListener {
    private TextView tv_sure;
    private RelativeLayout rl_ydyyzz;
    private RelativeLayout rl_er;
    private RelativeLayout rl_sdyyzz;
    private ImageView iv_blue;
    private ImageView iv_blue2;

    private ImageView iv_blue3;
    private FrameLayout fl_root;

    public OwnerSelectDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public OwnerSelectDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected OwnerSelectDialog(@NonNull Context context,boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        setContentView(R.layout.owner_blocokcontact_dialog);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        rl_sdyyzz = (RelativeLayout) findViewById(R.id.rl_sdyyzz);
        rl_ydyyzz = (RelativeLayout) findViewById(R.id.rl_ydyyzz);
        rl_er = (RelativeLayout) findViewById(R.id.rl_er);
        iv_blue = (ImageView) findViewById(R.id.iv_blue);
        iv_blue2 = (ImageView) findViewById(R.id.iv_blue2);
        iv_blue3 = (ImageView) findViewById(R.id.iv_blue3);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
        rl_sdyyzz.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        rl_ydyyzz.setOnClickListener(this);
        rl_er.setOnClickListener(this);
        fl_root.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_root:
                dismiss();
                break;
            case R.id.tv_sure:
                if (mOnSelectListener != null) {
                    String content;
                    String tag1 = (String) iv_blue.getTag();
                    String tag2 = (String) iv_blue2.getTag();
                    String tag3 = (String) iv_blue3.getTag();
                    if ("0".equals(tag1)){
                        content = "一单元业主组";
                    }else if ("0".equals(tag2)){
                        content = "二单元业主组";
                    }else {
                        content = "三单元业主组";
                    }
                    mOnSelectListener.select(content);
                }
                dismiss();
                break;
            case R.id.rl_sdyyzz:
                iv_blue.setImageResource(R.drawable.yz_xqh);
                iv_blue.setTag("1");
                iv_blue3.setTag("0");
                iv_blue2.setTag("1");
                iv_blue2.setImageResource(R.drawable.yz_xqh);
                iv_blue3.setImageResource(R.drawable.yz_xql);
                break;
            case R.id.rl_ydyyzz:
                iv_blue.setTag("0");
                iv_blue3.setTag("1");
                iv_blue2.setTag("1");
                iv_blue.setImageResource(R.drawable.yz_xql);
                iv_blue2.setImageResource(R.drawable.yz_xqh);
                iv_blue3.setImageResource(R.drawable.yz_xqh);
                break;
            case R.id.rl_er:
                iv_blue.setTag("1");
                iv_blue3.setTag("1");
                iv_blue2.setTag("0");
                iv_blue.setImageResource(R.drawable.yz_xqh);
                iv_blue2.setImageResource(R.drawable.yz_xql);
                iv_blue3.setImageResource(R.drawable.yz_xqh);
                break;
        }
    }

    public interface OnSelectListener {
        void select(String s);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }
}
