package com.hentica.app.module.mine.view.bank;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public interface BankCardVerifyView extends FragmentListener.UsualViewOperator {

    /**
     * 校验成功
     */
    void verifySuccess();

    /**
     * 设置下一步按钮是否可用
     * @param enable
     */
    void setNextBtnEnable(boolean enable);

}
