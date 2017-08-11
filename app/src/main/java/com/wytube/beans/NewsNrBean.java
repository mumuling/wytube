package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/8.
 * 类 描 述:
 */

public class NewsNrBean {

    /**
     * success : true
     * message : ok
     * code : 200
     * data : [{"createDate":"2017-08-03 17:37:02","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"infoId":"F4554B924CB84BD7B69D4BDB2A6F0A9F","title":"测试测试","desc":"","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/0803/15C5DE28AC3549F0AE5BE2C2A6DFBACA_20170803174557.png","type":"2A131D07EE654C2F9A22E80AEA598BA9","typeName":"阿斯蒂芬","content":"测测试","cellId":null}]
     * date : 2017-08-08 12:14:50
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
         * createDate : 2017-08-03 17:37:02
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark : null
         * infoId : F4554B924CB84BD7B69D4BDB2A6F0A9F
         * title : 测试测试
         * desc :
         * pic : http://123667.oss-cn-qingdao.aliyuncs.com/0803/15C5DE28AC3549F0AE5BE2C2A6DFBACA_20170803174557.png
         * type : 2A131D07EE654C2F9A22E80AEA598BA9
         * typeName : 阿斯蒂芬
         * content : 测测试
         * cellId : null
         */

        private String createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private Object remark;
        private String infoId;
        private String title;
        private String desc;
        private String pic;
        private String type;
        private String typeName;
        private String content;
        private Object cellId;

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

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getInfoId() {
            return infoId;
        }

        public void setInfoId(String infoId) {
            this.infoId = infoId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }
    }
}
