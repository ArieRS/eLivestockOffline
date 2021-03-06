package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.EditMasterBangsaModelSync;
import com.ui.webservice.HttpRequestHelper;

public class EditMasterBangsaSapiSync extends AsyncTask<String, Void, String> {
	EditMasterBangsaModelSync mEditMasterBangsaModelSync;
	Context mContext;
	FragmentActivity mFragmentActivity;
	
	private ProgressDialog mDialog;
	
	public EditMasterBangsaSapiSync(FragmentActivity activity, Context context,
			EditMasterBangsaModelSync editMasterBangsaModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mEditMasterBangsaModelSync = editMasterBangsaModelSync;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		mDialog = new ProgressDialog(mFragmentActivity);
		mDialog.setMessage("Please wait...");
		mDialog.setIndeterminate(true);
		mDialog.setCancelable(false);
		mDialog.show();
	}
	
	@Override
	protected String doInBackground(String... urls) {

		try {

			JSONObject json = new JSONObject();
			json.put("id", mEditMasterBangsaModelSync.getId());
			json.put("value", mEditMasterBangsaModelSync.getValue());
			json.put("user", mEditMasterBangsaModelSync.getUser());
			json.put("pass", mEditMasterBangsaModelSync.getPass());
			json.put("guid", mEditMasterBangsaModelSync.getGuid());
			json.put(
					"transaksi",
					mContext.getResources().getString(
							R.string.edit_master_bangsa_sapi));

			Log.d("json", json.toString());

			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_edit_master_bangsa), json);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
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
