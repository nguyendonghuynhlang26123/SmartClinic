package com.team13.doctorclient;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHandler {
    public static void sendNotification(Context ctx, String title, String body){
        Log.d("LONG", "SEND NOTI");
        Notification noti =  new NotificationCompat.Builder(ctx, Utils.NOTIFICATION_CHANNEL_ID).setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .build();

        NotificationManagerCompat notiManager = NotificationManagerCompat.from(ctx);
        notiManager.notify(1, noti);
    }

}
