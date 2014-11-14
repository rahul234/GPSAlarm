package com.alarm;

import android.app.Application;
import android.content.SharedPreferences;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.BaseAlarmDetails;
import com.google.gson.Gson;

public class AlarmApplication extends Application{
	
	@Override
	public void onCreate() {
		super.onCreate();
		if(retriveData() != null){
		BaseAlarmDetails.getSingletonInstance().setDetails(retriveData());
		}else{
			BaseAlarmDetails.getSingletonInstance().setDetails(new AlarmDetails());
		}
		
	}
	
	
	protected AlarmDetails retriveData() {
		SharedPreferences mPrefs = getApplicationContext().getSharedPreferences("AlarmDetails", MODE_PRIVATE);
		Gson gson = new Gson();
		String json = mPrefs.getString("AlarmDetails", "");
		AlarmDetails obj = gson.fromJson(json, AlarmDetails.class);
		return obj;
	}

}
