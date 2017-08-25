package com.wytube.beans;

/**
 * Created by Var on 2017/4/28.
 */

public class VersionBean {

    /**
     * success : true
     * message : ok
     * code : 200
     * data : {"createDate":"2017-04-28 14:19:09.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","versionId":"7516C30D860F4EC3AA068C8D10670395","version":"1.5","appId":"FFE76ED289F74217863628E44BFDD137","appName":"话务佳","path":"http://123667.oss-cn-qingdao.aliyuncs.com/app/","fileName":"Door后面需要优化调整的地方.docx","type":null}
     * date : 2017-04-28 14:35:31
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
         * createDate : 2017-04-28 14:19:09.0
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark :
         * versionId : 7516C30D860F4EC3AA068C8D10670395
         * version : 1.5
         * appId : FFE76ED289F74217863628E44BFDD137
         * appName : 话务佳
         * path : http://123667.oss-cn-qingdao.aliyuncs.com/app/
         * fileName : Door后面需要优化调整的地方.docx
         * type : null
         */

        private String createDate;
        private Object modifyDate;
        private Object createUser;
        private Object status;
        private Object sorted;
        private String remark;
        private String versionId;
        private String version;
        private String appId;
        private String appName;
        private String path;
        private String fileName;
        private Object type;

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

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }
    }
}
