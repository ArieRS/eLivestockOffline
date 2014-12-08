package com.ui.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ui.adapter.Search_PerubahanKepemilikanAdapter;
import com.ui.asynctask.DeletePerubahanKepemlikanSync;
import com.ui.asynctask.EditPerubahanKepemlikanSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsPerubahanKepemilikan;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.sync.EditPerubahanKepemilikanModelSync;

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

public class PerubahanKepemilikanFragment_Tab extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	ArrayList<Search_PerubahanKepemilikanModel> arraySearch_PerubahanKepemilikanModels;
	ListView listView;
	Button buttonAdd;
	int mNit;
	int UNIQUE_FRAGMENT_GROUP_ID = 4;
	DatabaseHelper db;
	SessionManager mSessionManager;
	public PerubahanKepemilikanFragment_Tab(
			Context context,
			ArrayList<Search_PerubahanKepemilikanModel> arrayListSearch_PerubahanKepemilikanModels,
			int nit) {
		mContext = context;
		arraySearch_PerubahanKepemilikanModels = arrayListSearch_PerubahanKepemilikanModels;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_perubahan_kepemilikan,container, false);
		listView = (ListView) rootView.findViewById(R.id.listPerubahanKepemilikanTab);
		buttonAdd = (Button) rootView.findViewById(R.id.buttonAddPerubahanKepemilikanTab);

		// Search_PerubahanKepemilikanAdapter mSearch_KejadianIBAdapter = new
		// Search_PerubahanKepemilikanAdapter(
		// mContext, R.layout.item_list_search_perubahan_kepemilikan,
		// arraySearch_PerubahanKepemilikanModels);
		arraySearch_PerubahanKepemilikanModels = db.getPerubahanKepemilikan(mNit);
		Search_PerubahanKepemilikanAdapter mSearch_KejadianIBAdapter = new Search_PerubahanKepemilikanAdapter(
				mContext, R.layout.item_list_search_perubahan_kepemilikan,
				arraySearch_PerubahanKepemilikanModels);
		mSearch_KejadianIBAdapter.notifyDataSetChanged();
		listView.setAdapter(mSearch_KejadianIBAdapter);
		registerForContextMenu(listView);

		if ((mSessionManager.getPeran().equalsIgnoreCase("aho")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("dinas")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("dprov")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("dkota")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("ins")))
		{
			buttonAdd.setVisibility(View.GONE);
			buttonAdd.setEnabled(false);
		}else if ((mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
		         (mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			buttonAdd.setVisibility(View.VISIBLE);
		}
		
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddPerubahanKepemilikan(mContext, mNit);
				General.replaceFragmentAddBackStack(fragment, getActivity().getSupportFragmentManager());
			}
		});
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if ((mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
		(mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			if (v.getId() == R.id.listPerubahanKepemilikanTab) {
				AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				menu.setHeaderTitle("Tanggal Kejadian : "
						+ arraySearch_PerubahanKepemilikanModels.get(info.position)
								.getTanggalKejadian());
				String[] menuItems = getResources().getStringArray(
						R.array.menu_perubahan_kepemilikan);
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
					R.array.menu_perubahan_kepemilikan);
			String menuItemName = menuItems[menuItemIndex];

			if (menuItemName.equalsIgnoreCase("Edit Perubahan Kepemilikan")) {
				// mode edit
				Fragment mFragment = new AddPerubahanKepemilikan(mContext,mNit,arraySearch_PerubahanKepemilikanModels.get(info.position));
				General.replaceFragmentAddBackStack(mFragment, getActivity().getSupportFragmentManager());
			} else if (menuItemName.equalsIgnoreCase("delete")) {
				final Search_PerubahanKepemilikanModel mSearch_PerubahanKepemilikanModel = arraySearch_PerubahanKepemilikanModels.get(info.position);
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data kepemilikan"+ " tanggal : "+ mSearch_PerubahanKepemilikanModel.getTanggalKejadian())
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								EditPerubahanKepemilikanModelSync editPerubahanKepemilikanModelSync = new EditPerubahanKepemilikanModelSync();
								editPerubahanKepemilikanModelSync.setKode_riwayat_kepemilikan(mSearch_PerubahanKepemilikanModel.getKode_riwayat_kepemilikan());
								editPerubahanKepemilikanModelSync.setUser(mSessionManager.getUserame());
								editPerubahanKepemilikanModelSync.setPass(mSessionManager.getPassword());
								editPerubahanKepemilikanModelSync.setGuid(General.generateGuid());

								DeletePerubahanKepemlikanSync deletePerubahanKepemlikanSync = new DeletePerubahanKepemlikanSync(getActivity(), mContext,editPerubahanKepemilikanModelSync);
								deletePerubahanKepemlikanSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = deletePerubahanKepemlikanSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
//										String id = jsonObject.getString("kode_riwayat_kepemilikan");
										elsPerubahanKepemilikan mElsPerubahanKepemilikan = new elsPerubahanKepemilikan();
										mElsPerubahanKepemilikan.setKode_riwayat_kepemilikan(mSearch_PerubahanKepemilikanModel.getKode_riwayat_kepemilikan());
										db.deletePerubahanKepemilikan(mElsPerubahanKepemilikan);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								arraySearch_PerubahanKepemilikanModels = db
										.getPerubahanKepemilikan(mNit);
								Search_PerubahanKepemilikanAdapter mSearch_KejadianIBAdapter = new Search_PerubahanKepemilikanAdapter(
										mContext, R.layout.item_list_search_perubahan_kepemilikan,
										arraySearch_PerubahanKepemilikanModels);
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
