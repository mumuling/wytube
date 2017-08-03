package com.cqxb.yecall.adapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.cqxb.yecall.ChatActivity;
import com.cqxb.yecall.PageViewActivity;
import com.cqxb.yecall.R;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.bean.MsgBean;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.T;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatAdapter extends BaseAdapter {
    
	private Context cxt;
	private LayoutInflater inflater;
	private List<SingleChatEntity> listMsg;

	public ChatAdapter(Context context, List<SingleChatEntity> msg) {
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
		
		SingleChatEntity entity = listMsg.get(position);
		
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
		useridView.setVisibility(View.GONE);
		
		dateView.setText(time);
		msgView.setText("");
		String sText2 = "";
		String msg = entity.getContext();
		System.out.println(" 聊天:  "+msg);
		String paths="";
		if (!TextUtils.isEmpty(msg)) {
			String[] split = msg.split("<!@split-split@>");
			msg = "";
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("<!@split-split1@>");
				if ("val".equals(split2[0])) {
					msgView.append(split2[1]);
				}
				if ("voice".equals(split2[0])) {
					msgView.append("语音");
					//Environment.getExternalStorageDirectory()+"/yet/chat/"+split2[1]+".amr"
					msgView.setOnClickListener(new chatClickListener(split2[1]));
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
						Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(path);
						ImageSpan imageSpan=new ImageSpan(cxt,loacalBitmap);
						String emojiStr = "[图片]";
						SpannableString spannableString = new SpannableString(emojiStr);
						spannableString.setSpan(imageSpan,emojiStr.indexOf("["),emojiStr.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						msgView.append(spannableString);
						msgView.setClickable(true);
						click(spannableString);
						paths+=path+",";
						msgView.setOnClickListener(new imgClickListener(paths));
//						msgView.append(Html.fromHtml(sText2, imageGetter, null));
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
	public void updateListView(List<SingleChatEntity> msg) {
		this.listMsg = msg;
		notifyDataSetChanged();
	}
	
	class imgClickListener implements View.OnClickListener{
		String val;
        public imgClickListener(String name){
            this.val = name;
        }
		@Override
		public void onClick(View v) {
			Log.e("", "*************"+val);
			if (!TextUtils.isEmpty(val)) {
				Intent intent = new Intent(cxt, PageViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("imgList", val);
				cxt.startActivity(intent);
			}
		}
		
	}
	
	
	
	MediaPlayer mediaPlayer;
	int flag=-1;
    class chatClickListener implements View.OnClickListener{
        String name;
        public chatClickListener(String name){
            this.name = name;
        }
        @Override
        public void onClick(View view) {
        	if(flag==0){
        		return;
        	}
        	flag=0;
        	final TextView re=((TextView)view.findViewById(R.id.formclient_row_msg));
        	re.setClickable(false);
        	re.setText("正在播放");
//        	re.setBackgroundResource(resid)
            Log.i("111111111111111111111111", "11111111111111111111");
        //    byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
          //      mediaPlayer.setDataSource(android.os.Environment.getExternalStorageDirectory()+"/yet/chat/"+"yuyin.amr");
                File file = new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_path)+name+".amr");
                if(!file.exists()){
                	re.setText("语音");
                	mediaPlayer.release();
                	return;
                }
                FileInputStream fis = new FileInputStream(file);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        re.setClickable(true);
                        re.setText("语音");
                        flag=1;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void cancelVoice(){
    	if(mediaPlayer!=null){
    		mediaPlayer.stop();
            mediaPlayer.release();
    	}
    }
    
	public void click(Spannable s) {
		ImageSpan[] image_spans = s.getSpans(0, s.length(), ImageSpan.class);
		for (ImageSpan span : image_spans) {

			final String image_src = span.getSource();
			final int start = s.getSpanStart(span);
			final int end = s.getSpanEnd(span);

			ClickableSpan click_span = new ClickableSpan() {

				@Override
				public void onClick(View widget) {
					Toast.makeText(cxt,
							"Image Clicked " + image_src, Toast.LENGTH_SHORT)
							.show();
				}

			};

			ClickableSpan[] click_spans = s.getSpans(start, end,
					ClickableSpan.class);

			if (click_spans.length != 0) {
				for (ClickableSpan c_span : click_spans) {
					s.removeSpan(c_span);
				}
			}
			s.setSpan(click_span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}
}
