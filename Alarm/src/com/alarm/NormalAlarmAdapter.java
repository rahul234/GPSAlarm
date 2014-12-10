package com.alarm;

import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alarm.framework.NormalAlarmInformation;

public class NormalAlarmAdapter extends BaseAdapter{
	
	private final Activity activity;
	private final List<NormalAlarmInformation> information;
	
	public NormalAlarmAdapter(Activity activity, List<NormalAlarmInformation> information){
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
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerDataSetObserver(observer);
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView== null){
			convertView = activity.getLayoutInflater().inflate(R.layout.normal_alarm_item, parent, false);
		}
		convertView.setTag(information.get(position));
		TextView time = (TextView) convertView.findViewById(R.id.time);
		String alarmTime = new StringBuilder().append(pad(information.get(position).getHours()))
				.append(":").append(pad(information.get(position).getMin())).toString();
		time.setText(alarmTime);
		return convertView;
	}

}