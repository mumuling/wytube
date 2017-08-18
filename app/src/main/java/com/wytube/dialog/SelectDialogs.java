package com.wytube.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cqxb.yecall.R;
import com.wytube.shared.WheelView;
import com.wytube.utlis.AppValue;


/**
 * 选择对话框
 */

public class SelectDialogs {

    private Context mContext;
    private Dialog mDialog;
    private WheelView wheelView;
    View viewDialog;
    private int selected;
    private String item;
    public static SelectDialogs selectDialogs;
//    private static SelectCallBack callBack;


    public SelectDialogs(Context context) {
        mContext = context;
    }

    public Dialog loadDialog() {
        mDialog = new Dialog(mContext, R.style.dialog1);
        LayoutInflater in = LayoutInflater.from(mContext);
        viewDialog = in.inflate(R.layout.activity_selects, null);
        wheelView = (WheelView) viewDialog.findViewById(R.id.wheel_view);
//        if (AppValue.selectBut != null && AppValue.selectBut.length() > 0) {
//            ((TextView) viewDialog.findViewById(R.id.select_text)).setText(AppValue.selectBut);
//        } else {
//            ((TextView) viewDialog.findViewById(R.id.select_text)).setText("绑定小区");
//        }
        viewDialog.findViewById(R.id.select_text).setOnClickListener(v -> {
            mDialog.dismiss();
        });
        init();
        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        mDialog.dismiss();
    }

    private void init() {
        wheelView.setItems(AppValue.items);
        wheelView.setOffset(1);
        wheelView.setSeletion(0);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                SelectDialogs.this.selected = selectedIndex;
                SelectDialogs.this.item = item;
            }
        });
    }
}
