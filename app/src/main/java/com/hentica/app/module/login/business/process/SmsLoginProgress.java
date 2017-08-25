package com.hentica.app.module.login.business.process;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.common.listener.DataListenerWrapper;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;

/**
 * 验证码登录流程
 */
public class SmsLoginProgress extends BaseLoginProgress {

    /**
     * 请求验证码
     *
     * @param reqData  手机号
     * @param listener 网络回调
     */
//    public void requestSms(MReqUserSendSmsData reqData, final OnDataBackListener<Void> listener) {
//        Request.getUsersendSms(reqData.getType(), reqData.getMobile(), new Post.FullListener() {
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onProgress(long curr, long max) {
//            }
//
//            @Override
//            public void onResult(NetData result) {
//                if(result.isSuccess()){
//                    // 请求成功
//                    if (listener != null) {
//                        listener.onSuccess(null);
//                    }
//                }
//            }
//        });
//    }

    /**
     * 请求登录，登录成功后，数据就可以直接从内存中读取了，不需要解析返回结果
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
//        Request.getUserlogin(ParseUtil.toJsonString(reqData), ListenerAdapter.createObjectListener(UserLoginData.class, listener));
//    }
}
