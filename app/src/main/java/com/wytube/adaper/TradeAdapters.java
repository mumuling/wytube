package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.PYTradeActivity;
import com.wytube.activity.TradingInfoActivity;
import com.wytube.beans.BaseJylb;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/28.
 * 类 描 述:
 */

public class TradeAdapters extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private List<BaseJylb.DataBean> list;
    public boolean flage = true;/*选择还是取消*/
    public boolean flages = true;/*全选还是不全选*/
    public Map<Integer, String> selected;


    public TradeAdapters(Context mContext, List<BaseJylb.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_trade_layout, null);
            mholder.image_but = (ImageView) convertView.findViewById(R.id.image_but);
            mholder.rela_xx = (RelativeLayout) convertView.findViewById(R.id.rela_xx);
            mholder.rel_but = (RelativeLayout) convertView.findViewById(R.id.rel_but);
            mholder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            mholder.text_money = (TextView) convertView.findViewById(R.id.text_money);
            mholder.textView22 = (TextView) convertView.findViewById(R.id.textView22);
            mholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        if (position==0){
            mholder.rela_xx.setVisibility(View.GONE);
            mholder.textView22.setVisibility(View.VISIBLE);
            mholder.image_but.setImageResource(R.drawable.jyxx_tj);
            mholder.checkbox.setVisibility(View.GONE);
        }else {
            mholder.textView22.setVisibility(View.GONE);
            mholder.rela_xx.setVisibility(View.VISIBLE);
            mholder.checkbox.setVisibility(View.VISIBLE);
            if (list.get(position-1).getImgList() != null && list.get(position-1).getImgList().size() > 0) {
                Utils.loadImage(mholder.image_but, list.get(position-1).getImgList().get(0).getImgUrl());
            }else {
                mholder.image_but.setImageResource(R.drawable.jygl_tz);
            }
            mholder.text_name.setText(list.get(position-1).getTitle());
            mholder.text_money.setText(list.get(position-1).getPrice());
        }

        mholder.rel_but.setOnClickListener(v -> {
            if (position==0){
                /*跳转发布*/
                mContext.startActivity(new Intent(mContext,PYTradeActivity.class));
            }else {
                /*跳转详情*/
                AppValue.tradingsBean = list.get(position-1);
                mContext.startActivity(new Intent(mContext, TradingInfoActivity.class));
            }
        });


        // 根据isSelected来设置checkbox的显示状况
        if (flage) {
            mholder.checkbox.setVisibility(View.GONE);
        } else {
            if (position==0){
                mholder.checkbox.setVisibility(View.GONE);
            }else {
                mholder.checkbox.setVisibility(View.VISIBLE);
            }
        }

        if (selected.containsKey(position-1))
            mholder.checkbox.setChecked(true);
        else
            mholder.checkbox.setChecked(false);
        mholder.checkbox.setChecked(list.get(position).isCheck);
        /*注意这里设置的不是onCheckedChangListener*/
        mholder.checkbox.setOnClickListener(v -> {
            if (list.get(position-1).isCheck) {
                list.get(position-1).isCheck = false;
            } else {
                AppValue.JYxxId = list.get(position-1).getTradingId();
            }
        });

        return convertView;
    }

    public class viewHolder {
        private ImageView image_but;
        private RelativeLayout rela_xx,rel_but;
        private TextView text_name,text_money,textView22;
        private CheckBox checkbox;
    }

}