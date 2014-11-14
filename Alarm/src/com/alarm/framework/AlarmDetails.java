package com.alarm.framework;

import java.util.ArrayList;
import java.util.List;

public class AlarmDetails {
	
	private List<GpsAlarmInformation> gpsAlarmInformation = new ArrayList<GpsAlarmInformation>();
	private List<NormalAlarmInformation> normalAlarmInformation = new ArrayList<NormalAlarmInformation>();

	public List<GpsAlarmInformation> getGpsAlarmInformation() {
		return gpsAlarmInformation;
	}

	public void setGpsAlarmInformation(
			List<GpsAlarmInformation> gpsAlarmInformation) {
		this.gpsAlarmInformation = gpsAlarmInformation;
	}

	public List<NormalAlarmInformation> getNormalAlarmInformation() {
		return normalAlarmInformation;
	}

	public void setNormalAlarmInformation(
			List<NormalAlarmInformation> normalAlarmInformation) {
		this.normalAlarmInformation = normalAlarmInformation;
	}

	

	

}
