package com.cqxb.yecall;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.cqxb.yecall.until.SharedOption;

/**
 * 创建时间: 2017/2/10.
 * 类 描 述: 自动开门设置界面
 */

public class OpenDoorSettings extends BaseTitleActivity {

    private static LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door);
        setTitle("自动开门");
        setCustomLeftBtn(R.drawable.fanhui, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDoorSettings.this.finish();
            }
        });
        if (SharedOption.readerString("OPEN_GPS", this) != null && SharedOption.readerString("OPEN_GPS", this).equals("TRUE")) {
            ((ToggleButton) findViewById(R.id.open_gps)).setChecked(true);
        } else {
            ((ToggleButton) findViewById(R.id.open_gps)).setChecked(false);
        }
    }

    public void openGPS(View view) {
        ToggleButton button = (ToggleButton) view;
        if (button.isChecked()) {
            while (!isOPen(this)) {
                openGPS(this);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SharedOption.writerString("OPEN_GPS", "TRUE", this);
        } else {
            SharedOption.writerString("OPEN_GPS", "FALSE", this);
        }
    }

    public static boolean isOPen(final Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
