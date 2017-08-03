package com.android.action.param;

/**
 * 网络应答公共基类
 * 
 */

public class CommReply {
	public static String SUCCESS = "000000";
	public static String ERROR_AUTH = "000009";
	public static String ERROR = "999999";

	private String statusCode;
	private String statusMessage;
	private String body;
	private String requestUUID;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}

}
