package com.cqxb.yecall;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ProtocolActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        setTitle("关于我们");
        setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
