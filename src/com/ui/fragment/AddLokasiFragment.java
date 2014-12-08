package com.ui.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ui.asynctask.AddMasterLokasiSync;
import com.ui.asynctask.EditMasterLokasiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsLokasi;
import com.ui.model.laporan.LaporanLokasiSapiModel;
import com.ui.model.sync.AddMasterLokasiModelSync;
import com.ui.model.sync.EditMasterLokasiModelSync;

public class AddLokasiFragment extends Fragment {
	Context mContext;
	EditText editTextNamaKontak, editTextAddress, editTextTelpn;
	Spinner spinKabupatenKota, spinType, spinPetugas;
	Button buttonSave, buttonCancel;
	DatabaseHelper databaseHelper;
	Activity activity;
	SessionManager sessionManager;
	LaporanLokasiSapiModel mLaporanLokasiSapiModel;
	String mode;

	public AddLokasiFragment(Context context) {
		mContext = context;
		activity = (Activity) mContext;
		databaseHelper = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddLokasiFragment(Context context,
			LaporanLokasiSapiModel laporanLokasiSapiModel) {
		mContext = context;
		activity = (Activity) mContext;
		databaseHelper = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mLaporanLokasiSapiModel = laporanLokasiSapiModel;
		mode = "edit";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_data_lokasi, container,
				false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		editTextNamaKontak = (EditText) rootView
				.findViewById(R.id.edtNamaKontakLokasiSapi);
		editTextAddress = (EditText) rootView
				.findViewById(R.id.edtAlamatLokasiSapi);
		editTextTelpn = (EditText) rootView
				.findViewById(R.id.edtNoTeleponLokasiSapi);
		spinKabupatenKota = (Spinner) rootView
				.findViewById(R.id.spinKabupatenKotaLokasiSapi);
		spinType = (Spinner) rootView.findViewById(R.id.spinTypeLokasiSapi);
		spinPetugas = (Spinner) rootView
				.findViewById(R.id.spinPetugasLokasiSapi);
		buttonSave = (Button) rootView.findViewById(R.id.buttonSimpanLokasi);
		buttonCancel = (Button) rootView.findViewById(R.id.buttonBatalLokasi);

		populateSpinner();
		setButtonOnClick();
	}

	private void populateSpinner() {
		ArrayAdapter<String> adapterKabupatenKota = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				databaseHelper.getAllNameKabupatenKota());
		adapterKabupatenKota
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinKabupatenKota.setAdapter(adapterKabupatenKota);

		ArrayAdapter<String> adapterType = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item,
				databaseHelper.getTypeLokasi());
		adapterType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinType.setAdapter(adapterType);

