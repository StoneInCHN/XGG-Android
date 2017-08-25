package com.hentica.app.module.login.util;

import android.text.TextUtils;

/**
 * Created by Snow on 2017/2/15.
 */

public class CheckLoginDataUtils {

    /**
     * 检查电话号码
     *
     * @param phone
     * @return 错误信息，若为""或null，则表示符合
     */
    public static String checkPhone(String phone) {
        String msg = "";
        if (TextUtils.isEmpty(phone)) {
            msg = "手机号未输入！";
        } else if (phone.length() != 11) {
            msg = "手机号错误！";
        }
        return msg;
    }

    /**
     * 检查密码
     *
     * @param pwd    密码
     * @param cmfPwd 确认密码
     * @return
     */
    public static String checkPwd(String pwd, String cmfPwd) {
        if (TextUtils.isEmpty(pwd)) {
            return "密码未输入！";
        }
        if (pwd.length() < 6) {
            return "密码不足6位！";
        }
        if (TextUtils.isEmpty(cmfPwd)) {
            return "确认密码未输入！";
        }
        if (cmfPwd.length() < 6) {
            return "确认密码不足6位！";
        }
        if(!pwd.equals(cmfPwd)){
            return "密码和确认密码不一致，请重新输入！";
        }
        return null;
    }

    /**
     * 检查密码
     * @param pwd 密码
     * @return
     */
    public static String checkPwd(String pwd){
        String msg = "";
        if(TextUtils.isEmpty(pwd)) {
            msg = "密码未输入！";
        }else if(pwd.length() < 6){
            msg = "密码不足6位！";
        }
        return msg;
    }

    /**
     * 检查验证码
     *
     * @param sms
     * @return
     */
    public static String checkSmsCode(String sms) {
        String msg = "";
        if (TextUtils.isEmpty(sms)) {
            msg = "验证码未输入！";
        }
        return msg;
    }

}
