package com.techcomm.ingrediomatic;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.*;

public class Search extends Activity {
	Handler errorHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		final ListView listView = (ListView) findViewById(R.id.myList);
		Button addButton = (Button) findViewById(R.id.read);
		Button searchButton = (Button) findViewById(R.id.button2);
		final EditText editText = (EditText) findViewById(R.id.editText1);
		final ArrayList<String> list = new ArrayList<String>();
		
		final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, list);
		listView.setAdapter(mAdapter); 
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		              int position, long id) {
		            final String item = (String) parent.getItemAtPosition(position);
                    list.remove(item);
                    mAdapter.notifyDataSetChanged();
		          }
		});
		final Context c = getApplicationContext();
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				final String item = editText.getText().toString();
				editText.setText("");
				list.add(item);
				mAdapter.notifyDataSetChanged();
				
			}
			
		});
		
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				GetJson myJson = new GetJson(c, Search.this);
				myJson.execute(list);
			}
			
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}

