package com.ui.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ui.adapter.Search_KejadianBeranakAdapter;
import com.ui.asynctask.DeleteKejadianBeranakSync;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.elivestock.R;
import com.ui.model.database.elsKejadianBeranak;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.Search_KejadianBeranakModel;
import com.ui.model.sync.EditKejadianBeranakModelSync;

public class KejadianBeranakFragment_Tab extends Fragment {
	Context mContext;
	Data_SapiModel mData_SapiModel;
	ArrayList<Search_KejadianBeranakModel> arraySearch_KejadianBeranakModels;
	ListView listView;
	Button button;
	DatabaseHelper db;
	int mNit;
	ArrayList<Search_KejadianBeranakModel> arrayKejadianBeranak;
	int UNIQUE_FRAGMENT_GROUP_ID = 1;
	SessionManager mSessionManager;

	public KejadianBeranakFragment_Tab(Context context,
			ArrayList<Search_KejadianBeranakModel> arrayListKejadianBeranak,
			int nit) {
		mContext = context;
		arraySearch_KejadianBeranakModels = arrayListKejadianBeranak;
		mNit = nit;
		db = new DatabaseHelper(mContext);
		mSessionManager = new SessionManager(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.tab_kejadian_beranak,
				container, false);
		listView = (ListView) rootView
				.findViewById(R.id.listKejadianBeranakTab);
		button = (Button) rootView
				.findViewById(R.id.buttonAddKejadianBeranakTab);

		// Search_KejadianBeranakAdapter mAdapter = new
		// Search_KejadianBeranakAdapter(
		// mContext, R.layout.item_list_search_kejadian_beranak,
		// arraySearch_KejadianBeranakModels);
		if ((mSessionManager.getPeran().equalsIgnoreCase("aho")) ||
		(mSessionManager.getPeran().equalsIgnoreCase("ins")) ||
		(mSessionManager.getPeran().equalsIgnoreCase("dinas")) ||
		(mSessionManager.getPeran().equalsIgnoreCase("dprov")) ||
		(mSessionManager.getPeran().equalsIgnoreCase("dkota")))
		{
			button.setVisibility(View.GONE);
			button.setEnabled(false);
		}else if ((mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
				(mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			button.setVisibility(View.VISIBLE);
		}
		
		arrayKejadianBeranak = db.getKejadianBeranak(mNit);
		Search_KejadianBeranakAdapter mAdapter = new Search_KejadianBeranakAdapter(
				mContext, R.layout.item_list_search_kejadian_beranak,
				arrayKejadianBeranak);
		mAdapter.notifyDataSetChanged();
		listView.setAdapter(mAdapter);
		registerForContextMenu(listView);

		final Activity mActivity = (Activity) mContext;
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// General.showDialogSuccess(mContext, "test", "sip");
				Fragment fragment = new AddKejadianBeranakFagment(mContext,
						mNit);
				General.replaceFragmentAddBackStack(fragment, getActivity()
						.getSupportFragmentManager());
			}
		});
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if ((mSessionManager.getPeran().equalsIgnoreCase("ppt")) ||
			(mSessionManager.getPeran().equalsIgnoreCase("admin")))
		{
			if (v.getId() == R.id.listKejadianBeranakTab) {
				AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
				menu.setHeaderTitle("NIT : "+ arrayKejadianBeranak.get(info.position).getNIT());
				String[] menuItems = getResources().getStringArray(R.array.menu_delete);
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
			String[] menuItems = getResources().getStringArray(R.array.menu_delete);
			String menuItemName = menuItems[menuItemIndex];

//			if (menuItemName.equalsIgnoreCase("edit")) {
//				// mode edit
//				Fragment mFragment = new AddKejadianBeranakFagment(mContext,
//						mNit, arrayKejadianBeranak.get(info.position));
//				General.replaceFragmentAddBackStack(mFragment, getActivity()
//						.getSupportFragmentManager());
//			} else 
			if (menuItemName.equalsIgnoreCase("delete")) {
				final Search_KejadianBeranakModel tempKejadianBeranakModel =arrayKejadianBeranak.get(info.position);
				
				new AlertDialog.Builder(mContext)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Delete?")
				.setMessage("Anda yakin menghapus data "+ " NIT : "+ tempKejadianBeranakModel.getNIT())
				.setNegativeButton(android.R.string.no, null)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0,
									int arg1) {
								
								EditKejadianBeranakModelSync editKejadianBeranakModelSync = new EditKejadianBeranakModelSync();
								editKejadianBeranakModelSync.setKode_kejadian_beranak(tempKejadianBeranakModel.getKode_kejadian_beranak());
								editKejadianBeranakModelSync.setNit(mNit);
//								editKejadianBeranakModelSync.setBanyak_anak_betina(banyakAnakBetina.getText().toString());
//								editKejadianBeranakModelSync.setBanyak_anak_jantan(banyakAnakJantan.getText().toString());
//								editKejadianBeranakModelSync.setTanggal_beranak(tanggalBeranak.getText().toString());
								editKejadianBeranakModelSync.setUser(mSessionManager.getUserame());
								editKejadianBeranakModelSync.setPass(mSessionManager.getPassword());
								editKejadianBeranakModelSync.setGuid(General.generateGuid());
								DeleteKejadianBeranakSync deleteKejadianBeranakSync = new DeleteKejadianBeranakSync(getActivity(), mContext, editKejadianBeranakModelSync);
								deleteKejadianBeranakSync.execute();
								
								String resultJson;
								JSONObject jsonObject;
								try {
									resultJson = deleteKejadianBeranakSync.get().toString();
									jsonObject = new JSONObject(resultJson);
									String status = jsonObject.getString("message");
									if(status.equalsIgnoreCase("sukses")) {
//										String id = jsonObject.getString("kode_kejadian_beranak");
										
										elsKejadianBeranak mElsKejadianBeranak = new elsKejadianBeranak();
										mElsKejadianBeranak.setKode_kejadian_beranak(tempKejadianBeranakModel.getKode_kejadian_beranak());
										db.deleteKejadianBeranak(mElsKejadianBeranak);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}catch (InterruptedException e) {
									e.printStackTrace();
								} catch (ExecutionException e) {
									e.printStackTrace();
								}
								
								arrayKejadianBeranak = db.getKejadianBeranak(mNit);
								Search_KejadianBeranakAdapter mAdapter = new Search_KejadianBeranakAdapter(
										mContext, R.layout.item_list_search_kejadian_beranak,
										arrayKejadianBeranak);
								mAdapter.notifyDataSetChanged();
								listView.setAdapter(mAdapter);
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
