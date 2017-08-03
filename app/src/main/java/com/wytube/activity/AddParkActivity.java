package com.wytube.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.BindBean;
import com.wytube.beans.CarsBean;
import com.wytube.beans.CreateBean;
import com.wytube.beans.ParkBean;
import com.wytube.dialog.SelectDialog;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;


@KActivity(R.layout.activity_add_park)
public class AddParkActivity extends Activity implements SelectDialog.SelectCallBack {

    @KBind(R.id.car_user)
    private EditText mCarUser;
    @KBind(R.id.phone_num)
    private EditText mPhoneNum;
    @KBind(R.id.car_num)
    private EditText mCarNum;
    @KBind(R.id.select)
    private TextView mSelect;
    private List<ParkBean.DataBean.ParksBean> parksBeanList;
    private String parkId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        mPhoneNum.setText(AppValue.sipName);
        findViewById(R.id.back_but).setOnClickListener(v -> {finish();});
        findViewById(R.id.title_text).setOnClickListener(v -> {finish();});

    }

    @KListener(R.id.add_stop)
    private void add_stopOnClick() {
        getAllParks();
    }

    @KListener(R.id.save_but)
    private void save_butOnClick() {
        String user = mCarUser.getText().toString();
        String phone = mPhoneNum.getText().toString();
        String car = mCarNum.getText().toString();
        if (user.length() <= 0) {
            Utils.showOkDialog(this, "请填写车主名称!");
            return;
        }
        if (phone.length() <= 0) {
            Utils.showOkDialog(this, "请填写车主电话!");
            return;
        }
        if (car.length() <= 0) {
            Utils.showOkDialog(this, "请填写车牌!");
            return;
        }
        if (parkId.length() <= 0) {
            Utils.showOkDialog(this, "请选择停车场!");
            return;
        }
        postData(user, phone, car);
    }

    /**
     * 提交数据
     */
    private void postData(String user, String phone, String car) {
        Utils.showLoad(this);
        String keyValue = "phone=" + phone + "&num=" + car + "&name" + user;
        Client.sendPost(NetParmet.CREATE_NEW_CAR, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            CreateBean bean = Json.toObject(json, CreateBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            selectCarId(phone);
            return false;
        }));
    }

    /**
     * 查询CarId
     *
     * @param phone 车主手机号
     */
    private void selectCarId(String phone) {
        Utils.showLoad(this);
        Client.sendGet(NetParmet.GET_ALL_CARS, "phone=" + AppValue.sipName, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("get");
            CarsBean bean = Json.toObject(json, CarsBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            if (bean.getData() == null || bean.getData().size() <= 0) {
                Utils.showOkDialog(this, "系统繁忙,请稍后再试!");
                return false;
            }
            String carId = bean.getData().get(0).getCar().getCarId();
            bindPark(phone, parkId, carId);
            return false;
        }));
    }

    /**
     * 获取所有停车场信息
     */
    private void getAllParks() {
        Utils.showLoad(this);
        Client.sendPost(NetParmet.GET_ALL_PARKS, "rows=10000", new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            ParkBean bean = Json.toObject(json, ParkBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            SelectDialog.setCallBack(this);
            parksBeanList = bean.getData().getParks();
            List<String> item = new ArrayList<>();
            for (ParkBean.DataBean.ParksBean parksBean : bean.getData().getParks()) {
                item.add(parksBean.getName());
            }
            AppValue.items = item;
            AppValue.selectBut = "选择";
            startActivity(new Intent(this, SelectDialog.class));
            return false;
        }));
    }

    /**
     * 绑定停车场
     */
    private void bindPark(String phone, String parkId, String carId) {
        Utils.showLoad(this);
        String keyValue = "phone=" + phone + "&parkId=" + parkId + "&carId=" + carId;
        Client.sendPost(NetParmet.BIND_USER_PARK, keyValue, new Handler(msg -> {
            Utils.exitLoad();
            String json = msg.getData().getString("post");
            BindBean bean = Json.toObject(json, BindBean.class);
            if (bean == null) {
                Utils.showNetErrorDialog(this);
                return false;
            }
            if (!bean.isSuccess()) {
                Utils.showOkDialog(this, bean.getMessage());
                return false;
            }
            Toast.makeText(this, "创建成功", Toast.LENGTH_SHORT).show();
            if (AppValue.chel == 1) {
                 /*从我的车辆进入添加成功返回*/
                AppValue.fish = 1;
                this.finish();
            }else {
                startActivity(new Intent(this, SelectCarsActivity.class));
                this.finish();
            }
            return false;
        }));
    }

    @Override
    public void OnSelected(int postion, String str) {
        mSelect.setText(str);
        parkId = parksBeanList.get(postion).getParkId();
    }
}
