package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.DetailsNoticeActivity;
import com.wytube.beans.PropMsgBean;
import com.wytube.utlis.AppValue;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业通知适配器
 */

public class PropListAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<PropMsgBean.DataBean> list;

    public PropListAdapters(Context mContext, List<PropMsgBean.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_prop_msg, null);
            mholder.title = (TextView) convertView.findViewById(R.id.title_text);
            mholder.date = (TextView) convertView.findViewById(R.id.date_text);
            mholder.readDonet = (ImageView) convertView.findViewById(R.id.red_donet);
            mholder.contentView = (WebView) convertView.findViewById(R.id.content_view);
            mholder.card_view = (CardView) convertView.findViewById(R.id.card_view);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        PropMsgBean.DataBean bean = list.get(position);
        mholder.title.setText(bean.getTitle());
        mholder.contentView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mholder.contentView.loadData(bean.getContent(), "text/html;charset=UTF-8", null);
//        mholder.contentView.setText(delHTMLTag(bean.getContent()));
        mholder.date.setText(bean.getStarttime());
        mholder.readDonet.setVisibility(View.INVISIBLE);
        mholder.card_view.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,DetailsNoticeActivity.class);
            intent.putExtra("title", bean.getTitle());
            intent.putExtra("content",bean.getContent());
            intent.putExtra("pushId",bean.getPushId());
            mContext.startActivity(intent);
        });
        return convertView;
    }

    public class viewHolder {
        private TextView title, content, date;
        private ImageView readDonet;
        private WebView contentView;
        private CardView card_view;
    }






}
