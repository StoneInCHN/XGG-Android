package com.hentica.app.module.manager.sms;

/**
 * 短信类型
 * Created by Snow on 2017/2/15.
 */

public enum SmsType {

    /** 登录短信 */
    kLoginSms("LOGIN"),
//    /** 绑定手机号短信 */
//    kBindSms("2"),
    /** 找回密码短信 */
    kFindPwdSms("RESETPWD"),
    /** 注册 */
    kRegistSms("REG"),
    /** 修改登录密码 */
    kUpdateLoginPwd("UPDATELOGINPWD"),
    /** 修改支付密码 */
    kUpdatePayPwd("UPDATEPAYPWD"),
    /** 银行预留手机号验证 */
    RESERVEDMOBILE("RESERVEDMOBILE"),
    /** 转账 */
    TRANSFER("TRANSFER");

    String value;
    SmsType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
