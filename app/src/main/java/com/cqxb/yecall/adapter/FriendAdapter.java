package com.cqxb.yecall.adapter;

import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.UserBean;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class FriendAdapter extends BaseAdapter implements SectionIndexer{
	private List<UserBean> list = null;
	private Context mContext;
	
	public FriendAdapter(Context mContext, List<UserBean> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<UserBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			final View v=view;
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.ivImg = (ImageView) view.findViewById(R.id.itemImg);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.status = (ImageView) view.findViewById(R.id.itemImgStatus);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.ivImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
			}
		});
		UserBean mContent = list.get(position);
		
		//根据position获取分类的首字母的Char ascii值ֵ
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(mContent.getNickName());
		
		
//		System.out.println("img status============:"+mContent.getStatusImg());
		//忙碌 -1 离开 -3 在线 - 4 
		int img=R.drawable.trans;
		
		if("unavailable".equals(mContent.getVisibility())){
			viewHolder.ivImg.setImageResource(R.drawable.qqli);
		}else {
			viewHolder.ivImg.setImageResource(R.drawable.qqzai);
			if(mContent.getStatusImg()!=null){
				if(0==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.trans;
					viewHolder.ivImg.setImageResource(R.drawable.qqli);
				}else if(1==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.status_shield;
				}else if(2==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.trans;
					viewHolder.ivImg.setImageResource(R.drawable.qqli);
				}else if(3==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.status_leave;
				}else if(4==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.status_online;
				}else if(5==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.status_qme;
				}else if(6==Integer.parseInt(mContent.getStatusImg())){
					img=R.drawable.ml3;
				}
			}
		}
		viewHolder.status.setBackgroundResource(img);
		return view;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		ImageView ivImg;
		ImageView status;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}