package com.cqxb.yecall.t9search.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.pinyinsearch.model.PinyinUnit;

public class Contacts extends BaseContacts implements Cloneable{
	private static final String TAG="ContactsContacts";
	public enum SearchByType {
		SearchByNull, SearchByName, SearchByPhoneNumber,
	}
	private String mSortKey; // as the sort key word

	private List<PinyinUnit> mNamePinyinUnits; // save the mName converted to
												// Pinyin characters.

	private SearchByType mSearchByType; // Used to save the type of search
	private StringBuffer mMatchKeywords;// Used to save the type of Match Keywords.(name or phoneNumber)
	private int mMatchStartIndex;		//the match start  position of mMatchKeywords in original string(name or phoneNumber).
	private int mMatchLength;			//the match length of mMatchKeywords in original string(name or phoneNumber).
	private boolean mSelected;	//whether select contact
	private boolean mFirstMultipleContacts;//whether the first multiple Contacts
	private boolean mHideMultipleContacts;		//whether hide multiple contacts
	private boolean mBelongMultipleContactsPhone; //whether belong multiple contacts phone, the value of the variable will not change once you set.
	
	private boolean mHideOperationView; 		//whether hide operation view
	private Contacts mNextContacts; //point the contacts information who has multiple numbers. 
	
	// Personal information
	private String name;
	private String telephoneNumber;
	private String group;
	private String iconUrl;

	// Help select
	private int contactType;// 联系人所属类型 “0-本机， 1-企业”
	private String sortLetters;// 名称首字母
	private String context;// 其它的内容
	private String jianpin;// 中文简拼

	// About call information
	private Integer callType = 10;
	private String beginTime = "";
	private String callDuration = "";

	// Enterprise information
	private String position = "";
	private String compName = "";
	private String sipaccount = "";
	private String sip_domain = "";
	private String sip_account = "";
	private String im_domain = "";
	private String im_account = "";
	private int activateState = 1;// 是否激活 “0-激活，1-未激活”
	private String signature = "";
	private int matchPhoneType = 0;// 匹配类型“0-'telephoneNumber'匹配， 1-‘sipaccount’匹配”	
	
	private String number;//得到手机号码
	private String contactName;//得到联系人名称
	private Long contactID;//得到联系人ID  
	private Long photoID;//得到联系人头像ID
	private Bitmap contactPhoto ;//得到联系人头像Bitamp  
	private String en;//首字符英文字母
	
	private String stamp;// 何时开始通话
	private String duration;// 通话时长
	private Integer type =4;// 通话类型 1-呼入 2-呼出 3-未接  4-联系人
	
