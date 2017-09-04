package com.cqxb.yecall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.cqxb.yecall.DetailDataActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

import org.linphone.DialerFragment;

import java.util.List;

public class CommunityAdapter extends BaseAdapter implements SectionIndexer{
	
	private List<Contacts> cList;
	private Context mContext;
	
	public CommunityAdapter(Context context,List<Contacts> list) {
		cList=list;
		mContext=context;
	}
	
	@Override
	public int getCount() {
		return cList.size();
	}

	@Override
	public Object getItem(int position) {
		return cList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		ImageView ivImg;
		ImageView status;
		ImageView callImg;
		TextView context;

		FrameLayout icon;//联系人头像，暂时没用到做隐藏处理
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			final View v=view;
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.ivImg = (ImageView) view.findViewById(R.id.itemImg);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.status = (ImageView) view.findViewById(R.id.itemImgStatus);
			viewHolder.context = (TextView) view.findViewById(R.id.context);
			viewHolder.callImg = (ImageView) view.findViewById(R.id.callImg);
			viewHolder.icon = (FrameLayout) view.findViewById(R.id.fl_contact_icon);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
//		viewHolder.ivImg.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				
//			}
//		});
		
		if(cList.size()<1){
			return view;
		}
		
		
		final Contacts contactBean = cList.get(position);
		if("-123456789".equals(contactBean.getNumber())){
			view.findViewById(R.id.callphone).setVisibility(View.GONE);
			view.findViewById(R.id.callNull).setVisibility(View.GONE);
			return view;
		}
		
		//根据position获取分类的首字母的Char ascii值ֵ
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(contactBean.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.icon.setVisibility(View.GONE);
//		viewHolder.ivImg.setBackgroundResource(R.drawable.people);
//		viewHolder.ivImg.setVisibility(View.GONE);
		
		viewHolder.status.setVisibility(View.GONE);
		viewHolder.tvTitle.setText(contactBean.getContactName());
		viewHolder.context.setText(contactBean.getNumber());
		viewHolder.context.setVisibility(View.VISIBLE);
//		viewHolder.callImg.setVisibility(View.GONE);
		viewHolder.callImg.setBackgroundResource(R.drawable.bianji);
		viewHolder.callImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(SettingInfo.getParams(PreferenceBean.LOGINFLAG, "").equals("")){
					DialerFragment.instance().justLogin(mContext);
				}else{
					Intent intent = new Intent(mContext, DetailDataActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("detail", contactBean.getContactName()+","+contactBean.getNumber());
					mContext.startActivity(intent);
				}
			}
		});

		return view;
	}

	public void updateListView(List<Contacts> list){
		cList=list;
		notifyDataSetChanged();
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return cList.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = cList.get(i).getSortLetters();
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
