package com.cqxb.yecall.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.VCard;

import com.cqxb.yecall.Smack;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.bean.XmlBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.TimeRender;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ChatListener  implements ChatManagerListener {
	private static XMPPConnection connection=null;
	public static ChatListener chatListener;
	private static Context context;
	public static final String action = "jason.broadcast.action";  
	private String TAG="ChatListener"; 
	private DBHelper dbHelper;
	
	public static void getInstenceContext(Context c){
		if(context==null){
			context=c;
		}
	}
	
	public void getDbhelper(DBHelper helper){
		dbHelper=helper;
	}
	
	//获取当前的登陆用户
	public String getUser(){
		return connection.getUser();
	}

	public static ChatListener getInstance(){
		if(chatListener==null){
			chatListener=new ChatListener();
		}
		return chatListener;
	}
	
	
	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		//添加消息监听事件
		MessageListener messageListener = new MessageListener() {

			@Override
			public void processMessage(Chat arg0, Message msg) {

				try {
					connection=Smack.conn;
					System.out.println(" 类型  : " + msg.getType());
					System.out.println(" registerChatListener:   "
							+ msg.getBody());
					// 收到的消息
					Log.i(TAG, "carbon 消息前: " + msg.toXML());
					String chatMessage = msg.getBody();

					// 转换消息格式
					List<XmlBean> xml = BaseUntil.getMsg(chatMessage);
					chatMessage = BaseUntil.getListString(xml);
					Log.i(TAG, "carbon 消息后: " + chatMessage);
					// fall through
					long ts;// 消息时间戳
					DelayInfo timestamp = (DelayInfo) msg.getExtension("delay",
							"urn:xmpp:delay");
					if (timestamp == null)
						timestamp = (DelayInfo) msg.getExtension("x",
								"jabber:x:delay");
					if (timestamp != null)
						ts = timestamp.getStamp().getTime();
					else
						ts = System.currentTimeMillis();

					String longToDate = TimeRender.longToDate(ts,
							"yyyy-MM-dd HH:mm:ss");

					String fromJID = getJabberID(msg.getFrom());// 消息来自对象

					String myJid = getJabberID(connection.getUser());

					VCard vcard = new BaseUntil().getVcard(fromJID);
					String nickName = "";
					if (vcard != null) {
						nickName = vcard.getNickName();
					} else {
						nickName = getNickName(fromJID);
					}

					// 添加到聊天记录
					ContentValues values = new ContentValues();

			        values.put(SingleChatEntity.NICKNAME, nickName);
			        values.put(SingleChatEntity.FRIENDID, fromJID);
			        values.put(SingleChatEntity.CONTEXT, chatMessage);
			        values.put(SingleChatEntity.ISREAD, SingleChatEntity.IS_NOT_READ);
			        values.put(SingleChatEntity.MSGDATE, longToDate);
			        values.put(SingleChatEntity.USERID, myJid);
			        values.put(SingleChatEntity.WHO, SingleChatEntity.IN);

			        dbHelper.insertData(SingleChatEntity.TABLE, null, values);
					
					
					// 添加到消息列表
					ContentValues initialValues = new ContentValues();
					initialValues.put(InformationList.FRIENDID, fromJID);

					if (vcard != null) {
						initialValues.put(InformationList.NICKNAME,
								vcard.getNickName());
					} else {
						initialValues.put(InformationList.NICKNAME,
								getNickName(fromJID));
					}
					initialValues.put(InformationList.CONTEXT, chatMessage);
					initialValues.put(InformationList.MSGDATE, longToDate);
					initialValues.put(InformationList.FLAG, "2");
					initialValues.put(InformationList.GID, myJid);
					if (!dbHelper.updateData(InformationList.TABLE,
							initialValues, InformationList.FRIENDID + " = ?",
							new String[] { fromJID })) {
						dbHelper.insertData(InformationList.TABLE, null,
								initialValues);
					}
					Intent intent = new Intent(action);
					intent.putExtra("newInfoFlag", "newInfo"); // 消息
					context.sendBroadcast(intent);
					System.out.println("数据发送成功！");
				} catch (Exception e) {
					Log.w(TAG,
							"registerChatListener is failed "
									+ e.getLocalizedMessage());
				}

			}
			
		};
		chat.addMessageListener(messageListener);
	}
	
	/**
	 * 获取jig
	 * @param from
	 * @return
	 */
	private String getJabberID(String from) {
        String[] res = from.split("/");
        return res[0].toLowerCase();
    }
	
	
	public String getNickName(String name){
    	if(name.indexOf("@")!=-1){
    		return name.substring(0, name.indexOf("@"));
    	}
    	return name;
    }
}
