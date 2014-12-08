package com.ui.moncong.sapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.model.laporan.Data_SapiModel;

public class RegisterFragment extends Fragment {

	Button picBtn = null;
	Button btnRegisterDatabase = null;
	
	EditText editNit;
	Context mContext = null;

	// ////////////////////
	private static final int ACTION_TAKE_PHOTO_B = 1;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	//
	private ImageView mImageView;
	private Bitmap mImageBitmap;
	//
	private String mCurrentPhotoPath;
	private String picturePath;

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	String stringFromserver = "";

	String upLoadServerUriId = null;
	String upLoadServerUriData = null;
	String ImageName = null;
	File ImagePath = null;

	// //
	int TIMEOUT_MILLISEC = 1000; // = 10 seconds

	// //untuk tampil citra setpic()
	Bitmap bitmapSetPic = null;
	long FinalTime = 0;
	Data_SapiModel mData_SapiModel;
	ActionBar actioncBar;
	Activity activity;
	
	public RegisterFragment(Context context, Data_SapiModel data_SapiModel) {
		mContext = context;
		mData_SapiModel = data_SapiModel;
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_register, container,
				false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		/*
		jenisSpinner = (Spinner) rootView.findViewById(R.id.Spinjenis);
		jenisKelaminSpinner = (Spinner) rootView.findViewById(R.id.SpinjenisKelamin);
		editLokasi = (EditText) rootView.findViewById(R.id.Editlokasi);
		editEarTag = (EditText) rootView.findViewById(R.id.EditEarTag);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				mContext, R.array.JenisSapi,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		jenisSpinner.setAdapter(adapter);

		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				mContext, R.array.JenisKelamin,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		jenisKelaminSpinner.setAdapter(adapter1);
		
		*/
		picBtn = (Button) rootView.findViewById(R.id.btnTakePicture);
		btnRegisterDatabase = (Button) rootView.findViewById(R.id.btnRegisterDatabase);
		editNit = (EditText) rootView.findViewById(R.id.EditNIT);
		editNit.setText(mData_SapiModel.getNIT());
		
		mImageView = (ImageView) rootView.findViewById(R.id.imageRegister);
		upLoadServerUriData = getResources().getString(R.string.URLServer_Moncong) + "android_upload_register.php";

//		picBtn.setOnClickListener
		setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		btnRegisterDatabase.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog = ProgressDialog.show(mContext, "", "Uploading file...", true);

				new Thread(new Runnable() 
				{
					public void run() {
						uploadFile(mCurrentPhotoPath);
					}
				}).start();
			}
		});

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
		} else {
			mAlbumStorageDirFactory = new BaseAlbumDirFactory();
		}
	}

	Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

	 //Some lifecycle callbacks so that the image can survive orientation change
	 @Override
	 public void onSaveInstanceState(Bundle outState) {
		 outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		 outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
		 (mImageBitmap != null));
		 super.onSaveInstanceState(outState);
	 }


	// @Override
	// protected void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
	// mImageView.setImageBitmap(mImageBitmap);
	// mImageView
	// .setVisibility(savedInstanceState
	// .getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
	// : ImageView.INVISIBLE);
	// mCurrentPhotoPath = savedInstanceState.getParcelable(mCurrentPhotoPath);
	// }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(mContext, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(mContext, "portrait", Toast.LENGTH_SHORT).show();
		}
	}

	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
