package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.WithdrawalsView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/7/8.
 */

public class WidhdrawalsPresenter2Impl extends WithdrawalsPresenterImpl implements WithdrawalsPresenter2 {

    private WithdrawalsView mView;

    public WidhdrawalsPresenter2Impl(WithdrawalsView view) {
        super(view);
        mView = view;
    }

    @Override
    public void widthDrawals(String entityId, double withdrawalsAmount, String remark) {
        Request.getEndUserWithdrawConfirm(LoginModel.getInstance().getLoginUserId(),
                withdrawalsAmount, remark, entityId,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess){
                                    mView.withdrawalsSuccess();
                                }
                            }
                        }));
    }
}