		ArrayList<String> arrayPetugas = databaseHelper.getNamaPetugas();
		ArrayAdapter<String> adapterPetugas = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				arrayPetugas);
		adapterPetugas
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinPetugas.setAdapter(adapterPetugas);
		
		if (mode.equalsIgnoreCase("edit")) {
			editTextNamaKontak.setText(mLaporanLokasiSapiModel.getNamaKontak());
			spinKabupatenKota.setSelection(adapterKabupatenKota.getPosition(mLaporanLokasiSapiModel.getKabupatenKota()));
			editTextAddress.setText(mLaporanLokasiSapiModel.getAlamat());
			editTextTelpn.setText(mLaporanLokasiSapiModel.getNoTelepon());
			spinType.setSelection(adapterType.getPosition(mLaporanLokasiSapiModel.getType()));
			spinPetugas.setSelection(adapterPetugas.getPosition(String.valueOf(databaseHelper.getIdPetugas(mLaporanLokasiSapiModel.getPetugas()))+" | "+mLaporanLokasiSapiModel.getPetugas()));
		}
	}

	private void setButtonOnClick() {
		if (mode.equalsIgnoreCase("edit")) {
			buttonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String namaKontak = editTextNamaKontak.getText().toString();
					String alamat = editTextAddress.getText().toString();
					String noTelepon = editTextTelpn.getText().toString();
					String kabupatenKota = spinKabupatenKota.getSelectedItem()
							.toString();
					String type = spinType.getSelectedItem().toString();
					String[] id_namaPetugas = spinPetugas.getSelectedItem()
							.toString().split(" \\| ");

					EditMasterLokasiModelSync mEditMasterLokasiModelSync = new EditMasterLokasiModelSync();
					mEditMasterLokasiModelSync.setIdl(Integer
							.parseInt(mLaporanLokasiSapiModel.getIdLokasi()));
					mEditMasterLokasiModelSync
							.setId_kabupaten_kota(databaseHelper
									.getIdKabupatenKota(kabupatenKota));
					mEditMasterLokasiModelSync.setNama_kontak(namaKontak);
					mEditMasterLokasiModelSync.setAddress(alamat);
					mEditMasterLokasiModelSync.setNo_telepon(noTelepon);
					mEditMasterLokasiModelSync.setType(databaseHelper
							.getIdTypeLokasi(type));
					mEditMasterLokasiModelSync.setId_petugas(Integer
							.parseInt(id_namaPetugas[0]));
					mEditMasterLokasiModelSync.setUser(sessionManager
							.getUserame());
					mEditMasterLokasiModelSync.setPass(sessionManager
							.getPassword());
					mEditMasterLokasiModelSync.setGuid(General.generateGuid());

					//sync
					EditMasterLokasiSync editMasterLokasiSync = new EditMasterLokasiSync(
							getActivity(), mContext, mEditMasterLokasiModelSync);
					editMasterLokasiSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = editMasterLokasiSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
//							String id = jsonObject.getString("id_kabupaten_kota");
							
							elsLokasi mElsLokasi = new elsLokasi();
							mElsLokasi.setIdl(Integer.parseInt(mLaporanLokasiSapiModel.getIdLokasi()));
							mElsLokasi.setId_kabupaten_kota(databaseHelper.getIdKabupatenKota(kabupatenKota));
							mElsLokasi.setNama_kontak(namaKontak);
							mElsLokasi.setAddress(alamat);
							mElsLokasi.setNo_telepon(noTelepon);
							mElsLokasi.setType(String.valueOf(databaseHelper.getIdTypeLokasi(type)));
							mElsLokasi.setId_petugas(Integer.parseInt(id_namaPetugas[0]));
							databaseHelper.updateLokasi(mElsLokasi);
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
			buttonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String namaKontak = editTextNamaKontak.getText().toString();
					String alamat = editTextAddress.getText().toString();
					String noTelepon = editTextTelpn.getText().toString();
					String kabupatenKota = spinKabupatenKota.getSelectedItem()
							.toString();
					String type = spinType.getSelectedItem().toString();
					String[] id_namaPetugas = spinPetugas.getSelectedItem()
							.toString().split(" \\| ");

					AddMasterLokasiModelSync addMasterLokasiModelSync = new AddMasterLokasiModelSync();
					addMasterLokasiModelSync
							.setId_kabupaten_kota(databaseHelper
									.getIdKabupatenKota(kabupatenKota));
					addMasterLokasiModelSync.setNama_kontak(namaKontak);
					addMasterLokasiModelSync.setAddress(alamat);
					addMasterLokasiModelSync.setNo_telepon(noTelepon);
					addMasterLokasiModelSync.setType(databaseHelper
							.getIdTypeLokasi(type));
					addMasterLokasiModelSync.setId_petugas(Integer
							.parseInt(id_namaPetugas[0]));
					addMasterLokasiModelSync.setUser(sessionManager
							.getUserame());
					addMasterLokasiModelSync.setPass(sessionManager
							.getPassword());
					addMasterLokasiModelSync.setGuid(General.generateGuid());

					AddMasterLokasiSync addMasterLokasiSync = new AddMasterLokasiSync(
							getActivity(), mContext, addMasterLokasiModelSync);
					addMasterLokasiSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = addMasterLokasiSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							String id = jsonObject.getString("idl");
							
							elsLokasi mElsLokasi = new elsLokasi();
							mElsLokasi.setIdl(Integer.parseInt(id));
							mElsLokasi.setId_kabupaten_kota(databaseHelper
									.getIdKabupatenKota(kabupatenKota));
							mElsLokasi.setNama_kontak(namaKontak);
							mElsLokasi.setAddress(alamat);
							mElsLokasi.setNo_telepon(noTelepon);
							mElsLokasi.setType(String.valueOf(databaseHelper
									.getIdTypeLokasi(type)));
							mElsLokasi.setId_petugas(Integer.parseInt(id_namaPetugas[0]));
							databaseHelper.createLokasiWithID(mElsLokasi);
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
