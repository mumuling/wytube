package com.wytube.adaper;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/7.
 * 类 描 述:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.BaseLeftxq;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/7.
 * 类 描 述: 生活服务管理适配器
 */

public class LifexqAdapater extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BaseLeftxq.DataBean> list;
    public boolean flage = true;/*选择还是取消*/
    public boolean flages = true;/*全选还是不全选*/
    public Map<Integer, String> selected;


    public LifexqAdapater(Context mContext, List<BaseLeftxq.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.left_xq_content, null);
            mholder.img_v = (ImageView) convertView.findViewById(R.id.img_v);
            mholder.text_phone = (TextView) convertView.findViewById(R.id.text_phone);
            mholder.text_add = (TextView) convertView.findViewById(R.id.text_add);
            mholder.content_view = (WebView) convertView.findViewById(R.id.content_view);
            mholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);

            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        BaseLeftxq.DataBean bean = list.get(position);
        if (bean.equals("[]")){
            Utils.showOkDialog(mContext, "暂无数据!");
        }
        mholder.text_phone.setText(bean.getConcat());
        mholder.text_add.setText(bean.getRemark());
        Utils.loadImage(mholder.img_v, bean.getCover());
        mholder.content_view.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mholder.content_view.loadData(bean.getShopName(), "text/html;charset=UTF-8", null);

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
        /*注意这里设置的不是onCheckedChangListener*/
        mholder.checkbox.setOnClickListener(v -> {
            if (list.get(position).isCheck) {
                list.get(position).isCheck = false;
                String[] LifeIds = AppValue.LifeId.split(",");
                AppValue.LifeId = "";
                for (int i = 0; i < LifeIds.length; i++) {
                    if(!LifeIds[i].equals(list.get(position).getShopId()))
                    {
                        if (AppValue.LifeId != null && !AppValue.LifeId.equals(""))
                        {
                            AppValue.LifeId += ",";
                        }
                        AppValue.LifeId += LifeIds[i];
                    }
                }
            } else {
                if (AppValue.LifeId != null && !AppValue.LifeId.equals(""))
                {
                    AppValue.LifeId += ",";
                }
                AppValue.LifeId += list.get(position).getShopId();
                list.get(position).isCheck = true;
            }
        });

        return convertView;
    }

    public class viewHolder {
        private TextView text_phone,text_add;
        private ImageView img_v;
        private WebView content_view;
        private CheckBox checkbox;
    }
}
