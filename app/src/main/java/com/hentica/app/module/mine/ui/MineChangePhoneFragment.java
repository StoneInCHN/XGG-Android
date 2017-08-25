package com.hentica.app.module.mine.ui;

import android.view.View;
import android.widget.EditText;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.module.mine.presenter.IChangePhonePresenter;
import com.hentica.app.module.mine.presenter.impl.ChangePhonePresenterImpl;
import com.hentica.app.module.mine.view.IChangePhoneView;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Snow on 2017/7/10.
 */

public class MineChangePhoneFragment extends BaseFragment implements IChangePhoneView{

    private IChangePhonePresenter mChangePhonePresenter;

    @BindView(R.id.account_edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.account_edt_sms)
    EditText mEdtSmsCode;

    @Override
    public int getLayoutId() {
        return R.layout.mine_change_phone_fragment;
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
        mChangePhonePresenter = new ChangePhonePresenterImpl();
        mChangePhonePresenter.attachView(this);
    }

    @Override
    protected void initView() {
        setViewVisiable(false, R.id.layout_confirm_pwd);
        setViewVisiable(false, R.id.layout_pwd);
        setViewVisiable(false, R.id.divider_pwd);
        setViewVisiable(false, R.id.divider_confirm_pwd);
        String phone = StorageUtil.getUserMobile();
        setViewText(String.format("当前手机号:%s", phone), R.id.tv_current_phone);
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void sendSmsCodeResult(boolean isSuccess) {
        if (isSuccess) {
            showToast("已向手机号发送短信，请注意查收！");
            startCountTimeDown();
        }
    }

    /**
     * 启动倒计时器
     */
    private void startCountTimeDown() {
        int duration = 60;//持续时间60秒
        float step = 0.5f;//间隔1秒
        new TimeDownManager(duration * 1000, (long) (step * 1000)){

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
    public void changePhoneResult(boolean isSuccess) {
        if (isSuccess) {
            showToast("操作成功！");
            StorageUtil.saveUserMobile(getPhone().trim());
            EventBus.getDefault().post(new DataEvent.OnToUpdataUserProfileEvent());
            finish();
        }
    }

    @OnClick(R.id.account_btn_send)
    public void sendSmsCode(View v) {
        mChangePhonePresenter.sendSmsCode(getPhone().trim());
    }

    @OnClick(R.id.find_pwd_btn_complete)
    public void changePhone(View v) {
        mChangePhonePresenter.changePhone(getPhone().trim(), getSmsCode().trim());
    }

    /**
     * 获取手机号
     * @return
     */
    private String getPhone(){
        return mEdtPhone.getText().toString();
    }

    /**
     * 获取验证码
     * @return
     */
    private String getSmsCode(){
        return mEdtSmsCode.getText().toString();
    }

    @Override
    public void finish() {
        mChangePhonePresenter.detachView();
        super.finish();
    }
}
