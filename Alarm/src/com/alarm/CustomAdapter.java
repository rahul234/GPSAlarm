package com.alarm;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alarm.framework.AlarmDetails;

public class CustomAdapter implements ExpandableListAdapter {

	private final Activity activity;
	private final List<AlarmDetails> alarmTypeList;

	public CustomAdapter(Activity activity, List<AlarmDetails> alarmTypeList) {
		this.activity = activity;
		this.alarmTypeList = alarmTypeList;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return null;
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return 0;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		return null;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return 0;
	}

	@Override
	public long getCombinedChildId(long arg0, long arg1) {
		return 0;
	}

	@Override
	public long getCombinedGroupId(long arg0) {
		return 0;
	}

	@Override
	public Object getGroup(int arg0) {
		return alarmTypeList.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return alarmTypeList.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}

	@Override
	public View getGroupView(final int position, boolean attach,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.group_item, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(position == 0 ? "Create alarm" : "Create GPS alarm");

		ImageView image = (ImageView) convertView
				.findViewById(R.id.addButtonImage);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity.getApplicationContext(),
						position == 0 ? NormalAlarmActivity.class
								: GpsAlarmActivity.class);
				activity.startActivity(i);

			}
		});

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void onGroupCollapsed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupExpanded(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
