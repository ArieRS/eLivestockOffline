package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddPerubahanKepemilikanModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class AddPerubahanKepemlikanSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	AddPerubahanKepemilikanModelSync mAddPerubahanKepemilikanModelSync;
	private ProgressDialog mDialog;

	public AddPerubahanKepemlikanSync(FragmentActivity fragmentActivity,
			Context context,
			AddPerubahanKepemilikanModelSync addPerubahanKepemilikanModelSync) {
		mFragmentActivity = fragmentActivity;
		mContext = context;
		mAddPerubahanKepemilikanModelSync = addPerubahanKepemilikanModelSync;
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {

			JSONObject json = new JSONObject();
			json.put("idl_sebelum", mAddPerubahanKepemilikanModelSync.getIdl_sebelum());
			json.put("idl_sesudah", mAddPerubahanKepemilikanModelSync.getIdl_sesudah());
			json.put("tanggal_kejadian", mAddPerubahanKepemilikanModelSync.getTanggal_kejadian());
			json.put("nit", mAddPerubahanKepemilikanModelSync.getNit());
			json.put("user", mAddPerubahanKepemilikanModelSync.getUser());
			json.put("pass", mAddPerubahanKepemilikanModelSync.getPass());
			json.put("guid", mAddPerubahanKepemilikanModelSync.getGuid());
			json.put(
					"transaksi",
					mContext.getResources().getString(
							R.string.add_perubahan_kepemilikan));

			Log.d("json", json.toString());

			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_perubahan_kepemilikan), json);

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
