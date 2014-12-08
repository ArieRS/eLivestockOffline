package com.ui.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ui.adapter.Search_RiwayatKesehatanAdapter;
import com.ui.asynctask.DeletePemeriksaanKesehatanSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsPemeriksaanKesehatan;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;
import com.ui.model.sync.EditRiwayatKesehatanModelSync;

public class DataRiwayatKesehatanFragment_Tab extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	ArrayList<Search_RiwayatKesehatanModel> arraySearch_RiwayatKesehatanModels;
	ListView listView;
	Button button;
	int mNit;
	DatabaseHelper db;
	ArrayList<Search_RiwayatKesehatanModel> arrayRiwayatKesehatan;
	int UNIQUE_FRAGMENT_GROUP_ID = 2;
	SessionManager sessionManager;
	HomeActivity mActivity;
	
	public DataRiwayatKesehatanFragment_Tab(HomeActivity activity, Context context,
			ArrayList<Search_RiwayatKesehatanModel> arrayListRiwayatKesehatan,
			int nit) {
		mContext = context;
		arraySearch_RiwayatKesehatanModels = arrayListRiwayatKesehatan;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		sessionManager  = new SessionManager(mContext);
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_riwayat_kesehatan,
				container, false);
		listView = (ListView) rootView
				.findViewById(R.id.listRiwayatKesehatanTab);
		button = (Button) rootView
				.findViewById(R.id.buttonAddriwayatKesehatanTab);
		// Search_RiwayatKesehatanAdapter mSearch_RiwayatKesehatanAdapter = new
		// Search_RiwayatKesehatanAdapter(
		// mContext, R.layout.item_list_search_riwayat_kesehatan,
		// arraySearch_RiwayatKesehatanModels);
		
		if ( (sessionManager.getPeran().equalsIgnoreCase("aho")) ||
				(sessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			button.setVisibility(View.VISIBLE);
			button.setEnabled(true);
		}else if ((sessionManager.getPeran().equalsIgnoreCase("ppt")) ||
				 (sessionManager.getPeran().equalsIgnoreCase("ins")) ||
				 (sessionManager.getPeran().equalsIgnoreCase("dinas")) ||
				 (sessionManager.getPeran().equalsIgnoreCase("dprov")) ||
				 (sessionManager.getPeran().equalsIgnoreCase("dkota")))
				 
		{
			button.setVisibility(View.GONE);
			button.setEnabled(false);
		}

		arrayRiwayatKesehatan = db.getPemeriksaanKesehatan(mNit);
		Search_RiwayatKesehatanAdapter mSearch_RiwayatKesehatanAdapter = new Search_RiwayatKesehatanAdapter(
				mContext, R.layout.item_list_search_riwayat_kesehatan, arrayRiwayatKesehatan);

		mSearch_RiwayatKesehatanAdapter.notifyDataSetChanged();
		listView.setAdapter(mSearch_RiwayatKesehatanAdapter);
		registerForContextMenu(listView);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddRiwayatKesehatan(mContext, mNit);
				General.replaceFragmentAddBackStack(fragment, getActivity().getSupportFragmentManager());
			}
		});
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if ((sessionManager.getPeran().equalsIgnoreCase("aho")) ||
			(sessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			if (v.getId() == R.id.listRiwayatKesehatanTab) {
				AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				menu.setHeaderTitle("Kode Pemeriksaan Kesehatan : " + arrayRiwayatKesehatan.get(info.position).getKodePemeriksaanKesehatan());
				String[] menuItems = getResources().getStringArray(R.array.menu_riwayat_kesehatan);
				for (int i = 0; i < menuItems.length; i++) {
					menu.add(UNIQUE_FRAGMENT_GROUP_ID, i, i, menuItems[i]);
				}
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getGroupId() == UNIQUE_FRAGMENT_GROUP_ID) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			int menuItemIndex = item.getItemId();
			String[] menuItems = getResources().getStringArray(R.array.menu_riwayat_kesehatan);
			String menuItemName = menuItems[menuItemIndex];

			if (menuItemName.equalsIgnoreCase("Edit Riwayat")) {
				// mode edit
				Fragment mFragment = new AddRiwayatKesehatan(mActivity, mContext, mNit,
						arrayRiwayatKesehatan.get(info.position));
				General.replaceFragmentAddBackStack(mFragment, getActivity()
						.getSupportFragmentManager());
			} else if (menuItemName.equalsIgnoreCase("delete")) {
				final Search_RiwayatKesehatanModel tempSearch_RiwayatKesehatanModel =  arrayRiwayatKesehatan.get(info.position);
				
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data "+ " Kode Pemeriksaan kesehatan : "+ tempSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan())
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								EditRiwayatKesehatanModelSync editRiwayatKesehatanModelSync = new EditRiwayatKesehatanModelSync();
								editRiwayatKesehatanModelSync.setKode_periksaan_kesehatan(Integer.parseInt(tempSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan()));
								editRiwayatKesehatanModelSync.setUser(sessionManager.getUserame());
								editRiwayatKesehatanModelSync.setPass(sessionManager.getPassword());
								editRiwayatKesehatanModelSync.setGuid(General.generateGuid());
								
								DeletePemeriksaanKesehatanSync deletePemeriksaanKesehatanSync = new DeletePemeriksaanKesehatanSync(getActivity(), mContext, editRiwayatKesehatanModelSync);
								deletePemeriksaanKesehatanSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = deletePemeriksaanKesehatanSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
//										String id = jsonObject.getString("kode_pemeriksaan_kesehatan");
										elsPemeriksaanKesehatan mElsPemeriksaanKesehatan = new elsPemeriksaanKesehatan();
										mElsPemeriksaanKesehatan.setKode_pemeriksaan_kesehatan(Integer.parseInt(tempSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan()));
										db.deletePemeriksaanKesehatan(mElsPemeriksaanKesehatan);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								arrayRiwayatKesehatan = db.getPemeriksaanKesehatan(mNit);
								Search_RiwayatKesehatanAdapter mSearch_RiwayatKesehatanAdapter = new Search_RiwayatKesehatanAdapter(
										mContext, R.layout.item_list_search_riwayat_kesehatan,
										arrayRiwayatKesehatan);

								mSearch_RiwayatKesehatanAdapter.notifyDataSetChanged();
								listView.setAdapter(mSearch_RiwayatKesehatanAdapter);
							}
						}).create().show();
			}
		}
		return super.onContextItemSelected(item);
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
