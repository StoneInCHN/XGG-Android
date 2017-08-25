package com.hentica.app.module.mine.presenter.impl;

import android.text.TextUtils;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.util.CheckLoginDataUtils;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.module.manager.sms.ISendSmsManager;
import com.hentica.app.module.manager.sms.SendSmsManager;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.module.mine.presenter.IChangePhonePresenter;
import com.hentica.app.module.mine.view.IChangePhoneView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/7/10.
 */

public class ChangePhonePresenterImpl implements IChangePhonePresenter {
    private IChangePhoneView mView;

    @Override
    public void attachView(IChangePhoneView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void sendSmsCode(String phone) {
        String msg = CheckLoginDataUtils.checkPhone(phone);
        if (!TextUtils.isEmpty(msg)) {
            if (mView != null) mView.showToast(msg);
            return;
        }
        ISendSmsManager manager = new SendSmsManager(SmsType.kRegistSms, mView);
        manager.sendSms(phone, new OperatorListener() {
            @Override
            public void success() {
                if (mView != null) mView.sendSmsCodeResult(true);
            }

            @Override
            public void failure() {
                if (mView != null) mView.sendSmsCodeResult(false);
            }
        });
    }

    @Override
    public void changePhone(String phone, String smsCode) {
        String msg = checkData(phone, smsCode);
        if (!TextUtils.isEmpty(msg)) {
            if (mView != null) mView.showToast(msg);
            return;
        }
        Request.getEndUserChangeRegMobile(LoginModel.getInstance().getLoginUserId(),
                smsCode, phone,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (mView != null) mView.changePhoneResult(isSuccess);
                            }
                        }));
    }

    private String checkData(String phone, String smsCode) {
        String msg = "";
        if (!TextUtils.isEmpty(msg = CheckLoginDataUtils.checkPhone(phone)) ||
                !TextUtils.isEmpty(msg = CheckLoginDataUtils.checkSmsCode(smsCode))) {

        }
        return msg;
    }
}
