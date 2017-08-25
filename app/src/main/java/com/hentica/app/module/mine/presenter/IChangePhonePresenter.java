package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.mine.view.IChangePhoneView;

/**
 * Created by Snow on 2017/7/10.
 */

public interface IChangePhonePresenter {

    void attachView(IChangePhoneView view);

    void detachView();

    /**
     * 验证码
     * @param phone
     */
    void sendSmsCode(String phone);

    /**
     * 更换手机号
     * @param phone
     */
    void changePhone(String phone, String smsCode);

}
