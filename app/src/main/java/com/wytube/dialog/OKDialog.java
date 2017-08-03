package com.wytube.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxb.yecall.R;


//@KActivity(R.layout.activity_okdialog)
public class OKDialog extends Activity {

    private TextView mTips;
    private LinearLayout mOkBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okdialog);
        mTips = (TextView) findViewById(R.id.tips);
        mOkBut = (LinearLayout) findViewById(R.id.ok_but);
        String text = getIntent().getStringExtra("tips");
        if (text != null) {
            mTips.setText(text);
        }
        mOkBut.setOnClickListener(v -> finish());
    }
}
