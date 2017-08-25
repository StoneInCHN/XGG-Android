package com.hentica.app.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 状态栏辅助工具
 *
 * @author mili
 */
public class StatusBarUtil {

    /**
     * 设置状态栏颜色
     *
     * @param activity 界面
     * @param color    状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    /**
     * 开启状态栏沉浸模式
     *
     * @param activity 对应的界面
     * @param on       是否开启
     */
    public static void setTranslucentStatus(Activity activity, boolean on) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }

        setStatusBarColor(activity, 0);
    }

    /**
     * 设置某View与状态栏一样高，只有api>14时生效
     */
    public static void setTitleHeight(Context context, View statusBarView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            int statusBarHeight = getStatusBarHeight(context);
            if (statusBarView != null) {

                ViewGroup.LayoutParams params = statusBarView.getLayoutParams();
                params.height = statusBarHeight;
                statusBarView.setLayoutParams(params);
            }
        }
    }

    /**
     * 取得状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
