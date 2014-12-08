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
import android.util.Log;
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

import com.ui.adapter.MasterProvinsiAdapter;
import com.ui.asynctask.DeleteMasterProvinsiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.elsProvinsi;
import com.ui.model.sync.EditMasterProvinsiModelSync;

public class MasterProvinsiFragment extends Fragment {

	ListView listView;
	MasterProvinsiAdapter adapter;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	String mUrl = "";
	SessionManager sessionManager;
	Button addProvinsi;
	ArrayList<elsProvinsi> arrayProvinsi;
	DatabaseHelper db;

	public MasterProvinsiFragment(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		// actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);

		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// for layout
		View resultView = inflater.inflate(
				R.layout.list_view_fragment_provinsi, container, false);

		setLayout(inflater, resultView);
		return resultView;
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		listView = (ListView) resultView
				.findViewById(R.id.ListFragmentProvinsi);
		addProvinsi = (Button) resultView.findViewById(R.id.buttonAddProvinsi);

		arrayProvinsi = db.getAllProvinsi();
		adapter = new MasterProvinsiAdapter(mContext,R.layout.item_list_with_icon, arrayProvinsi);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		registerForContextMenu(listView);
		
		setAddProvinsiClick();
	}

	private void setAddProvinsiClick() {
		addProvinsi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new AddProvinsiFragment(mContext);
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
			Fragment mFragment = new AddProvinsiFragment(mContext,
					arrayProvinsi.get(info.position));
			General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
		} else if (menuItemName.equalsIgnoreCase("delete")) {
			final elsProvinsi tempElsProvinsi =  arrayProvinsi.get(info.position);
			
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Delete?")
			.setMessage("Anda yakin menghapus data "+ arrayProvinsi.get(info.position).getNama_provinsi())
			.setNegativeButton(android.R.string.no, null)
			.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0,
								int arg1) {
							
							EditMasterProvinsiModelSync editMasterProvinsiModelSync = new EditMasterProvinsiModelSync();
							editMasterProvinsiModelSync.setId_provinsi(tempElsProvinsi.getId_provinsi());
//							editMasterProvinsiModelSync.setNama_provinsi(tempElsProvinsi.getText().toString());
							editMasterProvinsiModelSync.setUser(sessionManager.getUserame());
							editMasterProvinsiModelSync.setPass(sessionManager.getPassword());
							editMasterProvinsiModelSync.setGuid(General.generateGuid());
							
							DeleteMasterProvinsiSync deleteMasterProvinsiSync = new DeleteMasterProvinsiSync(getActivity(), mContext, editMasterProvinsiModelSync);
							deleteMasterProvinsiSync.execute();
							
							String resultJson;
							JSONObject jsonObject;
							try {
								resultJson = deleteMasterProvinsiSync.get().toString();
								jsonObject = new JSONObject(resultJson);
								String status = jsonObject.getString("message");
								if(status.equalsIgnoreCase("sukses")) {
//									String id = jsonObject.getString("id_provinsi");
									//delete
									db.deleteElsProvinsi(tempElsProvinsi.getId_provinsi());
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							
							//set list again
							arrayProvinsi = db.getAllProvinsi();
							adapter = new MasterProvinsiAdapter(mContext,R.layout.item_list_with_icon, arrayProvinsi);
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

		if (v.getId() == R.id.ListFragmentProvinsi) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle(arrayProvinsi.get(info.position)
					.getNama_provinsi());
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
		Log.d("backActionBar", "Back");
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
