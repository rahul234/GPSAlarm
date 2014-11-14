package com.alarm;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alarm.framework.GpsAlarmInformation;

public class GPSAlramAdapter extends BaseAdapter{
	
	private final Activity activity;
	private final List<GpsAlarmInformation> information;
	
	public GPSAlramAdapter(Activity activity, List<GpsAlarmInformation> information){
		this.activity= activity;
		this.information= information;
	}

	@Override
	public int getCount() {
		return information.size();
	}

	@Override
	public Object getItem(int position) {
		return information.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView== null){
			convertView = activity.getLayoutInflater().inflate(R.layout.gps_list_item, parent, false);
		}
		TextView distance = (TextView) convertView.findViewById(R.id.distance);
		TextView loction = (TextView) convertView.findViewById(R.id.loction);
		distance.setText(information.get(position).getDistance() + " " + "Km");
		loction.setText(information.get(position).getLocation());
		return convertView;
	}

}
