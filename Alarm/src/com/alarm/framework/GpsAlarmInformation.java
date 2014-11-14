package com.alarm.framework;

import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

public class GpsAlarmInformation  {
	
	private String distance ="";
	private String location="";
	private double latitude;
	private double longitutes;
	 private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
	            GEOFENCE_EXPIRATION_IN_HOURS * DateUtils.HOUR_IN_MILLIS;

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitutes() {
		return longitutes;
	}

	public void setLongitutes(double longitutes) {
		this.longitutes = longitutes;
	}
	
	 public Geofence toGeofence() {
	        // Build a new Geofence object
	        return new Geofence.Builder()
	                       .setCircularRegion(
	                               getLatitude(),
	                               getLongitutes(),
	                               Float.valueOf (getDistance()))
	                       .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
	                       .build();
	    }

}
