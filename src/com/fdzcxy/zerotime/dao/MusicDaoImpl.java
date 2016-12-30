package com.fdzcxy.zerotime.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.fdzcxy.zerotime.db.MusicDB;
import com.fdzcxy.zerotime.db.MusicTable;
import com.fdzcxy.zerotime.domain.Music;

public class MusicDaoImpl implements MusicDao, MusicTable {

	private static final String TAG = MusicDaoImpl.class.getSimpleName();

	public static SQLiteDatabase Db;

	public MusicDaoImpl(Context context) {
		super();
		// 获取单例模式的数据库实例
		Db = MusicDB.getInstance(context).getDB();
	}

	/**
	 * 添加一条音乐数据
	 * 
	 * @param note
	 * @return
	 */
	public void addMusic(Music music) {
		// 入库成功后的id
		if (null == music) {
			Log.w(TAG, "Add note:note is null!");
		}
		// 将boolean转成int型存入数据库中
		int islove = 0;
		if (music.getmIsLove())
			islove = 1;
		int isplay = 0;
		if (music.getmIsPlay())
			isplay = 1;

		Object[] object = new Object[] { music.getmName(), music.getmAuthor(),
				music.getmTime(), music.getmID(), music.getmNumber(), islove,
				isplay, music.getmUri(), music.getmPlayedTime() };
		// Log.i(TAG, "存入"+music.toString());
		Db.execSQL(SQL_DATA_ADD, object);
	}

	/**
	 * 查询全部音乐
	 * 
	 * @return
	 */
	public List<Music> loadMusic() {
		// 创建返回值
		List<Music> musicList = new ArrayList<Music>();
		// 查询条件（音乐编号 降序）
		String order = NUMBER + " ASC";
		// 获取游标
		Cursor cursor = null;
		try {
			cursor = Db.query(TABLE_NAME, null, null, null, null, null, order);
			if (cursor != null && cursor.moveToFirst()) {
				// 循环取出所有数值
				do {
					String name = cursor.getString(INDEX_NAME);
					String author = cursor.getString(INDEX_AUTHOR);
					int time = cursor.getInt(INDEX_Time);
					int id = cursor.getInt(INDEX_ID);
					int number = cursor.getInt(INDEX_NUMBER);
					Boolean islove = false;
					if (cursor.getInt(INDEX_ISLOVE) == 1)
						islove = true;
					Boolean isplay = false;
					if (cursor.getInt(INDEX_ISPLAY) == 1)
						isplay = true;
					String uri = cursor.getString(INDEX_URI);
					int PlayedTime = cursor.getInt(INDEX_PLAYEDTIME);
					Music music = new Music(name, author, time, id, number,
							islove, isplay, uri, PlayedTime);
					// Log.i(TAG, "读1:" + music.getmUri());
					// Log.i(TAG, "读2:" + music.getmTime());
					// 添加到集合中
					musicList.add(music);
				} while (cursor.moveToNext());
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "Query all note failed!" + e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return musicList;
	}

	/**
	 * 根据id删除音乐
	 * 
	 * @param id
	 */
	public void delMusic(int id) {
		Log.i(TAG, "删除音乐数据编号：" + id);
		Db.execSQL(SQL_DATA_DEL, new Object[] { id });
	}

	/**
	 * 根据id更新音乐
	 * 
	 * @param id
	 */
	public void updateMusic(Music music) {
//		Log.i(TAG, "更新音乐数据编号：" + music.getmID());

		// 入库成功后的id
		if (null == music) {
			Log.w(TAG, "Add note:note is null!");
		}
		// 将boolean转成int型存入数据库中
		int islove = 0;
		if (music.getmIsLove())
			islove = 1;
		int isplay = 0;
		if (music.getmIsPlay())
			isplay = 1;

		Object[] object = new Object[] { music.getmName(), music.getmAuthor(),
				music.getmTime(), music.getmID(), music.getmNumber(), islove,
				isplay, music.getmUri(), music.getmPlayedTime(), music.getmID() };
		Db.execSQL(SQL_UPDATE_DEL, object);
		// 另一种方法更新
		// ContentValues update =new ContentValues();
		// update.put(NAME , music.getmName());
		// update.put(AUTHOR , music.getmAuthor());
		// update.put(Time , music.getmTime());
		// update.put(ID , music.getmID());
		// update.put(NUMBER , music.getmNumber());
		// update.put(ISLOVE , 0);
		// update.put(ISPLAY , 0);
		// update.put(URI , music.getmUri());
		// update.put(PLAYEDTIME , 0);
		// Db.update(TABLE_NAME, update, ID+"="+id, null);
	}
}
