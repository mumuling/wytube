package com.cqxb.yecall.bean;

public class UserBean {
	private String userID;//用户id
	private String nickName;//昵称
	private String userAccount;//账号 gid 就是注册帐号@之前的数据
	private String userAvatarUrl; // 用户头像地址
	private String statusImg; // 用户在线状态图标
	private String status;//心情
	private String sortLetters;  //显示数据拼音的首字母
	private String visibility;//在线状态
	private String groups;//所在组
	private int isReqOrFriend;//0-请求 1-好友
	
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}
	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public int getIsReqOrFriend() {
		return isReqOrFriend;
	}
	public void setIsReqOrFriend(int isReqOrFriend) {
		this.isReqOrFriend = isReqOrFriend;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getStatusImg() {
		return statusImg;
	}
	public void setStatusImg(String statusImg) {
		this.statusImg = statusImg;
	}
	
	
}
