package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.adapter.MusicNearestAdapter;
import com.fdzcxy.zerotime.adapter.MyMusicAdapter;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.dialog.BelowDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.fragment.MusicFragment;

public class MusicNearestActivity extends BaseActivity {
	protected static final String TAG = "MusicNearestActivity";

	private MusicNearestAdapter mMusicNearestAdapter;

	/**
	 * 音乐单
	 */
	List<Music> musiclt;
	ListView mListView;
	/**
	 * 上一首
	 */
	ImageView mPrevious;
	/**
	 * 下一首
	 */
	ImageView mNext;
	/**
	 * 播放/暂停
	 */
	static ImageView mPlay;
	/**
	 * 播放框里正在播放音乐的歌名 静态防止handle更新UI时为null
	 */
	static TextView mSongName;
	/**
	 * 播放框里正在播放音乐的作者 静态防止handle更新UI时为null
	 */
	static TextView mSongAuthor;
	/**
	 * 右下角的更多
	 */
	private ImageView mMore;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_music);
		mApp = (CommonApplication) getApplication();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MusicDao musicDao = new MusicDaoImpl(MusicNearestActivity.this);
		List<Music> musicList = musicDao.loadMusic();
		// 判断是否有音乐
		if (musicList.size() == 0)
			ShowDialog();
		else {
			mApp.nearestMusic.clear();
			List<Music> listMusic = new ArrayList<Music>();
			listMusic = CommonUtiles.BubbleSort(musicList);
//			Log.i("111", "state"+MusicHelper.MUSIC_STATE);
			for (int i = 0; i < listMusic.size(); i++) {
//				Log.i("mNearestime:", listMusic.get(i).getmName()+"time:"+listMusic.get(i).getmPlayedTime());
				if (listMusic.get(i).getmPlayedTime() == 0)
					break;
				mApp.nearestMusic.add(listMusic.get(i));
			}
			if(mApp.nowMusic.isEmpty()||mApp.nowMusic.size() ==0)
				mApp.nowMusic.clear();
				mApp.nowMusic.addAll(mApp.nearestMusic);
			initView();
		}
	}

	/**
	 * 初始化视图
	 */
	void initView() {
		mListView = (ListView) findViewById(R.id.music_lv);
		((ImageView) findViewById(R.id.my_music_break))
				.setOnClickListener(mBackOnClickListener);
		((TextView) findViewById(R.id.music_title)).setText("最近播放");
		mPlay = (ImageView) findViewById(R.id.my_music_play);
		mPrevious = (ImageView) findViewById(R.id.my_music_previous);
		mNext = (ImageView) findViewById(R.id.my_music_next);

		mPlay.setOnClickListener(mMusicACT);
		mPrevious.setOnClickListener(mMusicACT);
		mNext.setOnClickListener(mMusicACT);

		mMore = (ImageView) findViewById(R.id.my_music_more);
		mMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BelowDialog dilog = new BelowDialog(MusicNearestActivity.this);
				dilog.show();

			}
		});
		mSongName = (TextView) findViewById(R.id.my_music_song_name);
		mSongAuthor = (TextView) findViewById(R.id.my_music_song_author);
		mMusicNearestAdapter = new MusicNearestAdapter(
				MusicNearestActivity.this, mApp.nearestMusic);
		mListView.setAdapter(mMusicNearestAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MusicHelper.PLAY_MUSIC_NUMBER = position;
				mApp.nowMusic.clear();
				mApp.nowMusic.addAll(mApp.nearestMusic);
				Intent intent = new Intent();
				intent.setAction("com.fdzcxy.zerotime.service.MusicService");
				intent.putExtra("ACT", MusicHelper.ACT_PLAY_MUSIC);
				intent.putExtra("FROM", mApp.MUSICNEARESTACTIVITY_REFRESHPLAY);
				startService(intent);
				// 改变音乐播放的状态
				MusicHelper.MUSIC_STATE = 1;
			}
		});
		RefreshPlay();
	}

	/**
	 * 音乐框的三种音乐操作
	 */
	private OnClickListener mMusicACT = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setAction("com.fdzcxy.zerotime.service.MusicService");

			switch (v.getId()) {
			case R.id.my_music_play: {
				// 根据音乐播放的状态进行设置
				if (MusicHelper.MUSIC_STATE == 0) {
					intent.putExtra("ACT", MusicHelper.ACT_PLAY_MUSIC);
				} else if (MusicHelper.MUSIC_STATE == 1) {
					intent.putExtra("ACT", MusicHelper.ACT_PAUSE_MUSIC);
				} else if (MusicHelper.MUSIC_STATE == 2) {
					intent.putExtra("ACT", MusicHelper.ACT_RESUME_MUSIC);
				}
				break;
			}
			case R.id.my_music_previous: {
				intent.putExtra("ACT", MusicHelper.ACT_PREVIOUS_MUSIC);

				break;
			}
			case R.id.my_music_next: {
				intent.putExtra("ACT", MusicHelper.ACT_NEXT_MUSIC);

				break;
			}
			}
			intent.putExtra("FROM", mApp.MUSICNEARESTACTIVITY_REFRESHPLAY);
			startService(intent);

		}
	};

	/**
	 * 对话框
	 */
	void ShowDialog() {
		CommonDialog dialog = new CommonDialog(MusicNearestActivity.this,
				new OnDialogListener() {

					@Override
					public void OnRightClick() {
						Intent intent = new Intent();
						intent.setClass(MusicNearestActivity.this,
								ScanMusicActivity.class);
						startActivity(intent);
						MusicNearestActivity.this.finish();
					}

					@Override
					public void OnLeftClick() {
						MusicNearestActivity.this.finish();
					}
				});

		dialog.setMsg("没有找到音乐");
		dialog.setRightBtnText("扫描音乐");
		dialog.setLeftBtnText("返回上一层");
		dialog.show();
	}

	/**
	 * 刷新播放框 歌名和歌手信息
	 */
	private void RefreshPlay() {

		int playMusicNumber = MusicHelper.PLAY_MUSIC_NUMBER;
		if (playMusicNumber != -1) {
//			Log.i("111", "state"+MusicHelper.MUSIC_STATE);
			mSongName.setText(mApp.nowMusic.get(playMusicNumber).getmName());
			mSongAuthor.setText(mApp.nowMusic.get(playMusicNumber).getmAuthor());
			if (MusicHelper.MUSIC_STATE == 0 || MusicHelper.MUSIC_STATE == 2) {
				mPlay.setBackgroundResource(R.drawable.main_btn_play);
			} else {
				MusicDao musicDao = new MusicDaoImpl(MusicNearestActivity.this);
				int nowTime = Math.abs((int) (System.currentTimeMillis() / 1000));
				mApp.nowMusic.get(playMusicNumber).setmPlayedTime(nowTime);
				musicDao.updateMusic(mApp.nowMusic.get(playMusicNumber));
				mPlay.setBackgroundResource(R.drawable.main_btn_pause);
			}
		}
	}

	/**
	 * 接受MusicService服务传来的更新播放框信息
	 */
	public Handler mHandlerRefreshPlay = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == mApp.MUSICNEARESTACTIVITY_REFRESHPLAY) {
				RefreshPlay();
			}
		}
	};
}
