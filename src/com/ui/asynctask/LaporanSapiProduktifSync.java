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
import com.ui.adapter.LaporanSapiProduktifAdapter;
import com.ui.elivestock.R;
import com.ui.list.ResponseLaporanSapiProduktif;

public class LaporanSapiProduktifSync extends
		AsyncTask<String, Void, ResponseLaporanSapiProduktif> {
	Context mContext;

	public LaporanSapiProduktifSync(Context context) {
		mContext = context;
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	public ResponseLaporanSapiProduktif readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseLaporanSapiProduktif result = null;

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
						ResponseLaporanSapiProduktif.class);

				// BufferedReader reader = new BufferedReader( new
				// InputStreamReader(inputStream));
				// String line;
				// while ((line = reader.readLine()) !=null){
				// stringBuilder.append(line);
				// }

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

	protected ResponseLaporanSapiProduktif doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseLaporanSapiProduktif result) {

		// adapter = new LaporanSapiProduktifAdapter(mContext,
		// R.layout.item_list_with_icon, result.getContent());
		// adapter.notifyDataSetChanged();
		// listView.setAdapter(adapter);
	};
}
