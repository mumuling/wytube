package com.wytube.beans;

/**
 * Created by LIN on 2017/8/11.
 */

public class TypeBean {

    /**
     * success : true
     * message : 检索OK
     * code : 200
     * data : {"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"roomId":"5A8973DF6FDD454CBB0E68AC05EBF024","roomNum":"0101","roomType":"0","roomStatus":"1","unitId":"E99869C1C3874356B46098B415AAF108","unitTypeId":"4E382B16ABBD42ED877F071958138D5F","checkinTime":"2017-08-08 18:30:26","cellId":null,"buildingId":"1B8D78AE2A9A4D29A29896235B076372","buildingName":"1栋","buildingNum":"15","unitName":"1单元","unitTypeName":"户型#1","cellName":null,"month":null,"isBill":null,"billTypeId":null,"insideArea":null,"outsideArea":null,"sideArea":"150","price":"1.8"}
     * date : 2017-08-11 10:45:07
     */

    private boolean success;
    private String message;
    private int code;
    private DataBean data;
    private String date;

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

    public static class DataBean {
        /**
         * createDate : null
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark : null
         * roomId : 5A8973DF6FDD454CBB0E68AC05EBF024
         * roomNum : 0101
         * roomType : 0
         * roomStatus : 1
         * unitId : E99869C1C3874356B46098B415AAF108
         * unitTypeId : 4E382B16ABBD42ED877F071958138D5F
         * checkinTime : 2017-08-08 18:30:26
         * cellId : null
         * buildingId : 1B8D78AE2A9A4D29A29896235B076372
         * buildingName : 1栋
         * buildingNum : 15
         * unitName : 1单元
         * unitTypeName : 户型#1
         * cellName : null
         * month : null
         * isBill : null
         * billTypeId : null
         * insideArea : null
         * outsideArea : null
         * sideArea : 150
         * price : 1.8
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private Object remark;
        private String roomId;
        private String roomNum;
        private String roomType;
        private String roomStatus;
        private String unitId;
        private String unitTypeId;
        private String checkinTime;
        private Object cellId;
        private String buildingId;
        private String buildingName;
        private String buildingNum;
        private String unitName;
        private String unitTypeName;
        private Object cellName;
        private Object month;
        private Object isBill;
        private Object billTypeId;
        private Object insideArea;
        private Object outsideArea;
        private String sideArea;
        private String price;

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

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getRoomStatus() {
            return roomStatus;
        }

        public void setRoomStatus(String roomStatus) {
            this.roomStatus = roomStatus;
        }

        public String getUnitId() {
            return unitId;
        }

        public void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        public String getUnitTypeId() {
            return unitTypeId;
        }

        public void setUnitTypeId(String unitTypeId) {
            this.unitTypeId = unitTypeId;
        }

        public String getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(String checkinTime) {
            this.checkinTime = checkinTime;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
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

        public String getBuildingNum() {
            return buildingNum;
        }

        public void setBuildingNum(String buildingNum) {
            this.buildingNum = buildingNum;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getUnitTypeName() {
            return unitTypeName;
        }

        public void setUnitTypeName(String unitTypeName) {
            this.unitTypeName = unitTypeName;
        }

        public Object getCellName() {
            return cellName;
        }

        public void setCellName(Object cellName) {
            this.cellName = cellName;
        }

        public Object getMonth() {
            return month;
        }

        public void setMonth(Object month) {
            this.month = month;
        }

        public Object getIsBill() {
            return isBill;
        }

        public void setIsBill(Object isBill) {
            this.isBill = isBill;
        }

        public Object getBillTypeId() {
            return billTypeId;
        }

        public void setBillTypeId(Object billTypeId) {
            this.billTypeId = billTypeId;
        }

        public Object getInsideArea() {
            return insideArea;
        }

        public void setInsideArea(Object insideArea) {
            this.insideArea = insideArea;
        }

        public Object getOutsideArea() {
            return outsideArea;
        }

        public void setOutsideArea(Object outsideArea) {
            this.outsideArea = outsideArea;
        }

        public String getSideArea() {
            return sideArea;
        }

        public void setSideArea(String sideArea) {
            this.sideArea = sideArea;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
