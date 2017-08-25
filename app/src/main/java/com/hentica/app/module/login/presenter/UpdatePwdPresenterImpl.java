package com.hentica.app.module.login.presenter;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.config.ConfigDataUtl;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.module.login.view.LoginFindPwdView;
import com.hentica.app.module.manager.sms.SmsType;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.StringUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.util.rsa.RsaUtils;

import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPhone;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPwd;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkSmsCode;

/**
 * 修改密码
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.17:50
 */

public class UpdatePwdPresenterImpl extends AbsFindPwdPresetner {
    private boolean payPwd;

    /**
     * @param view
     * @param payPwd 是否修改支付密码
     */
    public UpdatePwdPresenterImpl(LoginFindPwdView view, boolean payPwd) {
        super(view);
        this.payPwd = payPwd;
    }

    @Override
    protected SmsType getSendSmsType() {
        return payPwd ? SmsType.kUpdatePayPwd : SmsType.kUpdateLoginPwd;
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
                getFindPwdView().dismissLoadingDialog();
                String encryptPwd = RsaUtils.encrypt(pwd);
                String encryptCpwd = RsaUtils.encrypt(cpwd);
                Request.getEndUserUpdatePwd(ApplicationData.getInstance().getLoginUserId(),
                        phone, encryptPwd, encryptCpwd, smsCode,
                        payPwd ? "UPDATEPAYPWD" : "UPDATELOGINPWD",
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

    protected String checkData() {
        String phone = getFindPwdView().getPhone();
        String smsCode = getFindPwdView().getSmsCode();
        String pwd = getFindPwdView().getPwd();
        String cmfPwd = getFindPwdView().getCmfPwd();
        String msg = "";
        if (!TextUtils.isEmpty(msg = checkPhone(phone)) ||
                !TextUtils.isEmpty(msg = checkSmsCode(smsCode)) ||
                !TextUtils.isEmpty(msg = checkPwd(pwd, cmfPwd))) {
        }
        if(TextUtils.isEmpty(msg) && payPwd){
            if(StringUtil.isSame(pwd) || StringUtil.isSeriesAsc(pwd)
                    || StringUtil.isSeriesDesc(pwd)){
                msg = "密码不能是重复、连续的数字，请重新设置！";
            }
        }
        return msg;
    }
}
