package com.wytube.net;

import android.os.Handler;

import com.wytube.threads.ThreadPool;
import com.wytube.threads.thread.SendFile;
import com.wytube.threads.thread.SendGet;
import com.wytube.threads.thread.SendJson;
import com.wytube.threads.thread.SendPost;

import java.util.List;

/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 请求数据的类
 */

public class Client {

    /**
     * 提交JSON数据
     *
     * @param url     地址
     * @param json    JSON字符串
     * @param handler 请求回调
     */
    public static void sendJson(String url, String json, Handler handler) {
        SendJson sendJson = new SendJson(url, json, handler);
        ThreadPool.addThread(sendJson);
    }

    /**
     * 以POST的形式提交数据
     *
     * @param url     地址
     * @param value   参数
     * @param handler 请求回调
     */
    public static void sendPost(String url, String value, Handler handler) {
        SendPost sendPost = new SendPost(url, value, handler);
        ThreadPool.addThread(sendPost);
    }

    /**
     * 带文件和参数的POST请求
     *
     * @param url     地址
     * @param params  参数
     * @param files   文件列表
     * @param handler 请求回调
     */
    public static void sendFile(String url, String params, List<String> files, Handler handler) {
        SendFile sendFile = new SendFile(url, params, files, handler);
        ThreadPool.addThread(sendFile);
    }

    /**
     * 以GET的形式提交数据
     *
     * @param url     地址
     * @param value   参数
     * @param handler 请求回调
     */
    public static void sendGet(String url, String value, Handler handler) {
        SendGet sendGet = new SendGet(url, value, handler);
        ThreadPool.addThread(sendGet);
    }
}
