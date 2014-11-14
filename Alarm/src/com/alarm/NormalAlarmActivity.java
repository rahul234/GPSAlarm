package com.alarm;

import android.os.Bundle;
import android.view.View;

public class NormalAlarmActivity extends BaseAlarmActivity{
	
	private NormalAlarmFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.normal_alarm_activity);
		fragment = (NormalAlarmFragment) getSupportFragmentManager().findFragmentById(R.id.normalAlarmFragment);
	}
	
	
	public void onSaveClicked(View view){	
		fragment.onSaveClicked(view);
	}

}
