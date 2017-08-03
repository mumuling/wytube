package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/4.
 * 类 描 述:
 */

public class BeasOpen {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : null
     */

    private boolean success;
    private String message;
    private int code;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
