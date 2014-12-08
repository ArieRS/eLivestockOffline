package com.ui.fragment;

import java.util.ArrayList;

import com.ui.adapter.Search_DataSapiAdapter;
import com.ui.adapter.Search_KejadianBeranakAdapter;
import com.ui.adapter.Search_KejadianIBAdapter;
import com.ui.adapter.Search_KematianAdapter;
import com.ui.adapter.Search_PerubahanKepemilikanAdapter;
import com.ui.adapter.Search_RiwayatKesehatanAdapter;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.laporan.SearchDataSapiResultModel;
import com.ui.model.laporan.Search_DataSapiModel;
import com.ui.model.laporan.Search_KejadianBeranakModel;
import com.ui.model.laporan.Search_KejadianIBModel;
import com.ui.model.laporan.Search_KejadianKematianModel;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SearchDataSapiResultFragment extends Fragment {

	ActionBar actioncBar;
	Activity activity;

	Context mContext;
	SearchDataSapiResultModel mSearchDataSapiResultModel;

	ListView listDatasapi, listKejadianBeranak, listRiwayatKesehatan,
			listKejadianIB, listPerubahanKepemilikan, listKematian;

	public SearchDataSapiResultFragment(Context context,
			SearchDataSapiResultModel searchDataSapiResultModel) {
		mContext = context;
		mSearchDataSapiResultModel = searchDataSapiResultModel;
		General.hideKeyboard((Activity)mContext);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		activity = (Activity) mContext;
//		actioncBar = activity.getActionBar();
//		actioncBar.setHomeButtonEnabled(true);
//		actioncBar.setDisplayHomeAsUpEnabled(true);
//		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.search_result_sapi,
				container, false);
		listDatasapi = (ListView) rootView
				.findViewById(R.id.listSearchDataSapi);
		listKejadianBeranak = (ListView) rootView
				.findViewById(R.id.listSearchKejadianBeranak);
		listRiwayatKesehatan = (ListView) rootView
				.findViewById(R.id.listSearchRiwayatKesehatan);
		listKejadianIB = (ListView) rootView
				.findViewById(R.id.listSearchKejadianIB);
		listPerubahanKepemilikan = (ListView) rootView
				.findViewById(R.id.listSearchPerubahanKepemilikan);
		listKematian = (ListView) rootView
				.findViewById(R.id.listSearchKejadianKematian);

		ArrayList<Search_DataSapiModel> mSearch_DataSapiModelList = mSearchDataSapiResultModel
				.getmDataSapi();
		Search_DataSapiAdapter mSearch_DataSapiAdapter = new Search_DataSapiAdapter(
				mContext, R.layout.item_list_search_data_sapi,
				mSearch_DataSapiModelList);
		listDatasapi.setAdapter(mSearch_DataSapiAdapter);

		ArrayList<Search_KejadianBeranakModel> mSearch_KejadianBeranakList = mSearchDataSapiResultModel
				.getmKejadianBeranak();
		Search_KejadianBeranakAdapter mSearch_KejadianBeranakAdapter = new Search_KejadianBeranakAdapter(
				mContext, R.layout.item_list_search_kejadian_beranak,
				mSearch_KejadianBeranakList);
		listKejadianBeranak.setAdapter(mSearch_KejadianBeranakAdapter);

		ArrayList<Search_RiwayatKesehatanModel> mSearch_RiwayatKesehatanList = mSearchDataSapiResultModel
				.getmRiwayatKesehatan();
		Search_RiwayatKesehatanAdapter mSearch_RiwayatKesehatanAdapter = new Search_RiwayatKesehatanAdapter(
				mContext, R.layout.item_list_search_riwayat_kesehatan,
				mSearch_RiwayatKesehatanList);
		listRiwayatKesehatan.setAdapter(mSearch_RiwayatKesehatanAdapter);

		ArrayList<Search_KejadianIBModel> mSearch_KejadianIBList = mSearchDataSapiResultModel
				.getmKejadianIB();
		Search_KejadianIBAdapter mSearch_KejadianIBAdapter = new Search_KejadianIBAdapter(
				mContext, R.layout.item_list_search_kejadian_ib,
				mSearch_KejadianIBList);
		listKejadianIB.setAdapter(mSearch_KejadianIBAdapter);

		ArrayList<Search_PerubahanKepemilikanModel> mPerubahanKepemilikanList = mSearchDataSapiResultModel
				.getmPerubahanKepemilikan();
		Search_PerubahanKepemilikanAdapter mSearch_PerubahanKepemilikanAdapter = new Search_PerubahanKepemilikanAdapter(
				mContext, R.layout.item_list_search_perubahan_kepemilikan,
				mPerubahanKepemilikanList);
		listPerubahanKepemilikan
				.setAdapter(mSearch_PerubahanKepemilikanAdapter);

		ArrayList<Search_KejadianKematianModel> mSearch_KejadianKematianList = mSearchDataSapiResultModel.getmKejadianKematian();
		Search_KematianAdapter mSearch_KematianAdapter = new Search_KematianAdapter(
				mContext, R.layout.item_list_search_kejadian_kematian,
				mSearch_KejadianKematianList);
		listKematian.setAdapter(mSearch_KematianAdapter);
		// ArrayList<Search_DataSapiModel> mDataSapiModels = setData();
		// ArrayList<Search_DataSapiModel> mDataSapiModels1 = setData1();
		// Search_DataSapiAdapter mDataSapiAdapter1 = new
		// Search_DataSapiAdapter(
		// mContext, R.layout.item_list_search_data_sapi, mDataSapiModels1);
		//
		// listKejadianBeranak.setAdapter(mDataSapiAdapter);
		// listRiwayatKesehatan.setAdapter(mDataSapiAdapter);

		// listKejadianBeranak.setOnTouchListener(new View.OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// v.getParent().requestDisallowInterceptTouchEvent(true);
		// return false;
		// }
		// });

		setListViewHeightBasedChilder(listKejadianBeranak);
		setListViewHeightBasedChilder(listRiwayatKesehatan);
		setListViewHeightBasedChilder(listKejadianIB);
		setListViewHeightBasedChilder(listPerubahanKepemilikan);
		setListViewHeightBasedChilder(listKematian);
		
		return rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			actioncBar.setDisplayHomeAsUpEnabled(false);
//			actioncBar.setHomeButtonEnabled(false);
			Fragment fragment = new HomeFragment(mContext);
			General.replaceFragment(fragment, getFragmentManager());
			break;
		default:
			break;
		}
		Log.d("backActionBar", "Back");
		return super.onOptionsItemSelected(item);
	}

	private ArrayList<Search_DataSapiModel> setData() {
		ArrayList<Search_DataSapiModel> test = new ArrayList<Search_DataSapiModel>();
		Search_DataSapiModel mDataSapiModel = new Search_DataSapiModel();
		mDataSapiModel.setNIT("");
		mDataSapiModel.setLokasi("");
		mDataSapiModel.setBangsa("");
		mDataSapiModel.setTanggalLahir("");
		mDataSapiModel.setBentukWajah("");
		mDataSapiModel.setWarna("");
		mDataSapiModel.setStatusPunuk("");
		mDataSapiModel.setStatusAksesorisKaki("");
		mDataSapiModel.setStatusKepimilikan("");

		test.add(mDataSapiModel);
		test.add(mDataSapiModel);
		test.add(mDataSapiModel);
		return test;
	}

	private ArrayList<Search_DataSapiModel> setData1() {
		ArrayList<Search_DataSapiModel> test = new ArrayList<Search_DataSapiModel>();
		Search_DataSapiModel mDataSapiModel = new Search_DataSapiModel();
		mDataSapiModel.setNIT("");
		mDataSapiModel.setLokasi("");
		mDataSapiModel.setBangsa("");
		mDataSapiModel.setTanggalLahir("");
		mDataSapiModel.setBentukWajah("");
		mDataSapiModel.setWarna("");
		mDataSapiModel.setStatusPunuk("");
		mDataSapiModel.setStatusAksesorisKaki("");
		mDataSapiModel.setStatusKepimilikan("");

		test.add(mDataSapiModel);
		return test;
	}

	public static void setListViewHeightBasedChilder(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int desireWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desireWidth,
						LayoutParams.WRAP_CONTENT));
			view.measure(desireWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
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
