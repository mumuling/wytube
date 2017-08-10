package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.UserTypeBean;

import java.util.List;

/**
 * Created by LIN on 2017/8/8.
 */

public class TypeAdapter extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private List<UserTypeBean.DataBean> lists;

    public TypeAdapter(Context mContext, List<UserTypeBean.DataBean> list) {
        this.mContext = mContext;
        this.lists=list;
    }

    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView==null){
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.build_type_dialog, null);
            mholder.tv_type_name = (TextView) convertView.findViewById(R.id.tv_type_name);
            mholder.rl_foot_= (RelativeLayout) convertView.findViewById(R.id.rl_foot_);
            // 设置提示
            convertView.setTag(mholder);
        }else {
            mholder = (viewHolder) convertView.getTag();
        }
        UserTypeBean.DataBean bean = lists.get(position);
        mholder.tv_type_name.setText(bean.getUnitTypeName());
        return convertView;
    }
    public class viewHolder {
        private TextView tv_type_name;
        private RelativeLayout rl_foot_;
    }
}
