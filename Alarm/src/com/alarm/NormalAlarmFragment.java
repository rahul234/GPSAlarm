package com.alarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.BaseAlarmDetails;
import com.alarm.framework.NormalAlarmInformation;

public class NormalAlarmFragment extends BaseAlarmFragment {

	private TimePicker timePicker;
	private int hour;
	private int minute;
	protected int selectedHour;
	protected int selectedminute;
	private int position;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.normal_alarm, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		timePicker = (TimePicker) view.findViewById(R.id.timePicker);
		if(getActivity().getIntent().getExtras() != null){
        	String hours = getActivity().getIntent().getExtras().getString("Hours", "");
        	String minutes = getActivity().getIntent().getExtras().getString("Minutes", "");
        position = getActivity().getIntent().getExtras().getInt("Position");
        	setCurrentTimeOnView(Integer.valueOf(hours),Integer.valueOf(minutes));	
        }else{
        	final Calendar c = Calendar.getInstance();
    		hour = c.get(Calendar.HOUR_OF_DAY);
    		minute = c.get(Calendar.MINUTE);
    		setCurrentTimeOnView(Integer.valueOf(hour),Integer.valueOf(minute));	
        }

		
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				BaseAlarmDetails.getSingletonInstance().setHasUserChangedTime(true);
				selectedHour = hourOfDay;
				selectedminute = minute;

			}
		});
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	private AlarmDetails getDetails() {
		return BaseAlarmDetails.getSingletonInstance().getDetails();
	}
	
	public void onSaveClicked(View view) {

		if (BaseAlarmDetails.getSingletonInstance().isHasUserChangedTime()) {
			getDetails().getNormalAlarmInformation().remove(position);
			saveData(getDetails());
			NormalAlarmInformation alarmInformation = new NormalAlarmInformation();
			alarmInformation.setHours(selectedHour);
			alarmInformation.setMin(selectedminute);
			scheduleAlarm(selectedHour, selectedminute);
			BaseAlarmDetails.getSingletonInstance().getDetails().getNormalAlarmInformation().add(alarmInformation);
			saveData(BaseAlarmDetails.getSingletonInstance().getDetails());

		} else {
			Toast.makeText(getActivity(), "Please enter alarm time", Toast.LENGTH_SHORT).show();
		}
	}

	public void scheduleAlarm(int selectedHour, int selectedminute) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, selectedHour);
		cal.set(Calendar.MINUTE, selectedminute);
		//in case of choosing a previous hour, then set alarm to next day
	    if (System.currentTimeMillis() < cal.getTimeInMillis()){
	        cal.set(Calendar.HOUR_OF_DAY, selectedHour + 24);
	    }
		Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
		AlarmManager am = (AlarmManager) getActivity().getSystemService(Activity.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

	}

	public void setCurrentTimeOnView(int hours, int minute) {
		timePicker.setCurrentHour(hours);
		timePicker.setCurrentMinute(minute);

	}

}
