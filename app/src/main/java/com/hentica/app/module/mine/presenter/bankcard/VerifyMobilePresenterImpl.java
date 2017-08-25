package com.hentica.app.module.mine.presenter.bankcard;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.module.manager.sms.ISendSmsManager;
import com.hentica.app.module.manager.sms.SendSmsManagerFactory;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.module.mine.view.bank.VerifyMobileView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class VerifyMobilePresenterImpl implements VerifyMobilePresenter {
    private VerifyMobileView mVerifyMobileView;

    public VerifyMobilePresenterImpl(VerifyMobileView verifyMobileView) {
        mVerifyMobileView = verifyMobileView;
    }

    @Override
    public void sendSmsCode(String mobile) {
        if(TextUtils.isEmpty(mobile)){
            mVerifyMobileView.showToast("未填写手机号");
            return;
        }
        //发送注册验证码
        ISendSmsManager manager = SendSmsManagerFactory.getInstance(SmsType.RESERVEDMOBILE, mVerifyMobileView);
        manager.sendSms(mobile, new OperatorListener() {
            @Override
            public void success() {
                mVerifyMobileView.sendSmsSuccess();
            }

            @Override
            public void failure() {

            }
        });
    }

    @Override
    public void addBankCard(String smsCode, String owerName, String idCard, String cardNum, String bankName, String cardType, boolean isDefault, String bankLogo, String reservedMobile) {
        if(TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(owerName) || TextUtils.isEmpty(cardNum) ||
                TextUtils.isEmpty(bankName) || TextUtils.isEmpty(cardType) || TextUtils.isEmpty(bankLogo) ||
                TextUtils.isEmpty(reservedMobile)){
            mVerifyMobileView.showToast("资料未填写完整！");
            return;
        }
        Request.getBankCardAddCard(ApplicationData.getInstance().getLoginUserId(),
                smsCode, owerName, idCard, cardNum, bankName,
                cardType, isDefault, bankLogo, reservedMobile,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mVerifyMobileView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess && mVerifyMobileView != null){
                                    mVerifyMobileView.addBankCardSuccess();
                                }
                            }
                        }));
    }
}
