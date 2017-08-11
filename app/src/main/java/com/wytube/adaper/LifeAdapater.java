package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.LifexqActivity;
import com.wytube.beans.BaseSHfw;
import com.wytube.utlis.Utils;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/7.
 * 类 描 述:
 */

public class LifeAdapater extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BaseSHfw.DataBean> list;

    public LifeAdapater(Context mContext, List<BaseSHfw.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.kjcx_content, null);
            mholder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            mholder.text_tiele = (TextView) convertView.findViewById(R.id.text_tiele);
            mholder.RelativeLa = (RelativeLayout) convertView.findViewById(R.id.RelativeLa);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        BaseSHfw.DataBean bean = list.get(position);
        Utils.loadImage(mholder.imageView, bean.getPic());
        mholder.text_tiele.setText(bean.getName());
        mholder.RelativeLa.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,LifexqActivity.class);
            intent.putExtra("title",bean.getName());
            intent.putExtra("ShopId",bean.getShopTypeId());
            mContext.startActivity(intent);
        });
        return convertView;
    }

    public class viewHolder {
        private TextView text_tiele;
        private ImageView imageView;
        private RelativeLayout RelativeLa;
    }

}
