package com.hentica.app.module.mine.presenter;

/**
 * Created by Snow on 2017/7/8.
 */

public interface WithdrawalsPresenter2 extends WithdrawalsPresenter {

    /**
     * 确认提现
     * @param withdrawalsAmount 提现金额
     */
    void widthDrawals(String entityId, double withdrawalsAmount, String remark);

}
