package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/22.
 * 类 描 述: 朋友圈动态对象
 */

public class DynamicBean {


    /**
     * success : true
     * message : OK
     * code : 200
     * data : {"page":{"limit":10,"page":1,"totalCount":8,"lastPage":true,"firstPage":true,"endRow":8,"totalPages":1,"slider":[1],"prePage":1,"nextPage":1,"hasPrePage":false,"hasNextPage":false,"startRow":1,"offset":0},"tracks":[{"createDate":"2017-07-27 09:32:03","modifyDate":null,"createUser":"8662AAE1A6E040C2A1908BA0C55F9ED1","status":1,"sorted":null,"remark":null,"trackId":"EB889813CB8E4F009BE701FF41CDC7D9","cellId":"cell_test","content":"feels so good","ownerName":"17623656386","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0721/3F29F5513DD045F1974305040525956F_defaulthead.jpg","good":1,"reply":1,"type":3,"trackReplys":[{"replyId":"7CE2D233D52745479A275B6C0874C0C7","content":"不能发表情啊","createDate":"2017-07-27 16:55:51","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-27 09:32:05.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"92789C06A61248F1B982B822B6A9FE51","trackId":"EB889813CB8E4F009BE701FF41CDC7D9","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0727/FD8E5769EED946C8947A299CF77EE6DB_img2 (2).png"}],"ownerPhone":"17623656386","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:25:45","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"8966345E70BA475B8E37390F4974E97C","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":6,"type":1,"trackReplys":[{"replyId":"B5D10B2A84534F20A3F4D718AAA25365","content":"njmd ","createDate":"2017-07-27 15:50:49","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"98AF8007CD2641F0954AC1AC244EDBB8","content":"fcqefq","createDate":"2017-07-27 15:38:58","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"9772C3E4CA014161BC63A70B0B1E952B","content":"geg ","createDate":"2017-07-27 15:15:17","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null},{"replyId":"B491610AC5904A45870A8E84EC513495","content":"尴尬了","createDate":"2017-07-27 15:04:05","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"B8CE1FFE1FAB40C0BF509CC087B293CC","content":"哈哈","createDate":"2017-07-26 14:05:10","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null},{"replyId":"8B0062007A5B46BAB5718790D29B7296","content":"搞事搞事","createDate":"2017-07-25 17:45:11","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:25:42","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"A31D4344054B4169905B2F75C28BF31C","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":1,"type":1,"trackReplys":[{"replyId":"0BC516FC6201457B8B6074BF49CD4CB9","content":"沉默 话没说出口","createDate":"2017-07-25 17:32:52","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:11:25","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"E1CFF874FA994D8E851CD34FC0E1BFDB","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":0,"type":1,"trackReplys":[],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:35:01","modifyDate":null,"createUser":"DA097AA3347349088BB78F020C187A41","status":1,"sorted":null,"remark":null,"trackId":"F7B9131614B64356A7A327C3D823867A","cellId":"cell_test","content":"闭嘴！！","ownerName":"18696633472","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","good":3,"reply":1,"type":1,"trackReplys":[{"replyId":"C93726BF9329427EA456FCE4DC814796","content":"哎哟","createDate":"2017-07-20 15:54:19","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-20 15:35:01.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"086BA12C7414475CAA3BFB55E057C6A1","trackId":"F7B9131614B64356A7A327C3D823867A","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/42A8ED36F39B4AD3B073D0C5AB8DFE82_temp_img_0.jpg"}],"ownerPhone":"18696633472","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:33:18","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"6E35C341579744DF84CD41A5142C1FD0","cellId":"cell_test","content":"6666这风景！！","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":1,"reply":0,"type":3,"trackReplys":[],"trackPics":[{"createDate":"2017-07-20 15:33:18.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"A6139ECA3FC0400EB7E563C302254504","trackId":"6E35C341579744DF84CD41A5142C1FD0","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/6B99505A274C4997B2A819C5E25AD357_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:26:35","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"504EAEBF987749218C08FD3762D4AA53","cellId":"cell_test","content":"今天天气不错哟！！","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":2,"reply":2,"type":3,"trackReplys":[{"replyId":"24A868417777494F9EC21B08A4F06BC7","content":"举高高","createDate":"2017-07-26 17:22:00","fromName":"18696633472","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","fromPhone":"18696633472","toName":null,"toPic":null,"toPhone":null},{"replyId":"A52AD5D8BF104305B0EA37F60B962807","content":"必须的","createDate":"2017-07-25 14:13:40","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-20 15:26:36.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"F747782903F843DE93E55D68D6164C4C","trackId":"504EAEBF987749218C08FD3762D4AA53","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/B0BFD5CEF5E74960AF15471CC4378C8A_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":true},{"createDate":"2017-07-06 12:29:42","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"C251E3DEE67D4331AE232FAFBE74FBC1","cellId":"cell_test","content":"加油","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":2,"reply":1,"type":2,"trackReplys":[{"replyId":"017C4F314A1D4F5A98184AA3E3B696BE","content":"一起↖(^ω^)↗","createDate":"2017-07-26 08:53:29","fromName":"18696633472","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","fromPhone":"18696633472","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-06 12:29:42.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"D8BE1D08D1414DD4AD7E0861BB00EB3D","trackId":"C251E3DEE67D4331AE232FAFBE74FBC1","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0706/6E247F5BD11747E88E56F70BAD7F87AC_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":true}]}
     * date : 2017-07-27 17:39:45
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
         * page : {"limit":10,"page":1,"totalCount":8,"lastPage":true,"firstPage":true,"endRow":8,"totalPages":1,"slider":[1],"prePage":1,"nextPage":1,"hasPrePage":false,"hasNextPage":false,"startRow":1,"offset":0}
         * tracks : [{"createDate":"2017-07-27 09:32:03","modifyDate":null,"createUser":"8662AAE1A6E040C2A1908BA0C55F9ED1","status":1,"sorted":null,"remark":null,"trackId":"EB889813CB8E4F009BE701FF41CDC7D9","cellId":"cell_test","content":"feels so good","ownerName":"17623656386","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0721/3F29F5513DD045F1974305040525956F_defaulthead.jpg","good":1,"reply":1,"type":3,"trackReplys":[{"replyId":"7CE2D233D52745479A275B6C0874C0C7","content":"不能发表情啊","createDate":"2017-07-27 16:55:51","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-27 09:32:05.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"92789C06A61248F1B982B822B6A9FE51","trackId":"EB889813CB8E4F009BE701FF41CDC7D9","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0727/FD8E5769EED946C8947A299CF77EE6DB_img2 (2).png"}],"ownerPhone":"17623656386","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:25:45","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"8966345E70BA475B8E37390F4974E97C","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":6,"type":1,"trackReplys":[{"replyId":"B5D10B2A84534F20A3F4D718AAA25365","content":"njmd ","createDate":"2017-07-27 15:50:49","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"98AF8007CD2641F0954AC1AC244EDBB8","content":"fcqefq","createDate":"2017-07-27 15:38:58","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"9772C3E4CA014161BC63A70B0B1E952B","content":"geg ","createDate":"2017-07-27 15:15:17","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null},{"replyId":"B491610AC5904A45870A8E84EC513495","content":"尴尬了","createDate":"2017-07-27 15:04:05","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":"余长发","toPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","toPhone":"18680808185"},{"replyId":"B8CE1FFE1FAB40C0BF509CC087B293CC","content":"哈哈","createDate":"2017-07-26 14:05:10","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null},{"replyId":"8B0062007A5B46BAB5718790D29B7296","content":"搞事搞事","createDate":"2017-07-25 17:45:11","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:25:42","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"A31D4344054B4169905B2F75C28BF31C","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":1,"type":1,"trackReplys":[{"replyId":"0BC516FC6201457B8B6074BF49CD4CB9","content":"沉默 话没说出口","createDate":"2017-07-25 17:32:52","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-25 17:11:25","modifyDate":null,"createUser":"6F0FA4F2958C4BE6AF77FCE5A345F95E","status":1,"sorted":null,"remark":null,"trackId":"E1CFF874FA994D8E851CD34FC0E1BFDB","cellId":"cell_test","content":"这一刻的想法~","ownerName":"13512345678","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0713/7114DFDC31934D6283BFCA9F932142A7_20170713144412.png","good":2,"reply":0,"type":1,"trackReplys":[],"trackPics":[],"ownerPhone":"13512345678","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:35:01","modifyDate":null,"createUser":"DA097AA3347349088BB78F020C187A41","status":1,"sorted":null,"remark":null,"trackId":"F7B9131614B64356A7A327C3D823867A","cellId":"cell_test","content":"闭嘴！！","ownerName":"18696633472","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","good":3,"reply":1,"type":1,"trackReplys":[{"replyId":"C93726BF9329427EA456FCE4DC814796","content":"哎哟","createDate":"2017-07-20 15:54:19","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-20 15:35:01.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"086BA12C7414475CAA3BFB55E057C6A1","trackId":"F7B9131614B64356A7A327C3D823867A","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/42A8ED36F39B4AD3B073D0C5AB8DFE82_temp_img_0.jpg"}],"ownerPhone":"18696633472","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:33:18","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"6E35C341579744DF84CD41A5142C1FD0","cellId":"cell_test","content":"6666这风景！！","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":1,"reply":0,"type":3,"trackReplys":[],"trackPics":[{"createDate":"2017-07-20 15:33:18.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"A6139ECA3FC0400EB7E563C302254504","trackId":"6E35C341579744DF84CD41A5142C1FD0","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/6B99505A274C4997B2A819C5E25AD357_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":false},{"createDate":"2017-07-20 15:26:35","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"504EAEBF987749218C08FD3762D4AA53","cellId":"cell_test","content":"今天天气不错哟！！","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":2,"reply":2,"type":3,"trackReplys":[{"replyId":"24A868417777494F9EC21B08A4F06BC7","content":"举高高","createDate":"2017-07-26 17:22:00","fromName":"18696633472","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","fromPhone":"18696633472","toName":null,"toPic":null,"toPhone":null},{"replyId":"A52AD5D8BF104305B0EA37F60B962807","content":"必须的","createDate":"2017-07-25 14:13:40","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-20 15:26:36.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"F747782903F843DE93E55D68D6164C4C","trackId":"504EAEBF987749218C08FD3762D4AA53","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/B0BFD5CEF5E74960AF15471CC4378C8A_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":true},{"createDate":"2017-07-06 12:29:42","modifyDate":null,"createUser":"5C7CEB75724D43EB93EBFD59DD8BA811","status":1,"sorted":null,"remark":null,"trackId":"C251E3DEE67D4331AE232FAFBE74FBC1","cellId":"cell_test","content":"加油","ownerName":"移动端","ownerPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/2FF6346E3F6D45F7945D282B9B8420C8_photo.png","good":2,"reply":1,"type":2,"trackReplys":[{"replyId":"017C4F314A1D4F5A98184AA3E3B696BE","content":"一起↖(^ω^)↗","createDate":"2017-07-26 08:53:29","fromName":"18696633472","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0720/5678A1CC3E664BAF9C9E59D4EADE0F4D_photo.png","fromPhone":"18696633472","toName":null,"toPic":null,"toPhone":null}],"trackPics":[{"createDate":"2017-07-06 12:29:42.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"D8BE1D08D1414DD4AD7E0861BB00EB3D","trackId":"C251E3DEE67D4331AE232FAFBE74FBC1","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0706/6E247F5BD11747E88E56F70BAD7F87AC_temp_img_0.jpg"}],"ownerPhone":"18696633478","ownerId":null,"alreadyGood":true}]
         */

