package com.wytube.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cqxb.yecall.R;
import com.wytube.shared.WheelView;
import com.wytube.utlis.AppValue;


/**
 * 选择对话框
 */

public class SelectDialog extends Activity {

    private static SelectCallBack callBack;
    private WheelView wheelView;
    private int selected;
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        wheelView = (WheelView) findViewById(R.id.wheel_view);
//        if (AppValue.selectBut != null && AppValue.selectBut.length() > 0) {
//            ((TextView) findViewById(R.id.select_text)).setText(AppValue.selectBut);
//        } else {
//            ((TextView) findViewById(R.id.select_text)).setText("绑定小区");
//        }
        init();
    }

    private void init() {
        wheelView.setItems(AppValue.items);
        wheelView.setOffset(1);
        wheelView.setSeletion(0);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                SelectDialog.this.selected = selectedIndex;
                SelectDialog.this.item = item;
            }
        });
    }

    /**
     * 选择确定回调
     *
     * @param view
     */
    public void selectedExit(View view) {
        if (SelectDialog.callBack != null) {
            callBack.OnSelected(selected - 1, item);
        }
        SelectDialog.this.finish();
    }

    /**
     * 选择回调
     */
    public interface SelectCallBack {
        void OnSelected(int postion, String str);
    }

    public static void setCallBack(SelectCallBack callBack) {
        SelectDialog.callBack = callBack;
    }
}
