package com.hentica.app.module.mine.presenter.bankcard;

import android.os.Handler;
import android.text.TextUtils;

import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.entity.ResBankCardInfo;
import com.hentica.app.module.listener.Callback;
import com.hentica.app.module.mine.view.bank.BankCardCheckView;
import com.hentica.app.util.request.OkhttpUtils;

/**
 * Created by Snow on 2017/5/27 0027.
 */

public class BankCardCheckPresenterImpl implements BankCardCheckPresenter {

    private BankCardCheckView mCardCheckView;

    public BankCardCheckPresenterImpl(BankCardCheckView cardCheckView) {
        mCardCheckView = cardCheckView;
    }

    @Override
    public void checkBankCard(String cardId) {
        if(TextUtils.isEmpty(cardId)){
            mCardCheckView.showToast("卡号未填写！");
            return;
        }
        mCardCheckView.showLoadingDialog();
        Handler handler = new Handler(mCardCheckView.getUsualFragment().getContext().getMainLooper());
        OkhttpUtils.getBankCardInfo(handler, Constants.JUHE_BANKCARD_APPKEY, cardId, new Callback<ResBankCardInfo>() {
            @Override
            public void callback(boolean isSuccess, ResBankCardInfo data) {
                mCardCheckView.dismissLoadingDialog();
                if(isSuccess && data != null){
                    mCardCheckView.checkSuccess(data);
                }
            }

            @Override
            public void onFailed(NetData result) {
                mCardCheckView.dismissLoadingDialog();
                mCardCheckView.showToast("未获取到银行卡信息！");
            }
        });
    }
}
