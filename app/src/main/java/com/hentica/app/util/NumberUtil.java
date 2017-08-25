package com.hentica.app.util;

/**
 * 数据工具
 *
 * @author mili
 * @createTime 2016/10/12
 */
public class NumberUtil {

    /**
     * 字符串转换为数字
     *
     * @param val 字符串
     * @param def 默认值
     */
    public static int getInt(String val, int def) {

        try {
            return Integer.valueOf(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 字符串转换为数字
     *
     * @param val 字符串
     * @param def 默认值
     */
    public static long getLong(String val, long def) {

        try {
            return Integer.valueOf(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 字符串转换为数字
     *
     * @param val 字符串
     * @param def 默认值
     */
    public static float getFloat(String val, float def) {

        try {
            return Integer.valueOf(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 字符串转换为数字
     *
     * @param val 字符串
     * @param def 默认值
     */
    public static double getDouble(String val, double def) {

        try {
            return Integer.valueOf(val);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }
}
