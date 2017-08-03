package com.android.action.param;

public class FeedbackReply {
	private String feedBackId;
	private String typeclass;
	private String typemessage;
	
	public String getTypeclass() {
		return typeclass;
	}

	public void setTypeclass(String typeclass) {
		this.typeclass = typeclass;
	}

	public String getTypemessage() {
		return typemessage;
	}

	public void setTypemessage(String typemessage) {
		this.typemessage = typemessage;
	}	

	public String getFeedBackId() {
		return this.feedBackId;
	}

	public void setFeedBackId(String feedBackId) {
		this.feedBackId = feedBackId;
	}

}
