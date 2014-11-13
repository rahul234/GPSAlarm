package com.alarm;

import java.util.ArrayList;
import java.util.List;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.AlarmTypeList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class MainFragment extends BaseAlarmFragment {
	
	private ExpandableListView expandableListView;

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.main_frag, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		expandableListView = (ExpandableListView) view.findViewById(R.id.alarmExpandableList);
		List<AlarmTypeList> alarmTypeList = new ArrayList<AlarmTypeList>(2);
		AlarmDetails details = new AlarmDetails();
		AlarmTypeList list1 = new AlarmTypeList();
		list1.setAlarmType("Create Alarm");
		AlarmTypeList list2 = new AlarmTypeList();
		list2.setAlarmType("Create GPS Alarm");
		alarmTypeList.add(list1);
		alarmTypeList.add(list2);
		details.setAlarmTypeList(alarmTypeList);
		expandableListView.setAdapter(new CustomAdapter(getActivity(),details.getAlarmTypeList()));
	}

}
