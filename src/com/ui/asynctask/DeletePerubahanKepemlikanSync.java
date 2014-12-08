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
import com.ui.model.sync.EditPerubahanKepemilikanModelSync;
import com.ui.webservice.HttpRequestHelper;

public class DeletePerubahanKepemlikanSync extends AsyncTask<String, Void, String> {
	FragmentActivity mFragmentActivity;
	Context mContext;
	EditPerubahanKepemilikanModelSync mEditPerubahanKepemilikanModelSync;
	private ProgressDialog mDialog;

	public DeletePerubahanKepemlikanSync(FragmentActivity fragmentActivity,
			Context context,
			EditPerubahanKepemilikanModelSync editPerubahanKepemilikanModelSync) {
		mFragmentActivity = fragmentActivity;
		mContext = context;
		mEditPerubahanKepemilikanModelSync = editPerubahanKepemilikanModelSync;
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			JSONObject json = new JSONObject();
			json.put("kode_riwayat_kepemilikan", mEditPerubahanKepemilikanModelSync.getKode_riwayat_kepemilikan());
//			json.put("idl_sebelum", mEditPerubahanKepemilikanModelSync.getIdl_sebelum());
//			json.put("idl_sesudah", mEditPerubahanKepemilikanModelSync.getIdl_sesudah());
//			json.put("tanggal_kejadian", mEditPerubahanKepemilikanModelSync.getTanggal_kejadian());
			json.put("nit", mEditPerubahanKepemilikanModelSync.getNit());
			json.put("user", mEditPerubahanKepemilikanModelSync.getUser());
			json.put("pass", mEditPerubahanKepemilikanModelSync.getPass());
			json.put("guid", mEditPerubahanKepemilikanModelSync.getGuid());
			json.put("transaksi",mContext.getResources().getString(R.string.delete_perubahan_kepemilikan));

			Log.d("json", json.toString());

			return HttpRequestHelper.doPost(mContext.getResources().getString(R.string.url_delete_perubahan_kepemilikan), json);

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
						.setMessage("Delete Success ")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// pop to previous fragment
//										mFragmentActivity.getSupportFragmentManager().popBackStack();
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
