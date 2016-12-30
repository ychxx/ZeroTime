package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.Utiles.ActivityCollector;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;
import com.fdzcxy.zerotime.sharedpreferences.SharePrefHelper;
import com.fdzcxy.zerotime.sharedpreferences.UserSetting;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseActivity extends Activity {
	
	/**
	 * 全局变量
	 */
	public static CommonApplication mApp;
	
	/**
	 * 单词页面的配置
	 */
	public static AbcSetting mAbcSetting;

	/**
	 * 返回上一层按钮事件
	 */
	public OnClickListener mBackOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	/**
	 * 自动放入Activity管理器中
	 */
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}
	/**
	 * 自动将Activity从管理器中删除
	 */
	protected void onDestroy(){
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

}
