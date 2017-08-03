package com.wytube.threads.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wytube.net.HTTP;

import java.util.List;

/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 以Post的形式提交数据
 */

public class SendFile extends Thread {

    private String url;
    private String value;
    private List<String> files;
    private Handler handler;

    /**
     * 构造方法
     *
     * @param url     提交地址
     * @param params  提交参数
     * @param files   提交文件
     * @param handler 请求回调
     */
    public SendFile(String url, String params, List<String> files, Handler handler) {
        this.url = url;
        this.value = params;
        this.files = files;
        this.handler = handler;
    }

    @Override
    public void run() {
        String string = HTTP.sendPostWithFile(files, url, value);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("file", string);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
