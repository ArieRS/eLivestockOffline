package com.ui.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.sync.AddKejadianIBModelSync;
import com.ui.webservice.HttpRequestHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class AddKejadianIbSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	AddKejadianIBModelSync mAddKejadianIBModelSync;
	private ProgressDialog mDialog;

	public AddKejadianIbSync(FragmentActivity fragmentActivity,
			Context context, AddKejadianIBModelSync addKejadianIBModelSync) {
		mFragmentActivity = fragmentActivity;
		mContext = context;
		mAddKejadianIBModelSync = addKejadianIBModelSync;
	}

	@Override
	protected String doInBackground(String... params) {
		
		try {
			JSONObject json = new JSONObject();
			json.put("kode_straw", mAddKejadianIBModelSync.getKode_straw());
			json.put("tanggal_ib", mAddKejadianIBModelSync.getTanggal_ib());
			json.put("nit", mAddKejadianIBModelSync.getNit());
			json.put("id_petugas", mAddKejadianIBModelSync.getId_petugas());
			json.put("user", mAddKejadianIBModelSync.getUser());
			json.put("pass", mAddKejadianIBModelSync.getPass());
			json.put("guid", mAddKejadianIBModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.add_kejadian_ib));
			
			return HttpRequestHelper.doPost(
					mContext.getResources().getString(
							R.string.url_insert_kejadian_ib), json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
