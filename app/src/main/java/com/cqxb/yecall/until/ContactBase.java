package com.cqxb.yecall.until;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import android.util.Log;

import com.cqxb.yecall.bean.CallBean;
import com.cqxb.yecall.bean.CallLogBean;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;

public class ContactBase {
	private Context mContext;
	private List<Contacts> ctBeans;//通讯录
	
	
	public ContactBase(Context context) {
		mContext=context;
		ctBeans=new ArrayList<Contacts>();
	}
	
	public void instance(Context context) {
		mContext=context;
	}

	public List<Contacts> getAllcontact(){
		getContacts();
//		getSIMContacts();
//		for (int i = 0; i < ctBeans.size(); i++) {
//			Log.i("联系人","\n联系人姓名："+ctBeans.get(i).getContactName()+" \n联系人号码："+ctBeans.get(i).getNumber()+" \n联系人类型："+ctBeans.get(i).getContactType()+"\n联系人---------------------\n");
//		}
		getType();
		return ctBeans;
	}
	
	public void getType(){
		ContentResolver resolver = mContext.getContentResolver(); 
		Cursor phoneCursor = resolver.query(Uri.parse("content://com.android.contacts/data/phones"),null, null, null, null);
		if(phoneCursor.getCount()<=0){
			Log.e("", "未找到数据");
			return;
		}
		while (phoneCursor.moveToNext()) {
			String number=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  
		    String phoneNumberType = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));  
//			Log.e("", phoneCursor.getString(0)+" ===== "+phoneCursor.getString(1)+"    "+phoneCursor.getColumnName(0)+"    "+phoneCursor.getColumnName(1)+"    "+phoneCursor.getColumnName(2));
		    Log.e("", "number:"+number+"    phoneNumberType:"+phoneNumberType);
		}
	}
	
	//取手机通讯录列表
		public void getContacts(){
			try {

				ContentResolver resolver = mContext.getContentResolver(); 
				Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,new String[] {  
					       Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID }, null, null, Phone.DISPLAY_NAME+" collate LOCALIZED ");  
				if(phoneCursor.getCount()<=0)
					return;
				if (phoneCursor != null) { 
					while (phoneCursor.moveToNext()) {  
						//得到手机号码 
						/**电话号码**/  
						String phoneNumber = phoneCursor.getString(1);  
						//当手机号码为空的或者为空字段 跳过当前循环  
						if (TextUtils.isEmpty(phoneNumber))  
							continue;
						 //得到联系人名称 
						String contactName = phoneCursor.getString(0); 
						//得到联系人ID  
						Long contactID = phoneCursor.getLong(3);
						//得到联系人头像ID 
						Long photoID = phoneCursor.getLong(2);
						 //得到联系人头像Bitamp 
//						Bitmap contactPhoto = null;
//						//photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
//						if(photoID > 0 ) { 
//							Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactID);
//							 InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
//							contactPhoto = BitmapFactory.decodeStream(input); 
//						}else {
//							contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.txl);  
//						}
						Contacts cBean=new Contacts();
						cBean.setContactID(contactID);
						cBean.setContactName(contactName);
//						cBean.setContactPhoto(contactPhoto);
						cBean.setNumber((phoneNumber!=null?phoneNumber.replaceAll(" ", ""):phoneNumber));
						cBean.setPhotoID(photoID);
						cBean.setContactType(0);
						cBean.setType(4);
						ctBeans.add(cBean);
					}
					phoneCursor.close();
				}
				
			
			} catch (Exception e) {
				Log.w("", "卡一数据错误");
			}
		}
		
		//获取SIM卡联系人
		public void getSIMContacts(){
			try {
				ContentResolver resolver = mContext.getContentResolver();  
				// 获取Sim1卡联系人
				Uri uri = Uri.parse("content://icc/adn");  
				Cursor phoneCursor = resolver.query(uri, null, null, null, Phone.DISPLAY_NAME+" collate LOCALIZED "); 
				if(phoneCursor.getCount()<=0)
					return;
				 if (phoneCursor != null) { 
					 while (phoneCursor.moveToNext()) {  
						 Long ID=phoneCursor.getLong(0);//序号
						 String contactName=phoneCursor.getString(1);//姓名
						 String phoneNumber=phoneCursor.getString(2);//电话号码
						 
						 Contacts cBean=new Contacts();
						 cBean.setContactID(ID);
						 cBean.setContactName(contactName);
						 cBean.setNumber((phoneNumber!=null?phoneNumber.replaceAll(" ", ""):phoneNumber));
						 cBean.setContactType(1);
						 cBean.setType(4);
						 ctBeans.add(cBean);
					 }
					 phoneCursor.close(); 
					// 获取Sim2卡联系人
					Uri uri2 = Uri.parse("content://icc/adn2");  
					Cursor phoneCursor2 = resolver.query(uri2, null, null, null, null); 
					if(phoneCursor2.getCount()<=0)
						return;
					 if (phoneCursor2 != null) { 
						 while (phoneCursor2.moveToNext()) {  
							 Long ID=phoneCursor2.getLong(0);//序号
							 String contactName=phoneCursor2.getString(1);//姓名
							 String phoneNumber=phoneCursor2.getString(2);//电话号码
							 
							 Contacts cBean=new Contacts();
							 cBean.setContactID(ID);
							 cBean.setContactName(contactName);
							 cBean.setNumber((phoneNumber!=null?phoneNumber.replaceAll(" ", ""):phoneNumber));
							 cBean.setContactType(2);
							 cBean.setType(4);
							 ctBeans.add(cBean);
						 }
					 }
					 phoneCursor2.close();  
				 }
			} catch (Exception e) {
				Log.w("", "暂无卡二");
			}
			
		}
		
		
		//手机通话记录
