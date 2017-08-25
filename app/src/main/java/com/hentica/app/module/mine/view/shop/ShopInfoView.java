package com.hentica.app.module.mine.view.shop;

import com.hentica.app.framework.fragment.FragmentListener;
import com.hentica.app.module.entity.mine.shop.ResShopInfo;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/6.11:18
 */

public interface ShopInfoView extends FragmentListener.UsualViewOperator{

    /**
     * 店铺信息
     * @param data
     */
    void setShopInfoData(ResShopInfo data);

    void failure();

}
