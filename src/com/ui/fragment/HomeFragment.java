package com.ui.fragment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.list.ResponseCariSapiBetina;
import com.ui.model.laporan.SearchDataSapiResultModel;

public class HomeFragment extends Fragment {

	EditText editEarTag;
	Button buttonCariSapi;
	Context mContext;
	ActionBar actioncBar;
	Activity activity;
	
	ResponseCariSapiBetina mResponseCariSapiBetina = null;

	Fragment fragment;
	String mUrl = "";

	public HomeFragment(Context mContext) {
		this.mContext = mContext;
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,false);
		editEarTag = (EditText) rootView.findViewById(R.id.edtSearchSapiBetina);
		buttonCariSapi = (Button) rootView.findViewById(R.id.buttonCariHome);
		
		buttonCariSapi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View mView) {
				setListenerbutton(mView);
			}
		});

		return rootView;
	}

	private void setListenerbutton(View mView) {
		String NIT = editEarTag.getText().toString();
//		mUrl = getResources().getString(R.string.url_admin) + "/method/3~"+ masukanEarTag + "/format/json";
		DatabaseHelper newDatabaseHelper = new DatabaseHelper(mContext);
		SearchDataSapiResultModel mDataSapiResultModel =  newDatabaseHelper.cariDataSapi(NIT);
		
		if ((mDataSapiResultModel.mDataSapi.size() == 0) && (mDataSapiResultModel.mKejadianBeranak.size() == 0) &&
			(mDataSapiResultModel.mRiwayatKesehatan.size() == 0) && (mDataSapiResultModel.mKejadianIB.size() == 0) &&
			(mDataSapiResultModel.mPerubahanKepemilikan.size() == 0) && (mDataSapiResultModel.mKejadianKematian.size() == 0))
		{
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Data Tidak Ditemukan")
			.setMessage("Tidak ada data sapi dengan NIT = "+NIT)
			.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,int arg1) {
							}
						}).create().show();
		}
		else{
			fragment = new SearchDataSapiResultFragment(mContext, mDataSapiResultModel);
			new General().replaceFragmentAddBackStack(fragment,getFragmentManager());
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	public ResponseCariSapiBetina readJSONFeed(String URL) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL);
		InputStream inputStream = null;
		ResponseCariSapiBetina result = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();

				Reader reader_new = new InputStreamReader(inputStream);
				Gson gson = new Gson();

				result = gson.fromJson(reader_new, ResponseCariSapiBetina.class);

				inputStream.close();
			} else {
				Log.d("readJSONFeed", "Failed to download file");
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return result;
	}
	@Override
	public void setInitialSavedState(SavedState state) {
		super.setInitialSavedState(state);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
