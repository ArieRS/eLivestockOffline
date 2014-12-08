package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddMasterKabupatenKotaModelSync;
import com.ui.model.sync.EditMasterKabupatenKotaModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class DeleteMasterKabupatenKotaSync extends AsyncTask<String, Void, String> {

	Context mContext;
	FragmentActivity mFragmentActivity;
	EditMasterKabupatenKotaModelSync mEditMasterKabupatenKotaModelSync;
	private ProgressDialog mDialog;
	
	public DeleteMasterKabupatenKotaSync(FragmentActivity activity,
			Context context,
			EditMasterKabupatenKotaModelSync editMasterKabupatenKotaModelSync) {

		mFragmentActivity = activity;
		mContext = context;
		mEditMasterKabupatenKotaModelSync = editMasterKabupatenKotaModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			JSONObject json = new JSONObject();
			
			json.put("id_kabupaten_kota",
					mEditMasterKabupatenKotaModelSync.getId_kabupaten_kota());
			json.put("id_provinsi",
					mEditMasterKabupatenKotaModelSync.getId_provinsi());
			json.put("nama_kabupaten_kota",
					mEditMasterKabupatenKotaModelSync.getNama_kabupaten_kota());
			json.put("user", mEditMasterKabupatenKotaModelSync.getUser());
			json.put("pass", mEditMasterKabupatenKotaModelSync.getPass());
			json.put("guid", mEditMasterKabupatenKotaModelSync.getGuid());
			json.put(
					"transaksi",
					mContext.getResources().getString(
							R.string.edit_master_kabupaten_kota));
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_edit_master_kabupaten_kota), json);
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
						.setMessage("Edit Success ")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// pop to previous fragment
//										mFragmentActivity.getSupportFragmentManager().popBackStack();
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
