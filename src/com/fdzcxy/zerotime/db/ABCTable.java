package com.fdzcxy.zerotime.db;
/**
 * 单词的表字段
 * 
 * @author Administrator
 * 
 */
public interface ABCTable {

	/**
	 * 数据库里单词表的表名
	 */
	public static final String ABC_TABLE_NAME = "abc_table";
	
	/**
	 * 英文拼写
	 */
	public static final  String SPELLING ="spelling";

	/**
	 * 音标
	 */
	public static final  String PHONETICALPHABET = "phoneticalphabet";

	/**
	 * 中文意思
	 */
	public static final  String MEANING ="meaning";
	/**
	 * 编号
	 */
	public static final  String ID = "id";
	/**
	 * 是否记过
	 */
	public static final  String ISREMEMBERANCE ="isremeberance";
	
	
	
	// 字段的索引号
	public static final int INDEX_SPELLING = 0;
	public static final int INDEX_PHONETICALPHABET = 1;
	public static final int INDEX_MEANING = 2;
	public static final int INDEX_ID = 3;
	public static final int INDEX_ISREMEMBERANCE = 4;

	
	/**
	 * SQL,创建表
	 */
	public static final String SQL_TABLE_CREATE = new StringBuilder()
			.append("CREATE TABLE IF NOT EXISTS ").append(ABC_TABLE_NAME)
			.append("(")
			.append(SPELLING).append(" TEXT,")
			.append(PHONETICALPHABET).append(" TEXT,")
			.append(MEANING).append(" TEXT,")
			.append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
			.append(ISREMEMBERANCE).append(" INTEGER")
			.append(");")
			.toString();
	/**
	 * SQL,向表插入一条数据
	 */
	public static final String SQL_DATA_ADD = "insert into "
			+ ABC_TABLE_NAME
			+ " (spelling,phoneticalphabet,meaning,id,isremeberance) values(?,?,?,?,?)";

	/**
	 * SQL,向表删除一条数据
	 */
	public static final String SQL_DATA_DEL = "delete from " + ABC_TABLE_NAME
			+ " where id=?";
	/**
	 * SQL,向表更新一条数据
	 */
	public static final String SQL_UPDATE_DEL = "update " + ABC_TABLE_NAME+" set "+"spelling=?,phoneticalphabet=?,meaning=?,id=?,isremeberance=?"
			+ " where id=?";
}
