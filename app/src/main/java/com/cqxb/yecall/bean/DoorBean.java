package com.cqxb.yecall.bean;

import java.util.List;

/**
 * 创建时间: 2017/2/10.
 * 类 描 述: 设备门禁对象
 */

public class DoorBean {

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"B0C0595BA62049AA84D49EBC294F28BA","serial":null,"communityName":"浙江测试","doorName":"浙江测试","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"F9AB17FFB7E84FC58552DD1F01048044","serial":null,"communityName":"测试刷卡门禁一体机","doorName":"测试刷卡门禁一体机","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"E313256E5567452B92F51A987A23C073","serial":null,"communityName":"密码测试","doorName":"密码测试","doorType":2222,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"6E23CDEC6C4440A3972D9DAA78EAA462","serial":null,"communityName":"北城未来C大门3","doorName":"大门闸机3","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"963D0F7FC82A49868BA5EF7B29D21001","serial":null,"communityName":"北城未来C大门2","doorName":"大门闸机2","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"A04ABDB6999F42CB8B52B488D1F2114B","serial":null,"communityName":"北城未来C大门1","doorName":"大门闸机1","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"081620FB676F4882B50679C48466381A","serial":null,"communityName":"北城未来C6","doorName":"北城未来C6","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"3B33A4427992468092E10C09E0666779","serial":null,"communityName":"北城未来C5","doorName":"北城未来C5","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"D678D41140A84F75BF1C6EB5CF883A33","serial":null,"communityName":"北城未来C4","doorName":"北城未来C4","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"84A2898D013B4E4CB9B8D6E1673B9243","serial":null,"communityName":"北城未来C3","doorName":"北城未来C3","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"63856F4D88074805B1036F82157A7D14","serial":null,"communityName":"北城未来C2","doorName":"北城未来C2","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"2CF2BCF478EF4864A8EAFA65F870EB63","serial":null,"communityName":"北城未来C1","doorName":"北城未来C1","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"92E8530E54F7469D9E7BD21EB58D7735","serial":null,"communityName":"北城未来2#侧门","doorName":"北城未来2#侧门","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"CDED5C0FA079498C8CADB9821D4AF5E4","serial":null,"communityName":"td2","doorName":"td2","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"C5DF60FE4C944901BFB1CA535B11F4EC","serial":null,"communityName":"td1","doorName":"td1","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":"","latitude":"","leftLongitude":"","leftLatitude":"","rightLongitude":"","rightLatitude":""},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"F61F6321D05E44A88F3432AAA87A1E4F","serial":null,"communityName":"mytet29","doorName":"mytet29","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":"1015","latitude":"1023","leftLongitude":"1156","leftLatitude":"5569","rightLongitude":"5485","rightLatitude":"5563"},{"createDate":null,"modifyDate":null,"createUser":null,"remark":null,"status":null,"sorted":null,"startDate":null,"endDate":null,"doorId":"B89B0FF089A7424FA6FA350F0143A13C","serial":null,"communityName":"mytest","doorName":"mytest","doorType":1111,"sourceIp":null,"sourcePort":null,"destinationIp":null,"destinationPort":null,"instruct":null,"responseCode":null,"secretKey":null,"customerPhone":null,"customerName":null,"customerId":null,"doorgroupId":null,"usergroupId":null,"boundId":null,"longitude":null,"latitude":null,"leftLongitude":null,"leftLatitude":null,"rightLongitude":null,"rightLatitude":null}]
     */

    private boolean        success;
    private String         message;
    private int            code;
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
        /**
         * createDate : null
         * modifyDate : null
         * createUser : null
         * remark : null
         * status : null
         * sorted : null
         * startDate : null
         * endDate : null
         * doorId : B0C0595BA62049AA84D49EBC294F28BA
         * serial : null
         * communityName : 浙江测试
         * doorName : 浙江测试
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
         * boundId : null
         * longitude : null
         * latitude : null
         * leftLongitude : null
         * leftLatitude : null
         * rightLongitude : null
         * rightLatitude : null
         */

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
        private int    doorType;
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
        private Object boundId;
        private Object longitude;
        private Object latitude;
        private Object leftLongitude;
        private Object leftLatitude;
        private Object rightLongitude;
        private Object rightLatitude;

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

        public Object getBoundId() {
            return boundId;
        }

        public void setBoundId(Object boundId) {
            this.boundId = boundId;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLeftLongitude() {
            return leftLongitude;
        }

        public void setLeftLongitude(Object leftLongitude) {
            this.leftLongitude = leftLongitude;
        }

        public Object getLeftLatitude() {
            return leftLatitude;
        }

        public void setLeftLatitude(Object leftLatitude) {
            this.leftLatitude = leftLatitude;
        }

        public Object getRightLongitude() {
            return rightLongitude;
        }

        public void setRightLongitude(Object rightLongitude) {
            this.rightLongitude = rightLongitude;
        }

        public Object getRightLatitude() {
            return rightLatitude;
        }

        public void setRightLatitude(Object rightLatitude) {
            this.rightLatitude = rightLatitude;
        }
    }
}
