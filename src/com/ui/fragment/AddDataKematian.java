package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.asynctask.AddDataKematianSync;
import com.ui.asynctask.EditDataKematianSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsKejadianKematian;
import com.ui.model.laporan.Search_KejadianKematianModel;
import com.ui.model.sync.AddKematianModelSync;

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

public class AddDataKematian extends Fragment{
	Context mContext;
	DatabaseHelper db;
	int mNit;
	SessionManager mSessionManager;
	EditText edtTanggalKematian;
	Spinner spinSebabKematian, spinLokasiKematian;
	Button mButtonSave, mButtonCancel;
	String mode ;
	HomeActivity mActivity;
	TextView tvTanggalDataKematian;
	
	Search_KejadianKematianModel mSearch_KejadianKematianModel;
	public AddDataKematian(HomeActivity activity, Context context, int nit){
		mContext = context;
		mActivity = activity;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mNit = nit;
		mode = "add";
	}
	
	public AddDataKematian(HomeActivity activity, Context context, int nit,Search_KejadianKematianModel search_KejadianKematianModel) {
		mContext = context;
		mActivity = activity;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mNit = nit;
		mode = "edit";
		mSearch_KejadianKematianModel = search_KejadianKematianModel;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_kejadian_kematian, container, false);
		setLayout(rootView);
		return rootView;
	}
	private void setLayout(View rootView) {
		edtTanggalKematian = (EditText) rootView.findViewById(R.id.edtTanggalDataKematian);
		spinSebabKematian = (Spinner) rootView.findViewById(R.id.spinSebabDataKematian);
		spinLokasiKematian = (Spinner) rootView.findViewById(R.id.spinLokasiDataKematian); 
		mButtonSave = (Button) rootView.findViewById(R.id.buttonSimpanDataKematian);
		mButtonCancel = (Button) rootView.findViewById(R.id.buttonBatalDataKematian);
		tvTanggalDataKematian = (TextView) rootView.findViewById(R.id.tvTanggalDataKematian);
		
		edtTanggalKematian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        edtTanggalKematian.setText(date);
	                        
	                        tvTanggalDataKematian.setFocusable(true);
	                        tvTanggalDataKematian.setFocusableInTouchMode(true);
	                        tvTanggalDataKematian.requestFocus();
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
		List<String> labelsSebabKematian = new ArrayList<String>();
		labelsSebabKematian = db.getAllSebabKematian();
		final ArrayAdapter<String> spinnerSebabKematianAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsSebabKematian);
		spinnerSebabKematianAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSebabKematianAdapter.notifyDataSetChanged();
		spinSebabKematian.setAdapter(spinnerSebabKematianAdapter);
		
		List<String> labelsLokasiKematian = new ArrayList<String>();
		labelsLokasiKematian = db.getIdlKontakKota();
		final ArrayAdapter<String> spinnerLokasiAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, labelsLokasiKematian);
		spinnerLokasiAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLokasiAdapter.notifyDataSetChanged();
		spinLokasiKematian.setAdapter(spinnerLokasiAdapter);
		
		if (mode.equalsIgnoreCase("edit")){
			edtTanggalKematian.setText(mSearch_KejadianKematianModel.getTanggalKematian());
			spinSebabKematian.setSelection(spinnerSebabKematianAdapter.getPosition(mSearch_KejadianKematianModel.getSebabKematian()));
			spinLokasiKematian.setSelection(spinnerLokasiAdapter.getPosition(db.getIdlKontakKota(mSearch_KejadianKematianModel.getLokasiKematian())));
		}
	}

	private void setButtonListener() {
		mButtonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				int sebabKematian = (int)spinSebabKematian.getSelectedItemId()+1;
				String [] idlLokasiKematian = spinLokasiKematian.getSelectedItem().toString().split(" \\| ");
				
				elsKejadianKematian mElsKejadianKematian = new elsKejadianKematian();
				mElsKejadianKematian.setNit(String.valueOf(mNit));
				mElsKejadianKematian.setTanggal_kematian(edtTanggalKematian.getText().toString());
				mElsKejadianKematian.setSebab_kematian(String.valueOf(sebabKematian));
				mElsKejadianKematian.setIdl_kematian(idlLokasiKematian[0]);
				mElsKejadianKematian.setId_petugas(String.valueOf(db.getIdPetugas(mSessionManager.getUserame())));
				
				if (mode.equalsIgnoreCase("edit")){
					//sync to server
					AddKematianModelSync mAddKematianModelSync = new AddKematianModelSync();
					mAddKematianModelSync.setNit(String.valueOf(mNit));
					mAddKematianModelSync.setTanggal_kematian(edtTanggalKematian.getText().toString());
					mAddKematianModelSync.setSebab_kematian(String.valueOf(sebabKematian));
					mAddKematianModelSync.setIdl_kematian(idlLokasiKematian[0]);
					mAddKematianModelSync.setId_petugas(String.valueOf(db.getIdPetugas(mSessionManager.getUserame())));
					mAddKematianModelSync.setUser(mSessionManager.getUserame());
					mAddKematianModelSync.setPass(mSessionManager.getPassword());
					mAddKematianModelSync.setGuid(General.generateGuid());
					
					EditDataKematianSync mEditDataKematianSync = new EditDataKematianSync(mActivity, getActivity(), mContext, mAddKematianModelSync);
					mEditDataKematianSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mEditDataKematianSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							db.updateKejadianKematian(mElsKejadianKematian);
							// pop to previous fragment
							getActivity().getSupportFragmentManager().popBackStack();
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
					
					//sync to server
					AddKematianModelSync mAddKematianModelSync = new AddKematianModelSync();
					mAddKematianModelSync.setNit(String.valueOf(mNit));
					mAddKematianModelSync.setTanggal_kematian(edtTanggalKematian.getText().toString());
					mAddKematianModelSync.setSebab_kematian(String.valueOf(sebabKematian));
					mAddKematianModelSync.setIdl_kematian(idlLokasiKematian[0]);
					mAddKematianModelSync.setId_petugas(String.valueOf(db.getIdPetugas(mSessionManager.getUserame())));
					mAddKematianModelSync.setUser(mSessionManager.getUserame());
					mAddKematianModelSync.setPass(mSessionManager.getPassword());
					mAddKematianModelSync.setGuid(General.generateGuid());
					
					AddDataKematianSync mAddDataKematianSync = new AddDataKematianSync(getActivity(), mContext, mAddKematianModelSync);
					mAddDataKematianSync.execute();
					
					String resultJson;
					JSONObject jsonObject;
					try {
						resultJson = mAddDataKematianSync.get().toString();
						jsonObject = new JSONObject(resultJson);
						String status = jsonObject.getString("message");
						if(status.equalsIgnoreCase("sukses")) {
							db.createKejadianKematian(mElsKejadianKematian);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		});
		mButtonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//pop to previous fragment
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
