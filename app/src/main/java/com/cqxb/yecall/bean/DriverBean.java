package com.cqxb.yecall.bean;

import java.util.List;

/**
 * 创建时间: 2016/12/23.
 * 类 描 述:
 */

public class DriverBean {

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"C5DF60FE4C944901BFB1CA535B11F4EC","serial":null,"communityName":"td1","doorName":"td1","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null}]
     */

    private boolean success;
    private String message;
    private int code;
    /**
     * createDate : null
     * modifyDate : null
     * createUser : null
     * remark : null
     * status : null
     * sorted : null
     * startDate : null
     * endDate : null
     * doorId : C5DF60FE4C944901BFB1CA535B11F4EC
     * serial : null
     * communityName : td1
     * doorName : td1
     * doorType : 1111
     * sourceIp : null
     * sourcePort : null
     * destinationIp : null
     * destinationPort : null
     * instruct : null
     * responseCode : null
     * secretKey : null
     * customerPhone : null
     * customerName : null
     * customerId : null
     * doorgroupId : null
     * usergroupId : null
     */

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object remark;
        private Object status;
        private Object sorted;
        private Object startDate;
        private Object endDate;
        private String doorId;
        private Object serial;
        private String communityName;
        private String doorName;
        private int doorType;
        private Object sourceIp;
        private Object sourcePort;
        private Object destinationIp;
        private Object destinationPort;
        private Object instruct;
        private Object responseCode;
        private Object secretKey;
        private Object customerPhone;
        private Object customerName;
        private Object customerId;
        private Object doorgroupId;
        private Object usergroupId;

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

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
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

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public String getDoorId() {
            return doorId;
        }

        public void setDoorId(String doorId) {
            this.doorId = doorId;
        }

        public Object getSerial() {
            return serial;
        }

        public void setSerial(Object serial) {
            this.serial = serial;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getDoorName() {
            return doorName;
        }

        public void setDoorName(String doorName) {
            this.doorName = doorName;
        }

        public int getDoorType() {
            return doorType;
        }

        public void setDoorType(int doorType) {
            this.doorType = doorType;
        }

        public Object getSourceIp() {
            return sourceIp;
        }

        public void setSourceIp(Object sourceIp) {
            this.sourceIp = sourceIp;
        }

        public Object getSourcePort() {
            return sourcePort;
        }

        public void setSourcePort(Object sourcePort) {
            this.sourcePort = sourcePort;
        }

        public Object getDestinationIp() {
            return destinationIp;
        }

        public void setDestinationIp(Object destinationIp) {
            this.destinationIp = destinationIp;
        }

        public Object getDestinationPort() {
            return destinationPort;
        }

        public void setDestinationPort(Object destinationPort) {
            this.destinationPort = destinationPort;
        }

        public Object getInstruct() {
            return instruct;
        }

        public void setInstruct(Object instruct) {
            this.instruct = instruct;
        }

        public Object getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Object responseCode) {
            this.responseCode = responseCode;
        }

        public Object getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(Object secretKey) {
            this.secretKey = secretKey;
        }

        public Object getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(Object customerPhone) {
            this.customerPhone = customerPhone;
        }

        public Object getCustomerName() {
            return customerName;
        }

        public void setCustomerName(Object customerName) {
            this.customerName = customerName;
        }

        public Object getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Object customerId) {
            this.customerId = customerId;
        }

        public Object getDoorgroupId() {
            return doorgroupId;
        }

        public void setDoorgroupId(Object doorgroupId) {
            this.doorgroupId = doorgroupId;
        }

        public Object getUsergroupId() {
            return usergroupId;
        }

        public void setUsergroupId(Object usergroupId) {
            this.usergroupId = usergroupId;
        }
    }
}
