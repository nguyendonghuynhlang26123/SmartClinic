package com.team13.patientclient;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static final String BACK_END_API_PATH = "https://smart-clinic-team13.herokuapp.com/";
    //public static final String BACK_END_API_PATH = "http://192.168.100.11:3669/";

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

    public static String formatingPrice(long price){
        Locale locale = new Locale("vi", "VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        return (fmt.format(price));
    }

    public static Timestamp addDays(Timestamp date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new Timestamp(cal.getTime().getTime());
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
            return null;
        }
    }
}
