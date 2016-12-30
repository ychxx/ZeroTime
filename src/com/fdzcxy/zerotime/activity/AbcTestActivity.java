package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.EnglishWord;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.dao.ABCDaoImpl;
import com.fdzcxy.zerotime.dao.ACBDao;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;
import com.fdzcxy.zerotime.domain.Word;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

public class AbcTestActivity extends BaseActivity implements OnClickListener {
	/**
	 * 数据库所有背过的单词list
	 */
	private static List<Word> mMemoryWordList = new ArrayList<Word>();
	/**
	 * 数据库所有单词list
	 */
	private static List<Word> mWordList = new ArrayList<Word>();
	/**
	 * 返回按钮
	 */
	private Button mWordBreak;
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
	 * 下一个单词
	 */
	private Button mNextWord;
	/**
	 * 选项A布局
	 */
	private LinearLayout mLinearLayoutA;
	/**
	 * 选项B布局
	 */
	private LinearLayout mLinearLayoutB;
	/**
	 * 选项C布局
	 */
	private LinearLayout mLinearLayoutC;
	/**
	 * 选项A ImageView
	 */
	private ImageView mImageViewA;
	/**
	 * 选项B ImageView
	 */
	private ImageView mImageViewB;
	/**
	 * 选项C ImageView
	 */
	private ImageView mImageViewC;
	/**
	 * 选项A内容
	 */
	private TextView mContentA;
	/**
	 * 选项B内容
	 */
	private TextView mContentB;
	/**
	 * 选项C内容
	 */
	private TextView mContentC;
	/**
	 * 文本转语音
	 */
	private static TextToSpeech mTextToSpeech;
	/**
	 * 答案选项
	 */
	private static int mAnswer;
	/**
	 * 答案的拼写
	 */
	private static String mAnswerSpelling;
	private static ACBDao mLiaoTianDao;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abc_test);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initView();
		initData();
	}
	/**
	 * 初始化视图
	 */
	private void initView() {
		mWordBreak = (Button) findViewById(R.id.abc_test_break);
		mWordBreak.setOnClickListener(mBackOnClickListener);

		mPhoneticAlphabet = (TextView) findViewById(R.id.abc_test_alphabet);
		mMeaning = (TextView) findViewById(R.id.abc_test_meaning);
		mHorn = (ImageView) findViewById(R.id.abc_test_horn);
		mHorn.setOnClickListener(this);
		mTextToSpeech = new TextToSpeech(AbcTestActivity.this, mOnInitListener);

		mLinearLayoutA = (LinearLayout) findViewById(R.id.abc_test_a);
		mLinearLayoutB = (LinearLayout) findViewById(R.id.abc_test_b);
		mLinearLayoutC = (LinearLayout) findViewById(R.id.abc_test_c);

		mLinearLayoutA.setOnClickListener(this);
		mLinearLayoutB.setOnClickListener(this);
		mLinearLayoutC.setOnClickListener(this);

		mContentA = (TextView) findViewById(R.id.abc_test_a_content);
		mContentB = (TextView) findViewById(R.id.abc_test_b_content);
		mContentC = (TextView) findViewById(R.id.abc_test_c_content);

		mImageViewA = (ImageView) findViewById(R.id.abc_test_a_img);
		mImageViewB = (ImageView) findViewById(R.id.abc_test_b_img);
		mImageViewC = (ImageView) findViewById(R.id.abc_test_c_img);

		mNextWord = (Button) findViewById(R.id.abc_test_next);
		mNextWord.setOnClickListener(this);
	}

	/**
	 * 初始化单词数据
	 */
	private void initData() {
		mLiaoTianDao = new ABCDaoImpl(AbcTestActivity.this);

		mWordList = mLiaoTianDao.loadWord();
		if (mWordList.isEmpty() || mWordList.size() == 0) {
			for (int i = 0; i < EnglishWord.WORD_SPELLING.length; i++) {
				Word word = new Word(EnglishWord.WORD_SPELLING[i],
						EnglishWord.WORD_MEANING[i],
						EnglishWord.WORD_PHONETIC_ALPHABET[i], i, false);
				mLiaoTianDao.addWord(word);
				mWordList.add(word);
			}
		}
		for (int i = 0; i < mWordList.size(); i++) {
			if (mWordList.get(i).ismIsRememberance()) {
				mMemoryWordList.add(mWordList.get(i));
			}
		}
		RefreshWord();
	}

	/**
	 * 刷新显示的单词信息
	 */
	private void RefreshWord() {
		if (mMemoryWordList.size() == 0) {
			ShowDialog();
			return;
		}
		Random random = new Random();
		mAnswer = random.nextInt(3);
		int rd = random.nextInt(mMemoryWordList.size());
		mAnswerSpelling = mMemoryWordList.get(rd).getmSpelling();

		switch (mAnswer) {
		case 0: {
			mPhoneticAlphabet.setText(mMemoryWordList.get(rd)
					.getmPhoneticAlphabet());
			mMeaning.setText(mMemoryWordList.get(rd).getmMeaning());
			mContentA.setText(mMemoryWordList.get(rd).getmSpelling());
			mContentB.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			mContentC.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			break;
		}
		case 1: {
			mPhoneticAlphabet.setText(mMemoryWordList.get(rd)
					.getmPhoneticAlphabet());
			mMeaning.setText(mMemoryWordList.get(rd).getmMeaning());
			mContentA.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			mContentB.setText(mMemoryWordList.get(rd).getmSpelling());
			mContentC.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			break;
		}
		case 2: {
			mPhoneticAlphabet.setText(mMemoryWordList.get(rd)
					.getmPhoneticAlphabet());
			mMeaning.setText(mMemoryWordList.get(rd).getmMeaning());
			mContentA.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			mContentB.setText(mWordList.get(random.nextInt(mWordList.size()))
					.getmSpelling());
			mContentC.setText(mMemoryWordList.get(rd).getmSpelling());
			break;
		}
		}
		// 初始化字体颜色
		mContentA.setTextColor(this.getResources().getColor(
				R.color.common_white_blue));
		mImageViewA.setBackgroundResource(R.drawable.test_a);

		mContentB.setTextColor(this.getResources().getColor(
				R.color.common_white_blue));
		mImageViewB.setBackgroundResource(R.drawable.test_b);
		mContentC.setTextColor(this.getResources().getColor(
				R.color.common_white_blue));
		mImageViewC.setBackgroundResource(R.drawable.test_c);
	}

	/**
	 * 上一个单词、下一个单词和发音点击事件
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.abc_test_horn: {
			mTextToSpeech
					.speak(mAnswerSpelling, TextToSpeech.QUEUE_FLUSH, null);
			break;
		}
		case R.id.abc_test_next: {
			RefreshWord();
			break;
		}
		case R.id.abc_test_a: {
			rightAnswer(0);
			break;
		}
		case R.id.abc_test_b: {
			rightAnswer(1);
			break;
		}
		case R.id.abc_test_c: {
			rightAnswer(2);
			break;
		}
		}
	}

	/**
	 * 对话框
	 */
	void ShowDialog() {
		CommonDialog dialog = new CommonDialog(AbcTestActivity.this,
				new OnDialogListener() {

					@Override
					public void OnRightClick() {
						// 判断是否没有背诵过
						AbcTestActivity.this.finish();
						Intent intent = new Intent();
						intent.setClass(AbcTestActivity.this,
								AbcLearnActivity.class);
						startActivity(intent);
					}

					@Override
					public void OnLeftClick() {
						AbcTestActivity.this.finish();
					}
				});

		dialog.setMsg("没有背诵过的单词！");
		dialog.setRightBtnText("去背单词");
		dialog.setLeftBtnText("返回");
		dialog.show();
	}

	/**
	 * 判断是否选对答案
	 * 
	 * @param select
	 */
	private void rightAnswer(int select) {
		if (mAnswer == select) {
			ToastUtil.show(AbcTestActivity.this, "正确！");
		} else
			ToastUtil.show(AbcTestActivity.this, "错误！");
		// 将答案修改成红色
		if (mAnswer == 0) {
			mContentA.setTextColor(this.getResources().getColor(
					R.color.common_text_red_color));
			mImageViewA.setBackgroundResource(R.drawable.test_a_red);
		} else if (mAnswer == 1) {
			mContentB.setTextColor(this.getResources().getColor(
					R.color.common_text_red_color));
			mImageViewB.setBackgroundResource(R.drawable.test_b_red);
		} else if (mAnswer == 2) {
			mContentC.setTextColor(this.getResources().getColor(
					R.color.common_text_red_color));
			mImageViewC.setBackgroundResource(R.drawable.test_c_red);
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
