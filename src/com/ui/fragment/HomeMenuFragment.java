package com.ui.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ui.common.General;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.moncong.sapi.IndentifyFragment;

public class HomeMenuFragment extends Fragment{
	Context mContext;
	Button buttonNit, buttonMoncongSapi;
	ActionBar actioncBar;
	
	public HomeMenuFragment(HomeActivity activity,  Context context){
		mContext = context;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home_menu, container, false);
		buttonNit = (Button) rootView.findViewById(R.id.buttonCariByNIT);
		buttonMoncongSapi = (Button) rootView.findViewById(R.id.buttonCariByMoncong);
		
		buttonNit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment mFragment = new HomeFragment(mContext);
				General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
			}
		});
		buttonMoncongSapi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment mFragment = new IndentifyFragment(mContext);
				General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
			}
		});
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