//		String stringearTag = editEarTag.getText().toString();
//		if (stringearTag.length() == 0)
//			return getString(R.string.album_name);
//		else
//			return stringearTag;
	}

	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {

			storageDir = mAlbumStorageDirFactory
					.getAlbumStorageDir(getAlbumName());

			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("CameraSample", "failed to create directory");
						return null;
					}
				}
			}

		} else {
			Log.v(getString(R.string.app_name),
					"External storage is not mounted READ/WRITE.");
		}

		return storageDir;
	}

	private void SetFileName() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = new General_moncong().JPEG_FILE_PREFIX
				+ timeStamp;// +
		// "_";
		ImageName = imageFileName;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		String imageFileName = new General_moncong().JPEG_FILE_PREFIX
				+ timeStamp + "_";
		File albumF = getAlbumDir();
		File imageF = File.createTempFile(imageFileName,new General_moncong().JPEG_FILE_SUFFIX, albumF);
		//
		ImageName = imageFileName;
		// ImagePath = imageF;
		return imageF;
	}

	private File setUpPhotoFile() throws IOException {
		// save file in here
		File f = createImageFile();
		mCurrentPhotoPath = f.getAbsolutePath();

		return f;
	}

	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		// BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			int x_ = (targetW == 0) ? 0 : (photoW / targetW);
			int y_ = (targetH == 0) ? 0 : (photoH / targetH);
			scaleFactor = Math.min(x_, y_);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		bitmapSetPic = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmapSetPic);
		mImageView.setVisibility(View.VISIBLE);
		btnRegisterDatabase.setVisibility(View.VISIBLE);
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				"android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		mContext.sendBroadcast(mediaScanIntent);
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//
		// SetFileName();
		// startActivityForResult(takePictureIntent, actionCode);

		switch (actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;

			try {
				f = setUpPhotoFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
			break;
		default:
			break;
		} // switch

		new General_moncong().bitmapRecycle(bitmapSetPic);
		startActivityForResult(takePictureIntent, actionCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case ACTION_TAKE_PHOTO_B: {
			 if (resultCode == getActivity().RESULT_OK) {
				handleBigCameraPhoto();
			}
			 break;
			}
		}
	}

	private void handleBigCameraPhoto() {
		if (mCurrentPhotoPath != null) {
			setPic();
			galleryAddPic();
			picturePath = mCurrentPhotoPath;
			// mCurrentPhotoPath = null;
		}
	}

	private void handleBigCameraPhoto(Intent intent) {

		Bitmap scaledphoto = null;
		Bundle extras = intent.getExtras();
		mImageBitmap = (Bitmap) extras.get("data");

		int height = mImageBitmap.getHeight() + 1000;
		int width = mImageBitmap.getWidth() + 1000;

		scaledphoto = Bitmap.createScaledBitmap(mImageBitmap, height, width,
				true);

		Log.d("", "Picture scaled");

		String path = Environment.getExternalStorageDirectory().toString();
		FileOutputStream fOut = null;

		File file = new File(getAlbumDir(), ImageName
				+ new General_moncong().PNG_FILE_SUFFIX);
		mCurrentPhotoPath = file.toString();
		// File file = new File(path, imageFileName+".jpg");
		try {
			// fOut = new FileOutputStream(imageF);
			fOut = new FileOutputStream(file);
			scaledphoto.compress(Bitmap.CompressFormat.PNG, 50, fOut);
			Log.d("", "Scaled picture saved");
			fOut.flush();
			fOut.close();

			// //
			/* Get the size of the ImageView */
			int targetW = mImageView.getWidth();
			int targetH = mImageView.getHeight();

			/* Get the size of the image */
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			int photoW = bmOptions.outWidth;
			int photoH = bmOptions.outHeight;

			/* Figure out which way needs to be reduced less */
			int scaleFactor = 1;
			if ((targetW > 0) || (targetH > 0)) {
				int x_ = (targetW == 0) ? 0 : (photoW / targetW);
				int y_ = (targetH == 0) ? 0 : (photoH / targetH);
				scaleFactor = Math.min(x_, y_);
			}

			/* Set bitmap options to scale the image decode target */
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;

			/* Decode the JPEG file into a Bitmap */
			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,
					bmOptions);

			/* Associate the Bitmap to the ImageView */
			mImageView.setImageBitmap(bitmap);
			// //
			mImageView.setImageBitmap(scaledphoto);
			mImageView.setVisibility(View.VISIBLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

		}
		// if (mCurrentPhotoPath != null) {
		// setPic();
		// galleryAddPic();
		// mCurrentPhotoPath = null;
		// }
	}


	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private void setBtnListenerOrDisable(Button btn,
			Button.OnClickListener onClickListener, String intentName) {
		if (isIntentAvailable(mContext, intentName)) {
			btn.setOnClickListener(onClickListener);
		} else {
			btn.setText(getText(R.string.cannot).toString() + " "+ btn.getText());
			btn.setClickable(false);
		}
	}

	public int uploadFile(String sourceFileUri) {
		final String fileName = sourceFileUri;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 10000;
		File sourceFile = new File(sourceFileUri);

		long startTime = 0;

		// /
		FileInputStream fileInputStream = null;
		InputStream responseStream = null;

		if (!sourceFile.isFile()) {

			dialog.dismiss();

			Log.e("uploadFile", "Source File not exist :" + sourceFileUri);

			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					// messageText.setText("Source File not exist :" +
					// fileName);
				}
			});

			return 0;

		} else {
			try {
				startTime = System.nanoTime();
				// ///////////
				// new General().checkConnection(RegisterActivity.this);
				
				URL url = new URL(upLoadServerUriData);
				fileInputStream = new FileInputStream(sourceFile);
				// Open a HTTP connection to the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				// conn.setRequestProperty("uploadedfile", fileName);

				dos = new DataOutputStream(conn.getOutputStream());

				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
						+ fileName + "" + lineEnd);

				dos.writeBytes(lineEnd);

				final Bitmap bitmapInput = new General_moncong().decodeAndResizeFile(
						sourceFileUri, 512);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmapInput.compress(Bitmap.CompressFormat.PNG, 50, stream);
				byte[] byteArray = stream.toByteArray();

				dos.write(byteArray);

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + lineEnd);

