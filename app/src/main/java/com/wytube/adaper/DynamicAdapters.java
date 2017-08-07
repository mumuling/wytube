package com.wytube.adaper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.ImageActivity;
import com.wytube.beans.DynamicBean;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;



/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/28.
 * 类 描 述:
 */

public class DynamicAdapters extends BaseAdapter{
    Context mContext;
    viewHolder mholder;
    private int mPosition;
    int mCurrentPos;
    private List<DynamicBean.DataBean.TracksBean> list;

    public DynamicAdapters(Context mContext, List<DynamicBean.DataBean.TracksBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public void setCurrentPosition(int position){
        this.mCurrentPos = position;
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            mholder = new viewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_layout, null);
            mholder.userHead = (ImageView) convertView.findViewById(R.id.user_head);
            mholder.userName = (TextView) convertView.findViewById(R.id.user_name);
            mholder.userTime = (TextView) convertView.findViewById(R.id.user_time);
            mholder.userContext = (TextView) convertView.findViewById(R.id.user_context);
            mholder.imageList = (LinearLayout) convertView.findViewById(R.id.img_list);
            mholder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        mPosition = position;
        Utils.loadImage(mholder.userHead, ((String) list.get(position).getOwnerPic()));
        mholder.userName.setText(list.get(position).getOwnerName());
        mholder.userTime.setText(list.get(position).getCreateDate());
        mholder.userContext.setText(list.get(position).getContent());

        mholder.imageList.removeAllViews();
        for (DynamicBean.DataBean.TracksBean.TrackPicsBean trackPicsBean : list.get(position).getTrackPics()) {
            createAndAddImage(mholder.imageList, trackPicsBean.getUrl());
        }
        return convertView;
    }

    public class viewHolder {
        private ImageView userHead;
        private TextView userName, userTime, userContext;
        private LinearLayout imageList;
        private CheckBox checkbox;
    }

    /**
     * 创建并添加图片
     *
     * @param list 图片列表容器
     */
    private void createAndAddImage(LinearLayout list, String imageUrl) {
        final ImageView view = new ImageView(this.mContext);
        view.setOnClickListener(v -> {
            AppValue.tempImage = imageUrl;
            mContext.startActivity(new Intent(mContext, ImageActivity.class));
        });
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(128), dip2px(128));
        params.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        view.setLayoutParams(params);
        Utils.loadImage(view, imageUrl);
        list.addView(view);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = this.mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setBeans(List<DynamicBean.DataBean.TracksBean> beans) {
        this.list = beans;
    }
}
