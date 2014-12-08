package com.ui.asynctask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddDataSapiModelSync;
import com.ui.model.sync.AddKejadianBeranakModelSync;
import com.ui.webservice.HttpRequestHelper;

public class AddKejadianBeranakSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	AddKejadianBeranakModelSync mAddKejadianBeranakModelSync;
	private ProgressDialog mDialog;
	AddDataSapiModelSync mAddDataSapiModelSync1 ;
	AddDataSapiModelSync mAddDataSapiModelSync2 ;
	

	public AddKejadianBeranakSync(FragmentActivity activity, Context context,
			AddKejadianBeranakModelSync addKejadianBeranakModelSync, 
			AddDataSapiModelSync addDataSapiModelSync1, 
			AddDataSapiModelSync addDataSapiModelSync2) {
		
		mFragmentActivity = activity;
		mContext = context;
		mAddKejadianBeranakModelSync = addKejadianBeranakModelSync;
		mAddDataSapiModelSync1 = addDataSapiModelSync1;
		mAddDataSapiModelSync2 = addDataSapiModelSync2;
	}

	@Override
	protected String doInBackground(String... param) {

		try {
			JSONObject json = new JSONObject();
			json.put("nit", mAddKejadianBeranakModelSync.getNit());
			json.put("banyaknya_anak_betina",mAddKejadianBeranakModelSync.getBanyak_anak_betina());
			json.put("banyaknya_anak_jantan",mAddKejadianBeranakModelSync.getBanyak_anak_jantan());
			json.put("tanggal_beranak",mAddKejadianBeranakModelSync.getTanggal_beranak());
			json.put("user", mAddKejadianBeranakModelSync.getUser());
			json.put("pass", mAddKejadianBeranakModelSync.getPass());
			json.put("guid", mAddKejadianBeranakModelSync.getGuid());
			json.put("transaksi",mContext.getResources().getString(R.string.add_kejadian_beranak));
			
			JSONArray jsonArrayAnakBetina = new JSONArray();
			JSONObject jsonAnak1 = new JSONObject();
			if (mAddDataSapiModelSync1.getNit() != 0){
				jsonAnak1.put("nit", mAddDataSapiModelSync1.getNit());
				jsonAnak1.put("idl", mAddDataSapiModelSync1.getIdl());
				jsonAnak1.put("tanggal_lahir", mAddDataSapiModelSync1.getTanggalLahir());
				jsonAnak1.put("bangsa", mAddDataSapiModelSync1.getBangsa());
				jsonAnak1.put("nit_induk", mAddDataSapiModelSync1.getNit_induk());
				jsonAnak1.put("bentuk_wajah",mAddDataSapiModelSync1.getBentuk_wajah());
				jsonAnak1.put("warna", mAddDataSapiModelSync1.getWarna());
				jsonAnak1.put("status_punuk", mAddDataSapiModelSync1.getStatus_punuk());
				jsonAnak1.put("status_aksesoris_kaki", mAddDataSapiModelSync1.getStatus_aksesoris_kaki());
				jsonAnak1.put("status_kepemilikan", mAddDataSapiModelSync1.getStatus_kepemilikan());
				jsonArrayAnakBetina.put(jsonAnak1);
			}
			
			
			JSONObject jsonAnak2 = new JSONObject();
			if (mAddDataSapiModelSync2.getNit() != 0){
				jsonAnak2.put("nit", mAddDataSapiModelSync2.getNit());
				jsonAnak2.put("idl", mAddDataSapiModelSync2.getIdl());
				jsonAnak2.put("tanggal_lahir", mAddDataSapiModelSync2.getTanggalLahir());
				jsonAnak2.put("bangsa", mAddDataSapiModelSync2.getBangsa());
				jsonAnak2.put("nit_induk", mAddDataSapiModelSync2.getNit_induk());
				jsonAnak2.put("bentuk_wajah",mAddDataSapiModelSync2.getBentuk_wajah());
				jsonAnak2.put("warna", mAddDataSapiModelSync2.getWarna());
				jsonAnak2.put("status_punuk", mAddDataSapiModelSync2.getStatus_punuk());
				jsonAnak2.put("status_aksesoris_kaki", mAddDataSapiModelSync2.getStatus_aksesoris_kaki());
				jsonAnak2.put("status_kepemilikan", mAddDataSapiModelSync2.getStatus_kepemilikan());
				jsonArrayAnakBetina.put(jsonAnak2);
			}
			
			json.put("anak", jsonArrayAnakBetina);
			
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_kejadian_beranak), json);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = new ProgressDialog(mContext);
		mDialog.setCancelable(false);
		mDialog.setMessage("Please wait...");
		mDialog.show();
	}

	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(String result) {
		mDialog.dismiss();
		try {
			JSONObject jsonObject = new JSONObject(result);
			// {"message":"sukses"}
			String status = jsonObject.getString("message");

			if (status.equalsIgnoreCase("sukses")) {
				new AlertDialog.Builder(mContext)
						.setTitle("Success")
						.setMessage("Insert Success ")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// pop to previous fragment
										mFragmentActivity
												.getSupportFragmentManager()
												.popBackStack();
									}
								}).create().show();
			} else {
				General.showDialogError(mContext, mContext.getResources()
						.getString(R.string.failed_insert_to_server)
						+ ". Please Check Your Connection.", "Attention");
			}
		} catch (JSONException e) {
			General.showDialogError(mContext, mContext.getResources()
					.getString(R.string.failed_insert_to_server)
					+ ". Please Check Your Connection.", "Attention");
			e.printStackTrace();
		}
	}
}
