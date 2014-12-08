package com.ui.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.ui.common.DatabaseHelper;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.SearchDataSapiResultModel;

public class DetailDataSapiFragment extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	DatabaseHelper mDatabaseHelper;
	SearchDataSapiResultModel mSearchDataSapiResultModel;
	ActionBar actioncBar;
	HomeActivity mActivity;
	

	public DetailDataSapiFragment(HomeActivity activity, Context context, Data_SapiModel data_SapiModel) {
		mContext = context;
		mData_SapiModel = data_SapiModel;
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSearchDataSapiResultModel =  mDatabaseHelper.cariDataSapi(mData_SapiModel.getNIT());
		mActivity = activity;
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
		return inflater.inflate(R.layout.data_sapi_detail_tab, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
		MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
		tabs.setIndicatorColor(mContext.getResources().getColor(R.color.seperatorDataSapi));
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {
		// Context mContextAdapater;
		public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
			// mContextAdapater = context;
		}

		private final String[] TITLES = { "Data Sapi", "Kejadian Beranak",
				"Riwayat Kesehatan", "Kejadian IB", "Perubahan Kepemilikan",
				"Kejadian Kematian" };

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		// @Override
		// public SherlockFragment getItem(int index) {
		// return SuperAwesomeCardFragment.newInstance(index);
		// }

		@Override
		public Fragment getItem(int index) {

			switch (index) {
			case 0:
				return new DataSapiFragment_Tab( mContext, mSearchDataSapiResultModel.getmDataSapi());
			case 1:
				return new KejadianBeranakFragment_Tab(mContext, mSearchDataSapiResultModel.getmKejadianBeranak(), Integer.parseInt(mData_SapiModel.getNIT()));
			case 2:
				return new DataRiwayatKesehatanFragment_Tab(mActivity, mContext, mSearchDataSapiResultModel.getmRiwayatKesehatan(), Integer.parseInt(mData_SapiModel.getNIT()));
			case 3:
				return new DataKejadianIBFragment_Tab(mContext, mSearchDataSapiResultModel.getmKejadianIB(), Integer.parseInt(mData_SapiModel.getNIT()));
			case 4:
				return new PerubahanKepemilikanFragment_Tab(mContext, mSearchDataSapiResultModel.getmPerubahanKepemilikan(), Integer.parseInt(mData_SapiModel.getNIT()));
			case 5:
				return new KejadianKematianFragment_Tab(mActivity,mContext, mSearchDataSapiResultModel.getmKejadianKematian(), Integer.parseInt(mData_SapiModel.getNIT()));
			default:
				return new DataSapiFragment_Tab(mContext, mSearchDataSapiResultModel.getmDataSapi());
			}
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
