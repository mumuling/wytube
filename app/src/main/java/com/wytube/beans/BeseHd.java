package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/5.
 * 类 描 述: 活动列表
 */

public class BeseHd {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"activityId":"6d0460e6bf6e44c9b37181759a6b78d1","activityName":"中秋月圆    邻里相聚","phone":"18223034349","qq":"1039854102","starttime":"2017-07-07","endtime":"2017-07-08","content":null,"imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201706/20170629143523102.jpg","qualifications":null,"heartCount":null,"cellId":"cell_test","address":"国际小区","joinCount":"1","regUserId":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"activityId":"81034e8630ce495090b58f8027e67691","activityName":"端午粽传情 邻里一家亲","phone":"13155303332","qq":"78523694","starttime":"2017-05-29","endtime":"2017-05-30","content":null,"imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170527093358769.jpg","qualifications":null,"heartCount":null,"cellId":"cell_test","address":"观岚小区","joinCount":"2","regUserId":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"activityId":"5a89605ea89e4574a780361f9c756dee","activityName":"热情似火  舞动3月","phone":"18223034349","qq":"1039854102","starttime":"2017-03-16","endtime":"2017-03-17","content":null,"imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201706/20170629144157465.jpg","qualifications":null,"heartCount":null,"cellId":"cell_test","address":"国际小区","joinCount":"1","regUserId":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"activityId":"c95d419725c94a30977ad89ccc027f50","activityName":"凝聚邻里情 欢庆元宵节","phone":"18223034349","qq":"1039854102","starttime":"2017-01-01","endtime":"2017-01-02","content":null,"imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201706/20170629144511170.png","qualifications":null,"heartCount":null,"cellId":"cell_test","address":"国际小区","joinCount":null,"regUserId":null}]
     * date : 2017-07-05 11:46:13
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
         * heartCount : null
         * cellId : cell_test
         * address : 国际小区
         * joinCount : 1
         * regUserId : null
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
        private Object content;
        private String imgUrl;
        private Object qualifications;
        private Object heartCount;
        private String cellId;
        private String address;
        private String joinCount;
        private Object regUserId;

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

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
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

        public Object getHeartCount() {
            return heartCount;
        }

        public void setHeartCount(Object heartCount) {
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
    }
}
