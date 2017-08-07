package com.wytube.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.ImageActivity;
import com.wytube.activity.RepairInfoActivity;
import com.wytube.beans.RepairBean;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.List;


/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/6/23.
 * 类 描 述: 报修记录列表适配器
 */

public class RepairAdapters extends BaseAdapter {

    Context context;
    viewHolder mholder;
    private List<RepairBean.DataBean> list;

    public RepairAdapters(Context mContext, List<RepairBean.DataBean> list) {
        this.context = mContext;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_repair, null);
            mholder.stateImg = (ImageView) convertView.findViewById(R.id.state_img);
            mholder.repairType = (TextView) convertView.findViewById(R.id.repair_type);
            mholder.repairState = (TextView) convertView.findViewById(R.id.repair_state);
            mholder.conText = (TextView) convertView.findViewById(R.id.content_text);
            mholder.dateTiime = (TextView) convertView.findViewById(R.id.date_time);
            mholder.repairItem = (LinearLayout) convertView.findViewById(R.id.repair_item);
            mholder.imgList = (LinearLayout) convertView.findViewById(R.id.image_list);
            // 设置提示
            convertView.setTag(mholder);
        } else {
            mholder = (viewHolder) convertView.getTag();
        }
        RepairBean.DataBean bean = list.get(position);

        switch (bean.getStateId()) {
            case 0:
                //待受理
                mholder.repairState.setTextColor(context.getResources().getColor(R.color.dark_orange));
                break;
            case 1:
                //派单中
                mholder.repairState.setTextColor(context.getResources().getColor(R.color.colorAccent));
                break;
            case 2:
                //已完成
                mholder.repairState.setTextColor(context.getResources().getColor(R.color.result_view));
                break;
            default:
                mholder.repairState.setVisibility(View.VISIBLE);
                break;
        }
        mholder.repairState.setText(bean.getStateName());
        mholder.repairType.setText(bean.getTypeName());
        mholder.conText.setText(bean.getRepairContent());
        mholder.dateTiime.setText(bean.getStarttime());
        mholder.repairItem.setOnClickListener(v -> {
            AppValue.repairInfoBean = bean;
            Intent intent = new Intent(context, RepairInfoActivity.class);
            context.startActivity(intent);
        });
        mholder.imgList.removeAllViews();
        for (RepairBean.DataBean.ImgListBean imgListBean : bean.getImgList()) {
            createAndAddImage(mholder.imgList, imgListBean.getImgUrl());
        }
        return convertView;
    }

    public class viewHolder {
        private ImageView stateImg;
        private TextView repairType, repairState, conText, dateTiime;
        private LinearLayout repairItem;
        private LinearLayout imgList;
    }

    /**
     * 创建并添加图片
     *
     * @param list 图片列表容器
     */
    private void createAndAddImage(LinearLayout list, String imageUrl) {
        final ImageView view = new ImageView(this.context);
        view.setOnClickListener(v -> {
            AppValue.tempImage = imageUrl;
            context.startActivity(new Intent(context, ImageActivity.class));
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
        final float scale = this.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setBeans(List<RepairBean.DataBean> beans) {
        this.list = beans;
    }
}
