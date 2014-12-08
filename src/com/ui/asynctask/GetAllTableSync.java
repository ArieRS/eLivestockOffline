package com.ui.asynctask;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.elivestock.SplashActivity;
import com.ui.model.database.getAllTable;

public class GetAllTableSync extends AsyncTask<String, Void, String> {

	DatabaseHelper db;
	Context mContext;
	ProgressDialog mDialog;
	SessionManager mSessionManager;
	//HomeActivity mActivity;
	
	public GetAllTableSync(Context context){
		mContext = context;
		db  = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		//mActivity = activity;
	}
	public String readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		String result = "";
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				result = General.convertInputStreamToString(inputStream);
				inputStream.close();
			} else {
				Log.d("readJSONFeed", "Failed to download file");
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return result;
		// return listData;
	}

	@Override
	protected String doInBackground(String... urls) {
//		JSONObject json = new JSONObject();
//		try {
//			json.put("user", mSessionManager.getUserame());
//			json.put("pass", mSessionManager.getPassword());
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return HttpRequestHelper.doPost(mContext.getResources().getString(R.string.url_get_table), json);
		String result = readJSONFeed(mContext.getResources().getString(R.string.url_get_table));
		if (result.isEmpty()==false){
			JSONObject jsonObjectResult;
			try {
				jsonObjectResult = new JSONObject(result);
				String stringStatus = jsonObjectResult.getString("status");
				
				if (stringStatus.equalsIgnoreCase("sukses"))
				{
					String stringData = jsonObjectResult.getString("data");
					
					JSONArray jsonArray = new JSONArray(stringData);
							
					for(int ii = 1; ii< jsonArray.length(); ii++)
					{
						String resultPerTable = jsonArray.getString(ii);
						JSONObject jsonPerTable = new JSONObject(resultPerTable);
						
						//untuk melakukan jaga2 kalau isi dari json kosong
						if (resultPerTable.contains("master_penyakit_sapi")==false){
							Gson mGson = new Gson();
							getAllTable contentTable = mGson.fromJson(jsonPerTable.toString(), getAllTable.class);
							
							if ((resultPerTable.contains("els_kabupaten_kota")) && (contentTable.getListElsKabupatenKota().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsKabupatenKota().size(); xx++){
									db.createKabupatenKotaWithID(contentTable.getListElsKabupatenKota().get(xx));
								}
							}
							else if (resultPerTable.contains("els_kejadian_beranak")&& (contentTable.getListElsKejadianBeranak().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsKejadianBeranak().size(); xx++){
									db.createKejadianBeranakWithID(contentTable.getListElsKejadianBeranak().get(xx));
								}
							}
							else if (resultPerTable.contains("els_kejadian_ib")&& (contentTable.getListElsKejadianIb().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsKejadianIb().size(); xx++){
									db.createKejadianIBWithID(contentTable.getListElsKejadianIb().get(xx));
								}
							}
							else if (resultPerTable.contains("els_kejadian_kematian")&& (contentTable.getListKejadianKematian().size() > 0)){
								for (int xx=0; xx<contentTable.getListKejadianKematian().size(); xx++){
									db.createKejadianKematian(contentTable.getListKejadianKematian().get(xx));
								}
							}
							else if (resultPerTable.contains("els_lokasi")&& (contentTable.getListElsLokasi().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsLokasi().size(); xx++){
									db.createLokasiWithID(contentTable.getListElsLokasi().get(xx));
								}
							}
							else if (resultPerTable.contains("els_pemeriksaan_kesehatan")&& (contentTable.getListElsPemeriksaanKesehatan().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsPemeriksaanKesehatan().size(); xx++){
									db.createPemeriksaanKesehatanWithID(contentTable.getListElsPemeriksaanKesehatan().get(xx));
								}
							}
							else if (resultPerTable.contains("els_perubahan_kepemilikan")&& (contentTable.getListPerubahanKepemilikan().size() > 0)){
								for (int xx=0; xx<contentTable.getListPerubahanKepemilikan().size(); xx++){
									db.createPerubahanKepemilikanWithID(contentTable.getListPerubahanKepemilikan().get(xx));
								}
							}
							else if (resultPerTable.contains("els_petugas")&& (contentTable.getListElsPetuga().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsPetuga().size(); xx++){
									db.createElsPetugas(contentTable.getListElsPetuga().get(xx));
								}
							}
							else if (resultPerTable.contains("els_provinsi")&& (contentTable.getListElsProvinsi().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsProvinsi().size(); xx++){
									db.createElsProvinsiWithID(contentTable.getListElsProvinsi().get(xx));
								}
							}
							else if (resultPerTable.contains("els_sapi_betina")&& (contentTable.getListElsSapiBetina().size() > 0)){
								for (int xx=0; xx<contentTable.getListElsSapiBetina().size(); xx++){
									db.createDataSapiBetinaWithID(contentTable.getListElsSapiBetina().get(xx));
								}
							}
							else if (resultPerTable.contains("master_bangsa_sapi")&& (contentTable.getListMasterBangsaSapi().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterBangsaSapi().size(); xx++){
									db.createBangsaSapiWithID(contentTable.getListMasterBangsaSapi().get(xx));
								}
							}
							else if (resultPerTable.contains("master_bentuk_wajah_sapi")&& (contentTable.getListMasterBentukWajahSapi().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterBentukWajahSapi().size(); xx++){
									db.createMasterBentukWajahSapi(contentTable.getListMasterBentukWajahSapi().get(xx));
								}
							}
							else if (resultPerTable.contains("master_level_petugas")&& (contentTable.getListMasterLevelPetugas().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterLevelPetugas().size(); xx++){
									db.createMasterLevelPetugas(contentTable.getListMasterLevelPetugas().get(xx));
								}
							}
							else if (resultPerTable.contains("master_penyakit_sapi")&& (contentTable.getListMasterPenyakitSapi().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterPenyakitSapi().size(); xx++){
									db.createMasterPenyakitSapi(contentTable.getListMasterPenyakitSapi().get(xx));
								}
							}
							else if (resultPerTable.contains("master_peran_petugas")&& (contentTable.getListMasterPeranPetugas().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterPeranPetugas().size(); xx++){
									db.createMasterPeranPetugas(contentTable.getListMasterPeranPetugas().get(xx));
								}
							}
							else if (resultPerTable.contains("master_sebab_kematian")&& (contentTable.getListMasterSebabKematian().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterSebabKematian().size(); xx++){
									db.createMasterSebbaKematianWithID(contentTable.getListMasterSebabKematian().get(xx));
								}
							}
							else if (resultPerTable.contains("master_type_lokasi")&& (contentTable.getListMasterTypeLokasi().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterTypeLokasi().size(); xx++){
									db.createMasterTypeLokasi(contentTable.getListMasterTypeLokasi().get(xx));
								}
							}
							else if (resultPerTable.contains("master_warna_sapi")&& (contentTable.getListMasterWarnaSapi().size() > 0)){
								for (int xx=0; xx<contentTable.getListMasterWarnaSapi().size(); xx++){
									db.createMasterWarnaSapi(contentTable.getListMasterWarnaSapi().get(xx));
								}
							}
						}
					}
				}			
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		return result;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		//if (mActivity != null){
			mDialog = new ProgressDialog(mContext);
			
			mDialog.setMessage("Syncronize Data ...");
			mDialog.setIndeterminate(true);
			mDialog.setCancelable(false);
			mDialog.show();
		//}
	}
	
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
//		if (mActivity != null){
			mDialog.dismiss();
//		}
		Intent intent = new Intent(mContext, HomeActivity.class);
		mContext.startActivity(intent);
	}
}
