package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class ParkBean {

    /**
     * data : {"parks":[{"ip":"192.168.25.36","name":"停车场#2","parkId":"FAF1342FB9F248DFA2C839D7ABFCF3E8","port":"6325"},{"ip":"192.168.25.39","name":"停车场#1","parkId":"BEAC3D11C7B54B709A9FCD8D027912C3","port":"3626"},{"ip":"192.168.256.36","name":"停车场#3","parkId":"A6C54E4AB7A34879B0C10A6ACEBC9251","port":"4569"}],"page":{"endRow":3,"firstPage":true,"hasNextPage":false,"hasPrePage":false,"lastPage":true,"limit":10,"nextPage":1,"offset":0,"page":1,"prePage":1,"slider":[1],"startRow":1,"totalCount":3,"totalPages":1}}
     * date : 2017-05-17 17:05:59
     * message : OK
     * state : SUCCESS
     * success : true
     */

    private DataBean data;
    private String date;
    private String message;
    private String state;
    private boolean success;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * parks : [{"ip":"192.168.25.36","name":"停车场#2","parkId":"FAF1342FB9F248DFA2C839D7ABFCF3E8","port":"6325"},{"ip":"192.168.25.39","name":"停车场#1","parkId":"BEAC3D11C7B54B709A9FCD8D027912C3","port":"3626"},{"ip":"192.168.256.36","name":"停车场#3","parkId":"A6C54E4AB7A34879B0C10A6ACEBC9251","port":"4569"}]
         * page : {"endRow":3,"firstPage":true,"hasNextPage":false,"hasPrePage":false,"lastPage":true,"limit":10,"nextPage":1,"offset":0,"page":1,"prePage":1,"slider":[1],"startRow":1,"totalCount":3,"totalPages":1}
         */

        private PageBean page;
        private List<ParksBean> parks;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ParksBean> getParks() {
            return parks;
        }

        public void setParks(List<ParksBean> parks) {
            this.parks = parks;
        }

        public static class PageBean {
            /**
             * endRow : 3
             * firstPage : true
             * hasNextPage : false
             * hasPrePage : false
             * lastPage : true
             * limit : 10
             * nextPage : 1
             * offset : 0
             * page : 1
             * prePage : 1
             * slider : [1]
             * startRow : 1
             * totalCount : 3
             * totalPages : 1
             */

            private int endRow;
            private boolean firstPage;
            private boolean hasNextPage;
            private boolean hasPrePage;
            private boolean lastPage;
            private int limit;
            private int nextPage;
            private int offset;
            private int page;
            private int prePage;
            private int startRow;
            private int totalCount;
            private int totalPages;
            private List<Integer> slider;

            public int getEndRow() {
                return endRow;
            }

            public void setEndRow(int endRow) {
                this.endRow = endRow;
            }

            public boolean isFirstPage() {
                return firstPage;
            }

            public void setFirstPage(boolean firstPage) {
                this.firstPage = firstPage;
            }

            public boolean isHasNextPage() {
                return hasNextPage;
            }

            public void setHasNextPage(boolean hasNextPage) {
                this.hasNextPage = hasNextPage;
            }

            public boolean isHasPrePage() {
                return hasPrePage;
            }

            public void setHasPrePage(boolean hasPrePage) {
                this.hasPrePage = hasPrePage;
            }

            public boolean isLastPage() {
                return lastPage;
            }

            public void setLastPage(boolean lastPage) {
                this.lastPage = lastPage;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getNextPage() {
                return nextPage;
            }

            public void setNextPage(int nextPage) {
                this.nextPage = nextPage;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public int getPrePage() {
                return prePage;
            }

            public void setPrePage(int prePage) {
                this.prePage = prePage;
            }

            public int getStartRow() {
                return startRow;
            }

            public void setStartRow(int startRow) {
                this.startRow = startRow;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
            }

            public List<Integer> getSlider() {
                return slider;
            }

            public void setSlider(List<Integer> slider) {
                this.slider = slider;
            }
        }

        public static class ParksBean {
            /**
             * ip : 192.168.25.36
             * name : 停车场#2
             * parkId : FAF1342FB9F248DFA2C839D7ABFCF3E8
             * port : 6325
             */

            private String ip;
            private String name;
            private String parkId;
            private String port;

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public String getPort() {
                return port;
            }

            public void setPort(String port) {
                this.port = port;
            }
        }
    }
}
