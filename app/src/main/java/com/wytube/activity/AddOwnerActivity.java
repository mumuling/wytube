package com.wytube.activity;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.BaseActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.adaper.BuildAdapter;
import com.wytube.adaper.JYDialogAdapters;
import com.wytube.adaper.UnitAdapter;
import com.wytube.beans.BaseOK;
import com.wytube.beans.BuildBean;
import com.wytube.beans.RoomBean;
import com.wytube.beans.UnitBean;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by LIN on 2017/8/6.
 */
@KActivity(R.layout.add_owner)
public class AddOwnerActivity extends BaseActivity {

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
    @KBind(R.id.selectownertypeLine)
    private RelativeLayout selectownertypeLine;
    /*@KBind(R.id.selectowneroomLine)
    private RelativeLayout selectowneroomLine;*/
    @KBind(R.id.cv_agreebtn)
    private CardView cv_agreebtn;
    @KBind(R.id.tv_yzroom)
    private EditText tv_yzroom;
    @KBind(R.id.tv_blcokcontact)
    private TextView tv_blcokcontact;
    @KBind(R.id.ownercontact)
    private TextView ownercontact;
    @KBind(R.id.lv_blocolist)
    private ListView lv_blocolist;
    @KBind(R.id.lv_unitlist)
    private ListView lv_unitlist;
    @KBind(R.id.lv_roomlist)
    private ListView lv_roomlist;

    private LayoutInflater inflater;
    private Dialog dialog1;
    private Dialog dialog2;
    private Dialog dialog3;
    private List<BuildBean.DataBean> list;
    private BuildBean bean;
    private List<UnitBean.DataBean> mlist;
    private UnitBean unitbean;
    private RoomBean roombean;
    private String buildingId,unitId;
    int tradeType;

    String[] dialogtitle = {"业主","亲属","租客","其他"};
    int [] types = {0,1,2,3};
    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < dialogtitle.length; i++) {
            Map<String, Object> map= new HashMap<>();
            map.put("title", dialogtitle[i]);
            map.put("type", types[i]);
            list.add(map);
        }
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        //选择楼宇
        selectblockLine.setOnClickListener(view -> dialogBuild());
        //选择单元
        selectunitLine.setOnClickListener(view -> dialogUnit());
        //选择类型
        selectownertypeLine.setOnClickListener(view -> {
            if (!tv_yzroom.getText().toString().equals("")){
                loadroom();
                dialogType();
            }else {
                Toast.makeText(this, "请先输入房间号!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //选择类型
    List<Map<String, Object>> zflist;
    private void dialogType() {
        dialog3 = blocoList();
        View view = inflater.inflate(R.layout.activity_news_dilogtxts, null);
        ListView canshu_list = (ListView) view.findViewById(R.id.canshu_list);
        RelativeLayout rela_djxs = (RelativeLayout) view.findViewById(R.id.rela_djxs);
        dialog3.getWindow().setContentView(view);
        rela_djxs.setOnClickListener(v -> dialog3.dismiss());
        zflist=getData();
        JYDialogAdapters adapter = new JYDialogAdapters(this, zflist);
        canshu_list.setAdapter(adapter);
        canshu_list.setOnItemClickListener((parent, view1, position, id) -> {
            ownercontact.setText(zflist.get(position).get("title").toString());
            tradeType = (int) zflist.get(position).get("type");
            dialog3.dismiss();
        });
    }

    private void loadroom() {
        Client.sendPost(NetParmet.OWNER_ROOM, "unitId=" + unitId +"&roomNum="+tv_yzroom.getText(), new Handler(msg -> {
            String json = msg.getData().getString("post");
            roombean = Json.toObject(json, RoomBean.class);
            if (roombean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!roombean.isSuccess()) {
                Utils.showOkDialog(this, roombean.getMessage());
                return false;
            }
            return false;
        }));
    }


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
        String roomNum = tv_yzroom.getText().toString();
        String name = tv_yzxm.getText().toString();
        String phone = tv_yzphone.getText().toString();
        if (tv_blcokcontact.getText().equals("选择楼宇")){
            Utils.showOkDialog(this, "请选择楼宇!");
            return;
        }
        if (tv_xzdy.getText().equals("选择单元")){
            Utils.showOkDialog(this, "请选择单元!");
            return;
        }
        if (ownercontact.getText().equals("选择住户类型")){
            Utils.showOkDialog(this, "请选择住户类型!");
            return;
        }
        if (name.length() <= 0) {
            Utils.showOkDialog(this, "请输入业主姓名!");
            return;
        }
        if (phone.length() <= 0) {
            Utils.showOkDialog(this, "请输入业主电话!");
            return;
        }
        if (roomNum.length() <= 0) {
            Utils.showOkDialog(this, "请输入房间号!");
            return;
        }
        Utils.showLoad(this);
        String keyValue = "buildingId=" + buildingId+ "&unitId=" + unitId + "&roomId=" +
                roombean.getData().getRoomId()+ "&roomNum=" + roomNum + "&ownerType=" + tradeType + "&name=" + name + "&phone=" + phone;
        Client.sendPost(NetParmet.OWNER_CREATE, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BaseOK bean = Json.toObject(json, BaseOK.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }else {
                AppValue.fish=1;
                Toast.makeText(this, "添加业主成功!", Toast.LENGTH_SHORT).show();
                this.finish();
            }
            return false;
        }));
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
