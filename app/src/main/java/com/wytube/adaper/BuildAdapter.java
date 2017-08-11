package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.BuildBean;
import com.wytube.utlis.AppValue;

import java.util.List;

/**
 * Created by LIN on 2017/8/8.
 */

public class BuildAdapter extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BuildBean.DataBean> list;

    public BuildAdapter(Context mContext, List<BuildBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.build_item_dialog, null);
            mholder.tv_text_name = (TextView) convertView.findViewById(R.id.tv_text_name);
            mholder.rl_foot_= (RelativeLayout) convertView.findViewById(R.id.rl_foot_);

            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        BuildBean.DataBean bean = list.get(position);
        mholder.tv_text_name.setText(bean.getBuildingName());
        mholder.rl_foot_.setOnClickListener(view -> {
            AppValue.LYid = list.get(position).getBuildingId();
        });
        return convertView;
    }
    public class viewHolder {
        private TextView tv_text_name;
        private RelativeLayout rl_foot_;
    }
}
