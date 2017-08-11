package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.VisitorListBean;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/26.
 * 类 描 述:
 */

public class VisitorAdapters extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private List<VisitorListBean.DataBean> list;

    public VisitorAdapters(Context mContext, List<VisitorListBean.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_visitor_info, null);
            mholder.visitorNum = (TextView) convertView.findViewById(R.id.visitor_num);
            mholder.visitorCode = (TextView) convertView.findViewById(R.id.check_code);
            mholder.dateText = (TextView) convertView.findViewById(R.id.date_text);
            mholder.carNum = (TextView) convertView.findViewById(R.id.car_num);
            mholder.carNumLayout = (LinearLayout) convertView.findViewById(R.id.car_num_layout);
            mholder.text_content_xq = (TextView) convertView.findViewById(R.id.text_content_xq);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        VisitorListBean.DataBean bean = list.get(position);
        mholder.dateText.setText(bean.getStarttime());
        mholder.visitorNum.setText(bean.getPassMobile());
        mholder.visitorCode.setText(bean.getCode());
        mholder.text_content_xq.setText(bean.getCellName()+bean.getBuildingName()+bean.getUnitName()+bean.getNumberName());
        if (bean.getCarLicense() != null && ((String) bean.getCarLicense()).length() > 0) {
            mholder.carNum.setText(((String) bean.getCarLicense()));
            mholder.carNumLayout.setVisibility(View.VISIBLE);
        } else {
            mholder.carNumLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class viewHolder {
        private TextView visitorNum, visitorCode, dateText, carNum,text_content_xq;
        private LinearLayout carNumLayout;
    }
}
