package com.hentica.app.module.mine.presenter.bank;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public interface BankCardPresenter extends IdCardPresetner{

    /**
     * 删除银行卡
     * @param cardId
     */
    void deleteCard(String cardId);

    /**
     * 设置默认银行卡
     * @param cardId
     */
    void setDefault(String cardId);
}
