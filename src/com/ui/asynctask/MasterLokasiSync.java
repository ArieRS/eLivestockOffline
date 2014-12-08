package com.ui.asynctask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.ui.common.DatabaseHelper;
import com.ui.list.ResponseMasterLokasi;
import com.ui.model.database.elsLokasi;
import com.ui.model.sync.MasterLokasiModelSync;

public class MasterLokasiSync extends
		AsyncTask<String, Void, ResponseMasterLokasi> {
	Context mContext;
	DatabaseHelper db;
	public MasterLokasiSync(Context context){
		mContext = context;
		db = new DatabaseHelper(mContext);
	}
	public ResponseMasterLokasi readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseMasterLokasi result = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				Reader reader_new = new InputStreamReader(inputStream);
				Gson gson = new Gson();

				result = gson.fromJson(reader_new, ResponseMasterLokasi.class);

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

	protected ResponseMasterLokasi doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseMasterLokasi result) {
		ArrayList<MasterLokasiModelSync> mMasterLokasiModel = result.getContent();
		for (MasterLokasiModelSync masterLokasiModel : mMasterLokasiModel) {
			elsLokasi elsLokasiModel = new elsLokasi();
			elsLokasiModel.setIdl(Integer.valueOf(masterLokasiModel.getIdl()));
			elsLokasiModel.setNama_kontak(masterLokasiModel.getNama_kontak());
			elsLokasiModel.setId_kabupaten_kota(Integer.valueOf(masterLokasiModel.getId_kabupaten_kota()));
			elsLokasiModel.setAddress(masterLokasiModel.getAddress());
			elsLokasiModel.setNo_telepon(masterLokasiModel.getNo_telepon());
			elsLokasiModel.setType(masterLokasiModel.type);
			elsLokasiModel.setId_petugas(Integer.parseInt(masterLokasiModel.getId_petugas()));
			db.createLokasiWithID(elsLokasiModel);
		}
		db.closeDB();
	};
}
