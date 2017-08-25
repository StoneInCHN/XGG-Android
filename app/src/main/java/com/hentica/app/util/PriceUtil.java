package com.hentica.app.util;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 价格工具
 *
 * @author mili
 * @createTime 2016/10/19
 */
public class PriceUtil {

    /**
     * 格式化价格，通用格式
     *
     * @param price 价格
     */
    public static String format(double price) {
//        int value = (int) price;
//        if(value == price){
//            return String.valueOf(value);
//        }
        return String.format(Locale.getDefault(), "%.02f", keep2Decimal(price));
    }

    /**
     * 格式化价格，通用格式
     *
     * @param price 价格
     */
    public static String format2(double price) {
        int value = (int) price;
        if(value == price){
            return String.valueOf(value);
        }
        return String.format(Locale.getDefault(), "%.02f", keep2Decimal(price));
    }

    public static String format4Decimal(double d){
        return String.format(Locale.getDefault(), "%.04f", keepDecimal(d, 4));
    }

    /**
     * 格式化价格，只保留整数
     *
     * @param price 价格
     */
    public static String formatInt(double price) {

        return String.format(Locale.getDefault(), "%.0f", price);
    }

    /**
     * 四舍五入保留两位小数
     * @param d
     * @return
     */
    public static double keep2Decimal(double d){
        return  keepDecimal(d, 2);
    }

    /**
     * 四舍五入，保留小数
     * @param d
     * @param number 保留的小数位数
     * @return
     */
    public static double keepDecimal(double d, int number){
        BigDecimal big = new BigDecimal(d);
        return big.setScale(number, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
