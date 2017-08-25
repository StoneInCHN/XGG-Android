package com.hentica.app.module.mine.presenter;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineRealNameVerification;
import com.hentica.app.module.mine.view.RealNameVerificationView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/23.
 */

public class RealNameVerificationPresenterImpl implements RealNameVerificationPresenter {
    private RealNameVerificationView mVerificationView;

    public RealNameVerificationPresenterImpl(RealNameVerificationView verificationView) {
        mVerificationView = verificationView;
    }

    @Override
    public void verification(String name, String number, String cardFrontPic, String cardBackPick) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number) || TextUtils.isEmpty(cardFrontPic)
                || TextUtils.isEmpty(cardBackPick)){
            mVerificationView.showToast("资料未填写完整！");
            return;
        }
        Request.getEndUserWithdrawConfirm(ApplicationData.getInstance().getLoginUserId(),
                name, number, cardFrontPic, cardBackPick,
                RebateListenerAdapter.createObjectListener(ResMineRealNameVerification.class,
                        new RebateDataBackListener<ResMineRealNameVerification>(mVerificationView) {

                            @Override
                            protected void onComplete(boolean isSuccess, ResMineRealNameVerification data) {

                            }

                            @Override
                            public void setResult(NetData data) {
                                if(data.isSuccess() && mVerificationView != null){
                                    mVerificationView.verificationSuccess(data.getErrMsg());
                                }
                            }
                        }));
    }
}
