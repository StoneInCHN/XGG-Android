package com.hentica.app.util;

/**
 * 订单界面的辅助类，用于帮助判定订单列表与订单详情界面是否存在
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/10/27 14:41
 */

public class OrderUiHelper {

    /** 订单列表界面 */
    private static boolean isOrderListExist;

    /** 订单详情界面 */
    private static boolean isOrderDetailExist;

    /**
     * 订单详情界面是否存在
     * @return
     *      true:存在，false:不存在
     */
    public static boolean isOrderDetailExist() {
        return isOrderDetailExist;
    }

    /**
     * 设置订单详情界面是否存在
     * @param isExist
     *      true:存在，false:不存在
     */
    public static void setIsOrderDetailExist(boolean isExist) {
        isOrderDetailExist = isExist;
    }
    /**
     * 订单列表界面是否存在
     * @return
     *      true:存在，false:不存在
     */
    public static boolean isOrderListExist() {
        return isOrderListExist;
    }
    /**
     * 设置订单列表界面是否存在
     * @param isExist
     *      true:存在，false:不存在
     */
    public static void setIsOrderListExist(boolean isExist) {
        isOrderListExist = isExist;
    }
}
