package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/23.
 * 类 描 述:
 */

public class NewsTypeBean {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : [{"createDate":"2017-08-02 11:52:13","modifyDate":null,"createUser":"EDB06FE47EB34FD688F117DD6D312EE9","status":null,"sorted":null,"remark":"","typeId":"2A131D07EE654C2F9A22E80AEA598BA9","name":"阿斯蒂芬","cellId":null},{"createDate":"2017-07-03 16:06:28","modifyDate":null,"createUser":"EDB06FE47EB34FD688F117DD6D312EE9","status":null,"sorted":null,"remark":"","typeId":"94122E5C404841FE9F88DC8ACE2E4518","name":"文化体育","cellId":null},{"createDate":"2017-05-12 14:27:36","modifyDate":null,"createUser":"EDB06FE47EB34FD688F117DD6D312EE9","status":null,"sorted":null,"remark":"","typeId":"A009E7F15F7344A69D3904D931471996","name":"社区资讯","cellId":null},{"createDate":"2017-04-27 10:04:16","modifyDate":null,"createUser":"EDB06FE47EB34FD688F117DD6D312EE9","status":null,"sorted":null,"remark":"","typeId":"25C12BA152CC4D9CAC02A4FB7BC00D9F","name":"生活民生","cellId":null}]
     * date : 2017-08-08 09:42:45
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
         * createDate : 2017-08-02 11:52:13
         * modifyDate : null
         * createUser : EDB06FE47EB34FD688F117DD6D312EE9
         * status : null
         * sorted : null
         * remark :
         * typeId : 2A131D07EE654C2F9A22E80AEA598BA9
         * name : 阿斯蒂芬
         * cellId : null
         */

        private String createDate;
        private Object modifyDate;
        private String createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String typeId;
        private String name;
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

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
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

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }
    }
}
