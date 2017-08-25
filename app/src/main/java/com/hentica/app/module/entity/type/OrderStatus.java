package com.hentica.app.module.entity.type;

/**
 * 订单状态
 * Created by YangChen on 2017/4/6 19:11.
 * E-mail:656762935@qq.com
 */

public class OrderStatus {
    /** 全部 */
    public static String ALL = "";
    /** 未支付 */
    public static String UNPAID = "UNPAID";
    /** 已支付，待评价 */
    public static String PAID = "PAID";
    /** 评价后，已完成 */
    public static String FINISHED = "FINISHED";
}
