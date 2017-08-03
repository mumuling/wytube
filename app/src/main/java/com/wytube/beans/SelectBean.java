package com.wytube.beans;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class SelectBean {

    /**
     * statetype : 1
     * recordcount : 1
     * trans_date : 20151103102014
     * OrderFormNo :
     * tipmsg :
     * UserTransInfo : {"u_cardno":"4eac992345","u_cardtype":"临时卡A","u_cardstate":"未发行","u_no":"","u_name":"","u_dpt":"","carno":"京992345","cardfxdate":"","cardyxdate":"","cardmoney":0,"sfstate":"未付费","cometime":"2015-11-03 10:15:02","goouttime":"2015-11-03 10:20:14","stoptimes":"5分钟","sfmoney":20000,"parkno":null,"parkname":null,"shoufeirule":"按次收费,免费停车0分钟,每次收费200元"}
     */

    private int statetype;
    private int recordcount;
    private String trans_date;
    private String OrderFormNo;
    private String tipmsg;
    private UserTransInfoBean UserTransInfo;

    public int getStatetype() {
        return statetype;
    }

    public void setStatetype(int statetype) {
        this.statetype = statetype;
    }

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getOrderFormNo() {
        return OrderFormNo;
    }

    public void setOrderFormNo(String OrderFormNo) {
        this.OrderFormNo = OrderFormNo;
    }

    public String getTipmsg() {
        return tipmsg;
    }

    public void setTipmsg(String tipmsg) {
        this.tipmsg = tipmsg;
    }

    public UserTransInfoBean getUserTransInfo() {
        return UserTransInfo;
    }

    public void setUserTransInfo(UserTransInfoBean UserTransInfo) {
        this.UserTransInfo = UserTransInfo;
    }

    public static class UserTransInfoBean {
        /**
         * u_cardno : 4eac992345
         * u_cardtype : 临时卡A
         * u_cardstate : 未发行
         * u_no :
         * u_name :
         * u_dpt :
         * carno : 京992345
         * cardfxdate :
         * cardyxdate :
         * cardmoney : 0
         * sfstate : 未付费
         * cometime : 2015-11-03 10:15:02
         * goouttime : 2015-11-03 10:20:14
         * stoptimes : 5分钟
         * sfmoney : 20000
         * parkno : null
         * parkname : null
         * shoufeirule : 按次收费,免费停车0分钟,每次收费200元
         */

        private String u_cardno;
        private String u_cardtype;
        private String u_cardstate;
        private String u_no;
        private String u_name;
        private String u_dpt;
        private String carno;
        private String cardfxdate;
        private String cardyxdate;
        private int cardmoney;
        private String sfstate;
        private String cometime;
        private String goouttime;
        private String stoptimes;
        private int sfmoney;
        private Object parkno;
        private Object parkname;
        private String shoufeirule;

        public String getU_cardno() {
            return u_cardno;
        }

        public void setU_cardno(String u_cardno) {
            this.u_cardno = u_cardno;
        }

        public String getU_cardtype() {
            return u_cardtype;
        }

        public void setU_cardtype(String u_cardtype) {
            this.u_cardtype = u_cardtype;
        }

        public String getU_cardstate() {
            return u_cardstate;
        }

        public void setU_cardstate(String u_cardstate) {
            this.u_cardstate = u_cardstate;
        }

        public String getU_no() {
            return u_no;
        }

        public void setU_no(String u_no) {
            this.u_no = u_no;
        }

        public String getU_name() {
            return u_name;
        }

        public void setU_name(String u_name) {
            this.u_name = u_name;
        }

        public String getU_dpt() {
            return u_dpt;
        }

        public void setU_dpt(String u_dpt) {
            this.u_dpt = u_dpt;
        }

        public String getCarno() {
            return carno;
        }

        public void setCarno(String carno) {
            this.carno = carno;
        }

        public String getCardfxdate() {
            return cardfxdate;
        }

        public void setCardfxdate(String cardfxdate) {
            this.cardfxdate = cardfxdate;
        }

        public String getCardyxdate() {
            return cardyxdate;
        }

        public void setCardyxdate(String cardyxdate) {
            this.cardyxdate = cardyxdate;
        }

        public int getCardmoney() {
            return cardmoney;
        }

        public void setCardmoney(int cardmoney) {
            this.cardmoney = cardmoney;
        }

        public String getSfstate() {
            return sfstate;
        }

        public void setSfstate(String sfstate) {
            this.sfstate = sfstate;
        }

        public String getCometime() {
            return cometime;
        }

        public void setCometime(String cometime) {
            this.cometime = cometime;
        }

        public String getGoouttime() {
            return goouttime;
        }

        public void setGoouttime(String goouttime) {
            this.goouttime = goouttime;
        }

        public String getStoptimes() {
            return stoptimes;
        }

        public void setStoptimes(String stoptimes) {
            this.stoptimes = stoptimes;
        }

        public int getSfmoney() {
            return sfmoney;
        }

        public void setSfmoney(int sfmoney) {
            this.sfmoney = sfmoney;
        }

        public Object getParkno() {
            return parkno;
        }

        public void setParkno(Object parkno) {
            this.parkno = parkno;
        }

        public Object getParkname() {
            return parkname;
        }

        public void setParkname(Object parkname) {
            this.parkname = parkname;
        }

        public String getShoufeirule() {
            return shoufeirule;
        }

        public void setShoufeirule(String shoufeirule) {
            this.shoufeirule = shoufeirule;
        }
    }
}
