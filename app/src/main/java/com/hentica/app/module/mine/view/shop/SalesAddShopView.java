package com.hentica.app.module.mine.view.shop;

/**
 * Created by Snow on 2017/5/23.
 */

public interface SalesAddShopView extends ShopSettleView {

    /**
     * 绑定会员
     */
    void bindMember(String msg);

    /**
     * 不能绑定
     * @param msg
     */
    void cantBindMember(String msg);

}
