package com.wytube.activity;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.BuildAdapter;
import com.wytube.adaper.UnitAdapter;
import com.wytube.beans.BuildBean;
import com.wytube.beans.UnitBean;
import com.wytube.beans.UserTypeBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by LIN on 2017/8/6.
 */
@KActivity(R.layout.add_owner)
public class AddOwnerActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = AddOwnerActivity.class.getSimpleName();
    @KBind(R.id.tv_yezhuxm)
    private EditText tv_yzxm;
    @KBind(R.id.selectblockLine)
    private RelativeLayout selectblockLine;
    @KBind(R.id.selectunitLine)
    private RelativeLayout selectunitLine;
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
    @KBind(R.id.tv_blcokcontact)
    private TextView tv_blcokcontact;
    @KBind(R.id.ownercontact)
    private TextView ownercontact;
    private LayoutInflater inflater;
    private Dialog dialog1;
    private Dialog dialog2;
    private Dialog dialog3;
    @KBind(R.id.lv_blocolist)
    private ListView lv_blocolist;
    private List<BuildBean.DataBean> list;
    private BuildBean bean;
    @KBind(R.id.lv_unitlist)
    private ListView lv_unitlist;
    private List<UnitBean.DataBean> mlist;
    private UnitBean unitbean;
    @KBind(R.id.lv_typelist)
    private ListView lv_typelist;
    private List<UserTypeBean.DataBean> lists;
    private UserTypeBean typebean;

    private TextView tv_ownerye, tv_qinshu, tv_zuke, tv_other;


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


        //选择楼宇
        selectblockLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuild();
            }
        });
        //选择单元
        selectunitLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUnit();
            }

        });
        //选择类型
        selectownertypeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogType();
            }
        });
    }

    //选择类型
    String unitTypeId;

    private void dialogType() {
        dialog3 = blocoList();
        View view = inflater.inflate(R.layout.ownertypedialog, null);
        lv_typelist = (ListView) view.findViewById(R.id.lv_typelist);
        RelativeLayout rlrelatype = (RelativeLayout) view.findViewById(R.id.rlrelatype);


        // 在这里获取对应的控件，并注册监听事件
        tv_ownerye = (TextView) view.findViewById(R.id.tv_ownerye);
        tv_qinshu = (TextView) view.findViewById(R.id.tv_qinshu);
        tv_zuke = (TextView) view.findViewById(R.id.tv_zuke);
        tv_other = (TextView) view.findViewById(R.id.tv_other);
        tv_ownerye.setOnClickListener(this);
        tv_qinshu.setOnClickListener(this);
        tv_zuke.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        dialog3.getWindow().setContentView(view);
       /* rlrelatype.setOnClickListener(v -> dialog3.dismiss());
        loadtype(buildingId);
        lv_typelist.setOnItemClickListener((parent, view1, position, id) -> {
            ownercontact.setText(typebean.getData().get(position).getUnitTypeName());
            unitTypeId = typebean.getData().get(position).getUnitTypeId();
            dialog3.dismiss();
        });*/
    }

    /*private void loadtype(String buildingId) {
        Log.i(TAG, buildingId);
        if (TextUtils.isEmpty(buildingId)) {
            return;
        }
        Client.sendPost(NetParmet.OWNER_YTPE, "unitId=" + unitId, new Handler(msg -> {
            String json = msg.getData().getString("post");
            typebean = Json.toObject(json, UserTypeBean.class);
            lists = typebean.getData();
            TypeAdapter adapter = new TypeAdapter(this, lists);
            lv_typelist.setAdapter(adapter);

            return false;
        }));
    }
*/

    //单元
    String unitId;
    private void dialogUnit() {
        dialog2 = blocoList();
        View view = inflater.inflate(R.layout.ownerunitdialog, null);
        lv_unitlist = (ListView) view.findViewById(R.id.lv_unitlist);
        RelativeLayout rlrela = (RelativeLayout) view.findViewById(R.id.rlrela);
        dialog2.getWindow().setContentView(view);
        rlrela.setOnClickListener(v -> dialog2.dismiss());
        loadbunit();
        lv_unitlist.setOnItemClickListener((parent, view1, position, id) -> {
            tv_xzdy.setText(unitbean.getData().get(position).getUnitName());
            unitId = unitbean.getData().get(position).getUnitId();
            dialog2.dismiss();
        });
    }

    private void loadbunit() {
        Client.sendPost(NetParmet.OWNER_UTIN, "buildingId=" + buildingId, new Handler(msg -> {
            String json = msg.getData().getString("post");
            unitbean = Json.toObject(json, UnitBean.class);
            mlist = unitbean.getData();
            UnitAdapter adapter = new UnitAdapter(this, mlist);
            lv_unitlist.setAdapter(adapter);
            return false;
        }));
    }

    /* 楼宇*/
    String buildingId;

    //dialog对话框
    public void dialogBuild() {
        dialog1 = blocoList();
        View view = inflater.inflate(R.layout.ownerselectdialog, null);
        lv_blocolist = (ListView) view.findViewById(R.id.lv_blocolist);
        RelativeLayout rela = (RelativeLayout) view.findViewById(R.id.relarl);
        dialog1.getWindow().setContentView(view);
        rela.setOnClickListener(v -> dialog1.dismiss());
        loadblocok();
        lv_blocolist.setOnItemClickListener((parent, view1, position, id) -> {
            tv_blcokcontact.setText(bean.getData().get(position).getBuildingName());
            buildingId = bean.getData().get(position).getBuildingId();
            dialog1.dismiss();
        });
    }

    //请求楼宇列表
    private void loadblocok() {
        Client.sendPost(NetParmet.OWNER_BUILD, "", new Handler(msg -> {
            String json = msg.getData().getString("post");
            bean = Json.toObject(json, BuildBean.class);
            list = bean.getData();
            BuildAdapter adapter = new BuildAdapter(this, list);
            lv_blocolist.setAdapter(adapter);
            return false;
        }));
    }

    private Dialog blocoList() {
        Rect bloco = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(bloco);
        inflater = LayoutInflater.from(AddOwnerActivity.this);
        Dialog dialog = new Dialog(AddOwnerActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setDimAmount(0.3f);
        DisplayMetrics dm2 = AddOwnerActivity.this.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = bloco.width(); //(int) (dm2.widthPixels); // 设置宽度
        lp.height = bloco.height(); //(int) (dm2.heightPixels-titleBarHeight); // 设置高度
        dialog.getWindow().setAttributes(lp);
        getWindow().getDecorView().getWindowVisibleDisplayFrame(bloco);
        return dialog;
    }

    /**
     * 参数名	    必选	类型	说明
     * buildingId	是	String	楼宇ID
     * unitId	    是	String	单元ID
     * roomId	    是	String	房间ID
     * roomNum	    是	String	房间号
     * ownerType	是	String	业主类型
     * name	        是	String	姓名
     * phone	    是	String	电话
     */
    @KListener(R.id.cv_agreebtn)
    private void cv_agreebtnOnClick() {
        Utils.showLoad(this);
        String build = tv_blcokcontact.getText().toString();
        String unit = tv_xzdy.getText().toString();
        String roomNumb = tv_yzroom.getText().toString();
        String type = ownercontact.getText().toString();
        String name = tv_yzxm.getText().toString();
        String phone = tv_yzphone.getText().toString();
        String keyValue = "buildingId=" + build + "&unitId=" + unit + "&roomId=" +
                "" + "&roomNum=" + roomNumb + "&ownerType=" + type + "&name=" + name + "&phone=" + phone;
        Client.sendPost(NetParmet.OWNER_CREATE, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            Log.v(TAG, keyValue);
            Log.i("-----添加-----", json);
            Log.i("----------", keyValue);
            return false;

        }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ownerye:
                ownercontact.setText(tv_ownerye.getText().toString());
            case R.id.tv_qinshu:
                ownercontact.setText(tv_qinshu.getText().toString());
                break;
            case R.id.tv_zuke:
                ownercontact.setText(tv_zuke.getText().toString());
                break;
            case R.id.tv_other:
                ownercontact.setText(tv_other.getText().toString());
                break;
        }
        dialog3.dismiss();

    }


    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
