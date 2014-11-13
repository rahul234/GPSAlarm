package com.alarm;

import java.util.ArrayList;
import java.util.List;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.AlarmTypeList;
import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;

public abstract class BaseAlarmFragment extends Fragment {
	
	protected AlarmDetails retriveData(AlarmDetails details) {
		SharedPreferences mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		Gson gson = new Gson();
		String json = mPrefs.getString("AlarmDetails", "");
		AlarmDetails obj = gson.fromJson(json, AlarmDetails.class);
		return obj;
	}

	protected void saveData(AlarmDetails details) {
		SharedPreferences mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		Editor prefsEditor = mPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(details);
		prefsEditor.putString("AlarmDetails", json);
		prefsEditor.commit();
	}
	
	protected void intializeData(){
		
	}

}
