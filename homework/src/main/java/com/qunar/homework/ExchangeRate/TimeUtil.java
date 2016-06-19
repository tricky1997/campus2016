package com.qunar.homework.ExchangeRate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 处理时间相关的工具
 * Created by zhaocai-luo on 2016/6/15.
 */
public class TimeUtil {
    final int DAYS = 30;

    /**
     * 得到从今天起往前30天的日期的列表
     * @return 规定格式的日期列表
     */
    public List<String> getTimeList(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> timeList = new ArrayList<String>();
        for (int i = 0; i <= DAYS; i++){
            String time = sdf.format(calendar.getTime());
            timeList.add(time);
            calendar.add(Calendar.DATE, -1);
        }

        return timeList;
    }
}
