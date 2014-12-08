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
import com.ui.elivestock.R;
import com.ui.list.ResponseMasterProvinsi;
import com.ui.model.database.elsProvinsi;
import com.ui.model.sync.MasterProvinsiModelSync;

public class MasterProvinsiSync extends
		AsyncTask<String, Void, ResponseMasterProvinsi> {
	DatabaseHelper db;
	Context mContext;
	public MasterProvinsiSync(Context context){
		mContext = context;
		db  = new DatabaseHelper(mContext);
	}
	public ResponseMasterProvinsi readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseMasterProvinsi result = null;

		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				Reader reader_new = new InputStreamReader(inputStream);
				Gson gson = new Gson();

				result = gson
						.fromJson(reader_new, ResponseMasterProvinsi.class);

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
	protected ResponseMasterProvinsi doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseMasterProvinsi result) {
		
		ArrayList<MasterProvinsiModelSync> dataProvinsi = result.getContent();
		for (MasterProvinsiModelSync masterProvinsiModel : dataProvinsi) {
			elsProvinsi mElsProvinsi = new elsProvinsi();
			mElsProvinsi.setId_provinsi(Integer.parseInt(masterProvinsiModel.getId_provinsi()));
			mElsProvinsi.setNama_provinsi(masterProvinsiModel.getNama_provinsi());
			db.createElsProvinsiWithID(mElsProvinsi);
		}
		db.closeDB();
	};
}
