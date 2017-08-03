package com.cqxb.yecall.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;

public class ContactListAdapter extends BaseAdapter{
	
	private List<Contacts> cList;
	private Context mContext;
	
	public ContactListAdapter(Context context,List<Contacts> list) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Contacts contactBean = cList.get(position);
		convertView=LayoutInflater.from(mContext).inflate(R.layout.call_phone_recored, null);
		
//		ImageView cpr_contactImg=(ImageView)convertView.findViewById(R.id.cpr_contactImg);
//		cpr_contactImg.setVisibility(View.GONE);
		
		TextView cpr_callNumber=(TextView)convertView.findViewById(R.id.cpr_callNumber);
		cpr_callNumber.setText(contactBean.getContactName());
		cpr_callNumber.setTextScaleX(1);
		
		ImageView cpr_callType=(ImageView)convertView.findViewById(R.id.cpr_callType);
		cpr_callType.setVisibility(View.GONE);
		
		TextView cpr_callContext=(TextView)convertView.findViewById(R.id.cpr_callContext);
		cpr_callContext.setText(contactBean.getNumber());

		CheckBox cpr_contactDetail=(CheckBox)convertView.findViewById(R.id.cpr_contactDetail);
		cpr_contactDetail.setVisibility(View.GONE);
		
		TextView cpr_SIM=(TextView)convertView.findViewById(R.id.cpr_SIM);
		
		if(contactBean.getContactType()==0){
			//手机
			cpr_SIM.setText("手机存储");
		}else if(contactBean.getContactType()==1){
			//SIM1
			cpr_SIM.setText("SIM卡1");
		}else if(contactBean.getContactType()==2){
			//SIM2
			cpr_SIM.setText("SIM卡2");
		}
		
		cpr_SIM.setVisibility(View.VISIBLE);
		return convertView;
	}

	public void updateListView(List<Contacts> list){
		cList=list;
		notifyDataSetChanged();
	}
}
