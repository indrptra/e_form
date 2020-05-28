package com.kreditplus.eform.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.ChangePasswordActivity;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.activity.HomeActivity;
import com.kreditplus.eform.activity.PengajuanDetailActivity;
import com.kreditplus.eform.activity.PushNotifActivity;
import com.kreditplus.eform.activity.RetakePhotoActivity;
import com.kreditplus.eform.activity.SigninActivity;
import com.kreditplus.eform.model.TblFirebase;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    SharedPreferences.Editor editor;

    private NotificationVO notificationVO;
    private String strId;
    private String strTitle;
    private String strMessage;
    private String strIconUrl;
    private String strAction;
    private String strActionDestination;
    private String strTypeUser;
    private DatabaseService databaseService;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        token = "";
        if (token.isEmpty()) {

        } else {
            sharedPreferences = getSharedPreferences(getString(R.string.sharedprefname), Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString(getResources().getString(R.string.sharedpref_token_firebase_first), token);
            editor.apply();
            Log.i("token_firebase", token);
        }
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(Config.TAG, "From: " + remoteMessage.getFrom());
        notificationVO = new NotificationVO();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Config.TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            handleData(data);
            NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
//        notificationHelper.playNotificationSound();
            notificationHelper.sendChannel(notificationVO);
        }
    }

    private void handleNotification(RemoteMessage.Notification RemoteMsgNotification) {
        strMessage = RemoteMsgNotification.getBody();
        strTitle = RemoteMsgNotification.getTitle();
        notificationVO.setTitle(strTitle);
        notificationVO.setMessage(strMessage);
    }

    private void handleData(Map<String, String> data) {
        strId = data.get(Config.ID) == null ? "" : data.get(Config.ID);
        strTitle = data.get(Config.TITLE);
        strMessage = data.get(Config.MESSAGE);
        strIconUrl = data.get(Config.IMAGE);
        strAction = data.get(Config.ACTION);
        strActionDestination = data.get(Config.ACTION_DESTINATION);
        strTypeUser = data.get(Config.TYPE_USER);

        notificationVO.setId(strId);
        notificationVO.setTitle(strTitle);
        notificationVO.setMessage(strMessage);
        notificationVO.setIconUrl(strIconUrl);
        notificationVO.setAction(strAction);
        notificationVO.setActionDestination(strActionDestination);
        notificationVO.setStrTypeUser(strTypeUser);
    }
}
