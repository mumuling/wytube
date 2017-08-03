package com.cqxb.yecall.until;

import org.linphone.compatibility.Compatibility;

import com.cqxb.yecall.YETApplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationUtil {
	private PendingIntent mNotifContentIntent;
	private Notification mNotif;
	NotificationManager notificationManager;
	
	
	/**
	 * 
	 * @param flag  通知的标识
	 * @param context
	 * @param incomingReceivedActivity  类
	 * @param smallIcon  图标
	 * @param title  主题
	 */
	public void setNotification(int flag,Class<? extends Activity> incomingReceivedActivity ,int smallIcon,String title,String context) {
		if(notificationManager==null){
			notificationManager = (NotificationManager) YETApplication.getContext().getSystemService(android.content.Context.NOTIFICATION_SERVICE); 
		}
		mNotif = new Notification();
		mNotif.icon=smallIcon;
		mNotif.when = System.currentTimeMillis();
		mNotif.iconLevel=0;
		//FLAG_NO_CLEAR 不会清除
		mNotif.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中   
		mNotif.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用   
		mNotif.flags |= Notification.FLAG_SHOW_LIGHTS;
//        Intent notifIntent = new Intent(context, incomingReceivedActivity);
		Intent notifIntent = new Intent(Intent.ACTION_MAIN);
		notifIntent.setClass(YETApplication.getContext(), incomingReceivedActivity);
        notifIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mNotifContentIntent = PendingIntent.getActivity(YETApplication.getContext(), 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		mNotif.setLatestEventInfo(context, title, "", mNotifContentIntent);

//		Compatibility.setNotificationLatestEventInfo(mNotif, YETApplication.getContext(), title, context, mNotifContentIntent);
		notificationManager.notify(flag,mNotif);
		//mNotif.setAutoCancel(true);//点击消失
		//mNotif.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));//这句和点击消失那句是“Notification点击消失但不会跳转”的必须条件，如果只有点击消失那句，这个功能是不能实现的
    
	}
	
	public void addNotification(int flag,Notification mNotif){
		if(notificationManager==null){
			notificationManager = (NotificationManager) YETApplication.getContext().getSystemService(android.content.Context.NOTIFICATION_SERVICE); 
		}
		notificationManager.notify(flag,mNotif);
	}
	
	/**
	 * 
	 * @param context
	 * @param flag  删除的标识与通知的标识一致
	 */
	public void clearNotification(Context context,int flag){
        // 启动后删除之前我们定义的通知   
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(android.content.Context.NOTIFICATION_SERVICE);   
        notificationManager.cancel(flag);  
  
    }
}
