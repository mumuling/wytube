package com.cqxb.yecall.bean;

import java.util.ArrayList;

public class TreeOrgBean {
	private String name;
	private int level;
	private String type;
	private ArrayList<TreeOrgBean> objects;
	private boolean isExpanded;
	private boolean isParent;
	private String phone;
	private String position;
	
	private String account;
	private String domain;
	private String ip;
	private String port;
	private String pswd;
	
	
	public TreeOrgBean(String name, int level, String type,
			ArrayList<TreeOrgBean> objects) {
		super();
		this.name = name;
		this.level = level;
		this.type = type;
		this.objects = objects;
	}
	public TreeOrgBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<TreeOrgBean> getObjects() {
		return objects;
	}
	public void setObjects(ArrayList<TreeOrgBean> objects) {
		this.objects = objects;
	}
	public boolean isExpanded() {
		return isExpanded;
	}
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
	public boolean isParent() {
		return isParent;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
}
