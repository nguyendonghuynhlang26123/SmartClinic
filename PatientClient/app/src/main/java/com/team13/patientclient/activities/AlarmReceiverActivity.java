package com.team13.patientclient.activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.team13.patientclient.NotificationHandler;
import com.team13.patientclient.Utils;

public class AlarmReceiverActivity extends BroadcastReceiver {
    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        //PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LONG");
        //wl.acquire(1*60*1000L /*1 minutes*/);\
//        Log.d("LONG", "RECEIVED ALARM");
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            String appointmentId = intent.getStringExtra(Utils.BROADCAST_APPOINTMENT_ID);
            String patientId = intent.getStringExtra(Utils.BROADCAST_PATIENT_ID);
            if (appointmentId != null && patientId != null) {
//            PatientService service = new PatientService();
//            service.cancelAppointment(patientId, appointmentId, new OnSuccessResponse<Void>() {
//                @Override
//                public void onSuccess(Void response) {
                NotificationHandler.sendNotification(context, "Appointment alarm", "Your current appointment is coming");
//                }
//            });
            }
        }

        //wl.release();
    }
}
