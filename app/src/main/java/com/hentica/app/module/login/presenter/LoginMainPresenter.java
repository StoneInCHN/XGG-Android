package com.hentica.app.module.login.presenter;

import com.hentica.app.module.manager.loginmanager.LoginType;

/**
 * Created by Snow on 2017/2/15.
 */

public interface LoginMainPresenter {

    /**
     * 密码登录
     */
    void toLoginByPwd();

    /**
     * 验证码登录
     */
    void toLoginBySms();


    /**
     * 发送验证码
     */
    void sendSmsCode();

    /**
     * 第三方登录
     * @param type 登录方式，{@link LoginType 类型}<br >
     *             {@link LoginType#kQQLogin QQ登录}、{@link LoginType#kWeichatLogin 微信登录}、
     *             {@link LoginType#kBlogLogin 新浪微博登录}
     * @param opedId 登录openId
     * @param nickName 第三方昵称
     */
    void toThirdLogin(LoginType type, String opedId, String uuid, String nickName);
}
