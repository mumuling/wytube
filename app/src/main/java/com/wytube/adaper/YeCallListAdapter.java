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
import com.cqxb.yecall.R;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.wytube.beans.BeasOpen;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.shared.ToastUtils;
import com.wytube.utlis.SipCore;

import java.util.List;
import java.util.Map;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/3.
 * 类 描 述:
 */

public class YeCallListAdapter extends BaseAdapter {
    Context mContext;
    viewHolder mholder;
    private List<Map<String, Object>> list;
    String phone;

    public YeCallListAdapter(Context mContext, List<Map<String, Object>> list) {
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

        phone = SettingInfo.getParams(PreferenceBean.USERNAME, "");
        mholder.community.setText(list.get(position).get("name").toString());
        mholder.equipment.setText(list.get(position).get("content").toString());
        mholder.clickBtn.setOnClickListener(v -> {
            if (Integer.parseInt(list.get(position).get("doorType").toString())==1111){
                loadopen(phone,list.get(position).get("doorId").toString());
            }
            else if (Integer.parseInt(list.get(position).get("doorType").toString())==3333){
                /*发送消息内容*/
                SipCore.BsendMessage(list.get(position).get("sip").toString(),
                        list.get(position).get("serial").toString());
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
            if (beans == null) {
                ToastUtils.showToast(mContext,"网络异常!");
                return false;
            }
            if (beans.getMessage().equals("OK")){
                Toast.makeText(mContext, "开门成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext, "开门失败", Toast.LENGTH_SHORT).show();
            }
            return false;
        }));
    }
}
