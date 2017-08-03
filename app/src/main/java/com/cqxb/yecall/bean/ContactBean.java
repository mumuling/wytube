package com.cqxb.yecall.bean;

import android.graphics.Bitmap;

public class ContactBean {
	private String number;//得到手机号码
	private String contactName;//得到联系人名称
	private Long contactID;//得到联系人ID  
	private Long photoID;//得到联系人头像ID
	private Bitmap contactPhoto ;//得到联系人头像Bitamp  
	private int contactType;//联系人类型  0-本机  1-企业
	private String sortLetters;  //显示数据拼音的首字母
	private String context;//其他的内容
	private String en;//首字符英文字母
	private String group;
	
	private String stamp;// 何时开始通话
	private String duration;// 通话时长
	private Integer type;// 通话类型 1-呼入 2-呼出 3-未接  4-联系人
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Long getContactID() {
		return contactID;
	}
	public void setContactID(Long contactID) {
		this.contactID = contactID;
	}
	public Long getPhotoID() {
		return photoID;
	}
	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}
	public Bitmap getContactPhoto() {
		return contactPhoto;
	}
	public void setContactPhoto(Bitmap contactPhoto) {
		this.contactPhoto = contactPhoto;
	}
	public int getContactType() {
		return contactType;
	}
	public void setContactType(int contactType) {
		this.contactType = contactType;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
}
