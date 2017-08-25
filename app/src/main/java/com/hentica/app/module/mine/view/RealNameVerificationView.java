package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.ResMineRealNameVerification;

/**
 * Created by Snow on 2017/5/23.
 */

public interface RealNameVerificationView extends FragmentListener.UsualViewOperator{

    /**
     * 认证成功
     */
    void verificationSuccess(String name);

}
