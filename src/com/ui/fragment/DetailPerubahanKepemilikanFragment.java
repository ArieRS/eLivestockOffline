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
import com.ui.adapter.Search_PerubahanKepemilikanAdapter;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

public class DetailPerubahanKepemilikanFragment extends Fragment {
	Context mContext;
	String mNit;
	ArrayList<Search_PerubahanKepemilikanModel> mSearch_PerubahanKepemilikanModels;
	ListView listView;
	Button buttonView;
	
	
	public DetailPerubahanKepemilikanFragment(Context context, ArrayList<Search_PerubahanKepemilikanModel> arrayListSearch_PerubahanKepemilikanModels,
			String nit){
		mContext = context;
		mSearch_PerubahanKepemilikanModels = arrayListSearch_PerubahanKepemilikanModels;
		mNit = nit;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.list_view_laporan_kepemilikan, container, false);
		listView = (ListView) rootView.findViewById(R.id.ListFragmentLaporanKepemilikan);
		buttonView = (Button) rootView.findViewById(R.id.viewLaporanKepemilikan);
		buttonView.setText("Detail Perubahan Kepemilikan NIT : "+ mNit);
		
		Search_PerubahanKepemilikanAdapter mPerubahanKepemilikanAdapter = new Search_PerubahanKepemilikanAdapter(
				mContext, R.layout.item_list_search_perubahan_kepemilikan,
				mSearch_PerubahanKepemilikanModels);

		mPerubahanKepemilikanAdapter.notifyDataSetChanged();
		listView.setAdapter(mPerubahanKepemilikanAdapter);
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
