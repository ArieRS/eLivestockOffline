package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ui.asynctask.AddPemeriksaanKesehatanSync;
import com.ui.asynctask.EditPemeriksaanKesehatanSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsPemeriksaanKesehatan;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;
import com.ui.model.sync.AddRiwayatKesehatanModelSync;
import com.ui.model.sync.EditRiwayatKesehatanModelSync;

public class AddRiwayatKesehatan extends Fragment {
	Context mContext;
	int mNit;
	EditText edtTanggalPemeriksaan, edtDiagnosa, edtPerlakuan;
	Button buttonSimpan, buttonCancel;
	DatabaseHelper db;
	SessionManager mSessionManager;
	Search_RiwayatKesehatanModel mSearch_RiwayatKesehatanModel;
	String mode;
	HomeActivity mActivity;

	public AddRiwayatKesehatan(Context context, int nit) {
		mContext = context;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddRiwayatKesehatan(HomeActivity activity, Context context, int nit,
			Search_RiwayatKesehatanModel search_RiwayatKesehatanModel) {
		mContext = context;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mSearch_RiwayatKesehatanModel = search_RiwayatKesehatanModel;
		mode = "edit";
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_riwayat_kesehatan,
				container, false);
		setLayout(rootView);
		setButtonListener();
		return rootView;
	}

	private void setLayout(View rootView) {
		edtTanggalPemeriksaan = (EditText) rootView
				.findViewById(R.id.edtTanggalPemeriksaanRiwayatKesehatan);
		edtDiagnosa = (EditText) rootView
				.findViewById(R.id.edtDiagnosaRiwayatKesehatan);
		edtPerlakuan = (EditText) rootView
				.findViewById(R.id.edtPerlakuanRiwayatKesehatan);
		buttonSimpan = (Button) rootView
				.findViewById(R.id.buttonSimpanRiwayatKesehatan);
		buttonCancel = (Button) rootView
				.findViewById(R.id.buttonBatalRiwayatKesehatan);
		
		
		edtTanggalPemeriksaan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
	                DialogFragment datePickerFragment = new General.DatePickerFragment() {
	                    @Override
	                    public void onDateSet(DatePicker view, int year, int month, int day) {
	                        Calendar c = Calendar.getInstance();
	                        c.set(year, month, day);
	                        
	                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" );  
	                        String date = dateFormat.format(c.getTime());  // formatted date in string
	                        
	                        edtTanggalPemeriksaan.setText(date);
	                        edtDiagnosa.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		
		if (mode.equalsIgnoreCase("edit")) {
			edtTanggalPemeriksaan.setText(mSearch_RiwayatKesehatanModel.getTanggal_periksa());
			edtDiagnosa.setText(mSearch_RiwayatKesehatanModel.getDiagnosa());
			edtPerlakuan.setText(mSearch_RiwayatKesehatanModel.getPerlakuan());
		} 
	}

	private void setButtonListener() {

		if (mode.equalsIgnoreCase("edit")) {
			buttonSimpan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					EditRiwayatKesehatanModelSync editRiwayatKesehatanModelSync = new EditRiwayatKesehatanModelSync();
					editRiwayatKesehatanModelSync.setKode_periksaan_kesehatan(Integer.valueOf(mSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan()));
					editRiwayatKesehatanModelSync.setDiagnosa(edtDiagnosa
							.getText().toString());
					editRiwayatKesehatanModelSync.setPerlakuan(edtPerlakuan
							.getText().toString());
					editRiwayatKesehatanModelSync.setNit(mNit);
					editRiwayatKesehatanModelSync.setId_petugas(db
							.getIdPetugas(mSessionManager.getUserame()));
					editRiwayatKesehatanModelSync
							.setTanggal_periksa(edtTanggalPemeriksaan.getText()
									.toString());
					editRiwayatKesehatanModelSync.setUser(mSessionManager
							.getUserame());
					editRiwayatKesehatanModelSync.setPass(mSessionManager
							.getPassword());
					editRiwayatKesehatanModelSync.setGuid(General.generateGuid());

					EditPemeriksaanKesehatanSync mEditPemeriksaanKesehatanSync = new EditPemeriksaanKesehatanSync(
							mActivity, getActivity(), mContext,editRiwayatKesehatanModelSync);
					mEditPemeriksaanKesehatanSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mEditPemeriksaanKesehatanSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
//							String id = jsonObject.getString("kode_pemeriksaan_kesehatan");
							
							elsPemeriksaanKesehatan mElsPemeriksaanKesehatan = new elsPemeriksaanKesehatan();
							mElsPemeriksaanKesehatan.setKode_pemeriksaan_kesehatan(Integer.parseInt(mSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan()));
							mElsPemeriksaanKesehatan.setDiagnosa(edtDiagnosa.getText().toString());
							mElsPemeriksaanKesehatan.setPerlakuan(edtPerlakuan.getText().toString());
							mElsPemeriksaanKesehatan.setNit(mNit);
							mElsPemeriksaanKesehatan.setId_petugas(db.getIdPetugas(mSessionManager.getUserame()));
							mElsPemeriksaanKesehatan.setTanggalPeriksa(edtTanggalPemeriksaan.getText().toString());
							db.updatePemeriksaanKesehatan(mElsPemeriksaanKesehatan);
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
			buttonSimpan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					AddRiwayatKesehatanModelSync addRiwayatKesehatanModelSync = new AddRiwayatKesehatanModelSync();
					addRiwayatKesehatanModelSync.setDiagnosa(edtDiagnosa
							.getText().toString());
					addRiwayatKesehatanModelSync.setPerlakuan(edtPerlakuan
							.getText().toString());
					addRiwayatKesehatanModelSync.setNit(mNit);
					addRiwayatKesehatanModelSync.setId_petugas(db
							.getIdPetugas(mSessionManager.getUserame()));
					addRiwayatKesehatanModelSync
							.setTanggal_periksa(edtTanggalPemeriksaan.getText()
									.toString());
					addRiwayatKesehatanModelSync.setUser(mSessionManager
							.getUserame());
					addRiwayatKesehatanModelSync.setPass(mSessionManager
							.getPassword());
					addRiwayatKesehatanModelSync.setGuid(General.generateGuid());

					AddPemeriksaanKesehatanSync mAddPemeriksaanKesehatanSync = new AddPemeriksaanKesehatanSync(
							getActivity(), mContext,
							addRiwayatKesehatanModelSync);
					mAddPemeriksaanKesehatanSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mAddPemeriksaanKesehatanSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							String id = jsonObject.getString("kode_pemeriksaan_kesehatan");
							
							elsPemeriksaanKesehatan mElsPemeriksaanKesehatan = new elsPemeriksaanKesehatan();
							mElsPemeriksaanKesehatan.setKode_pemeriksaan_kesehatan(Integer.parseInt(id));
							mElsPemeriksaanKesehatan.setDiagnosa(edtDiagnosa.getText()
									.toString());
							mElsPemeriksaanKesehatan.setPerlakuan(edtPerlakuan
									.getText().toString());
							mElsPemeriksaanKesehatan.setNit(mNit);
							mElsPemeriksaanKesehatan.setId_petugas(db
									.getIdPetugas(mSessionManager.getUserame()));
							mElsPemeriksaanKesehatan
									.setTanggalPeriksa(edtTanggalPemeriksaan.getText()
											.toString());
							db.createPemeriksaanKesehatanWithID(mElsPemeriksaanKesehatan);
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
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// pop to previous fragment
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
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
