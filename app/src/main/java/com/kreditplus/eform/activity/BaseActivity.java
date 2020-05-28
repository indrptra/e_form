package com.kreditplus.eform.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.service.GPSTrackerService;

import butterknife.Unbinder;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;       // flag for GPS status
    public FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    protected abstract int getContentView();

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
            if (checkLocationPermission()) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (locationManager != null) isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGPSEnabled) {
                    if (!Util.isServiceRunning(this, GPSTrackerService.class)) {
                        // Start location service
                        try {
                            Intent intent = new Intent(this, GPSTrackerService.class);
                            startService(intent);
                        }catch (Exception e){

                        }
                    }
                } else {
                    showSettingsAlert();
                }
            } else {
                //minta permission / aktifin service
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void onStart() {
        super.onStart();
        // The rest of your onStart() code.
    }

    @Override
    public void onStop() {
        try {
            super.onStop();
        } catch (Exception e) {
            super.onStop();
        }
        // The rest of your onStop() code.
    }
}
