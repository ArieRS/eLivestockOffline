package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddKejadianIBModelSync;
import com.ui.model.sync.EditKejadianIBModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class EditKejadianIbSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	EditKejadianIBModelSync mEditKejadianIBModelSync;
	private ProgressDialog mDialog;

	public EditKejadianIbSync(FragmentActivity fragmentActivity,
			Context context, EditKejadianIBModelSync editKejadianIBModelSync) {
		mFragmentActivity = fragmentActivity;
		mContext = context;
		mEditKejadianIBModelSync = editKejadianIBModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		
		try {
			JSONObject json = new JSONObject();
			json.put("kode_kejadian_ib", mEditKejadianIBModelSync.getKode_kejadian_ib());
			json.put("kode_straw", mEditKejadianIBModelSync.getKode_straw());
			json.put("tanggal_ib", mEditKejadianIBModelSync.getTanggal_ib());
			json.put("nit", mEditKejadianIBModelSync.getNit());
			json.put("id_petugas", mEditKejadianIBModelSync.getId_petugas());
			json.put("user", mEditKejadianIBModelSync.getUser());
			json.put("pass", mEditKejadianIBModelSync.getPass());
			json.put("guid", mEditKejadianIBModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.edit_kejadian_ib));
			
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_edit_kejadian_ib), json);
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
						.setMessage("Edit Success")
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
