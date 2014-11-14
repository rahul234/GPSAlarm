package com.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;

import com.alarm.framework.AlarmDetails;
import com.google.gson.Gson;

public abstract class BaseAlarmFragment extends Fragment {
	
	protected void saveData(AlarmDetails details) {
		SharedPreferences mPrefs = getActivity().getSharedPreferences("AlarmDetails",Context.MODE_PRIVATE);
		Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(details);
		prefsEditor.putString("AlarmDetails", json);
		prefsEditor.commit();
		goHome();
	}


	private void goHome() {
		Intent i = new Intent(getActivity(), MainActivity.class);
		startActivity(i);
	}
	
	

	
	
	protected void intializeData(){
		
	}

}
