package com.qunar.training.ExchangeRate;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过joda-time实现的时间处理工具
 * Created by shining.cui on 2016/6/6.
 */

public class TimeUtil {
   static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * 获得从今天开始的30天内的天数
     * @return 前30天对应的集合
     */
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

    /**
     * 获得当前时间的日期格式化信息
     * @return 当日日期
     */
    public static String getNowDateStr() {
        DateTime now = new DateTime();
        String dateStr = now.toString(formatter);
        return dateStr;
    }
}