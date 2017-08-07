package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.BaseZPry;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业通知适配器
 */

public class PropDialogAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<BaseZPry.DataBean> list;

    public PropDialogAdapters(Context mContext, List<BaseZPry.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_dialog_txt, null);
            mholder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            mholder.imag_gx = (ImageView) convertView.findViewById(R.id.imag_gx);
            mholder.rela_xz = (RelativeLayout) convertView.findViewById(R.id.rela_xz);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        BaseZPry.DataBean bean = list.get(position);
        mholder.text_name.setText(bean.getRepairmanName());
//        mholder.rela_xz.setOnClickListener(v -> {
//            AppValue.TxtName = mholder.text_name.getText().toString();
//            Toast.makeText(mContext, AppValue.TxtName+"", Toast.LENGTH_SHORT).show();
//        });
//        mholder.imag_gx.setImageResource(R.drawable.ty_xq2);
//        mholder.imag_gx.setImageResource(R.drawable.ty_xq);

        return convertView;
    }

    public class viewHolder {
        private TextView text_name;
        private ImageView imag_gx;
        private RelativeLayout rela_xz;
    }






}
