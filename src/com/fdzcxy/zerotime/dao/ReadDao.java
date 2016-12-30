package com.fdzcxy.zerotime.dao;

import java.util.List;

import com.fdzcxy.zerotime.domain.News;

public interface ReadDao {
	public List<News> loadNews(String channelName, String channelID);

}