	private String recordFile = "";
	public String getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}

	private String photoFile = "";
	
	public String getRecordFile() {
		return recordFile;
	}

	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Long getContactID() {
		return contactID;
	}

	public void setContactID(Long contactID) {
		this.contactID = contactID;
	}

	public Long getPhotoID() {
		return photoID;
	}

	public void setPhotoID(Long photoID) {
		this.photoID = photoID;
	}

	public Bitmap getContactPhoto() {
		return contactPhoto;
	}

	public void setContactPhoto(Bitmap contactPhoto) {
		this.contactPhoto = contactPhoto;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getmSortKey() {
		return mSortKey;
	}

	public void setmSortKey(String mSortKey) {
		this.mSortKey = mSortKey;
	}

	public List<PinyinUnit> getmNamePinyinUnits() {
		return mNamePinyinUnits;
	}

	public void setmNamePinyinUnits(List<PinyinUnit> mNamePinyinUnits) {
		this.mNamePinyinUnits = mNamePinyinUnits;
	}

	public SearchByType getmSearchByType() {
		return mSearchByType;
	}

	public void setmSearchByType(SearchByType mSearchByType) {
		this.mSearchByType = mSearchByType;
	}

	public StringBuffer getmMatchKeywords() {
		return mMatchKeywords;
	}

	public void setmMatchKeywords(StringBuffer mMatchKeywords) {
		this.mMatchKeywords = mMatchKeywords;
	}

	public int getmMatchStartIndex() {
		return mMatchStartIndex;
	}

	public void setmMatchStartIndex(int mMatchStartIndex) {
		this.mMatchStartIndex = mMatchStartIndex;
	}

	public int getmMatchLength() {
		return mMatchLength;
	}

	public void setmMatchLength(int mMatchLength) {
		this.mMatchLength = mMatchLength;
	}

	public boolean ismSelected() {
		return mSelected;
	}

	public void setmSelected(boolean mSelected) {
		this.mSelected = mSelected;
	}

	public boolean ismFirstMultipleContacts() {
		return mFirstMultipleContacts;
	}

	public void setmFirstMultipleContacts(boolean mFirstMultipleContacts) {
		this.mFirstMultipleContacts = mFirstMultipleContacts;
	}

	public boolean ismHideMultipleContacts() {
		return mHideMultipleContacts;
	}

	public void setmHideMultipleContacts(boolean mHideMultipleContacts) {
		this.mHideMultipleContacts = mHideMultipleContacts;
	}

	public boolean ismBelongMultipleContactsPhone() {
		return mBelongMultipleContactsPhone;
	}

	public void setmBelongMultipleContactsPhone(boolean mBelongMultipleContactsPhone) {
		this.mBelongMultipleContactsPhone = mBelongMultipleContactsPhone;
	}

	public boolean ismHideOperationView() {
		return mHideOperationView;
	}

	public void setmHideOperationView(boolean mHideOperationView) {
		this.mHideOperationView = mHideOperationView;
	}

	public Contacts getmNextContacts() {
		return mNextContacts;
	}

	public void setmNextContacts(Contacts mNextContacts) {
		this.mNextContacts = mNextContacts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getContactType() {
		return contactType;
	}

	public void setContactType(int contactType) {
		this.contactType = contactType;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getJianpin() {
		return jianpin;
	}

	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	public Integer getCallType() {
		return callType;
	}

	public void setCallType(Integer callType) {
		this.callType = callType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getSipaccount() {
		return sipaccount;
	}

	public void setSipaccount(String sipaccount) {
		this.sipaccount = sipaccount;
	}

	public String getSip_domain() {
		return sip_domain;
	}

	public void setSip_domain(String sip_domain) {
		this.sip_domain = sip_domain;
	}

	public String getSip_account() {
		return sip_account;
	}

	public void setSip_account(String sip_account) {
		this.sip_account = sip_account;
	}

	public String getIm_domain() {
		return im_domain;
	}

	public void setIm_domain(String im_domain) {
		this.im_domain = im_domain;
	}

	public String getIm_account() {
		return im_account;
	}

	public void setIm_account(String im_account) {
		this.im_account = im_account;
	}

	public int getActivateState() {
		return activateState;
	}

	public void setActivateState(int activateState) {
		this.activateState = activateState;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getMatchPhoneType() {
		return matchPhoneType;
	}

	public void setMatchPhoneType(int matchPhoneType) {
		this.matchPhoneType = matchPhoneType;
	}

	public static Comparator<Object> getmChineseComparator() {
		return mChineseComparator;
	}

	public static void setmChineseComparator(Comparator<Object> mChineseComparator) {
		Contacts.mChineseComparator = mChineseComparator;
	}

	public static Comparator<Contacts> getmDesComparator() {
		return mDesComparator;
	}

	public static void setmDesComparator(Comparator<Contacts> mDesComparator) {
		Contacts.mDesComparator = mDesComparator;
	}

	public static Comparator<Contacts> getmAscComparator() {
		return mAscComparator;
	}

	public static void setmAscComparator(Comparator<Contacts> mAscComparator) {
		Contacts.mAscComparator = mAscComparator;
	}

	public static Comparator<Contacts> getmSearchComparator() {
		return mSearchComparator;
	}

	public static void setmSearchComparator(Comparator<Contacts> mSearchComparator) {
		Contacts.mSearchComparator = mSearchComparator;
	}

	public static String getTag() {
		return TAG;
	}
	
	public Contacts(){
		
	}

	public Contacts(String id ,String name, String phoneNumber) {
		super();
		setId(id);
		setName(name);
		setPhoneNumber(phoneNumber);
		setNamePinyinUnits(new ArrayList<PinyinUnit>());
		setSearchByType(SearchByType.SearchByNull);
		setMatchKeywords(new StringBuffer());
		getMatchKeywords().delete(0, getMatchKeywords().length());
		setMatchStartIndex(-1);
		setMatchLength(0);
		setNextContacts(null);
		setSelected(false);
		setFirstMultipleContacts(true);
		setHideMultipleContacts(false);
		setHideOperationView(true);
		setBelongMultipleContactsPhone(false);
	}
	
	public Contacts(String id, String name, String phoneNumber, String sortKey) {
		super();
		setId(id);
		setName(name);
		setPhoneNumber(phoneNumber);
		setSortKey(sortKey);
		setNamePinyinUnits(new ArrayList<PinyinUnit>());
		setSearchByType(SearchByType.SearchByNull);
		setMatchKeywords(new StringBuffer());
		getMatchKeywords().delete(0, getMatchKeywords().length());
		setMatchStartIndex(-1);
		setMatchLength(0);
		setNextContacts(null);
		setSelected(false);
		setFirstMultipleContacts(true);
		setHideMultipleContacts(false);
		setHideOperationView(true);
		setBelongMultipleContactsPhone(false);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Contacts obj=(Contacts) super.clone();
		obj.mNamePinyinUnits=new ArrayList<PinyinUnit>();
		for(PinyinUnit pu:mNamePinyinUnits){
			obj.mNamePinyinUnits.add((PinyinUnit)pu.clone());
		}
		obj.mSearchByType=mSearchByType;
		obj.mMatchKeywords=new StringBuffer(mMatchKeywords);
		obj.mNextContacts=mNextContacts;
		
		return super.clone();
	}

	private static Comparator<Object> mChineseComparator = Collator.getInstance(Locale.CHINA);
	
	public static Comparator<Contacts> mDesComparator = new Comparator<Contacts>() {

		@Override
		public int compare(Contacts lhs, Contacts rhs) {
		
			return mChineseComparator.compare(rhs.mSortKey, lhs.mSortKey);
		}
	};

	public static Comparator<Contacts> mAscComparator = new Comparator<Contacts>() {

		@Override
		public int compare(Contacts lhs, Contacts rhs) {
			return mChineseComparator.compare(lhs.mSortKey, rhs.mSortKey);
		}
	};
	
	/*public static Comparator<List<Contacts>> mAscComparator = new Comparator<List<Contacts>>() {

		@Override
		public int compare(List<Contacts> lhs, List<Contacts> rhs) {
			if((null==lhs)||(lhs.size()<=0)||(null==rhs)||(rhs.size()<=0)){
				return 0;
			}
			return mChineseComparator.compare(lhs.get(0).mSortKey, rhs.get(0).mSortKey);
		}
	};*/
	
	public static Comparator<Contacts> mSearchComparator = new Comparator<Contacts>() {

		@Override
		public int compare(Contacts lhs, Contacts rhs) {
			int compareMatchStartIndex=(lhs.mMatchStartIndex-rhs.mMatchStartIndex);
			return ((0!=compareMatchStartIndex)?(compareMatchStartIndex):(rhs.mMatchLength-lhs.mMatchLength));
		}
	};



	public List<PinyinUnit> getNamePinyinUnits() {
		return mNamePinyinUnits;
	}

	public void setNamePinyinUnits(List<PinyinUnit> namePinyinUnits) {
		mNamePinyinUnits = namePinyinUnits;
	}
	
	public String getSortKey() {
		return mSortKey;
	}

	public void setSortKey(String sortKey) {
		mSortKey = sortKey;
	}

	public SearchByType getSearchByType() {
		return mSearchByType;
	}

	public void setSearchByType(SearchByType searchByType) {
		mSearchByType = searchByType;
	}

	public StringBuffer getMatchKeywords() {
		return mMatchKeywords;
	}

	public void setMatchKeywords(StringBuffer matchKeywords) {
		mMatchKeywords = matchKeywords;
	}

	public void setMatchKeywords(String matchKeywords) {
		mMatchKeywords.delete(0, mMatchKeywords.length());
		mMatchKeywords.append(matchKeywords);
	}

	public void clearMatchKeywords() {
		mMatchKeywords.delete(0, mMatchKeywords.length());
	}
	
	public int getMatchStartIndex() {
		return mMatchStartIndex;
	}

	public void setMatchStartIndex(int matchStartIndex) {
		mMatchStartIndex = matchStartIndex;
	}

	public int getMatchLength() {
		return mMatchLength;
	}

	public void setMatchLength(int matchLength) {
		mMatchLength = matchLength;
	}

	public boolean isSelected() {
		return mSelected;
	}

	public void setSelected(boolean selected) {
		mSelected = selected;
	}
	
	public boolean isFirstMultipleContacts() {
		return mFirstMultipleContacts;
	}

	public void setFirstMultipleContacts(boolean firstMultipleContacts) {
		mFirstMultipleContacts = firstMultipleContacts;
	}
	
	public boolean isHideMultipleContacts() {
		return mHideMultipleContacts;
	}

	public void setHideMultipleContacts(boolean hideMultipleContacts) {
		mHideMultipleContacts = hideMultipleContacts;
	}
	
	public boolean isBelongMultipleContactsPhone() {
		return mBelongMultipleContactsPhone;
	}

	public void setBelongMultipleContactsPhone(boolean belongMultipleContactsPhone) {
		mBelongMultipleContactsPhone = belongMultipleContactsPhone;
	}

	public boolean isHideOperationView() {
		return mHideOperationView;
	}

	public void setHideOperationView(boolean hideOperationView) {
		mHideOperationView = hideOperationView;
	}
	
	public Contacts getNextContacts() {
		return mNextContacts;
	}

	public void setNextContacts(Contacts nextContacts) {
		mNextContacts = nextContacts;
	}

	public static Contacts addMultipleContact(Contacts contacts, String phoneNumber){
		do{
			if((TextUtils.isEmpty(phoneNumber))||(null==contacts)){
				break;
			}
			
			Contacts currentContact=null;
			Contacts nextContacts=null;
			for(nextContacts=contacts; null!=nextContacts; nextContacts=nextContacts.getNextContacts()){
				currentContact=nextContacts;
				if(nextContacts.getPhoneNumber().equals(phoneNumber)){
					break;
				}
			}
			Contacts cts=null;
			if(null==nextContacts){
				Contacts cs=currentContact;
				cts=new Contacts(cs.getId(), cs.getName(),phoneNumber);
				cts.setSortKey(cs.getSortKey());
				cts.setNamePinyinUnits(cs.getNamePinyinUnits());// not deep copy
				cts.setFirstMultipleContacts(false);
				cts.setHideMultipleContacts(true);
				cts.setBelongMultipleContactsPhone(true);
				cs.setBelongMultipleContactsPhone(true);
				cs.setNextContacts(cts);
			}
			
			return cts;
		}while(false);
		
		return null;
	}
	
	public static int getMultipleNumbersContactsCount(Contacts contacts){
		int contactsCount=0;
		if(null==contacts){
			return contactsCount;
		}
		Contacts currentContacts=contacts.getNextContacts();
		Contacts nextContacts=null;
		while(null!=currentContacts){
			contactsCount++;
			nextContacts=currentContacts;
			currentContacts=nextContacts.getNextContacts();
		}
		
		return contactsCount;
	}
	
	public static void hideOrUnfoldMultipleContactsView(Contacts contacts){
		if(null==contacts){
			return;
		}
		
		if(null==contacts.getNextContacts()){
			return;
		}
		
		boolean hide=!contacts.getNextContacts().isHideMultipleContacts();
		
		Contacts currentContact=contacts.getNextContacts();
		Contacts nextContact=null;
		while(null!=currentContact){
			currentContact.setHideMultipleContacts(hide);
			nextContact=currentContact;
			currentContact=nextContact.getNextContacts();
		}

		
		if(hide){
			Log.i(TAG, "hideMultipleContactsView");
		}else{
			Log.i(TAG, "UnfoldMultipleContactsView");
		}
		
	}
/*	public void showContacts(){
		Log.i(TAG,"mId=["+getId()+"]mSortKey=["+mSortKey+"]"+"mName=["+getName()+"]+"+"mPhoneNumber:"+getPhoneNumber()+"+ phoneNumberCount=["+mMultipleNumbersContacts.size()+1+"]");
		for(Contacts contacts:mMultipleNumbersContacts){
			Log.i(TAG, "phone=["+contacts.getPhoneNumber()+"]");
		}
	}*/
}
