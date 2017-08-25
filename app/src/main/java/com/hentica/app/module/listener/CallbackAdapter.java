package com.hentica.app.module.listener;

import com.hentica.app.lib.net.NetData;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/2/27.21:08
 */

public class CallbackAdapter<T> implements Callback<T> {

    @Override
    public void callback(boolean isSuccess, T data) {

    }

    @Override
    public void onFailed(NetData result) {

    }
}
