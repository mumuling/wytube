package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.NewsInfoActivity;
import com.wytube.beans.NewsNrBean;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/30.
 * 类 描 述:
 */

public class NewsAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<NewsNrBean.DataBean> list;

    public NewsAdapters(Context mContext, List<NewsNrBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_news_layout, null);
            mholder.newsItem = (LinearLayout) convertView.findViewById(R.id.news_item);
            mholder.newsImg = (ImageView) convertView.findViewById(R.id.news_img);
            mholder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
//        NewsBean.DataBean.InfosBean bean = list.get(position);
        Utils.loadImage(mholder.newsImg, list.get(position).getPic());
        mholder.newsTitle.setText(list.get(position).getTitle());
        mholder.newsItem.setOnClickListener(v -> {
            AppValue.infoBean = list.get(position);
            mContext.startActivity(new Intent(mContext, NewsInfoActivity.class));
        });
        return convertView;
    }

    public class viewHolder {
        private LinearLayout newsItem;
        private ImageView newsImg;
        private TextView newsTitle;
    }
    public void setBeans(List<NewsNrBean.DataBean> beans) {
        this.list = beans;
    }

}
