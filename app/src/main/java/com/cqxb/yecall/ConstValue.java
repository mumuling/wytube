package com.cqxb.yecall;

import java.util.HashMap;

/**
 * 创建时间: 2016/12/23.
 * 类 描 述:
 */

public class ConstValue {

    /* 操作成功 		200
     * 服务器异常		500
     * 没有权限		403
     * 请求参数异常		400
     * 会话过期		401
     * 没有数据		404
     * 数据已经存在		409
     * */

    public static HashMap<Integer, String> reqCode;

    static {

        reqCode = new HashMap<>();

        reqCode.put(200, "操作成功");

        reqCode.put(500, "服务器异常");

        reqCode.put(403, "没有权限执行该操作");

        reqCode.put(400, "请求参数异常");

        reqCode.put(401, "会话过期");

        reqCode.put(404, "没有数据");

        reqCode.put(409, "数据已经存在");
    }
}
