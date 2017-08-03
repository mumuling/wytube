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
     * data : {"cellInfoDTO":{"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"123","cellId":"1147424848043600","cellName":"长航小区","accessId":"10000001","shopId":null,"advType":null,"cityId":null,"cellAddress":"重庆","longitude":"116.304233","latitude":"40.000991","areaLand":"21.00","areaConstruction":"516.00","committeeId":"1147424838893100","logo":null,"term":null,"ip":null,"port":null,"appPrivateKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB","appPublicKey":"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOJtlnppeIy1gULmr0RdDNjIyPAdjIG4+QWi1EBbx9Q39y8JVfhTtkSQPQuJZg4vxJ6NfxgXKwxS335hvMVpxd58Xy1Kxt+UUSHBaEF0v6Ext3yaajuV2JGUzMIzOOTv1cJ/Ua+I8mN3ReshSUtlvz554deMc6I3k3anvhkO/ZolAgMBAAECgYEAiwnGFEb9qlGuHRmwWCdXQysQEDnk1Kdz6p0Q/rAdJdhz5aMy8jjdPH7hrVrimyWD8+RpPa7EVV3yNRXpJ8QKpqr0lg4uuC2HjxPNqvf30Fs9oBBMm/CdqCS36JsR7TsQWbzxMcquowZKzqv5iKIefMFUkLD4/owQeRNzH+fWUMUCQQD8Byr7i7u/xKXmVdgcJB/P0p1Cnf/u/5esNs6pgXt8PWCntfpER4/T1QRDH5rEitYY3o6dz8PF5P7dc9awNUSTAkEA5f8ibE9pS3ATlaBq4ArFER4faJzozM7u42aiLSYkYUiXzcFCxwCa5ud9jPV3Oh/jBUrzp6Fb90hV4nmD+AHRZwJABgO1xuMCzATJYMHTsng6Oh9wmVJj9TQsTnPQYsMwSzq7v8TcAB0lFY0T2PY8H0yg518IUEPRDDv2yRommXXr+QJBAN6zVr+NfSVAlpYRSKs7gmn6wurm1DxMOuAR5wLUpfFU+ziN430RxuvCRr2QiSvM6GOdmaQ9B/G/JvouM2yXRg0CQDcOE5FOhYqOEKUjPuvG2LdnNzzWyy2YZPd26uGuj0DUHj9suSeOWgtmlG4vm7KhIpyynXIMqDxW8/eGCPVHy2s=","appId":"2015052600090779","appName":"asdf","userName":null,"userPhone":null,"account":null,"roleId":null,"userType":null},"userDTO":{"createDate":"2017-02-15 12:03:15","modifyDate":"2017-03-16 11:41:02","createUser":null,"status":1,"sorted":null,"remark":"测试","userId":"02175E93F53E4127AEA2EB3F78306005","account":"qiubin","passWord":"e10adc3949ba59abbe56e057f20f883e","userPhone":"18523570974","userName":"邱冰海","userEmail":null,"userType":1,"lastLogin":"2017-07-31 16:09:27","loginCount":228,"shopId":"","cellId":null,"roleId":null,"roleName":null},"token":"4937864BE43C42628F5D25E6D1282EED"}
     * date : 2017-07-31 16:22:16
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
         * cellInfoDTO : {"createDate":null,"modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":"123","cellId":"1147424848043600","cellName":"长航小区","accessId":"10000001","shopId":null,"advType":null,"cityId":null,"cellAddress":"重庆","longitude":"116.304233","latitude":"40.000991","areaLand":"21.00","areaConstruction":"516.00","committeeId":"1147424838893100","logo":null,"term":null,"ip":null,"port":null,"appPrivateKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB","appPublicKey":"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOJtlnppeIy1gULmr0RdDNjIyPAdjIG4+QWi1EBbx9Q39y8JVfhTtkSQPQuJZg4vxJ6NfxgXKwxS335hvMVpxd58Xy1Kxt+UUSHBaEF0v6Ext3yaajuV2JGUzMIzOOTv1cJ/Ua+I8mN3ReshSUtlvz554deMc6I3k3anvhkO/ZolAgMBAAECgYEAiwnGFEb9qlGuHRmwWCdXQysQEDnk1Kdz6p0Q/rAdJdhz5aMy8jjdPH7hrVrimyWD8+RpPa7EVV3yNRXpJ8QKpqr0lg4uuC2HjxPNqvf30Fs9oBBMm/CdqCS36JsR7TsQWbzxMcquowZKzqv5iKIefMFUkLD4/owQeRNzH+fWUMUCQQD8Byr7i7u/xKXmVdgcJB/P0p1Cnf/u/5esNs6pgXt8PWCntfpER4/T1QRDH5rEitYY3o6dz8PF5P7dc9awNUSTAkEA5f8ibE9pS3ATlaBq4ArFER4faJzozM7u42aiLSYkYUiXzcFCxwCa5ud9jPV3Oh/jBUrzp6Fb90hV4nmD+AHRZwJABgO1xuMCzATJYMHTsng6Oh9wmVJj9TQsTnPQYsMwSzq7v8TcAB0lFY0T2PY8H0yg518IUEPRDDv2yRommXXr+QJBAN6zVr+NfSVAlpYRSKs7gmn6wurm1DxMOuAR5wLUpfFU+ziN430RxuvCRr2QiSvM6GOdmaQ9B/G/JvouM2yXRg0CQDcOE5FOhYqOEKUjPuvG2LdnNzzWyy2YZPd26uGuj0DUHj9suSeOWgtmlG4vm7KhIpyynXIMqDxW8/eGCPVHy2s=","appId":"2015052600090779","appName":"asdf","userName":null,"userPhone":null,"account":null,"roleId":null,"userType":null}
         * userDTO : {"createDate":"2017-02-15 12:03:15","modifyDate":"2017-03-16 11:41:02","createUser":null,"status":1,"sorted":null,"remark":"测试","userId":"02175E93F53E4127AEA2EB3F78306005","account":"qiubin","passWord":"e10adc3949ba59abbe56e057f20f883e","userPhone":"18523570974","userName":"邱冰海","userEmail":null,"userType":1,"lastLogin":"2017-07-31 16:09:27","loginCount":228,"shopId":"","cellId":null,"roleId":null,"roleName":null}
         * token : 4937864BE43C42628F5D25E6D1282EED
         */

        private CellInfoDTOBean cellInfoDTO;
        private UserDTOBean userDTO;
        private String token;

        public CellInfoDTOBean getCellInfoDTO() {
            return cellInfoDTO;
        }

        public void setCellInfoDTO(CellInfoDTOBean cellInfoDTO) {
            this.cellInfoDTO = cellInfoDTO;
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
             * remark : 123
             * cellId : 1147424848043600
             * cellName : 长航小区
             * accessId : 10000001
             * shopId : null
             * advType : null
             * cityId : null
             * cellAddress : 重庆
             * longitude : 116.304233
             * latitude : 40.000991
             * areaLand : 21.00
             * areaConstruction : 516.00
             * committeeId : 1147424838893100
             * logo : null
             * term : null
             * ip : null
             * port : null
             * appPrivateKey : MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB
             * appPublicKey : MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOJtlnppeIy1gULmr0RdDNjIyPAdjIG4+QWi1EBbx9Q39y8JVfhTtkSQPQuJZg4vxJ6NfxgXKwxS335hvMVpxd58Xy1Kxt+UUSHBaEF0v6Ext3yaajuV2JGUzMIzOOTv1cJ/Ua+I8mN3ReshSUtlvz554deMc6I3k3anvhkO/ZolAgMBAAECgYEAiwnGFEb9qlGuHRmwWCdXQysQEDnk1Kdz6p0Q/rAdJdhz5aMy8jjdPH7hrVrimyWD8+RpPa7EVV3yNRXpJ8QKpqr0lg4uuC2HjxPNqvf30Fs9oBBMm/CdqCS36JsR7TsQWbzxMcquowZKzqv5iKIefMFUkLD4/owQeRNzH+fWUMUCQQD8Byr7i7u/xKXmVdgcJB/P0p1Cnf/u/5esNs6pgXt8PWCntfpER4/T1QRDH5rEitYY3o6dz8PF5P7dc9awNUSTAkEA5f8ibE9pS3ATlaBq4ArFER4faJzozM7u42aiLSYkYUiXzcFCxwCa5ud9jPV3Oh/jBUrzp6Fb90hV4nmD+AHRZwJABgO1xuMCzATJYMHTsng6Oh9wmVJj9TQsTnPQYsMwSzq7v8TcAB0lFY0T2PY8H0yg518IUEPRDDv2yRommXXr+QJBAN6zVr+NfSVAlpYRSKs7gmn6wurm1DxMOuAR5wLUpfFU+ziN430RxuvCRr2QiSvM6GOdmaQ9B/G/JvouM2yXRg0CQDcOE5FOhYqOEKUjPuvG2LdnNzzWyy2YZPd26uGuj0DUHj9suSeOWgtmlG4vm7KhIpyynXIMqDxW8/eGCPVHy2s=
             * appId : 2015052600090779
             * appName : asdf
             * userName : null
             * userPhone : null
             * account : null
             * roleId : null
             * userType : null
             */

            private Object createDate;
            private Object modifyDate;
            private Object createUser;
            private Object status;
            private Object sorted;
            private String remark;
            private String cellId;
            private String cellName;
            private String accessId;
            private Object shopId;
            private Object advType;
            private Object cityId;
            private String cellAddress;
            private String longitude;
            private String latitude;
            private String areaLand;
            private String areaConstruction;
            private String committeeId;
            private Object logo;
            private Object term;
            private Object ip;
            private Object port;
            private String appPrivateKey;
            private String appPublicKey;
            private String appId;
            private String appName;
            private Object userName;
            private Object userPhone;
            private Object account;
            private Object roleId;
            private Object userType;

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

            public String getAccessId() {
                return accessId;
            }

            public void setAccessId(String accessId) {
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

            public String getCommitteeId() {
                return committeeId;
            }

            public void setCommitteeId(String committeeId) {
                this.committeeId = committeeId;
            }

            public Object getLogo() {
                return logo;
            }

            public void setLogo(Object logo) {
                this.logo = logo;
            }

            public Object getTerm() {
                return term;
            }

            public void setTerm(Object term) {
                this.term = term;
            }

            public Object getIp() {
                return ip;
            }

            public void setIp(Object ip) {
                this.ip = ip;
            }

            public Object getPort() {
                return port;
            }

            public void setPort(Object port) {
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
        }

        public static class UserDTOBean {
            /**
             * createDate : 2017-02-15 12:03:15
             * modifyDate : 2017-03-16 11:41:02
             * createUser : null
             * status : 1
             * sorted : null
             * remark : 测试
             * userId : 02175E93F53E4127AEA2EB3F78306005
             * account : qiubin
             * passWord : e10adc3949ba59abbe56e057f20f883e
             * userPhone : 18523570974
             * userName : 邱冰海
             * userEmail : null
             * userType : 1
             * lastLogin : 2017-07-31 16:09:27
             * loginCount : 228
             * shopId :
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
