package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/7/31.
 * 类 描 述:
 */

public class BaseLogin {

    /**
     * success : true
     * message : 登录成功
     * code : 200
     * data : {"sipPass":"123456","code":"code","cellInfoDTO":{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","cellId":"cell_test","cellName":"重庆国际小区","accessId":null,"shopId":null,"advType":null,"cityId":null,"cellAddress":"","longitude":"","latitude":"","areaLand":"","areaConstruction":"","committeeId":null,"logo":"http://123667.oss-cn-qingdao.aliyuncs.com/0608/D1B8F0EC5F304F3B9842061817F04FD1_default.jpg","term":"2025-05-23 11:55:19","ip":"","port":"","appPrivateKey":"65156165165","appPublicKey":"1561651651","appId":"15156","appName":"dwqd","userName":null,"userPhone":null,"account":null,"roleId":null,"userType":null,"code":"code","sip":"18680808185","sipPass":"123456","concat":"10086"},"concat":"10086","sip":"18680808185","userDTO":{"createDate":"2017-02-15 10:40:37","modifyDate":"2017-04-28 09:45:01","createUser":null,"status":1,"sorted":null,"remark":"测试数据","userId":"EDB06FE47EB34FD688F117DD6D312EE9","account":"kevinblandy","passWord":"e10adc3949ba59abbe56e057f20f883e","userPhone":"18523570974","userName":"余文朋","userEmail":null,"userType":1,"lastLogin":"2017-08-09 01:47:16","loginCount":1356,"shopId":"1e5e8f2a192f4daab954229219832b91","cellId":null,"roleId":null,"roleName":null},"token":"25CAC7DDF9114A9EB6D9E844078ABB50"}
     * date : 2017-08-09 08:51:55
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
         * sipPass : 123456
         * code : code
         * cellInfoDTO : {"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"","cellId":"cell_test","cellName":"重庆国际小区","accessId":null,"shopId":null,"advType":null,"cityId":null,"cellAddress":"","longitude":"","latitude":"","areaLand":"","areaConstruction":"","committeeId":null,"logo":"http://123667.oss-cn-qingdao.aliyuncs.com/0608/D1B8F0EC5F304F3B9842061817F04FD1_default.jpg","term":"2025-05-23 11:55:19","ip":"","port":"","appPrivateKey":"65156165165","appPublicKey":"1561651651","appId":"15156","appName":"dwqd","userName":null,"userPhone":null,"account":null,"roleId":null,"userType":null,"code":"code","sip":"18680808185","sipPass":"123456","concat":"10086"}
         * concat : 10086
         * sip : 18680808185
         * userDTO : {"createDate":"2017-02-15 10:40:37","modifyDate":"2017-04-28 09:45:01","createUser":null,"status":1,"sorted":null,"remark":"测试数据","userId":"EDB06FE47EB34FD688F117DD6D312EE9","account":"kevinblandy","passWord":"e10adc3949ba59abbe56e057f20f883e","userPhone":"18523570974","userName":"余文朋","userEmail":null,"userType":1,"lastLogin":"2017-08-09 01:47:16","loginCount":1356,"shopId":"1e5e8f2a192f4daab954229219832b91","cellId":null,"roleId":null,"roleName":null}
         * token : 25CAC7DDF9114A9EB6D9E844078ABB50
         */

        private String sipPass;
        private String code;
        private CellInfoDTOBean cellInfoDTO;
        private String concat;
        private String sip;
        private UserDTOBean userDTO;
        private String token;

        public String getSipPass() {
            return sipPass;
        }

