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
import com.ui.list.ResponseMasterBangsaSapi;
import com.ui.model.database.masterBangsaSapi;
import com.ui.model.sync.MasterBangsaSapiModelSync;

public class MasterBangsaSapiSync extends
		AsyncTask<String, Void, ResponseMasterBangsaSapi> {
	DatabaseHelper db;
	Context mContext;

	public MasterBangsaSapiSync(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
	}

	public ResponseMasterBangsaSapi readJSONFeed(String URL) {

		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseMasterBangsaSapi result = null;
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
						ResponseMasterBangsaSapi.class);

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
	protected ResponseMasterBangsaSapi doInBackground(String... urls) {
		return readJSONFeed(urls[0]);
	}

	protected void onPostExecute(ResponseMasterBangsaSapi result) {
		// insert database
		ArrayList<MasterBangsaSapiModelSync> listMasterBangsaSapiModel = result
				.getContent();
		for (MasterBangsaSapiModelSync masterBangsaSapiModel : listMasterBangsaSapiModel) {
			masterBangsaSapi modelBangsaSapi = new masterBangsaSapi();
			modelBangsaSapi.setId(Integer.parseInt(masterBangsaSapiModel
					.getID()));
			modelBangsaSapi.setValue(masterBangsaSapiModel.getVALUE());
			db.createBangsaSapiWithID(modelBangsaSapi);
		}
		db.closeDB();
	};
}
