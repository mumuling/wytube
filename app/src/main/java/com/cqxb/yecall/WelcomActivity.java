package com.cqxb.yecall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.cqxb.yecall.adapter.ViewPagerAdapter;
import com.wytube.utlis.AppValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢迎界面,新特性介绍
 */
public class WelcomActivity extends Activity {

    private ViewPager welcom_view;

    private List<View> views;

    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcom);

        welcom_view = (ViewPager) findViewById(R.id.welcom_view);

        LayoutInflater inflater = getLayoutInflater();

        View view1 = inflater.inflate(R.layout.welcom_layout_1, null);

        View view2 = inflater.inflate(R.layout.welcom_layout_2, null);

        View view3 = inflater.inflate(R.layout.welcom_layout_3, null);

        view3.findViewById(R.id.goto_login_but).setOnClickListener(v -> {
            AppValue.onec = 1;
            WelcomActivity.this.startActivity(new Intent(WelcomActivity.this, LoginAppActivity.class));
            WelcomActivity.this.finish();
        });

        views = new ArrayList<>();

        views.add(view1);

        views.add(view2);

        views.add(view3);

        adapter = new ViewPagerAdapter(views);

        welcom_view.setAdapter(adapter);

    }
}
