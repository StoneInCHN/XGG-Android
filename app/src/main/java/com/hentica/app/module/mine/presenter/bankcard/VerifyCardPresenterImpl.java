package com.hentica.app.module.mine.presenter.bankcard;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.mine.view.bank.BankCardVerifyView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class VerifyCardPresenterImpl implements VerifyCardPresenter {

    private BankCardVerifyView mVerifyView;

    public VerifyCardPresenterImpl(BankCardVerifyView verifyView) {
        mVerifyView = verifyView;
    }

    @Override
    public void VerifyCard(String name, String bankCardId, String idCard, String mobile) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(bankCardId) || TextUtils.isEmpty(idCard) ||
                TextUtils.isEmpty(mobile)){
            mVerifyView.showToast("资料未填写完整！");
            return;
        }
        mVerifyView.setNextBtnEnable(false);
        Request.getBankCardVerifiCard(ApplicationData.getInstance().getLoginUserId(),
                name, bankCardId, idCard, mobile,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mVerifyView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if (mVerifyView != null) {
                                    mVerifyView.setNextBtnEnable(false);
                                    if (isSuccess) {
                                        mVerifyView.verifySuccess();
                                    }
                                }
                            }
                        }));
    }

    @Override
    public void detachView() {
        mVerifyView = null;
    }
}
