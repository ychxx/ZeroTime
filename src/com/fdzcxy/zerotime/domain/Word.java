package com.fdzcxy.zerotime.domain;

/**
 * 单词实体类，存放单词有关信息
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class Word {
	/**
	 * 英文拼写
	 */
	private String mSpelling;

	/**
	 * 音标
	 */
	private String mPhoneticAlphabet;

	/**
	 * 中文意思
	 */
	private String mMeaning;
	/**
	 * 编号
	 */
	private int mId;
	/**
	 * 是否记过
	 */
	private boolean mIsRememberance;

	public Word() {
		super();
		this.mSpelling = "12";
		this.mPhoneticAlphabet = "12";
		this.mMeaning = "12";
		this.mId = 1;
		this.mIsRememberance = false;
	}

	public Word(String mSpelling, String mPhoneticAlphabet, String mMeaning,
			int mId, boolean mIsRememberance) {
		super();
		this.mSpelling = mSpelling;
		this.mPhoneticAlphabet = mPhoneticAlphabet;
		this.mMeaning = mMeaning;
		this.mId = mId;
		this.mIsRememberance = mIsRememberance;
	}

	public String getmSpelling() {
		return mSpelling;
	}

	public void setmSpelling(String mSpelling) {
		this.mSpelling = mSpelling;
	}

	public String getmPhoneticAlphabet() {
		return mPhoneticAlphabet;
	}

	public void setmPhoneticAlphabet(String mPhoneticAlphabet) {
		this.mPhoneticAlphabet = mPhoneticAlphabet;
	}

	public String getmMeaning() {
		return mMeaning;
	}

	public void setmMeaning(String mMeaning) {
		this.mMeaning = mMeaning;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public boolean ismIsRememberance() {
		return mIsRememberance;
	}

	public void setmIsRememberance(boolean mIsRememberance) {
		this.mIsRememberance = mIsRememberance;
	}

	@Override
	public String toString() {
		return "word [mSpelling=" + mSpelling + ", mPhoneticAlphabet="
				+ mPhoneticAlphabet + ", mMeaning=" + mMeaning + ", mId=" + mId
				+ ", mIsRememberance=" + mIsRememberance + "]";
	}

}
