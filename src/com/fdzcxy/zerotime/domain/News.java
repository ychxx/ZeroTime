package com.fdzcxy.zerotime.domain;

import java.io.Serializable;

/**
 * 新闻实体类
 * @author Administrator
 *实现序列化，bundle传递
 */
public class News implements Serializable{
	/**
	 * 标题
	 */
	private String mTitle;
	/**
	 * 简介
	 */
	private String mDese;
	/**
	 * 来源
	 */
	private String mSource;
	/**
	 * 时间
	 */
	private String mPubDate;
	/**
	 * 网址链接
	 */
	private String mLink;
	
	public News() {
		super();
	}
	public News(String mTitle, String mDese, String mSource, String mPubDate,
			String mLink) {
		super();
		this.mTitle = mTitle;
		this.mDese = mDese;
		this.mSource = mSource;
		this.mPubDate = mPubDate;
		this.mLink = mLink;
	}
	
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public String getmDese() {
		return mDese;
	}
	public void setmDese(String mDese) {
		this.mDese = mDese;
	}
	public String getmSource() {
		return mSource;
	}
	public void setmSource(String mSource) {
		this.mSource = mSource;
	}
	public String getmPubDate() {
		return mPubDate;
	}
	public void setmPubDate(String mPubDate) {
		this.mPubDate = mPubDate;
	}
	public String getmLink() {
		return mLink;
	}
	public void setmLink(String mLink) {
		this.mLink = mLink;
	}
	@Override
	public String toString() {
		return "News [mTitle=" + mTitle + ", mDese=" + mDese + ", mSource="
				+ mSource + ", mPubDate=" + mPubDate + ", mLink=" + mLink + "]";
	}
	
}
