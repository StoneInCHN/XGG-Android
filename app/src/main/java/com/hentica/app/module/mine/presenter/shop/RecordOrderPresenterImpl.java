package com.hentica.app.module.mine.presenter.shop;

import android.text.TextUtils;

import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.module.common.listener.ListenerAdapter;
import com.hentica.app.module.common.listener.UsualDataBackListener;
import com.hentica.app.module.entity.mine.shop.ResCustomerInfo;
import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResSellerInfo;
import com.hentica.app.module.entity.mine.shop.ResShopCartCount;
import com.hentica.app.module.mine.presenter.base.AbsPresenter;
import com.hentica.app.module.mine.view.shop.RecordOrderView;
import com.hentica.app.util.request.Request;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public class RecordOrderPresenterImpl extends AbsPresenter<RecordOrderView> implements RecordOrderPresenter{

    @Override
    public void getSellerInfo() {
        //获取店铺信息
        Request.getEndUserGetCurrentSellerInfo(ApplicationData.getInstance().getLoginUserId(),
                ListenerAdapter.createObjectListener(ResSellerInfo.class,
                        new UsualDataBackListener<ResSellerInfo>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResSellerInfo data) {
                                if(isSuccess && getView() != null){
                                    getView().setSellerInfo(data);
                                }
                            }
                        }));
    }

    @Override
    public void getCustomerInfo(String mobile) {
        //获取消费信息
        if(TextUtils.isEmpty(mobile)){
            getView().showToast("请输入消费者手机号！");
            return;
        }
        Request.getEndUserGetUserInfoByMobile(ApplicationData.getInstance().getLoginUserId(),
                mobile, ListenerAdapter.createObjectListener(ResCustomerInfo.class,
                        new UsualDataBackListener<ResCustomerInfo>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResCustomerInfo data) {
                                if(isSuccess && getView() != null){
                                    getView().setCustomerInfo(data);
                                }
                            }
                        }));
    }

    @Override
    public void addOrderToShopCart(long sellerId, long customerId, double amount, double discountAmount) {
        //加入购物车
        Request.getSellerOrderCartAdd(ApplicationData.getInstance().getLoginUserId(),
                String.valueOf(customerId), String.valueOf(sellerId), String.valueOf(amount),
                String.valueOf(discountAmount),
                ListenerAdapter.createObjectListener(ResShopCartCount.class,
                        new UsualDataBackListener<ResShopCartCount>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResShopCartCount data) {
                                if(isSuccess && getView() != null){
                                    getView().addShopCartSuccess(data);
                                }
                            }
                        }));
    }

    @Override
    public void generateOrder(long sellerId, long customerId, double amount, double discountAmount) {
        if(amount == 0){
            getView().showToast("");
            return;
        }
        //录单
        Request.getOrderGenerateSellerOrder(ApplicationData.getInstance().getLoginUserId(),
                String.valueOf(customerId), String.valueOf(amount), String.valueOf(sellerId),
                String.valueOf(discountAmount),
                ListenerAdapter.createObjectListener(ResGenerateOrder.class,
                        new UsualDataBackListener<ResGenerateOrder>(getView()) {
                            @Override
                            protected void onComplete(boolean isSuccess, ResGenerateOrder data) {
                                if(isSuccess && getView() != null){
                                    getView().generateOrderSuccess(data);
                                }
                            }
                        }));
    }

}
