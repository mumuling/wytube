package com.wytube.net;


import com.wytube.utlis.AppValue;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 发送Get和Post请求的类
 */

public class HTTP {

    private static String req = "";

    /**
     * 提交Json数据
     *
     * @param url     地址
     * @param strJson json数据
     * @return 从服务器返回的数据
     */
    public static String sendJson(String url, String strJson) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost();
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        post.addHeader("Authorization", AppValue.authorization);
        post.addHeader("Token", AppValue.token);
        String req = null;
        try {
            post.setURI(URI.create(url));
            post.setEntity(new StringEntity(strJson));
            HttpResponse response = client.execute(post);
            req = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return req;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendGet(String url, String params) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Close");
            conn.setRequestProperty("Authorization", AppValue.authorization);
            conn.setRequestProperty("Token", AppValue.token);
            conn.connect();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String sendPost(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Close");
            conn.setRequestProperty("Authorization", AppValue.authorization);
            conn.setRequestProperty("Token", AppValue.token);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 带文件的POST提交方法
     *
     * @param files  文件列表
     * @param url    提交的路径
     * @param params 提交的参数
     */
    public static String sendPostWithFile(List<String> files, String url, String params) {
        OkHttpClient client = new OkHttpClient();
        req = "";
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        /*创建文件*/
        for (String fileName : files) {
            File file = new File(fileName);
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }
        /*添加参数*/
        String[] keyValues = params.split("&");
        for (String keyValue : keyValues) {
            String[] keys = keyValue.split("=");
            builder.addFormDataPart(keys[0], keys[1]);
        }
        builder.build();
        Request request = new Request.Builder().url(url).post(builder.build())
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "Close")
                .addHeader("Authorization", AppValue.authorization)
                .addHeader("Token", AppValue.token)
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                req = response.body().string();

            }
        };
        client.newCall(request).enqueue(callback);
        while (req.length() <= 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return req;
    }
}
