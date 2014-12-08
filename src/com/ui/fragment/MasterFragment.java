package com.ui.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment.SavedState;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ui.adapter.ArrayStringAdapter;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;

public class MasterFragment extends Fragment {

	ArrayStringAdapter adapter;
	Context mContext;

	String[] masterString; 
	SessionManager sessionManager;
	ActionBar actioncBar;
	Activity activity;

	public MasterFragment(Context context) {
		mContext = context;

		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
		
		sessionManager = new SessionManager(mContext);
		
		if (sessionManager.getPeran().equalsIgnoreCase("ppt")){
			masterString = new String[] { "Lokasi"};
		}
		else{
			masterString = new String[] { "Lokasi", "Kabupaten/Kota",
					"Provinsi", "Bangsa Sapi" };
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
				R.layout.single_item_list, masterString);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View mView,
					int position, long arg3) {
				onListClick(mView, position, arg3);
			}
		});
	}

	private void onListClick(View mView, int Position, Long arg3) {
		String listArray = masterString[Position];

		Fragment mFragment;
		if (listArray.equalsIgnoreCase("lokasi")) {
			mFragment = new MasterLokasiDetailFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("kabupaten/kota")) {
			mFragment = new MasterKabupatenKotaFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("provinsi")) {
			mFragment = new MasterProvinsiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (listArray.equalsIgnoreCase("Bangsa Sapi")) {
			mFragment = new MasterBangsaSapiFragment(mContext);
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(mContext, "overide", Toast.LENGTH_LONG).show();
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
