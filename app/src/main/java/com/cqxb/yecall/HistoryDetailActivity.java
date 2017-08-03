package com.cqxb.yecall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.cqxb.yecall.bean.CallLogBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wytube.activity.OrderActivity;

import org.linphone.DialerFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryDetailActivity extends Activity {

	private SeekBar SoundseekBar,ProceseekBar2;
    private ImageButton button;
    private MediaPlayer mediaPlayer;
    private TextView nowPlayTime,allTime,tv_historydetail_contactName,tv_historydetail_contactNumber,
    tv_historydetail_callDate,tv_historydetail_callTime,tv_historydetail_callDuration,tv_historydetail_gallery,tv_audio_play;//,volumeView,maxVolumeTextView;
    private AudioManager audioManager;
    private String contactName,number,beginTime,callType,recordFile,photoFile,duration;
    private RelativeLayout rlAudioPlay;
    private ImageView iv_historydetail_callType,iv_historydetail_more,title_custom_left_id;
    private boolean isPrepared = false;
    private String[] imageUrls;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private DBHelper dbHelper;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        
        dbHelper=new DBHelper(getApplicationContext());
        ProceseekBar2=(SeekBar)findViewById(R.id.sp_historydetail_playProgress);
        SoundseekBar=(SeekBar)findViewById(R.id.sp_historydetail_volume);
        button=(ImageButton)findViewById(R.id.ib_historydetail_controlPlay);
        nowPlayTime=(TextView)findViewById(R.id.tv_historydetail_playTime);
        allTime=(TextView)findViewById(R.id.tv_historydetail_playDuration);
        rlAudioPlay=(RelativeLayout)findViewById(R.id.rl_audio_play);
        tv_historydetail_gallery=(TextView)findViewById(R.id.tv_historydetail_gallery);
        tv_audio_play=(TextView)findViewById(R.id.tv_audio_play);
        tv_historydetail_contactName=(TextView)findViewById(R.id.tv_historydetail_contactName);
        tv_historydetail_contactNumber=(TextView)findViewById(R.id.tv_historydetail_contactNumber);
        tv_historydetail_callDate=(TextView)findViewById(R.id.tv_historydetail_callDate);
        iv_historydetail_callType=(ImageView)findViewById(R.id.iv_historydetail_callType);
        tv_historydetail_callTime=(TextView)findViewById(R.id.tv_historydetail_callTime);
        tv_historydetail_callDuration=(TextView)findViewById(R.id.tv_historydetail_callDuration);
        title_custom_left_id=(ImageView)findViewById(R.id.title_custom_left_id);
        
        contactName = BaseUntil.stringNoNull(getIntent().getStringExtra("contactName")).trim();
        number = BaseUntil.stringNoNull(getIntent().getStringExtra("number")).trim();
        beginTime = BaseUntil.stringNoNull(getIntent().getStringExtra("beginTime")).trim();
        callType = BaseUntil.stringNoNull(getIntent().getStringExtra("callType")).trim();
        recordFile = BaseUntil.stringNoNull(getIntent().getStringExtra("recordFile")).trim();
        photoFile = BaseUntil.stringNoNull(getIntent().getStringExtra("photoFile")).trim();
        duration = BaseUntil.stringNoNull(getIntent().getStringExtra("duration")).trim();
        tv_historydetail_contactName.setText(contactName);
        if(number.indexOf("sip:")!=-1){
        	number = number.replaceFirst("sip:", "").trim();
			if(number.indexOf("@")!=-1){
				number = number.substring(0,number.indexOf("@")).trim();
			}
		}
        
        //右边删除按钮!
        title_custom_left_id.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbHelper.open();
				boolean deleteData = dbHelper.deleteData(CallLogBean.TABLE, CallLogBean.STARTCALL + "=?", new String[] {beginTime});
				dbHelper.close();
				if(!recordFile.equals("")){
		        	File file = new File(recordFile);
		        	if(file.exists()){
		        		file.delete();
		        	}
		        }
		        if(!photoFile.equals("")){
		        	for(String tempPhoto:photoFile.split(",")){
		        		File file = new File(tempPhoto);
			        	if(file.exists()){
			        		file.delete();
			        	}
		        	}
		        }
		        ContactBase cb = new ContactBase(getApplicationContext());
		        List<Contacts> allcontact = cb.getPhoneCallLists();
				YETApplication.getinstant().setThjl(allcontact);
				DialerFragment.instance().refreshList(allcontact);
		        finish();
			}
		});
        
        iv_historydetail_more=(ImageView)findViewById(R.id.iv_historydetail_more);
        iv_historydetail_more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HistoryDetailActivity.this, DetailDataActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("detail", contactName+","+number);
				startActivity(intent);				
			}
		});
