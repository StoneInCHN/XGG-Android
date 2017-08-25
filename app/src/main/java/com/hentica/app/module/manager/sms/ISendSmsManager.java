package com.hentica.app.module.manager.sms;

import com.hentica.app.module.manager.OperatorListener;

/**
 * Created by Snow on 2017/2/15.
 */

public interface ISendSmsManager {

    /**
     * 请求发送验证码
     *
     * @param phoneNumber 电话号码
     * @param l           回调
     */
    void sendSms(String phoneNumber, OperatorListener l);

}
