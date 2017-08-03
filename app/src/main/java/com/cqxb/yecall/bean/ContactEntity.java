package com.cqxb.yecall.bean;

import com.cqxb.yecall.YETApplication;

public class ContactEntity {
	public static String TABLE=YETApplication.appName+"contactList";
	public static String CTID="ctId";
	public static String FRIENDID="friendId";
	public static String NICKNAME="nickName";
	public static String FRIENDIMG="friendImg";
	public static String VISIBILITY="visibility";
	public static String STATUS="status";
	public static String DESCR="descr";
	public static String VISIBILITYIMG="visibilityImg";//在线状态图标
	public static String GROUP="groups";
	
	private String ctId;//主键
	private String friendId;//好友jid
	private String nickName;//好友昵称
	private String friendImg;//好友图像
	private String visibility;//是否在线
	private String visibilityImg;//在线图标
	private String status;//心情 or 说说
	private String descr;//备注
	private String groups;//所在分组
	private Object backup;//备用字段
	
	
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getFriendImg() {
		return friendImg;
	}
	public void setFriendImg(String friendImg) {
		this.friendImg = friendImg;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVisibilityImg() {
		return visibilityImg;
	}
	public void setVisibilityImg(String visibilityImg) {
		this.visibilityImg = visibilityImg;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public Object getBackup() {
		return backup;
	}
	public void setBackup(Object backup) {
		this.backup = backup;
	}
	
	
	
	
}
