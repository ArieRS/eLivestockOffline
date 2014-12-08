package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ui.asynctask.AddKejadianBeranakSync;
import com.ui.asynctask.EditKejadianBeranakSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKejadianBeranak;
import com.ui.model.laporan.Search_KejadianBeranakModel;
import com.ui.model.sync.AddDataSapiModelSync;
import com.ui.model.sync.AddKejadianBeranakModelSync;
import com.ui.model.sync.EditKejadianBeranakModelSync;

public class AddKejadianBeranakFagment extends Fragment {
	Context mContext;
	Button saveAddData, cancelAddData;
	int mNit;
	SessionManager mSessionManager;
	EditText tanggalBeranak, banyakAnakJantan, banyakAnakBetina;
	DatabaseHelper db;
	Search_KejadianBeranakModel mSearch_KejadianBeranakModel;
	String mode = "";
	
	LinearLayout linSapike1, linSapike2;
	EditText edtNitSapike1, edtTanggalLahirSapike1, edtBentukWajahSapike1, edtWarnaSapike1;
	Spinner spinLokasiSapike1, spinBangsaSapike1,spinNitIndukSapike1, spinStatusPunukSapiKe1, spinStatusAksesorisSapike1,spinStatusKepemilikanSapike1;
	
	EditText edtNitSapike2, edtTanggalLahirSapike2, edtBentukWajahSapike2, edtWarnaSapike2;
	Spinner spinLokasiSapike2, spinBangsaSapike2, spinNitIndukSapike2,spinStatusPunukSapiKe2, spinStatusAksesorisSapike2, spinStatusKepemilikanSapike2;
	TextView tvTanggalBeranakKejadianBeranak, tvTanggalLahirDataSapiBetinaKe1, tvTanggalLahirDataSapiBetinaKe2;

	boolean viewlinSapike1 = false, viewlinSapike2 = false;
	
	public AddKejadianBeranakFagment(Context context, int nit) {
		mContext = context;
		mNit = nit;
		mSessionManager = new SessionManager(mContext);
		db = new DatabaseHelper(mContext);
		mode = "add";
	}

	public AddKejadianBeranakFagment(Context context, int nit,
			Search_KejadianBeranakModel search_KejadianBeranakModel) {
		mContext = context;
		mNit = nit;
		mSessionManager = new SessionManager(mContext);
		db = new DatabaseHelper(mContext);
		mSearch_KejadianBeranakModel = search_KejadianBeranakModel;
		mode = "edit";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_data_kejadian_beranak,container, false);
		
		initializeView(rootView);
		populateSpinner();
		dataListener();
		
		banyakAnakBetina.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				int jumlahAnakBetina =  0;
				//menghidari input spasi
				if (banyakAnakBetina.getText().toString().trim().length() >0)
					jumlahAnakBetina =  Integer.parseInt(banyakAnakBetina.getText().toString());
					
