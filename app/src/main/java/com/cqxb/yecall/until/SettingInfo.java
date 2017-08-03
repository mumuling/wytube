package com.cqxb.yecall.until;

import org.xbill.DNS.tests.primary;

import android.content.Context;

public class SettingInfo {
	private static final String KEY_ACCOUNT = "key_account";
	private static final String KEY_PWD = "key_password";
	private static final String KEY_ZONE_NUMBER = "key_zone_number";
	private static final String KEY_AUTO_ANSWER_ENABLE = "key_auto_answer_enable";
	private static final String KEY_FEEDBACK_ID = "key_feedback_id";
	private static final String KEY_PUSHMESSAGE_ID = "key_pushmessage_id";
	private static final String KEY_AGREEMENT = "key_agreement";
	
	public static final String KEY_DATE = "key_date";
	public static final String KEY_UPDATE = "key_update";

	public static void init(Context context){
		PreferenceBase.init(context);
	}
	
	public static String getAccount() {
		return PreferenceBase.getmPreferences().getString(PreferenceBean.USERACCOUNT, "");
	}
	
	public static String getLinphoneAccount() {
		return PreferenceBase.getmPreferences().getString(PreferenceBean.USERLINPHONEACCOUNT, "");
	}

	public static void setAccount(String account) {
		PreferenceBase.getmPreferences().edit().putString(PreferenceBean.USERACCOUNT, account).commit();
	}
	
	public static void setLinphoneAccount(String account) {
		PreferenceBase.getmPreferences().edit().putString(PreferenceBean.USERLINPHONEACCOUNT, account).commit();
	}

	public static String getPassword() {
		return PreferenceBase.getmPreferences().getString(PreferenceBean.USERPWD, "");
	}
	
	public static String getLinphonePassword() {
		return PreferenceBase.getmPreferences().getString(PreferenceBean.USERLINPHONEPWD, "");
	}

	
	public static void setPassword(String password) {
		PreferenceBase.getmPreferences().edit().putString(PreferenceBean.USERPWD, password).commit();
	}
	
	public static void setLinphonePassword(String password) {
		PreferenceBase.getmPreferences().edit().putString(PreferenceBean.USERLINPHONEPWD, password).commit();
	}

	public static String getZoneNum() {
		return PreferenceBase.getmPreferences().getString(KEY_ZONE_NUMBER, "");
	}

	public static void setZoneNum(String zoneNum) {
		PreferenceBase.getmPreferences().edit().putString(KEY_ZONE_NUMBER, zoneNum).commit();
	}

	public static boolean getAutoAnswerEnable() {
		return PreferenceBase.getmPreferences().getBoolean(KEY_AUTO_ANSWER_ENABLE, false);
	}

	public static void setAutoAnswerEnable(boolean enable) {
		PreferenceBase.getmPreferences().edit().putBoolean(KEY_AUTO_ANSWER_ENABLE, enable).commit();
	}

	public static String getFeedbackID() {
		return PreferenceBase.getmPreferences().getString(KEY_FEEDBACK_ID, "");
	}

	public static void setFeedbackID(String feedback_id) {
		PreferenceBase.getmPreferences().edit().putString(KEY_FEEDBACK_ID, feedback_id).commit();
	}

	public static void setPushMessageID(String pushmessage_id) {
		PreferenceBase.getmPreferences().edit().putString(KEY_PUSHMESSAGE_ID, pushmessage_id).commit();
	}

	public static String getPushMessageID() {
		return PreferenceBase.getmPreferences().getString(KEY_PUSHMESSAGE_ID, "");
	}

	public static boolean getAgreement() {
		return PreferenceBase.getmPreferences().getBoolean(KEY_AGREEMENT, false);
	}

	public static void setAgreement(boolean agree) {
		PreferenceBase.getmPreferences().edit().putBoolean(KEY_AGREEMENT, agree).commit();
	}
	
	public static void setParams(String key,String value){
		PreferenceBase.getmPreferences().edit().putString(key, value).commit();
	}
	
	public static void setParams(String key,int value){
		PreferenceBase.getmPreferences().edit().putInt(key, value).commit();
	}
	
	public static String getParams(String key,String defValue){
		return PreferenceBase.getmPreferences().getString(key, defValue);
	}
	
	public static void setRefreshPassword(boolean value){
		PreferenceBase.getmPreferences().edit().putBoolean(KEY_UPDATE, value).commit();
	}
	
	public static Boolean getRefreshPassword(){
		return PreferenceBase.getmPreferences().getBoolean(KEY_UPDATE, false);
	}
} 
