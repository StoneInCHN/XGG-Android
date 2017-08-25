package com.hentica.app.module.mine.presenter.bank;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResIdCardData;
import com.hentica.app.module.mine.view.IdCardView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/6/2 0002.
 */

public class IdCardPresenterImpl implements IdCardPresetner {

    private IdCardView mIdCardView;

    public IdCardPresenterImpl(IdCardView idCardView) {
        mIdCardView = idCardView;
    }

    @Override
    public void getIdCard() {
        Request.getBankCardGetIdCard(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResIdCardData.class,
                        new UsualDataBackListener<ResIdCardData>(mIdCardView) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResIdCardData data) {
                                if(isSuccess){
                                    mIdCardView.setIdCardData(data);
                                }
                            }
                        }));
    }
}
