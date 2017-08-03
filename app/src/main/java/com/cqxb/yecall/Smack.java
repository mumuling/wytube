package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.DelayInfo;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.linphone.LinphoneManager;
import org.linphone.setup.SetupActivity;

import com.cqxb.yecall.bean.ContactEntity;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.bean.XmlBean;
import com.cqxb.yecall.carbons.Carbon;
import com.cqxb.yecall.carbons.CarbonManager;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.listener.ChatListener;
import com.cqxb.yecall.listener.PackgeListener;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.NewInfoNoticeUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.StatusMode;
import com.cqxb.yecall.until.TimeRender;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.Toast;

public class Smack {
//	private String YETIP="euc.yuneasy.cn";
	private String YETIP="10.1.20.11";
	private Integer YETPORT=5222;
	private String YETDOMAIN="yuneasy.cn";
	public static XMPPConnection conn;
	public static Smack instance;
	private String TAG="Smack";
	// 安装openfire服务时，需要配置ip,  如果配置主机名时，需要@主机名才能发消息
    public static String XMPP_SERVICE_NAME;
    private Roster mRoster;// 联系人对象
    private RosterListener mRosterListener;// 联系人动态监听
    private PacketListener mPacketListener;// 消息动态监听
    public static DBHelper dbHelper;
    public static Context mContext;
    public static final String action = "jason.broadcast.action"; 
    private PacketListener mSendFailureListener;// 消息发送失败动态监听
    private ChatManagerListener mChatManagerListener;
    private InvitationListener invitationListener;
    private Timer tExit;
    private int logintime = 2000;  
    
    
    
	/**
	 * 初始化 Smack
	 * @return
	 */
	public static synchronized Smack getInstance(){
		if(instance==null){
			instance=new Smack();
		}
		return instance;
	}
	
	
	public void initContext(Context context){
		mContext=context;
	}
	
	/**
	 * 创立xmpp连接
	 * @return
	 */
	public XMPPConnection getConnection(){
		if(conn==null){
			SettingInfo.init(mContext);
			YETIP = SettingInfo.getParams(PreferenceBean.USERIMIP, "");
			String port = SettingInfo.getParams(PreferenceBean.USERIMPORT, "");
			YETPORT=Integer.parseInt(port);
			YETDOMAIN = SettingInfo.getParams(PreferenceBean.USERIMDOMAIN, "");
			Log.e(TAG, "smack - ip:"+YETIP+"  port:"+YETPORT+"  domain:"+YETDOMAIN);
			ConnectionConfiguration config=new ConnectionConfiguration(YETIP, YETPORT, YETDOMAIN);
//			ConnectionConfiguration config=new ConnectionConfiguration(YETIP, YETPORT);
			config.setSASLAuthenticationEnabled(true);
			config.setReconnectionAllowed(true);
			conn =new XMPPConnection(config);
			
			// 收到好友邀请后manual表示需要经过同意,accept_all表示不经同意自动为好友
			Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
			ProviderManager pm = ProviderManager.getInstance();
			configure(pm);
			//com.dx.sunday.Extras.hostIp = SERVER_DOMAIN;
		}
		return conn;
	}
	
	
	/**
	 * 关闭连接
	 */
	public void closeConnection(){
		if(conn!=null){
			conn.disconnect();
			conn = null;
		}
		Log.i(TAG, conn==null?"xmpp 连接已关闭":"xmpp 连接未关闭");
	}
	
	
	/**
	 * xmpp 登陆
	 * @param account  账号
	 * @param password  密码
	 * @param client  客户端名称
	 * @return
	 */
	public boolean login(String account,String password,String client){
		try {
			if(conn.isConnected()){
				try {
//					conn.disconnect();
				} catch (Exception e) {
					Log.w(TAG, "xmpp disconnect faild");
				}
			}
			if(dbHelper!=null)
				dbHelper.close();
			dbHelper=new DBHelper(mContext);
//			SmackConfiguration.setPacketReplyTimeout(30000);// 设置超时时间
//			SmackConfiguration.setKeepAliveInterval(-1);
//	        SmackConfiguration.setDefaultPingInterval(0);
			
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
					// TODO Auto-generated method stub
					registerRosterListener();// 监听联系人动态变化
//				}
//			}).start();
			
			
			try {
				if(conn==null){
					return false;
				}
				conn.connect();
			} catch (XMPPException e) {
				Log.w(TAG, "xmpp login is faild"+e.getMessage());
			}
			
			//监听连接
			conn.addConnectionListener(new ConnectionListener() {
				public void connectionClosedOnError(Exception e) {
					// mService.postConnectionFailed(e.getMessage());//
					// 连接关闭时，动态反馈给服务
					boolean error = e.getMessage().equals("stream:error (conflict)");
					if (!error) {
						// 關閉連接
						removeListener();
						// 重连服务器
						tExit = new Timer();
						tExit.schedule(new timetask(), logintime);
					}

				}

	            public void connectionClosed() {
//	            	removeListener();
//	            	tExit = new Timer();
//	            	tExit.schedule(new timetask(), logintime);
	            }

	            public void reconnectingIn(int seconds) {
	            }

	            public void reconnectionFailed(Exception e) {
	            }

	            public void reconnectionSuccessful() {
	            }
	        });
			
//			initServiceDiscovery();// 与服务器交互消息监听,发送消息需要回执，判断是否发送成功
			
			// SMACK auto-logins if we were authenticated before
	        if (!conn.isAuthenticated()) {
//	            String ressource = PreferenceUtils.getPrefString(mService,PreferenceConstants.RESSOURCE, XMPP_IDENTITY_NAME);
	            Log.i(TAG, "====== account: "+account+" password: "+password);
	            conn.login(account, password, client);
	            Log.i(TAG,"====== mXMPPConnection serviceName: "+conn.getServiceName());
	            XMPP_SERVICE_NAME = conn.getServiceName();
	        }
//	        setStatusFromConfig();// 更新在线状态
	        
	        
			registerAllListener();// 注册监听其他的事件，比如新消息
			
			//ChatListener.getInstenceContext(mContext);
			//ChatListener chatListener = ChatListener.getInstance();
			//chatListener.getDbhelper(dbHelper);
			//conn.getChatManager().addChatListener(chatListener);
			
			//会议室加上监听
			PackgeListener.getInstenceContext(mContext);		
			PackgeListener.getInstance().initDB(dbHelper);
			
	        if (conn.isAuthenticated()) {
//	            registerGroupListener(); //监听群组变化
//	        	logLinphone("liuyiyi", "123456");
	        }
		} catch (XMPPException e) {
            Log.e(TAG,"XMPPException "+e.getLocalizedMessage(),
                    e.getWrappedThrowable());
        } catch (Exception e) {
            // actually we just care for IllegalState or NullPointer or XMPPEx.
        	Log.e(TAG, "login : " + Log.getStackTraceString(e));
        }
		

		
		return conn.isAuthenticated();
	}
	
	
	
