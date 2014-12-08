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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ui.adapter.MasterLokasiAdapter;
import com.ui.asynctask.DeleteMasterLokasiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsLokasi;
import com.ui.model.laporan.LaporanLokasiSapiModel;
import com.ui.model.sync.EditMasterLokasiModelSync;

public class MasterLokasiDetailFragment extends Fragment {

	ListView listView;
	MasterLokasiAdapter adapter;
	Context mContext;

	SherlockFragmentActivity activity;
	ActionBar actioncBar;
	String mURL = "";
	DatabaseHelper db;
	Button buttonView, buttonAdd;
	SessionManager sessionManager;
	ArrayList<LaporanLokasiSapiModel> arraylaporanLokasi;

	public MasterLokasiDetailFragment(Context context) {
		mContext = context;
		activity = (SherlockFragmentActivity) mContext;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// actioncBar = activity.getSupportActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View resultView = inflater.inflate(
				R.layout.list_view_fragment_add_master, container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView.findViewById(R.id.ListFragment);
		buttonView = (Button) resultView.findViewById(R.id.viewDataMaster);
		buttonView.setText(R.string.data_lokasi);

		buttonAdd = (Button) resultView.findViewById(R.id.buttonAdd);
		buttonAdd.setText(R.string.addDataLokasi);

		arraylaporanLokasi = db.getDataLokasi(sessionManager.getIdKabupatenKota(),sessionManager.getIdProvinsi(),sessionManager.getIdLevelAdmin());
		adapter = new MasterLokasiAdapter(mContext,R.layout.item_list_with_icon_master_lokasi, arraylaporanLokasi);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		registerForContextMenu(listView);

		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddLokasiFragment(mContext);
				General.replaceFragmentAddBackStack(fragment,
						getFragmentManager());
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menu);
		String menuItemName = menuItems[menuItemIndex];

		if (menuItemName.equalsIgnoreCase("edit")) {
			// mode edit
			Fragment fragment = new AddLokasiFragment(mContext, arraylaporanLokasi.get(info.position));
			General.replaceFragmentAddBackStack(fragment, getFragmentManager());
		} else if (menuItemName.equalsIgnoreCase("delete")) {
			final LaporanLokasiSapiModel tempLaporanLokasiSapiModel =  arraylaporanLokasi.get(info.position);
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Delete?")
			.setMessage("Anda yakin menghapus data "+ "ID Lokasi : "+arraylaporanLokasi.get(info.position)
					.getIdLokasi())
			.setNegativeButton(android.R.string.no, null)
			.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1) {
							
							EditMasterLokasiModelSync mEditMasterLokasiModelSync = new EditMasterLokasiModelSync();
							mEditMasterLokasiModelSync.setIdl(Integer.parseInt(tempLaporanLokasiSapiModel.getIdLokasi()));
							mEditMasterLokasiModelSync.setUser(sessionManager.getUserame());
							mEditMasterLokasiModelSync.setPass(sessionManager.getPassword());
							mEditMasterLokasiModelSync.setGuid(General.generateGuid());
							
							DeleteMasterLokasiSync deleteMasterLokasiSync = new DeleteMasterLokasiSync(getActivity(), mContext, mEditMasterLokasiModelSync);
							deleteMasterLokasiSync.execute();
							
							String resultJson;
							JSONObject jsonObject;
							try {
								resultJson = deleteMasterLokasiSync.get().toString();
								jsonObject = new JSONObject(resultJson);
								String status = jsonObject.getString("message");
								if(status.equalsIgnoreCase("sukses")) {
//									String id = jsonObject.getString("idLokasi");
									//delete
									db.deleteLokasi(tempLaporanLokasiSapiModel.getIdLokasi());
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							
							arraylaporanLokasi = db.getDataLokasi(sessionManager.getIdKabupatenKota(),sessionManager.getIdProvinsi(),sessionManager.getIdLevelAdmin());
							adapter = new MasterLokasiAdapter(mContext,R.layout.item_list_with_icon_master_lokasi, arraylaporanLokasi);
							adapter.notifyDataSetChanged();
							listView.setAdapter(adapter);
						}
					}).create().show();
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.ListFragment) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("ID Lokasi : "+arraylaporanLokasi.get(info.position)
					.getIdLokasi());
			String[] menuItems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	// public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem
	// item) {
	// Toast.makeText(mContext, "no overide", Toast.LENGTH_LONG).show();
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// actioncBar.setDisplayHomeAsUpEnabled(false);
	// actioncBar.setHomeButtonEnabled(false);
	// Fragment fragment = new MasterFragment(mContext);
	// General.replaceFragment(fragment, getFragmentManager());
	// break;
	// default:
	// actioncBar.setDisplayHomeAsUpEnabled(false);
	// actioncBar.setHomeButtonEnabled(false);
	// break;
	// }
	// Log.v("backActionBar", "onOptionsItemSelected");
	// return super.onOptionsItemSelected((MenuItem) item);
	// }
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// Toast.makeText(mContext, "overide", Toast.LENGTH_LONG).show();
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// actioncBar.setDisplayHomeAsUpEnabled(false);
	// actioncBar.setHomeButtonEnabled(false);
	// Fragment fragment = new MasterFragment(mContext);
	// General.replaceFragment(fragment, getFragmentManager());
	// break;
	// default:
	// actioncBar.setDisplayHomeAsUpEnabled(false);
	// actioncBar.setHomeButtonEnabled(false);
	// break;
	// }
	// Log.v("backActionBar", "onOptionsItemSelected");
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(mContext, "overide", Toast.LENGTH_LONG).show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
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
