package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/7.
 * 类 描 述: 生活服务适配器
 */

public class BaseSHfw {


    /**
     * success : true
     * message : ok
     * code : 200
     * data : [{"createDate":"2017-06-30 18:06:43","modifyDate":"2017-07-03 15:58:26","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"48155DA6E1C7426DABCECF732A8A704A","name":"电脑维修","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155824417.png","cellId":null},{"createDate":"2017-06-30 18:06:08","modifyDate":"2017-07-03 15:58:34","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"D008E0B0666642C5BAFD35F96C4080CB","name":"超市","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155833058.png","cellId":null},{"createDate":"2017-06-29 12:12:22","modifyDate":"2017-07-03 15:58:49","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"6200C60DCDB741CE8247CCB65568C45B","name":"银行","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155847772.png","cellId":null},{"createDate":"2017-06-29 12:12:04","modifyDate":"2017-07-03 15:58:56","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"4E2B4A995F534C628B329C5CF42F545A","name":"驾校","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155854789.png","cellId":null},{"createDate":"2017-06-29 12:11:59","modifyDate":"2017-07-03 15:59:09","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"9509D566C61E4FF5BDADDED061D44A00","name":"学校","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155907776.png","cellId":null},{"createDate":"2017-06-29 12:11:46","modifyDate":"2017-07-03 15:59:55","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"3EE5A1C5B4BA4E7793E57D6CD13E6F4A","name":"汽车美容","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155954842.png","cellId":null},{"createDate":"2017-06-29 12:11:31","modifyDate":"2017-07-03 16:00:03","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"6AECC8E323DA4B99A0DA184F978CE212","name":"婚庆","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703160002530.png","cellId":null},{"createDate":"2017-06-29 12:11:25","modifyDate":"2017-07-03 16:00:10","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"0A5EE9440F7D49E9823B1C2D0281C61D","name":"酒店","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703160009504.png","cellId":null},{"createDate":"2017-06-29 12:11:12","modifyDate":"2017-07-03 16:00:17","createUser":null,"status":null,"sorted":null,"remark":"","shopTypeId":"9341611FB6CB43679938C51FF0664CDC","name":"家装","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703160016455.png","cellId":null},{"createDate":"2017-06-29 12:10:57","modifyDate":"2017-07-03 16:00:27","createUser":null,"status":null,"sorted":null,"remark":"家政服务","shopTypeId":"565F2C9AFE63476BABBC1DCA1DA02D4D","name":"家政","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703160026212.png","cellId":null}]
     * date : 2017-08-09 09:55:55
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
         * createDate : 2017-06-30 18:06:43
         * modifyDate : 2017-07-03 15:58:26
         * createUser : null
         * status : null
         * sorted : null
         * remark :
         * shopTypeId : 48155DA6E1C7426DABCECF732A8A704A
         * name : 电脑维修
         * pic : http://123667.oss-cn-qingdao.aliyuncs.com/201707/20170703155824417.png
         * cellId : null
         */

        private String createDate;
        private String modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String shopTypeId;
        private String name;
        private String pic;
        private Object cellId;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(String modifyDate) {
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

        public String getShopTypeId() {
            return shopTypeId;
        }

        public void setShopTypeId(String shopTypeId) {
            this.shopTypeId = shopTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }
    }
}
