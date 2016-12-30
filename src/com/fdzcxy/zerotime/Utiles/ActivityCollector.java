package com.fdzcxy.zerotime.Utiles;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
/**
 * Activity活动管理器
 * @author Administrator
 *
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();
	/**
	 * 将Activity放入管理器中
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		activities.add(activity);
	}
	/**
	 * 将Activity从管理器中删除
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}
	/**
	 * 删除管理器中所有Activity，即完全退出app
	 * @param activity
	 */
	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
