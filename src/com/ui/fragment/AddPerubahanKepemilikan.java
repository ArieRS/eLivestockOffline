package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.asynctask.AddPerubahanKepemlikanSync;
import com.ui.asynctask.EditPerubahanKepemlikanSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsPerubahanKepemilikan;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.sync.AddPerubahanKepemilikanModelSync;
import com.ui.model.sync.EditPerubahanKepemilikanModelSync;

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

public class AddPerubahanKepemilikan extends Fragment {
	Context mContext;
	Spinner spinnerLokasiSebelum, spinnerLokasiSesudah;
	EditText edtTanggalKejadian;
	Button mButtonSave, mButtonCancel;
	SessionManager mSessionManager;
	DatabaseHelper db;
	int mNit;
	Search_PerubahanKepemilikanModel mSearch_PerubahanKepemilikanModel;
	String mode;
	TextView tvLokasiSebelumnya;

	public AddPerubahanKepemilikan(Context context, int nit) {
		mContext = context;
		mSessionManager = new SessionManager(mContext);
		db = new DatabaseHelper(mContext);
		mNit = nit;
		mode = "add";
	}

	public AddPerubahanKepemilikan(Context context, int nit,
			Search_PerubahanKepemilikanModel search_PerubahanKepemilikanModel) {
		mContext = context;
		mSessionManager = new SessionManager(mContext);
		db = new DatabaseHelper(mContext);
		mNit = nit;
		mSearch_PerubahanKepemilikanModel =  search_PerubahanKepemilikanModel;
		mode = "edit";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_perubahan_kepemilikan,
				container, false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		spinnerLokasiSebelum = (Spinner) rootView
				.findViewById(R.id.spinLokasiSebelumnyaPerubahanKepemilikan);
		spinnerLokasiSesudah = (Spinner) rootView
				.findViewById(R.id.spinLokasiSesudahPerubahanKepemilikan);
		edtTanggalKejadian = (EditText) rootView.findViewById(R.id.edtTanggalKejadianPerubahanKepemilikan);
		mButtonSave = (Button) rootView.findViewById(R.id.buttonSimpanPerubahanKepemilikan);
		mButtonCancel = (Button) rootView.findViewById(R.id.buttonBatalPerubahanKepemilikan);
		tvLokasiSebelumnya = (TextView) rootView.findViewById(R.id.tvLokasiSebelumnyaPerubahanKepemilikan);
		
		edtTanggalKejadian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        edtTanggalKejadian.setText(date);
	                        
	                        tvLokasiSebelumnya.setFocusable(true);
	                        tvLokasiSebelumnya.setFocusableInTouchMode(true);///add this line
	                        tvLokasiSebelumnya.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		
		populateSpinner();
		setButtonListener();
	}

	private void populateSpinner() {
		//sebelum
				List<String> labelsLokasiSebelum = db.getIdlKontakKota();
				ArrayAdapter<String> spinnerLokasiSebelumAdapter = new ArrayAdapter<String>(
						mContext, android.R.layout.simple_spinner_item, labelsLokasiSebelum);
				spinnerLokasiSebelumAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerLokasiSebelumAdapter.notifyDataSetChanged();
				spinnerLokasiSebelum.setAdapter(spinnerLokasiSebelumAdapter);
				//sesudah
				List<String> labelsLokasiSesudah = db.getIdlKontakKota();
				ArrayAdapter<String> spinnerLokasiSesudahAdapter = new ArrayAdapter<String>(
						mContext, android.R.layout.simple_spinner_item, labelsLokasiSesudah);
				spinnerLokasiSesudahAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerLokasiSebelumAdapter.notifyDataSetChanged();
				spinnerLokasiSesudah.setAdapter(spinnerLokasiSesudahAdapter);
				
				if (mode.equalsIgnoreCase("edit")){
					spinnerLokasiSebelum.setSelection(spinnerLokasiSebelumAdapter.getPosition(db.getIdlKontakKota(mSearch_PerubahanKepemilikanModel.getKotaSebelumnya())));
					spinnerLokasiSesudah.setSelection(spinnerLokasiSesudahAdapter.getPosition(db.getIdlKontakKota(mSearch_PerubahanKepemilikanModel.getKotaSesudahnya())));
					edtTanggalKejadian.setText(mSearch_PerubahanKepemilikanModel.getTanggalKejadian());
				}
	}

