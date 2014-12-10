package com.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.alarm.framework.AlarmDetails;
import com.alarm.framework.BaseAlarmDetails;
import com.alarm.framework.GpsAlarmInformation;
import com.alarm.framework.NormalAlarmInformation;

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
	public void onResume() {
		super.onResume();
		gpsAlarmList.setAdapter(new GPSAlramAdapter(getActivity(), getDetails().getGpsAlarmInformation()));
		normalAlarmList.setAdapter(new NormalAlarmAdapter(getActivity(), getDetails().getNormalAlarmInformation()));
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		gpsAlarmList = (ListView) view.findViewById(R.id.gpsAlarmList);
		normalAlarmList = (ListView) view.findViewById(R.id.normalAlarmList);
		final GPSAlramAdapter adapter = new GPSAlramAdapter(getActivity(), getDetails().getGpsAlarmInformation());
		gpsAlarmList.setAdapter(adapter);
		final NormalAlarmAdapter normalAdapter = new NormalAlarmAdapter(getActivity(), getDetails()
				.getNormalAlarmInformation());
		normalAlarmList.setAdapter(normalAdapter);
		gpsAlarmList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				getDetails().getGpsAlarmInformation().remove(position);
				gpsAlarmList.setAdapter(adapter);
				saveData(getDetails());
				return true;
			}
		});
		gpsAlarmList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(getActivity(), GpsAlarmActivity.class);
				GpsAlarmInformation infor = (GpsAlarmInformation) view.getTag();
				i.putExtra("Location", infor.getLocation());
				i.putExtra("Distance", infor.getDistance());
				i.putExtra("Position", position);
				startActivity(i);

			}
		});

		normalAlarmList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(getActivity(), NormalAlarmActivity.class);
				NormalAlarmInformation infor = (NormalAlarmInformation) view.getTag();
				i.putExtra("Hours", infor.getHours());
				i.putExtra("Minutes", infor.getMin());
				i.putExtra("Position", position);
				startActivity(i);
			}
		});

		normalAlarmList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				getDetails().getNormalAlarmInformation().remove(position);
				normalAlarmList.setAdapter(normalAdapter);
				saveData(getDetails());
				return true;
			}
		});

	}
}
