package com.hentica.app.module.mine.presenter.bankcard;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public interface VerifyCardPresenter {

    /**
     * 校验银行卡四要素
     * @param name
     * @param bankCardId
     * @param idCard
     * @param mobile
     */
    void VerifyCard(String name, String bankCardId, String idCard, String mobile);

    void detachView();
}
