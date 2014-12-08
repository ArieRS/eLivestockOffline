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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.fragment.SearchDataSapiResultFragment;
import com.ui.model.laporan.SearchDataSapiResultModel;

public class IndentifyFragment extends Fragment {

	private static final int ACTION_TAKE_PHOTO_B = 1;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	//
	private ImageView mImageView;
	private Bitmap mImageBitmap = null;
	private Button picBtn;
	private Button sendBtn ;
	private EditText textFromServer;
	//
	private String mCurrentPhotoPath;
	private String picturePath;
	private General_moncong general_moncong;

	private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

	TextView messageText;
	Button uploadButton;
	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	String stringFromserver = "";
	// /
	String upLoadServerUri = null;
	String ImageName = null;
	File ImagePath = null;

	long FinalTime = 0;
	Bitmap bitmapSetPic = null;
	Context mContext = null;
	int rotate = 0;
	// //
	int TIMEOUT_MILLISEC = 1000; // = 10 seconds
	ActionBar actioncBar;
	Activity activity;
	Boolean statusIndentifikasi = false;
	Fragment fragment;

	public IndentifyFragment(Context context) {
		mContext = context;
		general_moncong = new General_moncong();
		activity = (Activity) mContext;
		actioncBar = activity.getActionBar();
		actioncBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_indentify,
				container, false);
		setLayout(rootView);
		return rootView;
	}

	private void setLayout(View rootView) {
		upLoadServerUri = getResources().getString(R.string.URLServer_Moncong) + "matching.php";
		// + "sample1.php";

		mImageView = (ImageView) rootView.findViewById(R.id.imageView1);
		
		if (mImageBitmap!= null){
			if (!mImageBitmap.isRecycled()) 
				mImageBitmap.recycle();
		}
		

		picBtn = (Button) rootView.findViewById(R.id.btnIntend);
		sendBtn = (Button) rootView.findViewById(R.id.btnSend);
		textFromServer = (EditText) rootView.findViewById(R.id.editText);

		sendBtn.setVisibility(View.INVISIBLE);
		// picBtn.setOnClickListener
		setBtnListenerOrDisable(picBtn, mTakePicOnClickListener,
				MediaStore.ACTION_IMAGE_CAPTURE);

		sendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = ProgressDialog.show(mContext, "", "Uploading file...",
						true);
				new Thread(new Runnable() {

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

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,(mImageBitmap != null));
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
		String imageFileName = general_moncong.JPEG_FILE_PREFIX + timeStamp;// +
																			// "_";
		ImageName = imageFileName;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

		String imageFileName = general_moncong.JPEG_FILE_PREFIX + timeStamp
				+ "_";
		File albumF = getAlbumDir();
		
		File imageF = File.createTempFile(imageFileName,general_moncong.JPEG_FILE_SUFFIX, albumF);
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

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.ECLAIR)
	private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;

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
		sendBtn.setVisibility(View.VISIBLE);
		textFromServer.setVisibility(View.VISIBLE);
		textFromServer.setText("");
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

		File f = null;

		try {
			f = setUpPhotoFile();
			takePictureIntent
					.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		} catch (IOException e) {
			e.printStackTrace();
			f = null;
			mCurrentPhotoPath = null;
		}
		general_moncong.bitmapRecycle(bitmapSetPic);
		startActivityForResult(takePictureIntent, actionCode);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			getActivity();
			if (resultCode == Activity.RESULT_OK) {
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

		File file = new File(getAlbumDir(), ImageName + general_moncong.PNG_FILE_SUFFIX);
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
				scaleFactor = Math.min(photoW / targetW, photoH / targetH);
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
			// mImageView.setImageBitmap(scaledphoto);
			mImageView.setVisibility(View.VISIBLE);
			sendBtn.setVisibility(View.VISIBLE);
			textFromServer.setVisibility(View.VISIBLE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

		}

	}

	Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

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
			btn.setText(getText(R.string.cannot).toString() + " "
					+ btn.getText());
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
		int maxBufferSize = 1 * 1024 * 1024;
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
					messageText.setText("Source File not exist :" + fileName);
				}
			});

			return 0;

		} else {
			try {

				startTime = System.nanoTime();
				// ///////////
				// open a URL connection to the Servlet
				// BitmapFactory.
				URL url = new URL(upLoadServerUri);
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
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);

				Bitmap bitmapInput =  general_moncong.decodeAndResizeFile(sourceFileUri, 512);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmapInput.compress(Bitmap.CompressFormat.PNG, 50, stream);
				byte[] byteArray = stream.toByteArray();

				dos.write(byteArray);

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				stringFromserver = conn.getResponseMessage();

				// ////
				responseStream = new BufferedInputStream(conn.getInputStream());
				BufferedReader responseStreamReader = new BufferedReader(
						new InputStreamReader(responseStream));
				String line = "";
				StringBuilder stringBuilder = new StringBuilder();
				while ((line = responseStreamReader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}

				String response = stringBuilder.toString();
				stringFromserver = response;

//				JSONObject object = new JSONObject(response.trim());
//				JSONArray jsonArray = object.getJSONArray("posts");
//				if (jsonArray != null) {
//					// stringFromserver = jsonArray.get(0).toString();
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject object1 = (JSONObject) jsonArray.get(i);
//						if (object1.getString("msg") != null)
//							stringFromserver = object1.getString("msg");
//						else {
//							stringFromserver = "ID = "
//									+ object1.getString("id");
//							stringFromserver += " ; Jenis = "
//									+ object1.getString("jenis");
//							stringFromserver += " ; lokasi = "
//									+ object1.getString("lokasi");
//						}
//					}
//					// }
//				} else {
//					stringFromserver = "pesan null";
//				}
				//{"posts":{"data":{"nit_induk":999,"lokasi":"tajur, Solo, Jawa Tengah","tanggal_lahir":"2014-10-16","bentuk_wajah":"tipe wajah","warna":"Coklat","bangsa":"bangsa A","nit":1},
				//"message":"sukses","status":1,"nit":"1"}}
				JSONObject object = new JSONObject(response);
				String objectPosts = object.getString("posts");
				JSONObject object1 = new JSONObject(objectPosts);
				
				if (object1.getString("status").equalsIgnoreCase("0"))
					stringFromserver = object1.getString("message");
				else {
					statusIndentifikasi = true;
					stringFromserver = object1.getString("nit");
				}
				

//				JSONObject object = new JSONObject(response);
//				JSONArray jsonArray = object.getJSONArray("posts");
//				if (jsonArray != null) {
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject object1 = (JSONObject) jsonArray.get(i);
//						if (object1.getString("status").equalsIgnoreCase("0"))
//							stringFromserver = object1.getString("message");
//						else {
//							statusIndentifikasi = true;
//							stringFromserver = object.getString("nit");
//						}
//					}
//					// }
//				} else {
//					stringFromserver = "pesan null";
//				}
				// ////////

				conn.disconnect();
				responseStream.close();
				fileInputStream.close();
				responseStreamReader.close();

				// new General().bitmapRecycle(bitmapInput);

				stream.flush();
				stream.reset();
				stream.close();

				dos.flush();
				dos.close();

				FinalTime = System.nanoTime() - startTime;

				// textFromServer.setText(stringFromserver);
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						textFromServer.setText(stringFromserver);
						if (statusIndentifikasi == true) {
							new AlertDialog.Builder(mContext)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle("Indentifikasi?")
							.setMessage("Data sapi Nit = "+stringFromserver)
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface arg0,int arg1) {
											DatabaseHelper newDatabaseHelper = new DatabaseHelper(mContext);
											SearchDataSapiResultModel mDataSapiResultModel =  newDatabaseHelper.cariDataSapi(stringFromserver);
											fragment = new SearchDataSapiResultFragment(mContext, mDataSapiResultModel);
											new General().replaceFragmentAddBackStack(fragment,getFragmentManager());
										}
							}).create().show();
						}
					}
				});
			}catch (JsonParseException e) {
				dialog.dismiss();
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General().showDialogError(mContext, "Upload Time Out. Please Take Another Picture ", "Indentify");
					}
				});
			}  catch (OutOfMemoryError e) {
				dialog.dismiss();
				e.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General().showDialogError(mContext,"Please Take Another Picture", "Indentify");
					}
				});
			} catch (MalformedURLException ex) {

				dialog.dismiss();
				ex.printStackTrace();

				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						new General().showDialogError(mContext,"Please Take Another Picture", "Indentify");
					}
				});
			} catch (final Exception e) {

				dialog.dismiss();
				
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						textFromServer.setText(stringFromserver);

						if (e.getMessage().contains("NETWORK")) {
							new General().showDialogError(mContext,"Please Check Network Connection","Network");
						}
						else{
							new General().showDialogError(mContext,e.getMessage(),"Network");
						}
					}
				});
				Log.e("Upload file to server Exception",
						"Exception : " + e.getMessage(), e);
			}

			dialog.dismiss();

			//General_moncong.bitmapRecycle(bitmapSetPic);
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
}
