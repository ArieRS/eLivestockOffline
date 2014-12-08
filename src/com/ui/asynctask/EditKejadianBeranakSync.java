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
import com.ui.model.sync.AddKejadianBeranakModelSync;
import com.ui.model.sync.EditKejadianBeranakModelSync;
import com.ui.webservice.HttpRequestHelper;

public class EditKejadianBeranakSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	EditKejadianBeranakModelSync mEditKejadianBeranakModelSync;
	private ProgressDialog mDialog;

	public EditKejadianBeranakSync(FragmentActivity activity, Context context,
			EditKejadianBeranakModelSync editKejadianBeranakModelSync) {
		mFragmentActivity = activity;
		mContext = context;
		mEditKejadianBeranakModelSync = editKejadianBeranakModelSync;
	}

	@Override
	protected String doInBackground(String... param) {

		try {
			JSONObject json = new JSONObject();
			json.put("kode_kejadian_beranak", mEditKejadianBeranakModelSync.getKode_kejadian_beranak());
			json.put("nit", mEditKejadianBeranakModelSync.getNit());
			json.put("banyaknya_anak_betina",
					mEditKejadianBeranakModelSync.getBanyak_anak_betina());
			json.put("banyaknya_anak_jantan",
					mEditKejadianBeranakModelSync.getBanyak_anak_jantan());
			json.put("tanggal_beranak",
					mEditKejadianBeranakModelSync.getTanggal_beranak());
			json.put("user", mEditKejadianBeranakModelSync.getUser());
			json.put("pass", mEditKejadianBeranakModelSync.getPass());
			json.put("guid", mEditKejadianBeranakModelSync.getGuid());
			json.put("transaksi",mContext.getResources().getString(
							R.string.edit_kejadian_beranak));

			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_edit_kejadian_beranak), json);

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
			//{"message":"sukses","status":1}
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
