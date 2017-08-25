package com.hentica.app.util;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Snow on 2017/7/14.
 */
public class CalenderHelperTest {
    @Test
    public void setCalendar() throws Exception {
        Calendar calendar = Calendar.getInstance();
        CalenderHelper.setCalendar(calendar, 0, 0, 0, 0);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        equals("2017-07-14 00:00:00", fm.format(calendar.getTime()));
        CalenderHelper.setCalendar(calendar, 0, 23, 59, 59);
        equals("2017-07-14 23:59:59", fm.format(calendar.getTime()));
        CalenderHelper.setCalendar(calendar, 2, 0, 0, 0);
        equals("2017-07-16 00:00:00", fm.format(calendar.getTime()));
        CalenderHelper.resetCalendar(calendar);
        CalenderHelper.setCalendar(calendar, -1, 0, 0, 0);
        equals("2017-07-13 00:00:00", fm.format(calendar.getTime()));
        CalenderHelper.resetCalendar(calendar);
        CalenderHelper.setCalendar(calendar, -2, 0, 0, 0);
        equals("2017-07-12 00:00:00", fm.format(calendar.getTime()));

    }

    private void equals(String time1, String time2){
        Assert.assertEquals(time1, time2);
    }

    @Test
    public void testTime(){
        long startTime = 1499788800699l;
        Date startDate = new Date(startTime);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(fm.format(startDate));
        long endTime = 1500047999699l;
        Date endDate = new Date(endTime);
        System.out.println(fm.format(endDate));
    }

}