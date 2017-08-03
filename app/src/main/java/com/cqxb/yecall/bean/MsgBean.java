package com.cqxb.yecall.bean;

public class MsgBean {
	private String userid;
	private String msg;
	private String date;
	private String from;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public MsgBean(String userid, String msg, String date, String from) {
		super();
		this.userid = userid;
		this.msg = msg;
		this.date = date;
		this.from = from;
	}
}
