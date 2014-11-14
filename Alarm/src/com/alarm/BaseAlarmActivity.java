package com.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.alarm.framework.AlarmDetails;
import com.google.gson.Gson;

public abstract class BaseAlarmActivity extends FragmentActivity {

	public void onCancelClicked(View view) {
		finish();
	}

	
	protected void saveData(AlarmDetails details) {
		SharedPreferences mPrefs = getSharedPreferences("AlarmDetails",Context.MODE_PRIVATE);
		Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(details);
		prefsEditor.putString("AlarmDetails", json);
		prefsEditor.commit();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
