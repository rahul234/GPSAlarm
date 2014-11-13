package com.alarm.framework;

import java.util.ArrayList;
import java.util.List;

public class AlarmDetails {

	private List<AlarmTypeList> alarmTypeList = new ArrayList<AlarmTypeList>(2);

	public List<AlarmTypeList> getAlarmTypeList() {
		return alarmTypeList;
	}

	public void setAlarmTypeList(List<AlarmTypeList> alarmTypeList) {
		this.alarmTypeList = alarmTypeList;
	}

}
