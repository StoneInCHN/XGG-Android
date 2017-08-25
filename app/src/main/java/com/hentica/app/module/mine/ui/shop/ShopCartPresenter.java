package com.hentica.app.module.mine.ui.shop;

import com.hentica.app.module.entity.mine.shop.ResShpCartListData;
import com.hentica.app.module.mine.presenter.base.IPresenter;
import com.hentica.app.module.mine.view.shop.ShopCartView;

import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public interface ShopCartPresenter extends IPresenter<ShopCartView>{

    /**
     * 删除购物车中订单
     * @param data 要删除的订单
     */
    void deleteOrders(ResShpCartListData data);

    /**
     * 支付
     * @param datas
     * @param sellerId 商铺id
     */
    void toPayOrders(List<ResShpCartListData> datas, long sellerId);

}
