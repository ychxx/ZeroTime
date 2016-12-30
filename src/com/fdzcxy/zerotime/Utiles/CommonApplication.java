package com.fdzcxy.zerotime.Utiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.fragment.ReadFragment;
import com.fdzcxy.zerotime.sharedpreferences.MusicSetting;
import com.fdzcxy.zerotime.sharedpreferences.UserSetting;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

public class CommonApplication extends Application {

	/**
	 * 当前播放的 列表
	 */
	public static List<Music> nowMusic = new ArrayList<Music>();
	/**
	 * 我的音乐 列表
	 */
	public static List<Music> myMusic = new ArrayList<Music>();
	/**
	 * 我的最爱 列表
	 */
	public static List<Music> loveMusic = new ArrayList<Music>();
	/**
	 * 最近播放 列表
	 */
	public static List<Music> nearestMusic = new ArrayList<Music>();
	/**
	 * MusicFragment标识 用于更新播放框信息 的Handler标识
	 */
	public static int MUSICFRAGMENT_REFRESHPLAY = 200;
	/**
	 * MyMusicActivity标识 用于更新播放框信息 的Handler标识
	 */
	public static int MYMUSICACTIVITY_REFRESHPLAY = 201;
	/**
	 * MusicLoveActivity标识 用于更新播放框信息 的Handler标识
	 */
	public static int MUSICLOVEACTIVITY_REFRESHPLAY = 202;
	/**
	 * MusicNearestActivity标识 用于更新播放框信息 的Handler标识
	 */
	public static int MUSICNEARESTACTIVITY_REFRESHPLAY = 203;
	/**
	 * ReadFragment标识 用于更新播放框信息 的Handler标识
	 */
	public static int READFRAGMENT_REFRESHLISTVIEW = 300;

	/**
	 * 请求队列
	 */
	private RequestQueue mRequestQueue;
	/**
	 * 用户信息的配置
	 */
	public static UserSetting mUserSetting;
	// 初始化全局变量
	public void onCreate() {
		super.onCreate();
		// 设置ApiStoreSDK个人的apikey
		ApiStoreSDK.init(this, "e8e812175acc027c6ded2767cc488b6d");
		// 设置BmobSDK个人的apikey
		//Bmob.initialize(this, "9d93e99ed5e5abd742ca4bc2d61e9e4b");
		Bmob.initialize(this, "4d8eda44fa4f63bc53df3869e31e7c6c");
		// 根据配置文件初始化音乐播放模式
		MusicSetting musicSetting = MusicSetting
				.getInstance(CommonApplication.this);
		MusicHelper.PLAY_MODE = musicSetting.getmMusicMode();

		mUserSetting = UserSetting.getInstance(CommonApplication.this);
		mUserName = mUserSetting.getmUserName();
		mUserPassword = mUserSetting.getmUserPassword();
	}

	/**
	 * 获取请求队列
	 * 
	 * @return
	 */
	public RequestQueue getRequestQueue() {
		// 如果请求队列为空，那么通过valley来获得，将上下文传入
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}

	/**
	 * 将某某操作加入请求队列
	 * 
	 * @param req
	 * @param tag
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {

		// 设置标签
		req.setTag(TextUtils.isEmpty(tag) ? "BRSC" : tag);
		// 加入请求队列中
		getRequestQueue().add(req);

	}

	/**
	 * 当前用户名
	 */
	private String mUserName;
	/**
	 * 当前用户密码
	 */
	private String mUserPassword;

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
		mUserSetting.setmUserName(mUserName);
	}

	public String getmUserPassword() {
		return mUserPassword;
	}

	public void setmUserPassword(String mUserPassword) {
		this.mUserPassword = mUserPassword;
		mUserSetting.setmUserPassword(mUserPassword);
	}

}
