package com.hentica.app.module.login.business.process;

import android.text.TextUtils;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.DataListenerWrapper;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.request.Request;

/**
 * 自动登录流程
 *
 * @author mili
 * @createTime 2016/10/13
 */
public class AutoLoginProgress extends BaseLoginProgress {

    /**
     * 请求自动登录
     *
     * @param listener 网络回调，返回登录数据
     */
//    public void requestAutoLogin(OnDataBackListener<UserLoginData> listener) {
//
//        // 验证是否满足自动登录条件
//        UserLoginData loginData = StorageUtil.getLastLoginInfo();
//        boolean canAutoLogin = canAutoLogin(loginData);
//        if (!canAutoLogin) {
//            NetData netData = new NetData();
//            netData.setErrCode(-1);
//            netData.setErrMsg("不能自动登录");
//            listener.onFailed(netData);
//            return;
//        }
//
//        // 若可以自动登录
//        // 拦截登录结果
//        listener = new DataListenerWrapper<UserLoginData>(listener) {
//            @Override
//            public void onSuccess(UserLoginData data) {
//
//                onLoginSuccess(data);
//                super.onSuccess(data);
//            }
//        };
//
//        // 请求网络
//        MReqUserLoginData reqUserLoginData = new MReqUserLoginData();
//        reqUserLoginData.setType("0");
//        reqUserLoginData.setUserId(loginData.getUserId());
//        reqUserLoginData.setLoginPwd(loginData.getLoginPwd());
//        reqUserLoginData.setModel(getModel());
//        reqUserLoginData.setMac(getMac());
//        Request.getUserlogin(ParseUtil.toJsonString(reqUserLoginData), ListenerAdapter.createObjectListener(UserLoginData.class, listener));
//    }

    // 判断是否满足自动登录条件
//    private boolean canAutoLogin(UserLoginData lastLoginData) {
//        // 验证是否满足自动登录条件
//        boolean canAutoLogin = false;
//        if (lastLoginData != null) {
//
//            // 账号、密码不为空就可以登录
//            if (!TextUtils.isEmpty(lastLoginData.getUserIdStr()) && !TextUtils.isEmpty(lastLoginData.getLoginPwd())) {
//
//                canAutoLogin = true;
//            }
//        }
//        return canAutoLogin;
//    }
}
