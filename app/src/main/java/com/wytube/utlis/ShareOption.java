package com.wytube.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建时间: 2017/2/21.
 * 类 描 述: 私有文件操作类
 */

public class ShareOption {

    /**
     * 检查是否第一次运行该APP
     *
     * @param context APP参数
     * @return 返回true则表示是第一次运行, 返回false则表示不是第一次运行
     */
    public static boolean firstRun(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("FIRST_RUN", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun) {
            editor.putBoolean("FIRST_RUN", false);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将对应Key的Value写入到APP的私有文件中
     *
     * @param key           键值
     * @param default_value 参数
     * @param context       APP参数
     */
    public static void writerString(String key, String default_value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, default_value);
        editor.apply();
    }

    /**
     * 从APP的私有文件中读取对应键值的参数
     *
     * @param key     键值
     * @param context APP参数
     * @return 返回读取的参数, 没有则返回null
     */
    public static String readerString(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
