package com.wytube.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cqxb.yecall.LoginAppActivity;
import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;


@KActivity(R.layout.activity_ok_cancel_dialog)
public class TipsLoginDialog extends Activity {

    @KBind(R.id.tips)
    private TextView mTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        mTips.setText("您尚未登录,是否立即前往登录?");
    }

    @KListener(R.id.cancel_but)
    private void cancel_butOnClick() {
        this.finish();
    }

    @KListener(R.id.ok_but)
    private void ok_butOnClick() {
        startActivity(new Intent(this, LoginAppActivity.class));
        Log.i("----------", "跳转登录");
        this.finish();
    }
}
