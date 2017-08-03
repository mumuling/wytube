package com.android.action.param;

public class CallBkRequest extends CommRequest {
	private String caller;// 主叫号
	private String callee;// 被叫号
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getCallee() {
		return callee;
	}
	public void setCallee(String callee) {
		this.callee = callee;
	}
	

}
