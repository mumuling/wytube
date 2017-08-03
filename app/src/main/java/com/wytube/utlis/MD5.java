package com.wytube.utlis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对外提供getMD5(String)方法
 *
 * @author randyjia
 */
public class MD5 {
    /**
     * 加密key
     */
    private static final String key = "AAABACADAEAF20150925";

    /**
     * MD5加密
     */
    public static String getParamMD5(String... strings) {
        String param = "";
        for (String string : strings) {
            param += string;
        }
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.reset();
            localMessageDigest.update((key + param).getBytes());
            return HexEncode(localMessageDigest.digest());
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }

    private static String HexEncode(byte[] paramArrayOfByte) {
        StringBuilder localStringBuilder = new StringBuilder(
                paramArrayOfByte.length * 2);
        for (int k : paramArrayOfByte) {
            localStringBuilder.append(Integer.toHexString((k & 0xF0) >>> 4));
            localStringBuilder.append(Integer.toHexString(k & 0xF));
        }
        return localStringBuilder.toString();
    }
}
