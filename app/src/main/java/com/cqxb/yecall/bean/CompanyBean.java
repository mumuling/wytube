package com.cqxb.yecall.bean;

public class CompanyBean {
	private String company_name;//企业名称
	private String address;//地址
	private String balance;//余额
	private String aduit;//审核 0-未认证 1- 认证
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAduit() {
		return aduit;
	}
	public void setAduit(String aduit) {
		this.aduit = aduit;
	}
	
	
}
