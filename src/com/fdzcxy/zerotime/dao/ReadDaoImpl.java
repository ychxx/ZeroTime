package com.fdzcxy.zerotime.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.activity.MyMusicActivity;
import com.fdzcxy.zerotime.db.ABCTable;
import com.fdzcxy.zerotime.db.MusicDB;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.domain.News;
import com.fdzcxy.zerotime.domain.Word;
import com.fdzcxy.zerotime.fragment.ReadFragment;

public class ReadDaoImpl implements ReadDao {
	private ArrayList<News> newsList = new ArrayList<News>();
	/**
	 * 全局变量
	 */
	private static CommonApplication mApp ;
	public ReadDaoImpl(Activity activity) {
		mApp= (CommonApplication) activity.getApplication();
	}
	@Override
	public List<News> loadNews(String channelName, String channelID) {
		// TODO Auto-generated method stub
		Parameters para = new Parameters();
		para.put("channelId", channelID);
		para.put("channelName", channelName);
		para.put("page", "1");
		newsList.removeAll(newsList);
		ApiStoreSDK.execute(CommonUtiles.NEWS_URL, ApiStoreSDK.GET, para,
				new ApiCallBack() {

					@Override
					public void onSuccess(int status, String responseString) {
						// JSON解析
						try {
							JSONArray jSONArray = new JSONObject(responseString)
									.getJSONObject("showapi_res_body")
									.getJSONObject("pagebean")
									.getJSONArray("contentlist");

							for (int i = 0; i < jSONArray.length(); i++) {

								JSONObject item = jSONArray.getJSONObject(i);
								News news = new News();
								news.setmTitle(item.getString("title"));
								news.setmDese(item.getString("desc"));
								news.setmSource(item.getString("source"));
								news.setmPubDate(item.getString("pubDate"));
								news.setmLink(item.getString("link"));
								newsList.add(news);
							}
							//发送Handle刷新页面请求
							Bundle bundle = new Bundle();
							ArrayList list = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
							list.add(newsList);
							bundle.putParcelableArrayList("newsList",list);
							Message msg = new Message();
							
							msg.what = mApp.READFRAGMENT_REFRESHLISTVIEW;
							msg.setData(bundle);
							new ReadFragment().mHandlerRefreshListView.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onComplete() {
					}

					@Override
					public void onError(int status, String responseString,
							Exception e) {

					}

				});

		return newsList;
	}

}
