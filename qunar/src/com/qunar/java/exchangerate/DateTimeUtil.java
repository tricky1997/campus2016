package com.qunar.java.exchangerate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-28
 * Time: 下午3:16
 * 功能：日期和时间处理功能类
 */
public class DateTimeUtil {
    /**
     * @param d
     * @param day
     * @return 功能：获取指定天数以前的日期
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 功能：对日期进行格式化
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
}
