package com.test.kraftu.tinkoffnews.tools;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUntil {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String getStringDate(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return simpleDateFormat.format(calendar.getTime());
    }
}
