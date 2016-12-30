package com.fdzcxy.zerotime.sharedpreferences;

import android.content.Context;
import android.content.pm.PackageManager;

import com.fdzcxy.zerotime.Utiles.CommonUtiles;
/**
 * 用户信息的配置
 * @author Administrator
 *
 */
public class UserSetting {

	/**
	 * 用户名
	 */
	private static String mUserName;
	/**
	 * 用户密码
	 */
	private static String mUserPassword;
	/**
	 * 用户配置
	 */
	public static SharePrefHelper mSharePrefHelper;
	private static UserSetting musicSetting = null;

	public static UserSetting getInstance(Context paramContext) {
		if (musicSetting == null)
			musicSetting = new UserSetting(paramContext);
		return musicSetting;
	}
	protected UserSetting(Context paramContext) {
		mSharePrefHelper = SharePrefHelper.getInstance(paramContext);
	}
	public static SharePrefHelper getmSharePrefHelper() {
		return mSharePrefHelper;
	}
	
	public static String getmUserName() {
		mUserName = mSharePrefHelper.getPref("mUserName", "");
		return mUserName;
	}
	public static void setmUserName(String mUserName) {
		UserSetting.mUserName = mUserName;
		mSharePrefHelper.setPref("mUserName", mUserName);
	}
	public static String getmUserPassword() {
		mUserPassword = mSharePrefHelper.getPref("mUserPassword", "");
		return mUserPassword;
	}
	public static void setmUserPassword(String mUserPassword) {
		mSharePrefHelper.setPref("mUserPassword", mUserPassword);
		UserSetting.mUserPassword = mUserPassword;
	}

	
}
