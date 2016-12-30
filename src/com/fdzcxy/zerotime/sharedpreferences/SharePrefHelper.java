package com.fdzcxy.zerotime.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
/**
 * SharedPreferences帮助类
 * 存储用户个人设置数据
 * @author Administrator
 *
 */
public class SharePrefHelper {
	private static SharePrefHelper sharePrefHelper = null;
	/**
	 * 保存的文件名
	 */
	private static final String PREFERENCE_NAME = "zerotime";
	private SharedPreferences prefs = null;

	public static SharePrefHelper getInstance(Context paramContext) {
		if (sharePrefHelper == null)
			sharePrefHelper = new SharePrefHelper(paramContext);
		return sharePrefHelper;
	}

	public static SharePrefHelper newInstance(Context paramContext) {
		return new SharePrefHelper(paramContext);
	}

	protected SharePrefHelper(Context paramContext) {
		try {
			this.prefs = paramContext.createPackageContext(
					paramContext.getPackageName(), 2).getSharedPreferences(
					PREFERENCE_NAME, 0);
			return;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
	}

	public float getPref(String paramString, float paramFloat) {
		return this.prefs.getFloat(paramString, paramFloat);
	}

	public int getPref(String paramString, int paramInt) {
		return this.prefs.getInt(paramString, paramInt);
	}

	public long getPref(String paramString, long paramLong) {
		return this.prefs.getLong(paramString, paramLong);
	}

	public String getPref(String paramString1, String paramString2) {
		return this.prefs.getString(paramString1, paramString2);
	}

	public boolean getPref(String paramString, Boolean paramBoolean) {
		return this.prefs.getBoolean(paramString, paramBoolean.booleanValue());
	}

	public boolean hasPrefWithKey(String paramString) {
		return this.prefs.contains(paramString);
	}

	public void setPref(String paramString, float paramFloat) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putFloat(paramString, paramFloat);
		localEditor.commit();
	}

	public void setPref(String paramString, int paramInt) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putInt(paramString, paramInt);
		localEditor.commit();
	}

	public void setPref(String paramString, long paramLong) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putLong(paramString, paramLong);
		localEditor.commit();
	}

	public void setPref(String paramString1, String paramString2) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putString(paramString1, paramString2);
		localEditor.commit();
	}

	public void setPref(String paramString, boolean paramBoolean) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putBoolean(paramString, paramBoolean);
		localEditor.commit();
	}

	public boolean removePref(String paramString) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.remove(paramString);
		return localEditor.commit();
	}

	public boolean clearPref() {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.clear();
		return localEditor.commit();
	}
}
