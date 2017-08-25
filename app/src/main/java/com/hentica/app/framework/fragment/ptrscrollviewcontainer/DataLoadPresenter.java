package com.hentica.app.framework.fragment.ptrscrollviewcontainer;

import com.hentica.app.module.listener.Callback;

/**
 * Created by Snow on 2017/2/7.
 */

public interface DataLoadPresenter<T> {

    /**
     * 获取数据
     * @param l 回调
     * @param params 参数
     */
    void loadData(Callback<T> l, String... params);

}
