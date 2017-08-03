package com.cqxb.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.util.Log;

public class PhoneContacts {
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;
	/** 联系人显示名称 **/
	private static final int PHONES_NUMBER_INDEX = 1;
	/** 电话号码 **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;
	/** 头像ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;
	/** 联系人的ID **/

	public static final int PHONE_CALL_LOGS_MAX = 50;

	List<Contact> mContractsinfos = null;
//
	public static PhoneContacts mInstance;
	private static Context mContext;

	private PhoneContacts() {
	}

	public static PhoneContacts getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new PhoneContacts();
		}
		mContext = ctx;
		return mInstance;
	}

//	public void insertYouetongCallLog(youetongcalllog note) {
//		DbService.getInstance(mContext).saveNote(note);
//	}
//
//	public void deleteYouetongAllCallLog() {
//		DbService.getInstance(mContext).deleteAllNote();
//	}

//	public List<youetongcalllog> getCallsLog() {
//		List<youetongcalllog> mYouetongCallsLogList = DbService.getInstance(mContext).loadAllNote();
//		mYouetongCallsLogList.addAll(mPhoneCallsLogList);
//
//		// 排序
//		Collections.sort(mYouetongCallsLogList);
//
//		return mYouetongCallsLogList;
//	}

	/**
	 * 清空通话记录
	 */
//	public void clearCallsLog() {
//		// 清空优e通的通话记录
//		deleteYouetongAllCallLog();
//
//		// 清空手机的通话记录
//		mPhoneCallsLogList.clear();
//		Context context = mContext.getApplicationContext();
//		ContentResolver resolver = context.getContentResolver();
//		resolver.delete(CallLog.Calls.CONTENT_URI, null, null);
//	}

	/**
	 * 获取通话记录
	 * 
	 * @return
	 */
//	public List<youetongcalllog> loadCallsLog() {
//		mPhoneCallsLogList.clear();
//		Context context = mContext.getApplicationContext();
//		Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
//				new String[] { CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION }, null, null,
//				CallLog.Calls.DEFAULT_SORT_ORDER);
//
//		// query(数据库表名，取得的数据数组（里边包含字段名称），条件，参数组，排序等信息） //这就相当于一条SQL语句// 取得所有通话信息
//		// 这里边稍微有点复杂
//
//		int count = 0;
//		if (cursor.moveToFirst()) {
//			do {
//				if (count++ > PHONE_CALL_LOGS_MAX)
//					break;
//				Calls calls = new Calls();
//				// 号码
//				String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));
//				// 呼叫类型
//				String type;
//				int type_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)));
//				switch (type_id) {
//				case Calls.INCOMING_TYPE:
//					type = "呼入";
//					break;
//				case Calls.OUTGOING_TYPE:
//					type = "呼出";
//					break;
//				case Calls.MISSED_TYPE:
//					type = "未接";
//					break;
//				default:
//					type = "折断";
//					break;
//				}
//				SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				long stamp = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE)));
//				Date date = new Date(stamp);
//				// 呼叫 时间
//				String time = sfd.format(date);
//				// 联系人
//				String name = cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));
//				// 通话时间，单位：s
//				String duration = cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));
//				String str = "(" + count + ")号码：" + number + "\n" + "呼叫类型: " + type + "\n" + "呼叫时间: " + time + "\n" + "联系人: " + name + "\n" + "通话时间：" + duration + "秒";
//				if (number!=null && name!=null&& number.length() > 0 && name.length() > 0) {
//					youetongcalllog callslog = new youetongcalllog(name, number, stamp, Long.valueOf(duration), type_id);
//					mPhoneCallsLogList.add(callslog);
//					Log.d("add", str);
//				} else {
//					Log.w("dicard", str);
//				}
//			} while (cursor.moveToNext());
//
//		}
//		return mPhoneCallsLogList;
//
//	}

	// 获取通讯录列表
	protected List<Contact> loadPhoneContacts() {
		List<Contact> list = new ArrayList<Contact>();
		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		int i = 0;
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				contactName = contactName.replaceAll("\\s*", "");
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(phoneNumber);
				phoneNumber = m.replaceAll("").trim();
				Contact mUserInfo = new Contact();
				mUserInfo.setFname(contactName);
				mUserInfo.setPhone(phoneNumber);
				list.add(mUserInfo);
			}
			phoneCursor.close();
		}

		return list;
	}

	// 获取SIM卡联系人列表
	public void getSIMContacts(List<Contact> mList) {
		mList.removeAll(mList);
		ContentResolver resolver = mContext.getContentResolver();
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				Contact mUserInfo = new Contact();
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
				mUserInfo.setFname(contactName);
				mUserInfo.setPhone(phoneNumber);
				mList.add(mUserInfo);
			}
			phoneCursor.close();
		}
	}

	public List<Contact> getContacts(CharSequence match) {
		List<Contact> list = new ArrayList<Contact>();
		if (mContractsinfos != null) {
			for (Contact contract : mContractsinfos) {
				if (contract.getFname().contains(match) || contract.getPhone().contains(match)) {
					list.add(contract);
				}
			}
		}
		return list;
	}

	public List<Contact> getContacts() {
		loadContacts();
		return mContractsinfos;
	}

	public void loadContacts() {
		mContractsinfos = loadPhoneContacts();
		if (mContractsinfos.size() > 0) {
			Collections.sort(mContractsinfos);
			for (int i = 0; i < mContractsinfos.size(); i++) {
				Contact contracts = mContractsinfos.get(i);
				Log.v("", String.format("name:%s,  phone:%s", contracts.getFname(), contracts.getPhone()));
			}
		}
	}
}
