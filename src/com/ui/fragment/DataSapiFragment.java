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
import android.opengl.Visibility;
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
import android.widget.RelativeLayout.LayoutParams;

import com.ui.adapter.DataSapiAdapter;
import com.ui.asynctask.DeleteDataSapiBetinaSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.HomeActivity;
import com.ui.elivestock.R;
import com.ui.model.database.elsSapiBetina;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.sync.EditDataSapiModelSync;
import com.ui.moncong.sapi.RegisterFragment;

public class DataSapiFragment extends Fragment {

	Context mContext;
	DatabaseHelper mDatabaseHelper;
	ListView listViewDataSapi;
	ArrayList<Data_SapiModel> mData_SapiList;
	Button button;
	String mUrl = "";
	int UNIQUE_FRAGMENT_GROUP_ID = 0;
	SessionManager sessionManager;
	DatabaseHelper db;
	ActionBar actioncBar;
	Activity activity;
	HomeActivity mActivity;

	public DataSapiFragment(HomeActivity activity, Context context) {
		this.mContext = context;
		mActivity = activity;
		mDatabaseHelper = new DatabaseHelper(mContext);
		sessionManager = new SessionManager(mContext);
		db = new DatabaseHelper(mContext);
	}

	public static final String TAG = DataSapiFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.data_sapi_list_activity,
				container, false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		listViewDataSapi = (ListView) rootView.findViewById(R.id.ListDataSapi);
		button = (Button) rootView.findViewById(R.id.buttonAddDataSapi);

		mData_SapiList = mDatabaseHelper.getDataSapi(sessionManager.getIdKabupatenKota(),sessionManager.getIdProvinsi(), sessionManager.getIdLevelAdmin());
		DataSapiAdapter mDataSapiAdapter = new DataSapiAdapter(mContext,R.layout.item_list_with_icon_data_sapi, mData_SapiList);
		mDataSapiAdapter.notifyDataSetChanged();
		listViewDataSapi.setAdapter(mDataSapiAdapter);
		listViewDataSapi.setOnItemClickListener(new ListDataSapiClickListener());
		registerForContextMenu(listViewDataSapi);
		
		if (sessionManager.getPeran().equalsIgnoreCase("aho"))
		{
			button.setVisibility(View.INVISIBLE);
			button.setEnabled(false);
			
		}else if ((sessionManager.getPeran().equalsIgnoreCase("ppt"))||
				(sessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			button.setVisibility(View.VISIBLE);
			button.setEnabled(true);
		}else if (sessionManager.getPeran().equalsIgnoreCase("ins"))
		{
			button.setVisibility(View.INVISIBLE);
		}else if (sessionManager.getPeran().equalsIgnoreCase("dinas"))
		{
			button.setVisibility(View.INVISIBLE);
		}else if (sessionManager.getPeran().equalsIgnoreCase("dprov"))	
		{
			button.setVisibility(View.INVISIBLE);
		}else if (sessionManager.getPeran().equalsIgnoreCase("dkota")){
			button.setVisibility(View.INVISIBLE);
		}
			
		// add data sapi betina
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment mFragment = new AddDataSapiFragment(mContext);
				General.replaceFragmentAddBackStack(mFragment,
						getFragmentManager());
			}
		});
	}

	private class ListDataSapiClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Fragment fragment = new DetailDataSapiFragment(mActivity, mContext, mData_SapiList.get(position));
			if (fragment != null) {
				General.replaceFragmentAddBackStack(fragment,getFragmentManager());
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		if ( (sessionManager.getPeran().equalsIgnoreCase("ppt")) ||
				(sessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			if (v.getId() == R.id.ListDataSapi) {
				AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				menu.setHeaderTitle("NIT : " + mData_SapiList.get(info.position).getNIT());
				String[] menuItems = getResources().getStringArray(
						R.array.menu_data_sapi);
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
			String[] menuItems = getResources().getStringArray(R.array.menu_data_sapi);
			String menuItemName = menuItems[menuItemIndex];

			if (menuItemName.equalsIgnoreCase("Register Moncong Sapi")) {
				Fragment mFragment = new RegisterFragment(mContext, mData_SapiList.get(info.position));
				General.replaceFragmentAddBackStack(mFragment,
						getFragmentManager());
			} else if (menuItemName.equalsIgnoreCase("edit")) {
				// mode edit
				Fragment mFragment = new AddDataSapiFragment(mContext, mData_SapiList.get(info.position));
				General.replaceFragmentAddBackStack(mFragment, getFragmentManager());
			} else if (menuItemName.equalsIgnoreCase("delete")) {
				final Data_SapiModel tempData_SapiModel =  mData_SapiList.get(info.position);
				
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data "+ " NIT : "+ tempData_SapiModel.getNIT())
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								EditDataSapiModelSync editDataSapiModelSync = new EditDataSapiModelSync();
								editDataSapiModelSync.setNit(Integer.parseInt(tempData_SapiModel.getNIT()));
								editDataSapiModelSync.setUser(sessionManager.getUserame());
								editDataSapiModelSync.setPass(sessionManager.getPassword());
								editDataSapiModelSync.setGuid(General.generateGuid());
								
								DeleteDataSapiBetinaSync deleteDataSapiBetinaSync = new DeleteDataSapiBetinaSync(getActivity(), mContext, editDataSapiModelSync);
								deleteDataSapiBetinaSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = deleteDataSapiBetinaSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {										
										elsSapiBetina mElsSapiBetina = new elsSapiBetina();
										mElsSapiBetina.setNit(Integer.parseInt(tempData_SapiModel.getNIT()));
										db.deleteDataSapiBetina(mElsSapiBetina);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								mData_SapiList = mDatabaseHelper.getDataSapi(sessionManager.getIdKabupatenKota(),sessionManager.getIdProvinsi(), sessionManager.getIdLevelAdmin());
								DataSapiAdapter mDataSapiAdapter = new DataSapiAdapter(mContext,R.layout.item_list_with_icon_data_sapi, mData_SapiList);
								mDataSapiAdapter.notifyDataSetChanged();
								listViewDataSapi.setAdapter(mDataSapiAdapter);
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