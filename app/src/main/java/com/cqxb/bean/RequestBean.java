package com.cqxb.bean;

import com.cqxb.yecall.until.T;

/**
 * 创建时间: 2016/12/23.
 * 类 描 述:
 */

public class RequestBean {

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : null
     */

    private boolean success;
    private String message;
    private int code;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