	private void logLinphone(String username, String password) {
		
		SetupActivity.instance().linphoneLogIn(username, password,false);
	}
	
	
	
	
	 /**
     * 注册所有的监听
     */
    private void registerAllListener() {
    	try {
    		// actually, authenticated must be true now, or an exception must have
            // been thrown.
            if (isAuthenticated()) {
//                registerMessageListener();// 注册新消息监听
                registerChatListener();
                System.out.println("registerChatListener   init   ok!");
                registerPresenceListener();
                System.out.println("registerPresenceListener   init   ok!");
                chatRoom();
                System.out.println("chatRoom   init   ok!");
//                registerMessageSendFailureListener();// 注册消息发送失败监听
//                registerPongListener();// 注册服务器回应ping消息监听
//                sendOfflineMessages();// 发送离线消息
//                if (mService == null) {
//                    mXMPPConnection.disconnect();
//                    return;
//                }
                // we need to "ping" the service to let it know we are actually
                // connected, even when no roster entries will come in
//                mService.rosterChanged();
            }
		} catch (Exception e) {
			Log.e(TAG, TAG+"  registerAllListener is init failed "+ e.getLocalizedMessage());
        }
    }
	
   
    /**
     * ************** start 处理消息发送失败状态 **********************
     */
    /*
    private void registerMessageSendFailureListener() {
        // do not register multiple packet listeners
        if (mSendFailureListener != null)
            conn.removePacketSendFailureListener(mSendFailureListener);

        PacketTypeFilter filter = new PacketTypeFilter(Message.class);

        mSendFailureListener = new PacketListener() {
            public void processPacket(Packet packet) {
                try {
                    if (packet instanceof Message) {
                        Message msg = (Message) packet;
                        String chatMessage = msg.getBody();

                        Log.d("SmackableImp",
                                "message "
                                        + chatMessage
                                        + " could not be sent (ID:"
                                        + (msg.getPacketID() == null ? "null"
                                        : msg.getPacketID()) + ")");
                        changeMessageDeliveryStatus(msg.getPacketID(),
                                ChatConstants.DS_NEW);// 当消息发送失败时，将此消息标记为新消息，下次再发送
                    }
                } catch (Exception e) {
                    // SMACK silently discards exceptions dropped from
                    // processPacket :(
                    L.e("failed to process packet:");
                    e.printStackTrace();
                }
            }
        };

        conn.addPacketSendFailureListener(mSendFailureListener,
                filter);// 这句也是关键啦！
    }
   
    */
    
    /**
     * ********* start 新消息处理 *******************
     */
    private void registerMessageListener() {
//    	System.out.println("carbon  ********* start 新消息处理 *******************");
        // do not register multiple packet listeners
        if (mPacketListener != null)
            conn.removePacketListener(mPacketListener);

//        PacketTypeFilter filter = new PacketTypeFilter(Message.class);
        PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
        mPacketListener = new PacketListener() {
            public void processPacket(Packet packet) {
                try {
					if (packet instanceof Message) {
						//群聊接收消息
						Message message=(Message)packet;
						String jid=message.getFrom();//房间id
						String msg=message.getBody();
						List<XmlBean> xml = BaseUntil.getMsg(msg);
						msg = BaseUntil.getListString(xml);
						
//						System.out.println("registerMessageListener : "+msg);
						
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
						
						
						//添加到群组聊天记录
						ContentValues contentValues=new ContentValues();
						contentValues.put(GroupChatEntity.ROOMID, jid);
						contentValues.put(GroupChatEntity.FRIENDID, jid);
						contentValues.put(GroupChatEntity.CONTEXT, msg);
						contentValues.put(GroupChatEntity.WHO, GroupChatEntity.IN);
						contentValues.put(GroupChatEntity.ISREAD, GroupChatEntity.IS_NOT_READ);
						contentValues.put(GroupChatEntity.NICKNAME, "");
						contentValues.put(GroupChatEntity.MSGDATE, longToDate);
						dbHelper.insertData(GroupChatEntity.TABLE, null, contentValues);
						
						
						ContentValues initialValues=new ContentValues();
        				initialValues.put(InformationList.FRIENDID, jid);
        				initialValues.put(InformationList.NICKNAME,"");
        				initialValues.put(InformationList.CONTEXT, msg);
        				initialValues.put(InformationList.MSGDATE, longToDate);
        				initialValues.put(InformationList.FLAG, "4");
						dbHelper.insertData(InformationList.TABLE, null, initialValues);
						
						Intent intent=new Intent(action);
                        intent.putExtra("newInfoFlag", "newInfo");  //消息
                        mContext.sendBroadcast(intent);
					}
                } catch (Exception e) {
                    // SMACK silently discards exceptions dropped from
                    // processPacket :(
                    Log.i(TAG,"registerMessageListener failed to process packet:");
                    e.printStackTrace();
                }
            }
        };

        conn.addPacketListener(mPacketListener, filter);// 这是最关健的了，少了这句，前面的都是白费功夫
    }
    
    
    
    
    
