package com.wytube.beans;

import android.view.LayoutInflater;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LIN on 2017/8/12.
 */

public class HappyBean implements Serializable{

    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"efa97596dd114c139aaf7e8daf848af5","starttime":"2017-12-19 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"还是觉得好","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"01a1651c903043f0b4e3f26101bdd884","starttime":"2017-09-27 00:00:00.0","regUserId":"4A9989757AE5451DBE2382FBE19DC115","content":"洗脚","userId":"EDB06FE47EB34FD688F117DD6D312EE9","operateTime":"2017-06-29 15:28:23.0","stateId":"1","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"12332112332","regUserPhone":"12332112332","userName":"余文朋","stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"eb950308f5d84d33b79d630fa8a7bd25","starttime":"2017-08-30 00:00:00.0","regUserId":"4A9989757AE5451DBE2382FBE19DC115","content":"：谁是谁","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"12332112332","regUserPhone":"12332112332","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"a5b92540bbf04c6f935cbc8e32f54c54","starttime":"2017-08-24 00:00:00.0","regUserId":"7E499577309647A9863729B023EDC895","content":"哦哦哦哦哦哦哦哦","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18723196869","regUserPhone":"18723196869","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"fe804a1e289f40b98bf2145da980f559","starttime":"2017-08-17 00:00:00.0","regUserId":"FA837714111A46B3A30C8FA437206C6A","content":"打击打击假的","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18716366588","regUserPhone":"18716366588","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"2e510660c8cb432c97c4896bfc80c411","starttime":"2017-08-10 00:00:00.0","regUserId":"7E499577309647A9863729B023EDC895","content":"嗯嗯嗯嗯嗯嗯","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18723196869","regUserPhone":"18723196869","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"bd6db886103d46c0ba74a366d9065b84","starttime":"2017-08-05 00:00:00.0","regUserId":"46644CD2A8FA42D8A4B03F8C35816ADB","content":"生日","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18716366888","regUserPhone":"18716366888","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"fd20fd741fc545a199082f1557a53453","starttime":"2017-07-20 00:00:00.0","regUserId":"3A625FC76F5B4DEA82BB1A95284317E8","content":"家里儿子要结婚，要占用小区场地","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"15723075162","regUserPhone":"15723075162","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"abeccdf9e28147a4a1a8adb4b29ddb4a","starttime":"2017-07-19 00:00:00.0","regUserId":"D66980A99E6E4948BA1EB6373655F0B2","content":"请添加相关描述","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570000","regUserPhone":"18523570000","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"44e615f38aad4f52908fa315428638f6","starttime":"2017-07-18 00:00:00.0","regUserId":"D66980A99E6E4948BA1EB6373655F0B2","content":"请添加相关描述","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570000","regUserPhone":"18523570000","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"dfaf739d2b794aeb94763475451226d2","starttime":"2017-07-18 00:00:00.0","regUserId":"D66980A99E6E4948BA1EB6373655F0B2","content":"按摩","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"3单元","numberName":"3215","regUserName":"18523570000","regUserPhone":"18523570000","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"c414832f126a48ab8652477c0e232a53","starttime":"2017-07-18 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"进化无肉不欢被我4薄荷冰红茶人","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"8a6af0fb60c84f5e96ae631451a5034b","starttime":"2017-07-17 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"请添加相关描述","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"f96b0649a15b460c9ddcc63ae7c3c5f6","starttime":"2017-07-17 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"请添加相关描述","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"72f04ea055f34c5ab099015839ee2d1f","starttime":"2017-07-17 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"hgreher","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"05798809ea044e3fa5904cf5ec28c7af","starttime":"2017-07-13 00:00:00.0","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","content":"要结婚了","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"0103","regUserName":"移动端","regUserPhone":"18696633478","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"05798809ea044e3fa5904cf5ec28c7af","starttime":"2017-07-13 00:00:00.0","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","content":"要结婚了","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"4单元","numberName":"1015","regUserName":"移动端","regUserPhone":"18696633478","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"05798809ea044e3fa5904cf5ec28c7af","starttime":"2017-07-13 00:00:00.0","regUserId":"5C7CEB75724D43EB93EBFD59DD8BA811","content":"要结婚了","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"移动端","regUserPhone":"18696633478","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"c22d6246d702422583848941275a924e","starttime":"2017-07-12 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"rwgrwgrweg","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null},{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"celebrationId":"8af15714ffe145aa948f7bb1b7aaf652","starttime":"2017-07-12 00:00:00.0","regUserId":"0E11B890D54444128B2D5E0BCA7B5B28","content":"请添加相关描述","userId":null,"operateTime":null,"stateId":"0","cellId":"cell_test","cellName":"重庆国际小区","buildingName":"1栋","unitName":"1单元","numberName":"0101","regUserName":"余长发","regUserPhone":"18680808185","userName":null,"stateName":null}]
     * date : 2017-08-12 12:51:00
     */

    public boolean success;
    public String message;
    public int code;
    public String date;

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private LayoutInflater inflater;
    private boolean isChecked;
    private boolean isShow;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<DataBean> data;

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

    public static class DataBean  implements Serializable {
        /**
         * createDate : null
         * modifyDate : null
         * createUser : null
         * status : null
         * sorted : null
         * remark : null
         * celebrationId : efa97596dd114c139aaf7e8daf848af5
         * starttime : 2017-12-19 00:00:00.0
         * regUserId : 0E11B890D54444128B2D5E0BCA7B5B28
         * content : 还是觉得好
         * userId : null
         * operateTime : null
         * stateId : 0
         * cellId : cell_test
         * cellName : 重庆国际小区
         * buildingName : 1栋
         * unitName : 1单元
         * numberName : 0101
         * regUserName : 余长发
         * regUserPhone : 18680808185
         * userName : null
         * stateName : null
         */

        public Object createDate;
        public Object modifyDate;
        public Object createUser;
        public Object status;
        public Object sorted;
        public Object remark;
        public String celebrationId;
        public String starttime;
        public String regUserId;
        public String content;
        public Object userId;
        public Object operateTime;
        public int stateId;
        public String cellId;
        public String cellName;
        public String buildingName;
        public String unitName;
        public String numberName;
        public String regUserName;
        public String regUserPhone;
        public Object userName;
        public String stateName;

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

        public String getCelebrationId() {
            return celebrationId;
        }

        public void setCelebrationId(String celebrationId) {
            this.celebrationId = celebrationId;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getRegUserId() {
            return regUserId;
        }

        public void setRegUserId(String regUserId) {
            this.regUserId = regUserId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(Object operateTime) {
            this.operateTime = operateTime;
        }

        public int getStateId() {
            return stateId;
        }

        public void setStateId(int stateId) {
            this.stateId = stateId;
        }

        public String getCellId() {
            return cellId;
        }

        public void setCellId(String cellId) {
            this.cellId = cellId;
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

        public String getRegUserPhone() {
            return regUserPhone;
        }

        public void setRegUserPhone(String regUserPhone) {
            this.regUserPhone = regUserPhone;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }
    }
}
