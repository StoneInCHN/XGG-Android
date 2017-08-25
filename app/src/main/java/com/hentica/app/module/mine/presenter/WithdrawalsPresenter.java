package com.hentica.app.module.mine.presenter;

/**
 * Created by Snow on 2017/5/3.
 */

public interface WithdrawalsPresenter {

    /**
     * 获取提现信息
     */
    void getWithdrawalsInfo();

    /**
     * 确认提现
     * @param payPwd
     */
    void widthDrawals(String entityId, String payPwd, String remark);

}
