package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.UnitBean;

import java.util.List;


public class UnitAdapter extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private List<UnitBean.DataBean> mlist;

    public UnitAdapter(Context mContext, List<UnitBean.DataBean> list) {
        this.mContext = mContext;
        this.mlist=list;
    }

    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView==null){
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.build_item_dialog, null);
            mholder.tv_text_unit = (TextView) convertView.findViewById(R.id.tv_text_name);
            mholder.rl_foot_= (RelativeLayout) convertView.findViewById(R.id.rl_foot_);

            // 设置提示
            convertView.setTag(mholder);
        }else {
            mholder = (viewHolder) convertView.getTag();
        }
        UnitBean.DataBean bean = mlist.get(position);
        mholder.tv_text_unit.setText(bean.getUnitName());
        return convertView;
    }
    public class viewHolder {
        private TextView tv_text_unit;
        private RelativeLayout rl_foot_;
    }
}
