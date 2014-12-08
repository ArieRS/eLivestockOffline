package com.ui.common;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context mContext;
	int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "EliveStockPref";
	private static final String IS_LOGIN = "IsLoggedIn";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_PERAN = "peran";
	public static final String KEY_ID_LEVEL_ADMIN = "id_level_admin";
	public static final String KEY_ID_KABUPATEN_KOTA = "id_kabupaten_kota";
	public static final String KEY_ID_PROVINSI = "id_provinsi";

	// Constructor
	public SessionManager(Context context) {
		this.mContext = context;
		pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	}

	public void createLoginSession(String name, String password) {
		editor = pref.edit();
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_USERNAME, name);
		editor.putString(KEY_PASSWORD, password);
		editor.commit();
	}

	public void createLoginSession(String username, String password,
			String peran, int id_level_admin, int id_kabupaten_kota,
			int id_provinsi) {
		editor =  pref.edit();
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_PASSWORD, password);
		editor.putString(KEY_PERAN, peran);
		editor.putInt(KEY_ID_LEVEL_ADMIN, id_level_admin);
		editor.putInt(KEY_ID_KABUPATEN_KOTA, id_kabupaten_kota);
		editor.putInt(KEY_ID_PROVINSI, id_provinsi);
		editor.commit();
	}
	public void createEditPeran(String peran) {
		editor = pref.edit();
		editor.putString(KEY_PERAN, peran);
		editor.commit();
	}

	public void Logout() {
		editor = pref.edit();
		editor.clear();
		editor.commit();
	}

	public boolean isLogin() {
		return pref.getBoolean(IS_LOGIN, false);
	}

	public String getUserame() {
		String name = "";
		name = pref.getString(KEY_USERNAME, "admin");
		return name;
	}

	public String getPassword() {
		String password = "";
		password = pref.getString(KEY_PASSWORD, "admin");
		return password;
	}

	public String getPeran() {
		String peran = "";
		peran = pref.getString(KEY_PERAN, "admin");
		return peran;
	}

	public int getIdLevelAdmin() {
		int levelAdmin;
		levelAdmin = pref.getInt(KEY_ID_LEVEL_ADMIN, 1);
		return levelAdmin;
	}

	public int getIdKabupatenKota() {
		int levelKabupatenKota;
		levelKabupatenKota = pref.getInt(KEY_ID_KABUPATEN_KOTA, 1);
		return levelKabupatenKota;
	}

	public int getIdProvinsi() {
		int levelProvinsi;
		levelProvinsi = pref.getInt(KEY_ID_PROVINSI, 1);
		return levelProvinsi;
	}
}

//
// /**
// * Check login method wil check user login status If false it will redirect
// * user to login page Else won't do anything
// * */
// public void checkLogin() {
// // Check login status
// if (!this.isLoggedIn()) {
// // user is not logged in redirect him to Login Activity
// Intent i = new Intent(_context, LoginActivity.class);
// // Closing all the Activities
// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
// // Add new Flag to start new Activity
// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
// // Staring Login Activity
// _context.startActivity(i);
// }
//
// }
//
// /**
// * Get stored session data
// * */
// public HashMap<String, String> getUserDetails() {
// HashMap<String, String> user = new HashMap<String, String>();
// // user name
// user.put(KEY_NAME, pref.getString(KEY_NAME, null));
//
// // user email id
// user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
//
// // return user
// return user;
// }
//
// /**
// * Clear session details
// * */
// public void logoutUser(){
// // Clearing all data from Shared Preferences
// editor.clear();
// editor.commit();
//
// // After logout redirect user to Loing Activity
// Intent i = new Intent(_context, HomeActivity.);
// // Closing all the Activities
// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
// // Add new Flag to start new Activity
// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
// // Staring Login Activity
// _context.startActivity(i);
// }
//
// /**
// * Quick check for login
// * **/
// // Get Login State
// public boolean isLoggedIn() {
// return pref.getBoolean(IS_LOGIN, false);
// }
