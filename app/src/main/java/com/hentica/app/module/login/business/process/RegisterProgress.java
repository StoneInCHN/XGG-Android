package com.hentica.app.module.login.business.process;

/**
 * 注册流程
 */
public class RegisterProgress extends BaseLoginProgress {

    /**
     * 请求验证码
     *
     * @param reqData   发送验证码请求数据
     * @param listener  网络回调，返回验证码
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
     * 请求注册账号
     *
     * @param reqData 注册账号请求数据
     * @param listener       网络回调，返回登录数据
     */
//    public void requestRegister(MReqUserRegisterData reqData, final OnDataBackListener<UserLoginData> listener, FragmentListener.UsualViewOperator fragment) {
//        // TODO
//        Request.getUserregister(reqData.getMobile(),reqData.getLoginPwd(),reqData.getSmsCode(),reqData.getInviteCode(),reqData.getModel(),reqData.getMac(), ListenerAdapter.createObjectListener(UserLoginData.class, new UsualDataBackListener<UserLoginData>(fragment) {
//            @Override
//            protected void onComplete(boolean isSuccess, UserLoginData data) {
//                if (listener != null) {
//                    listener.onSuccess(data);
//                }
//            }
//        }));
//
//    }
}
