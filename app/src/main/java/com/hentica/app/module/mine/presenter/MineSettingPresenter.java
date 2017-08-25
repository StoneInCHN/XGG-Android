package com.hentica.app.module.mine.presenter;

/**
 * Created by Snow on 2017/2/17.
 */

public interface MineSettingPresenter {

    /**
     * 清除缓存文件
     */
    void clearCacheFiles();

    /**
     * 获取缓存文件大小
     *
     * @return
     */
    String getCacheFilesSize();
}
