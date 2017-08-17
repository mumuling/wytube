package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.OwnerBean;

import java.util.List;

import static com.cqxb.yecall.R.id.tv_yztype;

/**
 * 业主管理适配器
 */

public class OwnerAdapter extends BaseAdapter {
    Context context;
    viewHolder holder;
    private List<OwnerBean.DataBean> list;

    public OwnerAdapter(Context context, List<OwnerBean.DataBean> list) {
        this.context = context;
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

        if(convertView==null)  {
            holder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_owner,null);
            holder.tv_yename = (TextView) convertView.findViewById(R.id.tv_yename);
            holder.tv_yedz = (TextView) convertView.findViewById(R.id.tv_yedz);
            holder.tv_yztype = (TextView) convertView.findViewById(tv_yztype);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        OwnerBean.DataBean bean = list.get(position);
        holder.tv_yename.setText(bean.getOwnerName());
        if (bean.getOwnerType()==0) {
            holder.tv_yztype.setText("业主");
        }else if (bean.getOwnerType()==1){
            holder.tv_yztype.setText("租客");
        }else if ((bean.getOwnerType()==2)){
            holder.tv_yztype.setText("亲属");
        }else {
            holder.tv_yztype.setText("其他");
        }

        holder.tv_yedz.setText(bean.getBuildingName()+bean.getUnitName()+bean.getRoomNum());

        return convertView;
    }
    public class viewHolder{
        private TextView tv_yename;
        private TextView tv_yedz;
        private TextView tv_yztype;
    }

}



