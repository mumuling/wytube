package com.cqxb.yecall.until;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceBase {
	private static final String PREFERENCE_SHARE = "YET_SHARE";
	static private Context mContext;
	static public SharedPreferences mPreferences;

	public static SharedPreferences getmPreferences() {
		if (mPreferences == null) {
			mPreferenceBase = new PreferenceBase();
		}
		return mPreferences;
	}

	public static PreferenceBase mPreferenceBase;

	public static void init(Context context) {
		mContext = context;
	}

	public static PreferenceBase getInstance() {
		if (mPreferenceBase == null) {
			mPreferenceBase = new PreferenceBase();
		}
		return mPreferenceBase;
	}

	private PreferenceBase() {
		mPreferences = mContext.getSharedPreferences(PREFERENCE_SHARE, Context.MODE_PRIVATE);
		int a = 0;
		a++;
	}

	// 更换用户后需要删除前一个用户的sharedPreference
	public void cleanPreference() {
		mPreferences.edit().clear().commit();
		/*
		 * String preference_path = "/data/data/" +
		 * mContext.getPackageName().toString()+"/shared_prefs/" +
		 * PREFERENCE_SHARE + ".xml"; File file = new File(preference_path);
		 * if(file.exists()){ file.delete(); }
		 */
	}

}
