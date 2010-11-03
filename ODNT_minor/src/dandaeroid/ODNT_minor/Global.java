package dandaeroid.ODNT_minor;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

public class Global extends Application {
	ArrayList<Question> _alQuestion;
	ArrayList<String> _alCate01;
	ArrayList<String> _alCate02;
	ArrayList<String> _autoCTVstr;
	ArrayList<List2> _alBoardList;
	Bitmap _bitmap;
	int _counter;

	SQLiteDatabase _db;

	public void onCreate() {
		_db = openOrCreateDatabase("odnt.db",
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		_db.setVersion(1);
		_db.setLocale(Locale.getDefault());
		_db.setLockingEnabled(true);
		_db.execSQL("CREATE TABLE IF NOT EXISTS question (id INTEGER PRIMARY KEY AUTOINCREMENT, keyworkd TEXT, cate01 TEXT, cate02 TEXT, answer INTEGER, solution TEXT, rate INTEGER, filename TEXT);");
		_db.execSQL("CREATE TABLE IF NOT EXISTS cate01 (id INTEGER PRIMARY KEY AUTOINCREMENT, label TEXT);");
		_db.execSQL("CREATE TABLE IF NOT EXISTS cate02 (id INTEGER PRIMARY KEY AUTOINCREMENT, label TEXT);");
		_db.execSQL("CREATE TABLE IF NOT EXISTS odnt_my (id INTEGER PRIMARY KEY AUTOINCREMENT, filename TEXT);");

		_alQuestion = new ArrayList<Question>();
		_alCate01 = new ArrayList<String>();
		_alCate02 = new ArrayList<String>();
		_autoCTVstr = new ArrayList<String>();
		_alBoardList = new ArrayList<List2>();

		_alCate01.add("미분류");
		_alCate02.add("미분류");

		Cursor _cursor = _db.rawQuery("SELECT * FROM question", null);
		
		Log.v("DEBUG: _cursor.getCount()", ""+_cursor.getCount());
		
		if (_cursor.getCount() > 0) {
			_cursor.moveToLast();
			_counter = _cursor.getInt(0);
		} else
			_counter = 0;
		Log.v("DEBUG", "counter : <"+_counter+">");
		_cursor = _db.rawQuery("SELECT * FROM cate01", null);
		if (_cursor.moveToFirst())
			do {
				_alCate01.add(_cursor.getString(1));
			} while (_cursor.moveToNext());
		if (_cursor.getCount() == 0) {
			_alCate01.add("8월");
			_alCate01.add("9월");
			_alCate01.add("10월");
			ContentValues _cvInsert = new ContentValues();
			_cvInsert.put("label", "8월");
			_db.insert("cate01", null, _cvInsert);
			_cvInsert.put("label", "9월");
			_db.insert("cate01", null, _cvInsert);
			_cvInsert.put("label", "10월");
			_db.insert("cate01", null, _cvInsert);
		}
		_cursor = _db.rawQuery("SELECT * FROM cate02", null);
		if (_cursor.moveToFirst())
			do {
				_alCate02.add(_cursor.getString(1));
			} while (_cursor.moveToNext());
		if (_cursor.getCount() == 0) {
			_alCate02.add("토익");
			_alCate02.add("SSAT");
			ContentValues _cvInsert = new ContentValues();
			_cvInsert.put("label", "토익");
			_db.insert("cate02", null, _cvInsert);
			_cvInsert.put("label", "SSAT");
			_db.insert("cate02", null, _cvInsert);
		}
		_cursor.close();
	}
}