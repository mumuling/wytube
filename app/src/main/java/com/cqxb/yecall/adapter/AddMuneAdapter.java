package com.cqxb.yecall.adapter;

import java.util.List;

import com.cqxb.yecall.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddMuneAdapter extends BaseAdapter{
	private List<String> mList;
	private Context mContext;
	
	public AddMuneAdapter(Context context,List<String> list){
		mList=list;
		mContext=context;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		String string = mList.get(arg0);
		arg1=LayoutInflater.from(mContext).inflate(R.layout.add_mune, null);
		ImageView img=(ImageView) arg1.findViewById(R.id.am_img);
		img.setBackgroundResource(R.drawable.heiadd);
		img.setVisibility(View.GONE);
		TextView text =(TextView) arg1.findViewById(R.id.am_tv);
		text.setText(string);
		return arg1;
	}

}
