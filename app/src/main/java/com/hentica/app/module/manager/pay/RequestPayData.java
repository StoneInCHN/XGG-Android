package com.hentica.app.module.manager.pay;

/**
 * 支付数据
 * Created by Snow on 2017/1/16.
 */

public class RequestPayData {
    /** 支付方式 */
    public enum PayType{
        /** 支付宝 */
        kAlipy("1"),
        /** 微信 */
        kWeiChat("2"),
        /** 翼支付 */
        kWingPay("3");

        String value;

        PayType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}