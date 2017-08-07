package com.wytube.activity;

import android.os.Bundle;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.wytube.utlis.AppValue;

/**
 * Created by LIN on 2017/8/4.
 */
@KActivity(R.layout.activity_attence)
public class AttenceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        loadData();
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish == 1) {
            loadData();
            AppValue.fish = -1;
        }
    }

    private void loadData() {
    }
}