//        volumeView=(TextView)findViewById(R.id.textView3);
//        maxVolumeTextView=(TextView)findViewById(R.id.textView4);
        button.setOnClickListener(new ButtonListener());
       
        mediaPlayer=new MediaPlayer();
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);//获取音量服务
        int MaxSound=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//获取系统音量最大值
        SoundseekBar.setMax(MaxSound);//音量控制Bar的最大值设置为系统音量最大值
        int currentSount=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//获取当前音量
        SoundseekBar.setProgress(currentSount);//音量控制Bar的当前值设置为系统音量当前值
        SoundseekBar.setOnSeekBarChangeListener(new SeekBarListener());
        ProceseekBar2.setOnSeekBarChangeListener(new ProcessBarListener());
        
       
        tv_historydetail_contactNumber.setText(number.trim());
        
        String dateStr = "";
        String timeStr = "";
        String durationStr = "";
        if(!beginTime.equals("")){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        	SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        	dateStr = sdf.format(new Date(Long.parseLong(beginTime)));
        	timeStr = sdf2.format(new Date(Long.parseLong(beginTime)));
        }
        
        if (callType.equals("1")) {
			//"呼入";
        	iv_historydetail_callType.setImageResource(R.drawable.icon_incoming_call);
        	durationStr=" 呼入 "+duration;
		} else if (callType.equals("2")) {
			//"呼出";
			iv_historydetail_callType.setImageResource(R.drawable.icon_outgoing_call);
			durationStr=" 呼出 "+duration;
		} else if (callType.equals("3")) {
			// "未接";
			iv_historydetail_callType.setImageResource(R.drawable.icon_missed_call);
			durationStr=" 未接 ";
		}
        tv_historydetail_callDate.setText(dateStr);
        tv_historydetail_callTime.setText(timeStr);
        tv_historydetail_callDuration.setText(durationStr);
        
        if(recordFile.equals("")){
        	rlAudioPlay.setVisibility(View.GONE);
        	tv_audio_play.setVisibility(View.GONE);
        }else{
        	Log.e("", "recordFile="+recordFile);
        	File file = new File(recordFile);
        	if(!file.exists()){
        		rlAudioPlay.setVisibility(View.GONE);
            	tv_audio_play.setVisibility(View.GONE);
        	}
        }
        
        if(!photoFile.equals("")){
        	 Log.e("", "photoFile="+photoFile);
        	 imageLoader = ImageLoader.getInstance();
        	 imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        	
        	 imageUrls = photoFile.split(",");
        	 options = new DisplayImageOptions.Builder()
     		.showStubImage(R.drawable.ic_stub)
     		.showImageForEmptyUri(R.drawable.ic_empty)
     		.showImageOnFail(R.drawable.ic_error)
     		.cacheInMemory(true)
     		.cacheOnDisc(true)
     		.bitmapConfig(Bitmap.Config.RGB_565)
     		.build();

        	 
     		// 自API 16之后就被抛弃了
     		Gallery gallery = (Gallery) findViewById(R.id.g_historydetail_gallery);
     		gallery.setAdapter(new ImageGalleryAdapter());
     		gallery.setOnItemClickListener(new OnItemClickListener() {
     			@Override
     			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//     				startImagePagerActivity(position);

					OrderActivity.instance.showImg(imageUrls[position]);

     			}
     		});
        }else{
        	tv_historydetail_gallery.setVisibility(View.GONE);
        }
    }
    class ButtonListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (mediaPlayer.isPlaying()) {
            	button.setBackgroundResource(R.drawable.playaudio);
                mediaPlayer.pause();
            }
            else {
                try {
                	if(!isPrepared){
	                	button.setBackgroundResource(R.drawable.playaudio_over);
	                    mediaPlayer.reset();
	                    mediaPlayer.setDataSource(recordFile);
	                    mediaPlayer.prepare();
	                    mediaPlayer.start();
	                    StrartbarUpdate();
	                    int Alltime= mediaPlayer.getDuration();
	                    allTime.setText(ShowTime(Alltime));
	                    isPrepared = true;
                	}else{
                		button.setBackgroundResource(R.drawable.playaudio_over);
                		mediaPlayer.start();
                	}
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            }            
    }

//播放进度条

    class ProcessBarListener implements OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser==true) {
                mediaPlayer.seekTo(progress);
                nowPlayTime.setText(ShowTime(progress));
            }
            if(nowPlayTime.getText().equals(allTime.getText())){
            	button.setBackgroundResource(R.drawable.playaudio);
            }
            
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            
        }
        

    }

//音量进度条

    class SeekBarListener implements OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {
            // TODO Auto-generated method stub
            if (fromUser) {
                int SeekPosition=seekBar.getProgress();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, SeekPosition, 0);
            }
//          volumeView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            
        }
        
    }
    //时间显示函数,我们获得音乐信息的是以毫秒为单位的，把把转换成我们熟悉的00:00格式
    public String ShowTime(int time){
        time/=1000;
        int minute=time/60;
        int hour=minute/60;
        int second=time%60;
        minute%=60;
        return String.format("%02d:%02d", minute, second);
    }
    Handler handler=new Handler();
    public void StrartbarUpdate(){
        handler.post(r);
    }
    Runnable r=new Runnable() {
        
        @Override
        public void run() {
            // TODO Auto-generated method stub
            int CurrentPosition=mediaPlayer.getCurrentPosition();
            nowPlayTime.setText(ShowTime(CurrentPosition));
            int mMax=mediaPlayer.getDuration();
            ProceseekBar2.setMax(mMax);
            ProceseekBar2.setProgress(CurrentPosition);
            handler.postDelayed(r, 100);
        }
    };
    
//    private void startImagePagerActivity(int position) {
//		Intent intent = new Intent(this, ImageDetailActivity.class);
//		intent.putExtra("imgUrl", imageUrls[position]);
//		startActivity(intent);
//	}

	private class ImageGalleryAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_gallery_image, parent, false);
			}
			Bitmap loacalBitmap = BaseUntil.getLoacalBitmap(imageUrls[position]);
			imageView.setImageBitmap(loacalBitmap);
			//imageLoader.displayImage(imageUrls[position], imageView, options);
			return imageView;
		}
	}
	
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK ){
			if(mediaPlayer!=null && mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
