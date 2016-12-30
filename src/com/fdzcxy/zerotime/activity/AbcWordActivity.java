package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.EnglishWord;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.adapter.LearnAdapter;
import com.fdzcxy.zerotime.dao.ABCDaoImpl;
import com.fdzcxy.zerotime.dao.ACBDao;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;
import com.fdzcxy.zerotime.domain.Word;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

public class AbcWordActivity extends BaseActivity implements OnClickListener {
	/**
	 * 单词list
	 */
	private static List<Word> mWordList = new ArrayList<Word>();
	/**
	 * 返回按钮
	 */
	private Button mWordBreak;
	/**
	 * 拼写
	 */
	private TextView mSpelling;
	/**
	 * 音标
	 */
	private TextView mPhoneticAlphabet;
	/**
	 * 中文意思
	 */
	private TextView mMeaning;
	/**
	 * 喇叭按钮
	 */
	private ImageView mHorn;
	/**
	 * AbcLearnActivity传过来的行数(从0开始)
	 */
	private int mPosition;
	/**
	 * 上一个单词
	 */
	private Button mBackWord;
	/**
	 * 下一个单词
	 */
	private Button mNextWord;
	/**
	 * 文本转语音
	 */
	private static TextToSpeech mTextToSpeech;
	/**
	 * 显示第count个单词
	 */
	private static int mCount = 0;
	/**
	 * 显示单词范围的最小值
	 */
	private static int mMin;
	/**
	 * 显示单词范围的最大值
	 */
	private static int mMax;
	private static ACBDao mLiaoTianDao;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abc_word);
		mAbcSetting = AbcSetting.getInstance(AbcWordActivity.this);
		initView();
		initData();
		RefreshWord();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mWordBreak = (Button) findViewById(R.id.abc_word_break);
		mWordBreak.setOnClickListener(mBackOnClickListener);

		mSpelling = (TextView) findViewById(R.id.abc_word_spelling);
		mPhoneticAlphabet = (TextView) findViewById(R.id.abc_word_alphabet);
		mMeaning = (TextView) findViewById(R.id.abc_word_meaning);
		mHorn = (ImageView) findViewById(R.id.abc_word_horn);
		mTextToSpeech = new TextToSpeech(AbcWordActivity.this, mOnInitListener);

		mHorn.setOnClickListener(this);
		mBackWord = (Button) findViewById(R.id.abc_word_back);
		mNextWord = (Button) findViewById(R.id.abc_word_next);
		mBackWord.setOnClickListener(this);
		mNextWord.setOnClickListener(this);
	}

	/**
	 * 初始化单词数据
	 */
	private void initData() {
		mLiaoTianDao = new ABCDaoImpl(AbcWordActivity.this);
		mWordList = mLiaoTianDao.loadWord();
		if (mWordList.size() == 0) {
			for (int i = 0; i < EnglishWord.WORD_SPELLING.length; i++) {
				Word word = new Word(EnglishWord.WORD_SPELLING[i],
						EnglishWord.WORD_MEANING[i],
						EnglishWord.WORD_PHONETIC_ALPHABET[i], i, false);
				mLiaoTianDao.addWord(word);
				mWordList.add(word);
			}
		}

		mPosition = this.getIntent().getExtras().getInt("position");
		mAbcSetting.getAbcSetting();
		mMin = mAbcSetting.getmWordNum() * mPosition;
		mMax = mAbcSetting.getmWordNum() * (mPosition + 1);
		if (mMax > mWordList.size())
			mMax = mWordList.size();
		mCount = mMin;
	}

	/**
	 * 上一个单词、下一个单词和发音点击事件
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.abc_word_horn: {
			mTextToSpeech.speak(mSpelling.getText().toString(),
					TextToSpeech.QUEUE_FLUSH, null);
			break;
		}
		case R.id.abc_word_back: {
			if (mCount - 1 < mMin) {
				ToastUtil.show(AbcWordActivity.this, "已经是第一个了！");
			} else {
				mCount--;
				RefreshWord();
			}
			break;
		}
		case R.id.abc_word_next: {
			if (mCount + 1 >= mMax) {
				ShowDialog();
			} else {
				mCount++;
				RefreshWord();
			}
			break;
		}
		}
	}

	/**
	 * 刷新显示的单词信息
	 */
	private void RefreshWord() {
		mSpelling.setText(mWordList.get(mCount).getmSpelling());
		mPhoneticAlphabet.setText(mWordList.get(mCount).getmPhoneticAlphabet());
		mMeaning.setText(mWordList.get(mCount).getmMeaning());
	}

	/**
	 * 对话框
	 */
	void ShowDialog() {
		CommonDialog dialog = new CommonDialog(AbcWordActivity.this,
				new OnDialogListener() {

					@Override
					public void OnRightClick() {
						//判断是否没有背诵过
						if (!mWordList.get(mMin).ismIsRememberance()) {
							setWordAlarm();
						}
						AbcWordActivity.this.finish();
					}

					@Override
					public void OnLeftClick() {
					}
				});

		dialog.setMsg("是否完成背诵？");
		dialog.setRightBtnText("完成背诵");
		dialog.setLeftBtnText("继续背诵");
		dialog.show();
	}

	/**
	 * 完成背诵后的设置
	 */
	private void setWordAlarm() {
		// 修改word的数据，设置成已背诵过
		for (int i = mMin; i < mMax; i++) {
			mWordList.get(i).setmIsRememberance(true);
			mLiaoTianDao.updateWord(mWordList.get(i));

		}
		// 设置定时提醒
		int[] temp = mAbcSetting.getmMemoryCycle();
		for (int i = 0; i < temp.length; i++) {
			CommonUtiles.alarmSet(AbcWordActivity.this,
					(AlarmManager) getSystemService(Context.ALARM_SERVICE),
					temp[i] * 60000,i,mPosition);
		}

	}

	/**
	 * 设置喇叭准备事件
	 */
	private OnInitListener mOnInitListener = new OnInitListener() {

		public void onInit(int status) {
			if (status == TextToSpeech.SUCCESS) {
				int result = mTextToSpeech.setLanguage(Locale.ENGLISH);
				if (result == TextToSpeech.LANG_MISSING_DATA
						|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
					Log.e("lanageTag", "not use");
				} else {
					mHorn.setEnabled(true);
				}
			}

		}
	};

	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mTextToSpeech != null) {
			mTextToSpeech.stop();
			mTextToSpeech.shutdown();
		}
		super.onDestroy();
	}

}
