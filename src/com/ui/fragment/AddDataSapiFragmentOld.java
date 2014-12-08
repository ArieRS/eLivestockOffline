package com.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ui.common.SessionManager;
import com.ui.elivestock.R;

public class AddDataSapiFragmentOld extends Fragment {

	ActionBar actionBar;
	Activity mActivity;

	Button buttonlogin;
	SessionManager session;
	Context mContext;
	static String urlPOST = "";
	UUID guid;

	EditText editTextNit;
	Spinner spinLokasi;
	Spinner spinBangsaSapi;
	EditText editTextTanggalLahir;
	Spinner spinNitInduk;
	EditText editTextBentukWajahSapi;
	EditText editTextWarnaSapi;
	Spinner spinStatusPunuk;
	Spinner spinAksesorisSapi;
	Spinner spinKepemilikan;
	
	Button buttonSimpan;
	Button buttonBatal;
	

	String[] LokasiSpin = { "Udinaa|Bogor Kota", "Cepot|Bogor Kota",
			"rizkina|Semarang", "Budi|Solo" };
	String[] BangsaSpin = { "Bangsa A", "Bangsa B", "Bangsa C", "Bangsa D" };
	String[] NitIndukSpin = { "1", "123", "999", "2222" };
	String[] yesNoSpin = { "Ya", "Tidak" };
	String[] kepemilikanSpin = { "Pemerintah", "Pribadi" };

	public AddDataSapiFragmentOld(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (Activity) mContext;
		actionBar = mActivity.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setHomeButtonEnabled(true);
		setHasOptionsMenu(true);
		
		//for sync
//		urlPOST = mContext.getResources().getString(R.string.ip)
//				+ "/els/index.php/ws/newdata/method/1";
//		guid = UUID.randomUUID();
	}

	// untuk kembali ke data Home fragment
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			actionBar.setDisplayHomeAsUpEnabled(false);
			//actionBar.setHomeButtonEnabled(false);
			//Fragment fragment = new DataSapiFragment(mContext);
			//General.replaceFragment(fragment, getFragmentManager());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//layout
		View rootView = inflater.inflate(R.layout.add_data_sapi_old, container,
				false);

		editTextNit = (EditText) rootView.findViewById(R.id.edtNIT);
		spinLokasi = (Spinner) rootView.findViewById(R.id.spinLokasi);
		spinBangsaSapi = (Spinner) rootView.findViewById(R.id.spinBangsaSapi);
		editTextTanggalLahir = (EditText) rootView
				.findViewById(R.id.edtTanggalLahirSapi);
		spinNitInduk = (Spinner) rootView.findViewById(R.id.spinNITIndukSapi);
		editTextBentukWajahSapi = (EditText) rootView
				.findViewById(R.id.edtBentukWajah);
		editTextWarnaSapi = (EditText) rootView.findViewById(R.id.edtWarnaSapi);
		spinStatusPunuk = (Spinner) rootView
				.findViewById(R.id.spinStatusPunukSapi);
		spinAksesorisSapi = (Spinner) rootView
				.findViewById(R.id.spinAksesorisSapi);
		spinKepemilikan = (Spinner) rootView
				.findViewById(R.id.spinStatusKepemilikanSapi);
		buttonSimpan = (Button) rootView.findViewById(R.id.buttonSimpanData);
		buttonBatal= (Button) rootView.findViewById(R.id.buttonBatal);

		ArrayAdapter<String> adapterLokasi = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, LokasiSpin);
		ArrayAdapter<String> adapterBangsa = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, BangsaSpin);
		ArrayAdapter<String> adapterNitInduk = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, NitIndukSpin);
		ArrayAdapter<String> adapterYesNo = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, yesNoSpin);
		ArrayAdapter<String> adapterKepemilikan = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, kepemilikanSpin);

		adapterLokasi
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterBangsa
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterNitInduk
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterYesNo
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterKepemilikan
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinLokasi.setAdapter(adapterLokasi);
		spinBangsaSapi.setAdapter(adapterBangsa);
		spinNitInduk.setAdapter(adapterNitInduk);
		spinStatusPunuk.setAdapter(adapterYesNo);
		spinAksesorisSapi.setAdapter(adapterYesNo);
		spinKepemilikan.setAdapter(adapterKepemilikan);
		
		buttonSimpan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new HttpAsyncTask().execute(urlPOST);
			}
		});
		buttonBatal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Fragment fragment = new DataSapiFragment(mContext);
//				General.replaceFragment(fragment, getFragmentManager());
			}
		});
		return rootView;
	}

	public class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			
			String dataNit = editTextNit.getText().toString();//"20000";
			String dataIdl = String.valueOf(spinLokasi.getSelectedItemPosition()+1);//"1";
			String dataTglLahir = editTextTanggalLahir.getText().toString();//"2014-12-31"; // yyyy-mm-dd
			String dataBangsa = String.valueOf(spinBangsaSapi.getSelectedItemPosition()+1);// "3"; // 1 2 3
			String dataNitInduk = spinNitInduk.getSelectedItem().toString(); //"1";
			String dataWajah = editTextBentukWajahSapi.getText().toString();//"lonjong";
			String dataWarna = editTextWarnaSapi.getText().toString();//"putih";
			String dataPunuk = spinStatusPunuk.getSelectedItem().toString().equalsIgnoreCase("ya")?"1":"0";//"1"; // 1 0
			String dataAksesoris = spinAksesorisSapi.getSelectedItem().toString().equalsIgnoreCase("ya")?"1":"0";//"1"; // 1 0
			String dataKepemilikan = spinKepemilikan.getSelectedItem().toString().equalsIgnoreCase("pemerintah")?"1":"0";//"1"; // 1 pemerintah 0 pribadi

			String username = "admin";
			String password = "admin";
			String transaction = "InsertDataSapiBetina";

			try {
				JSONObject json = new JSONObject();
				json.put("nit", dataNit);
				json.put("idl", dataIdl);
				json.put("tanggal_lahir", dataTglLahir);
				json.put("bangsa", dataBangsa);
				json.put("nit_induk", dataNitInduk);
				json.put("bentuk_wajah", dataWajah);
				json.put("warna", dataWarna);
				json.put("status_punuk", dataPunuk);
				json.put("status_aksesoris_kaki", dataAksesoris);
				json.put("status_kepemilikan", dataKepemilikan);
				json.put("username", username);
				json.put("guid", guid.toString());
				json.put("transaksi", transaction);
				// json.put("password", password);

				Log.d("json", json.toString());

				return doPOST(urlPOST, json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Log.d("data", result);
		}
	}

	public static String doPOST(String urlPost, JSONObject data) {
		InputStream inputStream = null;
		String result = "";
		String url = urlPost;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			json = data.toString();

			StringEntity se = new StringEntity(json);

			httpPost.setEntity(se);

			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null){
				result = convertInputStreamToString(inputStream);
			}
			else
				result = "Did not work!";
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
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
