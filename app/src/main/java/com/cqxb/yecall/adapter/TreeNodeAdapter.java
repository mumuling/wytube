package com.cqxb.yecall.adapter;

import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.EmployeeBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TreeNodeAdapter extends BaseAdapter{
	private Context context;
	private List<EmployeeBean> list;
	
	public TreeNodeAdapter(Context context,List<EmployeeBean> list){
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EmployeeBean employeeBean=list.get(position);
		convertView=LayoutInflater.from(context).inflate(R.layout.tree_item_son, null);
		TextView name=(TextView) convertView.findViewById(R.id.name);
		TextView pos=(TextView) convertView.findViewById(R.id.position);
		name.setText(employeeBean.getName());
		pos.setText(employeeBean.getPosition());
		return convertView;
	}

	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<EmployeeBean> msg) {
		this.list = msg;
		notifyDataSetChanged();
	}
}
