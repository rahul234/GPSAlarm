package com.alarm.framework;


public class BaseAlarmDetails {
	
	private static BaseAlarmDetails singletonInstance;
	private AlarmDetails details = new AlarmDetails();
	private boolean hasUserChangedTime = false;
	
	
	public static BaseAlarmDetails getSingletonInstance() {
		if (null == singletonInstance) {
			singletonInstance = new BaseAlarmDetails();
		}
		return singletonInstance;
	}

	public AlarmDetails getDetails() {
		return details;
	}

	public void setDetails(AlarmDetails details) {
		this.details = details;
	}

	public boolean isHasUserChangedTime() {
		return hasUserChangedTime;
	}

	public void setHasUserChangedTime(boolean hasUserChangedTime) {
		this.hasUserChangedTime = hasUserChangedTime;
	}

	
	


}
