package com.team13.doctorclient;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

public class MyApp extends Application {
    private static com.team13.doctorclient.MyApp instance;
    public static com.team13.doctorclient.MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(Utils.NOTIFICATION_CHANNEL_ID,"Notification channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationChannel.setDescription("EDoctor patient app notification channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
