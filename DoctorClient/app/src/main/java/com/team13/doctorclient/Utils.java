package com.team13.doctorclient;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static final String BACK_END_API_PATH = "https://smart-clinic-team13.herokuapp.com/";
    public static final int NAME_LENGTH_LIMIT = 16;
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String TIME_PATTERN = "HH:mm";

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
}
