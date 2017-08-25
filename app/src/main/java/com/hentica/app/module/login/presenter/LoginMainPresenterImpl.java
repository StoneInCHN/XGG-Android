package com.hentica.app.module.login.presenter;

import android.text.TextUtils;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.storage.Storage;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.login.view.LoginMainView;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.module.manager.loginmanager.LoginType;
import com.hentica.app.module.manager.sms.ISendSmsManager;
import com.hentica.app.module.manager.sms.SendSmsManagerFactory;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.util.rsa.RsaUtils;

import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPhone;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPwd;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkSmsCode;

/**
 * Created by Snow on 2017/2/15.
 */

public class LoginMainPresenterImpl implements LoginMainPresenter {

    private LoginMainView mLoginMainView;

    public LoginMainPresenterImpl(LoginMainView loginMainView) {
        mLoginMainView = loginMainView;
    }

    @Override
    public void toLoginByPwd() {
        if (mLoginMainView == null) {
            return;
        }
        String msg = "";
        final String phone = mLoginMainView.getPhone();
        final String pwd = mLoginMainView.getPwd();
        //检查电话号码与密码
        if (!TextUtils.isEmpty(msg = checkPhone(phone)) || !TextUtils.isEmpty(msg = checkPwd(pwd))) {
            mLoginMainView.showToast(msg);
            return;
        }
        //获取登录
        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        mLoginMainView.showLoadingDialog();
        configDataUtl.getRsaPublicKey(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                mLoginMainView.dismissLoadingDialog();
                String encryptPwd = RsaUtils.encrypt(pwd);//对密码加密
                toLogin(phone, encryptPwd, null);
            }

            @Override
            public void onFailed(NetData result) {
                mLoginMainView.dismissLoadingDialog();
            }
        });

    }

    private void toLogin(String phone, String pwd, String smsCode) {
        Request.getEndUserLogin(phone, pwd, smsCode,
                ListenerAdapter.createObjectListener(UserLoginData.class,
                        new UsualDataBackListener<UserLoginData>(mLoginMainView) {
                            @Override
                            protected void onComplete(boolean isSuccess, UserLoginData data) {
                                if (isSuccess) {
                                    LoginModel.getInstance().setUserLogin(data);
                                    StorageUtil.saveUserMobile(data.getCellPhoneNum());
                                    //保存用户账号
                                    mLoginMainView.onLoginSuccess();
                                }
                            }
                        }));
    }

    @Override
    public void toLoginBySms() {
        if (mLoginMainView == null) {
            return;
        }
        String msg = "";
        String phone = mLoginMainView.getPhone();
        String sms = mLoginMainView.getSmsCode();
        //检查电话号码与密码
        if (!TextUtils.isEmpty(msg = checkPhone(phone)) || !TextUtils.isEmpty(msg = checkSmsCode(sms))) {
            mLoginMainView.showToast(msg);
            return;
        }
        toLogin(phone, null, sms);
    }

    @Override
    public void sendSmsCode() {
        if (mLoginMainView == null) {
            return;
        }
        String msg = "";
        String phone = mLoginMainView.getPhone();
        //检查电话号码与密码
        if (!TextUtils.isEmpty(msg = checkPhone(phone))) {
            mLoginMainView.showToast(msg);
            return;
        }
        ISendSmsManager manager = SendSmsManagerFactory.getInstance(SmsType.kLoginSms, mLoginMainView);
        //发送登录验证码
        manager.sendSms(phone, new OperatorListener() {
            @Override
            public void success() {
                mLoginMainView.onSendSmsSuccess();
            }

            @Override
            public void failure() {

            }
        });
    }

    @Override
    public void toThirdLogin(final LoginType type, final String opedId, String uuid, final String nickName) {
        //获取登录
        //无第3方登录
//        final MReqMemberUserLoginData loginData = new MReqMemberUserLoginData();
//        loginData.setType(type.getValue());
//        loginData.setQqAccount(uuid);//qq账号
//        loginData.setWechatAccount(uuid);
//        loginData.setBlogAccount(uuid);
//        loginData.setOpenId(opedId);
//        loginData.setNickname(nickName);//昵称
//
//        new AskLoginRequest(loginData).requestLogin(new CallbackAdapter<MResMemberUserLoginData>() {
//            @Override
//            public void callback(boolean isSuccess, MResMemberUserLoginData data) {
//                if(isSuccess) {
//                    UserLoginData loginData = new UserLoginData(data);
//                    LoginModel.getInstance().setUserLogin(loginData);
//                    LoginModel.getInstance().saveUserLoginInfo(loginData);
//                    mLoginMainView.onLoginSuccess();
//                }
//                mLoginMainView.dismissLoadingDialog();
//            }
//
//            @Override
//            public void onFailed(NetData result) {
//                super.onFailed(result);
//                mLoginMainView.showToast(result.getErrMsg());
//                if(result.getErrCode() == POST_RESULT_CODE_NEED_BIND_PHONE){
//                    //跳转绑定界面
//                    FragmentJumpUtil.toBindFragment(mLoginMainView.getUsualFragment(), loginData);
//                }
//                mLoginMainView.dismissLoadingDialog();
//            }
//        });
    }

}
