package com.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.asynctask.AddKejadianIbSync;
import com.ui.asynctask.EditKejadianIbSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.elivestock.R.integer;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsKejadianIb;
import com.ui.model.laporan.Search_KejadianIBModel;
import com.ui.model.sync.AddKejadianIBModelSync;
import com.ui.model.sync.EditKejadianIBModelSync;

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

public class AddKejadianIB extends Fragment {
	int mNit;
	Context mContext;
	EditText edtKodeStraw, edtTanggalIb;
	Button mButtonSave, mButtonCancel;
	DatabaseHelper db;
	SessionManager mSessionManager;
	Search_KejadianIBModel mSearch_KejadianIBModel;
	String mode;


	public AddKejadianIB(Context context, int nit) {
		mContext = context;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mode = "add";
	}

	public AddKejadianIB(Context context, int nit,
			Search_KejadianIBModel search_KejadianIBModel) {
		mContext = context;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
		mSearch_KejadianIBModel = search_KejadianIBModel;
		mode = "edit";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_kejadian_ib, container,
				false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		edtKodeStraw = (EditText) rootView
				.findViewById(R.id.edtKodeStrawKejadianIb);
		edtTanggalIb = (EditText) rootView
				.findViewById(R.id.edtTanggalKejadianIb);
		mButtonSave = (Button) rootView
				.findViewById(R.id.buttonSimpanKejadianIB);
		mButtonCancel = (Button) rootView
				.findViewById(R.id.buttonBatalKejadianIB);

		
		edtTanggalIb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
	                        
	                        edtTanggalIb.setText(date);
	                        edtKodeStraw.requestFocus();//moves the focus to something else after dialog is closed
	                    }
	                };
	                datePickerFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
	            }
			}
		});
		
		if (mode.equalsIgnoreCase("edit")){
			edtKodeStraw.setText(mSearch_KejadianIBModel.getKodeStraw());
			edtTanggalIb.setText(mSearch_KejadianIBModel.getTanggalIB());
		}
		setButtonListener();
	}

	private void setButtonListener() {
		if (mode.equalsIgnoreCase("edit")){
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String kodeStrawEdit = edtKodeStraw.getText().toString();
					String kodeTanggalIbEdit = edtTanggalIb.getText().toString();
					
					if ((kodeStrawEdit.trim().length()>0) &&
						(kodeTanggalIbEdit.trim().length()>0))
					{
						EditKejadianIBModelSync mEditKejadianIBModelSync = new EditKejadianIBModelSync();
						mEditKejadianIBModelSync.setKode_kejadian_ib(mSearch_KejadianIBModel.getKode_kejadian_ib());
						mEditKejadianIBModelSync.setKode_straw(kodeStrawEdit);
						mEditKejadianIBModelSync.setTanggal_ib(kodeTanggalIbEdit);
						mEditKejadianIBModelSync.setNit(mNit);
						mEditKejadianIBModelSync.setId_petugas(db.getIdPetugas(mSessionManager.getUserame()));
						mEditKejadianIBModelSync.setUser(mSessionManager.getUserame());
						mEditKejadianIBModelSync.setPass(mSessionManager.getPassword());
						mEditKejadianIBModelSync.setGuid(General.generateGuid());
						
						
						
						EditKejadianIbSync mEditKejadianIbSync = new EditKejadianIbSync(
								getActivity(), mContext, mEditKejadianIBModelSync);
						mEditKejadianIbSync.execute();
						
						String resultJson;
						JSONObject jsonObject;
						try {
							resultJson = mEditKejadianIbSync.get().toString();
							jsonObject = new JSONObject(resultJson);
							String status = jsonObject.getString("message");
							if(status.equalsIgnoreCase("sukses")) {
								//String id = jsonObject.getString("kode_kejadian_ib");
								
								elsKejadianIb mElsKejadianIb = new elsKejadianIb();
								mElsKejadianIb.setKode_kejadian_ib(mSearch_KejadianIBModel.getKode_kejadian_ib());
								mElsKejadianIb.setKode_straw(edtKodeStraw.getText().toString());
								mElsKejadianIb.setTanggal_ib(edtTanggalIb.getText().toString());
								mElsKejadianIb.setNit(mNit);
								mElsKejadianIb.setId_petugas(db.getIdPetugas(mSessionManager.getUserame()));
								db.updateKejadianIB(mElsKejadianIb);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}else{
						General.showDialogError(mContext, "Pastikan Kode Straw dan Tanggal IB terisi.", mContext.getString(R.string.data_not_complete));
					}
					
					
				}
			});
		}
		else if (mode.equalsIgnoreCase("add")){
			mButtonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String kodeStrawAdd = edtKodeStraw.getText().toString();
					String kodeTanggalIbAdd = edtTanggalIb.getText().toString();
					
					if ((kodeStrawAdd.trim().length()>0) &&
						(kodeTanggalIbAdd.trim().length()>0))
					{
						AddKejadianIBModelSync mAddKejadianIBModelSync = new AddKejadianIBModelSync();
						mAddKejadianIBModelSync.setKode_straw(kodeStrawAdd);
						mAddKejadianIBModelSync.setTanggal_ib(kodeTanggalIbAdd);
						mAddKejadianIBModelSync.setNit(mNit);
						mAddKejadianIBModelSync.setId_petugas(db
								.getIdPetugas(mSessionManager.getUserame()));
						mAddKejadianIBModelSync.setUser(mSessionManager.getUserame());
						mAddKejadianIBModelSync.setPass(mSessionManager.getPassword());
						mAddKejadianIBModelSync.setGuid(General.generateGuid());

						AddKejadianIbSync mAddKejadianIbSync = new AddKejadianIbSync(
								getActivity(), mContext, mAddKejadianIBModelSync);
						mAddKejadianIbSync.execute();
						
						String resultJson;
						JSONObject jsonObject;
						try {
							resultJson = mAddKejadianIbSync.get().toString();
							jsonObject = new JSONObject(resultJson);
							String status = jsonObject.getString("message");
							if(status.equalsIgnoreCase("sukses")) {
								String id = jsonObject.getString("kode_kejadian_ib");
								
								elsKejadianIb mElsKejadianIb = new elsKejadianIb();
								mElsKejadianIb.setKode_kejadian_ib(Integer.parseInt(id));
								mElsKejadianIb.setKode_straw(edtKodeStraw.getText().toString());
								mElsKejadianIb.setTanggal_ib(edtTanggalIb.getText().toString());
								mElsKejadianIb.setNit(mNit);
								mElsKejadianIb.setId_petugas(db.getIdPetugas(mSessionManager.getUserame()));
								db.createKejadianIBWithID(mElsKejadianIb);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}else{
						General.showDialogError(mContext, "Pastikan Kode Straw dan Tanggal IB terisi.", mContext.getString(R.string.data_not_complete));
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
