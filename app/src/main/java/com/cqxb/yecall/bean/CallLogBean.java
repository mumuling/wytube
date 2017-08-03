package com.cqxb.yecall.bean;

import com.cqxb.yecall.YETApplication;

public class CallLogBean {
	public static String TABLE=YETApplication.appName+"callLogs";
	public static String ID="id";
	public static String NAME="name";
	public static String NUMBER="number";
	public static String TYPE="type";
	public static String CALLTIME="callTime";
	public static String STARTCALL="startCall";
	public static String BELONG="belong";
	public static String NEWS="news";
	public static String RECORDPATH="recordPath";
	public static String PHOTOPATH="photoPath";
	
	private String id;
	private String name;//联系人
	private String number;//电话号码
	private String type;//通话类型  
	private String callTime;// 通话时长
	private String startCall;//何时开始通话
	private String belong;//是谁的通话记录
	private String news;//以看为看
	private String recordPath;//录音地址
	private String photoPath;//照片地址
	
	public String getRecordPath() {
		return recordPath;
	}
	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartCall() {
		return startCall;
	}
	public void setStartCall(String startCall) {
		this.startCall = startCall;
	}
	public String getCallTime() {
		return callTime;
	}
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	
	
}
