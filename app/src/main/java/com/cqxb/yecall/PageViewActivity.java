package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.PageViewAdapter;
import com.cqxb.yecall.until.T;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class PageViewActivity extends BaseActivity{

	private ViewPager mViewPager;
	private PageViewAdapter mAdapter;
	private String  path="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.img_page);
		Intent intent=getIntent();
		path=intent.getStringExtra("imgList");
		mViewPager = (ViewPager)findViewById(R.id.pager);  
		mAdapter = new PageViewAdapter(getApplicationContext(), getList());
		mViewPager.setAdapter(mAdapter); 
		mViewPager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				T.show(getApplicationContext(), "click.........................", Toast.LENGTH_SHORT);
			}
		});
		
		mViewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final float x = event.getRawX();
				final float y = event.getRawY();
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						//ToastUtil.makeText(getApplicationContext(), "ACTION_DOWN", Toast.LENGTH_SHORT);
						break;
					case MotionEvent.ACTION_MOVE:
						//ToastUtil.makeText(getApplicationContext(),"ACTION_MOVE", Toast.LENGTH_SHORT);
						break;
					default:  
	                    break;  
				}
				return false;
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public List<String> getList(){
		List<String> pList=new ArrayList<String>();
		if(!TextUtils.isEmpty(path)){
			String[] split = path.split(",");
			for (int i = 0; i < split.length; i++) {
				if(!TextUtils.isEmpty(split[i])){
					pList.add(split[i]);
				}
			}
		}
		return pList;
	}

	//监听按键
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			this.finish();
		}
		return false;  
	}
	
	public void close(View view){
		this.finish();
	}
}
