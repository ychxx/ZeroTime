package com.fdzcxy.zerotime.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import com.fdzcxy.zerotime.Utiles.CommonApplication;
import com.fdzcxy.zerotime.Utiles.CommonUtiles;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.activity.MusicLoveActivity;
import com.fdzcxy.zerotime.activity.MusicNearestActivity;
import com.fdzcxy.zerotime.activity.MyMusicActivity;
import com.fdzcxy.zerotime.fragment.MusicFragment;

import android.app.Service;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 后台音乐服务
 * 
 */
public class MusicService extends Service {

	private static String TAG = "MusicService";
	/**
	 * 全局变量
	 */
	private static CommonApplication mApp;
	/**
	 * 音乐
	 */
	private static MediaPlayer mMediaPlayer;
	/**
	 * 音乐地址
	 */
	private String mPath;
	/**
	 * 音乐时长
	 */
	private String mTime;
	/**
	 * 用户对音乐的操作
	 */
	private int ACT;
	/**
	 * 正在播放音乐的编号
	 */
	private static int mPlayMusicNumber;

	/**
	 * 标识发出请求的Activity（Fragment）
	 */
	private static int mFrom;

	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 初始化MediaPlay类
	 */
	public void onCreate() {
		// Service第一次被创建时，调用
		super.onCreate();
		Log.i(TAG, "onCreate");
		mApp = (CommonApplication) getApplication();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	/* 启动service时执行的方法 */
	public int onStartCommand(Intent intent, int flags, int startId) {
		ACT = intent.getExtras().getInt("ACT");
		if(ACT == -1)
		{
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setOnErrorListener(new OnErrorListener() {
					public boolean onError(MediaPlayer mp, int what, int extra) {
						// TODO Auto-generated method stub
						mMediaPlayer.reset();
						return false;
					}
				});
			} else {
				mMediaPlayer.reset();
				mMediaPlayer.release();
			}
			
		}
		// Log.i(TAG, "操作：" + ACT);
		mFrom = intent.getExtras().getInt("FROM");
		// 判断是否有音乐
		mPlayMusicNumber = MusicHelper.PLAY_MUSIC_NUMBER;
		if (mPlayMusicNumber != -1) {

			switch (ACT) {
			case MusicHelper.ACT_PLAY_MUSIC: {
				// 开始播放
				playMusic(mApp.nowMusic.get(mPlayMusicNumber).getmUri());
				MusicHelper.MUSIC_STATE = 1;
				break;
			}
			case MusicHelper.ACT_PAUSE_MUSIC: {
				// 暂停播放
				if (mMediaPlayer.isPlaying()) {// 正在播放
					mMediaPlayer.pause();// 暂停
					MusicHelper.MUSIC_STATE = 2;
				}
				break;
			}
			case MusicHelper.ACT_RESUME_MUSIC: {
				// 继续播放
				if (!mMediaPlayer.isPlaying()) {
					mMediaPlayer.start();
					MusicHelper.MUSIC_STATE = 1;
				}
				break;
			}
			case MusicHelper.ACT_NEXT_MUSIC: {
				// 下一首
				NextMusic();
				break;
			}
			case MusicHelper.ACT_PREVIOUS_MUSIC: {
				// 上一首
				PreviousMusic();
				break;
			}
			}
		}
		sendRefreshPlay();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 下一首歌
	 */
	private static void NextMusic() {

		switch (MusicHelper.PLAY_MODE) {

		case MusicHelper.MODE_RANDOM_PLAY: {
			// 随机播放
			Random random = new Random();
			int rd = random.nextInt(mApp.nowMusic.size());
			MusicHelper.PLAY_MUSIC_NUMBER = rd;
			playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER).getmUri());
			break;
		}
		case MusicHelper.MODE_SINGLE_CYCLE: {
			// 单曲循环
			playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER).getmUri());
			break;
		}
		case MusicHelper.MODE_SINGLE_PLAY: {
			// 单曲播放
			int temp = MusicHelper.PLAY_MUSIC_NUMBER + 1;
			if (temp >= 0 && temp < mApp.nowMusic.size()) {
//				Log.i(TAG, "NextMusic1:" + MusicHelper.PLAY_MUSIC_NUMBER);
				MusicHelper.PLAY_MUSIC_NUMBER = temp;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
//				Log.i(TAG, "NextMusic2:" + MusicHelper.PLAY_MUSIC_NUMBER);
			} else {
				MusicHelper.PLAY_MUSIC_NUMBER = 0;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
			}
			break;
		}
		case MusicHelper.MODE_LOOP_PLAY: {
			// 顺序播放
			int temp = MusicHelper.PLAY_MUSIC_NUMBER + 1;
			if (temp >= 0 && temp < mApp.nowMusic.size()) {
//				Log.i(TAG, "NextMusic1:" + MusicHelper.PLAY_MUSIC_NUMBER);
				MusicHelper.PLAY_MUSIC_NUMBER = temp;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
//				Log.i(TAG, "NextMusic2:" + MusicHelper.PLAY_MUSIC_NUMBER);
			} else {
				MusicHelper.PLAY_MUSIC_NUMBER = 0;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
			}
			break;
		}
		}
		MusicHelper.MUSIC_STATE = 1;
		sendRefreshPlay();
	}

	/**
	 * 上一首歌
	 */
	private static void PreviousMusic() {
		switch (MusicHelper.PLAY_MODE) {
		case MusicHelper.MODE_RANDOM_PLAY: {
			// 随机播放
			Random random = new Random();
			int rd = random.nextInt(mApp.nowMusic.size());
			MusicHelper.PLAY_MUSIC_NUMBER = rd;
			playMusic(mApp.nowMusic.get(rd).getmUri());
			break;
		}
		case MusicHelper.MODE_SINGLE_CYCLE: {
			// 单曲循环
			playMusic(mApp.nowMusic.get(mPlayMusicNumber).getmUri());
			break;
		}
		case MusicHelper.MODE_SINGLE_PLAY: {
			// 单曲播放
			int temp = MusicHelper.PLAY_MUSIC_NUMBER - 1;
			if (temp >= 0 && temp < mApp.nowMusic.size()) {
				MusicHelper.PLAY_MUSIC_NUMBER = temp;
				playMusic(mApp.nowMusic.get(temp).getmUri());
			} else {
				MusicHelper.PLAY_MUSIC_NUMBER = mApp.nowMusic.size() - 1;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
			}
			break;
		}
		case MusicHelper.MODE_LOOP_PLAY: {
			// 顺序播放
			int temp = MusicHelper.PLAY_MUSIC_NUMBER - 1;
			if (temp >= 0 && temp < mApp.nowMusic.size()) {
				MusicHelper.PLAY_MUSIC_NUMBER = temp;
				playMusic(mApp.nowMusic.get(temp).getmUri());
			} else {
				MusicHelper.PLAY_MUSIC_NUMBER = mApp.nowMusic.size() - 1;
				playMusic(mApp.nowMusic.get(MusicHelper.PLAY_MUSIC_NUMBER)
						.getmUri());
			}
			break;
		}
		}
		MusicHelper.MUSIC_STATE = 1;
		sendRefreshPlay();
	}
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		
	}
	/**
	 * 发送更新UI的Handle
	 */
	private static void sendRefreshPlay() {

		Message msg = new Message();
		msg.what = mFrom;

		if (mFrom == mApp.MYMUSICACTIVITY_REFRESHPLAY) {
			new MyMusicActivity().mHandlerRefreshPlay.sendMessage(msg);
		} else if (mFrom == mApp.MUSICFRAGMENT_REFRESHPLAY) {
			new MusicFragment().mHandlerRefreshPlay.sendMessage(msg);
		} else if (mFrom == mApp.MUSICLOVEACTIVITY_REFRESHPLAY) {
			new MusicLoveActivity().mHandlerRefreshPlay.sendMessage(msg);
		} else if (mFrom == mApp.MUSICNEARESTACTIVITY_REFRESHPLAY) {
			new MusicNearestActivity().mHandlerRefreshPlay.sendMessage(msg);
		}
	}

	/**
	 * 根据手机本地路径播放本地音乐
	 * 
	 * @param filePath
	 * @param onCompletionListener
	 */
	private static void playMusic(String filePath) {
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					mMediaPlayer.reset();
					return false;
				}
			});
		} else {
			mMediaPlayer.reset();
		}
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			FileInputStream fio = new FileInputStream(new File(filePath));
			mMediaPlayer.setDataSource(fio.getFD());
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				public void onPrepared(MediaPlayer mp) {
					mMediaPlayer.start();
				}
			});
			// 设置播放完事件
			mMediaPlayer.setOnCompletionListener(mNextPlayListener);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放完当前歌曲，自动播放下一首事件
	 **/
	private static OnCompletionListener mNextPlayListener = new OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			if (MusicHelper.PLAY_MODE == MusicHelper.MODE_SINGLE_PLAY) {
				mMediaPlayer.stop();
				MusicHelper.MUSIC_STATE = 0;
				sendRefreshPlay();
			} else
				NextMusic();
		}
	};

}
