package com.hentica.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.util.FileHelper;
import com.hentica.app.lib.util.MD5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 地区数据更新辅助工具
 */
public class RegionUpdateUtil {

    // 单例
    @SuppressLint("StaticFieldLeak")
    private static RegionUpdateUtil mInstance = new RegionUpdateUtil();

    // 多长时间读取一次配置信息，毫秒
    @SuppressWarnings("FieldCanBeLocal")
    private int mCheckSleepTime = 5000;

    // 下载状态
    private enum DownStatus {
        // 未检查
        kNotCheck,
        // 检查中
        kChecking,
        // 下载成功
        kSuccess
    }

    // 程序运行环境
    private Context mContext;

    // 剩余尝试下载次数
    private int mRestCount;

    // 最多尝试次数
    private int mMaxDownCount = 3;

    // 下载状态
    private DownStatus mDownStatus = DownStatus.kNotCheck;

    /**
     * 获取单例
     */
    public static RegionUpdateUtil getInstance() {

        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context 程序运行环境
     */
    public void init(Context context) {
        mContext = context;
    }

    /**
     * 取得地区数据库路径
     */
    public String getDbPath() {

        return String.format("%s/db/region.db", getDirPath());
    }

    /**
     * 检查更新
     */
    public void checkUpdate() {

        // 若下载完成后文件不存在，则把状态置为notCheck
        if (mDownStatus == DownStatus.kSuccess){

            // 先检查，当文件不存在的时候才复制
            String dbPath = getDbPath();
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {

                mDownStatus = DownStatus.kNotCheck;
            }
        }

        // 若未检查过
        if (mDownStatus == DownStatus.kNotCheck) {
            mDownStatus = DownStatus.kChecking;

            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    // 复制初始文件到sd卡中
                    copyDb();

                    boolean needUpdate = needUpdate();
                    final String newUrl = getDownUrl();
                    final String tmpSavepath = String.format("%s/region_down_db.tmp", getDirPath());

                    // 若可以下载
                    if (newUrl != null && needUpdate) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                mRestCount = mMaxDownCount;
                                downFile(newUrl, tmpSavepath);
                            }
                        });
                    }
                    // 若未取得下载地址
                    else if (newUrl == null) {
                        mDownStatus = DownStatus.kNotCheck;

                    }
                    // 若不需要更新
                    else {
                        mDownStatus = DownStatus.kSuccess;
                    }
                }
            }).start();
        }
    }

    // 取得下载地址，此方法应该在线程中执行
    private String getDownUrl() {

        // 总共检查多少次
        int restCheckCount = 50;
        String newUrl = null;

        // TODO
//        // 读取下载信息
//        MKResConfigData configData = null;
//        while (configData == null && restCheckCount > 0) {
//
//            configData = ApplicationData.newInstance().getConfigData();
//
//            if (configData != null) {
//
//                newUrl = configData.getAreaConfigUrl();
//            }
//
//            // 若未获取到值，则一段时间后再次尝试
//            if (configData == null) {
//
//                restCheckCount--;
//
//                // 一段时间后再尝试
//                try {
//                    Thread.sleep(mCheckSleepTime);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        return newUrl;
    }

    // 复制数据库到外存中
    private void copyDb() {

        // 先检查，当文件不存在的时候才复制
        String dbPath = getDbPath();
        File dbFile = new File(dbPath);
        if (dbFile.exists()) {
            return;
        }

        // 创建文件夹
        File parentFile = dbFile.getParentFile();
        if (!parentFile.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parentFile.mkdirs();
        }

        // 复制
        try {
            InputStream inputStream = mContext.getAssets().open("region/region.db");
            OutputStream outputStream = new FileOutputStream(dbFile);
            FileHelper.copyFile(inputStream, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 是否需要更新数据库
    private boolean needUpdate() {

        // TODO
//        // 取得新文件md5
//        MKResConfigData configData = ApplicationData.newInstance().getConfigData();
//        String newMd5 = (configData != null ? configData.getAreaConfigMd5() : "");
//
//        // 旧文件md5
//        String dbFile = getDbPath();
//        String oldMd5 = getMd5(dbFile);
//
//        // 对比md5信息，若不相同，则需要更新
//        return !TextUtils.isEmpty(newMd5) && !TextUtils.equals(newMd5, oldMd5);
        return false;
    }

    // 取得文件夹路径
    private String getDirPath() {
        return String.format("%s/db/", ApplicationData.getInstance().getNotTempDir());
    }

    // 取得某文件的md5值
    private String getMd5(String file) {

        return MD5Util.getMd5ByFile(file);
    }

    // 下载完成
    private void onDownComplete(boolean isSuccess) {

        if (isSuccess) {

            mDownStatus = DownStatus.kSuccess;
        } else {

            mDownStatus = DownStatus.kNotCheck;
        }
    }

    // 下载文件
    private void downFile(final String url, final String savePath) {

        // TODO
//        AbHttpUtil.newInstance(null).get(url, new AbFileHttpResponseListener(new File(savePath)) {
//            @Override
//            public void onStart() {
//            }
//
//            @Override
//            public void onFinish() {
//            }
//
//            @Override
//            public void onModifyPwdSuccess(int statusCode, File file) {
//
//                // 删除旧文件
//                File finalPath = new File(getDbPath());
//                if (finalPath.exists()) {
//                    //noinspection ResultOfMethodCallIgnored
//                    finalPath.delete();
//                }
//
//                // 重命名
//                try {
//                    //noinspection ResultOfMethodCallIgnored
//                    file.renameTo(finalPath);
//                    // 下载完成
//                    onDownComplete(true);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    onDownComplete(false);
//                }
//            }
//
//            @Override
//            public void onFailure(int i, String s, Throwable throwable) {
//
//                mRestCount--;
//                if (mRestCount > 0) {
//
//                    // 重试
//                    downFile(url, savePath);
//                } else {
//
//                    // 下载完成
//                    onDownComplete(false);
//                }
//            }
//        });
    }
}
