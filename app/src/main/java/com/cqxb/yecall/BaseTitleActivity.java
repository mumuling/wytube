package com.cqxb.yecall;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseTitleActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public void setContentView(int layoutResID) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View title = inflater.inflate(R.layout.title_custom, null);
		FrameLayout space = (FrameLayout) title.findViewById(R.id.space);
		ViewGroup content = (ViewGroup) inflater.inflate(layoutResID, null);
		((ViewGroup) space).addView(content);
		setContentView(title);

	}

	@Override
	public void setTitle(CharSequence title) {
		((TextView) findViewById(R.id.textView_title)).setText(title);
	}

	public void setTitleVisiable(int visibility) {
		findViewById(R.id.LayoutTitle).setVisibility(visibility);
	}
	
	public void setTitleBackground(int color) {
		findViewById(R.id.LayoutTitle).setBackgroundColor(color);
	}

	public void setCustomLeftBtn(int background, View.OnClickListener custom_btn_click_listener) {
		findViewById(R.id.title_custom_left_bg).setVisibility(View.VISIBLE);
		ImageView layout = (ImageView) findViewById(R.id.title_custom_left_id);
		layout.setBackgroundResource(background);
		if (custom_btn_click_listener != null) {
			findViewById(R.id.title_custom_left_bg).setOnClickListener(custom_btn_click_listener);
		}
	}
	
	
	public void setCustomRightBtn(int background, View.OnClickListener custom_btn_click_listener) {
		findViewById(R.id.title_custom_bg).setVisibility(View.VISIBLE);
		ImageView layout = (ImageView) findViewById(R.id.title_custom_id);
		layout.setBackgroundResource(background);
		if (custom_btn_click_listener != null) {
			findViewById(R.id.title_custom_bg).setOnClickListener(custom_btn_click_listener);
		}
	}
}
