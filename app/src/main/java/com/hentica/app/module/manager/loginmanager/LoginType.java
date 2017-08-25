package com.hentica.app.module.manager.loginmanager;

/**
 * 登录方式
 * Created by Snow on 2017/2/15.
 */

public enum LoginType {
    /** 自动登录 */
    kAutoLogin("0"),
    /** 动态验证码登录 */
    kSmsLogin("1"),
    /** 账号密码登录 */
    kPwdLogin("2"),
    /** QQ登录 */
    kQQLogin("3"),
    /** 微信登录 */
    kWeichatLogin("4"),
    /** 微博登录 */
    kBlogLogin("5");

    String value;

    LoginType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
