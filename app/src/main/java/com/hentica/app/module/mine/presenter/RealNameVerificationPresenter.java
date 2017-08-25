package com.hentica.app.module.mine.presenter;

/**
 * Created by Snow on 2017/5/23.
 */

public interface RealNameVerificationPresenter {

    /**
     * 实名认证
     * @param name
     * @param number
     * @param cardFrontPic
     * @param cardBackPick
     */
    void verification(String name, String number, String cardFrontPic, String cardBackPick);

}
