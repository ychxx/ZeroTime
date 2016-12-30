package com.fdzcxy.zerotime.sharedpreferences;

import android.content.Context;
import android.content.pm.PackageManager;

import com.fdzcxy.zerotime.Utiles.CommonUtiles;
/**
 * 音乐页面的配置
 * @author Administrator
 *
 */
public class MusicSetting {

	/**
	 * 音乐播放模式配置
	 */
	private static int mMusicMode =0;
	
	/**
	 * 用户配置
	 */
	public static SharePrefHelper mSharePrefHelper;
	private static MusicSetting musicSetting = null;

	public static MusicSetting getInstance(Context paramContext) {
		if (musicSetting == null)
			musicSetting = new MusicSetting(paramContext);
		return musicSetting;
	}
	protected MusicSetting(Context paramContext) {
		mSharePrefHelper = SharePrefHelper.getInstance(paramContext);
	}
	public static SharePrefHelper getmSharePrefHelper() {
		return mSharePrefHelper;
	}
	public int getmMusicMode() {
		this.mMusicMode = mSharePrefHelper.getPref("MusicMode", 3);
		return this.mMusicMode;
	}
	public void setmMusicMode(int mMusicMode) {
		this.mMusicMode = mMusicMode;
		mSharePrefHelper.setPref("MusicMode", mMusicMode);
	}
	
	
}
