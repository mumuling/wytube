package com.wytube.beans;

/**
 * Created by LIN on 2017/8/10.
 */

public class OwnerDel {
    /**
     * code : 200
     * data : {}
     * date : 2017-06-07 16:16:49
     * message : 删除OK
     * success : true
     */

    private int code;
    private DataBean data;
    private String date;
    private String message;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
    }
}
