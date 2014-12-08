package com.ui.fragment;

import com.ui.adapter.LaporanLokasiSapiAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;

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
import android.widget.ListView;

public class LaporanLokasiFragment extends Fragment {

	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	ListView listView;
	DatabaseHelper db;
	SessionManager sessionManager;

	public LaporanLokasiFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		 actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		 actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.list_view_fragment_laporan_lokasi, container, false);
		setLayout(inflater, rootView);
		return rootView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView
				.findViewById(R.id.ListFragmentLaporanLokasi);

		LaporanLokasiSapiAdapter adapter = new LaporanLokasiSapiAdapter(
				mContext, R.layout.item_list_with_icon_laporan_lokasi,
				db.getLaporanLokasi(sessionManager.getIdKabupatenKota(),
						sessionManager.getIdProvinsi(),
						sessionManager.getIdLevelAdmin()));
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			actioncBar.setDisplayHomeAsUpEnabled(false);
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
