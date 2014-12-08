package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ui.adapter.LaporanPenyakitSapiAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;

public class LaporanRiwayatPenyakitSapiFragment extends Fragment {

	ListView listView;
	LaporanPenyakitSapiAdapter adapter;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;

	DatabaseHelper db;
	// Spinner mSpinner;
	// Spinner spinKabKota;
	SessionManager session;
	Button viewButton;

	public LaporanRiwayatPenyakitSapiFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		session = new SessionManager(mContext);
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
		View resultView = inflater.inflate(R.layout.list_view_penyakit_sapi_fragment, container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView.findViewById(R.id.ListFragmentPenyakitSapi);
		viewButton = (Button) resultView.findViewById(R.id.viewLaporanPenyakitSapi);
		viewButton.setText("Laporan Riwayat Kesehatan");
		// mSpinner = (Spinner)
		// resultView.findViewById(R.id.spinProvinsiLaporanPenyakit);
		// spinKabKota = (Spinner)
		// resultView.findViewById(R.id.spinKabupatenKotaLaporanPenyakit);

		List<String> lables = new ArrayList<String>();
		String apa[] = { "dicoba", "huruf", "Besar" };

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				mContext, android.R.layout.simple_spinner_item, apa);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// mSpinner.setAdapter(spinnerAdapter);
		// spinKabKota.setAdapter(spinnerAdapter);

		LaporanPenyakitSapiAdapter adapter = new LaporanPenyakitSapiAdapter(
				mContext, R.layout.item_list_with_icon_laporan_penyakit,
				db.getLaporanPenyakit(session.getIdKabupatenKota(),
						session.getIdProvinsi(), session.getIdLevelAdmin()), getActivity().getSupportFragmentManager());
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// actioncBar.setDisplayHomeAsUpEnabled(false);
			// actioncBar.setHomeButtonEnabled(false);
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
