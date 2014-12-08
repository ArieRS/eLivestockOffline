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
import com.ui.model.sync.EditDataSapiModelSync;
import com.ui.webservice.HttpRequestHelper;

public class EditDataSapiBetinaSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	EditDataSapiModelSync mEditDataSapiModelSync;
	ProgressDialog mDialog;
	
	public EditDataSapiBetinaSync(FragmentActivity activity, Context context,
			EditDataSapiModelSync editDataSapiModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mEditDataSapiModelSync = editDataSapiModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		JSONObject json = new JSONObject();
		try {
			json.put("nit", mEditDataSapiModelSync.getNit());
			json.put("idl", mEditDataSapiModelSync.getIdl());
			json.put("tanggal_lahir", mEditDataSapiModelSync.getTanggalLahir());
			json.put("bangsa", mEditDataSapiModelSync.getBangsa());
			json.put("nit_induk", mEditDataSapiModelSync.getNit_induk());
			json.put("bentuk_wajah",mEditDataSapiModelSync.getBentuk_wajah());
			json.put("warna", mEditDataSapiModelSync.getWarna());
			json.put("status_punuk", mEditDataSapiModelSync.getStatus_punuk());
			json.put("status_aksesoris_kaki", mEditDataSapiModelSync.getStatus_aksesoris_kaki());
			json.put("status_kepemilikan", mEditDataSapiModelSync.getStatus_kepemilikan());
			json.put("user", mEditDataSapiModelSync.getUser());
			json.put("pass", mEditDataSapiModelSync.getPass());
			json.put("guid", mEditDataSapiModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(
					R.string.edit_data_sapi_betina));
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_edit_sapi_betina), json);
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
							.setMessage("Edit Success ")
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
							.getString(R.string.failed_edit_to_server)
							+ ". Please Check Your Connection.", "Attention");
				}
			} catch (JSONException e) {
				General.showDialogError(mContext, mContext.getResources()
						.getString(R.string.failed_edit_to_server)
						+ ". Please Check Your Connection.", "Attention");
				e.printStackTrace();
			}
		}
}
