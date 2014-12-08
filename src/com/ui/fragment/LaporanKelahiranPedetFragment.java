package com.ui.fragment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ui.adapter.LaporanKelahiranPedetAdapter;
import com.ui.adapter.MasterProvinsiAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.list.ResponseLaporanKelahiranPedetSapi;
import com.ui.list.ResponseMasterProvinsi;

public class LaporanKelahiranPedetFragment extends Fragment {

	ListView listView;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	DatabaseHelper db;
	Button button;

	public LaporanKelahiranPedetFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
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
		View resultView = inflater.inflate(R.layout.list_view_laporan_populasi_area,
				container, false);
		setLayout(inflater, resultView);

		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView.findViewById(R.id.ListFragmentLaporanPopulasiArea);
		button = (Button) resultView.findViewById(R.id.viewLaporanPopulasiArea);
		button.setText("Laporan Kelahiran Pedet");
		
		LaporanKelahiranPedetAdapter adapter = new LaporanKelahiranPedetAdapter(
				mContext, R.layout.item_list_with_icon_laporan_kelahiran_pedet,
				db.getLaporanKelahiranPedet());
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
