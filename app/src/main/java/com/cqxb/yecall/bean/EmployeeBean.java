package com.cqxb.yecall.bean;

import java.util.List;

public class EmployeeBean {
	private String name;
	private int level;
	private String type;
	private List<TreeNodeBean> objects;
	private String position;
	private String phone;
	
	private String deptName;
	private String display_name;
	private String sip_account;
	private String sex;
	private String emp_name;
	private String compName;
	private String mobile;
	private String available_balance;//可用余额
	
	
	
	
	public EmployeeBean() {
		super();
	}
	public EmployeeBean(String name, int level, String type,
			List<TreeNodeBean> objects) {
		super();
		this.name = name;
		this.level = level;
		this.type = type;
		this.objects = objects;
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
	public List<TreeNodeBean> getObjects() {
		return objects;
	}
	public void setObjects(List<TreeNodeBean> objects) {
		this.objects = objects;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getSip_account() {
		return sip_account;
	}
	public void setSip_account(String sip_account) {
		this.sip_account = sip_account;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAvailable_balance() {
		return available_balance;
	}
	public void setAvailable_balance(String available_balance) {
		this.available_balance = available_balance;
	}
	
	
	
	
	
}
