package com.hentica.app.module.mine.view.bank;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.ResBankCardInfo;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public interface BankCardCheckView extends FragmentListener.UsualViewOperator {

    /**
     * 银行卡校验成功
     * @param data
     */
    void checkSuccess(ResBankCardInfo data);

}
