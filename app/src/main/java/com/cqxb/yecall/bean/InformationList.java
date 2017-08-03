package com.cqxb.yecall.bean;

import com.cqxb.yecall.YETApplication;

public class InformationList {
	public static String TABLE=YETApplication.appName+"informationList";
	public static String ILID="tlId";
	public static String GID="gid";
	public static String FRIENDID="friendId";
	public static String ROOMID="roomId";
	public static String NICKNAME="nickName";
	public static String CONTEXT="context";
	public static String MSGDATE="msgDate";
	public static String FRIENDIMG="friendImg";
	public static String FLAG="flag";
	public static String OBJECT="object";
	public static String COUNT="count";
	
	
	private String ilId;
	private String gid;//自己的id
	private String friendId;//好友id
	private String nickName;//好友昵称
	private String context;//内容
	private String msgDate;//日期
	private String friendImg;//好友图像
	private String flag;//（1-加好友 2-好友聊天 3-加群组 4-群组聊天 5-移出群主 6-同意请求但是无会话）
	private Object object;
	private String roomId;
	private String count;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getIlId() {
		return ilId;
	}
	public void setIlId(String ilId) {
		this.ilId = ilId;
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getFriendImg() {
		return friendImg;
	}
	public void setFriendImg(String friendImg) {
		this.friendImg = friendImg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	
	
}
