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
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.sync.AddRiwayatKesehatanModelSync;
import com.ui.model.sync.EditRiwayatKesehatanModelSync;
import com.ui.webservice.HttpRequestHelper;

public class EditPemeriksaanKesehatanSync extends
		AsyncTask<String, Void, String> {

	FragmentActivity mFragmentActivity;
	Context mContext;
	EditRiwayatKesehatanModelSync mEditPemeriksaanKesehatanModelSync;
	private ProgressDialog mDialog;
	HomeActivity mActivity;
	
	public EditPemeriksaanKesehatanSync(HomeActivity activity,  FragmentActivity fragmentActivity,
			Context context, EditRiwayatKesehatanModelSync editPemeriksaanKesehatanModelSync) {
		mFragmentActivity = fragmentActivity;
		mContext = context;
		mEditPemeriksaanKesehatanModelSync = editPemeriksaanKesehatanModelSync;
		mActivity = activity;
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			JSONObject json = new JSONObject();
			json.put("kode_pemeriksaan_kesehatan", mEditPemeriksaanKesehatanModelSync.getKode_periksaan_kesehatan());
			json.put("diagnosa", mEditPemeriksaanKesehatanModelSync.getDiagnosa());
			json.put("perlakuan", mEditPemeriksaanKesehatanModelSync.getPerlakuan());
			json.put("nit", mEditPemeriksaanKesehatanModelSync.getNit());
			json.put("id_petugas", mEditPemeriksaanKesehatanModelSync.getId_petugas());
			json.put("tanggal_periksa", mEditPemeriksaanKesehatanModelSync.getTanggal_periksa());
			json.put("user", mEditPemeriksaanKesehatanModelSync.getUser());
			json.put("pass", mEditPemeriksaanKesehatanModelSync.getPass());
			json.put("guid", mEditPemeriksaanKesehatanModelSync.getGuid());
			json.put("transaksi", mContext.getResources().getString(R.string.edit_riwayat_kesehatan));
				
			String result =  HttpRequestHelper.doPost(mContext.getResources().getString(R.string.url_edit_riwayat_kesehatan), json);
			// {"message":"sukses"}
			
			//{"guid":"14ca9c53-0887-41dd-975c-5fda0dbef22c","id_petugas":0,"transaksi":"edit els_pemeriksaan_kesehatan","tanggal_periksa":"2014-09-07","nit":1,"diagnosa":"wow","kode_pemeriksaan_kesehatan":2,"user":"aho","perlakuan":"biasa aja","pass":"admin"}
			//{"kode_pemeriksaan_kesehatan":2,"message":"gagal edit data, mysql error message: ","status":0,"guid":"14ca9c53-0887-41dd-975c-5fda0dbef22c"}
			
			return result;
		} catch (JSONException e) {
			General.showDialogError(mContext, mContext.getResources() .getString(R.string.failed_edit_to_server) + ". Please Check Your Connection.", "Attention");
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
		super.onPostExecute(result);
		mDialog.dismiss();
		
		try {
			JSONObject jsonObject = new JSONObject(result);
			// {"message":"sukses"}
			String status = jsonObject.getString("message");

			if (status.equalsIgnoreCase("sukses")) {
				new AlertDialog.Builder(mContext)
						.setTitle("Success")
						.setMessage("Edit Success ")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// pop to previous fragment
										mFragmentActivity .getSupportFragmentManager().popBackStack();
									}
								}).create().show();
			} else {
				General.showDialogError(mContext, mContext.getResources()
						.getString(R.string.failed_edit_to_server)
						+ ". Please Check Your Connection.", "Attention");
			}
		} catch (JSONException e) {
			General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_edit_to_server)
					+ ". Please Check Your Connection.", "Attention");
			e.printStackTrace();
		}
	}
}
