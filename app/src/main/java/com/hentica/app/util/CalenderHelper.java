package com.hentica.app.util;

import java.util.Calendar;

/**
 * Created by Snow on 2017/7/14.
 */

public class CalenderHelper {

    /**
     * 日历重置
     * @param calendar
     * @return
     */
    public static void resetCalendar(Calendar calendar){
        if (calendar != null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
        }
    }

    /**
     * 设置日历时间
     * @param calendar
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static void setCalendar(Calendar calendar, int dayCount, int hour, int minute, int second){
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        calendar.add(Calendar.DAY_OF_MONTH, dayCount);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
    }

}
