package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineQrCode;
import com.hentica.app.module.mine.view.QrCodeView;
import com.hentica.app.util.request.Request;
import com.hentica.pay.alipay.Base64;

/**
 * 我的二维码
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.16:46
 */

public class MineQrCodePresenterImpl implements QrCodePresenter {

    private QrCodeView mQrCodeView;

    public MineQrCodePresenterImpl(QrCodeView qrView){
        this.mQrCodeView = qrView;
    }

    @Override
    public void getQrCode() {
        Request.getEndUserGetQrCode(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResMineQrCode.class,
                        new UsualDataBackListener<ResMineQrCode>(mQrCodeView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResMineQrCode data) {
                                if(isSuccess){
                                    //转换成byte[]
                                    mQrCodeView.setQrCode(Base64.decode(data.getQrImage()));
                                    mQrCodeView.setData(data);
                                }
                            }
                        }));
    }
}
