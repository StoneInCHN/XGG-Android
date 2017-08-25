package com.hentica.app.module.listener;

import com.hentica.app.lib.net.NetData;

/**
 * Created by Snow on 2016/12/21.
 */

public interface Callback<T> {

    void callback(boolean isSuccess, T data);

    void onFailed(NetData result);

}
