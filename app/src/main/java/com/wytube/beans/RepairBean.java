package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/4.
 * 类 描 述:
 */

public class RepairBean {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : [{"repairWorkId":"4034273e17d34563ae4e875f635ee21a","repairContent":"坏了","starttime":"2017-04-24 09:54:58.0","cost":"15","userRecontent":null,"stateId":0,"typeName":"疏通座厕、蹲位","stateName":"待处理","numberName":"0102","regUserName":"刘先生","cellName":"长航小区","buildingName":"1栋","unitName":"1单元","mobileNo":"18523053771","repairmanName":null,"runtime":"2017-04-24 11:18:09.0","repairmanPhone":"13423102145","imgList":[{"imgUrl":"https: //www.baidu.com/upload/20160128/20160128051447653122906530.jpg"},{"imgUrl":"https: //www.baidu.com/upload/20160128/20160128051447653122906530.jpg"}],"repairWorkResultList":[{"dimensionId":0,"dimensionName":"服务质量","Score":4},{"dimensionId":0,"dimensionName":"人员态度","Score":4}]},{"repairWorkId":"58f3e951b1fb4ba18577f27ff81350a9","repairContent":"坏了","starttime":"2017-04-24 09:54:42.0","cost":"15","userRecontent":null,"repairmanId":null,"stateId":null,"numberId":"4AB9F613BBAE469AB3BB0C00C861BFFA","accessId":null,"typeName":"疏通座厕、蹲位","stateName":null,"numberName":"0102","regUserName":"刘先生","cellName":"长航小区","cellId":null,"buildingName":"1栋","unitName":"1单元","mobileNo":"18523053771","repairmanName":null,"imgList":[],"repairWorkResultList":null}]
     * date : 2017-04-24 09:55:05
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
         * repairWorkId : 4034273e17d34563ae4e875f635ee21a
         * repairContent : 坏了
         * starttime : 2017-04-24 09:54:58.0
         * cost : 15
         * userRecontent : null
         * stateId : 0
         * typeName : 疏通座厕、蹲位
         * stateName : 待处理
         * numberName : 0102
         * regUserName : 刘先生
         * cellName : 长航小区
         * buildingName : 1栋
         * unitName : 1单元
         * mobileNo : 18523053771
         * repairmanName : null
         * runtime : 2017-04-24 11:18:09.0
         * repairmanPhone : 13423102145
         * imgList : [{"imgUrl":"https: //www.baidu.com/upload/20160128/20160128051447653122906530.jpg"},{"imgUrl":"https: //www.baidu.com/upload/20160128/20160128051447653122906530.jpg"}]
         * repairWorkResultList : [{"dimensionId":0,"dimensionName":"服务质量","Score":4},{"dimensionId":0,"dimensionName":"人员态度","Score":4}]
         * repairmanId : null
         * numberId : 4AB9F613BBAE469AB3BB0C00C861BFFA
         * accessId : null
         * cellId : null
         */

        private String repairWorkId;
        private String repairContent;
        private String starttime;
        private String cost;
        private Object userRecontent;
        private int stateId;
        private String typeName;
        private String stateName;
        private String numberName;
        private String regUserName;
        private String cellName;
        private String buildingName;
        private String unitName;
        private String mobileNo;
        private Object repairmanName;
        private String runtime;
        private String repairmanPhone;
        private Object repairmanId;
        private String numberId;
        private Object accessId;
        private Object cellId;
        private List<ImgListBean> imgList;
        private List<RepairWorkResultListBean> repairWorkResultList;

        public String getRepairWorkId() {
            return repairWorkId;
        }

        public void setRepairWorkId(String repairWorkId) {
            this.repairWorkId = repairWorkId;
        }

        public String getRepairContent() {
            return repairContent;
        }

        public void setRepairContent(String repairContent) {
            this.repairContent = repairContent;
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

        public int getStateId() {
            return stateId;
        }

        public void setStateId(int stateId) {
            this.stateId = stateId;
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

        public Object getRepairmanName() {
            return repairmanName;
        }

        public void setRepairmanName(Object repairmanName) {
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

        public Object getRepairmanId() {
            return repairmanId;
        }

        public void setRepairmanId(Object repairmanId) {
            this.repairmanId = repairmanId;
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

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public List<RepairWorkResultListBean> getRepairWorkResultList() {
            return repairWorkResultList;
        }

        public void setRepairWorkResultList(List<RepairWorkResultListBean> repairWorkResultList) {
            this.repairWorkResultList = repairWorkResultList;
        }

        public static class ImgListBean {
            /**
             * imgUrl : https: //www.baidu.com/upload/20160128/20160128051447653122906530.jpg
             */

            private String imgUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class RepairWorkResultListBean {
            /**
             * dimensionId : 0
             * dimensionName : 服务质量
             * Score : 4
             */

            private int dimensionId;
            private String dimensionName;
            private int Score;

            public int getDimensionId() {
                return dimensionId;
            }

            public void setDimensionId(int dimensionId) {
                this.dimensionId = dimensionId;
            }

            public String getDimensionName() {
                return dimensionName;
            }

            public void setDimensionName(String dimensionName) {
                this.dimensionName = dimensionName;
            }

            public int getScore() {
                return Score;
            }

            public void setScore(int Score) {
                this.Score = Score;
            }
        }
    }
}
