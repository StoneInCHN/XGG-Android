package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.mine.view.IOrderDeleteView;

/**
 * Created by Snow on 2017/7/5.
 */

public interface IOrderDeletePresenter {

    void attachView(IOrderDeleteView view);

    void detachView();

    void deleteOrder(String orderId);

}
