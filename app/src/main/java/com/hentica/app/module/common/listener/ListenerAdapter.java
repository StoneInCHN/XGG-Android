package com.hentica.app.module.common.listener;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.lib.net.Post;
import com.hentica.app.util.ParseUtil;

import java.util.List;

/**
 * 回调适配器，用于把网络回调转换为对象回调 <br />
 * 网络回调: Post.FullListener <br />
 * 对象回调: ListenerAdapter&lt;T> <br />
 *
 * @author mili
 * @createTime 2016/10/12
 */
public abstract class ListenerAdapter<T> implements Post.FullListener {

    /**
     * 创建一个对象回调适配器，自动把网络数据转换为指定对象
     *
     * @param objClass 对象类型
     * @param listener 对象回调
     * @return 适配器
     */
    public static <T> ListenerAdapter<T> createObjectListener(final Class<T> objClass, OnDataBackListener<T> listener) {

        return new ListenerAdapter<T>(listener) {
            @Override
            protected T parse(String json) {
                return ParseUtil.parseObject(json, objClass);
            }
        };
    }

    /**
     * 创建一个列表回调适配器，自动把网络数据转换为指定对象
     *
     * @param objClass 对象类型
     * @param listener 列表回调
     * @return 适配器
     */
    public static <T> ListenerAdapter<List<T>> createArrayListener(final Class<T> objClass, OnDataBackListener<List<T>> listener) {

        return new ListenerAdapter<List<T>>(listener) {
            @Override
            protected List<T> parse(String json) {
                return ParseUtil.parseArray(json, objClass);
            }
        };
    }

    // 数据层回调
    protected OnDataBackListener<T> mListener;

    /**
     * 构造函数
     *
     * @param listener 输出
     */
    public ListenerAdapter(OnDataBackListener<T> listener) {
        mListener = listener;
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
    public void onResult(NetData result) {

        if (result.isSuccess()) {

            if (mListener != null) {
                mListener.onSuccess(parse(result.getData()));
            }
        } else {
            if (mListener != null) {
                mListener.onFailed(result);
            }
        }
    }

    /**
     * 解析为指定数据结构
     *
     * @param json json字符串
     * @return 指定数据结构
     */
    protected abstract T parse(String json);
}
