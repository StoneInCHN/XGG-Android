package com.hentica.app.module.mine.view.shop;

import com.hentica.app.module.entity.mine.shop.ResCustomerInfo;
import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResSellerInfo;
import com.hentica.app.module.entity.mine.shop.ResShopCartCount;

/**
 * Created by Snow on 2017/5/24 0024.
 */

public interface RecordOrderView extends IView {

    /**
     * 店铺信息
     * @param data
     */
    void setSellerInfo(ResSellerInfo data);

    /**
     * 消费者信息
     * @param data
     */
    void setCustomerInfo(ResCustomerInfo data);

    /**
     * 添加购物车成功
     */
    void addShopCartSuccess(ResShopCartCount data);

    /**
     * 录单成功
     * @param data
     */
    void generateOrderSuccess(ResGenerateOrder data);

}
