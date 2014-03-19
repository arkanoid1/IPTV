package by.makarov.video.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import by.makarov.video.entitys.ChanelM3U;

import java.util.ArrayList;


public class DBManager {

	private static DBManager instance;
	private SQLiteDatabase db;
	private Context context;
	ChanelM3U chanel;

	private DBManager(Context cont) {
		context = cont;

	}

	public static synchronized void createInstance(Context cont) {
		if (instance == null) {
			instance = new DBManager(cont);
		}
	}

	public static synchronized DBManager getInstance() {
		return instance;
	}

	public synchronized void connect() {
		if (db == null) {
			db = new DBHelper(context).getWritableDatabase();
		}
	}

	public synchronized void disconnect() {
		if (db == null) {
			return;
		}
		if (db.isOpen()) {
			db.close();
			db = null;
		} else {
			db = null;
		}

	}

	public synchronized void addChanel(ChanelM3U c) {
		ContentValues cv = getContentValues(c);
		db.insert("iptv", null, cv);

	}

	private ContentValues getContentValues(ChanelM3U c) {
		ContentValues cv = new ContentValues();
		cv.put("TVG_NAME", c.getTvgName());
		cv.put("CANAL_NAME", c.getCanalName());
		cv.put("TVG_SHIFT", c.getTvgShift());
		cv.put("URL_CANAL", c.getUrlCanal());
//		cv.put("GROUP_TITLE", c.getGroupTitle());

		return cv;
	}

	public synchronized ArrayList<ChanelM3U> getChanels() {
		ArrayList<ChanelM3U> list = new ArrayList<ChanelM3U>();

		Cursor c = db.rawQuery("select * from iptv;", null);

		// Log.d("my", "get bd ____________");
		if (c != null) {
			if (c.moveToFirst()) {
				do {
					chanel = new ChanelM3U();
					for (String cn : c.getColumnNames()) {
						chanel.setId(c.getInt(DBHelper.ID));
						chanel.setCanalName(c.getString(DBHelper.CANAL_NAME_ID));
//						chanel.setGroupTitle(c
//								.getString(DBHelper.GROUP_TITLE_ID));
						chanel.setTvgName(c.getString(DBHelper.TVG_NAME_ID));
						chanel.setTvgShift(c.getString(DBHelper.TVG_SHIFT_ID));
						chanel.setUrlCanal(c.getString(DBHelper.URL_CANAL_ID));

					}

					list.add(chanel);
				} while (c.moveToNext());
			}
		}
		c.close();
		return list;
	}
	
	public synchronized void removeAll(){
		db.execSQL("delete from iptv;");
	}

}
