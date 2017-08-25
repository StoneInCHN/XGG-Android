package com.hentica.app.module.mine.presenter.shop;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.data.Constants;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.mine.view.shop.SalesAddShopView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/23.
 */

public class SalesAddShopPresenterImpl extends ShopSettlePresenterImpl implements SalesAddShopPresenter {
    private SalesAddShopView mView;
    public SalesAddShopPresenterImpl(SalesAddShopView mShopSettleView) {
        super(mShopSettleView);
        this.mView = mShopSettleView;
    }

    @Override
    public void checkPhone(String phone) {
        if(TextUtils.isEmpty(phone)){
            mView.showToast("店铺法人手机号未输入！");
            return;
        }
        if(phone.trim().length() != 11){
            mView.showToast("手机号输入错误！");
            return;
        }

        Request.getEndUserVerifyMobile(ApplicationData.getInstance().getLoginUserId(), phone.trim(),
                ListenerAdapter.createObjectListener(Void.class,
                        new UsualDataBackListener<Void>(mView) {
                            @Override
                            protected void onComplete(boolean isSuccess, Void data) {
                                mView.bindMember("");
                            }

                            @Override
                            public void onFailed(NetData result) {
                                mView.dismissLoadingDialog();
                                if(result.getErrCode() == Constants.CODE_BIND_MEMBER){
                                    mView.bindMember(result.getErrMsg());
                                }else if(result.getErrCode() == Constants.CODE_CAN_NOT_BIND){
                                    mView.cantBindMember(result.getErrMsg());
                                }else{
                                    super.onFailed(result);
                                }

                            }
                        }));
    }
}
