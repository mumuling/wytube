package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/11.
 * 类 描 述: 物品借用列表
 */

public class BaseWPjy {

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"goodsId":"55e8f0657a8742b19ebca48351ae4496","goodsName":"雨衣","goodsNum":"2500","accessId":null,"cellId":"cell_test","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/0807/9EBEBEE9DFBE4B79915B5BEE21C43D29_image0.png","cellName":"重庆国际小区","borrowCount":"0"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"goodsId":"753511b930374c089d3a251a06eae2d8","goodsName":"电钻","goodsNum":"15","accessId":null,"cellId":"cell_test","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201708/20170803164538024.png","cellName":"重庆国际小区","borrowCount":"0"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"goodsId":"b2e0e7799f9243ae8438aad645a0332c","goodsName":"工具箱","goodsNum":"5","accessId":null,"cellId":"cell_test","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201708/20170803164555175.png","cellName":"重庆国际小区","borrowCount":"0"},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"goodsId":"582e7bc589a542d5a0118ea947be9e2b","goodsName":"低级搬运车","goodsNum":"20","accessId":null,"cellId":"cell_test","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/201708/20170803164642209.png","cellName":"重庆国际小区","borrowCount":"0"}]
     * date : 2017-08-11 15:35:19
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
         * goodsId : 55e8f0657a8742b19ebca48351ae4496
         * goodsName : 雨衣
         * goodsNum : 2500
         * accessId : null
         * cellId : cell_test
         * imgUrl : http://123667.oss-cn-qingdao.aliyuncs.com/0807/9EBEBEE9DFBE4B79915B5BEE21C43D29_image0.png
         * cellName : 重庆国际小区
         * borrowCount : 0
         */

        private Object createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private Object remark;
        private String goodsId;
        private String goodsName;
        private String goodsNum;
        private Object accessId;
        private String cellId;
        private String imgUrl;
        private String cellName;
        private String borrowCount;
        public boolean isCheck;

        public boolean isisCheck() {
            return isCheck;
        }

        public void setisCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }
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

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(String goodsNum) {
            this.goodsNum = goodsNum;
        }

        public Object getAccessId() {
            return accessId;
        }

        public void setAccessId(Object accessId) {
            this.accessId = accessId;
        }

        public String getCellId() {
            return cellId;
        }

        public void setCellId(String cellId) {
            this.cellId = cellId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getBorrowCount() {
            return borrowCount;
        }

        public void setBorrowCount(String borrowCount) {
            this.borrowCount = borrowCount;
        }
    }
}
