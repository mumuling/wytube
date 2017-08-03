package com.cqxb.yecall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
	static final String TAG = "DBAdapter";
	
	static final String DATABASE_NAME = "MyYET";
	static final int DATABASE_VERSION = 1;
	
	final Context context;
	
	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	
	public DBHelper(Context cxt)
	{
		this.context = cxt;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{

		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			try
			{
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.wtf(TAG, "Upgrading database from version "+ oldVersion + "to "+
			 newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}
	
	//open the database
	public DBHelper open() throws SQLException
	{
		db = DBHelper.getReadableDatabase();
		return this;
	}
	//close the database
	public void close()
	{
		DBHelper.close();
	}
	
	/**
	 * 修改数据
	 * @param String table  要插入的表名
	 * @param String nullColumn  不为空的值
	 * @param ContentValues initialValues  要保存的值 
	 * @return
	 */
	public boolean insertData(String table, String nullColumn,ContentValues initialValues)
	{
		Long insertOk=0L;
		try {
			insertOk=db.insert(table, nullColumn, initialValues);
			Log.i(TAG, "数据操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "error:" +e.getMessage()+"  "+e.getLocalizedMessage());
		}
		return insertOk>0;
	}
	
	/**
	 * 删除数据
	 * @param String table   要删除的表名
	 * @param String whereClause  要删除的条件 例如 xx=?
	 * @param String[] whereArgs  删除条件的值
	 * @return
	 */
	public boolean deleteData(String table,String whereClause,String[] whereArgs)
	{
		int deleteOk=0;
		try {
			deleteOk=db.delete(table, whereClause, whereArgs);
			Log.i(TAG, "数据操作成功 :"+deleteOk);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "error:" +e.getMessage()+"  "+e.getLocalizedMessage());
		}
		return deleteOk>0;
	}
	
	/**
	 * 获取数据
	 * @param String sql
	 * @param String[] params 如果查询语句有条件则 把条件值依次序放入 params
	 * @return
	 * @throws SQLException
	 */
	public Cursor getData(String sql,String[] params) throws SQLException
	{
		Cursor mCursor = null;
		try {
			mCursor=db.rawQuery(sql, params);
			if (mCursor != null)
				mCursor.moveToFirst();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "error:" +e.getMessage()+"  "+e.getLocalizedMessage());
		}
		return mCursor;
	}
	
	/**
	 * 修改数据
	 * @param String table 要修改的表名
	 * @param ContentValues values 要修改的参数
	 * @param String whereClause 条件 例如 xx=?
	 * @param String[] whereArgs 条件的值
	 * @return
	 */
	public boolean updateData(String table,ContentValues values, String whereClause, String[] whereArgs)
	{
		int updateOk=0;
		try {
			updateOk=db.update(table, values, whereClause, whereArgs);
			Log.i(TAG, "数据操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "error:" +e.getMessage()+"  "+e.getLocalizedMessage()+" ");
		}
		return updateOk>0;
	}
	
	
	/**
	 * 
	 * @param String sql
	 * @param Object[] bindArgs 参数
	 */
	public boolean execSql(String sql,Object[] bindArgs){
		boolean bool=false;
		try {
			if(bindArgs==null){
				db.execSQL(sql);
				bool=true;
			}else {
				db.execSQL(sql, bindArgs);
				bool=true;
			}
			Log.i(TAG, "数据操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "error:" +e.getMessage()+"  "+e.getLocalizedMessage());
			bool=false;
		}
		return bool;
	}
}
