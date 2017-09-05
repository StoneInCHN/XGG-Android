package com.hentica.app.module.login.presenter;

import android.text.TextUtils;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.login.view.LoginRegistView;
import com.hentica.app.module.manager.OperatorListener;
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

public class LoginRegisPresenterImpl implements LoginRegistPresenter {

    private LoginRegistView mRegistView;

    public LoginRegisPresenterImpl(LoginRegistView registView) {
        mRegistView = registView;
    }

    @Override
    public void toSendSms() {
        if (mRegistView == null) {
            return;
        }
        String msg = "";
        String phone = mRegistView.getPhone();
        if (!TextUtils.isEmpty(msg = checkPhone(phone))) {
            mRegistView.showToast(msg);
            return;
        }
        //发送注册验证码
        ISendSmsManager manager = SendSmsManagerFactory.getInstance(SmsType.kRegistSms, mRegistView);
        manager.sendSms(phone, new OperatorListener() {
            @Override
            public void success() {
                mRegistView.onSendSmsSuccess();
            }

            @Override
            public void failure() {

            }
        });
    }

    @Override
    public void toRegist() {
        if (mRegistView == null) {
            return;
        }
        String msg = canRegist();
        if (!TextUtils.isEmpty(msg)) {
            mRegistView.showToast(msg);
            return;
        }
        // TODO: 2017/3/30 注册
        final String phone = mRegistView.getPhone();//手机号
        final String smsCode = mRegistView.getSmsCode();//验证码
        final String pwd = mRegistView.getPwd();//密码
        final String cpwd = mRegistView.getCmfPwd();//确认密码
        final String recomPhone = mRegistView.getRecommondPhone();//推荐人手机号

        ConfigDataUtl configDataUtl = ConfigDataUtl.getInstance();
        mRegistView.showLoadingDialog();
        configDataUtl.getRsaPublicKey(new Callback<String>() {
            @Override
            public void callback(boolean isSuccess, String data) {
                mRegistView.dismissLoadingDialog();
                String encryptPwd = RsaUtils.encrypt(pwd);
                String encryptCpwd = RsaUtils.encrypt(cpwd);
                Request.getEndUserReg(phone, smsCode, encryptPwd, encryptCpwd, recomPhone,
                        ListenerAdapter.createObjectListener(UserLoginData.class,
                                new UsualDataBackListener<UserLoginData>(mRegistView) {
                                    @Override
                                    protected void onComplete(boolean isSuccess, UserLoginData data) {
                                        if (isSuccess) {
                                            LoginModel.getInstance().setUserLogin(data);
                                            StorageUtil.saveUserMobile(data.getCellPhoneNum());
//                                            if (ApplicationData.getInstance().getToken() != null && !TextUtils.isEmpty(ApplicationData.getInstance().getToken()))
//                                                setJpushRegistId(data.getId() + "");
                                            mRegistView.onRegistSuccess();

                                        }
                                    }
                                }));
            }

            @Override
            public void onFailed(NetData result) {
                mRegistView.dismissLoadingDialog();
            }
        });


    }

    /**
     * 设置极光推送注册id
     */
    private void setJpushRegistId(String userId) {
        Request.setJpushRegistId(userId, new Post.FullListener() {
            @Override
            public void onResult(NetData result) {
                //保存用户账号
                mRegistView.onRegistSuccess();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long curr, long max) {

            }
        });
    }

    /**
     * 是否以注册，返回错误信息，或返回结果为""或null表示可以注册
     *
     * @return
     */
    private String canRegist() {
        String phone = mRegistView.getPhone();
        String smsCode = mRegistView.getSmsCode();
        String pwd = mRegistView.getPwd();
        String cmfPwd = mRegistView.getCmfPwd();
        String msg = "";
        if (!TextUtils.isEmpty(msg = checkPhone(phone)) ||
                !TextUtils.isEmpty(msg = checkSmsCode(smsCode)) ||
                !TextUtils.isEmpty(msg = checkPwd(pwd, cmfPwd))) {
        }
        if (!TextUtils.isEmpty(mRegistView.getRecommondPhone()) &&
                mRegistView.getRecommondPhone().equals(phone)) {
            msg = "推荐人不能是自己！";
        }
        return msg;
    }

}
