package com.team13.patientclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {
    public static void showOkDialog(Context ctx, String msg, String title, Runnable fn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage(msg).setTitle(title);

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                    fn.run();
                });

        AlertDialog alert = builder.create();

        alert.show();
    }
    public static void showConfirmDialog(Context ctx, String msg, String title, Runnable onConfirmCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setMessage(msg).setTitle(title);

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    dialog.dismiss();
                    onConfirmCallback.run();
                })
                .setNegativeButton("No", ((dialog, id) ->{
                    dialog.dismiss();
                }));

        AlertDialog alert = builder.create();

        alert.show();
    }
}
