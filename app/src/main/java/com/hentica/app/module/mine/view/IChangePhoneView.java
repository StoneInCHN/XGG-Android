package com.hentica.app.module.mine.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/7/10.
 */

public interface IChangePhoneView extends FragmentListener.UsualViewOperator {

    void sendSmsCodeResult(boolean isSuccess);

    void changePhoneResult(boolean isSuccess);

}