    /*
     * ********* start 新消息处理 *******************
     */
    private void registerChatListener() {
//    	System.out.println("carbon  ********* start 新消息处理 *******************");
        // do not register multiple packet listeners
    	
        if (mChatManagerListener != null||conn.getChatManager().getChatListeners()!=null)
            conn.getChatManager().removeChatListener(mChatManagerListener);
        
//        System.out.println(" carbon:  "+(mContext==null?"null":"not null"));
        mChatManagerListener = new ChatManagerListener() {
 		
 		@Override
 		public void chatCreated(Chat chat, boolean bool) {
// 			System.out.println(" carbon chatCreated :  "+(mContext==null?"null":"not null"));
 			chat.addMessageListener(new MessageListener() {
 				
 				@Override
 				public void processMessage(Chat arg0, Message msg) {
 					try {
// 						System.out.println(" carbon:  chat "+(mContext==null?"null":"not null"));
// 						System.out.println(" 类型  : "+msg.getType());
//						System.out.println(" registerChatListener:   "+msg.getBody());
 					// 收到的消息
//                        Log.i(TAG,"carbon 消息前: " + msg.toXML());
                        String chatMessage = msg.getBody();
                        if(TextUtils.isEmpty(chatMessage)){
                        	return;
                        }
                      //转换消息格式
                        List<XmlBean> xml = BaseUntil.getMsg(chatMessage);
                        chatMessage = BaseUntil.getListString(xml);
//                        Log.i(TAG,"carbon 消息后: " + chatMessage);
                        // fall through
                        long ts;// 消息时间戳
                        DelayInfo timestamp = (DelayInfo) msg.getExtension(
                                "delay", "urn:xmpp:delay");
                        if (timestamp == null)
                            timestamp = (DelayInfo) msg.getExtension("x",
                                    "jabber:x:delay");
                        if (timestamp != null)
                            ts = timestamp.getStamp().getTime();
                        else
                            ts = System.currentTimeMillis();

                        String longToDate = TimeRender.longToDate(ts,"yyyy-MM-dd HH:mm:ss");
                        
                        String fromJID = getJabberID(msg.getFrom());// 消息来自对象
                        
                        String myJid = getJabberID(conn.getUser());
 						
                        VCard vcard = new BaseUntil().getVcard(fromJID);
                        
                        String nickName="";
        				if(vcard!=null){
        					nickName=vcard.getNickName();
        				}else {
        					nickName=getNickName(fromJID);
        				}
                        
        				if(TextUtils.isEmpty(nickName)){
        					nickName=getNickName(fromJID);
        				}
        				dbHelper.open();
                        //添加到聊天记录
                        addChatMessageToDB(nickName, fromJID, chatMessage, SingleChatEntity.IS_NOT_READ, longToDate, myJid,SingleChatEntity.IN );
                        //添加到消息列表
        				ContentValues initialValues=new ContentValues();
        				initialValues.put(InformationList.FRIENDID, fromJID);
        				initialValues.put(InformationList.NICKNAME,nickName);
        				initialValues.put(InformationList.CONTEXT, chatMessage);
        				initialValues.put(InformationList.MSGDATE, longToDate);
        				initialValues.put(InformationList.GID, myJid);
        				initialValues.put(InformationList.FLAG, "2");
    					if(!dbHelper.updateData(InformationList.TABLE, initialValues, InformationList.FRIENDID+" = ? and "+InformationList.GID+" = ? ", new String[]{fromJID,myJid})){
        					dbHelper.insertData(InformationList.TABLE, null, initialValues);
        				}
        				dbHelper.close();
        				Intent intent=new Intent(action);
                        intent.putExtra("newInfoFlag", "newInfo");  //消息
                        mContext.sendBroadcast(intent);
                        
                        String playVoice = SettingInfo.getParams(PreferenceBean.PLAYVOICE, "");
    					String playVibrate = SettingInfo.getParams(PreferenceBean.PLAYVIBRATE, "");
    					if(!"".equals(playVoice)){
      					   try{
      	                        	new NewInfoNoticeUtil(mContext).playVoice();
      						  } catch (Exception e) {
      								e.printStackTrace();
      						  }
       					}
       					if(!"".equals(playVibrate)){
 						   try{
 	                        		new NewInfoNoticeUtil(mContext).vibrate();
 							  } catch (Exception e) {
 								e.printStackTrace();
 							  }
      					 }
//                        System.out.println("数据发送成功！");
 					} catch (Exception e) {
						Log.w(TAG, "registerChatListener is failed "+e.getLocalizedMessage());
						dbHelper.close();
					}
 				}
 			});
 			
 		}
 	};
// 		System.out.println(" carbon  =====:  "+(mContext==null?"null":"not null"));
     	conn.getChatManager().addChatListener(mChatManagerListener);
    }
    
    
    
    
    
    
    
    
    
