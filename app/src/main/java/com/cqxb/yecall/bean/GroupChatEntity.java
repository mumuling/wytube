package com.cqxb.yecall.bean;

import com.cqxb.yecall.YETApplication;

public class GroupChatEntity {
	public static String TABLE=YETApplication.appName+"groupChat";//群组聊天
	public static String GCID="gcId";
	public static String ROOMID="roomId";
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
	public static String GID="gid";
	
	private String gcId;
	private String roomId;//自己id
	private String friendId;//聊天对象id
	private String context;//内容
	private String who;//谁发的
	private String isRead;//是否读
	private String nickName;//备注
	private String msgDate;//时间
	private String gid;//登陆im的id
	public String getGcId() {
		return gcId;
	}
	public void setGcId(String gcId) {
		this.gcId = gcId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	
	
	
}
