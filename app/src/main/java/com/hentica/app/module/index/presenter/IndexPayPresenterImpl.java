package com.hentica.app.module.index.presenter;

import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.ConfigKey;
import com.hentica.app.module.entity.index.IndexPayInfoData;
import com.hentica.app.module.entity.index.IndexPayTypeListData;
import com.hentica.app.module.index.view.I_IndexPayView;
import com.hentica.app.util.request.Request;

import java.util.List;

/**
 * Created by Snow on 2017/6/27.
 */

public class IndexPayPresenterImpl implements I_IndexPayPresenter {

    private I_IndexPayView mView;

    @Override
    public void attachView(I_IndexPayView view) {
        mView = view;
    }

    @Override
    public void detachView(I_IndexPayView view) {
        if (mView == view) {
            mView = null;
        }
    }

    @Override
    public void getPayInfo(String userId, String sellerId) {
//        requestPayListData(userId);//一期没有乐豆抵扣
        requestPayInfoData(userId, sellerId);//二期添加乐豆抵扣
    }

    /**
     * 获取支付方式
     */
    private void requestPayListData(String userId){
        Request.getSystemConfigGetConfigByKey(userId, ConfigKey.PAYMENTTYPE,
                ListenerAdapter.createArrayListener(IndexPayTypeListData.class, new UsualDataBackListener<List<IndexPayTypeListData>>(mView) {
                    @Override
                    protected void onComplete(boolean isSuccess, List<IndexPayTypeListData> data) {
                        if (mView != null) mView.setPayTypeDatas(isSuccess, data);
                    }
                }));
    }

    /**
     * 获取支付相关信息，包含支付方式、当前乐豆数量，当前乐分数量
     */
    private void requestPayInfoData(String userid, String sellerId){
        Request.getOrderGetPayInfo(userid, sellerId,
                ListenerAdapter.createObjectListener(IndexPayInfoData.class, new UsualDataBackListener<IndexPayInfoData>(mView) {
                    @Override
                    protected void onComplete(boolean isSuccess, IndexPayInfoData data) {
                        if (mView != null) mView.setPayInfoDatas(isSuccess, data);
                    }
                }));
    }
}
