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

import com.alarm.framework.BaseAlarmDetails;
import com.alarm.framework.NormalAlarmInformation;

public class NormalAlarmFragment extends BaseAlarmFragment {
	
	private TimePicker timePicker;
	private int hour;
	private int minute;	
	protected int selectedHour;
	protected int selectedminute;
	
	
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
				BaseAlarmDetails.getSingletonInstance().setHasUserChangedTime(true);
				 selectedHour = hourOfDay;
				 selectedminute = minute;
				
				
			}
		});
	}

	public void onSaveClicked(View view){	
		
		if(BaseAlarmDetails.getSingletonInstance().isHasUserChangedTime())
		{
			NormalAlarmInformation alarmInformation = new NormalAlarmInformation();
			alarmInformation.setHours(selectedHour);
			alarmInformation.setMin(selectedminute);
			scheduleAlarm(selectedHour,selectedminute);
			BaseAlarmDetails.getSingletonInstance().getDetails().getNormalAlarmInformation().add(alarmInformation);
			saveData(BaseAlarmDetails.getSingletonInstance().getDetails());
			
		}else{
			Toast.makeText(getActivity(), "Please enter alarm time", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void scheduleAlarm(int selectedHour, int selectedminute){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
	   /* cal.add(Calendar.HOUR_OF_DAY, selectedHour);
	    cal.add(Calendar.MINUTE, selectedminute);
	    cal.add(Calendar.SECOND, 0);

	    //in case of choosing a previous hour, then set alarm to next day
	    if (System.currentTimeMillis() < cal.getTimeInMillis()){
	        cal.set(Calendar.HOUR_OF_DAY, selectedHour + 24);
	    }
	    
	  //  long diff = cal.getTime().getTime() - System.currentTimeMillis();
*/	    
        Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = 
            (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
       
    }
 
	
	



	
	public void setCurrentTimeOnView() {
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
 
	}
	
	 
	
}
 

