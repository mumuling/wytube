package com.wytube.beans;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.PropertyXQActivity;
import com.wytube.shared.Ftime.BiilBeaan;
import com.wytube.shared.Ftime.PinnedSectionRefreshListView;
import com.wytube.utlis.AppValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业缴费适配器
 */

public class BiilAdapters extends BaseAdapter implements
        PinnedSectionRefreshListView.PinnedSectionListAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BiilBeaan.DataBean> list;
    public boolean flage = true;/*选择还是取消*/
    public boolean flages = true;/*全选还是不全选*/
    public Map<Integer, String> selected;

    public List<BiilBeaan.DataBean> getList() {
        return list;
    }

    public void setList(List<BiilBeaan.DataBean> list) {
        if (list != null) {
            this.list = list;
        }
    }
    public BiilAdapters(List<BiilBeaan.DataBean> list, Context mContext) {
        super();
        this.setList(list);
        this.mContext = mContext;
        selected = new HashMap<>();
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
            mholder.item_type = (TextView) convertView.findViewById(R.id.item_type);
            mholder.text_timer = (TextView) convertView.findViewById(R.id.text_timer);
            mholder.linear_wyxq = (LinearLayout) convertView.findViewById(R.id.linear_wyxq);
            mholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        BiilBeaan.DataBean bean = list.get(position);
        mholder.item_type.setText(list.get(position).getBillName());
        mholder.text_timer.setText(list.get(position).getMonth());
        mholder.text_xqname.setText(bean.getBuildingName()+bean.getUnitName()+bean.getNumberName());
        mholder.pushMoney.setText(list.get(position).getTotalMoney());
        mholder.linear_wyxq.setOnClickListener(v -> {
            AppValue.wyfBeans = list.get(position);
            mContext.startActivity(new Intent(mContext, PropertyXQActivity.class));
        });

        // 根据isSelected来设置checkbox的显示状况
        if (flage) {
            mholder.checkbox.setVisibility(View.GONE);
        } else {
            mholder.checkbox.setVisibility(View.VISIBLE);
        }

        if (selected.containsKey(position))
            mholder.checkbox.setChecked(true);
        else
            mholder.checkbox.setChecked(false);
        mholder.checkbox.setChecked(list.get(position).isCheck);
        mholder.checkbox.setOnClickListener(v -> {
            if (list.get(position).isCheck) {
                list.get(position).isCheck = false;
                String[] WYjfIds = AppValue.WYjfId.split(",");
                AppValue.WYjfId = "";
                for (int i = 0; i < WYjfIds.length; i++) {
                    if(!WYjfIds[i].equals(list.get(position).getBillId()))
                    {
                        if (AppValue.WYjfId != null && !AppValue.WYjfId.equals(""))
                        {
                            AppValue.WYjfId += ",";
                        }
                        AppValue.WYjfId += WYjfIds[i];
                    }
                }
            } else {
                if (AppValue.WYjfId != null && !AppValue.WYjfId.equals(""))
                {
                    AppValue.WYjfId += ",";
                }
                AppValue.WYjfId += list.get(position).getBillId();
                list.get(position).isCheck = true;
            }
        });
        return convertView;
    }

    public class viewHolder {
        private TextView text_xqname, pushMoney,item_type,text_timer;
        private LinearLayout linear_wyxq;
        private CheckBox checkbox;
    }

    public void setBeen(List<BiilBeaan.DataBean> list) {
        this.list = list;
    }


    //判断是否是属于标题悬浮的
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == BiilBeaan.SECTION;
    }

    //arraylist的数据里面有2种类型,标题和内容
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
