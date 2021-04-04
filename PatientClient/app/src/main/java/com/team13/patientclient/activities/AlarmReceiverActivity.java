package com.team13.patientclient.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.team13.patientclient.NotificationHandler;

public class AlarmReceiverActivity extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LONG");
        wl.acquire(1*60*1000L /*1 minutes*/);
        NotificationHandler.sendNotification(context, "YEAH YEAH", "YEAH HEHEHE");
        wl.release();
    }
}
