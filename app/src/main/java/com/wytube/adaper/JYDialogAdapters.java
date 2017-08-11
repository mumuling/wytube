package com.wytube.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;

import java.util.List;
import java.util.Map;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 物业通知适配器
 */

public class JYDialogAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<Map<String, Object>> data;

    public JYDialogAdapters(Context mContext, List<Map<String, Object>> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_dialog_news, null);
            mholder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            mholder.rela_xz = (RelativeLayout) convertView.findViewById(R.id.rela_xz);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
//        BaseSHfw.DataBean bean = data.get(position);
        mholder.text_name.setText(data.get(position).get("title").toString());
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
        private RelativeLayout rela_xz;
    }






}
