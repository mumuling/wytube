package com.wytube.utlis;

/**
 * 创建时间: 2017/2/21.
 * 类 描 述: Base64加密的类
 */

public class Base64 {

    /**
     * 字符串加密为Base64
     *
     * @param str 未加密字符串
     * @return 加密字符串
     */
    public static String encode(String str) {

        /*在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型*/
        return Utils.clearString(new String(android.util.Base64.encode(str.getBytes(), android.util.Base64.DEFAULT)));
    }

    /**
     * Base64解密为明文
     *
     * @param str 加密的字符串
     * @return 明文
     */
    public static String decode(String str) {

        /*对base64加密后的数据进行解密*/
        return new String(android.util.Base64.decode(str.getBytes(), android.util.Base64.DEFAULT));
    }
}
