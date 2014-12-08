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
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.sync.AddKematianModelSync;
import com.ui.webservice.HttpRequestHelper;

public class EditDataKematianSync extends AsyncTask<String, Void, String> {
	AddKematianModelSync mAddKematianModelSync;
	Context mContext;
	FragmentActivity mFragmentActivity;
	private ProgressDialog mDialog;
	HomeActivity mActivity;
	
	public EditDataKematianSync(HomeActivity homeActivity, FragmentActivity activity, Context context, AddKematianModelSync addKematianModelSync) {
		mFragmentActivity = activity;
		mActivity = homeActivity;
		mContext = context;
		mAddKematianModelSync = addKematianModelSync;
		mDialog = ProgressDialog.show(mContext, "", "Please wait...", true);
		mDialog.hide();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(mActivity != null){
//			mDialog = new ProgressDialog(mActivity);
//			mDialog.setIndeterminate(true);
//			mDialog.setMessage("Please wait...");
//			mDialog.show();
//			mDialog = ProgressDialog.show(mContext, "", "Please wait...", true);
			//mDialog.setCancelable(false);
			mDialog.show();
		}
	}
	@Override
	protected String doInBackground(String... params) {
		try {
			JSONObject json = new JSONObject();
			json.put("nit", mAddKematianModelSync.getNit());
			json.put("tanggal_kematian", mAddKematianModelSync.getTanggal_kematian());
			json.put("sebab_kematian", mAddKematianModelSync.getSebab_kematian());
			json.put("idl_kematian", mAddKematianModelSync.getIdl_kematian());
			json.put("id_petugas", mAddKematianModelSync.getId_petugas());
			json.put("user", mAddKematianModelSync.getUser());
			json.put("pass", mAddKematianModelSync.getPass());
			json.put("guid", mAddKematianModelSync.getGuid());
			json.put("transaksi",mContext.getResources().getString(R.string.edit_data_kematian));

			Log.d("json", json.toString());
			
			String result = HttpRequestHelper.doPost( mContext.getResources().getString( R.string.url_edit_data_kematian), json);
			//{"nit":"1","message":"sukses","status":1,"guid":"28fdad6d-9473-4167-baed-671c1122e752"}
			//{"nit":"1","message":"gagal insert data","status":0,"guid":"a1c21f01-9c9f-474f-9f6e-3f36b7bb9de1"}
			// {"message":"sukses"}
			
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		 super.onPostExecute(result);
		 
	        JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String status = jsonObject.getString("status");
				
				if(mDialog.isShowing() == true){
					 mDialog.dismiss();
				}
				
				if (status.equalsIgnoreCase("0")) {
					General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_edit_to_server)+ ". Please Check Your Connection.", "Attention");
				}else {
					new AlertDialog.Builder(mContext)
						.setTitle("Success")
						.setMessage("Edit Success ")
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).create().show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				if(mDialog.isShowing() == true){
					 mDialog.dismiss();
				}
				General.showDialogError(mContext, mContext.getResources().getString(R.string.failed_edit_to_server)+ ". Please Check Your Connection.", "Attention");
			}
			
	}
}
