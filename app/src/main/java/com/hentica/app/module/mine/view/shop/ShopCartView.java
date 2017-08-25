package com.hentica.app.module.mine.view.shop;

import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResShpCartListData;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public interface ShopCartView extends IView {

    /**
     * 删除成功
     */
    void deleteSuccess(ResShpCartListData data);

    /**
     * 录单成功
     * @param data
     */
    void generateSuccess(ResGenerateOrder data);

}
