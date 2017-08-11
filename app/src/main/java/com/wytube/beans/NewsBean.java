package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/27.
 * 类 描 述:
 */

public class NewsBean {

    /**
     * success : true
     * message : OK
     * code : 200
     * data : {"page":{"limit":10000,"page":1,"totalCount":2,"firstPage":true,"slider":[1],"prePage":1,"nextPage":1,"hasPrePage":false,"hasNextPage":false,"startRow":1,"endRow":2,"totalPages":1,"lastPage":true,"offset":0},"infos":[{"createDate":"2017-05-16 16:41:27","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"infoId":"CF01F5D09F7F4C77A874772F94B5B31D","title":"亲情服务暖人心，惠民政策传万家","desc":"","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170516164117916.jpg","type":"A009E7F15F7344A69D3904D931471996","typeName":"社区资讯","content":"<p>为加强辖区养老保障工作，提高老人自身及其家庭的抗风险能力，海北社区近期在辖区积极宣传推广\u201c银龄安康保险\u201d工程。<\/p><p>为了将这项惠民政策真正落到实处,减轻居民的医疗负担，海北社区从多种途径进行宣传。一是到各网格的宣传栏、楼宇们张贴保险通知；二是针对行动不便的老年人，社区工作人员主动上门宣传，详细说明实施方案、特点、申领流程、理赔条件等；三是在社区服务大厅热心接待来访群众、耐心讲解参保条件、细心做好登记工作，确保登记信息准确无误，让来访居民满意而归。&nbsp;<\/p><p><\/p><p>通过开展\u201c银龄安康保险\u201d宣传活动，社区的老年人深切感受到了党和政府的关心与照顾，真正将\u201c银龄安康保险\u201d工程打造成了老年人的满意工程。<\/p><p><br><\/p>","cellId":null},{"createDate":"2017-05-16 16:38:44","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"infoId":"59747B7F7312430A8CF1564FC6C8CB1A","title":"开展健康知识讲座","desc":"开展心脑血管疾病防治知识健康讲座活动","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170516163832500.jpg","type":"A009E7F15F7344A69D3904D931471996","typeName":"社区资讯","content":"<p>为积极应对发病率逐年增高的心脑血管疾病，向社区居民普及预防心脑血管疾病的知识，5月16日上午，国华社区在卫生服务站开展心脑血管疾病防治知识健康讲座活动。<\/p><p>此次讲座邀请同仁堂医院心脑血管方面专家主讲。专家用通俗易懂的语言和简单的实例，采取演示与讲解相结合的方式，从心脑血管疾病的发病原因、表现症状及如何预防、如何安全用药等几个方面进行了深入浅出的讲解。接着就心脑血管疾病发作的第一时间如何采取急救措施以及生活饮食需注意的事项等方面做了详细介绍。他建议老年人在生活中平时要保持情绪稳定，避免精神过度紧张，要保持适度的体力劳动和体育锻炼，有节制地参加适宜的娱乐活动，提醒居民提高对心脑血管疾病的重视，做到早预防、早治疗。讲座结束后，专家进行了义诊活动，居民纷纷咨询和诊治，社区卫生服务站的医生免费为居民检测血栓、测量血压、血糖等。整个活动中居民与专家、医生踊跃互动，现场气氛活跃。<\/p><p><\/p><p>这次健康讲座活动的开展得到了居民的一致好评，居民纷纷表示这样不出社区就享受到医疗专家的高水平诊疗服务和健康教育，不仅增强了自身预防心脑血管疾病的意识，也为彼此面对面地沟通健康心得提供了平台，希望社区以后多开展类似的活动<\/p><p><br><\/p>","cellId":null}]}
     * date : 2017-05-27 15:06:08
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
         * page : {"limit":10000,"page":1,"totalCount":2,"firstPage":true,"slider":[1],"prePage":1,"nextPage":1,"hasPrePage":false,"hasNextPage":false,"startRow":1,"endRow":2,"totalPages":1,"lastPage":true,"offset":0}
         * infos : [{"createDate":"2017-05-16 16:41:27","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"infoId":"CF01F5D09F7F4C77A874772F94B5B31D","title":"亲情服务暖人心，惠民政策传万家","desc":"","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170516164117916.jpg","type":"A009E7F15F7344A69D3904D931471996","typeName":"社区资讯","content":"<p>为加强辖区养老保障工作，提高老人自身及其家庭的抗风险能力，海北社区近期在辖区积极宣传推广\u201c银龄安康保险\u201d工程。<\/p><p>为了将这项惠民政策真正落到实处,减轻居民的医疗负担，海北社区从多种途径进行宣传。一是到各网格的宣传栏、楼宇们张贴保险通知；二是针对行动不便的老年人，社区工作人员主动上门宣传，详细说明实施方案、特点、申领流程、理赔条件等；三是在社区服务大厅热心接待来访群众、耐心讲解参保条件、细心做好登记工作，确保登记信息准确无误，让来访居民满意而归。&nbsp;<\/p><p><\/p><p>通过开展\u201c银龄安康保险\u201d宣传活动，社区的老年人深切感受到了党和政府的关心与照顾，真正将\u201c银龄安康保险\u201d工程打造成了老年人的满意工程。<\/p><p><br><\/p>","cellId":null},{"createDate":"2017-05-16 16:38:44","modifyDate":null,"createUser":null,"status":null,"sorted":null,"remark":null,"infoId":"59747B7F7312430A8CF1564FC6C8CB1A","title":"开展健康知识讲座","desc":"开展心脑血管疾病防治知识健康讲座活动","pic":"http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170516163832500.jpg","type":"A009E7F15F7344A69D3904D931471996","typeName":"社区资讯","content":"<p>为积极应对发病率逐年增高的心脑血管疾病，向社区居民普及预防心脑血管疾病的知识，5月16日上午，国华社区在卫生服务站开展心脑血管疾病防治知识健康讲座活动。<\/p><p>此次讲座邀请同仁堂医院心脑血管方面专家主讲。专家用通俗易懂的语言和简单的实例，采取演示与讲解相结合的方式，从心脑血管疾病的发病原因、表现症状及如何预防、如何安全用药等几个方面进行了深入浅出的讲解。接着就心脑血管疾病发作的第一时间如何采取急救措施以及生活饮食需注意的事项等方面做了详细介绍。他建议老年人在生活中平时要保持情绪稳定，避免精神过度紧张，要保持适度的体力劳动和体育锻炼，有节制地参加适宜的娱乐活动，提醒居民提高对心脑血管疾病的重视，做到早预防、早治疗。讲座结束后，专家进行了义诊活动，居民纷纷咨询和诊治，社区卫生服务站的医生免费为居民检测血栓、测量血压、血糖等。整个活动中居民与专家、医生踊跃互动，现场气氛活跃。<\/p><p><\/p><p>这次健康讲座活动的开展得到了居民的一致好评，居民纷纷表示这样不出社区就享受到医疗专家的高水平诊疗服务和健康教育，不仅增强了自身预防心脑血管疾病的意识，也为彼此面对面地沟通健康心得提供了平台，希望社区以后多开展类似的活动<\/p><p><br><\/p>","cellId":null}]
         */

