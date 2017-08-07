package com.wytube.holder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.wytube.beans.OwnerBean;

/**
 * Created by LIN on 2017/8/6.
 */

public class OwerUnitHolder extends BaseOwnerHolder{
    private Activity mActivity;
    private TextView mOwner;
    public OwerUnitHolder(ViewGroup parent, Activity mActivity) {
        super(LayoutInflater.from(mActivity).inflate(R.layout.owner_yezuitem,parent,false));
        this.mActivity = mActivity;
        mOwner = (TextView) itemView.findViewById(R.id.tv_yezu);
    }

    @Override
    public void bindData(OwnerBean.DataBean dataBean,int ...position) {
        for (int i = 0; i < position.length; i++) {
            if (position[0] == 0){
                mOwner.setText("五单元");
            }else {
                mOwner.setText("四单元");
            }
        }

    }
}
