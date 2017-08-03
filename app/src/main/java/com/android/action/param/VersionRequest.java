package com.android.action.param;

public class VersionRequest extends CommRequest{
	private String currversion;
	private String apptype;
	public String getCurrversion() {
		return currversion;
	}
	public void setCurrversion(String currversion) {
		this.currversion = currversion;
	}
	public String getApptype() {
		return apptype;
	}
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	
	
}