//				dos.writeBytes("Content-Disposition: form-data; name=\"nit\""+ lineEnd);
//				dos.writeBytes(lineEnd);
//				dos.writeBytes(id_stock + lineEnd);
//				dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//				dos.writeBytes("Content-Disposition: form-data; name=\"lokasi\""
//						+ lineEnd);
//				dos.writeBytes(lineEnd);
//				dos.writeBytes(lokasi + lineEnd);
//				dos.writeBytes(twoHyphens + boundary + lineEnd);
//
//				dos.writeBytes("Content-Disposition: form-data; name=\"jenis_kelamin\""
//						+ lineEnd);
//				dos.writeBytes(lineEnd);
//				dos.writeBytes(jenis_kelamin + lineEnd);
//				dos.writeBytes(twoHyphens + boundary + lineEnd);

//				dos.writeBytes("Content-Disposition: form-data; name=\"jenis\""
//						+ lineEnd);
//				dos.writeBytes(lineEnd);
//				dos.writeBytes(jenis + lineEnd);
//				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				
				dos.writeBytes("Content-Disposition: form-data; name=\"nit\""+ lineEnd);
				dos.writeBytes(lineEnd);
				dos.writeBytes(mData_SapiModel.getNIT() + lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				// publishing the progress....
				Bundle resultData = new Bundle();
				resultData.putInt("progress", 100);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();

				// ////
				responseStream = new BufferedInputStream(conn.getInputStream());
				BufferedReader responseStreamReader = new BufferedReader(
						new InputStreamReader(responseStream));
				String line = "";
				StringBuilder stringBuilder = new StringBuilder();
				while ((line = responseStreamReader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}

				final String response = stringBuilder.toString();

				
				JSONObject object = new JSONObject(response);
				JSONArray jsonArray = object.getJSONArray("posts");
				if (jsonArray != null) {
					// stringFromserver = jsonArray.get(0).toString();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object1 = (JSONObject) jsonArray.get(i);
						if (object1.getString("status").equalsIgnoreCase("0"))
							stringFromserver = object1.getString("msg");
						else {
							stringFromserver = "Nit = " + object1.getString("nit");
							stringFromserver += " ; Message = " + object1.getString("msg");
						}
					}
					// }
				} else {
					stringFromserver = "pesan null";
				}
				
				
				conn.disconnect();
				responseStream.close();
				fileInputStream.close();
				responseStreamReader.close();

				stream.flush();
				stream.reset();
				stream.close();

				dos.flush();
				dos.close();

				FinalTime = System.nanoTime() - startTime;
				// new General().bitmapRecycle(bitmapInput);
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new AlertDialog.Builder(mContext).setTitle("Register").setMessage(stringFromserver)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								bitmapInput.recycle();
							}
						}).create().show();
						
//						if (serverResponseCode == 200) {
//							new General().showDialogSuccess(mContext,response.toString(), "Register");
//						} else {
//							new General().showDialogError(mContext,"Please Take Another Picture", "Register");
//						}
					}
				});

			} catch(JsonParseException e){
				dialog.dismiss();
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General();
						General.showDialogError(mContext, "Upload Time Out. Please Take Another Picture ", "Register");
					}
				});
			}  catch (OutOfMemoryError e) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General();
						General.showDialogError(mContext,"Please Take Another Picture", "Register");
					}
				});
			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General();
						General.showDialogError(mContext,"Please Take Another Picture", "Register");
					}
				});
			} catch (final Exception e) {
				dialog.dismiss();
				
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						if (e.getMessage().contains("NETWORK")) {
							new General();
							General.showDialogError(mContext,"Please Check Network Connection","Network");
						}
					}
				});
				Log.e("Upload file to server Exception","Exception : " + e.getMessage(), e);
			} 
			dialog.dismiss();
			return serverResponseCode;
		} // End else block
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		getActivity().getSupportFragmentManager().popBackStack();
	}

	@Override
	public void setInitialSavedState(SavedState state) {
		super.setInitialSavedState(state);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	// @Override
	// public void onBackPressed() {
	// new General_moncong().bitmapRecycle(bitmapSetPic);
	// Intent intent = new Intent(RegisterFragment.this, MenuActivity.class);
	// startActivity(intent);
	// RegisterFragment.this.finish();
	// }

	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// new General_moncong().bitmapRecycle(bitmapSetPic);
	// }

}