        public void setSipPass(String sipPass) {
            this.sipPass = sipPass;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public CellInfoDTOBean getCellInfoDTO() {
            return cellInfoDTO;
        }

        public void setCellInfoDTO(CellInfoDTOBean cellInfoDTO) {
            this.cellInfoDTO = cellInfoDTO;
        }

        public String getConcat() {
            return concat;
        }

        public void setConcat(String concat) {
            this.concat = concat;
        }

        public String getSip() {
            return sip;
        }

        public void setSip(String sip) {
            this.sip = sip;
        }

        public UserDTOBean getUserDTO() {
            return userDTO;
        }

        public void setUserDTO(UserDTOBean userDTO) {
            this.userDTO = userDTO;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class CellInfoDTOBean {
            /**
             * createDate : null
             * modifyDate : null
             * createUser : null
             * status : null
             * sorted : null
             * remark :
             * cellId : cell_test
             * cellName : 重庆国际小区
             * accessId : null
             * shopId : null
             * advType : null
             * cityId : null
             * cellAddress :
             * longitude :
             * latitude :
             * areaLand :
             * areaConstruction :
             * committeeId : null
             * logo : http://123667.oss-cn-qingdao.aliyuncs.com/0608/D1B8F0EC5F304F3B9842061817F04FD1_default.jpg
             * term : 2025-05-23 11:55:19
             * ip :
             * port :
             * appPrivateKey : 65156165165
             * appPublicKey : 1561651651
             * appId : 15156
             * appName : dwqd
             * userName : null
             * userPhone : null
             * account : null
             * roleId : null
             * userType : null
             * code : code
             * sip : 18680808185
             * sipPass : 123456
             * concat : 10086
             */

            private Object createDate;
            private Object modifyDate;
            private Object createUser;
            private Object status;
            private Object sorted;
            private String remark;
            private String cellId;
            private String cellName;
            private Object accessId;
            private Object shopId;
            private Object advType;
            private Object cityId;
            private String cellAddress;
            private String longitude;
            private String latitude;
            private String areaLand;
            private String areaConstruction;
            private Object committeeId;
            private String logo;
            private String term;
            private String ip;
            private String port;
            private String appPrivateKey;
            private String appPublicKey;
            private String appId;
            private String appName;
            private Object userName;
            private Object userPhone;
            private Object account;
            private Object roleId;
            private Object userType;
            private String code;
            private String sip;
            private String sipPass;
            private String concat;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

            public Object getAccessId() {
                return accessId;
            }

            public void setAccessId(Object accessId) {
                this.accessId = accessId;
            }

            public Object getShopId() {
                return shopId;
            }

            public void setShopId(Object shopId) {
                this.shopId = shopId;
            }

            public Object getAdvType() {
                return advType;
            }

            public void setAdvType(Object advType) {
                this.advType = advType;
            }

            public Object getCityId() {
                return cityId;
            }

            public void setCityId(Object cityId) {
                this.cityId = cityId;
            }

            public String getCellAddress() {
                return cellAddress;
            }

            public void setCellAddress(String cellAddress) {
                this.cellAddress = cellAddress;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getAreaLand() {
                return areaLand;
            }

            public void setAreaLand(String areaLand) {
                this.areaLand = areaLand;
            }

            public String getAreaConstruction() {
                return areaConstruction;
            }

            public void setAreaConstruction(String areaConstruction) {
                this.areaConstruction = areaConstruction;
            }

            public Object getCommitteeId() {
                return committeeId;
            }

            public void setCommitteeId(Object committeeId) {
                this.committeeId = committeeId;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getTerm() {
                return term;
            }

            public void setTerm(String term) {
                this.term = term;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }

            public String getAppPrivateKey() {
                return appPrivateKey;
            }

            public void setAppPrivateKey(String appPrivateKey) {
                this.appPrivateKey = appPrivateKey;
            }

            public String getAppPublicKey() {
                return appPublicKey;
            }

            public void setAppPublicKey(String appPublicKey) {
                this.appPublicKey = appPublicKey;
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

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public Object getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(Object userPhone) {
                this.userPhone = userPhone;
            }

            public Object getAccount() {
                return account;
            }

            public void setAccount(Object account) {
                this.account = account;
            }

            public Object getRoleId() {
                return roleId;
            }

            public void setRoleId(Object roleId) {
                this.roleId = roleId;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getSip() {
                return sip;
            }

            public void setSip(String sip) {
                this.sip = sip;
            }

            public String getSipPass() {
                return sipPass;
            }

            public void setSipPass(String sipPass) {
                this.sipPass = sipPass;
            }

            public String getConcat() {
                return concat;
            }

            public void setConcat(String concat) {
                this.concat = concat;
            }
        }

        public static class UserDTOBean {
            /**
             * createDate : 2017-02-15 10:40:37
             * modifyDate : 2017-04-28 09:45:01
             * createUser : null
             * status : 1
             * sorted : null
             * remark : 测试数据
             * userId : EDB06FE47EB34FD688F117DD6D312EE9
             * account : kevinblandy
             * passWord : e10adc3949ba59abbe56e057f20f883e
             * userPhone : 18523570974
             * userName : 余文朋
             * userEmail : null
             * userType : 1
             * lastLogin : 2017-08-09 01:47:16
             * loginCount : 1356
             * shopId : 1e5e8f2a192f4daab954229219832b91
             * cellId : null
             * roleId : null
             * roleName : null
             */

            private String createDate;
            private String modifyDate;
            private Object createUser;
            private int status;
            private Object sorted;
            private String remark;
            private String userId;
            private String account;
            private String passWord;
            private String userPhone;
            private String userName;
            private Object userEmail;
            private int userType;
            private String lastLogin;
            private int loginCount;
            private String shopId;
            private Object cellId;
            private Object roleId;
            private Object roleName;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getPassWord() {
                return passWord;
            }

            public void setPassWord(String passWord) {
                this.passWord = passWord;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public Object getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(Object userEmail) {
                this.userEmail = userEmail;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public String getLastLogin() {
                return lastLogin;
            }

            public void setLastLogin(String lastLogin) {
                this.lastLogin = lastLogin;
            }

            public int getLoginCount() {
                return loginCount;
            }

            public void setLoginCount(int loginCount) {
                this.loginCount = loginCount;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public Object getCellId() {
                return cellId;
            }

            public void setCellId(Object cellId) {
                this.cellId = cellId;
            }

            public Object getRoleId() {
                return roleId;
            }

            public void setRoleId(Object roleId) {
                this.roleId = roleId;
            }

            public Object getRoleName() {
                return roleName;
            }

            public void setRoleName(Object roleName) {
                this.roleName = roleName;
            }
        }
    }
}
