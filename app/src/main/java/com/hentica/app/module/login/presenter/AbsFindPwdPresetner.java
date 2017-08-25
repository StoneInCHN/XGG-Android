package com.hentica.app.module.login.presenter;

import android.text.TextUtils;

import com.hentica.app.module.login.view.LoginFindPwdView;
import com.hentica.app.module.manager.OperatorListener;
import com.hentica.app.module.manager.sms.ISendSmsManager;
import com.hentica.app.module.manager.sms.SendSmsManagerFactory;
import com.hentica.app.module.manager.sms.SmsType;

import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPhone;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkPwd;
import static com.hentica.app.module.login.util.CheckLoginDataUtils.checkSmsCode;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/8.17:02
 */

public abstract class AbsFindPwdPresetner implements LoginFindPwdPresenter {

    private LoginFindPwdView mFindPwdView;

    public AbsFindPwdPresetner(LoginFindPwdView view){
        this.mFindPwdView = view;
    }

    protected LoginFindPwdView getFindPwdView(){
        return this.mFindPwdView;
    }

    @Override
    public void toSendSms() {
        if (mFindPwdView == null) {
            return;
        }
        String msg = "";
        String phone = mFindPwdView.getPhone();
        if (!TextUtils.isEmpty(msg = checkPhone(phone))) {
            mFindPwdView.showToast(msg);
            return;
        }
        //发送注册验证码
        ISendSmsManager manager = SendSmsManagerFactory.getInstance(getSendSmsType(), mFindPwdView);
        manager.sendSms(phone, new OperatorListener() {
            @Override
            public void success() {
                mFindPwdView.onSendSmsSuccess();
            }

            @Override
            public void failure() {

            }
        });
    }

    /**
     * 发送的验证码类型
     * @return
     */
    protected abstract SmsType getSendSmsType();

    @Override
    public void toModifyPwd() {
        if (mFindPwdView == null) {
            return;
        }
        String msg = checkData();
        if (!TextUtils.isEmpty(msg)) {
            mFindPwdView.showToast(msg);
            return;
        }
        modify();
    }

    /**
     * 修改
     */
    protected abstract void modify();

    /**
     * 检查数据是否符合要求，
     *
     * @return null或""符合要求，错误提示信息
     */
    protected String checkData() {
        String phone = mFindPwdView.getPhone();
        String smsCode = mFindPwdView.getSmsCode();
        String pwd = mFindPwdView.getPwd();
        String cmfPwd = mFindPwdView.getCmfPwd();
        String msg = "";
        if (!TextUtils.isEmpty(msg = checkPhone(phone)) ||
                !TextUtils.isEmpty(msg = checkSmsCode(smsCode)) ||
                !TextUtils.isEmpty(msg = checkPwd(pwd, cmfPwd))) {
        }
        return msg;
    }
}
