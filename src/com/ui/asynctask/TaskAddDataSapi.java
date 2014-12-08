package com.ui.asynctask;


import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class TaskAddDataSapi extends AsyncTask<String, Void, String> {

	public static String doPOST(String method, JSONObject data){
        InputStream inputStream = null;
        String result = "";
        String url = "";//urlPOST+method;
        try {
 
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            json = data.toString();
 
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null){}
                //result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
