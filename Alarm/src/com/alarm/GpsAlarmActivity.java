package com.alarm;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsAlarmActivity extends BaseAlarmActivity {

	private EditText loactionDetails;
	private GoogleMap map;

	protected void getLocationFromAddress(String strAddress) {

		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
			address = coder.getFromLocationName(strAddress, 5);

			if (address == null) {
				return;
			}
			Address location = address.get(0);
			showMap(location.getLatitude(), location.getLongitude());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_alarm_activity);
		loactionDetails = (EditText) findViewById(R.id.loactionDetails);

	}

	public void onSearchClicked(View view) {
		String address = loactionDetails.getText().toString();
		if (!address.isEmpty() && address != null) {
			getLocationFromAddress(address);

		}
	}

	protected void showMap(double lat, double longi) {
		LatLng ltlng = new LatLng(lat, longi);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.addMarker(new MarkerOptions().position(ltlng).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place)));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlng, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}

}
