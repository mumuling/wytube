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
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","buildingId":"3C84FAF83AFA4EC2BD3AED1C05F45C5C","buildingName":"重庆国际小区","unitNum":2,"cellId":"重庆国际小区","buildingNum":2},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","buildingId":"C8C959DC3C1A40DD9598C943E0BC544F","buildingName":"楼宇","unitNum":4,"cellId":"重庆国际小区","buildingNum":6},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","buildingId":"97143269BEB04B9AA72EBCE4D405CD9E","buildingName":"名宇华都","unitNum":5,"cellId":"重庆国际小区","buildingNum":1},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","buildingId":"253C250AB346497BB82D768B3FB27473","buildingName":"3栋","unitNum":28,"cellId":"重庆国际小区","buildingNum":3},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","buildingId":"771450266EC64AE0983D3E39C3AFC9F9","buildingName":"2栋","unitNum":4,"cellId":"重庆国际小区","buildingNum":5},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"1栋","buildingId":"1B8D78AE2A9A4D29A29896235B076372","buildingName":"1栋","unitNum":4,"cellId":"重庆国际小区","buildingNum":15}]
     * date : 2017-08-11 10:06:59
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
         * createDate : null
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark :
         * buildingId : 3C84FAF83AFA4EC2BD3AED1C05F45C5C
         * buildingName : 重庆国际小区
         * unitNum : 2
         * cellId : 重庆国际小区
         * buildingNum : 2
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String buildingId;
        private String buildingName;
        private int unitNum;
        private String cellId;
        private int buildingNum;

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Object modifyDate) {
            this.modifyDate = modifyDate;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getSorted() {
            return sorted;
        }

        public void setSorted(Object sorted) {
            this.sorted = sorted;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

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

        public int getBuildingNum() {
            return buildingNum;
        }

        public void setBuildingNum(int buildingNum) {
            this.buildingNum = buildingNum;
        }
    }
}
