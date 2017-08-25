package com.hentica.app.module.mine.view;

import android.app.Fragment;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/8/14.
 */

public interface TransferView extends FragmentListener.UsualViewOperator{

    /**
     * 发送验证码
     */
    void sendSmsResult(boolean success);

    /**
     * 转账
     * @param success
     */
    void doTransferResult(boolean success);

    /**
     * 验证手机号结果
     * @param isSuccess
     * @param msg
     */
    void verifyMobileResult(boolean isSuccess, String msg);
}
