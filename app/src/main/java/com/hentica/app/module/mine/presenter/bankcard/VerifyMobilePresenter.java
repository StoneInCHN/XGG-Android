package com.hentica.app.module.mine.presenter.bankcard;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public interface VerifyMobilePresenter {

    /**
     * 发送验证码
     * @param mobile
     */
    void sendSmsCode(String mobile);

    /**
     * 添加银行卡
     * @param smsCode
     * @param owerName
     * @param cardNum
     * @param bankName
     * @param cardType
     * @param isDefault
     * @param bankLogo
     * @param reservedMobile
     */
    void addBankCard(String smsCode, String owerName, String idCard, String cardNum, String bankName,
                     String cardType, boolean isDefault, String bankLogo, String reservedMobile);

}
