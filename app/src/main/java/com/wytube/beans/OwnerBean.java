package com.wytube.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LIN on 2017/8/3.
 */

public class OwnerBean implements Serializable{

    /**
     * code : 200
     * data : [{"buildingId":"0FCA4BACCC2D4BE59D799BD4128A9970","buildingName":"1栋","cellId":"1147424848043600","cellName":"长航小区","createDate":"2017-04-18 09:46:22","ownerId":"454FA0B921F34745B94445B87FBC5F53","ownerName":"邱先生","ownerPhone":"18523053781","ownerType":0,"roomId":"8D5E64183CA748F8AAD5718EC4456614","roomNum":"0101","speackStatus":1,"status":1,"unitId":"C076A37080394E0CAD2AD2B4A79FE8D3","unitName":"1单元"},{"buildingId":"0FCA4BACCC2D4BE59D799BD4128A9970","buildingName":"1栋","cellId":"1147424848043600","cellName":"长航小区","createDate":"2017-05-22 17:52:38","ownerId":"B91AB4AE6C1B448586BD14C5462DD0C7","ownerName":"小康","ownerPhone":"18223960259","ownerType":1,"roomId":"CA242C15511840E1A9FA8EB130814B45","roomNum":"3004","speackStatus":1,"status":1,"unitId":"C076A37080394E0CAD2AD2B4A79FE8D3","unitName":"1单元"},{"buildingId":"0FCA4BACCC2D4BE59D799BD4128A9970","buildingName":"1栋","cellId":"1147424848043600","cellName":"长航小区","createDate":"2017-04-18 09:49:35","integral":"100","ownerId":"46E98F6E30BF4E569BD956ED1CAD6E6C","ownerName":"刘先生","ownerPhone":"18523053771","ownerType":0,"roomId":"4AB9F613BBAE469AB3BB0C00C861BFFA","roomNum":"0102","speackStatus":1,"status":1,"unitId":"C076A37080394E0CAD2AD2B4A79FE8D3","unitName":"1单元"}]
     * date : 2017-06-07 16:35:46
     * message : 检索OK
     * success : true
     */

    private int code;
    private String date;
    private String message;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * buildingId : 0FCA4BACCC2D4BE59D799BD4128A9970
         * buildingName : 1栋
         * cellId : 1147424848043600
         * cellName : 长航小区
         * createDate : 2017-04-18 09:46:22
         * ownerId : 454FA0B921F34745B94445B87FBC5F53
         * ownerName : 邱先生
         * ownerPhone : 18523053781
         * ownerType : 0
         * roomId : 8D5E64183CA748F8AAD5718EC4456614
         * roomNum : 0101
         * speackStatus : 1
         * status : 1
         * unitId : C076A37080394E0CAD2AD2B4A79FE8D3
         * unitName : 1单元
         * integral : 100
         */

        private String buildingId;
        private String buildingName;
        private String cellId;
        private String cellName;
        private String createDate;
        private String ownerId;
        private String ownerName;
        private String ownerPhone;
        private int ownerType;
        private String roomId;
        private String roomNum;
        private int speackStatus;
        private int status;
        private String unitId;
        private String unitName;
        private String integral;

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

        public String getCellId() {
            return cellId;
        }

        public void setCellId(String cellId) {
            this.cellId = cellId;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerPhone() {
            return ownerPhone;
        }

        public void setOwnerPhone(String ownerPhone) {
            this.ownerPhone = ownerPhone;
        }

        public int getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(int ownerType) {
            this.ownerType = ownerType;
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

        public int getSpeackStatus() {
            return speackStatus;
        }

        public void setSpeackStatus(int speackStatus) {
            this.speackStatus = speackStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

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

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
