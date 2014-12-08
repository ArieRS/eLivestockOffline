package com.ui.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ui.adapter.ArrayStringAdapter;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;

public class ReportDataFragment extends Fragment {

	String[] reportString = new String[] { "Riwayat Kesehatan", "Penyakit",
			"Lokasi", "Populasi/Area", "Populasi Lokasi", "Sapi Produktif",
			"Kepemilikan", "Penyembelihan", "Bangsa Sapi", "AI",
			"Kelahiran Pedet", };

	ArrayStringAdapter adapter;
	Context mContext;
	SessionManager sessionManager;
	ActionBar actioncBar;
	Activity activity;
	
	public ReportDataFragment(Context context) {
		mContext = context;
		sessionManager = new SessionManager(mContext);
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
		
		if (sessionManager.getPeran().equalsIgnoreCase("ins")){
			reportString = new String[] { "AI", "Kelahiran Pedet" };
		}
		else if (sessionManager.getPeran().equalsIgnoreCase("aho")){
			reportString = new String[] { "Riwayat Kesehatan","Penyakit", "Kelahiran Pedet" };
		}
		else{
			reportString = new String[] { "Riwayat Kesehatan", "Penyakit",
					"Lokasi", "Populasi/Area", "Populasi Lokasi", "Sapi Produktif",
					"Kepemilikan", "Penyembelihan", "Bangsa Sapi", "AI",
					"Kelahiran Pedet", };
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View resultView = inflater.inflate(R.layout.list_view_fragment,
				container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		ListView list = (ListView) resultView.findViewById(R.id.ListFragment);
		adapter = new ArrayStringAdapter(mContext,
				R.layout.single_item_list, reportString);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View mView,
					int position, long arg3) {
				onListClick(mView, position, arg3);
			}
		});
	}

	protected void onListClick(View mView, int position, long arg3) {
		String listArray = reportString[position];

		Fragment mFragment;
		if (listArray.equalsIgnoreCase("Riwayat Kesehatan")) {
			mFragment = new LaporanRiwayatPenyakitSapiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("penyakit")) {
			mFragment = new LaporanPenyakitSapiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("lokasi")) {
			mFragment = new LaporanLokasiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("populasi/area")) {
			mFragment = new LaporanPopulasiAreaFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("populasi lokasi")) {
			mFragment = new LaporanPopulasiLokasiSapiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("sapi produktif")) {
			mFragment = new LaporanSapiProduktifFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("kepemilikan")) {
			mFragment = new LaporanKepemilikanFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("penyembelihan")) {
			mFragment = new LaporanSapiDisembelihFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("bangsa sapi")) {
			mFragment = new LaporanSapiBerdasarkanBangsaFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("AI")) {
			mFragment = new LaporanAIFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("kelahiran pedet")) {
			mFragment = new LaporanKelahiranPedetFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		}
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
