package com.wytube.beans;

/**
 * 创 建 人: Var_雨中行.
 * 创建时间: 2017/5/26.
 * 类 描 述:
 */

public class MonthBean {

    /**
     * statetype : 1
     * recordcount : 1
     * trans_date : 20151015200952
     * OrderFormNo : fsA12340201510152009521375
     * sfmoney : 5000
     * freemoney : 0
     * yxdate : 20160215000000
     * tipmsg : 获取订单成功
     * WeiXingInfo : null
     * ZhiFuBaoZInfo : null
     */

    private int statetype;
    private int recordcount;
    private String trans_date;
    private String OrderFormNo;
    private int sfmoney;
    private int freemoney;
    private String yxdate;
    private String tipmsg;
    private Object WeiXingInfo;
    private Object ZhiFuBaoZInfo;

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

    public int getSfmoney() {
        return sfmoney;
    }

    public void setSfmoney(int sfmoney) {
        this.sfmoney = sfmoney;
    }

    public int getFreemoney() {
        return freemoney;
    }

    public void setFreemoney(int freemoney) {
        this.freemoney = freemoney;
    }

    public String getYxdate() {
        return yxdate;
    }

    public void setYxdate(String yxdate) {
        this.yxdate = yxdate;
    }

    public String getTipmsg() {
        return tipmsg;
    }

    public void setTipmsg(String tipmsg) {
        this.tipmsg = tipmsg;
    }

    public Object getWeiXingInfo() {
        return WeiXingInfo;
    }

    public void setWeiXingInfo(Object WeiXingInfo) {
        this.WeiXingInfo = WeiXingInfo;
    }

    public Object getZhiFuBaoZInfo() {
        return ZhiFuBaoZInfo;
    }

    public void setZhiFuBaoZInfo(Object ZhiFuBaoZInfo) {
        this.ZhiFuBaoZInfo = ZhiFuBaoZInfo;
    }
}
