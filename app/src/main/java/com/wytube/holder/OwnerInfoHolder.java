package com.wytube.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.activity.OwnerItemActivity;
import com.wytube.beans.OwnerBean;

/**
 * Created by LIN on 2017/8/6.
 */

public class OwnerInfoHolder extends BaseOwnerHolder {


    private TextView tv_yename;
    private TextView tv_yedz;
    private TextView tv_yztype;


    private Activity mActivity;
    public OwnerInfoHolder(ViewGroup parent, Activity mActivity) {
        super(LayoutInflater.from(mActivity).inflate(R.layout.item_owner,parent,false));
        this.mActivity = mActivity;
        tv_yename = (TextView) itemView.findViewById(R.id.tv_yename);
        tv_yedz = (TextView) itemView.findViewById(R.id.tv_yedz);
        tv_yztype = (TextView) itemView.findViewById(R.id.tv_yztype);
    }

    @Override
    public void bindData(OwnerBean.DataBean dataBean,int ...position) {
        tv_yename.setText(dataBean.getOwnerName());
        if (dataBean.getOwnerType()==0) {
            tv_yztype.setText("业主");
        }else {
            tv_yztype.setText("租客");
        }
        tv_yedz.setText(dataBean.getBuildingName()+dataBean.getUnitName()+dataBean.getRoomNum());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity,OwnerItemActivity.class);
                intent.putExtra("databean",dataBean);
                mActivity.startActivity(intent);
            }
        });

    }
}
