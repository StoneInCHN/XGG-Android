package com.hentica.app.test.base;

import android.os.AsyncTask;

import com.hentica.app.test.shadow.HttpUtilShadow;
import com.hentica.app.test.shadow.LaunchUtilShadow;
import com.hentica.app.test.shadow.NetHelperShadow;
import com.hentica.app.test.shadow.PhoneInfoShadow;
import com.hentica.app.test.shadow.SmartExecutorShadow;
import com.fiveixlg.app.customer.BuildConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * Created by kezhong.
 * E-Mail:396926020@qq.com
 * on 2016/9/29 17:56
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23,
        shadows = {LaunchUtilShadow.class
                , HttpUtilShadow.class
                , SmartExecutorShadow.class
                , NetHelperShadow.class
                , PhoneInfoShadow.class
        })
@Ignore
public class BaseTest {

    @Before
    public void setUp() {
        setShowLog(true);
        setSync();
    }

    public void tearDown() {
    }

    public void setShowLog(boolean showLog) {
        ShadowLog.stream = showLog ? System.out : null;
    }

    // 把异步任务设置为同步调用
    public static void setSync() {

        try {
            Method method = AsyncTask.class.getDeclaredMethod("setDefaultExecutor", Executor.class);
            method.invoke(null, new Executor() {
                @Override
                public void execute(Runnable command) {

                    // 立即执行
                    if (command != null) {
                        command.run();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 显示空行
    public static void showln() {

        System.out.println();
    }

    // 显示日志
    public static void show(Object log) {

        System.out.println("-- " + String.valueOf(log));
    }

    // 显示前加空行
    public static void showln(Object log) {

        System.out.println();
        System.out.println("-- " + String.valueOf(log));
    }

}
