package com.cqxb.yecall.until;

import java.util.List;

import org.linphone.mediastream.Log;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.cqxb.yecall.Smack;
import com.cqxb.yecall.YETApplication;
import com.cqxb.yecall.bean.ContactBean;
import com.cqxb.yecall.t9search.model.Contacts;

public class PhoneCallAuthUtil {
	
	private TelephonyManager tm;
	private String number="";
	private int type;
	private boolean isCalling=false;
	public void callState(){
		//如果是来电  
		if(tm==null)
        tm =  (TelephonyManager)YETApplication.getContext().getSystemService(Service.TELEPHONY_SERVICE);
        
     // 手动注册对PhoneStateListener中的listen_call_state状态进行监听  
        tm.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);  
        System.out.println("======== AutoAnswerReceiver  来电状态"+tm.getCallState());
	}
	
	 
	/*** 
	    * 继承PhoneStateListener类，我们可以重新其内部的各种监听方法 
	    *然后通过手机状态改变时，系统自动触发这些方法来实现我们想要的功能 
	    */  
    class MyPhoneStateListener extends PhoneStateListener{  
  
        @Override  
        public void onCallStateChanged(int state, String incomingNumber) { 
        	//是自己拨打的回呼
        	SettingInfo.init(YETApplication.getContext());
			String callBackSelf = SettingInfo.getParams(PreferenceBean.CALLBACKSELF, "");
            String name = SettingInfo.getParams(PreferenceBean.CALLBACKNAME, "");
            //初始化名字通过通话联系人来得到名字
            name="";
        	String callBackStart = SettingInfo.getParams(PreferenceBean.CALLBACKSTART, "");
        	if(!"".equals(callBackStart)){
        		switch (state) {  
                case TelephonyManager.CALL_STATE_IDLE:
                	handler.removeCallbacks(runnable);
                	//相当于挂断
                    System.out.println("电话状态:手机空闲起来了");
                    type=CallLog.Calls.INCOMING_TYPE;
                    if(isCalling){
                    	if(!"".equals(callBackSelf)){
                    		type=CallLog.Calls.OUTGOING_TYPE;
                    	}else {
                    		type=CallLog.Calls.INCOMING_TYPE;
						}
                    }else {
                    	type=CallLog.Calls.MISSED_TYPE;
					}
                    List<Contacts> cltList = YETApplication.getinstant().getCltList();
            		for (int i = 0; i < cltList.size(); i++) {
            			if(cltList.get(i).getNumber().equals(number)){
            				name=cltList.get(i).getContactName();
            				break;
            			}
            		}
            		if("".equals(name)){
                    	name=number;
                    }
            		Log.e("", "========>>name:"+name);
                    //插入通话记录
                    try {
                    	ContactBase.insertCallLog(YETApplication.getContext(), name, number, type, TimeRender.secToTime(callTime), System.currentTimeMillis());
					} catch (Exception e) {
						Log.e("PhoneCallAuthUtil line 66 ==>>> "+e.getLocalizedMessage());
					}
                    List<Contacts> phoneCallLists = new ContactBase(YETApplication.getContext()).getPhoneCallLists();
                    YETApplication.getinstant().setThjl(phoneCallLists);
                    //清空数据
                    SettingInfo.setParams(PreferenceBean.CALLBACKSELF, "");
                    SettingInfo.setParams(PreferenceBean.CALLBACKNAME, "");
                	SettingInfo.setParams(PreferenceBean.CALLBACKSTART, "");
                	callTime=0;
                	isCalling=false;
                	//刷新纪录
                	Intent intent=new Intent(Smack.action);
            		intent.putExtra("missCalled", "missCalled");
            		YETApplication.getContext().sendBroadcast(intent);
                    break;  
                case TelephonyManager.CALL_STATE_RINGING:  
                	//相当于振铃
                    System.out.println("电话状态:手机铃声响了，来电号码:"+incomingNumber);
                    number=incomingNumber;
                    break;  
                case TelephonyManager.CALL_STATE_OFFHOOK:  
                	//来电接通
                    System.out.println("电话状态:电话被挂起了");
                    isCalling=true;
                    handler.post(runnable);
                default:  
                    break;  
                }  
        	}
            super.onCallStateChanged(state, incomingNumber);  
        }  
          
    }
    
    private int callTime=0;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			callTime++;
			if(callTime==1){
				handler.removeCallbacks(runnable);
			}
			handler.postDelayed(runnable, 1000);
		}
	};
	
}
