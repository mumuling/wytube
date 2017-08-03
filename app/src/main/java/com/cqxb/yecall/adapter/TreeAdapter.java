package com.cqxb.yecall.adapter;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.TreeOrgBean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TreeAdapter extends BaseAdapter{
	private List<TreeOrgBean> arrayList;
	private Context mContext;
	private Bitmap down,up;
	private LayoutInflater inflater;

	public TreeAdapter(List<TreeOrgBean> list,Context context){
		arrayList=list;
		mContext=context;
		down = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.choosebar_press_down);
		up = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.choosebar_press_up);
	}
	
	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String jsonString = JSON.toJSONString(arrayList.get(position));
//		System.out.println("jsonStringjsonString:"+jsonString);
		TreeOrgBean treeBean =JSON.parseObject(jsonString, TreeOrgBean.class);
		inflater = LayoutInflater.from(mContext);
		convertView=inflater.inflate(R.layout.tree_item,null);
		ViewHolder holder;
		holder = new ViewHolder();
		holder.text = (TextView) convertView.findViewById(R.id.text);
		holder.icon = (ImageView) convertView.findViewById(R.id.icon);
		convertView.setTag(holder);
		
		int level = (treeBean.getLevel()-1<=0?0:treeBean.getLevel()-1);
		
		holder.icon.setPadding(25 * (level + 1), holder.icon.getPaddingTop(),
				0, holder.icon.getPaddingBottom());
		holder.text.setText(treeBean.getName());
		if("group".equals(treeBean.getType())&& (treeBean.isExpanded() == false)&&treeBean.isParent()){
			holder.icon.setImageBitmap(down);
		}else if("group".equals(treeBean.getType())&& (treeBean.isExpanded() == true)&&treeBean.isParent()){
			holder.icon.setImageBitmap(up);
		}else if(!"group".equals(treeBean.getType())){
			holder.icon.setBackgroundColor(Color.TRANSPARENT);
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView text;
		ImageView icon;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<TreeOrgBean> msg) {
		this.arrayList = msg;
		notifyDataSetChanged();
	}

}
