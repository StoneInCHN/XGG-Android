package com.hentica.app.util;

import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.OnDataBackListener;

/**
 * Created by YangChen on 2016/10/27 10:05.
 * E-mail:656762935@qq.com
 */

public class DataBackListenerHelper<T> {

    private OnListDataBackListener mListListener;

    private OnObjectDataBackListener mObjectListener;

    public DataBackListenerHelper(OnListDataBackListener listener){
        mListListener = listener;
    }

    public DataBackListenerHelper(OnObjectDataBackListener listener){
        mObjectListener = listener;
    }

    public OnDataBackListener createOnListDataBackListener(boolean isLoadMore){
        return new OnDataBackListenerAdapter<T>(mListListener,isLoadMore);
    }

    public OnDataBackListener createOnObjectDataBackListener(){
        return new OnDataBackListenerAdapter<T>(mObjectListener);
    }

    /** 列表数据回调监听包装类 */
    public class OnDataBackListenerAdapter<T> implements OnDataBackListener<T> {

        /** 列表数据回调监听 */
        private OnListDataBackListener mListListener;

        private OnObjectDataBackListener mObjectListener;

        /** 是否加载更多 */
        private boolean mIsLoadMore;

        public OnDataBackListenerAdapter(OnListDataBackListener listener,boolean isLoadMore){
            mListListener = listener;
            mIsLoadMore = isLoadMore;
        }

        public OnDataBackListenerAdapter(OnObjectDataBackListener listener){
            mObjectListener = listener;
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onProgress(long curr, long max) {

        }

        @Override
        public void onSuccess(T data) {

            if(mListListener != null){
                if(mIsLoadMore){
                    mListListener.onLoadMoreData(data);
                }else{
                    mListListener.onRefreshData(data);
                }
            }

            if(mObjectListener != null){
                mObjectListener.onSuccess(data);
            }
        }

        @Override
        public void onFailed(NetData result) {

        }
    }

    public interface OnListDataBackListener<T>{
        /** 下拉刷新 */
        public void onRefreshData(T data);

        /** 加载更多 */
        public void onLoadMoreData(T data);
    }

    public interface OnObjectDataBackListener<T>{
        public void onSuccess(T data);
    }
}
