package com.ui.asynctask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

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
import com.ui.adapter.LaporanKelahiranPedetAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.elivestock.R;
import com.ui.list.ResponseLaporanKelahiranPedetSapi;

public class LaporanKelahiranPedetSync extends AsyncTask<String, Void, ResponseLaporanKelahiranPedetSapi> {
	Context mContext;
	DatabaseHelper db;
	
	public LaporanKelahiranPedetSync(Context context){
		mContext = context;
		db = new DatabaseHelper(mContext);
	}
	
	protected ResponseLaporanKelahiranPedetSapi doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseLaporanKelahiranPedetSapi result) {
		
		
	};
	public ResponseLaporanKelahiranPedetSapi readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseLaporanKelahiranPedetSapi result = null;
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				Reader reader_new = new InputStreamReader(inputStream);
				Gson gson = new Gson();

				result = gson.fromJson(reader_new, ResponseLaporanKelahiranPedetSapi.class);

				inputStream.close();
			} else {
				Log.d("readJSONFeed", "Failed to download file");
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return result;
	}
}
