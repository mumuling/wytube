package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.HappyBean;

import java.util.List;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 报修记录列表适配器
 */

public class HappyAdapter extends BaseAdapter {

    Context context;
    viewHolder mholder;
    private List<HappyBean.DataBean> list;
    private OnItemClickListener onItemClickListener;
    public HappyAdapter(Context mContext, List<HappyBean.DataBean> list) {
        this.context = mContext;
        this.list = list;
    }

    public void setData(List<HappyBean.DataBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_happy, parent, false);
            mholder.happyType = (TextView) convertView.findViewById(R.id.happy_type);
            mholder.happyState = (TextView) convertView.findViewById(R.id.happy_state);
            mholder.happyconText = (TextView) convertView.findViewById(R.id.happy_text);
            mholder.happyTiime = (TextView) convertView.findViewById(R.id.happy_time);
            mholder.happyItem = (LinearLayout) convertView.findViewById(R.id.happy_item);

            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        HappyBean.DataBean bean = list.get(position);
        convertView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(bean);
            }
        });
        switch (bean.getStateId()) {
            case 0:
                //待受理
                mholder.happyState.setTextColor(context.getResources().getColor(R.color.dark_orange));
                mholder.happyState.setText("未通过");
                break;
            case 1:
                //已完成
                mholder.happyState.setTextColor(context.getResources().getColor(R.color.colorAccent));
                mholder.happyState.setText("已通过");
                break;
            case 2:
                //驳回
                mholder.happyState.setTextColor(context.getResources().getColor(R.color.grey11));
                mholder.happyState.setText("已驳回");
            default:
                mholder.happyState.setVisibility(View.VISIBLE);
                break;
        }

        mholder.happyType.setText(bean.getBuildingName() + bean.getUnitName() + bean.getNumberName());
        mholder.happyconText.setText(bean.getContent());
        mholder.happyTiime.setText(bean.getStarttime());
        //mholder.happyList.removeAllViews();
        return convertView;
    }

    public class viewHolder {
        private TextView happyType, happyState, happyconText, happyTiime;
        private LinearLayout happyItem;
        private LinearLayout happyList;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = this.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setBeans(List<HappyBean.DataBean> beans) {
        this.list = beans;
    }
    public void setOnIteOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(HappyBean.DataBean bean);
    }
}
