package com.hentica.app.module.common.listener;

import com.hentica.app.lib.net.NetData;

/**
 * 用于转发一个listener
 *
 * @author mili
 * @createTime 2016/10/12
 */
public class DataListenerWrapper<T> implements OnDataBackListener<T> {

    // 被转发的listener
    private OnDataBackListener<T> mListener;

    /**
     * 构造函数
     */
    public DataListenerWrapper() {
    }

    /**
     * 构造函数
     *
     * @param listener 被转发的listener
     */
    public DataListenerWrapper(OnDataBackListener<T> listener) {
        mListener = listener;
    }

    /**
     * 设置回调
     */
    public DataListenerWrapper<T> listener(OnDataBackListener<T> listener) {

        mListener = listener;
        return this;
    }

    @Override
    public void onStart() {
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    public void onProgress(long curr, long max) {
        if (mListener != null) {
            mListener.onProgress(curr, max);
        }
    }

    @Override
    public void onSuccess(T data) {
        if (mListener != null) {
            mListener.onSuccess(data);
        }
    }

    @Override
    public void onFailed(NetData result) {
        if (mListener != null) {
            mListener.onFailed(result);
        }
    }
}
