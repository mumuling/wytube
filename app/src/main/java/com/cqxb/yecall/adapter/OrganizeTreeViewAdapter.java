package com.cqxb.yecall.adapter;

import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.TreeElementBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrganizeTreeViewAdapter extends ArrayAdapter {

	private LayoutInflater mInflater;
	private List<TreeElementBean> mfilelist;
	private Bitmap mIconCollapse;
	private Bitmap mIconExpand;

	public OrganizeTreeViewAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		mInflater = LayoutInflater.from(context);
		mfilelist = objects;
		mIconCollapse = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.choosebar_press_down);
		mIconExpand = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.choosebar_press_up);
	}

	public int getCount() {
		return mfilelist.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		/* if (convertView == null) { */
		convertView = mInflater.inflate(R.layout.tree_item, null);
		holder = new ViewHolder();
		holder.text = (TextView) convertView.findViewById(R.id.text);
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		convertView.setTag(holder);
		/*
		 * } else { holder = (ViewHolder) convertView.getTag(); }
		 */

		final TreeElementBean obj = mfilelist.get(position);

		// holder.text.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.i("TreeView" , "obj.id:" + obj.getId());
		// }
		// });

		int level = obj.getLevel();
		holder.icon.setPadding(25 * (level + 1), holder.icon.getPaddingTop(),
				0, holder.icon.getPaddingBottom());
		holder.text.setText(obj.getOutlineTitle());
		if (obj.isMhasChild() && (obj.isExpanded() == false)) {
			holder.icon.setImageBitmap(mIconCollapse);
//			holder.icon.setBackgroundResource(R.drawable.choosebar_press_down);
		} else if (obj.isMhasChild() && (obj.isExpanded() == true)) {
			holder.icon.setImageBitmap(mIconExpand);
//			holder.icon.setBackgroundResource(R.drawable.choosebar_press_up);
		} else if (!obj.isMhasChild()) {
//			holder.icon.setImageBitmap(mIconCollapse);
//			holder.icon.setVisibility(View.INVISIBLE);
			holder.icon.setBackgroundColor(Color.WHITE);
		} 
		return convertView;
	}

	class ViewHolder {
		TextView text;
		ImageView icon;

	}

}
