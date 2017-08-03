package com.wytube.beans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxb.yecall.R;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业缴费适配器
 */

public class BiilAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BiilBeaan.DataBean> list;

    public BiilAdapters(Context mContext, List<BiilBeaan.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_zds, null);
            mholder.text_xqname = (TextView) convertView.findViewById(R.id.text_xqname);
            mholder.pushMoney = (TextView) convertView.findViewById(R.id.push_money);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        //绑定数据
        BiilBeaan.DataBean bean = list.get(position);
        mholder.text_xqname.setText(bean.getCellName()+bean.getBuildingName()+bean.getUnitName()+bean.getNumberName());
        mholder.pushMoney.setText(bean.getTotalMoney());
        return convertView;
    }

    public class viewHolder {
        private TextView text_xqname, pushMoney;
    }

    public void setBeen(List<BiilBeaan.DataBean> been) {
        this.list = been;
    }
}
