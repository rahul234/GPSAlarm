package com.alarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alarm.framework.BaseAlarmDetails;
import com.alarm.framework.GeofenceUtils;
import com.alarm.framework.GeofenceUtils.REMOVE_TYPE;
import com.alarm.framework.GeofenceUtils.REQUEST_TYPE;
import com.alarm.framework.GpsAlarmInformation;
import com.alarm.framework.SimpleGeofence;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GpsAlarmActivity extends BaseAlarmActivity {

	private EditText loactionDetails;
	private GoogleMap map;
	private TextView loactionDistace;
	 // Store the current request
    private REQUEST_TYPE mRequestType;
    private SimpleGeofence UIGeofence;
    private double lat;
    private double longit;
    private List<String> mGeofenceIdsToRemove;
    private REMOVE_TYPE mRemoveType;
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * DateUtils.HOUR_IN_MILLIS;
    private GeofenceRemover mGeofenceRemover;
    // Store a list of geofences to add
    List<Geofence> mCurrentGeofences;

    // Add geofences handler
    private GeofenceRequester mGeofenceRequester;
    
	protected void getLocationFromAddress(String strAddress) {

		Geocoder coder = new Geocoder(this);
		List<Address> address;

		try {
			address = coder.getFromLocationName(strAddress, 5);

			if (address == null) {
				return;
			}
			Address location = address.get(0);
			lat = location.getLatitude();
			longit = location.getLongitude();
			showMap(location.getLatitude(), location.getLongitude());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	 @Override
	    protected void onResume() {
	        super.onResume();
	        // Register the broadcast receiver to receive status updates
	        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mIntentFilter);
	    }


	
	public class GeofenceSampleReceiver extends BroadcastReceiver {
        /*
         * Define the required method for broadcast receivers
         * This method is invoked when a broadcast Intent triggers the receiver
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            // Check the action code and determine what to do
            String action = intent.getAction();

            // Intent contains information about errors in adding or removing geofences
            if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ERROR)) {

                handleGeofenceError(context, intent);

            // Intent contains information about successful addition or removal of geofences
            } else if (
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_ADDED)
                    ||
                    TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCES_REMOVED)) {

                handleGeofenceStatus(context, intent);

            // Intent contains information about a geofence transition
            } else if (TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION)) {

                handleGeofenceTransition(context, intent);

            // The Intent contained an invalid action
            } else {
                Toast.makeText(context, "Invalid", Toast.LENGTH_LONG).show();
            }
        }

        /**
         * If you want to display a UI message about adding or removing geofences, put it here.
         *
         * @param context A Context for this component
         * @param intent The received broadcast Intent
         */
        private void handleGeofenceStatus(Context context, Intent intent) {

        }

        /**
         * Report geofence transitions to the UI
         *
         * @param context A Context for this component
         * @param intent The Intent containing the transition
         */
        private void handleGeofenceTransition(Context context, Intent intent) {
            /*
             * If you want to change the UI when a transition occurs, put the code
             * here. The current design of the app uses a notification to inform the
             * user that a transition has occurred.
             */
        }

        /**
         * Report addition or removal errors to the UI, using a Toast
         *
         * @param intent A broadcast Intent sent by ReceiveTransitionsIntentService
         */
        private void handleGeofenceError(Context context, Intent intent) {
            String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
            Log.e(GeofenceUtils.APPTAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

	
	
	    
	 private GeofenceSampleReceiver mBroadcastReceiver;

	    // An intent filter for the broadcast receiver
	    private IntentFilter mIntentFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_alarm_activity);
		loactionDetails = (EditText) findViewById(R.id.loactionDetails);
		loactionDistace = (TextView) findViewById(R.id.loactionDistace);
		  mBroadcastReceiver = new GeofenceSampleReceiver();

	        // Create an intent filter for the broadcast receiver
	        mIntentFilter = new IntentFilter();

	        // Action for broadcast Intents that report successful addition of geofences
	        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_ADDED);

	        // Action for broadcast Intents that report successful removal of geofences
	        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCES_REMOVED);

	        // Action for broadcast Intents containing various types of geofencing errors
	        mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);

	        // All Location Services sample apps use this category
	        mIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);

	        
	        

	        // Instantiate the current List of geofences
	        mCurrentGeofences = new ArrayList<Geofence>();

	        // Instantiate a Geofence requester
	        mGeofenceRequester = new GeofenceRequester(this);
	        mGeofenceRemover = new GeofenceRemover(this);


	}

	public void onSearchClicked(View view) {
		String address = loactionDetails.getText().toString();
		if (!address.isEmpty() && address != null) {
			getLocationFromAddress(address);

		}
	}

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // If the request was to add geofences
                        if (GeofenceUtils.REQUEST_TYPE.ADD == mRequestType) {

                            // Toggle the request flag and send a new request
                            mGeofenceRequester.setInProgressFlag(false);

                            // Restart the process of adding the current geofences
                            mGeofenceRequester.addGeofences(mCurrentGeofences);

                        // If the request was to remove geofences
                        } else if (GeofenceUtils.REQUEST_TYPE.REMOVE == mRequestType ){

                            // Toggle the removal flag and send a new removal request
                            mGeofenceRemover.setInProgressFlag(false);

                            // If the removal was by Intent
                            if (GeofenceUtils.REMOVE_TYPE.INTENT == mRemoveType) {

                                // Restart the removal of all geofences for the PendingIntent
                                mGeofenceRemover.removeGeofencesByIntent(
                                    mGeofenceRequester.getRequestPendingIntent());

                            // If the removal was by a List of geofence IDs
                            } else {

                                // Restart the removal of the geofence list
                                mGeofenceRemover.removeGeofencesById(mGeofenceIdsToRemove);
                            }
                        }
                    break;

                    // If any other result was returned by Google Play services
                    default:

                        // Report that Google Play services was unable to resolve the problem.
                        Log.d(GeofenceUtils.APPTAG, getString(R.string.no_resolution));
                }

            // If any other request code was received
            default:
               // Report that this Activity received an unknown requestCode
               Log.d(GeofenceUtils.APPTAG,
                       getString(R.string.unknown_activity_request_code, requestCode));

               break;
        }
    }
	
	public void onSaveClicked(View view){	
		String distance = loactionDistace.getText().toString();
		String location = loactionDetails.getText().toString();
		if(!"".equals(distance) && !"".equals(location))
		{
			GpsAlarmInformation gpsAlarm = new GpsAlarmInformation();
			gpsAlarm.setDistance(distance);
			gpsAlarm.setLocation(location);
			gpsAlarm.setLatitude(longit);
			gpsAlarm.setLongitutes(longit);
			BaseAlarmDetails.getSingletonInstance().getDetails().getGpsAlarmInformation().add(gpsAlarm);
			saveData(BaseAlarmDetails.getSingletonInstance().getDetails());
			  /*
	         * Record the request as an ADD. If a connection error occurs,
	         * the app can automatically restart the add request if Google Play services
	         * can fix the error
	         */
	        mRequestType = GeofenceUtils.REQUEST_TYPE.ADD;

	        /*
	         * Check for Google Play services. Do this after
	         * setting the request type. If connecting to Google Play services
	         * fails, onActivityResult is eventually called, and it needs to
	         * know what type of request was in progress.
	         */
	        if (!servicesConnected()) {

	            return;
	        }

	       

	        /*
	         * Create a version of geofence 1 that is "flattened" into individual fields. This
	         * allows it to be stored in SharedPreferences.
	         */
	        UIGeofence = new SimpleGeofence("1",lat,longit,
	            Float.valueOf(distance),
	            // Set the expiration time
	            GEOFENCE_EXPIRATION_IN_MILLISECONDS,
	            // Only detect entry transitions
	            Geofence.GEOFENCE_TRANSITION_ENTER);
	        /*
	         * Add Geofence objects to a List. toGeofence()
	         * creates a Location Services Geofence object from a
	         * flat object
	         */
	        mCurrentGeofences.add(gpsAlarm.toGeofence());
	        

	        // Start the request. Fail if there's already a request in progress
	        try {
	            // Try to add geofences
	            mGeofenceRequester.addGeofences(mCurrentGeofences);
	        } catch (UnsupportedOperationException e) {
	            // Notify user that previous request hasn't finished.
	            Toast.makeText(this, R.string.add_geofences_already_requested_error,
	                        Toast.LENGTH_LONG).show();
	        }

	 			
		}else{
			Toast.makeText(this, "Please enter the distance from location, when you want us to trigger the alarm", Toast.LENGTH_SHORT).show();
		}
	}

	
	  public static class ErrorDialogFragment extends DialogFragment {

	        // Global field to contain the error dialog
	        private Dialog mDialog;

	        /**
	         * Default constructor. Sets the dialog field to null
	         */
	        public ErrorDialogFragment() {
	            super();
	            mDialog = null;
	        }

	        /**
	         * Set the dialog to display
	         *
	         * @param dialog An error dialog
	         */
	        public void setDialog(Dialog dialog) {
	            mDialog = dialog;
	        }

	        /*
	         * This method must return a Dialog to the DialogFragment.
	         */
	        @Override
	        public Dialog onCreateDialog(Bundle savedInstanceState) {
	            return mDialog;
	        }
	    }
	

	    private boolean servicesConnected() {

	        // Check that Google Play services is available
	        int resultCode =
	                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

	        // If Google Play services is available
	        if (ConnectionResult.SUCCESS == resultCode) {

	            // In debug mode, log the status
	            Log.d(GeofenceUtils.APPTAG, "Available");

	            // Continue
	            return true;

	        // Google Play services was not available for some reason
	        } else {

	            // Display an error dialog
	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
	            if (dialog != null) {
	                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
	                errorFragment.setDialog(dialog);
	                errorFragment.show(getSupportFragmentManager(), GeofenceUtils.APPTAG);
	            }
	            return false;
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
