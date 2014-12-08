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

import com.google.gson.Gson;
import com.ui.adapter.MasterKabupatenkotaAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.elivestock.R;
import com.ui.list.ResponseMasterKabupatenKota;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.sync.MasterKabupatenKotaModelSync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class MasterKabupatenKotaSync extends
		AsyncTask<String, Void, ResponseMasterKabupatenKota> {
	Context mContext;
	DatabaseHelper db;

	public MasterKabupatenKotaSync(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
	}

	public ResponseMasterKabupatenKota readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseMasterKabupatenKota result = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				Reader reader_new = new InputStreamReader(inputStream);
				Gson gson = new Gson();

				result = gson.fromJson(reader_new,
						ResponseMasterKabupatenKota.class);

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
	protected ResponseMasterKabupatenKota doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseMasterKabupatenKota result) {
		ArrayList<elsKabupatenKota> masterKabupatenKotaModels = result
				.getContent();
		for (elsKabupatenKota masterKabupatenKotaModel : masterKabupatenKotaModels) {
			elsKabupatenKota ElsKabupatenKota = new elsKabupatenKota();
			ElsKabupatenKota.setId_kabupaten_kota(masterKabupatenKotaModel.getId_kabupaten_kota());
			ElsKabupatenKota.setNama_kabupaten_kota(masterKabupatenKotaModel.getNama_kabupaten_kota());

			long hasil = db.createKabupatenKotaWithID(ElsKabupatenKota);
			if (hasil == -1) {
				Log.e("Error MasterKabupatenKotal", "gagal insert sync");
			}
		}
		db.closeDB();
	};
}
