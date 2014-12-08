package com.ui.asynctask;

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
import com.ui.webservice.HttpRequestHelper;

public class AddDataSapiBetinaSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	AddDataSapiModelSync mAddDataSapiModelSync;
	ProgressDialog mDialog;
	
	public AddDataSapiBetinaSync(FragmentActivity activity, Context context,
			AddDataSapiModelSync addDataSapiModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mAddDataSapiModelSync = addDataSapiModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		JSONObject json = new JSONObject();
		try {
			json.put("nit", mAddDataSapiModelSync.getNit());
			json.put("idl", mAddDataSapiModelSync.getIdl());
			json.put("tanggal_lahir", mAddDataSapiModelSync.getTanggalLahir());
			json.put("bangsa", mAddDataSapiModelSync.getBangsa());
			json.put("nit_induk", mAddDataSapiModelSync.getNit_induk());
			json.put("bentuk_wajah",mAddDataSapiModelSync.getBentuk_wajah());
			json.put("warna", mAddDataSapiModelSync.getWarna());
			json.put("status_punuk", mAddDataSapiModelSync.getStatus_punuk());
			json.put("status_aksesoris_kaki", mAddDataSapiModelSync.getStatus_aksesoris_kaki());
			json.put("status_kepemilikan", mAddDataSapiModelSync.getStatus_kepemilikan());
			json.put("user", mAddDataSapiModelSync.getUser());
			json.put("pass", mAddDataSapiModelSync.getPass());
			json.put("guid", mAddDataSapiModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.add_data_sapi_betina));
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_sapi_betina), json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
					General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_insert_to_server)
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
