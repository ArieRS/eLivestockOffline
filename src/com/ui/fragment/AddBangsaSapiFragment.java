package com.ui.fragment;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ui.asynctask.AddMasterBangsaSapiSync;
import com.ui.asynctask.EditMasterBangsaSapiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.masterBangsaSapi;
import com.ui.model.sync.AddMasterBangsaModelSync;
import com.ui.model.sync.EditMasterBangsaModelSync;

public class AddBangsaSapiFragment extends Fragment{
	Context mContext;
	Button mButtonSave, mButtonCancel;
	DatabaseHelper db;
	EditText edtNamaBangsa;
	ActionBar actioncBar;
	Activity activity;
	SessionManager sessionManager;
	String mode;
	ProgressBar progress;
	
	masterBangsaSapi mMasterBangsaSapi;
	
	public AddBangsaSapiFragment(Context mContext){
		this.mContext = mContext;
		db = new DatabaseHelper(this.mContext);
		sessionManager = new SessionManager(mContext);
		mode = "add";
	}
	public AddBangsaSapiFragment(Context mContext, masterBangsaSapi mMasterBangsaSapi){
		this.mContext = mContext;
		db = new DatabaseHelper(this.mContext);
		sessionManager = new SessionManager(mContext);
		this.mMasterBangsaSapi = mMasterBangsaSapi;
		mode = "edit";
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
//		actioncBar = activity.getActionBar();
//		actioncBar.setHomeButtonEnabled(true);
//		actioncBar.setDisplayHomeAsUpEnabled(true);
//		setHasOptionsMenu(true);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			//layout
			View rootView = inflater.inflate(R.layout.add_data_bangsa_sapi, container,
						false);
			mButtonSave = (Button) rootView.findViewById(R.id.buttonSimpanBangsa);
			mButtonCancel = (Button) rootView.findViewById(R.id.buttonBatalBangsa);
			edtNamaBangsa = (EditText) rootView.findViewById(R.id.edtNamaBangsa);
			progress = (ProgressBar) rootView.findViewById(R.id.progressBar1);
			
			if (mode.equalsIgnoreCase("edit")){
				edtNamaBangsa.setText(mMasterBangsaSapi.getValue());
				mButtonSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						progress.setVisibility(View.VISIBLE);
						
						
						//sync to server
						EditMasterBangsaModelSync editMasterBangsaSapiModel = new EditMasterBangsaModelSync();
						editMasterBangsaSapiModel.setId(mMasterBangsaSapi.getId());
						editMasterBangsaSapiModel.setValue(edtNamaBangsa.getText().toString());
						editMasterBangsaSapiModel.setUser(sessionManager.getUserame());
						editMasterBangsaSapiModel.setPass(sessionManager.getPassword());
						editMasterBangsaSapiModel.setGuid(General.generateGuid());
						
						EditMasterBangsaSapiSync editMasterBangsaSapiSync = new EditMasterBangsaSapiSync(getActivity(), mContext, editMasterBangsaSapiModel);
						editMasterBangsaSapiSync.execute();
						
						
						try {
							String result = editMasterBangsaSapiSync.get().toString();
							JSONObject jsonObject = new JSONObject(result);
							String status = jsonObject.getString("message");

							if (status.equalsIgnoreCase("sukses")) {
//								String id = jsonObject.getString("id");
								
								masterBangsaSapi bangsaSapiModel = new masterBangsaSapi();
								bangsaSapiModel.setId(mMasterBangsaSapi.getId());
								bangsaSapiModel.setValue(edtNamaBangsa.getText().toString());
								db.updateBangsaSapi(bangsaSapiModel);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				});
			}else if (mode.equalsIgnoreCase("add")){
				mButtonSave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						progress.setVisibility(View.VISIBLE);
						//sync to server
						AddMasterBangsaModelSync addMasterBangsaSapiModel = new AddMasterBangsaModelSync();
						addMasterBangsaSapiModel.setValue(edtNamaBangsa.getText().toString());
						addMasterBangsaSapiModel.setUser(sessionManager.getUserame());
						addMasterBangsaSapiModel.setPass(sessionManager.getPassword());
						addMasterBangsaSapiModel.setGuid(General.generateGuid());
						
						
						AddMasterBangsaSapiSync addMasterBangsaSapiSync = new AddMasterBangsaSapiSync(getActivity(), mContext, addMasterBangsaSapiModel);
						addMasterBangsaSapiSync.execute();
						try {
							String result = addMasterBangsaSapiSync.get().toString();
							JSONObject jsonObject = new JSONObject(result);
							 
							// {"message":"sukses"}
							String status = jsonObject.getString("message");

							if (status.equalsIgnoreCase("sukses")) {
								String id = jsonObject.getString("id");
								masterBangsaSapi bangsaSapiModel = new masterBangsaSapi();
								bangsaSapiModel.setId(Integer.parseInt(id));
								bangsaSapiModel.setValue(edtNamaBangsa.getText().toString());
								db.createBangsaSapiWithID(bangsaSapiModel);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}catch (JSONException e) {
							e.printStackTrace();
						}
						progress.setVisibility(View.INVISIBLE);
					}
				});
			}
			
			mButtonCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//pop to previous fragment
					getActivity().getSupportFragmentManager().popBackStack();
				}
			});
			
			return rootView;
	}
	@Override
	public void setInitialSavedState(SavedState state) {
		super.setInitialSavedState(state);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
////			actioncBar.setDisplayHomeAsUpEnabled(false);
////			actioncBar.setHomeButtonEnabled(false);
//			Fragment fragment = new MasterBangsaSapiFragment(mContext);
//			General.replaceFragment(fragment, getFragmentManager());
//			break;
//		default:
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
