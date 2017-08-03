package com.wytube.beans;

/**
 * 创 建 人: vr 柠檬 .
 * 创建时间: 2017/8/3.
 * 类 描 述:
 */

public class BaseTCOK {

    /**
     * statetype : 1
     * recordcount : 1
     * trans_date : 20170802144709
     * tipmsg : 收费成功
     */

    private int statetype;
    private int recordcount;
    private String trans_date;
    private String tipmsg;

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
}
