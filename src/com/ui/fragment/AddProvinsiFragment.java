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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ui.asynctask.AddMasterProvinsiSync;
import com.ui.asynctask.EditMasterProvinsiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsProvinsi;
import com.ui.model.sync.AddMasterProvinsiModelSync;
import com.ui.model.sync.EditMasterProvinsiModelSync;

public class AddProvinsiFragment extends Fragment {
	Context mContext;
	Button mButtonSave, mButtonCancel;
	DatabaseHelper db;
	EditText edtNamaProvinisi;
	ActionBar actioncBar;
	Activity activity;
	SessionManager sessionManager;
	String mode;
	elsProvinsi mElsProvinsi;

	public AddProvinsiFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddProvinsiFragment(Context context, elsProvinsi elsProvinsi) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mElsProvinsi = elsProvinsi;
		mode = "edit";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity = (Activity) mContext;
		// actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// layout
		View rootView = inflater.inflate(R.layout.add_data_provinsi, container,
				false);
		mButtonSave = (Button) rootView.findViewById(R.id.buttonSimpanProvinsi);
		mButtonCancel = (Button) rootView
				.findViewById(R.id.buttonBatalProvinsi);
		edtNamaProvinisi = (EditText) rootView
				.findViewById(R.id.edtNamaProvinsi);

		if (mode.equalsIgnoreCase("edit")){
			edtNamaProvinisi.setText(mElsProvinsi.getNama_provinsi());
			
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					// sync to server
					EditMasterProvinsiModelSync editMasterProvinsiModelSync = new EditMasterProvinsiModelSync();
					editMasterProvinsiModelSync.setId_provinsi(mElsProvinsi.getId_provinsi());
					editMasterProvinsiModelSync.setNama_provinsi(edtNamaProvinisi
							.getText().toString());
					editMasterProvinsiModelSync.setUser(sessionManager.getUserame());
					editMasterProvinsiModelSync.setPass(sessionManager.getPassword());
					editMasterProvinsiModelSync.setGuid(General.generateGuid());

					EditMasterProvinsiSync editMasterProvinsiSync = new EditMasterProvinsiSync(
							getActivity(), mContext, editMasterProvinsiModelSync);
					editMasterProvinsiSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = editMasterProvinsiSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
//							String id = jsonObject.getString("id_provinsi");
							
							elsProvinsi provinsiModel = new elsProvinsi();
							provinsiModel.setId_provinsi(mElsProvinsi.getId_provinsi());
							provinsiModel.setNama_provinsi(edtNamaProvinisi.getText()
									.toString());
							db.updateElsProvinsi(provinsiModel);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			});
		}
		else if (mode.equalsIgnoreCase("add")){
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					// sync to server
					AddMasterProvinsiModelSync addMasterProvinsiModel = new AddMasterProvinsiModelSync();
					addMasterProvinsiModel.setNama_provinsi(edtNamaProvinisi
							.getText().toString());
					addMasterProvinsiModel.setUser(sessionManager.getUserame());
					addMasterProvinsiModel.setPass(sessionManager.getPassword());
					addMasterProvinsiModel.setGuid(General.generateGuid());

					AddMasterProvinsiSync addMasterProvinsiSync = new AddMasterProvinsiSync(
							getActivity(), mContext, addMasterProvinsiModel);
					addMasterProvinsiSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = addMasterProvinsiSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							String id = jsonObject.getString("id_provinsi");
							
							elsProvinsi provinsiModel = new elsProvinsi();
							provinsiModel.setId_provinsi(Integer.parseInt(id));
							provinsiModel.setNama_provinsi(edtNamaProvinisi.getText()
									.toString());
							db.createElsProvinsiWithID(provinsiModel);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			actioncBar.setDisplayHomeAsUpEnabled(false);
			//actioncBar.setHomeButtonEnabled(false);
			Fragment fragment = new MasterProvinsiFragment(mContext);
			General.replaceFragment(fragment, getFragmentManager());
			break;
		default:
			break;
		}
		Log.d("backActionBar", "Back");
		return super.onOptionsItemSelected(item);
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
}