    /*
    * ********* start 新消息处理 *******************
    */
   private void registerPresenceListener() {
//   	System.out.println("carbon  ********* start 新消息处理 *******************");
       // do not register multiple packet listeners
       if (mPacketListener != null)
           conn.removePacketListener(mPacketListener);

//       PacketTypeFilter filter = new PacketTypeFilter(Message.class);
       PacketFilter filter = new AndFilter(new PacketTypeFilter(Presence.class)); 
       mPacketListener = new PacketListener() {
           public void processPacket(Packet packet) {
               try {
					if (packet instanceof Presence) {
						dbHelper.open();
						Presence presence = (Presence) packet;
						System.out.println("=====>>有人加好友啦：  "+ presence.getFrom() +" "+ presence.toXML());
						Document parse = Jsoup.parse(presence.toXML());
						Elements elementsByTag = parse.getElementsByTag("status");
						String code=elementsByTag.attr("code");
						System.out.println("=====>>"+code);
						String myJid = getJabberID(conn.getUser());
						if("307".equals(code)){//请出房间
							//123@muc.yuneasy.cn/zd@yuneasy.cn/android
							String roomid=presence.getFrom().split("/")[0];
							System.out.println("=====>>移除的房间id："+roomid);
							
							YETApplication.getinstant().getRoomMap().remove(roomid);
							dbHelper.deleteData(GroupChatEntity.TABLE, GroupChatEntity.ROOMID+" = ? and "+GroupChatEntity.GID+" = ? ", new String[]{roomid,myJid});
							dbHelper.deleteData(InformationList.TABLE, InformationList.ROOMID+" = ? and "+InformationList.GID+" = ? ", new String[]{roomid,myJid});
							Intent intent=new Intent(action);
	                        intent.putExtra("newInfoFlag", "delete");  //群组
	                        mContext.sendBroadcast(intent);	
	                        dbHelper.close();
							return;
						}
						//<presence to="a@yuneasy.cn/android" from="yjjw9mgkubzwhyqlckaa5x3ardzu62@muc.yuneasy.cn/a@yuneasy.cn/android" type="unavailable">
						//<x xmlns="http://jabber.org/protocol/muc#user">
						//<item affiliation="none" nick="a@yuneasy.cn/android" role="none">
						//<reason>
						//</reason>
						//<actor jid=""/>
						//</item>
						//<status code="307"/>
						//</x>
						//</presence>
						if (presence.getType().equals(Presence.Type.subscribe)) {
							String jid = getJabberID(presence.getFrom());

							VCard vcard = new BaseUntil().getVcard(jid);
							String name=getNickName(jid);
							if (vcard != null) {
								if(!TextUtils.isEmpty(vcard.getNickName())){
									name=vcard.getNickName();
								}
							} 
							
							 ContentValues initialValues=new ContentValues();
							 initialValues.put(InformationList.FRIENDID, jid);
							 initialValues.put(InformationList.NICKNAME, name);
							 initialValues.put(InformationList.MSGDATE, TimeRender.getDate("yyyy-MM-dd  hh:mm:ss"));
							 initialValues.put(InformationList.FLAG, "1");
							 initialValues.put(InformationList.GID, myJid);
							 if(!dbHelper.updateData(InformationList.TABLE, initialValues, InformationList.FRIENDID+" = ? and "+InformationList.FLAG +" = ? and "+InformationList.GID+" = ? ", new String[]{jid,"1",myJid})){
								 dbHelper.insertData(InformationList.TABLE, null,initialValues);
							 }
							 Intent intent=new Intent(action);
		                     intent.putExtra("newInfoFlag", "newFriend");  //群组
		                     mContext.sendBroadcast(intent);	
						}else if(presence.getType().equals(Presence.Type.unsubscribe)){//删除好友
							System.out.println("删除好友："+presence.toXML());
							String jid = getJabberID(presence.getFrom());
							boolean deleteData = dbHelper.deleteData(InformationList.TABLE, InformationList.FRIENDID+" = ? and "+InformationList.GID+" = ? ", new String[]{jid,myJid});
							boolean deleteData2 = dbHelper.deleteData(ContactEntity.TABLE, ContactEntity.FRIENDID+" = ?", new String[]{jid});
							boolean deleteData3 = dbHelper.deleteData(SingleChatEntity.TABLE, SingleChatEntity.FRIENDID+" = ? and "+SingleChatEntity.USERID+" = ? ", new String[]{jid,myJid});
							
							System.out.println("删除好友："+deleteData+"  "+deleteData2+" "+deleteData3+" "+jid);
							Intent intent=new Intent(action);
	                        intent.putExtra("newInfoFlag", "delete");  //群组
	                        mContext.sendBroadcast(intent);	
						}
						dbHelper.close();
					}
               } catch (Exception e) {
                   // SMACK silently discards exceptions dropped from
                   // processPacket :(
            	   dbHelper.close();
                   Log.i(TAG,"failed to process packet:");
                   e.printStackTrace();
               }
           }
       };

       conn.addPacketListener(mPacketListener, filter);// 这是最关健的了，少了这句，前面的都是白费功夫
   }
    
    public String getNickName(String name){
    	if(name.indexOf("@")!=-1){
    		return name.substring(0, name.indexOf("@"));
    	}
    	return name;
    }
    
    public boolean isAuthenticated() {// 是否与服务器连接上，供本类和外部服务调用
        if (conn != null) {
            return (conn.isConnected() && conn
                    .isAuthenticated());
        }
        return false;
    }
	
