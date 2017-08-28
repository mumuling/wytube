package com.wytube.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wytube.dialog.CProgressDialog;
import com.wytube.dialog.SelectDialog;
import com.wytube.dialog.TipsDialig;
import com.wytube.dialog.YCdialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建时间: 2017/4/20.
 * 类 描 述:
 */
public class Utils {


    /**
     * 去除字符串中的符号
     *
     * @param str 字符串
     * @return 返回格式化的字符串
     */
    public static String clearString(String str) {
        String dest = null;
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * 显示确定对话框
     *
     * @param context APP参数
     * @param title   显示的提示文字
     */
    public static void showOkDialog(Context context, String title) {
        YCdialog.ycdialog = new YCdialog(context);
        YCdialog.ycdialog.loadDialog(title);
    }

    /**
     * 显示确定对话框
     *
     * @param context APP参数
     */
    public static void showNetErrorDialog(Context context) {
        YCdialog.ycdialog = new YCdialog(context);
        YCdialog.ycdialog.loadDialog("服务器异常!请稍后再试!");
//        Intent intent = new Intent(getContext(),YCdialogYC.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getContext().startActivity(intent);
    }

    /**
     * 提示登录对话框
     *
     * @param context APP参数
     */
    public static void showLoginDialog(Context context) {
        TipsDialig.tipsDialig = new TipsDialig(context);
        TipsDialig.tipsDialig.loadDialog();
    }

    /**
     * 显示加载框
     *
     * @param context APP参数
     */
    public static void showLoad(Context context) {
        CProgressDialog.Progress = new CProgressDialog(context);
        CProgressDialog.Progress.loadDialog();
    }

    /**
     * 退出加载框
     */
    public static void exitLoad() {
        CProgressDialog.Progress.removeDialog();
    }

    /**
     * 显示选择对话框
     *
     * @param context  APP参数
     * @param items    选择数据列表
     * @param callBack 选择回调
     */
    public static void showSelectDialog(Context context, List<String> items, SelectDialog.SelectCallBack callBack) {
        AppValue.items = items;
        context.startActivity(new Intent(context, SelectDialog.class));
        SelectDialog.setCallBack(callBack);
    }

    /**
     * 从网络上异步加载图片
     *
     * @param view ImageView
     * @param url  图片的下载地址
     */
    public static void loadImage(ImageView view, String url) {
        ImageUtils.downloadAsyncTask(view, url);
    }

    /**
     * 传入字符串, 将字符串信息保存到文件中
     *
     * @param jsonData json数据
     */
    public static void saveFile(String jsonData, String path) {
        checkPath(path);
        try {
            File fileText = new File(path);
            FileWriter fileWriter = new FileWriter(fileText, false);
            fileWriter.write(jsonData);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Json信息
     *
     * @return 返回JSON字符串
     */
    public static String readJson(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String json = "";
        if (!file.exists()) {
            return null;
        }
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                json += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return json;
    }

    /**
     * 检查路径是否存在
     *
     * @param path 文件路径
     */
    private static void checkPath(String path) {
        File file = new File(path);
        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 过滤String中的非数字字符
     *
     * @param str 字符串
     * @return 返回字符串中包含的所有数字字符, 但包含非数字字符
     */
    public static String fliterString(String str) {
        String math = "[^0-9]";
        Pattern pattern = Pattern.compile(math);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("").trim();
    }

    /**
     * 将时间戳转换为年月日
     *
     * @param time 时间戳
     * @return 返回年月日时分秒 字符串
     */
    public static String toDate(long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    /**
     * 将日期转换为时间戳
     *
     * @param date 日期
     * @return 返回long类型时间戳
     */
    public static long toLong(String date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 手机屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getWindowWidth(Context ctx) {
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 手机屏幕高度
     *
     * @param ctx
     * @return
     */
    public static int getWindowHeight(Context ctx) {
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
