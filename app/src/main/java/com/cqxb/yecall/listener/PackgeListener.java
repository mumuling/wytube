package com.cqxb.yecall.listener;

import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.VCard;

import com.cqxb.yecall.Smack;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.XmlBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.TimeRender;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


public class PackgeListener implements PacketListener{

	private static PackgeListener packgeListener;
	private static  XMPPConnection conn;
	private static Context context;
	public static final String action = "jason.broadcast.action";  
	private DBHelper dbHelper;
	public static void getInstenceContext(Context c){
		if(context==null){
			context=c;
		}
	}
	
	public void initDB(DBHelper helper){
		dbHelper=helper;
	}
	
	public static PackgeListener getInstance(){
		if(packgeListener==null){
			packgeListener=new PackgeListener();
		}
		return packgeListener;
	}
	
	public PacketFilter getFilter(){
		PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class)); 
		return filter;
	}
	
	
	@Override
	public void processPacket(Packet packet) {
		
		if (packet instanceof Presence) {  
			 Presence presence = (Presence) packet;
			 
			 if (presence.getType().equals(Presence.Type.subscribe)) {//好友申请  
				 
			 }else if (presence.getType().equals(Presence.Type.unsubscribed)){
				 
			 }else if (presence.getType().equals(Presence.Type.unavailable)) {//好友下线   要更新好友列表，可以在这收到包后，发广播到指定页面   更新列表
				
			 }else if (presence.getType().equals(Presence.Type.available)) {//好友上线  
				
			 }
		}else if(packet instanceof Message){
			try {
				dbHelper.open();
				//群聊接收消息
				Message message=(Message)packet;
				System.out.println("myJid registerMessageListener : "+message.toXML());
				String roomId=message.getFrom();//房间id
				String jid="";
				if(roomId.indexOf("/")!=-1){
					roomId=message.getFrom().split("/")[0];
					jid=message.getFrom().split("/")[1];
				}
				
				System.out.println("==========jid=============="+jid);
				String myJid=jid;
				if(myJid.indexOf("/")!=-1){
					myJid=myJid.substring(0, myJid.indexOf("/"));
					if(myJid.indexOf("@")==-1){
						myJid=myJid+"@"+Smack.conn.getServiceName();
					}
				}else {
					if(myJid.indexOf("@")==-1){
						myJid=myJid+"@"+Smack.conn.getServiceName();
					}
				}
				
				
				String params = SettingInfo.getParams(PreferenceBean.USERACCOUNT,"");
				System.out.println("==========myJid=============="+myJid+"       "+params);
				try {
					if(myJid.equals(params)){
						return;
					}
				} catch (Exception e) {
					return;
				}
				
				
				String msg=message.getBody();
				List<XmlBean> xml = BaseUntil.getMsg(msg);
				msg = BaseUntil.getListString(xml);
				
			
				
	            long ts;// 消息时间戳
	            DelayInfo timestamp = (DelayInfo) message.getExtension(
	                    "delay", "urn:xmpp:delay");
	            if (timestamp == null)
	                timestamp = (DelayInfo) message.getExtension("x",
	                        "jabber:x:delay");
	            if (timestamp != null)
	                ts = timestamp.getStamp().getTime();
	            else
	                ts = System.currentTimeMillis();

	            String longToDate = TimeRender.longToDate(ts,"yyyy-MM-dd HH:mm:ss");
	            VCard vcard = new BaseUntil().getVcard(jid+"@"+Smack.conn.getServiceName());
				String nickName=getNickName(jid);
				if(vcard!=null){
					if(TextUtils.isEmpty(vcard.getNickName())){
						nickName=vcard.getNickName();
					}
				}
				
				//添加到群组聊天记录
				ContentValues contentValues=new ContentValues();
				contentValues.put(GroupChatEntity.ROOMID, roomId);
				contentValues.put(GroupChatEntity.FRIENDID, jid);
				contentValues.put(GroupChatEntity.CONTEXT, msg);
				contentValues.put(GroupChatEntity.WHO, GroupChatEntity.IN);
				contentValues.put(GroupChatEntity.ISREAD, GroupChatEntity.IS_NOT_READ);
				contentValues.put(GroupChatEntity.NICKNAME, nickName);
				contentValues.put(GroupChatEntity.MSGDATE, longToDate);
				contentValues.put(GroupChatEntity.GID, myJid);
				dbHelper.insertData(GroupChatEntity.TABLE, null, contentValues);
				
				//添加到消息列表
				ContentValues initialValues=new ContentValues();
				initialValues.put(InformationList.ROOMID, roomId);
				initialValues.put(InformationList.FRIENDID, jid);
				initialValues.put(InformationList.NICKNAME,nickName);
				initialValues.put(InformationList.CONTEXT, msg);
				initialValues.put(InformationList.MSGDATE, longToDate);
				initialValues.put(InformationList.GID, myJid);
				initialValues.put(InformationList.FLAG, "4");
				if(!dbHelper.updateData(InformationList.TABLE, initialValues, InformationList.ROOMID+"=?", new String[]{roomId})){
					dbHelper.insertData(InformationList.TABLE, null, initialValues);
				}
				
				Intent intent=new Intent(action);
	            intent.putExtra("newInfoFlag", "newGroupInfo");  //消息
	            context.sendBroadcast(intent);
	            System.out.println("群组消息发送成功");
	            dbHelper.close();
			} catch (Exception e) {
				e.printStackTrace();
				Log.w("PackgeListener", "PackgeListener "+e.getLocalizedMessage());
				dbHelper.close();
			}
		}
	}
	
	public String getNickName(String name){
    	if(name.indexOf("@")!=-1){
    		return name.substring(0, name.indexOf("@"));
    	}
    	return name;
    }
}
