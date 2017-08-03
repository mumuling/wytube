package com.wytube.adaper;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.action.param.NetParam;
import com.cqxb.until.ShakeListener;
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.wytube.beans.BeasOpen;
import com.wytube.beans.YeCallBeans;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.utlis.SipCore;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/3.
 * 类 描 述:
 */

public class YeCallListAdapters extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private ShakeListener mShaker;
    private List<YeCallBeans.DataBean> list;
    String phone;

    public YeCallListAdapters(Context mContext, List<YeCallBeans.DataBean> list) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.yecall_list_view, null);
            mholder.community = (TextView) convertView.findViewById(R.id.yecall_community);
            mholder.equipment = (TextView) convertView.findViewById(R.id.yecall_equipment);
            mholder.clickBtn = (ImageView) convertView.findViewById(R.id.yecall_click_btn);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        phone = SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");
        mholder.community.setText(list.get(position).getCommunityName());
        mholder.equipment.setText(list.get(position).getDoorName());
        mholder.clickBtn.setOnClickListener(v -> {
            if (list.get(position).getDoorType()==1111){
                loadopen(phone,list.get(position).getDoorId());
            } else if (list.get(position).getDoorType()==3333){
                /*发送消息内容*/
                SipCore.sendMessage(list.get(position));
                Toast.makeText(mContext, "开门成功", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public class viewHolder {
        private TextView community;
        private TextView equipment;
        private ImageView clickBtn;
        private LinearLayout Linear;
    }

    private BeasOpen beans;
    /**
     * 获取远程开门数据
     */
    private void loadopen(String phone,String door) {
        Client.sendPost(NetParam.USR_OPEN, "door="+door+"&phone="+phone, new Handler(msg -> {
            String json = msg.getData().getString("post");
            beans = Json.toObject(json, BeasOpen.class);
            if (beans.getMessage().equals("OK")){
                Toast.makeText(mContext, "开门成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, "开门失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }));
    }
}
