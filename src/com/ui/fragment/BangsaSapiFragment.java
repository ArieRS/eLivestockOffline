package com.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ui.adapter.MasterBangsaSapiAdapter;
import com.ui.elivestock.R;

public class BangsaSapiFragment extends Fragment {

	MasterBangsaSapiAdapter adapter;
	ListView mListView;
	Context mContext;

	public BangsaSapiFragment(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = setLayout(inflater, container);
		return view;
	}

	private View setLayout(LayoutInflater inflater, ViewGroup mView)
	{
		View view = inflater.inflate(R.layout.list_view_fragment_add_master, mView, false);
		mListView = (ListView) mView.findViewById(R.id.ListFragment);
//		adapter = new MasterBangsaSapiAdapter(mContext, R.layout.item_list, )
		return view;
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
