package com.hentica.app.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RemoteViews;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.lib.util.FileHelper;
import com.hentica.app.lib.util.NetHelper;
import com.hentica.app.lib.util.PhoneInfo;
import com.hentica.app.module.down.DownloadManager;
import com.hentica.app.module.down.Okhttp3DownManager;
import com.hentica.app.module.entity.ResAppUpdateData;
import com.hentica.app.widget.dialog.SelfAlertDialog;
import com.hentica.app.widget.dialog.UpdateDialog;
import com.fiveixlg.app.customer.R;

import java.io.File;

/**
 * 检测更新
 */
public class CheckUpdateUtil implements FragmentListener.OnKeyListener {

    // 通知栏布局
    private static final int VIEW_LAYOUT_NOTIFY = R.layout.common_layout_update;
    // 标题id
    private static final int VIEW_ID_TITLE = R.id.common_update_title;
    // 进度条id
    private static final int VIEW_ID_PROGRESS = R.id.common_update_progress;

    // 消息类型，下载中
    private static final int MSG_TYPE_DOWN_ING = 1;
    // 消息类型，下载成功
    private static final int MSG_TYPE_DOWN_SUCCESS = 2;
    // 消息类型，下载失败
    private static final int MSG_TYPE_DOWN_ERROR = 3;
    // 消息类型，数据库下载中
    private static final int MSG_TYPE_DB_DOWN_ING = 4;
    // 消息类型，数据库下载成功
    private static final int MSG_TYPE_DB_DOWN_SUCCESS= 5;
    // 消息类型，数据库下载失败
    private static final int MSG_TYPE_DB_DOWN_ERROR = 6;
    // 通知栏管理器
    private NotificationManager mNotificationManager = null;
    // 通知栏
    private Notification mNotification;
    // 通知栏id
    private int mNotification_id = 19172439;
    // 程序运行环境
    private Context mContext = null;
    // 是否正在下载中
    private boolean mIsDowning = false;
    // 数据库是否正在下载中
    private boolean mIsDBDowning = false;

    private boolean isFource = false;
    // 更新对话框
    private UpdateDialog mUpdateDialog;

    // 单实例
    private static CheckUpdateUtil mCheckUpdateUtil;

    private static final String DB_PATH = "db/car_peccancy_config.db";

    private String mUpDataAppName;

    /**
     * 取得单实例
     */
    public static CheckUpdateUtil getInstance(Context context) {
        if(mCheckUpdateUtil == null){
            synchronized (CheckUpdateUtil.class){
                if(mCheckUpdateUtil == null){
                    mCheckUpdateUtil = new CheckUpdateUtil();
                }
            }
        }
        mCheckUpdateUtil.setContext(context.getApplicationContext());
        return mCheckUpdateUtil;
    }

    // 构造函数
    private CheckUpdateUtil() {
    }

    /**
     * 程序运行环境
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 程序运行环境
     */
    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * 检查更新，提示app更新操作
     */
    public void checkUpdateApp(final UsualFragment fragment){
        // TODO: 2017/4/8 更新
        fragment.showToast("TODO...更新");
        // 只维护一个更新操作
//        if (mIsDowning) {
//
//            this.showUpdateDialog(fragment);
//            return;
//        }
//        // 对比服务器与本地版本号是否一致
//        final MResConfigData config = ApplicationData.getInstance().getConfigData();
//        if(config == null || TextUtils.isEmpty(config.getAndroidVersionCode())){
//            //  服务器版本信息为空
//            fragment.showToast("检测升级信息异常！");
//            return ;
//        }
//        int VersionCode = PhoneInfo.getAndroidVersioncode();
//        // 服务器上的版本号
//        int serviceVersionCode = getIntVersionCode(config.getAndroidVersionCode());
//        if(serviceVersionCode <= VersionCode){
//            // 不需要更新
//            fragment.showToast("已经是最新版本！");
//            return ;
//        }
//
//        // 更新
//        // 弹出警告框
//        SelfAlertDialog dialog = new SelfAlertDialog();
//        dialog.setText("确定要更新版本？");
//        dialog.show(fragment.getChildFragmentManager(), "确认升级");
//        dialog.setSureClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 版本更新地址 配置地址+userId
//                String url = config.getAndroidUrl();
//                showUpdateDialog(fragment);
//                updateApk(url);
//            }
//        });
    }

