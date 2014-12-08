package com.ui.moncong.sapi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.ui.elivestock.R;

public class General_moncong {

	public static String JPEG_FILE_PREFIX = "IMG_";
	public static String JPEG_FILE_SUFFIX = ".jpg";
	public static String PNG_FILE_SUFFIX = ".png";

	public Bitmap fixOrientation(int rotasi, Bitmap mBitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
				mBitmap.getHeight(), matrix, true);
		return mBitmap;
	}

	public static void createDialog(Context context, String message) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.action_settings).setPositiveButton(
				R.string.action_settings,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});

		// .setNegativeButton(R.string.cancel, new
		// DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// // User cancelled the dialog
		// }
		// });
		// Create the AlertDialog object and return it
		// return builder.create();

	}

	public static Bitmap decodeAndResizeFile(String f, int size) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = size;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public Bitmap get_Resized_Bitmap(Bitmap bmp, int newHeight, int newWidth) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height,
				matrix, false);
		return newBitmap;
	}

	public static void bitmapRecycle(Bitmap bitmapInput) {
		if (bitmapInput != null) {
			bitmapInput.recycle();
			bitmapInput = null;
		}
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
}
