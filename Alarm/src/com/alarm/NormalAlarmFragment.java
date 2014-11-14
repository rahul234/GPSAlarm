package com.alarm;

import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.alarm.framework.BaseAlarmDetails;
import com.alarm.framework.NormalAlarmInformation;

public class NormalAlarmFragment extends BaseAlarmFragment {
	
	private TimePicker timePicker;
	private int hour;
	private int minute;
	private String time="";
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.normal_alarm, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		timePicker = (TimePicker) view.findViewById(R.id.timePicker);
		setCurrentTimeOnView();
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				int selectedHour = hourOfDay;
				int selectedminute = minute;
				time = new StringBuilder().append(pad(selectedHour))
						.append(":").append(pad(selectedminute)).toString();
				
			}
		});
	}

	public void onSaveClicked(View view){	
		
		if(!"".equals(time))
		{
			NormalAlarmInformation alarmInformation = new NormalAlarmInformation();
			alarmInformation.setTime(time);
			
			BaseAlarmDetails.getSingletonInstance().getDetails().getNormalAlarmInformation().add(alarmInformation);
			saveData(BaseAlarmDetails.getSingletonInstance().getDetails());
			
		}else{
			Toast.makeText(getActivity(), "Please enter alarm time", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void setCurrentTimeOnView() {
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
 
	}
	
	 
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
}
 

