package com.techcomm.ingrediomatic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class GetJson extends AsyncTask<ArrayList<String>, Void, String> {
	private ProgressDialog dialog;
	private Context context;
	
	public GetJson(Context c, Context e)
	{
		context = c;
		dialog = new ProgressDialog(e);
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setMessage("Working...");
		dialog.show();
	}
	@Override
	protected String doInBackground(ArrayList<String>... params) {
		ArrayList<String> myList = params[0];
		String url = "http://ec2-54-201-43-92.us-west-2.compute.amazonaws.com/scripts/getjson.php";
		HttpParams httpparams = new BasicHttpParams();
		HttpProtocolParams.setContentCharset(httpparams, "UTF-8");
		
		HttpClient httpclient = new DefaultHttpClient(httpparams);
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("User-Agent", "MyUserAgent/1.0");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(myList.size());
		
		for (int i = 0; i < myList.size(); i++)
		{
			nameValuePairs.add(new BasicNameValuePair("ingreds[]", myList.get(i).trim().toLowerCase()));
			Log.d("Async", myList.get(i).trim().toLowerCase());
		}
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse result =  httpclient.execute(httppost);
			if (result != null) {
				int responseCode = result.getStatusLine().getStatusCode();
				String jsonbody = "";
				switch (responseCode) {
				case 200:
					HttpEntity entity = result.getEntity();
					if (entity != null) {
						jsonbody = EntityUtils.toString(entity);
						Log.d("Async", jsonbody);
						return jsonbody;
					}
					else
						Log.d("Async", "Entity Null");
					break;
				}
			}
		}
		catch (Exception e) {
			Log.d("Async", e.toString());
			return "";
		}
		return "";
		
	}

	@Override
	protected void onPostExecute(final String jsonbody) {
		if (dialog.isShowing())
			dialog.dismiss();
		Log.d("Async", "Post Execute");
		Log.d("Async", jsonbody);
		Intent i = new Intent(context, Results.class);
		i.putExtra("json", jsonbody);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		
		
	}
}
