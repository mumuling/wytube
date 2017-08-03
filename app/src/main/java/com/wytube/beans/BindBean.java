package com.wytube.beans;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class BindBean {

    /**
     * date : 2017-05-17 17:59:29
     * message : OK
     * state : SUCCESS
     * success : true
     */

    private String date;
    private String message;
    private String state;
    private boolean success;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
