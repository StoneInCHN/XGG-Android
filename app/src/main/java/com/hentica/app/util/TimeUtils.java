package com.hentica.app.util;

/**
 * 日间工具
 *
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/7.11:49
 */

public class TimeUtils {

    /**
     * 从固定格式时间字符串，转成成两时间字符串"XX:XX"
     *
     * @param time 格式"XX:XX-XX:XX"
     * @return
     */
    public static String[] getTimes(String time){
        return time.split("-");
    }

    /**
     * 从时间字符串中折出小时
     *
     * @param time 格式"08:00"
     * @return 出错返回0
     */
    public static int splitStringHour(String time) {
        String[] arrays = time.split(":");
        int value = getValue(arrays[0]);
        if (value > -1) {
            return value;
        }
        return 0;
    }

    /**
     * 从时间字符串中折出小时
     *
     * @param time 格式"08:00"——"时:分"
     * @return 出错返回0
     */
    public static int splitStringMinute(String time) {
        String[] arrays = time.split(":");
        if (arrays.length < 2) {
            //未拆分出时：分
            return 0;
        }
        int value = getValue(arrays[1]);
        if (value > -1) {
            return value;
        }
        return 0;
    }

    /**
     * 将字符串转换成整型值
     *
     * @param text
     * @return -1，转换失败
     */
    public static int getValue(String text) {
        text = text.trim();
        int result = -1;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException e) {

        }
        return result;
    }

}
