package com.hentica.app.module.mine.presenter;

import android.text.TextUtils;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.IOrderDeleteView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/7/5.
 */

public class OrderDeletePresenterImpl implements IOrderDeletePresenter {
    private IOrderDeleteView mView;

    @Override
    public void attachView(IOrderDeleteView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void deleteOrder(String orderId) {
        if (TextUtils.isEmpty(orderId)) {
            if (mView != null) mView.deleteOrderResult(false);
            return;
        }
        String userId = LoginModel.getInstance().getLoginUserId();
        Request.getOrderDelSellerUnpaidOrder(userId, orderId,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (mView != null) mView.deleteOrderResult(isSuccess);
                            }
                        }));
    }
}
