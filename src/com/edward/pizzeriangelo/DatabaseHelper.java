package com.edward.pizzeriangelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME ="angelo.db";
	public static final String name ="name";
	public static final String tipo="tipo";
	public static final String zutaten="zutaten";
	public static final String preis="preis";
	public static final String image="image";
	public static final String cantidad="cantidad";
	public static final String item="item";

	private static final int DATABASE_VERSION = 1;
	 //Database creation sql statement
	private static final String DATABASE_CREATE = "CREATE TABLE essen(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
			" name TEXT, tipo INTEGER, zutaten TEXT, preis TEXT, image TEXT, item INTEGER, cantidad INTEGER);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		}
	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS essen");
		onCreate(db);
	}
}