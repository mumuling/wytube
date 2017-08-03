package com.cqxb.yecall.until;

import java.io.FileInputStream;

import com.cqxb.yecall.R;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class NewInfoNoticeUtil {
	private Context mContext;
	
	public NewInfoNoticeUtil(Context context){
		if(mContext==null){
			mContext=context;
		}
	}
	
	private MediaPlayer mediaPlayer;
	public void playVoice(){
		 try {
			 
			 AudioManager audioService = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE); 
			 if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
				 //如果当前是铃音模式，则继续准备下面的 蜂鸣提示音操作，如果是静音或者震动模式。就不要继续了
				 return;
			 } 
			 if(mediaPlayer==null){
				 mediaPlayer=new MediaPlayer(); 
			 }
			 //注册的默认 音频通道 
			 mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			 
			 AssetFileDescriptor file = mContext.getResources().openRawResourceFd(  
	                    R.raw.dingdong);  
			 mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(), file.getLength());
			 file.close();
			 
//			 String defaultVoicePath="";
//			 FileInputStream fis = new FileInputStream(defaultVoicePath);
//             mediaPlayer.setDataSource(fis.getFD());
//             fis.close();
             mediaPlayer.prepare();
             mediaPlayer.start();
             mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                 @Override
                 public void onCompletion(MediaPlayer mp) {
                     mediaPlayer.stop();
                     mediaPlayer.release();
                 }
             });
		} catch (Exception e) {
			e.printStackTrace();
			mediaPlayer.stop();
		}
	}
	
	
	private Vibrator vibrator;
	public void vibrate(){
		try {
			if(vibrator==null){
				vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
			}
			//震动一次 
			vibrator.vibrate(500); 
		} catch (Exception e) {
			e.printStackTrace();
			vibrator.cancel();
		}
		
	}
	
}
