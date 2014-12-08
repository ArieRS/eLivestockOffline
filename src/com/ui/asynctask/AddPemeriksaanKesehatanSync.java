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
import com.ui.model.sync.AddRiwayatKesehatanModelSync;
import com.ui.webservice.HttpRequestHelper;

public class AddPemeriksaanKesehatanSync extends
		AsyncTask<String, Void, String> {

	FragmentActivity mFragmentActivity;
	Context mContext;
	AddRiwayatKesehatanModelSync mAddRiwayatKesehatanModelSync;
	private ProgressDialog mDialog;
	
	public AddPemeriksaanKesehatanSync(FragmentActivity activity,
			Context context,
			AddRiwayatKesehatanModelSync addRiwayatKesehatanModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mAddRiwayatKesehatanModelSync = addRiwayatKesehatanModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			JSONObject json = new JSONObject();
			json.put("diagnosa", mAddRiwayatKesehatanModelSync.getDiagnosa());
			json.put("perlakuan", mAddRiwayatKesehatanModelSync.getPerlakuan());
			json.put("nit", mAddRiwayatKesehatanModelSync.getNit());
			json.put("id_petugas", mAddRiwayatKesehatanModelSync.getId_petugas());
			json.put("tanggal_periksa", mAddRiwayatKesehatanModelSync.getTanggal_periksa());
			json.put("user", mAddRiwayatKesehatanModelSync.getUser());
			json.put("pass", mAddRiwayatKesehatanModelSync.getPass());
			json.put("guid", mAddRiwayatKesehatanModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.add_riwayat_kesehatan));
				
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_riwayat_kesehatan), json);
		} catch (JSONException e) {
			// TODO: handle exception
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
