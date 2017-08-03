package com.cqxb.yecall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqxb.throughImg.AttacherImageView;
import com.cqxb.throughImg.ProgressWheel;
import com.cqxb.yecall.R;

import java.util.List;

public class PageViewAdapter extends PagerAdapter{

	private List<String> pList;
	private Context mContext;
	
	public PageViewAdapter(Context context,List<String> list) {
		mContext=context;
		pList=list;
	}
	
	@Override
	public int getCount() {
		return pList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == (View) obj;  
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_bigimage_layout, null);
		AttacherImageView iv=(AttacherImageView)inflate.findViewById(R.id.mBigImage);
		ProgressWheel mProgressBar = (ProgressWheel)inflate.findViewById(R.id.mProgressBar);
		int[] pixels = new int[] { 0xFF2E9121, 0xFF2E9121, 0xFF2E9121,
				0xFF2E9121, 0xFF2E9121, 0xFF2E9121, 0xFFFFFFFF, 0xFFFFFFFF };
		Bitmap bmp = Bitmap.createBitmap(pixels, 8, 1, Bitmap.Config.ARGB_8888);
		Shader shader = new BitmapShader(bmp, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
		try {
			Bitmap bm=BitmapFactory.decodeFile(pList.get(position));
			iv.setImageBitmap(bm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		((ViewPager)container).addView(inflate,0);
		mProgressBar.setRimShader(shader);
		mProgressBar.setVisibility(View.GONE);
		return inflate;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<String> list){
		this.pList = list;
		notifyDataSetChanged();
	}
}
