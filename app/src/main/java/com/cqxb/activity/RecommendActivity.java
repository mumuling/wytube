package com.cqxb.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.cqxb.yecall.BaseTitleActivity;
import com.cqxb.yecall.R;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class RecommendActivity extends BaseTitleActivity implements
		OnClickListener {

	// QQ
	private int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	private int mExtarFlag = 0x00;
	private QQShare mQQShare = null;
	private static final String mAppid = "1104417503";
	private boolean isSendToWX = true;
	// 微信
	public static final String APP_ID = "wxa0f9af7496bc74a1"; // 分享到微信
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend);
		setTitle("推荐好友");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.ll_sms_recommend).setOnClickListener(this);
		findViewById(R.id.ll_wx_recommend).setOnClickListener(this);
		findViewById(R.id.ll_qq_recommend).setOnClickListener(this);
		findViewById(R.id.ll_friendCircle_recommend).setOnClickListener(this);
		
		mQQShare = new QQShare(this,Tencent.createInstance(mAppid, this).getQQToken());//QQ
		
		// 微信
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		// 将该app注册到微信
		api.registerApp(APP_ID);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.ll_sms_recommend){
			getShareURL();
			sendMessageToSMS();
		}else if(v.getId()==R.id.ll_wx_recommend){
			isSendToWX =true;
			sendMessageToWXOrFriendCircle();
		}else if(v.getId()==R.id.ll_qq_recommend){
			sendMessageToQQ();
		}else if(v.getId()==R.id.ll_friendCircle_recommend){
			isSendToWX =false;
			sendMessageToWXOrFriendCircle();
		}
	}

	private void sendMessageToSMS() {
		Uri smsToUri = Uri.parse("smsto:");
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", "我在使用云翌电话，感觉挺好用的，打电话不要钱，你也快来装一个吧！" + getShareURL());  		 
		startActivity(intent);

	}

	private void sendMessageToQQ() {
		final Bundle params = new Bundle();
		if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
			params.putString(QQShare.SHARE_TO_QQ_TITLE, "云翌电话");
			params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, getShareURL());
			params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "摘要:我在使用云翌电话，感觉挺好用的，打电话不要钱，你也快来装一个吧，点击下载！");
		}
		if (shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
			params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,
					"");
		} else {
			params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "");
		}
		params.putString(
				shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE ? QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL
						: QQShare.SHARE_TO_QQ_IMAGE_URL,
						"");
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "云翌电话");
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
		params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
		if (shareType == QQShare.SHARE_TO_QQ_TYPE_AUDIO) {
			params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, getShareURL());
		}
		if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN) != 0) {
			//Toast.makeText(this,"在好友选择列表会自动打开分享到qzone的弹窗~~~", Toast.LENGTH_SHORT).show();
		} else if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE) != 0) {
			//Toast.makeText(this, "在好友选择列表隐藏了qzone分享选项~~~",Toast.LENGTH_SHORT).show();

		}
		doShareToQQ(params);
	}
	 /**
     * 用异步方式启动分享
     * @param params
     */
    private void doShareToQQ(final Bundle params) {
    	final Activity activity = RecommendActivity.this;
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mQQShare.shareToQQ(activity, params, new IUiListener() {

                    @Override
                    public void onCancel() {
                        if(shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE){
                        }
                    }

                    @Override
                    public void onComplete(Object response) {
                    }

                    @Override
                    public void onError(UiError e) {
                    }
                });
            }
        }).start();
    }
    
	private void sendMessageToWXOrFriendCircle() {

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = getShareURL();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "云翌电话";
		msg.description = "摘要:我在使用云翌电话，感觉挺好用的，打电话不要钱，你也快来装一个吧，点击下载！";
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.tb144_144);
		msg.thumbData = Util.bmpToByteArray(thumb, true);		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = isSendToWX? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
	
	private String getShareURL() {		
		String shareURL = "http://web.123667.com/yecall/download.jsp?rid=";
		TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);		
		String localNumber  = phoneMgr.getLine1Number().trim();
		if (localNumber.startsWith("+")) {			
			localNumber = localNumber.substring(3);
		}
		return shareURL + localNumber;
		
	}
}
