package com.wytube.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.beans.OwnerBean;

/**
 * Created by LIN on 2017/8/6.
 */
@KActivity(R.layout.item_owner_info)
public class OwnerItemActivity extends BaseActivity{
    @KBind(R.id.owneradress)
    private TextView owneradress;
    @KBind(R.id.ownerName)
    private TextView ownerName;
    @KBind(R.id.ownerPhone)
    private TextView ownerPhone;
    @KBind(R.id.ownerType)
    private TextView ownerType;
    @KBind(R.id.ll_tellowner)
    private LinearLayout ll_tellowner;
    @KBind(R.id.tv_date_time)
    private TextView tv_date_time;
    @KBind(R.id.tv_edit)
    private TextView tv_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.title_text).setOnClickListener(v -> {
            finish();
        });
        OwnerBean.DataBean databean = (OwnerBean.DataBean) getIntent().getSerializableExtra("databean");
        owneradress.setText(databean.getBuildingName()+databean.getUnitName()+databean.getRoomNum());
        ownerName.setText(databean.getOwnerName());
        ownerPhone.setText(databean.getOwnerPhone());
        if (databean.getOwnerType()==0) {
            ownerType.setText("业主");
        }else {
            ownerType.setText("租客");
        }
        tv_date_time.setText(databean.getCreateDate());
        ll_tellowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ownerPhone.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

}