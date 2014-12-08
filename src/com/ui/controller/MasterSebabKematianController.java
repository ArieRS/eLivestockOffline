package com.ui.controller;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MasterSebabKematianController {

	private static final String TABLE_MASTER_SEBAB_KEMATIAN = "master_sebab_kematian";
	private static final String KEY_ID = "id";
	private static final String KEY_VALUE = "value";
	
	public boolean createTable(SQLiteDatabase db) {
		boolean result = false;
		// table create TABLE_MASTER_SEBAB_KEMATIAN
		String CREATE_TABLE_MASTER_SEBAB_KEMATIAN = "CREATE TABLE "
				+ TABLE_MASTER_SEBAB_KEMATIAN + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_VALUE + " TEXT" + " );";

		CREATE_TABLE_MASTER_SEBAB_KEMATIAN += "Create Index "+TABLE_MASTER_SEBAB_KEMATIAN+" ON " 
				+ TABLE_MASTER_SEBAB_KEMATIAN + "( "
				+ KEY_ID + " );";
		try {
			db.execSQL(CREATE_TABLE_MASTER_SEBAB_KEMATIAN);
			result = true;
		} catch (Exception e) {
			result = false;
			Log.d("[ERROR] CREATE TABLE", "Table: " + TABLE_MASTER_SEBAB_KEMATIAN + ", query: "
					+ CREATE_TABLE_MASTER_SEBAB_KEMATIAN + ", Error Message: " + e.getMessage());
		}
		return result;
	}
}