	private void setButtonListener() {
		
		
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final String[] selectedLokasiSebelum = spinnerLokasiSebelum.getSelectedItem().toString().split(" \\| ");
					final String[] selectedLokasiSesudah = spinnerLokasiSesudah.getSelectedItem().toString().split(" \\| ");
					String tanggalKejadian = edtTanggalKejadian.getText().toString();
					if (tanggalKejadian.trim().length()>0){
						if (mode.equalsIgnoreCase("edit")){
							
							EditPerubahanKepemilikanModelSync editPerubahanKepemilikanModelSync = new EditPerubahanKepemilikanModelSync();
							editPerubahanKepemilikanModelSync.setKode_riwayat_kepemilikan(mSearch_PerubahanKepemilikanModel.getKode_riwayat_kepemilikan());
							editPerubahanKepemilikanModelSync.setIdl_sebelum(Integer.parseInt(selectedLokasiSebelum[0]));
							editPerubahanKepemilikanModelSync.setIdl_sesudah(Integer.parseInt(selectedLokasiSesudah[0]));
							editPerubahanKepemilikanModelSync.setTanggal_kejadian(tanggalKejadian);
							editPerubahanKepemilikanModelSync.setNit(mNit);
							editPerubahanKepemilikanModelSync.setUser(mSessionManager.getUserame());
							editPerubahanKepemilikanModelSync.setPass(mSessionManager.getPassword());
							editPerubahanKepemilikanModelSync.setGuid(General.generateGuid());
		
							EditPerubahanKepemlikanSync editPerubahanKepemlikanSync = new EditPerubahanKepemlikanSync(
									getActivity(), mContext, editPerubahanKepemilikanModelSync);
							editPerubahanKepemlikanSync.execute();
							
							String resultJson;
							JSONObject jsonObject;
							try {
								resultJson = editPerubahanKepemlikanSync.get().toString();
								jsonObject = new JSONObject(resultJson);
								String status = jsonObject.getString("message");
								if(status.equalsIgnoreCase("sukses")) {
//									String id = jsonObject.getString("kode_riwayat_kepemilikan");
									
									elsPerubahanKepemilikan mElsPerubahanKepemilikan = new elsPerubahanKepemilikan();
									mElsPerubahanKepemilikan.setKode_riwayat_kepemilikan(mSearch_PerubahanKepemilikanModel.getKode_riwayat_kepemilikan());
									mElsPerubahanKepemilikan.setIdl_sebelum(Integer.parseInt(selectedLokasiSebelum[0]));
									mElsPerubahanKepemilikan.setIdl_sesudah(Integer.parseInt(selectedLokasiSesudah[0]));
									mElsPerubahanKepemilikan.setTanggal_kejadian(edtTanggalKejadian
											.getText().toString());
									mElsPerubahanKepemilikan.setNit(mNit);
									db.updatePerubahanKepemilikan(mElsPerubahanKepemilikan);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
						else if (mode.equalsIgnoreCase("add")){
								
								AddPerubahanKepemilikanModelSync addPerubahanKepemilikanModelSync = new AddPerubahanKepemilikanModelSync();
								addPerubahanKepemilikanModelSync.setIdl_sebelum(Integer.parseInt(selectedLokasiSebelum[0]));
								addPerubahanKepemilikanModelSync.setIdl_sesudah(Integer.parseInt(selectedLokasiSesudah[0]));
								addPerubahanKepemilikanModelSync.setTanggal_kejadian(tanggalKejadian);
								addPerubahanKepemilikanModelSync.setNit(mNit);
								addPerubahanKepemilikanModelSync.setUser(mSessionManager
										.getUserame());
								addPerubahanKepemilikanModelSync.setPass(mSessionManager
										.getPassword());
								addPerubahanKepemilikanModelSync.setGuid(General.generateGuid());
			
								AddPerubahanKepemlikanSync addPerubahanKepemlikanSync = new AddPerubahanKepemlikanSync(
										getActivity(), mContext,
										addPerubahanKepemilikanModelSync);
								addPerubahanKepemlikanSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = addPerubahanKepemlikanSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
										String id = jsonObject.getString("kode_riwayat_kepemilikan");
										
										elsPerubahanKepemilikan mElsPerubahanKepemilikan = new elsPerubahanKepemilikan();
										
										mElsPerubahanKepemilikan.setKode_riwayat_kepemilikan(Integer.parseInt(id));
										mElsPerubahanKepemilikan.setIdl_sebelum(Integer.parseInt(selectedLokasiSebelum[0]));
										mElsPerubahanKepemilikan.setIdl_sesudah(Integer.parseInt(selectedLokasiSesudah[0]));
										mElsPerubahanKepemilikan.setTanggal_kejadian(edtTanggalKejadian
												.getText().toString());
										mElsPerubahanKepemilikan.setNit(mNit);
										db.createPerubahanKepemilikanWithID(mElsPerubahanKepemilikan);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
						}
					}//end if validation
					else {
						General.showDialogError(mContext, "Pastikan Data tanggal Kejadian Terisi.", mContext.getString(R.string.data_not_complete));
					}
					
				}
			});
		
		mButtonCancel.setOnClickListener(new View.OnClickListener() {
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
