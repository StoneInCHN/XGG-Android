package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResShopQrCode;
import com.hentica.app.module.mine.view.QrCodeView;
import com.hentica.app.util.request.Request;
import com.hentica.pay.alipay.Base64;

/**
 * 店铺收款二维码
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.19:27
 */

public class ShopQrCodePresenterImpl implements QrCodePresenter {

    private String entityId;
    private QrCodeView mQrView;

    public ShopQrCodePresenterImpl(QrCodeView qrView, String entityId){
        this.mQrView = qrView;
        this.entityId = entityId;
    }

    @Override
    public void getQrCode() {
        Request.getSellerGetQrCode(ApplicationData.getInstance().getLoginUserId(), entityId,
                ListenerAdapter.createObjectListener(ResShopQrCode.class,
                        new UsualDataBackListener<ResShopQrCode>(mQrView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResShopQrCode data) {
                                if(isSuccess){
                                    //转换成byte[]
                                    mQrView.setQrCode(Base64.decode(data.getQrImage()));
                                    mQrView.setData(data);
                                }
                            }
                        }));

    }
}
