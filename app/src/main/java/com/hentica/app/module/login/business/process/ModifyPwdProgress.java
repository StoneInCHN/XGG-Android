package com.hentica.app.module.login.business.process;

import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;

/**
 * 修改密码流程
 *
 * @author mili
 * @createTime 2016/9/8
 */
public class ModifyPwdProgress extends FindPwdProgress {

    /**
     * 请求修改密码
     *
     * @param phone    手机号
     * @param smsCode  短信验证码
     * @param pwd      密码
     * @param listener 网络回调，返回登录数据
     */
    public void requestModifyPwd(String phone, String smsCode, String pwd, OnDataBackListener<UserLoginData> listener) {

        // 与请求找回一密码一样
        requestFindPwd(phone, smsCode, pwd, listener);
    }
}
