package com.globapp.globapp.util;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTextGetter {
    public static String getDateText(Date date){
        Date currentTime = Calendar.getInstance().getTime();
        long diff = currentTime.getTime() - date.getTime();

        TimeUnit timeDays = TimeUnit.DAYS;
        long difference = timeDays.convert(diff, TimeUnit.MILLISECONDS);

        if(difference >= 7){
            return date.toString();
        } else if(difference < 7 && difference >= 1) {
            return difference + " days ago";
        } else {
            TimeUnit timeHours = TimeUnit.HOURS;
            difference = timeHours.convert(diff, TimeUnit.MILLISECONDS);

            if(difference >= 1){
                return difference + " hours ago";
            } else {
                TimeUnit timeMinutes = TimeUnit.MINUTES;
                difference = timeMinutes.convert(diff, TimeUnit.MILLISECONDS);

                if(difference > 1){
                    return difference + " minutes ago";
                } else {
                    return "few moments ago";
                }
            }
        }
    }
}
