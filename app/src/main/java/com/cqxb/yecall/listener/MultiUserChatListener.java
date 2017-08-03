package com.cqxb.yecall.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.cqxb.yecall.Smack;


import android.content.Context;
import android.util.Log;

public class MultiUserChatListener {
	
	public static XMPPConnection conn;
	private MultiUserChat muc = null;
	private static MultiUserChatListener multiUserChatListener;
	public static Context mContext;
	
	public static MultiUserChatListener getInistance( ){
		if(multiUserChatListener==null){
			multiUserChatListener=new MultiUserChatListener();
		}
		return multiUserChatListener;
	}
	
	public static void initContext(Context c){
		if(mContext==null){
			mContext=c;
		}
	}
	
	/**
	 * 初始化连接
	 * @param mConn
	 */
	public void getInistanceConn(XMPPConnection mConn){
		if(conn==null){
			conn=mConn;
		}
	}
	
	/**
	 * 创建房间
	 * @param roomName
	 *            房间名称
	 */

	public MultiUserChat createRoom(String user, String roomName,
			String password) {
		if (conn == null)
			return null;
		try {
			System.out.println("roomName:"+roomName+" "+user+" "+password+" "+conn.getServiceName());
			System.out.println("roomName:"+(conn==null?"连接有问题":"连接没问题"));
			// 创建一个MultiUserChat
			muc = new MultiUserChat(conn, roomName + "@conference."+conn.getServiceName());
			System.out.println("roomName:"+(muc==null?"房间有问题":"房间没问题"));
			// 创建聊天室
			muc.create(roomName);
			// 获得聊天室的配置表单
			Form form = muc.getConfigurationForm();
			// 根据原始表单创建一个要提交的新表单。
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复
			for (Iterator<FormField> fields = form.getFields(); fields
			.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType())
				&& field.getVariable() != null) {
					// 设置默认值作为答复
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}
			// 设置聊天室的新拥有者
			List<String> owners = new ArrayList<String>();
			owners.add(user);// 用户JID
			submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			// 设置聊天室是持久聊天室，即将要被保存下来
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			// 房间仅对成员开放
			submitForm.setAnswer("muc#roomconfig_membersonly", false);
			// 允许占有者邀请其他人
			submitForm.setAnswer("muc#roomconfig_allowinvites", true);
			if (!password.equals("")) {
				// 进入是否需要密码
				submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",
				true);
				// 设置进入密码
				submitForm.setAnswer("muc#roomconfig_roomsecret", password);
			}
			// 能够发现占有者真实 JID 的角色
			// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
			// 登录房间对话
			submitForm.setAnswer("muc#roomconfig_enablelogging", true);
			// 仅允许注册的昵称登录
			submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
			// 允许使用者修改昵称
			submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
			// 允许用户注册房间
			submitForm.setAnswer("x-muc#roomconfig_registration", false);
			// 发送已完成的表单（有默认值）到服务器来配置聊天室
			muc.sendConfigurationForm(submitForm);
			Log.i("创建会议室", "创建会议室 成功");
		} catch (XMPPException e) {
			Log.i("创建会议室", "创建会议室 失败"+e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
		return muc;
	}
	
	
	
	/**
	 * 加入会议室
	 * @param user
	 *            昵称
	 * @param password
	 *            会议室密码
	 * @param roomsName
	 *            会议室名
	 */
	 
	public MultiUserChat joinMultiUserChat(String user,String roomName,   
	        String password,DiscussionHistory history) {  
		Log.i("收到来自", "收到来自   joinMultiUserChat    1111");
		Log.i("收到来自", "收到来自   joinMultiUserChat    "+roomName);
		Log.i("收到来自", "收到来自   joinMultiUserChat    222");
		Log.i("收到来自", "收到来自   "+(conn==null?"  conn is null ":"  conn is conn"));
	    if (conn == null)  
	        return null;  
	    try {  
	        // 使用XMPPConnection创建一个MultiUserChat窗口  
	        muc = new MultiUserChat(Smack.conn, roomName);  
	        // 聊天室服务将会决定要接受的历史记录数量  
	        DiscussionHistory his = new DiscussionHistory();  
	        his.setMaxChars(0);  
	       //his.setSince(new Date());  
	        // 用户加入聊天室  
//	        if(user!=null&&password==null)
//	        muc.join(user);
//	        if(user!=null&&password!=null&&history==null)
//	        muc.join(user, password);
//	        if(user!=null&&password!=null&&history!=null)
	        muc.join(user, password, null,  SmackConfiguration.getPacketReplyTimeout());  
	        
	        Log.i("MultiUserChat", "会议室【"+roomName+"】加入成功........");  
	        return muc;  
	    } catch (XMPPException e) {  
	        e.printStackTrace();  
	        Log.i("MultiUserChat", "会议室【"+roomName+"】加入失败........"+e.getLocalizedMessage());  
	        return null;  
	    }  
	}  
}
