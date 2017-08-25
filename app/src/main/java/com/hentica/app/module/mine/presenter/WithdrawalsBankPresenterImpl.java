package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResBankCardData;
import com.hentica.app.module.mine.view.WithdrawalsBankView;
import com.hentica.app.module.mine.view.WithdrawalsView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class WithdrawalsBankPresenterImpl extends WidhdrawalsPresenter2Impl implements WithdrawalsBankPresenter {
    private WithdrawalsBankView mBankView;
    public WithdrawalsBankPresenterImpl(WithdrawalsBankView view) {
        super(view);
        this.mBankView = view;
    }

    @Override
    public void getDefaultCard() {
        Request.getBankCardGetDefaultCard(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResBankCardData.class,
                        new UsualDataBackListener<ResBankCardData>(mBankView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResBankCardData data) {
                                if(isSuccess){
                                    mBankView.setBankCardData(data);
                                }
                            }
                        }));
    }
}
