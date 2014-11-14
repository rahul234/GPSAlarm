package com.alarm.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScrollableListView extends ListView {
	public ScrollableListView(Context context) {
		super(context);
	}

	public ScrollableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec;
		heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightSpec);
	}
}