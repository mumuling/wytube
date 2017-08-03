package com.wytube.beans;

import java.util.List;

/**
 * Created by Var on 2017/5/2.
 */

public class PropMsgBean {


    /**
     * success : true
     * message : 获取成功
     * code : 200
     * data : [{"pushId":"7e01309093a54742946835c9af7429dd","starttime":"2017-03-31 09:47:35.0","title":"测试","content":"<p>阿斯蒂芬阿斯顿发射点发岁的法撒旦发射点发射得分<\/p>","pushTime":"2017-03-31 10:09:12.0"}]
     * date : 2017-04-25 14:14:18
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
         * pushId : 7e01309093a54742946835c9af7429dd
         * starttime : 2017-03-31 09:47:35.0
         * title : 测试
         * content : <p>阿斯蒂芬阿斯顿发射点发岁的法撒旦发射点发射得分</p>
         * pushTime : 2017-03-31 10:09:12.0
         */

        private String pushId;
        private String starttime;
        private String title;
        private String content;
        private String pushTime;

        public String getPushId() {
            return pushId;
        }

        public void setPushId(String pushId) {
            this.pushId = pushId;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPushTime() {
            return pushTime;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }
    }
}
