package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/14.
 * 类 描 述:
 */

public class BaseBxxq {


    /**
     * msg : 获取成功
     * success : true
     * suitWork : {"suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","suitContent":"测试","starttime":"2017-07-18 10:55:39.0","userRecontent":"好样的","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","numberId":"C13CCC1D0BA041F29CF3A3362B4A02D9","accessId":null,"suitTypeId":"0","suitStateId":"2","cellId":null,"cellName":"重庆国际小区","numberName":"1015","unitName":"4单元","regUserName":"移动端","buildingName":"1栋","suitStateName":"已评价","mobileNo":"18696633478","suitTypeName":"环境管理类","imgList":[{"imgId":"e04f3f8e120340a6a9155e1bdd4ee921","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/0718/91FD58EB954D4D499E47F1515AEB5EA1_temp_upload_img_0.jpg","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","imgUrlFull":null}],"resultList":[{"score":"5","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","dimensionId":"0","dimensionName":"服务态度"},{"score":"5","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","dimensionId":"1","dimensionName":"回馈及时性"}],"replyContent":"完成","replytime":"2017-07-18 10:57:19.0"}
     */

    private String msg;
    private boolean success;
    private SuitWorkBean suitWork;

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

    public SuitWorkBean getSuitWork() {
        return suitWork;
    }

    public void setSuitWork(SuitWorkBean suitWork) {
        this.suitWork = suitWork;
    }

    public static class SuitWorkBean {
        /**
         * suitWorkId : 58b0eb5ce32b43d7850fe2daeea9bc27
         * suitContent : 测试
         * starttime : 2017-07-18 10:55:39.0
         * userRecontent : 好样的
         * regUserId : 5C7CEB75724D43EB93EBFD59DD8BA811
         * numberId : C13CCC1D0BA041F29CF3A3362B4A02D9
         * accessId : null
         * suitTypeId : 0
         * suitStateId : 2
         * cellId : null
         * cellName : 重庆国际小区
         * numberName : 1015
         * unitName : 4单元
         * regUserName : 移动端
         * buildingName : 1栋
         * suitStateName : 已评价
         * mobileNo : 18696633478
         * suitTypeName : 环境管理类
         * imgList : [{"imgId":"e04f3f8e120340a6a9155e1bdd4ee921","imgUrl":"http://123667.oss-cn-qingdao.aliyuncs.com/0718/91FD58EB954D4D499E47F1515AEB5EA1_temp_upload_img_0.jpg","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","imgUrlFull":null}]
         * resultList : [{"score":"5","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","dimensionId":"0","dimensionName":"服务态度"},{"score":"5","suitWorkId":"58b0eb5ce32b43d7850fe2daeea9bc27","dimensionId":"1","dimensionName":"回馈及时性"}]
         * replyContent : 完成
         * replytime : 2017-07-18 10:57:19.0
         */

        private String suitWorkId;
        private String suitContent;
        private String starttime;
        private String userRecontent;
        private String regUserId;
        private String numberId;
        private Object accessId;
        private String suitTypeId;
        private String suitStateId;
        private Object cellId;
        private String cellName;
        private String numberName;
        private String unitName;
        private String regUserName;
        private String buildingName;
        private String suitStateName;
        private String mobileNo;
        private String suitTypeName;
        private String replyContent;
        private String replytime;
        private List<ImgListBean> imgList;
        private List<ResultListBean> resultList;

        public String getSuitWorkId() {
            return suitWorkId;
        }

        public void setSuitWorkId(String suitWorkId) {
            this.suitWorkId = suitWorkId;
        }

        public String getSuitContent() {
            return suitContent;
        }

        public void setSuitContent(String suitContent) {
            this.suitContent = suitContent;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getUserRecontent() {
            return userRecontent;
        }

        public void setUserRecontent(String userRecontent) {
            this.userRecontent = userRecontent;
        }

        public String getRegUserId() {
            return regUserId;
        }

        public void setRegUserId(String regUserId) {
            this.regUserId = regUserId;
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

        public String getSuitTypeId() {
            return suitTypeId;
        }

        public void setSuitTypeId(String suitTypeId) {
            this.suitTypeId = suitTypeId;
        }

        public String getSuitStateId() {
            return suitStateId;
        }

        public void setSuitStateId(String suitStateId) {
            this.suitStateId = suitStateId;
        }

        public Object getCellId() {
            return cellId;
        }

        public void setCellId(Object cellId) {
            this.cellId = cellId;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getNumberName() {
            return numberName;
        }

        public void setNumberName(String numberName) {
            this.numberName = numberName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getRegUserName() {
            return regUserName;
        }

        public void setRegUserName(String regUserName) {
            this.regUserName = regUserName;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getSuitStateName() {
            return suitStateName;
        }

        public void setSuitStateName(String suitStateName) {
            this.suitStateName = suitStateName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getSuitTypeName() {
            return suitTypeName;
        }

        public void setSuitTypeName(String suitTypeName) {
            this.suitTypeName = suitTypeName;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getReplytime() {
            return replytime;
        }

        public void setReplytime(String replytime) {
            this.replytime = replytime;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ImgListBean {
            /**
             * imgId : e04f3f8e120340a6a9155e1bdd4ee921
             * imgUrl : http://123667.oss-cn-qingdao.aliyuncs.com/0718/91FD58EB954D4D499E47F1515AEB5EA1_temp_upload_img_0.jpg
             * suitWorkId : 58b0eb5ce32b43d7850fe2daeea9bc27
             * imgUrlFull : null
             */

            private String imgId;
            private String imgUrl;
            private String suitWorkId;
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

            public String getSuitWorkId() {
                return suitWorkId;
            }

            public void setSuitWorkId(String suitWorkId) {
                this.suitWorkId = suitWorkId;
            }

            public Object getImgUrlFull() {
                return imgUrlFull;
            }

            public void setImgUrlFull(Object imgUrlFull) {
                this.imgUrlFull = imgUrlFull;
            }
        }

        public static class ResultListBean {
            /**
             * score : 5
             * suitWorkId : 58b0eb5ce32b43d7850fe2daeea9bc27
             * dimensionId : 0
             * dimensionName : 服务态度
             */

            private float score;
            private String suitWorkId;
            private String dimensionId;
            private String dimensionName;

            public float getScore() {
                return score;
            }

            public void setScore(float score) {
                this.score = score;
            }

            public String getSuitWorkId() {
                return suitWorkId;
            }

            public void setSuitWorkId(String suitWorkId) {
                this.suitWorkId = suitWorkId;
            }

            public String getDimensionId() {
                return dimensionId;
            }

            public void setDimensionId(String dimensionId) {
                this.dimensionId = dimensionId;
            }

            public String getDimensionName() {
                return dimensionName;
            }

            public void setDimensionName(String dimensionName) {
                this.dimensionName = dimensionName;
            }
        }
    }
}
