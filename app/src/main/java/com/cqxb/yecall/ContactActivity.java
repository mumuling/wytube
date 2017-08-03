package com.cqxb.yecall;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.cqxb.yecall.adapter.ContactListAdapter;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.CharacterParser;
import com.cqxb.yecall.until.PinyinComparator;

public class ContactActivity extends BaseTitleActivity{
	//
	private List<Contacts> cList;
	private ListView listView;
	private ContactListAdapter adapter;
	public static final String action = "jason.broadcast.action";
	private EditText clCet;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		setTitle("联系人");
		setCustomLeftBtn(R.drawable.fh5, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		listView=(ListView)findViewById(R.id.cl_cl);
		initContactList();
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		//给搜索框添加事件 改变联系人列表
		clCet=(EditText)findViewById(R.id.cl_cet);
		clCet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("Contacts", "Contacts onTextChanged"+s+" "+start+" "+before+" "+count);
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.i("Contacts", "Contacts beforeTextChanged"+s+" "+start+" "+after+" "+count);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				Log.i("Contacts", "Contacts afterTextChanged"+s.toString());
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	//初始化联系人
	public void  initContactList(){
		cList=YETApplication.getinstant().getCltList();
		Log.i("联系人","联系人:"+cList.size());
		adapter=new ContactListAdapter(getApplicationContext(), cList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//隐藏键盘
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				Contacts contactBean = cList.get(arg2);
				Intent intent=new Intent(action);
				intent.putExtra("data", "更新拨号界面号码");
				intent.putExtra("callPhone", contactBean.getNumber());
				sendBroadcast(intent);
				finish();				
			}
		});
		listView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//隐藏键盘
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				return false;
			}
		});
		
	}
	
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<Contacts> filterDateList = new ArrayList<Contacts>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = cList;
		}else{
			filterDateList.clear();
			for(Contacts sortModel : cList){
				String name = sortModel.getContactName();
				String number=sortModel.getNumber();
				if(number.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(number).startsWith(filterStr.toString())||name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
//		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
