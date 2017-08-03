package com.android.action.param;

public class AuthCodeRequest extends CommRequest {
	private String account;
	private String version;
	private String type;
	private String expiredtime;
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExpiredtime() {
		return expiredtime;
	}
	public void setExpiredtime(String expiredtime) {
		this.expiredtime = expiredtime;
	}


}
