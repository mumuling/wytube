package com.wytube.beans;

import java.util.List;

/**
 * Created by LIN on 2017/8/8.
 */

public class UserTypeBean {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : [{"unitTypeId":"27AA99EA656B47AC9D3EC82EBF0CE07F","unitTypeName":"户型#2","insideArea":"180","outsideArea":"180"},{"unitTypeId":"4E382B16ABBD42ED877F071958138D5F","unitTypeName":"户型#1","insideArea":"150","outsideArea":"150"}]
     * date : 2017-08-08 09:58:05
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
         * unitTypeId : 27AA99EA656B47AC9D3EC82EBF0CE07F
         * unitTypeName : 户型#2
         * insideArea : 180
         * outsideArea : 180
         */

        private String unitTypeId;
        private String unitTypeName;
        private String insideArea;
        private String outsideArea;

        public String getUnitTypeId() {
            return unitTypeId;
        }

        public void setUnitTypeId(String unitTypeId) {
            this.unitTypeId = unitTypeId;
        }

        public String getUnitTypeName() {
            return unitTypeName;
        }

        public void setUnitTypeName(String unitTypeName) {
            this.unitTypeName = unitTypeName;
        }

        public String getInsideArea() {
            return insideArea;
        }

        public void setInsideArea(String insideArea) {
            this.insideArea = insideArea;
        }

        public String getOutsideArea() {
            return outsideArea;
        }

        public void setOutsideArea(String outsideArea) {
            this.outsideArea = outsideArea;
        }
    }
}
