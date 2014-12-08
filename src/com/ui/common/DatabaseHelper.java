package com.ui.common;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsKejadianBeranak;
import com.ui.model.database.elsKejadianIb;
import com.ui.model.database.elsKejadianKematian;
import com.ui.model.database.elsLokasi;
import com.ui.model.database.elsPemeriksaanKesehatan;
import com.ui.model.database.elsPerubahanKepemilikan;
import com.ui.model.database.elsPetugas;
import com.ui.model.database.elsProvinsi;
import com.ui.model.database.elsSapiBetina;
import com.ui.model.database.masterBangsaSapi;
import com.ui.model.database.masterBentukWajahSapi;
import com.ui.model.database.masterLevelPetugas;
import com.ui.model.database.masterPenyakitSapi;
import com.ui.model.database.masterPeranPetugas;
import com.ui.model.database.masterSebabKematian;
import com.ui.model.database.masterTypeLokasi;
import com.ui.model.database.masterWarnaSapi;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.LaporanAIModel;
import com.ui.model.laporan.LaporanKelahiranPedetModel;
import com.ui.model.laporan.LaporanKepemilikanModel;
import com.ui.model.laporan.LaporanLokasiSapiModel;
import com.ui.model.laporan.LaporanPenyakitModel;
import com.ui.model.laporan.LaporanPenyembelihanModel;
import com.ui.model.laporan.LaporanPopulasiArea_LokasiModel;
import com.ui.model.laporan.LaporanSapiProduktifModel;
import com.ui.model.laporan.MasterKabupatenKotaModel;
import com.ui.model.laporan.SearchDataSapiResultModel;
import com.ui.model.laporan.Search_DataSapiModel;
import com.ui.model.laporan.Search_KejadianBeranakModel;
import com.ui.model.laporan.Search_KejadianIBModel;
import com.ui.model.laporan.Search_KejadianKematianModel;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Name
	private static final String DATABASE_NAME = "db_els";
	public static final String DB_PATH = "/data/data/com.ui.elivestock/databases/";
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Table Names
	// MASTER
	private static final String TABLE_MASTER_BENTUK_WAJAH_SAPI = "master_bentuk_wajah_sapi";
	private static final String TABLE_MASTER_BANGSA_SAPI = "master_bangsa_sapi";
	private static final String TABLE_MASTER_SEBAB_KEMATIAN = "master_sebab_kematian";
	private static final String TABLE_MASTER_TYPE_LOKASI = "master_type_lokasi";
	private static final String TABLE_MASTER_PENYAKIT_SAPI = "master_penyakit_sapi";
	private static final String TABLE_MASTER_PERAN_PETUGAS = "master_peran_petugas";
	private static final String TABLE_MASTER_WARNA_SAPI = "master_warna_sapi";
	private static final String TABLE_MASTER_LEVEL_PETUGAS = "master_level_petugas";
	private static final String TABLE_ELS_PROVINSI = "els_provinsi";
	// OTHER TABLE
	private static final String TABLE_ELS_KEJADIAN_KEMATIAN = "els_kejadian_kematian";
	private static final String TABLE_ELS_KEJADIAN_BERANAK = "els_kejadian_beranak";
	private static final String TABLE_ELS_KEJADIAN_IB = "els_kejadian_ib";
	private static final String TABLE_ELS_KABUPATEN_KOTA = "els_kabupaten_kota";
	private static final String TABLE_ELS_PEMERIKSAAN_KESEHATAN = "els_pemeriksaan_kesehatan";
	private static final String TABLE_ELS_SAPI_BETINA = "els_sapi_betina";
	private static final String TABLE_ELS_PETUGAS = "els_petugas";
	private static final String TABLE_ELS_LOKASI = "els_lokasi";
	private static final String TABLE_ELS_PERUBAHAN_KEPEMILIKAN = "els_perubahan_kepemilikan";
	private static final String TABLE_LAST_SYNC = "last_sync";
	private static final String TABLE_TRAKSAKSI = "tbl_traksaksi";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_VALUE = "value";

	// TABLE_MASTER_PERAN_PETUGAS
	private static final String KEY_DESCRIPTION = "description";

	// TABLE_ELS_KABUPATEN_KOTA
	private static final String KEY_ID_KABUPATEN_KOTA = "id_kabupaten_kota";
	private static final String KEY_NAMA_KABUPATEN_KOTA = "nama_kabupaten_kota";

	// TABLE_ELS_LOKASI
	private static final String KEY_IDL = "idl";
	private static final String KEY_NAMA_KONTAK = "nama_kontak";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_NO_TELEPON = "no_telepon";
	private static final String KEY_TYPE = "type";
	private static final String KEY_ID_PETUGAS = "id_petugas";

	// TABLE KEJADIAN IB
	private static final String KEY_KODE_KEJADIAN_IB = "kode_kejadian_ib";
	private static final String KEY_KODE_STRAW = "kode_straw";
	private static final String KEY_TANGGAL_IB = "tanggal_ib";
	private static final String KEY_NIT = "nit";

	// TABLE_ELS_PROVINSI Table - column names
	private static final String KEY_ID_PROVINSI = "id_provinsi";
	private static final String KEY_NAMA_PROVINSI = "nama_provinsi";

	// TABLE_ELS_KEJADIAN_KEMATIAN Table - column names
	private static final String KEY_TANGGAL_KEMATIAN = "tanggal_kematian";
	private static final String KEY_SEBAB_KEMATIAN = "sebab_kematian";
	private static final String KEY_IDL_KEMATIAN = "idl_kematian";
	private static final String KEY_NIT_KEMATIAN = "nit";

	// TABLE_ELS_PETUGAS Table - column names
	private static final String KEY_NAMA = "nama";
	private static final String KEY_PERAN = "peran";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_ID_LEVEL_ADMIN = "id_level_admin";

	// TABLE_ELS_SAPI_BETINA
	private static final String KEY_TANGGAL_LAHIR = "tanggal_lahir";
	private static final String KEY_BANGSA = "bangsa";
	private static final String KEY_NIT_INDUK = "nit_induk";
	private static final String KEY_BENTUK_WAJAH = "bentuk_wajah";
	private static final String KEY_WARNA = "warna";
	private static final String KEY_STATUS_PUNUK = "status_punuk";
	private static final String KEY_STATUS_AKSESORIS_KAKI = "status_aksesoris_kaki";
	private static final String KEY_STATUS_KEPEMILIKAN = "status_kepemilikan";

	// TABLE ELS PEMERIKSAAN KESEHATAN
	private static final String KEY_KODE_PEMERIKSAAN_KESEHATAN = "kode_pemeriksaan_kesehatan";
	private static final String KEY_DIAGNOSA = "diagnosa";
	private static final String KEY_PERLAKUAN = "perlakuan";
	private static final String KEY_TANGGAL_PERIKSA = "tanggal_periksa";

	// TABLE ELS PERUBAHAN KEPEMILIKAN
	private static final String KEY_KODE_RIWAYAT_KEPEMILIKAN = "kode_riwayat_kepemilikan";
	private static final String KEY_IDL_SEBELUM = "idl_sebelum";
	private static final String KEY_IDL_SESUDAH = "idl_sesudah";
	private static final String KEY_TANGGAL_KEJADIAN = "tanggal_kejadian";

	// TABLE ELS KEJADIAN BERANAK
	private static final String KEY_KODE_KEJADIAN_BERANAK = "kode_kejadian_beranak";
	private static final String KEY_TANGGAL_BERANAK = "tanggal_beranak";
	private static final String KEY_BANYAKNYA_ANAK_BETINA = "banyaknya_anak_betina";
	private static final String KEY_BANYAKNYA_ANAK_JANTAN = "banyaknya_anak_jantan";

	// KEY for Report / Laporan
	private static final String KEY_LOKASI = "lokasi";

	// Table Create Statements
	// table create TABLE_MASTER_SEBAB_KEMATIAN
	private static final String CREATE_TABLE_MASTER_SEBAB_KEMATIAN = "CREATE TABLE "
			+ TABLE_MASTER_SEBAB_KEMATIAN
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT"
			+ " ) ";
	// table create TABLE_MASTER_PERAN_PETUGAS
	private static final String CREATE_TABLE_MASTER_PERAN_PETUGAS = "CREATE TABLE "
			+ TABLE_MASTER_PERAN_PETUGAS
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_VALUE
			+ " TEXT, "
			+ KEY_DESCRIPTION + " TEXT " + " ) ";
	// table create TABLE_MASTER_BANGSA_SAPI
	private static final String CREATE_TABLE_MASTER_BANGSA_SAPI = "CREATE TABLE "
			+ TABLE_MASTER_BANGSA_SAPI
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT"
			+ " ) ";
	// table create TABLE_ELS_KABUPATEN_KOTA
	private static final String CREATE_TABLE_ELS_KABUPATEN_KOTA = "CREATE TABLE "
			+ TABLE_ELS_KABUPATEN_KOTA
			+ " ( "
			+ KEY_ID_KABUPATEN_KOTA
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_ID_PROVINSI
			+ " INTEGER, " + KEY_NAMA_KABUPATEN_KOTA + " TEXT" + " ) ";
	// table create TABLE_ELS_LOKASI
	private static final String CREATE_TABLE_ELS_LOKASI = "CREATE TABLE "
			+ TABLE_ELS_LOKASI + " ( " + KEY_IDL
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAMA_KONTAK
			+ " TEXT, " + KEY_ID_KABUPATEN_KOTA + " INTEGER," + KEY_ADDRESS
			+ " TEXT, " + KEY_NO_TELEPON + " TEXT, " + KEY_TYPE + " TEXT, "
			+ KEY_ID_PETUGAS + " INTEGER " + " ) ";
	// table create TABLE_MASTER_BENTUK_WAJAH_SAPI
	private static final String CREATE_TABLE_MASTER_BENTUK_WAJAH_SAPI = "CREATE TABLE "
			+ TABLE_MASTER_BENTUK_WAJAH_SAPI
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT "
			+ " ) ";
	// table create TABLE_ELS_KEJADIAN_IB
	private static final String CREATE_TABLE_ELS_KEJADIAN_IB = "CREATE TABLE "
			+ TABLE_ELS_KEJADIAN_IB + " ( " + KEY_KODE_KEJADIAN_IB
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_KODE_STRAW
			+ " TEXT, " + KEY_TANGGAL_IB + " TEXT, " + KEY_NIT + " INTEGER, "
			+ KEY_ID_PETUGAS + " INTEGER " + " ) ";
	// table create TABLE_MASTER_TYPE_LOKASI
	private static final String CREATE_TABLE_MASTER_TYPE_LOKASI = "CREATE TABLE "
			+ TABLE_MASTER_TYPE_LOKASI
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT "
			+ " ) ";
	// table create TABLE_MASTER_PENYAKIT_SAPI
	private static final String CREATE_TABLE_MASTER_PENYAKIT_SAPI = "CREATE TABLE "
			+ TABLE_MASTER_PENYAKIT_SAPI
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT "
			+ " ) ";
	// table create TABLE_MASTER_LEVEL_PETUGAS
	private static final String CREATE_TABLE_MASTER_LEVEL_PETUGAS = "CREATE TABLE "
			+ TABLE_MASTER_LEVEL_PETUGAS
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT "
			+ " ) ";
	// table create TABLE_MASTER_WARNA_SAPI
	private static final String CREATE_TABLE_MASTER_WARNA_SAPI = "CREATE TABLE "
			+ TABLE_MASTER_WARNA_SAPI
			+ " ( "
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_VALUE
			+ " TEXT "
			+ " ) ";
	// table create TABLE_ELS_KEJADIAN_KEMATIAN
	private static final String CREATE_TABLE_ELS_KEJADIAN_KEMATIAN = "CREATE TABLE "
			+ TABLE_ELS_KEJADIAN_KEMATIAN
			+ " ( "
			+ KEY_TANGGAL_KEMATIAN
			+ " TEXT,"
			+ KEY_SEBAB_KEMATIAN
			+ " TEXT, "
			+ KEY_IDL_KEMATIAN
			+ " TEXT, "
			+ KEY_NIT
			+ " TEXT, "
			+ KEY_ID_PETUGAS
			+ " TEXT "
			+ " ) ";
	// table create TABLE_ELS_PROVINSI
	private static final String CREATE_TABLE_ELS_PROVINSI = "CREATE TABLE "
			+ TABLE_ELS_PROVINSI + " ( " + KEY_ID_PROVINSI
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAMA_PROVINSI
			+ " TEXT " + " ) ";
	// table create TABLE_ELS_PETUGAS
	private static final String CREATE_TABLE_ELS_PETUGAS = "CREATE TABLE "
			+ TABLE_ELS_PETUGAS + " ( " + KEY_ID_PETUGAS
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAMA + " TEXT, "
			+ KEY_PERAN + " TEXT," + KEY_NO_TELEPON + " TEXT, " + KEY_USERNAME
			+ " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_ID_LEVEL_ADMIN
			+ " INTEGER, " + KEY_ID_KABUPATEN_KOTA + " INTEGER, "
			+ KEY_ID_PROVINSI + " INTEGER " + " ) ";
	// table create TABLE_ELS_SAPI_BETINA
	private static final String CREATE_TABLE_ELS_SAPI_BETINA = "CREATE TABLE "
			+ TABLE_ELS_SAPI_BETINA + " ( " + KEY_NIT
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_IDL + " INTEGER, "
			+ KEY_TANGGAL_LAHIR + " TEXT, " + KEY_BANGSA + " TEXT, "
			+ KEY_NIT_INDUK + " INTEGER, " + KEY_BENTUK_WAJAH + " TEXT, "
			+ KEY_WARNA + " TEXT, " + KEY_STATUS_PUNUK + " INTEGER, "
			+ KEY_STATUS_AKSESORIS_KAKI + " INTEGER, " + KEY_STATUS_KEPEMILIKAN
			+ " INTEGER " + " ) ";
	// table create TABLE_ELS_PEMERIKSAAN_KESEHATAN
	private static final String CREATE_TABLE_ELS_PEMERIKSAAN_KESEHATAN = "CREATE TABLE "
			+ TABLE_ELS_PEMERIKSAAN_KESEHATAN
			+ " ( "
			+ KEY_KODE_PEMERIKSAAN_KESEHATAN
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_DIAGNOSA
			+ " TEXT, "
			+ KEY_PERLAKUAN
			+ " TEXT, "
			+ KEY_NIT
			+ " INTEGER, "
			+ KEY_ID_PETUGAS
			+ " INTEGER, "
			+ KEY_TANGGAL_PERIKSA
			+ " TEXT "
			+ " ) ";
	// table create TABLE_ELS_PERUBAHAN_KEPEMILIKAN
	private static final String CREATE_TABLE_ELS_PERUBAHAN_KEPEMILIKAN = "CREATE TABLE "
			+ TABLE_ELS_PERUBAHAN_KEPEMILIKAN
			+ " ( "
			+ KEY_KODE_RIWAYAT_KEPEMILIKAN
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_IDL_SEBELUM
			+ " INTEGER, "
			+ KEY_IDL_SESUDAH
			+ " INTEGER, "
			+ KEY_TANGGAL_KEJADIAN + " TEXT, " + KEY_NIT + " INTEGER " + " ) ";
	// table create TABLE_ELS_KEJADIAN_BERANAK
	private static final String CREATE_TABLE_ELS_KEJADIAN_BERANAK = "CREATE TABLE "
			+ TABLE_ELS_KEJADIAN_BERANAK
			+ " ( "
			+ KEY_KODE_KEJADIAN_BERANAK
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_NIT
			+ " INTEGER, "
			+ KEY_TANGGAL_BERANAK
			+ " TEXT, "
			+ KEY_BANYAKNYA_ANAK_BETINA
			+ " TEXT, " + KEY_BANYAKNYA_ANAK_JANTAN + " TEXT " + " ) ";

	private static final String CREATE_TABLE_LAST_SYNC = "CREATE TABLE "
			+ TABLE_LAST_SYNC + " ( Id  INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " MethodName " + " TEXT, " + " LastModifUpdate " + " TEXT, "
			+ " CONSTRAINT sqlite_autoindex_LastSynch_1 PRIMARY KEY (Id)) ";

	private static final String CREATE_TABLE_TRANSAKSI = " CREATE TABLE "
			+ TABLE_TRAKSAKSI + " ( Id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " Guid TEXT, " + " Transaksi TEXT," + " Json TEXT, "
			+ " Query TEXT, " + " Time TEXT, " + " Status TEXT) ";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MASTER_SEBAB_KEMATIAN);
		db.execSQL(CREATE_TABLE_MASTER_PERAN_PETUGAS);
		db.execSQL(CREATE_TABLE_MASTER_BANGSA_SAPI);
		db.execSQL(CREATE_TABLE_ELS_KABUPATEN_KOTA);
		db.execSQL(CREATE_TABLE_ELS_LOKASI);
		db.execSQL(CREATE_TABLE_MASTER_BENTUK_WAJAH_SAPI);
		db.execSQL(CREATE_TABLE_ELS_KEJADIAN_IB);
		db.execSQL(CREATE_TABLE_MASTER_TYPE_LOKASI);
		db.execSQL(CREATE_TABLE_MASTER_PENYAKIT_SAPI);
		db.execSQL(CREATE_TABLE_MASTER_LEVEL_PETUGAS);
		db.execSQL(CREATE_TABLE_MASTER_WARNA_SAPI);
		db.execSQL(CREATE_TABLE_ELS_KEJADIAN_KEMATIAN);
		db.execSQL(CREATE_TABLE_ELS_PROVINSI);
		db.execSQL(CREATE_TABLE_ELS_PETUGAS);
		db.execSQL(CREATE_TABLE_ELS_SAPI_BETINA);
		db.execSQL(CREATE_TABLE_ELS_PEMERIKSAAN_KESEHATAN);
		db.execSQL(CREATE_TABLE_ELS_PERUBAHAN_KEPEMILIKAN);
		db.execSQL(CREATE_TABLE_ELS_KEJADIAN_BERANAK);
		try {
			Log.d("create", "sukses");
		} catch (Exception e) {
			Log.d("creat", e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion){
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_SEBAB_KEMATIAN);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_PERAN_PETUGAS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_BANGSA_SAPI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KABUPATEN_KOTA);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_LOKASI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_BENTUK_WAJAH_SAPI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_IB);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_TYPE_LOKASI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_PENYAKIT_SAPI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_LEVEL_PETUGAS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_WARNA_SAPI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_KEMATIAN);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PROVINSI);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PETUGAS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_SAPI_BETINA);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PEMERIKSAAN_KESEHATAN);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PERUBAHAN_KEPEMILIKAN);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_BERANAK);
			onCreate(db);
		}
	}

	public void deleteAllTable(Context context){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_SEBAB_KEMATIAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_PERAN_PETUGAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_BANGSA_SAPI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KABUPATEN_KOTA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_LOKASI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_BENTUK_WAJAH_SAPI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_IB);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_TYPE_LOKASI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_PENYAKIT_SAPI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_LEVEL_PETUGAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_WARNA_SAPI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_KEMATIAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PROVINSI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PETUGAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_SAPI_BETINA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PEMERIKSAAN_KESEHATAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_PERUBAHAN_KEPEMILIKAN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELS_KEJADIAN_BERANAK);
		
		context.deleteDatabase(DB_PATH + DATABASE_NAME);
	}
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	@Override
	public synchronized void close() {
		closeDB();
	}

	// 1 controller els provinsi
	public long createElsProvinsiWithID(elsProvinsi provinsiModel) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_PROVINSI, provinsiModel.getId_provinsi());
		values.put(KEY_NAMA_PROVINSI, provinsiModel.getNama_provinsi());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_PROVINSI, null, values);

		return todo_id;
	}

	public long createElsProvinsiWithoutID(elsProvinsi provinsiModel) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAMA_PROVINSI, provinsiModel.getNama_provinsi());
		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_PROVINSI, null, values);
		return todo_id;
	}

	public long updateElsProvinsi(elsProvinsi provinsiModel) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAMA_PROVINSI, provinsiModel.getNama_provinsi());
		// insert row
		long todo_id = db.update(TABLE_ELS_PROVINSI, values, KEY_ID_PROVINSI
				+ " = " +  provinsiModel.getId_provinsi(), null);
		return todo_id;
	}
	public long deleteElsProvinsi(int Id_provinsi) {
		SQLiteDatabase db = this.getWritableDatabase();
 
		long todo_id = db.delete(TABLE_ELS_PROVINSI, KEY_ID_PROVINSI
				+ " = " + Id_provinsi, null);
		return todo_id;
	}

	public ArrayList<String> getAllNamaProvinsi() {
		ArrayList<String> listString = new ArrayList<String>();
		String query = "SELECT " + KEY_NAMA_PROVINSI + " FROM "
				+ TABLE_ELS_PROVINSI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		if (c.moveToFirst()) {
			do {
				String namaProvinsi = "";
				namaProvinsi = c.getString(c.getColumnIndex(KEY_NAMA_PROVINSI));

				// adding to todo list
				listString.add(namaProvinsi);
			} while (c.moveToNext());
		}
		return listString;
	}

	public elsProvinsi getProvinsiById(int id) {
		String selectQuery = "SELECT  * FROM " + TABLE_ELS_PROVINSI + " WHERE "
				+ KEY_ID_PROVINSI + " = " + id + ";";
		elsProvinsi value = new elsProvinsi();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			value.setId_provinsi(c.getInt(c.getColumnIndex(KEY_ID_PROVINSI)));
			value.setNama_provinsi(c.getString(c
					.getColumnIndex(KEY_NAMA_PROVINSI)));
		}
		return value;
	}

	public void deleteProvinsiById(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ELS_PROVINSI, KEY_ID_PROVINSI + " = ?",
				new String[] { String.valueOf(id) });
	}

	public int updateToDo(elsProvinsi provinsiModel) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_PROVINSI, provinsiModel.getId_provinsi());
		values.put(KEY_NAMA_PROVINSI, provinsiModel.getNama_provinsi());

		// updating row
		return db
				.update(TABLE_ELS_PROVINSI, values, KEY_ID_PROVINSI + " = ?",
						new String[] { String.valueOf(provinsiModel
								.getId_provinsi()) });
	}

	public ArrayList<elsProvinsi> getAllProvinsi() {
		ArrayList<elsProvinsi> provinsiList = new ArrayList<elsProvinsi>();
		String selectQuery = "SELECT  * FROM " + TABLE_ELS_PROVINSI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				elsProvinsi value = new elsProvinsi();
				value.setId_provinsi(c.getInt(c.getColumnIndex(KEY_ID_PROVINSI)));
				value.setNama_provinsi(c.getString(c
						.getColumnIndex(KEY_NAMA_PROVINSI)));

				// adding to todo list
				provinsiList.add(value);
			} while (c.moveToNext());
		}
		return provinsiList;
	}

	public int getIdProvinsi(String provinceName) {
		ArrayList<elsProvinsi> provinsiList = new ArrayList<elsProvinsi>();
		String selectQuery = "SELECT " + KEY_ID_PROVINSI + " FROM "
				+ TABLE_ELS_PROVINSI + " WHERE " + KEY_NAMA_PROVINSI + " = '"
				+ provinceName + "' ;";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		int value = 0;
		if (c.moveToFirst()) {
			value = (c.getInt(c.getColumnIndex(KEY_ID_PROVINSI)));
		}
		return value;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 2 controller Master Bangsa Sapi
	public long createBangsaSapiWithID(masterBangsaSapi bangsaSapi) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, bangsaSapi.getId());
		values.put(KEY_VALUE, bangsaSapi.getValue());

		long todo_id = db.insertOrThrow(TABLE_MASTER_BANGSA_SAPI, null, values);
		return todo_id;
	}

	public long createBangsaSapiWithoutID(masterBangsaSapi bangsaSapi) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_VALUE, bangsaSapi.getValue());

		long todo_id = db.insertOrThrow(TABLE_MASTER_BANGSA_SAPI, null, values);
		return todo_id;
	}

	public long updateBangsaSapi(masterBangsaSapi bangsaSapi) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_VALUE, bangsaSapi.getValue());

		long todo_id = db.update(TABLE_MASTER_BANGSA_SAPI, values, KEY_ID
				+ " = " + String.valueOf(bangsaSapi.getId()), null);
		// (TABLE_MASTER_BANGSA_SAPI, null, values);
		return todo_id;
	}
	public long deleteBangsaSapi(int idBangsaSapi) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		long todo_id = db.delete(TABLE_MASTER_BANGSA_SAPI, KEY_ID
				+ " = " + String.valueOf(idBangsaSapi), null);
		return todo_id;
	}

	public ArrayList<masterBangsaSapi> getAllBangsaSapi() {
		ArrayList<masterBangsaSapi> bangsaList = new ArrayList<masterBangsaSapi>();
		String selectQuery = "SELECT  * FROM " + TABLE_MASTER_BANGSA_SAPI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				masterBangsaSapi value = new masterBangsaSapi();
				value.setId(c.getInt(c.getColumnIndex(KEY_ID)));
				value.setValue(c.getString(c.getColumnIndex(KEY_VALUE)));

				// adding to todo list
				bangsaList.add(value);
			} while (c.moveToNext());
		}
		return bangsaList;
	}

	public masterBangsaSapi getBangsaSapiById(int id) {
		String selectQuery = "SELECT  * FROM " + TABLE_MASTER_BANGSA_SAPI
				+ " WHERE " + KEY_ID + " = " + id + ";";
		masterBangsaSapi value = new masterBangsaSapi();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			value.setId(c.getInt(c.getColumnIndex(KEY_ID)));
			value.setValue(c.getString(c.getColumnIndex(KEY_VALUE)));
		}
		return value;
	}

	public ArrayList<String> getIdValueBangsa() {
		ArrayList<String> stringResult = new ArrayList<String>();
		String selectQuery = "SELECT id, value " + " FROM "
				+ TABLE_MASTER_BANGSA_SAPI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = new String();
				value = c.getString(c.getColumnIndex(KEY_ID)) + " | "
						+ c.getString(c.getColumnIndex(KEY_VALUE));
				stringResult.add(value);
			} while (c.moveToNext());
		}
		return stringResult;
	}

	public String getIdValueBangsa(String bangsa) {

		String selectQuery = "SELECT id, value " + " FROM "
				+ TABLE_MASTER_BANGSA_SAPI + " WHERE value = '" + bangsa
				+ "' ;";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		String stringResult = "";
		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = new String();
				value = c.getString(c.getColumnIndex(KEY_ID)) + " | "
						+ c.getString(c.getColumnIndex(KEY_VALUE));
				stringResult = value;
			} while (c.moveToNext());
		}
		return stringResult;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 3 controller els kabupaten kota
	public long createKabupatenKotaWithoutID(elsKabupatenKota kabupatenKotaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID_PROVINSI, kabupatenKotaModel.getId_provinsi());
		values.put(KEY_NAMA_KABUPATEN_KOTA,
				kabupatenKotaModel.getNama_kabupaten_kota());

		long todo_id = db.insertOrThrow(TABLE_ELS_KABUPATEN_KOTA, null, values);
		return todo_id;
	}

	public long createKabupatenKotaWithID(elsKabupatenKota kabupatenKotaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.beginTransaction();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ID_KABUPATEN_KOTA,kabupatenKotaModel.getId_kabupaten_kota());
		values.put(KEY_ID_PROVINSI, kabupatenKotaModel.getId_provinsi());
		values.put(KEY_NAMA_KABUPATEN_KOTA,kabupatenKotaModel.getNama_kabupaten_kota());
		
		long todo_id = db.insertOrThrow(TABLE_ELS_KABUPATEN_KOTA, null, values);
		
		db.setTransactionSuccessful();
		db.endTransaction();
		return todo_id;
	}
	public void createdKabupatenKotaList(ArrayList<elsKabupatenKota> listElsKabupatenKota){
		
		for (int xx=0; xx<listElsKabupatenKota.size(); xx++){
			createKabupatenKotaWithID(listElsKabupatenKota.get(xx));
		}
	}

	public long updateKabupatenKota(elsKabupatenKota kabupatenKotaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID_PROVINSI, kabupatenKotaModel.getId_provinsi());
		values.put(KEY_NAMA_KABUPATEN_KOTA,
				kabupatenKotaModel.getNama_kabupaten_kota());

		long todo_id = db.update(
				TABLE_ELS_KABUPATEN_KOTA,
				values,
				KEY_ID_KABUPATEN_KOTA
						+ " = "
						+ String.valueOf(kabupatenKotaModel
								.getId_kabupaten_kota()), null);
		return todo_id;
	}
	public long deleteKabupatenKota(int Id_kabupaten_kota) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		long todo_id = db.delete(TABLE_ELS_KABUPATEN_KOTA, KEY_ID_KABUPATEN_KOTA
				+ " = "+ String.valueOf(Id_kabupaten_kota), null);
		return todo_id;
	}

	public ArrayList<elsKabupatenKota> getAllKabupatenKota() {
		ArrayList<elsKabupatenKota> kabupatenKotaList = new ArrayList<elsKabupatenKota>();
		String selectQuery = "SELECT  * FROM " + TABLE_ELS_KABUPATEN_KOTA;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				elsKabupatenKota value = new elsKabupatenKota();
				value.setId_kabupaten_kota(c.getInt(c
						.getColumnIndex(KEY_ID_KABUPATEN_KOTA)));
				value.setNama_kabupaten_kota(c.getString(c
						.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA)));

				kabupatenKotaList.add(value);
			} while (c.moveToNext());
		}
		return kabupatenKotaList;
	}

	public ArrayList<MasterKabupatenKotaModel> getAllNamaKabupatenKotoProvinsi() {
		ArrayList<MasterKabupatenKotaModel> kabupatenKotaList = new ArrayList<MasterKabupatenKotaModel>();
		String selectQuery = "SELECT ekk.id_kabupaten_kota, ekk.id_provinsi , ekk.nama_kabupaten_kota, ep.nama_provinsi "
				+ " FROM "
				+ TABLE_ELS_KABUPATEN_KOTA
				+ " AS ekk "
				+ " LEFT JOIN "
				+ TABLE_ELS_PROVINSI
				+ " AS ep ON ep.id_provinsi = ekk.id_provinsi";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				MasterKabupatenKotaModel value = new MasterKabupatenKotaModel();
				value.setId_kabupaten_kota(c.getInt(c
						.getColumnIndex(KEY_ID_KABUPATEN_KOTA)));
				value.setId_provinsi(c.getInt(c.getColumnIndex(KEY_ID_PROVINSI)));
				value.setNama_Kabupaten_Kota(c.getString(c
						.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA)));
				value.setNama_Provinsi(c.getString(c
						.getColumnIndex(KEY_NAMA_PROVINSI)));

				kabupatenKotaList.add(value);
			} while (c.moveToNext());
		}
		return kabupatenKotaList;
	}

	public ArrayList<String> getAllNameKabupatenKota() {
		ArrayList<String> stringKabupatenKotaList = new ArrayList<String>();
		String selectQuery = "SELECT " + KEY_NAMA_KABUPATEN_KOTA + " FROM "
				+ TABLE_ELS_KABUPATEN_KOTA;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = new String();
				value = c.getString(c.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA));

				stringKabupatenKotaList.add(value);
			} while (c.moveToNext());
		}
		return stringKabupatenKotaList;
	}

	public int getIdKabupatenKota(String kabupatenKota) {
		String selectQuery = "SELECT " + KEY_ID_KABUPATEN_KOTA + " FROM "
				+ TABLE_ELS_KABUPATEN_KOTA + " WHERE "
				+ KEY_NAMA_KABUPATEN_KOTA + " = '" + kabupatenKota + "' ;";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		int value = 0;
		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			value = c.getInt(c.getColumnIndex(KEY_ID_KABUPATEN_KOTA));
		}
		return value;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 4 controller els lokasi
	public long createLokasiWithoutID(elsLokasi lokasiModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAMA_KONTAK, lokasiModel.getNama_kontak());
		values.put(KEY_ID_KABUPATEN_KOTA, lokasiModel.getId_kabupaten_kota());
		values.put(KEY_ADDRESS, lokasiModel.getAddress());
		values.put(KEY_NO_TELEPON, lokasiModel.getNo_telepon());
		values.put(KEY_TYPE, lokasiModel.getType());
		values.put(KEY_ID_PETUGAS, lokasiModel.getId_petugas());

		long todo_id = db.insertOrThrow(TABLE_ELS_LOKASI, null, values);
		return todo_id;
	}

	public long createLokasiWithID(elsLokasi lokasiModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
	    	
		values.put(KEY_IDL, lokasiModel.getIdl());
		values.put(KEY_NAMA_KONTAK, lokasiModel.getNama_kontak());
		values.put(KEY_ID_KABUPATEN_KOTA, lokasiModel.getId_kabupaten_kota());
		values.put(KEY_ADDRESS, lokasiModel.getAddress());
		values.put(KEY_NO_TELEPON, lokasiModel.getNo_telepon());
		values.put(KEY_TYPE, lokasiModel.getType());
		values.put(KEY_ID_PETUGAS, lokasiModel.getId_petugas());

		long todo_id = db.insertOrThrow(TABLE_ELS_LOKASI, null, values);
		return todo_id;
	}

	public long updateLokasi(elsLokasi lokasiModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAMA_KONTAK, lokasiModel.getNama_kontak());
		values.put(KEY_ID_KABUPATEN_KOTA, lokasiModel.getId_kabupaten_kota());
		values.put(KEY_ADDRESS, lokasiModel.getAddress());
		values.put(KEY_NO_TELEPON, lokasiModel.getNo_telepon());
		values.put(KEY_TYPE, lokasiModel.getType());
		values.put(KEY_ID_PETUGAS, lokasiModel.getId_petugas());

		long todo_id = db.update(TABLE_ELS_LOKASI, values, KEY_IDL + " = "
				+ lokasiModel.getIdl(), null);
		return todo_id;
	}
	public long deleteLokasi(String Idl) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		long todo_id = db.delete(TABLE_ELS_LOKASI, KEY_IDL + " = "+ Idl, null);
		return todo_id;
	}

	public ArrayList<elsLokasi> getAllLokasi() {
		ArrayList<elsLokasi> lokasiList = new ArrayList<elsLokasi>();
		String selectQuery = "SELECT  * FROM " + TABLE_ELS_LOKASI;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				elsLokasi value = new elsLokasi();
				value.setIdl(c.getInt(c.getColumnIndex(KEY_IDL)));
				value.setNama_kontak(c.getString(c
						.getColumnIndex(KEY_NAMA_KONTAK)));
				value.setId_kabupaten_kota(c.getInt(c
						.getColumnIndex(KEY_ID_KABUPATEN_KOTA)));
				value.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
				value.setNo_telepon(c.getString(c
						.getColumnIndex(KEY_NO_TELEPON)));
				value.setType(c.getString(c.getColumnIndex(KEY_TYPE)));

				lokasiList.add(value);
			} while (c.moveToNext());
		}
		return lokasiList;
	}

	public ArrayList<LaporanLokasiSapiModel> getDataLokasi(int idKota,
			int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanLokasiSapiModel> listLaporanLokasi = new ArrayList<LaporanLokasiSapiModel>();

		String selectQuery = " SELECT el.idl as "
				+ KEY_IDL
				+ " , "
				+ " el.nama_kontak as "
				+ KEY_NAMA_KONTAK
				+ " , "
				+ " ekk.nama_kabupaten_kota as "
				+ KEY_NAMA_KABUPATEN_KOTA
				+ " , "
				+ " el.address as "
				+ KEY_ADDRESS
				+ " , "
				+ " el.no_telepon, "
				+ " mtl.value as "
				+ KEY_TYPE
				+ " , "
				+ " ep.nama as "
				+ KEY_NAMA
				+ " FROM els_lokasi as el "
				+ " LEFT JOIN els_kabupaten_kota as ekk ON   ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_petugas as ep ON   ep.id_petugas = el.id_petugas "
				+ " LEFT JOIN master_type_lokasi as mtl ON el.type = mtl.id "
				+ " WHERE 1 = 1 ";

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanLokasiSapiModel value = new LaporanLokasiSapiModel();
				value.setIdLokasi(c.getString(c.getColumnIndex(KEY_IDL)));
				value.setNamaKontak(c.getString(c
						.getColumnIndex(KEY_NAMA_KONTAK)));
				value.setKabupatenKota(c.getString(c
						.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA)));
				value.setAlamat(c.getString(c.getColumnIndex(KEY_ADDRESS)));
				value.setNoTelepon(c.getString(c.getColumnIndex("no_telepon")));
				value.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
				value.setPetugas(c.getString(c.getColumnIndex(KEY_NAMA)));

				listLaporanLokasi.add(value);
			} while (c.moveToNext());
		}
		return listLaporanLokasi;
	}

	public ArrayList<String> getIdlKontakKota() {
		String query = "SELECT el.idl, el.nama_kontak, ekk.nama_kabupaten_kota "
				+ " FROM "
				+ TABLE_ELS_LOKASI
				+ " as el"
				+ " LEFT JOIN "
				+ TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota; ";
		ArrayList<String> result = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = c.getString(c.getColumnIndex(KEY_IDL))
						+ " | "
						+ c.getString(c.getColumnIndex(KEY_NAMA_KONTAK))
						+ " | "
						+ c.getString(c.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA));
				result.add(value);
			} while (c.moveToNext());
		}
		return result;
	}

	public String getIdlKontakKota(String namaLokasi) {
		String query = "SELECT el.idl, el.nama_kontak, ekk.nama_kabupaten_kota "
				+ " FROM "
				+ TABLE_ELS_LOKASI
				+ " as el"
				+ " LEFT JOIN "
				+ TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " WHERE ekk.nama_kabupaten_kota='" + namaLokasi + "' ; ";

		String result = "";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			String value = c.getString(c.getColumnIndex(KEY_IDL)) + " | "
					+ c.getString(c.getColumnIndex(KEY_NAMA_KONTAK)) + " | "
					+ c.getString(c.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA));
			result = value;
		}
		return result;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 5 controller master type lokasi
	public ArrayList<String> getTypeLokasi() {
		String query = "SELECT " + KEY_VALUE + " FROM "
				+ TABLE_MASTER_TYPE_LOKASI + " ; ";
		ArrayList<String> resultType = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = c.getString(c.getColumnIndex(KEY_VALUE));
				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	public int getIdTypeLokasi(String selectedType) {
		String query = "SELECT " + KEY_ID + " FROM " + TABLE_MASTER_TYPE_LOKASI
				+ " AS mtl WHERE mtl.value= '" + selectedType + "' ; ";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		int value = 0;
		if (c.moveToFirst()) {
			value = c.getInt(c.getColumnIndex(KEY_ID));
		}
		return value;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 6 controller els petugas
	public ArrayList<String> getNamaPetugas() {
		String query = "SELECT " + KEY_ID_PETUGAS + " , " + KEY_NAMA + " FROM "
				+ TABLE_ELS_PETUGAS + " ; ";
		ArrayList<String> resultType = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = c.getString(c.getColumnIndex(KEY_ID_PETUGAS))
						+ " | " + c.getString(c.getColumnIndex(KEY_NAMA));
				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	public int getIdPetugas(String user) {
		String query = "SELECT " + KEY_ID_PETUGAS + " FROM "
				+ TABLE_ELS_PETUGAS + " WHERE " + KEY_USERNAME + " = '" + user
				+ "' ; ";
		int resultType = 0;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			resultType = c.getInt(c.getColumnIndex(KEY_ID_PETUGAS));
		}
		return resultType;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 7 controller els sapi betina
	public long createDataSapiBetinaWithID(elsSapiBetina sapiBetinaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_NIT, sapiBetinaModel.getNit());
		values.put(KEY_IDL, sapiBetinaModel.getIdl());
		values.put(KEY_BANGSA, sapiBetinaModel.getBangsa());
		values.put(KEY_TANGGAL_LAHIR, sapiBetinaModel.getTanggal_lahir());
		values.put(KEY_NIT_INDUK, sapiBetinaModel.getNit_induk());
		values.put(KEY_BENTUK_WAJAH, sapiBetinaModel.getBentuk_wajah());
		values.put(KEY_WARNA, sapiBetinaModel.getWarna());
		values.put(KEY_STATUS_PUNUK, sapiBetinaModel.getStatus_punuk());
		values.put(KEY_STATUS_AKSESORIS_KAKI,sapiBetinaModel.getStatus_aksesoris_kaki());
		values.put(KEY_STATUS_KEPEMILIKAN,sapiBetinaModel.getStatus_kepemilikan());

		long todo_id = db.insertOrThrow(TABLE_ELS_SAPI_BETINA, null, values);
		return todo_id;
	}

	public long deleteDataSapiBetina(elsSapiBetina sapiBetinaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		long todo_id = db.delete(TABLE_ELS_SAPI_BETINA, KEY_NIT + " = " + sapiBetinaModel.getNit(), null);
		return todo_id;
	}
	public long updateDataSapiBetinaWithID(elsSapiBetina sapiBetinaModel) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_IDL, sapiBetinaModel.getIdl());
		values.put(KEY_TANGGAL_LAHIR, sapiBetinaModel.getTanggal_lahir());
		values.put(KEY_BANGSA, sapiBetinaModel.getBangsa());
		values.put(KEY_NIT_INDUK, sapiBetinaModel.getNit_induk());
		values.put(KEY_BENTUK_WAJAH, sapiBetinaModel.getBentuk_wajah());
		values.put(KEY_WARNA, sapiBetinaModel.getWarna());
		values.put(KEY_STATUS_PUNUK, sapiBetinaModel.getStatus_punuk());
		values.put(KEY_STATUS_AKSESORIS_KAKI,
				sapiBetinaModel.getStatus_aksesoris_kaki());
		values.put(KEY_STATUS_KEPEMILIKAN,
				sapiBetinaModel.getStatus_kepemilikan());

		long todo_id = db.update(TABLE_ELS_SAPI_BETINA, values, KEY_NIT + " = "
				+ sapiBetinaModel.getNit(), null);
		return todo_id;
	}

	public ArrayList<String> getNIT() {
		String query = "SELECT " + KEY_NIT + " FROM " + TABLE_ELS_SAPI_BETINA
				+ " ; ";
		ArrayList<String> resultType = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = c.getString(c.getColumnIndex(KEY_NIT));
				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	public ArrayList<Data_SapiModel> getDataSapi(int idKota, int idProvinsi, int idLevelAdmin) {
		ArrayList<Data_SapiModel> listDataSapi = new ArrayList<Data_SapiModel>();

		String selectQuery = " SELECT "
				+ " 	esb.nit, "
				+ " 	esb.tanggal_lahir, "
				+ " 	mbs.value as bangsa, " //esb.bangsa
				+ " 	esb.nit_induk, "
				+ " 	esb.bentuk_wajah, "
				+ " 	esb.warna, "
				+ " 	el.nama_kontak as lokasi,  " //ekk.nama_kabupaten_kota
				+ "		esb.status_punuk, "
				+ "		esb.status_aksesoris_kaki, "
				+ "		esb.status_kepemilikan "
				+ " FROM "
				+ " 	els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id"
				+ " WHERE 1 = 1 ";

		String whereQuery = "";
//		if (idKota != 0) {
//			whereQuery = " AND ekk.id_kabupaten_kota = " + String.valueOf(idKota);
//			selectQuery += whereQuery;
//		} else if (idProvinsi != 0) {
//			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
//			selectQuery += whereQuery;
//		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND ekk.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}
		selectQuery += " GROUP BY esb.nit";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Data_SapiModel value = new Data_SapiModel();
				value.setNIT(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));
				
				String tanggalLahir = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggalLahir.contains("0000-00-00")) tanggalLahir = "1970-01-01";
				value.setTanggalLahir(tanggalLahir);
				
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNITInduk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatus_punuk((statusPunuk == 1 ? "Ya" : "Tidak"));

				int statusAksesoris = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatus_aksesoris_kaki((statusAksesoris == 1 ? "Ya"
						: "Tidak"));

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatus_kepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");

				listDataSapi.add(value);
			} while (c.moveToNext());
		}
		return listDataSapi;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 8 controller els kejadian beranak
	public long createKejadianBeranakWithoutID(elsKejadianBeranak mElsKejadianBeranak) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_NIT, mElsKejadianBeranak.getNit());
		values.put(KEY_TANGGAL_BERANAK,
				mElsKejadianBeranak.getTanggal_beranak());
		values.put(KEY_BANYAKNYA_ANAK_BETINA,
				mElsKejadianBeranak.getBanyaknya_anak_betina());
		values.put(KEY_BANYAKNYA_ANAK_JANTAN,
				mElsKejadianBeranak.getBanyaknya_anak_jantan());

		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_BERANAK, null, values);
		return todo_id;
	}
	public long createKejadianBeranakWithID(elsKejadianBeranak mElsKejadianBeranak) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
	    values.put(KEY_KODE_KEJADIAN_BERANAK, mElsKejadianBeranak.getKode_kejadian_beranak());
		values.put(KEY_NIT, mElsKejadianBeranak.getNit());
		values.put(KEY_TANGGAL_BERANAK,mElsKejadianBeranak.getTanggal_beranak());
		values.put(KEY_BANYAKNYA_ANAK_BETINA,mElsKejadianBeranak.getBanyaknya_anak_betina());
		values.put(KEY_BANYAKNYA_ANAK_JANTAN,mElsKejadianBeranak.getBanyaknya_anak_jantan());

		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_BERANAK, null, values);
		return todo_id;
	}
	public long updateKejadianBeranak(elsKejadianBeranak mElsKejadianBeranak) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_NIT, mElsKejadianBeranak.getNit());
		values.put(KEY_TANGGAL_BERANAK,
				mElsKejadianBeranak.getTanggal_beranak());
		values.put(KEY_BANYAKNYA_ANAK_BETINA,
				mElsKejadianBeranak.getBanyaknya_anak_betina());
		values.put(KEY_BANYAKNYA_ANAK_JANTAN,
				mElsKejadianBeranak.getBanyaknya_anak_jantan());

		long todo_id = db.update(
				TABLE_ELS_KEJADIAN_BERANAK,
				values,
				KEY_KODE_KEJADIAN_BERANAK
						+ " = "
						+ String.valueOf(mElsKejadianBeranak
								.getKode_kejadian_beranak()), null);
		return todo_id;
	}
	public long deleteKejadianBeranak(elsKejadianBeranak mElsKejadianBeranak) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		long todo_id = db.delete(TABLE_ELS_KEJADIAN_BERANAK,
				KEY_KODE_KEJADIAN_BERANAK + " = "+ String.valueOf(mElsKejadianBeranak.getKode_kejadian_beranak()), null);
		return todo_id;
	}

	public ArrayList<Search_KejadianBeranakModel> getKejadianBeranak(int mNit) {
		String query = "SELECT " + KEY_KODE_KEJADIAN_BERANAK + " , " + KEY_NIT
				+ " , " + KEY_TANGGAL_BERANAK + " , "
				+ KEY_BANYAKNYA_ANAK_BETINA + " , " + KEY_BANYAKNYA_ANAK_JANTAN
				+ " FROM " + TABLE_ELS_KEJADIAN_BERANAK + " WHERE nit = '"
				+ mNit + "' ; ";
		ArrayList<Search_KejadianBeranakModel> resultType = new ArrayList<Search_KejadianBeranakModel>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Search_KejadianBeranakModel value = new Search_KejadianBeranakModel();
				value.setKode_kejadian_beranak(c.getInt(c
						.getColumnIndex(KEY_KODE_KEJADIAN_BERANAK)));
				value.setNIT(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setTanggalBeranak(c.getString(c
						.getColumnIndex(KEY_TANGGAL_BERANAK)));
				value.setBanyaknyaAnakBetina(c.getString(c
						.getColumnIndex(KEY_BANYAKNYA_ANAK_BETINA)));
				value.setBanyaknyaAnakJantan(c.getString(c
						.getColumnIndex(KEY_BANYAKNYA_ANAK_JANTAN)));

				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 10 controller els pemeriksaan kesehatan
	public long createPemeriksaanKesehatanWithoutID(
			elsPemeriksaanKesehatan mElsPemeriksaanKesehatan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_DIAGNOSA, mElsPemeriksaanKesehatan.getDiagnosa());
		values.put(KEY_PERLAKUAN, mElsPemeriksaanKesehatan.getPerlakuan());
		values.put(KEY_NIT, mElsPemeriksaanKesehatan.getNit());
		values.put(KEY_ID_PETUGAS, mElsPemeriksaanKesehatan.getId_petugas());
		values.put(KEY_TANGGAL_PERIKSA,
				mElsPemeriksaanKesehatan.getTanggalPeriksa());

		long todo_id = db.insertOrThrow(TABLE_ELS_PEMERIKSAAN_KESEHATAN, null, values);
		return todo_id;
	}
	public long createPemeriksaanKesehatanWithID(elsPemeriksaanKesehatan mElsPemeriksaanKesehatan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

	    values.put(KEY_KODE_PEMERIKSAAN_KESEHATAN, mElsPemeriksaanKesehatan.getKode_pemeriksaan_kesehatan());
		values.put(KEY_NIT, mElsPemeriksaanKesehatan.getNit());
		values.put(KEY_DIAGNOSA, mElsPemeriksaanKesehatan.getDiagnosa());
		values.put(KEY_PERLAKUAN, mElsPemeriksaanKesehatan.getPerlakuan());
		values.put(KEY_ID_PETUGAS, mElsPemeriksaanKesehatan.getId_petugas());
		values.put(KEY_TANGGAL_PERIKSA,mElsPemeriksaanKesehatan.getTanggalPeriksa());

		long todo_id = db.insertOrThrow(TABLE_ELS_PEMERIKSAAN_KESEHATAN, null, values);
		return todo_id;
	}
	public long deletePemeriksaanKesehatan(elsPemeriksaanKesehatan mElsPemeriksaanKesehatan){
		SQLiteDatabase db = this.getWritableDatabase();
		long todo_id = db.delete(TABLE_ELS_PEMERIKSAAN_KESEHATAN, KEY_KODE_PEMERIKSAAN_KESEHATAN + " = " + String.valueOf(mElsPemeriksaanKesehatan.getKode_pemeriksaan_kesehatan()), null);
		return todo_id;
	}
	public long updatePemeriksaanKesehatan(
			elsPemeriksaanKesehatan mElsPemeriksaanKesehatan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_DIAGNOSA, mElsPemeriksaanKesehatan.getDiagnosa());
		values.put(KEY_PERLAKUAN, mElsPemeriksaanKesehatan.getPerlakuan());
		values.put(KEY_NIT, mElsPemeriksaanKesehatan.getNit());
		values.put(KEY_ID_PETUGAS, mElsPemeriksaanKesehatan.getId_petugas());
		values.put(KEY_TANGGAL_PERIKSA,mElsPemeriksaanKesehatan.getTanggalPeriksa());

		long todo_id = db.update(
				TABLE_ELS_PEMERIKSAAN_KESEHATAN,
				values,
				KEY_KODE_PEMERIKSAAN_KESEHATAN
						+ " = "
						+ String.valueOf(mElsPemeriksaanKesehatan
								.getKode_pemeriksaan_kesehatan()), null);
		return todo_id;
	}

	public ArrayList<Search_RiwayatKesehatanModel> getPemeriksaanKesehatan(
			int mNit) {
		String query = "SELECT " + KEY_KODE_PEMERIKSAAN_KESEHATAN + " , "
				+ KEY_NIT + " , " + KEY_DIAGNOSA + " , " + KEY_PERLAKUAN
				+ " , " + KEY_TANGGAL_PERIKSA + " , " + KEY_ID_PETUGAS
				+ " FROM " + TABLE_ELS_PEMERIKSAAN_KESEHATAN + " WHERE nit = '"
				+ mNit + "' ; ";
		ArrayList<Search_RiwayatKesehatanModel> resultType = new ArrayList<Search_RiwayatKesehatanModel>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Search_RiwayatKesehatanModel value = new Search_RiwayatKesehatanModel();
				value.setKodePemeriksaanKesehatan(c.getString(c.getColumnIndex(KEY_KODE_PEMERIKSAAN_KESEHATAN)));
				value.setDiagnosa(c.getString(c.getColumnIndex(KEY_DIAGNOSA)));
				value.setPerlakuan(c.getString(c.getColumnIndex(KEY_PERLAKUAN)));
				value.setTanggal_periksa(c.getString(c.getColumnIndex(KEY_TANGGAL_PERIKSA)));
				value.setId_petugas(c.getString(c.getColumnIndex(KEY_ID_PETUGAS)));
				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 11 controller els kejadian ib
	public long createKejadianIBWithoutID(elsKejadianIb mElsKejadianIb) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_KODE_STRAW, mElsKejadianIb.getKode_straw());
		values.put(KEY_TANGGAL_IB, mElsKejadianIb.getTanggal_ib());
		values.put(KEY_NIT, mElsKejadianIb.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianIb.getId_petugas());

		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_IB, null, values);
		return todo_id;
	}
	public long createKejadianIBWithID(elsKejadianIb mElsKejadianIb) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

	    values.put(KEY_KODE_KEJADIAN_IB, mElsKejadianIb.getKode_kejadian_ib());
		values.put(KEY_KODE_STRAW, mElsKejadianIb.getKode_straw());
		values.put(KEY_NIT, mElsKejadianIb.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianIb.getId_petugas());
		values.put(KEY_TANGGAL_IB, mElsKejadianIb.getTanggal_ib());

		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_IB, null, values);
		return todo_id;
	}
	public long deleteKejadianIB(elsKejadianIb mElsKejadianIb) {
		SQLiteDatabase db = this.getWritableDatabase();
		long todo_id = db.delete(TABLE_ELS_KEJADIAN_IB, KEY_KODE_KEJADIAN_IB + " = "+ String.valueOf(mElsKejadianIb.getKode_kejadian_ib()), null);
		return todo_id;
	}
	public long updateKejadianIB(elsKejadianIb mElsKejadianIb) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_KODE_STRAW, mElsKejadianIb.getKode_straw());
		values.put(KEY_TANGGAL_IB, mElsKejadianIb.getTanggal_ib());
		values.put(KEY_NIT, mElsKejadianIb.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianIb.getId_petugas());

		long todo_id = db.update(
				TABLE_ELS_KEJADIAN_IB,
				values,
				KEY_KODE_KEJADIAN_IB + " = "
						+ String.valueOf(mElsKejadianIb.getKode_kejadian_ib()),
				null);
		return todo_id;
	}

	public ArrayList<Search_KejadianIBModel> getKejadianIB(int mNit) {
		String query = "SELECT " 
				+ KEY_KODE_KEJADIAN_IB + " , "
				+ KEY_KODE_STRAW + " , " 
				+ KEY_TANGGAL_IB
				+ " FROM " + TABLE_ELS_KEJADIAN_IB + " WHERE nit = '" + mNit
				+ "' ; ";
		ArrayList<Search_KejadianIBModel> resultType = new ArrayList<Search_KejadianIBModel>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(query, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Search_KejadianIBModel value = new Search_KejadianIBModel();
				value.setKode_kejadian_ib(c.getInt(c.getColumnIndex(KEY_KODE_KEJADIAN_IB)));
				value.setKodeStraw(c.getString(c.getColumnIndex(KEY_KODE_STRAW)));
				value.setTanggalIB(c.getString(c.getColumnIndex(KEY_TANGGAL_IB)));

				resultType.add(value);
			} while (c.moveToNext());
		}
		return resultType;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 12 controller els perubahan kepemilikan
	public long createPerubahanKepemilikanWithoutID(
			elsPerubahanKepemilikan mElsPerubahanKepemilikan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_IDL_SEBELUM, mElsPerubahanKepemilikan.getIdl_sebelum());
		values.put(KEY_IDL_SESUDAH, mElsPerubahanKepemilikan.getIdl_sesudah());
		values.put(KEY_TANGGAL_KEJADIAN,mElsPerubahanKepemilikan.getTanggal_kejadian());
		values.put(KEY_NIT, mElsPerubahanKepemilikan.getNit());

		long todo_id = db.insertOrThrow(TABLE_ELS_PERUBAHAN_KEPEMILIKAN, null, values);
		return todo_id;
	}
	public long createPerubahanKepemilikanWithID(elsPerubahanKepemilikan mElsPerubahanKepemilikan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

	    values.put(KEY_KODE_RIWAYAT_KEPEMILIKAN, mElsPerubahanKepemilikan.getKode_riwayat_kepemilikan());	
		values.put(KEY_IDL_SEBELUM, mElsPerubahanKepemilikan.getIdl_sebelum());
		values.put(KEY_IDL_SESUDAH, mElsPerubahanKepemilikan.getIdl_sesudah());
		values.put(KEY_TANGGAL_KEJADIAN,mElsPerubahanKepemilikan.getTanggal_kejadian());
		values.put(KEY_NIT, mElsPerubahanKepemilikan.getNit());

		long todo_id = db.insertOrThrow(TABLE_ELS_PERUBAHAN_KEPEMILIKAN, null, values);
		return todo_id;
	}
	public long deletePerubahanKepemilikan(elsPerubahanKepemilikan mElsPerubahanKepemilikan) {
		SQLiteDatabase db = this.getWritableDatabase();
		long todo_id = db.delete(TABLE_ELS_PERUBAHAN_KEPEMILIKAN, KEY_KODE_RIWAYAT_KEPEMILIKAN+" = "+mElsPerubahanKepemilikan.getKode_riwayat_kepemilikan(), null);
		return todo_id;
	}
	public long updatePerubahanKepemilikan(elsPerubahanKepemilikan mElsPerubahanKepemilikan) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_IDL_SEBELUM, mElsPerubahanKepemilikan.getIdl_sebelum());
		values.put(KEY_IDL_SESUDAH, mElsPerubahanKepemilikan.getIdl_sesudah());
		values.put(KEY_TANGGAL_KEJADIAN,
				mElsPerubahanKepemilikan.getTanggal_kejadian());
		values.put(KEY_NIT, mElsPerubahanKepemilikan.getNit());

		long todo_id = db.update(TABLE_ELS_PERUBAHAN_KEPEMILIKAN, values, KEY_KODE_RIWAYAT_KEPEMILIKAN+" = "+mElsPerubahanKepemilikan.getKode_riwayat_kepemilikan(), null);
		return todo_id;
	}

	public ArrayList<Search_PerubahanKepemilikanModel> getPerubahanKepemilikan(
			int mNit) {
		String selectElsKepemilikan = " SELECT epk.kode_riwayat_kepemilikan, "
				+ " epk.kode_riwayat_kepemilikan, "
				+ " epk.idl_sebelum, "
				+ " epk.idl_sesudah, "
				+ " epk.tanggal_kejadian, "
				+ " epk.nit, "
				+ " el.nama_kontak AS nama_kabupaten_kota_sebelumnya, "//ekk.nama_kabupaten_kota
				+ " el2.nama_kontak  AS nama_kabupaten_kota_sesudahnya  " //ekk2.nama_kabupaten_kota
				+ " FROM "
				+ " els_perubahan_kepemilikan epk "
				+ " LEFT JOIN els_lokasi el ON el.idl = epk.idl_sebelum "
				+ " LEFT JOIN els_lokasi el2 ON el2.idl = epk.idl_sesudah "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_kabupaten_kota ekk2 ON ekk2.id_kabupaten_kota = el2.id_kabupaten_kota "
				+ " WHERE epk.nit = '" + mNit + "';";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor mCursor = db.rawQuery(selectElsKepemilikan,
				null);

		ArrayList<Search_PerubahanKepemilikanModel> mPerubahanKepemilikanList = new ArrayList<Search_PerubahanKepemilikanModel>();
		if (mCursor.moveToFirst()) {
			do {
				Search_PerubahanKepemilikanModel mSearch_PerubahanKepemilikanModel = new Search_PerubahanKepemilikanModel();
				mSearch_PerubahanKepemilikanModel.setKode_riwayat_kepemilikan(mCursor.getInt(mCursor.getColumnIndex(KEY_KODE_RIWAYAT_KEPEMILIKAN)));
				mSearch_PerubahanKepemilikanModel.setKotaSebelumnya(mCursor.getString(mCursor.getColumnIndex("nama_kabupaten_kota_sebelumnya")));
				mSearch_PerubahanKepemilikanModel.setKotaSesudahnya(mCursor.getString(mCursor.getColumnIndex("nama_kabupaten_kota_sesudahnya")));
				mSearch_PerubahanKepemilikanModel.setTanggalKejadian(mCursor.getString(mCursor.getColumnIndex("tanggal_kejadian")));
				mPerubahanKepemilikanList.add(mSearch_PerubahanKepemilikanModel);
			} while (mCursor.moveToNext());
		}
		return mPerubahanKepemilikanList;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 13 controller els kejadian kematian
	public long createKejadianKematian(elsKejadianKematian mElsKejadianKematian) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TANGGAL_KEMATIAN,
				mElsKejadianKematian.getTanggal_kematian());
		values.put(KEY_SEBAB_KEMATIAN, mElsKejadianKematian.getSebab_kematian());
		values.put(KEY_IDL_KEMATIAN, mElsKejadianKematian.getIdl_kematian());
		values.put(KEY_NIT, mElsKejadianKematian.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianKematian.getId_petugas());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_KEMATIAN, null, values);

		return todo_id;
	}
	public long deleteKejadianKematian(elsKejadianKematian mElsKejadianKematian) {
		SQLiteDatabase db = this.getWritableDatabase();
		long todo_id = db.delete(TABLE_ELS_KEJADIAN_KEMATIAN, KEY_NIT+" = "+mElsKejadianKematian.getNit(), null);
		return todo_id;
	}

	public long updateKejadianKematian(elsKejadianKematian mElsKejadianKematian) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TANGGAL_KEMATIAN,mElsKejadianKematian.getTanggal_kematian());
		values.put(KEY_SEBAB_KEMATIAN, mElsKejadianKematian.getSebab_kematian());
		values.put(KEY_IDL_KEMATIAN, mElsKejadianKematian.getIdl_kematian());
		//values.put(KEY_NIT, mElsKejadianKematian.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianKematian.getId_petugas());

		// insert row
		long todo_id = db.update(TABLE_ELS_KEJADIAN_KEMATIAN, values, KEY_NIT+" = "+mElsKejadianKematian.getNit(), null);
		return todo_id;
	}
	
	public ArrayList<Search_KejadianKematianModel> getKejadianKematian(int mNit) {

		String selectElsKejadianKematian = " SELECT  ekk.tanggal_kematian, "
				+ " msk.value as sebab_kematian , " //ekk.sebab_kematian
				+ " el.nama_kontak AS "+ KEY_LOKASI +" , "//ekkota.nama_kabupaten_kota
				+ " ekk.tanggal_kematian AS " + KEY_TANGGAL_KEMATIAN + " , "
				+ " els_petugas.nama AS " +  KEY_ID_PETUGAS
				+ " FROM els_kejadian_kematian ekk "
				+ " LEFT JOIN els_lokasi el ON el.idl = ekk.idl_kematian "
				+ " LEFT JOIN els_kabupaten_kota ekkota ON ekkota.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_sebab_kematian msk ON ekk.sebab_kematian = msk.id "
				+ " LEFT JOIN els_petugas ON  els_petugas.id_petugas = ekk.id_petugas "
				+ " WHERE nit = '" + mNit + "';";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursorKejadianKematian = db.rawQuery(selectElsKejadianKematian,
				null);
		ArrayList<Search_KejadianKematianModel> mSearch_KejadianKematianList = new ArrayList<Search_KejadianKematianModel>();
		if (cursorKejadianKematian.moveToFirst()) {
			do {
				Search_KejadianKematianModel mSearch_KejadianKematianModel = new Search_KejadianKematianModel();
				mSearch_KejadianKematianModel
						.setSebabKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_SEBAB_KEMATIAN)));
				mSearch_KejadianKematianModel
						.setTanggalKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_TANGGAL_KEMATIAN)));

				mSearch_KejadianKematianModel
						.setLokasiKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_LOKASI)));
				mSearch_KejadianKematianModel
				.setIdPetugas(cursorKejadianKematian
						.getString(cursorKejadianKematian
								.getColumnIndex(KEY_ID_PETUGAS)));
				mSearch_KejadianKematianList.add(mSearch_KejadianKematianModel);
			} while (cursorKejadianKematian.moveToNext());
		}
		return mSearch_KejadianKematianList;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 14 controller sebab kematian
	public long createMasterSebbaKematian(String value) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_VALUE, value);

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_SEBAB_KEMATIAN, null, values);
		return todo_id;
	}

	public long createMasterSebbaKematianWithID(masterSebabKematian mMasterSebabKematian) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterSebabKematian.getId());
		values.put(KEY_VALUE, mMasterSebabKematian.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_SEBAB_KEMATIAN, null, values);
		return todo_id;
	}

	public List<String> getAllSebabKematian() {
		List<String> todos = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + TABLE_MASTER_SEBAB_KEMATIAN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				String value = "";
				value = c.getString(c.getColumnIndex(KEY_VALUE));
				// Todo td = new Todo();
				// td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				// td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
				// td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// adding to todo list
				todos.add(value);
			} while (c.moveToNext());
		}

		return todos;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 1 controller laporan riwayat kesehatan
	public ArrayList<LaporanPenyakitModel> getLaporanPenyakit(int idKota,
			int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanPenyakitModel> penyakitList = new ArrayList<LaporanPenyakitModel>();
		String selectQuery = // "Select * from " + TABLE_ELS_SAPI_BETINA;
		" SELECT esb.nit as " + KEY_NIT + " , "
				+ " ekk.nama_kabupaten_kota as " + KEY_LOKASI + " ,  "
				+ " esb.tanggal_lahir as " + KEY_TANGGAL_LAHIR + " ,"
				+ " mbs.value as " + KEY_BANGSA + " , " + " esb.nit_induk, "
				+ " esb.bentuk_wajah, " + " esb.warna, "
				+ " esb.status_punuk as " + KEY_STATUS_PUNUK + " , "
				+ " esb.status_aksesoris_kaki as " + KEY_STATUS_AKSESORIS_KAKI
				+ " , " + " esb.status_kepemilikan FROM "
				+ TABLE_ELS_SAPI_BETINA + " as esb " + " LEFT JOIN "
				+ TABLE_ELS_LOKASI + " as el ON esb.idl = el.idl "
				+ " LEFT JOIN " + TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN " + TABLE_ELS_PEMERIKSAAN_KESEHATAN
				+ " as epk ON epk.nit = esb.nit " + " LEFT JOIN "
				+ TABLE_MASTER_BANGSA_SAPI + " as mbs ON mbs.id = esb.bangsa "
				+ " WHERE 1 = 1 ";

		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND ekk.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}
		selectQuery += "Group By esb.nit";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPenyakitModel value = new LaporanPenyakitModel();

				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));

				String tanggal = c.getString(c
						.getColumnIndex(KEY_TANGGAL_LAHIR));
				value.setTanggal_lahir(tanggal.contains("0000") ? "-" : tanggal);
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNit_induk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentuk_wajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatus_punuk((statusPunuk == 1 ? "Ya" : "Tidak"));

				int statusAksesoris = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatus_aksesoris_kaki((statusAksesoris == 1 ? "Ya"
						: "Tidak"));

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatus_kepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");

				penyakitList.add(value);
			} while (c.moveToNext());
		}
		return penyakitList;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 2 controller laporan penyakit sapi
	public ArrayList<LaporanPenyakitModel> getLaporanPenyakitKarenaSakit(
			int idKota, int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanPenyakitModel> penyakitList = new ArrayList<LaporanPenyakitModel>();
		String selectQuery = " SELECT esb.nit as " + KEY_NIT + " , "
				+ " ekk.nama_kabupaten_kota as " + KEY_LOKASI + " , "
				+ " esb.tanggal_lahir as " + KEY_TANGGAL_LAHIR + " ,"
				+ " mbs.value as " + KEY_BANGSA + " , " + " esb.nit_induk, "
				+ " esb.bentuk_wajah, " + " esb.warna, "
				+ " esb.status_punuk as " + KEY_STATUS_PUNUK + " , "
				+ " esb.status_aksesoris_kaki as " + KEY_STATUS_AKSESORIS_KAKI
				+ " , " + " esb.status_kepemilikan " + " FROM "
				+ TABLE_ELS_SAPI_BETINA + " as esb " + " LEFT JOIN "
				+ TABLE_ELS_LOKASI + " as el ON esb.idl = el.idl "
				+ " LEFT JOIN " + TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN " + TABLE_MASTER_BANGSA_SAPI
				+ " as mbs ON mbs.id = esb.bangsa " 
				+ " WHERE ("
				+ " 	SELECT count(*) FROM " + TABLE_ELS_KEJADIAN_KEMATIAN
				+ " as ekk"
				+ "		WHERE ekk.nit = esb.nit AND ekk.sebab_kematian = 2) > 0 ";

		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND ekk.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		selectQuery += "Group By esb.nit";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPenyakitModel value = new LaporanPenyakitModel();

				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));

				String tanggal = c.getString(c
						.getColumnIndex(KEY_TANGGAL_LAHIR));
				value.setTanggal_lahir(tanggal.contains("0000") ? "-" : tanggal);
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNit_induk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentuk_wajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatus_punuk((statusPunuk == 1 ? "Ya" : "Tidak"));

				int statusAksesoris = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatus_aksesoris_kaki((statusAksesoris == 1 ? "Ya"
						: "Tidak"));

				value.setStatus_kepemilikan(c.getString(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN)));

				penyakitList.add(value);
			} while (c.moveToNext());
		}
		return penyakitList;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 3 controller laporan lokasi sapi
	public ArrayList<LaporanLokasiSapiModel> getLaporanLokasi(int idKota,
			int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanLokasiSapiModel> listLaporanLokasi = new ArrayList<LaporanLokasiSapiModel>();

		String selectQuery = " SELECT el.idl as "
				+ KEY_IDL
				+ " , "
				+ " el.nama_kontak as "
				+ KEY_NAMA_KONTAK
				+ " , "
				+ " ekk.nama_kabupaten_kota as "
				+ KEY_NAMA_KABUPATEN_KOTA
				+ " , "
				+ " el.address as "
				+ KEY_ADDRESS
				+ " , "
				+ " el.no_telepon, "
				+ " mtl.value as "
				+ KEY_TYPE
				+ " , "
				+ " ep.nama as "
				+ KEY_NAMA
				+ " FROM els_lokasi as el "
				+ " LEFT JOIN els_kabupaten_kota as ekk ON   ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_petugas as ep ON   ep.id_petugas = el.id_petugas "
				+ " LEFT JOIN master_type_lokasi as mtl ON el.type = mtl.id "
				+ " WHERE 1 = 1 ";

		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND ekk.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanLokasiSapiModel value = new LaporanLokasiSapiModel();
				value.setIdLokasi(c.getString(c.getColumnIndex(KEY_IDL)));
				value.setNamaKontak(c.getString(c
						.getColumnIndex(KEY_NAMA_KONTAK)));
				value.setKabupatenKota(c.getString(c
						.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA)));
				value.setAlamat(c.getString(c.getColumnIndex(KEY_ADDRESS)));
				value.setNoTelepon(c.getString(c.getColumnIndex("no_telepon")));
				value.setType(c.getString(c.getColumnIndex(KEY_TYPE)));
				value.setPetugas(c.getString(c.getColumnIndex(KEY_NAMA)));

				listLaporanLokasi.add(value);
			} while (c.moveToNext());
		}
		return listLaporanLokasi;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 4 controller laporan populasi / area sapi
	public ArrayList<LaporanPopulasiArea_LokasiModel> getLaporanPopulasiArea(
			int idKota, int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanPopulasiArea_LokasiModel> listPopulasiArea = new ArrayList<LaporanPopulasiArea_LokasiModel>();

		String selectQuery = " SELECT "
				+ " esb.nit, "
				+ " ekk.nama_kabupaten_kota as lokasi , "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, " //esb.bangsa
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk, "
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan"
				+ " FROM els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id"
				+ " WHERE 1 = 1 ";
		
		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND ekk.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPopulasiArea_LokasiModel value = new LaporanPopulasiArea_LokasiModel();
				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				value.setTanggalLahir(tanggal);
				
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNitInduk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatusPunuk(statusPunuk == 1 ? "Ya" : "Tidak");

				int statusAksesorisKaki = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatusAksesorisKaki(statusAksesorisKaki == 1 ? "Ya"
						: "Tidak");

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatusKepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");

				listPopulasiArea.add(value);
			} while (c.moveToNext());
		}
		return listPopulasiArea;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 5 controller laporan populasi lokasi sapi
	public ArrayList<LaporanPopulasiArea_LokasiModel> getLaporanPopulasiLokasi(
			int idKota, int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanPopulasiArea_LokasiModel> listPopulasiLokasi = new ArrayList<LaporanPopulasiArea_LokasiModel>();

		String selectQuery = "SELECT"
				+ " esb.nit, "
				+ " ekk.nama_kabupaten_kota as lokasi , "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, " //esb.bangsa"
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk, "
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan "
				+ " FROM els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id ";
		
		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND ekk.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND el.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND ekk.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPopulasiArea_LokasiModel value = new LaporanPopulasiArea_LokasiModel();
				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				value.setTanggalLahir(tanggal);
				
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNitInduk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatusPunuk(statusPunuk == 1 ? "Ya" : "Tidak");

				int statusAksesorisKaki = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatusAksesorisKaki(statusAksesorisKaki == 1 ? "Ya"
						: "Tidak");

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatusKepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");

				listPopulasiLokasi.add(value);
			} while (c.moveToNext());
		}
		return listPopulasiLokasi;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 6 controller laporan sapi produktif
	public ArrayList<LaporanSapiProduktifModel> getLaporanSapiProduktif(
			int idKota, int idProvinsi, int idLevelAdmin) {
		ArrayList<LaporanSapiProduktifModel> listSapiProd = new ArrayList<LaporanSapiProduktifModel>();

		String selectQuery = "SELECT * from( "
				+ " SELECT "
				+ " 	esb.nit, "
				+ " 	esb.idl, "
				+ " 	esb.tanggal_lahir, "
				+ " 	mbs.value as bangsa, " //esb.bangsa"
				+ " 	esb.nit_induk, "
				+ " 	esb.bentuk_wajah, "
				+ " 	esb.warna, "
				+ " 	esb.status_punuk, "
				+ " 	esb.status_aksesoris_kaki, "
				+ " 	esb.status_kepemilikan, "
				+ " 	(CURRENT_DATE-esb.tanggal_lahir) AS umur_hari, "
				+ " 	count(ekb.banyaknya_anak_betina)+count(ekb.banyaknya_anak_jantan) AS banyak_anak, "
				+ "		ekk.*"
				+ " FROM els_sapi_betina esb "
				+ " LEFT JOIN els_kejadian_beranak ekb ON ekb.nit = esb.nit "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id "
				+ " GROUP BY esb.nit " + ") AS esb " + " WHERE 1 =1 ";

		
		String whereQuery = "";
		if (idKota != 0) {
			whereQuery = " AND esb.id_kabupaten_kota = "
					+ String.valueOf(idKota);
			selectQuery += whereQuery;
		} else if (idProvinsi != 0) {
			whereQuery = " AND esb.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += whereQuery;
		}

		String addQuery = "";
		if (idLevelAdmin == 1) {
			addQuery = " AND esb.id_kabupaten_kota = " + String.valueOf(idKota);
			selectQuery += addQuery;
		} else if (idLevelAdmin == 2) {
			addQuery = " AND esb.id_provinsi = " + String.valueOf(idProvinsi);
			selectQuery += addQuery;
		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {

			do {
				LaporanSapiProduktifModel value = new LaporanSapiProduktifModel();
				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_NAMA_KABUPATEN_KOTA)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				value.setTanggal_lahir(tanggal);
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNit_induk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentuk_wajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));
				value.setUmur_hari(c.getString(c.getColumnIndex("umur_hari")));
				value.setBanyaknya_anak(c.getString(c.getColumnIndex("banyak_anak")));
				listSapiProd.add(value);
			} while (c.moveToNext());
		}
		return listSapiProd;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 7 controller laporan kepemilikan
	public ArrayList<LaporanKepemilikanModel> getLaporanKepemilikan() {
		ArrayList<LaporanKepemilikanModel> listKepemilikan = new ArrayList<LaporanKepemilikanModel>();

		String selectQuery = " SELECT "
				+ " esb.nit, "
				+ " el.nama_kontak, "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, "
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk, "
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan "
				+ " FROM "
				+ " els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_perubahan_kepemilikan epk ON epk.nit = esb.nit "
				+ " LEFT JOIN master_bangsa_sapi mbs ON mbs.id = esb.bangsa "
				+ " WHERE 1=1 GROUP BY esb.nit";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanKepemilikanModel value = new LaporanKepemilikanModel();
				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setIdLokasi(c.getString(c.getColumnIndex(KEY_NAMA_KONTAK)));
				
				String tanggal  =c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				value.setTanggalLahir(tanggal);
				
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNit_induk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentuk_wajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatusPunuk(statusPunuk == 1 ? "Ya" : "Tidak");

				int statusAksesorisKaki = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatusAksesorisKaki(statusAksesorisKaki == 1 ? "Ya"
						: "Tidak");

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatusKepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");
				listKepemilikan.add(value);
			} while (c.moveToNext());
		}
		return listKepemilikan;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 8 controller laporan penyembelihan
	public ArrayList<LaporanPenyembelihanModel> getLaporanPenyembelihan() {
		ArrayList<LaporanPenyembelihanModel> listPenyembelihan = new ArrayList<LaporanPenyembelihanModel>();

		String selectQuery = "SELECT esb.nit, "
				+ " esb.idl, "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, " //esb.bangsa
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk,"
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan,"
				+ " ekkk.tanggal_kematian"
				+ " FROM els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_kejadian_kematian ekkk ON   ekkk.nit = esb.nit "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id"
				+ " WHERE (  SELECT  count(*) FROM "
				+ " els_kejadian_kematian ekk " + " WHERE "
				+ " ekk.nit = esb.nit AND ekk.sebab_kematian = 1) > 0 ";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPenyembelihanModel laporanPenyembelihanModel = new LaporanPenyembelihanModel();
				laporanPenyembelihanModel.setNit(c.getString(c
						.getColumnIndex(KEY_NIT)));
				laporanPenyembelihanModel.setLokasi(c.getString(c
						.getColumnIndex(KEY_IDL)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				
				laporanPenyembelihanModel.setTanggalLahir(tanggal);
				laporanPenyembelihanModel.setTanggalKematian(c.getString(c
						.getColumnIndex(KEY_TANGGAL_KEMATIAN)));
				laporanPenyembelihanModel.setBangsa(c.getString(c
						.getColumnIndex(KEY_BANGSA)));
				laporanPenyembelihanModel.setNitInduk(c.getString(c
						.getColumnIndex(KEY_NIT_INDUK)));
				laporanPenyembelihanModel.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				laporanPenyembelihanModel.setWarna(c.getString(c
						.getColumnIndex(KEY_WARNA)));
				laporanPenyembelihanModel.setStatusPunuk(c.getString(c
						.getColumnIndex(KEY_STATUS_PUNUK)));
				laporanPenyembelihanModel.setStatusAksesorisKaki(c.getString(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI)));
				laporanPenyembelihanModel.setStatusKepemilikan(c.getString(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN)));

				listPenyembelihan.add(laporanPenyembelihanModel);
			} while (c.moveToNext());
		}
		return listPenyembelihan;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 9 controller laporan bangsa sapi
	public ArrayList<LaporanPopulasiArea_LokasiModel> getLaporanBangsa() {
		ArrayList<LaporanPopulasiArea_LokasiModel> listPopulasiArea = new ArrayList<LaporanPopulasiArea_LokasiModel>();

		String selectQuery = " SELECT "
				+ " esb.nit, "
				+ " ekk.nama_kabupaten_kota as lokasi , "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, " //esb.bangsa
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk, "
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan"
				+ " FROM "
				+ " els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id";

		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanPopulasiArea_LokasiModel value = new LaporanPopulasiArea_LokasiModel();
				value.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				value.setLokasi(c.getString(c.getColumnIndex(KEY_LOKASI)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				
				value.setTanggalLahir(tanggal);
				value.setBangsa(c.getString(c.getColumnIndex(KEY_BANGSA)));
				value.setNitInduk(c.getString(c.getColumnIndex(KEY_NIT_INDUK)));
				value.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				value.setWarna(c.getString(c.getColumnIndex(KEY_WARNA)));

				int statusPunuk = c.getInt(c.getColumnIndex(KEY_STATUS_PUNUK));
				value.setStatusPunuk(statusPunuk == 1 ? "Ya" : "Tidak");

				int statusAksesorisKaki = c.getInt(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				value.setStatusAksesorisKaki(statusAksesorisKaki == 1 ? "Ya"
						: "Tidak");

				int statusKepemilikan = c.getInt(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				value.setStatusKepemilikan(statusKepemilikan == 1 ? "Pemerintah"
						: "Pribadi");

				listPopulasiArea.add(value);
			} while (c.moveToNext());
		}
		return listPopulasiArea;
	}

	public ArrayList<LaporanLokasiSapiModel> getLaporanBangsaSapi() {
		ArrayList<LaporanLokasiSapiModel> listLokasiModel = new ArrayList<LaporanLokasiSapiModel>();

		String selectQuery = // "Select * from " + TABLE_ELS_SAPI_BETINA;
		"SELECT " + "esb.nit as " + KEY_NIT + " , "
				+ "ekk.nama_kabupaten_kota as " + KEY_LOKASI + " ,  "
				+ "esb.tanggal_lahir as " + KEY_TANGGAL_LAHIR + " ,"
				+ "mbs.value as " + KEY_BANGSA + " , " + "esb.nit_induk, "
				+ "esb.bentuk_wajah, " + "esb.warna, " + "esb.status_punuk as "
				+ KEY_STATUS_PUNUK + " , " + "esb.status_aksesoris_kaki as "
				+ KEY_STATUS_AKSESORIS_KAKI + " , " + "esb.status_kepemilikan "
				+ " FROM " + TABLE_ELS_SAPI_BETINA + " as esb " + " LEFT JOIN "
				+ TABLE_ELS_LOKASI + " as el ON esb.idl = el.idl "
				+ " LEFT JOIN " + TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN " + TABLE_ELS_PEMERIKSAAN_KESEHATAN
				+ " as epk ON epk.nit = esb.nit " + " LEFT JOIN "
				+ TABLE_MASTER_BANGSA_SAPI + " as mbs ON mbs.id = esb.bangsa "
				+ " WHERE 1 = 1 ";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
			} while (c.moveToNext());
		}
		return listLokasiModel;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 10 controller laporan AI sapi
	public ArrayList<LaporanAIModel> getLaporanAI() {
		ArrayList<LaporanAIModel> listLaporanAI = new ArrayList<LaporanAIModel>();

		String selectQuery = " SELECT eki.kode_kejadian_ib, "
				+ " eki.kode_straw, "
				+ " eki.tanggal_ib, "
				+ " eki.nit, "
				+ " eki.id_petugas, "
				+ " el.idl, "
				+ " el.nama_kontak, "
				+ " ekk.nama_kabupaten_kota as "
				+ KEY_LOKASI
				+ " FROM els_kejadian_ib eki "
				+ " LEFT JOIN els_sapi_betina esb ON   esb.nit = eki.nit "
				+ " LEFT JOIN els_lokasi el ON   el.idl = esb.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON   ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_type_lokasi mtl ON   el.type = mtl.id	";
		;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				LaporanAIModel laporanAIModel = new LaporanAIModel();
				laporanAIModel.setKodeStraw(c.getString(c
						.getColumnIndex(KEY_KODE_STRAW)));
				laporanAIModel.setNit(c.getString(c.getColumnIndex(KEY_NIT)));
				laporanAIModel.setIdLokasi(c.getString(c
						.getColumnIndex(KEY_IDL)));
				laporanAIModel.setNamaKontak(c.getString(c
						.getColumnIndex(KEY_NAMA_KONTAK)));
				laporanAIModel.setLokasi(c.getString(c
						.getColumnIndex(KEY_LOKASI)));
				listLaporanAI.add(laporanAIModel);
			} while (c.moveToNext());
		}
		return listLaporanAI;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// 11 controller laporan kelahiran pedet
	public ArrayList<LaporanKelahiranPedetModel> getLaporanKelahiranPedet() {
		ArrayList<LaporanKelahiranPedetModel> listKelahiranPedet = new ArrayList<LaporanKelahiranPedetModel>();

		String selectQuery = " SELECT esb.nit, "
				+ " esb.idl, "
				+ " esb.tanggal_lahir, "
				+ " mbs.value as bangsa, " //esb.bangsa
				+ " esb.nit_induk, "
				+ " esb.bentuk_wajah, "
				+ " esb.warna, "
				+ " esb.status_punuk, "
				+ " esb.status_aksesoris_kaki, "
				+ " esb.status_kepemilikan ,"
				+ " ekk.nama_kabupaten_kota as " + KEY_LOKASI
				+ " FROM els_sapi_betina esb "
				+ " LEFT JOIN els_lokasi el ON esb.idl = el.idl "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN master_bangsa_sapi mbs ON esb.bangsa = mbs.id";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
				LaporanKelahiranPedetModel laporanKelahiranPedetModel = new LaporanKelahiranPedetModel();
				laporanKelahiranPedetModel.setNit(c.getString(c
						.getColumnIndex(KEY_NIT)));
				laporanKelahiranPedetModel.setLokasi(c.getString(c
						.getColumnIndex(KEY_LOKASI)));
				
				String tanggal = c.getString(c.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggal.equalsIgnoreCase("0000-00-00")) tanggal = "1970-01-01";
				
				laporanKelahiranPedetModel.setTanggalLahir(tanggal);
				laporanKelahiranPedetModel.setBangsa(c.getString(c
						.getColumnIndex(KEY_BANGSA)));
				laporanKelahiranPedetModel.setNitInduk(c.getString(c
						.getColumnIndex(KEY_NIT_INDUK)));
				laporanKelahiranPedetModel.setBentukWajah(c.getString(c
						.getColumnIndex(KEY_BENTUK_WAJAH)));
				laporanKelahiranPedetModel.setWarna(c.getString(c
						.getColumnIndex(KEY_WARNA)));
				laporanKelahiranPedetModel.setStatusPunuk(c.getString(c
						.getColumnIndex(KEY_STATUS_PUNUK)));
				laporanKelahiranPedetModel.setStatusAksesorisKaki(c.getString(c
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI)));
				laporanKelahiranPedetModel.setStatusKepemilikan(c.getString(c
						.getColumnIndex(KEY_STATUS_KEPEMILIKAN)));
				listKelahiranPedet.add(laporanKelahiranPedetModel);
			} while (c.moveToNext());
		}
		return listKelahiranPedet;
	}

	// //////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	// insert all table
	public long createMasterPeranPetugas(masterPeranPetugas mMasterPeranPetugas) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterPeranPetugas.getId());
		values.put(KEY_VALUE, mMasterPeranPetugas.getValue());
		values.put(KEY_DESCRIPTION, mMasterPeranPetugas.getDescription());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_PERAN_PETUGAS, null, values);

		return todo_id;
	}

	public long createMasterBentukWajahSapi(
			masterBentukWajahSapi mMasterBentukWajahSapi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterBentukWajahSapi.getId());
		values.put(KEY_VALUE, mMasterBentukWajahSapi.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_BENTUK_WAJAH_SAPI, null, values);
		return todo_id;
	}

	public long createElsKejadianIB(elsKejadianIb mElsKejadianIb) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KODE_KEJADIAN_IB, mElsKejadianIb.getKode_kejadian_ib());
		values.put(KEY_KODE_STRAW, mElsKejadianIb.getKode_straw());
		values.put(KEY_TANGGAL_IB, mElsKejadianIb.getTanggal_ib());
		values.put(KEY_NIT, mElsKejadianIb.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianIb.getId_petugas());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_IB, null, values);

		return todo_id;
	}

	public long createMasterTypeLokasi(masterTypeLokasi mMasterTypeLokasi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterTypeLokasi.getId());
		values.put(KEY_VALUE, mMasterTypeLokasi.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_TYPE_LOKASI, null, values);

		return todo_id;
	}

	public long createMasterPenyakitSapi(masterPenyakitSapi mMasterPenyakitSapi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterPenyakitSapi.getId());
		values.put(KEY_VALUE, mMasterPenyakitSapi.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_PENYAKIT_SAPI, null, values);

		return todo_id;
	}

	public long createMasterLevelPetugas(masterLevelPetugas mMasterLevelPetugas) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterLevelPetugas.getId());
		values.put(KEY_VALUE, mMasterLevelPetugas.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_LEVEL_PETUGAS, null, values);

		return todo_id;
	}

	public long createMasterWarnaSapi(masterWarnaSapi mMasterWarnaSapi) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, mMasterWarnaSapi.getId());
		values.put(KEY_VALUE, mMasterWarnaSapi.getValue());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_MASTER_WARNA_SAPI, null, values);

		return todo_id;
	}

	public long createElsKejadianKematian(
			elsKejadianKematian mElsKejadianKematian) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TANGGAL_KEMATIAN,
				mElsKejadianKematian.getTanggal_kematian());
		values.put(KEY_SEBAB_KEMATIAN, mElsKejadianKematian.getSebab_kematian());
		values.put(KEY_IDL_KEMATIAN, mElsKejadianKematian.getIdl_kematian());
		values.put(KEY_NIT, mElsKejadianKematian.getNit());
		values.put(KEY_ID_PETUGAS, mElsKejadianKematian.getId_petugas());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_KEMATIAN, null, values);

		return todo_id;
	}

	public long createElsPetugas(elsPetugas mElsPetugas) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ID_PETUGAS, mElsPetugas.getId_petugas());
		values.put(KEY_NAMA, mElsPetugas.getNama());
		values.put(KEY_PERAN, mElsPetugas.getPeran());
		values.put(KEY_NO_TELEPON, mElsPetugas.getNo_telepon());
		values.put(KEY_USERNAME, mElsPetugas.getUsername());
		values.put(KEY_PASSWORD, mElsPetugas.getPassword());
		values.put(KEY_ID_LEVEL_ADMIN, mElsPetugas.getId_level_admin());
		values.put(KEY_ID_KABUPATEN_KOTA, mElsPetugas.getId_kabupaten_kota());
		values.put(KEY_ID_PROVINSI, mElsPetugas.getId_provinsi());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_PETUGAS, null, values);

		return todo_id;
	}

	public long createElsSapiBetina(elsSapiBetina mElsSapiBetina) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NIT, mElsSapiBetina.getNit());
		values.put(KEY_IDL, mElsSapiBetina.getIdl());
		values.put(KEY_TANGGAL_LAHIR, mElsSapiBetina.getTanggal_lahir());
		values.put(KEY_BANGSA, mElsSapiBetina.getBangsa());
		values.put(KEY_NIT_INDUK, mElsSapiBetina.getNit_induk());
		values.put(KEY_BENTUK_WAJAH, mElsSapiBetina.getBentuk_wajah());
		values.put(KEY_WARNA, mElsSapiBetina.getWarna());
		values.put(KEY_STATUS_PUNUK, mElsSapiBetina.getStatus_punuk());
		values.put(KEY_STATUS_AKSESORIS_KAKI,
				mElsSapiBetina.getStatus_aksesoris_kaki());
		values.put(KEY_STATUS_KEPEMILIKAN,
				mElsSapiBetina.getStatus_kepemilikan());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_SAPI_BETINA, null, values);

		return todo_id;
	}

	public long createElsPemerikasaanKesehatan(
			elsPemeriksaanKesehatan mElsPemeriksaanKesehatan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KODE_PEMERIKSAAN_KESEHATAN,
				mElsPemeriksaanKesehatan.getKode_pemeriksaan_kesehatan());
		values.put(KEY_DIAGNOSA, mElsPemeriksaanKesehatan.getDiagnosa());
		values.put(KEY_PERLAKUAN, mElsPemeriksaanKesehatan.getPerlakuan());
		values.put(KEY_NIT, mElsPemeriksaanKesehatan.getNit());
		values.put(KEY_ID_PETUGAS, mElsPemeriksaanKesehatan.getId_petugas());
		values.put(KEY_TANGGAL_PERIKSA,
				mElsPemeriksaanKesehatan.getTanggalPeriksa());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_PEMERIKSAAN_KESEHATAN, null, values);

		return todo_id;
	}

	public long createElsPerubahanKepemilikan(
			elsPerubahanKepemilikan mPerubahanKepemilikan) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KODE_RIWAYAT_KEPEMILIKAN,
				mPerubahanKepemilikan.getKode_riwayat_kepemilikan());
		values.put(KEY_IDL_SEBELUM, mPerubahanKepemilikan.getIdl_sebelum());
		values.put(KEY_IDL_SESUDAH, mPerubahanKepemilikan.getIdl_sesudah());
		values.put(KEY_TANGGAL_KEJADIAN,
				mPerubahanKepemilikan.getTanggal_kejadian());
		values.put(KEY_NIT, mPerubahanKepemilikan.getNit());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_PERUBAHAN_KEPEMILIKAN, null, values);

		return todo_id;
	}

	public long createElsKejadianBeranak(elsKejadianBeranak mElsKejadianBeranak) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_KODE_KEJADIAN_BERANAK,
				mElsKejadianBeranak.getKode_kejadian_beranak());
		values.put(KEY_NIT, mElsKejadianBeranak.getNit());
		values.put(KEY_TANGGAL_BERANAK,
				mElsKejadianBeranak.getTanggal_beranak());
		values.put(KEY_BANYAKNYA_ANAK_BETINA,
				mElsKejadianBeranak.getBanyaknya_anak_betina());
		values.put(KEY_BANYAKNYA_ANAK_JANTAN,
				mElsKejadianBeranak.getBanyaknya_anak_jantan());

		// insert row
		long todo_id = db.insertOrThrow(TABLE_ELS_KEJADIAN_BERANAK, null, values);

		return todo_id;
	}

	public SearchDataSapiResultModel cariDataSapi(String NIT) {
		SQLiteDatabase db = this.getWritableDatabase();
		SearchDataSapiResultModel mSearchDataSapiResultModel = new SearchDataSapiResultModel();
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////

		String selectElsSapiBetina = "SELECT " + "esb.nit as " + KEY_NIT
				+ " , " + " ekk.nama_kabupaten_kota as " + KEY_LOKASI + " ,  "
				+ " esb.tanggal_lahir as " + KEY_TANGGAL_LAHIR + " , "
				+ " mbs.value as " + KEY_BANGSA + " , " + " esb.nit_induk, "
				+ " esb.bentuk_wajah, " + " esb.warna, "
				+ " esb.status_punuk as " + KEY_STATUS_PUNUK + " , "
				+ " esb.status_aksesoris_kaki as " + KEY_STATUS_AKSESORIS_KAKI
				+ " , " + " esb.status_kepemilikan " + " FROM "
				+ TABLE_ELS_SAPI_BETINA + " as esb " + " LEFT JOIN "
				+ TABLE_ELS_LOKASI + " as el ON esb.idl = el.idl "
				+ " LEFT JOIN " + TABLE_ELS_KABUPATEN_KOTA
				+ " as ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN " + TABLE_MASTER_BANGSA_SAPI
				+ " as mbs ON mbs.id = esb.bangsa " + " WHERE esb.nit = '"
				+ NIT + "';";
		Cursor cursorSapiBetina = db.rawQuery(selectElsSapiBetina, null);
		ArrayList<Search_DataSapiModel> mSearch_DataSapiModelList = new ArrayList<Search_DataSapiModel>();
		// looping through all rows and adding to list
		if (cursorSapiBetina.moveToFirst()) {
			do {
				Search_DataSapiModel mSearch_DataSapiModel = new Search_DataSapiModel();
				mSearch_DataSapiModel.setNIT(cursorSapiBetina
						.getString(cursorSapiBetina.getColumnIndex(KEY_NIT)));
				mSearch_DataSapiModel
						.setLokasi(cursorSapiBetina.getString(cursorSapiBetina
								.getColumnIndex(KEY_LOKASI)));
				mSearch_DataSapiModel
						.setBangsa(cursorSapiBetina.getString(cursorSapiBetina
								.getColumnIndex(KEY_BANGSA)));
				
				String tanggalLahir = cursorSapiBetina.getString(cursorSapiBetina.getColumnIndex(KEY_TANGGAL_LAHIR));
				if (tanggalLahir.contains("0000-00-00")) tanggalLahir = "1970-01-01";
				
				mSearch_DataSapiModel.setTanggalLahir(tanggalLahir);
				mSearch_DataSapiModel.setNITInduk(cursorSapiBetina
						.getString(cursorSapiBetina
								.getColumnIndex(KEY_NIT_INDUK)));
				mSearch_DataSapiModel.setBentukWajah(cursorSapiBetina
						.getString(cursorSapiBetina
								.getColumnIndex(KEY_BENTUK_WAJAH)));
				mSearch_DataSapiModel.setWarna(cursorSapiBetina
						.getString(cursorSapiBetina.getColumnIndex(KEY_WARNA)));

				int statusPunuk = cursorSapiBetina.getInt(cursorSapiBetina
						.getColumnIndex(KEY_STATUS_PUNUK));
				mSearch_DataSapiModel.setStatusPunuk((statusPunuk == 1 ? "Ya"
						: "Tidak"));

				int statusAksesoris = cursorSapiBetina.getInt(cursorSapiBetina
						.getColumnIndex(KEY_STATUS_AKSESORIS_KAKI));
				mSearch_DataSapiModel
						.setStatusAksesorisKaki((statusAksesoris == 1 ? "Ya"
								: "Tidak"));

				int statusKepemilikan = cursorSapiBetina
						.getInt(cursorSapiBetina
								.getColumnIndex(KEY_STATUS_KEPEMILIKAN));
				mSearch_DataSapiModel
						.setStatusKepimilikan(statusKepemilikan == 1 ? "Pemerintah"
								: "Pribadi");
				mSearch_DataSapiModelList.add(mSearch_DataSapiModel);
			} while (cursorSapiBetina.moveToNext());
		}
		mSearchDataSapiResultModel.setmDataSapi(mSearch_DataSapiModelList);
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////
		String selectElsKejadianBeranak = " SELECT * "
				+ " FROM els_kejadian_beranak " + " WHERE nit = '" + NIT + "';";
		Cursor cursorKejadianBeranak = db.rawQuery(selectElsKejadianBeranak,
				null);
		ArrayList<Search_KejadianBeranakModel> mSearch_KejadianBeranakList = new ArrayList<Search_KejadianBeranakModel>();

		if (cursorKejadianBeranak.moveToFirst()) {
			// do {
			Search_KejadianBeranakModel mSearch_KejadianBeranakModel = new Search_KejadianBeranakModel();
			mSearch_KejadianBeranakModel.setNIT(cursorKejadianBeranak
					.getString(cursorKejadianBeranak.getColumnIndex(KEY_NIT)));
			
			String tanggalBeranak = cursorKejadianBeranak.getString(cursorKejadianBeranak.getColumnIndex(KEY_TANGGAL_BERANAK));
			if (tanggalBeranak.contains("0000-00-00")) tanggalBeranak = "1970-01-01";
			mSearch_KejadianBeranakModel.setTanggalBeranak(tanggalBeranak);
			
			mSearch_KejadianBeranakModel
					.setBanyaknyaAnakBetina(cursorKejadianBeranak
							.getString(cursorKejadianBeranak
									.getColumnIndex(KEY_BANYAKNYA_ANAK_BETINA)));
			mSearch_KejadianBeranakModel
					.setBanyaknyaAnakJantan(cursorKejadianBeranak
							.getString(cursorKejadianBeranak
									.getColumnIndex(KEY_BANYAKNYA_ANAK_JANTAN)));
			// if (mSearch_KejadianBeranakModel.getNIT().length()!=0)
			mSearch_KejadianBeranakList.add(mSearch_KejadianBeranakModel);
			// } while (cursorKejadianBeranak.moveToNext());
		}
		mSearchDataSapiResultModel
				.setmKejadianBeranak(mSearch_KejadianBeranakList);
		// db.execSQL(selectElsKejadianBeranak);
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////
		String selectElsPemeriksaanKesehatan = " SELECT * "
				+ " FROM els_pemeriksaan_kesehatan " + " WHERE nit = '" + NIT
				+ "';";
		Cursor cursorPemeriksaanKesehatan = db.rawQuery(selectElsPemeriksaanKesehatan, null);
		ArrayList<Search_RiwayatKesehatanModel> mSearch_RiwayatKesehatanList = new ArrayList<Search_RiwayatKesehatanModel>();
		if (cursorPemeriksaanKesehatan.moveToFirst()) {
			do {
				Search_RiwayatKesehatanModel mSearch_RiwayatKesehatanModel = new Search_RiwayatKesehatanModel();
				mSearch_RiwayatKesehatanModel
						.setKodePemeriksaanKesehatan(cursorPemeriksaanKesehatan.getString(cursorPemeriksaanKesehatan
								.getColumnIndex(KEY_KODE_PEMERIKSAAN_KESEHATAN)));
				mSearch_RiwayatKesehatanModel
						.setDiagnosa(cursorPemeriksaanKesehatan
								.getString(cursorPemeriksaanKesehatan
										.getColumnIndex(KEY_DIAGNOSA)));
				mSearch_RiwayatKesehatanModel
						.setPerlakuan(cursorPemeriksaanKesehatan
								.getString(cursorPemeriksaanKesehatan
										.getColumnIndex(KEY_PERLAKUAN)));
				mSearch_RiwayatKesehatanModel.setId_petugas(cursorPemeriksaanKesehatan
								.getString(cursorPemeriksaanKesehatan
										.getColumnIndex(KEY_ID_PETUGAS)));
				mSearch_RiwayatKesehatanList.add(mSearch_RiwayatKesehatanModel);
			} while (cursorPemeriksaanKesehatan.moveToNext());
		}
		mSearchDataSapiResultModel
				.setmRiwayatKesehatan(mSearch_RiwayatKesehatanList);
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////
		String selectElsKejadianIB = " SELECT * " + " FROM els_kejadian_ib "
				+ " WHERE nit = '" + NIT + "';";
		Cursor cursorKejadianIB = db.rawQuery(selectElsKejadianIB, null);
		ArrayList<Search_KejadianIBModel> mKejadianIBList = new ArrayList<Search_KejadianIBModel>();
		if (cursorKejadianIB.moveToFirst()) {
			do {
				Search_KejadianIBModel mKejadianIBModel = new Search_KejadianIBModel();
				mKejadianIBModel.setKodeStraw(cursorKejadianIB
						.getString(cursorKejadianIB
								.getColumnIndex(KEY_KODE_STRAW)));
				mKejadianIBModel.setTanggalIB(cursorKejadianIB
						.getString(cursorKejadianIB
								.getColumnIndex(KEY_TANGGAL_IB)));

				mKejadianIBList.add(mKejadianIBModel);
			} while (cursorKejadianIB.moveToNext());
		}
		mSearchDataSapiResultModel.setmKejadianIB(mKejadianIBList);

		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////
		String selectElsKepemilikan = " SELECT epk.kode_riwayat_kepemilikan, "
				+ " epk.idl_sebelum, "
				+ " epk.idl_sesudah, "
				+ " epk.tanggal_kejadian, "
				+ " epk.nit, "
				+ " el.nama_kontak AS nama_kabupaten_kota_sebelumnya, " //ekk.nama_kabupaten_kota
				+ " el2.nama_kontak AS nama_kabupaten_kota_sesudahnya  " //ekk2.nama_kabupaten_kota
				+ " FROM "
				+ " els_perubahan_kepemilikan epk "
				+ " LEFT JOIN els_lokasi el ON el.idl = epk.idl_sebelum "
				+ " LEFT JOIN els_lokasi el2 ON el2.idl = epk.idl_sesudah "
				+ " LEFT JOIN els_kabupaten_kota ekk ON ekk.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " LEFT JOIN els_kabupaten_kota ekk2 ON ekk2.id_kabupaten_kota = el2.id_kabupaten_kota "
				+ " WHERE epk.nit = '" + NIT + "';";
		Cursor cursorPerubahanKepemilikan = db.rawQuery(selectElsKepemilikan,
				null);
		ArrayList<Search_PerubahanKepemilikanModel> mPerubahanKepemilikanList = new ArrayList<Search_PerubahanKepemilikanModel>();
		if (cursorPerubahanKepemilikan.moveToFirst()) {
			do {
				Search_PerubahanKepemilikanModel mSearch_PerubahanKepemilikanModel = new Search_PerubahanKepemilikanModel();
				mSearch_PerubahanKepemilikanModel
						.setKotaSebelumnya(cursorPerubahanKepemilikan.getString(cursorPerubahanKepemilikan
								.getColumnIndex("nama_kabupaten_kota_sebelumnya")));
				mSearch_PerubahanKepemilikanModel
						.setKotaSesudahnya(cursorPerubahanKepemilikan.getString(cursorPerubahanKepemilikan
								.getColumnIndex("nama_kabupaten_kota_sesudahnya")));
				mSearch_PerubahanKepemilikanModel
						.setTanggalKejadian(cursorPerubahanKepemilikan
								.getString(cursorPerubahanKepemilikan
										.getColumnIndex("tanggal_kejadian")));
				mPerubahanKepemilikanList
						.add(mSearch_PerubahanKepemilikanModel);
			} while (cursorPerubahanKepemilikan.moveToNext());
		}
		mSearchDataSapiResultModel
				.setmPerubahanKepemilikan(mPerubahanKepemilikanList);
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////
		String selectElsKejadianKematian = " SELECT  ekk.tanggal_kematian, "
				+ " ekk.sebab_kematian, "
				+ " ekkota.nama_kabupaten_kota AS "
				+ KEY_LOKASI
				+ " FROM els_kejadian_kematian ekk"
				+ " LEFT JOIN els_lokasi el ON el.idl = ekk.idl_kematian"
				+ " LEFT JOIN els_kabupaten_kota ekkota ON ekkota.id_kabupaten_kota = el.id_kabupaten_kota "
				+ " WHERE nit = '" + NIT + "';";
		Cursor cursorKejadianKematian = db.rawQuery(selectElsKejadianKematian,
				null);
		ArrayList<Search_KejadianKematianModel> mSearch_KejadianKematianList = new ArrayList<Search_KejadianKematianModel>();
		if (cursorKejadianKematian.moveToFirst()) {
			do {
				Search_KejadianKematianModel mSearch_KejadianKematianModel = new Search_KejadianKematianModel();
				mSearch_KejadianKematianModel
						.setSebabKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_SEBAB_KEMATIAN)));
				mSearch_KejadianKematianModel
						.setTanggalKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_TANGGAL_KEMATIAN)));

				mSearch_KejadianKematianModel
						.setLokasiKematian(cursorKejadianKematian
								.getString(cursorKejadianKematian
										.getColumnIndex(KEY_LOKASI)));
				mSearch_KejadianKematianList.add(mSearch_KejadianKematianModel);
			} while (cursorKejadianKematian.moveToNext());
		}
		mSearchDataSapiResultModel
				.setmKejadianKematian(mSearch_KejadianKematianList);
		// /////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////

		return mSearchDataSapiResultModel;
	}

	public void DummyData() {
		String Sqltets[] = {
				" INSERT INTO els_kabupaten_kota VALUES(1,1,'Bogor Kota'); ",
				" INSERT INTO els_kabupaten_kota VALUES(2,1,'Sukabumi Kabupaten'); ",
				" INSERT INTO els_kabupaten_kota VALUES(3,1,'Bogor Kabupaten'); ",
				" INSERT INTO els_kabupaten_kota VALUES(4,3,'Solo'); ",
				" INSERT INTO els_kabupaten_kota VALUES(5,4,'Surabaya'); ",
				" INSERT INTO els_kabupaten_kota VALUES(6,3,'Semarang'); ",
				" INSERT INTO els_kabupaten_kota VALUES(7,5,'SPR 1111 BANYUASIN'); ",
				" INSERT INTO els_kejadian_beranak VALUES(2,123,'2013-08-08','2','2'); ",
				" INSERT INTO els_kejadian_ib VALUES(2,'123123','2013-08-08',123,1); ",
				" INSERT INTO els_kejadian_ib VALUES(3,'123123123','2013-08-15',1,1); ",
				" INSERT INTO els_kejadian_ib VALUES(4,'123','2014-04-01',789,0); ",
				" INSERT INTO els_kejadian_ib VALUES(5,'123','2014-04-01',1,0); ",
				" INSERT INTO els_kejadian_ib VALUES(6,'123','2014-04-01',999,0); ",
				" INSERT INTO els_kejadian_kematian VALUES('2013-08-28','2','3','123','4'); ",
				// " INSERT INTO els_kejadian_kematian VALUES('2013-08-30','2','5','1','4'); ",
				" INSERT INTO els_lokasi VALUES(1,'Udinaa',1,'jalan sukasari ','08568299000','3',1); ",
				" INSERT INTO els_lokasi VALUES(5,'Cepot',1,'yeah','090909999','2',2); ",
				" INSERT INTO els_lokasi VALUES(3,'rizkina',6,'Jalan Malabar','3432423423','3',1); ",
				" INSERT INTO els_lokasi VALUES(4,'budi',4,'tajur','090909999','4',4); ",
				" INSERT INTO els_lokasi VALUES(6,'Joni',4,'Solo','','4',5); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(1,'sakit flue','suntik virus',123,4,NULL); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(2,'wow','biasa aja',1,4,'2014-03-12'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(3,'Diagnosa sapi','Perlakkuan sapi',1,4,'2013-11-28'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(4,'test','estset',1,4,'2014-03-05'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(5,'Flu','Flu',111,4,'2014-03-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(6,'Flulagi','Flulagi',111,4,'2014-03-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(7,'Flu','Flu',789,4,'2014-04-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(8,'Lapar','Makan',789,23,'2014-04-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(9,'Flu','Flue',789,24,'2014-04-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(10,'Lapar','Makan',789,25,'2014-04-01'); ",
				" INSERT INTO els_pemeriksaan_kesehatan VALUES(11,'tes','testset',1,4,'2014-04-28'); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(8,5,3,'2013-08-28',123); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(6,1,4,'2013-08-29',1); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(9,3,5,'2013-08-29',123); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(10,1,3,'2014-02-16',789); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(11,1,5,'2014-04-01',2147483647); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(12,3,4,'2014-04-01',789); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(13,4,3,'2014-04-01',789); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(14,3,4,'2014-04-01',789); ",
				" INSERT INTO els_perubahan_kepemilikan VALUES(15,4,3,'2014-04-01',1); ",
				" INSERT INTO els_petugas VALUES(1,'PPT PROVINSI JAWA BARAT','ppt','08568299000','ppt','dea3f521b19961787492d7afe294527a',2,0,1); ",
				" INSERT INTO els_petugas VALUES(2,'AHO Kota SOLO','aho','0888323123','aho','3f5264317940072e5ae27ba5416c5ce2',1,4,3); ",
				" INSERT INTO els_petugas VALUES(3,'INS KOTA BOGOR','ins','05555555555','ins','02577703ad2ac4d634660dbd7da8a32c',1,1,1); ",
				" INSERT INTO els_petugas VALUES(4,'admin','admin','00000000000','admin','2740231e18bd5c7bebb98628ab83c79f',0,0,0); ",
				" INSERT INTO els_petugas VALUES(5,'PPT KOTA SOLO','ppt','','ppt1','bd4ec5eb3c8fae7480b7d1136dab20e7',1,4,3); ",
				" INSERT INTO els_petugas VALUES(6,'PPT KOTA SEMARANG','ppt','12312','ppt2','683f611a54fdf14ec738964946abdbf4',1,6,0); ",
				" INSERT INTO els_petugas VALUES(7,'Dinas Kota Solo','dkota','','kota','c710185092a7d331a8354db470041e8d',1,4,0); ",
				" INSERT INTO els_petugas VALUES(8,'Dinas Provinsi Jawa Barat','dprov','','provinsi','223b5872af461f39f572d1345d0d0cce',2,2,1); ",
				" INSERT INTO els_petugas VALUES(9,'Dinas Pusat Nasional','dnas','','pusat','ed529de3ea5c4a215cacf44a492fb007',3,6,0); ",
				" INSERT INTO els_petugas VALUES(10,'PPT PUSAT','ppt','','pptpusat','6bfe1fa9201fd306e78c8982d9a9b12a',3,0,0); ",
				" INSERT INTO els_petugas VALUES(11,'AHO JAWA BARAT','aho','','ahoprov','ab2815ff84d5b642f291b3fc7a06eb5b',2,0,1); ",
				" INSERT INTO els_petugas VALUES(12,'AHO PUSAT','aho','','ahopusat','07fba37502a728d36320a6f434b71816',3,0,0); ",
				" INSERT INTO els_petugas VALUES(13,'INS PROVINSI JAWA BARAT','ins','','insprov','5963d8df0cda1fc7f77417f29e0c6255',2,0,1); ",
				" INSERT INTO els_petugas VALUES(14,'INS PUSAT','ins','','inspusat','e7b415fb8865b073e4151697175e3387',3,0,0); ",
				" INSERT INTO els_petugas VALUES(15,'PPT BOGOR','ppt','','pptbogor','b807f832ae588330f3f55f012533a7f0',1,1,1); ",
				" INSERT INTO els_petugas VALUES(16,'PPT SUKABUMI','ppt','','pptsukabumi','6e89b6ddfeb22e3a8ce95555f6c59f1c',1,2,1); ",
				" INSERT INTO els_petugas VALUES(17,'Dahlan Efendi','ins','6285279649737','dahlan_efendi','1495ffd8f54cd5f0b06e598048b75474',1,7,5); ",
				" INSERT INTO els_petugas VALUES(18,'ppt coba','aho','','pptcoba','a78221b9d88c48ee5467133cc55d94a1',3,1,1); ",
				" INSERT INTO els_petugas VALUES(19,'pptjateng','ppt','','pptjateng','995d5a55d9e5f4d713be59a3f5a092de',2,0,3); ",
				" INSERT INTO els_petugas VALUES(20,'Budi','ppt','','budi','329fa1ea327187864b05b9f102b9a099',3,0,0); ",
				" INSERT INTO els_petugas VALUES(21,'Joni','ppt','','joni','5d084c84d4312ab3e045c571ea00c0cc',2,0,3); ",
				" INSERT INTO els_petugas VALUES(22,'riki','ppt','','riki','72ecaa7dce77ae37702de6aca8f925dc',1,4,3); ",
				" INSERT INTO els_petugas VALUES(23,'budiaho','aho','','budiaho','5b4fe180b153480e669b04386c436f06',3,0,0); ",
				" INSERT INTO els_petugas VALUES(24,'joniaho','aho','','joniaho','32a73b58115ee659595e419d9cacd94b',2,0,3); ",
				" INSERT INTO els_petugas VALUES(25,'rikiaho','aho','','rikiaho','ea4440814d048426997ad20b95cad72c',1,4,3); ",
				" INSERT INTO els_petugas VALUES(26,'budiinseminator','ins','','budiinseminator','13110e25b5d41058ca46a8cc71b3e5d0',3,0,0); ",
				" INSERT INTO els_petugas VALUES(27,'joniinseminator','ins','','joniinseminator','0918609aad8dc4a3ce7e8e61137c6ff5',2,0,3); ",
				" INSERT INTO els_petugas VALUES(28,'rikiinseminator','ins','','rikiinseminator','46e6d04e9f2f971e5b71c2d8b83448ff',1,4,3); ",
				" INSERT INTO els_provinsi VALUES(1,'Jawa Barat'); ",
				" INSERT INTO els_provinsi VALUES(3,'Jawa Tengah'); ",
				" INSERT INTO els_provinsi VALUES(4,'Jawa Timur'); ",
				" INSERT INTO els_provinsi VALUES(5,'Sumatera Selatan'); ",
				" INSERT INTO els_sapi_betina VALUES(1,4,'0000-00-00','1',999,'tipe wajah','Coklat',1,1,1); ",
				" INSERT INTO els_sapi_betina VALUES(123,1,'1970-09-01','1',1,'lonjong','putih',1,0,0); ",
				" INSERT INTO els_sapi_betina VALUES(999,4,'2012-09-12','1',123,'lonjong','biru',1,1,1); ",
				" INSERT INTO els_sapi_betina VALUES(2222,1,'2013-09-12','2',1,'nono','kuning',0,1,0); ",
				" INSERT INTO els_sapi_betina VALUES(789,4,'2014-02-16','1',123,'Bulat','Coklat',1,1,1); ",
				" INSERT INTO els_sapi_betina VALUES(111,1,'2014-03-01','1',123,'Bulat','coklat',1,1,1); ",
				" INSERT INTO els_sapi_betina VALUES(2147483647,5,'2014-04-01','1',123,'Bulat','coklat',1,1,1); ",
				" INSERT INTO els_sapi_betina VALUES(123321,5,'2014-04-01','1',123,'Bulat','Coklat',1,1,1); ",
				" INSERT INTO master_bangsa_sapi VALUES(1,'bangsa A'); ",
				" INSERT INTO master_bangsa_sapi VALUES(2,'bangsa B'); ",
				" INSERT INTO master_bangsa_sapi VALUES(3,'bangsa C'); ",
				" INSERT INTO master_bangsa_sapi VALUES(4,'bANgsa D'); ",
				" INSERT INTO master_bangsa_sapi VALUES(6,'yeah'); ",
				" INSERT INTO master_bentuk_wajah_sapi VALUES(1,'Bentuk 1'); ",
				" INSERT INTO master_bentuk_wajah_sapi VALUES(2,'Bentuk 2'); ",
				" INSERT INTO master_bentuk_wajah_sapi VALUES(3,'Bentuk 3'); ",
				" INSERT INTO master_bentuk_wajah_sapi VALUES(4,'Bentuk 4'); ",
				" INSERT INTO master_level_petugas VALUES(1,'Level Kota'); ",
				" INSERT INTO master_level_petugas VALUES(2,'Level Provinsi'); ",
				" INSERT INTO master_level_petugas VALUES(3,'Level Nasional'); ",
				" INSERT INTO master_peran_petugas VALUES(1,'ppt','PPT'); ",
				" INSERT INTO master_peran_petugas VALUES(2,'aho','Animal Health Officer'); ",
				" INSERT INTO master_peran_petugas VALUES(3,'ins','Inseminator'); ",
				" INSERT INTO master_peran_petugas VALUES(4,'admin','Administrator'); ",
				" INSERT INTO master_peran_petugas VALUES(5,'dkota','Dinas Kota/Kabupaten'); ",
				" INSERT INTO master_peran_petugas VALUES(6,'dprov','Dinas Provinsi'); ",
				" INSERT INTO master_peran_petugas VALUES(7,'dnas','Dinas Nasional/Pemerintah'); ",
				" INSERT INTO master_sebab_kematian VALUES(1,'disembelih'); ",
				" INSERT INTO master_sebab_kematian VALUES(2,'sakit'); ",
				" INSERT INTO master_type_lokasi VALUES(1,'BPTU'); ",
				" INSERT INTO master_type_lokasi VALUES(2,'BIB'); ",
				" INSERT INTO master_type_lokasi VALUES(3,'Aimal Quarantine'); ",
				" INSERT INTO master_type_lokasi VALUES(4,'Farmer'); ",
				" INSERT INTO master_type_lokasi VALUES(5,'Kelompok Peternak'); ",
				" INSERT INTO master_warna_sapi VALUES(1,'Warna AA'); ",
				" INSERT INTO master_warna_sapi VALUES(2,'Warna BB'); ",
				" INSERT INTO master_warna_sapi VALUES(3,'Warna CC'); ",
				" INSERT INTO master_warna_sapi VALUES(4,'Warna DD'); " };
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			for (String string : Sqltets) {
				db.execSQL(string);
			}
		} catch (SQLException e) {
			Log.d("debug", e.getMessage());
		}
	}
}
