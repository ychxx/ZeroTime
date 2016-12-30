package com.fdzcxy.zerotime.Utiles;

import com.fdzcxy.zerotime.R;

import android.widget.SeekBar;

/**
 * 音乐帮助类
 * 
 * @author Administrator
 * 
 */
public class MusicHelper {

	/**
	 * 当前播放音乐的编号 -1代表没有播放歌曲
	 */
	public static int PLAY_MUSIC_NUMBER = 0;

	/**
	 * 播放模式 默认顺序播放
	 */
	public static int PLAY_MODE = 0;
	/**
	 * 播放模式 的图
	 */
	public static final int[] musicModeImg = { R.drawable.mode_loop,
			R.drawable.mode_random, R.drawable.mode_single_cycle,
			R.drawable.mode_single_play };
	/**
	 * 播放模式 的文字
	 */
	public static final String[] musicModeString = { "顺序播放", "随机播放", "单曲循环",
			"单曲播放" };
	/**
	 * 播放音乐三种状态： 0.没播放过音乐 1.正在播放音乐 2.暂停播放音乐
	 */
	public static int MUSIC_STATE = 0;

	/* 操作常量 */
	/**
	 * 开始播放
	 */
	public static final int ACT_PLAY_MUSIC = 10;
	/**
	 * 暂停播放
	 */
	public static final int ACT_PAUSE_MUSIC = 11;
	/**
	 * 继续播放
	 */
	public static final int ACT_RESUME_MUSIC = 12;
	/**
	 * 下一首
	 */
	public static final int ACT_NEXT_MUSIC = 13;
	/**
	 * 上一首
	 */
	public static final int ACT_PREVIOUS_MUSIC = 14;

	/* 播放模式常量 */
	/**
	 * 顺序播放
	 */
	public static final int MODE_LOOP_PLAY = 0;
	/**
	 * 随机播放
	 */
	public static final int MODE_RANDOM_PLAY = 1;
	/**
	 * 单曲循环
	 */
	public static final int MODE_SINGLE_CYCLE = 2;
	/**
	 * 单曲播放
	 */
	public static final int MODE_SINGLE_PLAY = 3;

}
