package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wytube.activity.CmunityxqActivity;
import com.wytube.beans.BeseHd;
import com.wytube.utlis.AppValue;

import java.util.List;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/5.
 * 类 描 述: 社区列表适配器
 */

public class CommunityAdapter extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private List<BeseHd.DataBean> list;

    public CommunityAdapter(Context mContext, List<BeseHd.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.activity_activecontent, null);
            mholder.img_v = (SimpleDraweeView) convertView.findViewById(R.id.img_v);
            mholder.text_tiele = (TextView) convertView.findViewById(R.id.text_tiele);
            mholder.text_tiele_content = (TextView) convertView.findViewById(R.id.text_tiele_content);
            mholder.text_hdtime = (TextView) convertView.findViewById(R.id.text_hdtime);
            mholder.text_phone = (TextView) convertView.findViewById(R.id.text_phone);
            mholder.text_add = (TextView) convertView.findViewById(R.id.text_add);
            mholder.text_sum = (TextView) convertView.findViewById(R.id.text_sum);
            mholder.text_xq = (TextView) convertView.findViewById(R.id.text_xq);
            mholder.Linearz = (LinearLayout) convertView.findViewById(R.id.Linearz);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        mholder.text_tiele.setText(list.get(position).getAddress()+": ");
        mholder.text_tiele_content.setText(list.get(position).getActivityName());
        mholder.text_hdtime.setText(list.get(position).getStarttime()+" 至 "+list.get(position).getEndtime());
        mholder.text_phone.setText(list.get(position).getPhone());
        mholder.text_add.setText(list.get(position).getAddress());
        if (list.get(position).getJoinCount()==null){
            mholder.text_sum.setText("0人");
        }else {
            mholder.text_sum.setText(list.get(position).getJoinCount()+"人");
        }
        if (list.get(position).getImgUrl() != null) {
            mholder.img_v.setImageURI(list.get(position).getImgUrl());
        }else {
            mholder.img_v.setVisibility(View.GONE);
        }
        mholder.text_xq.setOnClickListener(v -> {
            AppValue.listBeseHd = list.get(position);
            Intent intent = new Intent(mContext,CmunityxqActivity.class);
            mContext.startActivity(intent);
        });
        return convertView;
    }

    public class viewHolder {
        private TextView text_tiele, text_tiele_content;
        private TextView text_hdtime, text_phone,text_add;
        private TextView text_xq,text_sum;
        private SimpleDraweeView img_v;
        private LinearLayout Linearz;
    }
}
