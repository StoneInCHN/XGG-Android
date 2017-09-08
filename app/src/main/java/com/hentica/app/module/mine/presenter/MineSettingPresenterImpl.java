package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.AppApplication;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.lib.util.FileHelper;
import com.hentica.app.module.mine.view.MineSettingView;
import com.hentica.app.util.StorageUtil;
import com.hentica.app.util.request.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snow on 2017/2/17.
 */

public class MineSettingPresenterImpl implements MineSettingPresenter {

    // 缓存目录
    private List<String> mCacheFiles = new ArrayList<>();

    private MineSettingView mSettingView;

    public MineSettingPresenterImpl(MineSettingView settingView) {
        this.mSettingView = settingView;
        mCacheFiles.add(ApplicationData.getInstance().getTempDir());
        mCacheFiles.add(AppApplication.getInstance().getCacheDir().getAbsolutePath());
    }

    @Override
    public void clearCacheFiles() {
        for (String path : mCacheFiles) {
            FileHelper.deletePath(path);
        }
        if (mSettingView != null) {
            mSettingView.cacheClearSuccess();
        }
    }

    @Override
    public String getCacheFilesSize() {
        long cacheSize = 0;
        for (String path : mCacheFiles) {
            cacheSize += FileHelper.getDirSize(new File(path));
        }
        return FileHelper.formatSize(cacheSize, FileHelper.SIZETYPE_MB);
    }

    @Override
    public void setJpushToggle(final boolean msgSwitch) {
        Request.setJpushToggle(msgSwitch, new Post.FullListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(long curr, long max) {

            }

            @Override
            public void onResult(NetData result) {
                if (result.isSuccess()) {
                    StorageUtil.getLastLoginInfo().setPushMsg(msgSwitch);
                }
            }
        });
    }

}
