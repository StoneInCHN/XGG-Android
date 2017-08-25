package com.hentica.app.module.index;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.listener.CallbackAdapter;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.login.data.UserLoginData;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.request.Request;
import com.hentica.app.widget.dialog.PayPasswordInputDialog;
import com.hentica.app.widget.dialog.PayPasswordInputPresenter;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;

/**
 * Created by Snow on 2017/6/27.
 */

public class PayPwdHelper {

    private static PayPwdHelper mInstance;
    private UsualFragment mUsualFragment;
    private boolean isCheckPayPwd = false;//标记支付密码是否输入正确

    private PayPwdHelper(UsualFragment fragment) {
        if (fragment == null) {
            throw new RuntimeException("UsualFragment cannot be null!");
        }
        mUsualFragment = fragment;
    }

    public static PayPwdHelper newInstance(UsualFragment fragment) {
        if (mInstance != null) {
            mInstance = null;
        }
        return mInstance = new PayPwdHelper(fragment);
    }

    public static void destoryInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    /**
     * 检查支付密码
     *
     * @param l
     */
    public void checkPayPwd(final Callback<Void> l) {
        if (l == null) {
            return;
        }
        Callback<Void> checkListener = new CallbackAdapter<Void>() {
            @Override
            public void callback(boolean isSuccess, Void data) {
                isCheckPayPwd = isSuccess;
                l.callback(isSuccess, null);
            }
        };
        if (isCheckPayPwd) {//支付密码已验证
            l.callback(true, null);
        } else if (!isSetPayPwd()) {//未设置支付密码
            mUsualFragment.showToast("支付密码暂未设置！");
            setPayPwd(checkListener);
        } else {//验证支付密码
            showPayPwdInputDialog(checkListener);
        }
    }

    /**
     * 判断是否设置过支付密码
     */
    private boolean isSetPayPwd() {
        UserLoginData loginData = LoginModel.getInstance().getUserLogin();
        return loginData == null ? false : loginData.isIsSetPayPwd();
    }

    /**
     * 设置支付密码
     *
     * @param l 成功后的回调
     */
    private void setPayPwd(final Callback<Void> l) {
        // 跳转到密码设置界面
        FragmentJumpUtil.toUpdatePayPwdForResult(mUsualFragment);
        mUsualFragment.setResultListener(new UsualFragment.OnActivityResultListener() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent intent) {
                if (resultCode == Activity.RESULT_OK) {
                    //支付密码设置成功
                    l.callback(true, null);
                }
            }
        });
    }

    /**
     * 显示支付密码输入框
     */
    private void showPayPwdInputDialog(final Callback<Void> l) {
        final PayPasswordInputDialog dialog = new PayPasswordInputDialog();
        dialog.setPayPwdInputPresenter(new PayPasswordInputPresenter() {
            @Override
            public void setInputPassword(String payPwd) {
                // 2017/6/27 验证支付密码
                Callback<Void> checkPwdCallback = new CallbackAdapter<Void>() {
                    @Override
                    public void callback(boolean isSuccess, Void data) {
                        if (isSuccess) {
                            l.callback(isSuccess, null);
                        } else { //验证支付密码错误
                            showPayPwdErrorAlertDialog(l);
                        }
                    }
                };
                checkPayPwd(payPwd, checkPwdCallback);
                dialog.dismiss();
            }
        });
        dialog.show(mUsualFragment.getFragmentManager(), "password");
    }

    private void showPayPwdErrorAlertDialog(final Callback<Void> l){
        SelfAlertDialogHelper.showDialog(mUsualFragment.getFragmentManager(),
                "支付密码错误，请重试。",
                "重新输入", "忘记密码",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPayPwdInputDialog(l);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPayPwd(l);
                    }
                });
    }

    /**
     * 检查支付密码
     *
     * @param payPwd 加密后的支付密码
     * @param l      回调
     */
    private void checkPayPwd(String payPwd, final Callback<Void> l) {
        Request.getEndUserVerifyPayPwd(ApplicationData.getInstance().getLoginUserId(),
                payPwd, ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mUsualFragment) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (l != null) l.callback(isSuccess, data);
                            }
                        }));
    }
}
