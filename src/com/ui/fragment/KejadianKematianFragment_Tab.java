package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.ui.asynctask.DeleteDataKematianSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.common.Validation;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsKejadianKematian;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_KejadianKematianModel;
import com.ui.model.sync.AddKematianModelSync;

public class KejadianKematianFragment_Tab extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	ArrayList<Search_KejadianKematianModel> arraySearch_KejadianKematianModels;
	EditText edtTanggalKematian, edtIdPetugas;
	EditText edtSebabKematian, edtLokasiKematian;
	Button buttonAdd, buttonEdit, buttonDelete;
	SessionManager mSessionManager;
	DatabaseHelper  db;
	int mNit;
	Search_KejadianKematianModel mSearch_KejadianKematianModel = new Search_KejadianKematianModel();
	HomeActivity mActivity;
	Boolean isEditable = false;
	
	public KejadianKematianFragment_Tab(HomeActivity activity, Context context, ArrayList<Search_KejadianKematianModel> arrayListSearch_KejadianKematianModels, int nit){
		mContext = context;
		mActivity = activity;
		arraySearch_KejadianKematianModels = arrayListSearch_KejadianKematianModels;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rooView = inflater.inflate(R.layout.tab_kejadian_kematian, container, false);
		setLayout(rooView);
		return rooView;
	}
	
	private void setLayout(View rootView){
		edtTanggalKematian	= (EditText) rootView.findViewById(R.id.edtTanggalKematianTab);
		edtTanggalKematian.setFocusable(false);
		edtTanggalKematian.setEnabled(false);
		
		edtIdPetugas = (EditText) rootView.findViewById(R.id.edtIdPetugasKematianTab);
		edtIdPetugas.setFocusable(false);
		edtIdPetugas.setEnabled(false);
		
		edtSebabKematian	= (EditText) rootView.findViewById(R.id.edtSebabKematianTab);	
		edtSebabKematian.setFocusable(false);
		edtSebabKematian.setEnabled(false);
		
		edtLokasiKematian 	= (EditText) rootView.findViewById(R.id.edtLokasiKematianTab);
		edtLokasiKematian.setFocusable(false);
		edtLokasiKematian.setEnabled(false);
		
		buttonAdd			= (Button) rootView.findViewById(R.id.buttonAddDataKematian);
		buttonEdit			= (Button) rootView.findViewById(R.id.buttonEditDataKematian);
		buttonDelete		= (Button) rootView.findViewById(R.id.buttonDeletetDataKematian);
		
//		if (arraySearch_KejadianKematianModels.size() > 0)
//		{
//			mSearch_KejadianKematianModel = arraySearch_KejadianKematianModels.get(0);
//			edtTanggalKematian.setText(Validation.ifStringNull(mSearch_KejadianKematianModel.getTanggalKematian()));
//			populateSpinner(mSearch_KejadianKematianModel);
//		}
		if ((mSessionManager.getPeran().equalsIgnoreCase("aho")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("admin")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("ins")))	
		{
			isEditable = true;
		}	
		else if ((mSessionManager.getPeran().equalsIgnoreCase("dinas")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("dprov")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("dkota")))
		{
			isEditable = false;
		}
		
		arraySearch_KejadianKematianModels = db.getKejadianKematian(mNit);
		
		if (arraySearch_KejadianKematianModels.size() > 0)
		{
			mSearch_KejadianKematianModel = arraySearch_KejadianKematianModels.get(0);
			populateEdittext(mSearch_KejadianKematianModel);
			
			if (isEditable==true){
				buttonAdd.setVisibility(View.INVISIBLE);
				buttonDelete.setVisibility(View.VISIBLE);
				buttonEdit.setVisibility(View.VISIBLE);
			}
		}
		setButtonListener();
	}
	@Override
	public void onResume() {
		super.onResume();
		arraySearch_KejadianKematianModels = db.getKejadianKematian(mNit);
		
		if (arraySearch_KejadianKematianModels.size() > 0)
		{
			mSearch_KejadianKematianModel = arraySearch_KejadianKematianModels.get(0);
			populateEdittext(mSearch_KejadianKematianModel);
			
			if (isEditable==true){
				buttonAdd.setVisibility(View.INVISIBLE);
				buttonDelete.setVisibility(View.VISIBLE);
				buttonEdit.setVisibility(View.VISIBLE);
			}
		}
	}
	private void populateEdittext(Search_KejadianKematianModel search_KejadianKematianModel){
		edtTanggalKematian.setText(Validation.ifStringNull(search_KejadianKematianModel.getTanggalKematian()));
		edtIdPetugas.setText(Validation.ifStringNull(search_KejadianKematianModel.getIdPetugas()));
		edtSebabKematian.setText(Validation.ifStringNull(search_KejadianKematianModel.getSebabKematian()));
		edtLokasiKematian.setText(Validation.ifStringNull(search_KejadianKematianModel.getLokasiKematian()));
		
		
//		List<String> labelsSebabKematian = new ArrayList<String>();
//		labelsSebabKematian.add(search_KejadianKematianModel.getSebabKematian());
//		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, labelsSebabKematian);
//		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinSebabKematian.setAdapter(spinnerAdapter);
//		spinSebabKematian.setEnabled(false);
//		spinSebabKematian.setFocusable(false);
//		
//		List<String> labelsLokasiKematian = new ArrayList<String>();
//		labelsLokasiKematian.add(Validation.ifStringNull(search_KejadianKematianModel.getLokasiKematian()));
//		ArrayAdapter<String> spinnerLokasiAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, labelsLokasiKematian);
//		spinnerLokasiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinLokasiKematian.setAdapter(spinnerLokasiAdapter);
//		spinLokasiKematian.setEnabled(false);
//		spinLokasiKematian.setFocusable(false);
	}
	private void setButtonListener() {
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment =  new AddDataKematian(mActivity, mContext, mNit);
				General.replaceFragmentAddBackStack(fragment, getActivity().getSupportFragmentManager());
			}
		});
		buttonEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment =  new AddDataKematian(mActivity, mContext, mNit, arraySearch_KejadianKematianModels.get(0));
				General.replaceFragmentAddBackStack(fragment, getActivity().getSupportFragmentManager());
			}
		});
		buttonDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data kematian NIT =  "+ String.valueOf(mNit))
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								AddKematianModelSync mAddKematianModelSync = new AddKematianModelSync();
								mAddKematianModelSync.setNit(String.valueOf(mNit));
								mAddKematianModelSync.setUser(mSessionManager.getUserame());
								mAddKematianModelSync.setPass(mSessionManager.getPassword());
								mAddKematianModelSync.setGuid(General.generateGuid());
								
								
								DeleteDataKematianSync mDeleteDataKematianSync = new DeleteDataKematianSync(getActivity(), mContext, mAddKematianModelSync);
								mDeleteDataKematianSync.execute();

								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = mDeleteDataKematianSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
										elsKejadianKematian mElsKejadianKematian = new elsKejadianKematian();
										mElsKejadianKematian.setNit(String.valueOf(mNit));
										db.deleteKejadianKematian(mElsKejadianKematian);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								////set data
								arraySearch_KejadianKematianModels = db.getKejadianKematian(mNit);
								
								if (arraySearch_KejadianKematianModels.size() == 0)
								{
									edtTanggalKematian.setText("");
									edtIdPetugas.setText("");
									edtSebabKematian.setText("");
									edtLokasiKematian.setText("");
									
									buttonAdd.setVisibility(View.VISIBLE);
									buttonDelete.setVisibility(View.INVISIBLE);
									buttonEdit.setVisibility(View.INVISIBLE);
								}
							}
						}).create().show();
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
