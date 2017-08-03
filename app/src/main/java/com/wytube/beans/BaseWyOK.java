package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/3.
 * 类 描 述:
 */

public class BaseWyOK {

    /**
     * msg : 添加成功
     * code : 200
     * success : true
     */

    private String msg;
    private int code;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
