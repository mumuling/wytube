package com.cqxb.yecall.listener;

import org.jivesoftware.smackx.muc.UserStatusListener;


public class MyUserStatusListener implements UserStatusListener{

	final String TAG="MyUserStatusListener";
	@Override
	public void adminGranted() {
		System.out.println("adminGranted");
	}

	@Override
	public void adminRevoked() {
		System.out.println("adminRevoked");		
	}

	@Override
	public void banned(String arg0, String arg1) {
		System.out.println("banned(String arg0, String arg1) ");				
	}

	//监控被踢状态
	@Override
	public void kicked(String arg0, String arg1) {
		System.out.println("arg0:"+arg0+"    arg1:"+arg1);				
	}

	@Override
	public void membershipGranted() {
		System.out.println("membershipGranted");				
	}

	@Override
	public void membershipRevoked() {
		System.out.println("membershipRevoked");					
	}

	@Override
	public void moderatorGranted() {
		System.out.println("moderatorGranted");				
	}

	@Override
	public void moderatorRevoked() {
		System.out.println("moderatorRevoked");				
	}

	@Override
	public void ownershipGranted() {
		System.out.println("ownershipGranted");			
	}

	@Override
	public void ownershipRevoked() {
		System.out.println("ownershipRevoked");					
	}

	@Override
	public void voiceGranted() {
		System.out.println("voiceGranted");					
	}

	@Override
	public void voiceRevoked() {
		System.out.println("voiceRevoked");					
	}

}
