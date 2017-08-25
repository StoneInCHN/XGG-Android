package com.hentica.app.module.login.business.process;

import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.ParseUtil;

/**
 * 找回密码流程
 */
public class FindPwdProgress extends BaseLoginProgress {

    /**
     * 请求验证码
     *
     * @param phone    手机号
     * @param listener 网络回调
     */
    public void requestSms(String phone, OnDataBackListener<Void> listener) {

        // TODO
        if (listener != null){
            listener.onSuccess(null);
        }
    }

    /**
     * 请求找回密码
     *
     * @param phone    手机号
     * @param smsCode  短信验证码
     * @param pwd      密码
     * @param listener 网络回调，返回登录数据
     */
    public void requestFindPwd(String phone, String smsCode, String pwd, OnDataBackListener<UserLoginData> listener) {

        // TODO
        if (listener != null){
            String userJson = "{\"userId\":1,\"nickname\":\"235****3401\",\"headImage\":\"\",\"loginPwd\":\"5e9qp8\",\"session\":\"xxxxx\",\"signKey\":\"xxxxxxxx\"}";
            listener.onSuccess(ParseUtil.parseObject(userJson, UserLoginData.class));
        }
    }
}
