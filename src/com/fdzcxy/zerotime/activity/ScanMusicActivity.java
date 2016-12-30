package com.fdzcxy.zerotime.activity;

import java.util.ArrayList;
import java.util.List;

import com.fdzcxy.zerotime.R;
import com.fdzcxy.zerotime.Utiles.MusicHelper;
import com.fdzcxy.zerotime.Utiles.ToastUtil;
import com.fdzcxy.zerotime.dao.MusicDao;
import com.fdzcxy.zerotime.dao.MusicDaoImpl;
import com.fdzcxy.zerotime.dialog.LoadingDialog;
import com.fdzcxy.zerotime.domain.Music;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ScanMusicActivity extends BaseActivity {
	/**
	 * 返回按钮
	 */
	private Button mScanMusicBreak;
	/**
	 * 扫描按钮
	 */
	private Button mScanMusicBt;
	/**
	 * 异步线程，用于本地扫描音乐
	 */
	private CustomAsyncQueryHandler queryHandler;
	private static MusicDao musicDao;
	private static LoadingDialog loadingDialog;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_music);
		initView();
		initData();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mScanMusicBreak = (Button) findViewById(R.id.scanmusic_break);
		mScanMusicBreak.setOnClickListener(mBackOnClickListener);
		mScanMusicBt = (Button) findViewById(R.id.scanmusic_scan);
		mScanMusicBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				loadingDialog = new LoadingDialog(ScanMusicActivity.this,
						"扫描中..", R.anim.loading);
				loadingDialog.setCancelable(false);
				loadingDialog.show();
				scanMusicList();
			}
		});
	}

	/**
	 * 初始化音乐数据
	 */
	private void initData() {
		musicDao = new MusicDaoImpl(ScanMusicActivity.this);
		mApp.myMusic.addAll(musicDao.loadMusic());
	}

	/**
	 * 输入音乐列表（扫描音乐）
	 */
	private void scanMusicList() {

		queryHandler = new CustomAsyncQueryHandler(
				ScanMusicActivity.this.getContentResolver());
		queryHandler.startQuery(0, null,
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
				"DURATION > ?", new String[] { 6 * 20 + "" },
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	}

	private class CustomAsyncQueryHandler extends AsyncQueryHandler {// 读取歌曲
		public CustomAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			// Android提供的异步查询框架AsyncQueryHandler，
			// 是一种异步查询方式，当单查询完毕后，会调用onQueryComplete(token, cookie,
			// cursor)通知查询完毕，并且传回cursor

			// 存储新增音乐数量
			int num = 0;
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToNext();
				if (cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)) == 1) {
					// cursor.getColumnIndex:得到指定列名的索引号,就是说这个字段是第几列
					// cursor.getString(columnIndex) 可以得到当前行的第几列的值
					Music music = new Music();
					// 设置歌名
					music.setmName(cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.TITLE)));

					// 设置歌手
					music.setmAuthor(cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.ARTIST)));

					// 设置歌曲时长
					music.setmTime(cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media.DURATION)));

					// 设置歌曲ID
					music.setmID(cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media._ID)));

					// 设置歌曲是否是最爱的
					music.setmIsLove(false);

					// 设置歌曲是否在播放
					music.setmIsPlay(false);

					// 设置歌曲编号
					music.setmNumber(i);

					// 设置歌曲路径
					music.setmUri(cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DATA)));

					// 设置最近播放音乐的时间
					music.setmPlayedTime(0);

					int j;
					for (j = 0; j < mApp.myMusic.size(); j++) {
						if (music.equals(mApp.myMusic.get(j)))
							break;
					}
					if (j >= mApp.myMusic.size()) {
						num++;
						mApp.myMusic.add(music);
						musicDao.addMusic(music);
					}

				}
			}
			if (mApp.myMusic.size() == 0) {

				ToastUtil.show(ScanMusicActivity.this, "没有找到音乐");
			} else {
				if (MusicHelper.PLAY_MUSIC_NUMBER == -1)
					MusicHelper.PLAY_MUSIC_NUMBER = 0;
				ToastUtil.show(ScanMusicActivity.this, "新增" + num + "首音乐!");
			}
			Log.i("扫描完成", "新增" + num + "首音乐!");
			cursor.close();
			loadingDialog.dismiss();
		}

	}
}
