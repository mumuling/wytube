package com.cqxb.yecall.t9search.activity;

import android.support.v4.app.Fragment;

import com.cqxb.yecall.t9search.fragment.MainFragment;


public class MainActivity extends BaseSingleFragmentActivity{

	@Override
	protected Fragment createFragment() {
		return new MainFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		return false;
	}
	
}
