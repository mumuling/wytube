package com.wytube.beans;

import java.util.List;

/**
 * Created by LIN on 2017/8/8.
 */

public class BuildBean {

    /**
     * success : true
     * message : 检索OK
     * code : 200
     * data : [{"buildingId":"3C84FAF83AFA4EC2BD3AED1C05F45C5C","buildingName":"重庆国际小区","unitNum":2,"cellId":"重庆国际小区"},{"buildingId":"97143269BEB04B9AA72EBCE4D405CD9E","buildingName":"名宇华都","unitNum":5,"cellId":"重庆国际小区"}]
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
         * buildingId : 3C84FAF83AFA4EC2BD3AED1C05F45C5C
         * buildingName : 重庆国际小区
         * unitNum : 2
         * cellId : 重庆国际小区
         */

        private String buildingId;
        private String buildingName;
        private int unitNum;
        private String cellId;

        public String getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public int getUnitNum() {
            return unitNum;
        }

        public void setUnitNum(int unitNum) {
            this.unitNum = unitNum;
        }

        public String getCellId() {
            return cellId;
        }

        public void setCellId(String cellId) {
            this.cellId = cellId;
        }
    }
}
