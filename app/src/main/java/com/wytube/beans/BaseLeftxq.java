package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/7.
 * 类 描 述:
 */

public class BaseLeftxq {


    /**
     * success : true
     * message : ok
     * code : 200
     * data : [{"createDate":"2017-08-04 17:19:50","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","shopId":"3F5F2A6A4FF64E6EAF7D063849C343E0","shopName":"222","cover":"http://123667.oss-cn-qingdao.aliyuncs.com/201708/20170804171947359.png","concat":"13222222222","name":"222","description":"<p>12312<\/p>","longitude":null,"latitude":null,"cellId":null,"shopTypeId":"48155DA6E1C7426DABCECF732A8A704A","shopTypeName":"电脑维修"}]
     * date : 2017-08-09 10:15:47
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
         * createDate : 2017-08-04 17:19:50
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark :
         * shopId : 3F5F2A6A4FF64E6EAF7D063849C343E0
         * shopName : 222
         * cover : http://123667.oss-cn-qingdao.aliyuncs.com/201708/20170804171947359.png
         * concat : 13222222222
         * name : 222
         * description : <p>12312</p>
         * longitude : null
         * latitude : null
         * cellId : null
         * shopTypeId : 48155DA6E1C7426DABCECF732A8A704A
         * shopTypeName : 电脑维修
         */

        private String createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String shopId;
        private String shopName;
        private String cover;
        private String concat;
        private String name;
        private String description;
        private Object longitude;
        private Object latitude;
        private Object cellId;
        private String shopTypeId;
        private String shopTypeName;
        public boolean isCheck;

        public boolean isisCheck() {
            return isCheck;
        }

        public void setisCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
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

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getConcat() {
            return concat;
        }

        public void setConcat(String concat) {
            this.concat = concat;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public String getShopTypeId() {
            return shopTypeId;
        }

        public void setShopTypeId(String shopTypeId) {
            this.shopTypeId = shopTypeId;
        }

        public String getShopTypeName() {
            return shopTypeName;
        }

        public void setShopTypeName(String shopTypeName) {
            this.shopTypeName = shopTypeName;
        }
    }
}