	private void registerRosterListener() {
        mRoster = conn.getRoster();
        mRosterListener = new RosterListener() {
            private boolean isFristRoter;

            @Override
            public void presenceChanged(Presence presence) {// 联系人状态改变，比如在线或离开、隐身之类
                Log.i(TAG,"presenceChanged(" + presence.getFrom() + "): " +presence+"  "+ presence.getMode()+" "+presence.toXML());
                String jabberID = getJabberID(presence.getFrom());
                RosterEntry rosterEntry = mRoster.getEntry(jabberID);
                dbHelper.open();
                updateRosterEntryDB(rosterEntry);// 更新联系人数据库
                dbHelper.close();
    //            getMyRoomList();
//                mService.rosterChanged();// 回调通知服务，主要是用来判断一下是否掉线
                Intent intent=new Intent(action);
                intent.putExtra("presence", "presenceChanged");
                mContext.sendBroadcast(intent);
            }

            @Override
            public void entriesUpdated(Collection<String> entries) {// 更新数据库，第一次登陆  被删除的()
                // TODO Auto-generated method stub
            	/*
                Log.i(TAG,"entriesUpdated(" + entries + ")");
                dbHelper.open();
                for (String entry : entries) {
                    RosterEntry rosterEntry = mRoster.getEntry(entry);
                    //       Collection<RosterGroup> entriesGroup = mRoster.getGroups();
//                    System.out.println("friendName:"+rosterEntry.getUser());
                    updateRosterEntryInDB(rosterEntry);
                }
                dbHelper.close();
                Intent intent=new Intent(action);
                intent.putExtra("presence", "presenceChanged");
                mContext.sendBroadcast(intent);
                */
     //           getMyRoomList();
//                List<HostedRoom> roomList = getRoomList();
//                for(int i=0;i<roomList.size();i++){
//                    roomList.get(i).getName();
//                }
//                mService.rosterChanged();// 回调通知服务，主要是用来判断一下是否掉线
            }

            @Override
            public void entriesDeleted(Collection<String> entries) {// 有好友删除时，
                Log.i(TAG,"entriesDeleted(" + entries + ")");
                dbHelper.open();
                for (String entry : entries) {
                    deleteRosterEntryFromDB(entry);
                    
                }
                dbHelper.close();
                Intent intent=new Intent(action);
                intent.putExtra("presence", "presenceChanged");
                mContext.sendBroadcast(intent);
//                mService.rosterChanged();// 回调通知服务，主要是用来判断一下是否掉线
            }

            @Override
            public void entriesAdded(Collection<String> entries) {// 有人添加好友时，我这里没有弹出对话框确认，直接添加到数据库
                Log.i(TAG,"entriesAdded(" + entries + ")");
                dbHelper.open();
                for (String entry : entries) {
                    RosterEntry rosterEntry = mRoster.getEntry(entry);
                    if(rosterEntry.getUser()!=null||!"".equals(rosterEntry.getUser())){
                    	updateRosterEntryInDB(rosterEntry);
                    }
                    
                }
                dbHelper.close();
                if (isFristRoter) {
                    isFristRoter = false;
//                    mService.rosterChanged();// 回调通知服务，主要是用来判断一下是否掉线
                }
                Intent intent=new Intent(action);
                intent.putExtra("presence", "presenceChanged");
                mContext.sendBroadcast(intent);
                
                
            }
        };
        mRoster.addRosterListener(mRosterListener);
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
	
	 /**
	  * 
	  * @param nickName  昵称
	  * @param JID        jid
	  * @param context    消息
	  * @param isRead     是否读 yes no
	  * @param msgDate    消息时间
	  * @param userID     自己的id
	  * @param who        谁发的  （in out）
	  */
    private void addChatMessageToDB(String nickName, String JID, String context,
    		String isRead, String msgDate, String userID,String who) {
        ContentValues values = new ContentValues();

        values.put(SingleChatEntity.NICKNAME, nickName);
        values.put(SingleChatEntity.FRIENDID, JID);
        values.put(SingleChatEntity.CONTEXT, context);
        values.put(SingleChatEntity.ISREAD, isRead);
        values.put(SingleChatEntity.MSGDATE, msgDate);
        values.put(SingleChatEntity.USERID, userID);
        values.put(SingleChatEntity.WHO, who);

        dbHelper.insertData(SingleChatEntity.TABLE, null, values);
    }
	
	
	
	/**
     * 更新联系人数据库
     *
     * @param entry 联系人RosterEntry对象
     */
    private void updateRosterEntryInDB(final RosterEntry entry) {
        final ContentValues values = getContentValuesForRosterEntry(entry);
        boolean updateData = dbHelper.updateData(ContactEntity.TABLE, values, ContactEntity.FRIENDID+" = ?", new String[]{entry.getUser()});
        if(!updateData){
        	//没有修记录返回false 取反插入数据库
        	addRosterEntryToDB(entry);// 则添加到数据库
        }
    }
    
    /**
     * 更新联系人数据库
     *
     * @param entry 联系人RosterEntry对象
     */
    private void updateRosterEntryDB(final RosterEntry entry) {
        final ContentValues values = getContentValuesForRosterEntry(entry);
        boolean updateData = dbHelper.updateData(ContactEntity.TABLE, values, ContactEntity.FRIENDID+" = ?", new String[]{entry.getUser()});
    }
	
    /**
     * 添加到数据库
     *
     * @param entry 联系人RosterEntry对象
     */
    private void addRosterEntryToDB(final RosterEntry entry) {
        ContentValues values = getContentValuesForRosterEntry(entry);
        boolean insertData = dbHelper.insertData(ContactEntity.TABLE, null, values);
    }
    
    /**
     * 将联系人从数据库中删除
     *
     * @param jabberID
     */
    private void deleteRosterEntryFromDB(final String jabberID) {
        boolean deleteData = dbHelper.deleteData(ContactEntity.TABLE, ContactEntity.FRIENDID+" = ? ", new String[]{jabberID});
        System.out.println("entriesDeleted:"+jabberID+"   "+deleteData);
    }
    
    /**
     * 将联系人RosterEntry转化成ContentValues，方便存储数据库
     *
     * @param entry
     * @return
     */
    private ContentValues getContentValuesForRosterEntry(final RosterEntry entry) {
        final ContentValues values = new ContentValues();

        values.put(ContactEntity.FRIENDID, entry.getUser());
        VCard vcard = new BaseUntil().getVcard(entry.getUser());
        String nickName=getName(entry);
        if(vcard!=null){
        	if(!TextUtils.isEmpty(vcard.getNickName())){
        		nickName=vcard.getNickName();
        	}
        }
        values.put(ContactEntity.NICKNAME, nickName);
        Presence presence = mRoster.getPresence(entry.getUser());
//        System.out.println("getContentValuesForRosterEntry ："+entry.getUser()+"  "+presence.getType() +" " +getStatusInt(presence));
        values.put(ContactEntity.VISIBILITYIMG, getStatusInt(presence));
        values.put(ContactEntity.VISIBILITY, presence.getType().toString());
        
        values.put(ContactEntity.GROUP, getGroup(entry.getGroups()));

        return values;
    }
    
    /**
     * 获取联系人名称
     *
     * @param rosterEntry
     * @return
     */
    private String getName(RosterEntry rosterEntry) {
        String name = rosterEntry.getName();
        if (name != null && name.length() > 0) {
            return name;
        }
        name = StringUtils.parseName(rosterEntry.getUser());
        if (name.length() > 0) {
            return name;
        }
        return rosterEntry.getUser();
    }
    
    
    private int getStatusInt(final Presence presence) {
        return getStatus(presence).ordinal();
    }
    
    
    /**
     * 获取状态
     *
     * @param presence
     * @return
     */
    private StatusMode getStatus(Presence presence) {
        if (presence.getType() == Presence.Type.available) {
            if (presence.getMode() != null) {
//            	System.out.println("getStatus  :"+presence.getMode().name());
            	if("xa".equals(presence.getMode().name())){
            		return StatusMode.busy;
            	}
                return StatusMode.valueOf(presence.getMode().name());
            }
            return StatusMode.available;
        }
        return StatusMode.offline;
    }
    
    
    /**
     * 遍历获取组名
     *
     * @param groups
     * @return
     */
    private String getGroup(Collection<RosterGroup> groups) {
        for (RosterGroup group : groups) {
            return group.getName();
        }
        return "";
    }
    
	public static void configure(ProviderManager pm)
	{

		// Private Data Storage
				pm.addIQProvider("query", "jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());
				// Time
				try {
					pm.addIQProvider("query", "jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
				} catch (Exception e) {
					e.printStackTrace();
					// Logs.v(TAG,
					// "Can't load class for org.jivesoftware.smackx.packet.Time");
				}
				// Roster Exchange
				pm.addExtensionProvider("x", "jabber:x:roster", new RosterExchangeProvider());
				// Message Events
				pm.addExtensionProvider("x", "jabber:x:event", new MessageEventProvider());
				// Chat State
				pm.addExtensionProvider("active", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
				pm.addExtensionProvider("composing", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
				pm.addExtensionProvider("paused", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
				pm.addExtensionProvider("inactive", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
				pm.addExtensionProvider("gone", "http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
				// XHTML
				pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());
				// Group Chat Invitations
				pm.addExtensionProvider("x", "jabber:x:conference", new GroupChatInvitation.Provider());
				// Service Discovery # Items 
				pm.addIQProvider("query", "http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());
				// Service Discovery # Info //
				pm.addIQProvider("query", "http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());
				// Data Forms
				pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
				// MUC User
				pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user", new MUCUserProvider());
				// MUC Admin
				pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin", new MUCAdminProvider());
				// MUC Owner
				pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());
				// Delayed Delivery
				pm.addExtensionProvider("x", "jabber:x:delay", new DelayInformationProvider());
				// Version
				try {
					pm.addIQProvider("query", "jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
				} catch (ClassNotFoundException e) {
					// Not sure what's happening here.
				}
				// VCard
				pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
				// Offline Message Requests
				pm.addIQProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());
				// Offline Message Indicator
				pm.addExtensionProvider("offline", "http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());
				// Last Activity
				pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
				// User Search
				pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
				// SharedGroupsInfo
				pm.addIQProvider("sharedgroup", "http://www.jivesoftware.org/protocol/sharedgroup", new SharedGroupsInfo.Provider());
				// JEP-33: Extended Stanza Addressing
				pm.addExtensionProvider("addresses", "http://jabber.org/protocol/address", new MultipleAddressesProvider());
				// FileTransfer
				pm.addIQProvider("si", "http://jabber.org/protocol/si", new StreamInitiationProvider());
				pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
				// pm.addIQProvider("open", "http://jabber.org/protocol/ibb",
				// new IBBProviders.Open());
				//
				// pm.addIQProvider("close", "http://jabber.org/protocol/ibb",s
				// new IBBProviders.Close());
				//
				// pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb",
				// new IBBProviders.Data());
				// Privacy
				pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
				pm.addIQProvider("command", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider());
				pm.addExtensionProvider("malformed-action", "http://jabber.org/protocol/commands",
						new AdHocCommandDataProvider.MalformedActionError());
				pm.addExtensionProvider("bad-locale", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadLocaleError());
				pm.addExtensionProvider("bad-payload", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadPayloadError());
				pm.addExtensionProvider("bad-sessionid", "http://jabber.org/protocol/commands", new AdHocCommandDataProvider.BadSessionIDError());
				pm.addExtensionProvider("session-expired", "http://jabber.org/protocol/commands",
						new AdHocCommandDataProvider.SessionExpiredError());
	}
	
	public void chatRoom(){
			if(invitationListener!=null){
//				MultiUserChat.removeInvitationListener(arg0, arg1)
//				MultiUserChat multiUserChat = new MultiUserChat(null, null);
			}
			System.out.println("=============================================");
			invitationListener = new InvitationListener() {
			@Override
			public void invitationReceived(Connection arg0, String room, String inviter,
					String reason, String password, Message message) {
				dbHelper.open();
				Log.i(TAG, "收到来自 " + inviter + " 的聊天室邀请。邀请附带内容："  
                        + reason+"  "+room); 
				String myJid = getJabberID(conn.getUser());
				Cursor data = dbHelper.getData("select _id from "+InformationList.TABLE+" where "+InformationList.ROOMID+" = ? and "+InformationList.FLAG+" = ? and "+InformationList.GID+" = ? ", new String[]{room,"4",myJid});
				if(data!=null){
					if(data.getCount()>0){
						return;
					}
				}
				
				
				//b@yuneasy.cn/Jabber.Net
				String jid=getJabberID(inviter);
				ContentValues initialValues=new ContentValues();
				initialValues.put(InformationList.FRIENDID, jid);
				String nickName=getNickName(jid);
				VCard vcard = new BaseUntil().getVcard(jid);
				if(vcard!=null){
					if(!TextUtils.isEmpty(vcard.getNickName())){
						nickName=vcard.getNickName();
					}
				}
				initialValues.put(InformationList.NICKNAME, nickName);
				initialValues.put(InformationList.CONTEXT, reason+"");
				initialValues.put(InformationList.MSGDATE, TimeRender.getDate("yyyy-MM-dd  hh:mm:ss"));
				initialValues.put(InformationList.FLAG, "3");
				initialValues.put(InformationList.OBJECT, password);
				initialValues.put(InformationList.ROOMID, room);
				initialValues.put(InformationList.GID, myJid);
				if(!dbHelper.updateData(InformationList.TABLE, initialValues, InformationList.ROOMID+" = ? and "+InformationList.GID+" = ? ", new String[]{room,myJid})){
					dbHelper.insertData(InformationList.TABLE, null, initialValues);
				}
				System.out.println("      "+(mContext==null?"mContext  is  null":"mContext  connection"));
				Intent intent=new Intent(action);
                intent.putExtra("newInfoFlag", "newChatRoom");  //群组
                mContext.sendBroadcast(intent);	
                dbHelper.close();
			}
		};
		MultiUserChat.addInvitationListener(conn, invitationListener);
	}
	
	public void removeListener(){
		if(conn==null){
			return;
		}else {
			try {
				dbHelper.close();
				conn.getRoster().removeRosterListener(mRosterListener);
				conn.removePacketListener(mPacketListener);
				conn.getChatManager().removeChatListener(ChatListener.getInstance());
				MultiUserChat.removeInvitationListener(conn, invitationListener);
				conn.getRoster().removeRosterListener(null);
				conn.removePacketListener(null);
				conn.getChatManager().removeChatListener(null);
				MultiUserChat.removeInvitationListener(null,null);
				conn.removeConnectionListener(null);
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				e.getStackTrace();
				conn=null;
			}
			conn=null;
		}
		
	}
	
	
	/**
	 * 添加好友 有分组
	 * 
	 * @param userName
	 * @param name
	 * @param groupName
	 * @return
	 */
	public boolean addUser(String userName, String name, String groupName) {
		if (conn == null)
			return false;
		
		if(userName==null){
			if("".equals(userName)){
				return false;
			}
			return false;
		}
		if(userName.indexOf("@")==-1){
			userName=userName+"@"+Smack.conn.getServiceName();
		}
		
		try {
			Presence subscription = new Presence(Presence.Type.subscribed);
			subscription.setTo(userName);
			userName += "@" + conn.getServiceName();
			conn.sendPacket(subscription);
			conn.getRoster().createEntry(userName, name,
					new String[] { groupName });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加好友 无分组
	 * 
	 * @param userName gid
	 * @param name
	 * @return
	 */
	public static boolean addUser(String userName, String name) {
		if (conn == null)
			return false;
		
		if(userName==null){
			if("".equals(userName)){
				return false;
			}
			return false;
		}
		if(userName.indexOf("@")==-1){
			userName=userName+"@"+conn.getServiceName();
		}
		
		try {
			conn.getRoster().createEntry(userName, name, null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * 删除好友
	 * 
	 * @param userName
	 * @return
	 */
	public boolean removeUser(String userName) {
		if (conn== null)
			return false;
		try {
			RosterEntry entry = null;
			if (userName.contains("@"))
				entry = conn.getRoster().getEntry(userName);
			else
				entry = conn.getRoster().getEntry(
						userName + "@" + conn.getServiceName());
			if (entry == null)
				entry = conn.getRoster().getEntry(userName);
			conn.getRoster().removeEntry(entry);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	class timetask extends TimerTask {
		@Override
		public void run() {
			if (SettingInfo.getAccount() != null&& SettingInfo.getPassword() != null) {
				if(conn==null){
					getConnection();
				}
				Log.i("TaxiConnectionListener", "嘗試登錄");
				// 连接服务器
				String account=SettingInfo.getParams(PreferenceBean.USERIMACCOUNT, "")+"@"+SettingInfo.getParams(PreferenceBean.USERIMDOMAIN, "");
				if (login(account, SettingInfo.getParams(PreferenceBean.USERIMPWD, ""),SettingInfo.getParams(PreferenceBean.CLIENT, "android"))) {
					Log.i("TaxiConnectionListener", "登錄成功");
					tExit.cancel();
				} else {
					Log.i("TaxiConnectionListener", "重新登錄");
					tExit.schedule(new timetask(), logintime);
				}
			}
		}
	}  

	
	/**
	 * 加入会议室
	 * 
	 * @param user
	 *            昵称
	 * @param password
	 *            会议室密码
	 * @param roomsName
	 *            会议室名
	 */
	public MultiUserChat joinMultiUserChat(String user, String roomsName,
			String password) {
		if (conn == null)
			return null;
		
		if(roomsName==null){
			if("".equals(roomsName)){
				return null;
			}
			return null;
		}
		if(roomsName.indexOf("@")==-1){
			roomsName=roomsName+ "@muc." + conn.getServiceName();
		}
		
		try {
			// 使用XMPPConnection创建一个MultiUserChat窗口
			MultiUserChat muc = new MultiUserChat(conn, roomsName);
			// 聊天室服务将会决定要接受的历史记录数量
			DiscussionHistory history = new DiscussionHistory();
			history.setMaxChars(0);
			// history.setSince(new Date());
			// 用户加入聊天室
			muc.join(user, password, history,
					SmackConfiguration.getPacketReplyTimeout());
			Log.i("MultiUserChat", "会议室【"+roomsName+"】加入成功........");
			return muc;
		} catch (XMPPException e) {
			e.printStackTrace();
			Log.i("MultiUserChat", "会议室【"+roomsName+"】加入失败........");
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	
	/**
	 * 创建房间
	 * 
	 * @param roomName
	 *            房间名称
	 *
	public MultiUserChat createRoom(String user, String roomName,
			String password) {
		if (conn == null)
			return null;

		MultiUserChat muc = null;
		try {
//			conn.login(SettingInfo.getAccount(), SettingInfo.getPassword());
			// 创建一个MultiUserChat
			muc = new MultiUserChat(conn, roomName + "@muc."
					+ conn.getServiceName());
			muc.create(roomName);    //创建聊天室
            //     muc.join(groupChatName);
            // 获得聊天室的配置表单
            Form form = muc.getConfigurationForm();
            // 根据原始表单创建一个要提交的新表单。
            Form submitForm = form.createAnswerForm();
            // 向要提交的表单添加默认答复
            for (Iterator<?> fields = form.getFields(); fields.hasNext(); ) {
                FormField field = (FormField) fields.next();
                if (!FormField.TYPE_HIDDEN.equals(field.getType())
                        && field.getVariable() != null) {
                    // 设置默认值作为答复
                    submitForm.setDefaultAnswer(field.getVariable());
                }
            }

            // 设置聊天室是持久聊天室，即将要被保存下来
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            // 房间仅对成员开放
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            // 允许占有者邀请其他人
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            // 能够发现占有者真实 JID 的角色
            // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
            // 登录房间对话
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
            // 仅允许注册的昵称登录
            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
            // 允许使用者修改昵称
            submitForm.setAnswer("x-muc#roomconfig_canchangenick", true);
            // 允许用户注册房间
            submitForm.setAnswer("x-muc#roomconfig_registration", true);
            // 设置聊天室的新拥有者
            //       List<String> owners = new ArrayList<String>();
            //       owners.add(mXMPPConnection.getUser());
            //        submitForm.setAnswer("muc#roomconfig_roomowners", owners);

            //   submitForm.setAnswer("x-muc#roomconfig_registration", false);
            // 发送已完成的表单（有默认值）到服务器来配置聊天室
            muc.sendConfigurationForm(submitForm);
		} catch (XMPPException e) {
			e.printStackTrace();
			return null;
		}
		return muc;
	}
	
	
	**/
	
	
	/**
	 * 创建房间
	 * 
	 * @param roomName
	 *            房间名称
	 */
	public MultiUserChat createRoom(String user, String roomName,
			String password) {
		if (conn == null)
			return null;
		
		MultiUserChat muc = null;
		try {
//			conn.login(SettingInfo.getAccount(), SettingInfo.getPassword());
			// 创建一个MultiUserChat
			muc = new MultiUserChat(conn, roomName + "@muc."
					+ conn.getServiceName());
			muc.create(roomName);    //创建聊天室
            //     muc.join(groupChatName);
            // 获得聊天室的配置表单
            Form form = muc.getConfigurationForm();
            // 根据原始表单创建一个要提交的新表单。
            Form submitForm = form.createAnswerForm();
            // 向要提交的表单添加默认答复
            for (Iterator<?> fields = form.getFields(); fields.hasNext(); ) {
                FormField field = (FormField) fields.next();
                if (!FormField.TYPE_HIDDEN.equals(field.getType())
                        && field.getVariable() != null) {
                    // 设置默认值作为答复
                    submitForm.setDefaultAnswer(field.getVariable());
                }
            }
            
            //房间名称
            submitForm.setAnswer("muc#roomconfig_roomname", roomName);
            //房间的短描述
            submitForm.setAnswer("muc#roomconfig_roomdesc", roomName);
            //持久化房间
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            //使房间供公众查阅
            submitForm.setAnswer("muc#roomconfig_publicroom", true);
            //主持房间
            submitForm.setAnswer("muc#roomconfig_moderatedroom", true);
            // 房间仅对成员开放
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            //允许邀请成员
//            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
//            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            if (!password.equals("")) {
				// 进入是否需要密码
				submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",true);
				// 设置进入密码
				submitForm.setAnswer("muc#roomconfig_roomsecret", password);
			}
//            submitForm.setAnswer("muc#roomconfig_anonymity", "semianonymous");
            //是否允许改变话题
            submitForm.setAnswer("muc#roomconfig_changesubject", true);
            //使用聊天记录
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
            //房间回到历史信息的最大数量
            submitForm.setAnswer("muc#maxhistoryfetch", 50);
            //管理员
            List<String> owners = new ArrayList<String>();
            owners.add(user);
//            submitForm.setAnswer("muc#roomconfig_roomadmins", owners);
            
            
//            submitForm.setAnswer("muc#roomconfig_roomowners", user);
            
            
            /**
            // 设置聊天室是持久聊天室，即将要被保存下来
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);
            // 房间仅对成员开放
            submitForm.setAnswer("muc#roomconfig_membersonly", false);
            // 允许占有者邀请其他人
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);
            // 能够发现占有者真实 JID 的角色
            // submitForm.setAnswer("muc#roomconfig_whois", "anyone");
            // 登录房间对话
            submitForm.setAnswer("muc#roomconfig_enablelogging", true);
            // 仅允许注册的昵称登录
            submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
            // 允许使用者修改昵称
            submitForm.setAnswer("x-muc#roomconfig_canchangenick", true);
            // 允许用户注册房间
            submitForm.setAnswer("x-muc#roomconfig_registration", true);
            // 设置聊天室的新拥有者

            //   submitForm.setAnswer("x-muc#roomconfig_registration", false);
             * */
            // 发送已完成的表单（有默认值）到服务器来配置聊天室
            muc.sendConfigurationForm(submitForm);
            muc.join(roomName, password);
		} catch (XMPPException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return muc;
	}

}
