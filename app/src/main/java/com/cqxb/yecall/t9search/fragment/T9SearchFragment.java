package com.cqxb.yecall.t9search.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cqxb.yecall.R;
import com.cqxb.yecall.t9search.helper.ContactsHelper;
import com.cqxb.yecall.t9search.helper.ContactsIndexHelper;
import com.cqxb.yecall.t9search.helper.ContactsHelper.OnContactsLoad;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.t9search.util.ShareUtil;
import com.cqxb.yecall.t9search.view.ContactsOperationView;
import com.cqxb.yecall.t9search.view.T9TelephoneDialpadView;
import com.cqxb.yecall.t9search.view.ContactsOperationView.OnContactsOperationView;
import com.cqxb.yecall.t9search.view.T9TelephoneDialpadView.OnT9TelephoneDialpadView;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class T9SearchFragment extends BaseFragment implements OnT9TelephoneDialpadView,OnContactsLoad,OnContactsOperationView{
	private static final String TAG="T9SearchFragment";
	private T9TelephoneDialpadView mT9TelephoneDialpadView;
	private ContactsOperationView mContactsOperationView;
	
	private Button mDialpadOperationBtn;
	private boolean mFirstRefreshView=true;
	
	@Override
	public void onResume() {
		if(false==isFirstRefreshView()){
			mContactsOperationView.updateContactsList();
		}else{
			setFirstRefreshView(false);
		}
		
		super.onResume();
	}

	@Override
	public void onDestroy() {
		mT9TelephoneDialpadView.clearT9Input();
		ContactsHelper.getInstance().parseT9InputSearchContacts(null);
		
		List<Contacts> selectedContactsList=new ArrayList<Contacts>();
		selectedContactsList.addAll(ContactsHelper.getInstance().getSelectedContacts().values());
		Log.i(TAG, "onDestroy() selectedContactsList.size()="+selectedContactsList.size());
		for(Contacts cs:selectedContactsList){
			Log.i(TAG, "onDestroy() name=["+cs.getName()+"] phoneNumber=["+cs.getPhoneNumber()+"]");
		}
		
		mContactsOperationView.clearSelectedContacts();
		ContactsHelper.getInstance().clearSelectedContacts();
		super.onDestroy();
	}
	
	@Override
	protected void initData() {
		setContext(getActivity());
		ContactsHelper.getInstance().setOnContactsLoad(this);
		setFirstRefreshView(true);
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_t9_search, container, false);
		mT9TelephoneDialpadView = (T9TelephoneDialpadView) view.findViewById(R.id.t9_telephone_dialpad_layout);
		mT9TelephoneDialpadView.setOnT9TelephoneDialpadView(this);

		mContactsOperationView = (ContactsOperationView)view.findViewById(R.id.contacts_operation_layout);
		mContactsOperationView.setOnContactsOperationView(this);
		boolean startLoad = ContactsHelper.getInstance().startLoadContacts();
		if (true == startLoad) {
			mContactsOperationView.contactsLoading();
		}
		
		mDialpadOperationBtn = (Button) view.findViewById(R.id.dialpad_operation_btn);
		mDialpadOperationBtn.setText(R.string.hide_keyboard);
		return view;
	}

	@Override
	protected void initListener() {
		mDialpadOperationBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clickDialpad();
			}
		});
		
	}
	
	/*start:OnT9TelephoneDialpadView*/
	@Override
	public void onAddDialCharacter(String addCharacter) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDeleteDialCharacter(String deleteCharacter) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDialInputTextChanged(String curCharacter) {
		
		if(TextUtils.isEmpty(curCharacter)){
			ContactsHelper.getInstance().parseT9InputSearchContacts(null);
		}else{
			ContactsHelper.getInstance().parseT9InputSearchContacts(curCharacter);
		}
		mContactsOperationView.updateContactsList(TextUtils.isEmpty(curCharacter));
	}

	@Override
	public void onHideT9TelephoneDialpadView() {
		// TODO Auto-generated method stub
		
	}
	/*end:OnT9TelephoneDialpadView*/
	
	/*start:OnContactsLoad*/
	@Override
	public void onContactsLoadSuccess() {
		ContactsHelper.getInstance().parseT9InputSearchContacts(null);
		mContactsOperationView.contactsLoadSuccess();
		
		//just background printing contacts information
		//ContactsHelper.getInstance().showContactsInfo();
		ContactsIndexHelper.getInstance().praseContacts(ContactsHelper.getInstance().getBaseContacts());
		//ContactsIndexHelper.getInstance().showContactsInfo();
		
	}

	@Override
	public void onContactsLoadFailed() {
		mContactsOperationView.contactsLoadFailed();
	}
	/*end:OnContactsLoad*/
	
	
	/*start:OnContactsOperationView*/
	@Override
	public void onListItemClick(Contacts contacts,int position){
		ContactsHelper.getInstance().parseT9InputSearchContacts(null);
		mContactsOperationView.updateContactsList(true);
	}

	@Override
	public void onAddContactsSelected(Contacts contacts) {
		if(null!=contacts){
			Log.i(TAG, "onAddContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
			Toast.makeText(getContext(),"Add ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
			ContactsHelper.getInstance().addSelectedContacts(contacts);
		}
	}


	@Override
	public void onRemoveContactsSelected(Contacts contacts) {
		if(null!=contacts){
			Log.i(TAG, "onRemoveContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
			Toast.makeText(getContext(),"Remove ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
			ContactsHelper.getInstance().removeSelectedContacts(contacts);
		}
	}
	
	@Override
	public void onContactsCall(Contacts contacts) {
		//Toast.makeText(mContext, "onContactsCall"+contacts.getPhoneNumber(), Toast.LENGTH_SHORT).show();
		if(null!=contacts){
			 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+contacts.getPhoneNumber()));
			 startActivity(intent);
		}
	}


	@Override
	public void onContactsSms(Contacts contacts) {
		//Toast.makeText(mContext, "onContactsSms"+contacts.getPhoneNumber(), Toast.LENGTH_SHORT).show();
		if(null!=contacts){
			ShareUtil.shareTextBySms(getContext(), contacts.getPhoneNumber(), null);
		}
	}
	/*end:OnContactsOperationView*/
	
	public boolean isFirstRefreshView() {
		return mFirstRefreshView;
	}

	public void setFirstRefreshView(boolean firstRefreshView) {
		mFirstRefreshView = firstRefreshView;
	}

	
	private void clickDialpad() {
		if (mT9TelephoneDialpadView.getT9TelephoneDialpadViewVisibility() == View.VISIBLE) {
			mT9TelephoneDialpadView.hideT9TelephoneDialpadView();
			mDialpadOperationBtn.setText(R.string.display_keyboard);
		} else {
			mT9TelephoneDialpadView.showT9TelephoneDialpadView();
			mDialpadOperationBtn.setText(R.string.hide_keyboard);
		}
	}

}
