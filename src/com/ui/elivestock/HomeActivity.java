package com.ui.elivestock;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ui.adapter.NavDrawerListAdapter;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.common.SessionManager;
import com.ui.fragment.DataSapiFragment;
import com.ui.fragment.HomeMenuFragment;
import com.ui.fragment.LoginFragment;
import com.ui.fragment.MasterFragment;
import com.ui.fragment.ReportDataFragment;
import com.ui.model.sync.NavDrawerItem;

public class HomeActivity extends SherlockFragmentActivity {

	ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	SessionManager session;
	Context mContext;
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		mContext = HomeActivity.this;
		
		// set default home
        if (savedInstanceState == null) {
        	
    		session = new SessionManager(mContext);
    		db = new DatabaseHelper(mContext);
    		// mDatabaseHelper.createMasterSebbaKematian("tes1");
    		// mDatabaseHelper.createMasterSebbaKematian("tes2");
    		// List<String> test =mDatabaseHelper.getAllSebabKematian();

    		// load slide menu items
    		setListDrawerMenu();

    		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    		// setting the nav drawer list adapter
    		adapter = new NavDrawerListAdapter(getApplicationContext(),
    				navDrawerItems);
    		mDrawerList.setAdapter(adapter);

    		 // Enable ActionBar app icon to behave as action to toggle nav drawer
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            
            
    		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, R.string.drawer_open,
                    R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    // TODO Auto-generated method stub
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {
                    // TODO Auto-generated method stub
                    // Set the title on the action when drawer open
                    getSupportActionBar().setTitle("eLivestock");
                    super.onDrawerOpened(drawerView);
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            
            // on first time display view for first nav item
            displayView(0);
        }
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		try{
	        super.onRestoreInstanceState(savedInstanceState);
	    }catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	private void setListDrawerMenu() {
		if (session.isLogin()) {
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

			navDrawerItems = new ArrayList<NavDrawerItem>();
			if (session.getPeran().equalsIgnoreCase("admin")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_admin);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(1, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
						navMenuIcons.getResourceId(3, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("ppt")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_admin);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(1, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
						navMenuIcons.getResourceId(3, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("aho")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_aho);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(1, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("ins")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_ins);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(1, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("dkota")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_dkota);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("dprov")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_dkota);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(4, -1)));
			} else if (session.getPeran().equalsIgnoreCase("dnas")) {
				navMenuTitles = getResources().getStringArray(
						R.array.slider_drawer_items_dkota);
				navMenuIcons = getResources().obtainTypedArray(
						R.array.slider_drawer_icon);

				// adding nav drawer items to array
				// Home
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
						navMenuIcons.getResourceId(0, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
						navMenuIcons.getResourceId(2, -1)));
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
						navMenuIcons.getResourceId(4, -1)));
			}
			navMenuIcons.recycle();
		} else {
			navMenuTitles = getResources().getStringArray(
					R.array.slider_drawer_items_login);
			navMenuIcons = getResources().obtainTypedArray(
					R.array.slider_drawer_icon);

			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

			navDrawerItems = new ArrayList<NavDrawerItem>();
			// adding nav drawer items to array
			// Home
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
					.getResourceId(5, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
					.getResourceId(0, -1)));
			navMenuIcons.recycle();
		}
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		// add code
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(false);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// Set the title on the action when drawer open
				getSupportActionBar().setTitle("eLivestock");
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// end add code
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			setListDrawerMenu();
			displayView(position);
		}
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {

		String menuString = navMenuTitles[position];
		Fragment fragment = null;
		
		if (session.isLogin()) {
//			
			if (session.getPeran().equalsIgnoreCase("admin")) {
			
				if (menuString.equalsIgnoreCase("beranda")) {
	//				fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
					
				} 
				else if (menuString.equalsIgnoreCase("Data Sapi")) {
					// fragment = new DataSapiFragment(this);
					// getSupportFragmentManager()
					// .beginTransaction()
					// .replace(R.id.frame_container,
					// DataSapiFragment.newInstance(),
					// DataSapiFragment.TAG).commit();
					fragment = new DataSapiFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} else if (menuString.equalsIgnoreCase("master")) {
					fragment = new MasterFragment(this);
				} 
			} else if (session.getPeran().equalsIgnoreCase("aho")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Data Sapi")) {
					fragment = new DataSapiFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} 
			} else if (session.getPeran().equalsIgnoreCase("ppt")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Data Sapi")) {
					fragment = new DataSapiFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} else if (menuString.equalsIgnoreCase("master")) {
					fragment = new MasterFragment(this);
				}
			} else if (session.getPeran().equalsIgnoreCase("ins")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
					DatabaseHelper db = new DatabaseHelper(mContext);
				} else if (menuString.equalsIgnoreCase("Data Sapi")) {
					fragment = new DataSapiFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} else if (menuString.equalsIgnoreCase("master")) {
					fragment = new MasterFragment(this);
				} 
			}
			else if (session.getPeran().equalsIgnoreCase("dkota")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} 
			}
			else if (session.getPeran().equalsIgnoreCase("dprov")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} 
			}
			else if (session.getPeran().equalsIgnoreCase("dnas")) {
				if (menuString.equalsIgnoreCase("beranda")) {
//					fragment = new HomeFragment(this);
					fragment = new HomeMenuFragment(HomeActivity.this, mContext);
				} else if (menuString.equalsIgnoreCase("Laporan")) {
					fragment = new ReportDataFragment(this);
				} 
			}
		} 
		else {
			if (menuString.equalsIgnoreCase("beranda")) {
//				fragment = new HomeFragment(this);
				fragment = new HomeMenuFragment(HomeActivity.this, mContext);
			} else if (menuString.equalsIgnoreCase("login")) {
				fragment = new LoginFragment(HomeActivity.this, mContext);
			} 
		}
		if (menuString.equalsIgnoreCase("logout")) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Really Exit?")
					.setMessage("It will erase your data in this phone. Are you sure you want to LogOut?")
					.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//Fragment fragment1 = new HomeMenuFragment(HomeActivity.this, mContext);
							//General.replaceFragmentAddBackStack(fragment1, getSupportFragmentManager());
							//HomeActivity.this.onResumeFragments();
							 displayView(0);
						}
					})
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									ProgressDialog mDialog = ProgressDialog.show(HomeActivity.this, "", "Clear Data", true, false);
									db.deleteAllTable(mContext);
									session.Logout();
									mDialog.dismiss();
									HomeActivity.this.finish();
								}
							}).create().show();
			
		}
		if (fragment != null) {
//			if ( getSupportFragmentManager().getBackStackEntryCount() > 0){
//				getFragmentManager().popBackStack();
//			}
			
			General.replaceFragmentAddBackStack(fragment, getSupportFragmentManager());
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			// setTitle(navMenuTitles[position]);
			getSupportActionBar().setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("HomeActivity", "Error in creating fragment");
		}
	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			getSupportFragmentManager().popBackStack();
		} else {
			new AlertDialog.Builder(this)
					.setTitle("Really Exit?")
					.setMessage("Are you sure you want to exit?")
					.setNegativeButton(android.R.string.no, null)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									moveTaskToBack(true);
									HomeActivity.this.finish();
								}
							}).create().show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		 // If the nav drawer is open, hide action items related to the content
	      // view
	      boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	      menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
	      return super.onPrepareOptionsMenu(menu);       
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(getMenuItem(item))) { 
	         return true; 
	      }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true; 
        default:
            return super.onOptionsItemSelected(item);
        }
	}
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private android.view.MenuItem getMenuItem(final MenuItem item) {
	      return new android.view.MenuItem() {
	         @Override
	         public int getItemId() {
	            return item.getItemId();
	         }

	         public boolean isEnabled() {
	            return true;
	         }

	         @Override
	         public boolean collapseActionView() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public boolean expandActionView() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public ActionProvider getActionProvider() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public View getActionView() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public char getAlphabeticShortcut() {
	            // TODO Auto-generated method stub
	            return 0;
	         }

	         @Override
	         public int getGroupId() {
	            // TODO Auto-generated method stub
	            return 0;
	         }

	         @Override
	         public Drawable getIcon() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public Intent getIntent() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public ContextMenuInfo getMenuInfo() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public char getNumericShortcut() {
	            // TODO Auto-generated method stub
	            return 0;
	         }

	         @Override
	         public int getOrder() {
	            // TODO Auto-generated method stub
	            return 0;
	         }

	         @Override
	         public SubMenu getSubMenu() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public CharSequence getTitle() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public CharSequence getTitleCondensed() {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public boolean hasSubMenu() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public boolean isActionViewExpanded() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public boolean isCheckable() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public boolean isChecked() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public boolean isVisible() {
	            // TODO Auto-generated method stub
	            return false;
	         }

	         @Override
	         public android.view.MenuItem setActionProvider(ActionProvider actionProvider) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setActionView(View view) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setActionView(int resId) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setAlphabeticShortcut(char alphaChar) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setCheckable(boolean checkable) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setChecked(boolean checked) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setEnabled(boolean enabled) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIcon(Drawable icon) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIcon(int iconRes) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setIntent(Intent intent) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setNumericShortcut(char numericChar) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setShortcut(char numericChar, char alphaChar) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public void setShowAsAction(int actionEnum) {
	            // TODO Auto-generated method stub

	         }

	         @Override
	         public android.view.MenuItem setShowAsActionFlags(int actionEnum) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitle(CharSequence title) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitle(int title) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setTitleCondensed(CharSequence title) {
	            // TODO Auto-generated method stub
	            return null;
	         }

	         @Override
	         public android.view.MenuItem setVisible(boolean visible) {
	            // TODO Auto-generated method stub
	            return null;
	         }
	      };
	   }
}
