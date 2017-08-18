package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.BaseBxxq;
import com.wytube.shared.RatingBar;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/10.
 * 类 描 述:
 */

public class StarAdapter extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BaseBxxq.SuitWorkBean.ResultListBean> listBean;

    public StarAdapter(Context mContext, List<BaseBxxq.SuitWorkBean.ResultListBean> listBean) {
        this.mContext = mContext;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listBean.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listBean.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_repair_xq, null);
            mholder.text_tiele = (TextView) convertView.findViewById(R.id.text_tiele);
            mholder.rb = (RatingBar) convertView.findViewById(R.id.rb);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        //绑定数据
        BaseBxxq.SuitWorkBean.ResultListBean bean = listBean.get(position);
        mholder.text_tiele.setText(bean.getDimensionName());
        mholder.rb.setClickable(false);//设置可否点击
        mholder.rb.setStar(bean.getScore());//设置显示的星星个数
        mholder.rb.setStepSize(RatingBar.StepSize.Half);//设置每次点击增加一颗星还是半颗星
        mholder.rb.setTag(position);
        return convertView;
    }
    public class viewHolder {
        private TextView text_tiele;
        private RatingBar rb;
    }

    public void setBeen(List<BaseBxxq.SuitWorkBean.ResultListBean> been) {
        this.listBean = been;
    }

}
