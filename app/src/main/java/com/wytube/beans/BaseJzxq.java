package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/17.
 * 类 描 述:
 */

public class BaseJzxq {


    /**
     * msg : 获取成功
     * success : true
     * repairWork : {"repairWorkId":"13bcf8f2e57b4444ae5a941a43a4405c","typeId":"05b08a56187b41de81b930cfa03451e6","repairContent":"磨磨唧唧","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","starttime":"2017-08-03 15:38:47.0","cost":"20","userRecontent":null,"repairmanId":"c62c7ca49b7440f29340239da427fa4c","stateId":"2","numberId":"5A8973DF6FDD454CBB0E68AC05EBF024","accessId":null,"typeName":"安装毛巾架","stateName":"已完成","numberName":"0101","regUserName":"余长发","cellName":"重庆国际小区","cellId":null,"buildingName":"1栋","unitName":"1单元","mobileNo":"18680808185","repairmanName":"邱冰海","runtime":"2017-08-03 15:39:09.0","repairmanPhone":"18523053781","imgList":[{"imgId":"7359bc1ef8124e6ab4f002cf73ff58d3","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/0803/BFA2176A8F5547ABAEDF10DAC30B5C3D_temp_img_0.jpg","repairWorkId":"13bcf8f2e57b4444ae5a941a43a4405c","imgUrlFull":null}],"repairWorkResultList":null}
     */

    private String msg;
    private boolean success;
    private RepairWorkBean repairWork;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RepairWorkBean getRepairWork() {
        return repairWork;
    }

    public void setRepairWork(RepairWorkBean repairWork) {
        this.repairWork = repairWork;
    }

    public static class RepairWorkBean {
        /**
         * repairWorkId : 13bcf8f2e57b4444ae5a941a43a4405c
         * typeId : 05b08a56187b41de81b930cfa03451e6
         * repairContent : 磨磨唧唧
         * regUserId : 0E11B890D54444128B2D5E0BCA7B5B28
         * starttime : 2017-08-03 15:38:47.0
         * cost : 20
         * userRecontent : null
         * repairmanId : c62c7ca49b7440f29340239da427fa4c
         * stateId : 2
         * numberId : 5A8973DF6FDD454CBB0E68AC05EBF024
         * accessId : null
         * typeName : 安装毛巾架
         * stateName : 已完成
         * numberName : 0101
         * regUserName : 余长发
         * cellName : 重庆国际小区
         * cellId : null
         * buildingName : 1栋
         * unitName : 1单元
         * mobileNo : 18680808185
         * repairmanName : 邱冰海
         * runtime : 2017-08-03 15:39:09.0
         * repairmanPhone : 18523053781
         * imgList : [{"imgId":"7359bc1ef8124e6ab4f002cf73ff58d3","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/0803/BFA2176A8F5547ABAEDF10DAC30B5C3D_temp_img_0.jpg","repairWorkId":"13bcf8f2e57b4444ae5a941a43a4405c","imgUrlFull":null}]
         * repairWorkResultList : null
         */

        private String repairWorkId;
        private String typeId;
        private String repairContent;
        private String regUserId;
        private String starttime;
        private String cost;
        private Object userRecontent;
        private String repairmanId;
        private String stateId;
        private String numberId;
        private Object accessId;
        private String typeName;
        private String stateName;
        private String numberName;
        private String regUserName;
        private String cellName;
        private Object cellId;
        private String buildingName;
        private String unitName;
        private String mobileNo;
        private String repairmanName;
        private String runtime;
        private String repairmanPhone;
        private Object repairWorkResultList;
        private List<ImgListBean> imgList;

        public String getRepairWorkId() {
            return repairWorkId;
        }

        public void setRepairWorkId(String repairWorkId) {
            this.repairWorkId = repairWorkId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getRepairContent() {
            return repairContent;
        }

        public void setRepairContent(String repairContent) {
            this.repairContent = repairContent;
        }

        public String getRegUserId() {
            return regUserId;
        }

        public void setRegUserId(String regUserId) {
            this.regUserId = regUserId;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public Object getUserRecontent() {
            return userRecontent;
        }

        public void setUserRecontent(Object userRecontent) {
            this.userRecontent = userRecontent;
        }

        public String getRepairmanId() {
            return repairmanId;
        }

        public void setRepairmanId(String repairmanId) {
            this.repairmanId = repairmanId;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getNumberId() {
            return numberId;
        }

        public void setNumberId(String numberId) {
            this.numberId = numberId;
        }

        public Object getAccessId() {
            return accessId;
        }

        public void setAccessId(Object accessId) {
            this.accessId = accessId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getNumberName() {
            return numberName;
        }

        public void setNumberName(String numberName) {
            this.numberName = numberName;
        }

        public String getRegUserName() {
            return regUserName;
        }

        public void setRegUserName(String regUserName) {
            this.regUserName = regUserName;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getRepairmanName() {
            return repairmanName;
        }

        public void setRepairmanName(String repairmanName) {
            this.repairmanName = repairmanName;
        }

        public String getRuntime() {
            return runtime;
        }

        public void setRuntime(String runtime) {
            this.runtime = runtime;
        }

        public String getRepairmanPhone() {
            return repairmanPhone;
        }

        public void setRepairmanPhone(String repairmanPhone) {
            this.repairmanPhone = repairmanPhone;
        }

        public Object getRepairWorkResultList() {
            return repairWorkResultList;
        }

        public void setRepairWorkResultList(Object repairWorkResultList) {
            this.repairWorkResultList = repairWorkResultList;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public static class ImgListBean {
            /**
             * imgId : 7359bc1ef8124e6ab4f002cf73ff58d3
             * imgUrl : http://123667.oss-cn-qingdao.aliyuncs.com/0803/BFA2176A8F5547ABAEDF10DAC30B5C3D_temp_img_0.jpg
             * repairWorkId : 13bcf8f2e57b4444ae5a941a43a4405c
             * imgUrlFull : null
             */

            private String imgId;
            private String imgUrl;
            private String repairWorkId;
            private Object imgUrlFull;

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getRepairWorkId() {
                return repairWorkId;
            }

            public void setRepairWorkId(String repairWorkId) {
                this.repairWorkId = repairWorkId;
            }

            public Object getImgUrlFull() {
                return imgUrlFull;
            }

            public void setImgUrlFull(Object imgUrlFull) {
                this.imgUrlFull = imgUrlFull;
            }
        }
    }
}
