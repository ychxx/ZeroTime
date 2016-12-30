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
import com.fdzcxy.zerotime.adapter.MyMusicAdapter;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.dialog.BelowDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog;
import com.fdzcxy.zerotime.dialog.CommonDialog.OnDialogListener;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.fragment.MusicFragment;

/**
 * 音乐收藏页面
 * 
 * @author Administrator
 * 
 */
public class MusicLoveActivity extends BaseActivity {
	protected static final String TAG = "MusicLoveActivity";

	private MyMusicAdapter mMyMusicAdapter;

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
		MusicDao musicDao = new MusicDaoImpl(MusicLoveActivity.this);
		List<Music> musicList = musicDao.loadMusic();
		// 判断是否有音乐
		if (musicList.size() == 0)
			ShowDialog();
		else {
			initMusicData(musicList);
			initView();
		}
	}

	/**
	 * 初始化音乐数据
	 */
	protected void initMusicData(List<Music> musicList) {


		// 判断数据库中音乐数据是否为空，是则扫描手机,否则存入静态类音乐列表的 我的音乐 列表中
		if (musicList.isEmpty() || musicList.size() == 0) {
			MusicHelper.PLAY_MUSIC_NUMBER = -1;
		} else {
			mApp.loveMusic.clear();
			for (int i = 0; i < musicList.size(); i++) {
				if (musicList.get(i).getmIsLove())
					mApp.loveMusic.add(musicList.get(i));
			}
			if(mApp.nowMusic.isEmpty()||mApp.nowMusic.size() ==0)
				mApp.nowMusic.clear();
				mApp.nowMusic.addAll(mApp.loveMusic);
		}
	}

	/**
	 * 初始化视图
	 */
	void initView() {
		mListView = (ListView) findViewById(R.id.music_lv);
		((ImageView) findViewById(R.id.my_music_break))
				.setOnClickListener(mBackOnClickListener);
		((TextView) findViewById(R.id.music_title)).setText("我的最爱");
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
				BelowDialog dilog = new BelowDialog(MusicLoveActivity.this);
				dilog.show();

			}
		});
		mSongName = (TextView) findViewById(R.id.my_music_song_name);
		mSongAuthor = (TextView) findViewById(R.id.my_music_song_author);

		mMyMusicAdapter = new MyMusicAdapter(MusicLoveActivity.this, mApp.loveMusic);
		mListView.setAdapter(mMyMusicAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MusicHelper.PLAY_MUSIC_NUMBER = position;
				mApp.nowMusic.clear();
				mApp.nowMusic.addAll(mApp.loveMusic);
				Intent intent = new Intent();
				intent.setAction("com.fdzcxy.zerotime.service.MusicService");
				intent.putExtra("ACT", MusicHelper.ACT_PLAY_MUSIC);
				intent.putExtra("FROM", mApp.MUSICLOVEACTIVITY_REFRESHPLAY);
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
			intent.putExtra("FROM", mApp.MUSICLOVEACTIVITY_REFRESHPLAY);
			startService(intent);

		}
	};

	/**
	 * 对话框
	 */
	void ShowDialog() {
		CommonDialog dialog = new CommonDialog(MusicLoveActivity.this,
				new OnDialogListener() {

					@Override
					public void OnRightClick() {
						Intent intent = new Intent();
						intent.setClass(MusicLoveActivity.this,
								ScanMusicActivity.class);
						startActivity(intent);
						MusicLoveActivity.this.finish();
					}

					@Override
					public void OnLeftClick() {
						MusicLoveActivity.this.finish();
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
			List<Music> listMusic = new ArrayList<Music>();
			mSongName.setText(mApp.nowMusic.get(playMusicNumber).getmName());
			mSongAuthor
					.setText(mApp.nowMusic.get(playMusicNumber).getmAuthor());
			if (MusicHelper.MUSIC_STATE == 0 || MusicHelper.MUSIC_STATE == 2) {
				mPlay.setBackgroundResource(R.drawable.main_btn_play);
			} else {
				MusicDao musicDao = new MusicDaoImpl(MusicLoveActivity.this);
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
			if (msg.what == mApp.MUSICLOVEACTIVITY_REFRESHPLAY) {
				RefreshPlay();
			}
		}
	};
}