        private PageBean page;
        private List<TracksBean> tracks;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<TracksBean> getTracks() {
            return tracks;
        }

        public void setTracks(List<TracksBean> tracks) {
            this.tracks = tracks;
        }

        public static class PageBean {
            /**
             * limit : 10
             * page : 1
             * totalCount : 8
             * lastPage : true
             * firstPage : true
             * endRow : 8
             * totalPages : 1
             * slider : [1]
             * prePage : 1
             * nextPage : 1
             * hasPrePage : false
             * hasNextPage : false
             * startRow : 1
             * offset : 0
             */

            private int limit;
            private int page;
            private int totalCount;
            private boolean lastPage;
            private boolean firstPage;
            private int endRow;
            private int totalPages;
            private int prePage;
            private int nextPage;
            private boolean hasPrePage;
            private boolean hasNextPage;
            private int startRow;
            private int offset;
            private List<Integer> slider;

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public boolean isLastPage() {
                return lastPage;
            }

            public void setLastPage(boolean lastPage) {
                this.lastPage = lastPage;
            }

            public boolean isFirstPage() {
                return firstPage;
            }

            public void setFirstPage(boolean firstPage) {
                this.firstPage = firstPage;
            }

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public boolean isHasPrePage() {
                return hasPrePage;
            }

