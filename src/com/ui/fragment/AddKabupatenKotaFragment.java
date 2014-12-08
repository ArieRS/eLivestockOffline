package com.ui.fragment;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ui.asynctask.AddMasterKabupatenKotaSync;
import com.ui.asynctask.EditMasterKabupatenKotaSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.laporan.MasterKabupatenKotaModel;
import com.ui.model.sync.AddMasterKabupatenKotaModelSync;
import com.ui.model.sync.EditMasterKabupatenKotaModelSync;

public class AddKabupatenKotaFragment extends Fragment {
	Context mContext;
	Button mButtonSave, mButtonCancel;
	DatabaseHelper db;
	EditText edtNamaKabupatenKota;
	ActionBar actioncBar;
	Activity activity;
	Spinner spinnerProvinsi;
	SessionManager sessionManager;
	String mode;
	MasterKabupatenKotaModel mMasterKabupatenKotaModel;

	public AddKabupatenKotaFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(context);
		sessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddKabupatenKotaFragment(Context context,
			MasterKabupatenKotaModel masterKabupatenKotaModel) {
		mContext = context;
		db = new DatabaseHelper(context);
		sessionManager = new SessionManager(mContext);
		mMasterKabupatenKotaModel = masterKabupatenKotaModel;
		mode = "edit";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		// actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// layout
		View rootView = inflater.inflate(R.layout.add_data_kabupaten_kota,
				container, false);
		mButtonSave = (Button) rootView
				.findViewById(R.id.buttonSimpanKabupatenkota);
		mButtonCancel = (Button) rootView
				.findViewById(R.id.buttonBatalKabupatenKota);
		spinnerProvinsi = (Spinner) rootView
				.findViewById(R.id.spinNamaProvinsi);
		edtNamaKabupatenKota = (EditText) rootView
				.findViewById(R.id.edtNamaKabupatenKota);

		setLayout();
		return rootView;
	}

	private void setLayout() {
		ArrayList<String> arrayProvinsi = db.getAllNamaProvinsi();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, arrayProvinsi);
		arrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		arrayAdapter.notifyDataSetChanged();
		spinnerProvinsi.setAdapter(arrayAdapter);
		

		if (mode.equalsIgnoreCase("edit")) {
			spinnerProvinsi.setSelection(arrayAdapter.getPosition(mMasterKabupatenKotaModel.getNama_Provinsi()));
			edtNamaKabupatenKota.setText(mMasterKabupatenKotaModel.getNama_Kabupaten_Kota());
			
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String namaKabupatenKota = edtNamaKabupatenKota.getText()
							.toString();
					String selectedKabupatenKota = spinnerProvinsi
							.getSelectedItem().toString();
					int idProvinsi = db.getIdProvinsi(selectedKabupatenKota);

					EditMasterKabupatenKotaModelSync editMasterKabupatenKotaModelSync = new EditMasterKabupatenKotaModelSync();
					editMasterKabupatenKotaModelSync.setId_kabupaten_kota(mMasterKabupatenKotaModel.getId_kabupaten_kota());
					editMasterKabupatenKotaModelSync.setId_provinsi(idProvinsi);
					editMasterKabupatenKotaModelSync
							.setNama_kabupaten_kota(namaKabupatenKota);
					editMasterKabupatenKotaModelSync.setUser(sessionManager
							.getUserame());
					editMasterKabupatenKotaModelSync.setPass(sessionManager
							.getPassword());
					editMasterKabupatenKotaModelSync.setGuid(General
							.generateGuid());

					EditMasterKabupatenKotaSync mEditMasterKabupatenKotaSync = new EditMasterKabupatenKotaSync(
							getActivity(), mContext,
							editMasterKabupatenKotaModelSync);
					mEditMasterKabupatenKotaSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mEditMasterKabupatenKotaSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
//							String id = jsonObject.getString("id_kabupaten_kota");
							
							elsKabupatenKota mElsKabupatenKota = new elsKabupatenKota();
							mElsKabupatenKota.setId_kabupaten_kota(mMasterKabupatenKotaModel.getId_kabupaten_kota());
							mElsKabupatenKota.setId_provinsi(idProvinsi);
							mElsKabupatenKota.setNama_kabupaten_kota(namaKabupatenKota);
							db.updateKabupatenKota(mElsKabupatenKota);
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
		} else if (mode.equalsIgnoreCase("add")) {
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String namaKabupatenKota = edtNamaKabupatenKota.getText()
							.toString();
					String selectedKabupatenKota = spinnerProvinsi
							.getSelectedItem().toString();
					int idProvinsi = db.getIdProvinsi(selectedKabupatenKota);

					AddMasterKabupatenKotaModelSync addMasterKabupatenKotaModelSync = new AddMasterKabupatenKotaModelSync();
					addMasterKabupatenKotaModelSync.setId_provinsi(idProvinsi);
					addMasterKabupatenKotaModelSync
							.setNama_kabupaten_kota(namaKabupatenKota);
					addMasterKabupatenKotaModelSync.setUser(sessionManager
							.getUserame());
					addMasterKabupatenKotaModelSync.setPass(sessionManager
							.getPassword());
					addMasterKabupatenKotaModelSync.setGuid(General
							.generateGuid());

					AddMasterKabupatenKotaSync mAddMasterKabupatenKotaSync = new AddMasterKabupatenKotaSync(
							getActivity(), mContext,
							addMasterKabupatenKotaModelSync);
					mAddMasterKabupatenKotaSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mAddMasterKabupatenKotaSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							String id = jsonObject.getString("id_kabupaten_kota");
							elsKabupatenKota mElsKabupatenKota = new elsKabupatenKota();
							mElsKabupatenKota.setId_kabupaten_kota(Integer.parseInt(id));
							mElsKabupatenKota.setId_provinsi(idProvinsi);
							mElsKabupatenKota.setNama_kabupaten_kota(namaKabupatenKota);
							db.createKabupatenKotaWithID(mElsKabupatenKota);
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
				// pop to previous fragment
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			actioncBar.setDisplayHomeAsUpEnabled(false);
			//actioncBar.setHomeButtonEnabled(false);
			Fragment fragment = new MasterKabupatenKotaFragment(mContext);
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
