package com.cqxb.yecall.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 创建时间: 2016/11/8.
 * 类 描 述: ViewPager的Adapter
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewList;

    public ViewPagerAdapter(List<View> viewList) {

        this.viewList = viewList;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public int getCount() {

        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (viewList.get(position).getParent() == null) {

            container.addView(viewList.get(position));
        }

        return viewList.get(position);
    }
}
