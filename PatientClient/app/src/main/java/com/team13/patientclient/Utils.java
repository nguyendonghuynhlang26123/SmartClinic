package com.team13.patientclient;

import com.team13.patientclient.activities.ErrorResponse;

import retrofit2.Response;

public class Utils {
    public static final String BACK_END_API_PATH = "http://192.168.100.8:3669/";

    public static int calculateAge(String dob) {
        return 0;
    }

    public static String unFormatPhoneNumber(String raw) {
        return raw.replaceAll("[^\\d]","");
    }

}
