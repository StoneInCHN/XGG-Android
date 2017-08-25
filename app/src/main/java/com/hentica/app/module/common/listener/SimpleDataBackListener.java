package com.hentica.app.module.common.listener;

import com.hentica.app.lib.net.NetData;

/**
 * 事件，网络数据回来了，只提供一个complate接口
 *
 * @author mili
 * @createTime 2016/10/12
 */
public abstract class SimpleDataBackListener<T> implements OnDataBackListener<T> {

    @Override
    public void onStart() {
    }

    @Override
    public void onProgress(long curr, long max) {
    }

    @Override
    public void onSuccess(T data) {
        onComplete(true, data);
    }

    @Override
    public void onFailed(NetData result) {

        onComplete(false, null);
    }

    /**
     * 请求完成了
     *
     * @param isSuccess 是否成功
     * @param data       返回的对象
     */
    protected abstract void onComplete(boolean isSuccess, T data);
}
