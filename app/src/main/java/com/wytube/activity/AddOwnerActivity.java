package com.wytube.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.wytube.dialog.OwnerSelectDialog;

import static com.cqxb.yecall.R.id.tv_yezhuxm;

/**
 * Created by LIN on 2017/8/6.
 */
@KActivity(R.layout.add_owner)
public class AddOwnerActivity extends BaseActivity{
    @KBind(tv_yezhuxm)
    private EditText tv_yzxm;
    @KBind(R.id.selectblockLine)
    private RelativeLayout selectblockLine;
    @KBind(R.id.selectunitLine)
    private RelativeLayout selectunitLine;
    @KBind(R.id.selectownerLine)
    private RelativeLayout selectownerLine;
    @KBind(R.id.tv_yzphone)
    private EditText tv_yzphone;
    @KBind(R.id.tv_unit)
    private TextView tv_xzdy;
    @KBind(R.id.tv_yzroom)
    private EditText tv_yzroom;
    @KBind(R.id.selectownertypeLine)
    private RelativeLayout selectownertypeLine;
    @KBind(R.id.cv_agreebtn)
    private CardView cv_agreebtn;
    @KBind(R.id.contact)
    private TextView tvcontact;
    @KBind(R.id.tv_blcokcontact)
    private TextView tv_blcokcontact;
    @KBind(R.id.ownercontact)
    private TextView ownercontact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        selectunitLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OwnerSelectDialog ownerSelectDialog = new OwnerSelectDialog(AddOwnerActivity.this);
                ownerSelectDialog.setOnSelectListener(new OwnerSelectDialog.OnSelectListener() {
                    @Override
                    public void select(String s) {
                        tv_xzdy.setText(s);
                    }
                });
                ownerSelectDialog.show();
            }
        });
    }

   /* @KListener(R.id.cv_agreebtn)
    private void cv_agreebtnOnClick(){
        addowner(tv_yzxm.getText().toString(),tv_yzphone.getText().toString(),tv_yzroom.getText().toString(),tvcontact.getText().toString(),tv_blcokcontact.getText().toString(),tv_xzdy.getText().toString(),ownercontact.getText().toString());
    }
    private void addowner(String s, String name, String phone, String ownerType, String buildingName, String unitName, String roomNum) {
            Utils.showLoad(this);
            String keyValue = "name=" + name+ "&phone="+phone+"&ownerType="+ownerType+"&buildingName="+buildingName+"&unitName="+unitName+"&roomNum="+roomNum;
            Client.sendPost(NetParmet.OWNER_CREATE, keyValue, new Handler(msg -> {
                Utils.exitLoad();
                String json = msg.getData().getString("post");
                Log.i("-----添加-----", json);
                tv_yzxm.setText("");
                tv_yzphone.setText("");
                tv_yzroom.setText("");
                tvcontact.setText("");
                tv_blcokcontact.setText("");
                tv_xzdy.setText("");
                finish();
                return false;
            }));
        }*/

}
