package com.hentica.app.util;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.hentica.app.framework.activity.FrameActivity;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.login.LoginFragment;

/**
 * 登录跳转辅助工具
 *
 * @author mili
 * @createTime 2016-6-23 上午9:25:30
 */
public class LoginJumpHelper {

    /**
     * 弹出登录界面锁：false没有登录界面、true已经有登录界面
     */
    private static boolean isUserLoginLocked = false;

    /**
     * 上次跳转到登录界面
     */
    private static UsualFragment mLastToLoginFragment;

    /**
     * 退出登录界面了
     */
    public static void onLoginFinished() {

        isUserLoginLocked = false;
    }

    /**
     * 刷新登录前的界面
     */
    public static void refreshLastFragment() {

        // 重新加载fragment
        if (mLastToLoginFragment != null && mLastToLoginFragment.getActivity() != null) {

            if (mLastToLoginFragment.getActivity() instanceof FrameActivity) {

                FrameActivity lastToLoginActivity = (FrameActivity) mLastToLoginFragment
                        .getActivity();

                // 若界面没有销毁
                if (!lastToLoginActivity.isFinishing() && !lastToLoginActivity.isDestroyed()) {

                    lastToLoginActivity.reloadFragment();
                }
            }
        }
        // 刷新后释放
        mLastToLoginFragment = null;
//        LoginFragment.isLogin = false;
    }

    /**
     * 进入登录界面（Activity For Result）
     *
     * @param from
     */
    public static void toLoginApp(UsualFragment from) {

        toLoginApp(from, false);
    }

    public static void toLoginApp(FragmentActivity activity){
        toLoginApp(activity, false);
    }

    /**
     * 进入登录界面（Activity For Result）
     *
     * @param from
     * @param isFirstEnter 是否是首次进入
     */
    public static void toLoginApp(UsualFragment from, boolean isFirstEnter) {
        isUserLoginLocked = LoginFragment.isLogin;
        if (!isUserLoginLocked) {
            LoginFragment.isLogin  = true;
            isUserLoginLocked = true;
            Intent intent = new Intent();
            intent.putExtra("isFirstEnter", isFirstEnter);
            // TODO 跳转到登录界面
            from.startFrameActivityForResult(LoginFragment.class, intent,
                    Constants.ACTIVITY_REQUEST_CODE_NEED_RELOGIN);
//             记录跳转前的界面
            mLastToLoginFragment = from;
        }
    }

    /**
     * 进入登录界面（Activity For Result）
     *
     * @param activity
     * @param isFirstEnter 是否是首次进入
     */
    public static void toLoginApp(FragmentActivity activity, boolean isFirstEnter) {
        isUserLoginLocked = LoginFragment.isLogin;
        if (!isUserLoginLocked) {
            LoginFragment.isLogin  = true;
            isUserLoginLocked = true;
            Intent intent = new Intent();
            intent.putExtra("isFirstEnter", isFirstEnter);
            // TODO 跳转到登录界面
            FragmentJumpUtil.toFragmentForResult(activity, LoginFragment.class, intent,
                    Constants.ACTIVITY_REQUEST_CODE_NEED_RELOGIN);
//             记录跳转前的界面
        }
    }
}
