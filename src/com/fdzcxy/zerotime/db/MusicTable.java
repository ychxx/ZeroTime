package com.fdzcxy.zerotime.db;
/**
 * 音乐的表字段
 * 
 * @author Administrator
 * 
 */
public interface MusicTable {
	/**
	 * 音乐数据库名
	 */
	public static final String MUSIC_DB_NAME="dbmusic.db";
	/**
	 * 音乐数据库里音乐表的表名
	 */
	public static final String TABLE_NAME = "music_table";
	/**
	 * 歌名
	 */
	public static final String NAME = "name";
	/**
	 * 作者
	 */
	public static final String AUTHOR = "author";
	/**
	 * 时长
	 */
	public static final String Time = "time";
	/**
	 * 歌曲id,唯一主键
	 */
	public static final String ID = "id";
	/**
	 * 歌曲编号,排序
	 */
	public static final String NUMBER = "number";
	/**
	 * 是否是最爱的
	 */
	public static final String ISLOVE = "islove";

	/**
	 * 是否在播放
	 */
	public static final String ISPLAY = "isplay";
	/**
	 * 歌曲路径
	 */
	public static final String URI = "uri";
	/**
	 * 最近播放音乐的时间
	 * 0代表最近没有播放过该首音乐
	 */
	public static final String PLAYEDTIME = "PlayedTime";
	// 字段的索引号
	public static final int INDEX_NAME = 0;
	public static final int INDEX_AUTHOR = 1;
	public static final int INDEX_Time = 2;
	public static final int INDEX_ID = 3;
	public static final int INDEX_NUMBER = 4;
	public static final int INDEX_ISLOVE = 5;
	public static final int INDEX_ISPLAY = 6;
	public static final int INDEX_URI = 7;
	public static final int INDEX_PLAYEDTIME = 8;
	
	/**
	 * SQL,创建表
	 */
	public static final String SQL_TABLE_CREATE = new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
			.append("(")
			.append(NAME).append(" TEXT,")
			.append(AUTHOR).append(" TEXT,")
			.append(Time).append(" INTEGER,")
			.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
			.append(NUMBER).append(" INTEGER,")
			.append(ISLOVE).append(" INTEGER,")
			.append(ISPLAY).append(" INTEGER,")
			.append(URI).append(" TEXT,")
			.append(PLAYEDTIME).append(" INTEGER")
			.append(");")
			.toString();
	/**
	 * SQL,向表插入一条数据
	 */
	public static final String SQL_DATA_ADD = "insert into "
			+ TABLE_NAME
			+ " (name,author,time,id,number,islove,isplay,uri,PlayedTime) values(?,?,?,?,?,?,?,?,?)";
	
	/**
	 * SQL,向表删除一条数据
	 */
	public static final String SQL_DATA_DEL = "delete from " + TABLE_NAME
			+ " where id=?";
	/**
	 * SQL,向表更新一条数据
	 */
	public static final String SQL_UPDATE_DEL = "update " + TABLE_NAME+" set "+"name=?,author=?,time=?,id=?,number=?,islove=?,isplay=?,uri=?,PlayedTime=?"
			+ " where id=?";
}
