package com.hentica.app.module.update;

import com.hentica.app.module.listener.Callback;

/**
 * 检查更新
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.20:22
 */

public interface CheckUpdatePresenter<T> {



    /**
     * 检查是否有更新
     */
    void checkUpdate(Callback<T> l);

}
