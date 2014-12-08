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
import com.ui.model.sync.EditRiwayatKesehatanModelSync;
import com.ui.webservice.HttpRequestHelper;

public class DeletePemeriksaanKesehatanSync extends
		AsyncTask<String, Void, String> {

	FragmentActivity mFragmentActivity;
	Context mContext;
	EditRiwayatKesehatanModelSync mEditPemeriksaanKesehatanModelSync;
	private ProgressDialog mDialog;
	
	public DeletePemeriksaanKesehatanSync(FragmentActivity activity,
			Context context,
			EditRiwayatKesehatanModelSync editPemeriksaanKesehatanModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mEditPemeriksaanKesehatanModelSync = editPemeriksaanKesehatanModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			JSONObject json = new JSONObject();
			json.put("kode_pemeriksaan_kesehatan", mEditPemeriksaanKesehatanModelSync.getKode_periksaan_kesehatan());
//			json.put("diagnosa", mEditPemeriksaanKesehatanModelSync.getDiagnosa());
//			json.put("perlakuan", mEditPemeriksaanKesehatanModelSync.getPerlakuan());
//			json.put("nit", mEditPemeriksaanKesehatanModelSync.getNit());
//			json.put("id_petugas", mEditPemeriksaanKesehatanModelSync.getId_petugas());
//			json.put("tanggal_periksa", mEditPemeriksaanKesehatanModelSync.getTanggal_periksa());
			json.put("user", mEditPemeriksaanKesehatanModelSync.getUser());
			json.put("pass", mEditPemeriksaanKesehatanModelSync.getPass());
			json.put("guid", mEditPemeriksaanKesehatanModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.edit_riwayat_kesehatan));
				
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_delete_riwayat_kesehatan), json);
		} catch (JSONException e) {
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
							.setMessage("Delete Success ")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int whichButton) {
											// pop to previous fragment
//											mFragmentActivity.getSupportFragmentManager().popBackStack();
										}
									}).create().show();
				} else {
					General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_delete_to_server)+ ". Please Check Your Connection.", "Attention");
				}
			} catch (JSONException e) {
				General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_delete_to_server)+ ". Please Check Your Connection.", "Attention");
				e.printStackTrace();
			}
		}

}
