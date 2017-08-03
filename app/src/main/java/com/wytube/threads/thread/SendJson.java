package com.wytube.threads.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wytube.net.HTTP;


/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 发送Json数据的线程
 */

public class SendJson extends Thread {

    private String url;
    private String json;
    private Handler handler;

    /**
     * 构造方法
     *
     * @param url     请求地址
     * @param json    提交数据
     * @param handler 请求回调
     */
    public SendJson(String url, String json, Handler handler) {
        this.url = url;
        this.json = json;
        this.handler = handler;
    }

    @Override
    public void run() {
        String string = HTTP.sendJson(url, json);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("json", string);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
