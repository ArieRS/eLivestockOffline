package com.ui.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ui.adapter.LaporanPopulasiSapiAdapter;
import com.ui.adapter.LaporanSapiBerdasarkanBangsaAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.elivestock.R;

public class LaporanSapiBerdasarkanBangsaFragment extends Fragment {

	ListView listView;
	LaporanSapiBerdasarkanBangsaAdapter adapter;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	String mUrl = "";
	DatabaseHelper db;
	Button button;

	public LaporanSapiBerdasarkanBangsaFragment(Context mContext) {
		this.mContext = mContext;
		db =  new DatabaseHelper(mContext);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
//		actioncBar.setHomeButtonEnabled(true);
		actioncBar.setDisplayHomeAsUpEnabled(true);
//		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View resultView = inflater.inflate(R.layout.list_view_laporan_populasi_area,
				container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView.findViewById(R.id.ListFragmentLaporanPopulasiArea);
		button = (Button) resultView.findViewById(R.id.viewLaporanPopulasiArea);
		button.setText("Laporan Sapi Berdasarkan Bangsa");
		
		
		LaporanPopulasiSapiAdapter adapter = new LaporanPopulasiSapiAdapter(
				mContext, R.layout.item_list_with_icon_sapi_area_lokasi,
				db.getLaporanBangsa());
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			actioncBar.setDisplayHomeAsUpEnabled(false);
//			actioncBar.setHomeButtonEnabled(false);
			Fragment fragment = new ReportDataFragment(mContext);
			General.replaceFragment(fragment, getFragmentManager());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
