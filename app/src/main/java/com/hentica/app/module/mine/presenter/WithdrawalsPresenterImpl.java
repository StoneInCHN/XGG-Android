package com.hentica.app.module.mine.presenter;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineWithdrawalsInfo;
import com.hentica.app.module.login.business.LoginModel;
import com.hentica.app.module.mine.view.WithdrawalsView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/3.
 */

public class WithdrawalsPresenterImpl implements WithdrawalsPresenter {

    private WithdrawalsView mWithdrawalsView;

    public WithdrawalsPresenterImpl(WithdrawalsView view){
        this.mWithdrawalsView = view;
    }

    @Override
    public void getWithdrawalsInfo() {
        Request.getEndUserGetWithdrawInfo(LoginModel.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResMineWithdrawalsInfo.class,
                        new UsualDataBackListener<ResMineWithdrawalsInfo>(mWithdrawalsView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResMineWithdrawalsInfo data) {
                                mWithdrawalsView.setWithdrawalsInfo(isSuccess, data);
                            }
                        }));
    }

    @Override
    public void widthDrawals(String entityId, String payPwd, String remark) {
        Request.getEndUserWithdrawConfirm(LoginModel.getInstance().getLoginUserId(),
                payPwd, remark, entityId, ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mWithdrawalsView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess){
                                    mWithdrawalsView.withdrawalsSuccess();
                                }
                            }
                        }));
    }


}
