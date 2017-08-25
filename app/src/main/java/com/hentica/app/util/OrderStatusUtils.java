package com.hentica.app.util;

import android.text.TextUtils;

import com.hentica.app.module.entity.type.OrderStatus;

/**
 * Created by Snow on 2017/7/4.
 */

public class OrderStatusUtils {

    /** 获取订单状态描述 */
    public static String getStatusDes(String status){
        String des = "";
        if(TextUtils.equals(status, OrderStatus.UNPAID)){
            des = "未支付";
        }else if(TextUtils.equals(status, OrderStatus.PAID)){
            des = "待评价";
        }else if(TextUtils.equals(status, OrderStatus.FINISHED)){
            des = "已完成";
        }
        return des;
    }

    public static String getStatusDesc(String status, boolean isClearing) {
        if (OrderStatus.UNPAID.equals(status)) {
            return "未支付";
        } else if (isClearing) {
            return "已结算";
        } else if (OrderStatus.PAID.equals(status)) {
            return "已支付";
        } else if (OrderStatus.FINISHED.equals(status)) {
            return "已完成";
        } else {
            return "结算中";
        }
    }
}
