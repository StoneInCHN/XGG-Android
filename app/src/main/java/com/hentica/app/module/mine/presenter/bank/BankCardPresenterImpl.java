package com.hentica.app.module.mine.presenter.bank;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.RebateDataBackListener;
import com.hentica.app.module.common.listener.RebateListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResIdCardData;
import com.hentica.app.module.mine.view.BankCardView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class BankCardPresenterImpl implements BankCardPresenter {

    private BankCardView mCardView;

    public BankCardPresenterImpl(BankCardView cardView) {
        mCardView = cardView;
    }

    @Override
    public void getIdCard() {
        IdCardPresetner presetner = new IdCardPresenterImpl(mCardView);
        presetner.getIdCard();
    }

    @Override
    public void deleteCard(String cardId) {
        if(TextUtils.isEmpty(cardId)){
            mCardView.showToast("请输入卡号！");
            return;
        }
        Request.getBankCardDelCard(ApplicationData.getInstance().getLoginUserId(), cardId,
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mCardView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess){
                                    mCardView.deleteSuccess();
                                }
                            }
                        }));
    }

    @Override
    public void setDefault(String cardId) {
        if(TextUtils.isEmpty(cardId)){
            mCardView.showToast("请输入卡号！");
            return;
        }
        Request.getBankCardUpdateCardDefault(ApplicationData.getInstance().getLoginUserId(), cardId,
                RebateListenerAdapter.createObjectListener(Void.class,
                        new RebateDataBackListener<Void>(mCardView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                if(isSuccess){
                                    mCardView.setDefaultSuccess();
                                }
                            }

                            @Override
                            public void setResult(NetData data) {
                                super.setResult(data);
                                if(data.isSuccess()){
                                    mCardView.showToast(data.getErrMsg());
                                }
                            }
                        }));
    }
}
