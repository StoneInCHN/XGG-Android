package com.hentica.app.module.manager.sms;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 用于获取发送短信的Manager
 * Created by Snow on 2017/2/15.
 */

public class SendSmsManagerFactory {

    public static ISendSmsManager getInstance(SmsType type, FragmentListener.UsualViewOperator operator){
        return new SendSmsManager(type, operator);
    }

}
