package com.hentica.app.module.manager.sms;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/2/15.
 */

public class SendSmsManager implements ISendSmsManager {

    private SmsType mType;

    private FragmentListener.UsualViewOperator mOperator;


    public SendSmsManager(SmsType type, FragmentListener.UsualViewOperator operator){
        this.mType = type;
        this.mOperator = operator;
    }

    @Override
    public void sendSms(String phoneNumber, final OperatorListener l) {
        // TODO: 2017/3/30 对接发送验证api
        Request.getEndUserGetSmsCode(phoneNumber, mType.getValue(),
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mOperator) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(l != null){
                                    if(isSuccess){
                                        l.success();
                                    }else{
                                        l.failure();
                                    }
                                }
                            }
                        }));
    }
}
