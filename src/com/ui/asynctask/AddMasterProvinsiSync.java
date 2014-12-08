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
import com.ui.model.sync.AddMasterProvinsiModelSync;
import com.ui.webservice.HttpRequestHelper;

public class AddMasterProvinsiSync extends AsyncTask<String, Void, String> {
	Context mContext;
	FragmentActivity mFragmentActivity;
	AddMasterProvinsiModelSync mAddMasterProvinsiModel;
	private ProgressDialog mDialog;
	
	public AddMasterProvinsiSync(FragmentActivity activity, Context context,
			AddMasterProvinsiModelSync addMasterProvinsiModel) {
		mContext = context;
		mFragmentActivity = activity;
		mAddMasterProvinsiModel = addMasterProvinsiModel;
	}

	@Override
	protected String doInBackground(String... params) {
		JSONObject json = new JSONObject();
		try {
			json.put("nama_provinsi",
					mAddMasterProvinsiModel.getNama_provinsi());
			json.put("user", mAddMasterProvinsiModel.getUser());
			json.put("pass", mAddMasterProvinsiModel.getPass());
			json.put("guid", mAddMasterProvinsiModel.getGuid());
			json.put(
					"transaksi",
					mContext.getResources().getString(
							R.string.add_master_provinsi));

			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_master_provinsi), json);
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
