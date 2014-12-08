package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddMasterLokasiModelSync;
import com.ui.model.sync.EditMasterLokasiModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class DeleteMasterLokasiSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	EditMasterLokasiModelSync mEditMasterLokasiModelSync;
	private ProgressDialog mDialog;
	
	public DeleteMasterLokasiSync(FragmentActivity activity, Context context, EditMasterLokasiModelSync editMasterLokasiModelSync){
		mFragmentActivity = activity;
		mContext = context;
		mEditMasterLokasiModelSync = editMasterLokasiModelSync;
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			JSONObject json = new JSONObject();
			json.put("idl", mEditMasterLokasiModelSync.getIdl());
//			json.put("nama_kontak", mEditMasterLokasiModelSync.getNama_kontak());
//			json.put("id_kabupaten_kota", mEditMasterLokasiModelSync.getId_kabupaten_kota());
//			json.put("address", mEditMasterLokasiModelSync.getAddress());
//			json.put("no_telepon", mEditMasterLokasiModelSync.getNo_telepon());
//			json.put("type", mEditMasterLokasiModelSync.getType());
//			json.put("id_petugas", mEditMasterLokasiModelSync.getId_petugas());
			json.put("user", mEditMasterLokasiModelSync.getUser());
			json.put("pass", mEditMasterLokasiModelSync.getPass());
			json.put("guid", mEditMasterLokasiModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.delete_master_lokasi));
			
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_delete_master_lokasi), json);
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
							.setMessage("Delete Success")
							.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int whichButton) {
											// pop to previous fragment
//											mFragmentActivity.getSupportFragmentManager().popBackStack();
										}
									}).create().show();
				} else {
					General.showDialogError(mContext, mContext.getResources()
							.getString(R.string.failed_delete_to_server)
							+ ". Please Check Your Connection.", "Attention");
				}
			} catch (JSONException e) {
				General.showDialogError(mContext, mContext.getResources()
						.getString(R.string.failed_delete_to_server)
						+ ". Please Check Your Connection.", "Attention");
				e.printStackTrace();
			}
		}
}
