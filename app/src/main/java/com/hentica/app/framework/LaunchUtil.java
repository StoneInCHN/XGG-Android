package com.hentica.app.framework;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.Post;
import com.hentica.app.lib.storage.DataBaseHelper;
import com.hentica.app.lib.storage.DataBaseUtils;
import com.hentica.app.lib.storage.Storage;
import com.hentica.app.lib.util.CrashHandler;
import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.lib.util.TipUtil;
import com.hentica.app.lib.view.FloatPostViewHelper;
import com.hentica.app.module.db.ConfigModel;
import com.hentica.app.util.FontUtil;
import com.hentica.app.util.PackageUtil;
import com.hentica.app.util.request.AppPostWraper;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;

/**
 * 里面存放了一些启动时就应该进行的操作
 */
public class LaunchUtil {

    /**
     * 程序启动了
     */
    public static void onAppLaunch(Context context) {

        // 判断是否是主进程
        String mProcessName = getCurrentProcessName(context);
        boolean isMainProgress = TextUtils.equals(mProcessName, context.getPackageName());

        LaunchUtil.onLaunch(context, isMainProgress);
    }

    /**
     * 启动操作
     *
     * @param context 程序运行环境
     * @param isMain  是否主进程
     */
    private static void onLaunch(Context context, boolean isMain) {

        // 若不是主进程，则不进行下面的初始化操作
        if (!isMain) {
            return;
        }

        // 是否是测试模式
        boolean isDebug = ApplicationData.IS_DEBUG;

        // 设置全局字体
        FontUtil.init();

        // 设置测试模式
        FloatPostViewHelper.getInstance().setIsShowFloat(isDebug);
//		GeTuiPushUtil.mIsDebug = isDebug;
        AppPostWraper.IS_DEBUG = isDebug;
        Post.IS_DEBUG = isDebug;
        // 腾讯bugly错误统计
        CrashReport.initCrashReport(context, Constants.BUGLY_ID, isDebug);
        // 异常日志记录，开发阶段用

        if (isDebug) {
            String url = Constants.DEBUG_LOG_URL;
            String logDir = ApplicationData.getInstance().getTempDir() + "/log/";
            CrashHandler.getInstance().init(context, url, logDir);
        }

        // 为各个工具类绑定运行环境
        Storage.setContext(context);
        PhoneInfo.setContext(context);
        Post.setContext(context);

        TipUtil.setContext(context);
        // 为数据 库绑定运行环境
        DataBaseHelper.setContext(context);
        DataBaseUtils.init(context);
        // 区域配置
//		RegionHelper.init(context);
        ConfigModel.getInstace().destory();
        ConfigModel.getInstace().init(context);

        Resources res = context.getResources();
        // 有盟分享 微信 appid appsecret
        PlatformConfig.setWeixin(res.getString(R.string.WeiX_AppId), res.getString(R.string.WeiX_AppSecret));
        // QQ
//		PlatformConfig.setQQZone(res.getString(R.string.QQ_AppId), res.getString(R.string.QQ_AppKey));
        // 新浪微博
//		PlatformConfig.setSinaWeibo(res.getString(R.string.WeiB_AppId), res.getString(R.string.WeiB_AppSecret));
        // 融云通信
//		RongUtil.newInstance().init(context);

        // UMeng 推送 push初始化
        // UmengPushUtil.newInstance().dealWithNotification(context);
        // 个推
//		GeTuiPushUtil.newInstance().setContext(context);
        // 检测更新
        // CheckUpdateUtil.newInstance().setContext(context);

        // 百度地图
        SDKInitializer.initialize(context);
//		KF5SDKInitializer.initialize(context);
//		GeTuiPushUtil.getInstance().setContext(context);

        //初始化极光推送
//        JPushInterface.setDebugMode(isDebug);
//        JPushInterface.init(context);
        PackageUtil.init(context);
//		Log.d("JPushInterface", "initRegistId: " + JPushInterface.getRegistrationID(context));
    }

//	private static void initRegionCitys(){
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				CityHelper.getInstance();
//			}
//		}).start();
//	}

    /**
     * 取得当前进程名
     */
    private static String getCurrentProcessName(Context context) {

        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
