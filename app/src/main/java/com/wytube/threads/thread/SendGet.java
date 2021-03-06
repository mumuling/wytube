package com.wytube.threads.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wytube.net.HTTP;


/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 以Get的形式请求数据
 */

public class SendGet extends Thread {

    private String url;
    private String value;
    private Handler handler;

    /**
     * 构造方法
     *
     * @param url     请求地址
     * @param value   请求参数
     * @param handler 请求回调
     */
    public SendGet(String url, String value, Handler handler) {
        this.url = url;
        this.value = value;
        this.handler = handler;
    }

    @Override
    public void run() {
        String string = HTTP.sendGet(url, value);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("get", string);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
