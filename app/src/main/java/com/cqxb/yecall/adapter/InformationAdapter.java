package com.cqxb.yecall.adapter;

import java.io.File;
import java.util.List;

import org.jivesoftware.smackx.packet.VCard;

import com.cqxb.yecall.R;
import com.cqxb.yecall.adapter.ChatAdapter.chatClickListener;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.until.BaseUntil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationAdapter extends BaseAdapter{
	private List<InformationList> informationLists;
	private Context mContext;
	
	public InformationAdapter(Context context,List<InformationList> list){
		informationLists=list;
		mContext=context;
	}

	@Override
	public int getCount() {
		return informationLists.size();
	}

	@Override
	public Object getItem(int position) {
		return informationLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InformationList informationList = informationLists.get(position);
		convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_information_content, null);
		ImageView buddy_listview_child_avatar =(ImageView) convertView.findViewById(R.id.buddy_listview_child_avatar);
		TextView buddy_listview_child_nick =(TextView) convertView.findViewById(R.id.buddy_listview_child_nick);
		TextView msgDate =(TextView) convertView.findViewById(R.id.msgDate);
		TextView buddy_listview_child_trends =(TextView) convertView.findViewById(R.id.buddy_listview_child_trends);
		TextView buddy_no_child =(TextView) convertView.findViewById(R.id.buddy_no_child);
		TextView msgCount =(TextView) convertView.findViewById(R.id.msgCount);
		String count = informationList.getCount();
		if(!"".equals(count)){
			msgCount.setVisibility(View.VISIBLE);
			if(Integer.parseInt(count)>99){
				count="99+";
			}else if(Integer.parseInt(count)<=0){
				msgCount.setVisibility(View.GONE);
			}
		}
		
		buddy_listview_child_trends.setMaxEms(13);
		String date = BaseUntil.getDate(informationList.getMsgDate());
		
		if("1".equals(informationList.getFlag())){//好友请求
			buddy_listview_child_avatar.setBackgroundResource(R.drawable.messages);
			buddy_listview_child_nick.setText(informationList.getNickName());
			msgDate.setText(date);
			msgCount.setText(count);
			buddy_listview_child_trends.setText("附加消息："+BaseUntil.stringNoNull(informationList.getContext()));
			buddy_no_child.setText("");
		}else if("2".equals(informationList.getFlag())){//好友单人聊天
			msgDate.setText(date);
			msgCount.setText(count);
			buddy_listview_child_nick.setText(informationList.getNickName());
			buddy_listview_child_trends.setText(informationList.getContext());
			msg(buddy_listview_child_trends, informationList.getContext());
			buddy_no_child.setText("");
		}else if("3".equals(informationList.getFlag())){//群组请求
			msgDate.setText(date);
			msgCount.setText(count);
			buddy_listview_child_avatar.setBackgroundResource(R.drawable.messages);
			buddy_listview_child_nick.setText(informationList.getNickName());
			buddy_listview_child_trends.setText("附加消息："+BaseUntil.stringNoNull(informationList.getContext()));
			buddy_no_child.setText("");
		}else if("4".equals(informationList.getFlag())){//群组聊天
			msgDate.setText(date);
			msgCount.setText(count);
			buddy_listview_child_avatar.setBackgroundResource(R.drawable.qztb);
			buddy_listview_child_nick.setText(informationList.getRoomId());
			buddy_listview_child_trends.setText(informationList.getContext());
			msg(buddy_listview_child_trends, informationList.getContext());
			buddy_no_child.setText("");
		}else if("5".equals(informationList.getFlag())){//
			
		}else if("6".equals(informationList.getFlag())){
			
		}
		return convertView;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<InformationList> list){
		this.informationLists = list;
		notifyDataSetChanged();
	}
	
	public void msg(TextView text,String userEntity){
		//处理图片
		final Html.ImageGetter imageGetter = new Html.ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
					Drawable drawable=Drawable.createFromPath(source);
				    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), 30);
//				    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				return drawable;
			}
		};
		text.setText("");
		String sText2 = "";
//		String msg=BaseUntil.getMsgDistr(userEntity);
		System.out.println("sText2 : "+userEntity);
		//处理成图片和文字
		if(!TextUtils.isEmpty(userEntity)){
			String[] split = userEntity.split("<!@split-split@>");
			userEntity = "";
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("<!@split-split1@>");
				if ("val".equals(split2[0])) {
					text.append(split2[1]);
				}
				if ("voice".equals(split2[0])) {
					text.append("语音");
					//Environment.getExternalStorageDirectory()+"/yet/chat/"+split2[1]+".amr"
//					text.setOnClickListener(new chatClickListener(split2[1]));
//					((LinearLayout)convertView.findViewById(R.id.relativeLayout1)).setOnClickListener(new chatClickListener(split2[1]));
				}
				String path = "";
				if ("path".equals(split2[0])) {
					sText2 = "<img src=\"" + split2[1] + "\" />";
					path = split2[1];
				}
				File f = new File(path);
				if (f.exists()) {
					try {
						/*这里显示图片
						Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(path);
						ImageSpan imageSpan=new ImageSpan(mContext,loacalBitmap);
						String emojiStr = "[图片]";
						SpannableString spannableString = new SpannableString(emojiStr);
						spannableString.setSpan(imageSpan,emojiStr.indexOf("["),emojiStr.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						text.append(spannableString);
						*/
						text.append("图片");
//						msgView.append(Html.fromHtml(sText2, imageGetter, null));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
}