//		public List<CallBean> getPhoneCallList(){
//			Log.i("CallPhone", "CallPhone 第一次进入拨号");
//			List<CallBean> cList=new ArrayList<CallBean>();
//			ContentResolver cr = mContext.getContentResolver();
//			final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,// 使用系统URI，取得通话记录
//				    new String[] { CallLog.Calls.NUMBER,// 电话号码
//				    CallLog.Calls.CACHED_NAME,// 联系人
//				    CallLog.Calls.TYPE,// 通话类型
//				    CallLog.Calls.DATE,// 通话时间
//				    CallLog.Calls.DURATION,// 通话时长
//				    }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
//			for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()) {
//				String strNumber = cursor.getString(0); // 呼叫号码
//				String strName = cursor.getString(1); // 联系人姓名
//				if(strName==null){
//					strName="未知号码";
//				}else if(("-2").equals(strName)){
//					strName="未知号码";
//				}
//				int type =cursor.getInt(2);
//				String str_type = "";
//				if (type == CallLog.Calls.INCOMING_TYPE) {
//					str_type = "呼入";
//				} else if (type == CallLog.Calls.OUTGOING_TYPE) {
//					str_type = "呼出";
//				} else if (type == CallLog.Calls.MISSED_TYPE) {
//					str_type = "未接";
//				}
//				int duration =cursor.getInt(4);
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date date = new Date(Long.parseLong(cursor.getString(3)));
//				String time = sdf.format(date);
//				
//				int hour =  duration /(60*60);  
//				int min = (duration %(60*60)) /(60);  
//				int second =  (duration %(60));
//				String times=hour+" 时 "+min+" 分 "+second+" 秒";
//				CallBean contactBean=new CallBean();
//				contactBean.setName(strName);// 联系人
//				contactBean.setDuration(times);// 通话时长
//				contactBean.setNumber(strNumber);// 电话号码
//				contactBean.setType(type);// 通话类型
//				contactBean.setStamp(time);// 通话时间
//				cList.add(contactBean);
//				Log.i("CallPhone","\nCallPhone 类型：" + str_type + "\nCallPhone 称呼：" + strName +"\nCallPhone 号码：" + strNumber +"\nCallPhone 通话时长：" + duration + "秒" + "\nCallPhone 时间:" + time+"\nCallPhone ---------------------\n");
//			}
//			cursor.close();
//			return cList;
//		}
//		public List<CallBean> getPhoneCallList(){
//			List<CallBean> list=new ArrayList<CallBean>();
//			DBHelper dbHelper=new DBHelper(mContext);
//		    dbHelper.open();
//		    Cursor data = dbHelper.getData("select * from "+CallLogBean.TABLE +" where "+CallLogBean.BELONG+" = ? order by _id desc", new String[]{SettingInfo.getAccount()});
//		    System.out.println("插入成功   获取通话记录后："+SettingInfo.getAccount());
//		    if(data!=null){
//		    	for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()) {
//		    		CallBean call=new CallBean();
//		    		call.setDuration(data.getString(data.getColumnIndex(CallLogBean.CALLTIME)));
//		    		call.setName(data.getString(data.getColumnIndex(CallLogBean.NAME)));
//		    		String number=data.getString(data.getColumnIndex(CallLogBean.NUMBER));
//		    		if(number!=null){
//		    			number=number.replaceAll(" ", "");
//		    		}
//		    		call.setNumber(number);
//		    		call.setStamp(data.getString(data.getColumnIndex(CallLogBean.STARTCALL)));
//		    		call.setType(data.getInt(data.getColumnIndex(CallLogBean.TYPE)));
//		    		list.add(call);
////		    		System.out.println("插入成功::"+call.getNumber());
//		    	}
//		    }
//		    dbHelper.close();
//			return list;
//		}
		
		
		
		public List<Contacts> getPhoneCallLists(){
			List<Contacts> list=new ArrayList<Contacts>();
			DBHelper dbHelper=new DBHelper(mContext);
		    dbHelper.open();
		    Cursor data = dbHelper.getData("select * from "+CallLogBean.TABLE +" where "+CallLogBean.BELONG+" = ? order by _id desc", new String[]{SettingInfo.getAccount()});
		    System.out.println("插入成功   获取通话记录后："+SettingInfo.getAccount());
		    if(data!=null){
		    	for (data.moveToFirst(); !data.isAfterLast();data.moveToNext()) {
		    		Contacts call=new Contacts();
		    		call.setDuration(data.getString(data.getColumnIndex(CallLogBean.CALLTIME)));
		    		call.setContactName(data.getString(data.getColumnIndex(CallLogBean.NAME)));
		    		String number=BaseUntil.stringNoNull(data.getString(data.getColumnIndex(CallLogBean.NUMBER)));
		    		number=number.replaceAll(" ", "");
		    		call.setStamp(data.getString(data.getColumnIndex(CallLogBean.STARTCALL)));
		    		call.setType(data.getInt(data.getColumnIndex(CallLogBean.TYPE)));
		    		call.setRecordFile(BaseUntil.stringNoNull(data.getString(data.getColumnIndex(CallLogBean.RECORDPATH))).trim());
		    		call.setPhotoFile(BaseUntil.stringNoNull(data.getString(data.getColumnIndex(CallLogBean.PHOTOPATH))).trim());
		    		if(!"".equals(BaseUntil.stringNoNull(number))&&!"null".equals(BaseUntil.stringNoNull(number))){
		    			call.setNumber(number);
		    			list.add(call);
		    		}
		    		
//		    		System.out.println("插入成功::"+call.getNumber());
//		    		Log.e("", data.getString(data.getColumnIndex(CallLogBean.NAME))+" :  "+data.getString(data.getColumnIndex(CallLogBean.NUMBER)));
		    	}
		    }
		    dbHelper.close();
			return list;
		}
		
		
		/**
		 * 插入通话记录
		 * @param context  
		 * @param name  联系人
		 * @param number  电话号码
		 * @param type  通话类型  
		 * @通话类型 来电：CallLog.Calls.INCOMING_TYPE  (常量值：1) 
		 * 已拨：CallLog.Calls.OUTGOING_TYPE (常量值：2)
		 * 未接：CallLog.Calls.MISSED_TYPE (常量值：3)
		 * @param callTime  通话时长
		 * @param startCall 何时开始通话
		 * 
		 */
