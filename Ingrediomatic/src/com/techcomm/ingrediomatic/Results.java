package com.techcomm.ingrediomatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Results extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		final ListView listView = (ListView) findViewById(R.id.listView1);
		
		String json = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			json = extras.getString("json");
		}
		
		final ArrayList<String> list = new ArrayList<String>();
		final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.listlayout, list);
		listView.setAdapter(mAdapter); 
		//Log.d("Results", json);
		try {
		final JsonParserFactory factory = JsonParserFactory.getInstance();
		final JSONParser parser = factory.newJsonParser();
		Map jsonData = parser.parseJson(json);
		

		
		final List rootList = (List)jsonData.get("root");
		for (int i = 0; i < rootList.size(); i++)
		{
			list.add(((Map)rootList.get(i)).get("Title").toString());
		}

		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		              int position, long id) {
						final String item = (String) parent.getItemAtPosition(position);
						final int myIndex = list.indexOf(item);
		            	String[] passArray = new String[7];
		            	passArray[0] = ((Map)rootList.get(myIndex)).get("TotalTime").toString();//TotalTime
		            	passArray[1] = ((Map)rootList.get(myIndex)).get("PrepTime").toString();//PrepTime
		            	passArray[2] = ((Map)rootList.get(myIndex)).get("CookTime").toString();//CookTime
		            	passArray[3] = ((Map)rootList.get(myIndex)).get("Title").toString();//Title
		            	passArray[4] = ((Map)rootList.get(myIndex)).get("Ingredients").toString();//Ingredients
		            	passArray[5] = ((Map)rootList.get(myIndex)).get("Directions").toString();//Directions
		            	passArray[6] = ((Map)rootList.get(myIndex)).get("Source").toString();//Source
		            	//Log.d("Results", ((Map)rootList.get(myIndex)).get("TotalTime").toString());
		            	Intent dirIntent = new Intent(getApplicationContext(), Directions.class);
		            	dirIntent.putExtra("myInfo", passArray);
		            	startActivity(dirIntent);
		          }
		});
		}
		catch (Exception e)
		{
			//Log.d("Results", e.toString());
			list.add("No Results Found");
		}
	}
}
