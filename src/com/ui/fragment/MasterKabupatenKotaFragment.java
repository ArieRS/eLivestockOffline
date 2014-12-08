package com.ui.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
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

import com.ui.adapter.MasterKabupatenkotaAdapter;
import com.ui.asynctask.DeleteMasterKabupatenKotaSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.laporan.MasterKabupatenKotaModel;
import com.ui.model.sync.EditMasterKabupatenKotaModelSync;

public class MasterKabupatenKotaFragment extends Fragment {

	ListView listView;
	MasterKabupatenkotaAdapter adapter;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	DatabaseHelper db;
	Button buttonAdd, buttonView;
	ArrayList<MasterKabupatenKotaModel> arrayKabupatenKota;
	SessionManager sessionManager;
	public MasterKabupatenKotaFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		sessionManager = new SessionManager(mContext);
		// actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// layout
		View resultView = inflater.inflate(
				R.layout.list_view_fragment_add_master, container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView.findViewById(R.id.ListFragment);
		buttonView = (Button) resultView.findViewById(R.id.viewDataMaster);
		buttonView.setText(R.string.data_kabupaten_kota);
		buttonAdd = (Button) resultView.findViewById(R.id.buttonAdd);
		buttonAdd.setText(R.string.addDataKabupatenKota);
		buttonAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddKabupatenKotaFragment(mContext);
				General.replaceFragmentAddBackStack(fragment,
						getFragmentManager());
			}
		});
		arrayKabupatenKota = db.getAllNamaKabupatenKotoProvinsi();
		adapter = new MasterKabupatenkotaAdapter(mContext,
				R.layout.item_list_with_icon_master_kabupaten_kota,
				arrayKabupatenKota);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		registerForContextMenu(listView);
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
			Fragment mFragment = new AddKabupatenKotaFragment(mContext,
					arrayKabupatenKota.get(info.position));
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (menuItemName.equalsIgnoreCase("delete")) {
			final MasterKabupatenKotaModel tempKabupatenKotaModel =  arrayKabupatenKota.get(info.position);
			
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Delete?")
			.setMessage("Anda yakin menghapus data "+ arrayKabupatenKota.get(info.position).getNama_Kabupaten_Kota())
			.setNegativeButton(android.R.string.no, null)
			.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1) {
							
							EditMasterKabupatenKotaModelSync editMasterKabupatenKotaModelSync = new EditMasterKabupatenKotaModelSync();
							editMasterKabupatenKotaModelSync.setId_kabupaten_kota(tempKabupatenKotaModel.getId_kabupaten_kota());
							editMasterKabupatenKotaModelSync.setUser(sessionManager.getUserame());
							editMasterKabupatenKotaModelSync.setPass(sessionManager.getPassword());
							editMasterKabupatenKotaModelSync.setGuid(General
									.generateGuid());
							DeleteMasterKabupatenKotaSync deleteMasterKabupatenKotaSync = new DeleteMasterKabupatenKotaSync(getActivity(), mContext, editMasterKabupatenKotaModelSync);
							deleteMasterKabupatenKotaSync.execute();
							
							String resultJson;
							JSONObject jsonObject;
							try {
								resultJson = deleteMasterKabupatenKotaSync.get().toString();
								jsonObject = new JSONObject(resultJson);
								String status = jsonObject.getString("message");
								if(status.equalsIgnoreCase("sukses")) {
//									String id = jsonObject.getString("id_kabupaten_kota");
									//delete
									db.deleteKabupatenKota(tempKabupatenKotaModel.getId_kabupaten_kota());
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							
							arrayKabupatenKota = db.getAllNamaKabupatenKotoProvinsi();
							adapter = new MasterKabupatenkotaAdapter(mContext,
									R.layout.item_list_with_icon_master_kabupaten_kota,
									arrayKabupatenKota);
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
			menu.setHeaderTitle(arrayKabupatenKota.get(info.position)
					.getNama_Provinsi());
			String[] menuItems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			actioncBar.setDisplayHomeAsUpEnabled(false);
//			actioncBar.setHomeButtonEnabled(false);
			Fragment fragment = new MasterFragment(mContext);
			General.replaceFragment(fragment, getFragmentManager());
			break;
		default:
			break;
		}
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
