package by.makarov.video.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final int ID =0;
	public static final int TVG_NAME_ID = 1;
	public static final int CANAL_NAME_ID = 2;
	public static final int TVG_SHIFT_ID = 3;
	public static final int URL_CANAL_ID = 4;
	public static final int GROUP_TITLE_ID = 5;
//	public static final int CANAL_IMAGE_ID = 6;
	
	public DBHelper(Context context) {
		super(context, "iptv", null, 1);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "create table iptv(_id integer  primary key autoincrement, TVG_NAME text, CANAL_NAME text, " +
				"TVG_SHIFT text, URL_CANAL text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
