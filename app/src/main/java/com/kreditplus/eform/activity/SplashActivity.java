package com.kreditplus.eform.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.service.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

/**
 * Created by Iwan Nurdesa on 25/05/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    private String[] typeMasterSyncs;
    private String token;
    private String countMastersync;
    private int count;
    private DatabaseService databaseService;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static long SLEEP_TIME = 1; // Sleep for some time
    private IntentLauncher launcher = new IntentLauncher();
    private String statusLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        App.appComponent().inject(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "splash");
        mFirebaseAnalytics.logEvent("splash_screen_open", bundle);

        try{
            token = sharedPreferences.getString(getResources().getString(R.string.sharedpref_token), "");
            statusLogin = sharedPreferences.getString(getResources().getString(R.string.sharedpref_status_login), "");
            countMastersync = sharedPreferences.getString(getResources().getString(R.string.sharedpref_masterCount), "");
            databaseService = new DatabaseService(this);
        }catch (Exception e){
            Log.e("Error Splash Screen",e.getLocalizedMessage());
            e.getStackTrace();
            startActivity(new Intent(SplashActivity.this, SigninActivity.class));
        }


        typeMasterSyncs = getResources().getStringArray(R.array.type_master_sync);

        try {
            databaseService.CreateDatabase();
        } catch (IOException e) {
            if (BuildConfig.DEBUG)
                Log.e("Create data base", String.valueOf(e));
        }

        try {
            databaseService.OpenDatabase();
        } catch (SQLException sqle) {
            try {
                throw sqle;
            } catch (SQLException e) {
                if (BuildConfig.DEBUG)
                    Log.e("sql", String.valueOf(e));
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    Log.e("Thread sleep", String.valueOf(e));
            }

            count = 0;
            if (!"".equalsIgnoreCase(countMastersync)) // cek apakah semua type mastersync sudah di download
                count = Integer.parseInt(countMastersync);
            if ("".equalsIgnoreCase(token)) {/*|| count < typeMasterSyncs.lenvqgth*/
                startActivity(new Intent(SplashActivity.this, SigninActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }

            finish();
            super.run();
        }

    }
}
