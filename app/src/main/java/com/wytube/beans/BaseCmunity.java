package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/5.
 * 类 描 述:
 */

public class BaseCmunity {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : {"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"activityId":"6d0460e6bf6e44c9b37181759a6b78d1","activityName":"中秋月圆    邻里相聚","phone":"18223034349","qq":"1039854102","starttime":"2017-07-07","endtime":"2017-07-08","content":null,"imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201706/20170629143523102.jpg","qualifications":null,"heartCount":"00000000000","cellId":"cell_test","address":"国际小区","joinCount":"1","regUserId":null,"isHeart":"0","isJoin":"0"}
     * date : 2017-07-05 15:59:41
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
         * activityId : 6d0460e6bf6e44c9b37181759a6b78d1
         * activityName : 中秋月圆    邻里相聚
         * phone : 18223034349
         * qq : 1039854102
         * starttime : 2017-07-07
         * endtime : 2017-07-08
         * content : null
         * imgUrl : http://123667.oss-cn-qingdao.aliyuncs.com/201706/20170629143523102.jpg
         * qualifications : null
         * heartCount : 00000000000
         * cellId : cell_test
         * address : 国际小区
         * joinCount : 1
         * regUserId : null
         * isHeart : 0
         * isJoin : 0
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private Object remark;
        private String activityId;
        private String activityName;
        private String phone;
        private String qq;
        private String starttime;
        private String endtime;
        private String content;
        private String imgUrl;
        private Object qualifications;
        private String heartCount;
        private String cellId;
        private String address;
        private String joinCount;
        private Object regUserId;
        private String isHeart;
        private String isJoin;

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

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getQualifications() {
            return qualifications;
        }

        public void setQualifications(Object qualifications) {
            this.qualifications = qualifications;
        }

        public String getHeartCount() {
            return heartCount;
        }

        public void setHeartCount(String heartCount) {
            this.heartCount = heartCount;
        }

        public String getCellId() {
            return cellId;
        }

        public void setCellId(String cellId) {
            this.cellId = cellId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getJoinCount() {
            return joinCount;
        }

        public void setJoinCount(String joinCount) {
            this.joinCount = joinCount;
        }

        public Object getRegUserId() {
            return regUserId;
        }

        public void setRegUserId(Object regUserId) {
            this.regUserId = regUserId;
        }

        public String getIsHeart() {
            return isHeart;
        }

        public void setIsHeart(String isHeart) {
            this.isHeart = isHeart;
        }

        public String getIsJoin() {
            return isJoin;
        }

        public void setIsJoin(String isJoin) {
            this.isJoin = isJoin;
        }
    }
}
