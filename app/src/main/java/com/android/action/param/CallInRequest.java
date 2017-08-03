package com.android.action.param;

public class CallInRequest extends CommRequest {
	private String dnd = "";
	private String xfersetting = "";
	private String xferTo = "";
	private String customSetting ="";
	private String wlanTo ="";
	private String noAnswerTo = "";
	private String noResponseTo = "";
	private String busyTo = "";
	public String getDnd() {
		return dnd;
	}
	public void setDnd(String dnd) {
		this.dnd = dnd;
	}
	public String getXfersetting() {
		return xfersetting;
	}
	public void setXfersetting(String xfersetting) {
		this.xfersetting = xfersetting;
	}
	public String getXferTo() {
		return xferTo;
	}
	public void setXferTo(String xferTo) {
		this.xferTo = xferTo;
	}
	public String getCustomSetting() {
		return customSetting;
	}
	public void setCustomSetting(String customSetting) {
		this.customSetting = customSetting;
	}
	public String getWlanTo() {
		return wlanTo;
	}
	public void setWlanTo(String wlanTo) {
		this.wlanTo = wlanTo;
	}
	public String getNoAnswerTo() {
		return noAnswerTo;
	}
	public void setNoAnswerTo(String noAnswerTo) {
		this.noAnswerTo = noAnswerTo;
	}
	public String getNoResponseTo() {
		return noResponseTo;
	}
	public void setNoResponseTo(String noResponseTo) {
		this.noResponseTo = noResponseTo;
	}
	public String getBusyTo() {
		return busyTo;
	}
	public void setBusyTo(String busyTo) {
		this.busyTo = busyTo;
	}
	
}
