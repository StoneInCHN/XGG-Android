package com.hentica.app.module.login.business.process;

import com.hentica.app.module.common.listener.DataListenerWrapper;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;

/**
 * 密码登录流程
 */
public class PwdLoginProgress extends BaseLoginProgress {

    /**
     * 请求登录
     *
     * @param reqData  登录请求数据
     * @param listener 网络回调，返回登录数据
     */
//    public void requestLogin(MReqUserLoginData reqData, OnDataBackListener<UserLoginData> listener) {
//
//        // 拦截登录结果
//        listener = new DataListenerWrapper<UserLoginData>(listener) {
//
//            @Override
//            public void onSuccess(UserLoginData data) {
//
//                onLoginSuccess(data);
//                super.onSuccess(data);
//            }
//        };
//
//        Request.getUserlogin(ParseUtil.toJsonString(reqData), ListenerAdapter.createObjectListener(UserLoginData.class, listener));
//    }
}
