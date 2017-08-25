package com.hentica.app.module.login.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * Created by Snow on 2017/2/15.
 */

public interface LoginRegistView extends FragmentListener.UsualViewOperator{
    /**
     * 获取手机号
     * @return
     */
    String getPhone();

    /**
     * 获取验证码
     * @return
     */
    String getSmsCode();

    /**
     * 获取密码
     * @return
     */
    String getPwd();

    /**
     * 获取确认密码
     * @return
     */
    String getCmfPwd();

    /**
     * 获取推荐人手机号
     * @return
     */
    String getRecommondPhone();

    /**
     * 发送验证码成功
     */
    void onSendSmsSuccess();

    /**
     * 注册成功
     */
    void onRegistSuccess();
}
