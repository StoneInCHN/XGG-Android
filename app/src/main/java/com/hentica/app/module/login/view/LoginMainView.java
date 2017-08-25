package com.hentica.app.module.login.view;

import com.hentica.app.framework.fragment.FragmentListener;

public interface LoginMainView extends FragmentListener.UsualViewOperator {

    /** 登录成功*/
    void onLoginSuccess();

    /**
     * 发送成功
     */
    void onSendSmsSuccess();

    /** 获取手机号 */
    String getPhone();

    /** 获取密码 */
    String getPwd();

    /** 获取验证码 */
    String getSmsCode();

}
