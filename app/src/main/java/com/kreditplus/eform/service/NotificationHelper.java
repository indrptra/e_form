package com.kreditplus.eform.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.ChangePasswordActivity;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.activity.FormPengajuanNonElcActivity;
import com.kreditplus.eform.activity.PengajuanDetailActivity;
import com.kreditplus.eform.activity.PushNotifActivity;
import com.kreditplus.eform.activity.RetakePhotoActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

public class NotificationHelper {
    private Context mContext;
    private NotificationManagerCompat notificationManagerCompat;

    public NotificationHelper(Context mContext) {
        this.mContext = mContext;
    }

    void sendChannel(NotificationVO notificationVO) {
        String id = notificationVO.getId() == null ? "" : notificationVO.getId();
        String message = notificationVO.getMessage() == null ? "" : notificationVO.getMessage();
        String title = notificationVO.getTitle() == null ? "" : notificationVO.getTitle();
        String iconUrl = notificationVO.getIconUrl() == null ? "" : notificationVO.getIconUrl();
        String action = notificationVO.getAction() == null ? "" : notificationVO.getAction();
        String destination = notificationVO.getActionDestination() == null ? "" : notificationVO.getActionDestination();
        String typeUser = notificationVO.getStrTypeUser() == null ? "" : notificationVO.getStrTypeUser();
        Bitmap iconBitMap = null;


        if (iconUrl != null) iconBitMap = getBitmapFromURL(iconUrl);

        if (destination.equals("SigninActivity")) {
            Intent intentAutoLogout = new Intent(mContext, PushNotifActivity.class);
            intentAutoLogout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentAutoLogout.putExtra("action_destination", "SigninActivity");
            mContext.startActivity(intentAutoLogout);
        } else {
            notificationManagerCompat = NotificationManagerCompat.from(mContext);

            Intent intent = null;
            if (destination.equals("FormPengajuanActivity")) {
                if (typeUser.equalsIgnoreCase("cro")) {
                    intent = new Intent(mContext, FormPengajuanActivity.class);

                } else if (typeUser.equalsIgnoreCase("cmo")) {
                    intent = new Intent(mContext, FormPengajuanNonElcActivity.class);

                } else {
                    intent = new Intent(mContext, FormPengajuanNonElcActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("app_id", id);
                intent.putExtra("isAssignEdit", "isAssignEdit");
                if(typeUser.equalsIgnoreCase("assign_manual")){
                    intent.putExtra("form", "Draft");
                }else{
                    intent.putExtra("form", "Edit");
                }

            }
            if (destination.equals("RetakePhotoActivity")) {/*beluman*/
                intent = new Intent(mContext, RetakePhotoActivity.class);
                intent.putExtra("app_id", id);
            }
            if (destination.equals("ChangePasswordActivity")) {
                intent = new Intent(mContext, ChangePasswordActivity.class);
            }
            if (destination.equals("PengajuanDetailActivity")) {
                intent = new Intent(mContext, PengajuanDetailActivity.class);
                intent.putExtra("app_id", id);
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(mContext, mContext.getString(R.string.notification_channel_default_id)).setSmallIcon(R.drawable.ic_launcher).
                    setContentTitle(title).setContentText(message).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_MESSAGE).setContentIntent(pendingIntent).setAutoCancel(true).build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManagerCompat.notify(1, notification);
        }
    }


    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
