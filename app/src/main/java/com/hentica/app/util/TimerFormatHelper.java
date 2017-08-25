package com.hentica.app.util;

/**
 * 时间格式化工具，用于将秒数转换成指定单位的数值
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/8.15:55
 */

public class TimerFormatHelper {

    /**
     * 用于取不足1分的秒数
     * @param time 单位秒
     * @return
     */
    public static long getRemindSecond(long time){
        return time % 60;
    }

    /**
     * 获取转换后的分钟数
     * @param time 单位秒
     * @return
     */
    public static long getMinute(long time){
        time = time - getRemindSecond(time);
        return time / 60;
    }

    /**
     * 获取不足小时的分钟数据
     * @param time
     * @return
     */
    public static long getRemaindMinute(long time){
        return getMinute(time) % 60;
    }

    /**
     * 获取转换后的小时
     * @param time
     * @return
     */
    public static long getHour(long time){
        time = getMinute(time) - getRemaindMinute(time);
        return time / 60;
    }

    /**
     * 获取不足1天的小时数
     * @param time
     * @return
     */
    public static long getRemaindHour(long time){
        return getHour(time) % 24;
    }

    /**
     * 获取转换后的天数
     * @param time
     * @return
     */
    public static long getDay(long time){
        time = getHour(time) - getRemaindHour(time);
        return time / 24;
    }
}
