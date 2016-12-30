package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;
import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.fragment.ABCFragment;
import com.fdzcxy.zerotime.fragment.MusicFragment;
import com.fdzcxy.zerotime.fragment.ReadFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;


public class MainActivity extends BaseFragmentActivity implements
		OnClickListener {

	/**
	 * 帧布局对象,就是用来存放Fragment的容器
	 */
	private FrameLayout mFrameLayout;

	/**
	 * 音乐Fragment
	 */
	private MusicFragment mMusicFragment;
	/**
	 * 单词Fragment
	 */
	private ABCFragment mABCFragment;
	/**
	 * 阅读Fragment
	 */
	private ReadFragment mReadFragment;
	// 上方的三个tab和三段线
	private Button mMusic;
	private Button mABC;
	private Button mRead;
	private View mMusicLine;
	private View mABCLine;
	private View mReadLine;
	/**
	 * 个人中心按钮
	 */
	private Button mPersonal;
	/**
	 * 定义FragmentManager对象,用于管理Fragment的切换
	 */
	FragmentManager mFragmentManager;

	static List<Music> musicList = new ArrayList<Music>();
	static MusicDao musicDao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApp = (CommonApplication) getApplication();
		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		initView();
	}

	/**
	 * 初始化视图和绑定点击事件
	 */
	protected void initView() {
		mFrameLayout = (FrameLayout) findViewById(R.id.main_fl); // 下方fragment部分
		
		mPersonal =(Button) findViewById(R.id.main_personal);
		mPersonal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						PersonCenterActivity.class);
				startActivity(intent);
			}
		});
		
		mMusic = (Button) findViewById(R.id.main_music);
		mRead = (Button) findViewById(R.id.main_read);
		mABC = (Button) findViewById(R.id.main_abc);
		mMusicLine = (View) findViewById(R.id.main_music_line);
		mABCLine = (View) findViewById(R.id.main_abc_line);
		mReadLine = (View) findViewById(R.id.main_read_line);
		

		mMusic.setOnClickListener(this);
		mABC.setOnClickListener(this);
		mRead.setOnClickListener(this);
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		mMusicFragment = new MusicFragment();
		transaction.add(R.id.main_fl, mMusicFragment);
		transaction.commit();
	}



	/**
	 * 点击三个tab，进行fragment的切换
	 */
	public void onClick(View v) {

		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		mMusic.setTextColor(getResources().getColor(R.color.common_white_blue));
		mMusicLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue));
		mABC.setTextColor(getResources().getColor(R.color.common_white_blue));
		mABCLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue));
		mRead.setTextColor(getResources().getColor(R.color.common_white_blue));
		mReadLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue));
		switch (v.getId()) {
		case R.id.main_music: {
			if (mMusicFragment != null) {
				transaction.hide(mMusicFragment);
			}
			mMusicFragment = new MusicFragment();
			transaction.replace(R.id.main_fl, mMusicFragment);
			mMusic.setTextColor(getResources().getColor(R.color.common_white_blue_select));
			mMusicLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue_select));
			break;
		}
		case R.id.main_abc: {
			if (mABCFragment != null) {
				transaction.hide(mABCFragment);
			}
			mABCFragment = new ABCFragment();
			transaction.replace(R.id.main_fl, mABCFragment);
			mABC.setTextColor(getResources().getColor(R.color.common_white_blue_select));
			mABCLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue_select));
			break;
		}
		case R.id.main_read: {
			if (mReadFragment != null) {
				transaction.hide(mReadFragment);
			}
			mReadFragment = new ReadFragment();
			transaction.replace(R.id.main_fl, mReadFragment);
			mRead.setTextColor(getResources().getColor(R.color.common_white_blue_select));
			mReadLine.setBackgroundColor(getResources().getColor(R.color.common_white_blue_select));
			break;
		}
		}
		transaction.commit();
	}
}
