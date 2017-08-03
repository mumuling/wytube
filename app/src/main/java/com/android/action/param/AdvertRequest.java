package com.android.action.param;

public class AdvertRequest extends CommRequest {
	private String advertType = "1";// 广告类型
	
	public AdvertRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}
}
