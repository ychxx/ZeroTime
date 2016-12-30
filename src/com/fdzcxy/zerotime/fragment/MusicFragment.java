package com.fdzcxy.zerotime.fragment;

import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.activity.MainActivity;
import com.fdzcxy.zerotime.activity.MusicLoveActivity;
import com.fdzcxy.zerotime.activity.MusicNearestActivity;
import com.fdzcxy.zerotime.activity.MyMusicActivity;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.dialog.BelowDialog;
import com.fdzcxy.zerotime.domain.Music;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MusicFragment extends BaseFragment implements OnClickListener {

	/**
	 * 我的音乐
	 */
	private RelativeLayout mMyMusicRl;
	/**
	 * 我的音乐
	 */
	private RelativeLayout mLoveRl;
	/**
	 * 我的音乐
	 */
	private RelativeLayout mNearestRl;
	/**
	 * 我的音乐列表 数量
	 */
	private TextView mMusicNum;
//	/**
//	 * 我的最爱列表 数量
//	 */
//	private TextView mLoveNum;
//	/**
//	 * 我的最近播放列表 数量
//	 */
//	private TextView mNearestNum;
	/**
	 * 绑定的视图XML
	 */
	private View view;
	/**
	 * 播放框里正在播放音乐的歌名 静态防止handle更新UI时为null
	 */
	static TextView mSongName;
	/**
	 * 播放框里正在播放音乐的作者 静态防止handle更新UI时为null
	 */
	static TextView mSongAuthor;
	/**
	 * 上一首
	 */
	private ImageView mPrevious;
	/**
	 * 下一首
	 */
	private ImageView mNext;
	/**
	 * 播放/暂停
	 */
	static private ImageView mPlay;
	/**
	 * 右下角的更多
	 */
	static private ImageView mMore;
	String TAG = "MusicFragment";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_music, null);
		mApp = (CommonApplication) getActivity().getApplication();

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initMusicData();
		initView();
	}
	/**
	 * 初始化音乐数据
	 */
	protected void initMusicData() {

		MusicDao musicDao = new MusicDaoImpl(MusicFragment.this.getContext());
		List<Music>musicList = musicDao.loadMusic();
		
		// 判断数据库中音乐数据是否为空，是则扫描手机,否则存入静态类音乐列表的 我的音乐 列表中
		if (musicList.isEmpty()||musicList.size() == 0) {
			MusicHelper.PLAY_MUSIC_NUMBER = -1;
		} else {
			mApp.myMusic.clear();
			mApp.myMusic.addAll(musicList);
			//判断是否当前播放的列表是否为空
			if(mApp.nowMusic.isEmpty()||mApp.nowMusic.size() ==0)
				mApp.nowMusic.clear();
				mApp.nowMusic.addAll(musicList);
		}
	}
	protected void initView() {
		mMyMusicRl = (RelativeLayout) view.findViewById(R.id.music_my_rl);
		mMyMusicRl.setOnClickListener(this);
		mNearestRl = (RelativeLayout) view.findViewById(R.id.music_nearest_ll);
		mNearestRl.setOnClickListener(this);
		mLoveRl = (RelativeLayout) view.findViewById(R.id.music_love_ll);
		mLoveRl.setOnClickListener(this);
		
		mMusicNum = (TextView) view.findViewById(R.id.music_my_number);
		
//		mLoveNum = (TextView) view.findViewById(R.id.music_love_number);
//		mNearestNum = (TextView) view.findViewById(R.id.music_nearest_number);

		mMusicNum.setText("歌曲:" + mApp.myMusic.size());
//		mLoveNum.setText("歌曲:" + mApp.love.size());
//		mNearestNum.setText("歌曲:" + mApp.neares.size());

		// 音乐播放框
		mPlay = (ImageView) view.findViewById(R.id.music_play);
		mPrevious = (ImageView) view.findViewById(R.id.music_previous);
		mNext = (ImageView) view.findViewById(R.id.music_next);
		mMore = (ImageView) view.findViewById(R.id.music_more);

		mMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BelowDialog dilog = new BelowDialog(MusicFragment.this
						.getContext());
				dilog.show();

			}
		});
		mPlay.setOnClickListener(mMusicACT);
		mPrevious.setOnClickListener(mMusicACT);
		mNext.setOnClickListener(mMusicACT);

		mSongName = (TextView) view.findViewById(R.id.music_song_name);
		mSongAuthor = (TextView) view.findViewById(R.id.music_song_author);
		RefreshPlay();
	}

	/**
	 * 跳转到其他页面
	 */
	public void onClick(View v) {

		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.music_my_rl: {
			intent.setClass(MusicFragment.this.getActivity(),
					MyMusicActivity.class);
			break;
		}
		case R.id.music_nearest_ll: {
			intent.setClass(MusicFragment.this.getActivity(),
					MusicNearestActivity.class);
			break;
		}
		case R.id.music_love_ll: {
			intent.setClass(MusicFragment.this.getActivity(),
					MusicLoveActivity.class);
			break;
		}
		}
		startActivity(intent);
	}

	/**
	 * 刷新播放框 歌名和歌手信息
	 */
	void RefreshPlay() {
		int playMusicNumber = MusicHelper.PLAY_MUSIC_NUMBER;
		if (playMusicNumber != -1) {
			mSongName.setText(mApp.nowMusic.get(playMusicNumber).getmName());
			mSongAuthor.setText(mApp.nowMusic.get(playMusicNumber).getmAuthor());
			if (MusicHelper.MUSIC_STATE == 0 || MusicHelper.MUSIC_STATE == 2) {
				mPlay.setBackgroundResource(R.drawable.main_btn_play);
			} else {
				MusicDao musicDao =new MusicDaoImpl(MusicFragment.this.getContext());
				int nowTime = Math.abs((int) (System.currentTimeMillis() / 1000));
				mApp.nowMusic.get(playMusicNumber).setmPlayedTime(nowTime);
				musicDao.updateMusic(mApp.nowMusic.get(playMusicNumber));
				mPlay.setBackgroundResource(R.drawable.main_btn_pause);
			}
		}
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
			case R.id.music_play: {

				// 根据音乐播放的状态进行设置
//				Log.i(TAG, "" + MusicHelper.MUSIC_STATE);
				if (MusicHelper.MUSIC_STATE == 0) {

					intent.putExtra("ACT", MusicHelper.ACT_PLAY_MUSIC);
				} else if (MusicHelper.MUSIC_STATE == 1) {

					intent.putExtra("ACT", MusicHelper.ACT_PAUSE_MUSIC);
				} else {

					intent.putExtra("ACT", MusicHelper.ACT_RESUME_MUSIC);
				}
				break;
			}
			case R.id.music_previous: {
				intent.putExtra("ACT", MusicHelper.ACT_PREVIOUS_MUSIC);
				break;
			}
			case R.id.music_next: {
				intent.putExtra("ACT", MusicHelper.ACT_NEXT_MUSIC);
				break;
			}
			}
			intent.putExtra("FROM", mApp.MUSICFRAGMENT_REFRESHPLAY);
			getActivity().startService(intent);
		}
	};

	/**
	 * 接受MusicService服务传来的更新播放框信息
	 */
	public final Handler mHandlerRefreshPlay = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == mApp.MUSICFRAGMENT_REFRESHPLAY) {
				RefreshPlay();
			}
		}
	};
}
