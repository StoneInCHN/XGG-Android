package com.hentica.app.module.login;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.hentica.app.framework.activity.BaseCompatActivity;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.login.presenter.LoginMainPresenter;
import com.hentica.app.module.login.presenter.LoginMainPresenterImpl;
import com.hentica.app.module.login.view.LoginMainView;
import com.hentica.app.module.manager.timedown.TimeDownManager;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.ViewUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.view.TitleView;
import com.fiveixlg.app.customer.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 登录界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class LoginActivity extends BaseCompatActivity implements LoginMainView{

    public static boolean isLogin = false;

    /**
     * 发送短信是否正在倒计时
     */
    private boolean mIsOnCountDown;

    private LoginMainPresenter mLoginMainPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.login_fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        mLoginMainPresenter = new LoginMainPresenterImpl(this);
    }

    @Override
    protected void initView() {
        super.initView();
        TitleView title = getViews(R.id.common_title);
        title.getTitleTextView().setTextColor(getResources().getColor(R.color.text_white));
        title.setOnLeftImageBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setViewText(StorageUtil.getUserMobile(), R.id.login_edt_phone);
    }

    @Override
    protected void setEvent() {
        setBtnLoginEvent();
        setBtnLoginTypeEvent();
        setBtnFindPwdEvent();
        setBtnRegistEvent();
        setBtnSendSmsCodeEvent();
        ViewUtil.bindEditDelete(this,R.id.login_edt_phone,R.drawable.rebate_login_icon_number, R.drawable.rebate_login_delete2);
    }

    /** 设置登录按钮事件 */
    private void setBtnLoginEvent(){
        getViews(R.id.login_btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuery.id(R.id.login_togbtn_login_type).isChecked()){
                    //密码登录
                    mLoginMainPresenter.toLoginByPwd();
                }else{
                    //验证码登录
                    mLoginMainPresenter.toLoginBySms();
                }
            }
        });
    }

    /**
     * 设置登录方式按钮事件
     */
    private void setBtnLoginTypeEvent() {
        ToggleButton toggleButton = getViews(R.id.login_togbtn_login_type);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchLoginType(isChecked);
            }
        });
    }

    /**
     * 切换登录方式
     *
     * @param isPwdLogin true，密码登录，false：验证码登录
     */
    private void switchLoginType(boolean isPwdLogin) {
        //密码登录——隐藏发送按钮，隐藏验证码输入框及图片，显示密码输入框及图片
        //验证码登录——显示发送按钮，显示验证码输入框及图片，隐藏密码输入框及图片
        getViews(R.id.login_btn_send_sms).setVisibility(isPwdLogin ? View.GONE : View.VISIBLE);
        getViews(R.id.login_layout_sms).setVisibility(isPwdLogin ? View.GONE : View.VISIBLE);
        getViews(R.id.login_layout_pwd).setVisibility(isPwdLogin ? View.VISIBLE : View.GONE);
    }

    /**
     * 密码找回按钮事件
     */
    private void setBtnFindPwdEvent() {
        getViews(R.id.login_btn_find_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2017/3/29 跳转密码找回界面
                FragmentJumpUtil.toFragment(getActivity(), LoginFindPwdFragment.class);
            }
        });
    }

    /**
     * 注册按钮事件
     */
    private void setBtnRegistEvent() {
        getViews(R.id.login_btn_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转注册界面
                FragmentJumpUtil.toFragment(getActivity(), LoginRegistFragment.class);
            }
        });
    }

    /**
     * 发送按钮事件
     */
    private void setBtnSendSmsCodeEvent() {
        getViews(R.id.login_btn_send_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginMainPresenter.sendSmsCode();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        EventBus.getDefault().post(new DataEvent.OnLoginEvent(false));
        finish();
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
                mIsOnCountDown = true;
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
                mIsOnCountDown = false;
                setBtnSendSmsCodeText("发送");
                setBtnSendSmsCodeEnable(true);
            }
        }.start();
    }

    /** 设置发送按钮文字，发送按钮显示倒计时 */
    private void setBtnSendSmsCodeText(String text){
        setViewText(text, R.id.login_btn_send_sms);
    }

    /** 设置发送按钮是否可用 */
    private void setBtnSendSmsCodeEnable(boolean enable){
        mQuery.id(R.id.login_btn_send_sms).enabled(enable);
    }

    @Override
    public String getPhone() {
        return mQuery.id(R.id.login_edt_phone).getText().toString();
    }

    @Override
    public String getPwd() {
        return mQuery.id(R.id.login_edt_pwd).getText().toString();
    }

    @Override
    public String getSmsCode() {
        return mQuery.id(R.id.login_edt_sms).getText().toString();
    }

    @Override
    public void finish() {
        isLogin = false;
        super.finish();
    }

    @Override
    public void onDestroy() {
        isLogin = false;
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(DataEvent.OnLoginEvent event){
        finish();
    }
}
