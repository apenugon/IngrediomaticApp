package com.techcomm.ingrediomatic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Directions extends Activity {
	Handler errorHandler;
	static Activity me = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions);
		me = this;
		final Nuance nuanceObject = new Nuance();
		nuanceObject.initializeSpeechKit(getApplicationContext(), errorHandler);
		Button readButton = (Button) findViewById(R.id.read);
		TextView title = (TextView) findViewById(R.id.title);
		TextView totaltime = (TextView) findViewById(R.id.totaltime);
		TextView preptime = (TextView) findViewById(R.id.preptime);
		TextView cooktime = (TextView) findViewById(R.id.cooktime);
		TextView ingredients = (TextView) findViewById(R.id.ingredients);
		final TextView directions = (TextView) findViewById(R.id.directions);
		TextView source = (TextView) findViewById(R.id.source);
		String[] myInfo;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			myInfo = extras.getStringArray("myInfo");
			totaltime.setText(myInfo[0]);
			preptime.setText(myInfo[1]);
			cooktime.setText(myInfo[2]);
			title.setText(myInfo[3]);
			ingredients.setText(myInfo[4]);
			directions.setText(myInfo[5]);
			source.setText(myInfo[6]);
		}
		
		readButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				nuanceObject.speakTheString(directions.getText().toString(), getApplicationContext());
			}
			
		});
		
	}
}
