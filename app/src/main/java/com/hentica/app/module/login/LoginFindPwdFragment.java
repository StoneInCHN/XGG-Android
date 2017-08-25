package com.hentica.app.module.login;

import android.view.View;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.presenter.LoginFindPwdPresenter;
import com.hentica.app.module.login.presenter.LoginFindPwdPresenterImpl;
import com.hentica.app.module.login.view.LoginFindPwdView;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

/**
 * 密码找回界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class LoginFindPwdFragment extends BaseFragment implements LoginFindPwdView {

    private LoginFindPwdPresenter mFindPwdPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.login_find_pwd_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void initData() {
        mFindPwdPresenter = getFindPwdPresenter();
    }

    /**
     * 密码找回Presenter
     * @return
     */
    protected LoginFindPwdPresenter getFindPwdPresenter(){
        return new LoginFindPwdPresenterImpl(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        setBtnSendSmsCodeEvent();
        setBtnCompletePwdEvent();
    }

    /**
     * 发送按钮事件
     */
    private void setBtnSendSmsCodeEvent() {
        getViews(R.id.account_btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindPwdPresenter.toSendSms();
            }
        });
    }

    /** 完成按钮事件 */
    private void setBtnCompletePwdEvent(){
        getViews(R.id.find_pwd_btn_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindPwdPresenter.toModifyPwd();
            }
        });
    }

    @Override
    public String getPhone() {
        return mQuery.id(R.id.account_edt_phone).getText().toString();
    }

    @Override
    public String getSmsCode() {
        return mQuery.id(R.id.account_edt_sms).getText().toString();
    }

    @Override
    public String getPwd() {
        return mQuery.id(R.id.account_edt_pwd).getText().toString();
    }

    @Override
    public String getCmfPwd() {
        return mQuery.id(R.id.account_edt_cpwd).getText().toString();
    }

    @Override
    public void onSendSmsSuccess() {
        showToast("已向手机号发送短信，请注意查收！");
        startCountTimeDown();
    }

    /**
     * 启动倒计时器
     */
    private void startCountTimeDown() {
        //持续时间60秒，间隔1秒
        new TimeDownManager(60 * 1000, 500){

            @Override
            public void onStart() {
                super.onStart();
                setBtnSendSmsCodeEnable(false);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                int second = (int) (millisUntilFinished / 1000);
                setBtnSendSmsCodeText(second + "S");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                setBtnSendSmsCodeText("发送");
                setBtnSendSmsCodeEnable(true);
            }
        }.start();
    }

    /** 设置发送按钮文字，发送按钮显示倒计时 */
    private void setBtnSendSmsCodeText(String text){
        setViewText(text, R.id.account_btn_send);
    }

    /** 设置发送按钮是否可用 */
    private void setBtnSendSmsCodeEnable(boolean enable){
        mQuery.id(R.id.account_btn_send).enabled(enable);
    }

    @Override
    public void onFindPwdSuccess() {
        showToast("操作成功！");
        finish();
    }
}
