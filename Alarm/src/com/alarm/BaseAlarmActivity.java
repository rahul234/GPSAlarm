package com.alarm;

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

	protected AlarmDetails retriveData(AlarmDetails details) {
		SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
		Gson gson = new Gson();
		String json = mPrefs.getString("AlarmDetails", "");
		AlarmDetails obj = gson.fromJson(json, AlarmDetails.class);
		return obj;
	}

	protected void saveData(AlarmDetails details) {
		SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
		Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(details);
		prefsEditor.putString("AlarmDetails", json);
		prefsEditor.commit();
	}
}
