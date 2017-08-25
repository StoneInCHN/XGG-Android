package com.hentica.app.module.login.business.process;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.common.listener.DataListenerWrapper;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.OnDataBackListener;
import com.hentica.app.module.common.listener.SimpleDataBackListener;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.ParseUtil;
import com.hentica.app.util.request.Request;

/**
 * 第三方登录流程 <br />
 * 流程如下: <br />
 * <p>
 * 点击QQ -> view层跳转到QQ登录 -> QQ登录成功 -> 请求第三方登录 <br />
 * 情况①: -> 需要绑定手机 -> 跳转到绑定界面 -> 绑定成功，即登录成功 <br />
 * 情况②: -> 登录成功 <br />
 */
public class ThirdpartLoginProgress extends BaseLoginProgress {

    /**
     * 第三方登录接口
     */
    public interface ThirdpartLoginListener {

        /**
         * 登录成功
         *
         * @param loginData 用户数据
         */
        void onSuccess(UserLoginData loginData);

        /**
         * 需要绑定手机
         */
        void onNeedBindPhone();

        /**
         * 登录失败
         */
        void onError(NetData result);
    }

    /**
     * 第三方登录类型
     */
    public enum ThirdpartLoginType {

        // QQ
        kQQ,
        // 微信
        kWechat,
        // 微博
        kWeibo,
    }

    /**
     * 请求第三方登录
     *
     * @param type     登录类型
     * @param tokenId  第三方登录的tokenId
     * @param nickName 第三方登录的昵称
     * @param listener 网络回调
     */
//    public void requestThirdLogin(ThirdpartLoginType type, String tokenId, String nickName, final ThirdpartLoginListener listener) {
//
//        // 拦截登录结果
//        OnDataBackListener<UserLoginData> listenerWrapper = new SimpleDataBackListener<UserLoginData>() {
//            @Override
//            protected void onComplete(boolean isSuccess, UserLoginData data) {
//
//                if (listener != null) {
//
//                    // TODO 有可能需要绑定手机
////                    wrapListener.onNeedBindPhone();
//                    listener.onSuccess(data);
//                }
//            }
//        };
//
//        // 请求网络
//        MReqUserLoginData reqUserLoginData = new MReqUserLoginData();
//        switch (type) {
//            case kQQ:
//                reqUserLoginData.setType("3");
//                reqUserLoginData.setQqAccount(tokenId);
//                break;
//            case kWechat:
//                reqUserLoginData.setType("4");
//                reqUserLoginData.setWechatAccount(tokenId);
//                break;
//            case kWeibo:
//                reqUserLoginData.setType("5");
//                reqUserLoginData.setBlogAccount(tokenId);
//                break;
//        }
//        reqUserLoginData.setNickname(nickName);
//        reqUserLoginData.setModel(getModel());
//        reqUserLoginData.setMac(getMac());
//        Request.getUserlogin(ParseUtil.toJsonString(reqUserLoginData), ListenerAdapter.createObjectListener(UserLoginData.class, listenerWrapper));
//    }

    /**
     * 请求验证码
     *
     * @param reqData    请求数据结构
     * @param listener 网络回调，返回登录数据
     */
//    public void requestSms(MReqUserSendSmsData reqData,final OnDataBackListener<String> listener) {
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
     * 请求绑定手机
     *
     * @param reqData        请求数据结构
     * @param listener       网络回调，返回登录数据
     */
//    public void requestBindPhone(MReqUserLoginData reqData, OnDataBackListener<UserLoginData> listener) {
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
