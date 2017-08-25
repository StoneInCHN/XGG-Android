package com.hentica.app.module.login.presenter;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.login.view.LoginFindPwdView;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.util.request.Request;
import com.hentica.app.util.rsa.RsaUtils;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.17:50
 */

public class LoginFindPwdPresenterImpl extends AbsFindPwdPresetner {

    public LoginFindPwdPresenterImpl(LoginFindPwdView view) {
        super(view);

    }

    @Override
    protected SmsType getSendSmsType() {
        return SmsType.kFindPwdSms;
    }

    @Override
    protected void modify() {
        final String phone = getFindPwdView().getPhone();//手机号
        final String smsCode = getFindPwdView().getSmsCode();//验证码
        final String pwd = getFindPwdView().getPwd();//密码
        final String cpwd = getFindPwdView().getCmfPwd();//确认密码
        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        getFindPwdView().showLoadingDialog();
        configDataUtl.getRsaPublicKey(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                String encryptPwd = RsaUtils.encrypt(pwd);
                String encryptCpwd = RsaUtils.encrypt(cpwd);
                getFindPwdView().dismissLoadingDialog();
                Request.getEndUserResetPwd(phone, encryptPwd, encryptCpwd, smsCode,
                        ListenerAdapter.createObjectListener(Void.class,
                                new UsualDataBackListener<Void>(getFindPwdView()) {
                                    @Override
                                    protected void onComplete(boolean isSuccess, Void data) {
                                        if (isSuccess) {
                                            getFindPwdView().onFindPwdSuccess();
                                        }
                                    }
                                }));
            }

            @Override
            public void onFailed(NetData result) {
                getFindPwdView().dismissLoadingDialog();
            }
        });

    }
}