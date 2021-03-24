package com.team13.patientclient;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    public static final String SHARED_PREFS = "edoctor_PRef";

    public static String getData(Context context, String type){
        SharedPreferences sharePref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharePref.getString(type, "");
    }

    public static boolean saveData(Context context, String type, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(type, value);
        editor.apply();
        return true;
    }
}
