package com.fdzcxy.zerotime.domain;

import java.io.Serializable;

/**
 * 音乐类，存放音乐有关信息
 * 
 */
public class Music implements Serializable {

	/**
	 * 歌名
	 */
	private String mName;
	/**
	 * 歌手
	 */
	private String mAuthor;
	/**
	 * 时长
	 */
	private int mTime;
	/**
	 * 歌曲id,唯一主键
	 */
	private int mID;
	/**
	 * 歌曲编号,排序
	 */
	private int mNumber;

	/**
	 * 是否是最爱的
	 */
	private boolean mIsLove;

	/**
	 * 是否在播放
	 */
	private boolean mIsPlay;
	/**
	 * 歌曲路径
	 */
	private String mUri;
	/**
	 * 最近播放音乐的时间 0代表最近没有播放过该首音乐
	 */
	private int mPlayedTime = 0;

	public Music() {
		super();
	}

	// public Music(String mName, String mAuthor, int mTime, int mNumber,
	// boolean mIsLove, boolean mIsPlay, String mUri) {
	// super();
	// this.mName = mName;
	// this.mAuthor = mAuthor;
	// this.mTime = mTime;
	// this.mNumber = mNumber;
	// this.mIsLove = mIsLove;
	// this.mIsPlay = mIsPlay;
	// this.mUri = mUri;
	// }

	public Music(String mName, String mAuthor, int mTime, int mID, int mNumber,
			boolean mIsLove, boolean mIsPlay, String mUri, int mPlayedTime) {
		super();
		this.mName = mName;
		this.mAuthor = mAuthor;
		this.mTime = mTime;
		this.mID = mID;
		this.mNumber = mNumber;
		this.mIsLove = mIsLove;
		this.mIsPlay = mIsPlay;
		this.mUri = mUri;
		this.mPlayedTime = mPlayedTime;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmAuthor() {
		return mAuthor;
	}

	public void setmAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public int getmTime() {
		return mTime;
	}

	public void setmTime(int mTime) {
		this.mTime = mTime;
	}

	public int getmID() {
		return mID;
	}

	public void setmID(int mID) {
		this.mID = mID;
	}

	public int getmNumber() {
		return mNumber;
	}

	public void setmNumber(int mNumber) {
		this.mNumber = mNumber;
	}

	public boolean getmIsLove() {
		return mIsLove;
	}

	public void setmIsLove(boolean mIsLove) {
		this.mIsLove = mIsLove;
	}

	public boolean getmIsPlay() {
		return mIsPlay;
	}

	public void setmIsPlay(boolean mIsPlay) {
		this.mIsPlay = mIsPlay;
	}

	public String getmUri() {
		return mUri;
	}

	public void setmUri(String mUri) {
		this.mUri = mUri;
	}

	public int getmPlayedTime() {
		return mPlayedTime;
	}

	public void setmPlayedTime(int mPlayedTime) {
		this.mPlayedTime = mPlayedTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Music other = (Music) obj;
		if (mAuthor == null) {
			if (other.mAuthor != null)
				return false;
		} else if (!mAuthor.equals(other.mAuthor))
			return false;
		if (mID != other.mID)
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (mTime != other.mTime)
			return false;
		if (mUri == null) {
			if (other.mUri != null)
				return false;
		} else if (!mUri.equals(other.mUri))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Music [mName=" + mName + ", mAuthor=" + mAuthor + ", mTime="
				+ mTime + ", mID=" + mID + ", mNumber=" + mNumber
				+ ", mIsLove=" + mIsLove + ", mIsPlay=" + mIsPlay + ", mUri="
				+ mUri + ", mPlayedTime=" + mPlayedTime + "]";
	}

}
