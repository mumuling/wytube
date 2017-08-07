package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/4.
 * 类 描 述: 指派人员列表
 */

public class BaseZPry {


    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"repairmanId":"c62c7ca49b7440f29340239da427fa4c","repairmanName":"邱冰海","mobileNo":"18523053781","accessId":null,"cellId":"cell_test"},{"repairmanId":"fc70abbef7cd4cfcb2bab71e8fcf738d","repairmanName":"张欢","mobileNo":"18725854139","accessId":null,"cellId":"cell_test"},{"repairmanId":"94a874cec7e242d88055536882eeea86","repairmanName":"张无情","mobileNo":"18523014786","accessId":null,"cellId":"cell_test"},{"repairmanId":"92bc0574f981483ebde2cb6aea6d934b","repairmanName":"刘彬","mobileNo":"15845621346","accessId":null,"cellId":"cell_test"},{"repairmanId":"894adc08872d4c98bb96f5927ce067cd","repairmanName":"余师傅","mobileNo":"18523570974","accessId":null,"cellId":"cell_test"}]
     * date : 2017-08-04 16:22:51
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
         * repairmanId : c62c7ca49b7440f29340239da427fa4c
         * repairmanName : 邱冰海
         * mobileNo : 18523053781
         * accessId : null
         * cellId : cell_test
         */

        private String repairmanId;
        private String repairmanName;
        private String mobileNo;
        private Object accessId;
        private String cellId;

        public String getRepairmanId() {
            return repairmanId;
        }

        public void setRepairmanId(String repairmanId) {
            this.repairmanId = repairmanId;
        }

        public String getRepairmanName() {
            return repairmanName;
        }

        public void setRepairmanName(String repairmanName) {
            this.repairmanName = repairmanName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
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
    }
}