            public void setHasPrePage(boolean hasPrePage) {
                this.hasPrePage = hasPrePage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public List<Integer> getSlider() {
                return slider;
            }

            public void setSlider(List<Integer> slider) {
                this.slider = slider;
            }
        }

        public static class TracksBean {
            /**
             * createDate : 2017-07-27 09:32:03
             * modifyDate : null
             * createUser : 8662AAE1A6E040C2A1908BA0C55F9ED1
             * status : 1
             * sorted : null
             * remark : null
             * trackId : EB889813CB8E4F009BE701FF41CDC7D9
             * cellId : cell_test
             * content : feels so good
             * ownerName : 17623656386
             * ownerPic : http://123667.oss-cn-qingdao.aliyuncs.com/0721/3F29F5513DD045F1974305040525956F_defaulthead.jpg
             * good : 1
             * reply : 1
             * type : 3
             * trackReplys : [{"replyId":"7CE2D233D52745479A275B6C0874C0C7","content":"不能发表情啊","createDate":"2017-07-27 16:55:51","fromName":"余长发","fromPic":"http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png","fromPhone":"18680808185","toName":null,"toPic":null,"toPhone":null}]
             * trackPics : [{"createDate":"2017-07-27 09:32:05.0","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"picId":"92789C06A61248F1B982B822B6A9FE51","trackId":"EB889813CB8E4F009BE701FF41CDC7D9","url":"http://123667.oss-cn-qingdao.aliyuncs.com/0727/FD8E5769EED946C8947A299CF77EE6DB_img2 (2).png"}]
             * ownerPhone : 17623656386
             * ownerId : null
             * alreadyGood : false
             */

