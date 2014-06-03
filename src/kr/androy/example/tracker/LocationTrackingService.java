package kr.androy.example.tracker;


import kr.androy.base.util.log.Logger;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationTrackingService extends Service implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

	private static Logger logger = Logger.getLogger(LocationTrackingService.class);
    public static final String BROADCAST_ACTION = "com.my.package.LOCATION_UPDATE";
    
    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 60;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 40;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    
    private LocationClient locationClient;

    
    ///////// [S] Life Cycle method ///////////
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
        logger.d("Location service created...");
        
        locationClient = new LocationClient(this, this, this);
        locationClient.connect();
	}
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.d("onStartCommand...");
        return START_STICKY;
    }   
    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.d("Location service destroyed…");

        clearLocationData();
    }	
	///////// [E] Life Cycle method ///////////
	
		
	///////// [S] LocationListener ///////////
	@Override
	public void onLocationChanged(Location location) {

		if (location == null) {
			return;
		}
		logger.d("LOCATION: " + location.getLatitude() + ":" + location.getLongitude());

        // Since location information updated, broadcast it
        Intent broadcast = new Intent();

        // Set action so other parts of application can distinguish and use this information if needed
        broadcast.setAction(BROADCAST_ACTION);
        broadcast.putExtra("latitude", location.getLatitude());
        broadcast.putExtra("longitude", location.getLongitude());

        sendBroadcast(broadcast);
		
	}
	///////// [S] ConnectionCallbacks ///////////
	@Override
	public void onConnected(Bundle arg0) {
		logger.d("Location Callback. onConnected");

        Location currentLocation = locationClient.getLastLocation();
        // Create the LocationRequest object
        LocationRequest locationRequest = LocationRequest.create();
        // Use high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        locationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        locationClient.requestLocationUpdates(locationRequest, this);

        onLocationChanged(currentLocation);
		
	}
	@Override
	public void onDisconnected() {
		logger.d("Location Callback. onDisconnected");
	}
	///////// [S] OnConnectionFailedListener ///////////
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		logger.d("Location Callback. onConnectionFailed");
	}
	///////// [E] OnConnectionFailedListener ///////////
	
    private void clearLocationData() {
        locationClient.disconnect();

        if (locationClient.isConnected()) {
            locationClient.removeLocationUpdates(this);
        }
    }
	
}
