package com.wytube.activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.wytube.beans.NewsTypeBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@KActivity(R.layout.activity_news)
public class NewsActivity extends BaseActivity {

    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;	//下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)
    LocalActivityManager manager = null;
    private RadioGroup myRadioGroup;
    private int _id = 1000;
    private LinearLayout layout,titleLayout;
    private TextView textView;
    private List<NewsTypeBean.DataBean> typeBeans;
    private List<Map<String, Object>> titleList = new ArrayList<Map<String,Object>>();
    private Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> maps;
    Bundle savedInst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        savedInst = savedInstanceState;
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.text_fb).setOnClickListener(v -> {
            startActivity(new Intent(this, NewFBzxActivity.class));
        });
        loadData();
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppValue.fish==1){
            manager = new LocalActivityManager(this, true);
            manager.dispatchCreate(savedInst);
            loadData();
        }
    }

    /**
     * 加载分类列表
     */
    private void loadData() {
        Client.sendPost(NetParmet.GET_ALL_INFOS, "", new Handler(msg -> {
            String json = msg.getData().getString("post");
            NewsTypeBean bean = Json.toObject(json, NewsTypeBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            if (AppValue.fish==1){
                titleList.clear();
                map.clear();
                typeBeans.clear();
                mViews.clear();
                typeBeans = bean.getData();
                getTitleInfo();
//                initGroup();
                iniListener();
                iniVariable();
                AppValue.fish=-1;
                mViewPager.setCurrentItem(0);
            }else {
                typeBeans = bean.getData();
                getTitleInfo();
                initGroup();
                iniListener();
                iniVariable();
                mViewPager.setCurrentItem(0);
            }
            return false;
        }));
    }

    private void getTitleInfo(){
        for (int i = 0; i < typeBeans.size()  ; i++) {
            map = new HashMap<String, Object>();
            map.put("id", 0);
            map.put("tyId", typeBeans.get(i).getTypeId());
            map.put("title", typeBeans.get(i).getName());
            titleList.add(map);
        }
    }

    private void initGroup(){

        titleLayout = (LinearLayout) findViewById(R.id.title_lay);
        layout = (LinearLayout) findViewById(R.id.lay);
        //mImageView = new ImageView(this);
        mImageView = (ImageView)findViewById(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(myRadioGroup);
        for (int i = 0; i <titleList.size(); i++) {
            maps = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(25, 15, 25, 15);
            //radio.setPadding(left, top, right, bottom)
            radio.setId(_id+i);
            radio.setText(maps.get("title")+"");
            radio.setTextColor(Color.BLACK);
            radio.setTextSize(16);
            radio.setTag(maps);
            if (i == 0) {
                radio.setChecked(true);
                int itemWidth = (int) radio.getPaint().measureText(maps.get("title")+"");
                mImageView.setLayoutParams(new  LinearLayout.LayoutParams(itemWidth+radio.getPaddingLeft()+radio.getPaddingRight(),4));
            }
            myRadioGroup.addView(radio);
        }
        myRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
            int radioButtonId = group.getCheckedRadioButtonId();
            //根据ID获取RadioButton的实例
            RadioButton rb = (RadioButton)findViewById(radioButtonId);
            Map<String, Object> selectMap = (Map<String, Object>) rb.getTag();

            AnimationSet animationSet = new AnimationSet(true);
            TranslateAnimation translateAnimation;
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(true);
            animationSet.setFillAfter(true);
            animationSet.setDuration(300);

            mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
            mViewPager.setCurrentItem(radioButtonId-_id);//让下方ViewPager跟随上面的HorizontalScrollView切换
            mCurrentCheckedRadioLeft = rb.getLeft();//更新当前蓝色横条距离左边的距离
            mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.rdo2), 0);

            mImageView.setLayoutParams(new  LinearLayout.LayoutParams(rb.getRight()-rb.getLeft(),4));

        });

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }


    private void iniVariable() {
        mViews = new ArrayList<View>();
        for (int i = 0; i < titleList.size(); i++) {
            Intent intent1 = new Intent(this,NewsNRActivity.class);
            intent1.putExtra("id", i);
            intent1.putExtra("tyId", typeBeans.get(i).getTypeId());
            intent1.putExtra("title", typeBeans.get(i).getName());
            mViews.add(getView("View"+i, intent1));
        }
        mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
    }




    private void iniListener() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }


    /**
     * ViewPager的适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager)v).removeView(mViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager)v).addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

    }
    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }
        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            RadioButton radioButton = (RadioButton) findViewById(_id+position);
            radioButton.performClick();

        }
    }

}
