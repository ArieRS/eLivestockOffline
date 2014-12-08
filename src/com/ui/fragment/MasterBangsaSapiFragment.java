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

import com.ui.adapter.MasterBangsaSapiAdapter;
import com.ui.asynctask.DeleteMasterBangsaSapiSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.database.masterBangsaSapi;
import com.ui.model.sync.EditMasterBangsaModelSync;

public class MasterBangsaSapiFragment extends Fragment {

	ListView listView;
	MasterBangsaSapiAdapter adapter;
	Context mContext;
	Activity activity;
	ActionBar actioncBar;
	String mUrl = "";

	Button mButtonAddBangsa;
	DatabaseHelper db;
	ArrayList<masterBangsaSapi> arrayMasterBangsaSapi;
	SessionManager sessionManager;

	public MasterBangsaSapiFragment(Context context) {
		mContext = context;
		db = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View resultView = inflater.inflate(
				R.layout.list_view_fragment_bangsa_sapi, container, false);
		setLayout(inflater, resultView);
		return resultView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity) mContext;
		 actioncBar = activity.getActionBar();
		// actioncBar.setHomeButtonEnabled(true);
		// actioncBar.setDisplayHomeAsUpEnabled(true);
		// setHasOptionsMenu(true);
	}

	private void setLayout(LayoutInflater inflater, View resultView) {
		
		listView = (ListView) resultView
				.findViewById(R.id.ListFragmentBangsaSapi);
		arrayMasterBangsaSapi = db.getAllBangsaSapi();
		
		adapter = new MasterBangsaSapiAdapter(mContext,
				R.layout.item_list_with_icon, arrayMasterBangsaSapi);
		
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		registerForContextMenu(listView);
		
		mButtonAddBangsa = (Button) resultView
				.findViewById(R.id.buttonAddBangsaSapi);
		mButtonAddBangsa.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment mFragment = new AddBangsaSapiFragment(mContext);
				General.replaceFragmentAddBackStack(mFragment,
						getFragmentManager());
			}
		});
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
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		String[] menuItems = getResources().getStringArray(R.array.menu);
		String menuItemName = menuItems[menuItemIndex];
		
		if(menuItemName.equalsIgnoreCase("edit")){
			//mode edit
			Fragment mFragment = new AddBangsaSapiFragment(mContext, arrayMasterBangsaSapi.get(info.position));
			General.replaceFragmentAddBackStack(mFragment,
					getFragmentManager());
		}else if(menuItemName.equalsIgnoreCase("delete")){
			final masterBangsaSapi tempMasterBangsaSapi = arrayMasterBangsaSapi.get(info.position);
			
			new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Delete?")
			.setMessage("Anda yakin menghapus data "+ "Bangsa = " +arrayMasterBangsaSapi.get(info.position).getValue())
			.setNegativeButton(android.R.string.no, null)
			.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0,
								int arg1) {
							
							EditMasterBangsaModelSync editMasterBangsaSapiModel = new EditMasterBangsaModelSync();
							editMasterBangsaSapiModel.setId(tempMasterBangsaSapi.getId());
//							editMasterBangsaSapiModel.setValue(edtNamaBangsa.getText().toString());
							editMasterBangsaSapiModel.setUser(sessionManager.getUserame());
							editMasterBangsaSapiModel.setPass(sessionManager.getPassword());
							editMasterBangsaSapiModel.setGuid(General.generateGuid());
							DeleteMasterBangsaSapiSync deleteMasterBangsaSapiSync  = new DeleteMasterBangsaSapiSync(getActivity(), mContext, editMasterBangsaSapiModel);
							deleteMasterBangsaSapiSync.execute();
							
							String resultJson;
							JSONObject jsonObject;
							try {
								resultJson = deleteMasterBangsaSapiSync.get().toString();
								jsonObject = new JSONObject(resultJson);
								String status = jsonObject.getString("message");
								if(status.equalsIgnoreCase("sukses")) {
//									String id = jsonObject.getString("id");
									//delete
									db.deleteBangsaSapi(tempMasterBangsaSapi.getId());
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
							
							//set notify data change
							arrayMasterBangsaSapi = db.getAllBangsaSapi();
							adapter = new MasterBangsaSapiAdapter(mContext,R.layout.item_list_with_icon, arrayMasterBangsaSapi);
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
		if (v.getId() == R.id.ListFragmentBangsaSapi) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Bangsa = " +arrayMasterBangsaSapi.get(info.position).getValue());
			String[] menuItems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
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
