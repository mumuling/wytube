package com.wytube.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wytube.beans.OwnerBean;

/**
 * Created by LIN on 2017/8/6.
 */

public abstract class BaseOwnerHolder extends RecyclerView.ViewHolder {
    public BaseOwnerHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindData(OwnerBean.DataBean dataBean,int ...position);
}
