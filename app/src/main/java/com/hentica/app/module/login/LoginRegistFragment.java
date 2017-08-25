package com.hentica.app.module.login;

import android.view.View;

import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.presenter.LoginRegisPresenterImpl;
import com.hentica.app.module.login.presenter.LoginRegistPresenter;
import com.hentica.app.module.login.view.LoginRegistView;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.module.mine.ui.textcontent.LicenseFragment;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class LoginRegistFragment extends BaseFragment implements LoginRegistView{

    private LoginRegistPresenter mRegistPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.login_regist_fragment;
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
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
        title.getTitleTextView().setTextColor(getResources().getColor(R.color.text_white));
    }

    @Override
    protected void initData() {
        mRegistPresenter = new LoginRegisPresenterImpl(this);
    }

    @Override
    protected void initView() {
        ViewUtil.bindEditDelete(getView(),R.id.account_edt_phone,0,R.drawable.rebate_login_delete);
        ViewUtil.bindEditDelete(getView(),R.id.account_edt_recommond_phone,0,R.drawable.rebate_login_delete);
    }

    @Override
    protected void setEvent() {
        setBtnSendSmsCodeEvent();
        setBtnRegistEvent();
        setBtnLicenseEvent();
    }

    /**
     * 发送按钮事件
     */
    private void setBtnSendSmsCodeEvent() {
        getViews(R.id.account_btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegistPresenter.toSendSms();
            }
        });
    }

    /** 设置注册按钮事件 */
    private void setBtnRegistEvent(){
        getViews(R.id.regist_btn_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegistPresenter.toRegist();
            }
        });
    }

    /** 软件协议按钮事件 */
    private void setBtnLicenseEvent(){
        getViews(R.id.regist_btn_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  2017/3/30 注册协议
                startFrameActivity(LicenseFragment.class);
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
    public String getRecommondPhone() {
        return mQuery.id(R.id.account_edt_recommond_phone).getText().toString();
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
    public void onRegistSuccess() {
        EventBus.getDefault().post(new DataEvent.OnLoginEvent(false));
        finish();
    }
}
