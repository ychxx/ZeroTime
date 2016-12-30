package com.fdzcxy.zerotime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * SQLiteOpenHelper的帮助类：创建数据库，创建表，更新表功能
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	
	/**
	 * 创建数据库
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, MusicTable.MUSIC_DB_NAME, null, 1);
		Log.i("db", "创建数据库");
	}
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}
	/**
	 * 创建表
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MusicTable.SQL_TABLE_CREATE);
		db.execSQL(ABCTable.SQL_TABLE_CREATE);
		Log.i("db", "创建表");
	}
	
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(MusicTable.SQL_TABLE_CREATE);
		db.execSQL(ABCTable.SQL_TABLE_CREATE);
		Log.i("db", "创建表");
	}
}