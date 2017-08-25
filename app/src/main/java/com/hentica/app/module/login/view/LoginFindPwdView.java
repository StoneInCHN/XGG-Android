package com.hentica.app.module.login.view;

import com.hentica.app.framework.fragment.FragmentListener;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.17:46
 */

public interface LoginFindPwdView extends FragmentListener.UsualViewOperator{

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
     * 发送验证码成功
     */
    void onSendSmsSuccess();

    /**
     * 注册成功
     */
    void onFindPwdSuccess();

}
