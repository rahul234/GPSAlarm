package com.alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.BaseAlarmDetails;

public class MainFragment extends BaseAlarmFragment {

	private ListView gpsAlarmList;
	private ListView normalAlarmList;

	private AlarmDetails getDetails() {
		return BaseAlarmDetails.getSingletonInstance().getDetails();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.main_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		gpsAlarmList = (ListView) view.findViewById(R.id.gpsAlarmList);
		normalAlarmList = (ListView) view.findViewById(R.id.normalAlarmList);
		gpsAlarmList.setAdapter(new GPSAlramAdapter(getActivity(), getDetails().getGpsAlarmInformation()));
		normalAlarmList.setAdapter(new NormalAlarmAdapter(getActivity(), getDetails().getNormalAlarmInformation()));
	}

}
