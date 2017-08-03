package com.wytube.utlis;

import android.widget.ImageView;

import com.wytube.threads.ThreadPool;
import com.wytube.threads.thread.AsyncDownThread;


/**
 * 项目名称: WisdomHome.
 * 创建时间: 2017/2/28.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 异步加载图片的类.
 */

public class ImageUtils {

    /**
     * 异步加载图片
     *
     * @param imageview ImageView控件
     * @param url       图片路径
     */
    public static void downloadAsyncTask(final ImageView imageview, final String url) {
        if (url != null && url.length() > 0) {
            start(url, imageview);
        }
    }

    /**
     * 开始下载图片
     *
     * @param url  链接
     * @param view ImageView控件
     */
    private static void start(String url, ImageView view) {
        AsyncDownThread thread = new AsyncDownThread(url, view);
        ThreadPool.addThread(thread);
    }
}
