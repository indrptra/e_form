package com.kreditplus.eform.service;

/**
 * Created by nurirppan on 12/28/2017.
 */

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kreditplus.eform.R;


public class GPSTrackerService extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0f; // 10 meters
//    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 Second

    // Declaring a Location Manager
    protected LocationManager locationManager;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prefs = getSharedPreferences(getString(R.string.sharedprefname), Context.MODE_PRIVATE);
        editor = prefs.edit();
        trackLocation();

        return Service.START_NOT_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void trackLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager == null) stopSelf();

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null && (location.getLongitude() != 0.0 && location.getLatitude() != 0.0))
            onLocationChanged(location);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String strLongitude = String.valueOf(location.getLongitude());
        String strLatitude = String.valueOf(location.getLatitude());
        editor.putString(getString(R.string.sharedpref_location_longitude), strLongitude);
        editor.putString(getString(R.string.sharedpref_location_latitude), strLatitude);
        editor.apply();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
