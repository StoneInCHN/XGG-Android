package com.hentica.app.module.common.listener;

import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.util.TipUtil;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.util.FragmentJumpUtil;

import java.lang.ref.WeakReference;

/**
 * @author mili
 * @createTime 2016/10/18
 */
public abstract class UsualDataBackListener<T> extends SimpleDataBackListener<T> {

    // 对应的界面
    private WeakReference<FragmentListener.UsualViewOperator> mFragment;
    // 是否显示网络错误
    private boolean mIsTipError = true;
    // 是否跳转到登录界面
    private boolean mIsToLogin = true;
    // 是否显示加载框
    private boolean mIsShowLoading = true;

    /**
     * 构造函数
     *
     * @param fragment 界面
     */
    public UsualDataBackListener(FragmentListener.UsualViewOperator fragment) {
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * 构造函数
     *
     * @param fragment      界面
     * @param isTipError    是否提示错误
     * @param isToLogin     是否跳转登录界面
     * @param isShowLoading 是否显示加载框
     */
    public UsualDataBackListener(FragmentListener.UsualViewOperator fragment, boolean isTipError, boolean isToLogin, boolean isShowLoading) {
        mFragment = new WeakReference<>(fragment);
        mIsTipError = isTipError;
        mIsToLogin = isToLogin;
        mIsShowLoading = isShowLoading;
    }

    /**
     * 不跳转登录
     */
    public UsualDataBackListener<T> notToLogin() {
        mIsToLogin = false;
        return this;
    }

    /**
     * 不提示错误
     */
    public UsualDataBackListener<T> notTipError() {
        mIsTipError = false;
        return this;
    }

    /**
     * 不显示加载框
     */
    public UsualDataBackListener<T> notShowLoading() {
        mIsShowLoading = false;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();

        // 显示对话框
        handleShowLoading(true);
    }

    @Override
    public void onSuccess(T data) {
        super.onSuccess(data);

        // 取消对话框
        handleShowLoading(false);
    }

    @Override
    public void onFailed(NetData result) {
        super.onFailed(result);

        // 提示网络错误
        handleErrorTip(result);

        // 跳转到登录界面
        if (mIsToLogin && result != null) {
            if (
//                    result.getErrCode() == Constants.POST_RESULT_CODE_NEED_LOGIN ||
//                    result.getErrCode() == Constants.POST_RESULT_CODE_OTHER_LOGIN ||
                    result.getErrCode() == Constants.FAIL_TOKEN_TIMEOUT ||
                    result.getErrCode() == Constants.FAIL_TOKEN_OTHER_LOGIN) {
                LoginModel.getInstance().logout(false);
                handleLogin();
            }
        }

        // 取消对话框
        handleShowLoading(false);
    }

    // 处理错误提示
    private void handleErrorTip(NetData result) {

        if (mIsTipError) {

            TipUtil.autoTipError(result);
        }
    }

    // 处理登录跳转
    private void handleLogin() {

        if (mIsToLogin) {
            FragmentListener.UsualViewOperator fragment = mFragment.get();

            if (fragment != null) {
                fragment.onToLogin();
            }
        }
    }

    // 处理加载框
    private void handleShowLoading(boolean willShow) {

        if (mIsShowLoading) {

            FragmentListener.UsualViewOperator fragment = mFragment != null ? mFragment.get() : null;
            if (fragment != null) {

                if (willShow) {
                    fragment.showLoadingDialog();
                } else {
                    fragment.dismissLoadingDialog();
                }
            }
        }
    }
}