    /**
     * 检查更新，提示app更新操作
     */
    public void checkUpdateApp(final UsualFragment fragment, final ResAppUpdateData updateData, boolean showTips){
        if(updateData == null || updateData.getId() < 0){
            return;
        }
        int versionCode = PhoneInfo.getAndroidVersioncode();
        if(updateData.getId() == 0 || updateData.getVersionCode() <= versionCode){
            if(showTips) {
                fragment.showToast("已经是最新版本！");
            }
            return;
        }
        // 只维护一个更新操作
        if (mIsDowning) {
            this.showUpdateDialog(fragment, updateData.isIsForced());
            return;
        }
        //组装下载地址
        String url = ApplicationData.getInstance().mApkDownloadUrl + updateData.getApkPath();
        getUpdateAppName(url);//更新文件名
        if (checkUpdateApkExist()){
            installApk();
            return;
        }
        if (updateData.isIsForced()) {//强制更新
            showUpdate(url, fragment, true);
            return;
        }
        // 更新
        // 弹出警告框
        SelfAlertDialog dialog = new SelfAlertDialog();
        dialog.setText("确定要更新版本？");
        dialog.show(fragment.getChildFragmentManager(), "确认升级");
        dialog.setSureClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 版本更新地址 配置地址+userId
                String url = ApplicationData.getInstance().mApkDownloadUrl + updateData.getApkPath();
                showUpdate(url, fragment, false);
//                showUpdateDialog(fragment);
//                updateApk(url);
            }
        });
    }

    private void getUpdateAppName(String url){
        String name = "";
        if (TextUtils.isEmpty(url)) {
            name = "update.apk";
        } else {
            int index = url.lastIndexOf('/');
            if (index < 0) {
                name = url;
            } else {
                name = url.substring(index);
            }
        }
        mUpDataAppName = name;
    }

    private boolean checkUpdateApkExist(){
        File file = new File(getDownOverPath());
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void showUpdate(String url, UsualFragment fragment, boolean isFource){
        showUpdateDialog(fragment, isFource);
        updateApk(url);
    }

    /** 获取int类型版本号 */
    private int getIntVersionCode(String code){
        int intCode;
        try {
            intCode = Integer.parseInt(code);
        }catch (Exception e){
            // 类型转换异常
            intCode = -1;
        }
        return intCode;
    }

    /**
     *  检查数据库更新
     */
//    public void checkUpdateDB() {
//        // 只维护一个更新操作
//        if (mIsDBDowning) {
//            return;
//        }
//
//        // 比较服务器和本地md5码是否一致
//        MResConfigData config = ApplicationData.getInstance().getConfigData();
//        if(config == null || TextUtils.isEmpty(config.getConfigDBMd5())){
//            // 没有服务器对比信息
//            return ;
//        }
//        // 数据库地址
//        String dbPath = getDBPath();
//        // 本地数据库的md5加密信息
//        String dbMD5 = MD5Util.getMd5ByFile(dbPath);
//        if(!TextUtils.equals(config.getConfigDBMd5(),dbMD5)){
//            // 需要更新数据库
//            // 数据库下载地址
//            String url = config.getConfigDBUrl();
//            // 下载数据库
//            updateDB(url);
//        }
//    }

    /**
     * 检查更新，提示app更新操作
     */
//    public void checkUpdate(final UsualFragment fragment, final boolean isShowLoading,
//                            final boolean isShowToast) {
//
//        // 只维护一个更新操作
//        if (mIsDowning) {
//
//            this.showUpdateDialog(fragment);
//            return;
//        }
//
//        // 显示加载框
//        if (isShowLoading) {
//            fragment.showLoadingDialog();
//        }
//        String appType = "1";
//        String VersionCode = "" + PhoneInfo.getAndroidVersioncode();
//        String VersionName = PhoneInfo.getAndroidVersionname();
//        Request.getUpdatecheckupdate(appType, VersionCode, VersionName, new Post.FullListener() {
//
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onProgress(long curr, long max) {
//            }
//
//            @Override
//            public void onResult(NetData result) {
//                // 取消加载框
//                if (isShowLoading) {
//                    fragment.dismissLoadingDialog();
//                }
//                // 若请求失败
//                if (!result.isSuccess()) {
//                    return;
//                }
//                final UpdateInfo info = ParseUtil.parseObject(result.getData(), UpdateInfo.class);
//
//                if (info != null) {
//                    if (info.getNeedUpdate() != null && info.getNeedUpdate().equals("1")) {
//                        if (info.getDownloadUrl() != null) {
//
//                            // 弹出警告框
//                            SelfAlertDialog dialog = new SelfAlertDialog();
//                            dialog.setText("确定要更新版本？");
//                            dialog.show(fragment.getChildFragmentManager(), "确认升级");
//
//                            dialog.setSureClickListener(new OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//
//                                    String url = info.getDownloadUrl();
//                                    showUpdateDialog(fragment);
//                                    updateApk(url);
//                                }
//                            });
//
//                        } else {
//                            showToast(getContext(), "下载地址异常", isShowToast);
//                        }
//                    } else {
//                        showToast(getContext(), "已经是最新版！", isShowToast);
//                    }
//                } else {
//                    showToast(getContext(), "检测升级信息异常", isShowToast);
//                }
//            }
//        });
//    }

    // 显示提示文字
    private void showToast(Context context, String text, boolean isShow) {

        if (isShow) {

            ViewUtil.showShortToast(context, text);
        }
    }

    // 下载apk文件，可重复调用，不会重复下载
    private void updateApk(String url) {

        // 若当前没在下载中
        if (!mIsDowning) {

            // 删除旧文件
            try {

                File downFile = new File(getDowningPath());
                File apkFile = new File(getDownOverPath());
                //noinspection ResultOfMethodCallIgnored
                downFile.delete();
                //noinspection ResultOfMethodCallIgnored
                apkFile.delete();

            } catch (Exception e) {

                e.printStackTrace();
            }

            this.notificationInit();
            this.downloadApk(url);

            // 标记为下载中
            mIsDowning = true;
        }
    }

    // 下载数据库文件，可重复调用，不会重复下载
    private void updateDB(String url) {

        // 若当前没在下载中
        if (!mIsDowning) {

            // 删除旧文件
            try {

                File downFile = new File(getDBDowningPath());
                File dbFile = new File(getDBDownOverPath());
                //noinspection ResultOfMethodCallIgnored
                downFile.delete();
                //noinspection ResultOfMethodCallIgnored
                dbFile.delete();

            } catch (Exception e) {

                e.printStackTrace();
            }
            this.downloadDB(url);
            // 标记为下载中
            mIsDBDowning = true;
        }
    }

    // 显示更新对话框
    private void showUpdateDialog(UsualFragment fragment, boolean isFource) {
        this.isFource = isFource;
        try {
            if (mUpdateDialog != null) {

                if (!mUpdateDialog.isResumed()) {

                    mUpdateDialog.dismiss();
                    mUpdateDialog = null;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        if (mUpdateDialog == null) {

            mUpdateDialog = new UpdateDialog();
            mUpdateDialog.setFource(isFource);
            mUpdateDialog.show(fragment.getChildFragmentManager(), "更新对话框");
            mUpdateDialog.setDismissListener(new UpdateDialog.OnDismissListener() {

                @Override
                public void onDismiss(UpdateDialog dialog) {

                    mUpdateDialog = null;
                }
            });
        }
    }

    // 为对话框更新进度
    private void setUpdateDialogProgress(int curr, int max) {

        if (mUpdateDialog != null) {

            float progress = ((float) curr / max);
            mUpdateDialog.setProgress(progress);
        }
    }

    // 初始化通知栏
    private void notificationInit() {

        // 通知内容
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());// .setAutoCancel(true)
        // Notification.Builder builder = new
        // Notification.Builder(getContext());
        builder.setAutoCancel(true); // 自动取消
        builder.setSmallIcon(R.drawable.ic_launcher);
        mNotification = builder.build();
        mNotification.tickerText = "开始下载";
        mNotification.flags |= Notification.FLAG_NO_CLEAR;
        mNotification.contentView = new RemoteViews(getContext().getPackageName(), VIEW_LAYOUT_NOTIFY);

        mNotification.contentIntent = PendingIntent.getActivity(mContext, 0, new Intent(), 0);
        // 发出通知
        mNotificationManager = (NotificationManager) getContext().getSystemService(Activity.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotification_id, mNotification);
    }

    // 下载apk文件
    private void downloadApk(final String url) {

        // 保存同时只有一个下载任务
        if (mIsDowning) {
            return;
        }

        new Thread(new Runnable() {

            private float mLastPercent;

            @Override
            public void run() {

                final String savePath = getDowningPath();

                NetHelper.DownLoadListener downLoadListener = new NetHelper.DownLoadListener() {

                    @Override
                    public void update(int downByte, int allByte) {

                        Message msg = new Message();
                        msg.what = MSG_TYPE_DOWN_ING;
                        msg.arg1 = downByte; // 当前进度
                        msg.arg2 = allByte; // 总进度

                        float percent = (float) downByte / allByte;
                        if (percent - mLastPercent >= 0.01f || downByte == allByte) {

                            mNotifyHandler.sendMessage(msg);
                            mLastPercent = percent;
                        }
                    }

                    @Override
                    public void onStart() {

                        Message msg = new Message();
                        msg.what = MSG_TYPE_DOWN_ING;
                        msg.arg1 = 0; // 当前进度
                        msg.arg2 = 100; // 总进度
                        mNotifyHandler.sendMessage(msg);
                    }

                    @Override
                    public void onOver(boolean isSuccess) {

                        Message msg = new Message();
                        msg.what = isSuccess ? MSG_TYPE_DOWN_SUCCESS : MSG_TYPE_DOWN_ERROR;
                        mNotifyHandler.sendMessage(msg);
                    }
                };
                DownloadManager manager = new Okhttp3DownManager();
                manager.download(url, savePath, downLoadListener);
            }
        }).start();
    }

    // 下载数据库文件
    private void downloadDB(final String url){
        // 保存同时只有一个下载任务
        if (mIsDBDowning) {
            return;
        }

        new Thread(new Runnable() {

            private float mLastPercent;

            @Override
            public void run() {

                final String savePath = getDBDowningPath();

                NetHelper.downLoadToFile(url, savePath, new NetHelper.DownLoadListener() {

                    @Override
                    public void update(int downByte, int allByte) {

//                        Message msg = new Message();
//                        msg.what = MSG_TYPE_DATASOURCE_DOWN_ING;
//                        msg.arg1 = downByte; // 当前进度
//                        msg.arg2 = allByte; // 总进度
//
//                        float percent = (float) downByte / allByte;
//                        if (percent - mLastPercent >= 0.01f || downByte == allByte) {
//
//                            mNotifyHandler.sendMessage(msg);
//                            mLastPercent = percent;
//                        }
                    }

                    @Override
                    public void onStart() {

//                        Message msg = new Message();
//                        msg.what = MSG_TYPE_DOWN_ING;
//                        msg.arg1 = 0; // 当前进度
//                        msg.arg2 = 100; // 总进度
//                        mNotifyHandler.sendMessage(msg);
                    }

                    @Override
                    public void onOver(boolean isSuccess) {

                        Message msg = new Message();
                        msg.what = isSuccess ? MSG_TYPE_DB_DOWN_ING : MSG_TYPE_DB_DOWN_ERROR;
                        mNotifyHandler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }

    // 下载中
    private void onDowning(int downLoadFileSize, int fileSize) {

        int result = downLoadFileSize * 100 / fileSize;

        // 通知栏
        if(mNotification != null){
            mNotification.tickerText = "正在下载";
            mNotification.contentView.setTextViewText(VIEW_ID_TITLE,
                    getContext().getResources().getString(R.string.app_name) + " " + result + "%");
            mNotification.contentView.setProgressBar(VIEW_ID_PROGRESS, fileSize, downLoadFileSize, false);
            mNotificationManager.notify(mNotification_id, mNotification);
        }

        // 对话框进度
        setUpdateDialogProgress(downLoadFileSize, fileSize);
    }

    // 下载成功
    private void onDownSuccess() {

        // 通知栏
        mNotificationManager.cancel(mNotification_id);

        mNotification.tickerText = "下载完成，点击安装";
        mNotification.flags &= ~Notification.FLAG_NO_CLEAR;
        mNotification.contentView.setTextViewText(VIEW_ID_TITLE,
                getContext().getResources().getString(R.string.app_name) + " 下载完成");
        mNotification.contentView.setProgressBar(VIEW_ID_PROGRESS, 100, 100,
                false);
        mNotification.defaults |= Notification.DEFAULT_SOUND;

        // 命名为apk文件
        File downFile = new File(getDowningPath());
        File apkFile = new File(getDownOverPath());
        //noinspection ResultOfMethodCallIgnored
        downFile.renameTo(apkFile);
        installApk();
        // // 点击安装
        // PendingIntent pIntent =
        // PendingIntent.getActivity(getContext(),
        // 0, intent, 0);
        // mNotification.contentIntent = pIntent;
        //
        // mNotificationManager.notify(mNotification_id, mNotification);

        // 取消对话框
        this.dismissUpdateDialog();

        // 标记为未在下载中
        mIsDowning = false;
    }

    /**
     * 安装apk
     */
    private void installApk(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + getDownOverPath());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        // 自动跳转
        getContext().startActivity(intent);
    }

    // 下载失败
    private void onDownError() {

        // 通知栏
        mNotificationManager.cancel(mNotification_id);

        mNotification.tickerText = "下载失败";
        mNotification.flags &= ~Notification.FLAG_NO_CLEAR;
        mNotification.contentView.setTextViewText(VIEW_ID_TITLE,
                getContext().getResources().getString(R.string.app_name) + " 下载失败");
        mNotification.contentView.setProgressBar(VIEW_ID_PROGRESS, 100, 100, false);
        mNotificationManager.notify(mNotification_id, mNotification);

        // 取消对话框
        this.dismissUpdateDialog();

        // 标记为未在下载中
        mIsDowning = false;
    }

    // 取消更新对话框
    private void dismissUpdateDialog() {

        try {
            if (mUpdateDialog != null) {

                mUpdateDialog.dismiss();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 通知回调
    private Handler mNotifyHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            // 定义一个Handler，用于处理下载线程与UI间通讯
            switch (msg.what) {
                case MSG_TYPE_DOWN_ING: // APP更新进度
                    int downLoadFileSize = msg.arg1;
                    int fileSize = msg.arg2;

                    onDowning(downLoadFileSize, fileSize);
                    break;

                case MSG_TYPE_DOWN_SUCCESS: // APP下载成功
                    onDownSuccess();
                    break;

                case MSG_TYPE_DOWN_ERROR: // APP下载失败
                    onDownError();
                    break;

                case MSG_TYPE_DB_DOWN_ING: // 数据库下载中
                    // 暂不做操作
                    break;

                case MSG_TYPE_DB_DOWN_SUCCESS: // 数据库下载成功
                    onDBDownSuccess();
                    break;

                case MSG_TYPE_DB_DOWN_ERROR: // 数据库下载失败
                    // 暂不做操作
                    onDBDownError();
                    break;
                default:
                    break;
            }
        }
    };

    /** 数据库下载成功 */
    private void onDBDownSuccess(){

        // 命名为db文件
        File downFile = new File(getDBDowningPath());
        File dbFile = new File(getDBDownOverPath());
        downFile.renameTo(dbFile);

        // 删除以前的数据库
        File oldDBFile = new File(getDBPath());
        oldDBFile.delete();

        // 复制数据库文件
        FileHelper.copyFile(getDBDownOverPath(),getDBPath());

        // 标记为未在下载中
        mIsDBDowning = false;
    }

    /** 数据库下载失败 */
    private void onDBDownError(){
        // 标记为未在下载中
        mIsDBDowning = false;
    }

    // 取得下载路径
    private String getDowningPath() {

        return ApplicationData.getInstance().getTempDir() + "update/tmp/" + mUpDataAppName;
    }

    // 取得下载完成更名后的路径
    private String getDownOverPath() {

        return ApplicationData.getInstance().getTempDir() + "update/" + mUpDataAppName;
    }

    // 取得数据库下载路径
    private String getDBDowningPath(){
        return ApplicationData.getInstance().getSystemTempDir() + "update/car_peccancy_config.db_";
    }

    // 取得数据库下载完成后更名的路径
    private String getDBDownOverPath(){
        return ApplicationData.getInstance().getSystemTempDir() + "update/car_peccancy_config.db";
    }

    // 取得数据库路径
    private String getDBPath(){
        return ApplicationData.getInstance().getNotTempDir() + DB_PATH;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFource && mIsDowning) {
                return true;
            }
        }
        return false;
    }
}
