package com.ui.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ui.adapter.DetailDiagnosaAdapter;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

public class DetailDiagnosaFragment extends Fragment {
	Context mContext;
	String mNit;
	ArrayList<Search_RiwayatKesehatanModel> arraySearch_RiwayatKesehatanModels;
	ListView listView;
	Button button;
	
	public DetailDiagnosaFragment(Context context, ArrayList<Search_RiwayatKesehatanModel> arrayListRiwayatKesehatan,
			String nit){
		mContext = context;
		arraySearch_RiwayatKesehatanModels = arrayListRiwayatKesehatan;
		mNit = nit;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_view_penyakit_sapi_fragment,
				container, false);
		listView = (ListView) rootView.findViewById(R.id.ListFragmentPenyakitSapi);
		button = (Button) rootView.findViewById(R.id.viewLaporanPenyakitSapi);
		button.setText("Detail Diagnosa NIT : "+ mNit);
		
		DetailDiagnosaAdapter mDetailDiagnosaAdapter = new DetailDiagnosaAdapter(
				mContext, R.layout.detail_diagnosa,
				arraySearch_RiwayatKesehatanModels);

		mDetailDiagnosaAdapter.notifyDataSetChanged();
		listView.setAdapter(mDetailDiagnosaAdapter);
		
		return rootView;
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
