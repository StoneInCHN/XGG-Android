package com.hentica.app.module.mine.presenter;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.ResMineEvaluateDetailData;
import com.hentica.app.module.mine.view.shop.EvaluateDetailView;
import com.hentica.app.util.request.Request;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/11.16:24
 */

public class EvaluateDetailPresenterImpl implements EvaluateDetailPresenter {

    private EvaluateDetailView mDetialView;

    public EvaluateDetailPresenterImpl(EvaluateDetailView detailView){
        this.mDetialView = detailView;
    }

    @Override
    public void getDetailData(String orderId) {
        String userId = ApplicationData.getInstance().getLoginUserId();
        Request.getOrderGetEvaluateByOrder(userId,orderId,
                ListenerAdapter.createObjectListener(ResMineEvaluateDetailData.class,
                        new UsualDataBackListener<ResMineEvaluateDetailData>(mDetialView) {
                    @Override
                    protected void onComplete(boolean isSuccess, ResMineEvaluateDetailData data) {
                        if(isSuccess){
                            // 请求成功
                            mDetialView.setDetailData(data);
                        }
                    }
                }));
    }


}
