package com.wytube.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.cqxb.yecall.R;
import com.cqxb.yecall.Smack;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/28.
 * 类 描 述:
 */

@KActivity(R.layout.community_menu)
public class CommunityActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
    }

    private long exitTime = 0;// 退出程序时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(CommunityActivity.this, "再按一次结束程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
//                System.exit(0);
                sendBroadcast(new Intent(Smack.action).putExtra("backMune",
                        "backMune"));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