            private String createDate;
            private Object modifyDate;
            private String createUser;
            private int status;
            private Object sorted;
            private Object remark;
            private String trackId;
            private String cellId;
            private String content;
            private String ownerName;
            private String ownerPic;
            private int good;
            private int reply;
            private int type;
            private String ownerPhone;
            private Object ownerId;
            private boolean alreadyGood;
            private List<TrackReplysBean> trackReplys;
            private List<TrackPicsBean> trackPics;
            public boolean isCheck;


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

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getTrackId() {
                return trackId;
            }

            public void setTrackId(String trackId) {
                this.trackId = trackId;
            }

            public String getCellId() {
                return cellId;
            }

            public void setCellId(String cellId) {
                this.cellId = cellId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getOwnerPic() {
                return ownerPic;
            }

            public void setOwnerPic(String ownerPic) {
                this.ownerPic = ownerPic;
            }

            public int getGood() {
                return good;
            }

            public void setGood(int good) {
                this.good = good;
            }

            public int getReply() {
                return reply;
            }

            public void setReply(int reply) {
                this.reply = reply;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getOwnerPhone() {
                return ownerPhone;
            }

            public void setOwnerPhone(String ownerPhone) {
                this.ownerPhone = ownerPhone;
            }

            public Object getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(Object ownerId) {
                this.ownerId = ownerId;
            }

            public boolean isisCheck() {
                return isCheck;
            }

            public void setisCheck(boolean isCheck) {
                this.isCheck = isCheck;
            }

            public boolean isAlreadyGood() {
                return alreadyGood;
            }

            public void setAlreadyGood(boolean alreadyGood) {
                this.alreadyGood = alreadyGood;
            }

            public List<TrackReplysBean> getTrackReplys() {
                return trackReplys;
            }

            public void setTrackReplys(List<TrackReplysBean> trackReplys) {
                this.trackReplys = trackReplys;
            }

            public List<TrackPicsBean> getTrackPics() {
                return trackPics;
            }

            public void setTrackPics(List<TrackPicsBean> trackPics) {
                this.trackPics = trackPics;
            }

            public static class TrackReplysBean {
                /**
                 * replyId : 7CE2D233D52745479A275B6C0874C0C7
                 * content : 不能发表情啊
                 * createDate : 2017-07-27 16:55:51
                 * fromName : 余长发
                 * fromPic : http://123667.oss-cn-qingdao.aliyuncs.com/0725/14A41EAA380D4C3E8F333D33ABDCD5D6_20170725121042.png
                 * fromPhone : 18680808185
                 * toName : null
                 * toPic : null
                 * toPhone : null
                 */

                private String replyId;
                private String content;
                private String createDate;
                private String fromName;
                private String fromPic;
                private String fromPhone;
                private Object toName;
                private Object toPic;
                private Object toPhone;

                public String getReplyId() {
                    return replyId;
                }

                public void setReplyId(String replyId) {
                    this.replyId = replyId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getFromName() {
                    return fromName;
                }

                public void setFromName(String fromName) {
                    this.fromName = fromName;
                }

                public String getFromPic() {
                    return fromPic;
                }

                public void setFromPic(String fromPic) {
                    this.fromPic = fromPic;
                }

                public String getFromPhone() {
                    return fromPhone;
                }

                public void setFromPhone(String fromPhone) {
                    this.fromPhone = fromPhone;
                }

                public Object getToName() {
                    return toName;
                }

                public void setToName(Object toName) {
                    this.toName = toName;
                }

                public Object getToPic() {
                    return toPic;
                }

                public void setToPic(Object toPic) {
                    this.toPic = toPic;
                }

                public Object getToPhone() {
                    return toPhone;
                }

                public void setToPhone(Object toPhone) {
                    this.toPhone = toPhone;
                }
            }

            public static class TrackPicsBean {
                /**
                 * createDate : 2017-07-27 09:32:05.0
                 * modifyDate : null
                 * createUser : null
                 * status : null
                 * sorted : null
                 * remark : null
                 * picId : 92789C06A61248F1B982B822B6A9FE51
                 * trackId : EB889813CB8E4F009BE701FF41CDC7D9
                 * url : http://123667.oss-cn-qingdao.aliyuncs.com/0727/FD8E5769EED946C8947A299CF77EE6DB_img2 (2).png
                 */

                private String createDate;
                private Object modifyDate;
                private Object createUser;
                private Object status;
                private Object sorted;
                private Object remark;
                private String picId;
                private String trackId;
                private String url;

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

                public String getPicId() {
                    return picId;
                }

                public void setPicId(String picId) {
                    this.picId = picId;
                }

                public String getTrackId() {
                    return trackId;
                }

                public void setTrackId(String trackId) {
                    this.trackId = trackId;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
