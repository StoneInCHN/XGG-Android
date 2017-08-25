package com.hentica.app.module.mine.presenter;

/**
 * Created by Snow on 2017/8/14.
 */

public interface TransferPresenter {

    /**
     * 发送验证码
     * @param mobile
     */
    void sendSmsCode(String mobile);

    /**
     *
     * @param transType
     * @param smsCode
     * @param mobile
     * @param amount
     */
    void doTransfer(String transType, String smsCode, String mobile, String amount);

    /**
     * 验证手机号
     * @param mobile
     */
    void verifyMobile(String mobile);
}
