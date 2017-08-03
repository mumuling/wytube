package com.cqxb.yecall.t9search.activity;

import android.support.v4.app.Fragment;

import com.cqxb.yecall.t9search.fragment.QwertySearchFragment;

public class QwertySearchActivity extends BaseSingleFragmentActivity{

	@Override
	protected Fragment createFragment() {

		return new QwertySearchFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {

		return false;
	}
	
}
