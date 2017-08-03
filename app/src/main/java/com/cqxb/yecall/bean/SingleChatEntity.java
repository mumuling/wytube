package com.cqxb.yecall.bean;

import com.cqxb.yecall.YETApplication;

public class SingleChatEntity {
	public static String TABLE=YETApplication.appName+"singleChat";//单人聊天
	public static String SCID="scId";
	public static String USERID="userId";
	public static String FRIENDID="friendId";
	public static String CONTEXT="context";
	public static String WHO="who";
	public static String ISREAD="isRead";
	public static String NICKNAME="nickName";
	public static String MSGDATE="msgDate";
	public static String OUT="out";
	public static String IN="in";
	public static String IS_READ="YES";
	public static String IS_NOT_READ="NO";
	
	
	private String scId;
	private String userId;
	private String friendId;
	private String context;
	private String who; //in - 好友发送的消息   out 自己发送的消息
	private String isRead;
	private String nickName;
	private String msgDate;
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	
	
	
}
