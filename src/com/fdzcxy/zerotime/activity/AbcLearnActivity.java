package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.EnglishWord;
import com.fdzcxy.zerotime.adapter.LearnAdapter;
import com.fdzcxy.zerotime.adapter.MyMusicAdapter;
import com.fdzcxy.zerotime.dao.ABCDaoImpl;
import com.fdzcxy.zerotime.dao.ACBDao;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.domain.Word;
import com.fdzcxy.zerotime.sharedpreferences.AbcSetting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AbcLearnActivity extends BaseActivity {
	/**
	 * 返回按钮
	 */
	private Button mLearnBreak;

	/**
	 * 单词list
	 */
	private static List<Word> mWordList = new ArrayList<Word>();

	ACBDao mLiaoTianDao;
	private ListView mLearnLV;
	private static LearnAdapter LearnAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abc_learn);
		mAbcSetting = AbcSetting.getInstance(AbcLearnActivity.this);
		
	}
	/**
	 * 用于AbcTestActivity修改word表后刷新Listview内容
	 */
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initView();
		initData();

	}
	/**
	 * 初始化视图
	 */
	private void initView() {
		mLearnBreak = (Button) findViewById(R.id.abc_learn_break);
		mLearnBreak.setOnClickListener(mBackOnClickListener);
		mLearnLV = (ListView) findViewById(R.id.abc_learn_lv);

	}

	/**
	 * 初始化单词数据
	 */
	private void initData() {
		mLiaoTianDao = new ABCDaoImpl(AbcLearnActivity.this);
		mWordList = mLiaoTianDao.loadWord();
		if (mWordList.isEmpty() ||mWordList.size() == 0) {
			for (int i = 0; i < EnglishWord.WORD_SPELLING.length; i++) {
				Word word = new Word(EnglishWord.WORD_SPELLING[i],
						EnglishWord.WORD_PHONETIC_ALPHABET[i],
						EnglishWord.WORD_MEANING[i], i, false);
				mLiaoTianDao.addWord(word);
				mWordList.add(word);
			}
		}
		mAbcSetting.getAbcSetting();

		LearnAdapter = new LearnAdapter(AbcLearnActivity.this,
				mWordList, mAbcSetting.getmWordNum());
		LearnAdapter.notifyDataSetChanged();
		mLearnLV.setAdapter(LearnAdapter);
		//设置点击回调函数，跳转到背诵单词页面，并将选中的行数桶Intent传递过去
		mLearnLV.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(AbcLearnActivity.this, AbcWordActivity.class);

				intent.putExtra("position", position);
				startActivity(intent);
				
			};
		});
	}
}
