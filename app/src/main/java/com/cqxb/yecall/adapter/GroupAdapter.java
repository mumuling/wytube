package com.cqxb.yecall.adapter;

import java.io.File;
import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.MsgBean;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.until.T;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GroupAdapter extends BaseAdapter {

	private Context cxt;
	private LayoutInflater inflater;
	private List<GroupChatEntity> listMsg;

	public GroupAdapter(Context context, List<GroupChatEntity> msg) {
		this.cxt = context;
		this.listMsg = msg;
	}

	@Override
	public int getCount() {
		return listMsg.size();
	}

	@Override
	public Object getItem(int position) {
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GroupChatEntity entity = listMsg.get(position);
		
		// 显示消息的布局：内容、背景、用户、时间
		this.inflater = (LayoutInflater) this.cxt
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// IN,OUT的图片
		if (SingleChatEntity.IN.equals(entity.getWho())) {
			convertView = this.inflater.inflate(R.layout.formclient_chat_in,
					null);
		} else {
			convertView = this.inflater.inflate(R.layout.formclient_chat_out,
					null);
		}

		TextView useridView = (TextView) convertView
				.findViewById(R.id.formclient_row_userid);
		TextView dateView = (TextView) convertView
				.findViewById(R.id.formclient_row_date);
		TextView msgView = (TextView) convertView
				.findViewById(R.id.formclient_row_msg);

		final Html.ImageGetter imageGetter = new Html.ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = Drawable.createFromPath(source);
				drawable.setBounds(0, 0, 240,
						240);
				// drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				// drawable.getMinimumHeight());
				return drawable;
			}
		};
//		System.out.println("vcard adapter:"+entity.getNickName());
		
		String time=entity.getMsgDate();
		String[] times = time.split("-");
		time=times[1]+"-"+times[2];
		
		useridView.setText(entity.getNickName());
//		useridView.setVisibility(View.GONE);
		
		dateView.setText(time);
		msgView.setText("");
		String sText2 = "";
		String msg = entity.getContext();
		
		if (!TextUtils.isEmpty(msg)) {
			String[] split = msg.split(",");
			msg = "";
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("=");
				if ("val".equals(split2[0])) {
					msgView.append(split2[1]);
				}
				String path = "";
				if ("path".equals(split2[0])) {
					sText2 = "<img src=\"" + split2[1] + "\" />";
					path = split2[1];
				}
				File f = new File(path);
				if (f.exists()) {
					try {
						msgView.append(Html.fromHtml(sText2, imageGetter, null));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return convertView;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GroupChatEntity> msg) {
		this.listMsg = msg;
		notifyDataSetChanged();
	}

}
