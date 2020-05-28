package com.kreditplus.eform;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.core.CrashlyticsListener;
import com.facebook.stetho.Stetho;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.activity.BaseActivity;
import com.kreditplus.eform.activity.HomeActivity;
import com.kreditplus.eform.dagger.applicationscope.ApplicationComponent;
import com.kreditplus.eform.dagger.applicationscope.ApplicationModule;
import com.kreditplus.eform.dagger.applicationscope.DaggerApplicationComponent;

import java.util.stream.DoubleStream;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import dagger.Module;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Iwan Nurdesa on 24/05/16.
 */
public class App extends Application {

    public static final int REQUEST_NOTIFICATION_CONNECTION = 98098;
    private static ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

//        Fabric.with(this, new Crashlytics());
        CaocConfig.Builder.create()
                .showErrorDetails(false)
                .showRestartButton(true)
                .apply();


        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
        initNotificationChannel();

    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(getString(R.string.notification_channel_default_id), "getString(R.string.notification_channel_default_name)", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(getString(R.string.notification_channel_default_name));

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
    public static ApplicationComponent appComponent() {
        return applicationComponent;
    }
}
