package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ui.common.General;
import com.ui.common.Validation;
import com.ui.elivestock.R;
import com.ui.model.database.masterSebabKematian;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_DataSapiModel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class DataSapiFragment_Tab extends Fragment {
	Context mContext;
	ArrayList<Search_DataSapiModel> arraySearch_DataSapiModels;
	EditText editTextNIT;
	Spinner spinnerLokasi;
	Spinner spinnerBangsa;
	EditText editTextTanggalLahir;
	EditText editTextNITInduk;
	EditText editTextBentukWajah;
	EditText editTextWarna;
	Spinner spinnerStatusPunuk;
	Spinner spinnerStatusAksesorisKaki;
	Spinner spinnerStatusKepemilikan;

	public DataSapiFragment_Tab(Context context,
			ArrayList<Search_DataSapiModel> arrayListDataSapi) {
		mContext = context;
		arraySearch_DataSapiModels = arrayListDataSapi;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rooView = inflater.inflate(R.layout.tab_data_sapi, container,
				false);
		setLayout(rooView);

		return rooView;
	}

	private void setLayout(View rooView) {
		editTextNIT = (EditText) rooView.findViewById(R.id.edtNITEditDataSapi);
		spinnerLokasi = (Spinner) rooView
				.findViewById(R.id.spinLokasiEditDataSapi);
		spinnerBangsa = (Spinner) rooView
				.findViewById(R.id.spinBangsaEditDataSapi);
		editTextTanggalLahir = (EditText) rooView
				.findViewById(R.id.edtTanggalLahirEditDataSapi);
		editTextNITInduk = (EditText) rooView
				.findViewById(R.id.edtNITIndukEditDataSapi);
		editTextBentukWajah = (EditText) rooView
				.findViewById(R.id.edtBentukWajahEditDataSapi);
		editTextWarna = (EditText) rooView
				.findViewById(R.id.edtWarnaEditDataSapi);
		spinnerStatusPunuk = (Spinner) rooView
				.findViewById(R.id.spinStatusPunukEditDataSapi);
		spinnerStatusAksesorisKaki = (Spinner) rooView
				.findViewById(R.id.spinStatusAksesorisKakiEditDataSapi);
		spinnerStatusKepemilikan = (Spinner) rooView
				.findViewById(R.id.spinStatusKepemilikanEditDataSapi);

		Search_DataSapiModel mSearch_DataSapiModel = new Search_DataSapiModel();

		if (arraySearch_DataSapiModels.size() > 0)
			mSearch_DataSapiModel = arraySearch_DataSapiModels.get(0);

		editTextNIT.setText(Validation.ifStringNull(mSearch_DataSapiModel.getNIT()));
		editTextNIT.setEnabled(false);
		editTextNIT.setFocusable(false);
		
		populateSpinner(mSearch_DataSapiModel);
		editTextTanggalLahir.setText(Validation.ifStringNull(mSearch_DataSapiModel.getTanggalLahir()));
		editTextTanggalLahir.setEnabled(false);
		editTextTanggalLahir.setFocusable(false);
		
		editTextNITInduk.setText(Validation.ifStringNull(mSearch_DataSapiModel.getNITInduk()));
		editTextNITInduk.setEnabled(false);
		editTextNITInduk.setFocusable(false);
		
		editTextBentukWajah.setText(Validation.ifStringNull(mSearch_DataSapiModel.getBentukWajah()));
		editTextBentukWajah.setEnabled(false);
		editTextBentukWajah.setFocusable(false);
		
		editTextWarna.setText(Validation.ifStringNull(mSearch_DataSapiModel.getWarna()));
		editTextWarna.setEnabled(false);
		editTextWarna.setFocusable(false);
	}
	private void populateSpinner(Search_DataSapiModel search_DataSapiModel){
		List<String> labelsLokasi = new ArrayList<String>();
		labelsLokasi.add(Validation.ifStringNull(search_DataSapiModel.getLokasi()));
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsLokasi);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLokasi.setAdapter(spinnerAdapter);
		spinnerLokasi.setEnabled(false);
		spinnerLokasi.setFocusable(false);
		///////////////////
		//////////////////
		List<String> labelsBangsa = new ArrayList<String>();
		labelsBangsa.add(Validation.ifStringNull(search_DataSapiModel.getBangsa()));
		ArrayAdapter<String> spinnerBangsaAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsBangsa);
		spinnerBangsaAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBangsa.setAdapter(spinnerBangsaAdapter);
		spinnerBangsa.setEnabled(false);
		spinnerBangsa.setFocusable(false);
		/////////////////
		////////////////
		List<String> labelsStatusPunuk = new ArrayList<String>();
		labelsStatusPunuk.add(Validation.ifStringNull(search_DataSapiModel.getStatusPunuk()));
		ArrayAdapter<String> spinnerStatusPunukAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsStatusPunuk);
		spinnerStatusPunukAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusPunuk.setAdapter(spinnerStatusPunukAdapter);
		spinnerStatusPunuk.setEnabled(false);
		spinnerStatusPunuk.setFocusable(false);
		/////////////////
		////////////////
		List<String> labelsStatusAksesorisKaki = new ArrayList<String>();
		labelsStatusAksesorisKaki.add(Validation.ifStringNull(search_DataSapiModel.getStatusAksesorisKaki()));
		ArrayAdapter<String> spinnerStatusAksesorisAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsStatusAksesorisKaki);
		spinnerStatusAksesorisAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusAksesorisKaki.setAdapter(spinnerStatusAksesorisAdapter);
		spinnerStatusAksesorisKaki.setEnabled(false);
		spinnerStatusAksesorisKaki.setFocusable(false);
		/////////////////
		////////////////
		List<String> labelsStatusKepemilikan = new ArrayList<String>();
		labelsStatusKepemilikan.add(Validation.ifStringNull(search_DataSapiModel.getStatusKepimilikan()));
		ArrayAdapter<String> spinnerStatusKepemilikanAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsStatusKepemilikan);
		spinnerStatusKepemilikanAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStatusKepemilikan.setAdapter(spinnerStatusKepemilikanAdapter);
		spinnerStatusKepemilikan.setEnabled(false);
		spinnerStatusKepemilikan.setFocusable(false);
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
