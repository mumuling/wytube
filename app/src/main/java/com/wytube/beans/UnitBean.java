package com.wytube.beans;

import java.util.List;

/**
 * Created by LIN on 2017/8/8.
 */

public class UnitBean {

    /**
     * success : true
     * message : 检索OK
     * code : 200
     * data : [{"unitId":"EECF8624763044E9AB65C66EBBF69B1B","unitName":"4单元"},{"unitId":"B0AB0D2E5B2545EBA7A4B7E3D9CBB6E9","unitName":"3单元"},{"unitId":"AB82F3EB96D5406CB877A910E6BE5D12","unitName":"2单元"},{"unitId":"E99869C1C3874356B46098B415AAF108","unitName":"1单元"}]
     * date : 2017-08-08 09:55:04
     */

    private boolean success;
    private String message;
    private int code;
    private String date;
    private List<DataBean> data;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * unitId : EECF8624763044E9AB65C66EBBF69B1B
         * unitName : 4单元
         */

        private String unitId;
        private String unitName;

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }
}
