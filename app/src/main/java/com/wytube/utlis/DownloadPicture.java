package com.wytube.utlis;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 项目名称: WisdomHome.
 * 创建时间: 2017/3/15.
 * 创 建 人: Var_雨中行.
 * 类 描 述: 从网络下载图片.
 */

public class DownloadPicture {

    /*图片缓存路径*/
    @SuppressLint("SdCardPath")
    private static final String PATH = "/sdcard/tedi/WH/file/net/imgs/";

    /**
     * 从网络下载图片
     *
     * @param http 图片URL
     * @return
     * @throws IOException
     */
    public static Bitmap downloadImage(String http) throws IOException {
        String filePath = checkUrl(http);
        if (filePath != null) {
            return BitmapFactory.decodeFile(filePath);
        }
        URL url = new URL(http);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5 * 1000);
        InputStream inputStream = conn.getInputStream();
        byte[] data = readInputStream(inputStream);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        saveBitmap(bitmap, http);
        return bitmap;
    }

    /**
     * 从流中读取图片字节
     *
     * @param inputStream 图片流
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 检查本地是否有此文件
     *
     * @param url 图片下载链接
     * @return
     */
    private static String checkUrl(String url) throws IOException {
        String[] str = url.split("/");
        if (str.length > 0) {
            String path = PATH + "/" + str[str.length - 1];
            File file = new File(path);
            if (file.exists()) {
                return file.getPath();
            }
        }
        return null;
    }

    /**
     * 检查文件和目录是否存在
     */
    private static void checkFile(String filePath) throws IOException {
        File file = new File(filePath);
        File dir = new File(file.getParent());
        /*检查文件夹是否存在*/
        if (!dir.exists()) {
            dir.mkdirs();
        }
        /*检查文件是否存在*/
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * 储存Bitmap到本地
     *
     * @param bitmap
     */
    private static void saveBitmap(Bitmap bitmap, String url) throws IOException {
        String[] str = url.split("/");
        if (str.length > 0 && bitmap != null) {
            String path = PATH + "/" + str[str.length - 1];
            checkFile(path);
            File file = new File(path);
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, outputStream);
            outputStream.flush();
            outputStream.close();
        }
    }
}
