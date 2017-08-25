package com.hentica.app.module.mine.presenter.shop;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;
import com.hentica.app.module.mine.view.shop.ShopInfoView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.11:18
 */

public class ShopInfoPresenterImpl implements ShopInfoPresenter {

    private ShopInfoView mShopInfoView;

    public ShopInfoPresenterImpl(ShopInfoView infoView){
        this.mShopInfoView = infoView;
    }

    @Override
    public void getShopInfo() {
        Request.getSellerGetSellerInfo(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResShopInfo.class,
                        new UsualDataBackListener<ResShopInfo>(mShopInfoView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResShopInfo data) {
                                if(isSuccess){
                                    mShopInfoView.setShopInfoData(data);
                                }else{
                                    mShopInfoView.failure();
                                }
                            }
                        }));
    }
}
