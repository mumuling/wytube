/*
Digit.java
Copyright (C) 2010  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package org.linphone.ui;

import java.util.HashMap;

import org.linphone.InCallActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;

import com.cqxb.yecall.R;

import org.linphone.core.LinphoneCore;
import org.linphone.mediastream.Log;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Digit extends Button implements AddressAware {

	private AddressText mAddress;
	@Override
	public void setAddressWidget(AddressText address) {
		mAddress = address;
	}
	
	private boolean mPlayDtmf;
	public void setPlayDtmf(boolean play) {
		mPlayDtmf = play;
	}
	
	private SoundPool sp;//声明一个SoundPool 
	private HashMap<Integer, Integer> soundPoolMap;
	float volume;
	public void setTouchToneParam(SoundPool sp,HashMap<Integer, Integer> soundPoolMap,float volume) {
		this.sp = sp;
		this.soundPoolMap = soundPoolMap;
		this.volume = volume;
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int before,
			int after) {
		super.onTextChanged(text, start, before, after);
		
		if (text == null || text.length() < 1) {
			return;
		}

		DialKeyListener lListener = new DialKeyListener();
		setOnClickListener(lListener);
		setOnTouchListener(lListener);
		
		if ("0+".equals(text)) {
			setOnLongClickListener(lListener);
		}
	}
	
	public Digit(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		setLongClickable(true);
	}
	
	public Digit(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLongClickable(true);
	}

	public Digit(Context context) {
		super(context);
		setLongClickable(true);
	}

	private class DialKeyListener implements OnClickListener, OnTouchListener, OnLongClickListener {
		final char mKeyCode;
		boolean mIsDtmfStarted;

		DialKeyListener() {
			mKeyCode = Digit.this.getText().subSequence(0, 1).charAt(0);
		}

		private boolean linphoneServiceReady() {
			if (!LinphoneService.isReady()) {
				Log.w("Service is not ready while pressing digit");
				Toast.makeText(getContext(), getContext().getString(R.string.skipable_error_service_not_ready), Toast.LENGTH_SHORT).show();
				return false;
			}
			return true;
		}

		public void onClick(View v) {
			if (mPlayDtmf) {
				if (!linphoneServiceReady()) return;
				//volume = 1;
				//播放按键拨号音
//				if(sp!=null &&  soundPoolMap!=null){
//					switch (mKeyCode) {
//					case '1':
//						sp.play(soundPoolMap.get(1), volume, volume, 0, 0, 0);
//						break;
//					case '2':
//						sp.play(soundPoolMap.get(2), volume, volume, 0, 0, 0);
//						break;
//					case '3':
//						sp.play(soundPoolMap.get(3), volume, volume, 0, 0, 0);
//						break;
//					case '4':
//						sp.play(soundPoolMap.get(4), volume, volume, 0, 0, 0);
//						break;
//					case '5':
//						sp.play(soundPoolMap.get(5), volume, volume, 0, 0, 0);
//						break;
//					case '6':
//						sp.play(soundPoolMap.get(6), volume, volume, 0, 0, 0);
//						break;
//					case '7':
//						sp.play(soundPoolMap.get(7), volume, volume, 0, 0, 0);
//						break;
//					case '8':
//						sp.play(soundPoolMap.get(8), volume, volume, 0, 0, 0);
//						break;
//					case '9':
//						sp.play(soundPoolMap.get(9), volume, volume, 0, 0, 0);
//						break;
//					case '*':
//						sp.play(soundPoolMap.get(10), volume, volume, 0, 0, 0);
//						break;
//					case '0':
//						sp.play(soundPoolMap.get(11), volume, volume, 0, 0, 0);
//						break;
//					case '#':
//						sp.play(soundPoolMap.get(12), volume, volume, 0, 0, 0);
//						break;
//					default:
//						sp.play(soundPoolMap.get(1), volume, volume, 0, 0, 0);
//						break;
//					}
//				}
				
				LinphoneCore lc = LinphoneManager.getLc();
				lc.stopDtmf();
				mIsDtmfStarted =false;
				if (lc.isIncall()) {
					lc.sendDtmf(mKeyCode);
				}
			}
			
			if (mAddress != null) {
				int lBegin = mAddress.getSelectionStart();
				if (lBegin == -1) {
					lBegin = mAddress.length();
				}
				if (lBegin >= 0) {
					mAddress.getEditableText().insert(lBegin,String.valueOf(mKeyCode));
				}
			}
		}

		public boolean onTouch(View v, MotionEvent event) {
			if (!mPlayDtmf) return false;
			if (!linphoneServiceReady()) return true;

			if (InCallActivity.isInstanciated()) {
				InCallActivity.instance().resetControlsHidingCallBack();
			}
			
			LinphoneCore lc = LinphoneManager.getLc();
			if (event.getAction() == MotionEvent.ACTION_DOWN && !mIsDtmfStarted) {
				LinphoneManager.getInstance().playDtmf(getContext().getContentResolver(), mKeyCode);
				mIsDtmfStarted = true;
			} else {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					lc.stopDtmf();
					mIsDtmfStarted = false;
				}
			}
			return false;
		}
		
		public boolean onLongClick(View v) {
			if (mPlayDtmf) {
				if (!linphoneServiceReady()) return true;
				// Called if "0+" dtmf
				LinphoneCore lc = LinphoneManager.getLc();
				lc.stopDtmf();
			}
			
			if (mAddress == null) return true;

			int lBegin = mAddress.getSelectionStart();
			if (lBegin == -1) {
				lBegin = mAddress.getEditableText().length();
			}
			if (lBegin >= 0) {
			mAddress.getEditableText().insert(lBegin,"+");
			}
			return true;
		}
	};
	

}