				if (jumlahAnakBetina == 1){
					linSapike1.setVisibility(View.VISIBLE);
					viewlinSapike1 = true;
					General.showDialogSuccess(mContext, "Input Data Sapi Betina", "Terdapat 1 ekor Data Sapi Betina yang Harus Diisi");
				}else if (jumlahAnakBetina == 2){
					linSapike1.setVisibility(View.VISIBLE);
					linSapike2.setVisibility(View.VISIBLE);
					viewlinSapike1 = true;
					viewlinSapike2 = true;
					General.showDialogSuccess(mContext, "Input Data Sapi Betina", "Terdapat 2 ekor Data Sapi Betina yang Harus Diisi");
				}else if (jumlahAnakBetina == 0){
					linSapike1.setVisibility(View.GONE);
					linSapike2.setVisibility(View.GONE);
					viewlinSapike1 = false;
					viewlinSapike2 = false;
				}
			}
		});
		
		
		if (mode.equalsIgnoreCase("edit")) {
			tanggalBeranak.setText(mSearch_KejadianBeranakModel.getTanggalBeranak());
			banyakAnakBetina.setText(mSearch_KejadianBeranakModel.getBanyaknyaAnakBetina());
			banyakAnakJantan.setText(mSearch_KejadianBeranakModel.getBanyaknyaAnakJantan());
		}
		setLayout();
		return rootView;
	}

	private void dataListener() {

		tanggalBeranak.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        tanggalBeranak.setText(date);
	                        banyakAnakBetina.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		
		edtTanggalLahirSapike1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        edtTanggalLahirSapike1.setText(date);
	                        
	                        tvTanggalLahirDataSapiBetinaKe1.setFocusable(true);
	                        tvTanggalLahirDataSapiBetinaKe1.setFocusableInTouchMode(true);///add this line
	                        tvTanggalLahirDataSapiBetinaKe1.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		edtTanggalLahirSapike2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        edtTanggalLahirSapike2.setText(date);
	                        tvTanggalLahirDataSapiBetinaKe2.setFocusable(true);
	                        tvTanggalLahirDataSapiBetinaKe2.setFocusableInTouchMode(true);///add this line	                        
	                        tvTanggalLahirDataSapiBetinaKe2.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
	}

	private void initializeView(View rootView) {
		tanggalBeranak = (EditText) rootView.findViewById(R.id.edtTanggalBeranakKejadianBeranak);
		banyakAnakBetina = (EditText) rootView.findViewById(R.id.edtBanyakAnakBetinaKejadianBeranak);
		banyakAnakJantan = (EditText) rootView.findViewById(R.id.edtBanyakAnakJantanKejadianBeranak);
		saveAddData = (Button) rootView.findViewById(R.id.buttonSimpanKejadianBeranak);
		cancelAddData = (Button) rootView.findViewById(R.id.buttonBatalKejadianBeranak);
		tvTanggalBeranakKejadianBeranak = (TextView) rootView.findViewById(R.id.tvTanggalBeranakKejadianBeranak);
		
		linSapike1 = (LinearLayout) rootView.findViewById(R.id.linDataSapiBetinaKe1);
		edtNitSapike1 = (EditText) rootView.findViewById(R.id.edtNITDataSapiBetinaKe1);
		spinLokasiSapike1 = (Spinner) rootView.findViewById(R.id.spinLokasiDataSapiBetinaKe1);
		spinBangsaSapike1 = (Spinner) rootView.findViewById(R.id.spinBangsaDataSapiBetinaKe1);
		edtTanggalLahirSapike1 = (EditText) rootView.findViewById(R.id.edtTanggalLahirDataSapiBetinaKe1);
		spinNitIndukSapike1 = (Spinner) rootView.findViewById(R.id.spinNITIndukDataSapiBetinaKe1);
		edtBentukWajahSapike1 = (EditText) rootView.findViewById(R.id.edtBentukWajahDataSapiBetinaKe1);
		edtWarnaSapike1 = (EditText) rootView.findViewById(R.id.edtWarnaDataSapiBetinaKe1);
		spinStatusPunukSapiKe1 = (Spinner) rootView.findViewById(R.id.spinStatusPunukDataSapiBetinaKe1);
		spinStatusAksesorisSapike1 = (Spinner) rootView.findViewById(R.id.spinStatusAksesorisKakiDataSapiBetinaKe1);
		spinStatusKepemilikanSapike1 = (Spinner) rootView.findViewById(R.id.spinStatusKepemilikanDataSapiBetinaKe1);
		tvTanggalLahirDataSapiBetinaKe1 = (TextView) rootView.findViewById(R.id.tvTanggalLahirDataSapiBetinaKe1);
		
		linSapike2 = (LinearLayout) rootView.findViewById(R.id.linDataSapiBetinaKe2);
		edtNitSapike2 = (EditText) rootView.findViewById(R.id.edtNITDataSapiBetinaKe2);
		spinLokasiSapike2 = (Spinner) rootView.findViewById(R.id.spinLokasiDataSapiBetinaKe2);
		spinBangsaSapike2 = (Spinner) rootView.findViewById(R.id.spinBangsaDataSapiBetinaKe2);
		edtTanggalLahirSapike2 = (EditText) rootView.findViewById(R.id.edtTanggalLahirDataSapiBetinaKe2);
		spinNitIndukSapike2 = (Spinner) rootView.findViewById(R.id.spinNITIndukDataSapiBetinaKe2);
		edtBentukWajahSapike2 = (EditText) rootView.findViewById(R.id.edtBentukWajahDataSapiBetinaKe2);
		edtWarnaSapike2 = (EditText) rootView.findViewById(R.id.edtWarnaDataSapiBetinaKe2);
		spinStatusPunukSapiKe2 = (Spinner) rootView.findViewById(R.id.spinStatusPunukDataSapiBetinaKe2);
		spinStatusAksesorisSapike2 = (Spinner) rootView.findViewById(R.id.spinStatusAksesorisKakiDataSapiBetinaKe2);
		spinStatusKepemilikanSapike2 = (Spinner) rootView.findViewById(R.id.spinStatusKepemilikanDataSapiBetinaKe2);
		tvTanggalLahirDataSapiBetinaKe2 = (TextView) rootView.findViewById(R.id.tvTanggalLahirDataSapiBetinaKe2);
	}
	private void populateSpinner() {
		List<String> labelsLokasi = db.getIdlKontakKota();
		ArrayAdapter<String> spinnerLokasiAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsLokasi);
		spinnerLokasiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLokasiAdapter.notifyDataSetChanged();
		spinLokasiSapike1.setAdapter(spinnerLokasiAdapter);
		spinLokasiSapike2.setAdapter(spinnerLokasiAdapter);
		// /////////////////
		// ////////////////
		List<String> labelsBangsa = db.getIdValueBangsa();
		ArrayAdapter<String> spinnerBangsaAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsBangsa);
		spinnerBangsaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBangsaAdapter.notifyDataSetChanged();
		spinBangsaSapike1.setAdapter(spinnerBangsaAdapter);
		spinBangsaSapike2.setAdapter(spinnerBangsaAdapter);
		// ///////////////
		// //////////////

		List<String> labelsNitInduk = db.getNIT();
		ArrayAdapter<String> spinnerNITadapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsNitInduk);
		spinnerNITadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinNitIndukSapike1.setAdapter(spinnerNITadapter);
		spinNitIndukSapike2.setAdapter(spinnerNITadapter);
		// ///////////////
		// //////////////

		List<String> labelsStatusPunuk = new ArrayList<String>();
		labelsStatusPunuk.add("Ya");
		labelsStatusPunuk.add("Tidak");
		ArrayAdapter<String> spinnerStatusPunukAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,labelsStatusPunuk);
		spinnerStatusPunukAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinStatusPunukSapiKe1.setAdapter(spinnerStatusPunukAdapter);
		spinStatusPunukSapiKe2.setAdapter(spinnerStatusPunukAdapter);
		// ///////////////
		// //////////////
		List<String> labelsStatusAksesorisKaki = new ArrayList<String>();
		labelsStatusAksesorisKaki.add("Ya");
		labelsStatusAksesorisKaki.add("Tidak");
		ArrayAdapter<String> spinnerStatusAksesorisAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item,labelsStatusAksesorisKaki);
		spinnerStatusAksesorisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinStatusAksesorisSapike1.setAdapter(spinnerStatusAksesorisAdapter);
		spinStatusAksesorisSapike2.setAdapter(spinnerStatusAksesorisAdapter);
		
		// ///////////////
		// //////////////
		List<String> labelsStatusKepemilikan = new ArrayList<String>();
		labelsStatusKepemilikan.add("Pemerintah");
		labelsStatusKepemilikan.add("Pribadi");
		ArrayAdapter<String> spinnerStatusKepemilikanAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsStatusKepemilikan);
		spinnerStatusKepemilikanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinStatusKepemilikanSapike1.setAdapter(spinnerStatusKepemilikanAdapter);
		spinStatusKepemilikanSapike2.setAdapter(spinnerStatusKepemilikanAdapter);		
	}

	private void setLayout() {
		//edit tidak dipakai dalam kejadian baranak
		if (mode.equalsIgnoreCase("edit")) {
			saveAddData.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					EditKejadianBeranakModelSync editKejadianBeranakModelSync = new EditKejadianBeranakModelSync();
					editKejadianBeranakModelSync.setKode_kejadian_beranak(mSearch_KejadianBeranakModel.getKode_kejadian_beranak());
					editKejadianBeranakModelSync.setNit(mNit);
					editKejadianBeranakModelSync
							.setBanyak_anak_betina(banyakAnakBetina.getText()
									.toString());
					editKejadianBeranakModelSync
							.setBanyak_anak_jantan(banyakAnakJantan.getText()
									.toString());
					editKejadianBeranakModelSync
							.setTanggal_beranak(tanggalBeranak.getText()
									.toString());
					editKejadianBeranakModelSync.setUser(mSessionManager
							.getUserame());
					editKejadianBeranakModelSync.setPass(mSessionManager
							.getPassword());
					editKejadianBeranakModelSync.setGuid(General.generateGuid());

					EditKejadianBeranakSync editKejadianBeranakSync = new EditKejadianBeranakSync(getActivity(), mContext,editKejadianBeranakModelSync);
					editKejadianBeranakSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = editKejadianBeranakSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
//							String id = jsonObject.getString("kode_kejadian_beranak");
							elsKejadianBeranak mElsKejadianBeranak = new elsKejadianBeranak();
							mElsKejadianBeranak.setKode_kejadian_beranak(mSearch_KejadianBeranakModel.getKode_kejadian_beranak());
							mElsKejadianBeranak.setNit(mNit);
							mElsKejadianBeranak.setBanyaknya_anak_betina(banyakAnakBetina.getText().toString());
							mElsKejadianBeranak.setBanyaknya_anak_jantan(banyakAnakJantan.getText().toString());
							mElsKejadianBeranak.setTanggal_beranak(tanggalBeranak.getText().toString());
							db.updateKejadianBeranak(mElsKejadianBeranak);
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
			saveAddData.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AddKejadianBeranakModelSync addKejadianBeranakModelSync = new AddKejadianBeranakModelSync();
					addKejadianBeranakModelSync.setNit(mNit);
					addKejadianBeranakModelSync.setBanyak_anak_betina(banyakAnakBetina.getText().toString());
					addKejadianBeranakModelSync.setBanyak_anak_jantan(banyakAnakJantan.getText().toString());
					addKejadianBeranakModelSync.setTanggal_beranak(tanggalBeranak.getText().toString());
					addKejadianBeranakModelSync.setUser(mSessionManager.getUserame());
					addKejadianBeranakModelSync.setPass(mSessionManager.getPassword());
					addKejadianBeranakModelSync.setGuid(General.generateGuid());
					
					AddDataSapiModelSync addDataSapiModelSync1 = new AddDataSapiModelSync();
					AddDataSapiModelSync addDataSapiModelSync2 = new AddDataSapiModelSync();

					if (viewlinSapike1 == true){
						String nit = edtNitSapike1.getText().toString();
						String[] selectedLokasi = spinLokasiSapike1.getSelectedItem().toString().split(" \\| ");
						String[] bangsa = spinBangsaSapike1.getSelectedItem().toString().split(" \\| ");
						String tanggalLahir = edtTanggalLahirSapike1.getText().toString();
						String nitInduk = spinNitIndukSapike1.getSelectedItem().toString();
						String bentukWajah = edtBentukWajahSapike1.getText().toString();
						String warna = edtWarnaSapike1.getText().toString();

						int statusPunuk = 0;// 0 tidak
						if (spinStatusPunukSapiKe1.getSelectedItem().toString().equalsIgnoreCase("Ya"))
							statusPunuk = 1;

						int statusAksesorisSapi = 0;// 0 tidak
						if (spinStatusAksesorisSapike1.getSelectedItem().toString().equalsIgnoreCase("Ya"))
							statusAksesorisSapi = 1;

						int statusKepemilikan = 0;// 0 pribadi
						if (spinStatusKepemilikanSapike1.getSelectedItem().toString().equalsIgnoreCase("pemerintah"))
							statusKepemilikan = 1;

						addDataSapiModelSync1.setNit(Integer.parseInt(nit));
						addDataSapiModelSync1.setIdl(Integer.parseInt(selectedLokasi[0]));
						addDataSapiModelSync1.setTanggalLahir(tanggalLahir);
						addDataSapiModelSync1.setBangsa(bangsa[0]);
						addDataSapiModelSync1.setNit_induk(Integer.parseInt(nitInduk));
						addDataSapiModelSync1.setBentuk_wajah(bentukWajah);
						addDataSapiModelSync1.setWarna(warna);
						addDataSapiModelSync1.setStatus_punuk(statusPunuk);
						addDataSapiModelSync1.setStatus_aksesoris_kaki(statusAksesorisSapi);
						addDataSapiModelSync1.setStatus_kepemilikan(statusKepemilikan);
						addDataSapiModelSync1.setGuid(General.generateGuid());
					}
					if (viewlinSapike2 == true){
						String nit = edtNitSapike2.getText().toString();
						String[] selectedLokasi = spinLokasiSapike2.getSelectedItem().toString().split(" \\| ");
						String[] bangsa = spinBangsaSapike2.getSelectedItem().toString().split(" \\| ");
						String tanggalLahir = edtTanggalLahirSapike2.getText().toString();
						String nitInduk = spinNitIndukSapike2.getSelectedItem().toString();
						String bentukWajah = edtBentukWajahSapike2.getText().toString();
						String warna = edtWarnaSapike2.getText().toString();

						int statusPunuk = 0;// 0 tidak
						if (spinStatusPunukSapiKe2.getSelectedItem().toString().equalsIgnoreCase("Ya"))
							statusPunuk = 1;

						int statusAksesorisSapi = 0;// 0 tidak
						if (spinStatusAksesorisSapike2.getSelectedItem().toString().equalsIgnoreCase("Ya"))
							statusAksesorisSapi = 1;

						int statusKepemilikan = 0;// 0 pribadi
						if (spinStatusKepemilikanSapike2.getSelectedItem().toString().equalsIgnoreCase("pemerintah"))
							statusKepemilikan = 1;

						addDataSapiModelSync2.setNit(Integer.parseInt(nit));
						addDataSapiModelSync2.setIdl(Integer.parseInt(selectedLokasi[0]));
						addDataSapiModelSync2.setTanggalLahir(tanggalLahir);
						addDataSapiModelSync2.setBangsa(bangsa[0]);
						addDataSapiModelSync2.setNit_induk(Integer.parseInt(nitInduk));
						addDataSapiModelSync2.setBentuk_wajah(bentukWajah);
						addDataSapiModelSync2.setWarna(warna);
						addDataSapiModelSync2.setStatus_punuk(statusPunuk);
						addDataSapiModelSync2.setStatus_aksesoris_kaki(statusAksesorisSapi);
						addDataSapiModelSync2.setStatus_kepemilikan(statusKepemilikan);
						addDataSapiModelSync2.setGuid(General.generateGuid());
					}
					
					AddKejadianBeranakSync addKejadianBeranakSync = new AddKejadianBeranakSync(
							getActivity(), 
							mContext, 
							addKejadianBeranakModelSync,
							addDataSapiModelSync1,
							addDataSapiModelSync2);
					
					addKejadianBeranakSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = addKejadianBeranakSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							String id = jsonObject.getString("kode_kejadian_beranak");
							
							elsKejadianBeranak mElsKejadianBeranak = new elsKejadianBeranak();
							mElsKejadianBeranak.setKode_kejadian_beranak(Integer.parseInt(id));
							mElsKejadianBeranak.setNit(mNit);
							mElsKejadianBeranak.setBanyaknya_anak_betina(banyakAnakBetina.getText().toString());
							mElsKejadianBeranak.setBanyaknya_anak_jantan(banyakAnakJantan.getText().toString());
							mElsKejadianBeranak.setTanggal_beranak(tanggalBeranak.getText().toString());
							db.createKejadianBeranakWithID(mElsKejadianBeranak);
							
							if (!banyakAnakBetina.getText().toString().equalsIgnoreCase("")){
								int banyakSapiBetina = Integer.parseInt(banyakAnakBetina.getText().toString());
								if (banyakSapiBetina > 0){
									new General().showDialogSuccess(mContext, "Anda Memasukkan Data Sapi Betina Sebanyak "+banyakSapiBetina+" ekor", "Insert Data Sapi");
								}
							}
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

		cancelAddData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
