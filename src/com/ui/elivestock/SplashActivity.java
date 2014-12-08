package com.ui.elivestock;

import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity {

//	private static int splash_time_out = 1000;
//	SessionManager mSessionManager;
//	Context mContext;
//	DatabaseHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		
//		mContext = SplashActivity.this;
//		mSessionManager = new SessionManager(mContext);
//		db = new DatabaseHelper(mContext);
//		
//		if ((mSessionManager.isLogin()) && (General.checkConnection(mContext))){
//			
//			
//			db = new DatabaseHelper(mContext);
//			db.deleteAllTable(mContext);
//			
//			GetAllTableSync getAllTableSync = new GetAllTableSync(mContext);
//			getAllTableSync.execute();
//			finish();
//			
////			Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
////			startActivity(intent);
////			SplashActivity.this.finish();
//		}else 
//		{
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//					startActivity(intent);
//					finish();
//				}
//			}, splash_time_out);
//		}
//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				Intent intent = new Intent(SplashActivity.this,
//						HomeActivity.class);
//				startActivity(intent);
//				finish();
//			}
//		}, splash_time_out);
	}
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		try{
//	        super.onRestoreInstanceState(savedInstanceState);
//	    }catch (Exception e) {
//	        System.out.println(e.getMessage());
//	    }
//	}
}
