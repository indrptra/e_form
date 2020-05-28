package com.kreditplus.eform.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.kreditplus.eform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionCamera {
    private Activity mActivity;
    private final int REQUEST_PERMISSION = 99;
    private String TAG = "PermissionHelper";
    private PermissionListener listener;

    public PermissionCamera(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void permissionListener(PermissionListener permissionListener) {
        this.listener = permissionListener;
    }

    //1. Call this to check permission. (Call this affected loop for check permission until user Approved it)
    public void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCamera = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);

            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionCamera != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(Manifest.permission.CAMERA);
            if (!listPermissionsNeeded.isEmpty())
                ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_PERMISSION);
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listener.onPermissionCheckDone();
    }

    //2. call this inside onRequestPermissionsResult
    public void onRequestCallBack(int RequestCode, String[] permissions, int[] grantResults) {
        switch (RequestCode) {
            case REQUEST_PERMISSION: {
                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "permission granted");

                        checkAndRequestPermissions();
                    } else {
                        Log.e(TAG, "Some permissions are not granted ask again ");
                        // permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        // shouldShowRequestPermissionRationale will return true
                        // show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)) {
                            showDialogOK(mActivity.getString(R.string.permission_dialog),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
//                                                case DialogInterface.BUTTON_NEGATIVE:
//                                                    // proceed with logic by disabling the related features or quit the app.
//                                                    break;
                                            }
                                        }
                                    });
                        }
                        // permission is denied (and never ask again is  checked)
                        // shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(mActivity, R.string.go_to_permissions_settings, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                            intent.setData(uri);
                            mActivity.startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    public interface PermissionListener {
        void onPermissionCheckDone();
    }
}