//		public static void insertCallLog(Context context,String name,String number, int type, long callTime,long startCall)
//		{
//			//CallLog.Calls.NUMBER,// 电话号码
//		   // CallLog.Calls.CACHED_NAME,// 联系人
//		   //CallLog.Calls.TYPE,// 通话类型
//		    //CallLog.Calls.DATE,// 通话时间
//		    //CallLog.Calls.DURATION,// 通话时长
//		    // TODO Auto-generated method stub
//		    ContentValues values = new ContentValues();
//		    values.put(CallLog.Calls.NUMBER, number);// 电话号码
//		    values.put(CallLog.Calls.DATE, startCall);// 何时开始通话
//		    values.put(CallLog.Calls.DURATION, callTime);// 通话时长
//		    values.put(CallLog.Calls.TYPE,type);//通话类型
//		    values.put(CallLog.Calls.CACHED_NAME,name);// 联系人
//		    values.put(CallLog.Calls.NEW, 1);//0已看1未看
//		    context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
//		}
		public static void insertCallLog(Context context,String name,String number, int type, String callTime,long startCall)
		{
			//CallLog.Calls.NUMBER,// 电话号码
		   // CallLog.Calls.CACHED_NAME,// 联系人
		   //CallLog.Calls.TYPE,// 通话类型
		    //CallLog.Calls.DATE,// 通话时间
		    //CallLog.Calls.DURATION,// 通话时长
		    // TODO Auto-generated method stub
		    ContentValues values = new ContentValues();
		    values.put(CallLogBean.NUMBER, number);// 电话号码
		    values.put(CallLogBean.STARTCALL, startCall);// 何时开始通话
		    values.put(CallLogBean.CALLTIME, callTime);// 通话时长
		    values.put(CallLogBean.TYPE,type);//通话类型
		    values.put(CallLogBean.NAME,name);// 联系人
		    values.put(CallLogBean.NEWS, 1);//0已看1未看
		    values.put(CallLogBean.BELONG, SettingInfo.getAccount());
		    values.put(CallLogBean.RECORDPATH, SettingInfo.getParams(PreferenceBean.RECORDPATH, ""));//录音地址
		    values.put(CallLogBean.PHOTOPATH, SettingInfo.getParams(PreferenceBean.PHOTOPATH, ""));//录音地址
		    SettingInfo.setParams(PreferenceBean.RECORDPATH, "");
		    SettingInfo.setParams(PreferenceBean.PHOTOPATH, "");
		    DBHelper dbHelper=new DBHelper(context);
		    dbHelper.open();
		    boolean insertData = dbHelper.insertData(CallLogBean.TABLE, null, values);
		    System.out.println((insertData?"插入成功  插入通话记录："+SettingInfo.getAccount():"插入成功  插入通话记录失败")  );
		    dbHelper.close();
		}
		
		
		
		
		/**
		 * 添加联系人，使用事务
		 * @param context
		 * @param name
		 * @param number
		 */
	    public void testAddContact(Context context,String name,String number)  {
	    
	    		//首先插入空值，再得到rawContactsId ，用于下面插值   
	            ContentValues values = new ContentValues ();   
	            //insert a null value  
	            Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI,values);   
	            long rawContactsId = ContentUris.parseId(rawContactUri);   
	          
	            //往刚才的空记录中插入姓名   
	            values.clear();   
	            //A reference to the _ID that this data belongs to  
	            values.put(StructuredName.RAW_CONTACT_ID,rawContactsId);   
	            //"CONTENT_ITEM_TYPE" MIME type used when storing this in data table  
	            values.put(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE);   
	            //The name that should be used to display the contact.  
	            values.put(StructuredName.DISPLAY_NAME,name);   
	            //insert the real values  
	            context.getContentResolver().insert(Data.CONTENT_URI,values);   
	            //插入电话   
	            values.clear();   
	            values.put(Phone.RAW_CONTACT_ID,rawContactsId);   
	            //String "Data.MIMETYPE":The MIME type of the item represented by this row  
	            //String "CONTENT_ITEM_TYPE": MIME type used when storing this in data table.  
	            values.put(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE);   
	            values.put(Phone.NUMBER,number);   
	            context.getContentResolver().insert(Data.CONTENT_URI,values);   
	    	
	    }
}
