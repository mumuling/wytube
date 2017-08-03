package com.wytube.threads.thread;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.wytube.utlis.DownloadPicture;

import java.io.IOException;

/**
 * 项目名称: WisdomHome.
 * 创建时间: 2017/3/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 下载并设置图片的线程.
 */

public class AsyncDownThread extends Thread {

    private Handler handler;
    private String url;
    private Bitmap bitmap;

    public AsyncDownThread(String url, final ImageView view) {
        this.url = url;
        handler = new Handler(msg -> {
            if (bitmap != null) {
                view.setImageBitmap(bitmap);
            }
            return false;
        });
    }

    @Override
    public void run() {
        super.run();
        try {
            bitmap = DownloadPicture.downloadImage(url);
            Message message = new Message();
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
