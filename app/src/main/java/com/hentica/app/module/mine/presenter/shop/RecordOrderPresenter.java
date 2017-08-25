package com.hentica.app.module.mine.presenter.shop;


import com.hentica.app.module.mine.presenter.base.IPresenter;
import com.hentica.app.module.mine.view.shop.RecordOrderView;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public interface RecordOrderPresenter extends IPresenter<RecordOrderView>{

    /**
     * 获取店铺信息
     */
    void getSellerInfo();

    /**
     * 获取消费者信息
     * @param mobile 消费者电话
     */
    void getCustomerInfo(String mobile);

    /**
     * 添加到购物车
     * @param sellerId 店铺id
     * @param customerId 消费者id
     * @param amount 消费金额
     */
    void addOrderToShopCart(long sellerId, long customerId, double amount, double discountAmount);

    /**
     * 录单
     * @param sellerId 店铺id
     * @param customerId 消费者id
     * @param amount 消费金额
     */
    void generateOrder(long sellerId, long customerId, double amount, double discountAmount);

}
