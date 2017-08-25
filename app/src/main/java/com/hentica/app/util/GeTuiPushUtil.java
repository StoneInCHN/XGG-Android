package com.hentica.app.util;

/**
 * 个推push 自定义集成工具：<br>
 * 使用Device Token方式进行推送（需要tag方式推送的，见Dentistry项目）
 * <p>
 * <p>
 * 个推后台，添加新建消息的设置：<br>
 * ......<br>
 * 后续动作 : 打开指定页面(Activity) com.hentica.app.dentistry.activity.MsgCenterActivity
 */
public class GeTuiPushUtil {}
//        implements GeTuiMessageListener {
//
//    /**
//     * 单实例
//     */
//    private static GeTuiPushUtil sPushUtil;
//
//    /**
//     * 上传线程
//     */
//    private UploadTokenThread mUploadTokenThread;
//
//    /**
//     * 是否是测试模式
//     */
//    public static boolean mIsDebug = true;
//
//    /**
//     * 程序运行环境
//     */
//    private Context mContext;
//
//    private GeTuiPushUtil() {
//    }
//
//    /**
//     * 取得全局变量实例
//     */
//    public static GeTuiPushUtil getInstance() {
//
//        if (sPushUtil == null) {
//
//            sPushUtil = new GeTuiPushUtil();
//
//            // 注册接收事件处理
//            EventBus.getDefault().register(sPushUtil);
//
//            // 注册消息回调事件
//            GeTuiPushReceiver.setListener(sPushUtil);
//        }
//        return sPushUtil;
//    }
//
//    /**
//     * 在每个activity的onCreate方法中调用
//     */
//    public void onAppStart() {
//        PushManager.getInstance().initialize(mContext);
//    }
//
//    /**
//     * 设置程序运行环境
//     */
//    public void setContext(Context context) {
//
//        mContext = context;
//    }
//
//    /**
//     * 接收登录事件处理
//     *
//     * @param event
//     */
//    @Subscribe
//    public void onEventMainThread(DataEvent.OnLoginEvent event) {
//
//        // 开启推送服务
//        PushManager.getInstance().turnOnPush(mContext);
//
//        // 登录后向尝试向服务器提交token
//        if (mUploadTokenThread != null) {
//
//            mUploadTokenThread.mIsStop = true;
//        }
//        mUploadTokenThread = new UploadTokenThread();
//        mUploadTokenThread.start();
//    }
//
//    /**
//     * 接收注销事件处理
//     *
//     * @param event
//     */
//    @Subscribe
//    public void onEventMainThread(DataEvent.OnLoginInvaildEvent event) {
//
//        // 关闭推送服务
//        PushManager.getInstance().turnOffPush(mContext);
//    }
//
//    /**
//     * 将要注销登录，在注销时主动调用
//     */
//    public void willLoginOutBySelf() {
//
//        // 关闭推送服务
//        // mPushAgent.disable();
//
//        // 只请求一次
//        requestRemoveDeviceToken();
//    }
//
//    @Override
//    public void onMessageReceived(Context context, String msg) {
//
//        this.dealMsg(msg);
//    }
//
//    /**
//     * 处理消息
//     *
//     * @param msg 消息
//     */
//    private void dealMsg(String msg) {
//
//        // 若未登录，则不响应
//        if (!ApplicationData.getInstance().isLogin()) {
//
//            return;
//        }
//
//        // 唤醒app
//        wakeUpApp(mContext);
//
//        // 解析数据
//        DataEvent.OnReceivedMessageEvent event = null;
//        try {
//            Gson gson = new Gson();
//            event = gson.fromJson(msg, DataEvent.OnReceivedMessageEvent.class);
//
//        } catch (Exception e) {
//
//            CrashHandler.getInstance().handleException("JsonParseError_String", e);
//            e.printStackTrace();
//        }
//
//        if (event != null) {
//
//            // 发出事件
//            EventBus.getDefault().post(event);
//        }
//    }
//
//
//    /**
//     * 唤醒app，把app切换到前台
//     */
//    private static void wakeUpApp(Context context) {
//
//        String packageName = context.getPackageName();
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
//        ActivityManager manager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningTaskInfo> task_info = manager.getRunningTasks(20);
//        for (int i = 0; i < task_info.size(); i++) {
//
//            // 若当前任务未结束
//            if (packageName.equals(task_info.get(i).topActivity.getPackageName())) {
//
//                String className = task_info.get(i).topActivity.getClassName();
//                // 这里是指从后台返回到前台 前两个的是关键
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                try {
//                    intent.setComponent(new ComponentName(context, Class.forName(className)));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // 把app从后台唤醒到前台
//                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                        | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                break;
//            }
//        }
//
//        context.startActivity(intent);
//    }
//
//    /**
//     * 请求删除deviceToken
//     */
//    private void requestRemoveDeviceToken() {
//
//        // 本应该不需要删除
//        // String platform = "1";
//        // Request.getTenantUseruploadToken(platform, getClientId(), new
//        // Listener() {
//        //
//        // @Override
//        // public void onResult(NetData result) {
//        //
//        // }
//        // });
//    }
//
//    /**
//     * 取得推送ClientId
//     */
//    private String getClientId() {
//
//        return PushManager.getInstance().getClientid(mContext);
//    }
//
//    /**
//     * 上传DeviceToken到服务器
//     */
//    private class UploadTokenThread extends Thread {
//
//        /**
//         * 最大请求次数
//         */
//        private int mMaxRequestCount = 3;
//
//        /**
//         * 当前请求次数
//         */
//        private int mRequestCount;
//
//        /**
//         * 用于在主线程执行任务
//         */
//        private Handler mHandler = new Handler();
//
//        /**
//         * 是否停止
//         */
//        private boolean mIsStop = false;
//
//        @Override
//        public void run() {
//
//            // 线程获取DeviceToken，每1秒钟查看token是否获取成功
//            while (TextUtils.isEmpty(getClientId())) {
//
//                if (mIsStop) {
//                    return;
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // 获取到Token之后
//            // 向服务器发送deviceToken
//            mRequestCount = 0;
//            mHandler.post(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    // 网络请求不能在线程里执行，因此post
//                    registerDeviceToken();
//                }
//            });
//        }
//
//        /**
//         * 注册设备
//         */
//        private void registerDeviceToken() {
//
//            if (!mIsStop) {
//
//                String platform = "1";
//                // 待提供api
//                // 2017/2/27 上传TOKEN
////                Request.getMemberUserUploadPushToken(getClientId(), platform,
////                        new Post.FullListener() {
////                            @Override
////                            public void onResult(NetData result) {
////                                if (!result.isSuccess()) {
////
////                                    // 再次尝试
////                                    mRequestCount++;
////                                    if (mRequestCount < mMaxRequestCount) {
////
////                                        registerDeviceToken();
////                                    }
////                                }
////                            }
////
////                            @Override
////                            public void onStart() {
////
////                            }
////
////                            @Override
////                            public void onProgress(long curr, long max) {
////
////                            }
////                        });
//            }
//        }
//    }
//}
