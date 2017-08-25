package com.hentica.app.module.mine.presenter.base;

import com.hentica.app.module.mine.view.shop.IView;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public interface IPresenter<V extends IView> {
    /**
     * 绑定界面
     * @param view
     */
    void attachView(V view);

    /**
     * 界面解绑
     */
    void detachView();

}