        private PageBean page;
        private List<InfosBean> infos;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<InfosBean> getInfos() {
            return infos;
        }

        public void setInfos(List<InfosBean> infos) {
            this.infos = infos;
        }

        public static class PageBean {
            /**
             * limit : 10000
             * page : 1
             * totalCount : 2
             * firstPage : true
             * slider : [1]
             * prePage : 1
             * nextPage : 1
             * hasPrePage : false
             * hasNextPage : false
             * startRow : 1
             * endRow : 2
             * totalPages : 1
             * lastPage : true
             * offset : 0
             */

            private int limit;
            private int page;
            private int totalCount;
            private boolean firstPage;
            private int prePage;
            private int nextPage;
            private boolean hasPrePage;
            private boolean hasNextPage;
            private int startRow;
            private int endRow;
            private int totalPages;
            private boolean lastPage;
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

            public boolean isFirstPage() {
                return firstPage;
            }

            public void setFirstPage(boolean firstPage) {
                this.firstPage = firstPage;
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

            public boolean isLastPage() {
                return lastPage;
            }

            public void setLastPage(boolean lastPage) {
                this.lastPage = lastPage;
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

        public static class InfosBean {
            /**
             * createDate : 2017-05-16 16:41:27
             * modifyDate : null
             * createUser : null
             * status : null
             * sorted : null
             * remark : null
             * infoId : CF01F5D09F7F4C77A874772F94B5B31D
             * title : 亲情服务暖人心，惠民政策传万家
             * desc :
             * pic : http://123667.oss-cn-qingdao.aliyuncs.com/201705/20170516164117916.jpg
             * type : A009E7F15F7344A69D3904D931471996
             * typeName : 社区资讯
             * content : <p>为加强辖区养老保障工作，提高老人自身及其家庭的抗风险能力，海北社区近期在辖区积极宣传推广“银龄安康保险”工程。</p><p>为了将这项惠民政策真正落到实处,减轻居民的医疗负担，海北社区从多种途径进行宣传。一是到各网格的宣传栏、楼宇们张贴保险通知；二是针对行动不便的老年人，社区工作人员主动上门宣传，详细说明实施方案、特点、申领流程、理赔条件等；三是在社区服务大厅热心接待来访群众、耐心讲解参保条件、细心做好登记工作，确保登记信息准确无误，让来访居民满意而归。&nbsp;</p><p></p><p>通过开展“银龄安康保险”宣传活动，社区的老年人深切感受到了党和政府的关心与照顾，真正将“银龄安康保险”工程打造成了老年人的满意工程。</p><p><br></p>
             * cellId : null
             */

            private String createDate;
            private Object modifyDate;
            private Object createUser;
            private Object status;
            private Object sorted;
            private Object remark;
            private String infoId;
            private String title;
            private String desc;
            private String pic;
            private String type;
            private String typeName;
            private String content;
            private Object cellId;

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

            public String getInfoId() {
                return infoId;
            }

            public void setInfoId(String infoId) {
                this.infoId = infoId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getCellId() {
                return cellId;
            }

            public void setCellId(Object cellId) {
                this.cellId = cellId;
            }
        }
    }
}
