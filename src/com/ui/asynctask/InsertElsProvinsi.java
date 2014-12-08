package com.ui.asynctask;

import android.os.AsyncTask;


//public class InsertElsProvinsi  extends AsyncTask<String, Void, String>{
//
//	@Override
//	protected String doInBackground(String... params) {
//		String id_provinsi = i;
//		String nama_provinsi = idl.getText().toString();
//		
//		
//		try {
//			JSONObject json = new JSONObject();
//			json.put("nit", dataNit);
//			json.put("idl", dataIdl);
//			json.put("tanggal_lahir", dataTglLahir);
//			json.put("bangsa", dataBangsa);
//			json.put("nit_induk", dataNitInduk);
//			json.put("bentuk_wajah", dataWajah);
//			json.put("warna", dataWarna);
//			json.put("status_punuk", dataPunuk);
//			json.put("status_aksesoris_kaki", dataAksesoris);
//			json.put("status_kepemilikan", dataKepemilikan);
//			
//			Log.d("json", json.toString());
//			
//			doPOST("/method/1", json);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	public static String doPOST(String method, JSONObject data){
//        InputStream inputStream = null;
//        String result = "";
//        String url = urlPOST+method;
//        try {
// 
//            // 1. create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
// 
//            // 2. make POST request to the given URL
//            HttpPost httpPost = new HttpPost(url);
// 
//            String json = "";
// 
//            json = data.toString();
//            // 5. set json to StringEntity
//            StringEntity se = new StringEntity(json);
// 
//            // 6. set httpPost Entity
//            httpPost.setEntity(se);
// 
//            // 7. Set some headers to inform server about the type of the content   
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
// 
//            // 8. Execute POST request to the given URL
//            HttpResponse httpResponse = httpclient.execute(httpPost);
// 
//            // 9. receive response as inputStream
//            inputStream = httpResponse.getEntity().getContent();
// 
//            // 10. convert inputstream to string
//            if(inputStream != null)
//                result = convertInputStreamToString(inputStream);
//            else
//                result = "Did not work!";
// 
//        } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
// 
//        // 11. return result
//        return result;
//    }
//}
