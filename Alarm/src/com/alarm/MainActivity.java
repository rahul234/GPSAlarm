package com.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseAlarmActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	
	public void onNormalAlarmClicked(View view){
		Intent i = new Intent(this,NormalAlarmActivity.class);
		startActivity(i);
	}
	
	
	public void onGpsAlarmClicked(View view ){
		Intent i = new Intent(this,GpsAlarmActivity.class);
		startActivity(i);
		
	}
}
