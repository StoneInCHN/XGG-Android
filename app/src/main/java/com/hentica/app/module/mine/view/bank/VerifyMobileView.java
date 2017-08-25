package com.hentica.app.module.mine.view.bank;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public interface VerifyMobileView extends FragmentListener.UsualViewOperator{

    /**
     * 发送验证码成功
     */
    void sendSmsSuccess();

    /**
     * 添加银行卡成功
     */
    void addBankCardSuccess();

}
