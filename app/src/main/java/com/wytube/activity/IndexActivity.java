package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.cqxb.yecall.MainTabActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.CarsBean;
import com.wytube.beans.ParkBean;
import com.wytube.dialog.SelectDialog;
import com.wytube.net.Client;
import com.wytube.net.Json;
import com.wytube.net.NetParmet;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;

@KActivity(R.layout.fragment_index)
public class IndexActivity extends FragmentActivity {

    private List<ParkBean.DataBean.ParksBean> parksBeanList;
    private CarsBean.DataBean.CarBean carBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
    }


    /*物业推送*/
    @KListener(R.id.door_mang)
    private void door_mangOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, PropertyNoticeActivity.class));
    }

    /*停车缴费*/
    @KListener(R.id.card_pay)
    private void card_payOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        selectCars();
    }

    /*物业费*/
    @KListener(R.id.porpre_pay)
    private void porpre_payOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, PropertyPayActivity.class));
    }

    /*家政管理*/
    @KListener(R.id.comuty_mall)
    private void comuty_mallOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, RepairActivity.class));
    }

    /*业主管理*/
    @KListener(R.id.linea_yzgl)
    private void linea_yzglOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, OwnerActivity.class));
    }

    /*访客通行*/
    @KListener(R.id.linea_fktx)
    private void linea_fktxOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, VisitorInfoActivity.class));
    }

    /*物业考勤*/
    @KListener(R.id.linea_wykq)
    private void linea_wykqOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, AttenceActivity.class));
    }

    /*借用管理*/
    @KListener(R.id.linea_jygl)
    private void linea_jyglOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, BorroActivity.class));
    }

    /*投诉报修管理*/
    @KListener(R.id.linea_tsbxgl)
    private void linea_tsbxglOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, ComplaintActivity.class));
    }

    /*喜事管理*/
    @KListener(R.id.linea_jyxs)
    private void linea_jyxsOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
    }

    /*一键开门*/
    @KListener(R.id.linea_yjkm)
    private void linea_yjkmOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, OneKeyActivity.class));
    }

    /*密码开门*/
    @KListener(R.id.linea_mmkm)
    private void linea_mmkmOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, PassWordActivity.class));
    }

    /*楼宇对讲*/
    @KListener(R.id.linea_lydj)
    private void linea_lydjOnClick() {
        if (!AppValue.online) {
            Utils.showLoginDialog(this);
            return;
        }
        startActivity(new Intent(this, MainTabActivity.class));
    }


    /**
     * 查询所有停车缴费信息
     */
    private void selectCars() {
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
            if (bean.getData() == null || bean.getData().size() <= 1) {
                startActivity(new Intent(this, AddParkActivity.class));
            }
            else {
                AppValue.carsBeans = bean.getData();
                if (bean.getData().get(0).getPark() == null) {
                    carBean = bean.getData().get(0).getCar();
                    loadAllPark();
                } else {
                    Intent intent = new Intent(this,SelectCarsActivity.class);
                    intent.putExtra("chepai",bean.getData().get(0).getCar().getNum());
                    startActivity(intent);
                }
            }
            return false;
        }));
    }


    /**
     * 获取所有停车场信息
     */
    private void loadAllPark() {
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
            parksBeanList = bean.getData().getParks();
            List<String> item = new ArrayList<>();
            for (ParkBean.DataBean.ParksBean parksBean : bean.getData().getParks()) {
                item.add(parksBean.getName());
            }
            SelectDialog.setCallBack((SelectDialog.SelectCallBack) this);
            AppValue.items = item;
            AppValue.selectBut = "绑定";
            startActivity(new Intent(this, SelectDialog.class));
            return false;
        }));
    }

    private long exitTime = 0;// 退出程序时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(IndexActivity.this, "再按一次结束程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
//                sendBroadcast(new Intent(Smack.action).putExtra("backMune",
//                        "backMune"));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
