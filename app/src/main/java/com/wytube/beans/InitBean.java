package com.wytube.beans;

import java.util.List;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class InitBean {

    /**
     * statetype : 1
     * recordcount : 1
     * trans_date : 20170426151225
     * tipmsg : 初始化成功
     * UserLoginInfo : {"u_cardno":"888888","u_no":"888888","u_name":"管理员","u_dpt":"管理部","temp_key":"ea1ca592322e43bf30933d64163d87ff"}
     * PopedomList : [{"funname":"发卡报表","popedom":1},{"funname":"金额报表","popedom":1},{"funname":"延期报表","popedom":1},{"funname":"场内记录","popedom":1},{"funname":"车辆收费","popedom":1},{"funname":"中央收费报表","popedom":1},{"funname":"日报表汇总","popedom":1},{"funname":"月报表汇总","popedom":1},{"funname":"年报表汇总","popedom":1}]
     */

    private int statetype;
    private int recordcount;
    private String trans_date;
    private String tipmsg;
    private UserLoginInfoBean UserLoginInfo;
    private List<PopedomListBean> PopedomList;

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

    public String getTipmsg() {
        return tipmsg;
    }

    public void setTipmsg(String tipmsg) {
        this.tipmsg = tipmsg;
    }

    public UserLoginInfoBean getUserLoginInfo() {
        return UserLoginInfo;
    }

    public void setUserLoginInfo(UserLoginInfoBean UserLoginInfo) {
        this.UserLoginInfo = UserLoginInfo;
    }

    public List<PopedomListBean> getPopedomList() {
        return PopedomList;
    }

    public void setPopedomList(List<PopedomListBean> PopedomList) {
        this.PopedomList = PopedomList;
    }

    public static class UserLoginInfoBean {
        /**
         * u_cardno : 888888
         * u_no : 888888
         * u_name : 管理员
         * u_dpt : 管理部
         * temp_key : ea1ca592322e43bf30933d64163d87ff
         */

        private String u_cardno;
        private String u_no;
        private String u_name;
        private String u_dpt;
        private String temp_key;

        public String getU_cardno() {
            return u_cardno;
        }

        public void setU_cardno(String u_cardno) {
            this.u_cardno = u_cardno;
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

        public String getTemp_key() {
            return temp_key;
        }

        public void setTemp_key(String temp_key) {
            this.temp_key = temp_key;
        }
    }

    public static class PopedomListBean {
        /**
         * funname : 发卡报表
         * popedom : 1
         */

        private String funname;
        private int popedom;

        public String getFunname() {
            return funname;
        }

        public void setFunname(String funname) {
            this.funname = funname;
        }

        public int getPopedom() {
            return popedom;
        }

        public void setPopedom(int popedom) {
            this.popedom = popedom;
        }
    }
}
