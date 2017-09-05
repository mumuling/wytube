package com.wytube.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqxb.yecall.MainTabActivity;
import com.cqxb.yecall.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.beans.CarsBean;
import com.wytube.beans.ParkBean;
import com.wytube.utlis.AppValue;
import com.wytube.utlis.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@KActivity(R.layout.fragment_index)
public class IndexActivity extends FragmentActivity {
    @KBind(R.id.vp_shuffling)
    private ViewPager vpShuffling;

    private List<ParkBean.DataBean.ParksBean> parksBeanList;
    private CarsBean.DataBean.CarBean carBean;



    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem;
    private List<SimpleDraweeView> images;
    private List<String> Advert;
    private ViewPagerAdapter adapter;
    String[] tup= {"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170527094707517.jpg",
                        "http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170523175900718.png",
                        "http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170527094500099.jpg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        initView();
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
        Intent intent = new Intent(this,SelectCarsActivity.class);
        startActivity(intent);
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
        startActivity(new Intent(this, HappyActivity.class));
    }

    /*一键开门*/
    @KListener(R.id.linea_yjkm)
    private void linea_yjkmOnClick() {
        startActivity(new Intent(this, OneKeyActivity.class));
    }

    /*密码开门*/
    @KListener(R.id.linea_mmkm)
    private void linea_mmkmOnClick() {
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


    private void initView() {
        Advert = new ArrayList<String>();
        //显示的图片
        images = new ArrayList<SimpleDraweeView>();
        for (int i = 0; i < tup.length; i++) {
            Advert.add(tup[i]);
            SimpleDraweeView imageView = new SimpleDraweeView(this);
            Uri uri = Uri.parse(Advert.get(i));
            imageView.setImageURI(uri);
            images.add(imageView);
        }
        adapter = new ViewPagerAdapter();
        vpShuffling.setAdapter(adapter);
    }

    /**
     * 自定义Adapter
     * @author liuyazhuang
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {return images.size();}

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {return arg0 == arg1;}

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }
    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ViewPageTask(), 1, tup.length,
                TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    private class ViewPageTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % tup.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            vpShuffling.setCurrentItem(currentItem);
        }
    };


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
