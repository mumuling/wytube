package com.cqxb.yecall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class AddCallActivity extends BaseTitleActivity{
	private FragmentTransaction fragmentTransation;
	private FragmentManager fragmentManager;
	private Fragment[] mFragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_call);
		mFragments = new Fragment[1];
		fragmentManager = getSupportFragmentManager();
		mFragments[0] = fragmentManager.findFragmentById(R.id.addcall);
		
		fragmentTransation = fragmentManager.beginTransaction()
				.hide(mFragments[0]);
		fragmentTransation.show(mFragments[0]).commit();
	}

}
