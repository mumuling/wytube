package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/25.
 * 类 描 述:
 */

public class BasesOK {


    /**
     * msg : 修改失败
     * code : 500
     * success : false
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
