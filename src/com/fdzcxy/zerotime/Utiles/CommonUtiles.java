package com.fdzcxy.zerotime.Utiles;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.Inflater;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.myalarm.AlarmActivity;

public class CommonUtiles {

	/**
	 * 艾宾浩斯记忆周期 (分钟存储) 1． 第一个记忆周期：5分钟 2． 第二个记忆周期：30分钟 3． 第三个记忆周期：12小时 4．
	 * 第四个记忆周期：1天 5． 第五个记忆周期：2天 6． 第六个记忆周期：4天 7． 第七个记忆周期：7天 8． 第八个记忆周期：15天
	 */
	public static int[] Ebbinghaus = { 5, 30, 12 * 60, 1 * 24 * 60,
			2 * 24 * 60, 4 * 24 * 60, 7 * 24 * 60, 15 * 24 * 60 };

	/**
	 * 新闻页面选中第SELECT_CHANNEL_NUM个频道
	 */
	public static int SELECT_CHANNEL_NUM = 0;

	/**
	 * 新闻页面所以频道名字
	 */
	public static String[] CHANNEL_NAME = { "国内焦点", "国际焦点", "科技焦点", "互联网焦点" };
	/**
	 * 新闻页面所以频道ID
	 */
	public static String[] CHANNEL_ID = { "5572a108b3cdc86cf39001cd",
			"5572a108b3cdc86cf39001ce", "5572a108b3cdc86cf39001d9",
			"5572a108b3cdc86cf39001d1" };
	/**
	 * 新闻Api接口地址
	 */
	public static String NEWS_URL = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
	/**
	 * 短信验证的短信模板
	 */
	public static String TEMPLATE = "您的验证码是%smscode%，有效期为%ttl%分钟。您正在使用%appname%的验证码。";
	/**
	 * 电脑ipv4地址
	 */
	public static final String COMPUTER="http://192.168.1.109:8080";
	/**
	 * 访问登入网址
	 */
	public static final String USER_LOGIN_URL =COMPUTER+"/ZeroTime/UsersMgrServlet?act=login";
	/**
	 * 访问注册网址
	 */
	public static final String USER_REGISTER_URL =COMPUTER+"/ZeroTime/UsersMgrServlet?act=register";
	/**
	 * 访问修改密码网址
	 */
	public static final String USER_FORGOT_PASSWORD_URL =COMPUTER+"/ZeroTime/UsersMgrServlet?act=forgot_password";
	/**
	 * 访问修改密码网址
	 */
	public static final String USER_CHANGE_PASSWORD_URL =COMPUTER+"/ZeroTime/UsersMgrServlet?act=change_password";
	/**
	 * 设置一个闹钟
	 * 
	 * @param context
	 * @param am
	 * @param time
	 *            距离提醒时间还有time(毫秒)
	 * @param ip
	 *            用于标识的ip用于设置多个闹钟的
	 * @param position
	 *            第position天的闹钟
	 */
	public static void alarmSet(Context context, AlarmManager am, int time,
			int ip, int position) {

		long nowTime = System.currentTimeMillis() + time;
		Intent intent = new Intent(context, AlarmActivity.class);
		intent.putExtra("ACT", "alarmSet");
		intent.putExtra("position", position);
		// 利用PendingIntent启动一个Activity
		PendingIntent opretion = PendingIntent.getActivity(context, ip, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, nowTime, opretion);
	}

	/**
	 * 设置一个关闭软件定时器
	 * 
	 * @param context
	 * @param am
	 * @param time
	 *            距离关闭软件时间还有time(分钟级)
	 */
	public static void alarmShut(Context context, AlarmManager am, int time) {

		long nowTime = System.currentTimeMillis() + time * 60000;
		Intent intent = new Intent(context, AlarmActivity.class);
		intent.putExtra("ACT", "alarmShut");
		// 利用PendingIntent启动一个Activity
		PendingIntent opretion = PendingIntent.getActivity(context, 20, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.set(AlarmManager.RTC_WAKEUP, nowTime, opretion);
	}

	/**
	 * 歌曲时间格式转换
	 */
	public static String timeconvert(int time) {
		int min = 0, hour = 0;
		time /= 1000;
		min = time / 60;
		time %= 60;
		return min + ":" + time;
	}

	/**
	 * 验证手机号格式
	 */
	public static boolean isPhoneNum(String phoneNum) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String telRegex = "[1][358]\\d{9}";
		if (TextUtils.isEmpty(phoneNum))
			return false;
		else
			return phoneNum.matches(telRegex);
	}


	/**
	 * 根据音乐播放时间进行从大到小的冒泡排序
	 */
	public static List<Music> BubbleSort(List<Music> musicList) {
		int num = musicList.size();
		for (int i = 0; i < num; i++) {
			for (int j = i; j < num; j++) {
				if (musicList.get(i).getmPlayedTime() < musicList.get(j).getmPlayedTime()) {
					Music music = musicList.get(j);
					musicList.set(j, musicList.get(i));
					musicList.set(i, music);
				}
			}
		}
		return musicList;

	}
	/**
	 * 解析Json转成JsonResult
	 * @param response
	 * @return
	 * @throws JSONException
	 */
	public static JsonResult parseJsonResponse(JSONObject response)
			throws JSONException {

		JSONObject resultPkg = response.getJSONObject("usersdata");
		JsonResult result = new JsonResult();
		result.setResult(resultPkg.getString("result"));
		try {
			result.setDesc(new String(resultPkg.getString("desc").getBytes("iso-8859-1"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result.setRecord(resultPkg.getJSONArray("record"));

		return result;

	}
}
