package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddMasterLokasiModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class AddMasterLokasiSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	AddMasterLokasiModelSync mAddMasterLokasiModelSync;
	private ProgressDialog mDialog;
	
	public AddMasterLokasiSync(FragmentActivity activity, Context context, AddMasterLokasiModelSync addMasterLokasiModelSync){
		mFragmentActivity = activity;
		mContext = context;
		mAddMasterLokasiModelSync = addMasterLokasiModelSync;
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			JSONObject json = new JSONObject();
			json.put("nama_kontak", mAddMasterLokasiModelSync.getNama_kontak());
			json.put("id_kabupaten_kota", mAddMasterLokasiModelSync.getId_kabupaten_kota());
			json.put("address", mAddMasterLokasiModelSync.getAddress());
			json.put("no_telepon", mAddMasterLokasiModelSync.getNo_telepon());
			json.put("type", mAddMasterLokasiModelSync.getType());
			json.put("id_petugas", mAddMasterLokasiModelSync.getId_petugas());
			json.put("user", mAddMasterLokasiModelSync.getUser());
			json.put("pass", mAddMasterLokasiModelSync.getPass());
			json.put("guid", mAddMasterLokasiModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.add_master_lokasi));
			
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_master_lokasi), json);
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
							.setMessage("Insert Success")
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
