package com.qunar.training.ExchangeRate;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shining.cui on 2016/6/6.
 */

public class TimeUtil {
   static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static List<String> getThirtyDaysFromNow() {
        List<String> days = new ArrayList<String>();
        //从今天开始向前一共30天
        DateTime now = new DateTime();
        for (int i = 29; i >= 0; i--) {
            DateTime dateTime = now.minusDays(i);
            String dateStr = dateTime.toString(formatter);
            days.add(dateStr);
        }
        return days;
    }

    public static String getNowDateStr() {
        DateTime now = new DateTime();
        String dateStr = now.toString(formatter);
        return dateStr;
    }
}