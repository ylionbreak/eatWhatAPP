package science.hzl.random;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YLion on 2015/4/12.
 */
public class MySQLite extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "te.db";
	private static final int DATABASE_VERSION = 1;

	public MySQLite(Context context) {
		//CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public MySQLite(Context context, int version) {
		//CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, version);
	}

	public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("drop TABLE IF EXISTS place ");
		db.execSQL("drop TABLE IF EXISTS circle");
		db.execSQL("drop TABLE IF EXISTS restaurant");
		db.execSQL("drop TABLE IF EXISTS star");
		db.execSQL("CREATE TABLE IF NOT EXISTS place" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS circle" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongPlace INT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS restaurant" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongCircle INT, isCheck VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS star" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, starrestaurant VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop TABLE IF EXISTS place ");
		db.execSQL("drop TABLE IF EXISTS circle");
		db.execSQL("drop TABLE IF EXISTS restaurant");
		db.execSQL("drop TABLE IF EXISTS star");
	}


}
