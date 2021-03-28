package com.team13.patientclient;

import android.annotation.SuppressLint;

import com.team13.patientclient.activities.ErrorResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

public class Utils {
    public static final String BACK_END_API_PATH = "http://192.168.100.8:3669/";
    public static final int NAME_LENGTH_LIMIT = 16;
    public static final String DATE_PATTERN = "dd-MM-yyyy";

    public static boolean checkValidPatientName(String text) {
        return text.length() < NAME_LENGTH_LIMIT;
    }

    public static String unFormatPhoneNumber(String raw) {
        return raw.replaceAll("[^\\d]","");
    }

    public static long dateStringToNumber(String formattedDate){
        try {
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat(DATE_PATTERN).parse(formattedDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static  int calculateAge(
            Date birthDate,
            Date currentDate) {
        // validate inputs ...
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
        return age;
    }
}
