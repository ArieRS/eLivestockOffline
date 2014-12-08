package com.ui.common;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;

import com.ui.elivestock.R;

public class General {
	public static String generateGuid(){
		UUID uuid = UUID.randomUUID();
		String randomUUID = uuid.toString();
		return randomUUID;
	}
	public static void replaceFragment(Fragment fragment,
			FragmentManager fragmentManagerInput) {
		FragmentManager fragmentManager = fragmentManagerInput;
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).commit();
	}

	public static void replaceFragmentAddBackStack(Fragment fragment,
			FragmentManager fragmentManagerInput) {
		FragmentManager fragmentManager = fragmentManagerInput;
		fragmentManager.beginTransaction()
				.replace(R.id.frame_container, fragment).addToBackStack(null)
				.commit();
	}
//	public static void replaceFragmentAddBackStack(Fragment fragment,
//			android.app.FragmentManager fragmentManager2) {
//		android.app.FragmentManager fragmentManager = fragmentManager2;
//		fragmentManager.beginTransaction()
//				.replace(R.id.frame_container, fragment).addToBackStack(null)
//				.commit();
//	}
	public static void showDialogError(Context ctx, String msg, String title) {
		new AlertDialog.Builder(ctx)
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle(title)
				.setMessage(msg)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
	}

	public static void showDialogSuccess(Context ctx, String msg, String title) {
		new AlertDialog.Builder(ctx).setTitle(title).setMessage(msg)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create().show();
	}
	public static boolean checkConnection(Context ctx) {
		boolean connect = false;
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String networkOperator = tm.getNetworkOperatorName();
		final ConnectivityManager connMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		final NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		final int statusSim = tm.getSimState();

		if (statusSim != TelephonyManager.SIM_STATE_ABSENT) {
			if (wifi.isConnected() || mobile.isConnected()
					|| !networkOperator.equals("")) {
				connect = true;
			}
		} else {
			if (wifi.isConnected() || mobile.isConnected()
					|| !networkOperator.equals("")) {
				connect = true;
			}
		}
		return connect;
	}
	public static void hideKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(
				activity.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
	public static ProgressDialog showProgressDialog(Context context,
			String title, String message) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setIndeterminate(true);
		dialog.setInverseBackgroundForced(false);
		dialog.setCancelable(true);
		dialog.setTitle(title);
		dialog.setMessage(message);

		return dialog;
	}
	 public static String convertInputStreamToString(InputStream inputStream) throws IOException{
		 BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		 String line = "";
		 String result = "";
		 while((line = bufferedReader.readLine()) != null)
			 result += line;

		 inputStream.close();
		 return result;

	 }   
	 public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

		    @Override
		    public Dialog onCreateDialog(Bundle savedInstanceState) {
		        // Use the current date as the default date in the picker
		        final Calendar c = Calendar.getInstance();
		        int year = c.get(Calendar.YEAR);
		        int month = c.get(Calendar.MONTH);
		        int day = c.get(Calendar.DAY_OF_MONTH);

		        // Create a new instance of DatePickerDialog and return it
		        return new DatePickerDialog(getActivity(), this, year, month, day);
		    }

		    @Override
		    public void onDateSet(DatePicker view, int year, int month, int day) {
		        //blah
		    }
		}
}
