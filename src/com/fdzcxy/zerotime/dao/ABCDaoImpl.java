package com.fdzcxy.zerotime.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.fdzcxy.zerotime.db.ABCTable;
import com.fdzcxy.zerotime.db.MusicDB;
import com.fdzcxy.zerotime.domain.Music;
import com.fdzcxy.zerotime.domain.Word;

public class ABCDaoImpl implements ACBDao, ABCTable {

	private static final String TAG = ABCDaoImpl.class.getSimpleName();

	public static SQLiteDatabase Db;

	public ABCDaoImpl(Context context) {
		super();
		// 获取单例模式的数据库实例
		Db = MusicDB.getInstance(context).getDB();
	}

	public List<Word> loadWord() {
		// 创建返回值
		List<Word> wordList = new ArrayList<Word>();
		// 查询条件（音乐编号 降序）
		String order = ID + " ASC";
		// 获取游标
		Cursor cursor = null;
		try {
			cursor = Db.query(ABC_TABLE_NAME, null, null, null, null, null,
					order);
			if (cursor != null && cursor.moveToFirst()) {
				// 循环取出所有数值
				do {
					String spelling = cursor.getString(INDEX_SPELLING);
					String phoneticAlphabet = cursor
							.getString(INDEX_PHONETICALPHABET);
					String meaning = cursor.getString(INDEX_MEANING);
					int id = cursor.getInt(INDEX_ID);
					Boolean isRememberance = false;
					if (cursor.getInt(INDEX_ISREMEMBERANCE) == 1)
						isRememberance = true;

					Word word = new Word(spelling, phoneticAlphabet, meaning,
							id, isRememberance);
					//Log.i(TAG, "读1:" + word.toString());
					// Log.i(TAG, "读2:" + music.getmTime());
					// 添加到集合中
					wordList.add(word);
				} while (cursor.moveToNext());
			}
		} catch (SQLiteException e) {
			Log.e(TAG, "Query all note failed!" + e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return wordList;
	}

	public void addWord(Word word) {

		// 入库成功后的id
		if (null == word) {
			Log.w(TAG, "Add note:note is null!");
		}
		// 将boolean转成int型存入数据库中
		int isRememberance = 0;
		if (word.ismIsRememberance())
			isRememberance = 1;

		Object[] object = new Object[] { word.getmSpelling(),
				word.getmPhoneticAlphabet(), word.getmMeaning(), word.getmId(),
				isRememberance };
//		Log.i(TAG, "存入" + word.toString());
		Db.execSQL(SQL_DATA_ADD, object);
	}

	/**
	 * 根据id更新单词
	 * 
	 * @param id
	 */
	public void updateWord(Word word) {
//		Log.i(TAG, "更新数据编号：" + word.getmId());
		if (null == word) {
			Log.w(TAG, "Add note:note is null!");
		}
		// 将boolean转成int型存入数据库中
		int isRememberance = 0;
		if (word.ismIsRememberance())
			isRememberance = 1;

		Object[] object = new Object[] { word.getmSpelling(),
				word.getmPhoneticAlphabet(), word.getmMeaning(), word.getmId(),
				isRememberance , word.getmId()};
//		Log.i(TAG, "更新" + word.toString());
		Db.execSQL(SQL_UPDATE_DEL, object);
	}
}
