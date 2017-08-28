package com.wytube.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KListener;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/22.
 * 类 描 述:
 */
@KActivity(R.layout.activity_yc)
public class YCdialogYC extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
    }

    /*点击返回*/
    @KListener(R.id.borrow_but)
    private void borrow_butOnClick() {
        finish();
    }
}
