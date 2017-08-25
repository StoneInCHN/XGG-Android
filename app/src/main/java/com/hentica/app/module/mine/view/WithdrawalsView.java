package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResMineWithdrawalsInfo;

/**
 * Created by Snow on 2017/5/3.
 */

public interface WithdrawalsView extends FragmentListener.UsualViewOperator{

    /**
     * 提现信息
     * @param info
     */
    void setWithdrawalsInfo(boolean result, ResMineWithdrawalsInfo info);

    /**
     * 提现成功
     */
    void withdrawalsSuccess();

}
