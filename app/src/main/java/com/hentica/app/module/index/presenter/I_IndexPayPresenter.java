package com.hentica.app.module.index.presenter;

import com.hentica.app.module.index.view.I_IndexPayView;

/**
 * Created by Snow on 2017/6/27.
 */

public interface I_IndexPayPresenter {

    void attachView(I_IndexPayView view);

    void detachView(I_IndexPayView view);

    /**
     * 获取支付信息
     * @param sellerId
     */
    void getPayInfo(String userId, String sellerId);

}
