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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ui.adapter.Search_KejadianIBAdapter;
import com.ui.asynctask.DeleteKejadianIbSync;
import com.ui.asynctask.EditKejadianIbSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsKejadianIb;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_KejadianIBModel;
import com.ui.model.sync.EditKejadianIBModelSync;

public class DataKejadianIBFragment_Tab extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	ArrayList<Search_KejadianIBModel> arraySearch_KejadianIBModels;
	ListView listView;
	Button buttonAdd;
	int mNit;
	ArrayList<Search_KejadianIBModel> arrayKejadianIB;
	int UNIQUE_FRAGMENT_GROUP_ID = 3;
	DatabaseHelper db;
	SessionManager mSessionManager;

	public DataKejadianIBFragment_Tab(Context context,
			ArrayList<Search_KejadianIBModel> arrayListSearch_KejadianIBModels,
			int nit) {
		mContext = context;
		arraySearch_KejadianIBModels = arrayListSearch_KejadianIBModels;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_kejadian_ib, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.listKejadianIBTab);
		buttonAdd = (Button) rootView.findViewById(R.id.buttonAddKejadianIBTab);

		// Search_KejadianIBAdapter mSearch_KejadianIBAdapter = new
		// Search_KejadianIBAdapter(
		// mContext, R.layout.item_list_search_kejadian_ib,
		// arraySearch_KejadianIBModels);
		arrayKejadianIB = db.getKejadianIB(mNit);
		Search_KejadianIBAdapter mSearch_KejadianIBAdapter = new Search_KejadianIBAdapter(
				mContext, R.layout.item_list_search_kejadian_ib,
				arrayKejadianIB);

		mSearch_KejadianIBAdapter.notifyDataSetChanged();
		listView.setAdapter(mSearch_KejadianIBAdapter);
		registerForContextMenu(listView);

		if ((mSessionManager.getPeran().equalsIgnoreCase("aho")) ||
				(mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
				(mSessionManager.getPeran().equalsIgnoreCase("dinas")) ||
				(mSessionManager.getPeran().equalsIgnoreCase("dprov")) ||
				(mSessionManager.getPeran().equalsIgnoreCase("dkota")))
		{
			buttonAdd.setVisibility(View.GONE);
			buttonAdd.setEnabled(false);
		}else if ((mSessionManager.getPeran().equalsIgnoreCase("ins")) ||
		     (mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			buttonAdd.setVisibility(View.VISIBLE);
		}	
		
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddKejadianIB(mContext, mNit);
				General.replaceFragmentAddBackStack(fragment, getActivity()
						.getSupportFragmentManager());
			}
		});
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if ((mSessionManager.getPeran().equalsIgnoreCase("ins"))||
				(mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			if (v.getId() == R.id.listKejadianIBTab) {
				AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				menu.setHeaderTitle("Kode Straw : "
						+ arrayKejadianIB.get(info.position).getKodeStraw());
				String[] menuItems = getResources().getStringArray(R.array.menu);
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
			String[] menuItems = getResources().getStringArray(
					R.array.menu_kejadian_ib);
			String menuItemName = menuItems[menuItemIndex];

			if (menuItemName.equalsIgnoreCase("Edit Kejadian IB")) {
				// mode edit
				Fragment mFragment = new AddKejadianIB(mContext, mNit,arrayKejadianIB.get(info.position));
				General.replaceFragmentAddBackStack(mFragment, getActivity()
						.getSupportFragmentManager());
			} else if (menuItemName.equalsIgnoreCase("delete")) {
				final Search_KejadianIBModel mSearch_KejadianIBModel =  arrayKejadianIB.get(info.position);
				
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data "+ " Kode Straw : "+ mSearch_KejadianIBModel.getKodeStraw())
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								EditKejadianIBModelSync mEditKejadianIBModelSync = new EditKejadianIBModelSync();
								mEditKejadianIBModelSync.setKode_kejadian_ib(mSearch_KejadianIBModel.getKode_kejadian_ib());
//								mEditKejadianIBModelSync.setKode_straw(edtKodeStraw.getText().toString());
//								mEditKejadianIBModelSync.setTanggal_ib(edtTanggalIb.getText().toString());
//								mEditKejadianIBModelSync.setNit(mNit);
								mEditKejadianIBModelSync.setId_petugas(db.getIdPetugas(mSessionManager.getUserame()));
								mEditKejadianIBModelSync.setUser(mSessionManager.getUserame());
								mEditKejadianIBModelSync.setPass(mSessionManager.getPassword());
								mEditKejadianIBModelSync.setGuid(General.generateGuid());

								DeleteKejadianIbSync mDeleteKejadianIbSync = new DeleteKejadianIbSync(
										getActivity(), mContext, mEditKejadianIBModelSync);
								mDeleteKejadianIbSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = mDeleteKejadianIbSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
//										String id = jsonObject.getString("kode_kejadian_ib");
										
										elsKejadianIb mElsKejadianIb = new elsKejadianIb();
										mElsKejadianIb.setKode_kejadian_ib(mSearch_KejadianIBModel.getKode_kejadian_ib());
										db.deleteKejadianIB(mElsKejadianIb);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								arrayKejadianIB = db.getKejadianIB(mNit);
								Search_KejadianIBAdapter mSearch_KejadianIBAdapter = new Search_KejadianIBAdapter(
										mContext, R.layout.item_list_search_kejadian_ib,
										arrayKejadianIB);

								mSearch_KejadianIBAdapter.notifyDataSetChanged();
								listView.setAdapter(mSearch_KejadianIBAdapter);
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
