package com.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ui.asynctask.GetAllTableSync;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.webservice.HttpRequestHelper;

public class LoginFragment extends Fragment {

	Button buttonlogin;
	SessionManager mSession;
	Context mContext;
	EditText edtUserName;
	EditText edtUserPassword;
	static String urlPOST = "";
	HomeActivity mActivity;

	public LoginFragment(HomeActivity activity, Context mContext) {
		this.mContext = mContext;
		// urlPOST = mContext.getResources().getString(R.string.ip)
		// + "/els/index.php/ws/login";
		urlPOST = mContext.getResources().getString(R.string.url);
		mActivity = activity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.login, container, false);
		buttonlogin = (Button) rootView.findViewById(R.id.buttonLogin);
		edtUserName = (EditText) rootView.findViewById(R.id.editTextUserName);
		edtUserPassword = (EditText) rootView
				.findViewById(R.id.editTextPassword);

		mSession = new SessionManager(mContext);
		
//		buttonlogin.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				new HttpAsyncTask().execute(urlPOST);
//			}
//		});
		
		buttonlogin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				if (General.checkConnection(mContext)) {
					HttpAsyncTask mHttpAsyncTask = new HttpAsyncTask();
					mHttpAsyncTask.execute(urlPOST);
					try {
						String result  = mHttpAsyncTask.get();
						JSONObject jsonObject = new JSONObject(result);
						String statusResult =  jsonObject.getString("status");
						
						if (statusResult.equalsIgnoreCase("1")){
							GetAllTableSync mGetAllTableSync = new GetAllTableSync(mContext);
							mGetAllTableSync.execute();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					
				}
				else{
					General.showDialogError(mContext, "Your Internet Connection Is Down", "Internet Connection");
				}
			}
		});
		return rootView;
	}

	public class HttpAsyncTask extends AsyncTask<String, Void, String> {
		private ProgressDialog mDialog;
		@Override
		protected String doInBackground(String... urls) {

			String usernameInput = edtUserName.getText().toString();
			String passwordInput = edtUserPassword.getText().toString();

			try {

				JSONObject json = new JSONObject();
				json.put("username", usernameInput);
				json.put("password", passwordInput);

				Log.d("json", json.toString());
				String result = HttpRequestHelper.doPost(urlPOST, json);
				
				try {
					JSONObject jsonObject = new JSONObject(result);
					String statusResult =  jsonObject.getString("status");
					if (statusResult.equalsIgnoreCase("1")){
						JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
						String name = jsonObject1.getString("username");
						String password = jsonObject1.getString("password");
						String peran = jsonObject1.getString("peran");
						int idLeveAdmin = jsonObject1.getInt("id_level_admin");
						int idKabupatenKota = jsonObject1.getInt("id_kabupaten_kota");
						int idProvinsi = jsonObject1.getInt("id_provinsi");
						// {"Data":{"username":"aho","password":"aho","peran":"aho","id_level_admin":1,"id_kabupaten_kota":4,"id_provinsi":3}}
						mSession.createLoginSession(name, password, peran, idLeveAdmin,
								idKabupatenKota, idProvinsi);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					General.showDialogError(mContext, "Tidak Dapat Melakukan Koneksi ke Server", "Cek Koneksi Anda");
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mActivity != null){
				mDialog = new ProgressDialog(mActivity);
				
				mDialog.setMessage("Please Wait ...");
				mDialog.setIndeterminate(true);
				mDialog.setCancelable(false);
				mDialog.show();
			}
		}


		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (mActivity != null){
				mDialog.hide();
			}
			
			if (mSession.isLogin()){
				new AlertDialog.Builder(mContext).setTitle("Login Success").setMessage("Welcome "+mSession.getUserame())
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
			}else {
				General.showDialogError(mContext, "Your Password and Username Missmatch", "Attention");
			}
			
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

			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else
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
//	@Override
//	public void setInitialSavedState(SavedState state) {
//		super.setInitialSavedState(state);
//	}
//	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//	}
}
