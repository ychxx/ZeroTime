package com.fdzcxy.zerotime.sharedpreferences;

import android.content.Context;
import android.content.pm.PackageManager;

import com.fdzcxy.zerotime.Utiles.CommonUtiles;
/**
 * 单词页面的配置
 * @author Administrator
 *
 */
public class AbcSetting {
	/**
	 * 每日背诵单词个数
	 */
	private int mWordNum;
	/**
	 * 提醒方式0.艾宾浩斯 1.自定义 
	 */
	private int mMemoryWay;
	/**
	 * 八个记忆周期(分钟存储)
	 */
	private int[] mMemoryCycle = new int[8];

	/**
	 * 用户配置
	 */
	public static SharePrefHelper mSharePrefHelper;
	private static AbcSetting abcSetting = null;

	public static AbcSetting getInstance(Context paramContext) {
		if (abcSetting == null)
			abcSetting = new AbcSetting(paramContext);
		return abcSetting;
	}
	protected AbcSetting(Context paramContext) {
		mSharePrefHelper = SharePrefHelper.getInstance(paramContext);
		getAbcSetting();
	}
	/**
	 * 获取本地单词页面配置(分钟存储)
	 */
	public void getAbcSetting(){
		this.mWordNum = mSharePrefHelper.getPref("wordNumber", 12);

		this.mMemoryWay = mSharePrefHelper.getPref("MemoryWay", 0);
		this.mMemoryCycle[0]=mSharePrefHelper.getPref("MemoryCycle0", CommonUtiles.Ebbinghaus[0]);
		this.mMemoryCycle[1]=mSharePrefHelper.getPref("MemoryCycle1", CommonUtiles.Ebbinghaus[1]);
		this.mMemoryCycle[2]=mSharePrefHelper.getPref("MemoryCycle2", CommonUtiles.Ebbinghaus[2]);
		this.mMemoryCycle[3]=mSharePrefHelper.getPref("MemoryCycle3", CommonUtiles.Ebbinghaus[3]);
		this.mMemoryCycle[4]=mSharePrefHelper.getPref("MemoryCycle4", CommonUtiles.Ebbinghaus[4]);
		this.mMemoryCycle[5]=mSharePrefHelper.getPref("MemoryCycle5", CommonUtiles.Ebbinghaus[5]);
		this.mMemoryCycle[6]=mSharePrefHelper.getPref("MemoryCycle6", CommonUtiles.Ebbinghaus[6]);
		this.mMemoryCycle[7]=mSharePrefHelper.getPref("MemoryCycle7", CommonUtiles.Ebbinghaus[7]);
	}
	/**
	 * 设置本地单词页面配置
	 */
	public void setAbcSetting(){
		mSharePrefHelper.setPref("wordNumber", mWordNum);
		mSharePrefHelper.setPref("MemoryWay", mMemoryWay);
		mSharePrefHelper.setPref("MemoryCycle0", this.mMemoryCycle[0]);
		mSharePrefHelper.setPref("MemoryCycle1", this.mMemoryCycle[1]);
		mSharePrefHelper.setPref("MemoryCycle2", this.mMemoryCycle[2]);
		mSharePrefHelper.setPref("MemoryCycle3", this.mMemoryCycle[3]);
		mSharePrefHelper.setPref("MemoryCycle4", this.mMemoryCycle[4]);
		mSharePrefHelper.setPref("MemoryCycle5", this.mMemoryCycle[5]);
		mSharePrefHelper.setPref("MemoryCycle6", this.mMemoryCycle[6]);
		mSharePrefHelper.setPref("MemoryCycle7", this.mMemoryCycle[7]);
	}
	public int getmWordNum() {
		return mWordNum;
	}
	public void setmWordNum(int mWordNum) {
		this.mWordNum = mWordNum;
	}
	public int getmMemoryWay() {
		return mMemoryWay;
	}
	public void setmMemoryWay(int mMemoryWay) {
		this.mMemoryWay = mMemoryWay;
	}
	public int[] getmMemoryCycle() {
		return mMemoryCycle;
	}
	public void setmMemoryCycle(int[] mMemoryCycle) {
		this.mMemoryCycle = mMemoryCycle;
	}
	public static SharePrefHelper getmSharePrefHelper() {
		return mSharePrefHelper;
	}
	public static void setmSharePrefHelper(SharePrefHelper mSharePrefHelper) {
		AbcSetting.mSharePrefHelper = mSharePrefHelper;
	}
	
}
