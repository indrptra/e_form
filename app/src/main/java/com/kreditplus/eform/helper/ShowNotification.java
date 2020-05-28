package com.kreditplus.eform.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.HomeActivity;

/**
 * Created by apc-lap012 on 07/08/17.
 */

public class ShowNotification {

    private NotificationManager notificationManager;
    private static int notificationid = 99;
    private static int notifictionRequest = 88;

    public static void dataNotification(String bigtext, String title, Context context, String contentText){

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingtIntent = PendingIntent.getActivity(context, notifictionRequest,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigtext))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingtIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        if (notification != null)
            notificationManager.notify(notificationid,notification);
    }
}
