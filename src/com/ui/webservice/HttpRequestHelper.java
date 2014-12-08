package com.ui.webservice;

import java.io.DataOutputStream;
import java.io.InputStream;

import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

import com.ui.common.General;


public class HttpRequestHelper {
	HttpConnection conn = null;
	DataOutputStream mDataOutputStream = null;
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	int bytesRead, bytesAvailable, bufferSize;
	byte [] buffer;
	int maxBufferSize = 10000;
	
	public static String doPost(String url, JSONObject data){
		InputStream inputStream = null;
		String result = "";
		String mUrl = url;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(mUrl);
			String json = "";
			json = data.toString();
			StringEntity mStringEntity = new StringEntity(json);
			httpPost.setEntity(mStringEntity);
			httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            
            // 10. convert inputstream to string
            if(inputStream != null)
                result = General.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
            
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}
}
