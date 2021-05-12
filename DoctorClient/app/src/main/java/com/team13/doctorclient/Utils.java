package com.team13.doctorclient;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static final String BACK_END_API_PATH = "https://smart-clinic-team13.herokuapp.com/";
//    public static final String BACK_END_API_PATH = "http://192.168.100.7:3669/";
    public static final int NAME_LENGTH_LIMIT = 16;
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATETIME_PATTERN = "HH:mm dd/MM/yyyy";

    //INTENT
    public static final int QRSCAN_RESULT_INTENT = 136;

    //STATUS
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_CANCELED = "CANCELED";

    //AUTHORIZATION
    public static final String USER_TYPE_DOCTOR = "DOCTOR";
    public static final String USER_TYPE_NURSE = "NURSE";

    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";

    public static String unFormatPhoneNumber(String raw) {
        return raw.replaceAll("[^\\d]","");
    }

    public static long dateStringToNumber(String formattedDate){
        try {
            @SuppressLint("SimpleDateFormat") Date date = new SimpleDateFormat(DATE_PATTERN).parse(formattedDate);
            assert date != null;
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String dateNumberToString(long timeStamp){
        Date date = new Date(timeStamp);
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        return format.format(date);
    }

    public static String getCurrentDateString(){
        return new SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().getTime());
    }

    public static String shortenString(String string, int charLimit){
        if (string.length()<= charLimit) return string;
        return string.substring(0,charLimit)+"...";
    }


    @SuppressLint("NewApi")
    public static ArrayList<String> generateTimes(String startTime, String closeTime, int gapInMinutes) {
        try {
            int startMin = Integer.parseInt(startTime.substring(3,5));
            int startHour = Integer.parseInt(startTime.substring(0,2));
            int closeMin = Integer.parseInt(closeTime.substring(3,5));
            int closeHour = Integer.parseInt(closeTime.substring(0,2));

            LocalTime currentTime = LocalTime.of(startHour, startMin);
            LocalTime endTime = LocalTime.of(closeHour, closeMin);

            ArrayList<String> result = new ArrayList<>();

            while(currentTime.isBefore(endTime)){
                result.add(currentTime.toString());
                currentTime = currentTime.plusMinutes(gapInMinutes);
            }

            return result;
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @SuppressLint("NewApi")
    public static ArrayList<String> getAvailableTime(ArrayList<String> curShifts) {
        SimpleDateFormat timeFormat = new SimpleDateFormat(Utils.TIME_PATTERN , Locale.US);
        String curTime = timeFormat.format(new Timestamp(System.currentTimeMillis()));
        int curMinute = Integer.parseInt(curTime.substring(3,5));
        int curHour = Integer.parseInt(curTime.substring(0,2));
        LocalTime currentTime = LocalTime.of(curHour,curMinute );

        for (int i = 0; i < curShifts.size(); i++) {
            String time = curShifts.get(i);
            int min = Integer.parseInt(time.substring(3,5));
            int hour = Integer.parseInt(time.substring(0,2));

            if (currentTime.isBefore(LocalTime.of(hour,min))) {
                return new ArrayList<>(curShifts.subList(i, curShifts.size()));
            }
        }
        return new ArrayList<>();
    }

    //Compare if time1 is BEFORE time
    //! IMPORTANT: time1 and time2 must be in DATETIME_PATTERN format!
    @SuppressLint("SimpleDateFormat")
    public static boolean compareTimes(String time1, String time2){
        try {
            Date dTime1 = new SimpleDateFormat(DATETIME_PATTERN).parse(time1);
            Date dTime2 = new SimpleDateFormat(DATETIME_PATTERN).parse(time2);

            return dTime1.before(dTime2);
        } catch(Exception e) {
            return false;
        }
    }
}
