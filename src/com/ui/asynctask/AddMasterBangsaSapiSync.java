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
import com.ui.model.sync.AddMasterBangsaModelSync;
import com.ui.webservice.HttpRequestHelper;

public class AddMasterBangsaSapiSync extends AsyncTask<String, Void, String> {
	AddMasterBangsaModelSync mAddMasterBangsaSapiModel;
	Context mContext;
	FragmentActivity mAddBangsaSapiFragmentActivity;
	private ProgressDialog mDialog;
	
	public AddMasterBangsaSapiSync(FragmentActivity activity, Context context,
			AddMasterBangsaModelSync addMasterBangsaSapiModel) {
		mAddBangsaSapiFragmentActivity = activity;
		mContext = context;
		mAddMasterBangsaSapiModel = addMasterBangsaSapiModel;
	}

	@Override
	protected String doInBackground(String... urls) {

		try {

			JSONObject json = new JSONObject();
			json.put("value", mAddMasterBangsaSapiModel.getValue());
			json.put("user", mAddMasterBangsaSapiModel.getUser());
			json.put("pass", mAddMasterBangsaSapiModel.getPass());
			json.put("guid", mAddMasterBangsaSapiModel.getGuid());
			json.put(
					"transaksi",
					mContext.getResources().getString(R.string.add_master_bangsa_sapi));

			Log.d("json", json.toString());

			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_master_bangsa), json);

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
		//<div style="border:1px solid #990000;padding-left:20px;margin:0 0 10px 0;"><h4>A PHP Error was encountered</h4><p>Severity: Notice</p><p>Message:  Undefined index: id</p><p>Filename: ws/insert.php</p><p>Line Number: 133</p></div>{"id":null,"message":"sukses","status":1}
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
										mAddBangsaSapiFragmentActivity.getSupportFragmentManager()
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
