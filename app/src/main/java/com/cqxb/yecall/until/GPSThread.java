package com.cqxb.yecall.until;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cqxb.yecall.bean.DoorBean;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2017/2/10.
 * 类 描 述: GPS开门的线程
 */

public class GPSThread extends Thread implements LocationListener {

    private static LocationManager locationManager;

    private Context context;

    private Handler handler, gps_handler;

    private List<DoorBean.DataBean> dataBeen;

    /*HTTP协议*/
    public static final String HTTP_TCP = "http://";

    /*正式服务器*/
    public static final String FORMAL_SERVER = "web.123667.com";

    /*获取设备的接口*/
    public static final String USR_DOORS = HTTP_TCP + FORMAL_SERVER + "/door/api/doors";

    /*开门接口*/
    public static final String OPEN_DOOR_INTERFACE = HTTP_TCP + FORMAL_SERVER + "/door/api/open";

    private float max = 10000;

    private boolean isRegListner = false;

    public GPSThread(Context context) {
        this.context = context;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String   json = msg.getData().getString("json");
                Gson     gson = new Gson();
                DoorBean bean = gson.fromJson(json, DoorBean.class);
                if (bean != null) {
                    dataBeen = bean.getData();
                }
                return false;
            }
        });

        gps_handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                getGPSConfi();
                return false;
            }
        });
    }

    @Override
    public void run() {
        boolean isWhile = true;
        String  user    = "";
        while (isWhile) {
            user = SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");
            if (user != null && user.length() > 0) {
                isWhile = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message message = new Message();
        Bundle  bundle  = new Bundle();
        String  json    = sendPost(USR_DOORS, "phone=" + user);
        bundle.putString("json", json);
        message.setData(bundle);
        this.handler.sendMessage(message);
        while (dataBeen == null) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            String str = SharedOption.readerString("OPEN_GPS", context);
            if (str != null && str.equals("TRUE")) {
                while (!isOPen(context)) {
                    openGPS(context);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gps_handler.sendMessage(new Message());
            } else {
                if (isRegListner) {
                    isRegListner = false;
                    locationManager.removeUpdates(this);
                }
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public void getGPSConfi() {

        isRegListner = true;

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gps) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, this);

        } else {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2, 1, this);
        }

        update(location);
    }

    private void update(Location location) {

        String str = SharedOption.readerString("OPEN_GPS", context);

        if (str != null && str.equals("TRUE")) {
            if (location != null && dataBeen != null) {
                for (final DoorBean.DataBean data : dataBeen) {
                    if (data.getLatitude() == null) continue;
                    Point A = toPoint(data.getLatitude().toString(), data.getLongitude().toString());
                    Point B = toPoint(data.getLeftLatitude().toString(), data.getLeftLongitude().toString());
                    Point C = toPoint(data.getRightLatitude().toString(), data.getRightLongitude().toString());
                    /*Point A = new Point(29.537961 * max, 106.481328 * max);
                    Point B = new Point(29.538323 * max, 106.485751 * max);
                    Point C = new Point(29.534857 * max, 106.482541 * max);*/
                    Point P = new Point(location.getLatitude() * max, location.getLongitude() * max);
                    if (A == null && B == null && C == null) {
                        continue;
                    }
                    /*System.out.println("------------------");
                    System.out.println(A.getX() + "_____" + A.getY());
                    System.out.println(B.getX() + "_____" + B.getY());
                    System.out.println(C.getX() + "_____" + C.getY());
                    System.out.println(P.getX() + "_____" + P.getY());
                    System.out.println("------------------" + isAtABC(A, B, C, P));*/
                    if (isAtABC(A, B, C, P)) {
                        new Thread() {
                            @Override
                            public void run() {
                                String string = sendPost(OPEN_DOOR_INTERFACE, "phone=" + SettingInfo.getParams(PreferenceBean.USERACCOUNT, "") + "&door=" + data.getDoorId());
                                System.out.println("--------OPEN OK-------" + string + " Driver Name = " + data.getDoorName());
                            }
                        }.start();
                    }
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        update(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        update(locationManager.getLastKnownLocation(provider));
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isAtABC(Point a, Point b, Point c, Point p) {

        double abc = getArea(a, b, c);

        double abp = getArea(a, b, p);

        double acp = getArea(a, c, p);

        double bcp = getArea(b, c, p);

        double all = abp + acp + bcp;

        if (abc >= all - 1 && abc <= all + 1) {

            return true;

        } else {

            return false;
        }
    }

    private static double getArea(Point a, Point b, Point c) {

        double area = ((a.getX() * b.getY() + a.getY() * c.getX() + b.getX() * c.getY()) - (a.getX() * c.getY() + b.getY() * c.getX() + a.getY() * b.getX())) / 2;

        if (area < 0) {

            return -area;

        } else {

            return area;
        }
    }

    /**
     * 提交数据
     *
     * @param url   提交数据的地址
     * @param param 需要提交的数据
     * @return 提交过后服务器返回的数据, 如果服务器没有返回数据则返回null
     */
    public static String sendPost(String url, String param) {

        List<NameValuePair> list = new ArrayList<>();

        for (String s : param.split("&")) {

            list.add(new BasicNameValuePair(s.split("=")[0], s.split("=")[1]));
        }

        /*创建请求对象*/
        HttpPost httpPost = new HttpPost(url);

        /*post请求方式数据放在实体类中*/
        HttpEntity entity;

        try {

            entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);

            httpPost.setEntity(entity);

        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();
        }

        /*创建客户端对象*/
        HttpClient httpClient = new DefaultHttpClient();

        /*客户端带着请求对象请求服务器端*/
        try {

            /*服务器端返回请求的数据*/
            HttpResponse httpResponse = httpClient.execute(httpPost);

            /*解析请求返回的数据*/
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {

                return EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
            }
        } catch (IOException e) {

            Log.e(HttpRequest.class.toString(), "POST请求异常!" + e);

            e.printStackTrace();
        }
        return null;
    }

    private Point toPoint(String x, String y) {
        float local_x = toFloat(x);
        float local_y = toFloat(y);
        if (local_x == 0 && local_y == 0) {
            return null;
        }
        return new Point(local_x * max, local_y * max);
    }

    private float toFloat(String str) {
        if (str != null && str.length() > 0) {
            return Float.parseFloat(str);
        }
        return 0;
    }
}
