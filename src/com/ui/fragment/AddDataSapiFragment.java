package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ui.asynctask.AddDataSapiBetinaSync;
import com.ui.asynctask.EditDataSapiBetinaSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsSapiBetina;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.sync.AddDataSapiModelSync;
import com.ui.model.sync.EditDataSapiModelSync;

public class AddDataSapiFragment extends Fragment {
	Context mContext;
	EditText editTextNIT;
	Spinner spinnerLokasi;
	Spinner spinnerBangsa;
	EditText editTextTanggalLahir;
	Spinner spinnerNITInduk;
	EditText editTextBentukWajah;
	EditText editTextWarna;
	Spinner spinnerStatusPunuk;
	Spinner spinnerStatusAksesorisKaki;
	Spinner spinnerStatusKepemilikan;
	Button buttonSimpan, buttonBatal;
	SessionManager sessionManager;
	DatabaseHelper db;
	Data_SapiModel mData_SapiModel;
	String mode;
	
	public AddDataSapiFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddDataSapiFragment(Context context, Data_SapiModel data_SapiModel) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		mData_SapiModel = data_SapiModel;
		mode = "edit";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_data_sapi, container,
				false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		editTextNIT = (EditText) rootView.findViewById(R.id.edtNITEditDataSapi);
		spinnerLokasi = (Spinner) rootView.findViewById(R.id.spinLokasiEditDataSapi);
		spinnerBangsa = (Spinner) rootView.findViewById(R.id.spinBangsaEditDataSapi);
		editTextTanggalLahir = (EditText) rootView.findViewById(R.id.edtTanggalLahirEditDataSapi);
		spinnerNITInduk = (Spinner) rootView.findViewById(R.id.spinNITIndukEditDataSapi);
		editTextBentukWajah = (EditText) rootView.findViewById(R.id.edtBentukWajahEditDataSapi);
		editTextWarna = (EditText) rootView.findViewById(R.id.edtWarnaEditDataSapi);
		spinnerStatusPunuk = (Spinner) rootView.findViewById(R.id.spinStatusPunukEditDataSapi);
		spinnerStatusAksesorisKaki = (Spinner) rootView.findViewById(R.id.spinStatusAksesorisKakiEditDataSapi);
		spinnerStatusKepemilikan = (Spinner) rootView.findViewById(R.id.spinStatusKepemilikanEditDataSapi);
		buttonSimpan = (Button) rootView.findViewById(R.id.buttonSimpanData);
		buttonBatal = (Button) rootView.findViewById(R.id.buttonCancel);

		if (sessionManager.getPeran().equalsIgnoreCase("aho"))
		{}else if (sessionManager.getPeran().equalsIgnoreCase("ppt"))
		{}else if (sessionManager.getPeran().equalsIgnoreCase("ins"))
		{}else if (sessionManager.getPeran().equalsIgnoreCase("dinas"))
		{}else if (sessionManager.getPeran().equalsIgnoreCase("dprov"))	
		{}else if (sessionManager.getPeran().equalsIgnoreCase("dkota"))
		
		editTextTanggalLahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        editTextTanggalLahir.setText(date);
	                        editTextBentukWajah.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		
		populateSpinner();
		buttonClickListener();
	}

	private void populateSpinner() {
		List<String> labelsLokasi = db.getIdlKontakKota();
		ArrayAdapter<String> spinnerLokasiAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsLokasi);
		spinnerLokasiAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLokasiAdapter.notifyDataSetChanged();
		spinnerLokasi.setAdapter(spinnerLokasiAdapter);
		// /////////////////
		// ////////////////
		List<String> labelsBangsa = db.getIdValueBangsa();
		ArrayAdapter<String> spinnerBangsaAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsBangsa);
		spinnerBangsaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBangsaAdapter.notifyDataSetChanged();
		spinnerBangsa.setAdapter(spinnerBangsaAdapter);
		// ///////////////
		// //////////////

		List<String> labelsNitInduk = db.getNIT();
		ArrayAdapter<String> spinnerNITadapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsNitInduk);
		spinnerNITadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerNITInduk.setAdapter(spinnerNITadapter);
		// ///////////////
		// //////////////

		List<String> labelsStatusPunuk = new ArrayList<String>();
		labelsStatusPunuk.add("Ya");
		labelsStatusPunuk.add("Tidak");
		ArrayAdapter<String> spinnerStatusPunukAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				labelsStatusPunuk);
		spinnerStatusPunukAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusPunuk.setAdapter(spinnerStatusPunukAdapter);
		// ///////////////
		// //////////////
		List<String> labelsStatusAksesorisKaki = new ArrayList<String>();
		labelsStatusAksesorisKaki.add("Ya");
		labelsStatusAksesorisKaki.add("Tidak");
		ArrayAdapter<String> spinnerStatusAksesorisAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				labelsStatusAksesorisKaki);
		spinnerStatusAksesorisAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusAksesorisKaki.setAdapter(spinnerStatusAksesorisAdapter);
		// ///////////////
		// //////////////
		List<String> labelsStatusKepemilikan = new ArrayList<String>();
		labelsStatusKepemilikan.add("Pemerintah");
		labelsStatusKepemilikan.add("Pribadi");
		ArrayAdapter<String> spinnerStatusKepemilikanAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,
				labelsStatusKepemilikan);
		spinnerStatusKepemilikanAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusKepemilikan.setAdapter(spinnerStatusKepemilikanAdapter);

		if (mode.equalsIgnoreCase("edit")) {
			editTextNIT.setText(mData_SapiModel.getNIT());
			editTextNIT.setEnabled(false);
			editTextNIT.setFocusable(false);
			
			spinnerLokasi.setSelection(spinnerLokasiAdapter.getPosition(db
					.getIdlKontakKota(mData_SapiModel.getLokasi())));
			spinnerBangsa.setSelection(spinnerBangsaAdapter.getPosition(db
					.getIdValueBangsa(mData_SapiModel.getBangsa())));
			editTextTanggalLahir.setText(mData_SapiModel.getTanggalLahir());
			spinnerNITInduk.setSelection(spinnerNITadapter
					.getPosition(mData_SapiModel.getNITInduk()));
			editTextBentukWajah.setText(mData_SapiModel.getBentukWajah());
			editTextWarna.setText(mData_SapiModel.getWarna());
			spinnerStatusPunuk.setSelection(spinnerStatusPunukAdapter
					.getPosition(mData_SapiModel.getStatus_punuk()));
			spinnerStatusAksesorisKaki.setSelection(spinnerStatusPunukAdapter
					.getPosition(mData_SapiModel.getStatus_aksesoris_kaki()));
			spinnerStatusKepemilikan.setSelection(spinnerStatusPunukAdapter
					.getPosition(mData_SapiModel.getStatus_kepemilikan()));
		}
	}

	private void buttonClickListener() {
		if (mode.equalsIgnoreCase("edit")) {
			buttonSimpan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					String nit = editTextNIT.getText().toString();
					String[] selectedLokasi = spinnerLokasi.getSelectedItem()
							.toString().split(" \\| ");
					String[] bangsa = spinnerBangsa.getSelectedItem()
							.toString().split(" \\| ");
					String tanggalLahir = editTextTanggalLahir.getText()
							.toString();
					String nitInduk = spinnerNITInduk.getSelectedItem()
							.toString();
					String bentukWajah = editTextBentukWajah.getText()
							.toString();
					String warna = editTextWarna.getText().toString();

					int statusPunuk = 0;// 0 tidak
					if (spinnerStatusPunuk.getSelectedItem().toString()
							.equalsIgnoreCase("Ya"))
						statusPunuk = 1;

					int statusAksesorisSapi = 0;// 0 tidak
					if (spinnerStatusAksesorisKaki.getSelectedItem().toString()
							.equalsIgnoreCase("Ya"))
						statusAksesorisSapi = 1;

					int statusKepemilikan = 0;// 0 pribadi
					if (spinnerStatusKepemilikan.getSelectedItem().toString()
							.equalsIgnoreCase("pemerintah"))
						statusKepemilikan = 1;

					EditDataSapiModelSync editDataSapiModelSync = new EditDataSapiModelSync();
					editDataSapiModelSync.setNit(Integer.parseInt(nit));
					editDataSapiModelSync.setIdl(Integer
							.parseInt(selectedLokasi[0]));
					editDataSapiModelSync.setTanggalLahir(tanggalLahir);
					editDataSapiModelSync.setBangsa(bangsa[0]);
					editDataSapiModelSync.setNit_induk(Integer
							.parseInt(nitInduk));
					editDataSapiModelSync.setBentuk_wajah(bentukWajah);
					editDataSapiModelSync.setWarna(warna);
					editDataSapiModelSync.setStatus_punuk(statusPunuk);
					editDataSapiModelSync
							.setStatus_aksesoris_kaki(statusAksesorisSapi);
					editDataSapiModelSync
							.setStatus_kepemilikan(statusKepemilikan);
					editDataSapiModelSync.setUser(sessionManager.getUserame());
					editDataSapiModelSync.setPass(sessionManager.getPassword());
					editDataSapiModelSync.setGuid(General.generateGuid());

					EditDataSapiBetinaSync editDataSapiBetinaSync = new EditDataSapiBetinaSync(
							getActivity(), mContext, editDataSapiModelSync);
					editDataSapiBetinaSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = editDataSapiBetinaSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							elsSapiBetina mElsSapiBetina = new elsSapiBetina();
							mElsSapiBetina.setNit(Integer.parseInt(nit));
							mElsSapiBetina.setIdl(Integer.parseInt(selectedLokasi[0]));
							mElsSapiBetina.setTanggal_lahir(tanggalLahir);
							mElsSapiBetina.setBangsa(bangsa[0]);
							mElsSapiBetina.setNit_induk(Integer.parseInt(nitInduk));
							mElsSapiBetina.setBentuk_wajah(bentukWajah);
							mElsSapiBetina.setWarna(warna);
							mElsSapiBetina.setStatus_punuk(statusPunuk);
							mElsSapiBetina.setStatus_aksesoris_kaki(statusAksesorisSapi);
							mElsSapiBetina.setStatus_kepemilikan(statusKepemilikan);
							db.updateDataSapiBetinaWithID(mElsSapiBetina);
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

					String nit = editTextNIT.getText().toString();
					String[] selectedLokasi = spinnerLokasi.getSelectedItem()
							.toString().split(" \\| ");
					String[] bangsa = spinnerBangsa.getSelectedItem()
							.toString().split(" \\| ");
					String tanggalLahir = editTextTanggalLahir.getText()
							.toString();
					String nitInduk = spinnerNITInduk.getSelectedItem()
							.toString();
					String bentukWajah = editTextBentukWajah.getText()
							.toString();
					String warna = editTextWarna.getText().toString();

					int statusPunuk = 0;// 0 tidak
					if (spinnerStatusPunuk.getSelectedItem().toString()
							.equalsIgnoreCase("Ya"))
						statusPunuk = 1;

					int statusAksesorisSapi = 0;// 0 tidak
					if (spinnerStatusAksesorisKaki.getSelectedItem().toString()
							.equalsIgnoreCase("Ya"))
						statusAksesorisSapi = 1;

					int statusKepemilikan = 0;// 0 pribadi
					if (spinnerStatusKepemilikan.getSelectedItem().toString()
							.equalsIgnoreCase("pemerintah"))
						statusKepemilikan = 1;

					AddDataSapiModelSync addDataSapiModelSync = new AddDataSapiModelSync();
					addDataSapiModelSync.setNit(Integer.parseInt(nit));
					addDataSapiModelSync.setIdl(Integer.parseInt(selectedLokasi[0]));
					addDataSapiModelSync.setTanggalLahir(tanggalLahir);
					addDataSapiModelSync.setBangsa(bangsa[0]);
					addDataSapiModelSync.setNit_induk(Integer.parseInt(nitInduk));
					addDataSapiModelSync.setBentuk_wajah(bentukWajah);
					addDataSapiModelSync.setWarna(warna);
					addDataSapiModelSync.setStatus_punuk(statusPunuk);
					addDataSapiModelSync.setStatus_aksesoris_kaki(statusAksesorisSapi);
					addDataSapiModelSync.setStatus_kepemilikan(statusKepemilikan);
					addDataSapiModelSync.setUser(sessionManager.getUserame());
					addDataSapiModelSync.setPass(sessionManager.getPassword());
					addDataSapiModelSync.setGuid(General.generateGuid());

					AddDataSapiBetinaSync addDataSapiBetinaSync = new AddDataSapiBetinaSync(
							getActivity(), mContext, addDataSapiModelSync);
					addDataSapiBetinaSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = addDataSapiBetinaSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							elsSapiBetina mElsSapiBetina = new elsSapiBetina();
							mElsSapiBetina.setNit(Integer.parseInt(nit));
							mElsSapiBetina.setIdl(Integer.parseInt(selectedLokasi[0]));
							mElsSapiBetina.setTanggal_lahir(tanggalLahir);
							mElsSapiBetina.setBangsa(bangsa[0]);
							mElsSapiBetina.setNit_induk(Integer.parseInt(nitInduk));
							mElsSapiBetina.setBentuk_wajah(bentukWajah);
							mElsSapiBetina.setWarna(warna);
							mElsSapiBetina.setStatus_punuk(statusPunuk);
							mElsSapiBetina.setStatus_aksesoris_kaki(statusAksesorisSapi);
							mElsSapiBetina.setStatus_kepemilikan(statusKepemilikan);
							db.createDataSapiBetinaWithID(mElsSapiBetina);
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

		buttonBatal.setOnClickListener(new View.OnClickListener() {
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
